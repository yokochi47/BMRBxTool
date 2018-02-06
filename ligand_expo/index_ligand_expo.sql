
DROP INDEX IF EXISTS pcci_index;
CREATE INDEX pcci_index on pdbx_chem_comp_identifier ( comp_id, program );

DROP INDEX IF EXISTS pccd_index;
CREATE INDEX pccd_index on pdbx_chem_comp_descriptor ( comp_id, type );

DROP INDEX IF EXISTS cc_index;
CREATE INDEX cc_index on chem_comp ( id );

