package ch.rhb.integra.common;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.ConvertUtils;
/**
 * XML map utility helper functions
 * 
 * @author esentri AG, <a href="mailto:guillermo.orrantian@esentri.com">Guillermo Orrantia</a>
 *
 */
@Slf4j
public class XmlMapperUtils {

  /**
   * Private Constructor to avoid instance creation.
   */
  private XmlMapperUtils() {

  }

  /**
   * Formats the given XML date and time value based on the given pattern.
   * 
   * @param xmlDate the XML based date and time value (mandatory)
   * @param pattern the pattern, like "yyyyMMdd" etc. (mandatory)
   * @return the formatted String
   */
  public static String convertXmlDate2String(@NonNull XMLGregorianCalendar xmlDate,
      @NonNull String pattern) {
    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
    GregorianCalendar cal = xmlDate.toGregorianCalendar();
    String formattedDate = formatter.format(cal.getTime());
    return formattedDate;
  }

  /**
   * Builds a XML date time value. It uses the given pattern to parse the given value.
   * 
   * @param value a String containing the date time value
   * @param pattern the pattern to parse the value
   * @return the XML date time value
   */
  public static List<XMLGregorianCalendar> buildDateTimeValue(String value,
      @NonNull String pattern) {
    List<XMLGregorianCalendar> values = null;
    if (value == null) {
      values = Collections.emptyList();
    } else {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
      LocalDate date = LocalDate.parse(value, formatter);
      XMLGregorianCalendar xmlDate = buildDateTimeValue(date);
      values = new ArrayList<XMLGregorianCalendar>();
      values.add(xmlDate);
    }
    return values;
  }
  
  public static XMLGregorianCalendar buildDateTime(@NonNull String value, @NonNull String pattern) {
    
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
      LocalDate date = LocalDate.parse(value, formatter);
      XMLGregorianCalendar xmlDate = buildDateTimeValue(date);
    
    return xmlDate;
  }
  

  /**
   * Builds a XML date time value. It uses the default pattern "yyyyMMdd" to parse the given value.
   * 
   * @param value a String containing the date time value
   * @return the XML date time value
   */
  public static List<XMLGregorianCalendar> buildDateTimeValue(String value) {
    return buildDateTimeValue(value, "yyyyMMdd");
  }

  /**
   * Builds a XML date time value based on the given LocalDate object.
   *
   * @param date the Date to convert
   * @return a new calendar object for the given LocalDate
   */
  public static XMLGregorianCalendar buildDateTimeValue(LocalDate date) {
    XMLGregorianCalendar xmlcal = null;
    if (date != null) {
      GregorianCalendar cal = GregorianCalendar.from(date.atStartOfDay(ZoneId.systemDefault()));
      try {
        xmlcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        xmlcal.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
        xmlcal.setTime(DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED,
            DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED);
      } catch (DatatypeConfigurationException e) {
        log.warn(e.getMessage());
      }
    }
    return xmlcal;
  }

  /**
   * Builds a XML decimal value.
   * 
   * @param value the value to parse for a decimal value
   * @return the XML decimal value
   */
  public static List<BigDecimal> buildDecimalValue(Object value) {
    List<BigDecimal> values = null;
    if (value == null) {
      values = Collections.emptyList();
    } else {
      BigDecimal convertedValue = (BigDecimal) ConvertUtils.convert(value, BigDecimal.class);
      values = new ArrayList<BigDecimal>();
      values.add(convertedValue);
    }
    return values;
  }

  /**
   * Builds XML integer value
   * 
   * @param value the value to parse for a integer value
   * @return the XML integer value
   */
  public static List<BigInteger> buildIntegerValue(Object value) {
    List<BigInteger> values = null;
    if (value == null) {
      values = Collections.emptyList();
    } else {
      BigInteger convertedValue = (BigInteger) ConvertUtils.convert(value, BigInteger.class);
      values = new ArrayList<BigInteger>();
      values.add(convertedValue);
    }
    return values;
  }

  /**
   * Retrieve the first value from a XML list element.
   * 
   * @param xmlValue the XML list
   * @return the first integer value
   */
  public static BigInteger getIntegerValue(List<JAXBElement<List<BigInteger>>> xmlValue) {
    BigInteger value = null;
    if (xmlValue != null && xmlValue.size() > 0) {
      List<BigInteger> values = xmlValue.get(0).getValue();
      if (values != null && values.size() > 0) {
        value = values.get(0);
      }
    }
    return value;
  }

  /**
   * Retrieve the first value from a XML list element
   * 
   * @param xmlValue the XML list
   * @return the first decimal value
   */
  public static BigDecimal getDecimalValue(List<JAXBElement<List<BigDecimal>>> xmlValue) {
    BigDecimal value = null;
    if (xmlValue != null && xmlValue.size() > 0) {
      List<BigDecimal> values = xmlValue.get(0).getValue();
      if (values != null && values.size() > 0) {
        value = values.get(0);
      }
    }
    return value;
  }

  /**
   * Retrieve the first value from a XML list element
   * 
   * @param xmlValue the XML list
   * @return the first date time value
   */
  public static XMLGregorianCalendar getDateTimeValue(
      List<JAXBElement<List<XMLGregorianCalendar>>> xmlValue) {
    XMLGregorianCalendar value = null;
    if (xmlValue != null && xmlValue.size() > 0) {
      List<XMLGregorianCalendar> values = xmlValue.get(0).getValue();
      if (values != null && values.size() > 0) {
        value = values.get(0);
      }
    }
    return value;
  }
  /**
   * Static function to convert a string representation of a date
   * into a XMLGregorianCalendar 
   * 
   * @param datum string to be converted
   * @return XMLGregorianCalendar object
   */
  public static XMLGregorianCalendar toCal(String datum) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    LocalDate date = LocalDate.parse(datum, formatter);
    return XmlMapperUtils.buildDateTimeValue(date);
  }

}
