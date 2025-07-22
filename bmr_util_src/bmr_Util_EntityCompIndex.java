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

public class bmr_Util_EntityCompIndex {

	public static String getNumberOfMonomers(Connection conn_bmrb, String entry_id, String entity_id) {

		String poly_seq_one_letter_code = getPolySeqOneLetterCode(conn_bmrb, entry_id, entity_id);

		if (poly_seq_one_letter_code == null || poly_seq_one_letter_code.isEmpty() || poly_seq_one_letter_code.equals(".") || poly_seq_one_letter_code.equals("?"))
			return "";

		return String.valueOf(poly_seq_one_letter_code.length());
	}

	public static String getPolySeqOneLetterCode(Connection conn_bmrb, String entry_id, String entity_id) {

		StringBuilder poly_seq_one_letter_code = new StringBuilder();

		Statement state = null;
		ResultSet rset = null;

		String query = new String("select \"Comp_ID\" from \"Entity_comp_index\" where \"Entry_ID\"='" + entry_id + "' and \"Entity_ID\"='" + entity_id + "' order by \"ID\"::integer");

		try {

			state = conn_bmrb.createStatement();
			rset = state.executeQuery(query);

			while (rset.next()) {

				String comp_id = rset.getString("Comp_ID");

				if (!(comp_id == null || comp_id.isEmpty() || comp_id.equals(".") || comp_id.equals("?")))
					poly_seq_one_letter_code.append(bmr_Util_SeqOneLetterCode.getCode(comp_id));

			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_Util_EntityCompIndex.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_Util_EntityCompIndex.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		String code = poly_seq_one_letter_code.toString();

		return (code.matches("^X+$") ? "." : code);
	}

	public static String getPolySeqOneLetterCodeCan(Connection conn_bmrb, String entry_id, String entity_id) {

		StringBuilder poly_seq_one_letter_code_can = new StringBuilder();

		Statement state = null;
		ResultSet rset = null;

		String query = new String("select \"Comp_ID\" from \"Entity_comp_index\" where \"Entry_ID\"='" + entry_id + "' and \"Entity_ID\"='" + entity_id + "' order by \"ID\"::integer");

		try {

			state = conn_bmrb.createStatement();
			rset = state.executeQuery(query);

			while (rset.next()) {

				String comp_id = rset.getString("Comp_ID");

				if (!(comp_id == null || comp_id.isEmpty() || comp_id.equals(".") || comp_id.equals("?")))
					poly_seq_one_letter_code_can.append(bmr_Util_SeqOneLetterCode.getCodeCan(comp_id));

			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_Util_EntityCompIndex.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_Util_EntityCompIndex.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		String code = poly_seq_one_letter_code_can.toString();

		return (code.matches("^X+$") ? "." : code);
	}
}
