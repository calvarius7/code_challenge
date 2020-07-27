package de.neusta.challenge;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * @author limmoor
 * @since 29.08.2018
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorMessage {
  //@formatter:off
  ROOM_DUPLICATE(2, "Räume dürfen nur einmalig in der Importdatei vorkommen,"),
  PERSON_DUPLICATE(3,"Bewohner dürfen nur einmalig in der Importdatei vorkommen"),
  INVALID_CSV(4,"Ungültiges CSV"),
  NO_ROOM_FOUND(5,"Raum mit angegebener Nummer nicht vorhanden"),
  INVALID_ROOM_NUMBER(6,"Raumnummer nicht vierstellig"),
  CONVERT_ERROR(7, "Convert error");
  //@formatter:on

  /**
   * @param code
   * @param message
   */
  private ErrorMessage(final int code, final String message) {
    this.code = code;
    this.message = message;
  }

  private int code;
  private String message;

  public int getCode() {
    return this.code;
  }

  public String getMessage() {
    return this.message;
  }

  public String asJSON() {
    final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    String json = "";
    try {
      json = ow.writeValueAsString(this);
    } catch (final JsonProcessingException e) {
      // TODO maybe log?
    }
    return json;
  }
}
