package ch.rhb.integra.mitarbeiter.performis;

import ch.rhb.integra.config.PerformisConnectProperties;
import ch.rhb.integra.interceptors.LoggingRequestInterceptor;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;
/**
 * Main rest template adapter for calling Performis
 * 
 * 
 * @author gorrantia
 *
 */
@Service("mitarbeiterPerformisAdapter")
@Slf4j
public class MitarbeiterPerformisAdapter {
  @Autowired
  private PerformisConnectProperties endpointProperties;
  
  private final RestTemplateBuilder restTemplateBuilder;
  
  private Map<String,Personal> personalCache = null;
  private Map<String,Organisation> organisationCache = null;
  private Map<String,Address[]> addressCache = null;
  private Map<String,Partner[]> partnerCache = null;
  private Map<String,Codemaster[]> codemasterCache = null;
 
  
  private HttpEntity<?> entity=null;
  private RestTemplate restTemplate=null;
  
  private static final String COMPANY_ID="11";
  
  private static final String PARAM_FILTER = "$filter";
  private static final long  ORG_HIERARCHIE=5310002;
  private static final String PARAM_PERSNR = "PERSNR eq &VALUE1_TOKEN&"; //PERSNR eq 4841
  private static final String PARAM_GP_ADR_ID_MITARBEITER = "GP_ADR_ID_MITARBEITER eq &VALUE1_TOKEN&";
  private static final String PARAM_ORGANISATION_ID = "ORGANISATION_ID eq &VALUE1_TOKEN&";
  private static final String PARAM_BP_ADDR_ID = "BP_ADDR_ID eq &VALUE1_TOKEN&";
  private static final String PARAM_BP_ID = "BP_ID eq &VALUE1_TOKEN&";
  private static final String PARAM_CODE="CODE eq &VALUE1_TOKEN&";
  private static final String PARAM_MODIFY_DATE_COMM_ADDRESS = "MODIFY_DATE ge datetime'&VALUE1_TOKEN&' ";
  
  private static final String PARAM_PERSONAL_ID="PERSONAL_ID eq &VALUE1_TOKEN&";
  private static final String PARAM_VERSION_GUELTIG_AB = "FIRMANR eq 11 and VERSION_GUELTIG_AB eq datetime'&VALUE1_TOKEN&' "
      + "and MODIFY_DATE_MA_VERSION ge datetime'&VALUE1_TOKEN&'";
  private static final String PARAM_MODIFY_DATE = "FIRMANR eq &VALUE1_TOKEN& and "
      + "MODIFY_DATE_MA_VERSION ge datetime'&VALUE2_TOKEN&' ";
  private static final String PARAM_EINTRITSSDATUM = "FIRMANR eq 11 and EINTRITT lt datetime'&VALUE1_TOKEN&'";
  private static final String VALUE1_TOKEN = "&VALUE1_TOKEN&";
  private static final String VALUE2_TOKEN = "&VALUE2_TOKEN&";

  private static final String PARAM_BP_ADDR_ID_AND_BP_ID_EMPLOYEE = "BP_ADDR_ID eq &VALUE1_TOKEN& and BP_ID_EMPLOYEE eq &VALUE2_TOKEN&";
  
  public MitarbeiterPerformisAdapter(RestTemplateBuilder r) {
    restTemplateBuilder = r;
  }
  
  private void initializeCaches() {
    personalCache = new HashMap<>();
    addressCache= new HashMap<>();
    organisationCache = new HashMap<>();
    partnerCache = new HashMap<>();
    codemasterCache = new HashMap<>();
    
  }
  
  private RestTemplate getRestTemplate() {
    if(restTemplate==null) {
      restTemplate = buildTemplate();
    }
    return restTemplate;
  }
  
  private HttpEntity<?> getHttpEntity(){
    if(entity==null) {
      entity = buildHTTPEntity();
    }
    return entity;
  }
  
  
  
  /**
   * Searches the Mitabeiter table using PERSNR as filter
   * @param personalNummer filter for PERSNR columns
   * @param strUuid for logging purpouses
   * @return
   */
  public Mitarbeiter[] searchMitarbeiterByPersonalNummer(String personalNummer, String strUuid) {
    String param = PARAM_PERSNR.replace(VALUE1_TOKEN, personalNummer);
    return searchAndEnrichMitarbieter(param);
    
  }
  
  /**
   * Connectst to perfis to query Mitarbeiter objects
   * @param param the Query para filter to be used for the search
   * @return
   */
  private Mitarbeiter[] searchAndEnrichMitarbieter(String param) {
    initializeCaches();
    Mitarbeiter[] mitarbeiter= new Mitarbeiter[0];
    log.debug("Searching mitarbeiter ");
    String completeURL = endpointProperties.getMitarbeiter().getEndpointUri();
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(completeURL);
    builder.queryParam(encode(PARAM_FILTER),encode(param)); 
    try {
      log.info("Calling Performis to search mitarbeiter" + builder.build(true).toUri());
      HttpEntity<Mitarbeiter[]> response=getRestTemplate().exchange(builder.build(true).toUri(), HttpMethod.GET, getHttpEntity(), Mitarbeiter[].class);
      mitarbeiter=response.getBody();
      for(Mitarbeiter mita:mitarbeiter) {
        enrichMitarbeiter(mita);
      }
    }catch(HttpClientErrorException ex) {
      log.error("Error Invoking performis " + ex);
      throw ex;
    }
    log.info("Finish calling performis for Mitarbeiter. Result " + mitarbeiter.length);
    return mitarbeiter;
  }
  
  /**
   * Connects to performis to query Mitarbeiter objects. Does not enrich
   * @param param the Query param filter to be used for the search
   * @return
   */
  private Mitarbeiter searchMitarbieter(String param) {
    initializeCaches();
    Mitarbeiter mitarbeiter= null;
    log.debug("Searching mitarbeiter ");
    String completeURL = endpointProperties.getMitarbeiter().getEndpointUri();
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(completeURL);
    builder.queryParam(encode(PARAM_FILTER),encode(param)); 
    try {
      log.info("Calling Performis to search mitarbeiter" + builder.build(true).toUri());
      HttpEntity<Mitarbeiter[]> response=getRestTemplate().exchange(builder.build(true).toUri(), HttpMethod.GET, getHttpEntity(), Mitarbeiter[].class);
      Mitarbeiter [] m=response.getBody();
      if(m!=null && m.length>0) {
        mitarbeiter=m[0];
      }
    }catch(HttpClientErrorException ex) {
      log.error("Error Invoking performis " + ex);
      throw ex;
    }
    log.info("Finish calling performis for Mitarbeiter. Result " + mitarbeiter.getPersNr());
    return mitarbeiter;
  }
  
  /**
   * Searches Mitarbeiter by using the MODIFY_DATE_MA_VERSION
   * @param lowerValue value to compare against MODIFY_DATE_MA_VERSION
   * @param strUuid
   * @return
   */
  public Mitarbeiter[] searchMitarbeiterByModifyVersion(String lowerValue) {
    String param = PARAM_MODIFY_DATE.replace(VALUE1_TOKEN, COMPANY_ID);
    param = param.replace(VALUE2_TOKEN, lowerValue);
    return searchAndEnrichMitarbieter(param);
    
  }
  /**
   * Search for changes in workers where Version_Gueltig_Ab flag is set before timestamp
   * 
   * @return array of workers
   */
  public Mitarbeiter[] searchMitarbeiterByVersionGueltig(String lastExecution) {
     String param = PARAM_VERSION_GUELTIG_AB.replace(VALUE1_TOKEN, lastExecution);
    return searchAndEnrichMitarbieter(param);
    
  }
  
/**
 * Searches for the mitarbeiter using the GP_ADR_ID_MITARBEITER
 * @param mitarbeiterAddressID
 * @return
 */
  public Mitarbeiter searchMitarbeiterByAdrMitarbeiter(String mitarbeiterAddressID) {
     String param = PARAM_GP_ADR_ID_MITARBEITER.replace(VALUE1_TOKEN, mitarbeiterAddressID);
     Mitarbeiter [] mitarbeiter=searchAndEnrichMitarbieter(param);
     if(mitarbeiter!=null && mitarbeiter.length>0) {
       return mitarbeiter[0];
     }
     return null;
    
  }
  
  /**
   * Search workers using on-boarding date
   * 
   * @param lastExecution, on-board date
   * @return list of workers
   */
  public Mitarbeiter[] searchEintrittsDatum(String lastExecution) {
    DateTimeFormatter formatter =
        DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    LocalDate eintritt = LocalDate.parse(lastExecution, formatter);
    
    log.debug("Calling searchEintrittsdatum for {}", eintritt.plusDays(60));
    formatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    eintritt = eintritt.plusDays(60);     // Search for 60 days
    String param = PARAM_EINTRITSSDATUM.replace(VALUE1_TOKEN, formatter.format(eintritt));
    return searchAndEnrichMitarbieter(param);
    
  }
  
  /**
   * Perform a search by using CommAddressModifyDate
   * 
   * @param lastExecution, date of CommAddressModifyDate
   * @return list of workers
   */
 public Mitarbeiter[] searchMitarbeiterByCommAddressModifyDate(String lastExecution) {
   log.info("Calling searchMitarbeiterByCommAddressModifyDate " + lastExecution);
   initializeCaches();
   List<Mitarbeiter> mitarbeiterList= new ArrayList<>();
   String param = PARAM_MODIFY_DATE_COMM_ADDRESS.replace(VALUE1_TOKEN, lastExecution);
   CommAddress[] commAddresses= searchCommAddress(param);
   if(commAddresses== null ) {
     return new Mitarbeiter[0];
   }
   for(CommAddress commAddress:commAddresses) {
     if(commAddress.getBpIDEmployee()!=0) {
       Address[] addresses=searchAddressByBPAddrID(Long.toString(commAddress.getBpIDEmployee()));
       if(addresses!=null) {
         for(Address address:addresses) {
           if(address.getBpAddrID()!=0) {
             Mitarbeiter mitarbeiter=searchMitarbeiterByAdrMitarbeiter(Long.toString(address.getBpAddrID()));
             if(mitarbeiter!=null) {
               mitarbeiterList.add(mitarbeiter);
             }
           }
         }
       }
     }
   }
   log.info("Finish searchMitarbeiterByCommAddressModifyDate. Result " + mitarbeiterList.size());
   
   return mitarbeiterList.toArray(new Mitarbeiter[0]);

  }

 /**
  * Adds additional objects to parent worker (mitarbeiter) object.
  * 
  * @param mitarbeiter
  */
  private void enrichMitarbeiter(Mitarbeiter mitarbeiter) {
    if(personalCache.containsKey(Long.toString(mitarbeiter.getPersonalID()))){
      mitarbeiter.setPersonal(personalCache.get(Long.toString(mitarbeiter.getPersonalID())));
    }else {
      Personal[] personalArray=findPersonalByPersonalID(Long.toString(mitarbeiter.getPersonalID()));
      if(personalArray.length>0) {
        Personal personal=personalArray[0];
        enrichPersonal(personal);
        mitarbeiter.setPersonal(personal);
        personalCache.put(Long.toString(mitarbeiter.getPersonalID()), personalArray[0]);
      }
      
    }
    mitarbeiter.setFirmaAddress(searchMitarbeiterAddress(Long.toString(mitarbeiter.getGpAdrIDFirma())));
    mitarbeiter.setArbeitgeberAddress(searchMitarbeiterAddress(Long.toString(mitarbeiter.getGpAdrIDMArbeitgeber())));    
    mitarbeiter.setArbeitsortAddress(searchMitarbeiterAddress(Long.toString(mitarbeiter.getGpAdrIDMArbeitsort())));
    mitarbeiter.setMitarbeiterAddress(searchMitarbeiterAddress(Long.toString(mitarbeiter.getGpAdrIDMitarbeiter())));
    if(mitarbeiter.getPersnrVorgesetzterVersion()!=null) {
      // Suche den vorgesetzer
      String param = PARAM_PERSNR.replace(VALUE1_TOKEN, mitarbeiter.getPersnrVorgesetzterVersion());
      Mitarbeiter vorgesetzer=searchMitarbieter(param);
      if(vorgesetzer!=null) {
        mitarbeiter.setVorgesetzerVorname(vorgesetzer.getVorname());
        mitarbeiter.setVorgesetzerNachname(vorgesetzer.getNachname());
      }
    }
    
    if(organisationCache.containsKey(Long.toString(mitarbeiter.getOrganisationID()))){
      mitarbeiter.setOrganisation(organisationCache.get(Long.toString(mitarbeiter.getOrganisationID())));
    }else {
      Organisation org=findOrganisationByOrganisationID(Long.toString(mitarbeiter.getOrganisationID()));
      if(org!=null) {
        mitarbeiter.setOrganisation(org);
        organisationCache.put(Long.toString(mitarbeiter.getOrganisationID()), org);
      }
      
    }
  }
 /**
  * Augment Personal object
  * 
  * @param personal
  */
 private void enrichPersonal(Personal personal) {
   if(codemasterCache.containsKey(Long.toString(personal.getArbeitsPlatzKantonCode()))){
     personal.setCodemaster(codemasterCache.get(Long.toString(personal.getArbeitsPlatzKantonCode())));
   }else {
     Codemaster[] codemasterArray=searchCodemasterByCode(Long.toString(personal.getArbeitsPlatzKantonCode()));
     personal.setCodemaster(codemasterArray);
      codemasterCache.put(Long.toString(personal.getArbeitsPlatzKantonCode()), codemasterArray);
   }
 }
 /**
  * Do search of workers address using its address id
  * 
  * @param addressID
  * @return array of Address objects
  */
 private Address[] searchMitarbeiterAddress(String addressID) {
   if(addressID==null) {
     return new Address[0];
   }
   if(addressCache.containsKey(addressID)) {
     return addressCache.get(addressID);
   }
   Address[] result = searchAddressByBPAddrID(addressID);
   addressCache.put(addressID,result);
   /* Search Partners, codemaster and commadress */
   for(Address address: result) {
     enrichAddress(address);
   }

   return result;
 }
 /**
  * Augment Address object
  * 
  * @param address
  */
 private void enrichAddress(Address address) {
   if(partnerCache.containsKey(Long.toString(address.getBpID()))){
     address.setPartner(partnerCache.get(Long.toString(address.getBpID())));
   }else {
     Partner[] partnerArray=searchPartnerByBPID(Long.toString(address.getBpID()));
      address.setPartner(partnerArray);
      partnerCache.put(Long.toString(address.getBpID()), partnerArray);
   }
   if(codemasterCache.containsKey(Long.toString(address.getC3Country()))){
     address.setCodemaster(codemasterCache.get(Long.toString(address.getC3Country())));
   }else {
     Codemaster[] codemasterArray=searchCodemasterByCode(Long.toString(address.getC3Country()));
      address.setCodemaster(codemasterArray);
      codemasterCache.put(Long.toString(address.getC3Country()), codemasterArray);
   }
   CommAddress[] commAddressArray=searchCommAddressByBPAddrIDAndBPEmployeeID(Long.toString(address.getBpAddrID()),Long.toString(address.getBpID()));
   address.setCommAddress(commAddressArray);
   
 }
/**
 * Find organisation by using its id
 * @param organisationID
 * @return Organisation object
 */
  public Organisation findOrganisationByOrganisationID(String organisationID) {
    Organisation[] organisations= new Organisation[0];
    log.info("Calling searchOrganisationByOrganisationID " + organisationID);
    String completeURL = endpointProperties.getOrganisation().getEndpointUri();
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(completeURL);
    String param = PARAM_ORGANISATION_ID.replace(VALUE1_TOKEN, organisationID);
    builder.queryParam(encode(PARAM_FILTER),
        encode(param)); 
    
    try {
      log.info("Calling Performis " + builder.build(true).toUri());
      HttpEntity<Organisation[]> response=getRestTemplate().exchange(builder.build(true).toUri(), HttpMethod.GET, getHttpEntity(), Organisation[].class);
      organisations=response.getBody();
    }catch(HttpClientErrorException ex) {
      log.error("Error Invoking performis " + ex);
      throw ex;
    }
    //Loop through all Organisations to search from hierachie 5310002. If found retunr that organisation
    // if not use the first one and searcht the next organisation using ORG_ID_UEBERGEORDNET
    if(organisations!=null && organisations.length>0) {
      for(Organisation org:organisations) {
        if(org.getC531Hierarchie()==ORG_HIERARCHIE) {
          return org;
        }
      }
      return findOrganisationByOrganisationID(new Long(organisations[0].getOrgIDUebergeordnet()).toString());
    }
    log.info("Finish calling performis for Organisation. Result " + organisations.length);
    return null;
    
  }

  /**
   * Addresses using BP_ADDR_ID as a filter
   * @param used to filter against BP_ADDR_ID
   * @return
   */
  public Address[] searchAddressByBPAddrID(String bpAddrID) {
    log.info("Calling searchAddressByBPAddrID " + bpAddrID);
    String param = PARAM_BP_ADDR_ID.replace(VALUE1_TOKEN, bpAddrID);
     return searchAddress(param);
    
  }
  
  /**
   * Connects to performis and searches for Addresses
   * @param param -  the query filter
   * @return
   */
  private Address[] searchAddress(String param) {
    Address[] address= new Address[0];
    String completeURL =endpointProperties.getAddress().getEndpointUri();
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(completeURL);
    builder.queryParam(encode(PARAM_FILTER),encode(param)); 
    try {
      log.info("Calling Performis " + builder.build(true).toUri());
      HttpEntity<Address[]> response=getRestTemplate().exchange(builder.build(true).toUri(), HttpMethod.GET, getHttpEntity(), Address[].class);
      address=response.getBody();
    }catch(HttpClientErrorException ex) {
      log.error("Error Invoking performis " + ex);
      throw ex;
    }
    log.info("Finish calling performis for Addresse. Result " + address.length);
    return address;
  }
  
  /**
   * Searches Addresses using BP_ID as a filter
   * @param bpID used to filter against BP_ID
   * @return
   */
  public Address[] searchAddressByBPID(String bpID) {
    log.info("Calling searchAddressByBPID " + bpID);
    String param = PARAM_BP_ID.replace(VALUE1_TOKEN, bpID);
    return searchAddress(param);
    
  }
/**
 * Search list of Partners using Bp Id
 * 
 * @param bpID
 * @return list of Partner objects
 */
  public Partner[] searchPartnerByBPID(String bpID) {
    Partner[] partners= new Partner[0];
    log.info("Calling searchPartnerByBPID " + bpID);
    String completeURL =endpointProperties.getPartner().getEndpointUri();
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(completeURL);
    String param = PARAM_BP_ID.replace(VALUE1_TOKEN, bpID);
    builder.queryParam(encode(PARAM_FILTER),
        encode(param)); 
    
    try {
      log.info("Calling Performis " + builder.build(true).toUri());
      HttpEntity<Partner[]> response=getRestTemplate().exchange(builder.build(true).toUri(), HttpMethod.GET, getHttpEntity(), Partner[].class);
      partners=response.getBody();
    }catch(HttpClientErrorException ex) {
      log.error("Error Invoking performis " + ex);
      throw ex;
    }
    log.info("Finish calling performis for Partner. Result " + partners.length);

    return partners;
    
  }
  /**
   * Internal funcion to search for CommAddress objects
   * 
   * @param param
   * @return array of CommAddress objects
   */
  private CommAddress[] searchCommAddress(String param) {
    CommAddress[] commAddresses= new CommAddress[0];
    String completeURL =endpointProperties.getCommAddress().getEndpointUri();
    HttpEntity<?> entity = buildHTTPEntity();
    RestTemplate restTemplate = buildTemplate();
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(completeURL);
    builder.queryParam(encode(PARAM_FILTER),encode(param)); 

    try {
      log.info("Calling Performis " + builder.build(true).toUri());
      HttpEntity<CommAddress[]> response=restTemplate.exchange(builder.build(true).toUri(), HttpMethod.GET, entity, CommAddress[].class);
      commAddresses=response.getBody();
    }catch(HttpClientErrorException ex) {
      log.error("Error Invoking performis " + ex);
      throw ex;
    }
    log.info("Finish calling performis for CommAdress. Result " + commAddresses.length);
    return commAddresses;
  }
 /**
  * Internal funcion to search for CommAddress objects
  * 
  * @param bpAddrID
  * @param bpEmployeeID
  * @return array of CommAddress objects
  */
  public CommAddress[] searchCommAddressByBPAddrIDAndBPEmployeeID(String bpAddrID,String bpEmployeeID) {
    log.info("Calling searchCommAddressByBPAddrIDAndBPEmployeeID " + bpAddrID + " " + bpEmployeeID);
    String param = PARAM_BP_ADDR_ID_AND_BP_ID_EMPLOYEE.replace(VALUE1_TOKEN, bpAddrID);
    param = param.replace(VALUE2_TOKEN, bpEmployeeID);
    return searchCommAddress(param);
    
  }
/**
 * Internal function to do search by using code from Codemaster
 * 
 * @param code
 * @return array of Codemaster objects
 */
  public Codemaster[] searchCodemasterByCode(String code) {
    Codemaster[]  codemasters= new Codemaster[0] ;
    log.info("Calling searchCodemasterByCode " + code);
    String completeURL =endpointProperties.getCodemaster().getEndpointUri();
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(completeURL);
    String param = PARAM_CODE.replace(VALUE1_TOKEN, code);
    builder.queryParam(encode(PARAM_FILTER),
        encode(param)); 
    try {
      log.info("Calling Performis " + builder.build(true).toUri());
      HttpEntity<Codemaster[]> response=getRestTemplate().exchange(builder.build(true).toUri(), HttpMethod.GET, getHttpEntity(), Codemaster[].class);
      codemasters=response.getBody();
    }catch(HttpClientErrorException ex) {
      log.error("Error Invoking performis " + ex);
      throw ex;
    }
    log.info("Finish calling performis for Codemaster. Result " + codemasters.length);

    return codemasters;
    
  }
/**
 * Returns list of Personal objects by searching its id
 * 
 * @param personalID
 * @return array of Personal objects
 */
  public Personal[] findPersonalByPersonalID(String personalID) {
    Personal[] personal= new Personal[0];
    log.info("Calling searchPersonalByPersonalID " + personalID);
    String completeURL =endpointProperties.getPersonal().getEndpointUri();
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(completeURL);
    String param = PARAM_PERSONAL_ID.replace(VALUE1_TOKEN, personalID);
    builder.queryParam(encode(PARAM_FILTER),
        encode(param)); 
    try {
      log.info("Calling Performis " + builder.build(true).toUri());
      HttpEntity<Personal[]> response=getRestTemplate().exchange(builder.build(true).toUri(), HttpMethod.GET, getHttpEntity(), Personal[].class);
      personal=response.getBody();
    }catch(HttpClientErrorException ex) {
      log.error("Error Invoking performis " + ex);
      throw ex;
    }
    log.info("Finish calling performis for Personal. Result " + personal);

    return personal;
  }
  
  
  /**
   * Builds a template with Logging interceptor in order to log request and reply
   * 
   * @return Springboot resttemplate with a logging interceptor in it
   */
  private RestTemplate buildTemplate() {
      List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
      interceptors.add(new LoggingRequestInterceptor());
      restTemplateBuilder.interceptors(interceptors);
      return restTemplateBuilder.basicAuthorization(endpointProperties.getCredentials().getUsername(),
              endpointProperties.getCredentials().getPassword()).build();
  }

  /**
   * Tries to apply URL encoding on a given String.
   * 
   * @param str
   *            the String to encode
   * @return the encoded String
   */
  private String encode(String str) {
      try {
          return UriUtils.encode(str, StandardCharsets.UTF_8.toString());
      } catch (UnsupportedEncodingException e) {
          return str;
      }
  }
 /**
  * Builds httpEntity object
  *  
  * @return httpEntitiy object
  */
  private HttpEntity<?> buildHTTPEntity() {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);    
    HttpEntity<?> entity = new HttpEntity<>(headers);
    return entity;
  }
}