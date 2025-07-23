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

public class bmr_Util_SystematicChemShiftOffset {

	static final Map<String, String> map_type = new HashMap<String, String>() {

		private static final long serialVersionUID = 124L;

		{

			put("TROSY offset", "TROSY offset");
			put("others", "na");
			put("water saturation", "na");
			put("Water", "na");
			put("2H isotope effect", "2H isotope effect");
			put("spectrometer error", "spectrometer error");
			put("water", "na");
			put("na", "na");
			put("15N isotope effect", "15N isotope effect");
			put("2H isotope shift", "2H isotope effect");
			put("SAIL isotope labeling", "SAIL isotope labeling");
			put("13C isotope effect", "13C isotope effect");
			put("TROSY effect", "TROSY offset");
			put("corrected", "na");
			put("13C", "13C isotope effect");

		}
	};

	public static String getType(String val_name) {
		return (String) map_type.get(val_name);
	}

	static final Map<String, String> map_atom_type = new HashMap<String, String>() {

		private static final long serialVersionUID = 125L;

		{

			put("13C", "all 13C");
			put("all HN", "amide protons");
			put("1H", "all 1H");
			put("all nitrogens ", "all nitrogens");
			put("N", "all 15N");
			put("backbone CA/CB", "backbone CA");
			put("na", "na");
			put("all 1H", "all 1H");
			put("all 19F", "all 19F");
			put("all 13C", "all 13C");
			put("backbone CB", "backbone CB");
			put("H", "all 1H");
			put("backbone CA", "backbone CA");
			put("15N", "all 15N");
			put("all 31P", "all 31P");
			put("all nitrogens", "all nitrogens");
			put("all nitrogen bonded protons", "all nitrogen bonded protons");
			put("amide nitrogens", "amide nitrogens");
			put("15N, HN", "amide protons");
			put("all 15N", "all 15N");
			put("amide protons", "amide protons");

		}
	};

	public static String getAtomType(String val_name) {
		return (String) map_atom_type.get(val_name);
	}
}
