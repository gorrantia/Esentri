package ch.rhb.integra.mitarbeiter.performis;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address  implements Serializable{
 
  private static final long serialVersionUID = 8233808722256334878L;
  @JsonProperty("BP_ADDR_ID")
  private long bpAddrID;
  @JsonProperty("ACTIVE_BOOL")
  private long activeBool;
  @JsonProperty("MODIFY_USER")
  private String modifyUser;
  @JsonProperty("MODIFY_DATE")
  private String modifyDate;
  @JsonProperty("BP_ID")
  private long bpID;
  @JsonProperty("NAME")
  private String name;
  @JsonProperty("NAME_ADDITION")
  private String nameAddition;
  @JsonProperty("ADDRESS")
  private String address;
  @JsonProperty("ZIP")
  private String zip;
  @JsonProperty("ZIP_ADDITION")
  private String zipAddition;
  @JsonProperty("CITY")
  private String city;
  @JsonProperty("C3_COUNTRY")
  private long c3Country;
  @JsonProperty("SHORT_ADDRESS")
  private String shortAddress;
  @JsonProperty("C17_LANGUAGE")
  private long c17Language;
  @JsonProperty("ATTRIBUTE_LIST_VALUE_ID")
  private String attributeListValueID;
  @JsonProperty("ORIGIN")
  private String origin;
  @JsonProperty("STREET")
  private String street;
  @JsonProperty("HOUSE_NO")
  private String houseNO;
  @JsonProperty("ADDITIONAL_LINE1")
  private String additionalLine1;
  @JsonProperty("ADDITIONAL_LINE2")
  private String additionalLine2;
  @JsonProperty("PO_BOX")
  private String po_box;
  @JsonProperty("POBOX")
  private String poBox;
  @JsonProperty("LAT")
  private float lat;
  @JsonProperty("LNG")
  private float lng;
  @JsonProperty("SEARCH_TERMS")
  private String searchTerms;
  @JsonProperty("PHONE")
  private String phone;
  @JsonProperty("FAX")
  private String fax;
  @JsonProperty("MOBILE_PHONE")
  private String mobilePhone;
  @JsonProperty("FREE")
  private String free;
  @JsonProperty("EMAIL")
  private String email;
  @JsonProperty("INTERNET")
  private String internet;
  
  private Partner[] partner;
  private Codemaster[] codemaster;
  private CommAddress[] commAddress;

}
