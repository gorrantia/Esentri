package ch.rhb.integra.mitarbeiter;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
/**
 * 
 * Maps ands sends the Mitarbeiter to MIA. The Mapping filters the Mitarbeiter 
 * @author nfonnegra
 */

@Component
public class RouteTypeAMIA extends RouteBuilder{

  public static final String ROUTE_ID="RouteTypeAMIA";
  public final static String ROUTE_DIRECT_ENDPOINT = "direct:RouteTypeAMIA";

  @Override
  public void configure() throws Exception {
    from(ROUTE_DIRECT_ENDPOINT)
    .routeId(ROUTE_ID)
    .id(ROUTE_ID)
    .log(LoggingLevel.INFO, "Going to transform mia ${property.mitarbeiterModifyVersion.length}")
    // Maps the mitarbeiter to MIA format. Mitarbeiter are also being filtered out
    .to("bean:miaTransformer?method=transform(${property.mitarbeiterModifyVersion})")
    .log(LoggingLevel.INFO, "Transformed and filtered mitarbeiter for MIA ${body.size}")
    .setProperty("miaMitarbeiter", body())
    // Transforms the comm addres changed mitarbeiter. Mitarbeiter are also being filtered out
    .to("bean:miaTransformer?method=transform(${property.commAddress})")
    .setProperty("miaCommAddress", body())
    //sends the mitarbeiter to MIA
    .to("bean:miaAdapter?method=sendMIA(${property.miaMitarbeiter},${property.miaCommAddress})")
    .log(LoggingLevel.INFO, "MIA : ${body}");
  }

}
