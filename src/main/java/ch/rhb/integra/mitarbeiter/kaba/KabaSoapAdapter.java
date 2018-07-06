package ch.rhb.integra.mitarbeiter.kaba;

import ch.rhb.integra.common.Utilities;
import ch.rhb.integra.common.XmlMapperUtils;
import ch.rhb.integra.mitarbeiter.kaba.jaxb.ObjectFactory;
import ch.rhb.integra.mitarbeiter.kaba.jaxb.Person;
import ch.rhb.integra.mitarbeiter.kaba.jaxb.Persons;
import ch.rhb.integra.mitarbeiter.kaba.jaxb.SoapEnvelope;
import ch.rhb.integra.mitarbeiter.kaba.jaxb.SoapResponse;
import ch.rhb.integra.mitarbeiter.kaba.jaxb.SoapResponse.Body;
import ch.rhb.integra.mitarbeiter.performis.Address;
import ch.rhb.integra.mitarbeiter.performis.Mitarbeiter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;
/**
 * 
 * Kapa Soap Adapter
 * 
 * Will receive an array of Mitarbeiter objects, map this objects to Kapa Request object
 * and further transmit to Kapa
 * 
 * 
 * @author <a href="guillermo.orrantia@esentri.com">Guillermo Orrantia</a> *
 */
@Slf4j
@Service("KabaSoapAdapter")
public class KabaSoapAdapter {

  @Value("${kaba-exos.endpointUri}")
  private String endpoint;

  private ObjectFactory obj = new ObjectFactory();

  private RestTemplate restTemplate;
  
  public KabaSoapAdapter(RestTemplateBuilder builder) {
    restTemplate = builder.setConnectTimeout(5000).setReadTimeout(5000).build();
    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
    restTemplate.getMessageConverters().add(new StringHttpMessageConverter());    
  }
  /**
   * Calls the Kaba soap webservice
   * 
   * @param lista, an array of mitarbeiter objects
   * @return AddPersonResponse from web service
   */
  public SoapResponse sendUpdate(Mitarbeiter[] lista) {
    log.trace("KabaSoapAdapter: got {} Mitarbeitern",lista.length);
  
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.TEXT_XML);
    headers.set("SOAPAction", "http://kabawcf.ch/IKabaService/AddPerson");

    List<Person> people = new ArrayList<Person>();
    Arrays.stream(lista).forEach(x -> {
      Person p = agrega(x);
      people.add(p);
      });
   
    SoapEnvelope request = new SoapEnvelope();
    Persons p = new Persons(people);
    ch.rhb.integra.mitarbeiter.kaba.jaxb.SoapEnvelope.Body b = request.getBody();
    b.setAddPerson(p);
    
    p.setPerson(people);
    
    log.trace("Request message to Kaba {}");
    HttpEntity<SoapEnvelope> entity =
        new HttpEntity<SoapEnvelope>(request, headers);
    
    URI restUri = Utilities.buildEndpointUri(endpoint);
    log.trace("Call Kaba function on {}",restUri.toString());
    
    ResponseEntity<SoapResponse> response = restTemplate.exchange(restUri, HttpMethod.POST, entity, SoapResponse.class);

    return response.getBody();
  }
    
  /**
   * Convert a mitarbeiter object into a Person object
   * 
   * @param mitarbeiter object to be mapped
   * @return person object
   */
  public Person agrega(Mitarbeiter mitarbeiter) {
    Person p = obj.createPerson();
    
    p.setPersonalnummer(String.valueOf(mitarbeiter.getPersNr()));
    
    p.setVorname(mitarbeiter.getVorname());
    p.setNachname(mitarbeiter.getNachname());
    p.setEintritt(XmlMapperUtils.toCal(mitarbeiter.getEintritt()));
    if (mitarbeiter.getAustritt() != null)
      p.setAustritt(XmlMapperUtils.toCal(mitarbeiter.getAustritt()));
    if (mitarbeiter.getOrganisation().getBezeichnung() != null)
      p.setGeschaeftsbereich(mitarbeiter.getOrganisation().getBezeichnung());
    p.setHauptkostenstelleNummer(mitarbeiter.getKstnr());
    p.setOrganisation(mitarbeiter.getOrganisationPfadKurz());
    
    mitarbeiter.getGpAdrIDMArbeitgeber();
    mitarbeiter.getMaVersionID();
    
    if (mitarbeiter.getGpAdrIDMArbeitsort() != 0) {
      Optional<Address> addr = searchAddr(mitarbeiter,mitarbeiter.getGpAdrIDMArbeitsort());
    if (addr.isPresent())
      p.setArbeitsort(addr.get().getCity());
    }
    
    long gpAdrID = mitarbeiter.getGpAdrIDMArbeitgeber();
    if (gpAdrID != 0) {
    Optional<Address> addr = searchAddr(mitarbeiter,gpAdrID);
      if (addr.isPresent()) {
        p.setFunktion(addr.get().getPartner()[0].getBpID());
      }
    }
    
    return p;
  }
  
  /**
   * searches an address array for a BpAddrId with value id
   * 
   * @param mitarbeiter object containing addresses
   * @param id to be searched
   * @return Optional<Address> object
   */
  private Optional<Address> searchAddr(Mitarbeiter mitarbeiter,long id) {
        
    return Arrays.stream(mitarbeiter.getArbeitsortAddress())
        .filter(x -> x.getBpAddrID() == id).findAny();
  }
 
}
