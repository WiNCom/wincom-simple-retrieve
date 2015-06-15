package org.wincom.rfeye;

import java.io.DataInputStream;

public interface RFEyeDataBlock {
    String blockType = "";

    public String getBlockType();
    public void read(DataInputStream inputStream);
}
