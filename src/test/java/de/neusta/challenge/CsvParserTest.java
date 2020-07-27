package de.neusta.challenge;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvParserTest {

  private CsvParser csvParser;

  @BeforeEach
  private void setUp() {
    this.csvParser = new CsvParser();
  }

  /**
   * @author limmoor
   * @throws FileNotFoundException
   * @since 07.08.2018
   */
  @Test
  public void testParseContentCaseValidFile() throws FileNotFoundException {

    final File file = new File("./src/main/resources/sitzplan.csv");
    // @WHEN
    final List<String> content = this.csvParser.parseContent(file);
    // @THEN
    BDDAssertions.then(content).hasSize(15);

  }

  /**
   * @author limmoor
   * @since 07.08.2018
   */
  @Test
  public void testParseContentInvalidFile() {

    // @GIVEN
    final File file = new File("");
    // @WHEN

    // @THEN
    assertThrows(FileNotFoundException.class, () -> this.csvParser.parseContent(file));
  }

  /**
   * @author limmoor
   * @since 07.08.2018
   */
  @Test
  public void testParseContentCaseFileNull() {

    // @GIVEN
    final File file = null;

    // @THEN
    assertThrows(FileNotFoundException.class, () -> this.csvParser.parseContent(file));

  }
}
