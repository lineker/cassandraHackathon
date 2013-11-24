package com.model;

import java.util.ArrayList;
import java.util.Map;

import antlr.collections.List;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import org.json.JSONException;
import org.json.JSONObject;
public class CassandraModel {
	public final static String keyspace = "homeewatcher";
	public final static  String serverIP = "127.0.0.1";
	public final static String LOC_LIVINGROOM = "living room",
							   LOC_KITCHEN = "kitchen",
							   LOC_BATHROOM = "bathroom",
							   LOC_ROOM1 = "room1",
							   LOC_ROOM2 = "room2";
	public final static String[] months = {"january","february","march","april", "may","june","july","august","september","october","november","december"};   		
	
	
	
	
	
	
	
	
	public static JSONObject getResult(){
		
	
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
		return result;
	}
	public static JSONObject getDataByMonth(String month, int year,String location) throws JSONException
	{
		int year_num=2013;
		if(year != -1)
		{
			year_num = year;
		}
		
		Cluster cluster = Cluster.builder()
				  .addContactPoints(serverIP)
				  .build();
		JSONObject result = new JSONObject();
		result.append("title", "Months x Temperature");
		result.append("categories",getDaysOfMonth(month,year_num) );
		result.append("yAxis", "Days");
		result.append("valueSuffix","Celsius");
		int month_num=0;
		
		String cqlStatement="";
		
		while(!months[month_num].equalsIgnoreCase(month))
		{
			month_num++;
		}
		month_num++;
		
		
		Session session = cluster.connect(keyspace);
	
		
		int data[][] = new int[4][31];
		
		
		String[] Locations = {"living room","kitchen","bathroom","room1","room2"};
		int i = 0;
		JSONObject series = new JSONObject();
		ArrayList al = new ArrayList();
		for(int j=0;j<4;j++){
			//execute query for each location
			System.out.println(getLocationQuery(Locations[j],year_num,month_num));
			for (Row row : session.execute(getLocationQuery(Locations[j],year_num,month_num))) {
					System.out.println(row.getString(1)+row.getString(1)+row.getInt(2));
					data[j][i] = row.getInt(2);
					i++;
			}
			JSONObject jsondata = new JSONObject();
			jsondata.append("Location", Locations[j]);
			jsondata.append("Data", data[j]);
			al.add(jsondata);
		}
		
		result.append("series",al);
		return result;
	}
	public static String getLocationQuery(String location,int year_num,int month_num)
	{
		String month;
		if(month_num<10)
			month="0"+month_num; 
		else month=""+month_num;
		String cqlStatement = "select * "+
				"from temperature_by_day "+
				"where location_id = '"+location+"' "+
				"and date > '"+year_num+"-"+month+"-00' and date < '"+year_num+"-"+month_num+"-31';";
		return cqlStatement;
	}
	public static String[] getDaysOfMonth(String month,int year)
	{
		String[] result = new String[30];
		for(int i=0;i<30;i++)
		{
			result[i] = (i+1)+"-"+month+"-"+year;
		}
		return result;
		
	}
}