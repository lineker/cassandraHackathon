INSERT INTO temperature(location_id,event_time,temperature)
VALUES ('kitchen','2013-01-31 07:01:00',23);
INSERT INTO temperature(location_id,event_time,temperature)
VALUES ('room1','2013-04-03 07:02:00',34);
 
INSERT INTO temperature(location_id,event_time,temperature)
VALUES ('room2','2013-04-03 07:03:00',35);
 
INSERT INTO temperature(location_id,event_time,temperature)
VALUES ('bathroom','2013-04-03 07:04:00',31);


INSERT INTO temperature_by_day(location_id,date,temperature)
VALUES ('kitchen','2013-04-03',32);
 
INSERT INTO temperature_by_day(location_id,date,temperature)
VALUES ('room1','2013-04-03',34);
 
INSERT INTO temperature_by_day(location_id,date,temperature)
VALUES ('room2','2013-04-04',35);
 
INSERT INTO temperature_by_day(location_id,date,temperature)
VALUES ('bathroom','2013-04-03',34);

INSERT INTO temperature_by_day(location_id,date,temperature) 
VALUES ('bathroom','2013-04-04',31);


INSERT INTO temperature_by_month(location_id,event_time,temperature)
VALUES ('kitchen','2013-01-01 07:01:00',23);

INSERT INTO temperature_by_month(location_id,event_time,temperature)
VALUES ('kitchen','2013-02-01 07:02:00',34);
 
INSERT INTO temperature_by_month(location_id,event_time,temperature)
VALUES ('kitchen','2013-03-01 07:03:00',35);
 
INSERT INTO temperature_by_month(location_id,event_time,temperature)
VALUES ('kitchen','2013-04-01 07:04:00',31);


INSERT INTO latest_temperatures(location_id,event_time,temperature)
VALUES ('bathroom','2013-04-03 07:03:00',25) USING TTL 86400;
 
INSERT INTO latest_temperatures(location_id,event_time,temperature)
VALUES ('bathroom','2013-04-03 07:02:00',35) USING TTL 86400;
 
INSERT INTO latest_temperatures(location_id,event_time,temperature)
VALUES ('bathroom','2013-04-03 07:01:00',45) USING TTL 86400;
 
INSERT INTO latest_temperatures(location_id,event_time,temperature)
VALUES ('bathroom','2013-04-03 07:04:00',30) USING TTL 86400;


