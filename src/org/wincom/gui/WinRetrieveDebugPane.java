package org.wincom.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class WinRetrieveDebugPane extends JPanel implements ItemListener {
    private JTextArea terminal;
    private WinRetrieveOutputStream outputStream;
    private Map<JCheckBox, String> filterSelectors;

    public WinRetrieveDebugPane() {
        init();
        registerOutputStream();
    }

    private void init() {
        filterSelectors = new HashMap<JCheckBox, String>();
        this.setLayout(new BorderLayout());
        initFilters();

        terminal = new JTextArea(30,100);
        terminal.setEditable(false);

        outputStream = new WinRetrieveOutputStream(terminal);

        JScrollPane scrollPane = new JScrollPane(terminal, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initFilters() {
        JPanel filters = new JPanel();
        filters.setLayout(new GridLayout(1, 4));

        JCheckBox mongo = new JCheckBox("MongoDB");
        mongo.setSelected(true);
        filterSelectors.put(mongo, "MongoDB");
        mongo.addItemListener(this);

        JCheckBox dsNet = new JCheckBox("DsNet");
        dsNet.setSelected(true);
        filterSelectors.put(dsNet, "DsNet");
        dsNet.addItemListener(this);

        JCheckBox messages = new JCheckBox("Messages");
        messages.setSelected(true);
        filterSelectors.put(messages, "Messages");
        messages.addItemListener(this);

        JCheckBox errors = new JCheckBox("Errors");
        errors.setSelected(true);
        filterSelectors.put(errors, "Errors");
        errors.addItemListener(this);

        filters.add(mongo);
        filters.add(dsNet);
        filters.add(messages);
        filters.add(errors);

        add(filters, BorderLayout.NORTH);
    }

    private void registerOutputStream() {
        System.setOut(new PrintStream(outputStream));
    }

    @Override
    public void itemStateChanged(ItemEvent event) {
        Object source = event.getItemSelectable();
        String sourceSelected = filterSelectors.get(source);

        outputStream.filter(sourceSelected);
    }

}
