#!/bin/bash

PREFIX=
ATOM=atom

ZIP=gzip
UNZIP=gunzip

red='\e[0;31m'
normal='\e[0m'

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

if [ $ATOM = "atom" ] ; then

 XML_REL_DIR=$PREFIX"_xml_rel"
 XML_DOC_DIR=$PREFIX"_xml_doc"
 XML_RAW_DIR=$PREFIX"_xml_raw"

else

 XML_REL_DIR=$PREFIX"_xml_"$ATOM"_rel"
 XML_DOC_DIR=$PREFIX"_xml_"$ATOM"_doc"
 XML_RAW_DIR=$PREFIX"_xml_"$ATOM"_raw"

fi

errs=0

errproc () {

 BASENAME=$1
 ERR_CODE=$2

 rm -f $XML_REL_DIR/$BASENAME"_last"
 echo
 if [ $ERR_CODE = 0 ] ; then
  echo -e "${red}$BASENAME.xml unzip failed.${normal}"
 else
  echo -e "${red}$BASENAME.xml corrupted (code:$ERR_CODE).${normal}"
 fi
 let errs++

}

for file in `ls $XML_REL_DIR/*_last 2> /dev/null`
do

 BASENAME=`basename $file _last`
 XML_DOC_FILE=$XML_DOC_DIR/$BASENAME.xml

 if [ -e $XML_DOC_FILE.gz ] ; then

  $UNZIP -f $XML_DOC_FILE.gz
  EXIT_CODE=$?

  if [ -e $XML_DOC_FILE ] ; then

   xmlstarlet fo $XML_DOC_FILE > /dev/null
   STARLET_EXIT_CODE=$?

  fi

  if [ ! $EXIT_CODE = 0 ] || [ ! -s $XML_DOC_FILE ] || [ ! -e $XML_DOC_FILE ] ; then

   errproc $BASENAME 0

  elif [ $STARLET_EXIT_CODE != 0 ] ; then

   errproc $BASENAME $STARLET_EXIT_CODE

  else

   echo -n .
   $ZIP -f $XML_DOC_FILE

   if [ -d $XML_RAW_DIR ]; then
    rm -f $XML_RAW_DIR/$BASENAME.xml
   fi

  fi

 fi

done

echo

if [ $errs = 0 ] ; then

 if [ $ATOM = "atom" ] ; then
  echo "XML files (prefix:"$PREFIX") are checked."
 else
  echo "XML files (prefix:"$PREFIX"-"$ATOM") are checked."
 fi

else

 if [ $ATOM = "atom" ] ; then
  echo -e "${red}$errs errors were detected. Please run $PREFIX"2xml.sh."${normal}"
 else
  echo -e "${red}$errs errors were detected. Please run $PREFIX"2xml.sh -a $ATOM."${normal}"
 fi

 exit 1

fi

