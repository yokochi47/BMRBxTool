PREFIX BMRBo: <http://bmrbpub.protein.osaka-u.ac.jp/schema/mmcif_nmr-star.owl#>
PREFIX PDBo: <http://rdf.wwpdb.org/schema/pdbx-v40.owl#>

SELECT DISTINCT ?bmrb_id ?pubmed_id ?pdb_id ?method
FROM <http://bmrbpub.protein.osaka-u.ac.jp/rdf/bmr>
FROM <http://rdf.wwpdb.org/pdb>
WHERE {

  ?bmrb_c BMRBo:citation.id "1" ;
          BMRBo:citation.pubmed_id ?pubmed_id ;
          BMRBo:citation.entry_id ?bmrb_id .

  FILTER NOT EXISTS {

    ?bmrb_c2 BMRBo:citation.id "1" ;
             BMRBo:citation.pubmed_id ?pubmed_id ;
             BMRBo:citation.entry_id ?bmrb_id2 .

    FILTER (?bmrb_id != ?bmrb_id2)

  }

#  FILTER NOT EXISTS {

#    ?bmrb_e BMRBo:entry.id ?bmrb_id ;
#            BMRBo:entry.assigned_pdb_id ?_pdb_id .

#  }

#  FILTER (!bound(?_pdb_id))

  ?pdb_c PDBo:citation.id "primary" ;
         PDBo:citation.pdbx_database_id_PubMed ?pubmed_id ;
         PDBo:of_datablock ?pdb_db .

  BIND (STRAFTER(STR(?pdb_db), "http://rdf.wwpdb.org/pdb/") AS ?pdb_id)

  ?pdb_e PDBo:exptl.entry_id ?pdb_id ;
         PDBo:exptl.method ?method .

  FILTER (CONTAINS(?method, "NMR"))

#  FILTER NOT EXISTS {

#    ?bmrb_e2 BMRBo:entry.assigned_pdb_id ?pdb_id .

#  }

  FILTER NOT EXISTS {

    ?pdb_c2 PDBo:citation.id "primary" ;
            PDBo:citation.pdbx_database_id_PubMed ?pubmed_id ;
            PDBo:of_datablock ?pdb_db2 .

    FILTER (?pdb_db != ?pdb_db2)

  }

} ORDER BY ?bmrb_id
