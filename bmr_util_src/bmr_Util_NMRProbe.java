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

public class bmr_Util_NMRProbe {

	static final Map<String, String> map = new HashMap<String, String>() {

		private static final long serialVersionUID = 99L;

		{

			put("transmission line", "transmission line");
			put("not available", "na");
			put("triple resonance", "triple resonance");
			put("liquid", "na");
			put("cryoprobe", "cryogenically cooled");
			put("Cryoprobe", "cryogenically cooled");
			put("inverse coil", "inverse coil");
			put("double resonance", "double resonance");
			put("na", "na");
			put("MAS", "na");
			put("room temperature", "room temperature");
			put("saddle coil", "saddle coil");
			put("home built", "home built");
			put("solenoid coil", "solenoid coil");
			put("cryogenically cooled", "cryogenically cooled");

		}
	};

	public static String getType(String val_name) {
		return (String) map.get(val_name);
	}
}
