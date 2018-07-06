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
public class Codemaster implements Serializable{

  private static final long serialVersionUID = 7017243920906929878L;
  @JsonProperty("COMPANY_NO")
  private long companyNO;
  @JsonProperty("APPLICATION")
  private String application;
  @JsonProperty("CODE")
  private long code;
  @JsonProperty("CODE_ID")
  private long codeID;
  @JsonProperty("CODE_ART")
  private long codeArt;
  @JsonProperty("SHORTKEY")
  private String shortKey;
  @JsonProperty("TEXT")
  private String text;
  @JsonProperty("SHORTTEXT")
  private String shortText;
  @JsonProperty("MODIFY_DATE")
  private String modifyDate;

}
