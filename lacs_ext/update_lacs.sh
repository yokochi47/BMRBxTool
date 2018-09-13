#!/bin/bash

if [ ! `which psql` ] ; then

 echo "psql: command not found..."
 echo "Please install PostgreSQL (http://www.postgresql.org/)."
 exit 1

fi

source ../scripts/db-user.sh

BMRB_DB=bmrb

psql -U $DB_USER -l | grep $BMRB_DB > /dev/null || echo ( "database \"$BMRB_DB\" does not exist." && exit 1 )

psql -d $BMRB_DB -U $DB_USER -f schema.lacs_ext.sql

URL_MIRROR=url_mirror

if [ -e $URL_MIRROR ] && [ -s $URL_MIRROR ] ; then

 java -classpath ../lacs-ext.jar:../extlibs/* LACS_ext --user-bmrb $DB_USER --url-mirror `cat $URL_MIRROR`

else

 java -classpath ../lacs-ext.jar:../extlibs/* LACS_ext --user-bmrb $DB_USER

fi

echo

for FILE in lacs_ext/*.csv
do

 BASENAME=`basename $FILE .csv`

 psql -U $DB_USER -d $BMRB_DB -c "\COPY \"${BASENAME}\" FROM $FILE CSV HEADER"

done

