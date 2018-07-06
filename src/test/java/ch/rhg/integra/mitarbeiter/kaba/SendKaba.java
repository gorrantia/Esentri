package ch.rhg.integra.mitarbeiter.kaba;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import ch.rhb.integra.common.Utilities;
import ch.rhb.integra.common.XmlMapperUtils;
import ch.rhb.integra.mitarbeiter.kaba.KabaSoapAdapter;
import ch.rhb.integra.mitarbeiter.kaba.jaxb.AddPersonResponse;
import ch.rhb.integra.mitarbeiter.kaba.jaxb.Person;
import ch.rhb.integra.mitarbeiter.kaba.jaxb.SoapResponse;
import ch.rhb.integra.mitarbeiter.performis.Address;
import ch.rhb.integra.mitarbeiter.performis.CommAddress;
import ch.rhb.integra.mitarbeiter.performis.Mitarbeiter;
import ch.rhb.integra.mitarbeiter.performis.Organisation;
import ch.rhb.integra.mitarbeiter.performis.Partner;
import ch.rhb.integra.mitarbeiter.psipenta.XperResponseFixture;
import ch.rhb.integra.mitarbeiter.psipenta.jaxb.Reply;
import java.net.URI;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.ResponseCreator;
import lombok.extern.slf4j.Slf4j;
/**
 * 
 * JUnit test for the Kava web service
 * 
 * 
 * @author gorrantia
 *
 */
@RunWith(SpringRunner.class)
@RestClientTest()
@EnableConfigurationProperties()
@ContextConfiguration(locations = "/test-camel-context.xml", classes = {KabaSoapAdapter.class})
@Slf4j
public class SendKaba {

  @Autowired
  private MockRestServiceServer mockServer;
  @Value("${kaba-exos.endpointUri}")
  private String endpoint;
  
  @Autowired
  private KabaSoapAdapter adapter;

  private Mitarbeiter m = new Mitarbeiter();
  private KabaResponseFixture responseFixture = new KabaResponseFixture();


  @Before
  public void prepareTest() throws Exception {
    m.setActiveBool(0);
    m.setPersNr(1500);;
    m.setGpAdrIDMitarbeiter(2000);
    m.setNachname("Bond");
    m.setVorname("James");
    m.setBenutzer("james.bond");
    
    m.setEintritt("03.03.2015");
    m.setOrganisationPfadKurz("MI6");
    m.setGpAdrIDMArbeitsort(2000);
    
    Address testAddr = new Address();
    
    testAddr.setBpAddrID(2000);
    testAddr.setName("Rhb");
    testAddr.setCity("Wien");
    testAddr.setAddress("Österreich");
    
    Partner p = new Partner();
    p.setName("Partner");
    p.setBpID(2500);
    testAddr.setPartner(new Partner[] {p});
    
    CommAddress c1 = new CommAddress();
    c1.setBpAddrID(2000);
    c1.setC332CommType(3320001);
    c1.setCommAddress("+49 160 8444000");
    
    CommAddress c2 = new CommAddress();
    c2.setBpAddrID(2000);
    c2.setC332CommType(3320003);
    c2.setCommAddress("+49 160 8444001");
    
    CommAddress c3 = new CommAddress();
    c3.setBpAddrID(2000);
    c3.setC332CommType(3320005);
    c3.setCommAddress("test@rhb.ch");
    
    CommAddress[] commAddress = new CommAddress[] {c1,c2,c3};
    testAddr.setCommAddress(commAddress);
    
    Address[] arbeitgeberAddress = {testAddr};
    m.setArbeitgeberAddress(arbeitgeberAddress);
    m.setArbeitsortAddress(arbeitgeberAddress);
    
    Organisation org = new Organisation();
    org.setBezeichnung("MI6");
    m.setOrganisation(org);
    
  }
  /**
   * Test mapping of mitarbeiter object to Person
   */
  @Test
  public void testMappingKaba() {
    log.info("Test mapping of mitarbeiter object to Kaba");
    Person p = adapter.agrega(m);
    log.info(p.toString());
    Assert.assertEquals("Wien",p.getArbeitsort());
    Assert.assertEquals(XmlMapperUtils.buildDateTime("2015-03-03","yyyy-MM-dd"), p.getEintritt());
    Assert.assertEquals("Bond", p.getNachname());
    Assert.assertEquals("MI6", p.getOrganisation());
    Assert.assertEquals("1500", p.getPersonalnummer());
    Assert.assertEquals("James", p.getVorname());
  }
  
  @Test
  public void testKapaSend() throws Exception {
    SoapResponse res = responseFixture.getDocument("src/test/resources/kaba.xml");
    log.info(res.toString());
    
    ResponseCreator mockResponse = withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_XML)
        .body(res.toString());
    
    URI restUri = Utilities.buildEndpointUri(endpoint);
    
    mockServer.expect(ExpectedCount.manyTimes(),requestTo(restUri.toString()))
            .andExpect(method(HttpMethod.POST))
        .andRespond(mockResponse);
    
    SoapResponse response = adapter.sendUpdate(new Mitarbeiter[]{m});
    
    Assert.assertNotNull(response);
  }
  
}
