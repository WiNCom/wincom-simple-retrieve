package org.wincom.lib;

import java.util.Date;

public class FileRecord {
	private String filename;
	private String dsNetId;
	private String location;
	private String sensor;
	private String format;
	private String date;
	
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

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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
