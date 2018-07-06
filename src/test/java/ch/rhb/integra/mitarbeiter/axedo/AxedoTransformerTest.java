package ch.rhb.integra.mitarbeiter.axedo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import ch.rhb.integra.mitarbeiter.axedo.domain.EmployeeBaseData;
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

public class AxedoTransformerTest {
  @Autowired
  private ObjectMapper objectMapper;
  
  private Mitarbeiter[] mitarbeiter;
  
  private AxedoTransformer axedoTransformer=null;
  
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
    axedoTransformer=new AxedoTransformer();  
    List<EmployeeBaseData> employees=axedoTransformer.transform(mitarbeiter);
    assertEquals(employees.size(),1);
  }   
  
  @Test
  public void testEmployees() {
    axedoTransformer=new AxedoTransformer();  
    
    List<EmployeeBaseData> employees=axedoTransformer.transform(mitarbeiter);
    EmployeeBaseData emp=employees.get(0);
    assertEquals(new Long(42641),emp.getMaVersionID());
    //8552
    assertEquals("2014-01-01",emp.getEintritt().toString());
    assertEquals(null,emp.getAustritt());
    assertEquals(new Integer(8552).toString(),emp.getPersNR());
    assertEquals("Steiner",emp.getNachname());
    assertEquals("Bergstrasse 2",emp.getAddresse());
    assertEquals("Chur",emp.getArbeitsort());
    assertEquals("N",emp.getInaktiv());
    assertEquals("Finanzen",emp.getAbteilung());
    assertEquals(new Long(30170050),emp.getStrukturCode());
    assertEquals("8959",emp.getVorgesetzer());
  }   
  
  public void testActiveBool() {
    axedoTransformer=new AxedoTransformer();  
    mitarbeiter[0].setActiveBool(0);
    List<EmployeeBaseData> employees=axedoTransformer.transform(mitarbeiter);
    EmployeeBaseData emp=employees.get(0);
    assertNull(emp.getInaktiv());
  
  }   
  
  public void testStringToLong() {
    axedoTransformer=new AxedoTransformer();  
    mitarbeiter[0].setStrukturCode("NoLong");
    List<EmployeeBaseData> employees=axedoTransformer.transform(mitarbeiter);
    EmployeeBaseData emp=employees.get(0);
    assertEquals(new Long(0),emp.getStrukturCode());
  
  }   
}
