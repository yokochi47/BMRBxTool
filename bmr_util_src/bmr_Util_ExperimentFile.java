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

public class bmr_Util_ExperimentFile {

	static final Map<String, String> map_content = new HashMap<String, String>() {

		private static final long serialVersionUID = 82L;

		{

			put("Time-domain data", "Time-domain (raw spectral data)");
			put("Spectral image", "Spectral image");
			put("Time-domain (raw spectral data)", "Time-domain (raw spectral data)");
			put("Processing parameters", "Processing parameters");
			put("Pulse sequence", "Pulse sequence");
			put("Acquisition parameters", "Acquisition parameters");
			put("Pulse field gradient", "Pulse field gradient");
			put("Pulse sequence image file", "Pulse sequence image file");
			put("RNA fragment binding data", "RNA fragment binding data");
			put("Peak list", "Peak list");
			put("NMR experiment directory", "NMR experiment directory");

		}
	};

	public static String getContent(String val_name) {
		return (String) map_content.get(val_name);
	}
}
