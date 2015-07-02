package org.wincom.data;

import java.util.ArrayList;
import java.util.Date;

public class Band {
    private double startFrequency;
    private double stopFrequency;
    private int resolution;
    private ArrayList<Scan> scans;

    public Band(double startFrequency, double stopFrequency, int resolution) {
        this.startFrequency = startFrequency;
        this.stopFrequency = stopFrequency;
        this.resolution = resolution;
        scans = new ArrayList<Scan>();
    }

    public void addScan(Date startTime, Date stopTime, ArrayList<Double> powers) {
        Scan scan = new Scan(startTime, stopTime, powers);
        scans.add(scan);
    }
}
