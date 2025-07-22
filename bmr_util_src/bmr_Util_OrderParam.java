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

public class bmr_Util_OrderParam {

	public static String getModelFit(String val_name) {

		if (val_name != null) {

			val_name = val_name.replaceAll(",", ", ").replaceAll(",\\s+", ", ").trim().replaceFirst(",$", "");

			if (val_name.contains(".") && val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)?$"))
				val_name = null;

		}

	return val_name;
	}

	public static String getTauEVal(String val_name, Connection conn_bmrb, String entry_id) {

		Statement state = null;
		ResultSet rset = null;

		String query = new String("select \"Tau_e_val_units\" from \"Order_parameter_list\" where \"Entry_ID\"='" + entry_id + "'");

		try {

			state = conn_bmrb.createStatement();
			rset = state.executeQuery(query);

			while (rset.next()) {

				String tau_e_val_units = rset.getString("Tau_e_val_units");

				if (tau_e_val_units != null && tau_e_val_units.equalsIgnoreCase("E-10 s")) {
					val_name = String.valueOf(Float.valueOf(val_name) / 10.0);
					break;
				}

			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_Util_OrderParam.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_Util_OrderParam.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		return val_name;
	}
}
