package ch.rhb.integra.mitarbeiter.psipenta.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import lombok.Data;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PropertyList", namespace = "http://www.psipenta.de/WebServiceDataSchema", propOrder = {
    "property"
})
@XmlSeeAlso({
    PropertyListWithOptionalLines.class
})
@XmlRootElement(name="Record", namespace = "http://www.psipenta.de/WebServiceDataSchema")
public @Data class Record {
  
  @XmlElement(name = "Property", required = true)
  protected List<Property> property = new ArrayList<Property>();

}
