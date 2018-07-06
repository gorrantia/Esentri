package ch.rhb.integra.mitarbeiter.axedo;

import ch.rhb.integra.mitarbeiter.axedo.domain.EmployeeBaseData;
import ch.rhb.integra.mitarbeiter.axedo.repo.AxedoRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("axedoAdapter")
@Slf4j
public class AxedoAdapter {

  @Autowired
  private AxedoRepository axedoRepository;

  
  public void saveEmployeeBaseData(List<EmployeeBaseData> mitarbeitern) {
    log.info("Saving mitarbieter in axedo " + mitarbeitern.size());
      axedoRepository.save(mitarbeitern);
  }
  
 

}
