
package ch.rhb.integra.mitarbeiter.kaba.jaxb;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ch.rhb.integra.mitarbeiter.kaba.jaxb package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SoapMessage_QNAME = new QName("http://schemas.xmlsoap.org/soap/envelope/", "SoapMessage");
    private final static QName _Person_QNAME = new QName("http://kabawcf.ch", "Person");
    private final static QName _WcfFault_QNAME = new QName("http://kabawcf.ch", "WcfFault");
    private final static QName _SoapResponse_QNAME = new QName("http://schemas.xmlsoap.org/soap/envelope/", "SoapResponse");
    private final static QName _WcfFaultErrorNo_QNAME = new QName("http://kabawcf.ch", "ErrorNo");
    private final static QName _WcfFaultErrorMessage_QNAME = new QName("http://kabawcf.ch", "ErrorMessage");
    private final static QName _PersonVorname_QNAME = new QName("http://kabawcf.ch", "Vorname");
    private final static QName _PersonGeschaeftsbereich_QNAME = new QName("http://kabawcf.ch", "Geschaeftsbereich");
    private final static QName _PersonArbeitsort_QNAME = new QName("http://kabawcf.ch", "Arbeitsort");
    private final static QName _PersonHauptkostenstelleNummer_QNAME = new QName("http://kabawcf.ch", "HauptkostenstelleNummer");
    private final static QName _PersonOrganisation_QNAME = new QName("http://kabawcf.ch", "Organisation");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ch.rhb.integra.mitarbeiter.kaba.jaxb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SoapEnvelope }
     * 
     */
    public SoapEnvelope createSoapEnvelope() {
        return new SoapEnvelope();
    }

    /**
     * Create an instance of {@link SoapResponse }
     * 
     */
    public SoapResponse createSoapResponse() {
        return new SoapResponse();
    }

    /**
     * Create an instance of {@link SoapResponse.Envelope }
     * 
     */
    public SoapResponse createSoapResponseEnvelope() {
        return new SoapResponse();
    }

    /**
     * Create an instance of {@link AddPersonResponse }
     * 
     */
    public AddPersonResponse createAddPersonResponse() {
        return new AddPersonResponse();
    }

    /**
     * Create an instance of {@link Person }
     * 
     */
    public Person createPerson() {
        return new Person();
    }

    /**
     * Create an instance of {@link WcfFault }
     * 
     */
    public WcfFault createWcfFault() {
        return new WcfFault();
    }

    /**
     * Create an instance of {@link Persons }
     * 
     */
    public Persons createPersons() {
        return new Persons();
    }

     /**
     * Create an instance of {@link SoapResponse.Envelope.Body }
     * 
     */
    public SoapResponse.Body createSoapResponseEnvelopeBody() {
        return new SoapResponse.Body();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapEnvelope }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/soap/envelope/", name = "SoapMessage")
    public JAXBElement<SoapEnvelope> createSoapMessage(SoapEnvelope value) {
        return new JAXBElement<SoapEnvelope>(_SoapMessage_QNAME, SoapEnvelope.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Person }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://kabawcf.ch", name = "Person")
    public JAXBElement<Person> createPerson(Person value) {
        return new JAXBElement<Person>(_Person_QNAME, Person.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WcfFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://kabawcf.ch", name = "WcfFault")
    public JAXBElement<WcfFault> createWcfFault(WcfFault value) {
        return new JAXBElement<WcfFault>(_WcfFault_QNAME, WcfFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/soap/envelope/", name = "SoapResponse")
    public JAXBElement<SoapResponse> createSoapResponse(SoapResponse value) {
        return new JAXBElement<SoapResponse>(_SoapResponse_QNAME, SoapResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://kabawcf.ch", name = "ErrorNo", scope = WcfFault.class)
    public JAXBElement<String> createWcfFaultErrorNo(String value) {
        return new JAXBElement<String>(_WcfFaultErrorNo_QNAME, String.class, WcfFault.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://kabawcf.ch", name = "ErrorMessage", scope = WcfFault.class)
    public JAXBElement<String> createWcfFaultErrorMessage(String value) {
        return new JAXBElement<String>(_WcfFaultErrorMessage_QNAME, String.class, WcfFault.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://kabawcf.ch", name = "Vorname", scope = Person.class)
    public JAXBElement<String> createPersonVorname(String value) {
        return new JAXBElement<String>(_PersonVorname_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://kabawcf.ch", name = "Geschaeftsbereich", scope = Person.class)
    public JAXBElement<String> createPersonGeschaeftsbereich(String value) {
        return new JAXBElement<String>(_PersonGeschaeftsbereich_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://kabawcf.ch", name = "Arbeitsort", scope = Person.class)
    public JAXBElement<String> createPersonArbeitsort(String value) {
        return new JAXBElement<String>(_PersonArbeitsort_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://kabawcf.ch", name = "HauptkostenstelleNummer", scope = Person.class)
    public JAXBElement<String> createPersonHauptkostenstelleNummer(String value) {
        return new JAXBElement<String>(_PersonHauptkostenstelleNummer_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://kabawcf.ch", name = "Organisation", scope = Person.class)
    public JAXBElement<String> createPersonOrganisation(String value) {
        return new JAXBElement<String>(_PersonOrganisation_QNAME, String.class, Person.class, value);
    }

}
