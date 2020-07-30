#!/bin/bash

sync_update=true

if [ ! `which psql` ] ; then

 echo "psql: command not found..."
 echo "Please install PostgreSQL (http://www.postgresql.org/)."
 exit 1

fi

source ../scripts/db-user.sh

DB_NAME=ligand_expo

psql -U $DB_USER -l | grep $DB_NAME > /dev/null || ( echo "database \"$DB_NAME\" does not exist." && exit 1 )

#DB_FTP=ligand-expo.rcsb.org/dictionaries
#DB_TGZ=components-pub-xml.tar.gz
#XML_DIR=components-pub-xml

PDB_MIRROR=ftp.pdbj.org
XML_DIR=chem_comp/PDBML

wget -c -r -nv -np http://$PDB_MIRROR/$XML_DIR/ -nH -R index.html* 2> /dev/null

MD5_DIR=chk_sum_pdbml_cc

chk_sum_log=pdbml_cc_log

java -cp ../extlibs/xsd2pgschema.jar chksumstat --xml $XML_DIR --xml-file-ext gz --sync $MD5_DIR --update --verbose > $chk_sum_log

if [ -d $XML_DIR ] ; then
 while read cc_id ; do
  [ -z "$cc_id" ] || [[ "$cc_id" =~ ^#.* ]] && continue
  rm -f $XML_DIR/$cc_id.xml
 done < $chk_sum_log
fi

#rm -f $chk_sum_log

#WGET_LOG=wget.log

#wget -c -m http://$DB_FTP/$DB_TGZ -o $WGET_LOG || ( cat $WGET_LOG && exit 1 )

#grep 'nothing to do' $WGET_LOG > /dev/null

#if [ $? = 0 ] ; then

# echo $DB_NAME is update.
# exit 0

#fi

#rm -rf $XML_DIR

#tar xzf $DB_FTP/$DB_TGZ -C .

update=`grep docs pdbml_cc_log | cut -d ':' -f 2 | xargs`

rm -f $chk_sum_log

if [ "$update" = "0 0 0" ] ; then

 echo $DB_NAME is update.
 exit 0

fi

XSD_SCHEMA=pdbx-v50.xsd
DB_SCHEMA=ligand_expo.schema

java -cp ../extlibs/xsd2pgschema.jar xsd2pgschema --xsd $XSD_SCHEMA --no-rel --doc-key --no-key --ddl $DB_SCHEMA

echo
echo "Do you want to update $DB_NAME? (y [n]) "

read ans

case $ans in
 y*|Y*) ;;
 *) echo stopped.
  exit 1;;
esac

MD5_DIR=chk_sum_pgsql

relations=`psql -d $DB_NAME -U $DB_USER -c "\d" | wc -l`

if [ $sync_update != "true" ] || [ ! -d $MD5_DIR ] || [ $relations = "0" ] ; then
 sync_update=false
 psql -d $DB_NAME -U $DB_USER -f $DB_SCHEMA --quiet
fi

WORK_DIR=pg_work

if [ $sync_update != "true" ] ; then
 DATA_DIR=$WORK_DIR/data
fi

ERR_DIR=$WORK_DIR/err

rm -rf $WORK_DIR

mkdir -p $WORK_DIR

if [ $sync_update != "true" ] ; then
 mkdir -p $DATA_DIR
fi

mkdir -p $ERR_DIR

if [ $sync_update != "true" ] ; then
 rm -rf $DATA_DIR/*
fi

rm -rf $ERR_DIR/*

err_file=$ERR_DIR/all_err

if [ $sync_update != "true" ] ; then

 java -cp ../extlibs/xsd2pgschema.jar xml2pgtsv --xsd $XSD_SCHEMA --xml $XML_DIR --xml-file-ext gz --work-dir $DATA_DIR --sync $MD5_DIR --no-rel --doc-key --no-valid --db-name $DB_NAME --db-user $DB_USER --drop-doc-key-index 2> $err_file

else

 java -cp ../extlibs/xsd2pgschema.jar xml2pgsql --xsd $XSD_SCHEMA --xml $XML_DIR --xml-file-ext gz --sync $MD5_DIR --no-rel --doc-key --no-valid --db-name $DB_NAME --db-user $DB_USER 2> $err_file

fi

if [ $? = 0 ] && [ ! -s $err_file ] ; then
 rm -f $err_file
 if [ $sync_update != "true" ] ; then
  rm -rf $DATA_DIR
 fi
else
 echo $0 aborted.
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

if [ $sync_update != "true" ] ; then
 ./index_ligand_expo.sh
fi

./lucene_index.sh

if [ $? = 0 ] ; then
 ./lucene_ftxt.sh
fi

./sphinx_index.sh

date

