package ch.rhb.integra.mitarbeiter;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Sends, maps and saves the mitarbeiter in the trapeze DB. Mitarbeiter will be filtered before mapping and saving them
 * @author nfonnegra
 *
 */
@Component
public class RouteTypeATrapeze extends RouteBuilder{

  public static final String ROUTE_ID="RouteTypeATrapeze";
  public final static String ROUTE_DIRECT_ENDPOINT = "direct:RouteTypeATrapeze";
  @Override
  public void configure() throws Exception {
    from(ROUTE_DIRECT_ENDPOINT)
    .routeId(ROUTE_ID)
    .id(ROUTE_ID)
    // Obtain the filters from the DB
    .to("bean:trapezeFilterAdapter?method=getFilters()")
    .log(LoggingLevel.INFO, "Obtained filters : ${body.size}")
    .setProperty("trapezeFilters", body())
    // Filter the list of mitarbeiter
    .to("bean:trapezeMitarbeiterFilter?method=filterMitarbeiter(${property.mitarbeiterModifyVersion},${property.trapezeFilters})")
    .log(LoggingLevel.INFO, "Filtered Benutzer : ${body.size}")
    .setProperty("filteredMitarbeiter", body())
    // Map the Mitarbeiter into the Trapeze format
    .to("bean:trapezeTransformer?method=transform(${property.filteredMitarbeiter})")
    .log(LoggingLevel.INFO, "Transformed Benutzer : ${body.size}")
    // save mitarbeiter to trapeze
    .to("bean:trapezeAdapter?method=saveEmployeeImport(${body})") ;
    
    
  }

}
