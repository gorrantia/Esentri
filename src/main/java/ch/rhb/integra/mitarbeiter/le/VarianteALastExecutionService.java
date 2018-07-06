package ch.rhb.integra.mitarbeiter.le;



import ch.rhb.integra.configmap.write.json.Data;
import org.springframework.stereotype.Service;

@Service("VarianteALastExecutionService")
public class VarianteALastExecutionService extends LastExecutionService {
  private static final String MAP_NAME="variantea-lastexecution";
  @Override
  public String getMapName() {
    return MAP_NAME;
  }
  @Override
  public Data generateData(String value) {
    Data data=new Data();
    data.setVarianteaLastexecutionProperties(value);
    return data;
  }

}
