package ch.rhb.integra.mitarbeiter.mia.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType (propOrder={"person"})
@XmlRootElement(name = "MIA")
public class MIA {

  private Person person;

  public Person getPerson() {
    return person;
  }
  @XmlElement(name = "Person")
  public void setPerson(Person person) {
    this.person = person;
  }
  
}
