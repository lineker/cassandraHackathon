select * from temperature_by_day where location_id = 'bathroom' and date in ('2013-04-04', '2013-04-03');

select * 
from temperature_by_day 
where location_id = 'bathroom' 
and date = '2013-04-04';


SELECT temperature
FROM temperature
WHERE location_id='room1'
AND event_time > '2013-04-03 07:01:00'
AND event_time < '2013-04-03 07:04:00'

