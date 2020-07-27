package de.neusta.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author limmoor
 * @since 07.08.2018
 */
@Component
public class CsvParser {

  private static final Logger LOG = LoggerFactory.getLogger(CSVUploadController.class);

  /**
   * @param file
   * @return file-content line seperated
   * @author limmoor
   * @throws FileNotFoundException
   * @since 07.08.2018
   */
  public List<String> parseContent(final File file) throws FileNotFoundException {
    final List<String> content = new ArrayList<>();
    final String delimiter = "\\" + System.getProperty("line.separator");
    try (Scanner scanner = new Scanner(file)) {
      scanner.useDelimiter(delimiter);
      while (scanner.hasNext()) {
        content.add(scanner.next());
      }
    } catch (final NullPointerException cause) {
      LOG.error("file was null", cause);
      throw new FileNotFoundException();
    }
    return content;
  }

}
