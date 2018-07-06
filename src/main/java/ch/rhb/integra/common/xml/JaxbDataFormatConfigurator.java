package ch.rhb.integra.common.xml;

import java.util.Objects;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import org.apache.camel.converter.jaxb.JaxbDataFormat;

/**
 * Provides various options to configure a JaxbDataFormat object used within Camel routes to marshal
 * XML with JAXB.
 * 
 * @author esentri AG, <a href="mailto:markus.lohn@esentri.com">mlohn</a>
 */
public final class JaxbDataFormatConfigurator {

  /**
   * Private constructor to avoid instance creation.
   */
  private JaxbDataFormatConfigurator() {

  }

  /**
   * Creates a new JaxbDataFormat object ready to use CDATA section when a string contains special
   * characters, like &.
   * 
   * @param contextPath the package to find the JAXB objects
   * @return a new ready configured JaxbDataFormat object
   */
  public static JaxbDataFormat configureJaxbDataFormatWithCDataForStrings(String contextPath) {
    Objects.requireNonNull(contextPath, "contextPath is required.");

    JaxbDataFormat jaxb = new JaxbDataFormat(contextPath);
    jaxb.setPrettyPrint(true);
    jaxb.setXmlStreamWriterWrapper(new CamelCDataXMLStreamWriter());

    return jaxb;
  }

  /**
   * Creates a new JaxbDataFormat object ready to use CDATA section when a string contains special
   * characters, like &.
   * 
   * @param classesToBeBound list of java classes to be recognized by the new JAXBContext. Can be
   *        empty, in which case a JAXBContext that only knows about spec-defined classes will be
   *        returned.
   * @return a new ready configured JaxbDataFormat object
   */
  public static JaxbDataFormat configureJaxbDataFormatWithCDataForStrings(
      Class<?>... classesToBeBound) throws JAXBException {
    JAXBContext jaxbContext = JAXBContext.newInstance(classesToBeBound);
    JaxbDataFormat jaxb = new JaxbDataFormat(jaxbContext);
    jaxb.setPrettyPrint(true);
    jaxb.setXmlStreamWriterWrapper(new CamelCDataXMLStreamWriter());

    return jaxb;
  }

}
