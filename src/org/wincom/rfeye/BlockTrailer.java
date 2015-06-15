package org.wincom.rfeye;

import java.io.DataInputStream;

public class BlockTrailer implements RFEyeDataBlock {
    private String blockType = "Trailer";
    private int checksum;
    private int marker;

    public BlockTrailer() {

    }

    public String getBlockType() {
        return blockType;
    }

    public int getChecksum() {
        return checksum;
    }

    public int getMarker() {
        return marker;
    }

    public void read(DataInputStream inputStream) {
        DataTypeReader reader = new DataTypeReader(inputStream);

        checksum = reader.readInt();
        marker = reader.readInt();
    }
}
