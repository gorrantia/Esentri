package com.esentri.integration.common;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.camel.Exchange;
import org.springframework.web.client.HttpStatusCodeException;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * Implements a <code>Processor</code> usable in case of an exception.
 * </p>
 * <p>
 * It builds a JSON response as <code>DefaultRestResponse</code> object. Furthermore it sets the
 * value 500 (internal server error) as HTTP status code.
 * </p>
 *
 * @author esentri AG, <a href="mailto:markus.lohn@esentri.com">mlohn</a>
 */
@Slf4j
public class DefaultExceptionProcessor implements org.apache.camel.Processor {

  @Override
  public void process(Exchange exchange) throws Exception {
    Exception ex = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
    log.error(ex.getMessage(), ex);

    List<DefaultResponse.ApiError> errors = new ArrayList<DefaultResponse.ApiError>();
    String uuid = exchange.getIn().getHeader("uuid", String.class);
    if (uuid != null) {
      errors.add(new DefaultResponse.ApiError("uuid", uuid));
    }
    errors.add(new DefaultResponse.ApiError("Exception", ex.getClass().getName()));
    if (ex instanceof HttpStatusCodeException) {
      errors.add(new DefaultResponse.ApiError("Error Response",
          ((HttpStatusCodeException) ex).getResponseBodyAsString()));
    }
    DefaultResponse errorResponse =
        DefaultResponse.createGenericErrorResponse(ex.getMessage(), errors);

    exchange.getOut().setBody(errorResponse);
    exchange.getOut().setFault(true);
    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE,
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    // exchange.getOut().setHeader(Exchange.CONTENT_TYPE, "application/xml");
  }

}
