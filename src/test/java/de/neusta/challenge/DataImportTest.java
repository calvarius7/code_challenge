package de.neusta.challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataImportTest {

  private DataImport dataImport;
  private List<String> csvLines;

  @BeforeEach
  public void setUp() throws Exception {
    this.dataImport = new DataImport();
    this.csvLines = new ArrayList<>();
  }

  /**
   * @author limmoor
   * @since 08.08.2018
   */
  @Test
  public void testImportRooms() {

    // @WHEN
    final List<Room> rooms = this.dataImport.importRooms(this.csvLines);

    // @THEN
    assertNotNull(rooms);
    assertEquals(0, rooms.size());

  }

  /**
   * @author limmoor
   * @since 08.08.2018
   */
  @Test
  public void testImportRoomsCaseOneValidRoom() {

    // @GIVEN
    final String validRoomLine = "1111,";
    this.csvLines.add(validRoomLine);

    // @WHEN
    final List<Room> rooms = this.dataImport.importRooms(this.csvLines);

    // @THEN
    assertEquals(1, rooms.size());
    assertEquals("1111", rooms.get(0).getRoomNumber());

  }

  /**
   * @author limmoor
   * @since 08.08.2018
   */
  @Test
  public void testImportRoomsCaseTwoValidRooms() {

    // @GIVEN
    String validRoomLine = "1111,";
    this.csvLines.add(validRoomLine);
    validRoomLine = "8282,,,";
    this.csvLines.add(validRoomLine);

    // @WHEN
    final List<Room> rooms = this.dataImport.importRooms(this.csvLines);

    // @THEN
    assertEquals(2, rooms.size());
    assertEquals("8282", rooms.get(1).getRoomNumber());

  }

  /**
   * @author limmoor
   * @since 08.08.2018
   */
  @Test
  public void testImportRoomsCaseNull() {

    final List<Room> rooms = this.dataImport.importRooms(null);

    // @THEN
    assertEquals(0, rooms.size());
  }

  /**
   * @author limmoor
   * @since 08.08.2018
   */
  @Test
  public void testImportRoomsOneValidOneInvalid() {

    // @GIVEN
    final String validRoomLine = "1111,";
    final String invalidRoomLine = "";
    this.csvLines.add(validRoomLine);
    this.csvLines.add(invalidRoomLine);

    // @WHEN
    final List<Room> rooms = this.dataImport.importRooms(this.csvLines);

    // @THEN
    assertEquals(1, rooms.size());
  }

  /**
   * @author limmoor
   * @since 08.08.2018
   */
  @Test
  public void testImportRoomsCaseWithOnePerson() {

    // @GIVEN
    final String validRoomLineWithSimplPerson = "1111,Harry Potter";
    this.csvLines.add(validRoomLineWithSimplPerson);

    // @WHEN
    final List<Room> rooms = this.dataImport.importRooms(this.csvLines);

    // @THEN
    assertEquals(1, rooms.get(0).getPeople().size());

  }

  /**
   * @author limmoor
   * @since 08.08.2018
   */
  @Test
  public void testImportRoomsCaseEmptyPersonSpaceInList() {

    // @GIVEN
    final String validRoomLineWithSimplPerson = "1111,,Harry Potter";
    this.csvLines.add(validRoomLineWithSimplPerson);

    // @WHEN
    final List<Room> rooms = this.dataImport.importRooms(this.csvLines);

    // @THEN
    assertEquals(1, rooms.get(0).getPeople().size());

  }

  /**
   * @author limmoor
   * @since 08.08.2018
   */
  @Test
  public void testImportRoomsCaseWhiteSpacesPerson() {

    // @GIVEN
    final String validRoomLineWithSimplPerson = "1111,   ,Harry Potter";
    this.csvLines.add(validRoomLineWithSimplPerson);

    // @WHEN
    final List<Room> rooms = this.dataImport.importRooms(this.csvLines);

    // @THEN
    final List<Person> people = rooms.get(0).getPeople();
    assertEquals(1, people.size());
    assertEquals("Harry", people.get(0).getFirstName());
    assertEquals("Potter", people.get(0).getLastName());

  }

  /**
   * @author limmoor
   * @since 08.08.2018
   */
  @Test
  public void testImportRoomsCaseCommaEnd() {

    // @GIVEN
    final String validRoomLineWithSimplPerson = "1111,Harry Potter,";
    this.csvLines.add(validRoomLineWithSimplPerson);

    // @WHEN
    final List<Room> rooms = this.dataImport.importRooms(this.csvLines);

    // @THEN
    assertEquals(1, rooms.get(0).getPeople().size());

  }

}
