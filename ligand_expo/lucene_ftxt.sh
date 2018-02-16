#!/bin/bash

LUCENE_IDX_DIR=lucene_index

if [ ! -d $LUCENE_IDX_DIR ] ; then

 echo $LUCENE_IDX_DIR is not directory.
 exit 1

fi

LUCENE_FTXT_DIR=lucene_ftxt

rm -rf $LUCENE_FTXT_DIR

java -cp ../extlibs/xsd2pgschema.jar luceneidx2ftxt --idx-dir $LUCENE_IDX_DIR --ftxt-dir $LUCENE_FTXT_DIR --dic dictionary

java -cp ../extlibs/xsd2pgschema.jar luceneidx2ftxt --idx-dir $LUCENE_IDX_DIR --ftxt-dir $LUCENE_FTXT_DIR --dic dictionary.lig\
 --field chem_comp.formula --field chem_comp.name --field chem_comp.pdbx_synonyms --field chem_comp.three_letter_code --field chem_comp.id --field pdbx_chem_comp_descriptor.descriptor --field pdbx_chem_comp_feature.value --field pdbx_chem_comp_identifier.identifier

