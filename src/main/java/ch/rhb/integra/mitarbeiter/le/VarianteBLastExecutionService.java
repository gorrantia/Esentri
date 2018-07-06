package ch.rhb.integra.mitarbeiter.le;

import ch.rhb.integra.configmap.write.json.Data;
import org.springframework.stereotype.Service;
/**
 * Bean to update the last execution string from quartz execution
 * 
 * 
 * @author <a href="mailto:guillermo.orrantia@esentri.com">Guillermo Orrantia</a> *
 *
 */
@Service("VarianteBLastExecutionService")
public class VarianteBLastExecutionService extends LastExecutionService {

  private static final String MAP_NAME="varianteb-lastexecution";
  @Override
  public String getMapName() {
    return MAP_NAME;
  }
  
  public Data generateData(String value) {
    Data data=new Data();
    data.setVariantebLastexecutionProperties(value);
    return data;
  }

}
