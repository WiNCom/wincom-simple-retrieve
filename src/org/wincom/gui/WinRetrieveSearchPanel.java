package org.wincom.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.sourceforge.jdatepicker.DateModel;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import org.wincom.lib.FileRecord;
import org.wincom.lib.FileRetrievalAPI;

public class WinRetrieveSearchPanel extends JPanel {
	private FileRetrievalAPI fileRetrievalAPI;

	private WinRetrieveQueryPane parent;
	private Map<String, String> mongoFieldMap = new HashMap<>();
	private Map<String, JDatePickerImpl> dateFields = new HashMap<>();
	private Map<String, JComboBox<String>> selectedFields = new HashMap<>();

    private SimpleDateFormat dateFormat = new SimpleDateFormat("M-d-yyyy");

	public WinRetrieveSearchPanel(FileRetrievalAPI fileRetrievalAPI, WinRetrieveQueryPane parent) {
        this.fileRetrievalAPI = fileRetrievalAPI;
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
		
		dateFields.put(label, datePicker);
		
		datePanel.add(new JLabel(label + ": "));
		datePanel.add(datePicker);
		
		return datePanel;
	}
	
	private JPanel siteSelector() {
		JPanel siteSelector = new JPanel();
		siteSelector.setLayout(new GridLayout(1,2));
		
		JComboBox<String> sites = new JComboBox<>();
		sites.addItem("Any");
		
		ArrayList<String> siteList = fileRetrievalAPI.getDistinct("site");
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
		
		JComboBox<String> sensors = new JComboBox<>();
		sensors.addItem("Any");
		
		ArrayList<String> sensorList = fileRetrievalAPI.getDistinct("format");
		for(String sensor : sensorList) {
			sensors.addItem(sensor);
		}
		
		selectedFields.put("Sensor", sensors);
		
		sensorSelector.add(new JLabel("Sensor: "));
		sensorSelector.add(sensors);
		
		return sensorSelector;
	}
	
	private void clearSearchFields() {
		for(Map.Entry<String, JDatePickerImpl> entry : dateFields.entrySet()) {
            entry.getValue().getModel().setSelected(false);
		}
		
		for(Map.Entry<String, JComboBox<String>> entry : selectedFields.entrySet()) {
			entry.getValue().setSelectedItem("Any");
		}
	}
	
	private ArrayList<FileRecord> executeSearch() {
		System.out.println("[+] Search Pane: Extracting Search Criteria");
		Map<String, String> criteria = new HashMap<>();
        Map<String, Date> dateCriteria = new HashMap<>();

        if(dateFields.get("From Date").getModel().isSelected())
            dateCriteria.put("from", getDateFromModel(dateFields.get("From Date").getModel()));
        if(dateFields.get("To Date").getModel().isSelected())
            dateCriteria.put("to", getDateFromModel(dateFields.get("To Date").getModel()));
		
		for(Map.Entry<String, JComboBox<String>> entry : selectedFields.entrySet()) {
			String label = entry.getKey();
			String value = entry.getValue().getSelectedItem().toString();
			
			if(!value.equals("Any"))
				criteria.put(mongoFieldMap.get(label), value);
		}
		
		ArrayList<FileRecord> results = fileRetrievalAPI.mongoSearch(criteria, dateCriteria);
		return results;
	}

    private Date getDateFromModel(DateModel model) {
        Date formattedDate = new Date();
        int day = model.getDay();
        int month = model.getMonth()+1;
        int year = model.getYear();
        String date = month + "-" + day + "-" + year;

        try {
            formattedDate = dateFormat.parse(date);
        } catch(ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;
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
