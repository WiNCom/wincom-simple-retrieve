package org.wincom.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.wincom.lib.*;

public class WinRetrieveWindow extends JFrame {
	private FileRetrievalAPI fileRetrievalAPI;
	
	public WinRetrieveWindow(FileRetrievalAPI fileRetrievalAPI) {
		this.fileRetrievalAPI = fileRetrievalAPI;
		
		this.setSize(1200, 1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLocationByPlatform(true);
		this.setVisible(true);
		
		displayApplication();
	}
	
	private void displayApplication() {
		this.setLayout(new BorderLayout());
		
		addMenuBar();
		addTabPanel();
	}
	
	private void addMenuBar() {
		this.setJMenuBar(new WinRetrieveMenuBar(this));
	}

	private void addTabPanel() {
		this.add(new WinRetrieveTabPane(fileRetrievalAPI), BorderLayout.CENTER);
	}
}
