#!/bin/bash

obsolete_dat=obsolete.dat
obsolete_prop=obsolete_pdb_id.properties

wget -c ftp://ftp.pdbj.org/pub/pdb/data/status/$obsolete_dat

rm -f $obsolete_prop

while IFS=' ' read CODE DATE OLD NEW
do

 if [ -z $CODE ] || [ $CODE != "OBSLTE" ] || [ -z "$OLD" ] || [ -z "$NEW" ] ; then
  continue
 fi

 echo $OLD=`echo $NEW | cut -d ' ' -f 1` >> $obsolete_prop

done < $obsolete_dat

rm -f $obsolete_dat

