package ch.rhb.integra.mitarbeiter.trapeze;


import ch.rhb.integra.mitarbeiter.trapeze.domain.IntegraFilter;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("trapezeFilterAdapter")
@Slf4j
public class TrapezeFilterAdapter {
  
  public List<IntegraFilter> getFilters(){
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
    f=new IntegraFilter("PDS.EMPLOYEEIMPORT","PERSONALNUMMER_I","","");
    filter.add(f);
    f=new IntegraFilter("PDS.EMPLOYEEIMPORT","PERSONALNUMMER_I","","");
    filter.add(f);
    return filter;
  }

}
