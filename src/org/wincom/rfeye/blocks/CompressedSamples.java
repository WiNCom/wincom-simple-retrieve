package org.wincom.rfeye.blocks;

import org.wincom.rfeye.DataTypeReader;
import org.wincom.rfeye.RFEyeDataBlock;

import java.io.DataInputStream;
import java.util.ArrayList;

public class CompressedSamples implements RFEyeDataBlock {
    private String blockType = "Compressed Samples";
    private int start;
    private int stop;
    private int level;
    private int threshold;
    private int antenna;
    private int processing;
    private int loops;
    private int nvals;
    private int npads;
    private int nbytes;
    ArrayList<Integer> data = new ArrayList<Integer>();
    ArrayList<Integer> pad = new ArrayList<Integer>();

    public CompressedSamples() {

    }

    public String getBlockType() {
        return blockType;
    }

    public void read(DataInputStream inputStream) {
        DataTypeReader reader = new DataTypeReader(inputStream);

        start = reader.readInt();
        stop = reader.readInt();
        level = reader.readInt();
        threshold = reader.readInt();
        antenna = reader.readInt();
        processing = reader.readInt();
        loops = reader.readInt();
        nvals = reader.readInt();
        npads = reader.readInt();
        nbytes = reader.readInt();

        for(int i = 0; i < nbytes; i++) {
            data.add(reader.readByteAsInt());
        }

        for(int i = 0; i < npads; i++) {
            pad.add(reader.readByteAsInt());
        }
    }
}
