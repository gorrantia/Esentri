package ch.rhb.integra.mitarbeiter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import ch.rhb.integra.Application;
import ch.rhb.integra.mitarbeiter.performis.JSONMitarbeiterResponseSample;
import ch.rhb.integra.mitarbeiter.performis.Mitarbeiter;
import com.esentri.integration.common.DefaultResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

//@DirtiesContext
//@RunWith(CamelSpringBootRunner.class)
//@SpringBootTest(classes = { Application.class }, properties = {
//        "camel.springboot.java-routes-include-pattern=**/PollingMitarbeiterRouteTypeA*" })
//@MockEndpointsAndSkip("[s]+ftp.*|file.*|direct.*backup") 
public class PollingMitarbeiterRouteTypeATest {
  /*
  @Autowired
  private CamelContext context;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private ProducerTemplate template;

  @Before
  public void setUp() throws Exception {
      //AccountPayableResponse resp=objectMapper.readValue(new JSONResponseSample().getJsonSample(), AccountPayableResponse.class);
     Mitarbeiter mit = objectMapper.readValue(new JSONMitarbeiterResponseSample().getJsonSample(), Mitarbeiter.class);
     Mitarbeiter[] mitarbeiter= new Mitarbeiter[1];
     mitarbeiter[0]=mit;
    context.setTracing(true);
      context.getRouteDefinition(RouteTypeAAxedo.ROUTE_ID).adviceWith(context, new AdviceWithRouteBuilder() {
          @Override
          public void configure() throws Exception {
              interceptSendToEndpoint("bean:axedoAdapter*").skipSendToOriginalEndpoint()
                      .process(new Processor() {
                          public void process(Exchange exchange) throws Exception {
                              exchange.getIn().setBody(null);
                              
                          }
                      });
          }
      });
      context.getRouteDefinition(RouteTypeAMIA.ROUTE_ID).adviceWith(context, new AdviceWithRouteBuilder() {
        @Override
        public void configure() throws Exception {
            interceptSendToEndpoint("bean:miaAdapter*").skipSendToOriginalEndpoint()
                    .process(new Processor() {
                        public void process(Exchange exchange) throws Exception {
                            exchange.getIn().setBody(null);
                            
                        }
                    });
        }
      });
      context.getRouteDefinition(RouteTypeATrapeze.ROUTE_ID).adviceWith(context, new AdviceWithRouteBuilder() {
        @Override
        public void configure() throws Exception {
            interceptSendToEndpoint("bean:trapezeAdapter*").skipSendToOriginalEndpoint()
                    .process(new Processor() {
                        public void process(Exchange exchange) throws Exception {
                            exchange.getIn().setBody(null);
                            
                        }
                    });
        }
      });
      context.getRouteDefinition(PollingMitarbeiterRouteTypeA.ROUTE_ID).adviceWith(context, new AdviceWithRouteBuilder() {
        @Override
        public void configure() throws Exception {
            interceptSendToEndpoint("bean:mitarbeiterPerformisAdapter*").skipSendToOriginalEndpoint()
                    .process(new Processor() {
                        public void process(Exchange exchange) throws Exception {
                            exchange.getIn().setBody(mitarbeiter);
                            
                        }
                    });
        }
      });

  }
  
  @Test
  public void invokeAdvancedPermissionRoute() throws Exception {
      context.start();

      DefaultResponse response = (DefaultResponse) template.sendBody(PollingMitarbeiterRouteTypeA.ROUTE_DIRECT_ENDPOINT, ExchangePattern.InOut,
              "");
      System.out.println(response);
      assertNotNull(response);
      assertEquals(200, response.getStatusCode());
      context.stop();

  }*/
  

}
