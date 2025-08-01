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

public class bmr_Util_EntryAuthor {

	static final Map<String, String> map = new HashMap<String, String>() {

		private static final long serialVersionUID = 79L;

		{

			put("IV", "IV");
			put("D.", "null");
			put("V", "V");
			put("J.", "Jr.");
			put("M.", "null");
			put("II", "II");
			put("Sanchez", "null");
			put("I", "I");
			put("Dr.", "null");
			put("Prof.", "null");
			put("PhLic", "null");
			put("III", "III");
			put("Sr.", "Sr.");
			put("Jr.", "Jr.");
			put("Jr", "Jr.");
			put("Ms", "null");
			put("Mr", "null");
			put("COLL", "null");
			put("Mrs", "null");

		}
	};

	public static String getFamilyTitle(String val_name) {

		val_name = map.get(val_name);

		if (val_name != null && val_name.equalsIgnoreCase("null"))
			return null;

		return val_name;
	}
}
