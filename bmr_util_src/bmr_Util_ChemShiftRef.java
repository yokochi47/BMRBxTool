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

public class bmr_Util_ChemShiftRef {

	static final Map<String, String> map_mol_common_name = new HashMap<String, String>() {

		private static final long serialVersionUID = 28L;

		{

			put("CH3CO2NA", "sodium acetate");
			put("spectrometer frequency", "na");
			put("nitric acid", "nitric acid");
			put("trifluroethanol", "trifluoroethanol");
			put("[15N, 15N] ammonium nitrate", "[15N, 15N] ammonium nitrate");
			put("LEU residue no. 69", "na");
			put("suberic acid bis", "suberic acid bis");
			put("Internal Standard (POG10)", "na");
			put("TMSP", "TSP");
			put("H2O", "water");
			put("TMSPA", "TMSPA");
			put("calculated from 13C SR", "na");
			put("[15N](NH4)2SO4", "ammonium sulfate");
			put("85% H3PO4", "phosphoric acid (85%)");
			put("suberic acid bis(N-hydroxy-succinimide ester)", "suberic acid bis");
			put("MPD", "na");
			put("trimethylphosphate", "trimethyl phosphate");
			put("[15N] nitric acid", "[15N] nitric acid");
			put("PSSI", "na");
			put("H20", "water");
			put("[15N] ammonium nitrate", "[15N] ammonium nitrate");
			put("[15N] nitromethane", "[15N] nitromethane");
			put("[15N, 15N]NH4NO3", "[15N, 15N] ammonium nitrate");
			put("protein backbone amides", "na");
			put("Liquid NH3", "liquid anhydrous ammonia");
			put("CF3CHDOH", "trifluoroethanol");
			put("L11", "na");
			put("XC 4149", "na");
			put("ubiquitin", "na");
			put("Methanol", "methanol");
			put("external [15]NH4Cl", "[15N] ammonium chloride");
			put("UUCG RNA tetraloop", "na");
			put("phosphoric acid", "phosphoric acid");
			put("E2", "na");
			put("CD3OH", "methanol");
			put("TSPA", "TSPA");
			put("methyl iodide", "methyl iodide");
			put("TSP", "TSP");
			put("CD3OD", "methanol");
			put("TMPS", "TMPS");
			put("Liquid Ammonia", "liquid anhydrous ammonia");
			put("[15N]CH3NO2", "[15N] nitromethane");
			put("NH4", "liquid anhydrous ammonia");
			put("NH3", "liquid anhydrous ammonia");
			put("phosphoric acid (85%)", "phosphoric acid (85%)");
			put("sodium phosphate", "sodium phosphate");
			put("Trifuoroethanol", "trifluoroethanol");
			put("[15N]ammonium", "nitrate=[15N] ammonium nitrate");
			put("Amonia", "liquid anhydrous ammonia");
			put("Adamantane", "adamantane");
			put("CD3COOH", "acetate");
			put("Tyrosine(HCl)", "na");
			put("GB1", "na");
			put("15N4Cl in 1M HCl", "[15N] ammonium chloride");
			put("liquid NH3", "liquid anhydrous ammonia");
			put("TFE", "TFE");
			put("TFA", "TFA");
			put("TSP-d4", "TSP-d4");
			put("liquid ammonia", "liquid anhydrous ammonia");
			put("NH4Cl", "ammonium chloride");
			put("NH4[15]NO3", "[15N] ammonium nitrate");
			put("DSS", "DSS");
			put("AMS", "na");
			put("adamantane", "adamantane");
			put("ammonia", "liquid anhydrous ammonia");
			put("StR65", "na");
			put("ammonium sulfate", "ammonium sulfate");
			put("H3PO4", "phosphoric acid");
			put("Ammonium", "liquid anhydrous ammonia");
			put("methyl iodine", "methyl iodide");
			put("trimethyl phospate", "trimethyl phosphate");
			put("CF3CD2OH", "trifluoroethanol");
			put("NH4CL", "nitric acid");
			put("CH4(15N2O)", "urea");
			put("CF3CD2OD", "trifluoroethanol");
			put("Ala-Gly-Gly", "na");
			put("TPS", "TSP");
			put("NH4OH", "liquid anhydrous ammonia");
			put("ALA", "na");
			put("UbcH5B", "na");
			put("hexamethylbenzene", "hexamethylbenzene");
			put("cadmium perchlorate", "cadmium perchlorate");
			put("Dioxine", "dioxane");
			put("DMSO", "DMSO");
			put("borate", "borate");
			put("formamide", "formamide");
			put("[15N]NH4Cl", "[15N] ammonium chloride");
			put("HDO", "water");
			put("TIP-1", "na");
			put("trichlorofluoromethane", "trichlorofluoromethane");
			put("DDS", "DSS");
			put("CD2H", "methanol");
			put("liquid anhydrous ammonia", "liquid anhydrous ammonia");
			put("MeOH", "methanol");
			put("Dioxane", "dioxane");
			put("trifluoroethanol", "trifluoroethanol");
			put("nitromethane", "nitromethane");
			put("TFE-d2", "TFE-d2");
			put("ammonium chloride", "ammonium chloride");
			put("huwentoxin-xi", "na");
			put("(NH4)2SO4", "ammonium sulfate");
			put("HOD", "water");
			put("[15N] ammonium chloride", "[15N] ammonium chloride");
			put("sodium acetate", "sodium acetate");
			put("TMS", "TMS");
			put("TMP", "TMP");
			put("NH4NO3", "ammonium nitrate");
			put("NH4NO2", "ammonium nitrate");
			put("neat TMS", "TMS");
			put("H2O/HDO", "water");
			put("[15N]nitric acid", "[15N] nitric acid");
			put("[15N] ammonium sulfate", "[15N] ammonium sulfate");
			put("F3CCDHOD", "trifluoroethanol");
			put("ammonium hydroxide", "ammonium hydroxide");
			put("adamantan", "adamantane");
			put("ammonium nitrite", "ammonium nitrite");
			put("na", "na");
			put("HFIP", "HFIP");
			put("glucose", "glucose");
			put("methanol", "methanol");
			put("DMSO-d6", "DMSO-d6");
			put("DMSO-d5", "DMSO-d5");
			put("DMPC", "DMPC");
			put("urea", "urea");
			put("sC4", "na");
			put("HMS", "na");
			put("DNA", "na");
			put("ammonium nitrate", "ammonium nitrate");
			put("anhydrous ammonia", "liquid anhydrous ammonia");
			put("water", "water");
			put("p-dioxane", "p-dioxane");
			put("acetate", "acetate");
			put("ammonium chloride NH4Cl", "ammonium chloride");
			put("Urea", "urea");
			put("Water", "water");
			put("trimethyl phosphate", "trimethyl phosphate");
			put("T-DNA", "na");
			put("Trifluoroethanol", "trifluoroethanol");
			put("dioxan", "dioxane");
			put("AGG", "na");
			put("dioxane", "dioxane");
			put("15NHNO3", "[15N] nitric acid");
			put("Trimethyl silyl propionate", "TSP");
			put("Nitromethane", "Nitromethane");

		}
	};

	public static String getMolCommonName(String val_name) {
		return (String) map_mol_common_name.get(val_name);
	}

	static final Map<String, String> map_ref_method = new HashMap<String, String>() {

		private static final long serialVersionUID = 29L;

		{

			put("external_to_the_sample", "external");
			put("indirect", "external");
			put("external/internal", "na");
			put("direct", "internal");
			put("n/a", "na");
			put("External_in_the_sample", "external");
			put("indirectly", "external");
			put("internal", "internal");
			put("na", "na");
			put("external", "external");
			put("exinternal", "na");

		}
	};

	public static String getRefMethod(String val_name) {
		return (String) map_ref_method.get(val_name);
	}

	static final Map<String, String> map_ref_type = new HashMap<String, String>() {

		private static final long serialVersionUID = 30L;

		{

			put("secondary", "indirect");
			put("na", "na");
			put("internal", "direct");
			put("indirect", "indirect");
			put("n/a", "na");
			put("external", "indirect");
			put("direct", "direct");

		}
	};

	public static String getRefType(String val_name) {
		return (String) map_ref_type.get(val_name);
	}

	static final Map<String, String> map_chem_shift_units = new HashMap<String, String>() {

		private static final long serialVersionUID = 31L;

		{

			put("0.00", "ppm");
			put("ppm", "ppm");
			put("Hz", "Hz");
			put("MHz", "MHz");

		}
	};

	public static String getChemShiftUnits(String val_name) {
		return (String) map_chem_shift_units.get(val_name);
	}

	static final Map<String, String> map_external_ref_loc = new HashMap<String, String>() {

		private static final long serialVersionUID = 32L;

		{

			put("insert at outer edge of experimental sample tube", "insert at outer edge of experimental sample tube");
			put("insert at center of a separate sample tube", "insert at center of a separate sample tube");
			put("outside sample", "other");
			put("none", "other");
			put("in a separate rotor", "separate tube (no insert) similar to the experimental sample tube");
			put("separate tube (no insert) not similar to the experimental sample tube", "separate tube (no insert) not similar to the experimental sample tube");
			put("other", "other");
			put("External", "other");
			put("internal 50uM DSS", "other");
			put("na", "other");
			put("external to the sample", "other");
			put("indirect", "other");
			put("separate sample", "other");
			put("in the sample", "other");
			put("internal DSS 50 uM", "other");
			put("separate tube (no insert) similar to the experimental sample tube", "separate tube (no insert) similar to the experimental sample tube");
			put("internal 50 uM DSS", "other");
			put("cylindrical", "other");
			put("spiked into experimental sample", "other");
			put("methanol", "other");
			put("in sample", "other");
			put("External_in_the_sample", "other");
			put("separate solid-state NMR rotor", "separate tube (no insert) similar to the experimental sample tube");
			put("external in the sample", "other");
			put("insert at center of experimental sample tube", "insert at center of experimental sample tube");
			put("capilary in NMR tube", "insert at outer edge of experimental sample tube");
			put("urea", "other");
			put("e.g. separate NMR sample tube similar to the experimental one", "separate tube (no insert) similar to the experimental sample tube");
			put("n.a.", "other");
			put("insert at outer edge of a separate sample tube", "insert at outer edge of a separate sample tube");
			put("external to sample", "other");
			put("separate NMR sample tube similar to the experimental one", "separate tube (no insert) similar to the experimental sample tube");
			put("external_to_the_sample", "other");

		}
	};

	public static String getExternalRefLoc(String val_name) {
		return (String) map_external_ref_loc.get(val_name);
	}

	static final Map<String, String> map_external_ref_axis = new HashMap<String, String>() {

		private static final long serialVersionUID = 33L;

		{

			put("0.101329118", "na");
			put("0.251449530", "na");
			put("Parallel", "parallel");
			put("perpendicular", "perpendicular");
			put("internal", "na");
			put("perpendicular_to_Bo", "perpendicular");
			put("parallel", "parallel");
			put("na", "na");
			put("n.a.", "na");
			put("1.0", "na");
			put("other", "na");
			put("magic angle", "magic angle");
			put("none", "na");
			put("spherical", "na");
			put("parallel_to_Bo", "parallel");
			put("Magic Angle", "magic angle");

		}
	};

	public static String getExternalRefAxis(String val_name) {
		return (String) map_external_ref_axis.get(val_name);
	}

	static final Map<String, String> map_external_ref_sample_geometry = new HashMap<String, String>() {

		private static final long serialVersionUID = 34L;

		{

			put("0.101329118", "other");
			put("indirect", "other");
			put("0.251449530", "other");
			put("Cylindrical", "cylindrical");
			put("n/a", "other");
			put("internal", "other");
			put("cyclindrical", "cylindrical");
			put("n.a.", "other");
			put("1.0", "other");
			put("other", "other");
			put("external", "other");
			put("none", "other");
			put("spherical", "spherical");
			put("cylindrical", "cylindrical");

		}
	};

	public static String getExternalRefSampleGeometry(String val_name) {
		return (String) map_external_ref_sample_geometry.get(val_name);
	}

	String atom_isotope_number;
	String atom_type;
	String indirect_shift_ratio;

	bmr_Util_ChemShiftRef(String atom_type_name, String atom_isotope_number_name, String indirect_shift_ratio_name) {

		if (atom_type_name == null && !(atom_isotope_number_name == null || atom_isotope_number_name.isEmpty() || atom_isotope_number_name.equals(".") || atom_isotope_number_name.equals("?"))) {

			if (atom_isotope_number_name.equals("1") || atom_isotope_number_name.equals("2"))
				atom_type_name = "H";

			if (atom_isotope_number_name.equals("13"))
				atom_type_name = "C";

			if (atom_isotope_number_name.equals("15"))
				atom_type_name = "N";

			if (atom_isotope_number_name.equals("31"))
				atom_type_name = "P";

			if (atom_isotope_number_name.equals("19"))
				atom_type_name = "F";

			if (atom_isotope_number_name.equals("6"))
				atom_type_name = "Li";

			if (atom_isotope_number_name.equals("11") || atom_isotope_number_name.equals("10"))
				atom_type_name = "B";

			if (atom_isotope_number_name.equals("17"))
				atom_type_name = "O";

			if (atom_isotope_number_name.equals("23"))
				atom_type_name = "Na";

			if (atom_isotope_number_name.equals("29"))
				atom_type_name = "Si";

			if (atom_isotope_number_name.equals("35"))
				atom_type_name = "Cl";

			if (atom_isotope_number_name.equals("113") || atom_isotope_number_name.equals("111"))
				atom_type_name = "Cd";

			if (atom_isotope_number_name.equals("129"))
				atom_type_name = "Xe";

			if (atom_isotope_number_name.equals("195"))
				atom_type_name = "Pt";

		}

		if (atom_type_name != null) {

			if (atom_type_name.equalsIgnoreCase("1H") || atom_type_name.equalsIgnoreCase("2H"))
				atom_type_name = "H";

			if (atom_type_name.equalsIgnoreCase("13C"))
				atom_type_name = "C";

			if (atom_type_name.equalsIgnoreCase("15N") || atom_type_name.equalsIgnoreCase("14N"))
				atom_type_name = "N";

			if (atom_type_name.equalsIgnoreCase("31P"))
				atom_type_name = "P";

			if (atom_type_name.equalsIgnoreCase("19F"))
				atom_type_name = "F";

			if (atom_type_name.equalsIgnoreCase("6Li"))
				atom_type_name = "Li";

			if (atom_type_name.equalsIgnoreCase("11B") || atom_type_name.equalsIgnoreCase("10B"))
				atom_type_name = "B";

			if (atom_type_name.equalsIgnoreCase("17O"))
				atom_type_name = "O";

			if (atom_type_name.equalsIgnoreCase("23Na"))
				atom_type_name = "Na";

			if (atom_type_name.equalsIgnoreCase("29Si"))
				atom_type_name = "Si";

			if (atom_type_name.equalsIgnoreCase("35Cl"))
				atom_type_name = "Cl";

			if (atom_type_name.equalsIgnoreCase("113Cd") || atom_type_name.equalsIgnoreCase("111Cd"))
				atom_type_name = "Cd";

			if (atom_type_name.equalsIgnoreCase("129Xe"))
				atom_type_name = "Xe";

			if (atom_type_name.equalsIgnoreCase("195Pt"))
				atom_type_name = "Pt";

		}

		atom_type = atom_type_name;

		if (atom_type_name != null && (atom_isotope_number_name == null || atom_isotope_number_name.isEmpty() || atom_isotope_number_name.equals(".") || atom_isotope_number_name.equals("?"))) {

			if (atom_type_name.equalsIgnoreCase("H"))
				atom_isotope_number_name = "1";

			if (atom_type_name.equalsIgnoreCase("C"))
				atom_isotope_number_name = "13";

			if (atom_type_name.equalsIgnoreCase("N"))
				atom_isotope_number_name = "15";

			if (atom_type_name.equalsIgnoreCase("P"))
				atom_isotope_number_name = "31";

			if (atom_type_name.equalsIgnoreCase("F"))
				atom_isotope_number_name = "19";

			if (atom_type_name.equalsIgnoreCase("Li"))
				atom_isotope_number_name = "6";

			if (atom_type_name.equalsIgnoreCase("B"))
				atom_isotope_number_name = "11";

			if (atom_type_name.equalsIgnoreCase("O"))
				atom_isotope_number_name = "17";

			if (atom_type_name.equalsIgnoreCase("Na"))
				atom_isotope_number_name = "23";

			if (atom_type_name.equalsIgnoreCase("Si"))
				atom_isotope_number_name = "29";

			if (atom_type_name.equalsIgnoreCase("Cl"))
				atom_isotope_number_name = "35";

			if (atom_type_name.equalsIgnoreCase("Cd"))
				atom_isotope_number_name = "113";

			if (atom_type_name.equalsIgnoreCase("Xe"))
				atom_isotope_number_name = "129";

			if (atom_type_name.equalsIgnoreCase("Pt"))
				atom_isotope_number_name = "195";

		}

		if (atom_isotope_number_name != null && atom_type_name != null) {

			Integer _atom_isotope_number = Integer.valueOf(atom_isotope_number_name);

			if (atom_type_name.equalsIgnoreCase("H") && _atom_isotope_number != 1 && _atom_isotope_number != 2)
				atom_isotope_number_name = "1";

			if (atom_type_name.equalsIgnoreCase("C") && _atom_isotope_number != 13)
				atom_isotope_number_name = "13";

			if (atom_type_name.equalsIgnoreCase("N") && _atom_isotope_number != 15 && _atom_isotope_number != 14)
				atom_isotope_number_name = "15";

			if (atom_type_name.equalsIgnoreCase("P") && _atom_isotope_number != 31)
				atom_isotope_number_name = "31";

			if (atom_type_name.equalsIgnoreCase("F") && _atom_isotope_number != 19)
				atom_isotope_number_name = "19";

			if (atom_type_name.equalsIgnoreCase("B") && _atom_isotope_number != 11 && _atom_isotope_number != 10)
				atom_isotope_number_name = "11";

			if (atom_type_name.equalsIgnoreCase("O") && _atom_isotope_number != 17)
				atom_isotope_number_name = "17";

			if (atom_type_name.equalsIgnoreCase("Na") && _atom_isotope_number != 23)
				atom_isotope_number_name = "23";

			if (atom_type_name.equalsIgnoreCase("Si") && _atom_isotope_number != 29)
				atom_isotope_number_name = "29";

			if (atom_type_name.equalsIgnoreCase("Cl") && _atom_isotope_number != 35)
				atom_isotope_number_name = "35";

			if (atom_type_name.equalsIgnoreCase("Cd") && _atom_isotope_number != 113 && _atom_isotope_number != 111)
				atom_isotope_number_name = "113";

			if (atom_type_name.equalsIgnoreCase("Xe") && _atom_isotope_number != 129)
				atom_isotope_number_name = "129";

			if (atom_type_name.equalsIgnoreCase("Pt") && _atom_isotope_number != 195)
				atom_isotope_number_name = "195";

		}

		atom_isotope_number = atom_isotope_number_name;

		if (indirect_shift_ratio_name != null) {

			if (indirect_shift_ratio_name.startsWith("-"))
				indirect_shift_ratio_name.replaceFirst("^-", "");

			if (indirect_shift_ratio_name.startsWith("."))
				indirect_shift_ratio_name.replaceFirst("^.", "0.");

		}

		indirect_shift_ratio = indirect_shift_ratio_name;

	}

	static final Map<String, String> map_atom_type = new HashMap<String, String>() {

		private static final long serialVersionUID = 35L;

		{

			put("C", "C");
			put("Cd", "Cd");
			put("113Cd", "Cd");
			put("2H", "H");
			put("P", "P");
			put("31P", "P");
			put("N", "N");
			put("1H", "H");
			put("15N", "N");
			put("H", "H");
			put("F", "F");
			put("111Cd", "Cd");
			put("13C", "C");

		}
	};

	public static String getAtomType(String val_name) {

		val_name = map_atom_type.get(val_name);

		if (val_name != null && val_name.equalsIgnoreCase("null"))
			return null;

		return val_name;
	}

	public void setAtomType(String val_name) {
		atom_type = val_name;
	}

	public void setAtomIsotopeNumber(String val_name) {
		atom_isotope_number = val_name;
	}
}
