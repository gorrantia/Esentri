package ch.rhb.integra.mitarbeiter.trapeze;

import ch.rhb.integra.mitarbeiter.performis.Address;
import ch.rhb.integra.mitarbeiter.performis.Mitarbeiter;
import ch.rhb.integra.mitarbeiter.trapeze.domain.EmployeeImport;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


@Slf4j
@Service("trapezeTransformer")
public class TrapezeTransformer {
  
  
  private final static String DEFAULT_USERNAME="Middleware";
  
  public List<EmployeeImport> transform(List<Mitarbeiter> mitarbeiter){
    List<EmployeeImport> employees= new ArrayList<EmployeeImport>();
    for(Mitarbeiter mit:mitarbeiter) {
      EmployeeImport imp= new EmployeeImport();
      imp.setEmp(mit.getPersNr());
      imp.setFromDate(transformString(mit.getVersionGueltigAb()));
      if(mit.getActiveBool() != 1) {
        imp.setToDate(getCurrentDate());
      }else {
        imp.setToDate(generateFutureDate());
      }
      imp.setGender(StringUtils.truncate(mit.getGeschlecht(),10));
      imp.setTitle(StringUtils.truncate(obtainTitel(mit),40));
      imp.setFristName(StringUtils.truncate(mit.getVorname(),40));
      imp.setLastName(StringUtils.truncate(mit.getNachname(),40));
      imp.setAddress(StringUtils.truncate(mit.getStrasse() + " " + mit.getHausNummer(),50));
      imp.setCountryCode(StringUtils.truncate(mit.getLand(),4));
      imp.setZipCode(StringUtils.truncate(mit.getPlz(),10));
      imp.setCity(StringUtils.truncate(mit.getOrt(),40));
      imp.setAddress2(StringUtils.truncate(mit.getPostFach(),50));
      imp.setHireDate(transformString(mit.getEintritt()));
      if(mit.getPersonal()!=null) {
        imp.setSeniorityDate(transformString(mit.getPersonal().getTheorieEintritt()));
      }
      imp.setResignationDate(transformString(mit.getAustritt()));
      imp.setAccount(StringUtils.truncate(mit.getKstnr(),6));
      imp.setBirthDate(transformString(mit.getGeburstDatum()));
      imp.setWeeklyWorkHours(convertString(mit.getBeschaeftigungsGrad()));
      imp.setUserDefined1(StringUtils.truncate(buildVorgesetzer(mit),255));
      imp.setWorkplace(StringUtils.truncate(getWorkplace(mit),50));
      imp.setUserName(DEFAULT_USERNAME);
      
      imp.setImportedDate(generateDefaultDate());
      employees.add(imp);
    }
    return employees;
  }
  
  private Date getCurrentDate() {
    return new Date((new java.util.Date()).getTime());
  }
  
  private String getWorkplace(Mitarbeiter mit) {
    String workplace="";
    if(mit.getArbeitsortAddress()!=null && mit.getArbeitsortAddress().length>0) {
      workplace=mit.getArbeitsortAddress()[0].getCity();
    }
    return workplace;
  }
  
  private String buildVorgesetzer(Mitarbeiter mit) {
    String vorgesetzer="";
    if(mit.getPersnrVorgesetzterVersion()!=null) {
      vorgesetzer=mit.getVorgesetzerNachname() + " " +mit.getVorgesetzerVorname() +" (" +transformDouble(mit.getPersnrVorgesetzterVersion())+")";
    }
    return vorgesetzer;
  }
  
  private String transformDouble(String number) {
    Double doub=null;
    try {
      doub=new Double(number);
      int value=doub.intValue();
      return new Integer(value).toString();
    }catch(NumberFormatException e) {
      return number;
    }
  }
  
  private String obtainTitel(Mitarbeiter mit) {
    String titel="";
    if(mit.getMitarbeiterAddress()!=null && mit.getMitarbeiterAddress().length>0  ) {
      Address addr=mit.getMitarbeiterAddress()[0];
      if(addr.getPartner()!=null && addr.getPartner().length>0) {
        titel=addr.getPartner()[0].getTitle();
      }
    }
    return titel;
  }
  
  private Date transformString(String stringDate) {
    Date sqlDate= null;
    if(stringDate==null || stringDate.isEmpty()) {
      return sqlDate;
    }
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    try {
      java.util.Date date = sdf1.parse(stringDate);
      sqlDate=new Date(date.getTime());
    } catch (ParseException e) {
      log.warn("Could not cast " +stringDate+ " into SQL Date." , e.getMessage());
      throw new RuntimeException(e);
    }
    return sqlDate;
  }
  
  private Date generateDefaultDate() {
    Calendar now= Calendar.getInstance();
    now.set(Calendar.YEAR,1600);
    now.set(Calendar.MONTH,Calendar.JANUARY);
    now.set(Calendar.DAY_OF_MONTH,1);
    now.set(Calendar.HOUR, 0);
    now.set(Calendar.MINUTE, 0);
    now.set(Calendar.SECOND, 0);
    return new Date(now.getTimeInMillis());
  }
  
  private Date generateFutureDate() {
    Calendar now= Calendar.getInstance();
    now.set(Calendar.YEAR,3999);
    now.set(Calendar.MONTH,Calendar.DECEMBER);
    now.set(Calendar.DAY_OF_MONTH,31);
    now.set(Calendar.HOUR, 0);
    now.set(Calendar.MINUTE, 0);
    now.set(Calendar.SECOND, 0);
    return new Date(now.getTimeInMillis());
  }
  
  private Long convertString(String value) {
    Long response=new Long(0);
    if(value == null || value.isEmpty()) {
      return response;
    }
    try {
      response=Long.parseLong(value);
    }catch(NumberFormatException ex) {
      log.warn("Could not cast " +value+ " into Long. 0 will be returned", ex.getMessage());
    }
    return response;
  }

}
