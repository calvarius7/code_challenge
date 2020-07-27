package de.neusta.challenge;

import org.springframework.stereotype.Component;

/**
 * @author limmoor
 * @since 09.08.2018
 */
@Component
public class InputValidation {

  /**
   * @param roomNumber
   * @author limmoor
   * @since 09.08.2018
   */
  public void validRoomNumber(final String roomNumber) {
    if (!(roomNumber.length() == 4)) {

      throw new ValidationException(ErrorMessage.INVALID_ROOM_NUMBER.asJSON());

    }
  }

}
