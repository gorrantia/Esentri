package ch.rhb.integra.mitarbeiter.axedo;

import ch.rhb.integra.mitarbeiter.axedo.domain.EmployeeBaseData;
import ch.rhb.integra.mitarbeiter.performis.Mitarbeiter;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
@Service("axedoTransformer")
@Slf4j
public class AxedoTransformer {
  
  public List<EmployeeBaseData> transform(Mitarbeiter[] mitarbeitern){
    List<EmployeeBaseData> employees=new ArrayList<>();
    NumberFormat nf = NumberFormat.getIntegerInstance();
    nf.setMaximumFractionDigits(0);
    
    for(Mitarbeiter mit:mitarbeitern) {
      EmployeeBaseData employee = new EmployeeBaseData();
      employee.setMaVersionID(mit.getMaVersionID());
      employee.setEintritt(transformString(mit.getEintritt()));
      employee.setAustritt(transformString(mit.getAustritt()));
      employee.setGueltigPerDatum(transformString(mit.getVersionGueltigAb()));
      employee.setPersNR(StringUtils.truncate(new Long(mit.getPersNr()).toString(),10));
      employee.setNachname(StringUtils.truncate(mit.getNachname(),32));
      employee.setVorname(StringUtils.truncate(mit.getVorname(),32));
      employee.setGeburtsdatum(transformString(mit.getGeburstDatum()));
      employee.setAddresse(mit.getStrasse()+" "+mit.getHausNummer());
      employee.setWohnort(StringUtils.truncate(mit.getOrt(),40));
      employee.setPlz(StringUtils.truncate(mit.getPlz(),10));
      employee.setGeschlecht(mit.getGeschlecht());
      employee.setKstnr(StringUtils.truncate(mit.getKstnr(),4000));
      
      try {
        employee.setVorgesetzer(nf.parse(mit.getPersnrVorgesetzterVersion()).toString());
      } catch (ParseException e) {
        log.error("Unable to parse to integer {}",e.getMessage());
      }
      employee.setArbeitsort(getArbeitsort(mit));
      employee.setBeschaeftigungsgrad(convertString(mit.getBeschaeftigungsGrad()));
      employee.setStrukturCode(convertString(mit.getStrukturCode()));
      employee.setInaktiv(transformActiveBool(mit.getActiveBool()));
      if(mit.getPersonal()!=null) {
        if(mit.getPersonal().getTeilPensionierungsGrad()!=null) {
          employee.setTeilPensionierungsGrad(StringUtils.truncate(mit.getPersonal().getTeilPensionierungsGrad().toString(),40));
        }
        employee.setMaKatogorie(StringUtils.truncate(mit.getPersonal().getMitarbeiterKategorie(),40));
        employee.setMaKategorieCode(StringUtils.truncate(new Long(mit.getPersonal().getMitarbeiterKategorieCode()).toString(),40));
        employee.setLohnartCode(mit.getPersonal().getLohnArtCode().toString());
      }
      if(mit.getOrganisation() !=null && mit.getOrganisation().getBezeichnung() != null) {
        employee.setAbteilung(StringUtils.truncate(mit.getOrganisation().getBezeichnung(),40));
      }
      employee.setStatus(0L);
      employees.add(employee);
    }
    return employees;
  }
  
 
  private String transformActiveBool(long activeBool) {
    String res = "J";
    if(activeBool == 1) {
      res = "N";
    }
    return res;
  }
  
  private String getArbeitsort(Mitarbeiter mit) {
    String result = "";
    if(mit.getArbeitsortAddress()==null || mit.getArbeitsortAddress().length<1) {
      return result;
    }
    result=mit.getArbeitsortAddress()[0].getCity();
    return StringUtils.truncate(result,40);
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
}
