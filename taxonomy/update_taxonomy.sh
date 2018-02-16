#!/bin/bash

if [ ! `which psql` ] ; then

 echo "psql: command not found..."
 echo "Please install PostgreSQL (http://www.postgresql.org/)."

 exit 1

fi

source ../scripts/db-user.sh

TAX_DB=taxonomy

psql -U $DB_USER -l | grep $TAX_DB > /dev/null

if [ $? != 0 ] ; then

 echo "database \"$TAX_DB\" does not exist."
 exit 1

fi

TAX_FTP=ftp.ncbi.nlm.nih.gov/pub/taxonomy
TAX_DUMP=taxdump.tar

if [ -e $TAX_FTP/$TAX_DUMP.gz ] ; then
 chmod 644 $TAX_FTP/$TAX_DUMP.gz
 rm -f $TAX_FTP/$TAX_DUMP.gz
fi

wget -c -m -nv ftp://$TAX_FTP/$TAX_DUMP.gz

if [ -e $TAX_FTP/$TAX_DUMP ] ; then
 chmod 644 $TAX_FTP/$TAX_DUMP
 rm -f $TAX_FTP/$TAX_DUMP
fi

gunzip $TAX_FTP/$TAX_DUMP.gz

tar xf $TAX_FTP/$TAX_DUMP -C .

psql -d $TAX_DB -U $DB_USER -f taxonomy.sql

for FILE in *.dmp
do

 BASENAME=`basename $FILE .dmp`
 echo $BASENAME

 sed -i 's/\t//g' $FILE

 if [ -e $FILE ] ; then

  psql -U $DB_USER -d $TAX_DB -c "\COPY $BASENAME FROM $FILE WITH DELIMITER '|'"

 fi

done

psql -d $TAX_DB -U $DB_USER -f index_taxonomy.sql

