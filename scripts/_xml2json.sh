#!/bin/bash

source ./scripts/otool-home.sh

PREFIX=
UPDATE=yes
ATOM=atom

red='\e[0;31m'
normal='\e[0m'

ARGV=`getopt --long -o "p:u:a:" "$@"`
eval set -- "$ARGV"
while true ; do
 case "$1" in
 -p)
  PREFIX=$2
  shift
 ;;
 -u)
  UPDATE=$2
  shift
 ;;
 -a)
  ATOM=$2
  shift
 ;;
 *)
  break
 ;;
 esac
 shift
done

if [ $PREFIX != "bmr" ] && [ $PREFIX != "bms" ] ; then

 echo "Usage: $0 -p PREFIX"
 echo PREFIX should be either \"bmr\" or \"bms\".
 exit 1

fi

if [ $ATOM != "noatom" ] && [ $ATOM != "atom" ] ; then

 echo "Usage: $0 -a ATOM"
 echo ATOM should be either \"noatom\" or \"atom\".
 exit 1

fi

if [ $ATOM = "atom" ] ; then

 XML_RAW_DIR=$PREFIX"_xml_raw"
 XML_UPDATE_DIR=$PREFIX"_xml_raw.update"
 XML_ULIST=$PREFIX"_xml_ulist"
 XML_REL_DIR=$PREFIX"_xml_rel"
 JSON_DOC_DIR=$PREFIX"_json_doc"
 FILE_EXT_DIGEST=.

else

 XML_RAW_DIR=$PREFIX"_xml_"$ATOM"_raw"
 XML_UPDATE_DIR=$PREFIX"_xml_"$ATOM"_raw.update"
 XML_ULIST=$PREFIX"_xml_"$ATOM"_ulist"
 XML_REL_DIR=$PREFIX"_xml_"$ATOM"_rel"
 JSON_DOC_DIR=$PREFIX"_json_"$ATOM"_doc"
 FILE_EXT_DIGEST=-noatom

fi

if [[ $UPDATE =~ ^[nN].* ]] ; then
 rm -rf $JSON_DOC_DIR
fi

mkdir -p $JSON_DOC_DIR

./$PREFIX"unzip_xml.sh" -a $ATOM

rm -rf $XML_UPDATE_DIR
mkdir -p $XML_UPDATE_DIR

cd $XML_UPDATE_DIR

xml_file_list=xml_file_list

find ../$XML_RAW_DIR -name '*.xml' > $xml_file_list

if [[ $UPDATE =~ ^[yY].* ]] ; then

 if [ -e ../$XML_ULIST ] ; then

  for BASENAME in `cat ../$XML_ULIST | sort | uniq`
  do

   xml_file=../$XML_RAW_DIR/$BASENAME.xml

   ln -f -s $xml_file .

  done

 fi

 for rel_file in `ls ../$XML_REL_DIR 2> /dev/null`
 do

  BASENAME=`basename $rel_file _last`

  if [ ! -e ../$JSON_DOC_DIR/$BASENAME.json.gz ] ; then

   xml_file=../$XML_RAW_DIR/$BASENAME.xml

   ln -f -s $xml_file .

  fi

 done

else

 while read xml_file
 do

  ln -f -s $xml_file .

 done < $xml_file_list

fi

rm -f $xml_file_list

cd ..

XML_SCHEMA=schema/mmcif_nmr-star.xsd

if [ -e $BMRBO_TOOL_HOME/$XML_SCHEMA ] ; then
 XML_SCHEMA=$BMRBO_TOOL_HOME/$XML_SCHEMA
fi

ERR_DIR=$JSON_DOC_DIR/err

mkdir -p $ERR_DIR

err_file=$ERR_DIR/all_err

java -cp extlibs/xsd2pgschema.jar xml2json --xsd $XML_SCHEMA --xml $XML_UPDATE_DIR --xml-file-ext-digest $FILE_EXT_DIGEST --no-valid --col-json --discarded-doc-key-name entry_id --json-dir $JSON_DOC_DIR 2> $err_file

if [ $? = 0 ] && [ ! -s $err_file ] ; then
 rm -f $err_file
else
 echo $0 aborted.
 exit 1
fi

errs=`ls $ERR_DIR/*_err 2> /dev/null | wc -l`

if [ $errs = 0 ] ; then

 rm -rf $ERR_DIR

 cd $JSON_DOC_DIR

 if [[ $UPDATE =~ ^[nN].* ]] ; then

  gzip -r *

 else

  for file in `ls *.json 2> /dev/null`
  do

   gzip -f $file

  done

 fi

 if [ $? = 0 ] ; then

  echo

  if [ $ATOM = "atom" ] ; then
   echo "XML->JSON conversion (prefix:"$PREFIX") is completed."
  else
   echo "XML->JSON conversion (prefix:"$PREFIX"-"$ATOM") is completed."
  fi

  rm -rf ../$XML_UPDATE_DIR

 fi

else

 echo
 echo -e "${red}$errs errors were detected. Please check the log files for more details.${normal}"
 exit 1

fi

date

