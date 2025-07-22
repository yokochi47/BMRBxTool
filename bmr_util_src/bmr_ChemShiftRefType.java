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
import org.pdbj.bmrbpub.schema.mmcifNmrStar.ChemShiftRefType.*;

public class bmr_ChemShiftRefType {

	private static final String table_name = "Chem_shift_ref";

	public static int write_xml(Connection conn_bmrb, Connection conn_tax, Connection conn_le, String entry_id, XmlOptions xml_opt, BufferedWriter buffw, FileWriter logw, FileWriter errw) {

		ChemShiftRefType body = ChemShiftRefType.Factory.newInstance();
		ChemShiftRef[] list = new ChemShiftRef[1];

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

				String query2 = new String("select * from \"" + table_name + "\" where \"Entry_ID\"='" + entry_id + "' order by (0 || \"Chem_shift_reference_ID\")::decimal");

				rset.close();

				rset = state.executeQuery(query2);

				List<bmr_Util_ChemShiftRef> chem_shift_ref_list = new ArrayList<bmr_Util_ChemShiftRef>();

				while (rset.next())
					chem_shift_ref_list.add(new bmr_Util_ChemShiftRef(rset.getString("Atom_type"), rset.getString("Atom_isotope_number"), rset.getString("Indirect_shift_ratio")));

				for (int idx_13c = 0; idx_13c < chem_shift_ref_list.size(); idx_13c++) {

					bmr_Util_ChemShiftRef chem_shift_ref_13c = chem_shift_ref_list.get(idx_13c);

					if (chem_shift_ref_13c.atom_type == null || !chem_shift_ref_13c.atom_type.equalsIgnoreCase("C") || chem_shift_ref_13c.indirect_shift_ratio == null)
						continue;

					for (int idx_15n = 0; idx_15n < chem_shift_ref_list.size(); idx_15n++) {

						bmr_Util_ChemShiftRef chem_shift_ref_15n = chem_shift_ref_list.get(idx_15n);

						if (chem_shift_ref_15n.atom_type == null || !chem_shift_ref_15n.atom_type.equalsIgnoreCase("N") || chem_shift_ref_15n.indirect_shift_ratio == null)
							continue;

						if (chem_shift_ref_13c.indirect_shift_ratio == null || chem_shift_ref_13c.indirect_shift_ratio.isEmpty() || chem_shift_ref_13c.indirect_shift_ratio.equals(".") || chem_shift_ref_13c.indirect_shift_ratio.equals("?"))
							continue;

						if (chem_shift_ref_15n.indirect_shift_ratio == null || chem_shift_ref_15n.indirect_shift_ratio.isEmpty() || chem_shift_ref_15n.indirect_shift_ratio.equals(".") || chem_shift_ref_15n.indirect_shift_ratio.equals("?"))
							continue;

						float ratio_13c = Float.valueOf(chem_shift_ref_13c.indirect_shift_ratio);
						float ratio_15n = Float.valueOf(chem_shift_ref_15n.indirect_shift_ratio);

						if (bmr_Util_Main.remediate_xml && ratio_13c > 0.0 && ratio_15n > 0.0 && ratio_15n / ratio_13c > 2.3 && ratio_15n / ratio_13c < 2.7) {

							chem_shift_ref_13c.setAtomType("N");
							chem_shift_ref_13c.setAtomIsotopeNumber("15");

							chem_shift_ref_15n.setAtomType("C");
							chem_shift_ref_15n.setAtomIsotopeNumber("13");

						}
					}
				}

				rset = state.executeQuery(query2);

				int i = 0;

				int cols = rset.getMetaData().getColumnCount();
				int lines = 0;

				buffw.write("  <BMRBx:chem_shift_refCategory>\n");

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
							logw.write("category='ChemShiftRef', duplicated content of the category was excluded.\n");
						} catch (IOException e) {
							e.printStackTrace();
						}

						continue;
					}

					list[0] = ChemShiftRef.Factory.newInstance();

					bmr_Util_ChemShiftRef chem_shift_ref = chem_shift_ref_list.get(i++);

					if (!set_string(list[0], "setAtomGroup", "", true, rset.getString("Atom_group"), logw))
						continue;
					if (!set_enum_chem_shift_units(list[0], "setChemShiftUnits", "", true, rset.getString("Chem_shift_units"), logw, errw))
						continue;
					if (!set_decimal(list[0], "setChemShiftVal", "", true, rset.getString("Chem_shift_val"), logw))
						continue;
					if (!set_string(list[0], "setConcentrationUnits", "setNilConcentrationUnits", false, rset.getString("Concentration_units"), logw))
						continue;
					if (!set_decimal(list[0], "setConcentrationVal", "setNilConcentrationVal", false, rset.getString("Concentration_val"), logw))
						continue;
					if (!set_decimal(list[0], "setCorrectionVal", "setNilCorrectionVal", false, rset.getString("Correction_val"), logw))
						continue;
					if (!set_integer(list[0], "setCorrectionValCitId", "", false, rset.getString("Correction_val_cit_ID"), logw))
						continue;
					if (!set_string(list[0], "setCorrectionValCitLabel", "setNilCorrectionValCitLabel", false, rset.getString("Correction_val_cit_label"), logw))
						continue;
					if (!set_enum_external_ref_axis(list[0], "setExternalRefAxis", "setNilExternalRefAxis", false, rset.getString("External_ref_axis"), logw, errw))
						continue;
					if (!set_enum_external_ref_loc(list[0], "setExternalRefLoc", "setNilExternalRefLoc", false, rset.getString("External_ref_loc"), logw, errw))
						continue;
					if (!set_enum_external_ref_sample_geometry(list[0], "setExternalRefSampleGeometry", "setNilExternalRefSampleGeometry", false, rset.getString("External_ref_sample_geometry"), logw, errw))
						continue;
					if (!set_decimal(list[0], "setIndirectShiftRatio", "setNilIndirectShiftRatio", false, chem_shift_ref.indirect_shift_ratio, logw))
						continue;
					if (!set_integer(list[0], "setIndirectShiftRatioCitId", "", false, rset.getString("Indirect_shift_ratio_cit_ID"), logw))
						continue;
					if (!set_string(list[0], "setIndirectShiftRatioCitLabel", "setNilIndirectShiftRatioCitLabel", false, rset.getString("Indirect_shift_ratio_cit_label"), logw))
						continue;
					if (!set_string(list[0], "setRank", "setNilRank", false, rset.getString("Rank"), logw))
						continue;
					if (!set_string(list[0], "setRefCorrectionType", "setNilRefCorrectionType", false, rset.getString("Ref_correction_type"), logw))
						continue;
					if (!set_enum_ref_method(list[0], "setRefMethod", "setNilRefMethod", false, rset.getString("Ref_method"), logw, errw))
						continue;
					if (!set_enum_ref_type(list[0], "setRefType", "setNilRefType", false, rset.getString("Ref_type"), logw, errw))
						continue;
					if (!set_string(list[0], "setSolvent", "setNilSolvent", false, rset.getString("Solvent"), logw))
						continue;
					if (!set_integer(list[0], "setAtomIsotopeNumber", "", true, chem_shift_ref.atom_isotope_number, logw))
						continue;
					if (!set_enum_atom_type(list[0], "setAtomType", "", true, rset.getString("Atom_type"), logw, errw))
						continue;
					if (!set_integer(list[0], "setChemShiftReferenceId", "", true, rset.getString("Chem_shift_reference_ID"), logw))
						continue;
					if (!set_string(list[0], "setEntryId", "", true, rset.getString("Entry_ID"), logw))
						continue;
					if (!set_string(list[0], "setMolCommonName", "", true, rset.getString("Mol_common_name"), logw))
						continue;

					body.setChemShiftRefArray(list);

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

				buffw.write("  </BMRBx:chem_shift_refCategory>\n");

			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_ChemShiftRefType.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} catch (IOException ex) {

			Logger lgr = Logger.getLogger(bmr_ChemShiftRefType.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_ChemShiftRefType.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		return list_len;
	}

	private static boolean set_string(ChemShiftRef list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (method_name.equalsIgnoreCase("setAtomGroup")) {

			if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?"))) {

				if (val_name.equalsIgnoreCase("methyl  protons"))
					val_name = "methyl protons";

				if (val_name.equalsIgnoreCase("methl"))
					val_name = "methyl";

				if (val_name.equalsIgnoreCase("methly protons"))
					val_name = "methyl protons";

				if (val_name.equalsIgnoreCase("methy protons"))
					val_name = "methyl protons";

				if (val_name.equalsIgnoreCase("portons"))
					val_name = "protons";

				if (val_name.equalsIgnoreCase("Phosphade phasphorus"))
					val_name = "Phosphate phosphorus";

				if (val_name.equalsIgnoreCase("mehtyl protons"))
					val_name = "methyl protons";

				if (val_name.equalsIgnoreCase("ptotons"))
					val_name = "protons";

				if (val_name.equalsIgnoreCase("methly carbons"))
					val_name = "methyl carbons";

			} else
				val_name = "na";

		}

		if (bmr_Util_Main.remediate_xml && (nil_method_name == null || nil_method_name.isEmpty()) && !method_name.equalsIgnoreCase("setSampleState") && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")))
			val_name = "na";

		if ((method_name.contains("BiologicalFunction") || method_name.contains("Citation") || method_name.contains("Details") || method_name.contains("Name") || method_name.contains("Relationship") || method_name.contains("Task") || method_name.contains("Title")) && !(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && val_name.contains("\"")) {

			if ((val_name.startsWith("\"") && val_name.endsWith("\"")) || (((val_name.startsWith("\"") && val_name.lastIndexOf("\"") + 2 >= val_name.length()) || method_name.contains("Task")) && val_name.replaceAll("[^\"]", "").length() == 2) || (val_name.replaceAll("[^\"]", "").length() % 2) == 1)
				val_name = val_name.replaceAll("\"", "");

		}

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemShiftRef', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ChemShiftRef', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_integer(ChemShiftRef list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml && (nil_method_name == null || nil_method_name.isEmpty()) && !method_name.equalsIgnoreCase("setSampleID") && !method_name.equalsIgnoreCase("setExperimentID") && !method_name.equalsIgnoreCase("setStudyID") && !method_name.equalsIgnoreCase("setAssemblyID") && (method_name.contains("Id") || method_name.contains("Number") || method_name.contains("Count")) && !method_name.contains("ListId") && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$")))
			val_name = "0";

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemShiftRef', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='ChemShiftRef', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_decimal(ChemShiftRef list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || (!val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)?$") && !val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)([eE][-+]?[0-9]+)?$"))) && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (method_name.equalsIgnoreCase("setChemShiftVal"))
				val_name = "0.0";

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemShiftRef', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ChemShiftRef', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_enum_chem_shift_units(ChemShiftRef list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (val_name != null)
			val_name = bmr_Util_ChemShiftRef.getChemShiftUnits(val_name);

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?"))
			val_name = "na";

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemShiftRef', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ChemShiftRef', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ ChemShiftRef.ChemShiftUnits.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					ChemShiftRef.ChemShiftUnits.Enum _enum = ChemShiftRef.ChemShiftUnits.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:ChemShiftRef method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= ChemShiftRef.ChemShiftUnits.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + ChemShiftRef.ChemShiftUnits.Enum.forInt(i));
						try {
							errw.write("class_name:ChemShiftRef method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= ChemShiftRef.ChemShiftUnits.Enum.table.lastInt(); i++)
								errw.write(" enum:" + ChemShiftRef.ChemShiftUnits.Enum.forInt(i) + "\n");
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

	private static boolean set_enum_external_ref_axis(ChemShiftRef list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (val_name != null)
			val_name = bmr_Util_ChemShiftRef.getExternalRefAxis(val_name);

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemShiftRef', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ChemShiftRef', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ ChemShiftRef.ExternalRefAxis.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					ChemShiftRef.ExternalRefAxis.Enum _enum = ChemShiftRef.ExternalRefAxis.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:ChemShiftRef method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= ChemShiftRef.ExternalRefAxis.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + ChemShiftRef.ExternalRefAxis.Enum.forInt(i));
						try {
							errw.write("class_name:ChemShiftRef method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= ChemShiftRef.ExternalRefAxis.Enum.table.lastInt(); i++)
								errw.write(" enum:" + ChemShiftRef.ExternalRefAxis.Enum.forInt(i) + "\n");
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

	private static boolean set_enum_external_ref_loc(ChemShiftRef list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (val_name != null)
			val_name = bmr_Util_ChemShiftRef.getExternalRefLoc(val_name);

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemShiftRef', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ChemShiftRef', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ ChemShiftRef.ExternalRefLoc.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					ChemShiftRef.ExternalRefLoc.Enum _enum = ChemShiftRef.ExternalRefLoc.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:ChemShiftRef method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= ChemShiftRef.ExternalRefLoc.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + ChemShiftRef.ExternalRefLoc.Enum.forInt(i));
						try {
							errw.write("class_name:ChemShiftRef method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= ChemShiftRef.ExternalRefLoc.Enum.table.lastInt(); i++)
								errw.write(" enum:" + ChemShiftRef.ExternalRefLoc.Enum.forInt(i) + "\n");
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

	private static boolean set_enum_external_ref_sample_geometry(ChemShiftRef list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (val_name != null)
			val_name = bmr_Util_ChemShiftRef.getExternalRefSampleGeometry(val_name);

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemShiftRef', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ChemShiftRef', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ ChemShiftRef.ExternalRefSampleGeometry.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					ChemShiftRef.ExternalRefSampleGeometry.Enum _enum = ChemShiftRef.ExternalRefSampleGeometry.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:ChemShiftRef method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= ChemShiftRef.ExternalRefSampleGeometry.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + ChemShiftRef.ExternalRefSampleGeometry.Enum.forInt(i));
						try {
							errw.write("class_name:ChemShiftRef method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= ChemShiftRef.ExternalRefSampleGeometry.Enum.table.lastInt(); i++)
								errw.write(" enum:" + ChemShiftRef.ExternalRefSampleGeometry.Enum.forInt(i) + "\n");
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

	private static boolean set_enum_ref_method(ChemShiftRef list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (val_name != null)
			val_name = bmr_Util_ChemShiftRef.getRefMethod(val_name);

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemShiftRef', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ChemShiftRef', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ ChemShiftRef.RefMethod.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					ChemShiftRef.RefMethod.Enum _enum = ChemShiftRef.RefMethod.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:ChemShiftRef method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= ChemShiftRef.RefMethod.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + ChemShiftRef.RefMethod.Enum.forInt(i));
						try {
							errw.write("class_name:ChemShiftRef method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= ChemShiftRef.RefMethod.Enum.table.lastInt(); i++)
								errw.write(" enum:" + ChemShiftRef.RefMethod.Enum.forInt(i) + "\n");
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

	private static boolean set_enum_ref_type(ChemShiftRef list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (val_name != null)
			val_name = bmr_Util_ChemShiftRef.getRefType(val_name);

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemShiftRef', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ChemShiftRef', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ ChemShiftRef.RefType.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					ChemShiftRef.RefType.Enum _enum = ChemShiftRef.RefType.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:ChemShiftRef method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= ChemShiftRef.RefType.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + ChemShiftRef.RefType.Enum.forInt(i));
						try {
							errw.write("class_name:ChemShiftRef method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= ChemShiftRef.RefType.Enum.table.lastInt(); i++)
								errw.write(" enum:" + ChemShiftRef.RefType.Enum.forInt(i) + "\n");
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

	private static boolean set_enum_atom_type(ChemShiftRef list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (val_name != null)
			val_name = bmr_Util_ChemShiftRef.getAtomType(val_name);

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemShiftRef', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ChemShiftRef', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ ChemShiftRef.AtomType.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					ChemShiftRef.AtomType.Enum _enum = ChemShiftRef.AtomType.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:ChemShiftRef method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= ChemShiftRef.AtomType.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + ChemShiftRef.AtomType.Enum.forInt(i));
						try {
							errw.write("class_name:ChemShiftRef method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= ChemShiftRef.AtomType.Enum.table.lastInt(); i++)
								errw.write(" enum:" + ChemShiftRef.AtomType.Enum.forInt(i) + "\n");
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
