package org.wincom.lib;

import org.wincom.external.WinRetrieveConfigReader;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class FileRetrievalAPI {
    private MongoAccessor mongo;
    private DsNetAccessor dsNet;
    private WinRetrieveConfigReader config;

    public FileRetrievalAPI(WinRetrieveConfigReader config) {
        this.config = config;

        mongo = new MongoAccessor(config);
        dsNet = new DsNetAccessor(config);
    }

    public ArrayList<String> getDistinct(String columnName) {
        return mongo.getDistinct(columnName);
    }

    public ArrayList<FileRecord> mongoSearch(Map<String, String> criteria, Map<String, Date> dateCriteria) {
        return mongo.search(criteria, dateCriteria);
    }

    public void downloadId(String filename, String dsNetId) {
        (new Thread(new FileDownloaderThread(dsNet, mongo, filename, dsNetId))).start();
    }

    public void downloadMany(ArrayList<String> filenames, ArrayList<String> dsNetIds) {
        (new Thread(new FileDownloaderThread(dsNet, mongo, filenames, dsNetIds))).start();
    }
}
