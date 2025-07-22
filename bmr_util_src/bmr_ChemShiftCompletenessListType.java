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
import java.util.*;
import java.util.logging.*;
import java.sql.*;

import org.apache.xmlbeans.*;
import org.pdbj.bmrbpub.schema.mmcifNmrStar.ChemShiftCompletenessListType.*;

public class bmr_ChemShiftCompletenessListType {

	private static final String table_name = "Chem_shift_completeness_list";

	public static int write_xml(Connection conn_bmrb, Connection conn_tax, Connection conn_le, String entry_id, XmlOptions xml_opt, BufferedWriter buffw, FileWriter logw, FileWriter errw) {

		ChemShiftCompletenessListType body = ChemShiftCompletenessListType.Factory.newInstance();
		ChemShiftCompletenessList[] list = new ChemShiftCompletenessList[1];

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

				String query2 = new String("select * from \"" + table_name + "\" where \"Entry_ID\"='" + entry_id + "' order by (0 || \"Assigned_chem_shift_list_ID\")::decimal");

				rset.close();

				rset = state.executeQuery(query2);

				int cols = rset.getMetaData().getColumnCount();
				int lines = 0;

				buffw.write("  <BMRBx:chem_shift_completeness_listCategory>\n");

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
							logw.write("category='ChemShiftCompletenessList', duplicated content of the category was excluded.\n");
						} catch (IOException e) {
							e.printStackTrace();
						}

						continue;
					}

					list[0] = ChemShiftCompletenessList.Factory.newInstance();

					if (!set_string(list[0], "setAromChemShift13CFraction", "setNilAromChemShift13CFraction", false, rset.getString("Arom_chem_shift_13C_fraction"), logw))
						continue;
					if (!set_string(list[0], "setAromChemShift15NFraction", "setNilAromChemShift15NFraction", false, rset.getString("Arom_chem_shift_15N_fraction"), logw))
						continue;
					if (!set_string(list[0], "setAromChemShift1HFraction", "setNilAromChemShift1HFraction", false, rset.getString("Arom_chem_shift_1H_fraction"), logw))
						continue;
					if (!set_string(list[0], "setAromChemShiftFraction", "setNilAromChemShiftFraction", false, rset.getString("Arom_chem_shift_fraction"), logw))
						continue;
					if (!set_decimal(list[0], "setAssignedResidueCoverage", "setNilAssignedResidueCoverage", false, rset.getString("Assigned_residue_coverage"), logw))
						continue;
					if (!set_string(list[0], "setBbChemShift13CFraction", "setNilBbChemShift13CFraction", false, rset.getString("Bb_chem_shift_13C_fraction"), logw))
						continue;
					if (!set_string(list[0], "setBbChemShift15NFraction", "setNilBbChemShift15NFraction", false, rset.getString("Bb_chem_shift_15N_fraction"), logw))
						continue;
					if (!set_string(list[0], "setBbChemShift1HFraction", "setNilBbChemShift1HFraction", false, rset.getString("Bb_chem_shift_1H_fraction"), logw))
						continue;
					if (!set_string(list[0], "setBbChemShift31PFraction", "setNilBbChemShift31PFraction", false, rset.getString("Bb_chem_shift_31P_fraction"), logw))
						continue;
					if (!set_string(list[0], "setBbChemShiftFraction", "setNilBbChemShiftFraction", false, rset.getString("Bb_chem_shift_fraction"), logw))
						continue;
					if (!set_string(list[0], "setChemShift13CFraction", "setNilChemShift13CFraction", false, rset.getString("Chem_shift_13C_fraction"), logw))
						continue;
					if (!set_string(list[0], "setChemShift15NFraction", "setNilChemShift15NFraction", false, rset.getString("Chem_shift_15N_fraction"), logw))
						continue;
					if (!set_string(list[0], "setChemShift1HFraction", "setNilChemShift1HFraction", false, rset.getString("Chem_shift_1H_fraction"), logw))
						continue;
					if (!set_string(list[0], "setChemShift31PFraction", "setNilChemShift31PFraction", false, rset.getString("Chem_shift_31P_fraction"), logw))
						continue;
					if (!set_string(list[0], "setChemShiftFraction", "setNilChemShiftFraction", false, rset.getString("Chem_shift_fraction"), logw))
						continue;
					if (!set_string(list[0], "setElectronicAddress", "setNilElectronicAddress", false, rset.getString("Electronic_address"), logw))
						continue;
					if (!set_string(list[0], "setEntityPolymerType", "setNilEntityPolymerType", false, rset.getString("Entity_polymer_type"), logw))
						continue;
					if (!set_string(list[0], "setMethylChemShift13CFraction", "setNilMethylChemShift13CFraction", false, rset.getString("Methyl_chem_shift_13C_fraction"), logw))
						continue;
					if (!set_string(list[0], "setMethylChemShift1HFraction", "setNilMethylChemShift1HFraction", false, rset.getString("Methyl_chem_shift_1H_fraction"), logw))
						continue;
					if (!set_string(list[0], "setMethylChemShiftFraction", "setNilMethylChemShiftFraction", false, rset.getString("Methyl_chem_shift_fraction"), logw))
						continue;
					if (!set_string(list[0], "setOutputFileName", "setNilOutputFileName", false, rset.getString("Output_file_name"), logw))
						continue;
					if (!set_date(list[0], "setQueriedDate", "setNilQueriedDate", false, rset.getString("Queried_date") != null && !rset.getString("Queried_date").equals(".") && !rset.getString("Queried_date").equals("?") ? rset.getDate("Queried_date") : null, logw))
						continue;
					if (!set_string(list[0], "setScChemShift13CFraction", "setNilScChemShift13CFraction", false, rset.getString("Sc_chem_shift_13C_fraction"), logw))
						continue;
					if (!set_string(list[0], "setScChemShift15NFraction", "setNilScChemShift15NFraction", false, rset.getString("Sc_chem_shift_15N_fraction"), logw))
						continue;
					if (!set_string(list[0], "setScChemShift1HFraction", "setNilScChemShift1HFraction", false, rset.getString("Sc_chem_shift_1H_fraction"), logw))
						continue;
					if (!set_string(list[0], "setScChemShiftFraction", "setNilScChemShiftFraction", false, rset.getString("Sc_chem_shift_fraction"), logw))
						continue;
					if (!set_string(list[0], "setSfCategory", "", true, rset.getString("Sf_category"), logw))
						continue;
					if (!set_string(list[0], "setSfFramecode", "", true, rset.getString("Sf_framecode"), logw))
						continue;
					if (!set_integer(list[0], "setSfId", "setNilSfId", false, rset.getString("Sf_ID"), logw))
						continue;
					if (!set_integer(list[0], "setAssignedChemShiftListId", "", true, rset.getString("Assigned_chem_shift_list_ID"), logw))
						continue;
					if (!set_string(list[0], "setEntryId", "", true, rset.getString("Entry_ID"), logw))
						continue;

					body.setChemShiftCompletenessListArray(list);

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

				buffw.write("  </BMRBx:chem_shift_completeness_listCategory>\n");

			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_ChemShiftCompletenessListType.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} catch (IOException ex) {

			Logger lgr = Logger.getLogger(bmr_ChemShiftCompletenessListType.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_ChemShiftCompletenessListType.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		return list_len;
	}

	private static boolean set_string(ChemShiftCompletenessList list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

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
				logw.write("item='" + method_name.substring(3) + "', category='ChemShiftCompletenessList', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ChemShiftCompletenessList', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_integer(ChemShiftCompletenessList list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml && (nil_method_name == null || nil_method_name.isEmpty()) && !method_name.equalsIgnoreCase("setSampleID") && !method_name.equalsIgnoreCase("setExperimentID") && !method_name.equalsIgnoreCase("setStudyID") && !method_name.equalsIgnoreCase("setAssemblyID") && (method_name.contains("Id") || method_name.contains("Number") || method_name.contains("Count")) && !method_name.contains("ListId") && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$")))
			val_name = "0";

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemShiftCompletenessList', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='ChemShiftCompletenessList', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_decimal(ChemShiftCompletenessList list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || (!val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)?$") && !val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)([eE][-+]?[0-9]+)?$"))) && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (method_name.equalsIgnoreCase("setSpectrometerFrequency1H"))
				val_name = "0.0";

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemShiftCompletenessList', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ChemShiftCompletenessList', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_date(ChemShiftCompletenessList list, String method_name, String nil_method_name, boolean required, java.sql.Date date, FileWriter logw) {

		boolean nil = false;

		if (date == null)
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='ChemShiftCompletenessList', value='" + date + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ Calendar.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					Calendar cal = bmr_Util_Date.sqldate2calendar(date);
					if (cal == null)
						return false;
					method.invoke(list, cal);
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
