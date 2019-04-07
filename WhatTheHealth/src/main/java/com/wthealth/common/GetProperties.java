package com.wthealth.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetProperties {

	//Field
	public static final String weatherFileName = "config/weatherTranslation.properties";

	
	public GetProperties() {}
	
	public String getValue(String fileName, String key) throws Exception {
		
		   InputStream inputStream;
		   String value = "";

			   
		   Properties prop = new Properties();
		  
		   inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
		    
		       	if(inputStream != null) {
		        		prop.load(inputStream);
		       	}else {
		        		throw new FileNotFoundException("property file '" + fileName + "' not found in the classpath");
		       	}
		        	
		   value = prop.getProperty(key);	
		   inputStream.close();
		        

		return value;
	}

	

}
