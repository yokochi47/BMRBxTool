#!/bin/bash

show_usage() {

 echo "Usage: $0 -p PREFIX -q QUERY_FILE -e LOCATION -s SILENT"
 echo " -p PREFIX     : either 'bmr' (BMRB:default) or 'bms' (Metabolomics)."
 echo " -q QUERY_FILE : XQuery file."
 echo " -e (loc|pub)  : localhost (loc) or bmrbpub.protein.osaka-u.ac.jp (loc)."
 echo " -s (yes|no)   : Silent mode of curl command (default:no)."

}

if [ $# = 0 ] ; then

 echo "Couldn't specify XQuery file."
 exit 1

fi

URL_LOC=http://localhost:8984/rest/
URL_PUB=http://bmrbpub.protein.osaka-u.ac.jp/xml/

PREFIX=bmr
QUERY_FILE=$1
LOCATION=loc
SILENT=no

ARGV=`getopt --long -o "p:q:e:s:" "$@"`
eval set -- "$ARGV"
while true ; do
 case "$1" in
 -p)
  PREFIX=$2
  shift 2
 ;;
 -q)
  QUERY_FILE=$2
  shift 2
 ;;
 -e)
  LOCATION=$2
  shift 2
 ;;
 -s)
  SILENT=$2
  shift 2
 ;;
 *)
  break
 ;;
 esac
done

if [ ! -e $QUERY_FILE ] ; then

 echo "Couldn't find $QUERY_FILE."
 exit 1

fi

if [ $PREFIX != "bmr" ] && [ $PREFIX != "bms" ] ; then

 echo "Usage: $0 -p PREFIX"
 echo PREFIX should be either \"bmr\" or \"bms\".

 exit 1

fi

if [ $LOCATION != "loc" ] && [ $LOCATION != "pub" ] ; then

 echo LOCATION should be either \"loc\" or \"pub\".
 show_usage

 exit 1

fi

XQUERY_ENDPOINT=$URL_LOC/$PREFIX

if [ $LOCATION != "loc" ] ; then
 XQUERY_ENDPOINT=$URL_PUB/$PREFIX
fi

if [ $SILENT != "no" ] && [ $SILENT != "yes" ] ; then

 echo SILENT should be either \"no\" or \"yes\".
 show_usage

 exit 1

fi

if [ $SILENT = "no" ] ; then
 curl -X POST -H "Content-Type: application/xml" -d "<rest:query xmlns:rest='http://basex.org/rest'><rest:text>`cat $QUERY_FILE`</rest:text></rest:query>" $XQUERY_ENDPOINT
else
 curl -s -X POST -H "Content-Type: application/xml" -d "<rest:query xmlns:rest='http://basex.org/rest'><rest:text>`cat $QUERY_FILE`</rest:text></rest:query>" $XQUERY_ENDPOINT
fi

