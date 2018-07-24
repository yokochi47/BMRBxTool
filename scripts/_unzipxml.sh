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
 XML_DOC_DIR=$PREFIX"_xml_doc"
 XML_RAW_DIR=$PREFIX"_xml_raw"

else

 XML_REL_DIR=$PREFIX"_xml_"$ATOM"_rel"
 XML_DOC_DIR=$PREFIX"_xml_"$ATOM"_doc"
 XML_RAW_DIR=$PREFIX"_xml_"$ATOM"_raw"

fi

if [ ! -d $XML_REL_DIR ] ; then

 echo "Couldn't find $XML_REL_DIR directory."
 exit 1

fi

if [ ! -d $XML_DOC_DIR ] ; then

 echo "Couldn't find $XML_DOC_DIR directory."
 exit 1

fi

if [ ! -d $XML_RAW_DIR ] ; then

 mkdir $XML_RAW_DIR

fi

rm -f $XML_RAW_DIR/*_local.sh
ln -s ../scripts/chklines_local.sh $XML_RAW_DIR

errs=0

for file in `ls $XML_REL_DIR/*_last 2> /dev/null`
do

 BASENAME=`basename $file _last`
 XML_ZIP_FILE=$XML_DOC_DIR/$BASENAME.xml.gz
 XML_DOC_FILE=$XML_RAW_DIR/$BASENAME.xml

 if [ -e $XML_ZIP_FILE ] && [ ! -e $XML_DOC_FILE ] ; then

  cp -f $XML_ZIP_FILE $XML_RAW_DIR

  $UNZIP -f $XML_DOC_FILE.gz

  if [ $? = 0 ] ; then

    echo -n .

  else

   rm -f $XML_REL_DIR/$BASENAME"_last"
   echo -e "${red}$BASENAME.xml failed.${normal}"
   let errs++

  fi

 fi

done

echo

if [ $errs = 0 ] ; then

 if [ $ATOM = "atom" ] ; then
  echo "XML files (prefix:"$PREFIX") are unzipped."
 else
  echo "XML files (prefix:"$PREFIX"-"$ATOM") are unzipped."
 fi

else

 echo
 echo -e "${red}"$errs errors were detected."${normal}"
 exit 1

fi

