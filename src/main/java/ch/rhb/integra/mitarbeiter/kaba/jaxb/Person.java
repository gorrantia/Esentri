
package ch.rhb.integra.mitarbeiter.kaba.jaxb;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java-Klasse für Person complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Person">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Arbeitsort" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Austritt" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Eintritt" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Funktion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Geschaeftsbereich" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HauptkostenstelleNummer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Nachname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Organisation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Personalnummer" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Vorname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Person", namespace = "http://kabawcf.ch", propOrder = {
    "arbeitsort",
    "austritt",
    "eintritt",
    "funktion",
    "geschaeftsbereich",
    "hauptkostenstelleNummer",
    "nachname",
    "organisation",
    "personalnummer",
    "vorname"
})
public class Person {

    @XmlElementRef(name = "Arbeitsort", namespace = "http://kabawcf.ch", type = JAXBElement.class, required = false)
    protected String arbeitsort;
    @XmlElement(name = "Austritt", namespace = "http://kabawcf.ch")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar austritt;
    @XmlElement(name = "Eintritt", namespace = "http://kabawcf.ch")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar eintritt;
    @XmlElement(name = "Funktion", namespace = "http://kabawcf.ch", required = true, nillable = true)
    protected long funktion;
    @XmlElementRef(name = "Geschaeftsbereich", namespace = "http://kabawcf.ch", type = JAXBElement.class, required = false)
    protected String geschaeftsbereich;
    @XmlElementRef(name = "HauptkostenstelleNummer", namespace = "http://kabawcf.ch", type = JAXBElement.class, required = false)
    protected String hauptkostenstelleNummer;
    @XmlElement(name = "Nachname", namespace = "http://kabawcf.ch", required = true, nillable = true)
    protected String nachname;
    @XmlElementRef(name = "Organisation", namespace = "http://kabawcf.ch", type = JAXBElement.class, required = false)
    protected String organisation;
    @XmlElement(name = "Personalnummer", namespace = "http://kabawcf.ch", required = true, nillable = true)
    protected String personalnummer;
    @XmlElementRef(name = "Vorname", namespace = "http://kabawcf.ch", type = JAXBElement.class, required = false)
    protected String vorname;

    /**
     * Ruft den Wert der arbeitsort-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getArbeitsort() {
        return arbeitsort;
    }

    /**
     * Legt den Wert der arbeitsort-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setArbeitsort(String value) {
        this.arbeitsort = value;
    }

    /**
     * Ruft den Wert der austritt-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAustritt() {
        return austritt;
    }

    /**
     * Legt den Wert der austritt-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAustritt(XMLGregorianCalendar value) {
        this.austritt = value;
    }

    /**
     * Ruft den Wert der eintritt-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEintritt() {
        return eintritt;
    }

    /**
     * Legt den Wert der eintritt-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEintritt(XMLGregorianCalendar value) {
        this.eintritt = value;
    }

    /**
     * Ruft den Wert der funktion-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public long getFunktion() {
        return funktion;
    }

    /**
     * Legt den Wert der funktion-Eigenschaft fest.
     * 
     * @param l
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFunktion(long l) {
        this.funktion = l;
    }

    /**
     * Ruft den Wert der geschaeftsbereich-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getGeschaeftsbereich() {
        return geschaeftsbereich;
    }

    /**
     * Legt den Wert der geschaeftsbereich-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setGeschaeftsbereich(String value) {
        this.geschaeftsbereich = value;
    }

    /**
     * Ruft den Wert der hauptkostenstelleNummer-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getHauptkostenstelleNummer() {
        return hauptkostenstelleNummer;
    }

    /**
     * Legt den Wert der hauptkostenstelleNummer-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setHauptkostenstelleNummer(String value) {
        this.hauptkostenstelleNummer = value;
    }

    /**
     * Ruft den Wert der nachname-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNachname() {
        return nachname;
    }

    /**
     * Legt den Wert der nachname-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNachname(String value) {
        this.nachname = value;
    }

    /**
     * Ruft den Wert der organisation-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getOrganisation() {
        return organisation;
    }

    /**
     * Legt den Wert der organisation-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOrganisation(String value) {
        this.organisation = value;
    }

    /**
     * Ruft den Wert der personalnummer-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonalnummer() {
        return personalnummer;
    }

    /**
     * Legt den Wert der personalnummer-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonalnummer(String value) {
        this.personalnummer = value;
    }

    /**
     * Ruft den Wert der vorname-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getVorname() {
        return vorname;
    }

    /**
     * Legt den Wert der vorname-Eigenschaft fest.
     * 
     * @param string
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setVorname(String string) {
        this.vorname = string;
    }

}
