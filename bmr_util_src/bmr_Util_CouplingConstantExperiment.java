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

public class bmr_Util_CouplingConstantExperiment {

	public static String getExperimentID(String val_name, Connection conn_bmrb, String entry_id, String sample_id, String coupling_constant_list_id) {

		Statement state = null;
		ResultSet rset = null;

		String query = new String("select \"ID\",\"Name\",\"Sample_ID\" from \"Experiment\" where \"Entry_ID\"='" + entry_id + "'");

		int experiments = 0;
		String id = null;

		try {

			state = conn_bmrb.createStatement();
			rset = state.executeQuery(query);

			while (rset.next()) {

				String experiment_name = rset.getString("Name");

				if (experiment_name == null || experiment_name.isEmpty() || experiment_name.equals(".") || experiment_name.equals("?")) {

					if (!(sample_id == null || sample_id.isEmpty() || sample_id.equals(".") || sample_id.equals("?"))) {

						String _sample_id = rset.getString("Sample_ID");

						if (_sample_id != null && _sample_id.equals(sample_id)) {
							id = rset.getString("ID");
							experiments++;
						}

					}

					continue;
				}

				if (experiment_name.matches(".*J.*") || experiment_name.matches(".*[Cc][Oo][Ss][Yy].*") || experiment_name.matches(".*[Hh][Nn][Hh][Aa].*") || experiment_name.matches(".*[Ii][Pa][Aa][Pp].*") || experiment_name.matches(".*[Tt][Ro][Oo][Ss][Yy].*") || experiment_name.matches(".*[Cc][Oo][Uu][Pp][Ll][Ee].*")) {
					id = rset.getString("ID");
					experiments++;
				}

			}

			if (experiments == 1)
				val_name = id;

			else {

				experiments = 0;

				rset.close();

				rset = state.executeQuery(query);

				while (rset.next()) {

					id = rset.getString("ID");
					experiments++;

				}

				if (experiments == 1)
					val_name = id;

				else {

					rset.close();

					String query2 = new String("select \"Code\" from \"Coupling_constant\" where \"Entry_ID\"='" + entry_id + "' and \"Coupling_constant_list_ID\"='" + coupling_constant_list_id + "' and \"ID\"='1'");

					ResultSet rset2 = state.executeQuery(query2);

					rset2.next();

					String code = rset2.getString("Code");

					rset2.close();

					if (!(code == null || code.isEmpty() || code.equals(".") || code.equals("?"))) {

						rset = state.executeQuery(query);

						while (rset.next()) {

							String experiment_name = rset.getString("Name");

							if (experiment_name == null || experiment_name.isEmpty() || experiment_name.equals(".") || experiment_name.equals("?"))
								continue;

							if ((experiment_name.matches(".*[Cc][Oo][Ss][Yy].*") && code.matches(".*[Cc][Oo][Ss][Yy].*")) || (experiment_name.matches(".*[Hh][Nn][Hh][Aa].*") && code.matches(".*[Hh][Nn][Hh][Aa].*")) || (experiment_name.matches(".*[Ii][Pa][Aa][Pp].*") && code.matches(".*[Ii][Pa][Aa][Pp].*")) || (experiment_name.matches(".*[Tt][Ro][Oo][Ss][Yy].*") && code.matches(".*[Tt][Ro][Oo][Ss][Yy].*"))) {

								val_name = rset.getString("ID");
								break;

							}

						}

					}

					if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?"))
						val_name = "0";

				}

			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_Util_CouplingConstantExperiment.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_Util_CouplingConstantExperiment.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		return val_name;
	}
}
