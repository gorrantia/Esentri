package ch.rhb.integra.config;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Contains the information needed to call the rest API to change the config map
 * @author nfonnegra
 *
 */
@Configuration
@ConfigurationProperties(prefix = "configmap")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ConfigMapProperties {
		
	// aka project in openshift
	private String namespace;
	
	private String apiversion;
	private String resourceUrl;
	private String tokenLocation;
	private String propertiesFileLocation;
	

	
}
