#!/bin/bash

MAXPROCS=`cat /proc/cpuinfo 2> /dev/null | grep 'cpu cores' | uniq | sed 's/\s//g' | cut -d ':' -f 2`
#MAXPROCS=`cat /proc/cpuinfo 2> /dev/null | grep 'cpu cores' | wc -l`

if [ $MAXPROCS = 0 ] ; then
 MAXPROCS=1
fi

PREFIX=
ATOM=atom
OPTION=
INIT=true

# Please edit the following mail settings for your organization.
source ./scripts/db-user.sh

SCHEMA_HOST=bmrbpub.protein.osaka-u.ac.jp
MAIL_TO=bmrbsys@protein.osaka-u.ac.jp
MAIL_FROM=webmaster@bmrbpub.protein.osaka-u.ac.jp
SMTP_HOST=postman.protein.osaka-u.ac.jp

ZIP=gzip

red='\e[0;31m'
normal='\e[0m'

ARGV=`getopt --long -o "p:a:h:u:s:x:d:t:f:m:x:w:n:v:e:o:l:" "$@"`

eval set -- "$ARGV"
while true ; do
 case "$1" in
 -p)
  PREFIX=$2
  shift
 ;;
 -a)
  ATOM=$2
  shift
 ;;
 -h|-u|-s|-x|-d|-t|-f|-m|-x)
  OPTION=$OPTION" "$2" "$4
  shift 3
 ;;
 -w|-n|-v|-e|-l)
  OPTION=$OPTION" "$2
  shift
 ;;
 -o)
  INIT=false
  OPTION=$OPTION" "$2
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

 XML_LOC_DIR=$PREFIX"_xml_loc"
 XML_ULIST=$PREFIX"_xml_ulist"
 XML_REL_DIR=$PREFIX"_xml_rel"
 XML_DOC_DIR=$PREFIX"_xml_doc"
 XML_ERR_DIR=$PREFIX"_xml_err"
 XML_RAW_DIR=$PREFIX"_xml_raw"

else

 XML_LOC_DIR=$PREFIX"_xml_"$ATOM"_loc"
 XML_ULIST=$PREFIX"_xml_"$ATOM"_ulist"
 XML_REL_DIR=$PREFIX"_xml_"$ATOM"_rel"
 XML_DOC_DIR=$PREFIX"_xml_"$ATOM"_doc"
 XML_ERR_DIR=$PREFIX"_xml_"$ATOM"_err"
 XML_RAW_DIR=$PREFIX"_xml_"$ATOM"_raw"

fi

rm -f $XML_DOC_DIR/*.xml~

for file in `ls $XML_REL_DOC/*.xml 2> /dev/null`
do

 BASENAME=`basename $file .xml`
 rm -f $XML_REL_DIR/$BASENAME"_last"

done

success=true
i=1

errproc () {

 BASENAME=$1
 ERR_CODE=$2

 rm -f $XML_REL_DIR/$BASENAME"_last"

 if [ $ERR_CODE = 0 ] ; then
  echo -e "${red}$BASENAME.xml empty.${normal}"
 else
  echo -e "${red}$BASENAME.xml corrupted (code:$ERR_CODE).${normal}"
 fi

 success=false

}

finalize () {

 rm -f $XML_DOC_DIR/*.xml~

 errs=`ls $XML_ERR_DIR/*_err 2> /dev/null | wc -l`

 if [ $errs != 0 ] ; then

  for file in $XML_ERR_DIR/*_err
  do

   if [ ! -s $file ] ; then
    rm -f $file
   fi

  done

 fi

 errs=`ls $XML_ERR_DIR/*_err 2> /dev/null | wc -l`

 if [ $errs = 0 ] ; then
  rm -rf $XML_ERR_DIR
 fi

}

while true
do

 java -classpath $PREFIX-util.jar:schema/mmcifNmrStar.jar:extlibs/* jp.ac.osakaU.proteni.bmrbpub.schema.mmcifNmrStar.$PREFIX"_Util_Main" $OPTION --$ATOM\
 --user-bmrb $DB_USER --user-tax $DB_USER --user-le $DB_USER --mail-to $MAIL_TO --mail-from $MAIL_FROM --smtp-host $SMTP_HOST --max-thrds $MAXPROCS || exit 1

 if [ -d $XML_LOC_DIR ] || [ $INIT != "true" ] ; then

  if [ $INIT = "true" ] ; then

   finalize

  fi

  exit 0

 else

  success=true

  for file in `ls $XML_REL_DIR/*_last 2> /dev/null`
  do

   BASENAME=`basename $file _last`
   XML_DOC_FILE=$XML_DOC_DIR/$BASENAME.xml

   if [ -e $XML_DOC_FILE ] ; then

    if [ ! -s $XML_DOC_FILE ] ; then

     errproc $BASENAME 0

    else

     $ZIP -f $XML_DOC_FILE
     echo $BASENAME.xml.gz done.

     grep null $file > /dev/null

     if [ $? != 0 ] ; then
      echo $BASENAME >> $XML_ULIST
     fi

     if [ -d $XML_RAW_DIR ]; then
      rm -f $XML_RAW_DIR/$BASENAME.xml
     fi

    fi

   fi

  done

  for file in `ls $XML_REL_DIR/*_last 2> /dev/null`
  do

   BASENAME=`basename $file _last`
   XML_GZ_FILE=$XML_DOC_DIR/$BASENAME.xml.gz

   if [ ! -e $XML_GZ_FILE ] ; then

    errproc $BASENAME 0

   elif [ ! -s $XML_GZ_FILE ] ; then

    errproc $BASENAME 0

   fi

  done

  if [ $success = "true" ]; then
   break
  fi

 fi

 let i++

 if [ $i -ge 3 ] ; then
  break
 fi

done

finalize

