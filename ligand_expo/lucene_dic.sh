#!/bin/bash

LUCENE_IDX_DIR=lucene_index

if [ ! -d $LUCENE_IDX_DIR ] ; then

 echo $LUCENE_IDX_DIR is not directory.

 exit 1

fi

LUCENE_DIC_DIR=lucene_dic

rm -rf $LUCENE_DIC_DIR

java -cp ../xsd2pgschema.jar luceneidx2dic --idx-dir $LUCENE_IDX_DIR --dic-dir $LUCENE_DIC_DIR --dic dictionary

java -cp ../xsd2pgschema.jar luceneidx2dic --idx-dir $LUCENE_IDX_DIR --dic-dir $LUCENE_DIC_DIR --dic dictionary.lig\
 --field chem_comp.formula --field chem_comp.name --field chem_comp.pdbx_synonyms --field chem_comp.three_letter_code --field chem_comp.id --field pdbx_chem_comp_descriptor.descriptor --field pdbx_chem_comp_feature.value --field pdbx_chem_comp_identifier.identifier

