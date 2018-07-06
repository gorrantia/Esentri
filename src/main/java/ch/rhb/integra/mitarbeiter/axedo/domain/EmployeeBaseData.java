package ch.rhb.integra.mitarbeiter.axedo.domain;


import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "INTERFACE.EMPLOYEE_BASE_DATA")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class EmployeeBaseData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "RECORD_UID")
  private Long recordUID;
  
  @Column(name = "MA_VERSION_ID")
  private Long maVersionID;
  
  @Column(name = "GUELTIGPERDATUM")
  private Date gueltigPerDatum;

  @Column(name = "PERSNR")
  private String persNR;

  @Column(name = "NACHNAME")
  private String nachname;
  
  @Column(name = "VORNAME")
  private String vorname;
  
  @Column(name = "ADRESSE")
  private String addresse;
  
  @Column(name = "PLZ")
  private String plz;

  @Column(name = "WOHNORT")
  private String wohnort;
  
  @Column(name = "GESCHLECHT")
  private String geschlecht;

  @Column(name = "GEBURTSDATUM")
  private Date geburtsdatum;

  @Column(name = "EINTRITT")
  private Date eintritt;

  @Column(name = "AUSTRITT")
  private Date austritt;

  @Column(name = "KSTNR")
  private String kstnr;

  @Column(name = "VORGESETZTER")
  private String vorgesetzer;

  @Column(name = "ARBEITSORT")
  private String arbeitsort;

  @Column(name = "BESCHAEFTIGUNGSGRAD")
  private Long beschaeftigungsgrad;

  @Column(name = "STRUKTUR_CODE")
  private Long strukturCode;

  @Column(name = "STRUKTUR")
  private String struktur;

  @Column(name = "INAKTIV")
  private String inaktiv;

  @Column(name = "MA_KATEGORIE_CODE")
  private String maKategorieCode;
  
  @Column(name = "MA_KATEGORIE")
  private String maKatogorie;

  @Column(name = "ABTEILUNG")
  private String abteilung;

  @Column(name = "LOHNART_CODE")
  private String lohnartCode;
  
  @Column(name = "TEILPENSIONIERUNGSGRAD")
  private String teilPensionierungsGrad;

  @Column(name = "STATUS")
  private Long status;

  @Column(name = "PROCESSING_DATE")
  private Date processingDate;

}
