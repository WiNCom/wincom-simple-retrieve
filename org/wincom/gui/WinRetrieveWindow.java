package org.wincom.gui;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.wincom.lib.DsNetAccessor;
import org.wincom.lib.FileRecord;
import org.wincom.lib.MongoAccessor;
import org.wincom.lib.WinRetrieveConfig;

public class WinRetrieveWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private MongoAccessor mongo;
	private DsNetAccessor dsNet;
	private WinRetrieveConfig config;
	
	private WinRetrieveContentPanel contentPanel;
	
	public WinRetrieveWindow(WinRetrieveConfig config) {
		this.config = config;
		dsNet = new DsNetAccessor(this.config);
		mongo = new MongoAccessor(this.config);
		
		this.setSize(1200, 1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLocationByPlatform(true);
		this.setVisible(true);
		
		displayLoginScreen();
	}
	
	private void displayLoginScreen() {
		WinRetrieveLoginScreen loginScreen = new WinRetrieveLoginScreen(config, this);
		
		this.setLayout(new GridBagLayout());
		this.add(loginScreen);
	}
	
	public void login() {
		System.out.println("Logging In With:\n\tUsername: " + config.getUsername());
		if(mongo.connect() && dsNet.testConnection()) {
			displayApplication();
		}
	}
	
	private void displayApplication() {
		this.getContentPane().removeAll();
		this.setLayout(new BorderLayout());
		
		addMenuBar();
		addSearchPanel();
		addContentPanel();
		
		refresh();
	}
	
	private void addMenuBar() {
		this.setJMenuBar(new WinRetrieveMenuBar(this));
	}
	
	private void addSearchPanel() {
		this.add(new WinRetrieveSearchPanel(mongo, this), BorderLayout.NORTH);
	}
	
	private void addContentPanel() {
		contentPanel = new WinRetrieveContentPanel(mongo, dsNet);
		this.add(contentPanel, BorderLayout.CENTER);
	}
	
	public void updateContent(ArrayList<FileRecord> newContent) {
		contentPanel.updateListings(newContent);
		refresh();
	}
	
	public void refresh() {
		this.getContentPane().validate();
		this.getContentPane().repaint();
	}
}
