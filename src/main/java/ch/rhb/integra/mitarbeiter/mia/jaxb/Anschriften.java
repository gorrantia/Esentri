package ch.rhb.integra.mitarbeiter.mia.jaxb;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType (propOrder={"anschrift"})
public class Anschriften {
  
  private List<Anschrift> anschrift;

  public List<Anschrift> getAnschrift() {
    return anschrift;
  }
  @XmlElement(name = "Anschrift")
  public void setAnschrift(List<Anschrift> anschriften) {
    this.anschrift = anschriften;
  }

}
