
package ch.rhb.integra.mitarbeiter.psipenta.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für PropertyListWithOptionalLines complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="PropertyListWithOptionalLines">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.psipenta.de/WebServiceDataSchema}PropertyList">
 *       &lt;sequence>
 *         &lt;element name="Line" type="{http://www.psipenta.de/WebServiceDataSchema}PropertyList" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PropertyListWithOptionalLines", namespace = "http://www.psipenta.de/WebServiceDataSchema", propOrder = {
    "line"
})
public class PropertyListWithOptionalLines
    extends PropertyList
{

    @XmlElement(name = "Line")
    protected List<PropertyList> line;

    /**
     * Gets the value of the line property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the line property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLine().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PropertyList }
     * 
     * 
     */
    public List<PropertyList> getLine() {
        if (line == null) {
            line = new ArrayList<PropertyList>();
        }
        return this.line;
    }

}
