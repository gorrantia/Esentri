package ch.rhb.integra.mitarbeiter.mia.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType (propOrder={"kurzText", "text"})
public class AnschriftArt {

  private String kurzText;
  private String text;
  
  public AnschriftArt() {
    super();
  }

  public AnschriftArt(String k, String t) {
    kurzText=k;
    text=t;
  }

  public String getKurzText() {
    return kurzText;
  }
  @XmlElement(name = "KurzText")
  public void setKurzText(String kurzText) {
    this.kurzText = kurzText;
  }
  public String getText() {
    return text;
  }
  @XmlElement(name = "Text")
  public void setText(String text) {
    this.text = text;
  }
  
}
