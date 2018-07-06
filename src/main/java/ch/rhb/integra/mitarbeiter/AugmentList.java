package ch.rhb.integra.mitarbeiter;

import ch.rhb.integra.mitarbeiter.performis.Mitarbeiter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.camel.Body;
import org.apache.camel.Property;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
/**
 * Bean to merge two lists of mitarbeiter objects and make a list of unique mitarbeiter objects.s
 * 
 * 
 * @author esentri AG, <a href="mailto:guillermo.orrantian@esentri.com">Guillermo Orrantia</a>
 *
 */
@Slf4j
@Service("AugmentList")
public class AugmentList {

  public List<Mitarbeiter> checkVersionGultig(@Property("versionGueltigkeit") Mitarbeiter[] versionGueltig, 
      @Body Mitarbeiter[] eintritt)
  {  
    log.debug("Received {} for Versionsgueltigkeit and {} for Eintrittsdatum",versionGueltig.length,eintritt.length);
    
    if (eintritt.length == 0)
      return Arrays.asList(versionGueltig);
    
    List<Mitarbeiter> eintritts = Arrays.asList(eintritt);
    List<Mitarbeiter> kk = Arrays.asList(versionGueltig);
    
    List<Mitarbeiter> result = eintritts.stream().filter(x -> kk.contains(x)).collect(Collectors.toList());

    log.debug("Augmented list contains {} elements",result.size());
    return result;
  }
    
}
