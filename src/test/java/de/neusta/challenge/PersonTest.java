package de.neusta.challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonTest {

  private Person person;

  @BeforeEach
  void setUp() {
    this.person = new Person();
  }

  /**
   * @author limmoor
   * @since 08.08.2018
   */
  @Test
  public void testSetDataCaseSimplePerson() {

    // @GIVEN
    final String personData = "Harry Potter";

    // @WHEN
    this.person.setData(personData);

    // @THEN
    assertEquals("Harry", this.person.getFirstName());
    assertEquals("Potter", this.person.getLastName());
    assertEquals("", this.person.getTitle());
  }

  /**
   * @author limmoor
   * @since 08.08.2018
   */
  @Test
  public void testSetDataCasePersonWithTitle() {

    // @GIVEN
    final String personData = "Dr. Frank Stein";

    // @WHEN

    this.person.setData(personData);

    // @THEN
    assertEquals("Frank", this.person.getFirstName());
    assertEquals("Stein", this.person.getLastName());
    assertEquals("Dr.", this.person.getTitle());

  }

  /**
   * @author limmoor
   * @since 08.08.2018
   */
  @Test
  public void testSetDataCasePersonWithLDAP() {

    // @GIVEN
    final String personData = "Larry Lachs (llachs)";

    // @WHEN
    this.person.setData(personData);

    // @THEN

    assertEquals("llachs", this.person.getLdapUser());

  }

  /**
   * @author limmoor
   * @since 09.08.2018
   */
  @Test
  public void testSetDataCasePersonWithAddition() {

    // @GIVEN
    final String personData = "Ed van Schleck (eschleck)";

    // @WHEN
    this.person.setData(personData);

    // @THEN
    assertEquals("Ed", this.person.getFirstName());
    assertEquals("van", this.person.getNameAddition());
    assertEquals("Schleck", this.person.getLastName());
  }

  /**
   * @author limmoor
   * @since 09.08.2018
   */
  @Test
  public void testSetDataCasePersonWithVon() {

    // @GIVEN
    final String personData = "Ed von Schleck (eschleck)";

    // @WHEN
    this.person.setData(personData);

    // @THEN
    assertEquals("von", this.person.getNameAddition());
  }

  /**
   * @author limmoor
   * @since 09.08.2018
   */
  @Test
  public void testSetDataCasePersonWithDe() {

    // @GIVEN
    final String personData = "Ed de Schleck (eschleck)";

    // @WHEN
    this.person.setData(personData);

    // @THEN
    assertEquals("de", this.person.getNameAddition());
  }

  /**
   * @author limmoor
   * @since 09.08.2018
   */
  @Test
  public void testSetDataCasePersonWithDoppleName() {

    // @GIVEN
    final String personData = "Hans Otto Müller";

    // @WHEN
    this.person.setData(personData);

    // @THEN
    assertEquals("Hans Otto", this.person.getFirstName());

  }

  /**
   * @author limmoor
   * @since 14.08.2018
   */
  @Test
  public void testSetDataCaseAllOptions() {

    // @GIVEN
    final String personData = "Dr. Hans Otto von Müller (hmüller)";

    // @WHEN
    this.person.setData(personData);

    // @THEN
    assertEquals("Dr.", this.person.getTitle());
    assertEquals("Hans Otto", this.person.getFirstName());
    assertEquals("Müller", this.person.getLastName());
    assertEquals("von", this.person.getNameAddition());
    assertEquals("hmüller", this.person.getLdapUser());
  }

  /**
   * @author limmoor
   * @since 14.08.2018
   */
  @Test
  public void testSetDataCaseEmptyString() {

    // @WHEN
    this.person.setData("");
    // @THEN
    assertEquals("", this.person.getTitle());
    assertEquals("", this.person.getFirstName());
    assertEquals("", this.person.getLastName());
    assertEquals("", this.person.getNameAddition());
    assertEquals("", this.person.getLdapUser());

  }

  /**
   * @author limmoor
   * @since 14.08.2018
   */
  @Test
  public void testSetDataCaseNull() {
    this.person.setData(null);
    assertEquals("", this.person.getTitle());
    assertEquals("", this.person.getFirstName());
    assertEquals("", this.person.getLastName());
    assertEquals("", this.person.getNameAddition());
    assertEquals("", this.person.getLdapUser());
  }

}
