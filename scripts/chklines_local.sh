#!/bin/bash

XML_DOC_DIR=.

files=0
lines=0

for file in `ls $XML_DOC_DIR/*.xml 2> /dev/null`
do

 BASENAME=`basename $file .xml`
 XML_DOC_FILE=$XML_DOC_DIR/$BASENAME.xml

 if [ -e $XML_DOC_FILE ] ; then

  echo -n .

  line=`wc -l $XML_DOC_FILE | cut -d ' ' -f 1`
  lines=`expr $lines + $line`
  let files++

 fi

done

echo

echo "Total files: $files"
echo "Total lines: $lines"

