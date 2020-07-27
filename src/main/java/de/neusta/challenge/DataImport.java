package de.neusta.challenge;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author limmoor
 * @since 08.08.2018
 */
@Component
public class DataImport {

  private final DataValidator dataValidator = new DataValidator();

  /**
   * @param csvLines
   * @return List of all rooms in the csv
   * @author limmoor
   * @since 08.08.2018
   */
  public List<Room> importRooms(final List<String> csvLines) {
    final List<Room> roomList = new ArrayList<>();
    if (csvLines != null) {
      for (final String room : csvLines) {
        final String[] roomData = room.split(",");
        this.buildARoom(roomList, roomData);
      }
    }
    return roomList;
  }

  /**
   * @param rooms
   * @author limmoor
   * @param roomData
   * @since 08.08.2018
   */
  private void buildARoom(final List<Room> rooms, final String[] roomData) {
    if (this.dataValidator.roomNumberValidation(roomData[0])) {
      final Room room = new Room(roomData[0]);
      this.populateRoom(roomData, room);
      rooms.add(room);
    }
  }

  /**
   * @param roomData
   * @param room
   * @author limmoor
   * @since 08.08.2018
   */
  private void populateRoom(final String[] roomData, final Room room) {
    for (int i = 1; i < roomData.length; i++) {
      final String personData = roomData[i].trim();
      if (personData.length() > 0) {
        final Person person = new Person();
        person.setData(personData);
        room.addPerson(person);
      }
    }
  }

}
