package org.wincom.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.wincom.lib.DsNetAccessor;
import org.wincom.lib.FileRecord;
import org.wincom.lib.FileRetrievalAPI;
import org.wincom.lib.MongoAccessor;

public class WinRetrieveContentPanel extends JScrollPane {
	private static final long serialVersionUID = 1L;
	
	private JTable contentTable;

	private FileRetrievalAPI fileRetrievalAPI;

	public WinRetrieveContentPanel(FileRetrievalAPI fileRetrievalAPI) {
		createTable();
		this.getViewport().add(contentTable);
	}
	
	private void createTable() {
		String[] columnNames = {"Filename", "Date", "Location", "Sensor", "DSnet ID", "Downloads"};
		Object[][] data = {};
		
		contentTable = new JTable(new DefaultTableModel(data, columnNames));
		contentTable.setFillsViewportHeight(true);
		contentTable.setShowGrid(true);
		contentTable.setGridColor(Color.GRAY);
		addPopupMenu();
	}
	
	private void addPopupMenu() {
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem downloadItem = new JMenuItem("Download");
		
		downloadItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				DefaultTableModel model = (DefaultTableModel)contentTable.getModel();
				
				int[] selectedRows = contentTable.getSelectedRows();
				for(int rowIndex : selectedRows) {
					String dsNetId = (String)model.getValueAt(rowIndex, 4);
					String filename = (String)model.getValueAt(rowIndex, 0);
					fileRetrievalAPI.downloadId(filename, dsNetId);
					fileRetrievalAPI.incrementDownloadCount(dsNetId);
				}
			}
		});
		
		popupMenu.add(downloadItem);
		contentTable.setComponentPopupMenu(popupMenu);
	}
	
	private void removeAllRows(DefaultTableModel model) {
		int rowCount = model.getRowCount();
		
		for(int i = rowCount - 1; i >= 0; i--) {
			model.removeRow(i);
		}
	}
	
	public void updateListings(ArrayList<FileRecord> newContent) {
		DefaultTableModel model = (DefaultTableModel)contentTable.getModel();
		removeAllRows(model);
		
		for(FileRecord entry : newContent) {
			String filename = entry.getFilename();
			String dsNetId = entry.getDsNetId();
			String location = entry.getLocation();
			String sensor = entry.getSensor();
			String downloads = Integer.toString(entry.getDownloads());
			String date = entry.getDate().toString();
			
			model.addRow(new Object[]{filename, date, location, sensor, dsNetId, downloads});
		}
	}

}
