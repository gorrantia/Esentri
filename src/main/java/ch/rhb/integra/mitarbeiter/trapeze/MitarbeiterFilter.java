package ch.rhb.integra.mitarbeiter.trapeze;

import ch.rhb.integra.mitarbeiter.performis.Mitarbeiter;
import ch.rhb.integra.mitarbeiter.trapeze.domain.IntegraFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("trapezeMitarbeiterFilter")
public class MitarbeiterFilter {
  
  private static final String PERSONEN_INCLUDE_FILTER="PERSONALNUMMER_I";
  private static final String PERSONEN_EXCLUDE_FILTER="PERSONALNUMMER_E";
  private static final String KOSTENSTELLE_FILTER="KOSTENSTELLE";
  
  public List<Mitarbeiter> filterMitarbeiter(Mitarbeiter[] mitarbeiter,List<IntegraFilter> filter){
    List<Mitarbeiter> filteredMitarbeiter= new ArrayList<>();
    Map<String,List<IntegraFilter>> filterMap=obtainFilters(filter);
    for(Mitarbeiter mit: mitarbeiter) {
      if(evaluateInclude(new Long(mit.getPersNr()).toString(),filterMap.get(PERSONEN_INCLUDE_FILTER))) {
        filteredMitarbeiter.add(mit);
      } else {
        if(!evaluateInclude(new Long(mit.getPersNr()).toString(),filterMap.get(PERSONEN_EXCLUDE_FILTER)) && 
            evaluateInclude(mit.getKstnr(),filterMap.get(KOSTENSTELLE_FILTER))) {
          filteredMitarbeiter.add(mit);
        }
      }
    }
    return filteredMitarbeiter;
  }
  
  private boolean evaluateInclude(String value,List<IntegraFilter> filter) {
    
    for(IntegraFilter f:filter) {
      if(f.getIdentifierID().equals(value) && f.getAktiv().equals("J") ) {
        return true;
      }
    }
    return false;
  }
  
 
  
  private  Map<String,List<IntegraFilter>> obtainFilters(List<IntegraFilter> filter){
    Map<String,List<IntegraFilter>> filterMap=new HashMap<>();
    List<IntegraFilter> personInclude= new ArrayList<>();
    List<IntegraFilter> personExclude= new ArrayList<>();
    List<IntegraFilter> kostenstelle= new ArrayList<>();
    for(IntegraFilter f:filter) {
      if(f.getIdentifierName().equals(PERSONEN_INCLUDE_FILTER)) {
        personInclude.add(f);
      }
      if(f.getIdentifierName().equals(PERSONEN_EXCLUDE_FILTER)) {
        personExclude.add(f);
      }
      if(f.getIdentifierName().equals(KOSTENSTELLE_FILTER)) {
        kostenstelle.add(f);
      }
    }
    filterMap.put(KOSTENSTELLE_FILTER, kostenstelle);
    filterMap.put(PERSONEN_EXCLUDE_FILTER, personExclude);
    filterMap.put(PERSONEN_INCLUDE_FILTER, personInclude);
    return filterMap;
  }

  
}
