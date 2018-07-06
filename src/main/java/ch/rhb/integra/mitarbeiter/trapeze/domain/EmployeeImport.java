package ch.rhb.integra.mitarbeiter.trapeze.domain;


import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tjeneste.employeeimport")
@IdClass(EmployeeImportID.class)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeImport implements Serializable {
  
  private static final long serialVersionUID = 1L;

  @Column(name = "COMPANYCODE") 
  private String companyCode;
  
  @Id
  @Column(name = "EMP")  
  private Long emp;
  
  @Id
  @Column(name = "FROMDATE")
  private Date fromDate;
  
  @Id
  @Column(name = "TODATE")  
  private Date toDate;
  
  @Column(name = "GENDER")
  private String gender;
  @Column(name = "TITLE")   
  private String title;
  @Column(name = "FIRSTNAME")
  private String fristName;
  @Column(name = "LASTNAME")
  private String lastName;
  @Column(name = "ADDRESS") 
  private String address;
  @Column(name = "COUNTRYCODE") 
  private String countryCode;
  @Column(name = "NATIONALITY") 
  private String nationality;
  @Column(name = "ZIPCODE")
  private String zipCode;
  @Column(name = "CITY")
  private String city;
  @Column(name = "ADDRESS2")
  private String address2;
  @Column(name = "HIREDATE")
  private Date hireDate;
  @Column(name = "SENIORITYDATE")
  private Date seniorityDate;
  @Column(name = "RESIGNATIONDATE")
  private Date resignationDate;
  @Column(name = "ORGANIZATION")    
  private String organization;
  @Column(name = "ACCOUNT")
  private String account;
  @Column(name = "BIRTHDAY")   
  private Date birthDate;
  @Column(name = "WEEKLY_WORKHOURS")
  private Long weeklyWorkHours;
  @Column(name = "USERDEFINED1")
  private String userDefined1;
  @Column(name = "WORKPLACE")
  private String workplace;
  @Column(name = "USERNAME")
  private String userName;
  @Column(name = "CHANGEDATE")  
  private Date changeDate;
  @Column(name = "EXTERN_ID") 
  private Long externID;
  
  @Id
  @Column(name = "IMPORTEDDATE")
  private Date importedDate;
}
