#!/bin/sh

NMRSTAR_DIC_FILE=../NMR-STAR.dic

if [ -e $NMRSTAR_DIC_FILE ] ; then

 arg=(`tr -d '\r' < $NMRSTAR_DIC_FILE | grep dictionary.version`)
 DIC_VERSION=${arg[1]}

fi

BMRB_URL=svn.bmrb.wisc.edu
NMRSTAR_DIC_DIR=http://$BMRB_URL/svn/nmr-star-dictionary/bmrb_only_files/adit_input
NMRSTAR_DIC_DIR=https://raw.githubusercontent.com/uwbmrb/nmr-star-dictionary/7061314ffb32ba56b92392d7453cea1495d48db0
NMRSTAR_DIC_FILE=NMR-STAR.dic

DIC_PREFIX=mmcif_nmr-star

rm -f $NMRSTAR_DIC_FILE

wget -c $NMRSTAR_DIC_DIR/$NMRSTAR_DIC_FILE --no-check-certificate
tr -d '\r' < $NMRSTAR_DIC_FILE > $NMRSTAR_DIC_FILE~
sed -e 's/^   _pdbx_item_enumeration_details/#  _pdbx_item_enumeration_details/' $NMRSTAR_DIC_FILE~ > $NMRSTAR_DIC_FILE
rm -f $NMRSTAR_DIC_FILE~

if [ -e $NMRSTAR_DIC_FILE ] ; then

 arg=(`tr -d '\r' < $NMRSTAR_DIC_FILE | grep dictionary.version`)

 if [ ${arg[1]} != $DIC_VERSION ] ; then

  echo udpate was canceled because dictionary version was inconsistent.
  exit 1

 fi

fi

diff -c $NMRSTAR_DIC_FILE $DIC_PREFIX-v$DIC_VERSION.dic > $NMRSTAR_DIC_FILE.patch

