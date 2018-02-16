#!/bin/bash

#PREFIX=bmr
ATOM=atom
UPDATE=yes

WEBDAV_USER=admin:password

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

if [ $WEBDAV_USER = "admin:password" ] ; then

 echo "Specify the User-Agent string (eXist user account and password) to send to the HTTP server."
 echo "File $0, \$WEBDAV_USER=$WEBDAV_USER."

 exit 1

fi

if [ $ATOM != "noatom" ] && [ $ATOM != "atom" ] ; then

 echo "Usage: $0 -a ATOM"
 echo ATOM should be either \"noatom\" or \"atom\".

 exit 1

fi

if [ $ATOM = "atom" ] ; then

 XML_REL_DIR=$PREFIX"_xml_rel"
 XML_RAW_DIR=$PREFIX"_xml_raw"

else

 XML_REL_DIR=$PREFIX"_xml_"$ATOM"_rel"
 XML_RAW_DIR=$PREFIX"_xml_"$ATOM"_raw"

fi

curl -s localhost:8080 | grep exist  &> /dev/null

if [ $? != 0 ] ; then

 echo "eXist-db: server is not running..."
 echo "Please install eXist-db (http://exist-db.org/)."

 exit 1

fi

if [ $UPDATE = "yes" ] ; then

 ./$PREFIX"unzip_xml.sh" -a $ATOM || exit 1

fi

if [ ! -d $XML_RAW_DIR ] ; then

 echo "Couldn't find $XML_RAW_DIR directory."
 exit 1

fi

errs=0

for file in `ls $XML_REL_DIR/*_last 2> /dev/null`
do

 BASENAME=`basename $file _last`
 XML_DOC_FILE=$XML_RAW_DIR/$BASENAME.xml

 curl -T $XML_DOC_FILE -u $WEBDAV_USER http://localhost:8080/exist/webdav/db/$PREFIX/

 if [ $? = 0 ] ; then

  echo -n .

 else

  echo -e "${red}$BASENAME.xml failed.${normal}"
  let errs++

 fi

done

echo

if [ $errs = 0 ] ; then

 if [ $ATOM = "atom" ] ; then
  echo "XML->eXist(DB) storage (prefix:"$PREFIX") is completed."
 else
  echo "XML->eXist(DB) storage (prefix:"$PREFIX"-"$ATOM") is completed."
 fi

else

 exit 1

fi

