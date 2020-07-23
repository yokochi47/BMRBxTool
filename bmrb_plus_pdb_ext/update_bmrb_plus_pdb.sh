#!/bin/bash

MAXPROCS=`cat /proc/cpuinfo | grep 'cpu cores' | uniq | sed 's/\s//g' | cut -d ':' -f 2`
#MAXPROCS=`cat /proc/cpuinfo | grep 'cpu cores' | wc -l`

source ../scripts/db-user.sh

BMRB_MIRRORS=("www.bmrb.wisc.edu" "bmrb.pdbj.org" "bmrb.cerm.unifi.it")

printf "    BMRB mirror sites\t\t delay [ms]\n"
echo "-------------------------------------------"

BMRB_MIRROR=${BMRB_MIRRORS[0]}

delay=10000
i=1

for url in ${BMRB_MIRRORS[@]}
do

 time=`ping -c 1 -w 10 $url | grep 'avg' | cut -d '=' -f 2 | cut -d '/' -f 2`

 if [ $? = 0 ] ; then

  printf "[%d] %s\t\t%6.1f\n" $i $url $time

  cmp=`echo "$time > $delay" | bc`

  if [ "$cmp" = 0 ] ; then

   server_alive=`curl -I $url -m 5 2> /dev/null`

   if [ $? == 0 ] ; then

    BMRB_MIRROR=$url
    delay=$time

   fi

  fi

 else
  echo $url: timed out.
 fi

 let i++

done

DUMP_PATH=ftp/pub/bmrb/nmr_pdb_integrated_data/coordinates_restraints_chemshifts
DB_NAME=bmrb_plus_pdb
DB_FTP=http://$BMRB_MIRROR/$DUMP_PATH/$DB_NAME/

wget -c -m -nv -np $DB_FTP -nH -A *.str

echo
echo "Do you want to update BMRB+PDB? (y [n]) "

read ans

case $ans in
 y*|Y*) ;;
 *) echo skipped.
    exit 1;;
esac

if [ ! `which psql` ] ; then

 echo "psql: command not found..."
 echo "Please install PostgreSQL (http://www.postgresql.org/)."
 exit 1

fi

source ../scripts/db-user.sh

BMRB_DB=bmrb

psql -U $DB_USER -l | grep $BMRB_DB > /dev/null || ( echo "database \"$BMRB_DB\" does not exist." && exit 1 )

psql -d $BMRB_DB -U $DB_USER -c "drop index if exists atom_site_index;"

for proc_id in `seq 1 $MAXPROCS` ; do
 _proc_id=`expr $proc_id - 1`
 work_dir=work.$_proc_id
 java -classpath ../bmrb-plus-pdb-ext.jar:../extlibs/* BMRB_plus_PDB_ext --home .. --csv-dir $work_dir --user-bmrb $DB_USER --thrd-id $_proc_id --max-thrds $MAXPROCS &
done

wait

echo

if [ $? != 0 ] ; then
 echo $0 aborted.
 exit 1
fi

for proc_id in `seq 1 $MAXPROCS` ; do
 _proc_id=`expr $proc_id - 1`
 work_dir=work.$_proc_id
 rm -rf $work_dir
done

psql -d $BMRB_DB -U $DB_USER -f index_bmrb.sql

vacuumdb -d $BMRB_DB -U $DB_USER -f

#reindexdb -d $BMRB_DB -U $DB_USER

