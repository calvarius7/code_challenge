package de.neusta.challenge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * @author limmoor
 * @since 09.08.2018
 */
@Component
public class Storage {

  private final Map<String, Room> roomMap = new HashMap<>();

  /**
   * @param roomList
   * @author limmoor
   * @since 09.08.2018
   */
  public void saveRooms(final List<Room> roomList) {
    this.roomMap.clear();
    for (final Room room : roomList) {
      this.roomMap.put(room.getRoomNumber(), room);
    }
  }

  public List<Room> getRooms() {
    return new ArrayList<>(this.roomMap.values());
  }

  /**
   * @param searchRoomNumber
   * @return Room by roomNumber
   * @author limmoor
   * @since 09.08.2018
   */
  public Room getRoomByNumber(final String searchRoomNumber) {
    return this.roomMap.get(searchRoomNumber);
  }

}
