package de.neusta.challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.rules.ExpectedException;

public class InputValidationTest {

  @Rule
  public ExpectedException expectedEx = ExpectedException.none();

  private InputValidation inputValidation;

  @BeforeEach
  public void setUp() throws Exception {
    this.inputValidation = new InputValidation();
  }

  @ParameterizedTest
  @ValueSource(strings = { "123", "12345", " " })
  public void testValidRoomNumberCaseInvalidRoomNumber(final String invalidRoomNumber) {

    final ValidationException exception = assertThrows(ValidationException.class,
        () -> this.inputValidation.validRoomNumber(invalidRoomNumber));

    assertEquals(ErrorMessage.INVALID_ROOM_NUMBER.asJSON(), exception.getMessage());
  }

  /**
   * @author limmoor
   * @since 17.08.2018
   */
  @Test
  public void testValidRoomNumber() {

    this.inputValidation.validRoomNumber("1111");
  }

}
