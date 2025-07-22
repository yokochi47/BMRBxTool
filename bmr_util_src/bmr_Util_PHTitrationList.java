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

public class bmr_Util_PHTitrationList {

	static final Map<String, String> map_expt_observed_param = new HashMap<String, String>() {

		private static final long serialVersionUID = 109L;

		{

			put("coupling constant", "coupling constant");
			put("peak integral", "peak integral");
			put("chemcial shifts", "chemical shift");
			put("chemical shift", "chemical shift");
			put("chemical shifts", "chemical shift");
			put("peak height", "peak height");

		}
	};

	public static String getExptObservedParam(String val_name) {
		return (String) map_expt_observed_param.get(val_name);
	}
}
