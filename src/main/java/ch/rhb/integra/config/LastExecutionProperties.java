package ch.rhb.integra.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reads the last execution date from the properties file. It will have to always open the file and read in order to have
 * the latest value. The url to the properties file is provided externally
 * @author nfonnegra
 *
 */
public class LastExecutionProperties {

	
	public final static String LAST_EXECUTION_KEY="last-execution";
	private String fileURL;
	private final Logger logger = LoggerFactory.getLogger(LastExecutionProperties.class);
	public LastExecutionProperties(String file) {
		fileURL=file;
	}
	public String getLastExecution()  {
		String lastExecution=null;
		try {
			
			File propFile = new File(fileURL);
			if (propFile.exists()) {
				FileInputStream fis;
				fis = new FileInputStream(propFile);
				Properties props = new Properties();
				props.load(fis);
				lastExecution=props.getProperty(LAST_EXECUTION_KEY);
			}
		}
		catch (NullPointerException |IOException e) {
			logger.error("Could not read LastExecution file",e );
		}
		
		return lastExecution;
	}

	 
}
