#!/bin/bash

#PREFIX=bmr
UPDATE=yes
ATOM=atom

ARGV=`getopt --long -o "u:a:" "$@"`
eval set -- "$ARGV"
while true ; do
 case "$1" in
 -u)
  UPDATE=$2
  shift 2
 ;;
 -a)
  ATOM=$2
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

 JSON_DOC_DIR=$PREFIX"_json_doc"

else

 JSON_DOC_DIR=$PREFIX"_json_"$ATOM"_doc"

fi

if [ ! -d $JSON_DOC_DIR ] ; then

 UPDATE=no

fi

XSD_SCHEMA=schema/mmcif_nmr-star.xsd
JSON_SCHEMA=schema/bmrb_clone.json

java -cp xsd2pgschema.jar xsd2jsonschema --xsd $XSD_SCHEMA --col-json --discarded-doc-key-name entry_id --json $JSON_SCHEMA

echo
echo "Do you want to update BMRB/JSON? (y [n]) "

read ans

case $ans in
 y*|Y*) ;;
 *) echo stopped.
  exit 1;;
esac

./scripts/_xml2json.sh -p $PREFIX -u $UPDATE -a $ATOM

