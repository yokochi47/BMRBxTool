#!/bin/bash

#PREFIX=bmr
ATOM=atom
UPDATE=yes

ARGV=`getopt --long -o "a:u:" "$@"`
eval set -- "$ARGV"
while true ; do
 case "$1" in
 -a)
  ATOM=$2
  shift 2
 ;;
 -u)
  UPDATE=$2
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

 XML_RAW_DIR=$PREFIX"_xml_raw"

else

 XML_RAW_DIR=$PREFIX"_xml_"$ATOM"_raw"

fi

which basex &> /dev/null

if [ $? != 0 ] ; then

 echo "basex: command not found..."
 echo "Please install BaseX (http://basex.org/)."

 exit 1

fi

if [ $UPDATE = "yes" ] ; then

 ./$PREFIX"unzip_xml.sh" -a $ATOM

 if [ $? != 0 ] ; then

  exit 1

 fi

fi

if [ ! -d $XML_RAW_DIR ] ; then

 echo "Couldn't find $XML_RAW_DIR directory."
 exit 1

fi

BASEX_COM="DROP DB $PREFIX; CREATE DB $PREFIX $XML_RAW_DIR; OPEN $PREFIX; CREATE INDEX TEXT; CREATE INDEX ATTRIBUTE; CREATE INDEX FULLTEXT; OPTIMIZE"

basex -v -c"$BASEX_COM"

if [ $? = 0 ] ; then

 if [ $ATOM = "atom" ] ; then
  echo "XML->BaseX(DB) storage (prefix:"$PREFIX") is completed."
 else
  echo "XML->BaseX(DB) storage (prefix:"$PREFIX"-"$ATOM") is completed."
 fi

 date -u +"%b %d, %Y" > /tmp/$PREFIX-basex-last

else

 exit 1

fi

