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

public class bmr_Util_Datum {

	static final Map<String, String> map = new HashMap<String, String>() {

		private static final long serialVersionUID = 49L;

		{

			put("31P chemical shifts", "31P chemical shifts");
			put("T2 relaxation values", "T2 relaxation values");
			put("distance constraints", "distance constraints");
			put("15N chemical shifts", "15N chemical shifts");
			put("111Cd chemical shifts", "111Cd chemical shifts");
			put("pH NMR parameter values", "pH NMR parameter values");
			put("quadrupolar couplings", "quadrupolar couplings");
			put("na", "na");
			put("residual dipolar couplings", "residual dipolar couplings");
			put("dipolar coupling values", "dipolar coupling values");
			put("chemical shift constraints", "chemical shift constraints");
			put("torsion angle constraints", "torsion angle constraints");
			put("H exchange rates", "H exchange rates");
			put("3H chemical shifts", "3H chemical shifts");
			put("conformer coordinate sets", "na");
			put("113Cd chemical shifts", "113Cd chemical shifts");
			put("ambiguous distance constraints", "ambiguous distance constraints");
			put("bond orientation values", "bond orientation values");
			put("deduced hydrogen bonds", "deduced hydrogen bonds");
			put("deduced secondary structure values", "deduced secondary structure values");
			put("theoretical chemical shifts", "theoretical chemical shifts");
			put("chemical shift anisotropy values", "chemical shift anisotropy values");
			put("1H chemical shifts", "1H chemical shifts");
			put("6Li chemical shifts", "6Li chemical shifts");
			put("heteronuclear NOE values", "heteronuclear NOE values");
			put("spectral density values", "spectral density values");
			put("order parameters", "order parameters");
			put("10B chemical shifts", "10B chemical shifts");
			put("35Cl chemical shifts", "35Cl chemical shifts");
			put("T1 relaxation values", "T1 relaxation values");
			put("19F chemical shifts", "19F chemical shifts");
			put("chemical rates", "chemical rates");
			put("13C chemical shifts", "13C chemical shifts");
			put("29Si chemical shifts", "29Si chemical shifts");
			put("dipole-dipole relaxation values", "dipole-dipole relaxation values");
			put("chemical shift isotope effects", "chemical shift isotope effects");
			put("129Xe chemical shifts", "129Xe chemical shifts");
			put("dipolar couplings", "residual dipolar couplings");
			put("23Na chemical shifts", "23Na chemical shifts");
			put("T1rho relaxation values", "T1rho relaxation values");
			put("hydrogen bond distance constraints", "hydrogen bond distance constraints");
			put("Distance constraints", "distance constraints");
			put("spectral peak list", "na");
			put("binding constants", "binding constants");
			put("RDCs", "residual dipolar couplings");
			put("chemical shift perturbation values", "chemical shift perturbation values");
			put("17O chemical shifts", "17O chemical shifts");
			put("2H chemical shifts", "2H chemical shifts");
			put("homonuclear NOE values", "homonuclear NOE values");
			put("chemical shift anisotropy tensor values", "chemical shift anisotropy tensor values");
			put("peak list", "na");
			put("195Pt chemical shifts", "195Pt chemical shifts");
			put("H exchange protection factors", "H exchange protection factors");
			put("chemical shift tensors", "chemical shift tensors");
			put("coupling constants", "coupling constants");
			put("11B chemical shifts", "11B chemical shifts");
			put("pKa values", "pKa values");
			put("D/H fractionation factors", "D/H fractionation factors");
			put("molecule interaction chemical shift values", "chemical shift perturbation values");
			put("dipolar coupling tensor values", "dipolar coupling tensor values");
			put("symmetry constraints", "symmetry constraints");
			put("S2 parameters", "order parameters");
			put("cross correlation relaxation values", "cross correlation relaxation values");
			put("kinetic rates", "chemical rates");

		}
	};

	public static String getType(String val_name) {
		return (String) map.get(val_name);
	}
}
