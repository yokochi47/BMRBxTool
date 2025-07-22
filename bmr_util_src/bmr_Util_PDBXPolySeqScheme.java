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

public class bmr_Util_PDBXPolySeqScheme {

	public static String getEntityAssemblyID(String val_name, Connection conn_bmrb, String entry_id, String entity_id) {

		Statement state = null;
		ResultSet rset = null;

		String query = new String("select \"ID\" from \"Entity_assembly\" where \"Entry_ID\"='" + entry_id + "' and \"Entity_ID\"='" + entity_id + "'");

		int ids = 0;
		String id = null;

		try {

			state = conn_bmrb.createStatement();
			rset = state.executeQuery(query);

			while (rset.next()) {

				id = rset.getString("ID");

				if (!(id == null || id.isEmpty() || id.equals(".") || id.equals("?"))) {
					ids++;
					continue;
				}

			}

			if (ids == 1)
				val_name = id;

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_Util_PDBXPolySeqScheme.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_Util_PDBXPolySeqScheme.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		return val_name;
	}

	public static String getCompIndexID(String val_name, Connection conn_bmrb, String entry_id, String entity_id, String mon_id, String pdb_seq_num) {

		Statement state = null;
		ResultSet rset = null;

		String query = new String("select \"ID\",\"Comp_ID\" from \"Entity_comp_index\" where \"Entry_ID\"='" + entry_id + "' and \"Entity_ID\"='" + entity_id + "'");

		try {

			state = conn_bmrb.createStatement();
			rset = state.executeQuery(query);

			while (rset.next()) {

				String id = rset.getString("ID");
				String comp_id = rset.getString("Comp_ID");

				if (!(id == null || id.isEmpty() || id.equals(".") || id.equals("?")) && id.equals(pdb_seq_num) && !(comp_id == null || comp_id.isEmpty() || comp_id.equals(".") || comp_id.equals("?")) && comp_id.equals(mon_id)) {
					val_name = id;
					break;
				}

			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_Util_PDBXPolySeqScheme.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_Util_PDBXPolySeqScheme.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		return val_name;
	}
}
