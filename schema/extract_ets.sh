#!/bin/bash

ets_entry_log=ETS-Entry_log.txt

if [ ! -e $ets_entry_log ] ; then

 echo "Not found $ets_entry_log."
 exit 1

fi

bmrb_pdb_match=BMRB_PDB_match.csv

rm -f $bmrb_pdb_match

while IFS=$'\t' read DEPNUM BMRBNUM STATUS PROCESSED_BY NMR_DEP_CODE SUBMISSION_DATE ACCESSION_DATE AUTHOR_RETURN_DATE ONHOLD_STATUS ONHOLD_DATE RELEASE_DATE MOLECULAR_SYSTEM CONTACT_PERSON1 CONTACT_PERSON2 AUTHOR_VIEW_CODE PDB_CODE RCSB_CODE SUBMIT_TYPE SOURCE LIT_SEARCH_REQUIRED AUTHOR_EMAIL COMMENTS NMRSTAR_DATA RESTART_ID LAST_UPDATED
do

 if [ -z $LAST_UPDATED ] ; then
  continue
 elif [[ ! $PDB_CODE =~ ^[0-9][0-9A-Za-z][0-9A-Za-z][0-9A-Za-z]$ ]] ; then
  continue
 elif [[ ! $STATUS =~ ^rel.* ]] ; then
  continue
 fi

 echo $BMRBNUM,${PDB_CODE^^},$STATUS >> $bmrb_pdb_match

done < $ets_entry_log

rm -f $ets_entry_log

