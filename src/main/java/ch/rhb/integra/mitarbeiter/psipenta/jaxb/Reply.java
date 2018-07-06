
package ch.rhb.integra.mitarbeiter.psipenta.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für reply complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="reply">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Record" type="{http://www.psipenta.de/WebServiceDataSchema}PropertyList"/>
 *         &lt;element name="Result" type="{http://www.psipenta.de/WebServiceDataSchema}Result"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reply", namespace = "http://www.psipenta.de/WebServiceDataSchema", propOrder = {
    "record",
    "result"
})
@XmlRootElement(name="Reply")
public class Reply {

    @XmlElement(name = "Record", required = true)
    protected PropertyList record = new PropertyList();
    @XmlElement(name = "Result", required = true)
    protected Result result = new Result();

    /**
     * Ruft den Wert der record-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link PropertyList }
     *     
     */
    public PropertyList getRecord() {
        return record;
    }

    /**
     * Legt den Wert der record-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link PropertyList }
     *     
     */
    public void setRecord(PropertyList value) {
        this.record = value;
    }

    /**
     * Ruft den Wert der result-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Result }
     *     
     */
    public Result getResult() {
        return result;
    }

    /**
     * Legt den Wert der result-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Result }
     *     
     */
    public void setResult(Result value) {
        this.result = value;
    }
    
    @Override
    public String toString() {
      return "<p:Reply xmlns:p=\"http://www.psipenta.de/WebServiceDataSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.psipenta.de/WebServiceDataSchema psipenta.xsd \">\n" + 
          "  <Record p:key=\""+getRecord().getKey()+"\">\n" + 
          propToString()+ 
          "  </Record>\n" + 
          "  <Result p:type=\""+getResult().getType()+"\"/>\n" + 
          "</p:Reply>";
    }
    
    private String propToString() {
      StringBuilder builder = new StringBuilder();
      getRecord().getProperty().forEach(x -> builder.append("<Property p:name=\""+x.getName()+"\">"+x.getValue()+"</Property>\n"));
      return builder.toString();
    }

}
