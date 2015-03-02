package org.wincom.lib;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class WinRetrieveConfig {
	
	private String username = null;
	private char[] password = null;
	
	Map<String, String> configValues;
	
	public WinRetrieveConfig(String configFile) {
		configValues = new HashMap<String, String>();
		parseConfig(configFile);
	}
	
	private void parseConfig(String configFile) {
		Properties config = new Properties();
		
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(configFile);
			config.load(inputStream);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		for(String property : config.stringPropertyNames()) {
			configValues.put(property, config.getProperty(property));
		}
	}
	
	public String setUsername(String newUsername) {
		username = newUsername;
		return username;
	}
	
	public char[] setPassword(char[] newPassword) {
		this.password = newPassword;
		return password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public char[] getPassword() {
		return password;
	}
	
	public String getField(String field) {
		return configValues.get(field);
	}
}
