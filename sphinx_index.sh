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

if [ ! `which indexer` ] ; then

 echo "indexer: command not found..."
 echo "Please install Sphinx (http://sphinxsearch.com/)."
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

IDX_DIR=sphinx_index_$PREFIX
DIC_DIR=sphinx_dic_$PREFIX

if [ -d $IDX_DIR ] ; then

 echo
 echo "Do you want to update sphinx index? (y [n]) "

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

DIC_NAMES=("all" "aut" "pol" "lig" "org")

attrs_bmr=("--attr entry.id --attr entry.title --attr entry.original_release_date" "" "" "" "")
attrs_bms=("--attr chem_comp.name --attr entry.original_release_date" "" "" "" "")

fields_bmr=("--field-deny entity.polymer_seq_one_letter_code --field-deny entity.polymer_seq_one_letter_code_can" "--field entry_author.pdbx_name --field citation_author.pdbx_name" "--field assembly.name --field citation.title --field citation_keyword.keyword --field entity.name --field entity_assembly.entity_assembly_name --field entity_db_link.entry_mol_name --field entry.title" "--field chem_comp.bmrb_code --field chem_comp.details --field chem_comp.formula --field chem_comp.inchi_code --field chem_comp.name --field chem_comp.pdb_code --field chem_comp.pubchem_code --field chem_comp.synonyms --field chem_comp.three_letter_code --field chem_comp.id --field chem_comp_descriptor.descriptor --field chem_comp_identifier.identifier --field chem_comp_bio_function.biological_function --field chem_comp_common_name.name --field chem_comp_db_link.entry_details --field chem_comp_db_link.entry_mol_code --field chem_comp_db_link.entry_mol_name --field chem_comp_db_link.accession_code --field chem_comp_keyword.keyword --field chem_comp_smiles.string --field chem_comp_systematic_name.name" "--field entity_natural_src.organism_name_scientific --field entity_natural_src.organism_name_common --field entity_natural_src.superkingdom --field entity_natural_src.kingdom --field entity_natural_src.genus --field entity_natural_src.species --field entity_natural_src.ncbi_taxonomy_id")

fields_bms=("" "--field entry_author.pdbx_name --field citation_author.pdbx_name" "" "--field chem_comp.bmrb_code --field chem_comp.details --field chem_comp.formula --field chem_comp.inchi_code --field chem_comp.name --field chem_comp.pdb_code --field chem_comp.pubchem_code --field chem_comp.synonyms --field chem_comp.three_letter_code --field chem_comp.id --field chem_comp_descriptor.descriptor --field chem_comp_identifier.identifier --field chem_comp_bio_function.biological_function --field chem_comp_common_name.name --field chem_comp_db_link.entry_details --field chem_comp_db_link.entry_mol_code --field chem_comp_db_link.entry_mol_name --field chem_comp_db_link.accession_code --field chem_comp_keyword.keyword --field chem_comp_smiles.string --field chem_comp_systematic_name.name --field entity.name --field entity_assembly.entity_assembly_name --field entity_db_link.entry_mol_name --field entry.id --field entry.title" "")

dic_id=0

for dic_name in ${DIC_NAMES[@]} ; do

 if [ $PREFIX = "bmr" ] ; then
  attrs=${attrs_bmr[dic_id]}
  fields=${fields_bmr[dic_id]}
 else
  attrs=${attrs_bms[dic_id]}
  fields=${fields_bms[dic_id]}
 fi

 let dic_id++

 if [ $dic_name != "all" ] && [ -z "$fields" ] ; then
  continue
 fi

 echo $dic_name

 WORK_DIR=sphinx_work_$PREFIX"_"$dic_name
 DIC_WORK_DIR=sphinx_dic_work_$PREFIX"_"$dic_name
 ERR_DIR=$WORK_DIR/err

 if [ $sync_update != "true" ] ; then
  rm -rf $WORK_DIR
 else
  MD5_DIR=chk_sum_sphinx_$dic_name
 fi

 mkdir -p $WORK_DIR
 mkdir -p $DIC_WORK_DIR
 mkdir -p $ERR_DIR

 err_file=$ERR_DIR/all_err

 if [ $sync_update != "true" ] ; then

  java -cp extlibs/xsd2pgschema.jar xml2sphinxds --xsd $XML_SCHEMA --xml $XML_DOC_DIR --ds-dir $WORK_DIR --ds-name $PREFIX --no-valid --xml-file-ext gz --xml-file-ext-digest $FILE_EXT_DIGEST $attrs $fields 2> $err_file

 else

  java -cp extlibs/xsd2pgschema.jar xml2sphinxds --xsd $XML_SCHEMA --xml $XML_DOC_DIR --ds-dir $WORK_DIR --ds-name $PREFIX --no-valid --xml-file-ext gz --xml-file-ext-digest $FILE_EXT_DIGEST $attrs $fields --sync $MD5_DIR 2> $err_file

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

  echo

  if [ $dic_name = "all" ] ; then
   mkdir -p $IDX_DIR -m 777
   indexer $PREFIX
   indexer $PREFIX --buildstops $DIC_WORK_DIR/dictionary.txt 100000 --buildfreqs
  else
   indexer $PREFIX"_"$dic_name
   indexer $PREFIX"_"$dic_name --buildstops $DIC_WORK_DIR/dictionary.txt 100000 --buildfreqs
  fi

  mkdir -p $DIC_DIR -m 777

  java -cp extlibs/xsd2pgschema.jar dicmerge4sphinx --ds-dir $DIC_WORK_DIR --dic $DIC_WORK_DIR/dictionary.txt --freq 1

  if [ $dic_name = "all" ] ; then
   indexer $PREFIX"_dic"
  else
   indexer $PREFIX"_dic_"$dic_name
  fi

  if [ $? = 0 ] ; then

   if [ $ATOM = "atom" ] ; then
    echo "Sphinx index (prefix:"$PREFIX":"$dic_name") is update."
   else
    echo "Sphinx index (prefix:"$PREFIX"-"$ATOM":"$dic_name") is update."
   fi

   if [ $sync_update != "true" ] ; then
    rm -rf $WORK_DIR
   else
    rm -rf $ERR_DIR
   fi

  fi

 else

  echo
  echo -e "${red}$errs errors were detected. Please check the log files for more details.${normal}"
  exit 1

 fi

done

date

