#!/bin/bash

#PREFIX=bmr
ATOM=atom

UNZIP=gunzip

red='\e[0;31m'
normal='\e[0m'

ARGV=`getopt --long -o "a:" "$@"`
eval set -- "$ARGV"
while true ; do
 case "$1" in
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

if [ $ATOM != "noatom" ] && [ $ATOM != "atom" ] ; then

 echo "Usage: $0 -a ATOM"
 echo ATOM should be either \"noatom\" or \"atom\".
 exit 1

fi

if [ $ATOM = "atom" ] ; then

 XML_REL_DIR=$PREFIX"_xml_rel"
 JSON_DOC_DIR=$PREFIX"_json_doc"
 JSON_RAW_DIR=$PREFIX"_json_raw"

else

 XML_REL_DIR=$PREFIX"_xml_"$ATOM"_rel"
 JSON_DOC_DIR=$PREFIX"_json_"$ATOM"_doc"
 JSON_RAW_DIR=$PREFIX"_json_"$ATOM"_raw"

fi

if [ ! -d $XML_REL_DIR ] ; then

 echo "Couldn't find $XML_REL_DIR directory."
 exit 1

fi

if [ ! -d $JSON_DOC_DIR ] ; then

 echo "Couldn't find $JSON_DOC_DIR directory."
 exit 1

fi

if [ ! -d $JSON_RAW_DIR ] ; then

 mkdir $JSON_RAW_DIR

fi

errs=0

for file in `ls $XML_REL_DIR/*_last 2> /dev/null`
do

 BASENAME=`basename $file _last`
 JSON_ZIP_FILE=$JSON_DOC_DIR/$BASENAME.json.gz
 JSON_DOC_FILE=$JSON_RAW_DIR/$BASENAME.json

 if [ -e $JSON_ZIP_FILE ] && [ ! -e $JSON_DOC_FILE ] ; then

  cp -f $JSON_ZIP_FILE $JSON_RAW_DIR

  $UNZIP -f $JSON_DOC_FILE.gz

  if [ $? = 0 ] ; then

    echo -n .

  else

   rm -f $XML_REL_DIR/$BASENAME"_last"
   echo -e "${red}$BASENAME.json failed.${normal}"
   let errs++

  fi

 fi

done

echo

if [ $errs = 0 ] ; then

 if [ $ATOM = "atom" ] ; then
  echo "JSON files (prefix:"$PREFIX") are unzipped."
 else
  echo "JSON files (prefix:"$PREFIX"-"$ATOM") are unzipped."
 fi

else

 echo
 echo -e "${red}"$errs errors were detected."${normal}"
 exit 1

fi

