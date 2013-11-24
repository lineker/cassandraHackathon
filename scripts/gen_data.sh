#!/bin/bash
names=("kitchen" "room1" "living room" "bathroom")
for i in {1..12}
do
	for n in "${names[@]}"
	do
		 for a in {1..30}
		do
		   rand=$((RANDOM%100+0));	
		   echo $rand 
		   d="INSERT INTO energy_consumption_by_day(location_id,date,energy_consumption) VALUES ('$n','2013-$i-$a',$rand);"
		   echo $d >> /Users/ltomazeli/cassandraHack/scripts/insert_energy_dataset.cql

		   r=$((RANDOM%55+0));
			t="INSERT INTO temperature_by_day(location_id,date,temperature) VALUES ('$n','2013-$i-$a 01:01:00',$r);"
			echo $t >>/Users/ltomazeli/cassandraHack/scripts/insert_energy_dataset.cql
		done
	done
  	 
done

for i in {1..12}
do
	for n in "${names[@]}"
	do
		rand=$((RANDOM%2000+1000));
		m="INSERT INTO energy_consumption_by_month(location_id,event_time,energy_consumption) VALUES ('$n','2013-$i-01 01:01:00',$rand);"
		echo $m >>/Users/ltomazeli/cassandraHack/scripts/insert_energy_dataset.cql

		r=$((RANDOM%55+0));
		t="INSERT INTO temperature_by_month(location_id,event_time,temperature) VALUES ('$n','2013-$i-01 01:01:00',$r);"
		echo $t >>/Users/ltomazeli/cassandraHack/scripts/insert_energy_dataset.cql
	done
done