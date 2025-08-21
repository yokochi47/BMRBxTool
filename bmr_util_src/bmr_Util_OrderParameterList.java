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

public class bmr_Util_OrderParameterList {

	static final Map<String, String> map_tau_e_val_units = new HashMap<String, String>() {

		private static final long serialVersionUID = 107L;

		{

			put("ms", "ms");
			put("ns", "ns");
			put("s", "s");
			put("us", "us");
			put("E-10 s", "ns");
			put("ps", "ps");

		}
	};

	public static String getTauEValUnits(String val_name) {
		return (String) map_tau_e_val_units.get(val_name);
	}

	static final Map<String, String> map_rex_val_units = new HashMap<String, String>() {

		private static final long serialVersionUID = 108L;

		{

			put("ms-1", "ms-1");
			put("s", "s-1");
			put("s-1", "s-1");
			put("us-1", "us-1");

		}
	};

	public static String getRexValUnits(String val_name) {
		return (String) map_rex_val_units.get(val_name);
	}
}
