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
public class CommAddress implements Serializable{

  private static final long serialVersionUID = 7868455824445606663L;
  @JsonProperty("COMM_ADDRESS_ID")
  private long commAddressID;
  @JsonProperty("BP_ADDR_ID")
  private long bpAddrID;
  @JsonProperty("BP_ID_EMPLOYEE")
  private long bpIDEmployee;
  @JsonProperty("C332_COMM_TYPE")
  private long c332CommType;
  @JsonProperty("COMM_ADDRESS")
  private String commAddress;
  @JsonProperty("COMM_ADDRESS_TECHNICAL")
  private String commAddressTechnical;
  @JsonProperty("MODIFY_USER")
  private String modifyUser;
  @JsonProperty("MODIFY_DATE")
  private String modifyDate;
  @JsonProperty("ORIGIN")
  private String origin;
}
