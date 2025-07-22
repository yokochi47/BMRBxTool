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
import org.pdbj.bmrbpub.schema.mmcifNmrStar.AtomSiteType.*;

public class bmr_AtomSiteType {

	private static final String table_name = "Atom_site";

	public static int write_xml(Connection conn_bmrb, Connection conn_tax, Connection conn_le, String entry_id, XmlOptions xml_opt, BufferedWriter buffw, FileWriter logw, FileWriter errw) {

		if (bmr_Util_Main.noatom)
			return 0;

		AtomSiteType body = AtomSiteType.Factory.newInstance();
		AtomSite[] list = new AtomSite[1];

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

				String query2 = new String("select * from \"" + table_name + "\" where \"Entry_ID\"='" + entry_id + "' order by (0 || \"ID\")::decimal");

				rset.close();

				rset = state.executeQuery(query2);

				int cols = rset.getMetaData().getColumnCount();
				int lines = 0;

				buffw.write("  <BMRBx:atom_siteCategory>\n");

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
							logw.write("category='AtomSite', duplicated content of the category was excluded.\n");
						} catch (IOException e) {
							e.printStackTrace();
						}

						continue;
					}

					list[0] = AtomSite.Factory.newInstance();

					if (!set_integer(list[0], "setAssemblyAtomId", "", false, rset.getString("Assembly_atom_ID"), logw))
						continue;
					if (!set_integer(list[0], "setAssemblyId", "setNilAssemblyId", false, rset.getString("Assembly_ID"), logw))
						continue;
					if (!set_string(list[0], "setAuthAltId", "setNilAuthAltId", false, rset.getString("Auth_alt_ID"), logw))
						continue;
					if (!set_string(list[0], "setAuthAsymId", "setNilAuthAsymId", false, rset.getString("Auth_asym_ID"), logw))
						continue;
					if (!set_string(list[0], "setAuthAtomId", "setNilAuthAtomId", false, rset.getString("Auth_atom_ID"), logw))
						continue;
					if (!set_string(list[0], "setAuthAtomName", "setNilAuthAtomName", false, rset.getString("Auth_atom_name"), logw))
						continue;
					if (!set_string(list[0], "setAuthChainId", "setNilAuthChainId", false, rset.getString("Auth_chain_ID"), logw))
						continue;
					if (!set_string(list[0], "setAuthCompId", "setNilAuthCompId", false, rset.getString("Auth_comp_ID"), logw))
						continue;
					if (!set_integer(list[0], "setAuthEntityAssemblyId", "setNilAuthEntityAssemblyId", false, rset.getString("Auth_entity_assembly_ID"), logw))
						continue;
					if (!set_string(list[0], "setAuthSeqId", "setNilAuthSeqId", false, rset.getString("Auth_seq_ID"), logw))
						continue;
					if (!set_decimal(list[0], "setCartnX", "", true, rset.getString("Cartn_x"), logw))
						continue;
					if (!set_decimal(list[0], "setCartnXEsd", "setNilCartnXEsd", false, rset.getString("Cartn_x_esd"), logw))
						continue;
					if (!set_decimal(list[0], "setCartnY", "", true, rset.getString("Cartn_y"), logw))
						continue;
					if (!set_decimal(list[0], "setCartnYEsd", "setNilCartnYEsd", false, rset.getString("Cartn_y_esd"), logw))
						continue;
					if (!set_decimal(list[0], "setCartnZ", "", true, rset.getString("Cartn_z"), logw))
						continue;
					if (!set_decimal(list[0], "setCartnZEsd", "setNilCartnZEsd", false, rset.getString("Cartn_z_esd"), logw))
						continue;
					if (!set_string(list[0], "setDetails", "setNilDetails", false, rset.getString("Details"), logw))
						continue;
					if (!set_integer(list[0], "setFootnoteId", "setNilFootnoteId", false, rset.getString("Footnote_ID"), logw))
						continue;
					if (!set_string(list[0], "setLabelAtomId", "", true, rset.getString("Label_atom_ID"), logw))
						continue;
					if (!set_string(list[0], "setLabelCompId", "", true, rset.getString("Label_comp_ID"), logw))
						continue;
					if (!set_integer(list[0], "setLabelCompIndexId", "", true, rset.getString("Label_comp_index_ID"), logw))
						continue;
					if (!set_integer(list[0], "setLabelEntityAssemblyId", "", true, rset.getString("Label_entity_assembly_ID"), logw))
						continue;
					if (!set_integer(list[0], "setLabelEntityId", "", true, rset.getString("Label_entity_ID"), logw))
						continue;
					if (!set_integer(list[0], "setModelId", "", true, rset.getString("Model_ID"), logw))
						continue;
					if (!set_integer(list[0], "setModelSiteId", "setNilModelSiteId", false, rset.getString("Model_site_ID"), logw))
						continue;
					if (!set_decimal(list[0], "setOccupancy", "setNilOccupancy", false, rset.getString("Occupancy"), logw))
						continue;
					if (!set_decimal(list[0], "setOccupancyEsd", "setNilOccupancyEsd", false, rset.getString("Occupancy_esd"), logw))
						continue;
					if (!set_enum_ordered_flag(list[0], "setOrderedFlag", "setNilOrderedFlag", false, rset.getString("Ordered_flag"), logw, errw))
						continue;
					if (!set_string(list[0], "setPdbAtomName", "setNilPdbAtomName", false, rset.getString("PDB_atom_name"), logw))
						continue;
					if (!set_string(list[0], "setPdbInsCode", "setNilPdbInsCode", false, rset.getString("PDB_ins_code"), logw))
						continue;
					if (!set_integer(list[0], "setPdbModelNum", "setNilPdbModelNum", false, rset.getString("PDB_model_num"), logw))
						continue;
					if (!set_string(list[0], "setPdbRecordId", "setNilPdbRecordId", false, rset.getString("PDB_record_ID"), logw))
						continue;
					if (!set_string(list[0], "setPdbResidueName", "setNilPdbResidueName", false, rset.getString("PDB_residue_name"), logw))
						continue;
					if (!set_string(list[0], "setPdbResidueNo", "setNilPdbResidueNo", false, rset.getString("PDB_residue_no"), logw))
						continue;
					if (!set_string(list[0], "setPdbStrandId", "setNilPdbStrandId", false, rset.getString("PDB_strand_ID"), logw))
						continue;
					if (!set_integer(list[0], "setPdbxFormalCharge", "setNilPdbxFormalCharge", false, rset.getString("PDBX_formal_charge"), logw))
						continue;
					if (!set_string(list[0], "setPdbxLabelAsymId", "", true, rset.getString("PDBX_label_asym_ID"), logw))
						continue;
					if (!set_string(list[0], "setPdbxLabelAtomId", "setNilPdbxLabelAtomId", false, rset.getString("PDBX_label_atom_ID"), logw))
						continue;
					if (!set_string(list[0], "setPdbxLabelCompId", "", true, rset.getString("PDBX_label_comp_ID"), logw))
						continue;
					if (!set_string(list[0], "setPdbxLabelEntityId", "setNilPdbxLabelEntityId", false, rset.getString("PDBX_label_entity_ID"), logw))
						continue;
					if (!set_integer(list[0], "setPdbxLabelSeqId", "setNilPdbxLabelSeqId", false, rset.getString("PDBX_label_seq_ID"), logw))
						continue;
					if (!set_string(list[0], "setTypeSymbol", "", true, rset.getString("Type_symbol"), logw))
						continue;
					if (!set_decimal(list[0], "setUncertainty", "setNilUncertainty", false, rset.getString("Uncertainty"), logw))
						continue;
					if (!set_integer(list[0], "setConformerFamilyCoordSetId", "", true, rset.getString("Conformer_family_coord_set_ID"), logw))
						continue;
					if (!set_string(list[0], "setEntryId", "", true, rset.getString("Entry_ID"), logw))
						continue;
					if (!set_integer(list[0], "setId", "", true, rid[lines - 1], logw))
						continue;

					body.setAtomSiteArray(list);

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

				buffw.write("  </BMRBx:atom_siteCategory>\n");

			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_AtomSiteType.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} catch (IOException ex) {

			Logger lgr = Logger.getLogger(bmr_AtomSiteType.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_AtomSiteType.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		return list_len;
	}

	private static boolean set_string(AtomSite list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

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
				logw.write("item='" + method_name.substring(3) + "', category='AtomSite', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='AtomSite', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_integer(AtomSite list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml && (nil_method_name == null || nil_method_name.isEmpty()) && !method_name.equalsIgnoreCase("setSampleID") && !method_name.equalsIgnoreCase("setExperimentID") && !method_name.equalsIgnoreCase("setStudyID") && !method_name.equalsIgnoreCase("setAssemblyID") && (method_name.contains("Id") || method_name.contains("Number") || method_name.contains("Count")) && !method_name.contains("ListId") && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$")))
			val_name = "0";

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='AtomSite', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='AtomSite', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_decimal(AtomSite list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || (!val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)?$") && !val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)([eE][-+]?[0-9]+)?$"))) && (nil_method_name == null || nil_method_name.isEmpty())) {

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='AtomSite', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='AtomSite', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_enum_ordered_flag(AtomSite list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='AtomSite', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='AtomSite', value='" + _val_name + "' was empty, but not nillable.\n");
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
}
