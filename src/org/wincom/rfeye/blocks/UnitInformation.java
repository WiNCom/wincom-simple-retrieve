package org.wincom.rfeye.blocks;

import org.wincom.rfeye.DataTypeReader;
import org.wincom.rfeye.RFEyeDataBlock;

import java.io.DataInputStream;

public class UnitInformation implements RFEyeDataBlock {
    private String blockType = "Unit Information";
    private String asm;
    private int textLength;
    private String text;
    private int[] antennas = new int[4];

    public UnitInformation() {

    }

    public String getBlockType() {
        return blockType;
    }

    public void print() {
        System.out.println("Unit Information: ");
        System.out.println("\tASM: " + asm);
        System.out.println("\tText Length: " + textLength);
        System.out.println("\tText: " + text);
        System.out.println("\tAntennas: ");
        System.out.println("\t\tAntenna 1: " + antennas[0]);
        System.out.println("\t\tAntenna 2: " + antennas[1]);
        System.out.println("\t\tAntenna 3: " + antennas[2]);
        System.out.println("\t\tAntenna 4: " + antennas[3]);
    }

    public void read(DataInputStream inputStream) {
        DataTypeReader reader = new DataTypeReader(inputStream);
        asm = reader.readString(16);
        textLength = reader.readInt();
        text = reader.readString(textLength);
        antennas[0] = reader.readByteAsInt();
        antennas[1] = reader.readByteAsInt();
        antennas[2] = reader.readByteAsInt();
        antennas[3] = reader.readByteAsInt();
    }
}
