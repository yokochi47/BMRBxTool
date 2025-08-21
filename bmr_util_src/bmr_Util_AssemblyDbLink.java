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

public class bmr_Util_AssemblyDbLink {

	static final Map<String, String> map_database_code = new HashMap<String, String>() {

		private static final long serialVersionUID = 3L;

		{

			put("RCSB PDB", "PDB");
			put("TargetDB", "TargetDB");
			put("BMRB(withdrawn)", "BMRB(withdrawn)");
			put("PubMed", "PubMed");
			put("PIR", "PIR");
			put("ExPASy", "ExPASy");
			put("DBJ", "DBJ");
			put("SP", "SP");
			put("PUBMED", "PubMed");
			put("NCBI", "NCBI");
			put("SGD", "SGD");
			put("PRF", "PRF");
			put("EMBL", "EMBL");
			put("SMART", "SMART");
			put("GB", "GB");
			put("NDB", "NDB");
			put("BMRB", "BMRB");
			put("PDB", "PDB");

		}
	};

	public static String getDatabaseCode(String val_name, String accession_code) {

		if (accession_code != null)
			val_name = bmr_Util_DBName.guessDbName(val_name, accession_code);

		if (val_name != null && val_name.equals("NCBI") && accession_code != null && accession_code.matches("^[0-9]+$"))
			return null;

		return (String) map_database_code.get(val_name);
	}

	static final Map<String, String> map_entry_experimental_method = new HashMap<String, String>() {

		private static final long serialVersionUID = 4L;

		{

			put("NOESY, ROESY, TOCSY, IP-COSY,1H13C HSQC, 1H15N HSQC", "solution NMR");
			put("XAFS", "XAFS");
			put("solid-state NMR and X-ray fiber diffraction", "solid-state NMR and X-ray fiber diffraction");
			put("Solution NMR", "solution NMR");
			put("Fiber Diffraction", "X-ray fiber diffraction");
			put("X-ray fiber diffraction", "X-ray fiber diffraction");
			put("X-ray Crystal Analysis", "X-ray crystallography");
			put("x-ray", "X-ray");
			put("Minimized Average Structure from solid state NMR", "solid-state NMR");
			put("na", "na");
			put("X-ray fiber diffraction + solid state NMR", "solid-state NMR and X-ray fiber diffraction");
			put("mass spectrometry", "mass spectrometry");
			put("X-ray crystallography", "X-ray crystallography");
			put("solution NMR", "solution NMR");
			put("Minimized Average Structure from NMR", "solution NMR");
			put("X-ray", "X-ray");
			put("Expressed protein", "na");
			put("NMR", "NMR");
			put("NOESY, ROESY, TOCSY, IP-COSY, 1H13C HSQC, 1H15N HSQC", "solution NMR");
			put("solid-state NMR", "solid-state NMR");
			put("X-ray diffraction", "X-ray");
			put("X-ray Crystal Diffraction and Analysis", "X-ray crystallography");
			put("NMR spectroscopy", "NMR");
			put("FTIR", "FTIR");

		}
	};

	public static String getEntryExperimentalMethod(String val_name) {
		return (String) map_entry_experimental_method.get(val_name);
	}

	static final Map<String, String> map_author_supplied = new HashMap<String, String>() {

		private static final long serialVersionUID = 5L;

		{

			put("Y", "yes");
			put("yes", "yes");
			put("no", "no");

		}
	};

	public static String getAuthorSupplied(String val_name) {
		return (String) map_author_supplied.get(val_name);
	}

	public static String checkAccessionCode(String accession_code, String entry_id) {

		final String[][] accodetbl = {

				{"g605649", "5117", "AAA57568"},
				{"NIFU_HAEIN", "5842", "Q57074"},

		};

		for (int i = 0; i < accodetbl.length; i++) {

			if (accodetbl[i][0].equals(accession_code) && accodetbl[i][1].equals(entry_id))
				return accodetbl[i][2];

			if (accodetbl[i][0].isEmpty() && (accession_code == null || accession_code.isEmpty() || accession_code.equals(".") || accession_code.equals("?")) && accodetbl[i][1].equals(entry_id))
				return accodetbl[i][2];

		}

		if (accession_code == null)
			return null;

		return accession_code.toUpperCase();
	}
}
