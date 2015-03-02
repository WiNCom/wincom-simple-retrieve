package org.wincom.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import org.wincom.lib.FileRecord;
import org.wincom.lib.MongoAccessor;

public class WinRetrieveSearchPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private MongoAccessor mongo;
	private WinRetrieveWindow parent;
	private Map<String, String> mongoFieldMap = new HashMap<String, String>();
	private Map<String, JDatePickerImpl> dateFields = new HashMap<String, JDatePickerImpl>();
	private Map<String, JComboBox<String>> selectedFields = new HashMap<String, JComboBox<String>>();

	public WinRetrieveSearchPanel(MongoAccessor mongo, WinRetrieveWindow parent) {
		this.mongo = mongo;
		this.parent = parent;
		
		this.setLayout(new GridLayout(3,2));
		initializeMongoMap();
				
		this.add(datePanel("From Date"));
		this.add(datePanel("To Date"));
		this.add(siteSelector());
		this.add(sensorSelector());
		this.add(clearButton());
		this.add(searchButton());
	}
	
	private void initializeMongoMap() {
		mongoFieldMap.put("Site", "site");
		mongoFieldMap.put("Sensor", "format");
	}
	
	private JPanel datePanel(String label) {
		JPanel datePanel = new JPanel();
		datePanel.setLayout(new GridLayout(1,2));
		
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePickPanel = new JDatePanelImpl(model);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePickPanel);
		setDateToday(datePicker);
		
		dateFields.put(label, datePicker);
		
		datePanel.add(new JLabel(label + ": "));
		datePanel.add(datePicker);
		
		return datePanel;
	}
	
	private JPanel siteSelector() {
		JPanel siteSelector = new JPanel();
		siteSelector.setLayout(new GridLayout(1,2));
		
		JComboBox<String> sites = new JComboBox<String>();
		sites.addItem("Any");
		
		ArrayList<String> siteList = mongo.getDistinct("site");
		for(String site : siteList) {
			sites.addItem(site);
		}
		
		selectedFields.put("Site", sites);
		
		siteSelector.add(new JLabel("Site: "));
		siteSelector.add(sites);
		
		return siteSelector;
	}
	
	private JPanel sensorSelector() {
		JPanel sensorSelector = new JPanel();
		sensorSelector.setLayout(new GridLayout(1,2));
		
		JComboBox<String> sensors = new JComboBox<String>();
		sensors.addItem("Any");
		
		ArrayList<String> sensorList = mongo.getDistinct("format");
		for(String sensor : sensorList) {
			sensors.addItem(sensor);
		}
		
		selectedFields.put("Sensor", sensors);
		
		sensorSelector.add(new JLabel("Sensor: "));
		sensorSelector.add(sensors);
		
		return sensorSelector;
	}
	
	private void setDateToday(JDatePickerImpl datePicker) {
		Calendar now = Calendar.getInstance();
		
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH);
		int day = now.get(Calendar.DAY_OF_MONTH);		
		
		datePicker.getModel().setDate(year, month, day);
		datePicker.getModel().setSelected(true);
	}
	
	private void clearSearchFields() {
		for(Map.Entry<String, JDatePickerImpl> entry : dateFields.entrySet()) {
			setDateToday(entry.getValue());
		}
		
		for(Map.Entry<String, JComboBox<String>> entry : selectedFields.entrySet()) {
			entry.getValue().setSelectedItem("Any");
		}
	}
	
	private ArrayList<FileRecord> executeSearch() {
		System.out.println("Executing Search...");
		Map<String, String> criteria = new HashMap<String, String>();
		
		for(Map.Entry<String, JDatePickerImpl> entry : dateFields.entrySet()) {
			
		}
		
		for(Map.Entry<String, JComboBox<String>> entry : selectedFields.entrySet()) {
			String label = entry.getKey();
			String value = entry.getValue().getSelectedItem().toString();
			
			if(!value.equals("Any"))
				criteria.put(mongoFieldMap.get(label), value);
		}
		
		ArrayList<FileRecord> results = mongo.search(criteria);	
		
		for(FileRecord entry : results) {
			System.out.println("DsNet ID: " + entry.getDsNetId());
		}
		
		return results;
	}
	
	private JButton clearButton() {
		JButton clearButton = new JButton("Clear");
		
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				clearSearchFields();
			}
		});
		
		return clearButton;
	}
	
	private JButton searchButton() {
		JButton searchButton = new JButton("Search");
		
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				ArrayList<FileRecord> searchResults = executeSearch();
				parent.updateContent(searchResults);
			}
		});
		
		return searchButton;
	}

}
