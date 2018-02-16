#!/bin/bash

PREFIX=bmr

ARGV=`getopt --long -o "p:" "$@"`
eval set -- "$ARGV"
while true ; do
 case "$1" in
 -p)
  PREFIX=$2
  shift
 ;;
 *)
  break
 ;;
 esac
 shift
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

LUCENE_FTXT_DIR=lucene_ftxt_$PREFIX

rm -rf $LUCENE_FTXT_DIR

java -cp extlibs/xsd2pgschema.jar luceneidx2ftxt --idx-dir $LUCENE_IDX_DIR --ftxt-dir $LUCENE_FTXT_DIR --dic dictionary

java -cp extlibs/xsd2pgschema.jar luceneidx2ftxt --idx-dir $LUCENE_IDX_DIR --ftxt-dir $LUCENE_FTXT_DIR --dic dictionary.aut\
 --field entry_author.pdbx_name --field citation_author.pdbx_name

if [ $PREFIX = "bmr" ] ; then
 java -cp extlibs/xsd2pgschema.jar luceneidx2ftxt --idx-dir $LUCENE_IDX_DIR --ftxt-dir $LUCENE_FTXT_DIR --dic dictionary.pol\
  --field assembly.name --field citation.title --field citation_keyword.keyword --field entity.name --field entity_assembly.entity_assembly_name --field entity_db_link.entry_mol_name --field entry.title
fi

if [ $PREFIX = "bmr" ] ; then
 java -cp extlibs/xsd2pgschema.jar luceneidx2ftxt --idx-dir $LUCENE_IDX_DIR --ftxt-dir $LUCENE_FTXT_DIR --dic dictionary.lig\
  --field chem_comp.bmrb_code --field chem_comp.details --field chem_comp.formula --field chem_comp.inchi_code --field chem_comp.name --field chem_comp.pdb_code --field chem_comp.pubchem_code --field chem_comp.synonyms --field chem_comp.three_letter_code --field chem_comp.id --field chem_comp_descriptor.descriptor --field chem_comp_identifier.identifier --field chem_comp_bio_function.biological_function --field chem_comp_common_name.name --field chem_comp_db_link.entry_details --field chem_comp_db_link.entry_mol_code --field chem_comp_db_link.entry_mol_name --field chem_comp_db_link.accession_code --field chem_comp_keyword.keyword --field chem_comp_smiles.string --field chem_comp_systematic_name.name
else
 java -cp extlibs/xsd2pgschema.jar luceneidx2ftxt --idx-dir $LUCENE_IDX_DIR --ftxt-dir $LUCENE_FTXT_DIR --dic dictionary.lig\
  --field chem_comp.bmrb_code --field chem_comp.details --field chem_comp.formula --field chem_comp.inchi_code --field chem_comp.name --field chem_comp.pdb_code --field chem_comp.pubchem_code --field chem_comp.synonyms --field chem_comp.three_letter_code --field chem_comp.id --field chem_comp_descriptor.descriptor --field chem_comp_identifier.identifier --field chem_comp_bio_function.biological_function --field chem_comp_common_name.name --field chem_comp_db_link.entry_details --field chem_comp_db_link.entry_mol_code --field chem_comp_db_link.entry_mol_name --field chem_comp_db_link.accession_code --field chem_comp_keyword.keyword --field chem_comp_smiles.string --field chem_comp_systematic_name.name --field entity.name --field entity_assembly.entity_assembly_name --field entity_db_link.entry_mol_name --field entry.id --field entry.title
fi

if [ $PREFIX = "bmr" ] ; then
 java -cp extlibs/xsd2pgschema.jar luceneidx2ftxt --idx-dir $LUCENE_IDX_DIR --ftxt-dir $LUCENE_FTXT_DIR --dic dictionary.org\
  --field entity_natural_src.organism_name_scientific --field entity_natural_src.organism_name_common --field entity_natural_src.superkingdom --field entity_natural_src.kingdom --field entity_natural_src.genus --field entity_natural_src.species --field entity_natural_src.ncbi_taxonomy_id
fi

java -cp extlibs/xsd2pgschema.jar luceneidx2ftxt --idx-dir $LUCENE_IDX_DIR --ftxt-dir $LUCENE_FTXT_DIR --dic dictionary.dbac\
 --field entry.id

