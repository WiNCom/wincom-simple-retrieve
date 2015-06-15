package org.wincom.rfeye;

import java.io.DataInputStream;
import java.io.IOException;

public class DataTypeReader {
    DataInputStream inputStream;

    public DataTypeReader(DataInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String readString(int stringLength) {
        String output = "";

        try {
            for (int i = 0; i < stringLength; i++) {
                output += (char) (inputStream.readByte() & 0xFF);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        return output;
    }

    public int readInt() {
        int output = 0;

        try {
            for (int i = 0; i < 4; i++) {
                output += 0xff & inputStream.readByte();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        return output;
    }

    public char readChar() {
        char output = 'a';

        try {
            output = (char) (inputStream.readByte() & 0xFF);
        } catch(IOException e) {
            e.printStackTrace();
        }

        return output;
    }

    public short readShort() {
        short output = -1;

        try {
            for (int i = 0; i < 2; i++) {
                output += 0xff & inputStream.readByte();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        return output;
    }

    public int readByteAsInt() {
        int output = -1;

        try {
            output = 0xff & inputStream.readByte();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return output;
    }
}
