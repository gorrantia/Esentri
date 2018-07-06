package ch.rhb.integra.common;

import java.net.URI;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Common general Utilities
 * 
 * @author esentri AG, <a href="mailto:guillermo.orrantian@esentri.com">Guillermo Orrantia</a>
 *
 */
public class Utilities {

  
  /**
   * Construct the Windream docuemnt service URI from the properties file
   * 
   * @return URI
   */
  public static URI buildEndpointUri(String endpoint) {

    UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(endpoint);
    UriComponents uriComponents = uriBuilder.build(true);

    return uriComponents.toUri();
  }

}
