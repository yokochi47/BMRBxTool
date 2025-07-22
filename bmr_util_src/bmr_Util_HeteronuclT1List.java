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

public class bmr_Util_HeteronuclT1List {

	static final Map<String, String> map_t1_value_units = new HashMap<String, String>() {

		private static final long serialVersionUID = 83L;

		{

			put("ms-1", "ms-1");
			put("ms", "ms");
			put("s", "s");
			put("s-1", "s-1");
			put("Hz", "s-1");

		}
	};

	public static String getT1ValUnits(String val_name) {
		return (String) map_t1_value_units.get(val_name);
	}

	static final Map<String, String> map_t1_coherence_type = new HashMap<String, String>() {

		private static final long serialVersionUID = 84L;

		{

			put("Dz", "Iz");
			put("single quantum", "Sz");
			put("Iz", "Iz");
			put("Cz", "Sz");
			put("na", "na");
			put("Nz", "Sz");
			put("N15", "Sz");
			put("Hz", "Iz");
			put("NzHz", "Sz");
			put("1H", "Iz");
			put("Sz", "Sz");
			put("15N", "Sz");

		}
	};

	public static String getT1CoherenceType(String val_name, String entry_id) {
		return (String) map_t1_coherence_type.get(val_name);
	}
}
