package ch.rhb.integra.mitarbeiter.psipenta;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
/**
 * XML Type for PSI Penta Record
 * 
 * 
 * @author esentri AG, <a href="mailto:guillermo.orrantia@esentri.com">gorrantia</a>
 *
 */
@XmlType
public class PsipentaRecordEntry {

  private String name = "Property";
  private String value = "";
  private String attr = "";
  
  public PsipentaRecordEntry() {
  }
  
  public PsipentaRecordEntry(String attr) {
    this.attr = attr;
  }
  
  public PsipentaRecordEntry(String name,String value,String attribute) {
    this.name = name;
    this.attr = attribute;
    this.value = value;
  }
  
  @XmlTransient
  public String getName() {
      return name;
  }
  public void setName(String name) {
      this.name = name;
  }
  @XmlValue
  public String getValue() {
      return value;
  }
  public void setValue(String value) {
      this.value = value;
  }
  @XmlAttribute(name="attr")
  public String getAttr() {
      return attr;
  }
  public void setAttr(String attr) {
      this.attr = attr;
  }

  @Override
  public String toString() {
      return "<"+name + " attr=" + attr + ">"+value+"</Property>";
  }

}
