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
public class Partner implements Serializable{


  private static final long serialVersionUID = 883141919544486438L;
  @JsonProperty("BP_ID")
  private long bpID;
  @JsonProperty("ACTIVE_BOOL")
  private long activeBool;
  @JsonProperty("MODIFY_USER")
  private String modifyUser;
  @JsonProperty("MODIFY_DATE")
  private String modifyDate;
  @JsonProperty("SALUTATION")
  private String salutation;
  @JsonProperty("FIRST_NAME")
  private String firstName;
  @JsonProperty("MIDDLE_NAME")
  private String middleName;
  @JsonProperty("INITIALS")
  private String initials;
  @JsonProperty("NAME")
  private String name;
  @JsonProperty("NAME_ADDITION")
  private String nameAddition;
  @JsonProperty("CORPORATION_ID")
  private String corporationID;
  @JsonProperty("CONCERN_ID")
  private String concernID;
  @JsonProperty("ORIGIN")
  private String origin;
  @JsonProperty("ATTRIBUTE_LIST_VALUE_ID")
  private long attributeListValueID;
  @JsonProperty("PARTNER_TYPE")
  private String partnerType;
  @JsonProperty("C17_LANGUAGE")
  private long c17Language;
  @JsonProperty("TITLE")
  private String title;
  @JsonProperty("SECOND_NAME")
  private String secondName;
  @JsonProperty("LAST_NAME")
  private String lastName;
  @JsonProperty("LOCKED_SERVICES_BOOL")
  private long lockedServicesBool;
  @JsonProperty("LOCKED_COMMENT")
  private String lockedComment;
  @JsonProperty("LOCKED_DATE")
  private String lockedDate;
  @JsonProperty("LOCKED_USER")
  private String lockedUser;
}
