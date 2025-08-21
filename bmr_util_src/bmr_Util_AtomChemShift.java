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

public class bmr_Util_AtomChemShift {

	static final Map<String, String> map = new HashMap<String, String>() {

		private static final long serialVersionUID = 9L;

		{

			put("1@", "1");
			put("n", "1");
			put("9", "9");
			put("73", "2");
			put("6", "6");
			put("5", "5");
			put("4", "4");
			put("3", "3");
			put(".3", "null");
			put("11", "1");
			put("2", "2");
			put(".1", "1");
			put("1", "1");
			put("0", "1");
			put("1.", "1");

		}
	};

	public static String getAmbiguityCode(String val_name, String entry_id) {

		if (entry_id.equals("18205"))
			return null;

		val_name = map.get(val_name);

		if (val_name != null && val_name.equalsIgnoreCase("null"))
			return null;

		return val_name;
	}
}
