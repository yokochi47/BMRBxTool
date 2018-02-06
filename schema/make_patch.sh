#!/bin/sh

DICT_VERSION=3.2.0.15

BMRB_URL=svn.bmrb.wisc.edu
NMRSTAR_DICT_DIR=http://$BMRB_URL/svn/nmr-star-dictionary/bmrb_only_files/adit_input
NMRSTAR_DICT_FILE=NMR-STAR.dic

DICT_PREFIX=mmcif_nmr-star

rm -f $NMRSTAR_DICT_FILE

wget -c $NMRSTAR_DICT_DIR/$NMRSTAR_DICT_FILE --no-check-certificate
tr -d '\r' < $NMRSTAR_DICT_FILE > $NMRSTAR_DICT_FILE~
sed -e 's/^   _pdbx_item_enumeration_details/#  _pdbx_item_enumeration_details/' $NMRSTAR_DICT_FILE~ > $NMRSTAR_DICT_FILE
rm -f $NMRSTAR_DICT_FILE~

if [ -e $NMRSTAR_DICT_FILE ] ; then

 arg=(`grep dictionary.version $NMRSTAR_DICT_FILE`)

 if [ ${arg[1]} != $DICT_VERSION ] ; then

  echo udpate was canceled because dictionary version was inconsistent.
  exit 1

 fi

fi

diff -c $NMRSTAR_DICT_FILE $DICT_PREFIX-v$DICT_VERSION.dic > $NMRSTAR_DICT_FILE.patch

