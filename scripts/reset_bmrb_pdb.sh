#!/bin/bash

PREFIX=bmr
ATOM=atom

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
 REL_FILE_EXT="_last"

else

 XML_REL_DIR=$PREFIX"_xml_"$ATOM"_rel"
 REL_FILE_EXT="-noatom_last"

fi

bmrb_pdb_match=schema/BMRB_PDB_match.csv

if [ ! -e $bmrb_pdb_match ] ; then

 echo "Not found $bmrb_pdb_match."
 exit 1

fi

while IFS=, read BMRBNUM PDB_CODE STATUS
do

 rm -f $XML_REL_DIR/$PREFIX$BMRBNUM$REL_FILE_EXT

done < $bmrb_pdb_match

bmrb_pdb_match=schema/BMRB_PDB_PUBMED_match.csv

if [ ! -e $bmrb_pdb_match ] ; then

 echo "Not found $bmrb_pdb_match."
 exit 1

fi

while IFS=, read BMRBNUM PDB_CODE STATUS
do

 rm -f $XML_REL_DIR/$PREFIX$BMRBNUM$REL_FILE_EXT

done < $bmrb_pdb_match

