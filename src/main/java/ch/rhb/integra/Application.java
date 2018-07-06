package ch.rhb.integra;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Main application file
 * 
 * @author esentri AG, <a href="mailto:guillermo.orrantia@esentri.com">gorrantia</a>
 *
 */
@SpringBootApplication
@ImportResource({"classpath:spring/camel-context.xml"})
@Slf4j
public class Application {

  @Value("${camel.component.servlet.mapping.context-path}")
  private String camelServletMapping;

  @Value("${application.root-context-path}")
  private String rootContextPath;

  @Value("${application.api-context-path}")
  private String apiContextPath;

  /**
   * A main method to start this application.
   */
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  /**
   * This Route definition is not needed when using Camel 2.19 or later. However when using REST in
   * conjunction with the Servlet component the CamelServlet will not be registered automatically.
   * So this class is intended to to this job.
   */
  @Bean
  public ServletRegistrationBean camelServlet() {
    log.info("Register CamelServlet with context path {}.", camelServletMapping);

    ServletRegistrationBean servletBean = new ServletRegistrationBean();
    servletBean.setName("CamelServlet");
    servletBean.setLoadOnStartup(1);
    servletBean.setServlet(new CamelHttpTransportServlet());
    servletBean.addUrlMappings(camelServletMapping);
    return servletBean;
  }

  @Controller
  class SwaggerWelcome {
    @RequestMapping("/swagger-ui")
    public String redirectToUi() {
      return "redirect:/webjars/swagger-ui/index.html?url=/" + rootContextPath + "/"
          + apiContextPath + "&validatorUrl=";
    }
  }

}
