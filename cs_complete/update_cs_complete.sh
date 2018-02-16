#!/bin/bash

source ../scripts/db-user.sh

java -classpath ../cs-complete.jar:../extlibs/* bmr_Util_Main --user-bmrb $DB_USER

rm -rf cs_complete_loc

if [ -e url_mirror ] ; then

 url_mirror=`cat url_mirror`

 rsync -av --delete cs_complete_str/* rsync://$url_mirror/cs-complete-internal

fi

if [ ! `which psql` ] ; then

 echo "psql: command not found..."
 echo "Please install PostgreSQL (http://www.postgresql.org/)."

 exit 1

fi

BMRB_DB=bmrb

psql -U $DB_USER -l | grep $BMRB_DB > /dev/null

if [ $? != 0 ] ; then

 echo "database \"$BMRB_DB\" does not exist."
 exit 1

fi

psql -d $BMRB_DB -U $DB_USER -f schema.cs_complete.sql

java -classpath ../cs-complete.jar:../extlibs/* CS_complete --user-bmrb $DB_USER

echo

for FILE in cs_complete_csv/*.csv
do

 BASENAME=`basename $FILE .csv`

 psql -U $DB_USER -d $BMRB_DB -c "\COPY \"${BASENAME}\" FROM $FILE CSV HEADER"

done

