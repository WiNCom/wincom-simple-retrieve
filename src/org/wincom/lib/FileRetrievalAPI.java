package org.wincom.lib;

import org.omg.CORBA.DATA_CONVERSION;

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
        dsNet.downloadId(filename, dsNetId);
    }

    public void incrementDownloadCount(String dsNetId) {
        mongo.incrementDownloadCount(dsNetId);
    }
}
