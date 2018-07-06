package ch.rhb.integra.mitarbeiter.mia.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType (propOrder={"code", "text"})
public class Vertragsart {
  private long code;
  private String text;
 
  public Vertragsart() {
    super();
  }

  public Vertragsart(long c,String t) {
    code=c;
    text=t;
  }
  public long getCode() {
    return code;
  }
  @XmlElement(name = "Code")
  public void setCode(long code) {
    this.code = code;
  }
  public String getText() {
    return text;
  }
  @XmlElement(name = "Text")
  public void setText(String text) {
    this.text = text;
  }
}
