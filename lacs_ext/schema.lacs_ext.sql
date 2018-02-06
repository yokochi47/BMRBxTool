--
-- PostgreSQL database (LACS extention)
--

DROP TABLE public."LACS_plot";
DROP TABLE public."LACS_char";

--
-- Name: LACS_plot; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE "LACS_plot" (
    "Sf_category" text,
    "Sf_framecode" text,
    "Entry_ID" text,
    "Sf_ID" integer,
    "ID" text,
    "Queried_date" text,
    "Input_file_name" text,
    "Output_file_name" text,
    "Electronic_address" text,
    "Source_release_designation" text,
    "Source_release_date" text,
    "Details" text,
    "X_coord_name" text,
    "Y_coord_name" text,
    "Line_1_terminator_val_x_1" text,
    "Line_1_terminator_val_y_1" text,
    "Line_1_terminator_val_x_2" text,
    "Line_1_terminator_val_y_2" text,
    "Line_2_terminator_val_x_1" text,
    "Line_2_terminator_val_y_1" text,
    "Line_2_terminator_val_x_2" text,
    "Line_2_terminator_val_y_2" text,
    "Y_axis_chem_shift_offset" text
);

--
-- Name: LACS_char; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE "LACS_char" (
    "Assembly_ID" text,
    "Entity_assembly_ID" text,
    "Entity_ID" text,
    "Comp_index_ID" text,
    "Comp_ID" text,
    "X_coord_val" text,
    "Y_coord_val" text,
    "Designator" text,
    "Sf_ID" integer,
    "Entry_ID" text,
    "LACS_plot_ID" text
);

