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

public class bmr_Util_AssemblyInteraction {

	static final Map<String, String> map = new HashMap<String, String>() {

		private static final long serialVersionUID = 6L;

		{

			put("Protein-protein interaction with slow exchange", "Slow exchange");
			put("protein-protein interaction", "Slow exchange");
			put("Extremely slow exchange", "Extremely slow exchange");
			put("hydrophobic interaction", "Slow exchange");
			put("No interaction", "No interaction");
			put("Slow exchange", "Slow exchange");
			put("protein-protein", "Intermediate exchange");
			put("Intermediate exchange", "Intermediate exchange");
			put("protein dimer", "Fast exchange");
			put("Fast exchange", "Fast exchange");

		}
	};

	public static String getMolInteractionType(String val_name) {
		return (String) map.get(val_name);
	}
}
