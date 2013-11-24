

/* 
A range query
SELECT temperature
FROM temperature
WHERE location_id='room1'
AND event_time > '2013-04-03 07:01:00'
AND event_time < '2013-04-03 07:04:00';
*/

CREATE TABLE temperature (
   location_id text,
   event_time timestamp,
   temperature int,
   PRIMARY KEY (location_id,event_time)
);

/*
Note the (weatherstation_id,date) portion. 
When we do that in the PRMARY KEY definition, 
the key will be compounded with the two elements. 
Now when we insert data, the key will group all weather 
data for a single day on a single row.
*/

/* show temp for jan 2013
SELECT *
FROM temperature_by_day
WHERE weatherstation_id='room1'
AND date in ('2013-01-01', '2013-01-02', '2013-01-03', '2013-01-04')
*/

CREATE TABLE temperature_by_day (
   location_id text,
   date text,
   temperature int,
   PRIMARY KEY (location_id,date)
);

CREATE TABLE temperature_by_month (
   location_id text,
   event_time timestamp,
   temperature int,
   PRIMARY KEY (location_id,event_time)
);

/*
keep record of the last temperatures using TTL
*/
CREATE TABLE latest_temperatures (
   location_id text,
   event_time timestamp,
   temperature int,
   PRIMARY KEY (location_id,event_time),
) WITH CLUSTERING ORDER BY (event_time DESC);



CREATE TABLE energy_consumption (
   location_id text,
   event_time timestamp,
   energy_consumption int,
   PRIMARY KEY (location_id,event_time)
);

CREATE TABLE energy_consumption_by_day (
   location_id text,
   date text,
   energy_consumption int,
   PRIMARY KEY (location_id,date)
);

CREATE TABLE energy_consumption_by_month (
   location_id text,
   event_time timestamp,
   energy_consumption int,
   PRIMARY KEY (location_id,event_time)
);

CREATE TABLE latest_energy_consumption (
   location_id text,
   event_time timestamp,
   energy_consumption int,
   PRIMARY KEY (location_id,event_time),
) WITH CLUSTERING ORDER BY (event_time DESC);


