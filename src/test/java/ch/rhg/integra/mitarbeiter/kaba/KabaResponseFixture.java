package ch.rhg.integra.mitarbeiter.kaba;

import ch.rhb.integra.mitarbeiter.kaba.jaxb.AddPersonResponse;
import ch.rhb.integra.mitarbeiter.kaba.jaxb.ObjectFactory;
import ch.rhb.integra.mitarbeiter.kaba.jaxb.SoapResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KabaResponseFixture {

  private static JAXBContext jaxbContext = null;

  static {
    try {
      jaxbContext = JAXBContext.newInstance(SoapResponse.class);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * Tries to load and unmarshal a Document from the given resource, like a file.
   * 
   * @param name the name of the resource
   * @return the new Document or null if resource not found or null
   * @throws Exception if operation failed
   */
  public SoapResponse getDocument(String name) throws Exception {
    
    File f = new File(name);
    if (!f.exists()) {
      throw new Exception("File does not exist");
    }
        
    InputStream is = new FileInputStream(f);
    return unmarshal(is);
    }
  
  private SoapResponse unmarshal(InputStream is) throws Exception {
    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    return (SoapResponse) unmarshaller.unmarshal(is);
  }
  

}
