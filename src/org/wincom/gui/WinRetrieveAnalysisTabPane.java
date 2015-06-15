package org.wincom.gui;

import javax.swing.*;

public class WinRetrieveAnalysisTabPane extends JTabbedPane {
    private WinRetrieveBandPlanPane bandPlanPane;
    private WinRetrieveDataPane dataPane;

    public WinRetrieveAnalysisTabPane() {
        initComponents();
        initTabs();
    }

    private void initComponents() {
        bandPlanPane = new WinRetrieveBandPlanPane();
        dataPane = new WinRetrieveDataPane();
    }

    private void initTabs() {
        this.addTab("Band Plan", bandPlanPane);
        this.addTab("Data", dataPane);
    }
}
