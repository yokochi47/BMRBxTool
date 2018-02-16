#!/bin/bash

if [ ! `which psql` ] ; then

 echo "psql: command not found..."
 echo "Please install PostgreSQL (http://www.postgresql.org/)."

 exit 1

fi

DB_NAME=ligand_expo
DB_USER=$USER

psql -U $DB_USER -l | grep $DB_NAME > /dev/null

if [ $? != 0 ] ; then

 echo "database \"$DB_NAME\" does not exist."

 exit 1

fi

psql -d $DB_NAME -U $DB_USER -f index_ligand_expo.sql

