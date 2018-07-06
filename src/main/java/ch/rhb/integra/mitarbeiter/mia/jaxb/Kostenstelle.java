package ch.rhb.integra.mitarbeiter.mia.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType (propOrder={"kstMitarbeiterId","kostenstelle","verrechnungProzent"})
public class Kostenstelle {

  private long kstMitarbeiterId;
  private long kostenstelle;
  private long verrechnungProzent;
  
  public long getKstMitarbeiterId() {
    return kstMitarbeiterId;
  }
  @XmlElement(name = "KstMitarbeiterId")
  public void setKstMitarbeiterId(long kstMitarbeiterId) {
    this.kstMitarbeiterId = kstMitarbeiterId;
  }
  public long getKostenstelle() {
    return kostenstelle;
  }
  @XmlElement(name = "Kostenstelle")
  public void setKostenstelle(long kostenstelle) {
    this.kostenstelle = kostenstelle;
  }
  public long getVerrechnungProzent() {
    return verrechnungProzent;
  }
  @XmlElement(name = "VerrechnungProzent")
  public void setVerrechnungProzent(long verrechnungProzent) {
    this.verrechnungProzent = verrechnungProzent;
  }

  
  
}
