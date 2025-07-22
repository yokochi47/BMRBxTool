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

import java.sql.*;
import java.util.logging.*;

import java.util.HashMap;
import java.util.Map;

public class bmr_Util_RelatedEntries {

	static final Map<String, String> map = new HashMap<String, String>() {

		private static final long serialVersionUID = 110L;

		{

			put("Pfam", "Pfam");
			put("RCSB", "PDB");
			put("BMBR", "BMRB");
			put("ArachnoServer", "ArachnoServer");
			put("BMRB(withdrawn)", "BMRB(withdrawn)");
			put("TargetDB", "TargetDB");
			put("Taxonomy", "Taxonomy");
			put("PIR", "PIR");
			put("REF", "REF");
			put("Uniprot", "SP");
			put("DBJ", "DBJ");
			put("Saccharomyces Genome Database", "SGD");
			put("SP", "SP");
			put("NCBI_Taxon", "Taxonomy");
			put("UniProt", "SP");
			put("InterPro", "InterPro");
			put("NCBI", "NCBI");
			put("EMDB", "EMDB");
			put("SGD", "SGD");
			put("Protein Information Resource", "PIR");
			put("EMBL", "EMBL");
			put("GB", "GB");
			put("BMCD", "BMCD");
			put("Swiss-Prot", "SP");
			put("NDB", "PDB");
			put("BMRB", "BMRB");
			put("BRMB", "BMRB");
			put("PDB", "PDB");

		}
	};

	public static String getDatabaseName(String val_name, Connection conn_bmrb, String accession_code) {

		if (accession_code != null)
			val_name = bmr_Util_DBName.guessDbName(val_name, accession_code);

		if ((val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && accession_code != null && accession_code.matches("^[0-9]+$") && !accession_code.equals("0")) {

			Statement state = null;
			ResultSet rset = null;

			String query = new String("select \"ID\" from \"Entry\" where \"ID\"='" + accession_code + "'");

			try {

				state = conn_bmrb.createStatement();
				rset = state.executeQuery(query);

				while (rset.next()) {

					String id = rset.getString("ID");

					if (id != null && id.equals(accession_code)) {

						val_name = "BMRB";
						break;

					}

				}

				if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) {
					rset.close();

					String query2 = new String("select \"ID\" from \"Entry\" where \"ID\"::integer>" + (Integer.valueOf(accession_code) - 10) + " and \"ID\"::integer<" + (Integer.valueOf(accession_code) + 10));

					rset = state.executeQuery(query2);

					while (rset.next()) {

						val_name = "BMRB(withdrawn)";
						break;

					}

				}

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_Util_RelatedEntries.class.getName());
				lgr.log(Level.SEVERE, ex.getMessage(), ex);
				System.exit(1);

			} finally {

				try {

					if (rset != null)
						rset.close();

					if (state != null)
						state.close();

				} catch (SQLException ex) {

					Logger lgr = Logger.getLogger(bmr_Util_RelatedEntries.class.getName());
					lgr.log(Level.WARNING, ex.getMessage(), ex);

				}

			}

		}

		return (String) map.get(val_name);
	}

	public static String checkDatabaseAccessionCode(String database_accession_code, String entry_id) {

		final String[][] accodetbl = {

				{"CAA99465.1", "11032", "CAA99465"},
				{"NP_014887.1", "11032", "NP_014887"},
				{"CAA99465.1", "11033", "CAA99465"},
				{"NP_014887.1", "11033", "NP_014887"},
				{"GI:11321605", "15348", "NP_004178"},
				{"gi:21361157", "15481", "NP_004829"},

		};

		for (int i = 0; i < accodetbl.length; i++) {

			if (accodetbl[i][0].equals(database_accession_code) && accodetbl[i][1].equals(entry_id))
				return accodetbl[i][2];

			if (accodetbl[i][0].isEmpty() && (database_accession_code == null || database_accession_code.isEmpty() || database_accession_code.equals(".") || database_accession_code.equals("?")) && accodetbl[i][1].equals(entry_id))
				return accodetbl[i][2];

		}

		if (database_accession_code == null)
			return null;

		return database_accession_code.toUpperCase();
	}
}
