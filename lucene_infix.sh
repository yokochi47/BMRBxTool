#!/bin/bash

PREFIX=bmr

ARGV=`getopt --long -o "p:" "$@"`
eval set -- "$ARGV"
while true ; do
 case "$1" in
 -p)
  PREFIX=$2
  shift 2
 ;;
 *)
  break
 ;;
 esac
done

if [ $PREFIX != "bmr" ] && [ $PREFIX != "bms" ] ; then

 echo "Usage: $0 -p PREFIX -n SHARDS"
 echo PREFIX should be either \"bmr\" or \"bms\".

 exit 1

fi

LUCENE_IDX_DIR=lucene_index_$PREFIX

if [ ! -d $LUCENE_IDX_DIR ] ; then

 echo $LUCENE_IDX_DIR is not directory.
 exit 1

fi

LUCENE_INFIX_DIR=lucene_infix_$PREFIX

rm -rf $LUCENE_INFIX_DIR

java -cp extlibs/xsd2pgschema.jar luceneidx2infix --idx-dir $LUCENE_IDX_DIR --infix-dir $LUCENE_INFIX_DIR

