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

public class bmr_Util_NMRSpecExpt {

	static final Map<String, String> map_type = new HashMap<String, String>() {

		private static final long serialVersionUID = 105L;

		{

			put("NOE proton proton experiment", "NOE");
			put("NOE", "NOE");
			put("COSY", "COSY");

		}
	};

	public static String getType(String val_name) {
		return (String) map_type.get(val_name);
	}

	static final Map<String, String> map_nmr_tube_type = new HashMap<String, String>() {

		private static final long serialVersionUID = 106L;

		{

			put("cylindrical microcell", "cylindrical microcell");
			put("Shigemi", "Shigemi");
			put("4mm MAS rotor", "cylindrical microcell");
			put("3.2 mm MAS rotor", "cylindrical microcell");
			put("standard cylindrical", "standard cylindrical");
			put("7mm MAS rotor", "cylindrical microcell");
			put("5mm MAS rotor", "cylindrical microcell");
			put("spherical microcell", "spherical microcell");
			put("3.2mm MAS rotor", "cylindrical microcell");
			put("2.5mm MAS rotor", "cylindrical microcell");

		}
	};

	public static String getNMRTubeType(String val_name) {
		return (String) map_nmr_tube_type.get(val_name);
	}
}
