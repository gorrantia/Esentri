package ch.rhb.integra.mitarbeiter;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
@Component
public class RouteTypeAAxedo extends RouteBuilder{

  public static final String ROUTE_ID="RouteTypeAAxedo";
  public final static String ROUTE_DIRECT_ENDPOINT = "direct:RouteTypeAAxedo";
  @Override
  public void configure() throws Exception {
    from(ROUTE_DIRECT_ENDPOINT)
    .routeId(ROUTE_ID)
    .id(ROUTE_ID)
    .log(LoggingLevel.INFO, "Going to transform axedo ${property.mitarbeiterModifyVersion.length}")
    // Maps the mitarbeiter to MIA format. Mitarbeiter are also being filtered out
    .to("bean:axedoTransformer?method=transform(${property.mitarbeiterModifyVersion})")
    .setProperty("axedoMitarbeiter", body())
    .to("bean:axedoAdapter?method=saveEmployeeBaseData(${property.axedoMitarbeiter})")
    .log(LoggingLevel.INFO, "Fnish axedo import");
    
  }

}
