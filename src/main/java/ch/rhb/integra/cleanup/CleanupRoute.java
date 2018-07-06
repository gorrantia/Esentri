package ch.rhb.integra.cleanup;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;


/**
 * A simple route used to cleanup resources in regular intervals.
 * 
 * @author esentri AG, <a href="mailto:markus.lohn@esentri.com">mlohn</a>
 */
@Component
@Validated
@Slf4j
public class CleanupRoute extends RouteBuilder {

  /**
   * The endpoint definition when using a synchronous invocation.
   */
  public static final String ROUTE_DIRECT_ENDPOINT = "direct:cleanup";
  /**
   * The unique identifier of this route.
   */
  public static final String ROUTE_ID = "cleanup";

  @Value("${cleanup.synch.executionString}")
  @NotBlank
  private String executionString;

  @Value("${archive.directory}")
  @NotBlank
  private String archiveDirectory;

  @Value("${archive.retentionPeriod}")
  @NotBlank
  private Integer retentionPeriod;

  @Override
  public void configure() throws Exception {
    log.debug("Configure {}...", ROUTE_DIRECT_ENDPOINT);
    log.trace("Defines timer based activation for {}.", ROUTE_DIRECT_ENDPOINT);
    from(executionString).to(ROUTE_DIRECT_ENDPOINT);

    log.trace("Define {}...", ROUTE_DIRECT_ENDPOINT);
    from(ROUTE_DIRECT_ENDPOINT).routeId(ROUTE_ID).process(new Processor() {

      @Override
      public void process(Exchange exchange) throws Exception {
        MDC.put("routeId", ROUTE_ID);
      }

    }).log(LoggingLevel.INFO, "Cleanup resources started.")
        .setProperty("archiveDirectory", simple(archiveDirectory))
        .setProperty("retentionPeriod", simple(retentionPeriod.toString()))
        .process(new FilesCleanupProcessor());
  }

}
