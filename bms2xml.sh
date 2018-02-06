#!/bin/bash

OPTION=`echo $@ |\
 sed -e 's/\-\-noatom//g' |\
 sed -e 's/\-a atom//g' |\
 sed -e 's/\-a noatom//g'`

if [[ $@ == *noatom* ]] ; then
 export ATOM=noatom
else
 export ATOM=atom
fi

./scripts/_bms2xml.sh $@

