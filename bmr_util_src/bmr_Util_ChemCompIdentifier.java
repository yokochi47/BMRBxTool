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

public class bmr_Util_ChemCompIdentifier {

	static final Map<String, String> map_program = new HashMap<String, String>() {

		private static final long serialVersionUID = 25L;

		{

			put("OECHEM", "OECHEM");
			put("OTHER", "OTHER");
			put("ACD-LABS", "ACD");
			put("DAYLIGHT", "DAYLIGHT");
			put("AUTONOM", "AUTONOM");
			put("OpenEye OEToolkits", "OECHEM");
			put("ACD", "ACD");
			put("ACDLabs", "ACD");
			put("OpenEye/Lexichem", "OECHEM");

		}
	};

	public static String getProgram(String val_name) {
		return (String) map_program.get(val_name);
	}

	static final Map<String, String> map_type = new HashMap<String, String>() {

		private static final long serialVersionUID = 26L;

		{

			put("OPENEYE NAME", "PUBCHEM_IUPAC_OPENEYE_NAME");
			put("CAS REGISRY NUMBER", "CAS Registry number");
			put("NAME", "common name");
			put("CAS NAME", "PUBCHEM_IUPAC_CAS_NAME");
			put("COMMON NAME", "common name");
			put("TRADITIONAL NAME", "PUBCHEM_IUPAC_TRADITIONAL_NAME");
			put("PUBCHEM_IUPAC_TRADITIONAL_NAME", "PUBCHEM_IUPAC_TRADITIONAL_NAME");
			put("PUBCHEM_IUPAC_OPENEYE_NAME", "PUBCHEM_IUPAC_OPENEYE_NAME");
			put("IUPAC NAME", "PUBCHEM_IUPAC_NAME");
			put("PUBCHEM_IUPAC_CAS_NAME", "PUBCHEM_IUPAC_CAS_NAME");
			put("CAS Registry number", "CAS Registry number");
			put("PUBCHEM_IUPAC_SYSTEMATIC_NAME", "PUBCHEM_IUPAC_SYSTEMATIC_NAME");
			put("PUBCHEM_IUPAC_NAME", "PUBCHEM_IUPAC_NAME");
			put("CAS REGISTRY NUMBER", "CAS Registry number");
			put("MDL Identifier", "MDL Identifier");
			put("NSC NUMBER", "NSC NUMBER");
			put("PUBCHEM SID", "PUBCHEM SID");
			put("PUBCHEM Identifier", "PUBCHEM Identifier");
			put("systematic name", "systematic name");
			put("common name", "common name");
			put("SYSTEMATIC NAME", "systematic name");
			put("PUBCHEM CID", "PUBCHEM CID");

		}
	};

	public static String getType(String val_name) {
		return (String) map_type.get(val_name);
	}
}
