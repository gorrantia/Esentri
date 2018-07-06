package ch.rhb.integra.config;


import javax.validation.Valid;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * Manages properties for the Performis Connect Rest service. The properties are maintained in
 * application.properties.
 * 
 * @author esentri AG, <a href="mailto:guillermo.orrantia@esentri.com">mlohn</a>
 */
@Configuration
@ConfigurationProperties("performis.connect")
@Validated
public class PerformisConnectProperties {
  @NotBlank
  private String baseUri;
  @NotBlank
  private String companyNumber = null;
  @Valid
  private Credentials credentials;
  @Valid
  private Mitarbeiter mitarbeiter;
  @Valid
  private Organisation organisation;
  @Valid
  private Address address;
  @Valid
  private Partner partner;
  @Valid
  private CommAddress commAddress;
  @Valid
  private Codemaster codemaster;
  @Valid
  private Personal personal;


  /**
   * @return the credentials
   */
  public Credentials getCredentials() {
    return credentials;
  }

  /**
   * @param credentials the credentials to set
   */
  public void setCredentials(Credentials credentials) {
    this.credentials = credentials;
  }

 

  /**
   * @return the baseUri
   */
  public String getBaseUri() {
    return baseUri;
  }

  /**
   * @param baseUri the baseUri to set
   */
  public void setBaseUri(String baseUri) {
    this.baseUri = baseUri;
  }

  /**
   * @return the companyNumber
   */
  public String getCompanyNumber() {
    return companyNumber;
  }

  /**
   * @param companyNumber the companyNumber to set
   */
  public void setCompanyNumber(String companyNumber) {
    this.companyNumber = companyNumber;
  }

  
  public static class Mitarbeiter {
    @NotBlank
    private String endpointUri;

    /**
     * @return the endpointUri
     */
    public String getEndpointUri() {
      return endpointUri;
    }

    /**
     * @param endpointUri the endpointUri to set
     */
    public void setEndpointUri(String endpointUri) {
      this.endpointUri = endpointUri;
    }

  }

  public static class Address {
    @NotBlank
    private String endpointUri;

    /**
     * @return the endpointUri
     */
    public String getEndpointUri() {
      return endpointUri;
    }

    /**
     * @param endpointUri the endpointUri to set
     */
    public void setEndpointUri(String endpointUri) {
      this.endpointUri = endpointUri;
    }

  }

  public static class CommAddress {
    @NotBlank
    private String endpointUri;

    /**
     * @return the endpointUri
     */
    public String getEndpointUri() {
      return endpointUri;
    }

    /**
     * @param endpointUri the endpointUri to set
     */
    public void setEndpointUri(String endpointUri) {
      this.endpointUri = endpointUri;
    }

  }

  public static class Personal {
    @NotBlank
    private String endpointUri;

    /**
     * @return the endpointUri
     */
    public String getEndpointUri() {
      return endpointUri;
    }

    /**
     * @param endpointUri the endpointUri to set
     */
    public void setEndpointUri(String endpointUri) {
      this.endpointUri = endpointUri;
    }

  }
  
  public static class Organisation {
    @NotBlank
    private String endpointUri;

    /**
     * @return the endpointUri
     */
    public String getEndpointUri() {
      return endpointUri;
    }

    /**
     * @param endpointUri the endpointUri to set
     */
    public void setEndpointUri(String endpointUri) {
      this.endpointUri = endpointUri;
    }

  }

  public static class Partner {
    @NotBlank
    private String endpointUri;

    /**
     * @return the endpointUri
     */
    public String getEndpointUri() {
      return endpointUri;
    }

    /**
     * @param endpointUri the endpointUri to set
     */
    public void setEndpointUri(String endpointUri) {
      this.endpointUri = endpointUri;
    }

  }
  
  public static class Codemaster {
    @NotBlank
    private String endpointUri;

    /**
     * @return the endpointUri
     */
    public String getEndpointUri() {
      return endpointUri;
    }

    /**
     * @param endpointUri the endpointUri to set
     */
    public void setEndpointUri(String endpointUri) {
      this.endpointUri = endpointUri;
    }

  }


  public static class Credentials {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    /**
     * @return the username
     */
    public String getUsername() {
      return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
      this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
      return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
      this.password = password;
    }

  }


  public Mitarbeiter getMitarbeiter() {
    return mitarbeiter;
  }

  public void setMitarbeiter(Mitarbeiter mitarbeiter) {
    this.mitarbeiter = mitarbeiter;
  }

  public Organisation getOrganisation() {
    return organisation;
  }

  public void setOrganisation(Organisation organisation) {
    this.organisation = organisation;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public Partner getPartner() {
    return partner;
  }

  public void setPartner(Partner partner) {
    this.partner = partner;
  }

  public CommAddress getCommAddress() {
    return commAddress;
  }

  public void setCommAddress(CommAddress commAddress) {
    this.commAddress = commAddress;
  }

  public Codemaster getCodemaster() {
    return codemaster;
  }

  public void setCodemaster(Codemaster codemaster) {
    this.codemaster = codemaster;
  }

  public Personal getPersonal() {
    return personal;
  }

  public void setPersonal(Personal personal) {
    this.personal = personal;
  }
  

}
