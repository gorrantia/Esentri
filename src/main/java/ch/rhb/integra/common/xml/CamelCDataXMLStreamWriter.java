package ch.rhb.integra.common.xml;

import javax.xml.stream.XMLStreamWriter;
import org.apache.camel.converter.jaxb.JaxbXmlStreamWriterWrapper;

/**
 * Integrates the custom CDataXMLStreamWriter into Apache Camel. Needed to to use a CDATA section
 * for a string when using special characters, like &.
 * 
 * @author esentri AG, <a href="mailto:markus.lohn@esentri.com">mlohn</a>
 */
public class CamelCDataXMLStreamWriter implements JaxbXmlStreamWriterWrapper {

  @Override
  public XMLStreamWriter wrapWriter(final XMLStreamWriter writer) {
    return new CDataXMLStreamWriter(writer);
  }

}
