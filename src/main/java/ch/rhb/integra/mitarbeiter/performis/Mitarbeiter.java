package ch.rhb.integra.mitarbeiter.performis;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Data;
/**
 * Worker object for worker-synchronization service
 * 
 * @author gorrantia
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public @Data class Mitarbeiter implements Serializable{
  
  private static final long serialVersionUID = -3075709862447161023L;
  
  @JsonProperty("FIRMANR")
  private String firmanr;
  @JsonProperty("PERSNR")
  private long persNr;
  @JsonProperty("PERSONAL_ID")
  private long personalID;
  @JsonProperty("GP_ADR_ID_MITARBEITER")
  private long gpAdrIDMitarbeiter;
  @JsonProperty("VORNAME")
  private String vorname;
  @JsonProperty("NACHNAME")
  private String nachname;
  @JsonProperty("EINTRITT")
  private String eintritt;
  @JsonProperty("AUSTRITT")
  private String austritt;
  @JsonProperty("AUSTRITTSGRUND")
  private String austrittsGrund;
  @JsonProperty("AUSTRITTSGRUND_CODE")
  private String austrittsGrundCode;
  @JsonProperty("GP_ADR_ID_ARBEITGEBER")
  private long gpAdrIDMArbeitgeber;
  @JsonProperty("VERSION_GUELTIG_AB")
  private String versionGueltigAb;
  @JsonProperty("MA_VERSION_ID")
  private long maVersionID;
  @JsonProperty("ACTIVE_BOOL")
  private long activeBool;
  @JsonProperty("GEBURTSDATUM")
  private String geburstDatum;
  @JsonProperty("GESCHLECHT")
  private String geschlecht;
  @JsonProperty("STRASSE")
  private String strasse;
  @JsonProperty("HAUSNUMMER")
  private String hausNummer;
  @JsonProperty("POSTFACH")
  private String postFach;
  @JsonProperty("PLZ")
  private String plz;
  @JsonProperty("ORT")
  private String ort;
  @JsonProperty("KANTON")
  private String Kanton;
  @JsonProperty("LAND")
  private String land;
  @JsonProperty("TELEFON")
  private String telefon;
  @JsonProperty("MOBIL")
  private String mobil;
  @JsonProperty("EMAIL")
  private String email;
  @JsonProperty("SOZIALVERSICHERUNGSNR")
  private String sozialVersicherungsNummer;
  @JsonProperty("BERUF_ERLERNT")
  private String berufErlernt;
  @JsonProperty("BERUF_AUSGEUEBT")
  private String berufAusgeubt;
  @JsonProperty("BENUTZER")
  private String benutzer;
  @JsonProperty("FUNKTIONSGRUPPE")
  private String funktionsGruppe;
  @JsonProperty("FUNKTIONSGRUPPE_CODE")
  private String funktionsGruppeCode;
  @JsonProperty("KSTNR")
  private String kstnr;
  @JsonProperty("PERSNR_VORGESETZTER_VERSION")
  private String persnrVorgesetzterVersion;
  @JsonProperty("PERSNR_VORGESETZTER_AKTUELL")
  private long persnrVorgesetzterAktuell;
  @JsonProperty("BESCHAEFTIGUNGSGRAD")
  private String beschaeftigungsGrad;
  @JsonProperty("GP_ADR_ID_FIRMA")
  private long gpAdrIDFirma;
  @JsonProperty("GP_ADR_ID_ARBEITSORT")
  private long gpAdrIDMArbeitsort;
  @JsonProperty("ARBEITSORT_CODE")
  private String arbeitsortCode;
  @JsonProperty("STUNDENSATZ")
  private String stundenSatz;
  @JsonProperty("KLEIDERCODE")
  private String kleiderCode;
  @JsonProperty("STRUKTUR")
  private String struktur;
  @JsonProperty("STRUKTUR_CODE")
  private String strukturCode;
  @JsonProperty("FIRMENARBEITSVERTRAG")
  private String firmenArbeitsVertrag;
  @JsonProperty("FIRMENARBEITSVERTRAG_CODE")
  private String firmenArbeitsVertragCode;
  @JsonProperty("ORGANISATION_ID")
  private long organisationID;
  @JsonProperty("ORGANISATION_PFAD")
  private String organisationPfad;
  @JsonProperty("ORGANISATION_PFAD_KURZ")
  private String organisationPfadKurz;
  @JsonProperty("MODIFY_DATE_MA_VERSION")
  private String modifyDateMAVersion;
  @JsonProperty("MODIFY_DATE_KST")
  private String modifyDateKST;
  @JsonProperty("VORGESETZER_VORNAME")
  private String vorgesetzerVorname;
  @JsonProperty("VORGESETZER_NACHNAME")
  private String vorgesetzerNachname;
  
  private Personal personal;
  private Organisation organisation;
  private Address[] firmaAddress;
  private Address[] arbeitgeberAddress;
  private Address[] arbeitsortAddress;
  private Address[] mitarbeiterAddress;

}
