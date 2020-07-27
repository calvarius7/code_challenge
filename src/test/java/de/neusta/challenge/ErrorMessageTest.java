package de.neusta.challenge;

import org.assertj.core.api.BDDAssertions;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

public class ErrorMessageTest {

  /**
   * @author limmoor
   * @throws JsonProcessingException
   * @since 29.08.2018
   */
  @Test
  public void testAllErrorMessages() throws JsonProcessingException {

    for (final ErrorMessage message : ErrorMessage.values()) {
      BDDAssertions.then(message.asJSON()).contains("\"code\" : " + message.getCode());
    }
  }

}
