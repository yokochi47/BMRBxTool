#!/bin/bash

if [ ! `which psql` ] ; then

 echo "psql: command not found..."
 echo "Please install PostgreSQL (http://www.postgresql.org/)."

 exit 1

fi

source ../scripts/db-user.sh

BMRB_DB=bmrb

psql -U $DB_USER -l | grep $BMRB_DB > /dev/null

if [ $? != 0 ] ; then

 echo "database \"$BMRB_DB\" does not exist."
 exit 1

fi

psql -d $BMRB_DB -U $DB_USER -f schema.pb_ext.sql

java -classpath ../pb-ext.jar:../extlibs/* PB_ext --user-bmrb $DB_USER

echo

for FILE in pb_ext/*.csv
do

 BASENAME=`basename $FILE .csv`

 psql -U $DB_USER -d $BMRB_DB -c "\COPY \"${BASENAME}\" FROM $FILE CSV HEADER"

done

