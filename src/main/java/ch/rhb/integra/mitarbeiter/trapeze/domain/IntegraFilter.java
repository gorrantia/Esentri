package ch.rhb.integra.mitarbeiter.trapeze.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class IntegraFilter {
  private String filterName;
  private String identifierName;
  private String identifierID;
  private String aktiv;

}
