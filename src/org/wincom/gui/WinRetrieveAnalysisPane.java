package org.wincom.gui;

import org.wincom.lib.RFEyeFileParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class WinRetrieveAnalysisPane extends JPanel implements ActionListener {
    private WinRetrieveAnalysisTabPane analysisTabPane;
    private JPanel fileChooser;
    private JTextField textField;

    public WinRetrieveAnalysisPane() {
        initComponents();
    }

    private void initComponents() {
        this.setLayout(new BorderLayout());
        addFileChooserPanel();
        addTabbedPane();
    }

    private void addFileChooserPanel() {
        fileChooser = new JPanel();
        fileChooser.setLayout(new BorderLayout());

        fileChooser.add(new JLabel("Source File: "), BorderLayout.WEST);

        textField = new JTextField(50);
        textField.setEditable(false);
        fileChooser.add(textField, BorderLayout.CENTER);

        JButton open = new JButton("Open");
        open.addActionListener(this);
        open.setFocusPainted(false);
        fileChooser.add(open, BorderLayout.EAST);

        this.add(fileChooser, BorderLayout.NORTH);
    }

    private void addTabbedPane() {
        analysisTabPane = new WinRetrieveAnalysisTabPane();
        this.add(analysisTabPane, BorderLayout.CENTER);
    }

    private void loadFile(File inputFile) {
        if(inputFile.getAbsolutePath().contains(".bin")) {
            RFEyeFileParser parser = new RFEyeFileParser();
            parser.parseFile(inputFile);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooserDialog = new JFileChooser();
        fileChooserDialog.showDialog(this, "Open File");
        File file = fileChooserDialog.getSelectedFile();

        textField.setText(file.getAbsolutePath());
        loadFile(file);
    }

}
