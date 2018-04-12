
drop index if exists ambiguous_atom_chem_shift_index;
create index ambiguous_atom_chem_shift_index on "Ambiguous_atom_chem_shift" ( "Entry_ID" );

drop index if exists angle_index;
create index angle_index on "Angle" ( "Entry_ID" );

drop index if exists angular_order_param_index;
create index angular_order_param_index on "Angular_order_param" ( "Entry_ID" );

drop index if exists angular_order_parameter_list_index;
create index angular_order_parameter_list_index on "Angular_order_parameter_list" ( "Entry_ID" );

drop index if exists assembly_index;
create index assembly_index on "Assembly" ( "Entry_ID" );

drop index if exists assembly_annotation_list_index;
create index assembly_annotation_list_index on "Assembly_annotation_list" ( "Entry_ID" );

drop index if exists assembly_bio_function_index;
create index assembly_bio_function_index on "Assembly_bio_function" ( "Entry_ID" );

drop index if exists assembly_citation_index;
create index assembly_citation_index on "Assembly_citation" ( "Entry_ID" );

drop index if exists assembly_common_name_index;
create index assembly_common_name_index on "Assembly_common_name" ( "Entry_ID" );

drop index if exists assembly_db_link_index;
create index assembly_db_link_index on "Assembly_db_link" ( "Entry_ID" );

drop index if exists assembly_interaction_index;
create index assembly_interaction_index on "Assembly_interaction" ( "Entry_ID" );

drop index if exists assembly_keyword_index;
create index assembly_keyword_index on "Assembly_keyword" ( "Entry_ID" );

drop index if exists assembly_segment_index;
create index assembly_segment_index on "Assembly_segment" ( "Entry_ID" );

drop index if exists assembly_segment_description_index;
create index assembly_segment_description_index on "Assembly_segment_description" ( "Entry_ID" );

drop index if exists assembly_subsystem_index;
create index assembly_subsystem_index on "Assembly_subsystem" ( "Entry_ID" );

drop index if exists assembly_systematic_name_index;
create index assembly_systematic_name_index on "Assembly_systematic_name" ( "Entry_ID" );

drop index if exists assembly_type_index;
create index assembly_type_index on "Assembly_type" ( "Entry_ID" );

drop index if exists assigned_chem_shift_list_index;
create index assigned_chem_shift_list_index on "Assigned_chem_shift_list" ( "Entry_ID" );

drop index if exists assigned_peak_chem_shift_index;
create index assigned_peak_chem_shift_index on "Assigned_peak_chem_shift" ( "Entry_ID" );

drop index if exists assigned_spectral_transition_index;
create index assigned_spectral_transition_index on "Assigned_spectral_transition" ( "Entry_ID" );

drop index if exists atom_index;
create index atom_index on "Atom" ( "Entry_ID" );

drop index if exists atom_chem_shift_index;
create index atom_chem_shift_index on "Atom_chem_shift" ( "Entry_ID" );

drop index if exists atom_nomenclature_index;
create index atom_nomenclature_index on "Atom_nomenclature" ( "Entry_ID" );

drop index if exists atom_site_index;
create index atom_site_index on "Atom_site" ( "Entry_ID" );

drop index if exists atom_sites_footnote_index;
create index atom_sites_footnote_index on "Atom_sites_footnote" ( "Entry_ID" );

drop index if exists atom_type_index;
create index atom_type_index on "Atom_type" ( "Entry_ID" );

drop index if exists author_annotation_index;
create index author_annotation_index on "Author_annotation" ( "Entry_ID" );

drop index if exists auto_relaxation_index;
create index auto_relaxation_index on "Auto_relaxation" ( "Entry_ID" );

drop index if exists auto_relaxation_experiment_index;
create index auto_relaxation_experiment_index on "Auto_relaxation_experiment" ( "Entry_ID" );

drop index if exists auto_relaxation_list_index;
create index auto_relaxation_list_index on "Auto_relaxation_list" ( "Entry_ID" );

drop index if exists auto_relaxation_software_index;
create index auto_relaxation_software_index on "Auto_relaxation_software" ( "Entry_ID" );

drop index if exists auxiliary_files_index;
create index auxiliary_files_index on "Auxiliary_files" ( "Entry_ID" );

drop index if exists binding_experiment_index;
create index binding_experiment_index on "Binding_experiment" ( "Entry_ID" );

drop index if exists binding_param_index;
create index binding_param_index on "Binding_param" ( "Entry_ID" );

drop index if exists binding_param_list_index;
create index binding_param_list_index on "Binding_param_list" ( "Entry_ID" );

drop index if exists binding_partners_index;
create index binding_partners_index on "Binding_partners" ( "Entry_ID" );

drop index if exists binding_result_index;
create index binding_result_index on "Binding_result" ( "Entry_ID" );

drop index if exists binding_software_index;
create index binding_software_index on "Binding_software" ( "Entry_ID" );

drop index if exists binding_value_list_index;
create index binding_value_list_index on "Binding_value_list" ( "Entry_ID" );

drop index if exists bond_index;
create index bond_index on "Bond" ( "Entry_ID" );

drop index if exists bond_annotation_index;
create index bond_annotation_index on "Bond_annotation" ( "Entry_ID" );

drop index if exists bond_annotation_list_index;
create index bond_annotation_list_index on "Bond_annotation_list" ( "Entry_ID" );

drop index if exists bond_observed_conformer_index;
create index bond_observed_conformer_index on "Bond_observed_conformer" ( "Entry_ID" );

drop index if exists ca_cb_constraint_index;
create index ca_cb_constraint_index on "CA_CB_constraint" ( "Entry_ID" );

drop index if exists ca_cb_constraint_expt_index;
create index ca_cb_constraint_expt_index on "CA_CB_constraint_expt" ( "Entry_ID" );

drop index if exists ca_cb_constraint_list_index;
create index ca_cb_constraint_list_index on "CA_CB_constraint_list" ( "Entry_ID" );

drop index if exists ca_cb_constraint_software_index;
create index ca_cb_constraint_software_index on "CA_CB_constraint_software" ( "Entry_ID" );

drop index if exists cs_anisotropy_index;
create index cs_anisotropy_index on "CS_anisotropy" ( "Entry_ID" );

drop index if exists cs_anisotropy_experiment_index;
create index cs_anisotropy_experiment_index on "CS_anisotropy_experiment" ( "Entry_ID" );

drop index if exists cs_anisotropy_software_index;
create index cs_anisotropy_software_index on "CS_anisotropy_software" ( "Entry_ID" );

drop index if exists characteristic_index;
create index characteristic_index on "Characteristic" ( "Entry_ID" );

drop index if exists chem_comp_index;
create index chem_comp_index on "Chem_comp" ( "Entry_ID" );

drop index if exists chem_comp_smiles_index;
create index chem_comp_smiles_index on "Chem_comp_SMILES" ( "Entry_ID" );

drop index if exists chem_comp_angle_index;
create index chem_comp_angle_index on "Chem_comp_angle" ( "Entry_ID" );

drop index if exists chem_comp_assembly_index;
create index chem_comp_assembly_index on "Chem_comp_assembly" ( "Entry_ID" );

drop index if exists chem_comp_atom_index;
create index chem_comp_atom_index on "Chem_comp_atom" ( "Entry_ID" );

drop index if exists chem_comp_bio_function_index;
create index chem_comp_bio_function_index on "Chem_comp_bio_function" ( "Entry_ID" );

drop index if exists chem_comp_bond_index;
create index chem_comp_bond_index on "Chem_comp_bond" ( "Entry_ID" );

drop index if exists chem_comp_citation_index;
create index chem_comp_citation_index on "Chem_comp_citation" ( "Entry_ID" );

drop index if exists chem_comp_common_name_index;
create index chem_comp_common_name_index on "Chem_comp_common_name" ( "Entry_ID" );

drop index if exists chem_comp_db_link_index;
create index chem_comp_db_link_index on "Chem_comp_db_link" ( "Entry_ID" );

drop index if exists chem_comp_descriptor_index;
create index chem_comp_descriptor_index on "Chem_comp_descriptor" ( "Entry_ID" );

drop index if exists chem_comp_identifier_index;
create index chem_comp_identifier_index on "Chem_comp_identifier" ( "Entry_ID" );

drop index if exists chem_comp_keyword_index;
create index chem_comp_keyword_index on "Chem_comp_keyword" ( "Entry_ID" );

drop index if exists chem_comp_systematic_name_index;
create index chem_comp_systematic_name_index on "Chem_comp_systematic_name" ( "Entry_ID" );

drop index if exists chem_comp_tor_index;
create index chem_comp_tor_index on "Chem_comp_tor" ( "Entry_ID" );

drop index if exists chem_shift_anisotropy_index;
create index chem_shift_anisotropy_index on "Chem_shift_anisotropy" ( "Entry_ID" );

drop index if exists chem_shift_experiment_index;
create index chem_shift_experiment_index on "Chem_shift_experiment" ( "Entry_ID" );

drop index if exists chem_shift_isotope_effect_list_index;
create index chem_shift_isotope_effect_list_index on "Chem_shift_isotope_effect_list" ( "Entry_ID" );

drop index if exists chem_shift_perturbation_index;
create index chem_shift_perturbation_index on "Chem_shift_perturbation" ( "Entry_ID" );

drop index if exists chem_shift_perturbation_experiment_index;
create index chem_shift_perturbation_experiment_index on "Chem_shift_perturbation_experiment" ( "Entry_ID" );

drop index if exists chem_shift_perturbation_list_index;
create index chem_shift_perturbation_list_index on "Chem_shift_perturbation_list" ( "Entry_ID" );

drop index if exists chem_shift_perturbation_software_index;
create index chem_shift_perturbation_software_index on "Chem_shift_perturbation_software" ( "Entry_ID" );

drop index if exists chem_shift_ref_index;
create index chem_shift_ref_index on "Chem_shift_ref" ( "Entry_ID" );

drop index if exists chem_shift_reference_index;
create index chem_shift_reference_index on "Chem_shift_reference" ( "Entry_ID" );

drop index if exists chem_shift_software_index;
create index chem_shift_software_index on "Chem_shift_software" ( "Entry_ID" );

drop index if exists chem_shifts_calc_software_index;
create index chem_shifts_calc_software_index on "Chem_shifts_calc_software" ( "Entry_ID" );

drop index if exists chem_shifts_calc_type_index;
create index chem_shifts_calc_type_index on "Chem_shifts_calc_type" ( "Entry_ID" );

drop index if exists chem_struct_descriptor_index;
create index chem_struct_descriptor_index on "Chem_struct_descriptor" ( "Entry_ID" );

drop index if exists chemical_rate_index;
create index chemical_rate_index on "Chemical_rate" ( "Entry_ID" );

drop index if exists chemical_rate_experiment_index;
create index chemical_rate_experiment_index on "Chemical_rate_experiment" ( "Entry_ID" );

drop index if exists chemical_rate_list_index;
create index chemical_rate_list_index on "Chemical_rate_list" ( "Entry_ID" );

drop index if exists chemical_rate_software_index;
create index chemical_rate_software_index on "Chemical_rate_software" ( "Entry_ID" );

drop index if exists chromatographic_column_index;
create index chromatographic_column_index on "Chromatographic_column" ( "Entry_ID" );

drop index if exists chromatographic_system_index;
create index chromatographic_system_index on "Chromatographic_system" ( "Entry_ID" );

drop index if exists citation_index;
create index citation_index on "Citation" ( "Entry_ID" );

drop index if exists citation_author_index;
create index citation_author_index on "Citation_author" ( "Entry_ID" );

drop index if exists citation_editor_index;
create index citation_editor_index on "Citation_editor" ( "Entry_ID" );

drop index if exists citation_keyword_index;
create index citation_keyword_index on "Citation_keyword" ( "Entry_ID" );

drop index if exists computer_index;
create index computer_index on "Computer" ( "Entry_ID" );

drop index if exists computer_citation_index;
create index computer_citation_index on "Computer_citation" ( "Entry_ID" );

drop index if exists conf_family_coord_set_constr_list_index;
create index conf_family_coord_set_constr_list_index on "Conf_family_coord_set_constr_list" ( "Entry_ID" );

drop index if exists conf_stats_software_index;
create index conf_stats_software_index on "Conf_stats_software" ( "Entry_ID" );

drop index if exists conformer_family_coord_set_index;
create index conformer_family_coord_set_index on "Conformer_family_coord_set" ( "Entry_ID" );

drop index if exists conformer_family_coord_set_expt_index;
create index conformer_family_coord_set_expt_index on "Conformer_family_coord_set_expt" ( "Entry_ID" );

drop index if exists conformer_family_refinement_index;
create index conformer_family_refinement_index on "Conformer_family_refinement" ( "Entry_ID" );

drop index if exists conformer_family_software_index;
create index conformer_family_software_index on "Conformer_family_software" ( "Entry_ID" );

drop index if exists conformer_stat_list_index;
create index conformer_stat_list_index on "Conformer_stat_list" ( "Entry_ID" );

drop index if exists conformer_stat_list_ens_index;
create index conformer_stat_list_ens_index on "Conformer_stat_list_ens" ( "Entry_ID" );

drop index if exists conformer_stat_list_rep_index;
create index conformer_stat_list_rep_index on "Conformer_stat_list_rep" ( "Entry_ID" );

drop index if exists constraint_file_index;
create index constraint_file_index on "Constraint_file" ( "Entry_ID" );

drop index if exists constraint_stat_list_index;
create index constraint_stat_list_index on "Constraint_stat_list" ( "Entry_ID" );

drop index if exists constraint_stat_list_ens_index;
create index constraint_stat_list_ens_index on "Constraint_stat_list_ens" ( "Entry_ID" );

drop index if exists constraint_stat_list_rep_index;
create index constraint_stat_list_rep_index on "Constraint_stat_list_rep" ( "Entry_ID" );

drop index if exists constraint_stats_constr_list_index;
create index constraint_stats_constr_list_index on "Constraint_stats_constr_list" ( "Entry_ID" );

drop index if exists coupling_constant_index;
create index coupling_constant_index on "Coupling_constant" ( "Entry_ID" );

drop index if exists coupling_constant_experiment_index;
create index coupling_constant_experiment_index on "Coupling_constant_experiment" ( "Entry_ID" );

drop index if exists coupling_constant_list_index;
create index coupling_constant_list_index on "Coupling_constant_list" ( "Entry_ID" );

drop index if exists coupling_constant_software_index;
create index coupling_constant_software_index on "Coupling_constant_software" ( "Entry_ID" );

drop index if exists cross_correlation_dd_index;
create index cross_correlation_dd_index on "Cross_correlation_DD" ( "Entry_ID" );

drop index if exists cross_correlation_dd_experiment_index;
create index cross_correlation_dd_experiment_index on "Cross_correlation_DD_experiment" ( "Entry_ID" );

drop index if exists cross_correlation_dd_list_index;
create index cross_correlation_dd_list_index on "Cross_correlation_DD_list" ( "Entry_ID" );

drop index if exists cross_correlation_dd_software_index;
create index cross_correlation_dd_software_index on "Cross_correlation_DD_software" ( "Entry_ID" );

drop index if exists cross_correlation_d_csa_index;
create index cross_correlation_d_csa_index on "Cross_correlation_D_CSA" ( "Entry_ID" );

drop index if exists cross_correlation_d_csa_experiment_index;
create index cross_correlation_d_csa_experiment_index on "Cross_correlation_D_CSA_experiment" ( "Entry_ID" );

drop index if exists cross_correlation_d_csa_list_index;
create index cross_correlation_d_csa_list_index on "Cross_correlation_D_CSA_list" ( "Entry_ID" );

drop index if exists cross_correlation_d_csa_software_index;
create index cross_correlation_d_csa_software_index on "Cross_correlation_D_CSA_software" ( "Entry_ID" );

drop index if exists d_h_fract_factor_experiment_index;
create index d_h_fract_factor_experiment_index on "D_H_fract_factor_experiment" ( "Entry_ID" );

drop index if exists d_h_fract_factor_software_index;
create index d_h_fract_factor_software_index on "D_H_fract_factor_software" ( "Entry_ID" );

drop index if exists d_h_fractionation_factor_index;
create index d_h_fractionation_factor_index on "D_H_fractionation_factor" ( "Entry_ID" );

drop index if exists d_h_fractionation_factor_list_index;
create index d_h_fractionation_factor_list_index on "D_H_fractionation_factor_list" ( "Entry_ID" );

drop index if exists data_set_index;
create index data_set_index on "Data_set" ( "Entry_ID" );

drop index if exists datum_index;
create index datum_index on "Datum" ( "Entry_ID" );

drop index if exists decoupling_pulse_sequence_index;
create index decoupling_pulse_sequence_index on "Decoupling_pulse_sequence" ( "Entry_ID" );

drop index if exists deduced_h_bond_index;
create index deduced_h_bond_index on "Deduced_H_bond" ( "Entry_ID" );

drop index if exists deduced_h_bond_experiment_index;
create index deduced_h_bond_experiment_index on "Deduced_H_bond_experiment" ( "Entry_ID" );

drop index if exists deduced_h_bond_list_index;
create index deduced_h_bond_list_index on "Deduced_H_bond_list" ( "Entry_ID" );

drop index if exists deduced_h_bond_software_index;
create index deduced_h_bond_software_index on "Deduced_H_bond_software" ( "Entry_ID" );

drop index if exists deduced_secd_struct_experiment_index;
create index deduced_secd_struct_experiment_index on "Deduced_secd_struct_experiment" ( "Entry_ID" );

drop index if exists deduced_secd_struct_exptl_index;
create index deduced_secd_struct_exptl_index on "Deduced_secd_struct_exptl" ( "Entry_ID" );

drop index if exists deduced_secd_struct_feature_index;
create index deduced_secd_struct_feature_index on "Deduced_secd_struct_feature" ( "Entry_ID" );

drop index if exists deduced_secd_struct_list_index;
create index deduced_secd_struct_list_index on "Deduced_secd_struct_list" ( "Entry_ID" );

drop index if exists deduced_secd_struct_software_index;
create index deduced_secd_struct_software_index on "Deduced_secd_struct_software" ( "Entry_ID" );

drop index if exists dipolar_coupling_index;
create index dipolar_coupling_index on "Dipolar_coupling" ( "Entry_ID" );

drop index if exists dipolar_coupling_experiment_index;
create index dipolar_coupling_experiment_index on "Dipolar_coupling_experiment" ( "Entry_ID" );

drop index if exists dipolar_coupling_list_index;
create index dipolar_coupling_list_index on "Dipolar_coupling_list" ( "Entry_ID" );

drop index if exists dipolar_coupling_software_index;
create index dipolar_coupling_software_index on "Dipolar_coupling_software" ( "Entry_ID" );

drop index if exists dipole_dipole_relax_index;
create index dipole_dipole_relax_index on "Dipole_dipole_relax" ( "Entry_ID" );

drop index if exists dipole_dipole_relax_experiment_index;
create index dipole_dipole_relax_experiment_index on "Dipole_dipole_relax_experiment" ( "Entry_ID" );

drop index if exists dipole_dipole_relax_list_index;
create index dipole_dipole_relax_list_index on "Dipole_dipole_relax_list" ( "Entry_ID" );

drop index if exists dipole_dipole_relax_software_index;
create index dipole_dipole_relax_software_index on "Dipole_dipole_relax_software" ( "Entry_ID" );

drop index if exists dist_constr_software_setting_index;
create index dist_constr_software_setting_index on "Dist_constr_software_setting" ( "Entry_ID" );

drop index if exists dist_constraint_index;
create index dist_constraint_index on "Dist_constraint" ( "Entry_ID" );

drop index if exists dist_constraint_comment_org_index;
create index dist_constraint_comment_org_index on "Dist_constraint_comment_org" ( "Entry_ID" );

drop index if exists dist_constraint_conv_err_index;
create index dist_constraint_conv_err_index on "Dist_constraint_conv_err" ( "Entry_ID" );

drop index if exists dist_constraint_parse_err_index;
create index dist_constraint_parse_err_index on "Dist_constraint_parse_err" ( "Entry_ID" );

drop index if exists dist_constraint_parse_file_index;
create index dist_constraint_parse_file_index on "Dist_constraint_parse_file" ( "Entry_ID" );

drop index if exists dist_constraint_tree_index;
create index dist_constraint_tree_index on "Dist_constraint_tree" ( "Entry_ID" );

drop index if exists dist_constraint_value_index;
create index dist_constraint_value_index on "Dist_constraint_value" ( "Entry_ID" );

drop index if exists distance_constraint_expt_index;
create index distance_constraint_expt_index on "Distance_constraint_expt" ( "Entry_ID" );

drop index if exists distance_constraint_list_index;
create index distance_constraint_list_index on "Distance_constraint_list" ( "Entry_ID" );

drop index if exists distance_constraint_software_index;
create index distance_constraint_software_index on "Distance_constraint_software" ( "Entry_ID" );

drop index if exists emr_expt_index;
create index emr_expt_index on "EMR_expt" ( "Entry_ID" );

drop index if exists emr_instrument_index;
create index emr_instrument_index on "EMR_instrument" ( "Entry_ID" );

drop index if exists energetic_penalty_function_index;
create index energetic_penalty_function_index on "Energetic_penalty_function" ( "Entry_ID" );

drop index if exists entity_index;
create index entity_index on "Entity" ( "Entry_ID" );

drop index if exists entity_assembly_index;
create index entity_assembly_index on "Entity_assembly" ( "Entry_ID" );

drop index if exists entity_atom_list_index;
create index entity_atom_list_index on "Entity_atom_list" ( "Entry_ID" );

drop index if exists entity_biological_function_index;
create index entity_biological_function_index on "Entity_biological_function" ( "Entry_ID" );

drop index if exists entity_bond_index;
create index entity_bond_index on "Entity_bond" ( "Entry_ID" );

drop index if exists entity_chem_comp_deleted_atom_index;
create index entity_chem_comp_deleted_atom_index on "Entity_chem_comp_deleted_atom" ( "Entry_ID" );

drop index if exists entity_chimera_segment_index;
create index entity_chimera_segment_index on "Entity_chimera_segment" ( "Entry_ID" );

drop index if exists entity_citation_index;
create index entity_citation_index on "Entity_citation" ( "Entry_ID" );

drop index if exists entity_common_name_index;
create index entity_common_name_index on "Entity_common_name" ( "Entry_ID" );

drop index if exists entity_comp_index_index;
create index entity_comp_index_index on "Entity_comp_index" ( "Entry_ID" );

drop index if exists entity_comp_index_alt_index;
create index entity_comp_index_alt_index on "Entity_comp_index_alt" ( "Entry_ID" );

drop index if exists entity_db_link_index;
create index entity_db_link_index on "Entity_db_link" ( "Entry_ID" );

drop index if exists entity_deleted_atom_index;
create index entity_deleted_atom_index on "Entity_deleted_atom" ( "Entry_ID" );

drop index if exists entity_experimental_src_index;
create index entity_experimental_src_index on "Entity_experimental_src" ( "Entry_ID" );

drop index if exists entity_experimental_src_list_index;
create index entity_experimental_src_list_index on "Entity_experimental_src_list" ( "Entry_ID" );

drop index if exists entity_keyword_index;
create index entity_keyword_index on "Entity_keyword" ( "Entry_ID" );

drop index if exists entity_natural_src_index;
create index entity_natural_src_index on "Entity_natural_src" ( "Entry_ID" );

drop index if exists entity_natural_src_list_index;
create index entity_natural_src_list_index on "Entity_natural_src_list" ( "Entry_ID" );

drop index if exists entity_poly_seq_index;
create index entity_poly_seq_index on "Entity_poly_seq" ( "Entry_ID" );

drop index if exists entity_purity_index;
create index entity_purity_index on "Entity_purity" ( "Entry_ID" );

drop index if exists entity_purity_citation_index;
create index entity_purity_citation_index on "Entity_purity_citation" ( "Entry_ID" );

drop index if exists entity_purity_list_index;
create index entity_purity_list_index on "Entity_purity_list" ( "Entry_ID" );

drop index if exists entity_systematic_name_index;
create index entity_systematic_name_index on "Entity_systematic_name" ( "Entry_ID" );

drop index if exists entry_index;
create index entry_index on "Entry" ( "ID" );

drop index if exists entry_author_index;
create index entry_author_index on "Entry_author" ( "Entry_ID" );

drop index if exists entry_experimental_methods_index;
create index entry_experimental_methods_index on "Entry_experimental_methods" ( "Entry_ID" );

drop index if exists entry_src_index;
create index entry_src_index on "Entry_src" ( "Entry_ID" );

drop index if exists experiment_index;
create index experiment_index on "Experiment" ( "Entry_ID" );

drop index if exists experiment_file_index;
create index experiment_file_index on "Experiment_file" ( "Entry_ID" );

drop index if exists experiment_list_index;
create index experiment_list_index on "Experiment_list" ( "Entry_ID" );

drop index if exists fret_expt_index;
create index fret_expt_index on "FRET_expt" ( "Entry_ID" );

drop index if exists floating_chirality_index;
create index floating_chirality_index on "Floating_chirality" ( "Entry_ID" );

drop index if exists floating_chirality_assign_index;
create index floating_chirality_assign_index on "Floating_chirality_assign" ( "Entry_ID" );

drop index if exists floating_chirality_software_index;
create index floating_chirality_software_index on "Floating_chirality_software" ( "Entry_ID" );

drop index if exists fluorescence_instrument_index;
create index fluorescence_instrument_index on "Fluorescence_instrument" ( "Entry_ID" );

drop index if exists force_constant_index;
create index force_constant_index on "Force_constant" ( "Entry_ID" );

drop index if exists force_constant_list_index;
create index force_constant_list_index on "Force_constant_list" ( "Entry_ID" );

drop index if exists force_constant_software_index;
create index force_constant_software_index on "Force_constant_software" ( "Entry_ID" );

drop index if exists gen_dist_constraint_index;
create index gen_dist_constraint_index on "Gen_dist_constraint" ( "Entry_ID" );

drop index if exists gen_dist_constraint_comment_org_index;
create index gen_dist_constraint_comment_org_index on "Gen_dist_constraint_comment_org" ( "Entry_ID" );

drop index if exists gen_dist_constraint_conv_err_index;
create index gen_dist_constraint_conv_err_index on "Gen_dist_constraint_conv_err" ( "Entry_ID" );

drop index if exists gen_dist_constraint_expt_index;
create index gen_dist_constraint_expt_index on "Gen_dist_constraint_expt" ( "Entry_ID" );

drop index if exists gen_dist_constraint_list_index;
create index gen_dist_constraint_list_index on "Gen_dist_constraint_list" ( "Entry_ID" );

drop index if exists gen_dist_constraint_parse_err_index;
create index gen_dist_constraint_parse_err_index on "Gen_dist_constraint_parse_err" ( "Entry_ID" );

drop index if exists gen_dist_constraint_parse_file_index;
create index gen_dist_constraint_parse_file_index on "Gen_dist_constraint_parse_file" ( "Entry_ID" );

drop index if exists gen_dist_constraint_software_index;
create index gen_dist_constraint_software_index on "Gen_dist_constraint_software" ( "Entry_ID" );

drop index if exists gen_dist_constraint_software_param_index;
create index gen_dist_constraint_software_param_index on "Gen_dist_constraint_software_param" ( "Entry_ID" );

drop index if exists h_chem_shift_constraint_index;
create index h_chem_shift_constraint_index on "H_chem_shift_constraint" ( "Entry_ID" );

drop index if exists h_chem_shift_constraint_expt_index;
create index h_chem_shift_constraint_expt_index on "H_chem_shift_constraint_expt" ( "Entry_ID" );

drop index if exists h_chem_shift_constraint_list_index;
create index h_chem_shift_constraint_list_index on "H_chem_shift_constraint_list" ( "Entry_ID" );

drop index if exists h_chem_shift_constraint_software_index;
create index h_chem_shift_constraint_software_index on "H_chem_shift_constraint_software" ( "Entry_ID" );

drop index if exists h_exch_protection_fact_experiment_index;
create index h_exch_protection_fact_experiment_index on "H_exch_protection_fact_experiment" ( "Entry_ID" );

drop index if exists h_exch_protection_fact_software_index;
create index h_exch_protection_fact_software_index on "H_exch_protection_fact_software" ( "Entry_ID" );

drop index if exists h_exch_protection_factor_index;
create index h_exch_protection_factor_index on "H_exch_protection_factor" ( "Entry_ID" );

drop index if exists h_exch_protection_factor_list_index;
create index h_exch_protection_factor_list_index on "H_exch_protection_factor_list" ( "Entry_ID" );

drop index if exists h_exch_rate_index;
create index h_exch_rate_index on "H_exch_rate" ( "Entry_ID" );

drop index if exists h_exch_rate_experiment_index;
create index h_exch_rate_experiment_index on "H_exch_rate_experiment" ( "Entry_ID" );

drop index if exists h_exch_rate_list_index;
create index h_exch_rate_list_index on "H_exch_rate_list" ( "Entry_ID" );

drop index if exists h_exch_rate_software_index;
create index h_exch_rate_software_index on "H_exch_rate_software" ( "Entry_ID" );

drop index if exists heteronucl_noe_index;
create index heteronucl_noe_index on "Heteronucl_NOE" ( "Entry_ID" );

drop index if exists heteronucl_noe_experiment_index;
create index heteronucl_noe_experiment_index on "Heteronucl_NOE_experiment" ( "Entry_ID" );

drop index if exists heteronucl_noe_list_index;
create index heteronucl_noe_list_index on "Heteronucl_NOE_list" ( "Entry_ID" );

drop index if exists heteronucl_noe_software_index;
create index heteronucl_noe_software_index on "Heteronucl_NOE_software" ( "Entry_ID" );

drop index if exists heteronucl_t1_experiment_index;
create index heteronucl_t1_experiment_index on "Heteronucl_T1_experiment" ( "Entry_ID" );

drop index if exists heteronucl_t1_list_index;
create index heteronucl_t1_list_index on "Heteronucl_T1_list" ( "Entry_ID" );

drop index if exists heteronucl_t1_software_index;
create index heteronucl_t1_software_index on "Heteronucl_T1_software" ( "Entry_ID" );

drop index if exists heteronucl_t1rho_experiment_index;
create index heteronucl_t1rho_experiment_index on "Heteronucl_T1rho_experiment" ( "Entry_ID" );

drop index if exists heteronucl_t1rho_list_index;
create index heteronucl_t1rho_list_index on "Heteronucl_T1rho_list" ( "Entry_ID" );

drop index if exists heteronucl_t1rho_software_index;
create index heteronucl_t1rho_software_index on "Heteronucl_T1rho_software" ( "Entry_ID" );

drop index if exists heteronucl_t2_experiment_index;
create index heteronucl_t2_experiment_index on "Heteronucl_T2_experiment" ( "Entry_ID" );

drop index if exists heteronucl_t2_list_index;
create index heteronucl_t2_list_index on "Heteronucl_T2_list" ( "Entry_ID" );

drop index if exists heteronucl_t2_software_index;
create index heteronucl_t2_software_index on "Heteronucl_T2_software" ( "Entry_ID" );

drop index if exists history_index;
create index history_index on "History" ( "Entry_ID" );

drop index if exists homonucl_noe_index;
create index homonucl_noe_index on "Homonucl_NOE" ( "Entry_ID" );

drop index if exists homonucl_noe_experiment_index;
create index homonucl_noe_experiment_index on "Homonucl_NOE_experiment" ( "Entry_ID" );

drop index if exists homonucl_noe_list_index;
create index homonucl_noe_list_index on "Homonucl_NOE_list" ( "Entry_ID" );

drop index if exists homonucl_noe_software_index;
create index homonucl_noe_software_index on "Homonucl_NOE_software" ( "Entry_ID" );

drop index if exists interatomic_dist_index;
create index interatomic_dist_index on "Interatomic_dist" ( "Entry_ID" );

drop index if exists interatomic_distance_list_index;
create index interatomic_distance_list_index on "Interatomic_distance_list" ( "Entry_ID" );

drop index if exists isotope_effect_index;
create index isotope_effect_index on "Isotope_effect" ( "Entry_ID" );

drop index if exists isotope_effect_experiment_index;
create index isotope_effect_experiment_index on "Isotope_effect_experiment" ( "Entry_ID" );

drop index if exists isotope_effect_software_index;
create index isotope_effect_software_index on "Isotope_effect_software" ( "Entry_ID" );

drop index if exists isotope_label_pattern_index;
create index isotope_label_pattern_index on "Isotope_label_pattern" ( "Entry_ID" );

drop index if exists j_three_bond_constraint_index;
create index j_three_bond_constraint_index on "J_three_bond_constraint" ( "Entry_ID" );

drop index if exists j_three_bond_constraint_expt_index;
create index j_three_bond_constraint_expt_index on "J_three_bond_constraint_expt" ( "Entry_ID" );

drop index if exists j_three_bond_constraint_list_index;
create index j_three_bond_constraint_list_index on "J_three_bond_constraint_list" ( "Entry_ID" );

drop index if exists j_three_bond_constraint_software_index;
create index j_three_bond_constraint_software_index on "J_three_bond_constraint_software" ( "Entry_ID" );

drop index if exists karplus_equation_index;
create index karplus_equation_index on "Karplus_equation" ( "Entry_ID" );

drop index if exists local_structure_quality_index;
create index local_structure_quality_index on "Local_structure_quality" ( "Entry_ID" );

drop index if exists ms_chrom_ion_annotation_index;
create index ms_chrom_ion_annotation_index on "MS_chrom_ion_annotation" ( "Entry_ID" );

drop index if exists ms_chromatogram_experiment_index;
create index ms_chromatogram_experiment_index on "MS_chromatogram_experiment" ( "Entry_ID" );

drop index if exists ms_chromatogram_ion_index;
create index ms_chromatogram_ion_index on "MS_chromatogram_ion" ( "Entry_ID" );

drop index if exists ms_chromatogram_list_index;
create index ms_chromatogram_list_index on "MS_chromatogram_list" ( "Entry_ID" );

drop index if exists ms_chromatogram_param_index;
create index ms_chromatogram_param_index on "MS_chromatogram_param" ( "Entry_ID" );

drop index if exists ms_chromatogram_software_index;
create index ms_chromatogram_software_index on "MS_chromatogram_software" ( "Entry_ID" );

drop index if exists ms_expt_index;
create index ms_expt_index on "MS_expt" ( "Entry_ID" );

drop index if exists ms_expt_param_index;
create index ms_expt_param_index on "MS_expt_param" ( "Entry_ID" );

drop index if exists ms_expt_software_index;
create index ms_expt_software_index on "MS_expt_software" ( "Entry_ID" );

drop index if exists mz_precursor_ion_index;
create index mz_precursor_ion_index on "MZ_precursor_ion" ( "Entry_ID" );

drop index if exists mz_precursor_ion_annotation_index;
create index mz_precursor_ion_annotation_index on "MZ_precursor_ion_annotation" ( "Entry_ID" );

drop index if exists mz_product_ion_index;
create index mz_product_ion_index on "MZ_product_ion" ( "Entry_ID" );

drop index if exists mz_product_ion_annotation_index;
create index mz_product_ion_annotation_index on "MZ_product_ion_annotation" ( "Entry_ID" );

drop index if exists mz_ratio_data_list_index;
create index mz_ratio_data_list_index on "MZ_ratio_data_list" ( "Entry_ID" );

drop index if exists mz_ratio_experiment_index;
create index mz_ratio_experiment_index on "MZ_ratio_experiment" ( "Entry_ID" );

drop index if exists mz_ratio_software_index;
create index mz_ratio_software_index on "MZ_ratio_software" ( "Entry_ID" );

drop index if exists mz_ratio_spectrum_param_index;
create index mz_ratio_spectrum_param_index on "MZ_ratio_spectrum_param" ( "Entry_ID" );

drop index if exists mass_spec_index;
create index mass_spec_index on "Mass_spec" ( "Entry_ID" );

drop index if exists mass_spec_citation_index;
create index mass_spec_citation_index on "Mass_spec_citation" ( "Entry_ID" );

drop index if exists mass_spec_component_param_index;
create index mass_spec_component_param_index on "Mass_spec_component_param" ( "Entry_ID" );

drop index if exists mass_spec_config_index;
create index mass_spec_config_index on "Mass_spec_config" ( "Entry_ID" );

drop index if exists mass_spec_ref_compd_index;
create index mass_spec_ref_compd_index on "Mass_spec_ref_compd" ( "Entry_ID" );

drop index if exists mass_spec_ref_compd_set_index;
create index mass_spec_ref_compd_set_index on "Mass_spec_ref_compd_set" ( "Entry_ID" );

drop index if exists mass_spec_software_index;
create index mass_spec_software_index on "Mass_spec_software" ( "Entry_ID" );

drop index if exists mass_spectrometer_list_index;
create index mass_spectrometer_list_index on "Mass_spectrometer_list" ( "Entry_ID" );

drop index if exists mass_spectrometer_view_index;
create index mass_spectrometer_view_index on "Mass_spectrometer_view" ( "Entry_ID" );

drop index if exists matched_entries_index;
create index matched_entries_index on "Matched_entries" ( "Entry_ID" );

drop index if exists method_index;
create index method_index on "Method" ( "Entry_ID" );

drop index if exists method_citation_index;
create index method_citation_index on "Method_citation" ( "Entry_ID" );

drop index if exists method_file_index;
create index method_file_index on "Method_file" ( "Entry_ID" );

drop index if exists method_param_index;
create index method_param_index on "Method_param" ( "Entry_ID" );

drop index if exists model_type_index;
create index model_type_index on "Model_type" ( "Entry_ID" );

drop index if exists nmr_experiment_citation_index;
create index nmr_experiment_citation_index on "NMR_experiment_citation" ( "Entry_ID" );

drop index if exists nmr_experiment_file_index;
create index nmr_experiment_file_index on "NMR_experiment_file" ( "Entry_ID" );

drop index if exists nmr_expt_systematic_name_index;
create index nmr_expt_systematic_name_index on "NMR_expt_systematic_name" ( "Entry_ID" );

drop index if exists nmr_probe_index;
create index nmr_probe_index on "NMR_probe" ( "Entry_ID" );

drop index if exists nmr_spec_expt_index;
create index nmr_spec_expt_index on "NMR_spec_expt" ( "Entry_ID" );

drop index if exists nmr_spectral_proc_software_index;
create index nmr_spectral_proc_software_index on "NMR_spectral_proc_software" ( "Entry_ID" );

drop index if exists nmr_spectral_processing_index;
create index nmr_spectral_processing_index on "NMR_spectral_processing" ( "Entry_ID" );

drop index if exists nmr_spectrometer_index;
create index nmr_spectrometer_index on "NMR_spectrometer" ( "Entry_ID" );

drop index if exists nmr_spectrometer_citation_index;
create index nmr_spectrometer_citation_index on "NMR_spectrometer_citation" ( "Entry_ID" );

drop index if exists nmr_spectrometer_list_index;
create index nmr_spectrometer_list_index on "NMR_spectrometer_list" ( "Entry_ID" );

drop index if exists nmr_spectrometer_probe_index;
create index nmr_spectrometer_probe_index on "NMR_spectrometer_probe" ( "Entry_ID" );

drop index if exists nmr_spectrometer_probe_citation_index;
create index nmr_spectrometer_probe_citation_index on "NMR_spectrometer_probe_citation" ( "Entry_ID" );

drop index if exists nmr_spectrometer_view_index;
create index nmr_spectrometer_view_index on "NMR_spectrometer_view" ( "Entry_ID" );

drop index if exists natural_source_db_index;
create index natural_source_db_index on "Natural_source_db" ( "Entry_ID" );

drop index if exists observed_conformer_index;
create index observed_conformer_index on "Observed_conformer" ( "Entry_ID" );

drop index if exists order_param_index;
create index order_param_index on "Order_param" ( "Entry_ID" );

drop index if exists order_parameter_experiment_index;
create index order_parameter_experiment_index on "Order_parameter_experiment" ( "Entry_ID" );

drop index if exists order_parameter_list_index;
create index order_parameter_list_index on "Order_parameter_list" ( "Entry_ID" );

drop index if exists order_parameter_software_index;
create index order_parameter_software_index on "Order_parameter_software" ( "Entry_ID" );

drop index if exists org_constr_file_comment_index;
create index org_constr_file_comment_index on "Org_constr_file_comment" ( "Entry_ID" );

drop index if exists other_constraint_expt_index;
create index other_constraint_expt_index on "Other_constraint_expt" ( "Entry_ID" );

drop index if exists other_constraint_list_index;
create index other_constraint_list_index on "Other_constraint_list" ( "Entry_ID" );

drop index if exists other_constraint_software_index;
create index other_constraint_software_index on "Other_constraint_software" ( "Entry_ID" );

drop index if exists other_data_index;
create index other_data_index on "Other_data" ( "Entry_ID" );

drop index if exists other_data_experiment_index;
create index other_data_experiment_index on "Other_data_experiment" ( "Entry_ID" );

drop index if exists other_data_software_index;
create index other_data_software_index on "Other_data_software" ( "Entry_ID" );

drop index if exists other_data_type_list_index;
create index other_data_type_list_index on "Other_data_type_list" ( "Entry_ID" );

drop index if exists other_struct_feature_index;
create index other_struct_feature_index on "Other_struct_feature" ( "Entry_ID" );

drop index if exists other_struct_feature_list_index;
create index other_struct_feature_list_index on "Other_struct_feature_list" ( "Entry_ID" );

drop index if exists pdbx_chem_comp_feature_index;
create index pdbx_chem_comp_feature_index on "PDBX_chem_comp_feature" ( "Entry_ID" );

drop index if exists pdbx_nonpoly_scheme_index;
create index pdbx_nonpoly_scheme_index on "PDBX_nonpoly_scheme" ( "Entry_ID" );

drop index if exists pdbx_poly_seq_scheme_index;
create index pdbx_poly_seq_scheme_index on "PDBX_poly_seq_scheme" ( "Entry_ID" );

drop index if exists ph_param_index;
create index ph_param_index on "PH_param" ( "Entry_ID" );

drop index if exists ph_param_list_index;
create index ph_param_list_index on "PH_param_list" ( "Entry_ID" );

drop index if exists ph_titr_result_index;
create index ph_titr_result_index on "PH_titr_result" ( "Entry_ID" );

drop index if exists ph_titration_experiment_index;
create index ph_titration_experiment_index on "PH_titration_experiment" ( "Entry_ID" );

drop index if exists ph_titration_list_index;
create index ph_titration_list_index on "PH_titration_list" ( "Entry_ID" );

drop index if exists ph_titration_software_index;
create index ph_titration_software_index on "PH_titration_software" ( "Entry_ID" );

drop index if exists peak_index;
create index peak_index on "Peak" ( "Entry_ID" );

drop index if exists peak_char_index;
create index peak_char_index on "Peak_char" ( "Entry_ID" );

drop index if exists peak_constraint_link_index;
create index peak_constraint_link_index on "Peak_constraint_link" ( "Entry_ID" );

drop index if exists peak_constraint_link_list_index;
create index peak_constraint_link_list_index on "Peak_constraint_link_list" ( "Entry_ID" );

drop index if exists peak_general_char_index;
create index peak_general_char_index on "Peak_general_char" ( "Entry_ID" );

drop index if exists peak_row_format_index;
create index peak_row_format_index on "Peak_row_format" ( "Entry_ID" );

drop index if exists rdc_index;
create index rdc_index on "RDC" ( "Entry_ID" );

drop index if exists rdc_constraint_index;
create index rdc_constraint_index on "RDC_constraint" ( "Entry_ID" );

drop index if exists rdc_constraint_comment_org_index;
create index rdc_constraint_comment_org_index on "RDC_constraint_comment_org" ( "Entry_ID" );

drop index if exists rdc_constraint_conv_err_index;
create index rdc_constraint_conv_err_index on "RDC_constraint_conv_err" ( "Entry_ID" );

drop index if exists rdc_constraint_expt_index;
create index rdc_constraint_expt_index on "RDC_constraint_expt" ( "Entry_ID" );

drop index if exists rdc_constraint_list_index;
create index rdc_constraint_list_index on "RDC_constraint_list" ( "Entry_ID" );

drop index if exists rdc_constraint_parse_err_index;
create index rdc_constraint_parse_err_index on "RDC_constraint_parse_err" ( "Entry_ID" );

drop index if exists rdc_constraint_parse_file_index;
create index rdc_constraint_parse_file_index on "RDC_constraint_parse_file" ( "Entry_ID" );

drop index if exists rdc_constraint_software_index;
create index rdc_constraint_software_index on "RDC_constraint_software" ( "Entry_ID" );

drop index if exists rdc_experiment_index;
create index rdc_experiment_index on "RDC_experiment" ( "Entry_ID" );

drop index if exists rdc_list_index;
create index rdc_list_index on "RDC_list" ( "Entry_ID" );

drop index if exists rdc_software_index;
create index rdc_software_index on "RDC_software" ( "Entry_ID" );

drop index if exists recoupling_pulse_sequence_index;
create index recoupling_pulse_sequence_index on "Recoupling_pulse_sequence" ( "Entry_ID" );

drop index if exists related_entries_index;
create index related_entries_index on "Related_entries" ( "Entry_ID" );

drop index if exists release_index;
create index release_index on "Release" ( "Entry_ID" );

drop index if exists rep_conf_index;
create index rep_conf_index on "Rep_conf" ( "Entry_ID" );

drop index if exists rep_conf_refinement_index;
create index rep_conf_refinement_index on "Rep_conf_refinement" ( "Entry_ID" );

drop index if exists rep_conf_software_index;
create index rep_conf_software_index on "Rep_conf_software" ( "Entry_ID" );

drop index if exists rep_coordinate_details_index;
create index rep_coordinate_details_index on "Rep_coordinate_details" ( "Entry_ID" );

drop index if exists representative_conformer_index;
create index representative_conformer_index on "Representative_conformer" ( "Entry_ID" );

drop index if exists resonance_index;
create index resonance_index on "Resonance" ( "Entry_ID" );

drop index if exists resonance_assignment_index;
create index resonance_assignment_index on "Resonance_assignment" ( "Entry_ID" );

drop index if exists resonance_covalent_link_index;
create index resonance_covalent_link_index on "Resonance_covalent_link" ( "Entry_ID" );

drop index if exists resonance_linker_list_index;
create index resonance_linker_list_index on "Resonance_linker_list" ( "Entry_ID" );

drop index if exists saxs_constraint_index;
create index saxs_constraint_index on "SAXS_constraint" ( "Entry_ID" );

drop index if exists saxs_constraint_expt_index;
create index saxs_constraint_expt_index on "SAXS_constraint_expt" ( "Entry_ID" );

drop index if exists saxs_constraint_list_index;
create index saxs_constraint_list_index on "SAXS_constraint_list" ( "Entry_ID" );

drop index if exists saxs_constraint_software_index;
create index saxs_constraint_software_index on "SAXS_constraint_software" ( "Entry_ID" );

drop index if exists saxs_expt_index;
create index saxs_expt_index on "SAXS_expt" ( "Entry_ID" );

drop index if exists sg_project_index;
create index sg_project_index on "SG_project" ( "Entry_ID" );

drop index if exists sample_index;
create index sample_index on "Sample" ( "Entry_ID" );

drop index if exists sample_citation_index;
create index sample_citation_index on "Sample_citation" ( "Entry_ID" );

drop index if exists sample_component_index;
create index sample_component_index on "Sample_component" ( "Entry_ID" );

drop index if exists sample_component_atom_isotope_index;
create index sample_component_atom_isotope_index on "Sample_component_atom_isotope" ( "Entry_ID" );

drop index if exists sample_condition_citation_index;
create index sample_condition_citation_index on "Sample_condition_citation" ( "Entry_ID" );

drop index if exists sample_condition_list_index;
create index sample_condition_list_index on "Sample_condition_list" ( "Entry_ID" );

drop index if exists sample_condition_variable_index;
create index sample_condition_variable_index on "Sample_condition_variable" ( "Entry_ID" );

drop index if exists secondary_struct_index;
create index secondary_struct_index on "Secondary_struct" ( "Entry_ID" );

drop index if exists secondary_struct_list_index;
create index secondary_struct_list_index on "Secondary_struct_list" ( "Entry_ID" );

drop index if exists secondary_struct_sel_index;
create index secondary_struct_sel_index on "Secondary_struct_sel" ( "Entry_ID" );

drop index if exists software_index;
create index software_index on "Software" ( "Entry_ID" );

drop index if exists software_applied_history_index;
create index software_applied_history_index on "Software_applied_history" ( "Entry_ID" );

drop index if exists software_applied_list_index;
create index software_applied_list_index on "Software_applied_list" ( "Entry_ID" );

drop index if exists software_applied_methods_index;
create index software_applied_methods_index on "Software_applied_methods" ( "Entry_ID" );

drop index if exists software_citation_index;
create index software_citation_index on "Software_citation" ( "Entry_ID" );

drop index if exists software_specific_info_index;
create index software_specific_info_index on "Software_specific_info" ( "Entry_ID" );

drop index if exists software_specific_info_list_index;
create index software_specific_info_list_index on "Software_specific_info_list" ( "Entry_ID" );

drop index if exists spectral_acq_param_index;
create index spectral_acq_param_index on "Spectral_acq_param" ( "Entry_ID" );

drop index if exists spectral_density_index;
create index spectral_density_index on "Spectral_density" ( "Entry_ID" );

drop index if exists spectral_density_experiment_index;
create index spectral_density_experiment_index on "Spectral_density_experiment" ( "Entry_ID" );

drop index if exists spectral_density_list_index;
create index spectral_density_list_index on "Spectral_density_list" ( "Entry_ID" );

drop index if exists spectral_density_software_index;
create index spectral_density_software_index on "Spectral_density_software" ( "Entry_ID" );

drop index if exists spectral_dim_index;
create index spectral_dim_index on "Spectral_dim" ( "Entry_ID" );

drop index if exists spectral_dim_transfer_index;
create index spectral_dim_transfer_index on "Spectral_dim_transfer" ( "Entry_ID" );

drop index if exists spectral_peak_list_index;
create index spectral_peak_list_index on "Spectral_peak_list" ( "Entry_ID" );

drop index if exists spectral_peak_software_index;
create index spectral_peak_software_index on "Spectral_peak_software" ( "Entry_ID" );

drop index if exists spectral_processing_param_index;
create index spectral_processing_param_index on "Spectral_processing_param" ( "Entry_ID" );

drop index if exists spectral_transition_index;
create index spectral_transition_index on "Spectral_transition" ( "Entry_ID" );

drop index if exists spectral_transition_char_index;
create index spectral_transition_char_index on "Spectral_transition_char" ( "Entry_ID" );

drop index if exists spectral_transition_general_char_index;
create index spectral_transition_general_char_index on "Spectral_transition_general_char" ( "Entry_ID" );

drop index if exists spin_system_index;
create index spin_system_index on "Spin_system" ( "Entry_ID" );

drop index if exists spin_system_link_index;
create index spin_system_link_index on "Spin_system_link" ( "Entry_ID" );

drop index if exists struct_anno_char_index;
create index struct_anno_char_index on "Struct_anno_char" ( "Entry_ID" );

drop index if exists struct_anno_software_index;
create index struct_anno_software_index on "Struct_anno_software" ( "Entry_ID" );

drop index if exists struct_asym_index;
create index struct_asym_index on "Struct_asym" ( "Entry_ID" );

drop index if exists struct_classification_index;
create index struct_classification_index on "Struct_classification" ( "Entry_ID" );

drop index if exists struct_image_index;
create index struct_image_index on "Struct_image" ( "Entry_ID" );

drop index if exists struct_keywords_index;
create index struct_keywords_index on "Struct_keywords" ( "Entry_ID" );

drop index if exists structure_annotation_index;
create index structure_annotation_index on "Structure_annotation" ( "Entry_ID" );

drop index if exists structure_interaction_index;
create index structure_interaction_index on "Structure_interaction" ( "Entry_ID" );

drop index if exists structure_interaction_list_index;
create index structure_interaction_list_index on "Structure_interaction_list" ( "Entry_ID" );

drop index if exists study_index;
create index study_index on "Study" ( "Entry_ID" );

drop index if exists study_entry_list_index;
create index study_entry_list_index on "Study_entry_list" ( "Entry_ID" );

drop index if exists study_keyword_index;
create index study_keyword_index on "Study_keyword" ( "Entry_ID" );

drop index if exists study_list_index;
create index study_list_index on "Study_list" ( "Entry_ID" );

drop index if exists subsystem_biological_function_index;
create index subsystem_biological_function_index on "Subsystem_biological_function" ( "Entry_ID" );

drop index if exists subsystem_citation_index;
create index subsystem_citation_index on "Subsystem_citation" ( "Entry_ID" );

drop index if exists subsystem_common_name_index;
create index subsystem_common_name_index on "Subsystem_common_name" ( "Entry_ID" );

drop index if exists subsystem_component_index;
create index subsystem_component_index on "Subsystem_component" ( "Entry_ID" );

drop index if exists subsystem_db_link_index;
create index subsystem_db_link_index on "Subsystem_db_link" ( "Entry_ID" );

drop index if exists subsystem_keyword_index;
create index subsystem_keyword_index on "Subsystem_keyword" ( "Entry_ID" );

drop index if exists subsystem_type_index;
create index subsystem_type_index on "Subsystem_type" ( "Entry_ID" );

drop index if exists systematic_chem_shift_offset_index;
create index systematic_chem_shift_offset_index on "Systematic_chem_shift_offset" ( "Entry_ID" );

drop index if exists t1_index;
create index t1_index on "T1" ( "Entry_ID" );

drop index if exists t1rho_index;
create index t1rho_index on "T1rho" ( "Entry_ID" );

drop index if exists t2_index;
create index t2_index on "T2" ( "Entry_ID" );

drop index if exists ta_constraint_comment_org_index;
create index ta_constraint_comment_org_index on "TA_constraint_comment_org" ( "Entry_ID" );

drop index if exists ta_constraint_conv_err_index;
create index ta_constraint_conv_err_index on "TA_constraint_conv_err" ( "Entry_ID" );

drop index if exists ta_constraint_parse_err_index;
create index ta_constraint_parse_err_index on "TA_constraint_parse_err" ( "Entry_ID" );

drop index if exists ta_constraint_parse_file_index;
create index ta_constraint_parse_file_index on "TA_constraint_parse_file" ( "Entry_ID" );

drop index if exists task_index;
create index task_index on "Task" ( "Entry_ID" );

drop index if exists tensor_index;
create index tensor_index on "Tensor" ( "Entry_ID" );

drop index if exists tensor_list_index;
create index tensor_list_index on "Tensor_list" ( "Entry_ID" );

drop index if exists terminal_residue_index;
create index terminal_residue_index on "Terminal_residue" ( "Entry_ID" );

drop index if exists tertiary_struct_index;
create index tertiary_struct_index on "Tertiary_struct" ( "Entry_ID" );

drop index if exists tertiary_struct_element_list_index;
create index tertiary_struct_element_list_index on "Tertiary_struct_element_list" ( "Entry_ID" );

drop index if exists tertiary_struct_element_sel_index;
create index tertiary_struct_element_sel_index on "Tertiary_struct_element_sel" ( "Entry_ID" );

drop index if exists theoretical_t1_index;
create index theoretical_t1_index on "Theoretical_T1" ( "Entry_ID" );

drop index if exists theoretical_t2_index;
create index theoretical_t2_index on "Theoretical_T2" ( "Entry_ID" );

drop index if exists theoretical_auto_relaxation_index;
create index theoretical_auto_relaxation_index on "Theoretical_auto_relaxation" ( "Entry_ID" );

drop index if exists theoretical_auto_relaxation_experiment_index;
create index theoretical_auto_relaxation_experiment_index on "Theoretical_auto_relaxation_experiment" ( "Entry_ID" );

drop index if exists theoretical_auto_relaxation_list_index;
create index theoretical_auto_relaxation_list_index on "Theoretical_auto_relaxation_list" ( "Entry_ID" );

drop index if exists theoretical_auto_relaxation_software_index;
create index theoretical_auto_relaxation_software_index on "Theoretical_auto_relaxation_software" ( "Entry_ID" );

drop index if exists theoretical_chem_shift_index;
create index theoretical_chem_shift_index on "Theoretical_chem_shift" ( "Entry_ID" );

drop index if exists theoretical_chem_shift_list_index;
create index theoretical_chem_shift_list_index on "Theoretical_chem_shift_list" ( "Entry_ID" );

drop index if exists theoretical_coupling_constant_index;
create index theoretical_coupling_constant_index on "Theoretical_coupling_constant" ( "Entry_ID" );

drop index if exists theoretical_coupling_constant_experiment_index;
create index theoretical_coupling_constant_experiment_index on "Theoretical_coupling_constant_experiment" ( "Entry_ID" );

drop index if exists theoretical_coupling_constant_list_index;
create index theoretical_coupling_constant_list_index on "Theoretical_coupling_constant_list" ( "Entry_ID" );

drop index if exists theoretical_coupling_constant_software_index;
create index theoretical_coupling_constant_software_index on "Theoretical_coupling_constant_software" ( "Entry_ID" );

drop index if exists theoretical_cross_correlation_dd_index;
create index theoretical_cross_correlation_dd_index on "Theoretical_cross_correlation_DD" ( "Entry_ID" );

drop index if exists theoretical_cross_correlation_dd_experiment_index;
create index theoretical_cross_correlation_dd_experiment_index on "Theoretical_cross_correlation_DD_experiment" ( "Entry_ID" );

drop index if exists theoretical_cross_correlation_dd_list_index;
create index theoretical_cross_correlation_dd_list_index on "Theoretical_cross_correlation_DD_list" ( "Entry_ID" );

drop index if exists theoretical_cross_correlation_dd_software_index;
create index theoretical_cross_correlation_dd_software_index on "Theoretical_cross_correlation_DD_software" ( "Entry_ID" );

drop index if exists theoretical_heteronucl_noe_index;
create index theoretical_heteronucl_noe_index on "Theoretical_heteronucl_NOE" ( "Entry_ID" );

drop index if exists theoretical_heteronucl_noe_experiment_index;
create index theoretical_heteronucl_noe_experiment_index on "Theoretical_heteronucl_NOE_experiment" ( "Entry_ID" );

drop index if exists theoretical_heteronucl_noe_list_index;
create index theoretical_heteronucl_noe_list_index on "Theoretical_heteronucl_NOE_list" ( "Entry_ID" );

drop index if exists theoretical_heteronucl_noe_software_index;
create index theoretical_heteronucl_noe_software_index on "Theoretical_heteronucl_NOE_software" ( "Entry_ID" );

drop index if exists theoretical_heteronucl_t1_experiment_index;
create index theoretical_heteronucl_t1_experiment_index on "Theoretical_heteronucl_T1_experiment" ( "Entry_ID" );

drop index if exists theoretical_heteronucl_t1_list_index;
create index theoretical_heteronucl_t1_list_index on "Theoretical_heteronucl_T1_list" ( "Entry_ID" );

drop index if exists theoretical_heteronucl_t1_software_index;
create index theoretical_heteronucl_t1_software_index on "Theoretical_heteronucl_T1_software" ( "Entry_ID" );

drop index if exists theoretical_heteronucl_t2_experiment_index;
create index theoretical_heteronucl_t2_experiment_index on "Theoretical_heteronucl_T2_experiment" ( "Entry_ID" );

drop index if exists theoretical_heteronucl_t2_list_index;
create index theoretical_heteronucl_t2_list_index on "Theoretical_heteronucl_T2_list" ( "Entry_ID" );

drop index if exists theoretical_heteronucl_t2_software_index;
create index theoretical_heteronucl_t2_software_index on "Theoretical_heteronucl_T2_software" ( "Entry_ID" );

drop index if exists torsion_angle_index;
create index torsion_angle_index on "Torsion_angle" ( "Entry_ID" );

drop index if exists torsion_angle_constraint_index;
create index torsion_angle_constraint_index on "Torsion_angle_constraint" ( "Entry_ID" );

drop index if exists torsion_angle_constraint_list_index;
create index torsion_angle_constraint_list_index on "Torsion_angle_constraint_list" ( "Entry_ID" );

drop index if exists torsion_angle_constraint_software_index;
create index torsion_angle_constraint_software_index on "Torsion_angle_constraint_software" ( "Entry_ID" );

drop index if exists torsion_angle_constraints_expt_index;
create index torsion_angle_constraints_expt_index on "Torsion_angle_constraints_expt" ( "Entry_ID" );

drop index if exists vendor_index;
create index vendor_index on "Vendor" ( "Entry_ID" );

drop index if exists xray_instrument_index;
create index xray_instrument_index on "Xray_instrument" ( "Entry_ID" );

