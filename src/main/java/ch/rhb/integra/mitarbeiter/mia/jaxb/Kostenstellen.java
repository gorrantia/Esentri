package ch.rhb.integra.mitarbeiter.mia.jaxb;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
@XmlType (propOrder={"kostenstelle"})
public class Kostenstellen {

  private List<Kostenstelle> kostenstelle;

  public List<Kostenstelle> getKostenstelle() {
    return kostenstelle;
  }
  @XmlElement(name = "Kostenstelle")
  public void setKostenstelle(List<Kostenstelle> kostenstelle) {
    this.kostenstelle = kostenstelle;
  }
  
  
}
