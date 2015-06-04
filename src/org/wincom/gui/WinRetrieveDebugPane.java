package org.wincom.gui;

import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;

public class WinRetrieveDebugPane extends JPanel {
    private JTextArea terminal;
    private WinRetrieveOutputStream outputStream;

    public WinRetrieveDebugPane() {
        init();
        registerOutputStream();
    }

    private void init() {
        this.setLayout(new BorderLayout());

        terminal = new JTextArea(30,100);
        terminal.setEditable(false);

        outputStream = new WinRetrieveOutputStream(terminal);

        JScrollPane scrollPane = new JScrollPane(terminal, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);
    }

    private void registerOutputStream() {
        System.setOut(new PrintStream(outputStream));
    }

}
