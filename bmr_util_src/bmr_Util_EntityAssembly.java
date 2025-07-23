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

public class bmr_Util_EntityAssembly {

	static final Map<String, String> map_physical_state = new HashMap<String, String>() {

		private static final long serialVersionUID = 59L;

		{

			put("bound", "native");
			put("solid state", "native");
			put("trans", "native");
			put("not naturally occurring", "na");
			put("microcrystalline", "native");
			put("natively unstructured", "intrinsically disordered");
			put("na", "na");
			put("molten globule", "molten globule");
			put("not applicable", "na");
			put("folded in the model system", "native");
			put("SLAS micelle-bound", "native");
			put("recombinant", "na");
			put("unfolded", "unfolded");
			put("metal-substituted", "native");
			put("Other", "na");
			put("intrinsically disordered", "intrinsically disordered");
			put("denatured", "denatured");
			put("partially unfolded", "partially disordered");
			put("folded", "native");
			put("reduced", "native");
			put("Native", "native");
			put("non-polymer", "na");
			put("native", "native");
			put("de novo designed", "na");
			put("folding intermediate", "partially disordered");
			put("N/A", "na");
			put("intermediate", "partially disordered");
			put("partially disordered", "partially disordered");
			put("n.a.", "na");
			put("yes", "na");
			put("not available", "na");

		}
	};

	public static String getPhysicalState(String val_name) {
		return (String) map_physical_state.get(val_name);
	}

	static final Map<String, String> map_conformational_isomer = new HashMap<String, String>() {

		private static final long serialVersionUID = 60L;

		{

			put("native", "no");
			put("yes", "yes");
			put("no", "no");

		}
	};

	public static String getConformationalIsomer(String val_name) {
		return (String) map_conformational_isomer.get(val_name);
	}
}
