package de.neusta.challenge;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StorageTest {

  private Storage storage;

  @Before
  public void setUp() throws Exception {
    this.storage = new Storage();
  }

  /**
   * @author limmoor
   * @since 09.08.2018
   */
  @Test
  public void testSaveRooms() {

    // @GIVEN
    final List<Room> rooms = new ArrayList<>();
    // @WHEN
    this.storage.saveRooms(rooms);

    // @THEN
    assertEquals(rooms, this.storage.getRooms());

  }

  /**
   * @author limmoor
   * @since 09.08.2018
   */
  @Test
  public void testSaveRoomsCaseSecondRoom() {

    // @GIVEN
    final List<Room> rooms = new ArrayList<>();
    final List<Room> rooms2 = new ArrayList<>();

    // @WHEN
    this.storage.saveRooms(rooms);
    this.storage.saveRooms(rooms2);

    // @THEN

    assertEquals(rooms2, this.storage.getRooms());

  }

  /**
   * @author limmoor
   * @since 09.08.2018
   */
  @Test
  public void testGetRoomByNumber() {

    // @GIVEN
    final String roomNumber = "1234";
    final Room room = this.givenRoomList(roomNumber);

    // @WHEN
    final Room resultRoom = this.storage.getRoomByNumber(roomNumber);
    // @THEN
    assertEquals(room, resultRoom);
  }

  private Room givenRoomList(final String roomNumber) {
    final Room room = new Room(roomNumber);
    final List<Room> roomList = new ArrayList<>();
    roomList.add(room);
    this.storage.saveRooms(roomList);
    return room;
  }

  /**
   * @author limmoor
   * @since 09.08.2018
   */
  @Test
  public void testGetRoomByNumberInvalidNumber() {

    // @WHEN
    final Room roomByNumber = this.storage.getRoomByNumber("4321");

    // @THEN
    assertNull(roomByNumber);

  }

  /**
   * @author limmoor
   * @since 09.08.2018
   */
  @Test
  public void testGetRoomByNumberWhithoutSave() {

    // @GIVEN
    final String roomNumber = "1234";

    // @WHEN
    final Room room = this.storage.getRoomByNumber(roomNumber);

    // @THEN
    assertNull(room);

  }

}
