package org.wincom.rfeye.blocks;

import org.wincom.rfeye.DataTypeReader;
import org.wincom.rfeye.RFEyeDataBlock;

import java.io.DataInputStream;

public class GpsData implements RFEyeDataBlock {
    private String blockType = "GPS Data";
    private int time;
    private int date;
    private short fix;
    private short status;
    private short sats;
    private int latitude;
    private int longitude;
    private int speed;
    private short heading;
    private int altitude;


    public GpsData() {

    }

    public String getBlockType() {
        return blockType;
    }

    public void read(DataInputStream inputStream) {
        DataTypeReader reader = new DataTypeReader(inputStream);
        time = reader.readInt();
        date = reader.readInt();
        fix = reader.readShort();
        status = reader.readShort();
        sats = reader.readShort();
        latitude = reader.readInt();
        longitude = reader.readInt();
        speed = reader.readInt();
        heading = reader.readShort();
        altitude = reader.readInt();
    }
}
