package ch.rhb.integra.mitarbeiter.mia;


import ch.rhb.integra.mitarbeiter.mia.config.MIAProperties;
import ch.rhb.integra.mitarbeiter.mia.jaxb.MIA;
import ch.rhb.integra.mitarbeiter.mia.jaxb.ReceiveData;
import ch.rhb.integra.mitarbeiter.mia.jaxb.ReceiveDataResponse;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

@Service("miaAdapter")
@Slf4j
public class MIAAdapter extends WebServiceGatewaySupport {
  @Autowired
  private MIAProperties miaProperties;

  private JAXBContext jaxbContexMIA;

  private Marshaller jaxbMarshaller;

  @SuppressWarnings("unchecked")
  public ReceiveDataResponse sendMIA(List<MIA> mitarbeitern, List<MIA> addresse) {
    ReceiveDataResponse response = new ReceiveDataResponse();
    try {
      jaxbContexMIA = JAXBContext.newInstance(MIA.class);
      jaxbMarshaller = jaxbContexMIA.createMarshaller();


      Jaxb2Marshaller jaxb2Marshallar = generateMarshaller();
      this.setMarshaller(jaxb2Marshallar);
      this.setUnmarshaller(jaxb2Marshallar);

    } catch (JAXBException e) {
      log.error("Error creatiign JAXBContext ", e);
      throw new RuntimeException(e);
    }
    if (mitarbeitern == null) {
      mitarbeitern = new ArrayList<>();
    }
    mitarbeitern.addAll(addresse);
    for (MIA mitarbeiter : mitarbeitern) {
      ReceiveData rd = generateReceiveData(mitarbeiter);

      JAXBElement<ReceiveDataResponse> rdr =
          (JAXBElement<ReceiveDataResponse>) getWebServiceTemplate()
              .marshalSendAndReceive(miaProperties.getEndpoint(), rd);
      response = rdr.getValue();
    }


    return response;
  }

  private ReceiveData generateReceiveData(MIA mia) {
    ReceiveData rd = new ReceiveData();
    rd.setMandant(miaProperties.getMandanten());
    StringWriter sw = new StringWriter();
    try {
      jaxbMarshaller.marshal(mia, sw);
    } catch (JAXBException e) {
      log.error("Error creating JAXBContext ", e);
      throw new RuntimeException(e);
    }
    String xmlString = sw.toString();
    rd.setData(xmlString.getBytes());
    return rd;
  }

  public Jaxb2Marshaller generateMarshaller() {
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    // this package must match the package in the <generatePackage> specified in
    // pom.xml
    // marshaller.setContextPath("mia/MiaFileService.wsdl");
    marshaller.setPackagesToScan("ch.rhb.integra.mitarbeiter.mia.jaxb");
    return marshaller;
  }

}
