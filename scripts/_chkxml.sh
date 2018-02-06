#!/bin/bash

#PREFIX=bmr
ATOM=atom

ARGV=`getopt --long -o "a:" "$@"`
eval set -- "$ARGV"
while true ; do
 case "$1" in
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

which xmlstarlet &> /dev/null

if [ $? != 0 ] ; then

 echo "xmlstarlet: command not found..."
 echo "Please install XMLStarlet (http://xmlstar.sourceforge.net/)."

 exit 1

fi

./scripts/chkxml.sh -p $PREFIX -a $ATOM

