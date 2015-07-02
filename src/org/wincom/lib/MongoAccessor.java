package org.wincom.lib;

import java.net.UnknownHostException;
import java.util.*;

import com.mongodb.*;
import org.wincom.external.WinRetrieveConfigReader;

public class MongoAccessor {
	private WinRetrieveConfigReader config;
	
	private DB db;
	private DBCollection collection;
	private MongoClient mongoClient;
	
	public MongoAccessor(WinRetrieveConfigReader config) {
		this.config = config;
        connect();
	}
	
	private boolean connect() {
		String host = config.getField("Mongo_Hostname");
		int port = Integer.parseInt(config.getField("Mongo_Port"));

		String database = config.getField("Mongo_Database");
		String collectionName = config.getField("Mongo_Collection");

		String username = config.getField("Mongo_Username");
      char[] password = config.getField("Mongo_Password").toCharArray();
      MongoCredential credential = MongoCredential.createCredential(username, database, password);
		
		try {
			ServerAddress address = new ServerAddress(host, port);
			mongoClient = new MongoClient(address, Collections.singletonList(credential));
		} catch (UnknownHostException e) {
			System.out.println("[-] Mongo: Host Name '" + host + "' is invalid!");
			return false;
		} catch(MongoTimeoutException e) {
			System.out.println("[-] Mongo: We can't seem to connect to mongo");
			return false;
		}

		db = mongoClient.getDB(database);
		
		if(!db.collectionExists(collectionName)) {
			System.out.println("[-] Mongo: Collection Doesn't Exist Within Database!");
			return false;
		}
		
		collection = db.getCollection(collectionName);
		
		return true;
	}
	
	public void disconnect() {
		mongoClient.close();
	}
	
	public void incrementDownloadCount(String dsNetId) {
		BasicDBObject update = new BasicDBObject().append("$inc",
				new BasicDBObject().append("downloads", 1));
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.append("dsnet_id", dsNetId);
		
		collection.update(searchQuery, update);
	}
	
	public ArrayList<String> getDistinct(String columnName) {
		ArrayList<String> distinctResults = new ArrayList<String>();
		
		List<String> queryResults = collection.distinct(columnName);
		Collections.sort(queryResults);
		
		for(Object currentResult : queryResults) {
			String stringSite = (String)currentResult;
			distinctResults.add(stringSite);
		}
		
		return distinctResults;
	}
	
	public ArrayList<FileRecord> search(Map<String, String> criteria, Map<String, Date> dateCriteria) {
		ArrayList<FileRecord> results = new ArrayList<FileRecord>();
		BasicDBObject query = new BasicDBObject();

        if(dateCriteria.size() == 2) {
            query.put("date", BasicDBObjectBuilder.start("$gte", dateCriteria.get("from")).add("$lte", dateCriteria.get("to")).get());
        } else if(dateCriteria.size() == 1) {
            if(dateCriteria.containsKey("from"))
                query.put("date", new BasicDBObject("$gte", dateCriteria.get("from")));
            else
                query.put("date", new BasicDBObject("$lte", dateCriteria.get("to")));
        }
		
		Iterator iterator = criteria.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String,String> entry = (Map.Entry)iterator.next();
			query.append(entry.getKey(), entry.getValue());
		}

		System.out.println("[+] Mongo: Executing Mongo Query: " + query.toString());
		
		DBCursor queryResults = collection.find(query);
		try {
			while(queryResults.hasNext()) {
				DBObject result = queryResults.next();
				
				FileRecord record = new FileRecord();
				record.setFilename((String)result.get("filename"));
				record.setLocation((String)result.get("site"));
				record.setDsNetId((String)result.get("dsnet_id"));
				record.setDownloads((Integer)result.get("downloads"));
				record.setSensor((String)result.get("format"));
				record.setDate((Date)result.get("date"));
				
				results.add(record);
			}
		} finally {
			queryResults.close();
		}

		System.out.println("[+] Mongo: Returned " + results.size() + " Record(s)");
		return results;
	}
	
}
