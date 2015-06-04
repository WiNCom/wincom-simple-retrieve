package org.wincom.gui;

import org.wincom.lib.FileRetrievalAPI;

import javax.swing.*;

public class WinRetrieveTabPane extends JTabbedPane {
    private WinRetrieveQueryPane queryPane;
    private WinRetrieveDebugPane debugPane;

    public WinRetrieveTabPane(FileRetrievalAPI fileDownloaderAPI) {
        initComponents(fileDownloaderAPI);
        initTabs();
    }

    private void initComponents(FileRetrievalAPI fileDownloaderAPI) {
        queryPane = new WinRetrieveQueryPane(fileDownloaderAPI);
        debugPane = new WinRetrieveDebugPane();
    }

    private void initTabs() {
        this.addTab("Query", queryPane);
        this.addTab("Debug", debugPane);
    }
}
