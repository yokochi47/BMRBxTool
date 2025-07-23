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

public class bmr_Util_ChemCompDescriptor {

	static final Map<String, String> map_type = new HashMap<String, String>() {

		private static final long serialVersionUID = 23L;

		{

			put("INCHI_MAIN_HATOM", "INCHI_MAIN_HATOM");
			put("INCHI_MAIN", "INCHI_MAIN");
			put("InChI", "INCHI");
			put("INCHI_RECONNECT", "INCHI_RECONNECT");
			put("INCHI_MAIN_CONNECT", "INCHI_MAIN_CONNECT");
			put("SMILES_CANONICAL", "SMILES_CANONICAL");
			put("na", "na");
			put("INCHI_FIXEDH", "INCHI_FIXEDH");
			put("INCHI_CHARGE", "INCHI_CHARGE");
			put("INCHI", "INCHI");
			put("INCHI_ISOTOPE", "INCHI_ISOTOPE");
			put("SMILES", "SMILES");
			put("InChIKey", "INCHI");
			put("INCHI_STEREO", "INCHI_STEREO");
			put("INCHI_MAIN_FORMULA", "INCHI_MAIN_FORMULA");

		}
	};

	public static String getType(String val_name) {
		return (String) map_type.get(val_name);
	}

	static final Map<String, String> map_program = new HashMap<String, String>() {

		private static final long serialVersionUID = 24L;

		{

			put("OECHEM", "OECHEM");
			put("InChI", "na");
			put("OTHER", "OTHER");
			put("DAYLIGHT", "DAYLIGHT");
			put("na", "na");
			put("InChi", "na");
			put("OpenEye OEToolkits", "OECHEM");
			put("ACD", "ACD");
			put("ACDLabs", "ACD");
			put("CACTVS", "CACTVS");
			put("OpenEye/OEToolkits", "OECHEM");

		}
	};

	public static String getProgram(String val_name) {
		return (String) map_program.get(val_name);
	}
}
