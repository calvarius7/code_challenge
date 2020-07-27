package de.neusta.challenge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

/**
 * @author limmoor
 * @since 07.08.2018
 */
@Component
public class DataValidator {

  // RegEx: start with 4-chars roomNumber, then a "," then 0 -> n people,
  // seperated with ","
  // "," can be the end, or line.seperator
  final private String csvPattern = "([a-zA-Z0-9]{4}[,]([a-zA-Z äüöÄÖÜß.()]*[,])*([a-zA-Z äüöÄÖÜß.()]*[,]*)["
      + System.getProperty("line.separator") + "]*)";

  /**
   * Check if a string is a valid csv-line with correct structure
   *
   * @param lines
   * @return true if valid csv
   * @author limmoor
   * @since 07.08.2018
   */
  public boolean validateCsvLine(final List<String> lines) {
    boolean validCSV = true;
    if (lines != null && lines.size() > 0) {
      for (final String line : lines) {
        if (!(line != null && line.length() > 0 && line.matches(this.csvPattern))) {
          validCSV = false;
        }
      }
    } else {
      validCSV = false;
    }
    return validCSV;
  }

  /**
   * @param roomNumber
   * @return true if correct roomNumber
   * @author limmoor
   * @since 08.08.2018
   */
  public boolean roomNumberValidation(final String roomNumber) {
    return (roomNumber != null && roomNumber.length() == 4);
  }

  /**
   * @param roomList
   * @return true rooms have duplicates
   * @author limmoor
   * @since 14.08.2018
   */
  public boolean hasRoomDuplicates(final List<Room> roomList) {
    final Set<Room> distinct = new HashSet<>(roomList);
    return (roomList.size() > distinct.size());
  }

  /**
   * @param value
   * @return true if a persons have duplicates
   * @author limmoor
   * @since 15.08.2018
   */
  public boolean hasPeopleDuplicates(final List<Room> value) {
    final Set<Person> distinct = new HashSet<>();
    final List<Person> all = new ArrayList<>();

    for (final Room room : value) {
      for (final Person person : room.getPeople()) {
        distinct.add(person);
        all.add(person);
      }
    }

    return (all.size() > distinct.size());
  }
}
