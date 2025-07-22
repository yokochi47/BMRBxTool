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

public class bmr_Util_EntityDbLink {

	static final Map<String, String> map = new HashMap<String, String>() {

		private static final long serialVersionUID = 62L;

		{

			put("NCBI", "NCBI");
			put("BMRB", "BMRB");
			put("tRNAdb", "tRNADB");
			put("GB", "GB");
			put("PIR", "PIR");
			put("NDB", "NDB");
			put("BMRB(withdrawn)", "BMRB(withdrawn)");
			put("EMBL", "EMBL");
			put("HUGO", "HUGO");
			put("DBJ", "DBJ");
			put("SP", "SP");
			put("tRNADB", "tRNADB");
			put("REF", "REF");
			put("PRF", "PRF");
			put("PDB", "PDB");

		}
	};

	public static String getDatabaseCode(String val_name, String accession_code) {

		if (accession_code != null)
			val_name = bmr_Util_DBName.guessDbName(val_name, accession_code);

		if (val_name != null && val_name.equals("NCBI") && accession_code != null && accession_code.matches("^[0-9]+$"))
			return null;

		return (String) map.get(val_name);
	}

	static final Map<String, String> map_entry_experimental_method = new HashMap<String, String>() {

		private static final long serialVersionUID = 63L;

		{

			put("na", "na");
			put("Enterovirus E isolate Vir 404/03 polyprotein gene, complete cds", "na");
			put("solid-state NMR", "solid-state NMR");
			put("solution NMR", "solution NMR");
			put("X-ray crystallography", "X-ray crystallography");

		}
	};

	public static String getEntryExperimentalMethod(String val_name) {
		return (String) map_entry_experimental_method.get(val_name);
	}

	static final Map<String, String> map_author_supplied = new HashMap<String, String>() {

		private static final long serialVersionUID = 64L;

		{

			put("1", "no");
			put("yes", "yes");
			put("no", "no");

		}
	};

	public static String getAuthorSupplied(String val_name) {
		return (String) map_author_supplied.get(val_name);
	}

	public static String checkAccessionCode(String accession_code, String entry_id) {

		final String[][] accodetbl = {

				{"CAI77901.1", "4983", "CAI77901"},
				{"CAB90826.1", "4983", "CAB90826"},
				{"CAK44245.1", "4983", "CAK44245"},
				{"EDO17878.1", "4983", "EDO17878"},
				{"EDM13556.1", "4983", "EDM13556"},
				{"ABC94632.1", "4983", "ABC94632"},
				{"AAA36787.1", "4983", "AAA36787"},
				{"AAD44041.1", "4983", "AAD44041"},
				{"XP_001645736.1", "4983", "XP_001645736"},
				{"XP_001643983.1", "4983", "XP_001643983"},
				{"FAA00316.1", "4983", "FAA00316"},
				{"Hs.383396", "16485", "NM_001033"},
				{"23363", "18511", "O75147"},
				{"23363", "18563", "O75147"},
				{"GI:1718079", "18569", "P50552"},
				{"23020817", "18653", "ZP_00060511"},
				{"gi:21361157", "15481", "NP_004829"},

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
