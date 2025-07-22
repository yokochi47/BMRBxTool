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

public class bmr_Util_ChemCompBond {

	static final Map<String, String> map_aromatic_flag = new HashMap<String, String>() {

		private static final long serialVersionUID = 18L;

		{

			put("N", "no");
			put("Y", "yes");
			put("yes", "yes");
			put("no", "no");

		}
	};

	public static String getAromaticFlag(String val_name) {
		return (String) map_aromatic_flag.get(val_name);
	}

	static final Map<String, String> map_type = new HashMap<String, String>() {

		private static final long serialVersionUID = 19L;

		{

			put("hydrogen", "hydrogen");
			put("directed", "directed");
			put("metal coordination", "metal coordination");
			put("peptide", "peptide");
			put("ester", "ester");
			put("disulfide", "disulfide");
			put("ether", "ether");
			put("covalent", "covalent");
			put("amide", "amide");
			put("1", "covalent");
			put("thioether", "thioether");

		}
	};

	public static String getType(String val_name) {
		return (String) map_type.get(val_name);
	}

	static final Map<String, String> map_value_order = new HashMap<String, String>() {

		private static final long serialVersionUID = 20L;

		{

			put("AROM", "AROM");
			put("TRIP", "TRIP");
			put("sing", "SING");
			put("doub", "DOUB");
			put("trip", "TRIP");
			put("DIRECTED", "DIRECTED");
			put("DELO", "DELO");
			put("QUAD", "QUAD");
			put("PI", "PI");
			put("SING", "SING");
			put("POLY", "POLY");
			put("DOUB", "DOUB");

		}
	};

	public static String getValueOrder(String val_name) {
		return (String) map_value_order.get(val_name);
	}

	static final Map<String, String> map_stereo_config = new HashMap<String, String>() {

		private static final long serialVersionUID = 21L;

		{

			put("E", "E");
			put("Z", "Z");
			put("N", "N");
			put("no", "N");

		}
	};

	public static String getStereoConfig(String val_name) {
		return (String) map_stereo_config.get(val_name);
	}
}
