package org.wincom.lib;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoTimeoutException;
import com.mongodb.ServerAddress;

public class MongoAccessor {
	private WinRetrieveConfig config;
	
	private DB db;
	private DBCollection collection;
	private MongoClient mongoClient;
	
	public MongoAccessor(WinRetrieveConfig config) {
		this.config = config;
	}
	
	public boolean connect() {
		String host = config.getField("Mongo_Hostname");
		int port = Integer.parseInt(config.getField("Mongo_Port"));
		
		String username = config.getUsername();
		char[] password = config.getPassword();
		
		String database = config.getField("Mongo_Database");
		String collectionName = config.getField("Mongo_Collection");
		
		MongoCredential credentials = MongoCredential.createMongoCRCredential(username, database, password);
		List<String> databases = new ArrayList<String>();
		
		try {
			ServerAddress address = new ServerAddress(host, port);
			//mongoClient = new MongoClient(address, Arrays.asList(credentials));
			mongoClient = new MongoClient(address);
			databases = mongoClient.getDatabaseNames();
		} catch (UnknownHostException e) {
			System.out.println("Host Name '" + host + "' is invalid!");
			return false;
		} catch(MongoTimeoutException e) {
			System.out.println("We can't seem to connect to mongo");
			return false;
		}
		
		if(!databases.contains(database)) {
			System.out.println("Database Doesn't Exist Within Mongo!");
			return false;
		};
		
		db = mongoClient.getDB(database);
		
		if(!db.collectionExists(collectionName)) {
			System.out.println("Collection Doesn't Exist Within Database!");
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
	
	public ArrayList<FileRecord> search(Map<String, String> criteria) {
		ArrayList<FileRecord> results = new ArrayList<FileRecord>();
		BasicDBObject query = new BasicDBObject();
		
		Iterator iterator = criteria.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String,String> entry = (Map.Entry<String,String>)iterator.next();
			query.append(entry.getKey(), entry.getValue());
		}
		
		DBCursor queryResults = collection.find(query);
		try {
			while(queryResults.hasNext()) {
				DBObject result = queryResults.next();
				
				FileRecord record = new FileRecord();
				record.setFilename((String)result.get("filename"));
				record.setLocation((String)result.get("site"));
				record.setDsNetId((String)result.get("dsnet_id"));
				record.setDownloads((int)result.get("downloads"));
				record.setSensor((String)result.get("format"));
				record.setDate((Date)result.get("date"));
				
				results.add(record);
			}
		} finally {
			queryResults.close();
		}
		
		return results;
	}
	
}
