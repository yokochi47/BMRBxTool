#!/bin/bash

DB_USER=$USER

BMRB_DB=bmrb

( psql -U $DB_USER -l | grep $BMRB_DB ) &>> /dev/null

if [ $? != 0 ] ; then

 DB_USER=bmrb

 ( psql -U $DB_USER -l | grep $BMRB_DB ) &>> /dev/null || ( echo "Not found $BMRB_DB database." && exit 1 )

fi

