#!/bin/bash

if [ ! `which psql` ] ; then

 echo "psql: command not found..."
 echo "Please install PostgreSQL (http://www.postgresql.org/)."

 exit 1

fi

source ../scripts/db-user.sh

BMRB_DB=bmrb
MTBL_DB=metabolomics

BMRB_MIRROR=("www.bmrb.wisc.edu" "bmrb.pdbj.org" "bmrb.cerm.unifi.it")
DUMP_PATH=ftp/pub/bmrb/relational_tables

psql -U $DB_USER -l | grep $BMRB_DB > /dev/null

if [ $? != "0" ] ; then

 echo "database \"$BMRB_DB\" does not exist."

 exit 1

fi

psql -U $DB_USER -l | grep $MTBL_DB > /dev/null

if [ $? != "0" ] ; then

 echo "database \"$MTBL_DB\" does not exist."

 exit 1

fi

printf "    BMRB mirror sites\t\t delay [ms]\n"
echo "-------------------------------------------"

BMRB_URL=${BMRB_MIRROR[0]}

delay=10000
i=1

for url in ${BMRB_MIRROR[@]}
do

 time=`ping -c 1 -w 10 $url | grep 'avg' | cut -d '=' -f 2 | cut -d '/' -f 2`

 if [ $? = "0" ] ; then

  printf "[%d] %s\t\t%6.1f\n" $i $url $time

  cmp=`echo "$time > $delay" | bc`

  if [ $cmp = "0" ] ; then
   BMRB_URL=$url
   delay=$time
  fi

 else
  echo $url: timed out.
 fi

 let i++

done

echo
echo "$BMRB_URL was selected. OK (1/2/3/n [y]) ? "

read ans

case $ans in
 n*|N*)
  echo stopped.
  exit 1;;
 1) BMRB_URL=${BMRB_MIRROR[0]};;
 2) BMRB_URL=${BMRB_MIRROR[1]};;
 3) BMRB_URL=${BMRB_MIRROR[2]};;
 *) ;;
esac

echo $BMRB_URL > ../lacs_ext/url_mirror

if [ -e url_mirror ] ; then

 BMRB_URL=`cat url_mirror`

fi

rm -rf $DUMP_PATH

NMR_STAR3_1=nmr-star3.1

BMRB_FTP=http://$BMRB_URL/$DUMP_PATH/$NMR_STAR3_1/
MTBL_FTP=http://$BMRB_URL/$DUMP_PATH/$MTBL_DB/

wget -c -r -nv -np $BMRB_FTP -nH -R index*
wget -c -r -nv -np $MTBL_FTP -nH -R index*

cd $DUMP_PATH/$NMR_STAR3_1

if [ $? != "0" ] ; then

 exit 1

fi

psql -d $BMRB_DB -U $DB_USER -f schema.sql

for FILE in *.csv
do

 BASENAME=`basename $FILE .csv`
 echo $BASENAME | grep -E "^dict\." > /dev/null

 if [ $? != "0" ] ; then

  echo $BASENAME | grep -E "^web\." > /dev/null

  if [ $? != "0" ] ; then

   echo $BASENAME | grep -E "^pacsy\." > /dev/null

   if [ $? != "0" ] ; then

    if [ $BASENAME != "Task" ] && [ $BASENAME != "Software" ] && [  $BASENAME != "Vendor" ] ; then
     psql -U $DB_USER -d $BMRB_DB -c "\COPY \"${BASENAME}\" FROM $FILE CSV HEADER"
    else
     cp $FILE $FILE~
     PACSY_FILE=pacsy.${BASENAME,}.csv
     if [ -e $PACSY_FILE ] ; then
      sed 1d $PACSY_FILE >> $FILE~
     fi
     psql -U $DB_USER -d $BMRB_DB -c "\COPY \"${BASENAME}\" FROM $FILE~ CSV HEADER"
     rm -f $FILE~
    fi

   else

    _BASENAME=`echo $BASENAME | sed -e 's/pacsy\.//'`

    if [ ${_BASENAME^} != "Task" ] && [ ${_BASENAME^} != "Software" ] && [  ${_BASENAME^} != "Vendor" ] ; then
     psql -U $DB_USER -d $BMRB_DB -c "\COPY \"${_BASENAME^}\" FROM $FILE CSV HEADER"
    fi

   fi

  fi

 fi

done

cd ../$MTBL_DB

if [ $? != "0" ] ; then

 exit 1

fi

psql -d $MTBL_DB -U $DB_USER -f schema.sql

for FILE in *.csv
do

 BASENAME=`basename $FILE .csv`
 echo $BASENAME | grep -E "^dict\." > /dev/null

 if [ $? != "0" ] ; then

  echo $BASENAME | grep -E "^meta\." > /dev/null

  if [ $? != "0" ] ; then

   psql -U $DB_USER -d $MTBL_DB -c "\COPY \"${BASENAME}\" FROM $FILE CSV HEADER"

  fi

 fi

done

cd ../../../../..

psql -U $DB_USER -d $BMRB_DB -f index_bmrb.sql

