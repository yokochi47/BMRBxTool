--
-- PostgreSQL database
--
--drop index if exists assigned_chem_shift_list_index;
create index assigned_chem_shift_list_index on "Assigned_chem_shift_list" ( "Entry_ID" );

--drop index if exists atom_site_index;
create index atom_site_index on "Atom_site" ( "Entry_ID" );

--drop index if exists atom_chem_shift_index;
create index atom_chem_shift_index on "Atom_chem_shift" ( "Entry_ID", "Entity_ID" );

--drop index if exists datum_index;
create index datum_index on "Datum" ( "Entry_ID" );

--drop index if exists entity_index;
create index entity_index on "Entity" ( "Entry_ID" );

--drop index if exists entity_assembly_index;
create index entity_assembly_index on "Entity_assembly" ( "Entry_ID", "Entity_ID" );

--drop index if exists entity_comp_index_index;
create index entity_comp_index_index on "Entity_comp_index" ( "Entry_ID", "Entity_ID", "ID" );

--drop index if exists entity_poly_seq_index;
create index entity_poly_seq_index on "Entity_poly_seq" ( "Entry_ID" );

--drop index if exists entity_db_link_index;
create index entity_db_link_index on "Entity_db_link" ( "Entry_ID" );

--drop index if exists entry_index;
create index entry_index on "Entry" ( "ID" );

