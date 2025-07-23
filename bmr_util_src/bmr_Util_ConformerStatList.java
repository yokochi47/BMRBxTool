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

public class bmr_Util_ConformerStatList {

	static final Map<String, String> map_conformer_selection_criteria = new HashMap<String, String>() {

		private static final long serialVersionUID = 42L;

		{

			put("structures with the lowest energy and the least restraint violations", "structures with the lowest energy");
			put("10 energetically equidistant across 60 lowest energy", "structures with the lowest energy");
			put("closest to the average structure", "na");
			put("structures with acceptable covalent geometry", "structures with acceptable covalent geometry");
			put("STRUCTURES WITH THE LEAST RESTRAINT VIOLATIONS, STRUCTURES WITH THE LOWEST ENERGY", "structures with the lowest energy");
			put("structures with favorable non-bond energy", "structures with favorable non-bond energy");
			put("target function", "target function");
			put("na", "na");
			put("structures with the lowest energy", "structures with the lowest energy");
			put("structures with the least restraint violations", "structures with the least restraint violations");
			put("lowest_energy", "structures with the lowest energy");
			put("all calculated structures submitted", "all calculated structures submitted");
			put("minimized average structure", "na");
			put("back calculated data agree with experimental NOESY spectrum", "back calculated data agree with experimental NOESY spectrum");

		}
	};

	public static String getConformerSelectionCriteria(String val_name) {
		return (String) map_conformer_selection_criteria.get(val_name);
	}

	static final Map<String, String> map_rep_conformer_selection_criteria = new HashMap<String, String>() {

		private static final long serialVersionUID = 43L;

		{

			put("na", "na");
			put("lowest_energy", "lowest energy");
			put("closest to the average", "closest to the average");
			put("minimized average structure", "minimized average structure");
			put("randomly chosen", "na");
			put("fewest violations", "fewest violations");
			put("lowest energy", "lowest energy");

		}
	};

	public static String getRepConformerSelectionCriteria(String val_name) {
		return (String) map_rep_conformer_selection_criteria.get(val_name);
	}
}
