package de.neusta.challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RoomControllerTest {

  private RoomController roomController;

  @Mock
  private InputValidation inputValidationMock;

  @Mock
  private Storage storageMock;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    this.roomController = new RoomController(this.inputValidationMock, this.storageMock);
  }

  /**
   * @author limmoor
   * @since 09.08.2018
   */
  @Test
  public void testShowRoomInvalidRoom() {

    // @GIVEN
    final String invalidRoom = "1";
    BDDMockito.willThrow(new ValidationException(ErrorMessage.INVALID_ROOM_NUMBER.asJSON()))
        .given(this.inputValidationMock).validRoomNumber(invalidRoom);
    // @WHEN
    final ResponseEntity<?> responseEntity = this.roomController.showRoom(invalidRoom);

    // @THEN
    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    assertEquals(ErrorMessage.INVALID_ROOM_NUMBER.asJSON(), responseEntity.getBody());
  }

  /**
   * @author limmoor
   * @since 09.08.2018
   */
  @Test
  public void testShowRoomCaseRoomFromStorage() {

    // @GIVEN
    final String roomNumber = "1111";
    final Room room = new Room(roomNumber);
    BDDMockito.given(this.storageMock.getRoomByNumber(roomNumber)).willReturn(room);

    // @WHEN
    final ResponseEntity<?> responseEntity = this.roomController.showRoom(roomNumber);

    // @THEN
    assertEquals(room, responseEntity.getBody());
    BDDMockito.then(this.inputValidationMock).should().validRoomNumber(roomNumber);
    BDDMockito.then(this.storageMock).should().getRoomByNumber(roomNumber);

  }

  /**
   * @author limmoor
   * @since 13.08.2018
   */
  @Test
  public void testShowRoomCaseValidNumberNoSuitableRoom() {

    // @GIVEN
    final String searchNumber = "1111";

    final List<Room> list = new ArrayList<>();
    list.add(new Room("2222"));

    // @WHEN
    final ResponseEntity<?> responseEntity = this.roomController.showRoom(searchNumber);

    // @THEN
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertEquals(ErrorMessage.NO_ROOM_FOUND.asJSON(), responseEntity.getBody());
  }

  /**
   * @author limmoor
   * @since 13.08.2018
   */
  @Test
  public void testShowAllRooms() {

    // @GIVEN
    Room roomMock = new Room("1111");
    final List<Room> roomList = new ArrayList<>();
    roomList.add(roomMock);
    roomMock = new Room("2222");
    roomList.add(roomMock);
    BDDMockito.given(this.storageMock.getRooms()).willReturn(roomList);

    // @WHEN
    final ResponseEntity<?> responseEntity = this.roomController.showAllRooms();

    // @THEN
    assertEquals(roomList, responseEntity.getBody());
    BDDMockito.then(this.storageMock).should().getRooms();
  }

  /**
   * @author limmoor
   * @since 13.08.2018
   */
  @Test
  public void testShowAllRoomsWithImport() {

    // @WHEN
    final ResponseEntity<?> responseEntity = this.roomController.showAllRooms();

    // @THEN

    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertEquals(ErrorMessage.NO_ROOM_FOUND.asJSON(), responseEntity.getBody());
  }

}
