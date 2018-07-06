package ch.rhb.integra.mitarbeiter.mia.jaxb;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.piag.web.services package.
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

    private final static QName _ReceiveData_QNAME = new QName("http://services.web.piag.com/", "receiveData");
    private final static QName _ReceiveDataResponse_QNAME =
        new QName("http://services.web.piag.com/", "receiveDataResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.piag.web.services
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ReceiveData }
     *
     */
    public ReceiveData createReceiveData() {
        return new ReceiveData();
    }

    /**
     * Create an instance of {@link ReceiveDataResponse }
     *
     */
    public ReceiveDataResponse createReceiveDataResponse() {
        return new ReceiveDataResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReceiveData }{@code >}
     *
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ReceiveData }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.web.piag.com/", name = "receiveData")
    public JAXBElement<ReceiveData> createReceiveData(ReceiveData value) {
        return new JAXBElement<ReceiveData>(_ReceiveData_QNAME, ReceiveData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReceiveDataResponse }{@code >}
     *
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ReceiveDataResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.web.piag.com/", name = "receiveDataResponse")
    public JAXBElement<ReceiveDataResponse> createReceiveDataResponse(ReceiveDataResponse value) {
        return new JAXBElement<ReceiveDataResponse>(_ReceiveDataResponse_QNAME, ReceiveDataResponse.class, null, value);
    }

}
