package ch.rhb.integra.mitarbeiter.psipenta;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * 
 * XML Properties type 
 * 
 * @author esentri AG, <a href="mailto:guillermo.orrantian@esentri.com">gorrantia</a>
 *
 */
@XmlRootElement(name="Record",namespace="http://www.psipenta.de/WebServiceDataSchema")
public class PsipentaProperties {

  List<PsipentaRecordEntry> entries = new ArrayList<PsipentaRecordEntry>();
  
  public PsipentaProperties() {
  }
  
  public void addEntry(String name, String value, String attr){
    addEntry(new PsipentaRecordEntry(name, value, attr));
  }
  
  public void addEntry(PsipentaRecordEntry entry){
    entries.add(entry);
}
  
  public String searchProperty(String name) {
    return entries.stream().filter(x -> x.getAttr() == name).findFirst().get().getValue();
  }
  
  public String propsToString() {
    StringBuilder output = new StringBuilder();
    entries.forEach(x -> output.append(x.toString()+"\n") );
    return output.toString();
  }
  
  @Override
  public String toString() {
    return "<Record>\n"+propsToString()+
  "\n</Record>";
  }
}
