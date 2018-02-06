#!/bin/bash

scop_entry_log=dir.des.scope.2.06-stable.txt

if [ ! -e $scop_entry_log ] ; then

 echo "Not found $scop_entry_log."
 exit 1

fi

pdb_scop_match=struct_classification.sunid

echo "# item='sunid', category='struct_classification'" > $pdb_scop_match
echo >> $pdb_scop_match

while read line
do

 if [[ $line =~ ^#.* ]] ; then
  continue
 fi

 arr=($line)

 SUNID=${arr[0]}
 PDBQ=${arr[3]}

 if [ -z $PDBQ ] || [ $PDBQ = "-" ] ; then
  continue
 fi

 echo $PDBQ","$SUNID >> $pdb_scop_match

done < $scop_entry_log

