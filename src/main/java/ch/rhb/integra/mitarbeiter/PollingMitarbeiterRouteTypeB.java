package ch.rhb.integra.mitarbeiter;

import ch.rhb.integra.VersionInfo;
import ch.rhb.integra.config.VarianteBProperties;
import com.esentri.integration.common.DefaultExceptionProcessor;
import com.esentri.integration.common.DefaultResponse;
import com.fasterxml.jackson.core.JsonParseException;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Main Camel Route to handle Variate B
 * 
 * 
 * @author <a href="mailto:guillermo.orrantia@esentri.com">Guillermo Orrantia</a> *
 */
@Component
@Slf4j
public class PollingMitarbeiterRouteTypeB extends RouteBuilder{

  public final static String ROUTE_DIRECT_ENDPOINT = "direct:pollingRouteTypeB";
  
  public static final String ROUTE_ID="RouteTypeB";
   
  @Value("${application.root-context-path}")
  private String rootContextPath;

  @Value("${application.api-context-path}")
  private String apiContextPath; 
  
  @Autowired
  private VarianteBProperties varianteBProperties;
  
  @Override
  public void configure() throws Exception {
    log.debug("Configure {}...", ROUTE_DIRECT_ENDPOINT);
    VersionInfo info = new VersionInfo();
    
    log.trace("Define error handling...");
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

   log.trace("Define the REST interface with Rest DSL...");
    
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

    rest("/v1").consumes("application/json").produces("application/json")
    .get("/polling_mitarbeiter/typeb?lastExecution={lastExecution}")
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
       .to(ROUTE_DIRECT_ENDPOINT);
    
    log.trace("Defines timer based activation for {}.", ROUTE_DIRECT_ENDPOINT);
    from(varianteBProperties.getExecutionString()).routeId("VarianteB-Sync")
        .log(LoggingLevel.INFO, ROUTE_ID + " timer was activated.")
        .to("bean:VarianteBLastExecutionService?method=getLastExecutionTime()")
        .setProperty("lastExecution", body())
        .to(ROUTE_DIRECT_ENDPOINT)
        .to("bean:VarianteBLastExecutionService?method=updateToNow()")
    ;
 
 // Rest route
    from(ROUTE_DIRECT_ENDPOINT).routeId(ROUTE_ID).id(ROUTE_ID)
    .setProperty("uuid", simple(getContext().getUuidGenerator().generateUuid()))
    .log(LoggingLevel.INFO,"Calling Performis Adapter VersionGueltigkeits")
    .to("bean:mitarbeiterPerformisAdapter?method=searchMitarbeiterByVersionGueltig(${headers.lastExecution})").id("Performis-VersionGueltig")
    .validate(body().isNotNull())
    .setProperty("versionGueltigkeit", body())
    .setBody().constant(DefaultResponse.createSuccessResponse("Mitarbeiter version availability processed successfully."))
    .log(LoggingLevel.INFO,"Calling Performis Adapter EintrittsDatum function")
    .to("bean:mitarbeiterPerformisAdapter?method=searchEintrittsDatum(${headers.lastExecution})").id("Performis-VersionGueltig")
    .setBody().constant(DefaultResponse.createSuccessResponse("Mitarbeiter data processed successfully."))
    .bean("AugmentList")
    .log(LoggingLevel.INFO,"Calling Downstream systems PSIPenta and Kaba WebService")
    .multicast().to("KabaSoapAdapter","PsiPentaAdapter")
    ;
  }
  
  

}
