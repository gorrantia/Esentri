package ch.rhb.integra.mitarbeiter.mia.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType (propOrder={"mvaNr", "gehaltNeu"})
public class MVA {

  private long mvaNr;
  private long gehaltNeu;

  public long getMvaNr() {
    return mvaNr;
  }
  @XmlElement(name = "MVANr")
  public void setMvaNr(long mvaNr) {
    this.mvaNr = mvaNr;
  }
  public long getGehaltNeu() {
    return gehaltNeu;
  }
  @XmlElement(name = "GehaltNeu")
  public void setGehaltNeu(long gehaltNeu) {
    this.gehaltNeu = gehaltNeu;
  }
}
