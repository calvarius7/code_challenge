package de.neusta.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiOperation;

/**
 * @author limmoor
 * @since 07.08.2018
 */
@RestController
public class CSVUploadController {

  private static final Logger LOG = LoggerFactory.getLogger(CSVUploadController.class);
  private final CsvParser csvParser;
  private final DataValidator dataValidator;
  private final DataImport dataImport;
  private final Storage storage;
  private final MultiPartConverter multiPartConverter;

  /**
   * @param csvParser
   * @param dataValidator
   * @param dataImport
   * @param storageMock
   * @param multiPartConverter
   */
  public CSVUploadController(final CsvParser csvParser, final DataValidator dataValidator,
      final DataImport dataImport, final Storage storageMock,
      final MultiPartConverter multiPartConverter) {
    this.csvParser = csvParser;
    this.dataValidator = dataValidator;
    this.dataImport = dataImport;
    this.storage = storageMock;
    this.multiPartConverter = multiPartConverter;

  }

  /**
   * @param multiPartFile
   * @author limmoor
   * @return HttpStatus.OK or BAD_REQUEST
   * @since 07.08.2018
   */
  @ApiOperation("import file")
  @PostMapping("/api/import")
  public ResponseEntity<String> upload(@RequestParam("file") final MultipartFile multiPartFile) {
    try {
      final List<String> fileContent = this.parseFile(multiPartFile);
      final List<Room> rooms = this.getRooms(fileContent);
      return this.saveRooms(rooms);
    } catch (final Exception cause) {
      LOG.error("failed to read file", cause);
      return ResponseEntity.badRequest().body(cause.getMessage());
    }
  }

  /**
   * @param fileContent
   * @author limmoor
   * @return list of all rooms if everything is validated
   * @throws ValidationException
   * @since 14.08.2018
   */
  private List<Room> getRooms(final List<String> fileContent) throws ValidationException {
    this.validateCSV(fileContent);
    final List<Room> rooms = this.dataImport.importRooms(fileContent);
    this.validateDuplicates(rooms);
    return rooms;
  }

  /**
   * @param fileContent
   * @author limmoor
   * @since 14.08.2018
   */
  private void validateCSV(final List<String> fileContent) {
    if (!this.dataValidator.validateCsvLine(fileContent)) {
      throw new ValidationException(ErrorMessage.INVALID_CSV.asJSON());
    }
  }

  /**
   * @param rooms
   * @author limmoor
   * @since 14.08.2018
   */
  private void validateDuplicates(final List<Room> rooms) {
    if (this.dataValidator.hasRoomDuplicates(rooms)) {
      throw new ValidationException(ErrorMessage.ROOM_DUPLICATE.asJSON());
    }
    if (this.dataValidator.hasPeopleDuplicates(rooms)) {
      throw new ValidationException(ErrorMessage.PERSON_DUPLICATE.asJSON());
    }
  }

  /**
   * @param multiPartFile
   * @return content of a csv-file as List<String>
   * @throws FileNotFoundException
   * @author limmoor
   * @since 14.08.2018
   */
  private List<String> parseFile(final MultipartFile multiPartFile) throws FileNotFoundException {
    final File file = this.multiPartConverter.convert(multiPartFile);
    final List<String> fileContent = this.csvParser.parseContent(file);
    return fileContent;
  }

  /**
   * Save all rooms to storage
   *
   * @param rooms
   * @return Response.ok
   * @author limmoor
   * @since 14.08.2018
   */
  private ResponseEntity<String> saveRooms(final List<Room> rooms) {
    this.storage.saveRooms(rooms);
    return ResponseEntity.ok().body("room list uploaded");
  }

}
