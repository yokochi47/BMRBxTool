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

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.sql.*;
import java.util.logging.*;

public class bmr_Util_LE {

	public static String getFormulaWeight(String val_name, Connection conn_bmrb, Connection conn_le, String entry_id, String entity_id) {

		String _val_name = null;

		Statement state = null;
		ResultSet rset = null;

		Statement state2 = null;
		ResultSet rset2 = null;

		if (conn_bmrb != null && conn_le != null && !(entry_id == null || entry_id.isEmpty() || entry_id.equals(".") || entry_id.equals("?")) && !(entity_id == null || entity_id.isEmpty() || entity_id.equals(".") || entity_id.equals("?"))) {

			String assembly_id = bmr_Util_BMRB.getAssemblyID(null, conn_bmrb, entry_id, entity_id);

			String type = bmr_Util_AssemblyType.guessType(conn_bmrb, entry_id, assembly_id);

			try {

				BigDecimal formula_weight = BigDecimal.ZERO;

				String query = new String("select \"Comp_ID\",count(\"Comp_ID\") from \"Entity_comp_index\" where \"Entry_ID\"='" + entry_id + "' and \"Entity_ID\"='" + entity_id + "' and \"Comp_ID\" is not null group by \"Comp_ID\" having count(\"Comp_ID\") > 0");

				state = conn_bmrb.createStatement();
				rset = state.executeQuery(query);

				int total = 0;

				while (rset.next()) {

					String comp_id = rset.getString("Comp_ID");
					int count = rset.getInt("count");

					if (comp_id == null || comp_id.isEmpty() || comp_id.equals(".") || comp_id.equals("?") || count == 0)
						continue;

					comp_id = comp_id.toUpperCase();

					if (comp_id.equals("X") && (type.startsWith("protein") || type.startsWith("peptide"))) {

						formula_weight = null;
						break;

					}

					String query2 = new String("select formula_weight * " + count + " as total_weight from chem_comp where id='" + comp_id + "'");

					state2 = conn_le.createStatement();
					rset2 = state2.executeQuery(query2);

					total += count;

					while (rset2.next()) {

						BigDecimal total_weight = rset2.getBigDecimal("total_weight");
						formula_weight = formula_weight.add(total_weight);

						switch (comp_id) {
						case "ARG":
						case "HIS":
						case "LYS":
							formula_weight = formula_weight.subtract(new BigDecimal(1.00794 * count));
							break;
						}

						break;

					}

				}

				if (formula_weight != null) {

					if (total > 1) {

						BigDecimal water_weight = new BigDecimal(18.01528 * (total - 1));
						formula_weight = formula_weight.subtract(water_weight);

					}

					formula_weight.setScale(3, RoundingMode.HALF_EVEN);
					_val_name = formula_weight.toString();

				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {

				try {

					if (rset != null)
						rset.close();

					if (state != null)
						state.close();

					if (rset2 != null)
						rset2.close();

					if (state2 != null)
						state2.close();

				} catch (SQLException ex) {

					Logger lgr = Logger.getLogger(bmr_Util_LE.class.getName());
					lgr.log(Level.WARNING, ex.getMessage(), ex);

				}
			}

		}

		if (_val_name != null && Double.valueOf(_val_name) > 0.0)
			return _val_name;

		return (val_name == null || !val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)?$") || Double.valueOf(val_name) == 0.0 ? null : val_name);
	}

	public static String getFormulaWeight(String val_name, Connection conn_le, String pdb_code) {

		Statement state = null;
		ResultSet rset = null;

		if (conn_le != null && !(pdb_code == null || pdb_code.isEmpty() || pdb_code.equals(".") || pdb_code.equals("?"))) {

			try {

				String query = new String("select formula_weight from chem_comp where id='" + pdb_code + "'");

				state = conn_le.createStatement();
				rset = state.executeQuery(query);

				while (rset.next()) {

					BigDecimal formula_weight = rset.getBigDecimal("formula_weight");
					formula_weight.setScale(3, RoundingMode.HALF_EVEN);
					String weight = formula_weight.toString();

					return (Double.valueOf(weight) == 0.0 ? null : weight);

				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {

				try {

					if (rset != null)
						rset.close();

					if (state != null)
						state.close();

				} catch (SQLException ex) {

					Logger lgr = Logger.getLogger(bmr_Util_LE.class.getName());
					lgr.log(Level.WARNING, ex.getMessage(), ex);

				}
			}

		}

		return val_name;
	}
}
