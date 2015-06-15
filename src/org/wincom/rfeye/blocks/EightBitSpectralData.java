package org.wincom.rfeye.blocks;

import org.wincom.rfeye.DataTypeReader;
import org.wincom.rfeye.RFEyeDataBlock;

import java.io.DataInputStream;
import java.util.ArrayList;

public class EightBitSpectralData implements RFEyeDataBlock {
    private String blockType = "8-Bit Spectral Data";
    private short start;
    private short stop;
    private int level;
    private int antenna;
    private int processing;
    private int npads;
    private int nbytes;
    private ArrayList<Integer> data = new ArrayList<>();
    private ArrayList<Integer> pads = new ArrayList<>();


    public EightBitSpectralData() {

    }

    public String getBlockType() {
        return blockType;
    }

    public void read(DataInputStream inputStream) {
        DataTypeReader reader = new DataTypeReader(inputStream);

        start = reader.readShort();
        stop = reader.readShort();
        level = reader.readByteAsInt();
        antenna = reader.readByteAsInt();
        processing = reader.readByteAsInt();
        npads = reader.readByteAsInt();
        nbytes = reader.readInt();

        for(int i = 0; i < nbytes; i++) {
            data.add(reader.readByteAsInt());
        }

        for(int i = 0; i < npads; i++) {
            pads.add(reader.readByteAsInt());
        }
    }
}
