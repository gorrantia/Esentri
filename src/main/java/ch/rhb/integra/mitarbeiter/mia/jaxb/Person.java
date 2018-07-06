package ch.rhb.integra.mitarbeiter.mia.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType (propOrder={"personID", "geburtsdatum","mitarbeiter"})
public class Person {

  private long personID;
  private String geburtsdatum;
  private Mitarbeiter mitarbeiter;
  
  public long getPersonID() {
    return personID;
  }
  @XmlElement(name = "PersonId")
  public void setPersonID(long personID) {
    this.personID = personID;
  }
  public String getGeburtsdatum() {
    return geburtsdatum;
  }
  @XmlElement(name = "Geburtsdatum")
  public void setGeburtsdatum(String geburtsdatum) {
    this.geburtsdatum = geburtsdatum;
  }
  public Mitarbeiter getMitarbeiter() {
    return mitarbeiter;
  }
  @XmlElement(name = "Mitarbeiter")
  public void setMitarbeiter(Mitarbeiter mitarbeiter) {
    this.mitarbeiter = mitarbeiter;
  }
  

}
