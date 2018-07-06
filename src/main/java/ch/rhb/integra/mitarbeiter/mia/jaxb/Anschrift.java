package ch.rhb.integra.mitarbeiter.mia.jaxb;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType (propOrder={"anschriftArt","isoLandCode","strasse","hausNummer","plzOrt","postfach","bundesland","telefon",
    "mobile","fax","email"})
public class Anschrift {

  private AnschriftArt anschriftArt;
  
  private String isoLandCode;
  
  private String strasse;
  
  private String hausNummer;
  
  private PLZOrt plzOrt;
  
  private String postfach;
  
  private String bundesland;
  
  private String telefon;
  
  private String mobile;
  
  private String fax;
  
  private String email;
  
 
  public AnschriftArt getAnschriftArt() {
    return anschriftArt;
  }
  @XmlElement(name = "AnschriftArt")
  public void setAnschriftArt(AnschriftArt anschriftArt) {
    this.anschriftArt = anschriftArt;
  }
  public String getIsoLandCode() {
    return isoLandCode;
  }
  @XmlElement(name = "ISOLandCode")
  public void setIsoLandCode(String isoLandCode) {
    this.isoLandCode = isoLandCode;
  }
  public String getStrasse() {
    return strasse;
  }
  @XmlElement(name = "Strasse")
  public void setStrasse(String strasse) {
    this.strasse = strasse;
  }
  public String getHausNummer() {
    return hausNummer;
  }
  @XmlElement(name = "HausNummer")
  public void setHausNummer(String hausNummer) {
    this.hausNummer = hausNummer;
  }
  public PLZOrt getPlzOrt() {
    return plzOrt;
  }
  @XmlElement(name = "PLZOrt")
  public void setPlzOrt(PLZOrt plzOrt) {
    this.plzOrt = plzOrt;
  }
  public String getPostfach() {
    return postfach;
  }
  @XmlElement(name = "Postfach")
  public void setPostfach(String postfach) {
    this.postfach = postfach;
  }
  public String getBundesland() {
    return bundesland;
  }
  @XmlElement(name = "Bundesland")
  public void setBundesland(String bundesland) {
    this.bundesland = bundesland;
  }
  public String getTelefon() {
    return telefon;
  }
  @XmlElement(name = "Telefon")
  public void setTelefon(String telefon) {
    this.telefon = telefon;
  }
  public String getMobile() {
    return mobile;
  }
  @XmlElement(name = "Mobile")
  public void setMobile(String mobile) {
    this.mobile = mobile;
  }
  public String getFax() {
    return fax;
  }
  @XmlElement(name = "Fax")
  public void setFax(String fax) {
    this.fax = fax;
  }
  public String getEmail() {
    return email;
  }
  @XmlElement(name = "EMail")
  public void setEmail(String email) {
    this.email = email;
  }
}
