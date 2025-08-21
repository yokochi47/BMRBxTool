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

public class bmr_Util_Entity {

	static final Map<String, String> map_type = new HashMap<String, String>() {

		private static final long serialVersionUID = 50L;

		{

			put("D-SACCHARIDE", "polymer");
			put("L-PEPTIDE LINKING", "polymer");
			put("RNA linking", "polymer");
			put("polymer", "polymer");
			put("water", "water");
			put("polyribonucleotide", "polymer");
			put("NON-POLYMER", "non-polymer");
			put("SACCHARIDE", "polymer");
			put("non-polymer", "non-polymer");
			put("D-saccharide", "polymer");

		}
	};

	public static String getType(String val_name) {
		return (String) map_type.get(val_name);
	}

	static final Map<String, String> map_polymer_common_type = new HashMap<String, String>() {

		private static final long serialVersionUID = 51L;

		{

			put("polysaccharide", "polysaccharide");
			put("DNA/RNA hybrid", "DNA/RNA hybrid");
			put("polypeptide(L)", "protein");
			put("RNA", "RNA");
			put("DNA", "DNA");
			put("protein", "protein");

		}
	};

	public static String getPolymerCommonType(String val_name) {
		return (String) map_polymer_common_type.get(val_name);
	}

	static final Map<String, String> map_polymer_type = new HashMap<String, String>() {

		private static final long serialVersionUID = 52L;

		{

			put("polydeoxyribonucleotide/polyribonucleotide hybrid", "polydeoxyribonucleotide/polyribonucleotide hybrid");
			put("polysaccharide(D or L - TODO)", "other");
			put("polysaccharide(D)", "polysaccharide(D)");
			put("polyribonucleotide", "polyribonucleotide");
			put("polysaccharide(L)", "polysaccharide(L)");
			put("other", "other");
			put("polydeoxyribonucleotide", "polydeoxyribonucleotide");
			put("cyclic-pseudo-peptide", "cyclic-pseudo-peptide");
			put("DNA/RNA hybrid", "polydeoxyribonucleotide/polyribonucleotide hybrid");
			put("polypeptide(D)", "polypeptide(D)");
			put("polypeptide(L)", "polypeptide(L)");

		}
	};

	public static String getPolymerType(String val_name) {
		return (String) map_polymer_type.get(val_name);
	}

	static final Map<String, String> map_thiol_state = new HashMap<String, String>() {

		private static final long serialVersionUID = 53L;

		{

			put("other bound and free", "free and other bound");
			put("other bound, and free", "free and other bound");
			put("all disuldide bound", "all disulfide bound");
			put("unknown", "unknown");
			put("disulfide bound and other bound", "disulfide and other bound");
			put("free and disulfide bound", "free and disulfide bound");
			put("all bound other", "all other bound");
			put("all disulfied bond", "all disulfide bound");
			put("one free and seven bound to zinc", "free and other bound");
			put("disulfide bound and free", "free and disulfide bound");
			put("bound to Zinc", "free and other bound");
			put("free, other bound", "free and other bound");
			put("fully oxidized", "all disulfide bound");
			put("disulfide bound", "free and disulfide bound");
			put("all bound to ZINC", "all other bound");
			put("unkown", "unknown");
			put("all metal-bound", "all other bound");
			put("No", "not present");
			put("not reported", "not reported");
			put("free and bound", "free and disulfide bound");
			put("all free", "all free");
			put("all disulfide bound", "all disulfide bound");
			put("Disulfide Bound", "free and disulfide bound");
			put("Three bound to Zn+2 and one free.", "free and other bound");
			put("other bound or free", "free and other bound");
			put("all free and other bound", "free and other bound");
			put("not applicable", "not present");
			put("disulfide bound and not reported", "free and disulfide bound");
			put("all disulfide bond", "all disulfide bound");
			put("free and other bound", "free and other bound");
			put("free disulfide and other bound", "free disulfide and other bound");
			put("disulfide bond", "free and disulfide bound");
			put("not presnet", "not present");
			put("nearly all free", "all free");
			put("fully reduced", "all free");
			put("all bound", "all disulfide bound");
			put("not available", "not available");
			put("disulfide and other bound", "disulfide and other bound");
			put("present", "not reported");
			put("all other bound", "all other bound");
			put("not present", "not present");
			put("all reduced", "all free");
			put("no", "not present");
			put("All free", "all free");
			put("none present", "not present");
			put("all other bond", "all other bound");

		}
	};

	public static String getThiolState(String val_name) {
		return (String) map_thiol_state.get(val_name);
	}

	static final Map<String, String> map_ambiguous_conformational_states = new HashMap<String, String>() {

		private static final long serialVersionUID = 54L;

		{

			put("yes", "yes");
			put("no", "no");
			put(",", "no");

		}
	};

	public static String getAmbiguousConformationalStates(String val_name) {
		return (String) map_ambiguous_conformational_states.get(val_name);
	}

	static final Map<String, String> map_ambiguous_chem_comp_sites = new HashMap<String, String>() {

		private static final long serialVersionUID = 55L;

		{

			put("N", "no");
			put("yes", "yes");
			put("no", "no");

		}
	};

	public static String getAmbiguousChemCompSites(String val_name) {
		return (String) map_ambiguous_chem_comp_sites.get(val_name);
	}

	static final Map<String, String> map_nstd_chirality = new HashMap<String, String>() {

		private static final long serialVersionUID = 56L;

		{

			put("N", "no");
			put("yes", "yes");
			put("no", "no");

		}
	};

	public static String getNstdChirality(String val_name) {
		return (String) map_nstd_chirality.get(val_name);
	}

	static final Map<String, String> map_nstd_linkage = new HashMap<String, String>() {

		private static final long serialVersionUID = 57L;

		{

			put("N", "no");
			put("yes", "yes");
			put("no", "no");

		}
	};

	public static String getNstdLinkage(String val_name) {
		return (String) map_nstd_linkage.get(val_name);
	}

	static final Map<String, String> map_paramagnetic = new HashMap<String, String>() {

		private static final long serialVersionUID = 58L;

		{

			put("No", "no");
			put("no?", "no");
			put("yes", "yes");
			put("no", "no");

		}
	};

	public static String getParamagnetic(String val_name) {
		return (String) map_paramagnetic.get(val_name);
	}

	public static String checkECNumber(String ec_number, String entry_id) {

		final String[][] ecnumtbl = {

				{"EC 1.5.1.3", "11492", "1.5.1.3"},
				{"2834", "15025", "3.6.1.3"},
				{"na", "15267", "na"},
				{"n/a", "15393", "na"},
				{"EC 2.7.1.112", "15488", "2.7.1.112"},
				{"none", "15551", "na"},
				{"2.7.7.7, 4.2.99.-", "15566", "2.7.7.7, 4.2.99.-"},
				{"EC 3.1.4.35", "15734", "3.1.4.35"},
				{"EC 3.4.21.-", "15806", "3.4.21.-"},
				{"EC 2.7.11.30", "15956", "2.7.11.30"},
				{"PA3407", "15962", "na"},
				{"PA3407", "15963", "na"},
				{"EC 5.3.4.1", "15974", "5.3.4.1"},
				{"EC 5.3.4.1", "15998", "5.3.4.1"},
				{"EC 1.8.4.2", "16329", "1.8.4.2"},
				{"EC 1.8.4.2", "16330", "1.8.4.2"},
				{"EC 3.2.1.39", "16481", "3.2.1.39"},
				{"Hydrolase", "16550", "na"},
				{"EC: 5.2.1.8", "16690", "5.2.1.8"},
				{"EC 3.4.24.83", "16735", "3.4.24.83"},
				{"EC 2.7.7.7", "16869", "2.7.7.7"},
				{"EC: 2. 7. 11. 1", "16970", "2.7.11.1"},
				{"EC 2.7.7.7", "17201", "2.7.7.7"},
				{"N.A.", "17206", "na"},
				{"3.A.1.109", "17403", "3.4.1.109"},
				{"EC 6.3.4.1", "17935", "6.3.4.1"},
				{"E 2.4.1.119", "18477", "2.4.1.119"},
				{"E.C. 3.1.1.3", "18574", "3.1.1.3"},
				{"E.C. 3.1.1.3", "18575", "3.1.1.3"},
				{"EC 3.5.2.6", "19047", "3.5.2.6"},
				{"EC 3.5.2.6", "19048", "3.5.2.6"},
				{"na", "20004", "na"},
				{"na", "20005", "na"},
				{"3.1.27.10'=", "4158", "3.1.27.10"},
				{"EC: 1.14.99.3", "18798", "1.14.99.3"},
				{"EC: 1.14.99.3", "18799", "1.14.99.3"},
				{"EC: 1.14.99.3", "18800", "1.14.99.3"},
				{"ENZYME class: 6.3.2 by Uniprot", "25248", "6.3.2.-"},
				{"ENZYME class: 6.3.2 by Uniprot", "25249", "6.3.2.-"},
				{"EC 2.7.13.3", "25278", "2.7.13.3"},
				{"Enzyme class: 6.3.2 by Uniprot", "25351", "6.3.2.-"},
				{"EC 2.1.1.6", "26848", "2.1.1.6"},
				{"EC 2.1.1.6", "26851", "2.1.1.6"},
				{"not available", "26986", "na"},
				{"EC 2.7.2.3", "27022", "2.7.2.3"},
				{"E.C. 4.1.2.4", "27048", "4.1.2.4"},
				{"EC 3.6.1.7", "27137", "3.6.1.7"},
				{"EC 3.6.1.7", "27138", "3.6.1.7"},
				{"EC 3.6.1.7", "27139", "3.6.1.7"},
				{"EC 5.4.2.6", "27174", "5.4.2.6"},
				{"EC 5.4.2.6", "27175", "5.4.2.6"},
				{"EC 2.1.1", "27417", "2.1.1.-"},
				{"EC 3.1.3.48", "27645", "3.1.3.48"},
				{"EC 3.6.4.12", "27780", "3.6.4.12"},
				{"EC 3.6.4.12", "27785", "3.6.4.12"},
				{"3.4.19.12, 3.4.22.69", "30247", "3.4.19.12, 3.4.22.69"},
				{"N.A.", "26802", "na"},
				{"17773.4", "26588", "na"},
				{"2.4.1.129,3.4.16.4", "34255", "2.4.1.129, 3.4.16.4"},
				{"n.a.", "27235", "na"},
				{"P02511", "26640", "na"},
				{"2.1.1.-,2.7.7.-,3.1.3.33,3.4.22.-,3.6.1.15,3.6.4.13,2.7.7.48", "30043", "2.1.1.-, 2.7.7.-, 3.1.3.33, 3.4.22.-, 3.6.1.15, 3.6.4.13, 2.7.7.48"},
				{"2.3.1.48,2.3.1.-", "34233", "2.3.1.48, 2.3.1.-"},
				{"6.3.2.n3", "27381", "6.3.2.-"},
				{"none", "25197", "na"},
				{"N/A", "18870", "na"},
				{"EC:2.3.2.26", "27477", "2.3.2.26"},
				{"2.4.1.129,3.4.16.4", "34246", "2.4.1.129, 3.4.16.4"},
				{"2.3.1.48,2.3.1.-", "34231", "2.3.1.48, 2.3.1.-"},
				{"3.4.22.-,3.4.21.98", "30037", "3.4.22.-, 3.4.21.98"},
				{"3.6.4.-,6.3.2.-", "30098", "3.6.4.-, 6.3.2.-"},

		};

		for (int i = 0; i < ecnumtbl.length; i++) {

			if (ecnumtbl[i][0].equals(ec_number) && ecnumtbl[i][1].equals(entry_id))
				return ecnumtbl[i][2];

			if (ecnumtbl[i][0].isEmpty() && (ec_number == null || ec_number.isEmpty() || ec_number.equals(".") || ec_number.equals("?")) && ecnumtbl[i][1].equals(entry_id))
				return ecnumtbl[i][2];

		}

		if (ec_number.toLowerCase().startsWith("n") || !ec_number.contains("."))
			return "na";

		if (!ec_number.contains(" ") && ec_number.toLowerCase().startsWith("ec:"))
			return ec_number.substring(3);

		if (ec_number.contains(","))
			return ec_number.replaceAll(" ", "").replaceAll(",", ", ");

		return ec_number;
	}
}
