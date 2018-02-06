--
-- PostgreSQL database (Chem_shift_completeness)
--

DROP TABLE public."Chem_shift_completeness_list";
DROP TABLE public."Chem_shift_completeness_char";

--
-- Name: Chem_shift_completeness_list; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE "Chem_shift_completeness_list" (
    "Sf_ID" integer,
    "Sf_category" text,
    "Sf_framecode" text,
    "Queried_date" text,
    "Output_file_name" text,
    "Electronic_address" text,
    "Assigned_residue_coverage" text,
    "Chem_shift_fraction" text,
    "Chem_shift_1H_fraction" text,
    "Chem_shift_13C_fraction" text,
    "Chem_shift_15N_fraction" text,
    "Chem_shift_31P_fraction" text,
    "Bb_chem_shift_fraction" text,
    "Bb_chem_shift_1H_fraction" text,
    "Bb_chem_shift_13C_fraction" text,
    "Bb_chem_shift_15N_fraction" text,
    "Bb_chem_shift_31P_fraction" text,
    "Sc_chem_shift_fraction" text,
    "Sc_chem_shift_1H_fraction" text,
    "Sc_chem_shift_13C_fraction" text,
    "Sc_chem_shift_15N_fraction" text,
    "Arom_chem_shift_fraction" text,
    "Arom_chem_shift_1H_fraction" text,
    "Arom_chem_shift_13C_fraction" text,
    "Arom_chem_shift_15N_fraction" text,
    "Methyl_chem_shift_fraction" text,
    "Methyl_chem_shift_1H_fraction" text,
    "Methyl_chem_shift_13C_fraction" text,
    "Entity_polymer_type" text,
    "Entry_ID" text,
    "Assigned_chem_shift_list_ID" text
);

--
-- Name: Chem_shift_completeness_char; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE "Chem_shift_completeness_char" (
    "Sf_ID" integer,
    "Entity_assembly_ID" text,
    "Entity_ID" text,
    "Comp_index_ID" text,
    "Comp_ID" text,
    "Chem_shift_coverage" text,
    "Chem_shift_1H_coverage" text,
    "Chem_shift_13C_coverage" text,
    "Chem_shift_15N_coverage" text,
    "Chem_shift_31P_coverage" text,
    "Bb_chem_shift_coverage" text,
    "Bb_chem_shift_1H_coverage" text,
    "Bb_chem_shift_13C_coverage" text,
    "Bb_chem_shift_15N_coverage" text,
    "Bb_chem_shift_31P_coverage" text,
    "Sc_chem_shift_coverage" text,
    "Sc_chem_shift_1H_coverage" text,
    "Sc_chem_shift_13C_coverage" text,
    "Sc_chem_shift_15N_coverage" text,
    "Arom_chem_shift_coverage" text,
    "Arom_chem_shift_1H_coverage" text,
    "Arom_chem_shift_13C_coverage" text,
    "Arom_chem_shift_15N_coverage" text,
    "Methyl_chem_shift_coverage" text,
    "Methyl_chem_shift_1H_coverage" text,
    "Methyl_chem_shift_13C_coverage" text,
    "Entry_ID" text,
    "Assigned_chem_shift_list_ID" text
);

