package org.wincom.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.wincom.lib.WinRetrieveConfig;

public class WinRetrieveLoginScreen extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTextField usernameField;
	private JPasswordField passwordField;
	
	private WinRetrieveConfig config;
	private WinRetrieveWindow parent;

	public WinRetrieveLoginScreen(WinRetrieveConfig config, WinRetrieveWindow parent) {
		this.config = config;
		this.add(createContentPanel());
		this.parent = parent;
	}
	
	private JPanel createContentPanel() {
		JPanel contentPanel = new JPanel(new BorderLayout());
		
		contentPanel.add(createInputPanel(), BorderLayout.NORTH);
		contentPanel.add(createButtonPanel(), BorderLayout.CENTER);
		
		return contentPanel;
	}
	
	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel(new BorderLayout());
		
		JButton login = new JButton("Login");
		login.setFocusPainted(false);
		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String username = usernameField.getText();
				char[] password = passwordField.getPassword();
				
				config.setUsername(username);
				config.setPassword(password);
				
				parent.login();
			}
		});
		
		JButton exit = new JButton("Exit");
		exit.setFocusPainted(false);
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		
		buttonPanel.add(login, BorderLayout.WEST);
		buttonPanel.add(exit, BorderLayout.EAST);
		
		return buttonPanel;
	}
	
	private JPanel createInputPanel() {
		usernameField = new JTextField();
		passwordField = new JPasswordField();
		
		JPanel usernamePanel = new JPanel(new BorderLayout());
		usernameField.setPreferredSize(new Dimension(100,1));
		usernamePanel.add(new JLabel("Username: "), BorderLayout.WEST);
		usernamePanel.add(usernameField, BorderLayout.CENTER);
		
		JPanel passwordPanel = new JPanel(new BorderLayout());
		passwordField.setPreferredSize(new Dimension(100,1));
		passwordPanel.add(new JLabel("Password: "), BorderLayout.WEST);
		passwordPanel.add(passwordField, BorderLayout.CENTER);
		
		JPanel inputPanel = new JPanel(new BorderLayout());
		inputPanel.add(usernamePanel, BorderLayout.NORTH);
		inputPanel.add(passwordPanel, BorderLayout.CENTER);
		
		return inputPanel;
	}
}
