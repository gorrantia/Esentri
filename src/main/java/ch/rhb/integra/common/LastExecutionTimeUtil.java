package ch.rhb.integra.common;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import ch.rhb.integra.config.ConfigMapProperties;
import ch.rhb.integra.config.LastExecutionProperties;

/**
 * Utility class to read the last execution properties file from the file system
 * @author nfonnegra
 *
 */

@Service("LastExecutionTimeUtil")
@Slf4j
public class LastExecutionTimeUtil {
	
	@Autowired
    private ConfigMapProperties properties;
	
	/**
	 * Obtains the LastExecutionTime from the properties file/configmap. It tries to parse it. If an 
	 * exception thrown, the default execution date from the properties file will be used.
	 * @param uuid - Execution ID of the Route. Used only for logging purposes.
	 * @param routeID - ID of the Route used only for logging purposes.
	 * @param mapName. The confog map name is used to locate the file in the system
	 * @return the date form the last execution properties. The default execution date if it is not parsable.
	 */
	
	public String getLastExecutionTime(String uuid, String routeID,String mapName) {
		DateTimeFormatter formatter=DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		String lastExec=null;
		try {
			lastExec=obtainLastExecutionTime(formatter,mapName);
		}catch( NullPointerException | DateTimeParseException ex) {
		}
		return lastExec;
	}

	/**
	 * Obtains the LastExecutionTime from the properties file/configmap. It tries to parse it. If an 
	 * exception thrown, the default execution date from the properties file will be used.
	 * @param mapName. The config map name is used to locate the file in the system
	 * @return the date form the last execution properties. The default execution date if it is not parsable.
	 */
	
	public String getLastExecutionTime(String mapName) {
		DateTimeFormatter formatter=DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		String lastExec=null;
		try {
			lastExec=obtainLastExecutionTime(formatter,mapName);
		}catch( NullPointerException | DateTimeParseException ex) {
		}
		return lastExec;
	}

	
	/**
	 * Reads the properties file to obtain the last execution date. It will try to parse it to ISO_LOCAL_DATE_TIME format.
	 * It will return the default date from the application.properties file if the parsing was unsuccessful.
	 * @return
	 */
	private String obtainLastExecutionTime(DateTimeFormatter formatter, String mapName) {
		String fileURI=properties.getPropertiesFileLocation()+File.separator+mapName+File.separator+mapName+".properties";
		
		
		// instantiates the properties class with a reference to the properties file
		LastExecutionProperties lastExecutionProperties=new LastExecutionProperties(fileURI);
		String lastExecution=lastExecutionProperties.getLastExecution();
		// parses the date. If there is an exception, the default date will be used
		formatter.parse(lastExecution).toString();
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
}
