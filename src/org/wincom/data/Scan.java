package org.wincom.data;

import java.util.ArrayList;
import java.util.Date;

public class Scan {
    private Date startTime;
    private Date stopTime;
    private ArrayList<Double> measurements;

    public Scan(Date startTime, Date stopTime, ArrayList<Double> measurements) {
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.measurements = measurements;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public ArrayList<Double> getMeasurements() {
        return measurements;
    }
}
