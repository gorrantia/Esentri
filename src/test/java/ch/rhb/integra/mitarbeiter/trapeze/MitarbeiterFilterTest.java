package ch.rhb.integra.mitarbeiter.trapeze;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import ch.rhb.integra.mitarbeiter.performis.JSONMitarbeiterResponseSample;
import ch.rhb.integra.mitarbeiter.performis.Mitarbeiter;
import ch.rhb.integra.mitarbeiter.trapeze.domain.IntegraFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ObjectMapper.class})

public class MitarbeiterFilterTest {
  
  @Autowired
  private ObjectMapper objectMapper;
  
  private MitarbeiterFilter filter;
  
  private Mitarbeiter[] mitarbeiter;
  @Before
  public void setUp() {
      try {
        Mitarbeiter mit = objectMapper.readValue(new JSONMitarbeiterResponseSample().getJsonSampleTrapeze(), Mitarbeiter.class);
        mitarbeiter= new Mitarbeiter[1];
        mitarbeiter[0]=mit;
      } catch (IOException e) {
          e.printStackTrace();
          fail();
      }
  }
  
  @Test
  public void testFilterKstnrNotExcludeActive() {
    filter= new MitarbeiterFilter();
    List<Mitarbeiter> mitList= filter.filterMitarbeiter(mitarbeiter, getFilters());
    assertEquals(1,mitList.size());
  }
  
  @Test
  public void testFilterPrsnrExcludeActive() {
    filter= new MitarbeiterFilter();
    mitarbeiter[0].setPersNr(8999);
    List<Mitarbeiter> mitList= filter.filterMitarbeiter(mitarbeiter, getFilters());
    
    assertEquals(0,mitList.size());
  }
  
  @Test
  public void testFilterKstnrNotExcludeNotActive() {
    filter= new MitarbeiterFilter();
    mitarbeiter[0].setKstnr("1111");
    List<Mitarbeiter> mitList= filter.filterMitarbeiter(mitarbeiter, getFilters());
    assertEquals(0,mitList.size());
  }
  
  @Test
  public void testFilterPrsnrExcludeNotActive() {
    filter= new MitarbeiterFilter();
    mitarbeiter[0].setPersNr(9999);
    List<Mitarbeiter> mitList= filter.filterMitarbeiter(mitarbeiter, getFilters());
    
    assertEquals(1,mitList.size());
  }
  
  @Test
  public void testFilterPrsnrIncludeActive() {
    filter= new MitarbeiterFilter();
    mitarbeiter[0].setPersNr(5555);
    List<Mitarbeiter> mitList= filter.filterMitarbeiter(mitarbeiter, getFilters());
    
    assertEquals(1,mitList.size());
  }
  
  @Test
  public void testFilterPrsnrKstnrIncludeActive() {
    filter= new MitarbeiterFilter();
    mitarbeiter[0].setPersNr(5555);
    mitarbeiter[0].setKstnr("1111");
    List<Mitarbeiter> mitList= filter.filterMitarbeiter(mitarbeiter, getFilters());
    
    assertEquals(1,mitList.size());
  }
  
  @Test
  public void testFilterPrsnrKstnrIncludeNotActive() {
    filter= new MitarbeiterFilter();
    mitarbeiter[0].setPersNr(6666);
    mitarbeiter[0].setKstnr("1111");
    List<Mitarbeiter> mitList= filter.filterMitarbeiter(mitarbeiter, getFilters());
    
    assertEquals(0,mitList.size());
  }
  
  @Test
  public void testFilterKstnrNotExist() {
    filter= new MitarbeiterFilter();
    mitarbeiter[0].setKstnr("I don't exist");
    List<Mitarbeiter> mitList= filter.filterMitarbeiter(mitarbeiter, getFilters());
    
    assertEquals(0,mitList.size());
  }
  
  @Test
  public void testFilterKstnrNotExistIncludeActive() {
    filter= new MitarbeiterFilter();
    mitarbeiter[0].setKstnr("I don't exist");
    mitarbeiter[0].setPersNr(5555);
    List<Mitarbeiter> mitList= filter.filterMitarbeiter(mitarbeiter, getFilters());
    
    assertEquals(1,mitList.size());
  }
  
  private List<IntegraFilter> getFilters(){
    List<IntegraFilter> filter=new ArrayList<IntegraFilter>();
    IntegraFilter f=new IntegraFilter("PDS.EMPLOYEEIMPORT","KOSTENSTELLE","6206","J");
    filter.add(f);
    f=new IntegraFilter("PDS.EMPLOYEEIMPORT","KOSTENSTELLE","6213","J");
    filter.add(f);
    f=new IntegraFilter("PDS.EMPLOYEEIMPORT","KOSTENSTELLE","7180","J");
    filter.add(f);
    f=new IntegraFilter("PDS.EMPLOYEEIMPORT","KOSTENSTELLE","6086","J");
    filter.add(f);
    f=new IntegraFilter("PDS.EMPLOYEEIMPORT","KOSTENSTELLE","6120","J");
    filter.add(f);
    f=new IntegraFilter("PDS.EMPLOYEEIMPORT","KOSTENSTELLE","6124","J");
    filter.add(f);
    f=new IntegraFilter("PDS.EMPLOYEEIMPORT","KOSTENSTELLE","5746","J");
    filter.add(f);
    f=new IntegraFilter("PDS.EMPLOYEEIMPORT","KOSTENSTELLE","1111","N");
    filter.add(f);
    f=new IntegraFilter("PDS.EMPLOYEEIMPORT","PERSONALNUMMER_I","5555","J");
    filter.add(f);
    f=new IntegraFilter("PDS.EMPLOYEEIMPORT","PERSONALNUMMER_I","6666","N");
    filter.add(f);
    f=new IntegraFilter("PDS.EMPLOYEEIMPORT","PERSONALNUMMER_E","8999","J");
    filter.add(f);
    f=new IntegraFilter("PDS.EMPLOYEEIMPORT","PERSONALNUMMER_E","9999","N");
    filter.add(f);
    return filter;
  }

}
