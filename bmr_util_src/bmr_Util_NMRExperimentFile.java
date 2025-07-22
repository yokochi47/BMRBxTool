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

public class bmr_Util_NMRExperimentFile {

	static final Map<String, String> map = new HashMap<String, String>() {

		private static final long serialVersionUID = 98L;

		{

			put("Raw Spectral Data", "Time-domain (raw spectral data)");
			put("Pulse sequence", "Pulse sequence");
			put("aquisition parameters", "Acquisition parameters");
			put("NMR Experiment Directory", "NMR experiment directory");
			put("Pulse sequence image file", "Pulse sequence image file");
			put("peak list", "Peak list");
			put("Peak list", "Peak list");
			put("Acquisition parameters", "Acquisition parameters");
			put("Spectral image", "Spectral image");
			put("acquisition parameters", "Acquisition parameters");
			put("raw spectral data", "Time-domain (raw spectral data)");
			put("Time-domain (raw spectral data)", "Time-domain (raw spectral data)");
			put("Pulse Program", "Pulse sequence");
			put("Processing parameters", "Processing parameters");
			put("pulse program", "Pulse sequence");
			put("NMR Experimental Directory", "NMR experiment directory");
			put("Acquisition Parameters", "Acquisition parameters");
			put("Pulse field gradient", "Pulse field gradient");
			put("NMR experiment directory", "NMR experiment directory");
			put("processing parameters", "Processing parameters");
			put("Processing Parameters", "Processing parameters");

		}
	};

	public static String getType(String val_name) {
		return (String) map.get(val_name);
	}
}
