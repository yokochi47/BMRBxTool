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

public class bmr_Util_SpectralDim {

	static final Map<String, String> map_under_sampling_type = new HashMap<String, String>() {

		private static final long serialVersionUID = 120L;

		{

			put("fold", "folded");
			put("aliased", "aliased");
			put("not observed", "not observed");
			put("folded", "folded");

		}
	};

	public static String getUnderSamplingType(String val_name) {
		return (String) map_under_sampling_type.get(val_name);
	}
}
