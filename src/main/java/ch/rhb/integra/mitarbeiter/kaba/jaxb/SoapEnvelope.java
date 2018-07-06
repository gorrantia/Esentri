
package ch.rhb.integra.mitarbeiter.kaba.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für SoapEnvelope complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="SoapEnvelope">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Envelope">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Body">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="AddPerson" type="{http://kabawcf.ch}Persons"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SoapEnvelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/", propOrder = {"body"})
@XmlRootElement(name="Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
public class SoapEnvelope {

  @XmlElement(name = "Body", namespace = "http://schemas.xmlsoap.org/soap/envelope/", required = true)
  protected SoapEnvelope.Body body = new Body();

  /**
   * Ruft den Wert der body-Eigenschaft ab.
   * 
   * @return
   *     possible object is
   *     {@link SoapEnvelope.Envelope.Body }
   *     
   */
  public SoapEnvelope.Body getBody() {
      return body;
  }

  /**
   * Legt den Wert der body-Eigenschaft fest.
   * 
   * @param value
   *     allowed object is
   *     {@link SoapEnvelope.Envelope.Body }
   *     
   */
  public void setBody(SoapEnvelope.Body value) {
      this.body = value;
  }
  
  @Override
  public String toString() {
    return "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"+
        "<s:Body>\n"+
          getBody().getAddPerson().toString()+
        " </s:Body>\n"+
        "</<s:Envelope>";
  }

  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name = "", propOrder = {"addPerson"})
  public static class Body {
    
    @XmlElement(name = "AddPerson", namespace = "http://kabawcf.ch", required = true)
    protected Persons addPerson = new Persons();
    
    public Body() {
    }

    /**
     * Ruft den Wert der addPerson-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Persons }
     *     
     */
    public Persons getAddPerson() {
        return addPerson;
    }

    /**
     * Legt den Wert der addPerson-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Persons }
     *     
     */
    public void setAddPerson(Persons value) {
        this.addPerson = value;
    }
  }
}
