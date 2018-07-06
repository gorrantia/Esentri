package ch.rhb.integra.mitarbeiter.mia.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType (propOrder={"maVersionId", "maVersionGueltigAb","name","vorname","personal","mva","anschriften","kostenstellen"})
public class MaVersion {

  private long maVersionId;
  
  private String maVersionGueltigAb;
  
  private String name;
  
  private String vorname;
  
  private Personal personal;
 
  private MVA mva;
  
  private Anschriften anschriften;
  
  private Kostenstellen kostenstellen;

  public long getMaVersionId() {
    return maVersionId;
  }
  @XmlElement(name = "MaVersionId")
  public void setMaVersionId(long maVersionId) {
    this.maVersionId = maVersionId;
  }
  public String getMaVersionGueltigAb() {
    return maVersionGueltigAb;
  }
  @XmlElement(name = "MaVersionGueltigAb")
  public void setMaVersionGueltigAb(String maVersionGueltigAb) {
    this.maVersionGueltigAb = maVersionGueltigAb;
  }
  public String getName() {
    return name;
  }
  @XmlElement(name = "Name")
  public void setName(String name) {
    this.name = name;
  }
  public String getVorname() {
    return vorname;
  }
  @XmlElement(name = "Vorname")
  public void setVorname(String vorname) {
    this.vorname = vorname;
  }
  public Personal getPersonal() {
    return personal;
  }
  @XmlElement(name = "Personal")
  public void setPersonal(Personal personal) {
    this.personal = personal;
  }
  public MVA getMva() {
    return mva;
  }
  @XmlElement(name = "MVA")
  public void setMva(MVA mva) {
    this.mva = mva;
  }
  public Anschriften getAnschriften() {
    return anschriften;
  }
  @XmlElement(name = "Anschriften")
  public void setAnschriften(Anschriften anschriften) {
    this.anschriften = anschriften;
  }
  public Kostenstellen getKostenstellen() {
    return kostenstellen;
  }
  @XmlElement(name = "Kostenstellen")
  public void setKostenstellen(Kostenstellen kostenstellen) {
    this.kostenstellen = kostenstellen;
  }
  
  
  
  
 }
