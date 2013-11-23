package com.model;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import org.json.JSONException;
import org.json.JSONObject;
public class CassandraModel {
	public final static String LOC_LIVINGROOM = "living room",
							   LOC_KITCHEN = "kitchen",
							   LOC_BATHROOM = "bathroom",
							   LOC_ROOM1 = "room1",
							   LOC_ROOM2 = "room2";
							
	public static String getResult(){
		String serverIP = "127.0.0.1";
		String keyspace = "stockwatcher";
	
		Cluster cluster = Cluster.builder()
		  .addContactPoints(serverIP)
		  .build();
	
		Session session = cluster.connect(keyspace);
		JSONObject result = new JSONObject();
		String cqlStatement = "SELECT * FROM playlists";
		for (Row row : session.execute(cqlStatement)) {
		  System.out.println(row.toString());
		  try {
			result.put(row.getInt(1)+"", row.getString(2));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  System.out.println(row.getString(2));
		}
		return result.toString();
	}
}