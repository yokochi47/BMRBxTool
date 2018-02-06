--
-- PostgreSQL database
--
drop table nodes;
drop table names;
drop table division;
drop table gencode;
drop table delnodes;
drop table merged;
drop table citations;

create table nodes (
        tax_id              int,
        parent_tax_id       int,
        rank                text,
        embl_code           text,
        division_id         int,
        inherited_div_flag  int2,
        genetic_code_id     int,
        inherited_GC_flag   int2,
        mitochondrial_genetic_code_id int,
        inherited_MGC_flag  int2,
        GenBank_hidden_flag int2,
        hidden_subtree_root_flag int2,
        comments            text,
        dummy               text
);

create table names (
        tax_id              int,
        name_txt            text,
        unique_name         text,
        name_class          text,
        dummy               text
);

create table division (
        division_id         int,
        division_cde        text,
        division_name       text,
        comments            text,
        dummy               text
);

create table gencode (
        genetic_code_id     int,
        abbreviation        text,
        name                text,
        cde                 text,
        starts              text,
        dummy               text
);

create table delnodes (
        tax_id              int,
        dummy               text
);

create table merged (
        old_tax_id          int,
        new_tax_id          int,
        dummy               text
);

create table citations (
        cit_id              int,
        cit_key             text,
        pubmed_id           text,
        medline_id          text,
        url                 text,
        text                text,
        taxid_list          text,
        dummy               text
);
