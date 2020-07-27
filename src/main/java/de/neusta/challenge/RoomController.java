package de.neusta.challenge;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

/**
 * @author limmoor
 * @since 09.08.2018
 */
@RestController
public class RoomController {

  private final InputValidation inputValidation;

  private final Storage storage;

  /**
   * @param inputValidation
   * @param storage
   */
  public RoomController(final InputValidation inputValidation, final Storage storage) {
    this.inputValidation = inputValidation;
    this.storage = storage;
  }

  /**
   * @param roomNumber
   * @author limmoor
   * @return Room
   * @since 09.08.2018
   */
  @ApiOperation("export room")
  @GetMapping("/api/room/{roomNumber}")
  public ResponseEntity<?> showRoom(@PathVariable(value = "roomNumber") final String roomNumber) {
    try {
      this.inputValidation.validRoomNumber(roomNumber);
      final Room room = this.storage.getRoomByNumber(roomNumber);
      if (room != null) {
        return ResponseEntity.ok(room);
      }
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessage.NO_ROOM_FOUND.asJSON());
    } catch (final ValidationException cause) {
      return ResponseEntity.badRequest().body(cause.getMessage());
    }
  }

  /**
   * @author limmoor
   * @return Room
   * @since 09.08.2018
   */
  @ApiOperation("export room")
  @GetMapping("/api/room/")
  public ResponseEntity<?> showAllRooms() {
    try {
      final List<Room> roomList = this.storage.getRooms();
      if (roomList.isEmpty()) {
        throw new ValidationException(ErrorMessage.NO_ROOM_FOUND.asJSON());
      }
      return ResponseEntity.ok(roomList);

    } catch (final ValidationException cause) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cause.getMessage());
    }

  }

}
