#!/bin/bash

DB_FTP=ligand-expo.rcsb.org/dictionaries
DB_TGZ=components-pub-xml.tar.gz
XML_DIR=components-pub-xml

WGET_LOG=wget.log

if [ ! -e $DB_FTP/$DB_TGZ ] ; then

 wget -c -m http://$DB_FTP/$DB_TGZ -o $WGET_LOG

 if [ $? != 0 ] ; then

  cat $WGET_LOG
  exit 1

 fi

fi

if [ ! -d $XML_DIR ] ; then
 tar xzf $DB_FTP/$DB_TGZ -C .
fi

XSD_SCHEMA=pdbx-v50.xsd

IDX_DIR=lucene_index

if [ -d $IDX_DIR ] ; then

 echo
 echo "Do you want to update lucene index? (y [n]) "

 read ans

 case $ans in
  y*|Y*) ;;
  *) echo stopped.
   exit 1;;
 esac

 rm -rf $IDX_DIR

fi

WORK_DIR=lucene_work
ERR_DIR=$WORK_DIR/err

rm -rf $WORK_DIR

mkdir -p $WORK_DIR
mkdir -p $IDX_DIR
mkdir -p $ERR_DIR

err_file=$ERR_DIR/all_err

java -cp ../extlibs/xsd2pgschema.jar xml2luceneidx --xsd $XSD_SCHEMA --xml $XML_DIR --idx-dir $IDX_DIR --attr-all --no-rel --no-valid 2> $err_file

if [ $? = 0 ] && [ ! -s $err_file ] ; then
 rm -f $err_file
else
 echo "$0 aborted."
 exit 1
fi

red='\e[0;31m'
normal='\e[0m'

errs=`ls $ERR_DIR/*_err 2> /dev/null | wc -l`

if [ $errs = 0 ] ; then

 echo "Lucene index (Ligand Expo) is update."

 rm -rf $WORK_DIR

else

 echo
 echo -e "${red}$errs errors were detected. Please check the log files for more details.${normal}"
 exit 1

fi

date

