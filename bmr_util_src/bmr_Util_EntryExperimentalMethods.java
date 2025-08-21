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

public class bmr_Util_EntryExperimentalMethods {

	static final Map<String, String> map_method = new HashMap<String, String>() {

		private static final long serialVersionUID = 80L;

		{

			put("WAXS", "WAXS");
			put("SAXS", "SAXS");
			put("MD", "MD");
			put("X-ray", "X-ray");
			put("MS", "MS");
			put("NMR", "NMR");
			put("EMR", "EMR");
			put("Theoretical", "Theoretical");
			put("FRET", "FRET");
			put("SOLUTION NMR", "NMR");

		}
	};

	public static String getMethod(String val_name) {

		val_name = map_method.get(val_name);

		if (val_name != null && val_name.equalsIgnoreCase("null"))
			return null;

		return val_name;
	}

	static final Map<String, String> map_subtype = new HashMap<String, String>() {

		private static final long serialVersionUID = 81L;

		{

			put("SOLUTION NMR", "solution");
			put("solution", "solution");
			put("SOLID-STATE NMR", "solid-state");
			put("solid-state", "solid-state");

		}
	};

	public static String getSubtype(String val_name) {

		val_name = map_subtype.get(val_name);

		if (val_name != null && val_name.equalsIgnoreCase("null"))
			return null;

		return val_name;
	}
}
