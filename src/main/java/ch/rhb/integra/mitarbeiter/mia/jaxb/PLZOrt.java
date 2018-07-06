package ch.rhb.integra.mitarbeiter.mia.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType (propOrder={"plz","ort"})
public class PLZOrt {

  private long plz;
  
  private String ort;
  
  public PLZOrt() {
    super();
  }

  public PLZOrt(long p, String o) {
    plz=p;
    ort=o;
  }

  public long getPlz() {
    return plz;
  }
  @XmlElement(name = "PLZ")
  public void setPlz(long plz) {
    this.plz = plz;
  }

  public String getOrt() {
    return ort;
  }
  @XmlElement(name = "Ort")
  public void setOrt(String ort) {
    this.ort = ort;
  }
  
  
}
