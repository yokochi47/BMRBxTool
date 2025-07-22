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
import org.pdbj.bmrbpub.schema.mmcifNmrStar.TheoreticalHeteronuclNoeType.*;

public class bmr_TheoreticalHeteronuclNoeType {

	private static final String table_name = "Theoretical_heteronucl_NOE";

	public static int write_xml(Connection conn_bmrb, Connection conn_tax, Connection conn_le, String entry_id, XmlOptions xml_opt, BufferedWriter buffw, FileWriter logw, FileWriter errw) {

		TheoreticalHeteronuclNoeType body = TheoreticalHeteronuclNoeType.Factory.newInstance();
		TheoreticalHeteronuclNoe[] list = new TheoreticalHeteronuclNoe[1];

		Statement state = null;
		ResultSet rset = null;

		String[] rcsv = null;

		String[] rid = null;

		int list_len = 0;

		try {

			String query = new String("select count(*) from \"" + table_name + "\" where \"Entry_ID\"='" + entry_id + "' group by \"Entry_ID\"");

			state = conn_bmrb.createStatement();
			rset = state.executeQuery(query);

			if (rset.next()) {

				list_len = Integer.parseInt(rset.getString(1));

				rcsv = new String[list_len];

				rid = new String[list_len];

				String query2 = new String("select * from \"" + table_name + "\" where \"Entry_ID\"='" + entry_id + "' order by (0 || \"Theoretical_heteronucl_NOE_list_ID\")::decimal,(0 || \"ID\")::decimal");

				rset.close();

				rset = state.executeQuery(query2);

				int cols = rset.getMetaData().getColumnCount();
				int lines = 0;

				buffw.write("  <BMRBx:theoretical_heteronucl_noeCategory>\n");

				StringBuilder sb = new StringBuilder();

				while (rset.next()) {

					for (int j = 1; j <= cols; j++) {
						String val = rset.getString(j);
						sb.append((val != null ? val : "") + ",");
					}

					rcsv[lines] = sb.toString();

					sb.setLength(0);

					int l;

					String id = rset.getString("ID");
					rid[lines] = id;

					if (id == null || id.isEmpty() || id.equals(".") || id.equals("?")) {
						if (lines == 0)
							rid[lines] = "1";
						else
							rid[lines] = String.valueOf(Integer.valueOf(rid[lines - 1]) + 1);
					}

					for (l = 0; l < lines; l++) {
						if (rid[lines].equals(rid[l]))
							break;
					}

					if (l < lines)
						rid[lines] = String.valueOf(Integer.valueOf(rid[lines - 1]) + 1);

					for (l = 0; l < lines; l++) {
						if (rcsv[lines].equals(rcsv[l]))
							break;
					}

					if (l < lines++) {

						try {
							logw.write("category='TheoreticalHeteronuclNoe', duplicated content of the category was excluded.\n");
						} catch (IOException e) {
							e.printStackTrace();
						}

						continue;
					}

					list[0] = TheoreticalHeteronuclNoe.Factory.newInstance();

					if (!set_integer(list[0], "setAssemblyAtomId1", "", true, rset.getString("Assembly_atom_ID_1"), logw))
						continue;
					if (!set_integer(list[0], "setAssemblyAtomId2", "", true, rset.getString("Assembly_atom_ID_2"), logw))
						continue;
					if (!set_string(list[0], "setAtomId1", "", true, rset.getString("Atom_ID_1"), logw))
						continue;
					if (!set_string(list[0], "setAtomId2", "", true, rset.getString("Atom_ID_2"), logw))
						continue;
					if (!set_integer(list[0], "setAtomIsotopeNumber1", "setNilAtomIsotopeNumber1", false, rset.getString("Atom_isotope_number_1"), logw))
						continue;
					if (!set_integer(list[0], "setAtomIsotopeNumber2", "setNilAtomIsotopeNumber2", false, rset.getString("Atom_isotope_number_2"), logw))
						continue;
					if (!set_string(list[0], "setAtomType1", "", true, rset.getString("Atom_type_1"), logw))
						continue;
					if (!set_string(list[0], "setAtomType2", "", true, rset.getString("Atom_type_2"), logw))
						continue;
					if (!set_string(list[0], "setAuthAtomId1", "setNilAuthAtomId1", false, rset.getString("Auth_atom_ID_1"), logw))
						continue;
					if (!set_string(list[0], "setAuthAtomId2", "setNilAuthAtomId2", false, rset.getString("Auth_atom_ID_2"), logw))
						continue;
					if (!set_string(list[0], "setAuthCompId1", "setNilAuthCompId1", false, rset.getString("Auth_comp_ID_1"), logw))
						continue;
					if (!set_string(list[0], "setAuthCompId2", "setNilAuthCompId2", false, rset.getString("Auth_comp_ID_2"), logw))
						continue;
					if (!set_string(list[0], "setAuthEntityAssemblyId1", "setNilAuthEntityAssemblyId1", false, rset.getString("Auth_entity_assembly_ID_1"), logw))
						continue;
					if (!set_string(list[0], "setAuthEntityAssemblyId2", "setNilAuthEntityAssemblyId2", false, rset.getString("Auth_entity_assembly_ID_2"), logw))
						continue;
					if (!set_string(list[0], "setAuthSeqId1", "setNilAuthSeqId1", false, rset.getString("Auth_seq_ID_1"), logw))
						continue;
					if (!set_string(list[0], "setAuthSeqId2", "setNilAuthSeqId2", false, rset.getString("Auth_seq_ID_2"), logw))
						continue;
					if (!set_string(list[0], "setCompId1", "", true, rset.getString("Comp_ID_1"), logw))
						continue;
					if (!set_string(list[0], "setCompId2", "", true, rset.getString("Comp_ID_2"), logw))
						continue;
					if (!set_integer(list[0], "setCompIndexId1", "", true, rset.getString("Comp_index_ID_1"), logw))
						continue;
					if (!set_integer(list[0], "setCompIndexId2", "", true, rset.getString("Comp_index_ID_2"), logw))
						continue;
					if (!set_integer(list[0], "setEntityAssemblyId1", "", true, rset.getString("Entity_assembly_ID_1"), logw))
						continue;
					if (!set_integer(list[0], "setEntityAssemblyId2", "", true, rset.getString("Entity_assembly_ID_2"), logw))
						continue;
					if (!set_integer(list[0], "setEntityId1", "", true, rset.getString("Entity_ID_1"), logw))
						continue;
					if (!set_integer(list[0], "setEntityId2", "", true, rset.getString("Entity_ID_2"), logw))
						continue;
					if (!set_integer(list[0], "setResonanceId1", "", true, rset.getString("Resonance_ID_1"), logw))
						continue;
					if (!set_integer(list[0], "setResonanceId2", "", true, rset.getString("Resonance_ID_2"), logw))
						continue;
					if (!set_integer(list[0], "setSeqId1", "", true, rset.getString("Seq_ID_1"), logw))
						continue;
					if (!set_integer(list[0], "setSeqId2", "", true, rset.getString("Seq_ID_2"), logw))
						continue;
					if (!set_decimal(list[0], "setVal", "", true, rset.getString("Val"), logw))
						continue;
					if (!set_decimal(list[0], "setValErr", "setNilValErr", false, rset.getString("Val_err"), logw))
						continue;
					if (!set_string(list[0], "setEntryId", "", true, rset.getString("Entry_ID"), logw))
						continue;
					if (!set_integer(list[0], "setId", "", true, rid[lines - 1], logw))
						continue;
					if (!set_integer(list[0], "setTheoreticalHeteronuclNoeListId", "", true, rset.getString("Theoretical_heteronucl_NOE_list_ID"), logw))
						continue;

					body.setTheoreticalHeteronuclNoeArray(list);

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

				buffw.write("  </BMRBx:theoretical_heteronucl_noeCategory>\n");

			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_TheoreticalHeteronuclNoeType.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} catch (IOException ex) {

			Logger lgr = Logger.getLogger(bmr_TheoreticalHeteronuclNoeType.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_TheoreticalHeteronuclNoeType.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		return list_len;
	}

	private static boolean set_string(TheoreticalHeteronuclNoe list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

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
				logw.write("item='" + method_name.substring(3) + "', category='TheoreticalHeteronuclNoe', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='TheoreticalHeteronuclNoe', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_integer(TheoreticalHeteronuclNoe list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml && (nil_method_name == null || nil_method_name.isEmpty()) && !method_name.equalsIgnoreCase("setSampleID") && !method_name.equalsIgnoreCase("setExperimentID") && !method_name.equalsIgnoreCase("setStudyID") && !method_name.equalsIgnoreCase("setAssemblyID") && (method_name.contains("Id") || method_name.contains("Number") || method_name.contains("Count")) && !method_name.contains("ListId") && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$")))
			val_name = "0";

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='TheoreticalHeteronuclNoe', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='TheoreticalHeteronuclNoe', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_decimal(TheoreticalHeteronuclNoe list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || (!val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)?$") && !val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)([eE][-+]?[0-9]+)?$"))) && (nil_method_name == null || nil_method_name.isEmpty())) {

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='TheoreticalHeteronuclNoe', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='TheoreticalHeteronuclNoe', value='" + _val_name + "' was empty, but not nillable.\n");
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
}
