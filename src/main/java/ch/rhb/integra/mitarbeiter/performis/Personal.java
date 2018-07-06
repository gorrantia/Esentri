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
public class Personal implements Serializable{

 
  private static final long serialVersionUID = -2558603974935846070L;
  @JsonProperty("FIRMANR")
  private long firmaNR;
  @JsonProperty("PERSNR")
  private long persNR;
  @JsonProperty("PERSONAL_ID")
  private long personalID;
  @JsonProperty("ACTIVE_BOOL")
  private long activeBool;
  @JsonProperty("MODIFY_DATE")
  private String modifyDate;
  @JsonProperty("VORNAME")
  private String vorname;
  @JsonProperty("NAME")
  private String name;
  @JsonProperty("HEIMATORT")
  private String heimatort;
  @JsonProperty("GESCHLECHT")
  private String geschlecht;
  @JsonProperty("GESCHLECHT_CODE")
  private long geschlechtCode;
  @JsonProperty("ZIVILSTAND")
  private String zivilstand;
  @JsonProperty("ZIVILSTAND_CODE")
  private long zivilstandCode;
  @JsonProperty("ZIVILSTAND_DATUM_VON")
  private String zivilstandDatumVon;
  @JsonProperty("MITARBEITERKATEGORIE")
  private String mitarbeiterKategorie;
  @JsonProperty("MITARBEITERKATEGORIE_CODE")
  private long mitarbeiterKategorieCode;
  @JsonProperty("THEORIE_EINTRITT")
  private String theorieEintritt;
  @JsonProperty("NATIONALITAET")
  private String nationalitaet;
  @JsonProperty("NATIONALITAET_KURZ")
  private String nationalitaetKurz;
  @JsonProperty("NATIONALITAET_CODE")
  private long nationalitaetCode;
  @JsonProperty("ARBEITSPLATZKANTON_CODE")
  private long arbeitsPlatzKantonCode;
  @JsonProperty("TEILPENSIONIERUNGSGRAD")
  private Double teilPensionierungsGrad;
  @JsonProperty("LOHNART_CODE")
  private Long lohnArtCode;
  
  private Codemaster[] codemaster;

}
