package ch.rhb.integra.mitarbeiter.psipenta;

import ch.rhb.integra.common.Utilities;
import ch.rhb.integra.mitarbeiter.performis.Address;
import ch.rhb.integra.mitarbeiter.performis.CommAddress;
import ch.rhb.integra.mitarbeiter.performis.Mitarbeiter;
import ch.rhb.integra.mitarbeiter.psipenta.jaxb.Reply;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * PSI Penta Adapter for the Xper function
 * 
 * 
 * @author esentri AG, <a href="mailto:guillermo.orrantian@esentri.com">Guillermo Orrantia</a>
 *
 */

@Slf4j
@Service("PsiPentaAdapter")
public class PsiPentaAdapter {

  @Value("${psi-penta.xper.endpointUri}")
  private String xperEndpoint;
  @Value("${psi-penta.pobi.endpointUri}")
  private String pobiEndpoint;
  
  private RestTemplate restTemplate;
  private Reply output;

  public PsiPentaAdapter(RestTemplateBuilder builder) {
    restTemplate = builder.setConnectTimeout(5000).setReadTimeout(5000).build();
    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
    restTemplate.getMessageConverters().add(new StringHttpMessageConverter());    
  }
  
  public Reply callXper(Mitarbeiter[] liste) {
    log.trace("Calling psipenta adapter for xper and pobi");
    List<Mitarbeiter> failed = new ArrayList<Mitarbeiter>();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_XML);
    
    Arrays.stream(liste).forEach(x ->{
      PsipentaProperties request = mapXperRequest(x);
      HttpEntity<PsipentaProperties> entity =
          new HttpEntity<PsipentaProperties>(request, headers);
      
      URI restUri = Utilities.buildEndpointUri(xperEndpoint+"/"+x.getPersNr());
      log.debug("Call PsiPenta Xper function on {}",restUri.toString());
      
      ResponseEntity<Reply> response = restTemplate.exchange(restUri, HttpMethod.PUT, entity, Reply.class);
      log.debug("Received {} with status code {}",response.getBody().toString(),response.getStatusCode()); 
      
      if (response.getStatusCode() != HttpStatus.OK) {
        log.error("Update of worker with id={} has failed",x.getPersNr());
        failed.add(x);
      }
      
     pobiEndpoint = pobiEndpoint.replaceAll("ID", String.valueOf(x.getPersNr()));
      restUri = Utilities.buildEndpointUri(pobiEndpoint);
      log.debug("Call PsiPenta Pobi function on {} using {}",x.getPersNr(),restUri.toString());

      request = mapPobiRequest(x);      
      response = restTemplate.exchange(restUri, HttpMethod.PUT, entity, Reply.class);
      log.debug("Received {} with status code {}",response.getBody().toString(),response.getStatusCode()); 
      
      output = response.getBody();

      if (response.getStatusCode() != HttpStatus.OK) {
        log.error("Update of worker with id={} has failed",x.getPersNr());
        failed.add(x);
      }
    
    });
    return output;
  }
  /**
   * Creates a PsipentaProperties object from a mitarbeiter object
   * @param m mitarbeiter object
   * @return PsipentaProperties object
   */
  public PsipentaProperties mapXperRequest(Mitarbeiter m) {
    PsipentaProperties request = new PsipentaProperties();
    
    PsipentaRecordEntry id = new PsipentaRecordEntry("ID");
    id.setValue(String.valueOf(m.getPersNr()));
    request.addEntry(id);
    
    PsipentaRecordEntry company        = new PsipentaRecordEntry("COMPANY");
    company.setValue(searchAddress(m).getPartner()[0].getName());
    request.addEntry(company);
    
    PsipentaRecordEntry companyaddress = new PsipentaRecordEntry("COMPANYADDRESS");
    companyaddress.setValue(searchAddress(m).getAddress());
    request.addEntry(companyaddress);
    
    PsipentaRecordEntry degree         = new PsipentaRecordEntry("DEGREE");
    request.addEntry(degree);
    
    PsipentaRecordEntry emailAdress = new PsipentaRecordEntry("EMAILADRESS");
    emailAdress.setValue(searcCommAdress(m,3320005).getCommAddress());
    request.addEntry(emailAdress);
    
    PsipentaRecordEntry employeeNo = new PsipentaRecordEntry("EMPLOYEENO");
    employeeNo.setValue(String.valueOf(m.getPersNr()));
    request.addEntry(employeeNo);
    
    PsipentaRecordEntry firstName = new PsipentaRecordEntry("FIRSTNAME");
    firstName.setValue(m.getVorname());
    request.addEntry(firstName);
    
    PsipentaRecordEntry homepage = new PsipentaRecordEntry("HOMEPAGE");
    homepage.setValue(m.getBerufAusgeubt());
    request.addEntry(homepage);
    
    PsipentaRecordEntry initials = new PsipentaRecordEntry("INITIALS");
    initials.setValue(m.getBenutzer());
    request.addEntry(initials);
    
    PsipentaRecordEntry mobilPhone = new PsipentaRecordEntry("MOBILEPHONE");
    mobilPhone.setValue(searcCommAdress(m,3320003).getCommAddress());
    request.addEntry(mobilPhone);
    
    PsipentaRecordEntry nameSupplement = new PsipentaRecordEntry("NAMESUPPLEMENT");
    request.addEntry(nameSupplement);
    
    PsipentaRecordEntry officeFax = new PsipentaRecordEntry("OFFICEFAX");
    request.addEntry(officeFax);
    PsipentaRecordEntry officePhone = new PsipentaRecordEntry("OFFICEPHONE");
    officePhone.setValue(searcCommAdress(m,3320001).getCommAddress());
    request.addEntry(officePhone);
    
    PsipentaRecordEntry position = new PsipentaRecordEntry("POSITION");
    request.addEntry(position);
    PsipentaRecordEntry privateAddress = new PsipentaRecordEntry("PRIVATEADDRESS");
    request.addEntry(privateAddress);
    PsipentaRecordEntry privatePhone = new PsipentaRecordEntry("PRIVATEPHONE");
    request.addEntry(privatePhone);
    PsipentaRecordEntry room        = new PsipentaRecordEntry("ROOM");
    request.addEntry(room);
    PsipentaRecordEntry substitute  = new PsipentaRecordEntry("SUBSTITUTE");
    request.addEntry(substitute);
    PsipentaRecordEntry substituteStatus = new PsipentaRecordEntry("SUBSTITUTESTATUS");
    request.addEntry(substituteStatus);
    PsipentaRecordEntry surname          = new PsipentaRecordEntry("SURNAME");
    surname.setValue(m.getNachname());
    request.addEntry(surname);
    
    PsipentaRecordEntry title = new PsipentaRecordEntry("TITLE");
    request.addEntry(title);
    
    log.debug("Mapped object {}",request.toString());
    return request;
  }
  
  private Address searchAddress(Mitarbeiter m) {
    Stream<Address> addressStream = Arrays.stream(m.getArbeitgeberAddress());
    
    long addr = m.getGpAdrIDMitarbeiter();
    Optional<Address> gpIdAdr = addressStream
        .filter(x -> x.getBpAddrID() == addr).findFirst();
    
    if (gpIdAdr.isPresent()) {
      return gpIdAdr.get();
    }
    
    return new Address();
  }
  
  private CommAddress searcCommAdress(Mitarbeiter m,long tipe) {
    Address adr = searchAddress(m);

    Stream<CommAddress> commStream = Arrays.stream(adr.getCommAddress());
    Optional<CommAddress> commAdr = commStream.filter(x -> x.getC332CommType() == tipe).findFirst();
    
    if(commAdr.isPresent()) {
      return commAdr.get();
    }
    
    return new CommAddress();
  }
  
  public PsipentaProperties mapPobiRequest(Mitarbeiter m) {
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    GregorianCalendar now = new GregorianCalendar();
    
    PsipentaProperties request = new PsipentaProperties();
    
    PsipentaRecordEntry folge_nr = new PsipentaRecordEntry("FOLGE_NR");
    folge_nr.setValue("0");
    request.addEntry(folge_nr);
    
    PsipentaRecordEntry objekt = new PsipentaRecordEntry("OBJEKT");
    objekt.setValue("XPER");
    request.addEntry(objekt);
    
    PsipentaRecordEntry objektSchluessel = new PsipentaRecordEntry("OBJEKTSCHLUESSEL");
    objektSchluessel.setValue(String.valueOf(m.getPersNr()));
    request.addEntry(objektSchluessel);    
    
    PsipentaRecordEntry pa01 = new PsipentaRecordEntry("P_A01");
    objektSchluessel.setValue(m.getKstnr());
    request.addEntry(pa01);    
   
    PsipentaRecordEntry pa02 = new PsipentaRecordEntry("P_A02");
    objektSchluessel.setValue(m.getBerufAusgeubt());
    request.addEntry(pa02);    
    
    PsipentaRecordEntry pa03 = new PsipentaRecordEntry("P_A03");
    objektSchluessel.setValue(m.getStundenSatz());
    request.addEntry(pa03);    

    PsipentaRecordEntry pa04 = new PsipentaRecordEntry("P_A04");
    objektSchluessel.setValue(m.getKleiderCode());
    request.addEntry(pa04);    

    PsipentaRecordEntry pa07 = new PsipentaRecordEntry("P_A07");
    objektSchluessel.setValue(m.getNachname());
    request.addEntry(pa07);    

    PsipentaRecordEntry pa08 = new PsipentaRecordEntry("P_A08");
    String status = (m.getActiveBool() == 0)? "I" : "A";
    objektSchluessel.setValue(status);
    request.addEntry(pa08);    

    PsipentaRecordEntry pa09 = new PsipentaRecordEntry("P_A09");
    objektSchluessel.setValue(df.format(now.getTime()));
    request.addEntry(pa09);    

    log.debug("Mapped object {}",request.toString());
    return request;
  }
  
}
