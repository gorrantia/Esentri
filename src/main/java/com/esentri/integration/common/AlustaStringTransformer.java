package com.esentri.integration.common;

import org.apache.commons.lang3.StringUtils;

/**
 * A utils class for String manipulation used in JAXB transformations.
 * 
 * @author esentri AG, <a href="mailto:nicolas.fonnegra@esentri.com">nfonnegra</a>
 */
public class AlustaStringTransformer {

  public static String truncateAndEscape(String original, int length) {
    if (original == null) {
      return "";
    }
    return StringUtils.truncate(original, length);
  }

}
