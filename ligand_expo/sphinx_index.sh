#!/bin/bash

if [ ! `which indexer` ] ; then

 echo "indexer: command not found..."
 echo "Please install Sphinx (http://sphinxsearch.com/)."

 exit 1

fi

DB_FTP=ligand-expo.rcsb.org/dictionaries
DB_TGZ=components-pub-xml.tar.gz
XML_DIR=components-pub-xml

WGET_LOG=wget.log

if [ ! -e $DB_FTP/$DB_TGZ ] ; then

 wget -c -m http://$DB_FTP/$DB_TGZ -o $WGET_LOG

 if [ $? != 0 ] ; then

  cat $WGET_LOG
  exit 1

 fi

fi

if [ ! -d $XML_DIR ] ; then
 tar xzf $DB_FTP/$DB_TGZ -C .
fi

XSD_SCHEMA=pdbx-v50.xsd
PREFIX=le

IDX_DIR=sphinx_index
DIC_DIR=sphinx_dic

if [ -d $IDX_DIR ] ; then

 echo
 echo "Do you want to update sphinx index? (y [n]) "

 read ans

 case $ans in
  y*|Y*) ;;
  *) echo stopped.
   exit 1;;
 esac

 rm -rf $IDX_DIR

fi

WORK_DIR=sphinx_work
ERR_DIR=$WORK_DIR/err

DIC_NAMES=("all" "lig")
ATTRS=("--attr chem_comp.name --attr chem_comp.pdbx_initial_date" "")
FIELDS=("" "--field chem_comp.formula --field chem_comp.name --field chem_comp.pdbx_synonyms --field chem_comp.three_letter_code --field chem_comp.id --field pdbx_chem_comp_descriptor.descriptor --field pdbx_chem_comp_feature.value --field pdbx_chem_comp_identifier.identifier")

dic_id=0

for dic_name in ${DIC_NAMES[@]} ; do

 attrs=${ATTRS[dic_id]}
 fields=${FIELDS[dic_id]}

 let dic_id++

 echo $dic_name

 rm -rf $WORK_DIR

 mkdir -p $WORK_DIR
 mkdir -p $ERR_DIR

 err_file=$ERR_DIR/all_err

 java -cp ../extlibs/xsd2pgschema.jar xml2sphinxds --xsd $XSD_SCHEMA --xml $XML_DIR --ds-dir $WORK_DIR --ds-name $PREFIX $attrs $fields --no-valid 2> $err_file

 if [ $? = 0 ] && [ ! -s $err_file ] ; then
  rm -f $err_file
 else
  echo "$0 aborted."
  exit 1
 fi

 red='\e[0;31m'
 normal='\e[0m'

 errs=`ls $ERR_DIR/*_err 2> /dev/null | wc -l`

 if [ $errs = 0 ] ; then

  echo

  if [ $dic_name = "all" ] ; then
   mkdir -p $IDX_DIR -m 777
   indexer $PREFIX
   indexer $PREFIX --buildstops $WORK_DIR/dictionary.txt 100000 --buildfreqs
  else
   indexer $PREFIX"_"$dic_name
   indexer $PREFIX"_"$dic_name --buildstops $WORK_DIR/dictionary.txt 100000 --buildfreqs
  fi

  mkdir -p $DIC_DIR -m 777

  java -cp ../extlibs/xsd2pgschema.jar dicmerge4sphinx --ds-dir $WORK_DIR --dic $WORK_DIR/dictionary.txt --freq 1

  if [ $dic_name = "all" ] ; then
   indexer $PREFIX"_dic"
  else
   indexer $PREFIX"_dic_"$dic_name
  fi

  if [ $? = 0 ] ; then

   echo "Sphinx index (Ligand Expo:"$dic_name") is update."

   rm -rf $WORK_DIR

  fi

 else

  echo
  echo -e "${red}$errs errors were detected. Please check the log files for more details.${normal}"

  exit 1

 fi

done

date

