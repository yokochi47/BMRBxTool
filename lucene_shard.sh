#!/bin/bash

PREFIX=bmr
ATOM=atom

SHARDS=4

ARGV=`getopt --long -o "p:a:n:" "$@"`
eval set -- "$ARGV"
while true ; do
 case "$1" in
 -p)
  PREFIX=$2
  shift 2
 ;;
 -a)
  ATOM=$2
  shift 2
 ;;
 -n)
  SHARDS=$2
  shift 2
 ;;
 *)
  break
 ;;
 esac
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

if [ $SHARDS -lt 2 ] ; then

 echo "Usage: $0 -p PREFIX -n SHARDS"
 echo Invalid value for number of shards.

 exit 1

fi

if [ $ATOM = "atom" ] ; then

 XML_RAW_DIR=$PREFIX"_xml_raw"
 FILE_EXT_DIGEST=.

else

 XML_RAW_DIR=$PREFIX"_xml_noatom_raw"
 FILE_EXT_DIGEST=-noatom

fi

XSD_SCHEMA=schema/mmcif_nmr-star.xsd

IDX_DIR=lucene_shard_$PREFIX

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

./$PREFIX"unzip_xml.sh" -a $ATOM

WORK_DIR=lucene_work
ERR_DIR=$WORK_DIR/err

rm -rf $WORK_DIR

mkdir -p $WORK_DIR
mkdir -p $IDX_DIR
mkdir -p $ERR_DIR

err_file=$ERR_DIR/all_err

java -cp extlibs/xsd2pgschema.jar xml2luceneidx --xsd $XSD_SCHEMA --xml $XML_RAW_DIR --idx-dir $IDX_DIR --attr-all --no-rel --no-valid --xml-file-ext-digest $FILE_EXT_DIGEST --shard-size $SHARDS 2> $err_file

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

 if [ $? = 0 ] ; then

  if [ $ATOM = "atom" ] ; then
   echo "Lucene index (prefix:"$PREFIX") is update."
  else
   echo "Lucene index (prefix:"$PREFIX"-"$ATOM") is update."
  fi

#  rm -rf $XML_RAW_DIR
  rm -rf $WORK_DIR

 fi

else

 echo
 echo -e "${red}$errs errors were detected. Please check the log files for more details.${normal}"
 exit 1

fi

date

