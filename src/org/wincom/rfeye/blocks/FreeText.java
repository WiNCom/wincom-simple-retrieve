package org.wincom.rfeye.blocks;

import org.wincom.rfeye.DataTypeReader;
import org.wincom.rfeye.RFEyeDataBlock;

import java.io.DataInputStream;

public class FreeText implements RFEyeDataBlock {
    private String blockType = "Free Text";
    private int textLength;
    private String text;

    public FreeText() {

    }

    public String getBlockType() {
        return blockType;
    }

    public void read(DataInputStream inputStream) {
        DataTypeReader reader = new DataTypeReader(inputStream);

        textLength = reader.readInt();
        text = reader.readString(textLength);
    }
}
