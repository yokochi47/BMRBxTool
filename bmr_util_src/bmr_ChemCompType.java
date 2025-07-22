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
import org.pdbj.bmrbpub.schema.mmcifNmrStar.ChemCompType.*;

public class bmr_ChemCompType {

	private static final String table_name = "Chem_comp";

	public static int write_xml(Connection conn_bmrb, Connection conn_tax, Connection conn_le, String entry_id, XmlOptions xml_opt, BufferedWriter buffw, FileWriter logw, FileWriter errw) {

		ChemCompType body = ChemCompType.Factory.newInstance();
		ChemComp[] list = new ChemComp[1];

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

				String query2 = new String("select * from \"" + table_name + "\" where \"Entry_ID\"='" + entry_id + "'");

				rset.close();

				rset = state.executeQuery(query2);

				int cols = rset.getMetaData().getColumnCount();
				int lines = 0;

				buffw.write("  <BMRBx:chem_compCategory>\n");

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
							logw.write("category='ChemComp', duplicated content of the category was excluded.\n");
						} catch (IOException e) {
							e.printStackTrace();
						}

						continue;
					}

					list[0] = ChemComp.Factory.newInstance();

					if (!set_enum_ambiguous_flag(list[0], "setAmbiguousFlag", "setNilAmbiguousFlag", false, rset.getString("Ambiguous_flag"), logw, errw))
						continue;
					if (!set_enum_aromatic(list[0], "setAromatic", "setNilAromatic", false, rset.getString("Aromatic"), logw, errw))
						continue;
					if (!set_enum_atom_nomenclature_source(list[0], "setAtomNomenclatureSource", "setNilAtomNomenclatureSource", false, rset.getString("Atom_nomenclature_source"), logw, errw))
						continue;
					if (!set_string(list[0], "setBmrbCode", "setNilBmrbCode", false, rset.getString("BMRB_code"), logw))
						continue;
					if (!set_date(list[0], "setDbLastQueryRevisedLastDate", "setNilDbLastQueryRevisedLastDate", false, rset.getString("DB_last_query_revised_last_date") != null && !rset.getString("DB_last_query_revised_last_date").equals(".") && !rset.getString("DB_last_query_revised_last_date").equals("?") ? rset.getDate("DB_last_query_revised_last_date") : null, logw))
						continue;
					if (!set_date(list[0], "setDbQueryDate", "setNilDbQueryDate", false, rset.getString("DB_query_date") != null && !rset.getString("DB_query_date").equals(".") && !rset.getString("DB_query_date").equals("?") ? rset.getDate("DB_query_date") : null, logw))
						continue;
					if (!set_string(list[0], "setDetails", "setNilDetails", false, rset.getString("Details"), logw))
						continue;
					if (!set_enum_formal_charge(list[0], "setFormalCharge", "setNilFormalCharge", false, rset.getString("Formal_charge"), logw, errw))
						continue;
					if (!set_string(list[0], "setFormula", "setNilFormula", false, rset.getString("Formula"), logw))
						continue;
					if (!set_decimal(list[0], "setFormulaMonoIsoWt13C", "setNilFormulaMonoIsoWt13C", false, rset.getString("Formula_mono_iso_wt_13C"), logw))
						continue;
					if (!set_decimal(list[0], "setFormulaMonoIsoWt13C15N", "setNilFormulaMonoIsoWt13C15N", false, rset.getString("Formula_mono_iso_wt_13C_15N"), logw))
						continue;
					if (!set_decimal(list[0], "setFormulaMonoIsoWt15N", "setNilFormulaMonoIsoWt15N", false, rset.getString("Formula_mono_iso_wt_15N"), logw))
						continue;
					if (!set_decimal(list[0], "setFormulaMonoIsoWtNat", "setNilFormulaMonoIsoWtNat", false, rset.getString("Formula_mono_iso_wt_nat"), logw))
						continue;
					if (!set_decimal(list[0], "setFormulaWeight", "setNilFormulaWeight", false, rset.getString("Formula_weight"), logw))
						continue;
					if (!set_string(list[0], "setIdealCoordinatesDetails", "setNilIdealCoordinatesDetails", false, rset.getString("Ideal_coordinates_details"), logw))
						continue;
					if (!set_enum_ideal_coordinates_missing_flag(list[0], "setIdealCoordinatesMissingFlag", "setNilIdealCoordinatesMissingFlag", false, rset.getString("Ideal_coordinates_missing_flag"), logw, errw))
						continue;
					if (!set_enum_image_file_format(list[0], "setImageFileFormat", "setNilImageFileFormat", false, rset.getString("Image_file_format"), logw, errw))
						continue;
					if (!set_string(list[0], "setImageFileName", "setNilImageFileName", false, rset.getString("Image_file_name"), logw))
						continue;
					if (!set_string(list[0], "setInchiCode", "setNilInchiCode", false, rset.getString("InChI_code"), logw))
						continue;
					if (!set_date(list[0], "setInitialDate", "setNilInitialDate", false, rset.getString("Initial_date") != null && !rset.getString("Initial_date").equals(".") && !rset.getString("Initial_date").equals("?") ? rset.getDate("Initial_date") : null, logw))
						continue;
					if (!set_string(list[0], "setModelCoordinatesDbCode", "setNilModelCoordinatesDbCode", false, rset.getString("Model_coordinates_db_code"), logw))
						continue;
					if (!set_string(list[0], "setModelCoordinatesDetails", "setNilModelCoordinatesDetails", false, rset.getString("Model_coordinates_details"), logw))
						continue;
					if (!set_enum_model_coordinates_missing_flag(list[0], "setModelCoordinatesMissingFlag", "setNilModelCoordinatesMissingFlag", false, rset.getString("Model_coordinates_missing_flag"), logw, errw))
						continue;
					if (!set_string(list[0], "setModelDetails", "setNilModelDetails", false, rset.getString("Model_details"), logw))
						continue;
					if (!set_string(list[0], "setModelErf", "setNilModelErf", false, rset.getString("Model_erf"), logw))
						continue;
					if (!set_string(list[0], "setModelSource", "setNilModelSource", false, rset.getString("Model_source"), logw))
						continue;
					if (!set_date(list[0], "setModifiedDate", "setNilModifiedDate", false, rset.getString("Modified_date") != null && !rset.getString("Modified_date").equals(".") && !rset.getString("Modified_date").equals("?") ? rset.getDate("Modified_date") : null, logw))
						continue;
					if (!set_string(list[0], "setMonNstdClass", "setNilMonNstdClass", false, rset.getString("Mon_nstd_class"), logw))
						continue;
					if (!set_string(list[0], "setMonNstdDetails", "setNilMonNstdDetails", false, rset.getString("Mon_nstd_details"), logw))
						continue;
					if (!set_enum_mon_nstd_flag(list[0], "setMonNstdFlag", "setNilMonNstdFlag", false, rset.getString("Mon_nstd_flag"), logw, errw))
						continue;
					if (!set_string(list[0], "setMonNstdParent", "setNilMonNstdParent", false, rset.getString("Mon_nstd_parent"), logw))
						continue;
					if (!set_string(list[0], "setMonNstdParentCompId", "setNilMonNstdParentCompId", false, rset.getString("Mon_nstd_parent_comp_ID"), logw))
						continue;
					if (!set_string(list[0], "setName", "", true, rset.getString("Name"), logw))
						continue;
					if (!set_integer(list[0], "setNumberAtomsAll", "setNilNumberAtomsAll", false, rset.getString("Number_atoms_all"), logw))
						continue;
					if (!set_integer(list[0], "setNumberAtomsNh", "setNilNumberAtomsNh", false, rset.getString("Number_atoms_nh"), logw))
						continue;
					if (!set_string(list[0], "setOneLetterCode", "setNilOneLetterCode", false, rset.getString("One_letter_code"), logw))
						continue;
					if (!set_enum_paramagnetic(list[0], "setParamagnetic", "setNilParamagnetic", false, rset.getString("Paramagnetic"), logw, errw))
						continue;
					if (!set_string(list[0], "setPdbCode", "setNilPdbCode", false, rset.getString("PDB_code"), logw))
						continue;
					if (!set_enum_processing_site(list[0], "setProcessingSite", "setNilProcessingSite", false, rset.getString("Processing_site"), logw, errw))
						continue;
					if (!set_enum_provenance(list[0], "setProvenance", "", true, rset.getString("Provenance"), logw, errw))
						continue;
					if (!set_string(list[0], "setPubchemCode", "setNilPubchemCode", false, rset.getString("PubChem_code"), logw))
						continue;
					if (!set_enum_release_status(list[0], "setReleaseStatus", "setNilReleaseStatus", false, rset.getString("Release_status"), logw, errw))
						continue;
					if (!set_string(list[0], "setReplacedBy", "setNilReplacedBy", false, rset.getString("Replaced_by"), logw))
						continue;
					if (!set_string(list[0], "setReplaces", "setNilReplaces", false, rset.getString("Replaces"), logw))
						continue;
					if (!set_string(list[0], "setSfCategory", "", true, rset.getString("Sf_category"), logw))
						continue;
					if (!set_string(list[0], "setSfFramecode", "", true, rset.getString("Sf_framecode"), logw))
						continue;
					if (!set_string(list[0], "setStdDerivBmrbCode", "setNilStdDerivBmrbCode", false, rset.getString("Std_deriv_BMRB_code"), logw))
						continue;
					if (!set_string(list[0], "setStdDerivChemCompName", "setNilStdDerivChemCompName", false, rset.getString("Std_deriv_chem_comp_name"), logw))
						continue;
					if (!set_string(list[0], "setStdDerivOneLetterCode", "setNilStdDerivOneLetterCode", false, rset.getString("Std_deriv_one_letter_code"), logw))
						continue;
					if (!set_string(list[0], "setStdDerivPdbCode", "setNilStdDerivPdbCode", false, rset.getString("Std_deriv_PDB_code"), logw))
						continue;
					if (!set_string(list[0], "setStdDerivThreeLetterCode", "setNilStdDerivThreeLetterCode", false, rset.getString("Std_deriv_three_letter_code"), logw))
						continue;
					if (!set_string(list[0], "setStereochemParamFileFormat", "setNilStereochemParamFileFormat", false, rset.getString("Stereochem_param_file_format"), logw))
						continue;
					if (!set_string(list[0], "setStereochemParamFileName", "setNilStereochemParamFileName", false, rset.getString("Stereochem_param_file_name"), logw))
						continue;
					if (!set_string(list[0], "setStructFileFormat", "setNilStructFileFormat", false, rset.getString("Struct_file_format"), logw))
						continue;
					if (!set_string(list[0], "setStructFileName", "setNilStructFileName", false, rset.getString("Struct_file_name"), logw))
						continue;
					if (!set_string(list[0], "setSubcomponentList", "setNilSubcomponentList", false, rset.getString("Subcomponent_list"), logw))
						continue;
					if (!set_string(list[0], "setSynonyms", "setNilSynonyms", false, rset.getString("Synonyms"), logw))
						continue;
					if (!set_string(list[0], "setThreeLetterCode", "setNilThreeLetterCode", false, rset.getString("Three_letter_code"), logw))
						continue;
					if (!set_string(list[0], "setTopoFileFormat", "setNilTopoFileFormat", false, rset.getString("Topo_file_format"), logw))
						continue;
					if (!set_string(list[0], "setTopoFileName", "setNilTopoFileName", false, rset.getString("Topo_file_name"), logw))
						continue;
					if (!set_enum_type(list[0], "setType", "", true, rset.getString("Type"), logw, errw))
						continue;
					if (!set_enum_vendor(list[0], "setVendor", "setNilVendor", false, rset.getString("Vendor"), logw, errw))
						continue;
					if (!set_string(list[0], "setVendorProductCode", "setNilVendorProductCode", false, rset.getString("Vendor_product_code"), logw))
						continue;
					if (!set_string(list[0], "setEntryId", "", true, rset.getString("Entry_ID"), logw))
						continue;
					if (!set_string_id(list[0], "setId", "", true, rset.getString("ID"), rset.getString("Name"), logw))
						continue;

					body.setChemCompArray(list);

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

				buffw.write("  </BMRBx:chem_compCategory>\n");

			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_ChemCompType.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} catch (IOException ex) {

			Logger lgr = Logger.getLogger(bmr_ChemCompType.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_ChemCompType.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		return list_len;
	}

	private static boolean set_string(ChemComp list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

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
				logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_string_id(ChemComp list, String method_name, String nil_method_name, boolean required, String val_name, String name, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")))
			val_name = name;

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' was empty, but not nillable.\n");
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
			Method nil_method = null;

			if (nil_method_name != null && !nil_method_name.isEmpty())
				nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					nil_method.invoke(list);
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

	private static boolean set_integer(ChemComp list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml && (nil_method_name == null || nil_method_name.isEmpty()) && !method_name.equalsIgnoreCase("setSampleID") && !method_name.equalsIgnoreCase("setExperimentID") && !method_name.equalsIgnoreCase("setStudyID") && !method_name.equalsIgnoreCase("setAssemblyID") && (method_name.contains("Id") || method_name.contains("Number") || method_name.contains("Count")) && !method_name.contains("ListId") && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$")))
			val_name = "0";

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_decimal(ChemComp list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || (!val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)?$") && !val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)([eE][-+]?[0-9]+)?$"))) && (nil_method_name == null || nil_method_name.isEmpty())) {

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_decimal_formula_weight(ChemComp list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_le, String pdb_code, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) // && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+.[0-9]{3}$") || (!val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)?$") && !val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)([eE][-+]?[0-9]+)?$"))))
			val_name = bmr_Util_LE.getFormulaWeight(val_name, conn_le, pdb_code);

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || (!val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)?$") && !val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)([eE][-+]?[0-9]+)?$")))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_date(ChemComp list, String method_name, String nil_method_name, boolean required, java.sql.Date date, FileWriter logw) {

		boolean nil = false;

		if (date == null)
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + date + "' was empty, but not nillable.\n");
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

	private static boolean set_enum_ambiguous_flag(ChemComp list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ ChemComp.AmbiguousFlag.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					ChemComp.AmbiguousFlag.Enum _enum = ChemComp.AmbiguousFlag.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:ChemComp method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= ChemComp.AmbiguousFlag.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + ChemComp.AmbiguousFlag.Enum.forInt(i));
						try {
							errw.write("class_name:ChemComp method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= ChemComp.AmbiguousFlag.Enum.table.lastInt(); i++)
								errw.write(" enum:" + ChemComp.AmbiguousFlag.Enum.forInt(i) + "\n");
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

	private static boolean set_enum_aromatic(ChemComp list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ ChemComp.Aromatic.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					ChemComp.Aromatic.Enum _enum = ChemComp.Aromatic.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:ChemComp method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= ChemComp.Aromatic.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + ChemComp.Aromatic.Enum.forInt(i));
						try {
							errw.write("class_name:ChemComp method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= ChemComp.Aromatic.Enum.table.lastInt(); i++)
								errw.write(" enum:" + ChemComp.Aromatic.Enum.forInt(i) + "\n");
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

	private static boolean set_enum_atom_nomenclature_source(ChemComp list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ ChemComp.AtomNomenclatureSource.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					ChemComp.AtomNomenclatureSource.Enum _enum = ChemComp.AtomNomenclatureSource.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:ChemComp method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= ChemComp.AtomNomenclatureSource.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + ChemComp.AtomNomenclatureSource.Enum.forInt(i));
						try {
							errw.write("class_name:ChemComp method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= ChemComp.AtomNomenclatureSource.Enum.table.lastInt(); i++)
								errw.write(" enum:" + ChemComp.AtomNomenclatureSource.Enum.forInt(i) + "\n");
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

	private static boolean set_enum_formal_charge(ChemComp list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (val_name != null) {

			if (val_name.startsWith("-"))
				val_name = val_name.replaceFirst("^-","") + "-";
			if (val_name.startsWith("+"))
				val_name = val_name.replaceFirst("^+","") + "+";

			if (!val_name.equals("0") && !val_name.endsWith("-") && val_name.matches("^[1-9]$"))
				val_name = val_name + "+";

			if (!val_name.matches("^[0-9][-+]?$"))
				val_name = "0";

		}

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ ChemComp.FormalCharge.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					ChemComp.FormalCharge.Enum _enum = ChemComp.FormalCharge.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:ChemComp method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= ChemComp.FormalCharge.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + ChemComp.FormalCharge.Enum.forInt(i));
						try {
							errw.write("class_name:ChemComp method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= ChemComp.FormalCharge.Enum.table.lastInt(); i++)
								errw.write(" enum:" + ChemComp.FormalCharge.Enum.forInt(i) + "\n");
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

	private static boolean set_enum_ideal_coordinates_missing_flag(ChemComp list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ ChemComp.IdealCoordinatesMissingFlag.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					ChemComp.IdealCoordinatesMissingFlag.Enum _enum = ChemComp.IdealCoordinatesMissingFlag.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:ChemComp method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= ChemComp.IdealCoordinatesMissingFlag.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + ChemComp.IdealCoordinatesMissingFlag.Enum.forInt(i));
						try {
							errw.write("class_name:ChemComp method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= ChemComp.IdealCoordinatesMissingFlag.Enum.table.lastInt(); i++)
								errw.write(" enum:" + ChemComp.IdealCoordinatesMissingFlag.Enum.forInt(i) + "\n");
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

	private static boolean set_enum_image_file_format(ChemComp list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ ChemComp.ImageFileFormat.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					ChemComp.ImageFileFormat.Enum _enum = ChemComp.ImageFileFormat.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:ChemComp method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= ChemComp.ImageFileFormat.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + ChemComp.ImageFileFormat.Enum.forInt(i));
						try {
							errw.write("class_name:ChemComp method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= ChemComp.ImageFileFormat.Enum.table.lastInt(); i++)
								errw.write(" enum:" + ChemComp.ImageFileFormat.Enum.forInt(i) + "\n");
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

	private static boolean set_enum_model_coordinates_missing_flag(ChemComp list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ ChemComp.ModelCoordinatesMissingFlag.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					ChemComp.ModelCoordinatesMissingFlag.Enum _enum = ChemComp.ModelCoordinatesMissingFlag.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:ChemComp method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= ChemComp.ModelCoordinatesMissingFlag.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + ChemComp.ModelCoordinatesMissingFlag.Enum.forInt(i));
						try {
							errw.write("class_name:ChemComp method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= ChemComp.ModelCoordinatesMissingFlag.Enum.table.lastInt(); i++)
								errw.write(" enum:" + ChemComp.ModelCoordinatesMissingFlag.Enum.forInt(i) + "\n");
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

	private static boolean set_enum_mon_nstd_flag(ChemComp list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ ChemComp.MonNstdFlag.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					ChemComp.MonNstdFlag.Enum _enum = ChemComp.MonNstdFlag.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:ChemComp method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= ChemComp.MonNstdFlag.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + ChemComp.MonNstdFlag.Enum.forInt(i));
						try {
							errw.write("class_name:ChemComp method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= ChemComp.MonNstdFlag.Enum.table.lastInt(); i++)
								errw.write(" enum:" + ChemComp.MonNstdFlag.Enum.forInt(i) + "\n");
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

	private static boolean set_enum_paramagnetic(ChemComp list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (val_name != null)
			val_name = bmr_Util_ChemComp.getParamagnetic(val_name);

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ ChemComp.Paramagnetic.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					ChemComp.Paramagnetic.Enum _enum = ChemComp.Paramagnetic.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:ChemComp method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= ChemComp.Paramagnetic.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + ChemComp.Paramagnetic.Enum.forInt(i));
						try {
							errw.write("class_name:ChemComp method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= ChemComp.Paramagnetic.Enum.table.lastInt(); i++)
								errw.write(" enum:" + ChemComp.Paramagnetic.Enum.forInt(i) + "\n");
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

	private static boolean set_enum_processing_site(ChemComp list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (val_name != null)
			val_name = bmr_Util_ChemComp.getProcessingSite(val_name);

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ ChemComp.ProcessingSite.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					ChemComp.ProcessingSite.Enum _enum = ChemComp.ProcessingSite.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:ChemComp method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= ChemComp.ProcessingSite.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + ChemComp.ProcessingSite.Enum.forInt(i));
						try {
							errw.write("class_name:ChemComp method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= ChemComp.ProcessingSite.Enum.table.lastInt(); i++)
								errw.write(" enum:" + ChemComp.ProcessingSite.Enum.forInt(i) + "\n");
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

	private static boolean set_enum_provenance(ChemComp list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?"))
				val_name = "na";

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ ChemComp.Provenance.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					ChemComp.Provenance.Enum _enum = ChemComp.Provenance.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:ChemComp method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= ChemComp.Provenance.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + ChemComp.Provenance.Enum.forInt(i));
						try {
							errw.write("class_name:ChemComp method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= ChemComp.Provenance.Enum.table.lastInt(); i++)
								errw.write(" enum:" + ChemComp.Provenance.Enum.forInt(i) + "\n");
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

	private static boolean set_enum_release_status(ChemComp list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ ChemComp.ReleaseStatus.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					ChemComp.ReleaseStatus.Enum _enum = ChemComp.ReleaseStatus.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:ChemComp method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= ChemComp.ReleaseStatus.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + ChemComp.ReleaseStatus.Enum.forInt(i));
						try {
							errw.write("class_name:ChemComp method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= ChemComp.ReleaseStatus.Enum.table.lastInt(); i++)
								errw.write(" enum:" + ChemComp.ReleaseStatus.Enum.forInt(i) + "\n");
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

	private static boolean set_enum_type(ChemComp list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (val_name != null)
			val_name = bmr_Util_ChemComp.getType(val_name);

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?"))
				val_name = "OTHER";

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ ChemComp.Type.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					ChemComp.Type.Enum _enum = ChemComp.Type.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:ChemComp method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= ChemComp.Type.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + ChemComp.Type.Enum.forInt(i));
						try {
							errw.write("class_name:ChemComp method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= ChemComp.Type.Enum.table.lastInt(); i++)
								errw.write(" enum:" + ChemComp.Type.Enum.forInt(i) + "\n");
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

	private static boolean set_enum_vendor(ChemComp list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='ChemComp', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ ChemComp.Vendor.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					ChemComp.Vendor.Enum _enum = ChemComp.Vendor.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:ChemComp method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= ChemComp.Vendor.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + ChemComp.Vendor.Enum.forInt(i));
						try {
							errw.write("class_name:ChemComp method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= ChemComp.Vendor.Enum.table.lastInt(); i++)
								errw.write(" enum:" + ChemComp.Vendor.Enum.forInt(i) + "\n");
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
