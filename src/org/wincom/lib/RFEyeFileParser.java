package org.wincom.lib;

import org.wincom.data.RecordingSession;
import org.wincom.rfeye.BlockHeader;
import org.wincom.rfeye.BlockTrailer;
import org.wincom.rfeye.FileHeader;
import org.wincom.rfeye.RFEyeDataBlock;
import org.wincom.rfeye.blocks.*;

import java.io.*;
import java.util.ArrayList;

public class RFEyeFileParser {

    public RFEyeFileParser() {

    }

    public RecordingSession parseFile(File fileToParse) {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileToParse);
            BufferedInputStream inputStream = new BufferedInputStream(fileInputStream);
            DataInputStream dataStream = new DataInputStream(inputStream);
            ArrayList<RFEyeDataBlock> dataBlocks = new ArrayList<RFEyeDataBlock>();


            FileHeader header = readHeader(dataStream);

            while(true) {
                if(dataStream == null)
                    break;
                dataBlocks.add(readBlock(dataStream));

            }

        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private FileHeader readHeader(DataInputStream dataStream) {
        FileHeader header = new FileHeader();
        header.read(dataStream);
        return header;
    }

    private RFEyeDataBlock readBlock(DataInputStream dataStream) {
        BlockHeader header = new BlockHeader();
        header.read(dataStream);
        RFEyeDataBlock dataBlock = null;

        switch(header.getDataType()) {
            case 1:
                dataBlock = new UnitInformation();
                break;
            case 2:
                dataBlock = new GpsData();
                break;
            case 3:
                dataBlock = new DataThreadInformation();
                break;
            case 4:
                dataBlock = new EightBitSpectralData();
                break;
            case 5:
                dataBlock = new FreeText();
                break;
            case 6:
                dataBlock = new SixteenBitIQData();
                break;
            case 7:
                dataBlock = new CompressedSamples();
                break;
            case 8:
                dataBlock = new OccupancyOverTime();
                break;
            case 9:
                dataBlock = new TypedText();
                break;
            default:
                break;
        }

        if(dataStream == null)
            System.out.println("Test");
        if(dataBlock == null)
            System.out.println("Test");
        dataBlock.read(dataStream);

        BlockTrailer trailer = new BlockTrailer();
        trailer.read(dataStream);

        return dataBlock;
    }
}
