package de.neusta.challenge;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author limmoor
 * @since 08.08.2018
 */
public class Person {

  private String firstName = "";
  private String lastName = "";
  private String ldapUser = "";
  private String nameAddition = "";

  private List<String> data;

  final private String title = "Dr.";
  private boolean hasTitle = false;

  /**
   * @param personData
   * @author limmoor
   * @since 08.08.2018
   */
  public void setData(final String personData) {
    if (personData != null && !(personData.isEmpty())) {
      this.data = new LinkedList<>(Arrays.asList(personData.split(" ")));

      this.setTitle();

      this.setLdapUser();

      this.setNames();
    }

  }

  /**
   * @author limmoor
   * @since 14.08.2018
   */
  private void setTitle() {
    if (this.data.get(0).equals(this.title)) {
      this.data.remove(0);
      this.hasTitle = true;
    }
  }

  /**
   * Set first und last name and check for name extras like secon first name or
   * name additions
   *
   * @author limmoor
   * @since 08.08.2018
   */
  private void setNames() {
    this.firstName = this.data.remove(0);
    this.lastName = this.data.remove(this.data.size() - 1);

    while (!this.data.isEmpty()) {
      this.setNameAdditions(this.data.remove(0));
    }

  }

  /**
   * Check for name addtion like van, von, de or second first name
   *
   * @author limmoor
   * @param potentialNameAddition
   * @since 09.08.2018
   */
  private void setNameAdditions(final String potentialNameAddition) {
    final String[] nameAdditionOptions = { "van", "von", "de" };
    if (Arrays.asList(nameAdditionOptions).contains(potentialNameAddition)) {
      this.nameAddition = potentialNameAddition;
    } else {
      this.firstName += " " + potentialNameAddition;
    }
  }

  /**
   * Last entry of name data is the ldap-username in brackets brackets are not
   * shown
   *
   * @author limmoor
   * @since 14.08.2018
   */
  private void setLdapUser() {
    final String ldap = this.data.get(this.data.size() - 1);
    if (ldap.matches("[(].+[)]")) {
      this.ldapUser = ldap.replaceAll("[()]", "");
      this.data.remove(this.data.size() - 1);
    } else {
      this.ldapUser = "";
    }
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  /**
   * @return title if person has one
   * @author limmoor
   * @since 08.08.2018
   */
  public String getTitle() {
    if (this.hasTitle) {
      return this.title;
    }
    return "";
  }

  public String getLdapUser() {
    return this.ldapUser;
  }

  public String getNameAddition() {
    return this.nameAddition;
  }

  /**
   * {@inheritDoc}
   *
   * @author limmoor
   * @since 15.08.2018
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.data == null) ? 0 : this.data.hashCode());
    result = prime * result + ((this.firstName == null) ? 0 : this.firstName.hashCode());
    result = prime * result + (this.hasTitle ? 1231 : 1237);
    result = prime * result + ((this.lastName == null) ? 0 : this.lastName.hashCode());
    result = prime * result + ((this.ldapUser == null) ? 0 : this.ldapUser.hashCode());
    result = prime * result + ((this.nameAddition == null) ? 0 : this.nameAddition.hashCode());
    result = prime * result + ((this.title == null) ? 0 : this.title.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   *
   * @author limmoor
   * @since 15.08.2018
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
    final Person other = (Person) obj;

    if (this.firstName == null) {
      if (other.firstName != null) {
        return false;
      }
    } else if (!this.firstName.equals(other.firstName)) {
      return false;
    }
    if (this.hasTitle != other.hasTitle) {
      return false;
    }
    if (this.lastName == null) {
      if (other.lastName != null) {
        return false;
      }
    } else if (!this.lastName.equals(other.lastName)) {
      return false;
    }
    if (this.ldapUser == null) {
      if (other.ldapUser != null) {
        return false;
      }
    } else if (!this.ldapUser.equals(other.ldapUser)) {
      return false;
    }
    if (this.nameAddition == null) {
      if (other.nameAddition != null) {
        return false;
      }
    } else if (!this.nameAddition.equals(other.nameAddition)) {
      return false;
    }
    if (this.title == null) {
      if (other.title != null) {
        return false;
      }
    } else if (!this.title.equals(other.title)) {
      return false;
    }
    return true;
  }

}
