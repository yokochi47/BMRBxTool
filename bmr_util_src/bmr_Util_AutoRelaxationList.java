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

public class bmr_Util_AutoRelaxationList {

	static final Map<String, String> map_temp_control_method = new HashMap<String, String>() {

		private static final long serialVersionUID = 10L;

		{

			put("no temperature control applied", "no temperature control applied");
			put("temperature compensation block", "temperature compensation block");
			put("single scan interleaving", "single scan interleaving");
			put("single fid interleaving", "single scan interleaving");
			put("single scan interleaving and temperature compensation block", "single scan interleaving and temperature compensation block");

		}
	};

	public static String getTempControlMethod(String val_name) {
		return (String) map_temp_control_method.get(val_name);
	}
}
