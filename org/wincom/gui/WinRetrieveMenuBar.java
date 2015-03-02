package org.wincom.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class WinRetrieveMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	
	private JFrame parent;

	public WinRetrieveMenuBar(JFrame parent) {
		this.parent = parent;
		
		initFileMenu();
		initEditMenu();
		initHelpMenu();
	}
	
	private void initFileMenu() {
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem configMongo = new JMenuItem("Configure Mongo");
		JMenuItem configDSnet = new JMenuItem("Configure DSnet");
		
		/*configMongo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				new MongoConfigWindow(parent);
			}
		});
		
		configDSnet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				new DSnetConfigWindow(parent);
			}
		});
		*/
		fileMenu.add(configMongo);
		fileMenu.add(configDSnet);
		
		this.add(fileMenu);
	}
	
	private void initEditMenu() {
		JMenu editMenu = new JMenu("Edit");
		
		this.add(editMenu);
	}
	
	private void initHelpMenu() {
		JMenu helpMenu = new JMenu("Help");
		
		this.add(helpMenu);
	}
}
