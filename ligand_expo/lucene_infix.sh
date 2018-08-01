#!/bin/bash

LUCENE_IDX_DIR=lucene_index

if [ ! -d $LUCENE_IDX_DIR ] ; then

 echo $LUCENE_IDX_DIR is not directory.
 exit 1

fi

LUCENE_INFIX_DIR=lucene_infix

rm -rf $LUCENE_INFIX_DIR

java -cp ../extlibs/xsd2pgschema.jar luceneidx2infix --idx-dir $LUCENE_IDX_DIR --infix-dir $LUCENE_INFIX_DIR --freq 1

