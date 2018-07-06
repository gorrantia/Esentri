package ch.rhb.integra.mitarbeiter.swisspension;

import ch.rhb.integra.mitarbeiter.performis.Mitarbeiter;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.camel.Handler;
import org.springframework.stereotype.Service;
/**
 * Generate a CSV file for the Swiss Pension transfer
 * 
 * 
 * @author esentri AG, <a href="mailto:guillermo.orrantian@esentri.com">Guillermo Orrantia</a>
 *
 */
@Service("CreateSwissPensionFile")
public class createSwissPension {
  
  private Map<Integer,Integer> zivilStand = new HashMap<Integer,Integer>();
  private FileWriter csvFile;
  private List<SwissPensionRecord> records = new ArrayList<SwissPensionRecord>();
  
  final String[] CSV_HEADER = { "Record-Art", "Periode", "VonDatum", "LohnNr","Andere",
      "Name","VorName","GebDatum","Geschlecht","Sprache","AHVID","Strasse",
      "Zusatz","Postfach","PLZ","Ort","Lnad","E-Mail","Zivilstand","Zivilstandsdatum",
      "Nation","Eintritt","Austritt","AustrittGrund","AGID","BeschGrad",
      "AHVLohn","E_Name","E_Vorname","E_Geschlecht","E_GebDatum","E_AHVID"};

  
  public createSwissPension() {
    zivilStand.put(new Integer(12330001), new Integer(0)); //unbekannt (Standard)
    zivilStand.put(new Integer(12330002), new Integer(1)); // ledig
    zivilStand.put(new Integer(12330003), new Integer(2)); // verheiratet
    zivilStand.put(new Integer(12330004), new Integer(3)); // geschieden
    zivilStand.put(new Integer(12330005), new Integer(4)); // verwitwet
    zivilStand.put(new Integer(12330006), new Integer(5)); // getrent
    zivilStand.put(new Integer(12330007), new Integer(12)); // eingetragene Partnerschaft
    
     try {
      csvFile = new FileWriter("customer.csv");
    } catch (IOException e) {
      e.printStackTrace();
    }
     
 
  }

  @Handler
  public void doCsvFile(List<Mitarbeiter> lista) throws Exception{
    
    ColumnPositionMappingStrategy<SwissPensionRecord> mappingStrategy = 
        new ColumnPositionMappingStrategy<SwissPensionRecord>();
 
    mappingStrategy.setType(SwissPensionRecord.class);
    mappingStrategy.setColumnMapping(CSV_HEADER);
    
    lista.forEach(x -> doRecord(x));
    
    StatefulBeanToCsv<SwissPensionRecord> beanToCsv = new StatefulBeanToCsvBuilder<SwissPensionRecord>(csvFile)
        .withMappingStrategy(mappingStrategy)
        .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
        .build();
 
    beanToCsv.write(records);
  }
  
  private void doRecord(Mitarbeiter m) {
    SwissPensionRecord record = new SwissPensionRecord();
    
    record.setRecordArt(1);
    record.setVonDatum(LocalDate.now().withDayOfMonth(1));
    record.setLohnNr(m.getPersNr());
    record.setName(m.getNachname());
    record.setVorname(m.getVorname());
    record.setGebDatum(stringToLocalDate(m.getGeburstDatum()));
    record.setGeschlecht(m.getGeschlecht().charAt(0));
    record.setSprache(new Character('D'));
    record.setAhvid(m.getSozialVersicherungsNummer());
    record.setStrasse(m.getStrasse()+" "+m.getHausNummer());
    record.setPostfach(m.getPostFach());
    record.setOrt(m.getOrt());
    record.setPlz(m.getPlz());
    record.setLand(m.getLand());
    record.setEmail(m.getEmail());
    record.setZivilstand(zivilStand.get(m.getPersonal().getZivilstand()));
    record.setZivilstandDatum(stringToLocalDate(m.getPersonal().getZivilstandDatumVon()));
    record.setNation(m.getPersonal().getNationalitaetKurz());
    record.setEintritt(stringToLocalDate(m.getEintritt()));
    record.setAustritt(stringToLocalDate(m.getAustritt()));
    record.setBeschGrad(Integer.valueOf(m.getBeschaeftigungsGrad()));
    
    records.add(record);
  }
  
  private LocalDate stringToLocalDate(String datum) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD HH:mm:ss");
    return LocalDate.parse(datum, formatter);
  }
    
}
