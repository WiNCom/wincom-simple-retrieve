package org.wincom.data;

import java.util.ArrayList;
import java.util.Date;

public class RecordingSession {
    private String filename;
    private Date filedate;
    private String location;
    private ArrayList<Band> bands;

    public RecordingSession(String filename, Date filedate, String location, ArrayList<Band> bands) {
        this.filename = filename;
        this.filedate = filedate;
        this.location = location;
        this.bands = bands;
    }
}
