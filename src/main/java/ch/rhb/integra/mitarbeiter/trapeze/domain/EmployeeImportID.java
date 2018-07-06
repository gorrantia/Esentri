package ch.rhb.integra.mitarbeiter.trapeze.domain;

import java.io.Serializable;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeImportID implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = -848100878463306172L;

  private Long emp;
  private Date fromDate;
  private Date toDate;
  private Date importedDate;

}
