package de.neusta.challenge;

/**
 * @author limmoor
 * @since 09.08.2018
 */
@SuppressWarnings("serial")
public class ValidationException extends RuntimeException {

  /**
   * @param message
   */
  public ValidationException(final String message) {
    super(message);
  }
}
