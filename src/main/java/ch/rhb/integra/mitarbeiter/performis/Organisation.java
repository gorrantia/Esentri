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

public class Organisation implements Serializable{

  private static final long serialVersionUID = 6854631660068879738L;
  @JsonProperty("ORGANISATION_ID")
  private long organisationID;
  @JsonProperty("REC_VERS")
  private long recVers;
  @JsonProperty("CREATE_DATE")
  private String createDate;
  @JsonProperty("CREATE_USER")
  private String createUser;
  @JsonProperty("MODIFY_DATE")
  private String modifyDate;
  @JsonProperty("CREATE_USER")
  private String MODIFY_USER;
  @JsonProperty("FIRMANR")
  private long firmaNR;
  @JsonProperty("BEZEICHNUNG")
  private String bezeichnung;
  @JsonProperty("KURZBEZEICHNUNG")
  private String kurzBezeichnung;
  @JsonProperty("SORT")
  private long sort;
  @JsonProperty("ORG_ID_UEBERGEORDNET")
  private long orgIDUebergeordnet;
  @JsonProperty("STABSSTELLE")
  private String stabsstelle;
  @JsonProperty("ACTIVE_BOOL")
  private long activeBool;
  @JsonProperty("C531_HIERARCHIE")
  private long c531Hierarchie;
  @JsonProperty("MA_VERSION_ID_VERANTWORTLICHER")
  private long maVersionIDVerantwortlicher;
}
