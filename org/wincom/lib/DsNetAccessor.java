package org.wincom.lib;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;

public class DsNetAccessor {
	private static final int BUFFER_SIZE = 4096;
	
	private String username;
	private char[] password;
	
	private String hostname;
	private String vaultType;
	private String vaultName;
	private String outputDirectory;
	
	public DsNetAccessor(WinRetrieveConfig config) {
		username = config.getUsername();
		password = config.getPassword();
		
		hostname = config.getField("DSnet_Hostname");
		vaultType = config.getField("DSnet_Type");
		vaultName = config.getField("DSnet_Vault");
		outputDirectory = config.getField("Output_Directory");
		
		setupAuthenticator(username, password);
	}
	
	public boolean testConnection() {
		return true;
	}
	
	public void downloadId(String filename, String dsNetId) {
		String stringUrl = String.format("http://%s/%s/%s/%s", hostname, vaultType, vaultName, dsNetId);
		
		try {	
			URL url = new URL(stringUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			int responseCode = connection.getResponseCode();
			if(responseCode != HttpURLConnection.HTTP_OK) {
				System.out.println("Cannot Connect to DsNet!");
				return;
			};
			
			InputStream inputStream = connection.getInputStream();
			String saveFilePath = outputDirectory + File.separator + filename;
			
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);
			System.out.println("Downloading: " + filename + "\n\tDSnet ID: " + dsNetId);
			int bytesRead = -1;
			byte[] buffer = new byte[BUFFER_SIZE];
			while((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			
			outputStream.close();
			inputStream.close();
		} catch(MalformedURLException e) {
			e.printStackTrace();
			return;
		} catch(IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	private void setupAuthenticator(String username, char[] password) {
		Authenticator.setDefault(new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
	}

}
