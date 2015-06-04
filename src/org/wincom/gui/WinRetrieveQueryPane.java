package org.wincom.gui;

import org.wincom.lib.FileRecord;
import org.wincom.lib.FileRetrievalAPI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class WinRetrieveQueryPane extends JPanel {
    private FileRetrievalAPI fileRetrievalAPI;

    private WinRetrieveContentPanel contentPanel;

    public WinRetrieveQueryPane(FileRetrievalAPI fileRetrievalAPI) {
        this.fileRetrievalAPI = fileRetrievalAPI;
        initComponents();
    }

    private void initComponents() {
        this.setLayout(new BorderLayout());
        addSearchPanel();
        addContentPanel();
        refresh();
    }

    private void addSearchPanel() {
        this.add(new WinRetrieveSearchPanel(fileRetrievalAPI, this), BorderLayout.NORTH);
    }

    private void addContentPanel() {
        contentPanel = new WinRetrieveContentPanel(fileRetrievalAPI);
        this.add(contentPanel, BorderLayout.CENTER);
    }

    public void updateContent(ArrayList<FileRecord> newContent) {
        contentPanel.updateListings(newContent);
        refresh();
    }

    public void refresh() {
        this.validate();
        this.repaint();
    }
}
