--
-- PostgreSQL database (PB extention)
--

DROP TABLE public."PB_list";
DROP TABLE public."PB_char";

--
-- Name: PB_list; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE "PB_list" (
    "Sf_ID" integer,
    "Sf_category" text,
    "Sf_framecode" text,
    "ID" text,
    "Query_ID" text,
    "Queried_date" text,
    "Input_file_name" text,
    "Output_file_name" text,
    "Electronic_address" text,
    "AA_seq_one_letter_code" text,
    "PB_seq_code" text,
    "PDB_ID" text,
    "PDBX_exptl_method" text,
    "PDBX_NMR_refine_method" text,
    "PDBX_refine_ls_R_factor_R_free" text,
    "PDBX_refine_ls_R_factor_R_work" text,
    "PDBX_refine_ls_d_res_high" text,
    "PDBX_refine_ls_d_res_low" text,
    "Entry_ID" text
);

--
-- Name: PB_char; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE "PB_char" (
    "Sf_ID" integer,
    "Entity_assembly_ID" text,
    "Assembly_ID" text,
    "Entity_ID" text,
    "Comp_index_ID" text,
    "Comp_ID" text,
    "PDB_model_num" text,
    "PDB_strand_ID" text,
    "PDB_ins_code" text,
    "PDB_residue_no" text,
    "PDB_residue_name" text,
    "PB_code" text,
    "Align" text,
    "Entry_ID" text,
    "PB_list_ID" text
);

