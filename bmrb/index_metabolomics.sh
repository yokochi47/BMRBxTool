#!/bin/bash

if [ ! `which psql` ] ; then

 echo "psql: command not found..."
 echo "Please install PostgreSQL (http://www.postgresql.org/)."
 exit 1

fi

source ../scripts/db-user.sh

MTBL_DB=metabolomics

psql -U $DB_USER -l | grep $MTBL_DB > /dev/null || echo "database \"$MTBL_DB\" does not exist." && exit 1

psql -d $MTBL_DB -U $DB_USER -f index_metabolomics.sql

