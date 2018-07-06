package ch.rhb.integra.mitarbeiter.psipenta;

import ch.rhb.integra.mitarbeiter.psipenta.jaxb.ObjectFactory;
import ch.rhb.integra.mitarbeiter.psipenta.jaxb.Property;
import ch.rhb.integra.mitarbeiter.psipenta.jaxb.Reply;
import ch.rhb.integra.mitarbeiter.psipenta.jaxb.Result;
import ch.rhb.integra.mitarbeiter.psipenta.jaxb.ResultType;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XperResponseFixture {

  private static JAXBContext jaxbContext = null;

  static {
    try {
      jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
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
  public Reply getDocument(String name) throws Exception {
    
    File f = new File(name);
    if (!f.exists()) {
      throw new Exception("File does not exist");
    }
        
    InputStream is = new FileInputStream(f);
    return unmarshal(is);
    }
  
  private Reply unmarshal(InputStream is) throws Exception {
    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    JAXBElement<Object> doc =(JAXBElement<Object>) unmarshaller.unmarshal(is);
    return (Reply)doc.getValue();
  }
  
  public Reply setDocument() {
    Reply res = new Reply();
    res.getRecord().setKey("1001");
    List<Property> props = res.getRecord().getProperty();
      
      props.add(new Property("COMPANY","Rhb"));
      props.add(new Property("COMPANYADDRESS","4841"));
      props.add(new Property("EMAILADRESS","michael.forrer@rhb.ch"));
      props.add(new Property("EMPLOYEENO","10001"));
      props.add(new Property("FIRSTNAME","Michael Testli vom Michi"));
      props.add(new Property("HOMEPAGE","Techniker Test"));
      props.add(new Property("SURNAME","Forrer Test"));
  
    Result r = res.getResult();
    r.setType(ResultType.OK);
   
    return res;
  }

}
