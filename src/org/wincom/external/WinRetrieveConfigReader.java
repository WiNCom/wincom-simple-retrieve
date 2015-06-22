package org.wincom.external;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class WinRetrieveConfigReader {
    private static final String DEFAULT_CONFIG = "winretrieve.conf";

    private String configPath;
    private InputStream configStream;
	Map<String, String> configValues;

	public WinRetrieveConfigReader() {
        configPath = ResourceLoader.getResource(DEFAULT_CONFIG);
        configStream = ResourceLoader.getResourceAsStream(DEFAULT_CONFIG);
        init();
    }

	public WinRetrieveConfigReader(String configFile) {
        configPath = configFile;
        configStream = ResourceLoader.getResourceAsStream(configFile);
		init();
	}

    private void init() {
        configValues = new HashMap<>();
        parseConfig();
    }

    private void parseConfig() {
        System.out.println("[+] Parsing Config: " + configPath);

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(configStream));
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.startsWith("#") || currentLine.equals(""))
                    continue;

                String[] parameter = currentLine.split("=");
                configValues.put(parameter[0], parameter[1]);
            }

            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("[-] Cannot continue, config not found!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[-] Cannot continue, config malformed!");
        }
    }
	
	public String getField(String field) {
		return configValues.get(field);
	}
}
