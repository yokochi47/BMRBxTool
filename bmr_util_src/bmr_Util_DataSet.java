/*
   BMRBxTool - XML converter for NMR-STAR data
    Copyright 2013-2025 Masashi Yokochi

    https://github.com/yokochi47/BMRBxTool

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package org.pdbj.bmrbpub.schema.mmcifNmrStar;

import java.util.HashMap;
import java.util.Map;

public class bmr_Util_DataSet {

	static final Map<String, String> map = new HashMap<String, String>() {

		private static final long serialVersionUID = 48L;

		{

			put("pKa_values", "pKa_value_data_set");
			put("chemical_rates", "chemical_rates");
			put("spectral_density_values", "spectral_density_values");
			put("dipole_CSA_cross_correlation_relaxation", "dipole_CSA_cross_correlation_relaxation");
			put("conformer_family_coord_set", "conformer_family_coord_set");
			put("molecular_axis_determinations", "molecular_axis_determinations");
			put("pKa_value_data_set", "pKa_value_data_set");
			put("representative_conformer", "representative_conformer");
			put("pH_param_list", "pH_NMR_param_list");
			put("deduced_secd_struct_features", "deduced_secd_struct_features");
			put("CSA_CSA_cross_correlation_relaxation", "CSA_CSA_cross_correlation_relaxation");
			put("H_exch_protection_factors", "H_exch_protection_factors");
			put("dipole_dipole_cross_correlation_relaxation", "dipole_dipole_cross_correlation_relaxation");
			put("deduced_hydrogen_bonds", "deduced_hydrogen_bonds");
			put("other_data_list", "other_data_list");
			put("peak_lists", "spectral_peak_list");
			put("assigned_chemical_shifts", "assigned_chemical_shifts");
			put("heteronucl_T2_relaxation", "heteronucl_T2_relaxation");
			put("D_H_fractionation_factors", "D_H_fractionation_factors");
			put("H_exch_rates", "H_exch_rates");
			put("spectral_peak_list", "spectral_peak_list");
			put("cross_correlation", "dipole_CSA_cross_correlation_relaxation");
			put("NMR_applied_experiment", "other_data_list");
			put("homonucl_NOEs", "homonucl_NOEs");
			put("kinetic_rates", "chemical_rates");
			put("pH_NMR_param_list", "pH_NMR_param_list");
			put("dipolar_couplings", "dipolar_couplings");
			put("binding constants", "binding_constants");
			put("binding_constants", "binding_constants");
			put("RDCs", "RDCs");
			put("other_kind_of_data", "other_data_list");
			put("heteronucl_T1rho_relaxation", "heteronucl_T1rho_relaxation");
			put("pH_titration", "pH_titration");
			put("dipole_dipole_relaxation", "dipole_dipole_relaxation");
			put("heteronucl_T1_relaxation", "heteronucl_T1_relaxation");
			put("order_parameters", "order_parameters");
			put("general_relaxation", "general_relaxation");
			put("T1rho_relaxation", "heteronucl_T1rho_relaxation");
			put("coupling_constants", "coupling_constants");
			put("heteronucl_NOEs", "heteronucl_NOEs");

		}
	};

	public static String getType(String val_name) {
		return (String) map.get(val_name);
	}
}
