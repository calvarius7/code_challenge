package de.neusta.challenge;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author limmoor
 * @since 10.08.2018
 */
@Component
public class MultiPartConverter {

  private static final Logger LOG = LoggerFactory.getLogger(MultiPartConverter.class);

  /**
   * @param multipartFile
   * @return converted File
   * @author limmoor
   * @since 10.08.2018
   */
  public File convert(final MultipartFile multipartFile) {
    try {
      final String filePath = System.getProperty("java.io.tmpdir")
          + multipartFile.getOriginalFilename();
      final File convFile = new File(filePath);
      multipartFile.transferTo(convFile);
      return convFile;
    } catch (IllegalStateException | IOException cause) {
      LOG.error("convert error:", cause.getMessage());
      throw new ValidationException(ErrorMessage.CONVERT_ERROR.asJSON());
    }
  }

}
