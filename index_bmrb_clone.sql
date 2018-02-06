
DROP INDEX IF EXISTS acs_index;
CREATE INDEX acs_index on atom_chem_shift ( entry_id, entity_id, atom_id, atom_type, comp_id, comp_index_id, seq_id );

DROP INDEX IF EXISTS sac_index;
CREATE INDEX sac_index on struct_anno_char ( entry_id, entity_id, secondary_structure_code, comp_index_id );

DROP INDEX IF EXISTS lacs_index;
CREATE INDEX lacs_index on lacs_plot ( entry_id );

DROP INDEX IF EXISTS lacs_char_index;
CREATE INDEX lacs_char_index on lacs_char ( entry_id, lacs_plot_id );

DROP INDEX IF EXISTS acsl_index;
CREATE INDEX acsl_index on assigned_chem_shift_list ( entry_id, sample_condition_list_id );

DROP INDEX IF EXISTS scv_index;
CREATE INDEX scv_index on sample_condition_variable ( entry_id, sample_condition_list_id, "type", val_units );

DROP INDEX IF EXISTS ent_index;
CREATE INDEX ent_index on entity ( entry_id, id, thiol_state );

DROP INDEX IF EXISTS bond_index;
CREATE INDEX bond_index on bond ( entry_id, entity_id_1, entity_id_2, seq_id_1, seq_id_2, comp_id_1, comp_id_2, atom_id_1, atom_id_2 );

DROP INDEX IF EXISTS eam_index;
CREATE INDEX eam_index on entity_assembly ( entry_id, entity_id );

DROP INDEX IF EXISTS asm_index;
CREATE INDEX asm_index on assembly ( entry_id );

DROP INDEX IF EXISTS eci_index;
CREATE INDEX eci_index on entity_comp_index ( entry_id, entity_id, id );

DROP INDEX IF EXISTS eci2_index;
CREATE INDEX eci2_index on entity_comp_index ( comp_id );

DROP INDEX IF EXISTS eps_index;
CREATE INDEX eps_index on entity_poly_seq ( entry_id, entity_id );

DROP INDEX IF EXISTS cit_index;
CREATE INDEX cit_index on citation ( entry_id, id );

DROP INDEX IF EXISTS pmid_index;
CREATE INDEX pmid_index on citation ( pubmed_id );

DROP INDEX IF EXISTS caut_index;
CREATE INDEX caut_index on citation_author ( entry_id, citation_id );

DROP INDEX IF EXISTS cscl_index;
CREATE INDEX cscl_index on chem_shift_completeness_list ( entry_id, assigned_chem_shift_list_id );

DROP INDEX IF EXISTS cscc_index;
CREATE INDEX cscc_index on chem_shift_completeness_char ( entry_id, assigned_chem_shift_list_id, entity_id );

DROP INDEX IF EXISTS entry_index;
CREATE INDEX entry_index on entry ( id, assigned_pdb_id );

DROP INDEX IF EXISTS ea_index;
CREATE INDEX ea_index on entry_author ( entry_id );

DROP INDEX IF EXISTS ds_index;
CREATE INDEX ds_index on data_set ( entry_id );

DROP INDEX IF EXISTS dt_index;
CREATE INDEX dt_index on data_set ( type );

DROP INDEX IF EXISTS ak_index;
CREATE INDEX ak_index on assembly_keyword ( entry_id );

DROP INDEX IF EXISTS cck_index;
CREATE INDEX cck_index on chem_comp_keyword ( entry_id );

DROP INDEX IF EXISTS ck_index;
CREATE INDEX ck_index on citation_keyword ( entry_id );

DROP INDEX IF EXISTS ek_index;
CREATE INDEX ek_index on entity_keyword ( entry_id );

DROP INDEX IF EXISTS strk_index;
CREATE INDEX strk_index on struct_keywords ( entry_id );

DROP INDEX IF EXISTS stdyk_index;
CREATE INDEX stdyk_index on study_keyword ( entry_id );

DROP INDEX IF EXISTS subsysk_index;
CREATE INDEX subsysk_index on subsystem_keyword ( entry_id );

DROP INDEX IF EXISTS ens_index;
CREATE INDEX ens_index on entity_natural_src ( entry_id );

DROP INDEX IF EXISTS pl_index;
CREATE INDEX pl_index on pb_list ( entry_id );

DROP INDEX IF EXISTS pc_index;
CREATE INDEX pc_index on pb_char ( entry_id );

DROP INDEX IF EXISTS t1_index;
CREATE INDEX t1_index on t1 ( entry_id, heteronucl_t1_list_id );

DROP INDEX IF EXISTS t2_index;
CREATE INDEX t2_index on t2 ( entry_id, heteronucl_t2_list_id );

DROP INDEX IF EXISTS t1rho_index;
CREATE INDEX t1rho_index on t1rho ( entry_id, heteronucl_t1rho_list_id );

DROP INDEX IF EXISTS noe_index;
CREATE INDEX noe_index on heteronucl_noe ( entry_id, heteronucl_noe_list_id );

DROP INDEX IF EXISTS cc_index;
CREATE INDEX cc_index on chem_comp ( entry_id );


