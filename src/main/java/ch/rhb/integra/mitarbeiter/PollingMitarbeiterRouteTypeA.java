package ch.rhb.integra.mitarbeiter;


import ch.rhb.integra.VersionInfo;
import ch.rhb.integra.config.VarianteAProperties;
import com.esentri.integration.common.DefaultExceptionProcessor;
import com.esentri.integration.common.DefaultResponse;
import com.fasterxml.jackson.core.JsonParseException;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PollingMitarbeiterRouteTypeA extends RouteBuilder{
  private final Logger logger = LoggerFactory.getLogger(PollingMitarbeiterRouteTypeA.class);
  /**
   * The endpoint definition when using a synchronous invocation.
   */
  public final static String ROUTE_DIRECT_ENDPOINT = "direct:pollingMitarbeiterRouteTypeA";
  
  public static final String ROUTE_ID="Main-Request-MitarbeiterRouteTypeA";
  public static final String SUB_ROUTE_ID="Sub-Request-MitarbeiterRouteTypeA";
  
  @Value("${application.root-context-path}")
  private String rootContextPath;

  @Value("${application.api-context-path}")
  private String apiContextPath;
  @Autowired
  private VarianteAProperties varianteAProperties;
  
  @Override
  public void configure() throws Exception {
    logger.debug("Configure {}...", ROUTE_DIRECT_ENDPOINT);
    VersionInfo info = new VersionInfo();
    /*JaxbDataFormat jaxb = JaxbDataFormatConfigurator
               .configureJaxbDataFormatWithCDataForStrings("ch.rhb.integra.paymentinfo.alusta");  */
    logger.trace("Define error handling...");
    onException(JsonParseException.class)
      .handled(true)
      .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
      .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
      .setBody().constant("Couldn't parse request data, because of invalid json data.");

    onException(ClientAbortException.class)
      .handled(true)
      .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
      .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
      .setBody().constant("Timeout reached for REST interface.");

    onException(Exception.class)
      .handled(true)
      .process(new DefaultExceptionProcessor());

   logger.trace("Define the REST interface with Rest DSL...");
    
   JacksonDataFormat format = new JacksonDataFormat();
    format.setInclude("NON_NULL");
    restConfiguration()
    .bindingMode(RestBindingMode.json)
    .skipBindingOnErrorCode(true)
    .dataFormatProperty("json.in.disableFeatures", "FAIL_ON_UNKNOWN_PROPERTIES")
    .dataFormatProperty("prettyPrint", "true")
    .enableCORS(true)
    .apiProperty("host", "") 
    .apiProperty("schemes", "" )
    .contextPath(rootContextPath)
    .apiContextPath(apiContextPath)
    .apiContextPath("api-doc")
    .apiProperty("api.version", info.getVersionNumberString())
    .apiProperty("api.title", info.getTitle());

    rest("/v1")
    .consumes("application/json")
    .produces("application/json")
    .get("/polling_mitarbeiter/typea?lastExecution={lastExecution}")
       .description("Synchronize mitarbeiter from Performis.")
       .outType(DefaultResponse.class)
       .responseMessage()
           .code(200).message("Information successfully synchronized with the end systems.")
       .endResponseMessage()
       .responseMessage()
           .code(400).message("Received an invalid JSON object.")
       .endResponseMessage()
       .responseMessage()
           .code(500).message("Mitarbeiter data of Performis could not be synchronized with the end systems.")
       .endResponseMessage()
       .to("direct:restPollingMitarbeiterRouteTypeA");

    // Route for timer
    from(varianteAProperties.getExecutionString())
    .to("bean:VarianteALastExecutionService?method=getLastExecutionTime()")
    .setProperty("lastExecution", body())
    .to("direct:restPollingMitarbeiterRouteTypeA")
    .to("bean:VarianteALastExecutionService?method=updateToNow()");
    
    
    
    // Rest route
    from("direct:restPollingMitarbeiterRouteTypeA")
    .routeId(ROUTE_ID)
    .id(ROUTE_ID)
    .setProperty("lastExecution",simple("${headers.lastExecution}"))
    .to(ROUTE_DIRECT_ENDPOINT)
     .setBody().constant(DefaultResponse.createSuccessResponse("Mitarbeiter data processed successfully."));
    
    //main route
    from(ROUTE_DIRECT_ENDPOINT)
    .routeId(SUB_ROUTE_ID)
    .id(SUB_ROUTE_ID)
    .setProperty("uuid", simple(getContext().getUuidGenerator().generateUuid())) 
    .log(LoggingLevel.INFO, "Searching Mitarbeiter for modify date ${property.lastExecution}")
    .to("bean:mitarbeiterPerformisAdapter?method=searchMitarbeiterByModifyVersion(${property.lastExecution})")
    .log(LoggingLevel.INFO, "Found Mitarbeiter By Version : ${body}")
    .setProperty("mitarbeiterModifyVersion", body())
    .to("bean:mitarbeiterPerformisAdapter?method=searchMitarbeiterByCommAddressModifyDate(${property.lastExecution})")
    .setProperty("commAddress", body())
    .log(LoggingLevel.INFO, "Found Mitarbeiter By Comm Address: ${body.length}")
    

    .to(RouteTypeATrapeze.ROUTE_DIRECT_ENDPOINT)
    .to(RouteTypeAMIA.ROUTE_DIRECT_ENDPOINT)
    .to(RouteTypeAAxedo.ROUTE_DIRECT_ENDPOINT)
    ;
  }

}
