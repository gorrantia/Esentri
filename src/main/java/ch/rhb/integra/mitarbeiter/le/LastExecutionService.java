package ch.rhb.integra.mitarbeiter.le;



import ch.rhb.integra.config.ConfigMapProperties;
import ch.rhb.integra.configmap.write.json.ConfigMap;
import ch.rhb.integra.configmap.write.json.Data;
import ch.rhb.integra.configmap.write.json.Metadata;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Service that provides the functionality to obtain the lastExecutionTime from the kubernetes
 * configmap and to update it
 * @author nfonnegra
 *
 */
@Slf4j
public abstract class LastExecutionService {

  @Autowired
  private ConfigMapProperties properties;
  
  public final static String LAST_EXECUTION_KEY = "last-execution";
  private static final String NAMESPACE_TAG="<NAMESPACE>";
  private static final String NAME_TAG="<MAPNAME>";
  private static final String KIND="ConfigMap";
  private static final String MAP_KEY=LAST_EXECUTION_KEY+"=";

  /**
   * Reads the properties file and extracts the value
   * @param fileUTI the path to the properties file
   * @return
   */
  private String readLastExecution(String fileURI) {
    String lastExecution = null;
    try {

        File propFile = new File(fileURI);
        if (propFile.exists()) {
            FileInputStream fis;
            fis = new FileInputStream(propFile);
            Properties props = new Properties();
            props.load(fis);
            lastExecution = props.getProperty(LAST_EXECUTION_KEY);
        }
    } catch (NullPointerException | IOException e) {
        log.error("Could not read LastExecution file", e);
    }

    return lastExecution;
  }
  
  /**
   * Delivers the current date in ISO_LOCAL_DATE_TIME (2011-12-03T10:15:30) format without milliseconds
   * @return String with the current date in ISO_LOCAL_DATE_TIME format
   */
  public String getCurrentExecutionTime() {
      LocalDateTime now = LocalDateTime.now();
      DateTimeFormatter formatter=DateTimeFormatter.ISO_LOCAL_DATE_TIME;
      return now.truncatedTo(ChronoUnit.SECONDS).format(formatter);
  }

  /**
   * Obtains the LastExecutionTime from the properties file/configmap. It tries to parse it. It will return null if an
   * exception occurs.
   * @return the date form the last execution properties. Null if it is not parseable.
   */
  
  public String getLastExecutionTime() {
      DateTimeFormatter formatter=DateTimeFormatter.ISO_LOCAL_DATE_TIME;
      String lastExec=null;
      try {
          lastExec=obtainLastExecutionTime(formatter);
      }catch( NullPointerException | DateTimeParseException ex) {
        log.info("Last execution format in configmap is not a valid date {}.",formatter.toString());
          
      }
      return lastExec;
  }
  
  /**
   * Reads the properties file to obtain the last execution date. It will try to parse it to ISO_LOCAL_DATE_TIME format.
   * It will return the default date from the application.properties file if the parsing was unsuccessful.
   * @return
   */
  private String obtainLastExecutionTime(DateTimeFormatter formatter) {
      String fileURI=properties.getPropertiesFileLocation()+File.separator+getMapName()+File.separator+getMapName()+".properties";
      String lastExecution=readLastExecution(fileURI);
      // parses the date. If there is an exception, the default date will be used
      formatter.parse(lastExecution).toString();
      return lastExecution;
  }
  
  /**
   * generates the REST URL for invoking the kubernetes API
   * @return
   */
  private String obtainResourceURL() {
      String url=null;
      url=properties.getResourceUrl();
      url=url.replaceAll(NAMESPACE_TAG, properties.getNamespace());
      url=url.replaceAll(NAME_TAG, getMapName());
      return url;
  }
  
  /**
   * Updates the configmap with the current time using LastExecutionTimeUtil
   *
   */
  public void updateToNow() {
      update(getCurrentExecutionTime());
  }
  
  
  /**
   * Generates the JSON object representing the configmap
   * @param lastExecution
   * @return
   */
  private ConfigMap generateNextExecution(String lastExecution) {
     ConfigMap updatedInstance = new ConfigMap();
     updatedInstance.setApiVersion(properties.getApiversion());
     updatedInstance.setKind(KIND);
     Data data=generateData(MAP_KEY+lastExecution);
     Metadata metadata= new Metadata();
     metadata.setNamespace(properties.getNamespace());
     metadata.setName(getMapName());
     updatedInstance.setMetadata(metadata);
     updatedInstance.setData(data);
     return updatedInstance;
 
  }
  
  /**
   * Updates the configmap with the new execution time
   * @param lastExecution
   */
  public void update(String lastExecution)  {
      // normal rest template. We don't need to log the invocations here so no interceptors are added
      RestTemplate restTemplate = new RestTemplate();
      HttpHeaders headers = new HttpHeaders();
      headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
      headers.setContentType(MediaType.APPLICATION_JSON);
      String token=null;
      // reads the token from the file system. The token is the users id that is provided by kubernetes and stored
      // automatically on every POD in the file system
      try {
          token = readToken();
      } catch (IOException e) {
          log.error("Error reading token file to update map",e);
          throw new RuntimeException("Error reading token file to update map ",e);
      }
      // add the token to the HTTP header request
      headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
      // Generates JSON java Object
      ConfigMap configMap=generateNextExecution(lastExecution);
      HttpEntity<ConfigMap> entity = new HttpEntity<ConfigMap>(configMap, headers);
      // gets the invocation url
      String resourceUrl =obtainResourceURL();
      ResponseEntity<ConfigMap> result =  restTemplate.exchange(
        resourceUrl, 
        HttpMethod.PUT, 
        entity, 
        ConfigMap.class);
      if(result.getStatusCode().is2xxSuccessful()) {
          log.info("Updating configMap with new execution Date {}",lastExecution);
      } else {
          log.error("Error updating configmap {}",result);
          throw new RuntimeException("Error updating configmap "+result);
      }
      
      
  }

  /**
   * Reads the token from the POD's file system. 
   * @return
   * @throws IOException
   */
   private String readToken() throws IOException {
          FileReader fileReader = new FileReader(properties.getTokenLocation());
          BufferedReader bufferedReader = new BufferedReader(fileReader);
          String line = bufferedReader.readLine();
          bufferedReader.close();        
          return line;
  }
  
   /**
    * Returns hte concrete mapname
    * @return
    */
  public abstract String getMapName(); 
  /**
   * Generates the concrete Data object.
   * @param value
   * @return
   */
  public abstract Data generateData(String value);
}
