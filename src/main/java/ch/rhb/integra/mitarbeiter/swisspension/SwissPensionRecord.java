package ch.rhb.integra.mitarbeiter.swisspension;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;
/**
 * CSV Record for use with the create Swiss Pension
 * 
 * @author esentri AG, <a href="mailto:guillermo.orrantian@esentri.com">Guillermo Orrantia</a>
 *
 */
public @Data class SwissPensionRecord {

  private int RecordArt;
  private LocalDate periode;
  private LocalDate vonDatum;
  private long lohnNr;
  private String andere;
  private String name;
  private String vorname;
  private LocalDate gebDatum;
  private Character geschlecht;
  private Character sprache;
  private String ahvid;
  private String strasse;
  private String zusatz;
  private String plz;
  private String postfach;
  private String ort;
  private String land;
  private String nation;
  private String email;
  private int zivilstand;
  private LocalDate zivilstandDatum;
  private LocalDate eintritt;
  private LocalDate austritt;
  private int austrittGrund;
  private int agid;
  private int beschGrad;
  private BigDecimal AHVLohn;
  private String e_name;
  private String e_vorname;
  private String e_geschlecht;
  private LocalDate e_gebDatum;
  private String e_ehvid;
  
}
