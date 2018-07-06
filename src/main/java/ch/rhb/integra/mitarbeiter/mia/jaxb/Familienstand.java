package ch.rhb.integra.mitarbeiter.mia.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType (propOrder={"zivilstand", "zivilstandDatumVon"})
public class Familienstand {

  private Zivilstand zivilstand;
  
  private String zivilstandDatumVon;

  public Zivilstand getZivilstand() {
    return zivilstand;
  }
  @XmlElement(name = "Zivilstand")
  public void setZivilstand(Zivilstand zivilstand) {
    this.zivilstand = zivilstand;
  }

  public String getZivilstandDatumVon() {
    return zivilstandDatumVon;
  }
  @XmlElement(name = "ZivilstandDatumVon")
  public void setZivilstandDatumVon(String zivilstandDatumVon) {
    this.zivilstandDatumVon = zivilstandDatumVon;
  }
}
