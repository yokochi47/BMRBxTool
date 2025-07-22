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
import org.pdbj.bmrbpub.schema.mmcifNmrStar.ConstraintStatListEnsType.*;

public class bmr_ConstraintStatListEnsType {

	private static final String table_name = "Constraint_stat_list_ens";

	public static int write_xml(Connection conn_bmrb, Connection conn_tax, Connection conn_le, String entry_id, XmlOptions xml_opt, BufferedWriter buffw, FileWriter logw, FileWriter errw) {

		if (bmr_Util_Main.noatom)
			return 0;

		ConstraintStatListEnsType body = ConstraintStatListEnsType.Factory.newInstance();
		ConstraintStatListEns[] list = new ConstraintStatListEns[1];

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

				String query2 = new String("select * from \"" + table_name + "\" where \"Entry_ID\"='" + entry_id + "' order by (0 || \"Constraint_stat_list_ID\")::decimal");

				rset.close();

				rset = state.executeQuery(query2);

				int cols = rset.getMetaData().getColumnCount();
				int lines = 0;

				buffw.write("  <BMRBx:constraint_stat_list_ensCategory>\n");

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
							logw.write("category='ConstraintStatListEns', duplicated content of the category was excluded.\n");
						} catch (IOException e) {
							e.printStackTrace();
						}

						continue;
					}

					list[0] = ConstraintStatListEns.Factory.newInstance();

					if (!set_decimal(list[0], "setAllDistRmsd", "setNilAllDistRmsd", false, rset.getString("All_dist_rmsd"), logw))
						continue;
					if (!set_decimal(list[0], "setAllDistRmsdErr", "setNilAllDistRmsdErr", false, rset.getString("All_dist_rmsd_err"), logw))
						continue;
					if (!set_decimal(list[0], "setAmbIntermolDistRmsd", "setNilAmbIntermolDistRmsd", false, rset.getString("Amb_intermol_dist_rmsd"), logw))
						continue;
					if (!set_decimal(list[0], "setAmbIntermolDistRmsdErr", "setNilAmbIntermolDistRmsdErr", false, rset.getString("Amb_intermol_dist_rmsd_err"), logw))
						continue;
					if (!set_decimal(list[0], "setAmbIntramolDistRmsd", "setNilAmbIntramolDistRmsd", false, rset.getString("Amb_intramol_dist_rmsd"), logw))
						continue;
					if (!set_decimal(list[0], "setAmbIntramolDistRmsdErr", "setNilAmbIntramolDistRmsdErr", false, rset.getString("Amb_intramol_dist_rmsd_err"), logw))
						continue;
					if (!set_enum_constraint_stats_not_available(list[0], "setConstraintStatsNotAvailable", "setNilConstraintStatsNotAvailable", false, rset.getString("Constraint_stats_not_available"), logw, errw))
						continue;
					if (!set_decimal(list[0], "setDihedralAngleRmsd", "setNilDihedralAngleRmsd", false, rset.getString("Dihedral_angle_rmsd"), logw))
						continue;
					if (!set_decimal(list[0], "setDihedralAngleRmsdErr", "setNilDihedralAngleRmsdErr", false, rset.getString("Dihedral_angle_rmsd_err"), logw))
						continue;
					if (!set_string(list[0], "setDihedralConstStatCalcMeth", "setNilDihedralConstStatCalcMeth", false, rset.getString("Dihedral_const_stat_calc_meth"), logw))
						continue;
					if (!set_decimal(list[0], "setDihedralConstViolatAvg", "setNilDihedralConstViolatAvg", false, rset.getString("Dihedral_const_violat_avg"), logw))
						continue;
					if (!set_decimal(list[0], "setDihedralConstViolatMax", "setNilDihedralConstViolatMax", false, rset.getString("Dihedral_const_violat_max"), logw))
						continue;
					if (!set_decimal(list[0], "setDipolar13C13CRmsd", "setNilDipolar13C13CRmsd", false, rset.getString("Dipolar_13C_13C_rmsd"), logw))
						continue;
					if (!set_decimal(list[0], "setDipolar13C13CRmsdErr", "setNilDipolar13C13CRmsdErr", false, rset.getString("Dipolar_13C_13C_rmsd_err"), logw))
						continue;
					if (!set_decimal(list[0], "setDipolar1H13CRmsd", "setNilDipolar1H13CRmsd", false, rset.getString("Dipolar_1H_13C_rmsd"), logw))
						continue;
					if (!set_decimal(list[0], "setDipolar1H13CRmsdErr", "setNilDipolar1H13CRmsdErr", false, rset.getString("Dipolar_1H_13C_rmsd_err"), logw))
						continue;
					if (!set_decimal(list[0], "setDipolar1H15NRmsd", "setNilDipolar1H15NRmsd", false, rset.getString("Dipolar_1H_15N_rmsd"), logw))
						continue;
					if (!set_decimal(list[0], "setDipolar1H15NRmsdErr", "setNilDipolar1H15NRmsdErr", false, rset.getString("Dipolar_1H_15N_rmsd_err"), logw))
						continue;
					if (!set_decimal(list[0], "setDipolar1H1HRmsd", "setNilDipolar1H1HRmsd", false, rset.getString("Dipolar_1H_1H_rmsd"), logw))
						continue;
					if (!set_decimal(list[0], "setDipolar1H1HRmsdErr", "setNilDipolar1H1HRmsdErr", false, rset.getString("Dipolar_1H_1H_rmsd_err"), logw))
						continue;
					if (!set_decimal(list[0], "setDistConstraintViolationAvg", "setNilDistConstraintViolationAvg", false, rset.getString("Dist_constraint_violation_avg"), logw))
						continue;
					if (!set_decimal(list[0], "setDistConstraintViolationMax", "setNilDistConstraintViolationMax", false, rset.getString("Dist_constraint_violation_max"), logw))
						continue;
					if (!set_decimal(list[0], "setHydrogenBondRmsd", "setNilHydrogenBondRmsd", false, rset.getString("Hydrogen_bond_rmsd"), logw))
						continue;
					if (!set_decimal(list[0], "setHydrogenBondRmsdErr", "setNilHydrogenBondRmsdErr", false, rset.getString("Hydrogen_bond_rmsd_err"), logw))
						continue;
					if (!set_decimal(list[0], "setIntraresidueDistRmsd", "setNilIntraresidueDistRmsd", false, rset.getString("Intraresidue_dist_rmsd"), logw))
						continue;
					if (!set_decimal(list[0], "setIntraresidueDistRmsdErr", "setNilIntraresidueDistRmsdErr", false, rset.getString("Intraresidue_dist_rmsd_err"), logw))
						continue;
					if (!set_decimal(list[0], "setLongRangeDistRmsd", "setNilLongRangeDistRmsd", false, rset.getString("Long_range_dist_rmsd"), logw))
						continue;
					if (!set_decimal(list[0], "setLongRangeDistRmsdErr", "setNilLongRangeDistRmsdErr", false, rset.getString("Long_range_dist_rmsd_err"), logw))
						continue;
					if (!set_decimal(list[0], "setLowerDistConstrViolatMax", "setNilLowerDistConstrViolatMax", false, rset.getString("Lower_dist_constr_violat_max"), logw))
						continue;
					if (!set_decimal(list[0], "setSequentialDistRmsd", "setNilSequentialDistRmsd", false, rset.getString("Sequential_dist_rmsd"), logw))
						continue;
					if (!set_decimal(list[0], "setSequentialDistRmsdErr", "setNilSequentialDistRmsdErr", false, rset.getString("Sequential_dist_rmsd_err"), logw))
						continue;
					if (!set_decimal(list[0], "setShortRangeDistRmsd", "setNilShortRangeDistRmsd", false, rset.getString("Short_range_dist_rmsd"), logw))
						continue;
					if (!set_decimal(list[0], "setShortRangeDistRmsdErr", "setNilShortRangeDistRmsdErr", false, rset.getString("Short_range_dist_rmsd_err"), logw))
						continue;
					if (!set_decimal(list[0], "setUnambIntermolDistRmsd", "setNilUnambIntermolDistRmsd", false, rset.getString("Unamb_intermol_dist_rmsd"), logw))
						continue;
					if (!set_decimal(list[0], "setUnambIntermolDistRmsdErr", "setNilUnambIntermolDistRmsdErr", false, rset.getString("Unamb_intermol_dist_rmsd_err"), logw))
						continue;
					if (!set_decimal(list[0], "setUpperDistConstrViolatMax", "setNilUpperDistConstrViolatMax", false, rset.getString("Upper_dist_constr_violat_max"), logw))
						continue;
					if (!set_integer(list[0], "setConstraintStatListId", "", true, rset.getString("Constraint_stat_list_ID"), logw))
						continue;
					if (!set_string(list[0], "setEntryId", "", true, rset.getString("Entry_ID"), logw))
						continue;

					body.setConstraintStatListEnsArray(list);

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

				buffw.write("  </BMRBx:constraint_stat_list_ensCategory>\n");

			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_ConstraintStatListEnsType.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} catch (IOException ex) {

			Logger lgr = Logger.getLogger(bmr_ConstraintStatListEnsType.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_ConstraintStatListEnsType.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		return list_len;
	}

	private static boolean set_string(ConstraintStatListEns list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

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
				logw.write("item='" + method_name.substring(3) + "', category='ConstraintStatListEns', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ConstraintStatListEns', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_integer(ConstraintStatListEns list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml && (nil_method_name == null || nil_method_name.isEmpty()) && !method_name.equalsIgnoreCase("setSampleID") && !method_name.equalsIgnoreCase("setExperimentID") && !method_name.equalsIgnoreCase("setStudyID") && !method_name.equalsIgnoreCase("setAssemblyID") && (method_name.contains("Id") || method_name.contains("Number") || method_name.contains("Count")) && !method_name.contains("ListId") && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$")))
			val_name = "0";

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ConstraintStatListEns', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='ConstraintStatListEns', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_decimal(ConstraintStatListEns list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || (!val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)?$") && !val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)([eE][-+]?[0-9]+)?$"))) && (nil_method_name == null || nil_method_name.isEmpty())) {

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ConstraintStatListEns', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ConstraintStatListEns', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_enum_constraint_stats_not_available(ConstraintStatListEns list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ConstraintStatListEns', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ConstraintStatListEns', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ ConstraintStatListEns.ConstraintStatsNotAvailable.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					ConstraintStatListEns.ConstraintStatsNotAvailable.Enum _enum = ConstraintStatListEns.ConstraintStatsNotAvailable.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:ConstraintStatListEns method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= ConstraintStatListEns.ConstraintStatsNotAvailable.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + ConstraintStatListEns.ConstraintStatsNotAvailable.Enum.forInt(i));
						try {
							errw.write("class_name:ConstraintStatListEns method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= ConstraintStatListEns.ConstraintStatsNotAvailable.Enum.table.lastInt(); i++)
								errw.write(" enum:" + ConstraintStatListEns.ConstraintStatsNotAvailable.Enum.forInt(i) + "\n");
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
