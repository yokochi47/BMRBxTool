#!/bin/bash

sync_update=true

source ./scripts/db-user.sh

PREFIX=bmr
ATOM=atom

ARGV=`getopt --long -o "p:a:" "$@"`
eval set -- "$ARGV"
while true ; do
 case "$1" in
 -p)
  PREFIX=$2
  shift
 ;;
 -a)
  ATOM=$2
  shift
 ;;
 *)
  break
 ;;
 esac
 shift
done

if [ $PREFIX != "bmr" ] && [ $PREFIX != "bms" ] ; then

 echo "Usage: $0 -p PREFIX"
 echo PREFIX should be either \"bmr\" or \"bms\".

 exit 1

fi

if [ $ATOM != "noatom" ] && [ $ATOM != "atom" ] ; then

 echo "Usage: $0 -a ATOM"
 echo ATOM should be either \"noatom\" or \"atom\".

 exit 1

fi

if [ ! `which psql` ] ; then

 echo "psql: command not found..."
 echo "Please install PostgreSQL (https://www.postgresql.org/)."

 exit 1

fi

if [ $PREFIX = "bmr" ] ; then

 DB_NAME=bmrb_clone

else

 DB_NAME=metabolomics_clone

fi

if [ $ATOM = "atom" ] ; then

 XML_RAW_DIR=$PREFIX"_xml_raw"
 FILE_EXT_DIGEST=.

else

 XML_RAW_DIR=$PREFIX"_xml_noatom_raw"
 FILE_EXT_DIGEST=-noatom

fi

psql -U $DB_USER -l | grep $DB_NAME > /dev/null

if [ $? != 0 ] ; then

 echo "database \"$DB_NAME\" does not exist."
 exit 1

fi

XSD_SCHEMA=schema/mmcif_nmr-star.xsd
DB_SCHEMA=schema/bmrb_clone.schema

java -cp extlibs/xsd2pgschema.jar xsd2pgschema --xsd $XSD_SCHEMA --no-rel --hash-by SHA-1 --ddl $DB_SCHEMA --inplace-doc-key-name entry_id --inplace-doc-key-name entry.id

echo
echo "Do you want to update $DB_NAME? (y [n]) "

read ans

case $ans in
 y*|Y*) ;;
 *) echo stopped.
  exit 1;;
esac

./$PREFIX"unzip_xml.sh" -a $ATOM

relations=`psql -d $DB_NAME -U $DB_USER -c "\d" | wc -l`

if [ $sync_update != "true" ] || [ $relations = "0" ] ; then
 sync_update=false
 psql -d $DB_NAME -U $DB_USER -f $DB_SCHEMA --quiet
fi

WORK_DIR=pg_work

if [ $sync_update != "true" ] ; then
 CSV_DIR=$WORK_DIR/csv
fi

MD5_DIR=chk_sum_pgsql_$PREFIX
ERR_DIR=$WORK_DIR/err

rm -rf $WORK_DIR

mkdir -p $WORK_DIR

if [ $sync_update != "true" ] ; then
 mkdir -p $CSV_DIR
fi

mkdir -p $ERR_DIR

if [ $sync_update != "true" ] ; then
 rm -rf $CSV_DIR/*
fi

rm -rf $ERR_DIR/*

err_file=$ERR_DIR/all_err

if [ $sync_update != "true" ] ; then

 java -cp extlibs/xsd2pgschema.jar xml2pgcsv --xsd $XSD_SCHEMA --xml $XML_RAW_DIR --csv-dir $CSV_DIR --sync $MD5_DIR --no-rel --inplace-doc-key-name entry_id --inplace-doc-key-name entry.id --no-valid --xml-file-prefix-digest bmr --xml-file-ext-digest $FILE_EXT_DIGEST --db-name $DB_NAME --db-user $DB_USER 2> $err_file

else

 java -cp extlibs/xsd2pgschema.jar xml2pgsql --xsd $XSD_SCHEMA --xml $XML_RAW_DIR --sync $MD5_DIR --no-rel --inplace-doc-key-name entry_id --inplace-doc-key-name entry.id --no-valid --xml-file-prefix-digest bmr --xml-file-ext-digest $FILE_EXT_DIGEST --db-name $DB_NAME --db-user $DB_USER 2> $err_file

fi

if [ $? = 0 ] && [ ! -s $err_file ] ; then
 rm -f $err_file
 if [ $sync_update != "true" ] ; then
  rm -rf $CSV_DIR
 fi
else
 echo "$0 aborted."
 exit 1
fi

red='\e[0;31m'
normal='\e[0m'

errs=`ls $ERR_DIR/*_err 2> /dev/null | wc -l`

if [ $errs = 0 ] ; then

 rm -rf $WORK_DIR

 if [ $PREFIX = "bmr" ] ; then

  ./index_bmrb_clone.sh

  psql -d $DB_NAME -U $DB_USER -c "CREATE LANGUAGE plpgsql"

 else

  ./index_metabolomics_clone.sh

 fi

 echo "Database ($DB_NAME) is update."

else

 echo
 echo -e "${red}$errs errors were detected. Please check the log files for more details.${normal}"

 exit 1

fi

date

