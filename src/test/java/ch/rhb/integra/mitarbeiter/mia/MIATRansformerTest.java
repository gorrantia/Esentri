package ch.rhb.integra.mitarbeiter.mia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import ch.rhb.integra.mitarbeiter.mia.jaxb.Anschrift;
import ch.rhb.integra.mitarbeiter.mia.jaxb.MIA;
import ch.rhb.integra.mitarbeiter.performis.JSONMitarbeiterResponseSample;
import ch.rhb.integra.mitarbeiter.performis.Mitarbeiter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ObjectMapper.class})

public class MIATRansformerTest {
  @Autowired
  private ObjectMapper objectMapper;
  
  private Mitarbeiter[] mitarbeiter;
  
  private MIATransformer miaTransformer;
  
  @Before
  public void setUp() {
      try {
        Mitarbeiter mit = objectMapper.readValue(new JSONMitarbeiterResponseSample().getJsonSample(), Mitarbeiter.class);
        mitarbeiter= new Mitarbeiter[1];
        mitarbeiter[0]=mit;
      } catch (IOException e) {
          e.printStackTrace();
          fail();
      }
  }
  @Test
  public void testBasicMapping() {
    miaTransformer=new MIATransformer();
    mitarbeiter[0].getArbeitgeberAddress()[0].setName("Rhätische Bahn AG");
    List<MIA> mia=miaTransformer.transform(mitarbeiter);
    assertEquals(1,mia.size());
  }
  
  @Test
  public void testMappingWithWrongArbeitegeberAdreess() {
    miaTransformer=new MIATransformer();
    mitarbeiter[0].getArbeitgeberAddress()[0].setName("Rhätische");
    List<MIA> mia=miaTransformer.transform(mitarbeiter);
    assertEquals(0,mia.size());
  }
  
  @Test
  public void testMapping() {
    miaTransformer=new MIATransformer();
    mitarbeiter[0].getArbeitgeberAddress()[0].setName("Rhätische Bahn AG");
    List<MIA> miaList=miaTransformer.transform(mitarbeiter);
    MIA mia=miaList.get(0);
    assertEquals("1965-10-17",mia.getPerson().getGeburtsdatum());
    assertEquals(30352,mia.getPerson().getPersonID());
    assertEquals(11, mia.getPerson().getMitarbeiter().getFirmaNr());
    assertEquals(8552, mia.getPerson().getMitarbeiter().getPersonalNummer());
    assertEquals("2014-01-01", mia.getPerson().getMitarbeiter().getEintritt());
    assertEquals("",mia.getPerson().getMitarbeiter().getAustritt());
    assertEquals("2018-06-06",mia.getPerson().getMitarbeiter().getMaVersion().getMaVersionGueltigAb());
    assertEquals("Steiner",mia.getPerson().getMitarbeiter().getMaVersion().getName());
    assertEquals("Langnau i.E.",mia.getPerson().getMitarbeiter().getMaVersion().getPersonal().getHeimatOrt());
    assertEquals(100,mia.getPerson().getMitarbeiter().getMaVersion().getPersonal().getBeschaeftigungsGrad());
    assertEquals("M",mia.getPerson().getMitarbeiter().getMaVersion().getPersonal().getGeschlecht().getText());
    assertEquals(2,mia.getPerson().getMitarbeiter().getMaVersion().getAnschriften().getAnschrift().size());
    int numD=0;
    int numP=0;
    for(Anschrift a:mia.getPerson().getMitarbeiter().getMaVersion().getAnschriften().getAnschrift()) {
      if(a.getAnschriftArt().getKurzText().equals("D")) {
        assertEquals("GR",a.getBundesland());
        assertEquals("CH",a.getIsoLandCode());
        assertNull(a.getTelefon());
        assertNull(a.getMobile());
        assertNull(a.getEmail());
        numD++;
      }
      if(a.getAnschriftArt().getKurzText().equals("P")) {
        assertNull(a.getBundesland());
        assertEquals("CH",a.getIsoLandCode());
        assertNull(a.getTelefon());
        assertNull(a.getMobile());
        assertNull(a.getEmail());
        numP++;
      }
    }
    assertEquals(1,numP);
    assertEquals(1,numD);
      
  }
}
