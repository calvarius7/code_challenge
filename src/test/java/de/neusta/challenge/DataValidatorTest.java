package de.neusta.challenge;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class DataValidatorTest {

  private DataValidator validator;
  private List<String> lines;
  private String line;

  @BeforeEach
  void setUp() {
    this.validator = new DataValidator();
    this.lines = new ArrayList<>();
  }

  /**
   * @author limmoor
   * @since 08.08.2018
   */
  @Test
  public void testValidateCsvLineCaseEmptyString() {

    // @GIVEN
    this.line = "";
    this.lines.add(this.line);
    // @THEN
    assertFalse(this.validator.validateCsvLine(this.lines));

  }

  /**
   * @author limmoor
   * @since 08.08.2018
   */
  @Test
  public void testValidateCsvLineCaseLineNull() {

    this.line = null;
    this.lines.add(this.line);

    assertFalse(this.validator.validateCsvLine(this.lines));
  }

  /**
   * @author limmoor
   * @since 08.08.2018
   */
  @Test
  public void testValidateCsvLineCaseEmptyList() {

    assertFalse(this.validator.validateCsvLine(null));
  }

  /**
   * @author limmoor
   * @param invalidLine
   * @since 08.08.2018
   */
  @ParameterizedTest
  @ValueSource(strings = { ";", ",,,,,,", "11111111" })
  public void testValidateCsvLineCaseInvalidCSV(final String invalidLine) {

    // @GIVEN
    // CSV sperator"," "4-chars roomnumber,Name,Name..."

    this.lines.add(invalidLine);

    assertFalse(this.validator.validateCsvLine(this.lines));
  }

  /**
   * @author limmoor
   * @param validCSVLine
   * @since 08.08.2018
   */
  @ParameterizedTest
  @MethodSource("createValidCSVCases")
  public void testValidateCsvLineCaseValidCSV(final String validCSVLine) {

    // @GIVEN
    this.lines.add(validCSVLine);

    // @THEN
    assertTrue(this.validator.validateCsvLine(this.lines));

  }

  @SuppressWarnings("unused")
  private static Stream<Arguments> createValidCSVCases() {
    return Stream.of(
    //@formatter:off
        Arguments.of("1102,,,,"),
        Arguments.of("1113,Claudia Fleuter (cfleuter),Sabine Strodthoff (sstrodthoff),,"+ System.getProperty("line.separator")),
        Arguments.of("1113,THomas von Gostomski (tgostomski)\r"),
        Arguments.of("1110,Christina Hülsemann (chuelsemann),Iftikar Ahmad Khan (ikhan),Ralf Schmidt (rschmidt)"),
        Arguments.of("1110,Dr.Dr."));
    //@formatter:on
  }

  // ROOM NUMBER VALIDATION
  // 4 characters

  /**
   * @author limmoor
   * @since 08.08.2018
   */
  @Test
  public void testRoomNumberValidationCaseValidRoom() {

    // @GIVEN
    this.line = "1111";

    // @THEN
    assertTrue(this.validator.roomNumberValidation(this.line));
  }

  /**
   * @author limmoor
   * @since 08.08.2018
   */
  @Test
  public void testRoomNumberValidationCaseToShort() {

    // @GIVEN
    this.line = "111";

    // @THEN
    assertFalse(this.validator.roomNumberValidation(this.line));

    // @GIVEN
    this.line = "";

    // @THEN
    assertFalse(this.validator.roomNumberValidation(this.line));
  }

  /**
   * @author limmoor
   * @since 08.08.2018
   */
  @Test
  public void testRoomNumberValidationCaseToLong() {

    // @GIVEN
    this.line = "11111";

    // @THEN
    assertFalse(this.validator.roomNumberValidation(this.line));

    // @GIVEN
    this.line = "56546546546541321635465135131541656asd465as45f45as4f65as4df654asf5asasff46af465asf465asf";

    // @THEN
    assertFalse(this.validator.roomNumberValidation(this.line));
  }

  /**
   * @author limmoor
   * @since 08.08.2018
   */
  @Test
  public void testRoomNumberValidationCaseNull() {
    assertFalse(this.validator.roomNumberValidation(null));
  }

  /**
   * @author limmoor
   * @since 14.08.2018
   */
  @Test
  public void testHasRoomDuplicatesCaseDuplicate() {

    // @GIVEN
    final List<Room> roomList = new ArrayList<>();
    final Room room = new Room("1111");
    roomList.add(room);
    roomList.add(room);

    // @THEN

    assertTrue(this.validator.hasRoomDuplicates(roomList));
  }

  /**
   * @author limmoor
   * @since 14.08.2018
   */
  @Test
  public void testHasRoomDuplicatesCaseSameRoomNumber() {

    // @GIVEN
    final List<Room> roomList = new ArrayList<>();
    roomList.add(new Room("1111"));
    roomList.add(new Room("1111"));

    // @THEN
    assertTrue(this.validator.hasRoomDuplicates(roomList));
  }

  /**
   * @author limmoor
   * @since 14.08.2018
   */
  @Test
  public void testHasRoomDuplicatesCaseNoDuplicates() {

    // @GIVEN
    final List<Room> roomList = new ArrayList<>();
    roomList.add(new Room("1111"));
    roomList.add(new Room("1234"));

    // @THEN
    assertFalse(this.validator.hasRoomDuplicates(roomList));
  }

  /**
   * @author limmoor
   * @since 15.08.2018
   */
  @Test
  public void testHasPeopleDuplicatesCaseNoDuplicates() {

    // @GIVEN
    final Person person = this.getPersonWithData();
    final Person differentPerson = new Person();
    differentPerson.setData("Dr. Toni Tester (ttester)");

    final Room room = new Room("1111");
    room.addPerson(person);
    room.addPerson(differentPerson);
    final List<Room> roomList = new ArrayList<>();
    roomList.add(room);

    // @THEN
    assertFalse(this.validator.hasPeopleDuplicates(roomList));

  }

  /**
   * @author limmoor
   * @since 15.08.2018
   */
  @Test
  public void testHasPeopleDuplicatesCaseDuplicates() {

    final Person person = this.getPersonWithData();
    final Room room = new Room("1111");
    // Duplicate person
    room.addPerson(person);
    room.addPerson(person);

    final List<Room> roomList = new ArrayList<>();
    roomList.add(room);

    // @THEN
    assertTrue(this.validator.hasPeopleDuplicates(roomList));

  }

  /**
   * @author limmoor
   * @since 15.08.2018
   */
  @Test
  public void testHasPeopleDuplicatesCaseDuplicatesSameData() {

    final Person person = this.getPersonWithData();
    final Person personDuplicate = this.getPersonWithData();
    final Room room = new Room("1111");
    // Duplicate person
    room.addPerson(person);
    room.addPerson(personDuplicate);

    final List<Room> roomList = new ArrayList<>();
    roomList.add(room);

    // @THEN
    assertTrue(this.validator.hasPeopleDuplicates(roomList));

  }

  /**
   * @author limmoor
   * @since 15.08.2018
   */
  @Test
  public void testHasPeopleDuplicateCaseDuplicateDifferentRooms() {

    final Person person = this.getPersonWithData();
    final Room room = new Room("1234");
    final Person personDuplicate = this.getPersonWithData();
    final Room differentRoom = new Room("1111");
    // Duplicate person
    room.addPerson(person);
    differentRoom.addPerson(personDuplicate);

    final List<Room> roomList = new ArrayList<>();
    roomList.add(room);
    roomList.add(differentRoom);

    // @THEN
    assertTrue(this.validator.hasPeopleDuplicates(roomList));
  }

  /**
   * @return a person with data for equalstest
   * @author limmoor
   * @since 15.08.2018
   */
  private Person getPersonWithData() {
    final Person person = new Person();
    person.setData("Dr. Hans Otto von Schmidt (hschmidt)");
    return person;
  }
}
