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

public class bmr_Util_CouplingConstatnList {

	static final Map<String, String> map = new HashMap<String, String>() {

		private static final long serialVersionUID = 47L;

		{

			put("800.13", "800");
			put("500.13", "500");
			put("700", "700");
			put("600.21", "600");
			put("800", "800");
			put("750", "750");
			put("600.13", "600");
			put("400", "400");
			put("900", "900");
			put("850", "850");
			put("600.153", "600");
			put("500", "500");
			put("950", "950");
			put("1000", "1000");
			put("500.1323", "500");
			put("920", "920");
			put("600", "600");

		}
	};

	public static String getSpectrometerFrequency1H(String val_name) {
		return (String) map.get(val_name);
	}
}
