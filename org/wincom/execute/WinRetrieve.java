package org.wincom.execute;

import org.wincom.gui.WinRetrieveWindow;
import org.wincom.lib.WinRetrieveConfig;

public class WinRetrieve {
	
	private static void createAndShowGUI(String[] args) {
		String configPath;
		if(args.length > 0) 
			configPath = args[0];
		else
			configPath = "config.properties";
		
		WinRetrieveConfig config = initializeConfig(configPath);
		new WinRetrieveWindow(config);
	}
	
	private static WinRetrieveConfig initializeConfig(String configPath) {
		WinRetrieveConfig config = new WinRetrieveConfig(configPath);
		return config;
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(args);
            }
        });
	}

}
