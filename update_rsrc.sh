#!/bin/bash

if [ $# = 0 ] || [ $1 = "schema" ] ; then
 cd schema
 ./update_schema.sh
 ./update_obsolete_pdb_id.sh
 if [ -e ETS-Entry_log.txt ] ; then
  ./extract_ets.sh
 fi
 cd ..
 if [ -e queries/connect_bmrb_id_and_pdb_id_via_pubmed_id.sql ] ; then
  ./run_sparql.sh -s yes -e pub -q queries/connect_bmrb_id_and_pdb_id_via_pubmed_id.sql | sed -e 1d | sed -e 's/"//g' > schema/BMRB_PDB_PUBMED_match.csv
 fi
fi

if [ $# = 0 ] || [ $1 = "bmrb" ] ; then
 ( cd bmrb; ./update_bmrb.sh )
fi

if [ $# = 0 ] || [[ $1 =~ ^tax.* ]] ; then
 ( cd taxonomy; ./update_taxonomy.sh )
fi

if [ $# = 0 ] || [[ $1 =~ ^lacs.* ]] ; then
 ( cd lacs_ext; ./update_lacs.sh )
fi

if [ $# = 0 ] || [[ $1 =~ ^plus.* ]] || [[ $1 =~ ^pdb.* ]] ; then
 ( cd bmrb_plus_pdb_ext; ./update_bmrb_plus_pdb.sh )
fi

if [ $# = 0 ] || [[ $1 =~ ^pb.* ]] ; then
 ( cd pb_ext; ./update_pb.sh )
fi

if [ $# = 0 ] || [[ $1 =~ ^cs_complete.* ]] ; then
 ( cd cs_complete; ./update_cs_complete.sh )
fi

if [ $# = 0 ] || [[ $1 =~ ^le.* ]] || [[ $1 =~ ^lig.* ]] || [[ $1 =~ ^ex.* ]]; then
 ( cd ligand_expo; yes | ./update_ligand_expo.sh )
fi

