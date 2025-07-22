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

public class bmr_Util_SampleConditionVariable {

	static final Map<String, String> map_type = new HashMap<String, String>() {

		private static final long serialVersionUID = 115L;

		{

			put("pD", "pD");
			put("pressure", "pressure");
			put("temperature controller setting", "temperature controller setting");
			put("viscosity", "viscosity");
			put("pH*", "pH*");
			put("ionic strength", "ionic strength");
			put("na", "na");
			put("Time", "na");
			put("temperature", "temperature");
			put("solvent exchange time", "na");
			put("ionic_strength", "ionic strength");
			put("dielectric constant", "dielectric constant");
			put("1", "temperature");
			put("pH", "pH");

		}
	};

	public static String getType(String val_name) {
		return (String) map_type.get(val_name);
	}

	static final Map<String, String> map_val_units = new HashMap<String, String>() {

		private static final long serialVersionUID = 116L;

		{

			put("Not defined", "Not defined");
			put("mbar", "mbar");
			put("none", "Not defined");
			put("Pa", "Pa");
			put("%", "%");
			put("Torr", "Torr");
			put("P", "P");
			put("M", "M");
			put("mEq", "mM");
			put("na", "Not defined");
			put("K", "K");
			put("n.a", "Not defined");
			put("H", "K");
			put("mM", "mM");
			put("m^2/s", "m^2/s");
			put("mm Hg", "mmHg");
			put("s", "s");
			put("K.", "K");
			put("C", "C");
			put("pH", "pH");
			put("mu", "M");
			put("pD", "pD");
			put("hPa", "hPa");
			put("k", "K");
			put("N/A", "Not defined");
			put("Pa.s", "Pa.s");
			put("bar", "bar");
			put("atm", "atm");
			put("mmHg", "mmHg");
			put("298", "Not defined");
			put("ATM", "atm");
			put("1", "Not defined");
			put("cP", "cP");

		}
	};

	public static String getValUnits(String val_name) {
		return (String) map_val_units.get(val_name);
	}
}
