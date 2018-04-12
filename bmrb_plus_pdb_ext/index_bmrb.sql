
create index if not exists atom_site_index on "Atom_site" ( "Entry_ID" );

create index if not exists gen_dist_constraint_index on "Gen_dist_constraint" ( "Entry_ID" );

create index if not exists gen_dist_constraint_comment_org_index on "Gen_dist_constraint_comment_org" ( "Entry_ID" );

create index if not exists rdc_index on "RDC" ( "Entry_ID" );

create index if not exists rdc_constraint_commnet_org_index on "RDC_constraint_comment_org" ( "Entry_ID" );

create index if not exists torsion_angle_constraint_index on "Torsion_angle_constraint" ( "Entry_ID" );

create index if not exists ta_constraint_comment_org_index on "TA_constraint_comment_org" ( "Entry_ID" );

