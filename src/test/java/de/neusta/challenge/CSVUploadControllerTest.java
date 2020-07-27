package de.neusta.challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

public class CSVUploadControllerTest {

  private CSVUploadController csvUploadController;

  @Mock
  private CsvParser csvParserMock;

  @Mock
  private DataValidator dataValidatorMock;

  @Mock
  private DataImport dataImportMock;

  @Mock
  private Storage storageMock;

  @Mock
  private MultipartFile multipartFileMock;

  @Mock
  private MultiPartConverter multiPartConverterMock;

  /**
   * @author limmoor
   * @since 07.08.2018
   */
  @BeforeEach
  private void setUp() {
    MockitoAnnotations.initMocks(this);
    this.csvUploadController = new CSVUploadController(this.csvParserMock, this.dataValidatorMock,
        this.dataImportMock, this.storageMock, this.multiPartConverterMock);
  }

  /**
   * @author limmoor
   * @throws FileNotFoundException
   * @since 07.08.2018
   */
  @Test
  public void testUploadCaseInvalidContent() throws FileNotFoundException {

    // @GIVEN
    final File file = this.givenFile();
    Mockito.when(this.csvParserMock.parseContent(file))
        .thenThrow(new RuntimeException("invalid content"));

    // @WHEN
    final ResponseEntity<String> errorResponse = this.csvUploadController
        .upload(this.multipartFileMock);

    // @THEN
    assertEquals(errorResponse.getStatusCode(), HttpStatus.BAD_REQUEST);
    assertEquals("invalid content", errorResponse.getBody());
  }

  /**
   * @author limmoor
   * @throws FileNotFoundException
   * @since 07.08.2018
   */
  @Test
  public void testUploadCaseParseFileInvalidCSV() throws FileNotFoundException {

    // @GIVEN
    final File file = this.givenFile();
    final List<String> value = new ArrayList<>();
    BDDMockito.given(this.csvParserMock.parseContent(file)).willReturn(value);

    // @WHEN
    final ResponseEntity<String> response = this.csvUploadController.upload(this.multipartFileMock);

    // @THEN
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals(ErrorMessage.INVALID_CSV.asJSON(), response.getBody());
    BDDMockito.then(this.csvParserMock).should().parseContent(file);
  }

  /**
   * @author limmoor
   * @throws FileNotFoundException
   * @since 09.08.2018
   */
  @SuppressWarnings("boxing")
  @Test
  public void testUploadCaseValidFileWithImport() throws FileNotFoundException {

    final List<String> lines = this.getValidCSV();

    final List<Room> value = new ArrayList<>();
    BDDMockito.given(this.dataImportMock.importRooms(lines)).willReturn(value);
    BDDMockito.given(this.dataValidatorMock.hasRoomDuplicates(value)).willReturn(false);

    // @WHEN
    final ResponseEntity<String> response = this.csvUploadController.upload(this.multipartFileMock);

    // @THEN
    assertEquals(HttpStatus.OK, response.getStatusCode());
    BDDMockito.then(this.multiPartConverterMock).should().convert(this.multipartFileMock);
    BDDMockito.then(this.dataImportMock).should().importRooms(lines);
    BDDMockito.then(this.dataValidatorMock).should().hasRoomDuplicates(value);
    BDDMockito.then(this.storageMock).should().saveRooms(value);
  }

  /**
   * @author limmoor
   * @throws FileNotFoundException
   * @since 14.08.2018
   */
  @SuppressWarnings("boxing")
  @Test
  public void testUploadCaseDuplicateRooms() throws FileNotFoundException {

    // @GIVEN
    final List<String> lines = this.getValidCSV();

    final List<Room> value = new ArrayList<>();
    BDDMockito.given(this.dataImportMock.importRooms(lines)).willReturn(value);
    BDDMockito.given(this.dataValidatorMock.hasRoomDuplicates(value)).willReturn(true);

    // @WHEN
    final ResponseEntity<String> response = this.csvUploadController.upload(this.multipartFileMock);

    // @THEN
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals(ErrorMessage.ROOM_DUPLICATE.asJSON(), response.getBody());
    BDDMockito.then(this.storageMock).shouldHaveZeroInteractions();

  }

  /**
   * @author limmoor
   * @throws FileNotFoundException
   * @throws JsonProcessingException
   * @since 15.08.2018
   */
  @SuppressWarnings("boxing")
  @Test
  public void testUploadCaseDuplicatePeople()
      throws FileNotFoundException, JsonProcessingException {

    final List<String> lines = this.getValidCSV();

    final List<Room> value = new ArrayList<>();
    BDDMockito.given(this.dataImportMock.importRooms(lines)).willReturn(value);
    BDDMockito.given(this.dataValidatorMock.hasRoomDuplicates(value)).willReturn(false);
    BDDMockito.given(this.dataValidatorMock.hasPeopleDuplicates(value)).willReturn(true);

    // @WHEN
    final ResponseEntity<String> response = this.csvUploadController.upload(this.multipartFileMock);

    // @THEN
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals(ErrorMessage.PERSON_DUPLICATE.asJSON(), response.getBody());
    BDDMockito.then(this.storageMock).shouldHaveZeroInteractions();

  }

  /**
   * @return valid CSV parsed into List<String>
   * @throws FileNotFoundException
   * @author limmoor
   * @since 15.08.2018
   */
  @SuppressWarnings("boxing")
  private List<String> getValidCSV() throws FileNotFoundException {
    final File file = this.givenFile();
    final List<String> lines = new ArrayList<>();

    BDDMockito.given(this.csvParserMock.parseContent(file)).willReturn(lines);
    BDDMockito.given(this.dataValidatorMock.validateCsvLine(lines)).willReturn(true);
    return lines;
  }

  /**
   * @return file based on multipartfile
   * @author limmoor
   * @since 10.08.2018
   */
  private File givenFile() {
    final File file = new File("");
    BDDMockito.given(this.multiPartConverterMock.convert(this.multipartFileMock)).willReturn(file);
    return file;
  }

}
