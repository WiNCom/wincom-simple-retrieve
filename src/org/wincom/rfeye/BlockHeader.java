package org.wincom.rfeye;

import java.io.DataInputStream;

public class BlockHeader implements RFEyeDataBlock {
    private String blockType = "Header";
    private int threadId;
    private int blockSize;
    private int dataType;

    public BlockHeader() {

    }

    public String getBlockType() {
        return blockType;
    }

    public int getThreadId() {
        return threadId;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public int getDataType() {
        return dataType;
    }

    public void read(DataInputStream inputStream) {
        DataTypeReader reader = new DataTypeReader(inputStream);

        threadId = reader.readInt();
        blockSize = reader.readInt();
        dataType = reader.readInt();
    }
}
