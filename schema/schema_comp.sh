#!/bin/sh

if [ ! `which scomp` ] ; then

 echo "scomp: command not found..."
 echo "Please install XMLBeans (http://xmlbeans.apache.org/)."

 exit 1

fi

if [ ! `which javac` ] ; then

 echo "javac: command not found..."
 exit 1

fi

NMRSTAR_DICT_FILE=NMR-STAR.dic

DICT_PREFIX=mmcif_nmr-star

if [ -e $NMRSTAR_DICT_FILE ] ; then

 arg=(`grep dictionary.version $NMRSTAR_DICT_FILE`)
 DICT_VERSION=${arg[1]}

fi

grep -v "enumeration value=\"\"" $DICT_PREFIX-v$DICT_VERSION.xsd > $DICT_PREFIX-v$DICT_VERSION.xsd~
mv $DICT_PREFIX-v$DICT_VERSION.xsd~ $DICT_PREFIX-v$DICT_VERSION.xsd

scomp $DICT_PREFIX-v$DICT_VERSION.xsd -out mmcifNmrStar.jar -compiler `which javac`

