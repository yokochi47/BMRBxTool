#!/bin/bash

source ../scripts/db-user.sh

BMRB_MIRRORS=("www.bmrb.wisc.edu" "bmrb.pdbj.org") # "bmrb.cerm.unifi.it")

printf "    BMRB mirror sites\t\t delay [ms]\n"
echo "-------------------------------------------"

BMRB_MIRROR=${BMRB_MIRRORS[0]}

delay=10000
i=1

for url in ${BMRB_MIRRORS[@]}
do

 time=`ping -c 1 -w 10 $url | grep 'avg' | cut -d '=' -f 2 | cut -d '/' -f 2`

 if [ $? = 0 ] ; then

  printf "[%d] %s\t\t%6.1f\n" $i $url $time

  cmp=`echo "$time > $delay" | bc 2> /dev/null`

  if [ "$cmp" = 0 ] ; then

   server_alive=`curl -I $url -m 5 2> /dev/null`

   if [ $? == 0 ] ; then

    BMRB_MIRROR=$url
    delay=$time

   fi

  fi

 else
  echo $url: timed out.
 fi

 let i++

done

java -classpath ../cs-complete.jar:../extlibs/* bmr_Util_Main --user-bmrb $DB_USER --url-mirror $BMRB_MIRROR

rm -rf cs_complete_loc

rsync -av --delete cs_complete_str/* rsync://bmrbpub.pdbj.org/cs-complete-internal

if [ ! `which psql` ] ; then

 echo "psql: command not found..."
 echo "Please install PostgreSQL (http://www.postgresql.org/)."
 exit 1

fi

BMRB_DB=bmrb

psql -U $DB_USER -l | grep $BMRB_DB > /dev/null || ( echo "database \"$BMRB_DB\" does not exist." && exit 1 )

psql -d $BMRB_DB -U $DB_USER -f schema.cs_complete.sql

java -classpath ../cs-complete.jar:../extlibs/* CS_complete --user-bmrb $DB_USER --url-mirror $BMRB_MIRROR

echo

for FILE in cs_complete_csv/*.csv
do

 BASENAME=`basename $FILE .csv`

 psql -U $DB_USER -d $BMRB_DB -c "\COPY \"${BASENAME}\" FROM $FILE CSV HEADER"

done

