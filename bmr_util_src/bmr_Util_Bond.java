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

public class bmr_Util_Bond {

	static final Map<String, String> map_type = new HashMap<String, String>() {

		private static final long serialVersionUID = 11L;

		{

			put("iron-sulfur", "metal coordination");
			put("Hydrogen bond", "hydrogen");
			put("cis peptide", "peptide");
			put("coordinate covalent", "metal coordination");
			put("amine", "covalent");
			put("Octahedral coordination", "metal coordination");
			put("thioester", "thioester");
			put("dilsulfide", "disulfide");
			put("oxime", "oxime");
			put("phosphoester", "phosphoester");
			put("glycosidic", "covalent");
			put("diselenide", "diselenide");
			put("inter-chain-disulfide", "disulfide");
			put("ligand coordination", "metal coordination");
			put("thio ether", "thioether");
			put("coordination Nitrogen-FE", "metal coordination");
			put("metal coordination", "metal coordination");
			put("thiol ether", "thioether");
			put("phosphodiester", "phosphodiester");
			put("metal coordination bonds", "metal coordination");
			put("metal-coordination", "metal coordination");
			put("Disulfide", "disulfide");
			put("amide", "amide");
			put("hydrogen", "hydrogen");
			put("not reported", "not reported");
			put("corrdination", "metal coordination");
			put("all disulfide bound", "disulfide");
			put("directed", "directed");
			put("peptide", "peptide");
			put("lactam", "covalent");
			put("Coordination", "metal coordination");
			put("coordinate-covalent", "metal coordination");
			put("desulfide", "disulfide");
			put("coordinative", "metal coordination");
			put("C-S", "covalent");
			put("coordinate", "metal coordination");
			put("disulphide", "disulfide");
			put("C-N", "covalent");
			put("most likely an ester bond", "ester");
			put("ether", "ether");
			put("glycosylic", "covalent");
			put("isoaspartyl-glycine", "covalent");
			put("Octahedral Coordination", "metal coordination");
			put("C-C", "covalent");
			put("thioether", "thioether");
			put("thio-ether", "thioether");
			put("covalent", "covalent");
			put("metal ligand", "metal coordination");
			put("metal-ligand", "metal coordination");
			put("coodination", "metal coordination");
			put("disulfide", "disulfide");
			put("thioesther", "thioester");
			put("ester", "ester");
			put("metal coordinate", "metal coordination");

		}
	};

	public static String getType(String val_name) {
		return (String) map_type.get(val_name);
	}

	static final Map<String, String> map_value_order = new HashMap<String, String>() {

		private static final long serialVersionUID = 12L;

		{

			put("doub", "doub");
			put("directed", "directed");
			put("delo", "delo");
			put("poly", "poly");
			put("singel", "sing");
			put("DOUB", "doub");
			put("SINGLE", "sing");
			put("coordinative", "sing");
			put("quad", "quad");
			put("sing", "sing");
			put("trip", "trip");
			put("SING", "sing");
			put("Single", "sing");
			put("coordinate", "sing");
			put("single", "sing");
			put("pi", "pi");
			put("1", "sing");
			put("double", "doub");
			put("arom", "arom");

		}
	};

	public static String getValueOrder(String val_name, String entry_id) {

		if ((val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && entry_id.equals("19312"))
			return "sing";

		return (String) map_value_order.get(val_name);
	}

	public static String getHECAtomID2(String val_name, String comp_id_1, String atom_id_1, String comp_id_2) {

		if (comp_id_1 != null && atom_id_1 != null && comp_id_2 != null && comp_id_1.equalsIgnoreCase("CYS") && atom_id_1.equalsIgnoreCase("SG") && comp_id_2.equalsIgnoreCase("HEC") && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")))
			return "FE";

		return val_name;
	}
}
