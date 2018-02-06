#!/bin/bash

if [ ! `which psql` ] ; then

 echo "psql: command not found..."
 echo "Please install PostgreSQL (https://www.postgresql.org/)."

 exit 1

fi

source ./scripts/db-user.sh

DB_NAME=bmrb_clone

psql -U $DB_USER -l | grep $DB_NAME > /dev/null

if [ $? != "0" ] ; then

 echo "database \"$DB_NAME\" does not exist."

 exit 1

fi

psql -d $DB_NAME -U $DB_USER -f index_bmrb_clone.sql

