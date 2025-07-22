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

import java.io.*;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.logging.*;
import java.sql.*;

import org.apache.xmlbeans.*;
import org.pdbj.bmrbpub.schema.mmcifNmrStar.ConformerStatListRepType.*;

public class bmr_ConformerStatListRepType {

	private static final String table_name = "Conformer_stat_list_rep";

	public static int write_xml(Connection conn_bmrb, Connection conn_tax, Connection conn_le, String entry_id, XmlOptions xml_opt, BufferedWriter buffw, FileWriter logw, FileWriter errw) {

		if (bmr_Util_Main.noatom)
			return 0;

		ConformerStatListRepType body = ConformerStatListRepType.Factory.newInstance();
		ConformerStatListRep[] list = new ConformerStatListRep[1];

		Statement state = null;
		ResultSet rset = null;

		String[] rcsv = null;

		int list_len = 0;

		try {

			String query = new String("select count(*) from \"" + table_name + "\" where \"Entry_ID\"='" + entry_id + "' group by \"Entry_ID\"");

			state = conn_bmrb.createStatement();
			rset = state.executeQuery(query);

			if (rset.next()) {

				list_len = Integer.parseInt(rset.getString(1));

				rcsv = new String[list_len];

				String query2 = new String("select * from \"" + table_name + "\" where \"Entry_ID\"='" + entry_id + "' order by (0 || \"Conformer_stat_list_ID\")::decimal");

				rset.close();

				rset = state.executeQuery(query2);

				int cols = rset.getMetaData().getColumnCount();
				int lines = 0;

				buffw.write("  <BMRBx:conformer_stat_list_repCategory>\n");

				StringBuilder sb = new StringBuilder();

				while (rset.next()) {

					for (int j = 1; j <= cols; j++) {
						String val = rset.getString(j);
						sb.append((val != null ? val : "") + ",");
					}

					rcsv[lines] = sb.toString();

					sb.setLength(0);

					int l;

					for (l = 0; l < lines; l++) {
						if (rcsv[lines].equals(rcsv[l]))
							break;
					}

					if (l < lines++) {

						try {
							logw.write("category='ConformerStatListRep', duplicated content of the category was excluded.\n");
						} catch (IOException e) {
							e.printStackTrace();
						}

						continue;
					}

					list[0] = ConformerStatListRep.Factory.newInstance();

					if (!set_decimal(list[0], "setAngleEValue", "setNilAngleEValue", false, rset.getString("Angle_E_value"), logw))
						continue;
					if (!set_decimal(list[0], "setAngleEValueErr", "setNilAngleEValueErr", false, rset.getString("Angle_E_value_err"), logw))
						continue;
					if (!set_decimal(list[0], "setAngleRmsd", "setNilAngleRmsd", false, rset.getString("Angle_rmsd"), logw))
						continue;
					if (!set_decimal(list[0], "setAngleRmsdErr", "setNilAngleRmsdErr", false, rset.getString("Angle_rmsd_err"), logw))
						continue;
					if (!set_decimal(list[0], "setBondEValue", "setNilBondEValue", false, rset.getString("Bond_E_value"), logw))
						continue;
					if (!set_decimal(list[0], "setBondEValueErr", "setNilBondEValueErr", false, rset.getString("Bond_E_value_err"), logw))
						continue;
					if (!set_decimal(list[0], "setBondRmsd", "setNilBondRmsd", false, rset.getString("Bond_rmsd"), logw))
						continue;
					if (!set_decimal(list[0], "setBondRmsdErr", "setNilBondRmsdErr", false, rset.getString("Bond_rmsd_err"), logw))
						continue;
					if (!set_decimal(list[0], "setImproperTorsionAngleRmsd", "setNilImproperTorsionAngleRmsd", false, rset.getString("Improper_torsion_angle_rmsd"), logw))
						continue;
					if (!set_decimal(list[0], "setImproperTorsionAngleRmsdErr", "setNilImproperTorsionAngleRmsdErr", false, rset.getString("Improper_torsion_angle_rmsd_err"), logw))
						continue;
					if (!set_decimal(list[0], "setImpropersEValue", "setNilImpropersEValue", false, rset.getString("Impropers_E_value"), logw))
						continue;
					if (!set_decimal(list[0], "setImpropersEValueErr", "setNilImpropersEValueErr", false, rset.getString("Impropers_E_value_err"), logw))
						continue;
					if (!set_decimal(list[0], "setLennardJonesEValue", "setNilLennardJonesEValue", false, rset.getString("Lennard_Jones_E_value"), logw))
						continue;
					if (!set_decimal(list[0], "setLennardJonesEValueErr", "setNilLennardJonesEValueErr", false, rset.getString("Lennard_Jones_E_value_err"), logw))
						continue;
					if (!set_decimal(list[0], "setNcsEValue", "setNilNcsEValue", false, rset.getString("NCS_E_value"), logw))
						continue;
					if (!set_decimal(list[0], "setNcsEValueErr", "setNilNcsEValueErr", false, rset.getString("NCS_E_value_err"), logw))
						continue;
					if (!set_decimal(list[0], "setNoeEValue", "setNilNoeEValue", false, rset.getString("NOE_E_value"), logw))
						continue;
					if (!set_decimal(list[0], "setNoeEValueErr", "setNilNoeEValueErr", false, rset.getString("NOE_E_value_err"), logw))
						continue;
					if (!set_decimal(list[0], "setPeptidePlanarityRmsd", "setNilPeptidePlanarityRmsd", false, rset.getString("Peptide_planarity_rmsd"), logw))
						continue;
					if (!set_decimal(list[0], "setPeptidePlanarityRmsdErr", "setNilPeptidePlanarityRmsdErr", false, rset.getString("Peptide_planarity_rmsd_err"), logw))
						continue;
					if (!set_decimal(list[0], "setRamachanAllowedPct", "setNilRamachanAllowedPct", false, rset.getString("Ramachan_allowed_pct"), logw))
						continue;
					if (!set_decimal(list[0], "setRamachanDisallowedPct", "setNilRamachanDisallowedPct", false, rset.getString("Ramachan_disallowed_pct"), logw))
						continue;
					if (!set_decimal(list[0], "setRamachanGenAllowedPct", "setNilRamachanGenAllowedPct", false, rset.getString("Ramachan_gen_allowed_pct"), logw))
						continue;
					if (!set_decimal(list[0], "setRamachanMostFavoredPct", "setNilRamachanMostFavoredPct", false, rset.getString("Ramachan_most_favored_pct"), logw))
						continue;
					if (!set_enum_stats_not_available(list[0], "setStatsNotAvailable", "setNilStatsNotAvailable", false, rset.getString("Stats_not_available"), logw, errw))
						continue;
					if (!set_decimal(list[0], "setStructFigureOfMerit", "setNilStructFigureOfMerit", false, rset.getString("Struct_figure_of_merit"), logw))
						continue;
					if (!set_string(list[0], "setStructFigureOfMeritFuncForm", "setNilStructFigureOfMeritFuncForm", false, rset.getString("Struct_figure_of_merit_func_form"), logw))
						continue;
					if (!set_decimal(list[0], "setTorsionalEValue", "setNilTorsionalEValue", false, rset.getString("Torsional_E_value"), logw))
						continue;
					if (!set_decimal(list[0], "setTorsionalEValueErr", "setNilTorsionalEValueErr", false, rset.getString("Torsional_E_value_err"), logw))
						continue;
					if (!set_decimal(list[0], "setTotalEValue", "setNilTotalEValue", false, rset.getString("Total_E_value"), logw))
						continue;
					if (!set_decimal(list[0], "setTotalEValueErr", "setNilTotalEValueErr", false, rset.getString("Total_E_value_err"), logw))
						continue;
					if (!set_decimal(list[0], "setVanDerWaalsEVal", "setNilVanDerWaalsEVal", false, rset.getString("Van_der_Waals_E_val"), logw))
						continue;
					if (!set_decimal(list[0], "setVanDerWaalsEValErr", "setNilVanDerWaalsEValErr", false, rset.getString("Van_der_Waals_E_val_err"), logw))
						continue;
					if (!set_integer(list[0], "setConformerStatListId", "", true, rset.getString("Conformer_stat_list_ID"), logw))
						continue;
					if (!set_string(list[0], "setEntryId", "", true, rset.getString("Entry_ID"), logw))
						continue;

					body.setConformerStatListRepArray(list);

					BufferedReader buffr = new BufferedReader(new StringReader(body.xmlText(xml_opt)));

					String prev = buffr.readLine();

					String cont = null;

					while ((prev = buffr.readLine()) != null) {

						if (cont != null)
							buffw.write(cont + "\n");

						cont = prev;

					}

					buffr.close();

				}

				buffw.write("  </BMRBx:conformer_stat_list_repCategory>\n");

			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_ConformerStatListRepType.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} catch (IOException ex) {

			Logger lgr = Logger.getLogger(bmr_ConformerStatListRepType.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_ConformerStatListRepType.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		return list_len;
	}

	private static boolean set_string(ConformerStatListRep list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (bmr_Util_Main.remediate_xml && (nil_method_name == null || nil_method_name.isEmpty()) && !method_name.equalsIgnoreCase("setSampleState") && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")))
			val_name = "na";

		if ((method_name.contains("BiologicalFunction") || method_name.contains("Citation") || method_name.contains("Details") || method_name.contains("Name") || method_name.contains("Relationship") || method_name.contains("Task") || method_name.contains("Title")) && !(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && val_name.contains("\"")) {

			if ((val_name.startsWith("\"") && val_name.endsWith("\"")) || (((val_name.startsWith("\"") && val_name.lastIndexOf("\"") + 2 >= val_name.length()) || method_name.contains("Task")) && val_name.replaceAll("[^\"]", "").length() == 2) || (val_name.replaceAll("[^\"]", "").length() % 2) == 1)
				val_name = val_name.replaceAll("\"", "");

		}

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ConformerStatListRep', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='ConformerStatListRep', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ String.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else
					method.invoke(list, val_name);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		return true;
	}

	private static boolean set_integer(ConformerStatListRep list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml && (nil_method_name == null || nil_method_name.isEmpty()) && !method_name.equalsIgnoreCase("setSampleID") && !method_name.equalsIgnoreCase("setExperimentID") && !method_name.equalsIgnoreCase("setStudyID") && !method_name.equalsIgnoreCase("setAssemblyID") && (method_name.contains("Id") || method_name.contains("Number") || method_name.contains("Count")) && !method_name.contains("ListId") && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$")))
			val_name = "0";

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ConformerStatListRep', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='ConformerStatListRep', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ int.class });
			Method nil_method = null;

			if (nil_method_name != null && !nil_method_name.isEmpty())
				nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					nil_method.invoke(list);
				else
					method.invoke(list, Integer.parseInt(val_name));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		return true;
	}

	private static boolean set_decimal(ConformerStatListRep list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || (!val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)?$") && !val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)([eE][-+]?[0-9]+)?$"))) && (nil_method_name == null || nil_method_name.isEmpty())) {

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ConformerStatListRep', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || (!val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)?$") && !val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)([eE][-+]?[0-9]+)?$")))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='ConformerStatListRep', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ BigDecimal.class });
			Method nil_method = null;

			if (nil_method_name != null && !nil_method_name.isEmpty())
				nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					nil_method.invoke(list);
				else
					method.invoke(list, new BigDecimal(val_name, MathContext.DECIMAL32));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		return true;
	}

	private static boolean set_enum_stats_not_available(ConformerStatListRep list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ConformerStatListRep', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || val_name.equals("null"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='ConformerStatListRep', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ ConformerStatListRep.StatsNotAvailable.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					ConformerStatListRep.StatsNotAvailable.Enum _enum = ConformerStatListRep.StatsNotAvailable.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:ConformerStatListRep method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= ConformerStatListRep.StatsNotAvailable.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + ConformerStatListRep.StatsNotAvailable.Enum.forInt(i));
						try {
							errw.write("class_name:ConformerStatListRep method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= ConformerStatListRep.StatsNotAvailable.Enum.table.lastInt(); i++)
								errw.write(" enum:" + ConformerStatListRep.StatsNotAvailable.Enum.forInt(i) + "\n");
						} catch (IOException e) {
							e.printStackTrace();
						}
						//if (nil_method != null)
							//nil_method.invoke(list);
						//else
							return false;
					}
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		return true;
	}
}
