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

public class bmr_Util_ConformerFamilyRefinement {

	static final Map<String, String> map = new HashMap<String, String>() {

		private static final long serialVersionUID = 41L;

		{

			put("minimization", "na");
			put("matrix relaxation", "matrix relaxation");
			put("DGSA-distance geometry simulated annealing", "DGSA-distance geometry simulated annealing");
			put("TORSION ANGLE DYNAMICS", "torsion angle dynamics");
			put("molecular dynamics", "molecular dynamics");
			put("simulated annealing", "simulated annealing");
			put("na", "na");
			put("simulated_annealing", "simulated annealing");
			put("distance geometry", "distance geometry");
			put("torsion angle dynamics", "torsion angle dynamics");
			put("refer to application for detail", "na");
			put("Energy minimization", "na");
			put("not reported", "na");
			put("STRUCTURES WERE CALCULATED BY TORSION ANGLE DYNAMICS AND SIMULATED ANNEALING", "torsion angle dynamics");
			put("1", "na");
			put("distance geometry and simulated annealing", "DGSA-distance geometry simulated annealing");
			put("energy minimization", "na");

		}
	};

	public static String getRefineMethod(String val_name) {
		return (String) map.get(val_name);
	}
}
