#!/bin/bash
names=("kitchen" "room1" "living room" "bathroom")
for i in {1..12}
do
	if (($i < 10));
   	then
   	 i="0$i"
    fi 
	for n in "${names[@]}"
	do
		for a in {1..30}
		do
		   if (($a < 10));
		   	then
		   	 a="0$a"
		   fi
		   #echo $i
		   rand=$((RANDOM%100+0));	
		   #echo $rand 
		   echo "INSERT INTO energy_consumption_by_day(location_id,date,energy_consumption) VALUES ('$n','2013-$i-$a',$rand);"

		   r=$((RANDOM%55+0));
		   echo "INSERT INTO temperature_by_day(location_id,date,temperature) VALUES ('$n','2013-$i-$a 01:01:00',$r);" 

		done
	done
  	 
done

for i in {1..12}
do
	for n in "${names[@]}"
	do
		rand=$((RANDOM%2000+1000));
		echo "INSERT INTO energy_consumption_by_month(location_id,event_time,energy_consumption) VALUES ('$n','2013-$i-01 01:01:00',$rand);"

		r=$((RANDOM%55+0));
		echo "INSERT INTO temperature_by_month(location_id,event_time,temperature) VALUES ('$n','2013-$i-01 01:01:00',$r);"

	done
done