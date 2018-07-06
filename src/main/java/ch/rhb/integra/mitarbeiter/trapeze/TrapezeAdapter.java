package ch.rhb.integra.mitarbeiter.trapeze;

import ch.rhb.integra.mitarbeiter.trapeze.domain.EmployeeImport;
import ch.rhb.integra.mitarbeiter.trapeze.repo.TrapezeRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("trapezeAdapter")
@Slf4j
public class TrapezeAdapter {
  
  @Autowired
  private TrapezeRepository trapezeRepository;
  
  public void saveEmployeeImport(List<EmployeeImport> employeeImport) {
    log.info("Saving EmployeeImports in Trapezedb " + employeeImport.size());
    trapezeRepository.save(employeeImport);
    
  }

}
