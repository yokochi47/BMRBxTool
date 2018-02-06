--
-- PostgreSQL database
--
--drop index if exists atom_site_index;
create index atom_site_index on "Atom_site" ( "Entry_ID" );

--drop index if exists gen_dist_constraint_index;
create index gen_dist_constraint_index on "Gen_dist_constraint" ( "Entry_ID" );

--drop index if exists gen_dist_constraint_commnet_org_index;
create index gen_dist_constraint_comment_org_index on "Gen_dist_constraint_comment_org" ( "Entry_ID" );

--drop index if exists rdc_index;
create index rdc_index on "RDC" ( "Entry_ID" );

--drop index if exists rdc_constraint_commnet_org_index;
create index rdc_constraint_commnet_org_index on "RDC_constraint_comment_org" ( "Entry_ID" );

--drop index if exists torsion_angle_constraint_index;
create index torsion_angle_constraint_index on "Torsion_angle_constraint" ( "Entry_ID" );

--drop index if exists ta_constraint_comment_org_index;
create index ta_constraint_comment_org_index on "TA_constraint_comment_org" ( "Entry_ID" );

