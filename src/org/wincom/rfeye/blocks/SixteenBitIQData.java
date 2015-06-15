package org.wincom.rfeye.blocks;

import org.wincom.rfeye.DataTypeReader;
import org.wincom.rfeye.RFEyeDataBlock;

import java.io.DataInputStream;
import java.util.ArrayList;

public class SixteenBitIQData implements RFEyeDataBlock {
    private String blockType = "16-bit IQ Data";
    private int start;
    private int stop;
    private int delta;
    private int antenna;
    private int samples;
    private int nfreqs;
    private ArrayList<Integer> gains = new ArrayList<>();
    private int nvals;
    private ArrayList<Integer> iqvals = new ArrayList<>();

    public SixteenBitIQData() {

    }

    public String getBlockType() {
        return blockType;
    }

    public void read(DataInputStream inputStream) {
        DataTypeReader reader = new DataTypeReader(inputStream);

        start = reader.readInt();
        stop = reader.readInt();
        delta = reader.readInt();
        antenna = reader.readInt();
        samples = reader.readInt();
        nfreqs = reader.readInt();
        for(int i = 0; i < nfreqs; i++) {
            gains.add(reader.readInt());
        }
        nvals = reader.readInt();
        for(int i = 0; i < nvals; i++) {
            iqvals.add(reader.readInt());
        }
    }
}
