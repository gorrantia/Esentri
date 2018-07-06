
package ch.rhb.integra.mitarbeiter.kaba.jaxb;

import ch.rhb.integra.common.XmlMapperUtils;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für Persons complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Persons">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Person" type="{http://kabawcf.ch}Person" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Persons", namespace = "http://kabawcf.ch", propOrder = {
    "person"
})
public class Persons {

    @XmlElement(name = "Person", namespace = "http://kabawcf.ch", required = true)
    protected List<Person> person = new ArrayList<Person>();
    
    public Persons() {
    }
    
    public Persons(List<Person> p) {
      this.person = p;
    }

    /**
     * Gets the value of the person property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the person property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPerson().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Person }
     * 
     * 
     */
    public List<Person> getPerson() {
        if (person == null) {
            person = new ArrayList<Person>();
        }
        return this.person;
    }
    
    public void setPerson(List<Person> p) {
      this.person = p;
    }
    
    @Override()
    public String toString() {
      return "";
    }
    
    private String personsToString() {
      StringBuilder builder = new StringBuilder();
      builder.append("<AddPerson>\n");
      person.forEach(x -> builder.append("<Person>"
          + "<Arberitsort>"+x.getArbeitsort()+"</Arbeitsort>\n"+
          printNull(XmlMapperUtils.convertXmlDate2String(x.getAustritt(), "yyyy-MM-dd"))+
          "<Eintritt>"+XmlMapperUtils.convertXmlDate2String(x.getEintritt(), "yyyy-MM-dd")+"</Eintritt>\n"+
          "<Geschaefsbereich>"+x.getGeschaeftsbereich()+"</Geschaefsbereich>\n"+
          "<Hauptkostenstelle>"+x.getHauptkostenstelleNummer()+"</Hauptkostenstelle>\n"+
          "<Nachname>"+x.getNachname()+"<Nachname>\n"+
          "<Funktion>"+x.getFunktion()+"</Funktion>\n"+
          "<Organistaion>"+x.getOrganisation()+"</Organisation>\n"+
          "<Personalnummer>"+x.getPersonalnummer()+"</Personalnummer>\n"+
          "<Vorname>"+x.getVorname()+"</Vorname>\n"+
          "</Person>\n"));
      builder.append("</AddPerson>\n");
      return builder.toString();
    }
    
    private String printNull(String s) {
      if (s.equals(null))
        return s;
      else
        return "";
    }

}
