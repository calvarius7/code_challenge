package de.neusta.challenge;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author limmoor
 * @since 08.08.2018
 */
public class Room {

  private final String roomNumber;
  private final List<Person> people;

  /**
   * @param roomNumber
   */
  public Room(@JsonProperty("roomNumber") final String roomNumber) {
    this.roomNumber = roomNumber;
    this.people = new ArrayList<>();
  }

  /**
   * @return room number
   * @author limmoor
   * @since 08.08.2018
   */
  public String getRoomNumber() {

    return this.roomNumber;
  }

  public void addPerson(final Person person) {
    this.people.add(person);
  }

  /**
   * @return List of all people in a room
   * @author limmoor
   * @since 08.08.2018
   */
  public List<Person> getPeople() {
    return this.people;
  }

  /**
   * {@inheritDoc}
   *
   * @author limmoor
   * @since 14.08.2018
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.roomNumber == null) ? 0 : this.roomNumber.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   *
   * @author limmoor
   * @since 14.08.2018
   */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    final Room other = (Room) obj;
    if (this.roomNumber == null) {
      if (other.roomNumber != null) {
        return false;
      }
    } else if (!this.roomNumber.equals(other.roomNumber)) {
      return false;
    }
    return true;
  }

}
