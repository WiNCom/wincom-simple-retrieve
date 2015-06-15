package org.wincom.rfeye.blocks;

import org.wincom.rfeye.DataTypeReader;
import org.wincom.rfeye.RFEyeDataBlock;

import java.io.DataInputStream;

public class TypedText implements RFEyeDataBlock {
    private String blockType = "Typed Text";
    private int type;
    private int descriptionLength;
    private String description;
    private int contentLength;
    private String content;

    public TypedText() {

    }

    public String getBlockType() {
        return blockType;
    }

    public void read(DataInputStream inputStream) {
        DataTypeReader reader = new DataTypeReader(inputStream);

        type = reader.readInt();
        descriptionLength = reader.readInt();
        description = reader.readString(descriptionLength);
        contentLength = reader.readInt();
        content = reader.readString(contentLength);
    }
}
