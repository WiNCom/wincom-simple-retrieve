package org.wincom.gui;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

public class WinRetrieveOutputStream extends OutputStream {

    private final JTextArea terminal;
    private final StringBuilder stringBuilder = new StringBuilder();

    public WinRetrieveOutputStream(JTextArea terminal) {
        this.terminal = terminal;
    }

    @Override
    public void flush() {

    }

    @Override
    public void close() {

    }

    @Override
    public void write(int nextByte) throws IOException {
        if(nextByte =='\r') {
            return;
        } else if(nextByte == '\n') {
            final String textToAppend = stringBuilder.toString() + "\n";
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    terminal.append(textToAppend);
                    terminal.setCaretPosition(terminal.getDocument().getLength());
                }
            });

            stringBuilder.setLength(0);
        } else {
            stringBuilder.append((char)nextByte);
        }
    }
}
