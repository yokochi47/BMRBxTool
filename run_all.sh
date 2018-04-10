#!/bin/bash

#MAXPROCS=`cat /proc/cpuinfo 2> /dev/null | grep 'cpu cores' | uniq | sed 's/\s//g' | cut -d ':' -f 2`
MAXPROCS=`cat /proc/cpuinfo 2> /dev/null | grep 'cpu cores' | wc -l`

if [ $MAXPROCS = 0 ] ; then
 MAXPROCS=1
fi

source ./scripts/db-user.sh

WRITE_OPT=--no-validate
WRITE_NOATOM_OPT="-a noatom --no-validate"
# XML validation (full schema check)
VALIDATE_OPT=--validate-only
# XML validation (well-formed check)
VALIDATE_NOATOM_OPT="-a noatom --validate-only --well-formed"

update_db=true
force_update=false

ARGV=`getopt --long -o "n" "$@"`
eval set -- "$ARGV"
while true ; do
 case "$1" in
 -n)
  update_db=false
 ;;
 *)
  break
 ;;
 esac
 shift
done

echo "# BMRBxTool v1.27.0 (NMR-STAR v3.2.1.2)"
echo "# + Resource updates"

if [ ! -e ./schema/mmcif_nmr-star.xsd ] ; then

 ./update_rsrc.sh schema

fi

if [ ! -d ./bmrb/ftp ] ; then

 ./update_rsrc.sh bmrb

elif [ $update_db = "true" ] ; then

 echo
 echo "Do you want to update BMRB DB? (y [n]) "

 read ans

 case $ans in
  y*|Y*) force_update=true; ./update_rsrc.sh bmrb;;
  *) echo skipped.;;
 esac

fi

if [ ! -e ./taxonomy/nodes.dmp ] ; then

 ./update_rsrc.sh tax

elif [ $update_db = "true" ] ; then

 echo
 echo "Do you want to update NCBI Taxonomy DB? (y [n]) "

 read ans

 case $ans in
  y*|Y*) ./update_rsrc.sh tax;;
  *) echo skipped.;;
 esac

fi

if [ $force_update = "true" ] || [ ! -e ./lacs_ext/lacs_ext/LACS_plot.csv ] ; then

 ./update_rsrc.sh lacs

elif [ $update_db = "true" ] ; then

 echo
 echo "Do you want to update LACS validation report? (y [n]) "

 read ans

 case $ans in
  y*|Y*) ./update_rsrc.sh lacs;;
  *) echo skipped.;;
 esac

fi

if [ $force_update = "true" ] || [ ! -d ./bmrb_plus_pdb_ext/ftp ] ; then

 ./update_rsrc.sh plus

elif [ $update_db = "true" ] ; then

 echo
 echo "Do you want to update BMRB+PDB archive? (y [n]) "

 read ans

 case $ans in
  y*|Y*) ./update_rsrc.sh plus;;
  *) echo skipped.;;
 esac

fi

if [ $force_update = "true" ] || [ ! -e ./pb_ext/pb_ext/PB_list.csv ] ; then

 ./update_rsrc.sh pb

elif [ $update_db = "true" ] ; then

 echo
 echo "Do you want to update PB annotation? (y [n]) "

 read ans

 case $ans in
  y*|Y*) ./update_rsrc.sh pb;;
  *) echo skipped.;;
 esac

fi

if [ $force_update = "true" ] || [ ! -e ./cs_complete/cs_complete_csv/Chem_shift_completeness_list.csv ] ; then

 ./update_rsrc.sh cs_complete

elif [ $update_db = "true" ] ; then

 echo
 echo "Do you want to update CS completeness validation report? (y [n]) "

 read ans

 case $ans in
  y*|Y*) ./update_rsrc.sh cs_complete;;
  *) echo skipped.;;
 esac

fi

if [ ! -d ./ligand_expo/components-pub-xml ] ; then

 ./update_rsrc.sh le

elif [ $update_db = "true" ] ; then

 echo
 echo "Do you want to update RCSB Ligand Expo DB? (y [n]) "

 read ans

 case $ans in
  y*|Y*) ./update_rsrc.sh le;;
  *) echo skipped.;;
 esac

fi

chk_err_dir () {

 err_dir=$1
 ebk_dir=$2
 doc_dir=$3
 log_dir=$4

 entry_category_err=$log_dir/entry_category_err

 grep "category='Entry'" $log_dir/*log | grep nillable | cut -d ':' -f 1 | cut -d '/' -f 2 | cut -d '_' -f 1 > $entry_category_err

 if [ ! -s $entry_category_err ] ; then
  rm -f $entry_category_err
 else

  mkdir -p $ebk_dir

  while read BASENAME
  do
   if [ -e $doc_dir/$BASENAME.xml ] ; then
    mv -f $doc_dir/$BASENAME.xml $ebk_dir
   fi
  done < $entry_category_err

 fi

 errs=`ls $err_dir 2> /dev/null | wc -l`

 if [ $errs = 0 ] ; then
  rm -rf $err_dir
 else

  mkdir -p $ebk_dir

  echo "Please check error messages in $ebk_dir directory."

  for file in $err_dir/*_err
  do

   BASENAME=`basename $file _err`
   FILENAME=`basename $file`

   if [ -e $doc_dir/$BASENAME.xml ] ; then
    mv -f $doc_dir/$BASENAME.xml $ebk_dir
   fi

   cat $file >> $ebk_dir/$FILENAME
   rm -f $file

  done

 fi

}

echo "# + XML conversions (BMRB/XML)"

for prefix in bmr bms ; do

 echo "# ++ BMRB/XML (prefix: $prefix)..."

 err_dir=$prefix"_xml_err"
 ebk_dir=$prefix"_xml_ebk"
 doc_dir=$prefix"_xml_doc"
 log_dir=$prefix"_xml_log"

 rm -rf $ebk_dir

 ./$prefix"2xml.sh" $WRITE_OPT

 if [ $? != 0 ] ; then
  echo "$0 aborted."
  exit 1
 else
  chk_err_dir $err_dir $ebk_dir $doc_dir $log_dir
 fi

 for proc_id in `seq 1 $MAXPROCS` ; do
  sleep $proc_id
  if [ $proc_id = "1" ] ; then
   ./$prefix"2xml.sh" $VALIDATE_OPT &
  else
   ./$prefix"2xml.sh" $VALIDATE_OPT --noinit &
  fi
 done

 if [ $? != 0 ] ; then
  echo "$0 aborted."
  exit 1
 fi

 wait

 chk_err_dir $err_dir $ebk_dir $doc_dir $log_dir

 ./$prefix"2xml.sh" $VALIDATE_OPT

 if [ $? != 0 ] ; then
  echo "$0 aborted."
  exit 1
 else
  chk_err_dir $err_dir $ebk_dir $doc_dir $log_dir
 fi

 rm -f $prefix_"xml_doc"/mmcif_nmr-star.xsd

 echo "# ++ BMRB/XML (prefix: $prefix-noatom)..."

 err_dir=$prefix"_xml_noatom_err"
 ebk_dir=$prefix"_xml_noatom_ebk"
 doc_dir=$prefix"_xml_noatom_doc"
 log_dir=$prefix"_xml_noatom_log"

 rm -rf $ebk_dir

 ./$prefix"2xml.sh" $WRITE_NOATOM_OPT

 if [ $? != 0 ] ; then
  echo "$0 aborted."
  exit 1
 else
  chk_err_dir $err_dir $ebk_dir $doc_dir $log_dir
 fi

 for proc_id in `seq 1 $MAXPROCS` ; do
  sleep $proc_id
  if [ $proc_id = "1" ] ; then
   ./$prefix"2xml.sh" $VALIDATE_NOATOM_OPT &
  else
   ./$prefix"2xml.sh" $VALIDATE_NOATOM_OPT --noinit &
  fi
 done

 if [ $? != 0 ] ; then
  echo "$0 aborted."
  exit 1
 fi

 wait

 chk_err_dir $err_dir $ebk_dir $doc_dir $log_dir

 ./$prefix"2xml.sh" $VALIDATE_NOATOM_OPT

 if [ $? != 0 ] ; then
  echo "$0 aborted."
  exit 1
 else
  chk_err_dir $err_dir $ebk_dir $doc_dir $log_dir
 fi

 rm -f $prefix_"xml_noatom_doc"/mmcif_nmr-star.xsd

done

if [ -d bmr_xml_ebk ] || [ -d bmr_xml_noatom_ebk ] || [ -d bms_xml_ebk ] || [ -d bms_xml_noatom_ebk ] ; then
 echo "$0 aborted."
 exit 1
fi

bmr_ulist_len=0
bms_ulist_len=0

bmr_ulist=bmr_xml_noatom_ulist
bms_ulist=bms_xml_noatom_ulist

if [ -e $bmr_ulist ] ; then
 bmr_ulist_len=`wc -l $bmr_ulist | tail -n 1 | while read _total _file ; do echo $_total ; done`
fi

if [ -e $bms_ulist ] ; then
 bms_ulist_len=`wc -l $bms_ulist | tail -n 1 | while read _total _file ; do echo $_total ; done`
fi

echo "# + XML Database (BaseX) loader (optional)"

which basex > /dev/null

if [ $? = 0 ] && ( [ $bmr_ulist_len -gt 0 ] || [ $bms_ulist_len -gt 0 ] ) ; then

 echo
 echo "Do you want to update BaseX DB? (y [n]) "

 read ans

 case $ans in
  y*|Y*) ;;
  *)
   echo skipped.
   exit 0;;
 esac

 rm -f /tmp/bmr-basex-last

 if [ $bmr_ulist_len -gt 0 ] ; then
  ./bmr2basex.sh -a noatom
 fi

 rm -f /tmp/bms-basex-last

 if [ $bms_ulist_len -gt 0 ] ; then
  ./bms2basex.sh -a noatom
 fi

fi

echo "# + Cloning BMRB DB from BMRB/XML"

if [ $bmr_ulist_len -gt 0 ] ; then

 BMRB_DB=bmrb_clone

 psql -U $DB_USER -l | grep $BMRB_DB > /dev/null

 if [ $? = 0 ] ; then
  ./clone_bmrb.sh -p bmr -a noatom
 fi

fi

if [ $bms_ulist_len -gt 0 ] ; then

 BMRB_DB=metabolomics_clone

 psql -U $DB_USER -l | grep $BMRB_DB > /dev/null

 if [ $? = 0 ] ; then
  ./clone_bmrb.sh -p bms -a atom
 fi

fi

echo "# + Full text indexing using BMRB/XML"

if [ $bmr_ulist_len -gt 0 ] ; then
 ./lucene_index.sh -p bmr -a noatom
 if [ $? = 0 ] ; then
  ./lucene_ftxt.sh -p bmr
 fi
 ./sphinx_index.sh -p bmr -a noatom
fi

if [ $bms_ulist_len -gt 0 ] ; then
 ./lucene_index.sh -p bms -a atom
 if [ $? = 0 ] ; then
  ./lucene_ftxt.sh -p bms
 fi
 ./sphinx_index.sh -p bms -a atom
fi

echo "# + BMRB/XML -> BMRB/JSON conversion"

if [ $bmr_ulist_len -gt 0 ] ; then
 ./bmrxml2json.sh -a noatom
fi

if [ $bms_ulist_len -gt 0 ] ; then
 ./bmsxml2json.sh -a noatom
fi

echo done.

