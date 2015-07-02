package org.wincom.rfeye.blocks;

import org.wincom.rfeye.DataTypeReader;
import org.wincom.rfeye.RFEyeDataBlock;

import java.io.DataInputStream;
import java.util.ArrayList;

public class OccupancyOverTime implements RFEyeDataBlock {
    private String blockType = "Occupancy Over Time";
    private int start;
    private int stop;
    private int threshold;
    private int time;
    private int date;
    private int duration;
    private int antenna;
    private int number;
    private int npads;
    private int nbytes;
    private ArrayList<Integer> data = new ArrayList<Integer>();
    private ArrayList<Integer> pad = new ArrayList<Integer>();

    public OccupancyOverTime() {

    }

    public String getBlockType() {
        return blockType;
    }

    public void read(DataInputStream inputStream) {
        DataTypeReader reader = new DataTypeReader(inputStream);

        start = reader.readInt();
        stop = reader.readInt();
        threshold = reader.readInt();
        time = reader.readInt();
        date = reader.readInt();
        duration = reader.readInt();
        antenna = reader.readInt();
        number = reader.readInt();
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
