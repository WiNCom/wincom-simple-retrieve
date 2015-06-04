package org.wincom.lib;

import java.util.ArrayList;

public class FileDownloaderThread implements Runnable {
    private DsNetAccessor dsNet;
    private MongoAccessor mongo;
    private ArrayList<String> filenames;
    private ArrayList<String> dsNetIds;

    public FileDownloaderThread(DsNetAccessor dsNet, MongoAccessor mongo, String filename, String dsNetId) {
        this.dsNet = dsNet;
        this.mongo = mongo;
        filenames = new ArrayList<>();
        dsNetIds = new ArrayList<>();

        filenames.add(filename);
        dsNetIds.add(dsNetId);
    }

    public FileDownloaderThread(DsNetAccessor dsNet, MongoAccessor mongo, ArrayList<String> filenames, ArrayList<String> dsNetIds) {
        if(filenames.size() != dsNetIds.size()) {
            System.out.println("[-] Downloader: Filenames and DsNet IDs are out of sync! Skipping download...");
            this.filenames = new ArrayList<>();
            this.dsNetIds = new ArrayList<>();
        } else {
            this.dsNet = dsNet;
            this.mongo = mongo;
            this.filenames = filenames;
            this.dsNetIds = dsNetIds;
        }
    }

    @Override
    public void run() {
        String filename;
        String dsNetId;

        if(filenames.size() > 0)
            System.out.println("[+] Downloader: Starting Download of " + filenames.size() + " Files");

        for(int i = 0; i < filenames.size(); i++) {
            filename = filenames.get(i);
            dsNetId = dsNetIds.get(i);

            System.out.println("[+] Downloader: Downloading File: " + filename);
            dsNet.downloadId(filename, dsNetId);
            mongo.incrementDownloadCount(dsNetId);
            System.out.println("[+] Downloader: Completed Downloading File: " + filename);
        }

        System.out.println("[+] Downloader: Completed Downloading All Files");
    }
}
