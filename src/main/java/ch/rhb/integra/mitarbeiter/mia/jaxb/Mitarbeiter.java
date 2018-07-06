package ch.rhb.integra.mitarbeiter.mia.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType (propOrder={"firmaNr", "personalNummer","eintritt","austritt","maVersion"})
public class Mitarbeiter {
  private long firmaNr;
  private long personalNummer;
  private String eintritt;
  private String austritt;
  private MaVersion maVersion;
  
  public long getFirmaNr() {
    return firmaNr;
  }
  @XmlElement(name = "FirmaNr")
  public void setFirmaNr(long firmaNr) {
    this.firmaNr = firmaNr;
  }
  public long getPersonalNummer() {
    return personalNummer;
  }
  @XmlElement(name = "PersonalNummer")
  public void setPersonalNummer(long personalNummer) {
    this.personalNummer = personalNummer;
  }
  public String getEintritt() {
    return eintritt;
  }
  @XmlElement(name = "Eintritt")
  public void setEintritt(String eintritt) {
    this.eintritt = eintritt;
  }
  public String getAustritt() {
    return austritt;
  }
  @XmlElement(name = "Austritt")
  public void setAustritt(String austritt) {
    this.austritt = austritt;
  }
  public MaVersion getMaVersion() {
    return maVersion;
  }
  @XmlElement(name = "MaVersion")
  public void setMaVersion(MaVersion maVersion) {
    this.maVersion = maVersion;
  }
  

}
