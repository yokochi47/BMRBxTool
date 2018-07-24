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

NMRSTAR_DIC_FILE=NMR-STAR.dic

DIC_PREFIX=mmcif_nmr-star

if [ -e $NMRSTAR_DIC_FILE ] ; then

 arg=(`tr -d '\r' < $NMRSTAR_DIC_FILE | grep dictionary.version`)
 DIC_VERSION=${arg[1]}

fi

grep -v "enumeration value=\"\"" $DIC_PREFIX-v$DIC_VERSION.xsd > $DIC_PREFIX-v$DIC_VERSION.xsd~
mv $DIC_PREFIX-v$DIC_VERSION.xsd~ $DIC_PREFIX-v$DIC_VERSION.xsd

scomp $DIC_PREFIX-v$DIC_VERSION.xsd -out mmcifNmrStar.jar -compiler `which javac`

