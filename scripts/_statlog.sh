#!/bin/bash

#PREFIX=bmr
ATOM=atom
VERBOSE=no

ARGV=`getopt --long -o "a:" "$@"`
eval set -- "$ARGV"
while true ; do
 case "$1" in
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

if [ $ATOM != "noatom" ] && [ $ATOM != "atom" ] ; then

 echo "Usage: $0 -a ATOM"
 echo ATOM should be either \"noatom\" or \"atom\".
 exit 1

fi

if [ $ATOM = "atom" ] ; then

 XML_REL_DIR=$PREFIX"_xml_rel"
 XML_REP_DIR=$PREFIX"_xml_rep"

else

 XML_REL_DIR=$PREFIX"_xml_"$ATOM"_rel"
 XML_REP_DIR=$PREFIX"_xml_"$ATOM"_rep"

fi

if [ ! -d $XML_REL_DIR ] ; then

 echo "Couldn't find $XML_REL_DIR directory."
 exit 1

fi

if [ ! -d $XML_REP_DIR ] ; then

 echo "ouldn't find $XML_REP_DIR directory."
 exit 1

fi

total=0

count_0=0
count_5=0
count_10=0
count_20=0
count_50=0
count_50_=0

errors=0

for file in `ls $XML_REL_DIR/*_last 2> /dev/null`
do

 echo -n .

 BASENAME=`basename $file _last`
 XML_REP_FILE=$XML_REP_DIR/$BASENAME"_log"
 XML_REP_FILE_=$XML_REP_DIR/$BASENAME"_log_"

 if [ -e $XML_REP_FILE ] ; then

  grep -v "value='null'" $XML_REP_FILE | grep category | grep item | grep value | grep -v ChemComp | grep -v OrderParam | grep -v StructAnno | sort -n -r | sed -e 's/  //g' | cut -d ' ' -f 1 > $XML_REP_FILE_

  error=0

  while read _error
  do

   if [ -z "$_error" ] ; then
    continue
   fi

   error=`expr $error + $_error`

  done < $XML_REP_FILE_

  rm -f $XML_REP_FILE_

  if [ $error -eq 0 ] ; then
   let count_0++
  elif [ $error -le 5 ] ; then
   let count_5++
  elif [ $error -le 10 ] ; then
   let count_10++
  elif [ $error -le 20 ] ; then
   let count_20++
  elif [ $error -le 50 ] ; then
   let count_50++
  else
   let count_50_++
   echo $BASENAME"(>50)"
  fi

 else
   let count_0++
 fi

 let total++

 errors=`expr $errors + $error`

done

echo

share_0=`echo "scale=3; $count_0 / $total * 100.0" | bc`
share_5=`echo "scale=3; $count_5 / $total * 100.0" | bc`
share_10=`echo "scale=3; $count_10 / $total * 100.0" | bc`
share_20=`echo "scale=3; $count_20 / $total * 100.0" | bc`
share_50=`echo "scale=3; $count_50 / $total * 100.0" | bc`
share_50_=`echo "scale=3; $count_50_ / $total * 100.0" | bc`

printf "0\t%d (%.1f)\n" $count_0 $share_0
printf "1-5\t%d (%.1f)\n" $count_5 $share_5
printf "6-10\t%d (%.1f)\n" $count_10 $share_10
printf "11-20\t%d (%.1f)\n" $count_20 $share_20
printf "21-50\t%d (%.1f)\n" $count_50 $share_50
printf "50-\t%d (%.1f)\n" $count_50_ $share_50_

echo "total entries: "$total
echo "total remediated data: "$errors

