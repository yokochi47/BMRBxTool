#!/bin/bash

if [ ! `which psql` ] ; then

 echo "psql: command not found..."
 echo "Please install PostgreSQL (http://www.postgresql.org/)."

 exit 1

fi

source ../scripts/db-user.sh

DB_NAME=ligand_expo

psql -U $DB_USER -l | grep $DB_NAME > /dev/null

if [ $? != 0 ] ; then

 echo "database \"$DB_NAME\" does not exist."

 exit 1

fi

DB_FTP=ligand-expo.rcsb.org/dictionaries
DB_TGZ=components-pub-xml.tar.gz
XML_DIR=components-pub-xml

WGET_LOG=wget.log

wget -c -m http://$DB_FTP/$DB_TGZ -o $WGET_LOG

if [ $? != 0 ] ; then

 cat $WGET_LOG
 exit 1

fi

grep 'nothing to do' $WGET_LOG > /dev/null

if [ $? = 0 ] ; then

 echo $DB_NAME is update.
 exit 0

fi

rm -rf $XML_DIR

tar xzf $DB_FTP/$DB_TGZ -C .

XSD_SCHEMA=pdbx-v50.xsd
DB_SCHEMA=ligand_expo.schema

java -cp ../xsd2pgschema.jar xsd2pgschema --xsd $XSD_SCHEMA --no-rel --ddl $DB_SCHEMA

echo
echo "Do you want to update $DB_NAME? (y [n]) "

read ans

case $ans in
 y*|Y*) ;;
 *) echo stopped.
  exit 1;;
esac

psql -d $DB_NAME -U $DB_USER -f $DB_SCHEMA --quiet

WORK_DIR=pg_work
CSV_DIR=$WORK_DIR/csv
ERR_DIR=$WORK_DIR/err

rm -rf $WORK_DIR

mkdir -p $WORK_DIR
mkdir -p $CSV_DIR
mkdir -p $ERR_DIR

rm -rf $CSV_DIR/*
rm -rf $ERR_DIR/*

err_file=$ERR_DIR/all_err

java -cp ../xsd2pgschema.jar xml2pgcsv --xsd $XSD_SCHEMA --xml $XML_DIR --csv-dir $CSV_DIR --no-rel --no-valid --db-name $DB_NAME --db-user $DB_USER 2> $err_file

if [ $? = 0 ] && [ ! -s $err_file ] ; then
 rm -f $err_file
 rm -rf $CSV_DIR
else
 echo "$0 aborted."
 exit 1
fi

red='\e[0;31m'
normal='\e[0m'

errs=`ls $ERR_DIR/*_err 2> /dev/null | wc -l`

if [ $errs = 0 ] ; then

 echo "Database ($DB_NAME) is update."
 rm -rf $WORK_DIR

else

 for file in $ERR_DIR/*_err
 do

  BASENAME=`basename $file _err`
  echo File: $BASENAME.xml

 done

 echo
 echo -e "${red}$errs errors were detected. Please check the log files for more details.${normal}"
 exit 1

fi

./index_ligand_expo.sh

./lucene_index.sh

if [ $? = 0 ] ; then
 ./lucene_ftxt.sh
fi

./sphinx_index.sh

date

