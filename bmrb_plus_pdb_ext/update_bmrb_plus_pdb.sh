#!/bin/bash

MAXPROCS=`cat /proc/cpuinfo | grep 'cpu cores' | uniq | sed 's/\s//g' | cut -d ':' -f 2`
#MAXPROCS=`cat /proc/cpuinfo | grep 'cpu cores' | wc -l`

source ../scripts/db-user.sh

url_mirror=bmrb.pdbj.org

if [ -e url_mirror ] ; then

 url_mirror=`cat url_mirror`

fi

DUMP_PATH=ftp/pub/bmrb/nmr_pdb_integrated_data/coordinates_restraints_chemshifts
DB_NAME=bmrb_plus_pdb
DB_FTP=http://$url_mirror/$DUMP_PATH/$DB_NAME/

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

