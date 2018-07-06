
package ch.rhb.integra.mitarbeiter.psipenta.jaxb;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ch.rhb.integra.mitarbeiter.psipenta.jaxb package. 
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

    private final static QName _Reply_QNAME = new QName("http://www.psipenta.de/WebServiceDataSchema", "Reply");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ch.rhb.integra.mitarbeiter.psipenta.jaxb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Reply }
     * 
     */
    public Reply createReply() {
        return new Reply();
    }

    /**
     * Create an instance of {@link PropertyListWithOptionalLines }
     * 
     */
    public PropertyListWithOptionalLines createPropertyListWithOptionalLines() {
        return new PropertyListWithOptionalLines();
    }

    /**
     * Create an instance of {@link PropertyList }
     * 
     */
    public PropertyList createPropertyList() {
        return new PropertyList();
    }

    /**
     * Create an instance of {@link Property }
     * 
     */
    public Property createProperty() {
        return new Property();
    }

    /**
     * Create an instance of {@link Result }
     * 
     */
    public Result createResult() {
        return new Result();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Reply }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.psipenta.de/WebServiceDataSchema", name = "Reply")
    public JAXBElement<Reply> createReply(Reply value) {
        return new JAXBElement<Reply>(_Reply_QNAME, Reply.class, null, value);
    }

}
