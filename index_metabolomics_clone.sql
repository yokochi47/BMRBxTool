
DROP INDEX IF EXISTS cit_index;
CREATE INDEX cit_index on citation ( entry_id, id );

DROP INDEX IF EXISTS caut_index;
CREATE INDEX caut_index on citation_author ( entry_id, citation_id );

DROP INDEX IF EXISTS cc_index;
CREATE INDEX cc_index on chem_comp ( entry_id );

DROP INDEX IF EXISTS ccpc_index;
CREATE INDEX ccpc_index on chem_comp ( pdb_code );

DROP INDEX IF EXISTS entry_index;
CREATE INDEX entry_index on entry ( id );

DROP INDEX IF EXISTS ccdl_index;
CREATE INDEX ccdl_index on chem_comp_db_link ( entry_id, database_code, accession_code_type );

DROP INDEX IF EXISTS ccsn_index;
CREATE INDEX ccsn_index on chem_comp_systematic_name ( entry_id, naming_system );

DROP INDEX IF EXISTS ccs_index;
CREATE INDEX ccs_index on chem_comp_smiles ( entry_id, type );

DROP INDEX IF EXISTS ea_index;
CREATE INDEX ea_index on entry_author ( entry_id );

