#!/bin/bash

#PREFIX=bmr
ATOM=atom
VERBOSE=no

ARGV=`getopt --long -o "a:v:" "$@"`
eval set -- "$ARGV"
while true ; do
 case "$1" in
 -a)
  ATOM=$2
  shift 2
 ;;
 -v)
  VERBOSE=$2
  shift 2
 ;;
 *)
  break
 ;;
 esac
done

if [ $ATOM != "noatom" ] && [ $ATOM != "atom" ] ; then

 echo "Usage: $0 -a ATOM"
 echo ATOM should be either \"noatom\" or \"atom\".

 exit 1

fi

if [ $ATOM = "atom" ] ; then

 XML_REL_DIR=$PREFIX"_xml_rel"
 XML_LOG_DIR=$PREFIX"_xml_log"
 XML_REP_DIR=$PREFIX"_xml_rep"

else

 XML_REL_DIR=$PREFIX"_xml_"$ATOM"_rel"
 XML_LOG_DIR=$PREFIX"_xml_"$ATOM"_log"
 XML_REP_DIR=$PREFIX"_xml_"$ATOM"_rep"

fi

if [ ! -d $XML_REL_DIR ] ; then

 echo "Couldn't find $XML_REL_DIR directory."
 exit 1

fi

if [ ! -d $XML_LOG_DIR ] ; then

 echo "Couldn't find $XML_LOG_DIR directory."
 exit 1

fi

rm -rf $XML_REP_DIR
mkdir $XML_REP_DIR

echo
echo "step:entry"

for file in `ls $XML_REL_DIR/*_last 2> /dev/null`
do

 BASENAME=`basename $file _last`
 XML_LOG_FILE=$XML_LOG_DIR/$BASENAME"_log"
 XML_REP_FILE=$XML_REP_DIR/$BASENAME"_log"

 if [[ $VERBOSE =~ ^[nN].* ]] ; then
  grep category $XML_LOG_FILE | grep item | grep value | grep -v "value='null'" | grep -v "value='.'" | grep -v "value='?'" | sort | uniq -c | sort -n -r > $XML_REP_FILE # types of remediation
 else
  grep category $XML_LOG_FILE | grep item | grep value | sort | uniq -c | sort -n -r > $XML_REP_FILE
 fi

 if [ ! -s $XML_REP_FILE ] ; then
  rm -f $XML_REP_FILE
 fi

 echo -n .

done

echo
echo "step:all"

XML_TMP_FILE=$XML_LOG_DIR/$PREFIX"_all_log"
XML_REP_FILE=$XML_REP_DIR/$PREFIX"_all_log"

rm -rf $XML_TMP_FILE

for file in `ls $XML_REL_DIR/*_last 2> /dev/null`
do

 BASENAME=`basename $file _last`
 XML_LOG_FILE=$XML_LOG_DIR/$BASENAME"_log"

 if [[ $VERBOSE =~ ^[nN].* ]] ; then
  grep -v "value='null'" $XML_LOG_FILE >> $XML_TMP_FILE
 else
  cat $XML_LOG_FILE >> $XML_TMP_FILE
 fi

 echo -n .

done

sort $XML_TMP_FILE | uniq -c | sort -n -r > $XML_REP_FILE

echo

