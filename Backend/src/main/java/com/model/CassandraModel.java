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
	public final static String TABLE_TEMP = "temperature_by_day",
							   TABLE_TEMP_MONTH = "temperature_by_month",
							   TABLE_CONSUMPTION = "energy_consumption_by_day",
							   TABLE_CONSUMPTION_MONTH = "energy_consumption_by_month";
	public final static String LOC_LIVINGROOM = "living room",
							   LOC_KITCHEN = "kitchen",
							   LOC_BATHROOM = "bathroom",
							   LOC_ROOM1 = "room1",
							   LOC_ROOM2 = "room2";
	public final static String[] months = {"january","february","march","april", "may","june","july","august","september","october","november","december"};   		
	
	public static JSONObject result;
	
	
	
	
	
	
	public static JSONObject getResult(){
		
	
		Cluster cluster = Cluster.builder()
		  .addContactPoints(serverIP)
		  .build();
	
		Session session = cluster.connect(keyspace);
		
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
	public static JSONObject getDataForYear( int year,String location,String table) throws JSONException
	{
	
		String cqlStatement="";
		
		Cluster cluster = Cluster.builder()
				  .addContactPoints(serverIP)
				  .build();
		Session session = cluster.connect(keyspace);
	
		
		int data[][] = new int[4][12];
		
		
		String[] Locations;
		if(location.isEmpty())
			Locations = new String[]{"living room","kitchen","bathroom","room1"};
		else
			Locations = new String[]{location};
		int i = 0;
		JSONObject series = new JSONObject();
		
		for(int j=0;j<Locations.length;j++){
			//execute query for each location
			i=0;
			System.out.println();
			for (Row row : session.execute(getYearQuery(Locations[j], year, table))) {

					data[j][i] = row.getInt(2);
					i++;
			}
			JSONObject jsondata = new JSONObject();
			jsondata.put("name", Locations[j]);
			jsondata.put("data", data[j]);
			//al.add(jsondata);
			result.append("series",jsondata);
		}
		
		return result;
	}
	public static JSONObject getDataByMonth(String month, int year,String location,String table) throws JSONException
	{
		

		
		System.out.println("inside data"+ month);
		String cqlStatement="";
		int month_num=0;
		while(!months[month_num].equalsIgnoreCase(month))
		{
			System.out.println("month_num"+month_num+"month" + month+ "data point"+month_num);
			month_num++;
			
		}
		month_num++;
		
		Cluster cluster = Cluster.builder()
				  .addContactPoints(serverIP)
				  .build();
		Session session = cluster.connect(keyspace);
	
		
		int data[][] = new int[4][31];
		
		
		String[] Locations;
		if(location.isEmpty())
			Locations = new String[]{"living room","kitchen","bathroom","room1"};
		else
			Locations = new String[]{location};
		int i = 0;
		JSONObject series = new JSONObject();
		
		for(int j=0;j<Locations.length;j++){
			//execute query for each location
			i=0;
			System.out.println(getLocationQuery(Locations[j],year,month_num,table));
			for (Row row : session.execute(getLocationQuery(Locations[j],year,month_num,table))) {
					System.out.println(row.getString(1)+row.getString(1)+row.getInt(2));
					data[j][i] = row.getInt(2);
					i++;
			}
			JSONObject jsondata = new JSONObject();
			jsondata.put("name", Locations[j]);
			jsondata.put("data", data[j]);
			//al.add(jsondata);
			result.append("series",jsondata);
		}
		
		return result;
	}
	public static String getLocationQuery(String location,int year_num,int month_num,String table)
	{
		String month;
		if(month_num<10)
			month="0"+month_num; 
		else month=""+month_num;
		String cqlStatement = "select * "+
				"from "+table+" "+
				"where location_id = '"+location+"' "+
				"and date > '"+year_num+"-"+month+"-00' and date < '"+year_num+"-"+month+"-31';";
		return cqlStatement;
	}
	public static String getYearQuery(String location,int year_num,String table)
	{
		
		String cqlStatement = "select * "+
				"from "+table+" "+
				"where location_id = '"+location+"' "+
				"and event_time > '"+year_num+"-01-01 00:00:00' and event_time < '"+(year_num+1)+"-12-31 11:59:59';";
		System.out.println(cqlStatement);
		return cqlStatement;
	}
	
	public static JSONObject getPowerUsageForYear(int year,String location) throws JSONException
	{	
		int year_num=2013;
		if(year != -1)
			{
				year_num = year;
			}
		 result = new JSONObject();
		 
		 JSONObject xAxis = new JSONObject();
			
		 xAxis.put("title", "Power (Watts)");
		 xAxis.put("categories",months);
		 if(location.isEmpty())
			 result.put("title", "Usage for the year of "+year_num);
		 else
			 result.put("title", "Usage for the year of "+year_num+" in "+location);
		 
		 
		 result.put("xAxis", xAxis);
		 result.put("yAxis", (new JSONObject()).put("title", "Energy(W)"));
			
		 result.put("valueSuffix","W");
		 return getDataForYear(year_num, location, TABLE_CONSUMPTION_MONTH);
	}
	public static JSONObject getTempForYear(int year,String location) throws JSONException
	{	
		int year_num=2013;
		if(year != -1)
			{
				year_num = year;
			}
		 result = new JSONObject();
		 
		 JSONObject xAxis = new JSONObject();
			
		 xAxis.put("title", "Power (Watts)");
		 xAxis.put("categories",months);
		 if(location.isEmpty())
			 result.put("title", "Usage for the year of "+year_num);
		 else
			 result.put("title", "Usage for the year of "+year_num+" in "+location);
		 
		 
		 result.put("xAxis", xAxis);
		 result.put("yAxis", (new JSONObject()).put("title", "Temperature(C)"));
			
		 result.put("valueSuffix","C");
		 return getDataForYear(year_num, location, TABLE_TEMP_MONTH);
	}
	public static JSONObject getPowerUsageForMonth(String month, int year,String location) throws JSONException
	{	
		int year_num=2013;
		if(year != -1)
			{
				year_num = year;
			}
		 result = new JSONObject();
		 
		 JSONObject xAxis = new JSONObject();
			
		 xAxis.put("title", "Power (Watts)");
		 xAxis.put("categories",getDaysOfMonth(month,year_num));
		 if(location.isEmpty())
			 result.put("title", "Usage for the month of "+month+" "+year_num);
		 else
			 result.put("title", "Usage for the month of "+month+" "+year_num+" in "+location);
		 
		 
		 result.put("xAxis", xAxis);
		 result.put("yAxis", (new JSONObject()).put("title", "Months"));
			
		 result.put("valueSuffix","W");
		 System.out.println(month);
		 return getDataByMonth(month, year_num,location,TABLE_CONSUMPTION);
	}
	public static JSONObject getTemperatureForMonth(String month, int year,String location) throws JSONException
	{
		
		int year_num=2013;
		if(year != -1)
		{
			year_num = year;
		}
		
		result = new JSONObject();
		
		JSONObject xAxis = new JSONObject();
		xAxis.put("title", "Temperature (Celsius)");
		xAxis.put("categories",getDaysOfMonth(month,year_num));
		if(location.isEmpty())
			 result.put("title", "Usage for the month of "+month+" "+year_num);
		 else
			 result.put("title", "Usage for the month of "+month+" "+year_num+" in "+location);
		result.put("xAxis", xAxis);
		
		result.put("yAxis", (new JSONObject()).put("title", "Months"));
		
		result.put("valueSuffix","°C");
		System.out.println(month);
		return getDataByMonth(month, year_num,location,TABLE_TEMP);
	}
	
	public static String[] getDaysOfMonth(String month,int year)
	{
		String[] result = new String[30];
		for(int i=0;i<30;i++)
		{
			result[i] = (i+1)+"";
		}
		return result;
		
	}
}