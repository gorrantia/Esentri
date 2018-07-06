
package ch.rhb.integra.mitarbeiter.kaba.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für SoapResponse complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="SoapResponse">
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
 *                             &lt;element name="AddPersonResponse" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
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
@XmlType(name = "SoapResponse", namespace = "http://schemas.xmlsoap.org/soap/envelope/", propOrder = {
    "body"
})
@XmlRootElement(name="Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
public class SoapResponse {

  @XmlElement(name = "Body", namespace = "http://schemas.xmlsoap.org/soap/envelope/", required = true)
  protected SoapResponse.Body body;

  /**
   * Ruft den Wert der body-Eigenschaft ab.
   * 
   * @return
   *     possible object is
   *     {@link SoapResponse.Envelope.Body }
   *     
   */
  public SoapResponse.Body getBody() {
      return body;
  }

  /**
   * Legt den Wert der body-Eigenschaft fest.
   * 
   * @param value
   *     allowed object is
   *     {@link SoapResponse.Envelope.Body }
   *     
   */
  public void setBody(SoapResponse.Body value) {
      this.body = value;
  }


  /**
   * <p>Java-Klasse für anonymous complex type.
   * 
   * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
   * 
   * <pre>
   * &lt;complexType>
   *   &lt;complexContent>
   *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
   *       &lt;sequence>
   *         &lt;element name="AddPersonResponse" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
   *       &lt;/sequence>
   *     &lt;/restriction>
   *   &lt;/complexContent>
   * &lt;/complexType>
   * </pre>
   * 
   * 
   */
  
  @Override
  public String toString() {
    return "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" + 
        "   <s:Body>\n" + 
        getBody().getAddPersonResponse().toString()+
        "   </s:Body>\n" + 
        "</s:Envelope>";
  }
  
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name = "", propOrder = {"addPersonResponse"})
  public static class Body {

      @XmlElement(name = "AddPersonResponse", namespace = "http://kabawcf.ch", required = true)
      protected Object addPersonResponse;

      /**
       * Ruft den Wert der addPersonResponse-Eigenschaft ab.
       * 
       * @return
       *     possible object is
       *     {@link Object }
       *     
       */
      public Object getAddPersonResponse() {
          return addPersonResponse;
      }

      /**
       * Legt den Wert der addPersonResponse-Eigenschaft fest.
       * 
       * @param value
       *     allowed object is
       *     {@link Object }
       *     
       */
      public void setAddPersonResponse(Object value) {
          this.addPersonResponse = value;
      }

  }
}
