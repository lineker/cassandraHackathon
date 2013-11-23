

/* A range query
SELECT temperature
FROM temperature
WHERE location_id='room1'
AND event_time > '2013-04-03 07:01:00'
AND event_time < '2013-04-03 07:04:00';
*/

CREATE TABLE temperature (
   location_id text,
   event_time timestamp,
   temperature text,
   PRIMARY KEY (location_id,event_time)
);

/*
Note the (weatherstation_id,date) portion. 
When we do that in the PRMARY KEY definition, 
the key will be compounded with the two elements. 
Now when we insert data, the key will group all weather 
data for a single day on a single row.
*/

/*
SELECT *
FROM temperature_by_day
WHERE weatherstation_id='1234ABCD'
AND date='2013-04-03';  AND date in ('xxx', 'ssss', 'ssss')
*/

CREATE TABLE temperature_by_day (
   location_id text,
   date text,
   temperature text,
   PRIMARY KEY (location_id,date)
);


/*keep record of the last temperatures using TTL*/
CREATE TABLE latest_temperatures (
   location_id text,
   event_time timestamp,
   temperature text,
   PRIMARY KEY (location_id,event_time),
) WITH CLUSTERING ORDER BY (event_time DESC);



CREATE TABLE energy_consumption (
   location_id text,
   event_time timestamp,
   energy_consumption text,
   PRIMARY KEY (location_id,event_time)
);

CREATE TABLE energy_consumption_by_day (
   location_id text,
   date text,
   energy_consumption text,
   PRIMARY KEY (location_id,date)
);

CREATE TABLE latest_energy_consumption (
   location_id text,
   event_time timestamp,
   energy_consumption text,
   PRIMARY KEY (location_id,event_time),
) WITH CLUSTERING ORDER BY (event_time DESC);


