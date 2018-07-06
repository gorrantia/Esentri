package ch.rhb.integra.common.xml;

import java.util.regex.Pattern;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Implementation which is able to decide to use a CDATA section for a string. Inspired by
 * http://blog.mi-ernst.de/2012/05/04/jaxb-and-cdata-sections/
 * 
 * @author esentri AG, <a href="mailto:markus.lohn@esentri.com">mlohn</a>
 */
public class CDataXMLStreamWriter extends DelegatingXMLStreamWriter {
  private static final Pattern XML_CHARS = Pattern.compile("[&<>]");

  public CDataXMLStreamWriter(XMLStreamWriter writer) {
    super(writer);
  }

  @Override
  public void writeCharacters(String text) throws XMLStreamException {
    boolean useCData = XML_CHARS.matcher(text).find();
    if (useCData) {
      super.writeCData(text);
    } else {
      super.writeCharacters(text);
    }
  }
}
