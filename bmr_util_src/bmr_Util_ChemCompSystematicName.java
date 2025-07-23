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

public class bmr_Util_ChemCompSystematicName {

	static final Map<String, String> map = new HashMap<String, String>() {

		private static final long serialVersionUID = 27L;

		{

			put("PUBCHEM_IUPAC_CAS_NAME", "CAS name");
			put("BMRB", "BMRB");
			put("One letter code", "One letter code");
			put("CAS registry number", "CAS registry number");
			put("IUPAC_OPENEYE", "IUPAC");
			put("IUPAC_CAS", "CAS name");
			put("IUPAC", "IUPAC");
			put("PUBCHEM_IUPAC_NAME", "IUPAC");
			put("PUBCHEM_IUPAC_SYSTEMATIC_NAME", "IUPAC");
			put("Lignin abbreviation", "Lignin abbreviation");
			put("Three letter code", "Three letter code");
			put("PUBCHEM_IUPAC_TRADITIONAL_NAME", "IUPAC");
			put("PUBCHEM_IUPAC_OPENEYE_NAME", "IUPAC");
			put("IUPAC_SYSTEMATIC", "IUPAC");
			put("Beilstein", "Beilstein");
			put("IUPAC_TRADITIONAL", "IUPAC");
			put("CAS name", "CAS name");

		}
	};

	public static String getNamingSystem(String val_name) {
		return (String) map.get(val_name);
	}
}
