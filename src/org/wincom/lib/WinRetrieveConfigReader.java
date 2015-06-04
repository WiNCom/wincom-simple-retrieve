package org.wincom.lib;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class WinRetrieveConfigReader {
    private static final String DEFAULT_CONFIG = WinRetrieveConfigReader.class.getClassLoader().getResource("resources/winretrieve.conf").getPath();

    private String configPath;
	Map<String, String> configValues;

	public WinRetrieveConfigReader() {
        try {
            this.configPath = URLDecoder.decode(DEFAULT_CONFIG, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        init();
    }

	public WinRetrieveConfigReader(String configFile) {
        this.configPath = configFile;
		init();
	}

    private void init() {
        configValues = new HashMap<>();
        parseConfig();
    }

    private void parseConfig() {
        System.out.println("[+] Parsing Config: " + configPath);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(configPath));
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
