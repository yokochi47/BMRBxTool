#!/bin/bash

PREFIX=bms
#ATOM=atom

if [ $# -ge 1  ] ; then

 OPTION=`echo $@ |\
 sed -e 's/\-\-home/\-h \-\-home \-\-/g' |\
 sed -e 's/\-\-url\-bmrb/\-u \-\-url\-bmrb \-\-/g' |\
 sed -e 's/\-\-pass\-bmrb/\-s \-\-pass\-bmrb \-\-/g' |\
 sed -e 's/\-\-url\-tax/\-x \-\-url\-tax \-\-/g' |\
 sed -e 's/\-\-pass\-tax/\-d \-\-pass\-tax \-\-/g' |\
 sed -e 's/\-\-mail\-to/\-t \-\-mail\-to \-\-/g' |\
 sed -e 's/\-\-mail\-from/\-f \-\-mail\-from \-\-/g' |\
 sed -e 's/\-\-smtp\-host/\-m \-\-smtp\-host \-\-/g' |\
 sed -e 's/\-\-max\-thrd/\-x \-\-max\-thrd \-\-/g' |\
 sed -e 's/\-\-no\-remediate/\-w \-\-no\-remediate/g' |\
 sed -e 's/\-\-no\-validate/\-n \-\-no\-validate/g' |\
 sed -e 's/\-\-validate\-only/\-v \-\-validate\-only/g' |\
 sed -e 's/\-\-well\-formed/\-e \-\-well\-formed/g' |\
 sed -e 's/\-\-noinit/\-o \-\-noinit/g' |\
 sed -e 's/\-\-help/\-l \-\-help/g'`

 ./scripts/db2xml.sh -p $PREFIX -a $ATOM $OPTION

else

 ./scripts/db2xml.sh -p $PREFIX -a $ATOM

fi

