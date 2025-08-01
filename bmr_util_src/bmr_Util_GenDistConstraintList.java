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

public class bmr_Util_GenDistConstraintList {

	static final Map<String, String> map = new HashMap<String, String>() {

		private static final long serialVersionUID = 97L;

		{

			put("hydrogen bond", "hydrogen bond");
			put("NOE build-up", "NOE build-up");
			put("ROE", "ROE");
			put("paramagnetic relaxation", "paramagnetic relaxation");
			put("NOE", "NOE");
			put("symmetry", "symmetry");
			put("ROE build-up", "ROE build-up");
			put("general distance", "general distance");
			put("disulfide bond", "disulfide bond");
			put("PRE", "paramagnetic relaxation");
			put("NOE not seen", "NOE not seen");

		}
	};

	public static String getConstraintType(String val_name) {
		return (String) map.get(val_name);
	}
}
