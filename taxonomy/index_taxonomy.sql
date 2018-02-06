--
-- PostgreSQL database
--
--drop index if exists names_name_index;
create index names_name_index on names ( name_txt );

--drop index if exists names_tax_index;
create index names_tax_index on names ( tax_id );

--drop index if exists nodes_index;
create unique index nodes_index on nodes ( tax_id );
