#!/bin/bash

if [ ! `which psql` ] ; then

 echo "psql: command not found..."
 echo "Please install PostgreSQL (http://www.postgresql.org/)."
 exit 1

fi

source ../scripts/db-user.sh

BMRB_MIRRORS=("bmrb.io" "bmrb.pdbj.org")

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

BMRB_DB=bmrb

psql -U $DB_USER -l | grep $BMRB_DB > /dev/null || ( echo "database \"$BMRB_DB\" does not exist." && exit 1 )

psql -d $BMRB_DB -U $DB_USER -f schema.lacs_ext.sql

java -classpath ../lacs-ext.jar:../extlibs/* LACS_ext --user-bmrb $DB_USER --url-mirror $BMRB_MIRROR

echo

for FILE in lacs_ext/*.csv
do

 BASENAME=`basename $FILE .csv`

 psql -U $DB_USER -d $BMRB_DB -c "\COPY \"${BASENAME}\" FROM $FILE CSV HEADER"

done

