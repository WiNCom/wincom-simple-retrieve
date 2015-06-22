package org.wincom.execute;

import org.wincom.gui.WinRetrieveWindow;
import org.wincom.lib.FileRetrievalAPI;
import org.wincom.external.WinRetrieveConfigReader;

public class WinRetrieve {
	
	private static void createAndShowGUI(String[] args) {
		WinRetrieveConfigReader config;

		if(args.length > 0)
			config = new WinRetrieveConfigReader(args[0]);
		else
			config = new WinRetrieveConfigReader();

        FileRetrievalAPI api = new FileRetrievalAPI(config);
		new WinRetrieveWindow(api);
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(args);
            }
        });
	}

}
