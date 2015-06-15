package org.wincom.rfeye;

import java.io.DataInputStream;

public class FileHeader {
    private int fileVersion;
    private String filename;

    public FileHeader() {

    }

    public String getFilename() {
        return filename;
    }

    public int getFileVersion() {
        return fileVersion;
    }

    public void read(DataInputStream inputStream) {
        DataTypeReader reader = new DataTypeReader(inputStream);
        fileVersion = reader.readInt();
        filename = reader.readString(32);
    }
}
