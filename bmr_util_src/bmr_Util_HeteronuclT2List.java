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

public class bmr_Util_HeteronuclT2List {

	static final Map<String, String> map_t2_value_units = new HashMap<String, String>() {

		private static final long serialVersionUID = 85L;

		{

			put("ms-1", "ms-1");
			put("ms", "ms");
			put("s", "s");
			put("s-1", "s-1");
			put("Hz", "s-1");

		}
	};

	public static String getT2ValUnits(String val_name) {
		return (String) map_t2_value_units.get(val_name);
	}

	static final Map<String, String> map_t2_coherence_type = new HashMap<String, String>() {

		private static final long serialVersionUID = 86L;

		{

			put("Dz", "I(+,-)");
			put("single quantum", "S(+,-)");
			put("Dy", "I(+,-)");
			put("Dx", "I(+,-)");
			put("cpmg", "S(+,-)");
			put("Nx,y", "S(+,-)");
			put("na", "na");
			put("Nz", "S(+,-)");
			put("SQ", "S(+,-)");
			put("N15", "S(+,-)");
			put("Ny", "S(+,-)");
			put("I(+,-)", "I(+,-)");
			put("Nx", "S(+,-)");
			put("NzHz", "S(+,-)");
			put("15N", "S(+,-)");
			put("S(+,-)", "S(+,-)");
			put("Nxy", "S(+,-)");

		}
	};

	public static String getT2CoherenceType(String val_name, String entry_id) {
		return (String) map_t2_coherence_type.get(val_name);
	}

	static final Map<String, String> map_rex_units = new HashMap<String, String>() {

		private static final long serialVersionUID = 87L;

		{

			put("ms-1", "ms-1");
			put("1/s", "s-1");
			put("ms", "ms-1");
			put("none", "s-1");
			put("s", "s-1");
			put("s-1", "s-1");
			put("us-1", "us-1");

		}
	};

	public static String getRexUnits(String val_name) {
		return (String) map_rex_units.get(val_name);
	}

	static final Map<String, String> map_temp_calibration_method = new HashMap<String, String>() {

		private static final long serialVersionUID = 88L;

		{

			put("unknown", "no calibration applied");
			put("DSS", "no calibration applied");
			put("n/a", "no calibration applied");
			put("ethylene glycol reference sample", "monoethylene glycol");
			put("no calibration applied", "no calibration applied");
			put("thermocouple", "no calibration applied");
			put("monoethylene glycol", "monoethylene glycol");
			put("ethylene glycol", "monoethylene glycol");
			put("TSP", "no calibration applied");
			put("methanol", "methanol");

		}
	};

	public static String getTempCalibrationMethod(String val_name) {
		return (String) map_temp_calibration_method.get(val_name);
	}

	static final Map<String, String> map_temp_control_method = new HashMap<String, String>() {

		private static final long serialVersionUID = 89L;

		{

			put("no temperature control applied", "no temperature control applied");
			put("temperature compensation block", "temperature compensation block");
			put("single scan interleaving", "single scan interleaving");
			put("not reported", "no temperature control applied");

		}
	};

	public static String getTempControlMethod(String val_name) {
		return (String) map_temp_control_method.get(val_name);
	}
}
