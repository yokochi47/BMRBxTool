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

public class bmr_Util_Sample {

	static final Map<String, String> map = new HashMap<String, String>() {

		private static final long serialVersionUID = 113L;

		{

			put("micelle", "micelle");
			put("DPC micelles", "micelle");
			put("bicelles", "bicelle");
			put("Bi-cells", "bicelle");
			put("solution", "solution");
			put("microcrystals", "microcrystalline");
			put("polycrystalline powder", "polycrystalline powder");
			put("lyophilized powder", "lyophilized powder");
			put("soultion", "solution");
			put("liquid crystal", "liquid crystal");
			put("in living E. coli cells", "in-cell");
			put("DMPC:CHAPS bicelle", "bicelle");
			put("solid", "solid");
			put("in-cell", "in-cell");
			put("b_bicell", "bicelle");
			put("microcrystal", "microcrystalline");
			put("microcrystalline", "microcrystalline");
			put("DPC micelle", "micelle");
			put("bicell", "bicelle");
			put("filamentous phage", "filamentous virus");
			put("bicell_solution", "bicelle");
			put("liquid", "solution");
			put("filamentous virus", "filamentous virus");
			put("fibrous protein", "fibrous protein");
			put("Bi-cell", "bicelle");
			put("d_bicell_solution", "bicelle");
			put("oriented membrane film", "oriented membrane film");
			put("single crystal", "single crystal");
			put("micelle, lipid bilayers", "micelle");
			put("emulsion", "emulsion");
			put("bi-cell", "bicelle");
			put("fiber", "fiber");
			put("b_micelles", "micelle");
			put("reverse micelle", "reverse micelle");
			put("gel solution", "gel solution");
			put("amyloid fibril", "fibrous protein");
			put("frozen solution", "solid");
			put("cell suspension", "in-cell");
			put("DMPC:CHAPS:GM1 Bicelle", "bicelle");
			put("OG micelles", "micelle");
			put("membrane", "membrane");
			put("gel solid", "gel solid");
			put("solution/partial magnetic alignement", "solution");
			put("liposome", "liposome");
			put("solution in SDS (micelles)", "micelle");
			put("Bi_cell", "bicelle");
			put("micelles", "micelle");
			put("solution`", "solution");
			put("c_micelles", "micelle");
			put("gel", "gel solution");
			put("bicelle", "bicelle");

		}
	};

	public static String getType(String val_name) {
		return (String) map.get(val_name);
	}

	public static String getGelType(String val_name, Connection conn_bmrb, String entry_id) {

		if (val_name != null && !val_name.equalsIgnoreCase("gel"))
			return val_name;

		Statement state = null;
		ResultSet rset = null;

		String query = new String("select \"Experimental_method_subtype\" from \"Entry\" where \"ID\"='" + entry_id + "'");

		try {

			state = conn_bmrb.createStatement();
			rset = state.executeQuery(query);

			while (rset.next()) {

				String subtype = bmr_Util_Entry.getExperimentalMethodSubtype(rset.getString("Experimental_method_subtype"), entry_id);

				if (subtype != null && subtype.equalsIgnoreCase("SOLID-STATE"))
					val_name = "gel solid";
				else
					val_name = "gel solution";
			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_Util_Sample.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_Util_Sample.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		return val_name;
	}
}
