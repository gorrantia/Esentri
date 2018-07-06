package ch.rhb.integra.mitarbeiter.mia.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
@XmlType (propOrder={"sozialVersicherungsNummer", "geschlecht","heimatOrt","familienstand","vertragsart","austtritsgrund",
    "kaderStruktur","funktionsgruppe","beschaeftigungsGrad","beruf","externeBerufsbezeichnung","staatsangehoerigkeit"})
public class Personal {

  private String sozialVersicherungsNummer;
  
  private Geschlecht geschlecht;
  
  private String heimatOrt;
  
  private Familienstand familienstand;
  
  private Vertragsart vertragsart;
  
  private AustrittsGrund austtritsgrund;
  
  private KaderStruktur kaderStruktur;
  
  private Funktionsgruppe funktionsgruppe;
  
  private long beschaeftigungsGrad;
  
  private String beruf;
  
  private String externeBerufsbezeichnung;
  
  private String staatsangehoerigkeit;

  public String getSozialVersicherungsNummer() {
    return sozialVersicherungsNummer;
  }
  @XmlElement(name = "SozialVersicherungsNummer")
  public void setSozialVersicherungsNummer(String sozialVersicherungsNummer) {
    this.sozialVersicherungsNummer = sozialVersicherungsNummer;
  }
  public Geschlecht getGeschlecht() {
    return geschlecht;
  }
  @XmlElement(name = "Geschlecht")
  public void setGeschlecht(Geschlecht geschlecht) {
    this.geschlecht = geschlecht;
  }
  public String getHeimatOrt() {
    return heimatOrt;
  }
  @XmlElement(name = "HeimatOrt")
  public void setHeimatOrt(String heimatOrt) {
    this.heimatOrt = heimatOrt;
  }
  public Familienstand getFamilienstand() {
    return familienstand;
  }
  @XmlElement(name = "Familienstand")
  public void setFamilienstand(Familienstand familienstand) {
    this.familienstand = familienstand;
  }
  public Vertragsart getVertragsart() {
    return vertragsart;
  }
  @XmlElement(name = "Vertragsart")
  public void setVertragsart(Vertragsart vertragsart) {
    this.vertragsart = vertragsart;
  }
  public AustrittsGrund getAusttritsgrund() {
    return austtritsgrund;
  }
  @XmlElement(name = "AustrittsGrund")
  public void setAusttritsgrund(AustrittsGrund austtritsgrund) {
    this.austtritsgrund = austtritsgrund;
  }
  public KaderStruktur getKaderStruktur() {
    return kaderStruktur;
  }
  @XmlElement(name = "KaderStruktur")
  public void setKaderStruktur(KaderStruktur kaderStruktur) {
    this.kaderStruktur = kaderStruktur;
  }
  public Funktionsgruppe getFunktionsgruppe() {
    return funktionsgruppe;
  }
  @XmlElement(name = "Funktionsgruppe")
  public void setFunktionsgruppe(Funktionsgruppe funktionsgruppe) {
    this.funktionsgruppe = funktionsgruppe;
  }
  public long getBeschaeftigungsGrad() {
    return beschaeftigungsGrad;
  }
  @XmlElement(name = "BeschaeftigungsGrad")
  public void setBeschaeftigungsGrad(long beschaeftigungsGrad) {
    this.beschaeftigungsGrad = beschaeftigungsGrad;
  }
  public String getBeruf() {
    return beruf;
  }
  @XmlElement(name = "Beruf")
  public void setBeruf(String beruf) {
    this.beruf = beruf;
  }
  public String getExterneBerufsbezeichnung() {
    return externeBerufsbezeichnung;
  }
  @XmlElement(name = "ExterneBerufsbezeichnung")
  public void setExterneBerufsbezeichnung(String externeBerufsbezeichnung) {
    this.externeBerufsbezeichnung = externeBerufsbezeichnung;
  }
  public String getStaatsangehoerigkeit() {
    return staatsangehoerigkeit;
  }
  @XmlElement(name = "Staatsangehoerigkeit")
  public void setStaatsangehoerigkeit(String staatsangehoerigkeit) {
    this.staatsangehoerigkeit = staatsangehoerigkeit;
  }
  
  
}
