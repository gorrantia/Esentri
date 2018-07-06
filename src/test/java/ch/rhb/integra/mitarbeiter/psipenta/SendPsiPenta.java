package ch.rhb.integra.mitarbeiter.psipenta;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.anything;

import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import ch.rhb.integra.mitarbeiter.performis.Address;
import ch.rhb.integra.mitarbeiter.performis.CommAddress;
import ch.rhb.integra.mitarbeiter.performis.Mitarbeiter;
import ch.rhb.integra.mitarbeiter.performis.Partner;
import ch.rhb.integra.mitarbeiter.psipenta.PsipentaProperties;
import ch.rhb.integra.mitarbeiter.psipenta.jaxb.Reply;
import ch.rhb.integra.mitarbeiter.psipenta.PsiPentaAdapter;
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

@RunWith(SpringRunner.class)
@RestClientTest()
@EnableConfigurationProperties()
@ContextConfiguration(locations = "/test-camel-context.xml", classes = {PsiPentaAdapter.class})
@Slf4j
public class SendPsiPenta {

  @Autowired
  private MockRestServiceServer mockServer;
  @Value("${psi-penta.xper.endpointUri}")
  private String endpoint;
  
  @Autowired
  private PsiPentaAdapter adapter;
  
  private Mitarbeiter m = new Mitarbeiter();
  private XperResponseFixture responseFixture = new XperResponseFixture();
  
  @Before
  public void prepareTest() throws Exception {
    m.setActiveBool(0);
    m.setPersNr(1500);;
    m.setGpAdrIDMitarbeiter(2000);
    m.setNachname("Bond");
    m.setVorname("James");
    m.setBenutzer("james.bond");
    
    Address testAddr = new Address();
    
    testAddr.setBpAddrID(2000);
    testAddr.setName("Rhb");
    testAddr.setAddress("Österreich");
    
    Partner p = new Partner();
    p.setName("Partner");
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
  }
  
  
@Test
public void testMappingXper() {
  log.info("Test mapping of mitarbeiter object to xper");
  PsipentaProperties response = adapter.mapXperRequest(m);
  
  Assert.assertNotNull(response);
  Assert.assertEquals(response.searchProperty("ID"),"1500");
  Assert.assertEquals(response.searchProperty("COMPANY"), "Partner");
  Assert.assertEquals(response.searchProperty("EMAILADRESS"), "test@rhb.ch");
  Assert.assertEquals(response.searchProperty("EMPLOYEENO"),"1500");
  Assert.assertEquals(response.searchProperty("FIRSTNAME"),"James");
  Assert.assertEquals(response.searchProperty("INITIALS"),"james.bond");
  Assert.assertEquals(response.searchProperty("MOBILEPHONE"),"+49 160 8444001");
  Assert.assertEquals(response.searchProperty("OFFICEPHONE"),"+49 160 8444000");
  
}

@Test
public void testXperResponse() throws Exception {
  Reply res = responseFixture.getDocument("src/test/resources/psipenta.xml");
  
  endpoint = endpoint + m.getPersNr();
  ResponseCreator mockResponse = withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_XML)
      .body(res.toString());
  
  mockServer.expect(ExpectedCount.manyTimes(),anything()) //requestTo(endpoint)
          .andExpect(method(HttpMethod.PUT))
      .andRespond(mockResponse);
  
   Reply response = adapter.callXper(new Mitarbeiter[] {m});
  
  Assert.assertNotNull(response);
//  String company = response.getRecord().getProperty().stream().filter(x -> x.getName() == "COMPANY").findFirst().get().getValue();
//  log.info("Company {}",response.getRecord().getKey());
//  log.info("Result type {}",response.getResult().getType());
}

}
