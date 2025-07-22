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

public class bmr_Util_HeteronuclT1RhoList {

	static final Map<String, String> map_rex_units = new HashMap<String, String>() {

		private static final long serialVersionUID = 90L;

		{

			put("ms-1", "ms-1");
			put("ms", "ms-1");
			put("s-1", "s-1");
			put("us-1", "us-1");

		}
	};

	public static String getRexUnits(String val_name) {
		return (String) map_rex_units.get(val_name);
	}

	static final Map<String, String> map_t1rho_coherence_type = new HashMap<String, String>() {

		private static final long serialVersionUID = 91L;

		{

			put("Nz", "S(+,-)");
			put("S(+,-)", "S(+,-)");
			put("Nx", "S(+,-)");
			put("I(+,-)", "I(+,-)");

		}
	};

	public static String getT1RhoCoherenceType(String val_name) {
		return (String) map_t1rho_coherence_type.get(val_name);
	}

	static final Map<String, String> map_temp_calibration_method = new HashMap<String, String>() {

		private static final long serialVersionUID = 92L;

		{

			put("na", "no calibration applied");
			put("methanol", "methanol");
			put("monoethylene glycol", "monoethylene glycol");
			put("no calibration applied", "no calibration applied");

		}
	};

	public static String getTempCalibrationMethod(String val_name) {
		return (String) map_temp_calibration_method.get(val_name);
	}

	static final Map<String, String> map_temp_control_method = new HashMap<String, String>() {

		private static final long serialVersionUID = 93L;

		{

			put("no temperature control applied", "no temperature control applied");
			put("temperature compensation block", "temperature compensation block");
			put("None", "no temperature control applied");
			put("single scan interleaving", "single scan interleaving");

		}
	};

	public static String getTempControlMethod(String val_name) {
		return (String) map_temp_control_method.get(val_name);
	}
}
