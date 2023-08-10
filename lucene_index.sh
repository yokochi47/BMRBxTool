#!/bin/bash

sync_update=true

PREFIX=bmr
ATOM=atom

ARGV=`getopt --long -o "p:a:" "$@"`
eval set -- "$ARGV"
while true ; do
 case "$1" in
 -p)
  PREFIX=$2
  shift
 ;;
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

if [ $PREFIX != "bmr" ] && [ $PREFIX != "bms" ] ; then

 echo "Usage: $0 -p PREFIX"
 echo PREFIX should be either \"bmr\" or \"bms\".
 exit 1

fi

if [ $ATOM != "noatom" ] && [ $ATOM != "atom" ] ; then

 echo "Usage: $0 -a ATOM"
 echo ATOM should be either \"noatom\" or \"atom\".
 exit 1

fi

if [ $ATOM = "atom" ] ; then

 XML_DOC_DIR=$PREFIX"_xml_doc"
 FILE_EXT_DIGEST=.

else

 XML_DOC_DIR=$PREFIX"_xml_noatom_doc"
 FILE_EXT_DIGEST=-noatom

fi

XML_SCHEMA=schema/mmcif_nmr-star.xsd

IDX_DIR=lucene_index_$PREFIX

if [ -d $IDX_DIR ] ; then

 echo
 echo "Do you want to update lucene index? (y [n]) "

 read ans

 case $ans in
  y*|Y*) ;;
  *) echo stopped.
   exit 1;;
 esac

 if [ $sync_update != "true" ] ; then
  rm -rf $IDX_DIR
 fi

fi

WORK_DIR=lucene_work
ERR_DIR=$WORK_DIR/err

rm -rf $WORK_DIR

mkdir -p $WORK_DIR
mkdir -p $ERR_DIR

if [ $sync_update = "true" ] ; then
 MD5_DIR=chk_sum_lucene
fi

err_file=$ERR_DIR/all_err

if [ $sync_update != "true" ] ; then

 java -cp extlibs/xsd2pgschema.jar xml2luceneidx --xsd $XML_SCHEMA --xml $XML_DOC_DIR --idx-dir $IDX_DIR --attr-all --field-deny entity.polymer_seq_one_letter_code --field-deny entity.polymer_seq_one_letter_code_can --no-rel --no-valid --xml-file-ext gz --xml-file-ext-digest $FILE_EXT_DIGEST 2> $err_file

else

 java -cp extlibs/xsd2pgschema.jar xml2luceneidx --xsd $XML_SCHEMA --xml $XML_DOC_DIR --idx-dir $IDX_DIR --attr-all --field-deny entity.polymer_seq_one_letter_code --field-deny entity.polymer_seq_one_letter_code_can --no-rel --no-valid --xml-file-ext gz --xml-file-ext-digest $FILE_EXT_DIGEST --sync $MD5_DIR 2> $err_file

fi

if [ $? = 0 ] && [ ! -s $err_file ] ; then
 rm -f $err_file
else
 echo $0 aborted.
 exit 1
fi

red='\e[0;31m'
normal='\e[0m'

errs=`ls $ERR_DIR/*_err 2> /dev/null | wc -l`

if [ $errs = 0 ] ; then

 if [ $? = 0 ] ; then

  if [ $ATOM = "atom" ] ; then
   echo "Lucene index (prefix:"$PREFIX") is update."
  else
   echo "Lucene index (prefix:"$PREFIX"-"$ATOM") is update."
  fi

  rm -rf $WORK_DIR

 fi

else

 echo
 echo -e "${red}$errs errors were detected. Please check the log files for more details.${normal}"
 exit 1

fi

date

