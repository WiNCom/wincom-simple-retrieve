package org.wincom.lib;

import java.util.Date;

public class FileRecord {
	private String filename;
	private String dsNetId;
	private String location;
	private String sensor;
	private Date date;
	private int downloads;
	
	public FileRecord() {
		
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSensor() {
		return sensor;
	}

	public void setSensor(String sensor) {
		this.sensor = sensor;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getDownloads() {
		return downloads;
	}

	public void setDownloads(int downloads) {
		this.downloads = downloads;
	}

	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getDsNetId() {
		return dsNetId;
	}
	
	public void setDsNetId(String dsNetId) {
		this.dsNetId = dsNetId;
	}

}
