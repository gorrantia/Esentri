package ch.rhb.integra.mitarbeiter.trapeze;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import ch.rhb.integra.mitarbeiter.performis.JSONMitarbeiterResponseSample;
import ch.rhb.integra.mitarbeiter.performis.Mitarbeiter;
import ch.rhb.integra.mitarbeiter.trapeze.TrapezeTransformer;
import ch.rhb.integra.mitarbeiter.trapeze.domain.EmployeeImport;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.sql.Date;
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

public class TrapezeTransformerTest {
  @Autowired
  private ObjectMapper objectMapper;
  
  private TrapezeTransformer trapezeTransformer;
  
  private List<Mitarbeiter> mitarbeiter;
  @Before
  public void setUp() {
      try {
        Mitarbeiter mit = objectMapper.readValue(new JSONMitarbeiterResponseSample().getJsonSampleTrapeze(), Mitarbeiter.class);
        mitarbeiter= new ArrayList<>();
        mitarbeiter.add(mit);
      } catch (IOException e) {
          e.printStackTrace();
          fail();
      }
  }
  
  @Test
  public void testBasicMapping() {
    trapezeTransformer=new TrapezeTransformer();  
    List<EmployeeImport> employees=trapezeTransformer.transform(mitarbeiter);
    assertEquals(employees.size(),1);
  }   
  
  @Test
  public void testEmployeesMapping() {
    trapezeTransformer=new TrapezeTransformer();  
    List<EmployeeImport> employees=trapezeTransformer.transform(mitarbeiter);
    EmployeeImport emp=employees.get(0);
    assertEquals(new Long(8929),emp.getEmp());
    assertEquals("2018-07-01",emp.getFromDate().toString());
    assertEquals("3999-12-31",emp.getToDate().toString());
    assertEquals("M",emp.getGender());
    assertNull(emp.getTitle());
    assertEquals("Roland",emp.getFristName());
    assertEquals("Kenel",emp.getLastName());
    assertEquals("Hummelweg 28",emp.getAddress());
    assertEquals("8730",emp.getZipCode());
    assertNull(emp.getAddress2());
    assertEquals("Uznach",emp.getCity());
    assertEquals("6086",emp.getAccount());
    assertEquals("Bass Andreas (7681)",emp.getUserDefined1());
    assertEquals("Chur",emp.getWorkplace());
    assertEquals("1600-01-01",emp.getImportedDate().toString());
  }   
  
  public void testToDateMapping() {
    trapezeTransformer=new TrapezeTransformer();  
    mitarbeiter.get(0).setActiveBool(0);
    List<EmployeeImport> employees=trapezeTransformer.transform(mitarbeiter);
    EmployeeImport emp=employees.get(0);
    assertEquals(getCurrentDate(),emp.getToDate());
    
  }   
  
  private Date getCurrentDate() {
    return new Date((new java.util.Date()).getTime());
  }
  
  
}
