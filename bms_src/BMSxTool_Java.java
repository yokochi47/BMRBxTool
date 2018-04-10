/*
    BMRBxTool - XML converter for NMR-STAR data
    Copyright 2013-2018 Masashi Yokochi

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

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

import org.w3c.dom.*;

public class BMSxTool_Java {

	private static String type_class_name = null; // root class of XMLBeans
	private static String class_name = null; // child class of root class
	private static String abs_class_name = null; // absolute name of the child class

	private static String category_name = null; // category tag name

	public static int write(Node node, Connection conn_bmrb, String attr_name, String table_name, String dir_name, String prefix, String file_prefix) {

		String attr_name_lower = attr_name.toLowerCase();

		char[] _class_name = attr_name_lower.toCharArray();
		char[] _Class_name = attr_name.toCharArray();

		boolean under_score = true;
		boolean numeric_code = false;

		for (int i = 0; i < attr_name.length(); i++) {
			if (under_score || numeric_code || (_Class_name[i] >= 'A' && _Class_name[i] <= 'Z'))
				_class_name[i] = Character.toUpperCase(_class_name[i]);
			under_score = (_class_name[i] == '_');
			numeric_code = (_class_name[i] >= '0' && _class_name[i] <= '9');
		}

		class_name = String.valueOf(_class_name).replace("_", "").replaceFirst("Type$", "");
		category_name = attr_name.replaceFirst("Type$", "") + "Category";

		if (class_name.matches(".*[12]Type$")) { // T1TypeType -> T1Type, T2TypeType -> T2Type cases
			class_name = String.valueOf(_class_name).replace("_", "").replaceFirst("Type$", "").replaceFirst("Type$", "");
			category_name = attr_name.replaceFirst("Type$", "").replaceFirst("Type$", "") + "Category";
		}

		type_class_name = class_name + "Type";

		if (type_class_name.endsWith("TypeType") || class_name.equalsIgnoreCase("Method")) // to prevent name collision.
			abs_class_name = BMSxTool_DOM.package_name + "." + type_class_name + "." + type_class_name.replaceFirst("Type$", "");
		else
			abs_class_name = class_name;

		//		System.out.println(attr_name + " " + class_name + " " + type_class_name);

		String query_key = (table_name.equals("Entry") ? "ID" : "Entry_ID");

		boolean _integer = has_integer(node) || has_enum_integer(node); // has xsd:integer
		boolean _integer_id = has_integer_id(node); // has xsd:integer with name "id"
		boolean _decimal = has_decimal(node) || has_enum_decimal(node); // has xsd:decimal
		boolean _date = has_date(node); // has xsd:date
		boolean _enum = has_enum(node); // has xsd:restriction

		//		System.out.println("table_name :" + table_name + ", int: " + (_integer ? "true":"false") + ", decimal: " + (_decimal ? "true" : "false") + ", date: " + (_date ? "true" : "false") + " enum:" + (_enum ? "true" : "false"));

		ResultSet rset = null;
		DatabaseMetaData meta;
		List<String> column_list = new ArrayList<String>();

		// get column names from table

		try {

			meta = conn_bmrb.getMetaData();
			rset = meta.getColumns(null, null, table_name, null);

			while (rset.next())
				column_list.add(rset.getString("COLUMN_NAME"));

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(BMSxTool_Java.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		} finally {

			try {

				if (rset != null)
					rset.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(BMSxTool_Java.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		// write java program file

		String program_name = dir_name + file_prefix + "_" + type_class_name + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);

			filew.write(BMSxTool_DOM.license);

			filew.write("package " + BMSxTool_DOM.package_name + ";\n\n");

			filew.write("import java.io.*;\n");
			filew.write("import java.lang.reflect.*;\n");

			if (_integer /* && !class_name.equalsIgnoreCase("Entry") */ )
				filew.write("import java.math.BigInteger;\n");
			if (_decimal)
				filew.write("import java.math.BigDecimal;\n");
			if (has_decimal(node))
				filew.write("import java.math.MathContext;\n");
			if (_date || class_name.equalsIgnoreCase("ChemShiftRef"))
				filew.write("import java.util.*;\n");

			filew.write("import java.util.logging.*;\n");
			filew.write("import java.sql.*;\n\n");

			filew.write("import org.apache.xmlbeans.*;\n");

			if (class_name.equalsIgnoreCase("Citation"))
				filew.write("import org.w3c.dom.Node;\n");

			if (class_name.equalsIgnoreCase(abs_class_name))
				filew.write("import " + BMSxTool_DOM.package_name + "." + type_class_name + ".*;\n\n");
			else
				filew.write("import " + BMSxTool_DOM.package_name + "." + type_class_name + ";\n\n");

			filew.write("public class " + file_prefix + "_" + type_class_name + " {\n\n");

			filew.write("\tprivate static final String table_name = \"" + table_name + "\";\n\n");

			if (class_name.equalsIgnoreCase("EntityExperimentalSrc"))
				filew.write("\tpublic static int write_xml(Connection conn_bmrb, Connection conn_tax, Connection conn_le, " + file_prefix + "_" + BMSxTool_DOM.util_entityexperimentalsrc + " ee, String entry_id, XmlOptions xml_opt, FileWriter filew, FileWriter logw, FileWriter errw) {\n\n");
			else if (class_name.equalsIgnoreCase("EntityNaturalSrc"))
				filew.write("\tpublic static int write_xml(Connection conn_bmrb, Connection conn_tax, Connection conn_le, " + file_prefix + "_" + BMSxTool_DOM.util_entitynaturalsrc + " en, String entry_id, XmlOptions xml_opt, FileWriter filew, FileWriter logw, FileWriter errw) {\n\n");
			else if (class_name.equalsIgnoreCase("Citation"))
				filew.write("\tpublic static int write_xml(Connection conn_bmrb, Connection conn_clone, Connection conn_tax, Connection conn_le, " + file_prefix + "_" + BMSxTool_DOM.util_entitynaturalsrc + " en, String entry_id, XmlOptions xml_opt, FileWriter filew, FileWriter logw, FileWriter errw) {\n\n");
			else
				filew.write("\tpublic static int write_xml(Connection conn_bmrb, Connection conn_tax, Connection conn_le, String entry_id, XmlOptions xml_opt, FileWriter filew, FileWriter logw, FileWriter errw) {\n\n");

			// category='BMRBx:atom_site', 'BMRBx:*conformer*', 'BMRBx:*constr*', 'BMRBx:*peak*'
			if (class_name.equalsIgnoreCase("AtomSite") || class_name.contains("Conformer") || class_name.contains("Constr") || class_name.contains("Peak")) {
				filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".noatom)\n");
				filew.write("\t\t\treturn 0;\n\n");
			}

			filew.write("\t\t" + type_class_name + " body = " + type_class_name + ".Factory.newInstance();\n");
			filew.write("\t\t" + abs_class_name + "[] list = new " + abs_class_name + "[1];\n\n");

			filew.write("\t\tStatement state = null;\n");

			if (class_name.equalsIgnoreCase("Citation")) {

				filew.write("\t\tStatement state_clone = null;\n\n");

				filew.write("\t\ttry {\n");
				filew.write("\t\t\tstate_clone = (conn_clone != null ? conn_clone.createStatement() : null);\n");
				filew.write("\t\t} catch (SQLException e) {}\n\n");

			}

			filew.write("\t\tResultSet rset = null;\n\n");

			filew.write("\t\tString[] rcsv = null;\n\n");

			if (_integer_id)
				filew.write("\t\tString[] rid = null;\n\n");

			filew.write("\t\tint list_len = 0;\n\n");

			filew.write("\t\ttry {\n\n");

			filew.write("\t\t\tString query = new String(\"select count(*) from \\\"\" + table_name + \"\\\" where \\\"" + query_key + "\\\"='\" + entry_id + \"' group by \\\"" + query_key + "\\\"\");\n\n");

			filew.write("\t\t\tstate = conn_bmrb.createStatement();\n");
			filew.write("\t\t\trset = state.executeQuery(query);\n\n");

			// entry_id=5505, category='BMRBx:heteronucl_t1_experiment', 'BMRBx:heteronucl_t1_list', 'BMRBx:heteronucl_t1_software', 'BMRBx:t1'
			if (class_name.equalsIgnoreCase("HeteronuclT1Experiment") || class_name.equalsIgnoreCase("HeteronuclT1List") || class_name.equalsIgnoreCase("HeteronuclT1Software") || class_name.equalsIgnoreCase("T1"))
				filew.write("\t\t\tif (rset.next() && " + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && !entry_id.equals(\"5505\")) {\n\n");
			// entry_id=6693, category='BMRBx:peak_general_char'
			else if (class_name.equalsIgnoreCase("PeakGeneralChar"))
				filew.write("\t\t\tif (rset.next() && " + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && !entry_id.equals(\"6693\")) {\n\n");
			else
				filew.write("\t\t\tif (rset.next()) {\n\n");

			filew.write("\t\t\t\tlist_len = Integer.parseInt(rset.getString(1));\n\n");

			filew.write("\t\t\t\trcsv = new String[list_len];\n\n");

			if (_integer_id)
				filew.write("\t\t\t\trid = new String[list_len];\n\n");

			// extract sort key attrs

			List<String> key_attrs = new ArrayList<String>();
			List<String> rep_attrs = new ArrayList<String>();
			List<String> list_attrs = new ArrayList<String>();

			extract_key_attr_nodes(node, column_list, key_attrs, rep_attrs, list_attrs);

			if (key_attrs.size() > 1)
				key_attrs.sort((arg0, arg1) -> arg1.length() - arg0.length());

			if (rep_attrs.size() > 1)
				rep_attrs.sort((arg0, arg1) -> arg1.length() - arg0.length());

			if (list_attrs.size() > 1)
				list_attrs.sort((arg0, arg1) -> arg1.length() - arg0.length());

			if (rep_attrs.size() > 0)
				list_attrs.addAll(rep_attrs);

			if (key_attrs.size() > 0)
				list_attrs.addAll(key_attrs);

			if (list_attrs.size() == 0)
				filew.write("\t\t\t\tString query2 = new String(\"select * from \\\"\" + table_name + \"\\\" where \\\"" + query_key + "\\\"='\" + entry_id + \"'\");\n\n");

			else {
				StringBuilder sb = new StringBuilder();

				for (String key_attr : list_attrs)
					sb.append("(0 || \\\"" + key_attr + "\\\")::decimal,");

				filew.write("\t\t\t\tString query2 = new String(\"select * from \\\"\" + table_name + \"\\\" where \\\"" + query_key + "\\\"='\" + entry_id + \"' order by " + sb.substring(0, sb.length() - 1) + "\");\n\n");
			}

			list_attrs.clear();
			rep_attrs.clear();
			key_attrs.clear();
			/*
			// category='BMRBx:entity_experimental_src', 'BMRBx:experiment', 'BMRBx:nmr_spectrometer_view', 'BMRBx:spectral_dim', 'BMRBx:study'
			if (class_name.equalsIgnoreCase("EntityExperimentalSrc") || class_name.equalsIgnoreCase("Experiment") || class_name.equalsIgnoreCase("NMRSpectrometerView") || class_name.equalsIgnoreCase("SpectralDim") || class_name.equalsIgnoreCase("Study"))
				filew.write("\t\t\t\tString query2 = new String(\"select * from \\\"\" + table_name + \"\\\" where \\\"" + query_key + "\\\"='\" + entry_id + \"' order by \\\"ID\\\"\");\n\n");
			// category='BMRBx:entity_deleted_atom'
			else if (class_name.equalsIgnoreCase("EntityDeletedAtom"))
				filew.write("\t\t\t\tString query2 = new String(\"select * from \\\"\" + table_name + \"\\\" where \\\"" + query_key + "\\\"='\" + entry_id + \"' order by \\\"Entity_atom_list_ID\\\"\");\n\n");
			// category='BMRBx:entity_purity'
			else if (class_name.equalsIgnoreCase("EntityPurity"))
				filew.write("\t\t\t\tString query2 = new String(\"select * from \\\"\" + table_name + \"\\\" where \\\"" + query_key + "\\\"='\" + entry_id + \"' order by \\\"Entity_ID\\\"\");\n\n");
			// category='BMRBx:release'
			else if (class_name.equalsIgnoreCase("Release"))
				filew.write("\t\t\t\tString query2 = new String(\"select * from \\\"\" + table_name + \"\\\" where \\\"" + query_key + "\\\"='\" + entry_id + \"' order by \\\"Release_number\\\",\\\"Date\\\"\");\n\n");
			else
				filew.write("\t\t\t\tString query2 = new String(\"select * from \\\"\" + table_name + \"\\\" where \\\"" + query_key + "\\\"='\" + entry_id + \"'\");\n\n");
			 */
			filew.write("\t\t\t\trset.close();\n\n");

			filew.write("\t\t\t\trset = state.executeQuery(query2);\n\n");

			// category='BMRBx:chem_shift_ref'
			if (class_name.equalsIgnoreCase("ChemShiftRef")) {

				filew.write("\t\t\t\tList<" + file_prefix + "_" + BMSxTool_DOM.util_chemshiftref + "> chem_shift_ref_list = new ArrayList<" + file_prefix + "_" + BMSxTool_DOM.util_chemshiftref + ">();\n\n");

				filew.write("\t\t\t\twhile (rset.next())\n");
				filew.write("\t\t\t\t\tchem_shift_ref_list.add(new " + file_prefix + "_" + BMSxTool_DOM.util_chemshiftref + "(rset.getString(\"Atom_type\"), rset.getString(\"Atom_isotope_number\"), rset.getString(\"Indirect_shift_ratio\")));\n\n");

				filew.write("\t\t\t\tfor (int idx_13c = 0; idx_13c < chem_shift_ref_list.size(); idx_13c++) {\n\n");

				filew.write("\t\t\t\t\t" + file_prefix + "_" + BMSxTool_DOM.util_chemshiftref + " chem_shift_ref_13c = chem_shift_ref_list.get(idx_13c);\n\n");

				filew.write("\t\t\t\t\tif (chem_shift_ref_13c.atom_type == null || !chem_shift_ref_13c.atom_type.equalsIgnoreCase(\"C\") || chem_shift_ref_13c.indirect_shift_ratio == null)\n");
				filew.write("\t\t\t\t\t\tcontinue;\n\n");

				filew.write("\t\t\t\t\tfor (int idx_15n = 0; idx_15n < chem_shift_ref_list.size(); idx_15n++) {\n\n");

				filew.write("\t\t\t\t\t\t" + file_prefix + "_" + BMSxTool_DOM.util_chemshiftref + " chem_shift_ref_15n = chem_shift_ref_list.get(idx_15n);\n\n");

				filew.write("\t\t\t\t\t\tif (chem_shift_ref_15n.atom_type == null || !chem_shift_ref_15n.atom_type.equalsIgnoreCase(\"N\") || chem_shift_ref_15n.indirect_shift_ratio == null)\n");
				filew.write("\t\t\t\t\t\t\tcontinue;\n\n");

				filew.write("\t\t\t\t\t\tif (" + empty_check("chem_shift_ref_13c.indirect_shift_ratio") + ")\n");
				filew.write("\t\t\t\t\t\t\tcontinue;\n\n");

				filew.write("\t\t\t\t\t\tif (" + empty_check("chem_shift_ref_15n.indirect_shift_ratio") + ")\n");
				filew.write("\t\t\t\t\t\t\tcontinue;\n\n");

				filew.write("\t\t\t\t\t\tfloat ratio_13c = Float.valueOf(chem_shift_ref_13c.indirect_shift_ratio);\n");
				filew.write("\t\t\t\t\t\tfloat ratio_15n = Float.valueOf(chem_shift_ref_15n.indirect_shift_ratio);\n\n");

				filew.write("\t\t\t\t\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && ratio_13c > 0.0 && ratio_15n > 0.0 && ratio_15n / ratio_13c > 2.3 && ratio_15n / ratio_13c < 2.7) {\n\n");

				filew.write("\t\t\t\t\t\t\tchem_shift_ref_13c.setAtomType(\"N\");\n");
				filew.write("\t\t\t\t\t\t\tchem_shift_ref_13c.setAtomIsotopeNumber(\"15\");\n\n");

				filew.write("\t\t\t\t\t\t\tchem_shift_ref_15n.setAtomType(\"C\");\n");
				filew.write("\t\t\t\t\t\t\tchem_shift_ref_15n.setAtomIsotopeNumber(\"13\");\n\n");

				filew.write("\t\t\t\t\t\t}\n\t\t\t\t\t}\n\t\t\t\t}\n\n");

				filew.write("\t\t\t\trset = state.executeQuery(query2);\n\n");

			}

			// category='BMRBx:atom_nomenclature', 'BMRBx:chem_shift_ref', 'BMRBx:entity_experimental_src', 'BMRBx:entity_purity', 'BMRBx:natural_source_db', 'BMRBx:nmr_spectrometer_view', 'BMRBx:release', 'BMRBx:sg_project', 'BMRBx:*software'
			if (class_name.equalsIgnoreCase("AtomNomenclature") || class_name.equalsIgnoreCase("ChemShiftRef") || class_name.equalsIgnoreCase("NaturalSourceDb") || class_name.equalsIgnoreCase("Release") || class_name.equalsIgnoreCase("SGProject") || (class_name.endsWith("Software") && !class_name.startsWith("Software")))
				filew.write("\t\t\t\tint i = 0;\n\n");

			filew.write("\t\t\t\tint cols = rset.getMetaData().getColumnCount();\n");
			filew.write("\t\t\t\tint lines = 0;\n\n");

			filew.write("\t\t\t\tfilew.write(\"  <" + prefix + ":" + category_name + ">\\n\");\n\n");

			filew.write("\t\t\t\twhile (rset.next()) {\n\n");

			filew.write("\t\t\t\t\tStringBuilder sb = new StringBuilder();\n\n");

			filew.write("\t\t\t\t\tfor (int j = 1; j <= cols; j++) {\n");
			filew.write("\t\t\t\t\t\tString val = rset.getString(j);\n");
			filew.write("\t\t\t\t\t\tif (val != null)\n");
			filew.write("\t\t\t\t\t\t\tsb.append(val + \",\");\n");
			filew.write("\t\t\t\t\t\telse\n");
			filew.write("\t\t\t\t\t\t\tsb.append(\",\");\n");
			filew.write("\t\t\t\t\t}\n\n");

			filew.write("\t\t\t\t\trcsv[lines] = sb.toString();\n\n");

			filew.write("\t\t\t\t\tint l;\n\n");

			if (_integer_id) {

				filew.write("\t\t\t\t\tString id = rset.getString(\"ID\");\n");
				filew.write("\t\t\t\t\trid[lines] = id;\n\n");

				filew.write("\t\t\t\t\tif (" + empty_check("id") + ") {\n");
				filew.write("\t\t\t\t\t\tif (lines == 0)\n");
				filew.write("\t\t\t\t\t\t\trid[lines] = \"1\";\n");
				filew.write("\t\t\t\t\t\telse\n");
				filew.write("\t\t\t\t\t\t\trid[lines] = String.valueOf(Integer.valueOf(rid[lines - 1]) + 1);\n");
				filew.write("\t\t\t\t\t}\n\n");

				filew.write("\t\t\t\t\tfor (l = 0; l < lines; l++) {\n");
				filew.write("\t\t\t\t\t\tif (rid[lines].equals(rid[l]))\n");
				filew.write("\t\t\t\t\t\t\tbreak;\n");
				filew.write("\t\t\t\t\t}\n\n");

				filew.write("\t\t\t\t\tif (l < lines)\n");
				filew.write("\t\t\t\t\t\trid[lines] = String.valueOf(Integer.valueOf(rid[lines - 1]) + 1);\n\n");

			}

			filew.write("\t\t\t\t\tfor (l = 0; l < lines; l++) {\n");
			filew.write("\t\t\t\t\t\tif (rcsv[lines].equals(rcsv[l]))\n");
			filew.write("\t\t\t\t\t\t\tbreak;\n");
			filew.write("\t\t\t\t\t}\n\n");

			filew.write("\t\t\t\t\tif (l < lines++) {\n");

			write_excld_log_code(filew, "duplicated content of the category was excluded.");

			filew.write("\t\t\t\t\t\tcontinue;\n\t\t\t\t\t}\n\n");

			filew.write("\t\t\t\t\tlist[0] = " + abs_class_name + ".Factory.newInstance();\n\n");

			// category='BMRBx:chem_comp'
			if (class_name.equals("ChemComp")) {

				filew.write("\t\t\t\t\tString _bmrb_code = rset.getString(\"BMRB_code\");\n");
				filew.write("\t\t\t\t\tString _pdb_code = rset.getString(\"PDB_code\");\n");
				filew.write("\t\t\t\t\tString pdb_id = rset.getString(\"Model_coordinates_db_code\");\n\n");

				filew.write("\t\t\t\t\tString bmrb_code = " + file_prefix + "_" + BMSxTool_DOM.util_chemcomp + ".checkChemCompIDwithBMRBLigandExpo(_bmrb_code, true, 0);\n");
				filew.write("\t\t\t\t\tString pdb_code = " + file_prefix + "_" + BMSxTool_DOM.util_chemcomp + ".checkChemCompIDwithChemComp(_pdb_code, pdb_id, 0);\n\n");

				write_remed_bmrb_code(filew, "setBmrbCode");
				write_remed_pdb_code(filew, "setPdbCode");

			}

			// category='BMRBx:chem_shift_ref'
			if (class_name.equalsIgnoreCase("ChemShiftRef"))
				filew.write("\t\t\t\t\t" + file_prefix + "_" + BMSxTool_DOM.util_chemshiftref + " chem_shift_ref = chem_shift_ref_list.get(i++);\n\n");

			// category="BMRBx:citation'
			if (class_name.equalsIgnoreCase("Citation")) {

				filew.write("\t\t\t\t\tString _pubmed_id = rset.getString(\"PubMed_ID\");\n");
				filew.write("\t\t\t\t\tString pubmed_id = _pubmed_id;\n");
				filew.write("\t\t\t\t\tString title = rset.getString(\"Title\");\n");
				filew.write("\t\t\t\t\tString full_citation = rset.getString(\"Full_citation\");\n");
				filew.write("\t\t\t\t\tString _doi = rset.getString(\"DOI\");\n");
				filew.write("\t\t\t\t\tString doi = _doi;\n\n");

				filew.write("\t\t\t\t\tboolean clone_used = false;\n\n");

				filew.write("\t\t\t\t\tif (conn_clone != null) {\n\n");

				filew.write("\t\t\t\t\t\tString query_clone = \"select pubmed_id,doi from citation where entry_id='\" + entry_id + \"' and id=\" + rset.getString(\"Id\");\n");
				filew.write("\t\t\t\t\t\tResultSet rset_clone = state_clone.executeQuery(query_clone);\n\n");

				filew.write("\t\t\t\t\t\twhile (rset_clone.next()) {\n");
				filew.write("\t\t\t\t\t\t\tpubmed_id = rset_clone.getString(1);\n");
				filew.write("\t\t\t\t\t\t\tdoi = rset_clone.getString(2);\n");
				filew.write("\t\t\t\t\t\t\tclone_used = pubmed_id != null && !pubmed_id.isEmpty() && doi != null && !doi.isEmpty();\n");
				filew.write("\t\t\t\t\t\t\tif (!clone_used) {\n");
				filew.write("\t\t\t\t\t\t\t\tpubmed_id = _pubmed_id;\n");
				filew.write("\t\t\t\t\t\t\t\tdoi = _doi;\n");
				filew.write("\t\t\t\t\t\t\t}\n");
				filew.write("\t\t\t\t\t\t\tbreak;\n");
				filew.write("\t\t\t\t\t\t}\n\n");

				filew.write("\t\t\t\t\t\trset_clone.close();\n\n");

				filew.write("\t\t\t\t\t}\n\n");

				filew.write("\t\t\t\t\tif ((" + empty_check("pubmed_id") + " || pubmed_id.matches(\"^[0-9]$\") || pubmed_id.matches(\"^[A-Za-z].*\")) && !(" + empty_check("title") + "))\n");
				filew.write("\t\t\t\t\t\tpubmed_id = " + file_prefix + "_" + BMSxTool_DOM.util_citation + ".getPubMedIDByTitle(title, true, conn_bmrb, entry_id, 0);\n\n");

				filew.write("\t\t\t\t\tif (pubmed_id != null && (" + empty_check("pubmed_id") + " || pubmed_id.matches(\"^[0-9]$\") || pubmed_id.matches(\"^[A-Za-z].*\")))\n");
				filew.write("\t\t\t\t\t\tpubmed_id = null;\n\n");

				filew.write("\t\t\t\t\tNode doc_sum = " + file_prefix + "_" + BMSxTool_DOM.util_citation + ".getDocSumNodeByPubMedID(pubmed_id, 0);\n\n");

				filew.write("\t\t\t\t\tif (doc_sum == null && !(" + empty_check("title") + ")) {\n");
				filew.write("\t\t\t\t\t\tpubmed_id = " + file_prefix + "_" + BMSxTool_DOM.util_citation + ".getPubMedIDByTitle(title, false, conn_bmrb, entry_id, 0);\n");
				filew.write("\t\t\t\t\t\tdoc_sum = " + file_prefix + "_" + BMSxTool_DOM.util_citation + ".getDocSumNodeByPubMedID(pubmed_id, 0);\n");
				filew.write("\t\t\t\t\t\tif (doc_sum == null)\n");
				filew.write("\t\t\t\t\t\t\tpubmed_id = null;\n");
				filew.write("\t\t\t\t\t}\n\n");

				filew.write("\t\t\t\t\tif (doc_sum == null && !(" + empty_check("full_citation") + ")) {\n");
				filew.write("\t\t\t\t\t\tString[] terms = full_citation.split(\"\\n\");\n");
				filew.write("\t\t\t\t\t\tfor (String term : terms) {\n");
				filew.write("\t\t\t\t\t\t\tpubmed_id = " + file_prefix + "_" + BMSxTool_DOM.util_citation + ".getPubMedIDByTitle(term, false, conn_bmrb, entry_id, 0);\n");
				filew.write("\t\t\t\t\t\t\tdoc_sum = " + file_prefix + "_" + BMSxTool_DOM.util_citation + ".getDocSumNodeByPubMedID(pubmed_id, 0);\n");
				filew.write("\t\t\t\t\t\t\tif (doc_sum == null)\n");
				filew.write("\t\t\t\t\t\t\t\tpubmed_id = null;\n");
				filew.write("\t\t\t\t\t\t\telse\n");
				filew.write("\t\t\t\t\t\t\t\tbreak;\n");
				filew.write("\t\t\t\t\t\t}\n");
				filew.write("\t\t\t\t\t}\n\n");

				write_remed_pubmed_id(filew, "setPubMedId");

				filew.write("\t\t\t\t\tif (doc_sum != null && (!clone_used || (" + empty_check("doi") + "))) {\n");
				filew.write("\t\t\t\t\t\tdoi = " + file_prefix + "_" + BMSxTool_DOM.util_citation + ".getDOI(doi, doc_sum);\n");
				filew.write("\t\t\t\t\t\tif (" + empty_check("doi") + ")\n");
				filew.write("\t\t\t\t\t\t\tdoi = " + file_prefix + "_" + BMSxTool_DOM.util_citation + ".getDOIByTitle(" + file_prefix + "_" + BMSxTool_DOM.util_citation + ".getTitle(null, doc_sum), true, conn_bmrb, entry_id, " + file_prefix + "_" + BMSxTool_DOM.util_citation + ".getYear(null, doc_sum), 0);\n");
				filew.write("\t\t\t\t\t}\n\n");

				write_remed_doi(filew, "setDoi");

			}

			// category='BMRBx:entity_comp_index', 'BMRBx:entity_poly_seq'
			if (class_name.equalsIgnoreCase("EntityCompIndex")) {

				filew.write("\t\t\t\t\tString comp_id = rset.getString(\"Comp_ID\");\n\n");

				filew.write("\t\t\t\t\tif (" + empty_check("comp_id") + ") {\n\n");

				write_excld_log_code(filew, "content of null comp_id was excluded.");

				filew.write("\t\t\t\t\t\tcontinue;\n\t\t\t\t\t}\n\n");

			}

			// category='BMRBx:entity_experimental_src'
			if (class_name.equalsIgnoreCase("EntityExperimentalSrc")) {

				filew.write("\t\t\t\t\tString _ncbi_taxonomy_id = rset.getString(\"Host_org_NCBI_taxonomy_ID\");\n");
				filew.write("\t\t\t\t\tString ncbi_taxonomy_id = " + file_prefix + "_" + BMSxTool_DOM.util_tax + ".latestNCBITaxonomyID(conn_tax, ee.checkNCBITaxonomyID(_ncbi_taxonomy_id, entry_id, rset.getString(\"Entity_ID\"), rset.getString(\"Host_org_scientific_name\")));\n\n");

				write_remed_tax_code(filew, "setHostOrgNcbiTaxonomyId");

			}

			// category='BMRBx:entity_natural_src'
			if (class_name.equalsIgnoreCase("EntityNaturalSrc")) {

				filew.write("\t\t\t\t\tString _ncbi_taxonomy_id = rset.getString(\"NCBI_taxonomy_ID\");\n");
				filew.write("\t\t\t\t\tString ncbi_taxonomy_id = " + file_prefix + "_" + BMSxTool_DOM.util_tax + ".latestNCBITaxonomyID(conn_tax, en.checkNCBITaxonomyID(_ncbi_taxonomy_id, entry_id, rset.getString(\"Organism_name_scientific\")));\n\n");

				write_remed_tax_code(filew, "setNcbiTaxonomyId");

			}

			// category='BMRBx:entity_poly_seq'
			if (class_name.equalsIgnoreCase("EntityPolySeq")) {

				filew.write("\t\t\t\t\tString mon_id = rset.getString(\"Mon_ID\");\n\n");

				filew.write("\t\t\t\t\tif (" + empty_check("mon_id") + ") {\n\n");

				write_excld_log_code(filew, "content of null mon_id was excluded.");

				filew.write("\t\t\t\t\t\tcontinue;\n\t\t\t\t\t}\n\n");

			}

			// category='BMRBx:entry' (metabolomics specific)
			if (class_name.equalsIgnoreCase("Entry")) {

				// (metabolimics specific)
				filew.write("\t\t\t\t\tlist[0].setType(Entry.Type.METABOLITE_NATURAL_PRODUCT);\n\n");

				filew.write("\t\t\t\t\tjava.sql.Date accession_date = rset.getString(\"Accession_date\") != null && !rset.getString(\"Accession_date\").equals(\".\") && !rset.getString(\"Accession_date\").equals(\"?\") ? rset.getDate(\"Accession_date\") : null;\n");
				filew.write("\t\t\t\t\tjava.sql.Date submission_date = rset.getString(\"Submission_date\") != null && !rset.getString(\"Submission_date\").equals(\".\") && !rset.getString(\"Submission_date\").equals(\"?\") ? rset.getDate(\"Submission_date\") : null;\n\n");

			}

			// category='BMRBx:exepriment' (metabolomics specific)
			if (class_name.equalsIgnoreCase("Experiment")) {

				filew.write("\t\t\t\t\tlist[0].setNilXrayInstrumentId();\n");
				filew.write("\t\t\t\t\tlist[0].setNilXrayInstrumentLabel();\n\n");

			}

			// link items and columns

			extract_element_nodes(filew, node, table_name, column_list, file_prefix);

			// item='pdbx_name', category='BMRBx:citation_author', 'BMRBx:citation_editor', 'BMRBx:entry_author' 
			if (class_name.equalsIgnoreCase("CitationAuthor") || class_name.equalsIgnoreCase("CitationEditor") || class_name.equalsIgnoreCase("EntryAuthor"))
				filew.write("\t\t\t\t\tset_string_pdbx_name(list[0], \"setPdbxName\", \"setNilPdbxName\", false, rset.getString(\"Family_name\"), rset.getString(\"Given_name\"), rset.getString(\"Middle_initials\"), logw);\n");

			filew.write("\n\t\t\t\t\tbody.set" + class_name + "Array(list);\n\n");

			// output functions

			filew.write("\t\t\t\t\tBufferedReader bufferr = new BufferedReader(new StringReader(body.xmlText(xml_opt)));\n\n");

			filew.write("\t\t\t\t\tString prev = bufferr.readLine();\n\n");
			filew.write("\t\t\t\t\tString cont = null;\n\n");

			filew.write("\t\t\t\t\twhile ((prev = bufferr.readLine()) != null) {\n\n");

			filew.write("\t\t\t\t\t\tif (cont != null)\n");
			filew.write("\t\t\t\t\t\t\tfilew.write(cont + \"\\n\");\n\n");

			filew.write("\t\t\t\t\t\tcont = prev;\n\n");

			filew.write("\t\t\t\t\t}\n\n");

			filew.write("\t\t\t\t\tbufferr.close();\n\n");

			// item='atom_type', category='BMRBx:systematic_chem_shift_offset'
			if (class_name.equalsIgnoreCase("SystematicChemShiftOffset")) {

				filew.write("\t\t\t\t\tString _atom_type = rset.getString(\"Atom_type\");\n");
				filew.write("\t\t\t\t\tString atom_type = _atom_type;\n\n");

				filew.write("\t\t\t\t\tif (_atom_type != null && (_atom_type.equals(\"15N, HN\") || _atom_type.equals(\"Backbone CA/CB\"))) {\n\n");

				filew.write("\t\t\t\t\t\tif (_atom_type.equals(\"15N, HN\"))\n");
				filew.write("\t\t\t\t\t\t\tatom_type = \"amide nitrogens\";\n");
				filew.write("\t\t\t\t\t\telse\n");
				filew.write("\t\t\t\t\t\t\tatom_type = \"Backbone CB\";\n\n");

				write_remed_atom_type(filew, "setAtomType");

				filew.write("\n\t\t\t\t\t\tlist[0] = SystematicChemShiftOffset.Factory.newInstance();\n\n");

				filew.write("\n\t\t\t\t\t\tset_enum_atom_type(list[0], \"setAtomType\", \"\", false, atom_type, logw, errw);\n\n");

				filew.write("\t\t\t\t\t\tbody.setSystematicChemShiftOffsetArray(list);\n\n");

				filew.write("\t\t\t\t\t\tbufferr = new BufferedReader(new StringReader(body.xmlText(xml_opt)));\n\n");

				filew.write("\t\t\t\t\t\tprev = bufferr.readLine();\n\n");
				filew.write("\t\t\t\t\t\tcont = null;\n\n");

				filew.write("\t\t\t\t\t\twhile ((prev = bufferr.readLine()) != null) {\n\n");

				filew.write("\t\t\t\t\t\t\tif (cont != null)\n");
				filew.write("\t\t\t\t\t\t\t\tfilew.write(cont + \"\\n\");\n\n");

				filew.write("\t\t\t\t\t\t\tcont = prev;\n\n");

				filew.write("\t\t\t\t\t\t}\n\n");

				filew.write("\t\t\t\t\t\tbufferr.close();\n\n");

				filew.write("\t\t\t\t\t}\n\n");

			}

			filew.write("\t\t\t\t}\n\n");

			filew.write("\t\t\t\tfilew.write(\"  </" + prefix + ":" + category_name + ">\\n\");\n\n");

			// entry_id=5505, category='BMRBx:heteronucl_t1_experiment', 'BMRBx:heteronucl_t1_list', 'BMRBx:heteronucl_t1_software', 'BMRBx:t1'
			if (class_name.equalsIgnoreCase("HeteronuclT1Experiment") || class_name.equalsIgnoreCase("HeteronuclT1List") || class_name.equalsIgnoreCase("HeteronuclT1Software") || class_name.equalsIgnoreCase("T1")) {
				filew.write("\t\t\t} else if (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && entry_id.equals(\"5505\")) {\n");

				write_excld_log_code(filew, "all information content of the category was excluded.");

			}

			// entry_id=6693, category='BMRBx:peak_general_char'
			else if (class_name.equalsIgnoreCase("PeakGeneralChar")) {
				filew.write("\t\t\t} else if (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && entry_id.equals(\"6693\")) {\n");

				write_excld_log_code(filew, "all information content of the category was excluded.");

			}

			filew.write("\t\t\t}\n\n");

			filew.write("\t\t} catch (SQLException ex) {\n\n");

			filew.write("\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + type_class_name + ".class.getName());\n");
			filew.write("\t\t\tlgr.log(Level.SEVERE, ex.getMessage(), ex);\n");
			filew.write("\t\t\tSystem.exit(1);\n\n");

			filew.write("\t\t} catch (IOException ex) {\n\n");

			filew.write("\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + type_class_name + ".class.getName());\n");
			filew.write("\t\t\tlgr.log(Level.SEVERE, ex.getMessage(), ex);\n");
			filew.write("\t\t\tSystem.exit(1);\n\n");

			filew.write("\t\t} finally {\n\n");

			filew.write("\t\t\ttry {\n\n");

			filew.write("\t\t\t\tif (rset != null)\n");
			filew.write("\t\t\t\t\trset.close();\n\n");

			filew.write("\t\t\t\tif (state != null)\n");
			filew.write("\t\t\t\t\tstate.close();\n\n");

			if (class_name.equalsIgnoreCase("Citation")) {

				filew.write("\t\t\t\tif (state_clone != null)\n");
				filew.write("\t\t\t\t\tstate_clone.close();\n\n");

			}

			filew.write("\t\t\t} catch (SQLException ex) {\n\n");

			filew.write("\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + type_class_name + ".class.getName());\n");
			filew.write("\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			filew.write("\t\t\t}\n\t\t}\n\n");

			filew.write("\t\treturn list_len;\n\t}\n");

			// sub routines

			filew.write("\n\tprivate static boolean set_string(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {\n\n");

			filew.write("\t\tboolean nil = false;\n\n");

			filew.write("\t\tString _val_name = val_name;\n\n");

			filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml) {\n\n");

			// category='BMRBx:chem_shift_ref'
			if (class_name.equalsIgnoreCase("ChemShiftRef")) {

				// item='atom_group', category='BMRBx:chem_shift_ref'
				filew.write("\t\tif (method_name.equalsIgnoreCase(\"setAtomGroup\")) {\n\n");

				filew.write("\t\t\tif (" + empty_check("val_name") + ") {\n\n");

				filew.write("\t\t\t\tif (val_name.equalsIgnoreCase(\"methyl  protons\"))\n");
				filew.write("\t\t\t\t\tval_name = \"methyl protons\";\n\n");

				filew.write("\t\t\t\tif (val_name.equalsIgnoreCase(\"methl\"))\n");
				filew.write("\t\t\t\t\tval_name = \"methyl\";\n\n");

				filew.write("\t\t\t\tif (val_name.equalsIgnoreCase(\"methly protons\"))\n");
				filew.write("\t\t\t\t\tval_name = \"methyl protons\";\n\n");

				filew.write("\t\t\t\tif (val_name.equalsIgnoreCase(\"methy protons\"))\n");
				filew.write("\t\t\t\t\tval_name = \"methyl protons\";\n\n");

				filew.write("\t\t\t\tif (val_name.equalsIgnoreCase(\"portons\"))\n");
				filew.write("\t\t\t\t\tval_name = \"protons\";\n\n");

				filew.write("\t\t\t\tif (val_name.equalsIgnoreCase(\"Phosphade phasphorus\"))\n");
				filew.write("\t\t\t\t\tval_name = \"Phosphate phosphorus\";\n\n");

				filew.write("\t\t\t\tif (val_name.equalsIgnoreCase(\"mehtyl protons\"))\n");
				filew.write("\t\t\t\t\tval_name = \"methyl protons\";\n\n");

				filew.write("\t\t\t\tif (val_name.equalsIgnoreCase(\"ptotons\"))\n");
				filew.write("\t\t\t\t\tval_name = \"protons\";\n\n");

				filew.write("\t\t\t\tif (val_name.equalsIgnoreCase(\"methly carbons\"))\n");
				filew.write("\t\t\t\t\tval_name = \"methyl carbons\";\n\n");

				filew.write("\t\t\t} else\n");
				filew.write("\t\t\t\tval_name = \"na\";\n\n\t\t}\n\n");

			}

			// item='sample_state', category='BMRBx:conformer_family_coord_set*expt', 'BMRBx:cross_correlation*experiment', 'BMRBx:h_exch*experiment', 'BMRBx:heteronucl*experiment', 'BMRBx:homonucl_noe_experiment', 'BMRBx:order_parameter_experiment', 'BMRBx:other_data_experiment', 'BMRBx:ph_titration_experiment', 'BMRBx:rdc_experiment', 'nmstar:spectral_density_experiment'
			if ((class_name.endsWith("Experiment") || class_name.endsWith("Expt")) && (class_name.startsWith("ConformerFamilyCoordSet") || class_name.startsWith("CrossCorrelation") || class_name.startsWith("HExch") || class_name.startsWith("Heteronucl") || class_name.startsWith("Homonucl") || class_name.startsWith("OrderParameter") || class_name.startsWith("OtherData") || class_name.startsWith("PHTitration") || class_name.startsWith("SpectralDensity") || class_name.startsWith("RDC") || class_name.startsWith("SpectralDensity"))) {

				filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + " || val_name.equals(\"solution\")) && method_name.equalsIgnoreCase(\"setSampleState\"))\n");
				filew.write("\t\t\tval_name = \"isotropic\";\n\n");

			}

			filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (nil_method_name == null || nil_method_name.isEmpty()) && !method_name.equalsIgnoreCase(\"setSampleState\") && (" + empty_check("val_name") + "))\n");
			filew.write("\t\t\tval_name = \"na\";\n\n");

			// item='*biological_function*', '*citation*', '*details*', '*name*', '*relation_ship*','*task*', '*title*'
			filew.write("\t\tif ((method_name.contains(\"BiologicalFunction\") || method_name.contains(\"Citation\") || method_name.contains(\"Details\") || method_name.contains(\"Name\") || method_name.contains(\"Relationship\") || method_name.contains(\"Task\") || method_name.contains(\"Title\")) && !(" + empty_check("val_name") + ") && val_name.contains(\"\\\"\")) {\n\n");

			filew.write("\t\t\tif ((val_name.startsWith(\"\\\"\") && val_name.endsWith(\"\\\"\")) || (((val_name.startsWith(\"\\\"\") && val_name.lastIndexOf(\"\\\"\") + 2 >= val_name.length()) || method_name.contains(\"Task\")) && val_name.replaceAll(\"[^\\\"]\", \"\").length() == 2) || (val_name.replaceAll(\"[^\\\"]\", \"\").length() % 2) == 1)\n");
			filew.write("\t\t\t\tval_name = val_name.replaceAll(\"\\\"\", \"\");\n\n");

			filew.write("\t\t}\n\n");

			write_remed_log_code(filew);

			filew.write("\t\t}\n\n");

			filew.write("\t\tif (" + empty_check("val_name") + ")\n");
			filew.write("\t\t\tnil = true;\n\n");

			write_missing_log_code(filew);

			filew.write("\t\tClass<?> _class = list.getClass();\n\n");

			filew.write("\t\ttry {\n");
			filew.write("\t\t\tMethod method = _class.getMethod(method_name, new Class[]{ String.class });\n");
			filew.write("\t\t\t" + (class_name.equalsIgnoreCase("Entry") ? "" : ";//") + "Method nil_method = null;\n\n");

			filew.write("\t\t\t" + (class_name.equalsIgnoreCase("Entry") ? "" : ";//") + "if (nil_method_name != null && !nil_method_name.isEmpty())\n");
			filew.write("\t\t\t\t" + (class_name.equalsIgnoreCase("Entry") ? "" : ";//") + "nil_method = _class.getMethod(nil_method_name);\n\n");

			filew.write("\t\t\ttry {\n");
			filew.write("\t\t\t\tif (nil)\n");
			filew.write("\t\t\t\t\t" + (class_name.equalsIgnoreCase("Entry") ? "" : ";//") + "nil_method.invoke(list);\n");
			filew.write("\t\t\t\telse\n");
			filew.write("\t\t\t\t\tmethod.invoke(list, val_name);\n");
			filew.write("\t\t\t} catch (IllegalAccessException e) {\n");
			filew.write("\t\t\t\te.printStackTrace();\n");
			filew.write("\t\t\t} catch (IllegalArgumentException e) {\n");
			filew.write("\t\t\t\te.printStackTrace();\n");
			filew.write("\t\t\t} catch (InvocationTargetException e) {\n");
			filew.write("\t\t\t\te.printStackTrace();\n\t\t\t}\n\n");

			filew.write("\t\t} catch (NoSuchMethodException e) {\n");
			filew.write("\t\t\te.printStackTrace();\n");
			filew.write("\t\t\tSystem.exit(1);\n");
			filew.write("\t\t} catch (SecurityException e) {\n");
			filew.write("\t\t\te.printStackTrace();\n\t\t}\n\n");

			filew.write("\t\treturn true;\n\t}\n");

			// item='atom_id', category='BMRBx:atom_nomenclature'
			if (class_name.equalsIgnoreCase("AtomNomenclature")) {

				filew.write("\n\tprivate static boolean set_string_atom_id(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, int atom_id, FileWriter logw) {\n\n");

				filew.write("\t\tboolean nil = false;\n\n");

				filew.write("\t\tString _val_name = val_name;\n\n");

				filew.write("\t\tif (" + empty_check("val_name") + ")\n");
				filew.write("\t\t\tnil = true;\n\n");

				write_missing_log_code(filew);

				write_invoke_string_method(filew);

			}

			// item='atom_id_2', category='BMRBx:bond'
			if (class_name.equalsIgnoreCase("Bond")) {

				filew.write("\n\tprivate static boolean set_string_atom_id_2(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, String comp_id_1, String atom_id_1, String comp_id_2, FileWriter logw) {\n\n");

				filew.write("\t\tboolean nil = false;\n\n");

				filew.write("\t\tString _val_name = val_name;\n\n");

				filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml)\n");
				filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_bond + ".getHECAtomID2(val_name, comp_id_1, atom_id_1, comp_id_2);\n\n");

				write_remed_log_code(filew);

				filew.write("\t\tif (" + empty_check("val_name") + ")\n");
				filew.write("\t\t\tnil = true;\n\n");

				write_missing_log_code(filew);

				write_invoke_string_method(filew);

			}

			// item='id', category='BMRBx:chem_comp'
			if (class_name.equalsIgnoreCase("ChemComp")) {

				filew.write("\n\tprivate static boolean set_string_id(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, String name, FileWriter logw) {\n\n");

				filew.write("\t\tboolean nil = false;\n\n");

				filew.write("\t\tString _val_name = val_name;\n\n");

				filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");
				filew.write("\t\t\tval_name = name;\n\n");

				write_remed_log_code(filew);

				filew.write("\t\tif (" + empty_check("val_name") + ")\n");
				filew.write("\t\t\tnil = true;\n\n");

				write_missing_log_code(filew);

				write_invoke_string_method(filew);

			}

			// item='doi', 'title', 'journal_name_full', 'journal_abbrev', 'journal_volume', 'journal_issue', 'journal_issn', 'page_first', 'page_last', 'title', 'year', category='BMRBx:citation'
			if (class_name.equalsIgnoreCase("Citation")) {

				filew.write("\n\tprivate static boolean set_string_by_doc_sum(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Node doc_sum, FileWriter logw) {\n\n");

				filew.write("\t\tboolean nil = false;\n\n");

				filew.write("\t\tString _val_name = val_name;\n\n");

				filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && doc_sum != null) {\n");
				filew.write("\t\t\t//if (method_name.equalsIgnoreCase(\"setDoi\"))\n");
				filew.write("\t\t\t\t//val_name = " + file_prefix + "_" + BMSxTool_DOM.util_citation + ".getDOI(val_name, doc_sum);\n");
				filew.write("\t\t\tif (method_name.equalsIgnoreCase(\"setTitle\"))\n");
				filew.write("\t\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_citation + ".getTitle(val_name, doc_sum);\n");
				filew.write("\t\t\tif (method_name.equalsIgnoreCase(\"setJournalNameFull\"))\n");
				filew.write("\t\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_citation + ".getJournalNameFull(val_name, doc_sum);\n");
				filew.write("\t\t\tif (method_name.equalsIgnoreCase(\"setJournalAbbrev\"))\n");
				filew.write("\t\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_citation + ".getJournalAbbrev(val_name, doc_sum);\n");
				filew.write("\t\t\tif (method_name.equalsIgnoreCase(\"setJournalVolume\"))\n");
				filew.write("\t\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_citation + ".getJournalVolume(val_name, doc_sum);\n");
				filew.write("\t\t\tif (method_name.equalsIgnoreCase(\"setJournalIssue\"))\n");
				filew.write("\t\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_citation + ".getJournalIssue(val_name, doc_sum);\n");
				filew.write("\t\t\tif (method_name.equalsIgnoreCase(\"setJournalIssn\"))\n");
				filew.write("\t\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_citation + ".getJournalISSN(val_name, doc_sum);\n");
				filew.write("\t\t\tif (method_name.equalsIgnoreCase(\"setPageFirst\"))\n");
				filew.write("\t\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_citation + ".getPageFirst(val_name, doc_sum);\n");
				filew.write("\t\t\tif (method_name.equalsIgnoreCase(\"setPageLast\"))\n");
				filew.write("\t\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_citation + ".getPageLast(val_name, doc_sum);\n");
				filew.write("\t\t\tif (method_name.equalsIgnoreCase(\"setYear\"))\n");
				filew.write("\t\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_citation + ".getYear(val_name, doc_sum);\n");
				filew.write("\t\t}\n\n");

				write_remed_log_code(filew);

				filew.write("\t\tif (" + empty_check("val_name") + ")\n");
				filew.write("\t\t\tnil = true;\n\n");

				write_missing_log_code(filew);

				write_invoke_string_method(filew);

			}

			// item='enzyme_commission_number', category='BMRBx:assembly'
			// item='ec_number', category='BMRBx:entity'
			if (class_name.equalsIgnoreCase("Assembly") || class_name.equalsIgnoreCase("Entity")) {

				filew.write("\n\tprivate static boolean set_string_ec_number(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, String entry_id, FileWriter logw) {\n\n");

				filew.write("\t\tboolean nil = false;\n\n");

				filew.write("\t\tString _val_name = val_name;\n\n");

				filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && !(" + empty_check("val_name") + "))\n");
				if (class_name.equalsIgnoreCase("Assembly"))
					filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_assembly + ".checkECNumber(val_name, entry_id);\n\n");
				else
					filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_entity + ".checkECNumber(val_name, entry_id);\n\n");

				filew.write("\t\tif (val_name != null && val_name.equals(\"na\"))\n");
				filew.write("\t\t\tval_name = null;\n\n");

				write_remed_log_code(filew);

				filew.write("\t\tif (" + empty_check("val_name") + ")\n");
				filew.write("\t\t\tnil = true;\n\n");

				write_missing_log_code(filew);

				write_invoke_string_method(filew);

			}

			// item='accession_code', category='BMRBx:assembly_db_link', 'BMRBx:entity_db_link'
			if (class_name.equalsIgnoreCase("AssemblyDbLink") || class_name.equalsIgnoreCase("EntityDbLink")) {

				filew.write("\n\tprivate static boolean set_string_accession_code(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, String entry_id, FileWriter logw) {\n\n");

				filew.write("\t\tboolean nil = false;\n\n");

				filew.write("\t\tString _val_name = val_name;\n\n");

				filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");
				filew.write("\t\t\tval_name = \"na\";\n\n");

				write_remed_log_code(filew);

				filew.write("\t\tif (" + empty_check("val_name") + ")\n");
				filew.write("\t\t\tnil = true;\n\n");

				write_missing_log_code(filew);

				write_invoke_string_method(filew);

			}

			// item='polymer_seq_one_letter_code', 'polymer_seq_one_letter_code_can', category='BMRBx:entity'
			if (class_name.equalsIgnoreCase("Entity")) {

				filew.write("\n\tprivate static boolean set_string_polymer_seq_one_letter_code(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, String entry_id, String entity_id, FileWriter logw) {\n\n");

				filew.write("\t\tboolean nil = false;\n\n");

				filew.write("\t\tString _val_name = val_name;\n\n");

				filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml)\n");
				filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_entitycompindex + ".getPolySeqOneLetterCode(conn_bmrb, entry_id, entity_id);\n\n");

				write_remed_log_code(filew);

				filew.write("\t\tif (" + empty_check("val_name") + ")\n");
				filew.write("\t\t\tnil = true;\n\n");

				write_missing_log_code(filew);

				write_invoke_string_method(filew);

				filew.write("\n\tprivate static boolean set_string_polymer_seq_one_letter_code_can(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, String entry_id, String entity_id, FileWriter logw) {\n\n");

				filew.write("\t\tboolean nil = false;\n\n");

				filew.write("\t\tString _val_name = val_name;\n\n");

				filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml)\n");
				filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_entitycompindex + ".getPolySeqOneLetterCodeCan(conn_bmrb, entry_id, entity_id);\n\n");

				write_remed_log_code(filew);

				filew.write("\t\tif (" + empty_check("val_name") + ")\n");
				filew.write("\t\t\tnil = true;\n\n");

				write_missing_log_code(filew);

				write_invoke_string_method(filew);

			}

			// item='ncbi_taxonomy_id', category='BMRBx:entity_natural_src'
			// item='host_org_ncbi_taxonomy_id', category='BMRBx:entity_experimental_src'
			if (class_name.equalsIgnoreCase("EntityNaturalSrc") || class_name.equalsIgnoreCase("EntityExperimentalSrc")) {

				filew.write("\n\tprivate static boolean set_string_ncbi_taxonomy_id(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_tax, FileWriter logw, FileWriter errw) {\n\n");

				filew.write("\t\tboolean nil = false;\n\n");

				filew.write("\t\tString _val_name = val_name;\n\n");

				filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && !" + file_prefix + "_" + BMSxTool_DOM.util_tax + ".isValidNCBITaxonomyID(conn_tax, val_name)) {\n\n");

				filew.write("\t\t\tif (!(" + empty_check("val_name") + ") && val_name.matches(\"^[0-9]+$\") && !val_name.equals(\"0\")) {\n\n");

				if (class_name.equalsIgnoreCase("EntityNaturalSrc")) {

					filew.write("\t\t\t\tSystem.err.println(\"class_name:" + class_name + " method_name:ncbi_taxonomy_id val_name:\" + val_name);\n");
					filew.write("\t\t\t\ttry {\n");
					filew.write("\t\t\t\t\terrw.write(\"class_name:" + class_name + " method_name:ncbi_taxonomy_id val_name:\" + val_name + \"\\n\");\n");
					filew.write("\t\t\t\t} catch (IOException e) {\n");
					filew.write("\t\t\t\t\te.printStackTrace();\n");
					filew.write("\t\t\t\t}\n\n");

				}

				if (class_name.equalsIgnoreCase("EntityExperimentalSrc")) {

					filew.write("\t\t\t\tSystem.err.println(\"class_name:" + class_name + " method_name:host_org_ncbi_taxonomy_id val_name:\" + val_name);\n");
					filew.write("\t\t\t\ttry {\n");
					filew.write("\t\t\t\t\terrw.write(\"class_name:" + class_name + " method_name:host_org_ncbi_taxonomy_id val_name:\" + val_name + \"\\n\");\n");
					filew.write("\t\t\t\t} catch (IOException e) {\n");
					filew.write("\t\t\t\t\te.printStackTrace();\n");
					filew.write("\t\t\t\t}\n\n");

				}

				filew.write("\t\t\t}\n\n");

				if (class_name.equalsIgnoreCase("EntityNaturalSrc")) {

					filew.write("\t\t\tif (" + empty_check("val_name") + ")\n");
					filew.write("\t\t\t\tval_name = \"n/a\";\n\n");

				}

				if (class_name.equalsIgnoreCase("EntityExperimentalSrc"))
					filew.write("\t\t\tval_name = null;\n\n");
				else
					write_remed_log_code(filew);

				filew.write("\t\t}\n\n");

				filew.write("\t\tif (" + empty_check("val_name") + ")\n");
				filew.write("\t\t\tnil = true;\n\n");

				write_missing_log_code(filew);

				write_invoke_string_method(filew);

			}

			// category='BMRBx:entity_experimental_src'
			if (class_name.equalsIgnoreCase("EntityExperimentalSrc")) {

				// item='host_org_genus'
				filew.write("\n\tprivate static boolean set_string_host_org_genus(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_tax, String ncbi_taxonomy_id, FileWriter logw) {\n\n");

				filew.write("\t\tboolean nil = false;\n\n");

				filew.write("\t\tString _val_name = val_name;\n\n");

				filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml)\n");
				filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_tax + ".getGenus(val_name, conn_tax, ncbi_taxonomy_id);\n\n");

				write_remed_log_code(filew);

				filew.write("\t\tif (" + empty_check("val_name") + ")\n");
				filew.write("\t\t\tnil = true;\n\n");

				write_missing_log_code(filew);

				write_invoke_string_method(filew);

				// item='host_org_species'
				filew.write("\n\tprivate static boolean set_string_host_org_species(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_tax, String ncbi_taxonomy_id, FileWriter logw) {\n\n");

				filew.write("\t\tboolean nil = false;\n\n");

				filew.write("\t\tString _val_name = val_name;\n\n");

				filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml)\n");
				filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_tax + ".getSpecies(val_name, conn_tax, ncbi_taxonomy_id);\n\n");

				write_remed_log_code(filew);

				filew.write("\t\tif (" + empty_check("val_name") + ")\n");
				filew.write("\t\t\tnil = true;\n\n");

				write_missing_log_code(filew);

				write_invoke_string_method(filew);

				// item='host_org_name_common'
				filew.write("\n\tprivate static boolean set_string_host_org_name_common(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_tax, String ncbi_taxonomy_id, FileWriter logw) {\n\n");

				filew.write("\t\tboolean nil = false;\n\n");

				filew.write("\t\tString _val_name = val_name;\n\n");

				filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml)\n");
				filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_tax + ".getOrganismCommonName(val_name, conn_tax, ncbi_taxonomy_id);\n\n");

				filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");
				filew.write("\t\t\tval_name = \"not available\";\n\n");

				write_remed_log_code(filew);

				filew.write("\t\tif (" + empty_check("val_name") + ")\n");
				filew.write("\t\t\tnil = true;\n\n");

				write_missing_log_code(filew);

				write_invoke_string_method(filew);

			}

			// category='BMRBx:entity_natural_src'
			if (class_name.equalsIgnoreCase("EntityNaturalSrc")) {

				// item='genus'
				filew.write("\n\tprivate static boolean set_string_genus(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_tax, String ncbi_taxonomy_id, FileWriter logw) {\n\n");

				filew.write("\t\tboolean nil = false;\n\n");

				filew.write("\t\tString _val_name = val_name;\n\n");

				filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml) {\n\n");

				filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_tax + ".getGenus(val_name, conn_tax, ncbi_taxonomy_id);\n\n");

				filew.write("\t\t\tif ((" + empty_check("val_name") + ") && (nil_method_name == null || nil_method_name.isEmpty()))\n");
				filew.write("\t\t\t\tval_name = \"Not applicable\";\n\n");

				filew.write("\t\t}\n\n");

				write_remed_log_code(filew);

				filew.write("\t\tif (" + empty_check("val_name") + ")\n");
				filew.write("\t\t\tnil = true;\n\n");

				write_missing_log_code(filew);

				write_invoke_string_method(filew);

				// item='species'
				filew.write("\n\tprivate static boolean set_string_species(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_tax, String ncbi_taxonomy_id, FileWriter logw) {\n\n");

				filew.write("\t\tboolean nil = false;\n\n");

				filew.write("\t\tString _val_name = val_name;\n\n");

				filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml) {\n\n");

				filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_tax + ".getSpecies(val_name, conn_tax, ncbi_taxonomy_id);\n\n");

				filew.write("\t\t\tif ((" + empty_check("val_name") + ") && (nil_method_name == null || nil_method_name.isEmpty()))\n");
				filew.write("\t\t\t\tval_name = \"unidentified\";\n\n");

				filew.write("\t\t}\n\n");

				write_remed_log_code(filew);

				filew.write("\t\tif (" + empty_check("val_name") + ")\n");
				filew.write("\t\t\tnil = true;\n\n");

				write_missing_log_code(filew);

				write_invoke_string_method(filew);

				// item='organism_name_common'
				filew.write("\n\tprivate static boolean set_string_organism_name_common(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_tax, String ncbi_taxonomy_id, FileWriter logw) {\n\n");

				filew.write("\t\tboolean nil = false;\n\n");

				filew.write("\t\tString _val_name = val_name;\n\n");

				filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml)\n");
				filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_tax + ".getOrganismCommonName(val_name, conn_tax, ncbi_taxonomy_id);\n\n");

				filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");
				filew.write("\t\t\tval_name = \"not available\";\n\n");

				write_remed_log_code(filew);

				filew.write("\t\tif (" + empty_check("val_name") + ")\n");
				filew.write("\t\t\tnil = true;\n\n");

				write_missing_log_code(filew);

				write_invoke_string_method(filew);

				// item='organism_name_scientific'
				filew.write("\n\tprivate static boolean set_string_organism_name_scientific(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_tax, String entry_id, String ncbi_taxonomy_id, FileWriter logw, FileWriter errw) {\n\n");

				filew.write("\t\tboolean nil = false;\n\n");

				filew.write("\t\tString _val_name = val_name;\n\n");

				filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml) {\n\n");

				filew.write("\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_tax + ".getOrganismScientificName(val_name, conn_tax, ncbi_taxonomy_id);\n\n");

				filew.write("\t\tif (val_name == null || val_name.equals(\"not applicable\"))\n");
				filew.write("\t\t\tval_name = \"unidentified\";\n\n");

				write_remed_log_code(filew);

				filew.write("\t\t}\n\n");

				filew.write("\t\tif (" + empty_check("val_name") + ")\n");
				filew.write("\t\t\tnil = true;\n\n");

				write_missing_log_code(filew);

				write_invoke_string_method(filew);

			}

			// item='database_code', category='BMRBx:natural_source_db'
			if (class_name.equalsIgnoreCase("NaturalSourceDb")) {

				filew.write("\n\tprivate static boolean set_string_database_code(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, String accession_code, FileWriter logw) {\n\n");

				filew.write("\t\tboolean nil = false;\n\n");

				filew.write("\t\tString _val_name = val_name;\n\n");

				filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");
				filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_naturalsourcedb + ".getDatabaseCode(val_name, accession_code);\n\n");

				filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");
				filew.write("\t\t\tval_name = \"na\";\n\n");

				write_remed_log_code(filew);

				filew.write("\t\tif (" + empty_check("val_name") + ")\n");
				filew.write("\t\t\tnil = true;\n\n");

				write_missing_log_code(filew);

				write_invoke_string_method(filew);

			}

			// item='database_accession_code', category='BMRBx:related_entries'
			if (class_name.equalsIgnoreCase("RelatedEntries")) {

				filew.write("\n\tprivate static boolean set_string_database_accession_code(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, String entry_id, FileWriter logw) {\n\n");

				filew.write("\t\tboolean nil = false;\n\n");

				filew.write("\t\tString _val_name = val_name;\n\n");

				filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");
				filew.write("\t\t\tval_name = \"na\";\n\n");

				write_remed_log_code(filew);

				filew.write("\t\tif (" + empty_check("val_name") + ")\n");
				filew.write("\t\t\tnil = true;\n\n");

				write_missing_log_code(filew);

				write_invoke_string_method(filew);

			}

			// item='spectral_region', category='BMRBx:spectral_dim'
			if (class_name.equalsIgnoreCase("SpectralDim")) {

				filew.write("\n\tprivate static boolean set_string_spectral_region(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, String atom_type, FileWriter logw) {\n\n");

				filew.write("\t\tboolean nil = false;\n\n");

				filew.write("\t\tString _val_name = val_name;\n\n");

				filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");
				filew.write("\t\t\tval_name = atom_type;\n\n");

				filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");
				filew.write("\t\t\tval_name = \"na\";\n\n");

				write_remed_log_code(filew);

				filew.write("\t\tif (" + empty_check("val_name") + ")\n");
				filew.write("\t\t\tnil = true;\n\n");

				write_missing_log_code(filew);

				write_invoke_string_method(filew);

			}

			// item='charge', category='BMRBx:chem_comp_atom'
			if (class_name.equalsIgnoreCase("ChemCompAtom")) {

				// item='host_org_genus'
				filew.write("\n\tprivate static boolean set_string_charge(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, String entry_id, String val_name_can, FileWriter logw) {\n\n");

				filew.write("\t\tboolean nil = false;\n\n");

				filew.write("\t\tString _val_name = val_name;\n\n");

				filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (entry_id.equals(\"bmse000098\") || entry_id.equals(\"bmse000126\") || entry_id.equals(\"bmse000180\") || entry_id.equals(\"bmse000888\") || entry_id.equals(\"bmse000978\"))) {\n\n");

				filew.write("\t\t\tif (!(" + empty_check("val_name") + ") && val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)?$\"))\n");
				filew.write("\t\t\t\tval_name = val_name_can;\n\n");

				write_remed_log_code(filew);

				filew.write("\t\t}\n\n");

				filew.write("\t\tif (" + empty_check("val_name") + ")\n");
				filew.write("\t\t\tnil = true;\n\n");

				write_missing_log_code(filew);

				write_invoke_string_method(filew);

			}

			// item='pdbx_name', category='BMRBx:citation_author', 'BMRBx:citation_editor', 'BMRBx:entry_author' 
			if (class_name.equalsIgnoreCase("CitationAuthor") || class_name.equalsIgnoreCase("CitationEditor") || class_name.equalsIgnoreCase("EntryAuthor")) {

				filew.write("\n\tprivate static boolean set_string_pdbx_name(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String family_name, String given_name, String middle_initials, FileWriter logw) {\n\n");

				filew.write("\t\tboolean nil = false;\n\n");

				filew.write("\t\tString val_name = (family_name != null ? family_name.replaceFirst(\"\\\\.$\", \"\") : null);\n\n");

				filew.write("\t\tString _val_name = val_name;\n\n");

				filew.write("\t\tif (!(" + empty_check("given_name") + ")) {\n\n");

				filew.write("\t\t\tval_name += \", \" + given_name.toUpperCase().charAt(0) + \".\";\n\n");

				filew.write("\t\t\tif (!(" + empty_check("middle_initials") + ")) {\n\n");

				filew.write("\t\t\t\tval_name += middle_initials.toUpperCase();\n\n");

				filew.write("\t\t\t\tif (!val_name.endsWith(\".\"))\n");
				filew.write("\t\t\t\t\tval_name += \".\";\n\n");

				filew.write("\t\t\t}\n\n\t\t}\n\n");

				filew.write("\t\tif (" + empty_check("val_name") + ")\n");
				filew.write("\t\t\tnil = true;\n\n");

				write_missing_log_code(filew);

				write_invoke_string_method(filew);

			}

			if (_integer) {

				if (!( /* class_name.equalsIgnoreCase("Entry") || */ class_name.equalsIgnoreCase("Release") || class_name.equalsIgnoreCase("SgProject"))) {

					filew.write("\n\tprivate static boolean set_integer(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (nil_method_name == null || nil_method_name.isEmpty()) && !method_name.equalsIgnoreCase(\"setSampleID\") && !method_name.equalsIgnoreCase(\"setExperimentID\") && !method_name.equalsIgnoreCase(\"setStudyID\") && !method_name.equalsIgnoreCase(\"setAssemblyID\") && (method_name.contains(\"Id\") || method_name.contains(\"Number\") || method_name.contains(\"Count\")) && !method_name.contains(\"ListId\") && (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\")))\n");
					filew.write("\t\t\tval_name = \"0\";\n\n");

					if (class_name.equalsIgnoreCase("ConformerStatList")) {

						filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (nil_method_name == null || nil_method_name.isEmpty()) && (method_name.equalsIgnoreCase(\"setConformerCalculatedTotalNum\") || method_name.equalsIgnoreCase(\"setRepresentativeConformer\")) && (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\")))\n");
						filew.write("\t\t\tval_name = \"0\";\n\n");

					}

					if (class_name.equalsIgnoreCase("PhTitrationExperiment")) {

						filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (nil_method_name == null || nil_method_name.isEmpty()) && (method_name.equalsIgnoreCase(\"setSampleID\") || method_name.equalsIgnoreCase(\"setExperimentID\")) && (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\")))\n");
						filew.write("\t\t\tval_name = \"0\";\n\n");

					}

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\"))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigint_method(filew);

				}

				// item='id', 'BMRBx:entity_experimental_src', 'BMRBx:entity_purity', 'BMRBx:nmr_spectrometer_view'
				/*
				if (class_name.equalsIgnoreCase("EntityExperimentalSrc") || class_name.equalsIgnoreCase("EntityPurity") || class_name.equalsIgnoreCase("NMRSpectrometerView")) {

					filew.write("\n\tprivate static boolean set_integer_id(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, int id, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\")))\n");
					filew.write("\t\t\tval_name = String.valueOf(id + 1);\n\n");

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + ")\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigint_method(filew);

				}
				 */

				// item='entity_natural_src_id', category='BMRBx:natural_source_db'
				if (class_name.equalsIgnoreCase("NaturalSourceDb")) {

					filew.write("\n\tprivate static boolean set_integer_entity_natural_src_id(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, int id, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");
					filew.write("\t\t\tval_name = String.valueOf(id + 1);\n\n");

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\"))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigint_method(filew);

				}

				// item='sg_project_id', category='nmrstr:sg_project'
				if (class_name.equalsIgnoreCase("SGProject")) {

					filew.write("\n\tprivate static boolean set_integer_sg_project_id(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, int id, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");
					filew.write("\t\t\tval_name = String.valueOf(id + 1);\n\n");

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\"))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigint_method(filew);

				}

				// item='release_number', category='BMRBx:release'
				if (class_name.equalsIgnoreCase("Release")) {

					filew.write("\n\tprivate static boolean set_integer_release_number(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, int id, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");
					filew.write("\t\t\tval_name = String.valueOf(id + 1);\n\n");

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\"))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigint_method(filew);

				}

				// item='software_id', category='BMRBx:*software'
				if (class_name.endsWith("Software") && !class_name.startsWith("Software")) {

					filew.write("\n\tprivate static boolean set_integer_software_id(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, int id, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");
					filew.write("\t\t\tval_name = String.valueOf(id + 1);\n\n");

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\"))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigint_method(filew);

				}

				// item='experiment_id', 'sample_id', category='BMRBx:coupling_constant_experiment', 'BMRBx:rdc_experiment'
				if (class_name.equalsIgnoreCase("CouplingConstantExperiment") || class_name.equalsIgnoreCase("RDCExperiment") ) {

					filew.write("\n\tprivate static boolean set_integer_experiment_id(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, String entry_id, String sample_id, String list_id, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");

					if (class_name.equalsIgnoreCase("CouplingConstantExperiment"))
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_couplingconstantexperiment + ".getExperimentID(val_name, conn_bmrb, entry_id, sample_id, list_id);\n\n");
					if (class_name.equalsIgnoreCase("RDCExperiment"))
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_rdcexperiment + ".getExperimentID(val_name, conn_bmrb, entry_id, sample_id, list_id);\n\n");

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\"))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigint_method(filew);

					filew.write("\n\tprivate static boolean set_integer_sample_id(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, String entry_id, String experiment_id, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");
					filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".getSampleID(val_name, conn_bmrb, entry_id, experiment_id);\n\n");

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\"))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigint_method(filew);

				}

				// item='sample_condition_list_id', category='BMRBx:conformer_family_coord_set', 'BMRBx:coupling_constant_list', 'BMRBx:other_data_type_list', 'BMRBx:rdc_list'
				if (class_name.equalsIgnoreCase("ConformerFamilyCoordSet") || class_name.equalsIgnoreCase("CouplingConstantList") || class_name.equalsIgnoreCase("OtherDataTypeList") || class_name.equalsIgnoreCase("RDCList")) {

					filew.write("\n\tprivate static boolean set_integer_sample_condition_list_id(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, String entry_id, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");
					filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".getSampleConditionListID(val_name, conn_bmrb, entry_id);\n\n");

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\"))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigint_method(filew);

				}

				// item='experiment_id', 'sample_id', category='BMRBx:chem_shift*experiment', 'BMRBx:conformer_family_coord_set*expt', 'BMRBx:crosscorrlation*experiment', 'BMRBx:h_exch*experiment', 'BMRBx:heteronucl*experiment', 'BMRBx:homonucl_noe_experiment', 'BMRBx:order_parameter_experiment', 'BMRBx:ph_titration_experiment', 'BMRBx:spectral_density_experiment'
				if ((class_name.endsWith("Experiment") || class_name.endsWith("Expt")) && (class_name.startsWith("ChemShift") || class_name.startsWith("ConformerFamilyCoordSet") || class_name.startsWith("CrossCorrelation") || class_name.startsWith("HExch") || class_name.startsWith("Heteronucl") || class_name.startsWith("Homonucl") || class_name.startsWith("OrderParameter") || class_name.startsWith("OtherData") || class_name.startsWith("PHTitration") || class_name.startsWith("SpectralDensity"))) {

					filew.write("\n\tprivate static boolean set_integer_experiment_id(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, String entry_id, String sample_id, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");
					if (class_name.equalsIgnoreCase("HExchProtectionFactExperiment"))
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".getExperimentID(val_name, conn_bmrb, entry_id, sample_id, " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".experiment_type.HExchProtectionFact);\n\n");
					if (class_name.equalsIgnoreCase("HExchRateExperiment"))
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".getExperimentID(val_name, conn_bmrb, entry_id, sample_id, " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".experiment_type.HExchRate);\n\n");
					if (class_name.equalsIgnoreCase("HeteronuclT1Experiment"))
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".getExperimentID(val_name, conn_bmrb, entry_id, sample_id, " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".experiment_type.HeteronuclT1);\n\n");
					if (class_name.equalsIgnoreCase("HeteronuclT2Experiment"))
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".getExperimentID(val_name, conn_bmrb, entry_id, sample_id, " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".experiment_type.HeteronuclT2);\n\n");
					if (class_name.equalsIgnoreCase("HeteronuclT1RhoExperiment"))
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".getExperimentID(val_name, conn_bmrb, entry_id, sample_id, " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".experiment_type.HeteronuclT1Rho);\n\n");
					if (class_name.equalsIgnoreCase("HeteronuclNOEExperiment"))
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".getExperimentID(val_name, conn_bmrb, entry_id, sample_id, " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".experiment_type.HeteronuclNOE);\n\n");
					if (class_name.equalsIgnoreCase("HomonuclNOEExperiment"))
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".getExperimentID(val_name, conn_bmrb, entry_id, sample_id, " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".experiment_type.HomonuclNOE);\n\n");
					if (class_name.startsWith("ChemShift") || class_name.startsWith("ConformerFamilyCoordSet") || class_name.startsWith("CrossCorrelation") || class_name.equalsIgnoreCase("OrderParameterExperiment") || class_name.equalsIgnoreCase("PHTitrationExperiment"))
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".getExperimentID(val_name, conn_bmrb, entry_id, sample_id);\n\n");

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\"))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigint_method(filew);

					filew.write("\n\tprivate static boolean set_integer_sample_id(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, String entry_id, String experiment_id, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");
					filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".getSampleID(val_name, conn_bmrb, entry_id, experiment_id);\n\n");

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\"))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigint_method(filew);

				}

				// item='sample_id', category='BMRBx:entity_purity'
				if (class_name.equalsIgnoreCase("EntityPurity")) {

					filew.write("\n\tprivate static boolean set_integer_sample_id(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, String entry_id, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");
					filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".getSampleID(val_name, conn_bmrb, entry_id);\n\n");

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\"))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigint_method(filew);

				}

				// item='experiment_id', category='BMRBx:binding_result'
				if (class_name.equalsIgnoreCase("BindingResult")) {

					filew.write("\n\tprivate static boolean set_integer_experiment_id(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, String entry_id, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml)\n");
					filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".getExperimentID(val_name, conn_bmrb, entry_id, \"\");\n\n");

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\"))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigint_method(filew);

				}

				// item='experiment_id', 'BMRBx:spectral_peak_list'
				if (class_name.equalsIgnoreCase("SpectralPeakList")) {

					filew.write("\n\tprivate static boolean set_integer_experiment_id(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, String entry_id, String sample_id, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml)\n");
					filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".getExperimentID(val_name, conn_bmrb, entry_id, sample_id);\n\n");

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\"))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigint_method(filew);

				}

				// item='sample_id', 'sample_condition_list_id', category='BMRBx:experiment', 'BMRBx:spectral_peak_list'
				if (class_name.equalsIgnoreCase("Experiment") || class_name.equalsIgnoreCase("SpectralPeakList")) {

					filew.write("\n\tprivate static boolean set_integer_sample_id(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, String entry_id, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");
					filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".getSampleID(val_name, conn_bmrb, entry_id);\n\n");

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\"))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigint_method(filew);

					filew.write("\n\tprivate static boolean set_integer_sample_condition_list_id(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, String entry_id, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");
					filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".getSampleConditionListID(val_name, conn_bmrb, entry_id);\n\n");

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\"))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigint_method(filew);

				}

				// item='assigned_chem_shift_list_id', category='BMRBx:assigned_peak_chem_shift'
				if (class_name.equalsIgnoreCase("AssignedPeakChemShift")) {

					filew.write("\n\tprivate static boolean set_integer_assigned_chem_shift_list_id(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, String entry_id, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");
					filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".getAssignedChemShiftListID(val_name, conn_bmrb, entry_id);\n\n");

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\"))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigint_method(filew);

				}

				// item='spectral_peak_list_id', category='BMRBx:gen_dist_constraint'
				if (class_name.equalsIgnoreCase("GenDistConstraint")) {

					filew.write("\n\tprivate static boolean set_integer_spectral_peak_list_id(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, String entry_id, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");
					filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".getSpectralPeakListID(val_name, conn_bmrb, entry_id);\n\n");

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\"))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigint_method(filew);

				}

				// item='number_of_monomers', category='BMRBx:entity'
				if (class_name.equalsIgnoreCase("Entity")) {

					filew.write("\n\tprivate static boolean set_integer_number_of_monomers(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, String entry_id, String entity_id, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml)\n");
					filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_entitycompindex + ".getNumberOfMonomers(conn_bmrb, entry_id, entity_id);\n\n");

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\"))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigint_method(filew);

				}

				// item='comp_index_id', 'entity_assembly_id', category='BMRBx:pdbx_poly_seq_scheme'
				if (class_name.equalsIgnoreCase("PDBXPolySeqScheme")) {

					filew.write("\n\tprivate static boolean set_integer_comp_index_id(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, String entry_id, String entity_id, String mon_id, String pdb_seq_num, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");
					filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_pdbxpolyseqscheme + ".getCompIndexID(val_name, conn_bmrb, entry_id, entity_id, mon_id, pdb_seq_num);\n\n");

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\"))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigint_method(filew);

					filew.write("\n\tprivate static boolean set_integer_entity_assembly_id(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, String entry_id, String entity_id, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");
					filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_pdbxpolyseqscheme + ".getEntityAssemblyID(val_name, conn_bmrb, entry_id, entity_id);\n\n");

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\"))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigint_method(filew);

				}

				// item='assembly_id', category='BMRBx:sample_component'
				if (class_name.equalsIgnoreCase("SampleComponent")) {

					filew.write("\n\tprivate static boolean set_integer_assembly_id(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, String entry_id, String entity_id, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");
					filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".getAssemblyID(val_name, conn_bmrb, entry_id, entity_id);\n\n");

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\"))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigint_method(filew);

				}

				// item='study_id', category='BMRBx:study_entry_list', 'BMRBx:study_keyword'
				if (class_name.equalsIgnoreCase("StudyEntryList") || class_name.equalsIgnoreCase("StudyKeyword")) {

					filew.write("\n\tprivate static boolean set_integer_study_id(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, String entry_id, String study_list_id, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");
					filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".getStudyID(val_name, conn_bmrb, entry_id, study_list_id);\n\n");

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\"))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigint_method(filew);

				}

				// item='modeled_sample_cond_list_id', category='BMRBx:chem_shifts_calc_type'
				if (class_name.equalsIgnoreCase("ChemShiftsCalcType")) {

					filew.write("\n\tprivate static boolean set_integer_modeled_sample_cond_list_id(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, String entry_id, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");
					filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".getSampleConditionListID(val_name, conn_bmrb, entry_id);\n\n");

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\"))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigint_method(filew);

				}

				// item='tensor_list_id', category='BMRBx:theoretical_chem_shift_list'
				if (class_name.equalsIgnoreCase("TheoreticalChemShiftList")) {

					filew.write("\n\tprivate static boolean set_integer_tensor_list_id(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, String entry_id, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + "))\n");
					filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_bmrb + ".getTensorListID(val_name, conn_bmrb, entry_id);\n\n");

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\"))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigint_method(filew);

				}

			}

			if (has_decimal(node)) {

				filew.write("\n\tprivate static boolean set_decimal(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {\n\n");

				filew.write("\t\tboolean nil = false;\n\n");

				filew.write("\t\tString _val_name = val_name;\n\n");

				filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + " || (!val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)?$\") && !val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)([eE][-+]?[0-9]+)?$\"))) && (nil_method_name == null || nil_method_name.isEmpty())) {\n\n");

				// item='chem_shift_val', category='BMRBx:chem_shift_ref'
				if (class_name.equalsIgnoreCase("ChemShiftRef")) {

					filew.write("\t\t\tif (method_name.equalsIgnoreCase(\"setChemShiftVal\"))\n");
					filew.write("\t\t\t\tval_name = \"0.0\";\n\n");

				}

				// item='val', category='BMRBx:h_exch_protection_factor'
				if (class_name.equalsIgnoreCase("HExchProtectionFactor")) {

					filew.write("\t\t\tif (method_name.equalsIgnoreCase(\"setVal\"))\n");
					filew.write("\t\t\t\tval_name = \"0.0\";\n\n");

				}

				// item='order_param_val', category='BMRBx:order_param'
				if (class_name.equalsIgnoreCase("OrderParam")) {

					filew.write("\t\t\tif (method_name.equalsIgnoreCase(\"setOrderParamVal\"))\n");
					filew.write("\t\t\t\tval_name = \"0.0\";\n\n");

				}

				// item='ph_val', 'observed_nmr_param_val', category='BMRBx:ph_param'
				if (class_name.equalsIgnoreCase("PHParam")) {

					filew.write("\t\t\tif (method_name.equalsIgnoreCase(\"setPHVal\"))\n");
					filew.write("\t\t\t\tval_name = \"0.0\";\n\n");

					filew.write("\t\t\tif (method_name.equalsIgnoreCase(\"setObservedNMRParamVal\"))\n");
					filew.write("\t\t\t\tval_name = \"0.0\";\n\n");

				}

				// item='rdc_val', category='BMRBx:rdc_constraint'
				if (class_name.equalsIgnoreCase("RDCConstraint")) {

					filew.write("\t\t\tif (method_name.equalsIgnoreCase(\"setRDCVal\"))\n");
					filew.write("\t\t\t\tval_name = \"0.0\";\n\n");

				}

				// item='val', category='BMRBx:systematic_chem_shift_offset'
				if (class_name.equalsIgnoreCase("SystematicChemShiftOffset")) {

					filew.write("\t\t\tif (method_name.equalsIgnoreCase(\"setVal\"))\n");
					filew.write("\t\t\t\tval_name = \"0.0\";\n\n");

				}

				// item='spectrometer_frequency_1h', category='BMRBx:*list'
				if (class_name.endsWith("List")) {

					filew.write("\t\t\tif (method_name.equalsIgnoreCase(\"setSpectrometerFrequency1H\"))\n");
					filew.write("\t\t\t\tval_name = \"0.0\";\n\n");

				}

				write_remed_log_code(filew);

				filew.write("\t\t}\n\n");

				filew.write("\t\tif (" + empty_check("val_name") + " || (!val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)?$\") && !val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)([eE][-+]?[0-9]+)?$\")))\n");
				filew.write("\t\t\tnil = true;\n\n");

				write_missing_log_code(filew);

				write_invoke_bigdec_method(filew);

				// item='val', category='BMRBx:atom_chem_shift'
				if (class_name.equalsIgnoreCase("AtomChemShift")) {

					filew.write("\n\tprivate static boolean set_decimal_val(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, String ambiguity_code, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + " || (!val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)?$\") && !val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)([eE][-+]?[0-9]+)?$\")) || (ambiguity_code != null && ambiguity_code.equals(\"73\"))))\n");
					filew.write("\t\t\treturn false;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigdec_method(filew);

				}

				// item='formula_weight', category='BMRBx:entity'
				if (class_name.equalsIgnoreCase("Entity")) {

					filew.write("\n\tprivate static boolean set_decimal_formula_weight(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, Connection conn_le, String entry_id, String entity_id, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml) // && (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+.[0-9]{3}$\") || (!val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)?$\") && !val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)([eE][-+]?[0-9]+)?$\"))))\n");
					filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_le + ".getFormulaWeight(val_name, conn_bmrb, conn_le, entry_id, entity_id);\n\n");

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || (!val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)?$\") && !val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)([eE][-+]?[0-9]+)?$\")))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigdec_method(filew);

				}

				// item='formula_weight', category='BMRBx:chem_comp'
				if (class_name.equalsIgnoreCase("ChemComp")) {

					filew.write("\n\tprivate static boolean set_decimal_formula_weight(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_le, String pdb_code, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml) // && (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+.[0-9]{3}$\") || (!val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)?$\") && !val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)([eE][-+]?[0-9]+)?$\"))))\n");
					filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_le + ".getFormulaWeight(val_name, conn_le, pdb_code);\n\n");

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || (!val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)?$\") && !val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)([eE][-+]?[0-9]+)?$\")))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigdec_method(filew);

				}

				// item='tau_e_val', 'tau_e_val_fit_err', category='BMRBx:order_param'
				if (class_name.equalsIgnoreCase("OrderParam")) {

					filew.write("\n\tprivate static boolean set_decimal_tau_e_val(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, String entry_id, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml)\n");
					filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_orderparam + ".getTauEVal(val_name, conn_bmrb, entry_id);\n\n");

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || (!val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)?$\") && !val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)([eE][-+]?[0-9]+)?$\")))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigdec_method(filew);

				}

				// item='drawing_2d_coord_x', 'drawing_2d_coord_y', category='BMRBx:chem_comp_atom'
				if (class_name.equalsIgnoreCase("ChemCompAtom")) {

					filew.write("\n\tprivate static boolean set_decimal_drawing_2d_coord_xy(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, String entry_id, String val_name_can, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (entry_id.equals(\"bmse000098\") || entry_id.equals(\"bmse000126\") || entry_id.equals(\"bmse000180\") || entry_id.equals(\"bmse000888\") || entry_id.equals(\"bmse000978\"))) {\n\n");

					filew.write("\t\t\tif (!(" + empty_check("val_name_can") + ") && val_name_can.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)?$\"))\n");
					filew.write("\t\t\t\tval_name = val_name_can;\n\n");

					write_remed_log_code(filew);

					filew.write("\t\t}\n\n");

					filew.write("\t\tif (" + empty_check("val_name") + " || (!val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)?$\") && !val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)([eE][-+]?[0-9]+)?$\")))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigdec_method(filew);

				}

				// item='chem_shift_val', category='BMRBx:peak_char'
				if (class_name.equalsIgnoreCase("PeakChar")) {

					filew.write("\n\tprivate static boolean set_decimal_chem_shift_val(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + " || (!val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)?$\") && !val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)([eE][-+]?[0-9]+)?$\"))))\n");
					filew.write("\t\t\treturn false;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigdec_method(filew);

				}

				// item='intensity_val', category='BMRBx:peak_general_char'
				if (class_name.equalsIgnoreCase("PeakGeneralChar")) {

					filew.write("\n\tprivate static boolean set_decimal_intensity_val(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && (" + empty_check("val_name") + " || (!val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)?$\") && !val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)([eE][-+]?[0-9]+)?$\"))))\n");
					filew.write("\t\t\treturn false;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigdec_method(filew);

				}

			}

			if (has_double(node)) {

				filew.write("\n\tprivate static boolean set_double(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {\n\n");

				filew.write("\t\tboolean nil = false;\n\n");

				filew.write("\t\tString _val_name = val_name;\n\n");

				filew.write("\t\tif (" + empty_check("val_name") + " || (!val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)?$\") && !val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)([eE][-+]?[0-9]+)?$\")))\n");
				filew.write("\t\t\tnil = true;\n\n");

				write_missing_log_code(filew);

				filew.write("\t\tClass<?> _class = list.getClass();\n\n");

				filew.write("\t\ttry {\n");
				filew.write("\t\t\tMethod method = _class.getMethod(method_name, new Class[]{ double.class });\n");
				filew.write("\t\t\t;//Method nil_method = null;\n\n");

				filew.write("\t\t\t;//if (nil_method_name != null && !nil_method_name.isEmpty())\n");
				filew.write("\t\t\t\t;//nil_method = _class.getMethod(nil_method_name);\n\n");

				filew.write("\t\t\ttry {\n");
				filew.write("\t\t\t\tif (nil)\n");
				filew.write("\t\t\t\t\t;//nil_method.invoke(list);\n");
				filew.write("\t\t\t\telse\n");
				filew.write("\t\t\t\t\tmethod.invoke(list, Double.valueOf(val_name));\n");
				filew.write("\t\t\t} catch (IllegalAccessException e) {\n");
				filew.write("\t\t\t\te.printStackTrace();\n");
				filew.write("\t\t\t} catch (IllegalArgumentException e) {\n");
				filew.write("\t\t\t\te.printStackTrace();\n");
				filew.write("\t\t\t} catch (InvocationTargetException e) {\n");
				filew.write("\t\t\t\te.printStackTrace();\n\t\t\t}\n\n");

				filew.write("\t\t} catch (NoSuchMethodException e) {\n");
				filew.write("\t\t\te.printStackTrace();\n");
				filew.write("\t\t\tSystem.exit(1);\n");
				filew.write("\t\t} catch (SecurityException e) {\n");
				filew.write("\t\t\te.printStackTrace();\n\t\t}\n\n");

				filew.write("\t\treturn true;\n\t}\n");

			}

			if (_date) {

				// category='BMRBx:entry'
				if (class_name.equalsIgnoreCase("Entry")) {

					// item='original_release_date'
					filew.write("\n\tprivate static boolean set_original_release_date(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, java.sql.Date date, Connection conn_bmrb, String entry_id, java.sql.Date accession_date, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && date == null) {\n\n");

					filew.write("\t\t\tdate = " + file_prefix + "_" + BMSxTool_DOM.util_entry + ".getOriginalReleaseDate(date, conn_bmrb, entry_id);\n\n");

					filew.write("\t\t\tif (date == null)\n");
					filew.write("\t\t\t\tdate = " + file_prefix + "_" + BMSxTool_DOM.util_entry + ".getFirstReleaseDate(date, conn_bmrb, entry_id);\n\n");

					filew.write("\t\t\tif (date == null)\n");
					filew.write("\t\t\t\tdate = accession_date;\n\n");

					filew.write("\t\t}\n\n");

					filew.write("\t\tif (date == null)\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_date_log_code(filew);

					filew.write("\t\tClass<?> _class = list.getClass();\n\n");

					filew.write("\t\ttry {\n");
					filew.write("\t\t\tMethod method = _class.getMethod(method_name, new Class[]{ Calendar.class });\n");
					filew.write("\t\t\t" + (class_name.equalsIgnoreCase("Entry") ? "" : ";//") + "Method nil_method = null;\n\n");

					filew.write("\t\t\t" + (class_name.equalsIgnoreCase("Entry") ? "" : ";//") + "if (nil_method_name != null && !nil_method_name.isEmpty())\n");
					filew.write("\t\t\t\t" + (class_name.equalsIgnoreCase("Entry") ? "" : ";//") + "nil_method = _class.getMethod(nil_method_name);\n\n");

					filew.write("\t\t\ttry {\n");
					filew.write("\t\t\t\tif (nil)\n");
					filew.write("\t\t\t\t\t" + (class_name.equalsIgnoreCase("Entry") ? "" : ";//") + "nil_method.invoke(list);\n");
					filew.write("\t\t\t\telse {\n");
					filew.write("\t\t\t\t\tCalendar cal = " + file_prefix + "_" + BMSxTool_DOM.util_date + ".sqldate2calendar(date);\n");
					filew.write("\t\t\t\t\tif (cal == null)\n");
					filew.write("\t\t\t\t\t\treturn false;\n");
					filew.write("\t\t\t\t\tmethod.invoke(list, cal);\n");
					filew.write("\t\t\t\t}\n");
					filew.write("\t\t\t} catch (IllegalAccessException e) {\n");
					filew.write("\t\t\t\te.printStackTrace();\n");
					filew.write("\t\t\t} catch (IllegalArgumentException e) {\n");
					filew.write("\t\t\t\te.printStackTrace();\n");
					filew.write("\t\t\t} catch (InvocationTargetException e) {\n");
					filew.write("\t\t\t\te.printStackTrace();\n\t\t\t}\n\n");

					filew.write("\t\t} catch (NoSuchMethodException e) {\n");
					filew.write("\t\t\te.printStackTrace();\n");
					filew.write("\t\t\tSystem.exit(1);\n");
					filew.write("\t\t} catch (SecurityException e) {\n");
					filew.write("\t\t\te.printStackTrace();\n\t\t}\n\n");

					filew.write("\t\treturn true;\n\t}\n");

					// item='last_release_date'
					filew.write("\n\tprivate static boolean set_last_release_date(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, java.sql.Date date, Connection conn_bmrb, String entry_id, java.sql.Date accession_date, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && date == null) {\n\n");

					filew.write("\t\t\tdate = " + file_prefix + "_" + BMSxTool_DOM.util_entry + ".getLastReleaseDate(date, conn_bmrb, entry_id);\n\n");

					filew.write("\t\t\tif (date == null)\n");
					filew.write("\t\t\t\tdate = accession_date;\n\n");

					filew.write("\t\t}\n\n");

					filew.write("\t\tif (date == null)\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_date_log_code(filew);

					filew.write("\t\tClass<?> _class = list.getClass();\n\n");

					filew.write("\t\ttry {\n");
					filew.write("\t\t\tMethod method = _class.getMethod(method_name, new Class[]{ Calendar.class });\n");
					filew.write("\t\t\t" + (class_name.equalsIgnoreCase("Entry") ? "" : ";//") + "Method nil_method = null;\n\n");

					filew.write("\t\t\t" + (class_name.equalsIgnoreCase("Entry") ? "" : ";//") + "if (nil_method_name != null && !nil_method_name.isEmpty())\n");
					filew.write("\t\t\t\t" + (class_name.equalsIgnoreCase("Entry") ? "" : ";//") + "nil_method = _class.getMethod(nil_method_name);\n\n");

					filew.write("\t\t\ttry {\n");
					filew.write("\t\t\t\tif (nil)\n");
					filew.write("\t\t\t\t\t" + (class_name.equalsIgnoreCase("Entry") ? "" : ";//") + "nil_method.invoke(list);\n");
					filew.write("\t\t\t\telse {\n");
					filew.write("\t\t\t\t\tCalendar cal = " + file_prefix + "_" + BMSxTool_DOM.util_date + ".sqldate2calendar(date);\n");
					filew.write("\t\t\t\t\tif (cal == null)\n");
					filew.write("\t\t\t\t\t\treturn false;\n");
					filew.write("\t\t\t\t\tmethod.invoke(list, cal);\n");
					filew.write("\t\t\t\t}\n");
					filew.write("\t\t\t} catch (IllegalAccessException e) {\n");
					filew.write("\t\t\t\te.printStackTrace();\n");
					filew.write("\t\t\t} catch (IllegalArgumentException e) {\n");
					filew.write("\t\t\t\te.printStackTrace();\n");
					filew.write("\t\t\t} catch (InvocationTargetException e) {\n");
					filew.write("\t\t\t\te.printStackTrace();\n\t\t\t}\n\n");

					filew.write("\t\t} catch (NoSuchMethodException e) {\n");
					filew.write("\t\t\te.printStackTrace();\n");
					filew.write("\t\t\tSystem.exit(1);\n");
					filew.write("\t\t} catch (SecurityException e) {\n");
					filew.write("\t\t\te.printStackTrace();\n\t\t}\n\n");

					filew.write("\t\treturn true;\n\t}\n");

					// item='accession_date', 'submission_date'
					filew.write("\n\tprivate static boolean set_date_entry(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, java.sql.Date date, Connection conn_bmrb, String entry_id, FileWriter logw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && date == null) {\n\n");

					filew.write("\t\t\tdate = " + file_prefix + "_" + BMSxTool_DOM.util_entry + ".getFirstReleaseDate(date, conn_bmrb, entry_id);\n\n");

					filew.write("\t\t\tif (date == null)\n");
					filew.write("\t\t\t\tdate = java.sql.Date.valueOf(\"1970-01-01\");\n");

					filew.write("\t\t}\n\n");

					filew.write("\t\tif (date == null)\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_date_log_code(filew);

					filew.write("\t\tClass<?> _class = list.getClass();\n\n");

					filew.write("\t\ttry {\n");
					filew.write("\t\t\tMethod method = _class.getMethod(method_name, new Class[]{ Calendar.class });\n");
					filew.write("\t\t\t" + (class_name.equalsIgnoreCase("Entry") ? "" : ";//") + "Method nil_method = null;\n\n");

					filew.write("\t\t\t" + (class_name.equalsIgnoreCase("Entry") ? "" : ";//") + "if (nil_method_name != null && !nil_method_name.isEmpty())\n");
					filew.write("\t\t\t\t" + (class_name.equalsIgnoreCase("Entry") ? "" : ";//") + "nil_method = _class.getMethod(nil_method_name);\n\n");

					filew.write("\t\t\ttry {\n");
					filew.write("\t\t\t\tif (nil)\n");
					filew.write("\t\t\t\t\t" + (class_name.equalsIgnoreCase("Entry") ? "" : ";//") + "nil_method.invoke(list);\n");
					filew.write("\t\t\t\telse {\n");
					filew.write("\t\t\t\t\tCalendar cal = " + file_prefix + "_" + BMSxTool_DOM.util_date + ".sqldate2calendar(date);\n");
					filew.write("\t\t\t\t\tif (cal == null)\n");
					filew.write("\t\t\t\t\t\treturn false;\n");
					filew.write("\t\t\t\t\tmethod.invoke(list, cal);\n");
					filew.write("\t\t\t\t}\n");
					filew.write("\t\t\t} catch (IllegalAccessException e) {\n");
					filew.write("\t\t\t\te.printStackTrace();\n");
					filew.write("\t\t\t} catch (IllegalArgumentException e) {\n");
					filew.write("\t\t\t\te.printStackTrace();\n");
					filew.write("\t\t\t} catch (InvocationTargetException e) {\n");
					filew.write("\t\t\t\te.printStackTrace();\n\t\t\t}\n\n");

					filew.write("\t\t} catch (NoSuchMethodException e) {\n");
					filew.write("\t\t\te.printStackTrace();\n");
					filew.write("\t\t\tSystem.exit(1);\n");
					filew.write("\t\t} catch (SecurityException e) {\n");
					filew.write("\t\t\te.printStackTrace();\n\t\t}\n\n");

					filew.write("\t\treturn true;\n\t}\n");


				}

				//				else {

				filew.write("\n\tprivate static boolean set_date(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, java.sql.Date date, FileWriter logw) {\n\n");

				filew.write("\t\tboolean nil = false;\n\n");

				filew.write("\t\tif (date == null)\n");
				filew.write("\t\t\tnil = true;\n\n");

				write_missing_date_log_code(filew);

				filew.write("\t\tClass<?> _class = list.getClass();\n\n");

				filew.write("\t\ttry {\n");
				filew.write("\t\t\tMethod method = _class.getMethod(method_name, new Class[]{ Calendar.class });\n");
				filew.write("\t\t\t" + (class_name.equalsIgnoreCase("Entry") ? "" : ";//") + "Method nil_method = null;\n\n");

				filew.write("\t\t\t" + (class_name.equalsIgnoreCase("Entry") ? "" : ";//") + "if (nil_method_name != null && !nil_method_name.isEmpty())\n");
				filew.write("\t\t\t\t" + (class_name.equalsIgnoreCase("Entry") ? "" : ";//") + "nil_method = _class.getMethod(nil_method_name);\n\n");

				filew.write("\t\t\ttry {\n");
				filew.write("\t\t\t\tif (nil)\n");
				filew.write("\t\t\t\t\t" + (class_name.equalsIgnoreCase("Entry") ? "" : ";//") + "nil_method.invoke(list);\n");
				filew.write("\t\t\t\telse {\n");
				filew.write("\t\t\t\t\tCalendar cal = " + file_prefix + "_" + BMSxTool_DOM.util_date + ".sqldate2calendar(date);\n");
				filew.write("\t\t\t\t\tif (cal == null)\n");
				filew.write("\t\t\t\t\t\treturn false;\n");
				filew.write("\t\t\t\t\tmethod.invoke(list, cal);\n");
				filew.write("\t\t\t\t}\n");
				filew.write("\t\t\t} catch (IllegalAccessException e) {\n");
				filew.write("\t\t\t\te.printStackTrace();\n");
				filew.write("\t\t\t} catch (IllegalArgumentException e) {\n");
				filew.write("\t\t\t\te.printStackTrace();\n");
				filew.write("\t\t\t} catch (InvocationTargetException e) {\n");
				filew.write("\t\t\t\te.printStackTrace();\n\t\t\t}\n\n");

				filew.write("\t\t} catch (NoSuchMethodException e) {\n");
				filew.write("\t\t\te.printStackTrace();\n");
				filew.write("\t\t\tSystem.exit(1);\n");
				filew.write("\t\t} catch (SecurityException e) {\n");
				filew.write("\t\t\te.printStackTrace();\n\t\t}\n\n");

				filew.write("\t\treturn true;\n\t}\n");

				//				}

			}

			// xsd:restrictes cases

			if (_enum)
				extract_enum_nodes(filew, node, table_name, column_list, file_prefix);

			filew.write("}\n");

			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return 0;
	}

	private static boolean has_integer(Node node) {

		for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {

			if (child.getNodeType() == Node.ELEMENT_NODE && (child.getNodeName().equals("xsd:element") || child.getNodeName().equals("xsd:attribute"))) {
				//				BMRxTool_DOM.getNodeInfo(child);

				NamedNodeMap node_map = child.getAttributes();

				if (node_map != null) {

					Node attr_name = node_map.getNamedItem("type");

					if (attr_name != null && attr_name.getTextContent().equals("xsd:integer"))
						return true;
				}
			}

			if (has_integer(child))
				return true;
		}

		return false;
	}

	private static boolean has_integer_id(Node node) {

		for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {

			if (child.getNodeType() == Node.ELEMENT_NODE && (child.getNodeName().equals("xsd:element") || child.getNodeName().equals("xsd:attribute"))) {
				//				BMRxTool_DOM.getNodeInfo(child);

				NamedNodeMap node_map = child.getAttributes();

				if (node_map != null) {

					Node attr_name = node_map.getNamedItem("name");

					if (attr_name != null && attr_name.getTextContent().equals("id")) {

						attr_name = node_map.getNamedItem("type");

						if (attr_name != null && attr_name.getTextContent().equals("xsd:integer"))
							return true;
					}
				}
			}

			if (has_integer_id(child))
				return true;
		}

		return false;
	}

	private static boolean has_decimal(Node node) {

		for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {

			if (child.getNodeType() == Node.ELEMENT_NODE && (child.getNodeName().equals("xsd:element") || child.getNodeName().equals("xsd:attribute"))) {
				//				BMRxTool_DOM.getNodeInfo(child);

				NamedNodeMap node_map = child.getAttributes();

				if (node_map != null) {

					Node attr_name = node_map.getNamedItem("type");

					if (attr_name != null && attr_name.getTextContent().equals("xsd:decimal"))
						return true;
				}
			}

			if (has_decimal(child))
				return true;
		}

		return false;
	}

	private static boolean has_double(Node node) {

		for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {

			if (child.getNodeType() == Node.ELEMENT_NODE && (child.getNodeName().equals("xsd:element") || child.getNodeName().equals("xsd:attribute"))) {
				//				BMRxTool_DOM.getNodeInfo(child);

				NamedNodeMap node_map = child.getAttributes();

				if (node_map != null) {

					Node attr_name = node_map.getNamedItem("type");

					if (attr_name != null && attr_name.getTextContent().equals("xsd:double"))
						return true;
				}
			}

			if (has_double(child))
				return true;
		}

		return false;
	}

	private static boolean has_date(Node node) {

		for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {

			if (child.getNodeType() == Node.ELEMENT_NODE && (child.getNodeName().equals("xsd:element") || child.getNodeName().equals("xsd:attribute"))) {
				//				BMRxTool_DOM.getNodeInfo(child);

				NamedNodeMap node_map = child.getAttributes();

				if (node_map != null) {

					Node attr_name = node_map.getNamedItem("type");

					if (attr_name != null && attr_name.getTextContent().equals("xsd:date"))
						return true;
				}
			}

			if (has_date(child))
				return true;
		}

		return false;
	}

	private static boolean has_enum(Node node) {

		for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {

			if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals("xsd:restriction")) {
				//				BMRxTool_DOM.getNodeInfo(child);

				return true;
			}

			if (has_enum(child))
				return true;
		}

		return false;
	}

	private static boolean has_enum_string(Node node) {

		for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {

			if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals("xsd:restriction")) {
				//				BMRxTool_DOM.getNodeInfo(child);

				NamedNodeMap node_map = child.getAttributes();

				if (node_map != null) {

					Node attr_name = node_map.getNamedItem("base");

					if (attr_name != null && attr_name.getTextContent().equals("xsd:string"))
						return true;
				}
			}

			if (has_enum_string(child))
				return true;
		}

		return false;
	}

	private static boolean has_enum_integer(Node node) {

		for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {

			if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals("xsd:restriction")) {
				//				BMRxTool_DOM.getNodeInfo(child);

				NamedNodeMap node_map = child.getAttributes();

				if (node_map != null) {

					Node attr_name = node_map.getNamedItem("base");

					if (attr_name != null && attr_name.getTextContent().equals("xsd:integer"))
						return true;
				}
			}

			if (has_enum_integer(child))
				return true;
		}

		return false;
	}

	private static boolean has_enum_decimal(Node node) {

		for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {

			if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals("xsd:restriction")) {
				//				BMRxTool_DOM.getNodeInfo(child);

				NamedNodeMap node_map = child.getAttributes();

				if (node_map != null) {

					Node attr_name = node_map.getNamedItem("base");

					if (attr_name != null && attr_name.getTextContent().equals("xsd:decimal"))
						return true;
				}
			}

			if (has_enum_decimal(child))
				return true;
		}

		return false;
	}

	private static String convert_attr_name(String attr_name, List<String> column_list) {

		String attr_name_lower = attr_name.toLowerCase();

		if (class_name.equalsIgnoreCase("AuxiliaryFiles") && attr_name_lower.equals("path")) {
			for (String column_name : column_list) {
				if ("bmrb_path_name".equalsIgnoreCase(column_name))
					return column_name;
			}
		}	

		if (class_name.equalsIgnoreCase("Bond") && attr_name_lower.equals("value_order")) {
			for (String column_name : column_list) {
				if ("order".equalsIgnoreCase(column_name))
					return column_name;
			}
		}

		if (class_name.equalsIgnoreCase("BondAnnotation") && attr_name_lower.equals("type")) {
			for (String column_name : column_list) {
				if ("Bond_type".equalsIgnoreCase(column_name))
					return column_name;
			}
		}

		if (class_name.equalsIgnoreCase("BondAnnotation") && attr_name_lower.equals("value_order")) {
			for (String column_name : column_list) {
				if ("Bond_order".equalsIgnoreCase(column_name))
					return column_name;
			}
		}

		if (class_name.equals("EntityDbLink") && attr_name_lower.equals("chimera_segment_id")) {
			for (String column_name : column_list) {
				if ("Chimera_segment".equalsIgnoreCase(column_name))
					return column_name;
			}
		}

		if (class_name.equalsIgnoreCase("AtomType") && attr_name_lower.equals("van_der_waals_radii")) {
			for (String column_name : column_list) {
				if ("van_der_vaals_radii".equalsIgnoreCase(column_name))
					return column_name;
			}
		}

		if ((class_name.equalsIgnoreCase("Experiment") || class_name.equalsIgnoreCase("SaxsExpt")) && attr_name_lower.equals("x_ray_instrument_id")) {
			for (String column_name : column_list) {
				if ("xray_instrument_id".equalsIgnoreCase(column_name))
					return column_name;
			}
		}

		if ((class_name.equalsIgnoreCase("Experiment") || class_name.equalsIgnoreCase("SaxsExpt")) && attr_name_lower.equals("x_ray_instrument_label")) {
			for (String column_name : column_list) {
				if ("xray_instrument_label".equalsIgnoreCase(column_name))
					return column_name;
			}
		}

		if ((class_name.equalsIgnoreCase("GenDistConstraint") || class_name.equalsIgnoreCase("RDCConstraint") || class_name.equalsIgnoreCase("TorsionAngleConstraint")) && attr_name_lower.equals("index_id")) {
			for (String column_name : column_list) {
				if ("id".equalsIgnoreCase(column_name))
					return column_name;
			}
		}

		for (String column_name : column_list) {
			if (attr_name.equalsIgnoreCase(column_name))
				return column_name;
		}

		return attr_name;
	}

	private static void extract_key_attr_nodes(Node node, List<String> column_list, List<String> key_attrs, List<String> rep_attrs, List<String> list_attrs) {

		for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {

			if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals("xsd:attribute")) {

				NamedNodeMap node_map = child.getAttributes();

				if (node_map != null) {

					Node attr_name = node_map.getNamedItem("name");
					Node attr_type = node_map.getNamedItem("type");
					Node attr_use = node_map.getNamedItem("use");

					if (attr_name != null && attr_type != null && attr_type.getTextContent().equals("xsd:integer") && attr_use != null && attr_use.getTextContent().equals("required")) {

						String column_name = convert_attr_name(attr_name.getTextContent(), column_list);

						if (column_name != null) {

							String column_name_lower = column_name.toLowerCase();

							if (column_name_lower.endsWith("_list_id"))
								list_attrs.add(column_name);

							else if (column_name_lower.equals("id") || column_name_lower.equals("index_id") || column_name_lower.equals("orginal"))
								key_attrs.add(column_name);

							else {
								String class_name_lower = class_name.toLowerCase();
								String[] terms = column_name_lower.replaceFirst("_id$", "").split("_");

								boolean contain_class_name = false;

								for (String term : terms) {
									if (class_name_lower.contains(term)) {
										contain_class_name = true;
										break;
									}
								}

								if (contain_class_name && column_name_lower.endsWith("_id"))
									rep_attrs.add(column_name);
							}
						}
					}
				}
			}

			extract_key_attr_nodes(child, column_list, key_attrs, rep_attrs, list_attrs);
		}
	}

	private static void extract_element_nodes(FileWriter filew, Node node, String table_name, List<String> column_list, String file_prefix) {

		for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {

			if (child.getNodeType() == Node.ELEMENT_NODE && (child.getNodeName().equals("xsd:element") || child.getNodeName().equals("xsd:attribute"))) {
				//				BMRxTool_DOM.getNodeInfo(child);

				NamedNodeMap node_map = child.getAttributes();

				if (node_map != null) {

					Node attr_max_occurs = node_map.getNamedItem("maxOccurs");
					Node attr_min_occurs = node_map.getNamedItem("minOccurs");
					Node attr_name = node_map.getNamedItem("name");
					Node attr_type = node_map.getNamedItem("type");
					Node attr_nillable = node_map.getNamedItem("nillable");
					Node attr_use = node_map.getNamedItem("use");

					if (attr_name != null) {

						if (!attr_name.getTextContent().equalsIgnoreCase(table_name) || (attr_name.getTextContent().equalsIgnoreCase(table_name) && (attr_max_occurs == null || attr_max_occurs.getTextContent().isEmpty()))) {

							boolean required = ((child.getNodeName().equals("xsd:element") && attr_min_occurs != null && attr_min_occurs.getTextContent().equals("1")) ||
									(child.getNodeName().equals("xsd:attribute") && attr_use != null && attr_use.getTextContent().equals("required")));

							link_node_and_db(filew, child, (attr_type != null ? attr_type.getTextContent() : null), attr_name.getTextContent(), (attr_nillable != null ? attr_nillable.getTextContent() : null), required, column_list, file_prefix);
						}
					}
				}
			}

			extract_element_nodes(filew, child, table_name, column_list, file_prefix);
		}
	}

	private static void link_node_and_db(FileWriter filew, Node node, String attr_type, String attr_name, String attr_nillable, boolean required, List<String> column_list, String file_prefix) {

		boolean _has_enum = has_enum(node);

		if (attr_type == null && !_has_enum) {

			System.err.println("class: " + class_name + ", attr: " + attr_name + " has no any xsd:type.");

			return;
		}

		String attr_name_lower = attr_name.toLowerCase();

		String attr_name_ = convert_attr_name(attr_name, column_list);

		char[] _attr_name = attr_name_lower.toCharArray();
		char[] _Attr_name = attr_name.toCharArray();

		boolean under_score = true;
		boolean numeric_code = false;

		for (int i = 0; i < attr_name.length(); i++) {
			if (under_score || numeric_code || (_Attr_name[i] >= 'A' && _Attr_name[i] <= 'Z'))
				_attr_name[i] = Character.toUpperCase(_attr_name[i]);
			under_score = (_attr_name[i] == '_');
			numeric_code = (_attr_name[i] >= '0' && _attr_name[i] <= '9');
		}

		String method_name = "set" + String.valueOf(_attr_name).replace("_", "");
		String nil_method_name = (attr_nillable != null && attr_nillable.equals("true") ? "setNil" + String.valueOf(_attr_name).replace("_", "") : "");

		if (attr_type == null && !has_enum_string(node) && !has_enum_integer(node) && !has_enum_decimal(node) && !has_double(node)) {

			System.err.println("class: " + class_name + ", attr: " + attr_name + " has unsupported xsd:type.");

			return;
		}

		for (String column_name : column_list) {

			if (attr_name_.equalsIgnoreCase(column_name)) {

				try {

					if (_has_enum) {

						if (method_name.equalsIgnoreCase("setClass")) { // XMLBeans try to prevent name space collision automatically for "setClass" method.
							method_name = "setClass1";
							if (!nil_method_name.isEmpty())
								nil_method_name = "setNilClass1";
						}

						// item='database_code', category='BMRBx:assembly_db_link'
						if (attr_name_lower.equals("database_code") && class_name.equalsIgnoreCase("AssemblyDbLink")) {
							filew.write("\t\t\t\t\tif (!set_enum_" + attr_name_lower +"(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), entry_id, " + file_prefix + "_" + BMSxTool_DOM.util_assemblydblink + ".checkAccessionCode(rset.getString(\"Accession_code\"), entry_id), logw, errw)) {\n");
							write_excld_log_code(filew, method_name, column_name);
							filew.write("\t\t\t\t\t\tcontinue;\n\t\t\t\t\t}\n");
						}
						// item='database_code', category='BMRBx:entity_db_link'
						else if (attr_name_lower.equals("database_code") && class_name.equalsIgnoreCase("EntityDbLink")) {
							filew.write("\t\t\t\t\tif (!set_enum_" + attr_name_lower +"(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), entry_id, " + file_prefix + "_" + BMSxTool_DOM.util_entitydblink + ".checkAccessionCode(rset.getString(\"Accession_code\"), entry_id), logw, errw)) {\n");
							write_excld_log_code(filew, method_name, column_name);
							filew.write("\t\t\t\t\t\tcontinue;\n\t\t\t\t\t}\n");
						}
						// item='database_name', category='BMRBx:releated_entries'
						else if (attr_name_lower.equals("database_name") && (class_name.equalsIgnoreCase("RelatedEntries")))
							filew.write("\t\t\t\t\tif (!set_enum_" + attr_name_lower +"(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_bmrb, entry_id, " + file_prefix + "_" + BMSxTool_DOM.util_relatedentries + ".checkDatabaseAccessionCode(rset.getString(\"Database_accession_code\"), entry_id), logw, errw))\n\t\t\t\t\t\tcontinue;\n");

						// item='type', category='BMRBx:assembly_type'
						else if (attr_name_lower.equals("type") && class_name.equalsIgnoreCase("AssemblyType"))
							filew.write("\t\t\t\t\tif (!set_enum_" + attr_name_lower +"(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_bmrb, entry_id, rset.getString(\"Assembly_ID\"), logw, errw))\n\t\t\t\t\t\tcontinue;\n");
						// item='type', category='BMRBx:sample'
						else if (attr_name_lower.equals("type") && class_name.equalsIgnoreCase("Sample"))
							filew.write("\t\t\t\t\tif (!set_enum_" + attr_name_lower +"(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_bmrb, entry_id, logw, errw))\n\t\t\t\t\t\tcontinue;\n");
						// item='ambiguity_code', category='BMRBx:atom_chem_shift'
						// item='value_order', category='BMRBx:bond'
						// item='experimental_method_subtype', category='BMRBx:entry'
						// item='t1_coherence_type', category='BMRBx:hetoronucl_t1_list'
						// item='t2_coherence_type', category='BMRBx:hetoronucl_t2_list'
						else if ((attr_name_lower.equals("ambiguity_code") && class_name.equalsIgnoreCase("AtomChemShift")) || (attr_name_lower.equals("value_order") && class_name.equalsIgnoreCase("Bond")) || (attr_name_lower.equals("experimental_method_subtype") && class_name.equalsIgnoreCase("Entry")) || (attr_name_lower.equals("t1_coherence_type") && class_name.equalsIgnoreCase("HeteronuclT1List")) || (attr_name_lower.equals("t2_coherence_type") && class_name.equalsIgnoreCase("HeteronuclT2List")))
							filew.write("\t\t\t\t\tif (!set_enum_" + attr_name_lower +"(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), entry_id, logw, errw))\n\t\t\t\t\t\tcontinue;\n");
						// item='constraint_type', category='BMRBx:constraint_file'
						else if (attr_name_lower.equals("constraint_type") && class_name.equalsIgnoreCase("ConstraintFile"))
							filew.write("\t\t\t\t\tif (!set_enum_" + attr_name_lower +"(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), rset.getString(\"Constraint_filename\"), logw, errw))\n\t\t\t\t\t\tcontinue;\n");

						// item='superkingdom', 'kingdom', category='BMRBx:entity_natural_src'
						else if ((attr_name_lower.equals("superkingdom") || attr_name_lower.equals("kingdom")) && class_name.equalsIgnoreCase("EntityNaturalSrc"))
							filew.write("\t\t\t\t\tif (!set_enum_" + attr_name_lower +"(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_tax, entry_id, ncbi_taxonomy_id, logw, errw))\n\t\t\t\t\t\tcontinue;\n");
						// item='host_org_scientific_name', category='BMRBx:entity_experimental_src'
						else if (attr_name_lower.equals("host_org_scientific_name") && class_name.equalsIgnoreCase("EntityExperimentalSrc"))
							filew.write("\t\t\t\t\tif (!set_enum_" + attr_name_lower +"(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_tax, entry_id, ncbi_taxonomy_id, logw, errw))\n\t\t\t\t\t\tcontinue;\n");
						// item='production_method', category='BMRBx:entity_experimental_src'
						else if (attr_name_lower.equals("production_method") && class_name.equalsIgnoreCase("EntityExperimentalSrc"))
							filew.write("\t\t\t\t\tif (!set_enum_" + attr_name_lower +"(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), ee, logw, errw))\n\t\t\t\t\t\tcontinue;\n");
						// item='common', 'organ', 'organelle', 'secretion', 'type', category='BMRBx:entity_natural_src'
						else if ((attr_name_lower.equals("common") || attr_name_lower.equals("organ") || attr_name_lower.equals("organelle") || attr_name_lower.equals("secretion") || attr_name_lower.equals("type")) && class_name.equalsIgnoreCase("EntityNaturalSrc"))
							filew.write("\t\t\t\t\tif (!set_enum_" + attr_name_lower +"(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), en, logw, errw))\n\t\t\t\t\t\tcontinue;\n");

						// item='naming_system', category='BMRBx:assembly_systematic_name', 'BMRBx:entity_systematic_name'
						// item='type', category='BMRBx:systematic_chem_shift_offset'
						else if ((attr_name_lower.equals("naming_system") && (class_name.equalsIgnoreCase("AssemblySystematicName") || class_name.equalsIgnoreCase("EntitySystematicName"))) || (attr_name_lower.equals("type") && class_name.equalsIgnoreCase("SystematicChemShiftOffset"))) {
							filew.write("\t\t\t\t\tif (!set_enum_" + attr_name_lower +"(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), logw, errw)) {\n");
							write_excld_log_code(filew, method_name, column_name);
							filew.write("\t\t\t\t\t\tcontinue;\n\t\t\t\t\t}\n");
						}
						// item='stereo_config', category='BMRBx:chem_comp_atom'
						else if (attr_name_lower.equals("stereo_config") && class_name.equalsIgnoreCase("ChemCompAtom"))
							filew.write("\t\t\t\t\tif (!set_enum_" + attr_name_lower +"(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), entry_id, rset.getString(\"Drawing_2D_coord_x\"), logw, errw))\n\t\t\t\t\t\tcontinue;\n");
						else
							filew.write("\t\t\t\t\tif (!set_enum_" + attr_name_lower +"(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), logw, errw))\n\t\t\t\t\t\tcontinue;\n");

					}

					else if (attr_type.equals("xsd:string")) {

						if (method_name.equalsIgnoreCase("setClass")) { // XMLBeans try to prevent name space collision automatically for "setClass" method.
							method_name = "setClass1";
							if (!nil_method_name.isEmpty())
								nil_method_name = "setNilClass1";
						}

						// item='atom_id', category='BMRBx:atom_nomenclature'
						if (attr_name_lower.equals("atom_id") && class_name.equalsIgnoreCase("AtomNomenclature"))
							filew.write("\t\t\t\t\tif (!set_string_atom_id(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), i++, logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='atom_id_2', category='BMRBx:bond'
						if (attr_name_lower.equals("atom_id_2") && class_name.equalsIgnoreCase("Bond"))
							filew.write("\t\t\t\t\tif (!set_string_atom_id_2(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), rset.getString(\"Comp_id_1\"), rset.getString(\"Atom_id_1\"), rset.getString(\"Comp_id_2\"), logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='id', category='BMRBx:chem_comp'
						if (attr_name_lower.equals("id") && class_name.equalsIgnoreCase("ChemComp"))
							filew.write("\t\t\t\t\tif (!set_string_id(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), rset.getString(\"Name\"), logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='atom_type', category='BMRBx:chem_shift_ref'
						else if (attr_name_lower.equals("atom_type") && class_name.equalsIgnoreCase("ChemShiftRef")) {
							filew.write("\t\t\t\t\tif (!set_string(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", chem_shift_ref.atom_type, logw)) {\n");
							write_excld_log_code(filew, method_name, column_name);
							filew.write("\t\t\t\t\t\tcontinue;\n\t\t\t\t\t}\n");
						}

						// item='bmrb_code', category='BMRBx:chem_comp'
						else if (attr_name_lower.equals("bmrb_code") && class_name.equalsIgnoreCase("ChemComp"))
							filew.write("\t\t\t\t\tif (!set_string(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", bmrb_code, logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='pdb_code', category='BMRBx:chem_comp'
						else if (attr_name_lower.equals("pdb_code") && class_name.equalsIgnoreCase("ChemComp"))
							filew.write("\t\t\t\t\tif (!set_string(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", pdb_code, logw))\n\t\t\t\t\t\tcontinue;\n");

						// item='pubmed_id', category='BMRBx:citation'
						else if (attr_name_lower.equals("pubmed_id") && class_name.equalsIgnoreCase("Citation"))
							filew.write("\t\t\t\t\tif (!set_string(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", pubmed_id, logw))\n\t\t\t\t\t\tcontinue;\n");

						// item='doi', category='BMRBx:citation'
						else if (attr_name_lower.equals("doi") && class_name.equalsIgnoreCase("Citation"))
							filew.write("\t\t\t\t\tif (!set_string(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", doi, logw))\n\t\t\t\t\t\tcontinue;\n");

						// item='enzyme_commission_number', category='BMRBx:assembly'
						// item='ec_number', category='BMRBx:entity'
						else if ((attr_name_lower.equals("enzyme_commission_number") && class_name.equalsIgnoreCase("Assembly")) || (attr_name_lower.equals("ec_number") && class_name.equalsIgnoreCase("Entity")))
							filew.write("\t\t\t\t\tif (!set_string_ec_number(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), entry_id, logw))\n\t\t\t\t\t\tcontinue;\n");

						// item='accession_code', category='BMRBx:assembly_db_link'
						else if (attr_name_lower.equals("accession_code") && class_name.equalsIgnoreCase("AssemblyDbLink"))
							filew.write("\t\t\t\t\tif (!set_string_accession_code(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", " + file_prefix + "_" + BMSxTool_DOM.util_assemblydblink + ".checkAccessionCode(rset.getString(\"" + column_name + "\"), entry_id), entry_id, logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='accession_code', category='BMRBx:entity_db_link'
						else if (attr_name_lower.equals("accession_code") && class_name.equalsIgnoreCase("EntityDbLink"))
							filew.write("\t\t\t\t\tif (!set_string_accession_code(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", " + file_prefix + "_" + BMSxTool_DOM.util_entitydblink + ".checkAccessionCode(rset.getString(\"" + column_name + "\"), entry_id), entry_id, logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='database_accession_code', category='BMRBx:related_entries'
						else if (attr_name_lower.equals("database_accession_code") && class_name.equalsIgnoreCase("RelatedEntries")) {
							filew.write("\t\t\t\t\tif (!set_string_database_accession_code(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", " + file_prefix + "_" + BMSxTool_DOM.util_relatedentries + ".checkDatabaseAccessionCode(rset.getString(\"" + column_name + "\"), entry_id), entry_id, logw)) {\n");
							write_excld_log_code(filew, method_name, column_name);
							filew.write("\t\t\t\t\t\tcontinue;\n\t\t\t\t\t}\n");
						}

						// item='polymer_seq_one_letter_code', 'polymer_seq_one_letter_code_can', category='BMRBx:entity'
						else if ((attr_name_lower.equals("polymer_seq_one_letter_code") || attr_name_lower.equals("polymer_seq_one_letter_code_can")) && class_name.equalsIgnoreCase("Entity"))
							filew.write("\t\t\t\t\tif (!set_string_" + attr_name_lower +"(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_bmrb, entry_id, rset.getString(\"ID\"), logw))\n\t\t\t\t\t\tcontinue;\n");

						// item='ncbi_taxonomy_id', category='BMRBx:entity_natural_src'
						else if (attr_name_lower.equals("ncbi_taxonomy_id") && class_name.equalsIgnoreCase("EntityNaturalSrc"))
							filew.write("\t\t\t\t\tif (!set_string_ncbi_taxonomy_id(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", ncbi_taxonomy_id, conn_tax, logw, errw))\n\t\t\t\t\t\tcontinue;\n");
						// item='host_org_ncbi_taxonomy_id', category='BMRBx:entity_experimental_src'
						else if (attr_name_lower.equals("host_org_ncbi_taxonomy_id") && class_name.equalsIgnoreCase("EntityExperimentalSrc"))
							filew.write("\t\t\t\t\tif (!set_string_ncbi_taxonomy_id(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", ncbi_taxonomy_id, conn_tax, logw, errw))\n\t\t\t\t\t\tcontinue;\n");
						// item='genus', category='BMRBx:entity_natural_src'
						else if (attr_name_lower.equals("genus") && class_name.equalsIgnoreCase("EntityNaturalSrc"))
							filew.write("\t\t\t\t\tif (!set_string_genus(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_tax, ncbi_taxonomy_id, logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='species', category='BMRBx:entity_natural_src'
						else if (attr_name_lower.equals("species") && class_name.equalsIgnoreCase("EntityNaturalSrc"))
							filew.write("\t\t\t\t\tif (!set_string_species(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_tax, ncbi_taxonomy_id, logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='organism_name_common', category='BMRBx:entity_natural_src'
						else if (attr_name_lower.equals("organism_name_common") && class_name.equalsIgnoreCase("EntityNaturalSrc"))
							filew.write("\t\t\t\t\tif (!set_string_organism_name_common(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_tax, ncbi_taxonomy_id, logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='host_org_genus', category='BMRBx:entity_experimental_src'
						else if (attr_name_lower.equals("host_org_genus") && class_name.equalsIgnoreCase("EntityExperimentalSrc"))
							filew.write("\t\t\t\t\tif (!set_string_host_org_genus(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_tax, ncbi_taxonomy_id, logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='host_org_species', category='BMRBx:entity_experimental_src'
						else if (attr_name_lower.equals("host_org_species") && class_name.equalsIgnoreCase("EntityExperimentalSrc"))
							filew.write("\t\t\t\t\tif (!set_string_host_org_species(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_tax, ncbi_taxonomy_id, logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='host_org_name_common', category='BMRBx:entity_experimental_src'
						else if (attr_name_lower.equals("host_org_name_common") && class_name.equalsIgnoreCase("EntityExperimentalSrc"))
							filew.write("\t\t\t\t\tif (!set_string_host_org_name_common(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_tax, ncbi_taxonomy_id, logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='organism_name_scientific', category='BMRBx:entity_natural_src'
						else if ((attr_name_lower.equals("organism_name_scientific")) && class_name.equalsIgnoreCase("EntityNaturalSrc"))
							filew.write("\t\t\t\t\tif (!set_string_" + attr_name_lower +"(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_tax, entry_id, ncbi_taxonomy_id, logw, errw))\n\t\t\t\t\t\tcontinue;\n");

						// item='database_code', category='BMRBx:natural_source_db'
						else if (attr_name_lower.equals("database_code") && class_name.equalsIgnoreCase("NaturalSourceDb"))
							filew.write("\t\t\t\t\tif (!set_string_database_code(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), rset.getString(\"Entry_code\"), logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='atom_id', category='BMRBx:chem_comp_atom'
						// item='name', category='BMRBx:entity_common_name'
						// item='atom_type', category='BMRBx:spectral_dim'
						// item='bmrb_accession_code', category='BMRBx:study_entry_list'
						else if ((attr_name_lower.equals("atom_id") && class_name.equalsIgnoreCase("ChemCompAtom")) || (attr_name_lower.equals("name") && class_name.equalsIgnoreCase("EntityCommonName")) || (attr_name_lower.equals("atom_type") && class_name.equalsIgnoreCase("SpectralDim")) || (attr_name_lower.equals("bmrb_accession_code") && class_name.equalsIgnoreCase("StudyEntryList"))) {
							filew.write("\t\t\t\t\tif (!set_string(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), logw)) {\n");
							write_excld_log_code(filew, method_name, column_name);
							filew.write("\t\t\t\t\t\tcontinue;\n\t\t\t\t\t}\n");
						}
						// item='title', 'journal_name_full', 'journal_abbrev', 'journal_volume', 'journal_issue', 'journal_issn', 'page_first', 'page_last', 'title', 'year', category='BMRBx:citation'
						else if ((attr_name_lower.equals("journal_name_full") || attr_name_lower.equals("journal_abbrev") || attr_name_lower.equals("journal_volume") || attr_name_lower.equals("journal_issue") || attr_name_lower.equals("journal_issn") || attr_name_lower.equals("page_first") || attr_name_lower.equals("page_last") || attr_name_lower.equals("title") || attr_name_lower.equals("year")) && class_name.equalsIgnoreCase("Citation"))
							filew.write("\t\t\t\t\tif (!set_string_by_doc_sum(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), doc_sum, logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='spectral_region', category='BMRBx:spectral_dim'
						else if (attr_name_lower.equals("spectral_region") && class_name.equalsIgnoreCase("SpectralDim"))
							filew.write("\t\t\t\t\tif (!set_string_spectral_region(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), rset.getString(\"Atom_type\"), logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='charge', category='BMRBx:chem_comp_atom'
						else if (attr_name_lower.equals("charge") && class_name.equalsIgnoreCase("ChemCompAtom"))
							filew.write("\t\t\t\t\tif (!set_string_charge(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), entry_id, rset.getString(\"Drawing_2D_coord_y\"), logw))\n\t\t\t\t\t\tcontinue;\n");
						else
							filew.write("\t\t\t\t\tif (!set_string(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), logw))\n\t\t\t\t\t\tcontinue;\n");

					}

					else if (attr_type.equals("xsd:integer")) {

						// item='id'
						if (attr_name_lower.equals("id"))
							filew.write("\t\t\t\t\tif (!set_integer(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rid[lines - 1], logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='id', 'BMRBx:entity_experimental_src', 'BMRBx:entity_purity', 'BMRBx:nmr_spectrometer_view'
						/*
						if (attr_name_lower.equals("id") && (class_name.equalsIgnoreCase("EntityExperimentalSrc") || class_name.equalsIgnoreCase("EntityPurity") || class_name.equalsIgnoreCase("NMRSpectrometerView")))
							filew.write("\t\t\t\t\tif (!set_integer_id(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), i++, logw))\n\t\t\t\t\t\tcontinue;\n");
						 */
						// item='entity_natural_src_id', category='BMRBx:natural_source_db'
						else if (attr_name_lower.equals("entity_natural_src_id") && class_name.equalsIgnoreCase("NaturalSourceDb"))
							filew.write("\t\t\t\t\tif (!set_integer_entity_natural_src_id(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), i++, logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='sg_project_id', category='BMRBx:sg_project'
						else if (attr_name_lower.equals("sg_project_id") && class_name.equalsIgnoreCase("SGProject"))
							filew.write("\t\t\t\t\tif (!set_integer_sg_project_id(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), i++, logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='release_number', category='BMRBx:release'
						else if (attr_name_lower.equals("release_number") && class_name.equalsIgnoreCase("Release"))
							filew.write("\t\t\t\t\tif (!set_integer_release_number(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), i++, logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='software_id', category='BMRBx:*software'
						else if (attr_name_lower.equals("software_id") && class_name.endsWith("Software") && !class_name.startsWith("Software"))
							filew.write("\t\t\t\t\tif (!set_integer_software_id(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), i++, logw))\n\t\t\t\t\t\tcontinue;\n");

						// item='sample_id', category='BMRBx:chem_shift*experiment', 'BMRBx:conformer_family_coord_set*expt', 'BMRBx:coupling_constant_experiment', 'BMRBx:cross_correlation*experiment', 'BMRBx:h_exch*experiment', 'BMRBx:heteronucl*experiment', 'BMRBx:homonucle*experiment', 'BMRBx:order_parameter_experiment', 'BMRBx:other_data_experiment', 'BMRBx:ph_titration_experiment', 'BMRBx:spectral_density_experiment', 'BMRBx:rdc_experiment'
						else if (attr_name_lower.equals("sample_id") && (class_name.endsWith("Experiment") || class_name.endsWith("Expt")) && (class_name.startsWith("ChemShift") || class_name.startsWith("ConformerFamilyCoordSet") || class_name.startsWith("CouplingConstant") || class_name.startsWith("CrossCorrelation") || class_name.startsWith("HExch") || class_name.startsWith("Heteronucl") || class_name.startsWith("Homonucl") || class_name.startsWith("OrderParameter") || class_name.startsWith("OtherData") || class_name.startsWith("PHTitration") || class_name.equalsIgnoreCase("RDCExperiment") || class_name.startsWith("SpectralDensity")))
							filew.write("\t\t\t\t\tif (!set_integer_sample_id(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_bmrb, entry_id, rset.getString(\"Experiment_ID\"), logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='sample_id', category='BMRBx:entity_purity', 'BMRBx:experiment', 'BMRBx:spectral_peak_list'
						else if (attr_name_lower.equals("sample_id") && (class_name.equalsIgnoreCase("EntityPurity") || class_name.equalsIgnoreCase("Experiment") || class_name.equalsIgnoreCase("SpectralPeakList")))
							filew.write("\t\t\t\t\tif (!set_integer_sample_id(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_bmrb, entry_id, logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='sample_condition_list_id', category='BMRBx:conformer_family_coord_set', 'BMRBx:coupling_constant_list', 'BMRBx:experiment', 'BMRBx:other_data_type_list', 'BMRBx:rdc_list', 'BMRBx:spectral_peak_list'
						else if (attr_name_lower.equals("sample_condition_list_id") && (class_name.equalsIgnoreCase("ConformerFamilyCoordSet") || class_name.equalsIgnoreCase("CouplingConstantList") || class_name.equalsIgnoreCase("Experiment") || class_name.equalsIgnoreCase("OtherDataTypeList") || class_name.equalsIgnoreCase("RDCList") || class_name.equalsIgnoreCase("SpectralPeakList")))
							filew.write("\t\t\t\t\tif (!set_integer_sample_condition_list_id(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_bmrb, entry_id, logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='experiment_id', category='BMRBx:coupling_constant_experiment'
						else if (attr_name_lower.equals("experiment_id") && class_name.equalsIgnoreCase("CouplingConstantExperiment"))
							filew.write("\t\t\t\t\tif (!set_integer_experiment_id(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_bmrb, entry_id, rset.getString(\"Sample_ID\"), rset.getString(\"Coupling_constant_list_ID\"), logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='experiment_id', category='BMRBx:rdc_experiment'
						else if (attr_name_lower.equals("experiment_id") && class_name.equalsIgnoreCase("RDCExperiment"))
							filew.write("\t\t\t\t\tif (!set_integer_experiment_id(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_bmrb, entry_id, rset.getString(\"Sample_ID\"), rset.getString(\"RDC_list_ID\"), logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='experiment_id', category='BMRBx:chem_shift*experiment', 'BMRBx:conformer_family_coord_set*expt', 'BMRBx:cross_correlation*experiment', 'BMRBx:h_excg*experiment', 'BMRBx:heteronucl*experiment', 'BMRBx:homonucl_noe_experiment', 'BMRBx:order_parameter_experiment', 'BMRBx:ph_titration_experiment', 'BMRBx:spectral_density_experiment'
						else if (attr_name_lower.equals("experiment_id") && (class_name.endsWith("Experiment") || class_name.endsWith("Expt")) && (class_name.startsWith("ChemShift") || class_name.startsWith("ConformerFamilyCoordSet") || class_name.startsWith("CrossCorrelation") || class_name.startsWith("HExch") || class_name.startsWith("Heteronucl") || class_name.startsWith("Homonucl") || class_name.startsWith("OrderParameter") || class_name.startsWith("OtherData") || class_name.startsWith("PHTitration") || class_name.startsWith("SpectralDensity")))
							filew.write("\t\t\t\t\tif (!set_integer_experiment_id(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_bmrb, entry_id, rset.getString(\"Sample_ID\"), logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='experiment_id', category='BMRBx:binding_result'
						else if (attr_name_lower.equals("experiment_id") && class_name.equalsIgnoreCase("BindingResult"))
							filew.write("\t\t\t\t\tif (!set_integer_experiment_id(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_bmrb, entry_id, logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='experiment_id', category='BMRBx:spectral_peak_list'
						else if (attr_name_lower.equals("experiment_id") && class_name.equalsIgnoreCase("SpectralPeakList"))
							filew.write("\t\t\t\t\tif (!set_integer_experiment_id(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_bmrb, entry_id, rset.getString(\"Sample_ID\"), logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='assigned_chem_shift_list_id', category='BMRBx:assigned_peak_chem_shift'
						else if (attr_name_lower.equals("assigned_chem_shift_list_id") && class_name.equalsIgnoreCase("AssignedPeakChemShift"))
							filew.write("\t\t\t\t\tif (!set_integer_assigned_chem_shift_list_id(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_bmrb, entry_id, logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='spectral_peak_list_id', category='BMRBx:gen_dist_constraint'
						else if (attr_name_lower.equals("spectral_peak_list_id") && class_name.equalsIgnoreCase("GenDistConstraint"))
							filew.write("\t\t\t\t\tif (!set_integer_spectral_peak_list_id(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_bmrb, entry_id, logw))\n\t\t\t\t\t\tcontinue;\n");

						// item='atom_isotope_number', category='BMRBx:chem_shift_ref'
						else if (attr_name_lower.equals("atom_isotope_number") && class_name.equalsIgnoreCase("ChemShiftRef"))
							filew.write("\t\t\t\t\tif (!set_integer(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", chem_shift_ref.atom_isotope_number, logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='number_of_monomers', category='BMRBx:entity'
						else if (attr_name_lower.equals("number_of_monomers"))
							filew.write("\t\t\t\t\tif (!set_integer_number_of_monomers(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_bmrb, entry_id, rset.getString(\"ID\"), logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='comp_index_id', category='BMRBx:pdbx_poly_seq_scheme'
						else if (attr_name_lower.equals("comp_index_id") && class_name.equalsIgnoreCase("PDBXPolySeqScheme"))
							filew.write("\t\t\t\t\tif (!set_integer_comp_index_id(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_bmrb, entry_id, rset.getString(\"Entity_ID\"), rset.getString(\"Mon_ID\"), rset.getString(\"PDB_seq_num\"), logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='entity_assembly_id', category='BMRBx:pdbx_poly_seq_scheme'
						else if (attr_name_lower.equals("entity_assembly_id") && class_name.equalsIgnoreCase("PDBXPolySeqScheme"))
							filew.write("\t\t\t\t\tif (!set_integer_entity_assembly_id(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_bmrb, entry_id, rset.getString(\"Entity_ID\"), logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='assembly_id', category='BMRBx:sample_component'
						else if (attr_name_lower.equals("assembly_id") && class_name.equalsIgnoreCase("SampleComponent"))
							filew.write("\t\t\t\t\tif (!set_integer_assembly_id(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_bmrb, entry_id, rset.getString(\"Entity_ID\"), logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='study_id', category='BMRBx:study_entry_list', 'BMRBx:study_keyword'
						else if (attr_name_lower.equals("study_id") && (class_name.equalsIgnoreCase("StudyEntryList") || class_name.equalsIgnoreCase("StudyKeyword")))
							filew.write("\t\t\t\t\tif (!set_integer_study_id(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_bmrb, entry_id, rset.getString(\"Study_list_ID\"), logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='modeled_sample_cond_list_id', category='BMRBx:chem_shifts_calc_type'
						else if (attr_name_lower.equals("modeled_sample_cond_list_id") && class_name.equalsIgnoreCase("ChemShiftsCalcType"))
							filew.write("\t\t\t\t\tif (!set_integer_modeled_sample_cond_list_id(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_bmrb, entry_id, logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='tensor_list_id', category='BMRBx:theoretical_chem_shift_list'
						else if (attr_name_lower.equals("tensor_list_id") && class_name.equalsIgnoreCase("TheoreticalChemShiftList"))
							filew.write("\t\t\t\t\tif (!set_integer_tensor_list_id(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_bmrb, entry_id, logw))\n\t\t\t\t\t\tcontinue;\n");
						else
							filew.write("\t\t\t\t\tif (!set_integer(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), logw))\n\t\t\t\t\t\tcontinue;\n");

					}

					else if (attr_type.equals("xsd:decimal")) {

						// item='val', category='BMRBx:atom_chem_shift'
						if (attr_name_lower.equals("val") && class_name.equalsIgnoreCase("AtomChemShift")) {
							filew.write("\t\t\t\t\tif (!set_decimal_val(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), rset.getString(\"Ambiguity_code\"), logw)) {\n");
							write_excld_log_code(filew, method_name, column_name);
							filew.write("\t\t\t\t\t\tcontinue;\n\t\t\t\t\t}\n");
						}
						// item='indirect_shift_ratio', category='BMRBx:chem_shift_ref'
						else if (attr_name_lower.equals("indirect_shift_ratio") && class_name.equalsIgnoreCase("ChemShiftRef"))
							filew.write("\t\t\t\t\tif (!set_decimal(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", chem_shift_ref.indirect_shift_ratio, logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='tau_e_val', 'tau_e_val_fiterr', category='BMRBx:order_param'
						else if (attr_name_lower.startsWith("tau_e_val") && class_name.equalsIgnoreCase("OrderParam"))
							filew.write("\t\t\t\t\tif (!set_decimal_tau_e_val(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_bmrb, entry_id, logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='drawing_2d_coord_x', category='BMRBx:chem_comp_atom'
						else if (attr_name_lower.startsWith("drawing_2d_coord_x") && class_name.equalsIgnoreCase("ChemCompAtom"))
							filew.write("\t\t\t\t\tif (!set_decimal_drawing_2d_coord_xy(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), entry_id, rset.getString(\"Stereo_config\"), logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='drawing_2d_coord_y', category='BMRBx:chem_comp_atom'
						else if (attr_name_lower.startsWith("drawing_2d_coord_y") && class_name.equalsIgnoreCase("ChemCompAtom"))
							filew.write("\t\t\t\t\tif (!set_decimal_drawing_2d_coord_xy(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), entry_id, rset.getString(\"Charge\"), logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='formula_weight', category='BMRBx:entity'
						else if (attr_name_lower.equals("formula_weight") && class_name.equalsIgnoreCase("Entity"))
							filew.write("\t\t\t\t\tif (!set_decimal_formula_weight(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_bmrb, conn_le, entry_id, rset.getString(\"ID\"), logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='formula_weight', category='BMRBx:chem_comp'
						else if (attr_name_lower.equals("formula_weight") && class_name.equalsIgnoreCase("ChemComp"))
							filew.write("\t\t\t\t\tif (!set_decimal_formula_weight(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), conn_le, pdb_code, logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='chem_shift_val', category='BMRBx:peak_char'
						else if (attr_name_lower.equals("chem_shift_val") && class_name.equalsIgnoreCase("PeakChar")) {
							filew.write("\t\t\t\t\tif (!set_decimal_chem_shift_val(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), logw)) {\n");
							write_excld_log_code(filew, method_name, column_name);
							filew.write("\t\t\t\t\t\tcontinue;\n\t\t\t\t\t}\n");
						}
						// item='intensity_val', category='BMRBx:peak_general_char'
						else if (attr_name_lower.equals("intensity_val") && class_name.equalsIgnoreCase("PeakGeneralChar")) {
							filew.write("\t\t\t\t\tif (!set_decimal_intensity_val(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), logw)) {\n");
							write_excld_log_code(filew, method_name, column_name);
							filew.write("\t\t\t\t\t\tcontinue;\n\t\t\t\t\t}\n");
						}
						else
							filew.write("\t\t\t\t\tif (!set_decimal(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), logw))\n\t\t\t\t\t\tcontinue;\n");

					}

					else if (attr_type.equals("xsd:double"))
						filew.write("\t\t\t\t\tif (!set_double(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\"), logw))\n\t\t\t\t\t\tcontinue;\n");

					else if (attr_type.equals("xsd:date")) {
						// item='original_release_date', category='BMRBx:entry'
						if (attr_name_lower.equals("original_release_date") && class_name.equalsIgnoreCase("Entry"))
							filew.write("\t\t\t\t\tif (!set_original_release_date(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\") != null && !rset.getString(\"" + column_name + "\").equals(\".\") && !rset.getString(\"" + column_name + "\").equals(\"?\") ? rset.getDate(\"" + column_name + "\") : null, conn_bmrb, entry_id, accession_date != null ? accession_date : submission_date, logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='last_release_date', category='BMRBx:entry'
						else if (attr_name_lower.equals("last_release_date") && class_name.equalsIgnoreCase("Entry"))	
							filew.write("\t\t\t\t\tif (!set_last_release_date(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\") != null && !rset.getString(\"" + column_name + "\").equals(\".\") && !rset.getString(\"" + column_name + "\").equals(\"?\") ? rset.getDate(\"" + column_name + "\") : null, conn_bmrb, entry_id, accession_date != null ? accession_date : submission_date, logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='accession_date', category='BMRBx:entry'
						else if (attr_name_lower.equals("accession_date") && class_name.equalsIgnoreCase("Entry"))	
							filew.write("\t\t\t\t\tif (!set_date_entry(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", accession_date != null ? accession_date : submission_date, conn_bmrb, entry_id, logw))\n\t\t\t\t\t\tcontinue;\n");
						// item='submission_date', category='BMRBx:entry'
						else if (attr_name_lower.equals("submission_date") && class_name.equalsIgnoreCase("Entry"))	
							filew.write("\t\t\t\t\tif (!set_date_entry(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", submission_date != null ? submission_date : accession_date, conn_bmrb, entry_id, logw))\n\t\t\t\t\t\tcontinue;\n");
						else
							filew.write("\t\t\t\t\tif (!set_date(list[0], \"" + method_name + "\", \"" + nil_method_name + "\", " + required + ", rset.getString(\"" + column_name + "\") != null && !rset.getString(\"" + column_name + "\").equals(\".\") && !rset.getString(\"" + column_name + "\").equals(\"?\") ? rset.getDate(\"" + column_name + "\") : null, logw))\n\t\t\t\t\t\tcontinue;\n");
					}

					else
						System.err.println("class: " + class_name + ", attr: " + attr_name + " has unsupported xsd:type.");

				} catch (IOException e) {
					e.printStackTrace();
				}

				return;
			}
		}

		//			BMSxTool_DOM.getNodeInfo(child);
		if (!attr_name.equalsIgnoreCase("pdbx_name") || !attr_name.equalsIgnoreCase("sunid")) {
			if (required) {
				System.err.println("class: " + class_name + ", xsd_attr: " + attr_name + " is unresolved. [Fatal Error]");
				System.exit(1);
			}
			else
				System.out.println("class: " + class_name + ", xsd_attr: " + attr_name + " is unresolved.");
		}
	}

	private static void extract_enum_nodes(FileWriter filew, Node node, String table_name, List<String> column_list, String file_prefix) {

		for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {

			if (child.getNodeType() == Node.ELEMENT_NODE && (child.getNodeName().equals("xsd:element") || child.getNodeName().equals("xsd:attribute"))) {
				//				BMSxTool_DOM.getNodeInfo(child);

				NamedNodeMap node_map = child.getAttributes();

				if (node_map != null) {

					Node attr_occur = node_map.getNamedItem("maxOccurs");
					Node attr_name = node_map.getNamedItem("name");

					if (attr_name != null) {
						if (!attr_name.getTextContent().equalsIgnoreCase(table_name) || (attr_name.getTextContent().equalsIgnoreCase(table_name) && (attr_occur == null || attr_occur.getTextContent().isEmpty()))) {
							if (has_enum_string(child))
								write_enum_string_util(filew, child, attr_name.getTextContent(), column_list, file_prefix);
							else if (has_enum_integer(child))
								write_enum_integer_util(filew, child, attr_name.getTextContent(), column_list, file_prefix);
							else if (has_enum_decimal(child))
								write_enum_decimal_util(filew, child, attr_name.getTextContent(), column_list, file_prefix);
						}
					}
				}
			}

			extract_enum_nodes(filew, child, table_name, column_list, file_prefix);
		}
	}

	private static void write_enum_string_util(FileWriter filew, Node node, String attr_name, List<String> column_list, String file_prefix) {

		String attr_name_lower = attr_name.toLowerCase();

		char[] _attr_name = attr_name_lower.toCharArray();
		char[] _Attr_name = attr_name.toCharArray();

		boolean under_score = true;
		boolean numeric_code = false;

		for (int i = 0; i < attr_name.length(); i++) {
			if (under_score || numeric_code || (_Attr_name[i] >= 'A' && _Attr_name[i] <= 'Z'))
				_attr_name[i] = Character.toUpperCase(_attr_name[i]);
			under_score = (_attr_name[i] == '_');
			numeric_code = (_attr_name[i] >= '0' && _attr_name[i] <= '9');
		}

		String __attr_name = String.valueOf(_attr_name).replace("_", "");
		String enum_class_name;

		if (!abs_class_name.equalsIgnoreCase(__attr_name))
			enum_class_name = abs_class_name + "." + __attr_name + ".Enum";
		else
			enum_class_name = abs_class_name + "." + __attr_name + "2.Enum";

		if (class_name.equals("EntryInterview") && (attr_name_lower.equals("auto_relaxation") || attr_name_lower.equals("interatomic_distance") || attr_name_lower.equals("tensor")))
			return;

		// category='BMRBx:entry' (metabolomics specific)
		//		if (class_name.equals("Entry") && (attr_name_lower.equals("type")))
		//			return;

		for (String column_name : column_list) {

			if (attr_name.equalsIgnoreCase(column_name)) {

				try {

					// item='database_code', category='BMRBx:entity_db_link', 'BMRBx:assembly_db_link'
					if (attr_name_lower.equals("database_code") && (class_name.equalsIgnoreCase("EntityDbLink") || class_name.equalsIgnoreCase("AssemblyDbLink")))
						filew.write("\n\tprivate static boolean set_enum_" + attr_name_lower +"(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, String entry_id, String accession_code, FileWriter logw, FileWriter errw) {\n\n");
					// item='database_name', category='BMRBx:related_entries'
					else if (attr_name_lower.equals("database_name") && (class_name.equalsIgnoreCase("RelatedEntries")))
						filew.write("\n\tprivate static boolean set_enum_" + attr_name_lower +"(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, String entry_id, String accession_code, FileWriter logw, FileWriter errw) {\n\n");
					// item='type', category='BMRBx:assembly_type'
					else if (attr_name_lower.equals("type") && class_name.equalsIgnoreCase("AssemblyType"))
						filew.write("\n\tprivate static boolean set_enum_" + attr_name_lower +"(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, String entry_id, String assembly_id, FileWriter logw, FileWriter errw) {\n\n");
					// item='type', category='BMRBx:sample'
					else if (attr_name_lower.equals("type") && class_name.equalsIgnoreCase("Sample"))
						filew.write("\n\tprivate static boolean set_enum_" + attr_name_lower +"(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, String entry_id, FileWriter logw, FileWriter errw) {\n\n");
					// item='value_order', category='BMRBx:bond'
					// item='experimental_method_subtype', category='BMRBx:entry'
					// item='t1_coherence_type', category='BMRBx:hetoronucl_t1_list'
					// item='t2_coherence_type', category='BMRBx:hetoronucl_t2_list'
					else if ((attr_name_lower.equals("value_order") && class_name.equalsIgnoreCase("Bond")) || (attr_name_lower.equals("experimental_method_subtype") && class_name.equalsIgnoreCase("Entry")) || (attr_name_lower.equals("t1_coherence_type") && class_name.equalsIgnoreCase("HeteronuclT1List")) || (attr_name_lower.equals("t2_coherence_type") && class_name.equalsIgnoreCase("HeteronuclT2List")))
						filew.write("\n\tprivate static boolean set_enum_" + attr_name_lower +"(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, String entry_id, FileWriter logw, FileWriter errw) {\n\n");
					// item='constraint_type', category='BMRBx:constraint_file'
					else if (attr_name_lower.equals("constraint_type") && class_name.equalsIgnoreCase("ConstraintFile"))
						filew.write("\n\tprivate static boolean set_enum_" + attr_name_lower +"(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, String constraint_filename, FileWriter logw, FileWriter errw) {\n\n");
					// item='superkingdom', 'kingdom', category='BMRBx:entity_natural_src'
					// item='host_org_scientific_name', category='BMRBx:entity_experimental_src'
					else if (((attr_name_lower.equals("superkingdom") || attr_name_lower.equals("kingdom")) && class_name.equalsIgnoreCase("EntityNaturalSrc")) || (attr_name_lower.equals("host_org_scientific_name") && class_name.equalsIgnoreCase("EntityExperimentalSrc")))
						filew.write("\n\tprivate static boolean set_enum_" + attr_name_lower +"(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_tax, String entry_id, String ncbi_taxonomy_id, FileWriter logw, FileWriter errw) {\n\n");
					// item='production_method', category='BMRBx:entity_experimental_src'
					else if (attr_name_lower.equals("production_method") && class_name.equalsIgnoreCase("EntityExperimentalSrc"))
						filew.write("\n\tprivate static boolean set_enum_" + attr_name_lower +"(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, " + file_prefix + "_" + BMSxTool_DOM.util_entityexperimentalsrc + " ee, FileWriter logw, FileWriter errw) {\n\n");
					// item='common', 'organ', 'organelle', 'secretion', 'type', category='BMRBx:entity_natural_src'
					else if ((attr_name_lower.equals("common") || attr_name_lower.equals("organ") || attr_name_lower.equals("organelle") || attr_name_lower.equals("secretion") || attr_name_lower.equals("type")) && class_name.equalsIgnoreCase("EntityNaturalSrc"))
						filew.write("\n\tprivate static boolean set_enum_" + attr_name_lower +"(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, " + file_prefix + "_" + BMSxTool_DOM.util_entitynaturalsrc + " en, FileWriter logw, FileWriter errw) {\n\n");
					// item='stereo_config', category='BMRBx:chem_comp_atom'
					else if (attr_name_lower.equals("stereo_config") && class_name.equalsIgnoreCase("ChemCompAtom"))
						filew.write("\n\tprivate static boolean set_enum_" + attr_name_lower +"(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, String entry_id, String val_name_can, FileWriter logw, FileWriter errw) {\n\n");
					else // if (!attr_name_lower.equals("sweep_width_units") || !class_name.equalsIgnoreCase("SpectralDim"))
						filew.write("\n\tprivate static boolean set_enum_" + attr_name_lower +"(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {\n\n");

					//			if (!attr_name_lower.equals("sweep_width_units") || !class_name.equalsIgnoreCase("SpectralDim")) {

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml) {\n\n");

					// category='BMRBx:assembly'
					if (class_name.equalsIgnoreCase("Assembly")) {

						// item='thiol_state'
						if (attr_name_lower.equals("thiol_state")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_assembly + ".getThiolState(val_name);\n\n");

						}

						// item='non_standard_bonds'
						if (attr_name_lower.equals("non_standard_bonds")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_assembly + ".getNonStandardBonds(val_name);\n\n");

						}

						// item='ambiguous_chem_comp_sites'
						if (attr_name_lower.equals("ambiguous_chem_comp_sites")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_assembly + ".getAmbiguousChemCompSites(val_name);\n\n");

						}

						// item='ambiguous_conformational_states'
						if (attr_name_lower.equals("ambiguous_conformational_states")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_assembly + ".getAmbiguousConformationalStates(val_name);\n\n");

						}

						// item='paramagnetic'
						if (attr_name_lower.equals("paramagnetic")) {

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\tval_name = \"no\";\n\n");

						}

					}

					// item='database_code', category='BMRBx:assembly_db_link', 'BMRBx:entity_db_link'
					if (attr_name_lower.equals("database_code")) {

						// category='BMRBx:assembly_db_link'
						if (class_name.equalsIgnoreCase("AssemblyDbLink")) {

							filew.write("\t\tif (val_name != null || accession_code != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_assemblydblink + ".getDatabaseCode(val_name, accession_code);\n\n");

						}

						// category='BMRBx:entity_db_link'
						if (class_name.equalsIgnoreCase("EntityDbLink")) {

							filew.write("\t\tif (val_name != null || accession_code != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_entitydblink + ".getDatabaseCode(val_name, accession_code);\n\n");

						}

						filew.write("\t\tif (" + empty_check("val_name") + ")\n");
						filew.write("\t\t\treturn false;\n\n");

					}

					// category='BMRBx:assembly_db_link'
					if (class_name.equalsIgnoreCase("AssemblyDbLink")) {

						// item='entry_experimental_method'
						if (attr_name_lower.equals("entry_experimental_method")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_assemblydblink + ".getEntryExperimentalMethod(val_name);\n\n");

						}

						// item='author_supplied'
						if (attr_name_lower.equals("author_supplied")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_assemblydblink + ".getAuthorSupplied(val_name);\n\n");

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\tval_name = \"yes\";\n\n");

						}

					}

					// item='mol_interaction_type', category='BMRBx:assembly_interaction'
					if (attr_name_lower.equals("mol_interaction_type") && class_name.equalsIgnoreCase("AssemblyInteraction")) {

						filew.write("\t\tif (val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_assemblyinteraction + ".getMolInteractionType(val_name);\n\n");

						filew.write("\t\tif (" + empty_check("val_name") + ")\n");
						filew.write("\t\t\tval_name = \"na\";\n\n");

					}

					// item='naming_system'
					if (attr_name_lower.equals("naming_system")) {

						// category='BMRBx:assembly_systematic_name'
						if (class_name.equalsIgnoreCase("AssemblySystematicName")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_assemblysystematicname + ".getNamingSystem(val_name);\n\n");

						}

						// category='BMRBx:chem_comp_systematic_name'
						if (class_name.equalsIgnoreCase("ChemCompSystematicName")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_chemcompsystematicname + ".getNamingSystem(val_name);\n\n");

						}

						// category='BMRBx:entity_systematic_name'
						if (class_name.equalsIgnoreCase("EntitySystematicName")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_entitysystematicname + ".getNamingSystem(val_name);\n\n");

						}

					}

					// item='type', category='BMRBx:assembly_type'
					if (attr_name_lower.equals("type") && class_name.equalsIgnoreCase("AssemblyType")) {

						filew.write("\t\tif (val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_assemblytype + ".getType(val_name);\n\n");

						filew.write("\t\tif (" + empty_check("val_name") + ")\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_assemblytype + ".getType(val_name, entry_id, assembly_id);\n\n");

						filew.write("\t\tif (" + empty_check("val_name") + ")\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_assemblytype + ".guessType(conn_bmrb, entry_id, assembly_id);\n\n");

					}

					// item='temp_control_method', category='BMRBx:auto_relaxation_list'
					if (attr_name_lower.equals("temp_control_method") && class_name.equalsIgnoreCase("AutoRelaxationList")) {

						filew.write("\t\tif (val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_autorelaxationlist + ".getTempControlMethod(val_name);\n\n");

					}

					// category='BMRBx:bond'
					if (class_name.equalsIgnoreCase("Bond")) {

						// item='type'
						if (attr_name_lower.equals("type")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_bond + ".getType(val_name);\n\n");

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\tval_name = \"na\";\n\n");

						}

						// item='value_order'
						if (attr_name_lower.equals("value_order")) {

							filew.write("\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_bond + ".getValueOrder(val_name, entry_id);\n\n");

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\tval_name = \"sing\";\n\n");

						}

					}

					// category='BMRBx:chem_comp'
					if (class_name.equalsIgnoreCase("ChemComp")) {

						// item='formal_charge'
						if (attr_name_lower.equals("formal_charge")) {

							filew.write("\t\tif (val_name != null) {\n\n");

							filew.write("\t\t\tif (val_name.startsWith(\"-\"))\n");
							filew.write("\t\t\t\tval_name = val_name.replaceFirst(\"^-\",\"\") + \"-\";\n");

							filew.write("\t\t\tif (val_name.startsWith(\"+\"))\n");
							filew.write("\t\t\t\tval_name = val_name.replaceFirst(\"^+\",\"\") + \"+\";\n\n");

							filew.write("\t\t\tif (!val_name.equals(\"0\") && !val_name.endsWith(\"-\") && val_name.matches(\"^[1-9]$\"))\n");
							filew.write("\t\t\t\tval_name = val_name + \"+\";\n\n");

							filew.write("\t\t\tif (!val_name.matches(\"^[0-9][-+]?$\"))\n");
							filew.write("\t\t\t\tval_name = \"0\";\n\n");

							filew.write("\t\t}\n\n");

						}

						// item='type'
						if (attr_name_lower.equals("type")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_chemcomp + ".getType(val_name);\n\n");

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\t\tval_name = \"OTHER\";\n\n");

						}

						// item='processing_site'
						if (attr_name_lower.equals("processing_site")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_chemcomp + ".getProcessingSite(val_name);\n\n");

						}

						// item='paramagnetic'
						if (attr_name_lower.equals("paramagnetic")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_chemcomp + ".getParamagnetic(val_name);\n\n");

						}

						// item='aromatic'
						if (attr_name_lower.equals("aromatic")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_chemcomp + ".getAromatic(val_name);\n\n");

						}

						// item='provenance'
						if (attr_name_lower.equals("provenance")) {

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\t\tval_name = \"na\";\n\n");

						}

					}

					// category='BMRBx:chem_comp_atom'
					if (class_name.equalsIgnoreCase("ChemCompAtom")) {

						// item='aromatic_flag'
						if (attr_name_lower.equals("aromatic_flag")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_chemcompatom + ".getAromaticFlag(val_name);\n\n");

						}

						// item='leaving_atom_flag'
						if (attr_name_lower.equals("leaving_atom_flag")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_chemcompatom + ".getLeavingAtomFlag(val_name);\n\n");

						}

						// item='stereo_config'
						if (attr_name_lower.equals("stereo_config")) {

							filew.write("\t\tif (entry_id.equals(\"bmse000098\") || entry_id.equals(\"bmse000126\") || entry_id.equals(\"bmse000180\") || entry_id.equals(\"bmse000888\") || entry_id.equals(\"bmse000978\")) {\n\n");

							filew.write("\t\t\tif (!(" + empty_check("val_name") + ") && val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)?$\"))\n");
							filew.write("\t\t\t\tval_name = val_name_can;\n\n");

							filew.write("\t\t}\n\n");

						}

					}

					// category='BMRBx:chem_comp_bond'
					if (class_name.equalsIgnoreCase("ChemCompBond")) {

						// item='type'
						if (attr_name_lower.equals("type")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_chemcompbond + ".getType(val_name);\n\n");

						}

						// item='value_order'
						if (attr_name_lower.equals("value_order")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_chemcompbond + ".getValueOrder(val_name);\n\n");

						}

						// item='stereo_config'
						if (attr_name_lower.equals("stereo_config")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_chemcompbond + ".getStereoConfig(val_name);\n\n");

						}

					}

					// item='type', category='BMRBx:chem_comp_common_name'
					if (attr_name_lower.equals("type") && class_name.equalsIgnoreCase("ChemCompCommonName")) {

						filew.write("\t\tif (val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_chemcompcommonname + ".getType(val_name);\n\n");

						filew.write("\t\tif (" + empty_check("val_name") + ")\n");
						filew.write("\t\t\tval_name = \"na\";\n\n");

					}

					// category='BMRBx:chem_comp_descriptor'
					if (class_name.equalsIgnoreCase("ChemCompDescriptor")) {

						// item='type'
						if (attr_name_lower.equals("type")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_chemcompdescriptor + ".getType(val_name);\n\n");

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\tval_name = \"na\";\n\n");

						}

						// item='program'
						if (attr_name_lower.equals("program")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_chemcompdescriptor + ".getProgram(val_name);\n\n");

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\tval_name = \"na\";\n\n");

						}

					}

					// item='program', category='BMRBx:chem_comp_identifier'
					if (attr_name_lower.equals("program") && class_name.equalsIgnoreCase("ChemCompIdentifier")) {

						filew.write("\t\tif (val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_chemcompidentifier + ".getProgram(val_name);\n\n");

						filew.write("\t\tif (" + empty_check("val_name") + ")\n");
						filew.write("\t\t\tval_name = \"OTHER\";\n\n");

					}

					// item='sample_state'
					if (attr_name_lower.equals("sample_state") && (class_name.endsWith("Experiment") || class_name.endsWith("Expt"))) {

						// category='BMRBx:chem_shift_experiment', 'BMRBx:chemical_rate_experiment', 'BMRBx:binding experiment'
						if (class_name.equalsIgnoreCase("ChemShiftExperiment") || class_name.equalsIgnoreCase("ChemicalRateExperiment") || class_name.equalsIgnoreCase("BindingExperiment")) {

							filew.write("\t\tif (val_name != null && (val_name.startsWith(\"aniso\") || val_name.equals(\"solid\") || val_name.contains(\"crystal\")))\n");
							filew.write("\t\t\tval_name = \"anisotropic\";\n\n");

							filew.write("\t\telse\n");
							filew.write("\t\t\tval_name = \"isotropic\";\n\n");

						}

						else {

							filew.write("\t\tif (val_name != null && val_name.startsWith(\"aniso\"))\n");
							filew.write("\t\t\tval_name = \"anisotropic\";\n\n");

							filew.write("\t\telse if (val_name != null && (val_name.equals(\"solid\") || val_name.contains(\"crystal\")))\n");
							filew.write("\t\t\tval_name = \"solid\";\n\n");

							filew.write("\t\telse\n");
							filew.write("\t\t\tval_name = \"isotropic\";\n\n");

						}

					}

					//item='caluculation_level', category='BMRBx:chem_shift_calc_type'
					if (attr_name_lower.equals("calculation_level") && class_name.equalsIgnoreCase("ChemShiftsCalcType")) {

						filew.write("\t\tif (val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_chemshiftscalctype + ".getCalculationLevel(val_name);\n\n");

					}

					// category='BMRBx:chem_shift_ref'
					if (class_name.equalsIgnoreCase("ChemShiftRef")) {

						// item='atom_type'
						if (attr_name_lower.equals("atom_type")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_chemshiftref + ".getAtomType(val_name);\n\n");

						}

						// item='mol_common_name'
						if (attr_name_lower.equals("mol_common_name")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_chemshiftref + ".getMolCommonName(val_name);\n\n");

						}

						// item='ref_method'
						if (attr_name_lower.equals("ref_method")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_chemshiftref + ".getRefMethod(val_name);\n\n");

						}

						// item='ref_type'
						if (attr_name_lower.equals("ref_type")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_chemshiftref + ".getRefType(val_name);\n\n");

						}

						// item='chem_shift_units'
						if (attr_name_lower.equals("chem_shift_units")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_chemshiftref + ".getChemShiftUnits(val_name);\n\n");

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\tval_name = \"na\";\n\n");

						}

						// item='external_ref_loc'
						if (attr_name_lower.equals("external_ref_loc")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_chemshiftref + ".getExternalRefLoc(val_name);\n\n");

						}

						// item='external_ref_axis'
						if (attr_name_lower.equals("external_ref_axis")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_chemshiftref + ".getExternalRefAxis(val_name);\n\n");

						}

						// item='external_ref_sample_geometry'
						if (attr_name_lower.equals("external_ref_sample_geometry")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_chemshiftref + ".getExternalRefSampleGeometry(val_name);\n\n");

						}

					}

					// category='BMRBx:citation'
					if (class_name.equalsIgnoreCase("Citation")) {

						// item='class'
						if (attr_name_lower.equals("class")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_citation + ".getClass1(val_name);\n\n");

						}

						// item='type'
						if (attr_name_lower.equals("type")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_citation + ".getType(val_name);\n\n");

						}

						// item='status'
						if (attr_name_lower.equals("status")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_citation + ".getStatus(val_name);\n\n");

						}

					}

					// item='family_title'
					if (attr_name_lower.equals("family_title")) {

						// category='BMRBx:citation_author'
						if (class_name.equalsIgnoreCase("CitationAuthor")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_citationauthor + ".getFamilyTitle(val_name);\n\n");

						}

						// category='BMRBx:entry_author'
						if (class_name.equalsIgnoreCase("EntryAuthor")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_entryauthor + ".getFamilyTitle(val_name);\n\n");

						}

					}

					// item='refine_method', category='BMRBx:conformer_family_refinement'
					if (attr_name_lower.equals("refine_method") && class_name.equalsIgnoreCase("ConformerFamilyRefinement")) {

						filew.write("\t\tif (val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_conformerfamilyrefinement + ".getRefineMethod(val_name);\n\n");

						filew.write("\t\tif (" + empty_check("val_name") + ")\n");
						filew.write("\t\t\tval_name = \"na\";\n\n");

					}

					// category='BMRBx:conformer_stat_list'
					if (class_name.equalsIgnoreCase("ConformerStatList")) {

						// item='conformer_selection_criteria'
						if (attr_name_lower.equals("conformer_selection_criteria")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_conformerstatlist + ".getConformerSelectionCriteria(val_name);\n\n");

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\tval_name = \"na\";\n\n");

						}

						// item='rep_conformer_selection_criteria'
						if (attr_name_lower.equals("rep_conformer_selection_criteria")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_conformerstatlist + ".getRepConformerSelectionCriteria(val_name);\n\n");

						}

						// item='both_ensemble_and_rep_conformer'
						if (attr_name_lower.equals("both_ensemble_and_rep_conformer")) {

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\tval_name = \"no\";\n\n");

						}

						// item='conformer_ensemble_only'
						if (attr_name_lower.equals("conformer_ensemble_only")) {

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\tval_name = \"no\";\n\n");

						}

						// item='representative_conformer_only'
						if (attr_name_lower.equals("representative_conformer_only")) {

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\tval_name = \"no\";\n\n");

						}

					}

					// category='BMRBx:constraint_file'
					if (class_name.equalsIgnoreCase("ConstraintFile")) {

						// item='constraint_type'
						if (attr_name_lower.equals("constraint_type")) {

							filew.write("\t\tif ((" + empty_check("val_name") + ") && constraint_filename != null) {\n\n");

							filew.write("\t\t\tif (constraint_filename.endsWith(\".upl\"))\n");
							filew.write("\t\t\t\tval_name = \"distance\";\n\n");

							filew.write("\t\t}\n\n");

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_constraintfile + ".getConstraintType(val_name);\n\n");

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\tval_name = \"na\";\n\n");

						}

						// item='constraint_subtype'
						if (attr_name_lower.equals("constraint_subtype")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_constraintfile + ".getConstraintSubtype(val_name);\n\n");

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\tval_name = \"Not applicable\";\n\n");

						}

						// item='constraint_subsubtype'
						if (attr_name_lower.equals("constraint_subsubtype")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_constraintfile + ".getConstraintSubsubtype(val_name);\n\n");

						}

					}

					// item='spectrometer_frequency_1h', category='BMRBx:coupling_constatn_list'
					if (attr_name_lower.equals("spectrometer_frequency_1h") && class_name.equalsIgnoreCase("CouplingConstantList")) {

						filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_couplingconstantlist + ".getSpectrometerFrequency1H(val_name);\n\n");

					}

					// item='type', category='BMRBx:data_set'
					if (attr_name_lower.equals("type") && class_name.equalsIgnoreCase("DataSet")) {

						filew.write("\t\tif (val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_dataset + ".getType(val_name);\n\n");

					}

					// item='type', category='BMRBx:datum'
					if (attr_name_lower.equals("type") && class_name.equalsIgnoreCase("Datum")) {

						filew.write("\t\tif (val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_datum + ".getType(val_name);\n\n");

						filew.write("\t\tif (" + empty_check("val_name") + ")\n");
						filew.write("\t\t\tval_name = \"na\";\n\n");

					}

					// category='BMRBx:entity'
					if (class_name.equalsIgnoreCase("Entity")) {

						// item='type'
						if (attr_name_lower.equals("type")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_entity + ".getType(val_name);\n\n");

						}

						// item='polymer_type'
						if (attr_name_lower.equals("polymer_type")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_entity + ".getPolymerType(val_name);\n\n");

						}

						// item='thiol_state'
						if (attr_name_lower.equals("thiol_state")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_entity + ".getThiolState(val_name);\n\n");

						}

						// item='ambiguous_conformational_states'
						if (attr_name_lower.equals("ambiguous_conformational_states")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_entity + ".getAmbiguousConformationalStates(val_name);\n\n");

						}

						// item='ambiguous_chem_comp_sites'
						if (attr_name_lower.equals("ambiguous_chem_comp_sites")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_entity + ".getAmbiguousChemCompSites(val_name);\n\n");

						}

						// item='nstd_chirality'
						if (attr_name_lower.equals("nstd_chirality")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_entity + ".getNstdChirality(val_name);\n\n");

						}

						// item='nstd_linkage'
						if (attr_name_lower.equals("nstd_linkage")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_entity + ".getNstdLinkage(val_name);\n\n");

						}

						// item='paramagnetic'
						if (attr_name_lower.equals("paramagnetic")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_entity + ".getParamagnetic(val_name);\n\n");

						}

					}

					// category='BMRBx:entity_assembly'
					if (class_name.equalsIgnoreCase("EntityAssembly")) {

						// item='physical_state'
						if (attr_name_lower.equals("physical_state")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_entityassembly + ".getPhysicalState(val_name);\n\n");

						}

						// item='conformational_isomer'
						if (attr_name_lower.equals("conformational_isomer")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_entityassembly + ".getConformationalIsomer(val_name);\n\n");

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\tval_name = \"no\";\n\n");

						}

						// item='chemical_exchange_state'
						if (attr_name_lower.equals("chemical_exchange_state")) {

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\tval_name = \"no\";\n\n");

						}

					}

					// item='value_order', category='BMRBx:entity_bond'
					if (attr_name_lower.equals("value_order") && class_name.equalsIgnoreCase("EntityBond")) {

						filew.write("\t\tif (val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_entitybond + ".getValueOrder(val_name);\n\n");

					}

					// category='BMRBx:entity_db_link'
					if (class_name.equalsIgnoreCase("EntityDbLink")) {

						// item='entry_experimental_method'
						if (attr_name_lower.equals("entry_experimental_method")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_entitydblink + ".getEntryExperimentalMethod(val_name);\n\n");

						}

						// item='author_supplied'
						if (attr_name_lower.equals("author_supplied")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_entitydblink + ".getAuthorSupplied(val_name);\n\n");

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\tval_name = \"yes\";\n\n");

						}

					}

					// category='BMRBx:entity_experimental_src'
					if (class_name.equalsIgnoreCase("EntityExperimentalSrc")) {

						// item='production_method'
						if (attr_name_lower.equals("production_method")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = ee.getProductionMethod(val_name);\n\n");

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\tval_name = \"na\";\n\n");

						}

						// item='host_org_scientific_name'
						if (attr_name_lower.equals("host_org_scientific_name")) {

							filew.write("\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_tax + ".getOrganismScientificName(val_name, conn_tax, ncbi_taxonomy_id);\n\n");

						}

					}

					// category='BMRBx:entity_natural_src'
					if (class_name.equalsIgnoreCase("EntityNaturalSrc")) {

						// item='superkingdom'
						if (attr_name_lower.equals("superkingdom")) {

							filew.write("\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_tax + ".getSuperkingdom(val_name, conn_tax, ncbi_taxonomy_id);\n\n");

							filew.write("\t\tif ((val_name != null && val_name.equals(\"unclassified\")) || entry_id.equals(\"18145\"))\n");
							filew.write("\t\t\tval_name = \"Unclassified\";\n\n");

							filew.write("\t\tif (val_name != null && entry_id.equals(\"5934\"))\n");
							filew.write("\t\t\tval_name = \"Bacteria\";\n\n");

							filew.write("\t\tif (val_name == null || val_name.equals(\"n/a\"))\n");
							filew.write("\t\t\tval_name = \"Unclassified\";\n\n");

						}

						// item='kingdom'
						if (attr_name_lower.equals("kingdom")) {

							filew.write("\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_tax + ".getKingdom(val_name, conn_tax, ncbi_taxonomy_id);\n\n");

							filew.write("\t\tif (val_name == null || val_name.equals(\"n/a\"))\n");
							filew.write("\t\t\tval_name = \"Not applicable\";\n\n");

						}

						// item='type'
						if (attr_name_lower.equals("type")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = en.getType(val_name);\n\n");

						}

						// item='organ'
						if (attr_name_lower.equals("organ")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = en.getOrgan(val_name);\n\n");

						}

						// item='organelle'
						if (attr_name_lower.equals("organelle")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = en.getOrganelle(val_name);\n\n");

						}

						// item='secretion'
						if (attr_name_lower.equals("secretion")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = en.getSecretion(val_name);\n\n");

						}

						// item='common'
						if (attr_name_lower.equals("common")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = en.getCommon(val_name);\n\n");

						}

					}

					// category='BMRBx:entry'
					if (class_name.equalsIgnoreCase("Entry")) {

						// item='experimental_method'
						if (attr_name_lower.equals("experimental_method")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_entry + ".getExperimentalMethod(val_name);\n\n");

						}

						// item='experimental_method_subtype'
						if (attr_name_lower.equals("experimental_method_subtype")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_entry + ".getExperimentalMethodSubtype(val_name, entry_id);\n\n");

						}

						// item='version_type'
						if (attr_name_lower.equals("version_type")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_entry + ".getVersionType(val_name);\n\n");

						}

						// item='nmr_star_version'
						if (attr_name_lower.equals("nmr_star_version")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_entry + ".getNMRStarVersion(val_name);\n\n");

						}

						// item='original_nmr_star_version'
						if (attr_name_lower.equals("original_nmr_star_version")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_entry + ".getOriginalNMRStarVersion(val_name);\n\n");

						}

						// item='type'
						if (attr_name_lower.equals("type")) {

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\tval_name = \"metabolite/natural product\";\n\n");

						}

					}

					// item='method', category='BMRBx:entry_experimental_methods'
					if (attr_name_lower.equals("method") && class_name.equalsIgnoreCase("EntryExperimentalMethods")) {

						filew.write("\t\tif (val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_entryexperimentalmethods + ".getMethod(val_name);\n\n");

					}

					// category='BMRBx:heteronucl_t1_list'
					if (class_name.equalsIgnoreCase("HeteronuclT1List")) {

						// item='t1_val_units'
						if (attr_name_lower.equals("t1_val_units")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_heteronuclt1list + ".getT1ValUnits(val_name);\n\n");

						}

						// item='t1_coherence_type'
						if (attr_name_lower.equals("t1_coherence_type")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_heteronuclt1list + ".getT1CoherenceType(val_name, entry_id);\n\n");

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\tval_name = \"na\";\n\n");

						}

					}

					// category='BMRBx:heteronucl_t2_list'
					if (class_name.equalsIgnoreCase("HeteronuclT2List")) {

						// item='t2_val_units'
						if (attr_name_lower.equals("t2_val_units")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_heteronuclt2list + ".getT2ValUnits(val_name);\n\n");

						}

						// item='t2_coherence_type'
						if (attr_name_lower.equals("t2_coherence_type")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_heteronuclt2list + ".getT2CoherenceType(val_name, entry_id);\n\n");

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\tval_name = \"na\";\n\n");

						}

						// item='rex_units'
						if (attr_name_lower.equals("rex_units")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_heteronuclt2list + ".getRexUnits(val_name);\n\n");

						}

						// item='temp_calibration_method'
						if (attr_name_lower.equals("temp_calibration_method")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_heteronuclt2list + ".getTempCalibrationMethod(val_name);\n\n");

						}

						// item='temp_control_method'
						if (attr_name_lower.equals("temp_control_method")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_heteronuclt2list + ".getTempControlMethod(val_name);\n\n");

						}

					}

					// category='BMRBx:heteronucl_t1rho_list'
					if (class_name.equalsIgnoreCase("HeteronuclT1RhoList")) {

						// item='rex_units'
						if (attr_name_lower.equals("rex_units")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_heteronuclt1rholist + ".getRexUnits(val_name);\n\n");

						}

						// item='t1rho_coherence_type'
						if (attr_name_lower.equals("t1rho_coherence_type") && class_name.equalsIgnoreCase("HeteronuclT1RhoList")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_heteronuclt1rholist + ".getT1RhoCoherenceType(val_name);\n\n");

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\tval_name = \"na\";\n\n");

						}

					}

					// item='heteronuclear_noe_val_type', category='BMRBx:heteronucl_noe_list'
					if (attr_name_lower.equals("heteronuclear_noe_val_type") && class_name.equalsIgnoreCase("HeteronuclNOEList")) {

						filew.write("\t\tif (val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_heteronuclnoelist + ".getHeteronuclearNOEValType(val_name);\n\n");

						filew.write("\t\tif (" + empty_check("val_name") + ")\n");
						filew.write("\t\t\tval_name = \"na\";\n\n");

					}

					// item='homonuclear_noe_val_type', category='BMRBx:homonucl_noe_list'
					if (attr_name_lower.equals("homonuclear_noe_val_type") && class_name.equalsIgnoreCase("HomonuclNOEList")) {

						filew.write("\t\tif (val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_homonuclnoelist + ".getHomonuclearNOEValType(val_name);\n\n");

						filew.write("\t\tif (" + empty_check("val_name") + ")\n");
						filew.write("\t\t\tval_name = \"na\";\n\n");

					}

					// item='val_units', category='BMRBx:h_exch_rate_list'
					if (attr_name_lower.equals("val_units") && class_name.equalsIgnoreCase("HExchRateList")) {

						filew.write("\t\tif (val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_hexchratelist + ".getValUnits(val_name);\n\n");

					}

					// item='constraint_type', category='BMRBx:gen_dist_constraint_list'
					if (attr_name_lower.equals("constraint_type") && class_name.equalsIgnoreCase("GenDistConstraintList")) {

						filew.write("\t\tif (val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_gendistconstraintlist + ".getConstraintType(val_name);\n\n");

					}

					// item='type', category='BMRBx:nmr_experiment_file'
					if (attr_name_lower.equals("type") && class_name.equalsIgnoreCase("NMRExperimentFile")) {

						filew.write("\t\tif (val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_nmrexperimentfile + ".getType(val_name);\n\n");

						filew.write("\t\tif (" + empty_check("val_name") + ")\n");
						filew.write("\t\t\t\tval_name = \"na\";\n\n");

					}

					// item='type', category='BMRBx:nmr_probe'
					if (attr_name_lower.equals("type") && class_name.equalsIgnoreCase("NMRProbe")) {

						filew.write("\t\tif (val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_nmrprobe + ".getType(val_name);\n\n");

					}

					// category='BMRBx:nmr_spec_expt'
					if (class_name.equalsIgnoreCase("NMRSpecExpt")) {

						// item='type'
						if (attr_name_lower.equals("type")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_nmrspecexpt + ".getType(val_name);\n\n");

						}

						// item='nmr_tube_type'
						if (attr_name_lower.equals("nmr_tube_type")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_nmrspecexpt + ".getNMRTubeType(val_name);\n\n");

						}

					}

					// item='manufacturer'
					if (attr_name_lower.equals("manufacturer")) {

						// category='BMRBx:nmr_spectrometer'
						if (class_name.equalsIgnoreCase("NMRSpectrometer")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_nmrspectrometer + ".getManufacturer(val_name);\n\n");

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\tval_name = \"na\";\n\n");

						}

						// category='BMRBx:nmr_spectrometer_probe'
						if (class_name.equalsIgnoreCase("NMRSpectrometerProbe")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_nmrspectrometerprobe + ".getManufacturer(val_name);\n\n");

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\tval_name = \"na\";\n\n");

						}

						// category='BMRBx:nmr_spectrometer_view'
						if (class_name.equalsIgnoreCase("NMRSpectrometerView")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_nmrspectrometerview + ".getManufacturer(val_name);\n\n");

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\tval_name = \"na\";\n\n");

						}

					}

					// item='field_strength'
					if (attr_name_lower.equals("field_strength")) {

						// category='BMRBx:nmr_spectrometer'
						if (class_name.equalsIgnoreCase("NMRSpectrometer")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_nmrspectrometer + ".getFieldStrength(val_name);\n\n");

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\tval_name = \"na\";\n\n");

						}

						// category='BMRBx:nmr_spectrometer_view'
						if (class_name.equalsIgnoreCase("NMRSpectrometerView")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_nmrspectrometerview + ".getFieldStrength(val_name);\n\n");

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\tval_name = \"na\";\n\n");

						}

					}

					// category='BMRBx:order_parameter_list'
					if (class_name.equalsIgnoreCase("OrderParameterList")) {

						// item='model_fit'
						if (attr_name_lower.equals("model_fit")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_orderparameterlist + ".getModelFit(val_name);\n\n");

						}

						// item='tau_e_val_units'
						if (attr_name_lower.equals("tau_e_val_units")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_orderparameterlist + ".getTauEValUnits(val_name);\n\n");

						}

						// item='rex_val_units'
						if (attr_name_lower.equals("rex_val_units")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_orderparameterlist + ".getRexValUnits(val_name);\n\n");

						}

					}

					// item='measurement_method', category='BMRBx:peak_general_char'
					if (attr_name_lower.equals("measurement_method") && class_name.equalsIgnoreCase("PeakGeneralChar")) {

						filew.write("\t\tif (val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_peakgeneralchar + ".getMeasurementMethod(val_name);\n\n");

					}

					// item='observed_nmr_param', category='BMRBx:ph_param_list'
					if (attr_name_lower.equals("observed_nmr_param") && class_name.equalsIgnoreCase("PHParamList")) {

						filew.write("\t\tif (" + empty_check("val_name") + ")\n");
						filew.write("\t\t\tval_name = \"na\";\n\n");

					}

					// item='expt_observed_param', category='BMRBx:ph_titration_list'
					if (attr_name_lower.equals("expt_observed_param") && class_name.equalsIgnoreCase("PHTitrationList")) {

						filew.write("\t\tif (val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_phtitrationlist + ".getExptObservedParam(val_name);\n\n");

						filew.write("\t\tif (" + empty_check("val_name") + ")\n");
						filew.write("\t\t\tval_name = \"na\";\n\n");

					}

					// item='database_name', category='BMRBx:related_entries'
					if (attr_name_lower.equals("database_name") && class_name.equalsIgnoreCase("RelatedEntries")) {

						filew.write("\t\tif (val_name != null || accession_code != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_relatedentries + ".getDatabaseName(val_name, conn_bmrb, accession_code);\n\n");

					}

					// item='type', category='BMRBx:release'
					if (attr_name_lower.equals("type") && class_name.equalsIgnoreCase("Release")) {

						filew.write("\t\tif (val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_release + ".getType(val_name);\n\n");

						filew.write("\t\tif (" + empty_check("val_name") + ")\n");
						filew.write("\t\t\tval_name = \"na\";\n\n");

					}

					// item='refine_method', category='BMRBx:rep_conf_refinement'
					if (attr_name_lower.equals("refine_method") && class_name.equalsIgnoreCase("RepConfRefinement")) {

						filew.write("\t\tif (val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_repconfrefinement + ".getRefineMethod(val_name);\n\n");

						filew.write("\t\tif (" + empty_check("val_name") + ")\n");
						filew.write("\t\t\tval_name = \"na\";\n\n");

					}

					// item='type', category='BMRBx:sample'
					if (attr_name_lower.equals("type") && class_name.equalsIgnoreCase("Sample")) {

						filew.write("\t\tif (val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_sample + ".getType(val_name);\n\n");

						filew.write("\t\tif (val_name != null && val_name.equals(\"gel\"))\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_sample + ".getGelType(val_name, conn_bmrb, entry_id);\n\n");

					}

					// item='concentration_val_units', category='BMRBx:sample_component'
					if (attr_name_lower.equals("concentration_val_units") && class_name.equalsIgnoreCase("SampleComponent")) {

						filew.write("\t\tif (val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_samplecomponent + ".getConcentrationValUnits(val_name);\n\n");

					}

					// category='BMRBx:sample_condition_variable'
					if (class_name.equalsIgnoreCase("SampleConditionVariable")) {

						// item='type'
						if (attr_name_lower.equals("type")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_sampleconditionvariable + ".getType(val_name);\n\n");

						}

						// item='val_units'
						if (attr_name_lower.equals("val_units")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_sampleconditionvariable + ".getValUnits(val_name);\n\n");

						}

					}

					// category='BMRBx:sg_project'
					if (class_name.equalsIgnoreCase("SGProject")) {

						// item='full_name_of_center'
						if (attr_name_lower.equals("full_name_of_center")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_sgproject + ".getFullNameOfCenter(val_name);\n\n");

						}

						// item='initial_of_center'
						if (attr_name_lower.equals("initial_of_center")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_sgproject + ".getInitialOfCenter(val_name);\n\n");

						}

						// item='project_name'
						if (attr_name_lower.equals("project_name")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_sgproject + ".getProjectName(val_name);\n\n");

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\tval_name = \"not applicable\";\n\n");

						}

					}

					// item='measurement_method', category='BMRBx:spectral_transition_general_char'
					if (attr_name_lower.equals("measurement_method") && class_name.equalsIgnoreCase("SpectralTransitionGeneralChar")) {

						filew.write("\t\tif (val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_spectraltransitiongeneralchar + ".getMeasurementMethod(val_name);\n\n");

					}

					// item='edge_designation', category='BMRBx:struct_anno_char'
					if (attr_name_lower.equals("edge_designation") && class_name.equalsIgnoreCase("StructAnnoChar")) {

						filew.write("\t\tif (val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_structannochar + ".getEdgeDesignation(val_name);\n\n");

					}

					// item='type', category='BMRBx:study'
					if (attr_name_lower.equals("type") && class_name.equalsIgnoreCase("Study")) {

						filew.write("\t\tif (val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_study + ".getType(val_name);\n\n");

					}

					// category='BMRBx:systematic_chem_shift_offset'
					if (class_name.equalsIgnoreCase("SystematicChemShiftOffset")) {

						// item='type'
						if (attr_name_lower.equals("type")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_systematicchemshiftoffset + ".getType(val_name);\n\n");

						}

						// item='atom_type'
						if (attr_name_lower.equals("atom_type")) {

							filew.write("\t\tif (val_name != null)\n");
							filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_systematicchemshiftoffset + ".getAtomType(val_name);\n\n");

							filew.write("\t\tif (" + empty_check("val_name") + ")\n");
							filew.write("\t\t\tval_name = \"na\";\n\n");

						}

					}

					write_remed_log_code(filew);

					filew.write("\t\t}\n\n");

					filew.write("\t\tif (" + empty_check("val_name") + " || val_name.equals(\"null\"))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					filew.write("\t\tClass<?> _class = list.getClass();\n\n");

					filew.write("\t\ttry {\n");
					filew.write("\t\t\tMethod method = _class.getMethod(method_name, new Class[]{ " + enum_class_name + ".class });\n");
					filew.write("\t\t\t" + (class_name.equalsIgnoreCase("Entry") ? "" : ";//") + "Method nil_method = null;\n\n");

					filew.write("\t\t\t" + (class_name.equalsIgnoreCase("Entry") ? "" : ";//") + "if (nil_method_name != null && !nil_method_name.isEmpty())\n");
					filew.write("\t\t\t\t" + (class_name.equalsIgnoreCase("Entry") ? "" : ";//") + "nil_method = _class.getMethod(nil_method_name);\n\n");

					filew.write("\t\t\ttry {\n");
					filew.write("\t\t\t\tif (nil)\n");
					filew.write("\t\t\t\t\t" + (class_name.equalsIgnoreCase("Entry") ? "" : ";//") + "nil_method.invoke(list);\n");
					filew.write("\t\t\t\telse {\n");
					filew.write("\t\t\t\t\t" + enum_class_name + " _enum = " + enum_class_name + ".forString(val_name);\n");
					filew.write("\t\t\t\t\tif (_enum != null)\n");
					filew.write("\t\t\t\t\t\tmethod.invoke(list, _enum);\n");
					filew.write("\t\t\t\t\telse {\n");

					filew.write("\t\t\t\t\t\tSystem.err.println(\"class_name:" + class_name + " method_name:\" + method_name + \" val_name:\" + val_name);\n");
					filew.write("\t\t\t\t\t\tfor (int i = 1; i <= " + enum_class_name + ".table.lastInt(); i++)\n");
					filew.write("\t\t\t\t\t\t\tSystem.err.println(\" enum:\" + " + enum_class_name + ".forInt(i));\n");

					filew.write("\t\t\t\t\t\ttry {\n");
					filew.write("\t\t\t\t\t\t\terrw.write(\"class_name:" + class_name + " method_name:\" + method_name + \" val_name:\" + val_name + \"\\n\");\n");
					filew.write("\t\t\t\t\t\t\tfor (int i = 1; i <= " + enum_class_name + ".table.lastInt(); i++)\n");
					filew.write("\t\t\t\t\t\t\t\terrw.write(\" enum:\" + " + enum_class_name + ".forInt(i) + \"\\n\");\n");
					filew.write("\t\t\t\t\t\t} catch (IOException e) {\n");
					filew.write("\t\t\t\t\t\t\te.printStackTrace();\n");
					filew.write("\t\t\t\t\t\t}\n");

					filew.write("\t\t\t\t\t\t" + (class_name.equalsIgnoreCase("Entry") ? "" : "//") + "if (nil_method != null)\n");
					filew.write("\t\t\t\t\t\t\t" + (class_name.equalsIgnoreCase("Entry") ? "" : "//") + "nil_method.invoke(list);\n");
					filew.write("\t\t\t\t\t\t" + (class_name.equalsIgnoreCase("Entry") ? "" : "//") + "else\n");
					filew.write("\t\t\t\t\t\t\treturn false;\n");
					filew.write("\t\t\t\t\t}\n");
					filew.write("\t\t\t\t}\n");
					filew.write("\t\t\t} catch (IllegalAccessException e) {\n");
					filew.write("\t\t\t\te.printStackTrace();\n");
					filew.write("\t\t\t} catch (IllegalArgumentException e) {\n");
					filew.write("\t\t\t\te.printStackTrace();\n");
					filew.write("\t\t\t} catch (InvocationTargetException e) {\n");
					filew.write("\t\t\t\te.printStackTrace();\n\t\t\t}\n\n");

					filew.write("\t\t} catch (NoSuchMethodException e) {\n");
					filew.write("\t\t\te.printStackTrace();\n");
					filew.write("\t\t\tSystem.exit(1);\n");
					filew.write("\t\t} catch (SecurityException e) {\n");
					filew.write("\t\t\te.printStackTrace();\n\t\t}\n\n");

					filew.write("\t\treturn true;\n\t}\n");

					//			}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	private static void write_enum_integer_util(FileWriter filew, Node node, String attr_name, List<String> column_list, String file_prefix) {

		String attr_name_lower = attr_name.toLowerCase();

		for (String column_name : column_list) {

			if (attr_name.equalsIgnoreCase(column_name)) {

				try {

					// item='ambiguity_code', category='BMRBx:atom_chem_shift'
					if (attr_name_lower.equals("ambiguity_code") && class_name.equalsIgnoreCase("AtomChemShift"))
						filew.write("\n\tprivate static boolean set_enum_" + attr_name_lower +"(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, String entry_id, FileWriter logw, FileWriter errw) {\n\n");
					else
						filew.write("\n\tprivate static boolean set_enum_" + attr_name_lower +"(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					// item='ambiguity_code', category='BMRBx:atom_chem_shift'
					if (attr_name_lower.equals("ambiguity_code") && class_name.equalsIgnoreCase("AtomChemShift")) {

						filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_atomchemshift + ".getAmbiguityCode(val_name, entry_id);\n\n");

					}

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || !val_name.matches(\"^[-+]?[0-9]+$\"))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					write_invoke_bigint_method(filew);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void write_enum_decimal_util(FileWriter filew, Node node, String attr_name, List<String> column_list, String file_prefix) {

		String attr_name_lower = attr_name.toLowerCase();

		for (String column_name : column_list) {

			if (attr_name.equalsIgnoreCase(column_name)) {

				try {

					filew.write("\n\tprivate static boolean set_enum_" + attr_name_lower +"(" + abs_class_name + " list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {\n\n");

					filew.write("\t\tboolean nil = false;\n\n");

					filew.write("\t\tString _val_name = val_name;\n\n");

					// item='spectrometer_frequency_1h', category='BMRBx:coupling_constatn_list'
					if (attr_name_lower.equals("spectrometer_frequency_1h") && class_name.equalsIgnoreCase("CouplingConstantList")) {

						filew.write("\t\tif (" + file_prefix + "_" + BMSxTool_DOM.util_main + ".remediate_xml && val_name != null)\n");
						filew.write("\t\t\tval_name = " + file_prefix + "_" + BMSxTool_DOM.util_couplingconstantlist + ".getSpectrometerFrequency1H(val_name);\n\n");

					}

					write_remed_log_code(filew);

					filew.write("\t\tif (" + empty_check("val_name") + " || (!val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)?$\") && !val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)([eE][-+]?[0-9]+)?$\")))\n");
					filew.write("\t\t\tnil = true;\n\n");

					write_missing_log_code(filew);

					filew.write("\t\tClass<?> _class = list.getClass();\n\n");

					filew.write("\t\ttry {\n");
					filew.write("\t\t\tMethod method = _class.getMethod(method_name, new Class[]{ BigDecimal.class });\n");
					filew.write("\t\t\t;//Method nil_method = null;\n\n");

					filew.write("\t\t\t;//if (nil_method_name != null && !nil_method_name.isEmpty())\n");
					filew.write("\t\t\t\t;//nil_method = _class.getMethod(nil_method_name);\n\n");

					filew.write("\t\t\ttry {\n");
					filew.write("\t\t\t\tif (nil)\n");
					filew.write("\t\t\t\t\t;//nil_method.invoke(list);\n");
					filew.write("\t\t\t\telse {\n");
					filew.write("\t\t\t\t\tString[] num_val = val_name.split(\"[Mm][Hh][Zz]$\");\n");
					filew.write("\t\t\t\t\tBigDecimal _enum = BigDecimal.valueOf(Double.valueOf(num_val[0]));\n");
					filew.write("\t\t\t\t\tif (_enum != null)\n");
					filew.write("\t\t\t\t\t\tmethod.invoke(list, _enum);\n");
					filew.write("\t\t\t\t\telse {\n");
					filew.write("\t\t\t\t\t\t//if (nil_method != null)\n");
					filew.write("\t\t\t\t\t\t\t//nil_method.invoke(list);\n");
					filew.write("\t\t\t\t\t\t//else\n");
					filew.write("\t\t\t\t\t\t\treturn false;\n");
					filew.write("\t\t\t\t\t}\n");
					filew.write("\t\t\t\t}\n");
					filew.write("\t\t\t} catch (IllegalAccessException e) {\n");
					filew.write("\t\t\t\te.printStackTrace();\n");
					filew.write("\t\t\t} catch (IllegalArgumentException e) {\n");
					filew.write("\t\t\t\te.printStackTrace();\n");
					filew.write("\t\t\t} catch (InvocationTargetException e) {\n");
					filew.write("\t\t\t\te.printStackTrace();\n\t\t\t}\n\n");

					filew.write("\t\t} catch (NoSuchMethodException e) {\n");
					filew.write("\t\t\te.printStackTrace();\n");
					filew.write("\t\t\tSystem.exit(1);\n");
					filew.write("\t\t} catch (SecurityException e) {\n");
					filew.write("\t\t\te.printStackTrace();\n\t\t}\n\n");

					filew.write("\t\treturn true;\n\t}\n");

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void write_remed_log_code(FileWriter filew) throws IOException {

		filew.write("\t\tif (!(" + empty_check("val_name") + ") && (" + empty_check("_val_name") + " || !val_name.equals(_val_name))) {\n\n");

		filew.write("\t\t\ttry {\n");
		filew.write("\t\t\t\tlogw.write(\"item='\" + method_name.substring(3) + \"', category='" + class_name + "', value='\" + _val_name + \"' -> '\" + val_name + \"'\\n\");\n");
		filew.write("\t\t\t} catch (IOException e) {\n");
		filew.write("\t\t\t\te.printStackTrace();\n");
		filew.write("\t\t\t}\n\n");

		filew.write("\t\t}\n\n");

	}

	private static void write_remed_bmrb_code(FileWriter filew, String method_name) throws IOException {

		filew.write("\t\t\t\t\tif (!(" + empty_check("bmrb_code") + ") && (" + empty_check("_bmrb_code") + " || !bmrb_code.equals(_bmrb_code)))\n");
		filew.write("\t\t\t\t\t\tlogw.write(\"item='" + method_name.substring(3) + "', category='" + class_name + "', value='\" + _bmrb_code + \"' -> '\" + bmrb_code + \"'\\n\");\n\n");

	}

	private static void write_remed_pdb_code(FileWriter filew, String method_name) throws IOException {

		filew.write("\t\t\t\t\tif (!(" + empty_check("pdb_code") + ") && (" + empty_check("_pdb_code") + " || !pdb_code.equals(_pdb_code)))\n");
		filew.write("\t\t\t\t\t\tlogw.write(\"item='" + method_name.substring(3) + "', category='" + class_name + "', value='\" + _pdb_code + \"' -> '\" + pdb_code + \"'\\n\");\n\n");

	}

	private static void write_remed_pubmed_id(FileWriter filew, String method_name) throws IOException {

		filew.write("\t\t\t\t\tif (!(" + empty_check("pubmed_id") + ") && (" + empty_check("_pubmed_id") + " || !pubmed_id.equals(_pubmed_id)))\n");
		filew.write("\t\t\t\t\t\tlogw.write(\"item='" + method_name.substring(3) + "', category='" + class_name + "', value='\" + _pubmed_id + \"' -> '\" + pubmed_id + \"'\\n\");\n\n");

	}

	private static void write_remed_doi(FileWriter filew, String method_name) throws IOException {

		filew.write("\t\t\t\t\tif (" + empty_check("doi") + " && (" + empty_check("_doi") + " || !doi.equals(_doi)))\n");
		filew.write("\t\t\t\t\t\tlogw.write(\"item='" + method_name.substring(3) + "', category='" + class_name + "', value='\" + _doi + \"' -> '\" + doi + \"'\\n\");\n\n");

	}

	private static void write_remed_tax_code(FileWriter filew, String method_name) throws IOException {

		filew.write("\t\t\t\t\tif (!(" + empty_check("ncbi_taxonomy_id") + ") && (" + empty_check("_ncbi_taxonomy_id") + " || !ncbi_taxonomy_id.equals(_ncbi_taxonomy_id)))\n");
		filew.write("\t\t\t\t\t\tlogw.write(\"item='" + method_name.substring(3) + "', category='" + class_name + "', value='\" + _ncbi_taxonomy_id + \"' -> '\" + ncbi_taxonomy_id + \"'\\n\");\n\n");

	}

	private static void write_remed_atom_type(FileWriter filew, String method_name) throws IOException {

		filew.write("\t\t\t\t\tif (!(" + empty_check("atom_type") + ") && (" + empty_check("_atom_type") + " || !atom_type.equals(_atom_type)))\n");
		filew.write("\t\t\t\t\t\tlogw.write(\"item='" + method_name.substring(3) + "', category='" + class_name + "', value='\" + _atom_type + \"' -> '\" + atom_type + \"'\\n\");\n\n");

	}

	private static void write_excld_log_code(FileWriter filew, String message) throws IOException {

		filew.write("\n\t\t\t\t\t\ttry {\n");
		filew.write("\t\t\t\t\t\t\tlogw.write(\"category='" + class_name + "', " + message + "\\n\");\n");
		filew.write("\t\t\t\t\t\t} catch (IOException e) {\n");
		filew.write("\t\t\t\t\t\t\te.printStackTrace();\n");
		filew.write("\t\t\t\t\t\t}\n\n");

	}

	private static void write_excld_log_code(FileWriter filew, String method_name, String column_name) throws IOException {

		filew.write("\n\t\t\t\t\t\ttry {\n");
		filew.write("\t\t\t\t\t\t\tlogw.write(\"item='" + method_name.substring(3) + "', category='" + class_name + "', value='\" + rset.getString(\"" + column_name + "\") + \"' was excluded.\\n\");\n");
		filew.write("\t\t\t\t\t\t} catch (IOException e) {\n");
		filew.write("\t\t\t\t\t\t\te.printStackTrace();\n");
		filew.write("\t\t\t\t\t\t}\n\n");

	}

	private static void write_missing_log_code(FileWriter filew) throws IOException {

		filew.write("\t\tif (nil && (nil_method_name == null || nil_method_name.isEmpty())) {\n\n");

		filew.write("\t\t\tif (required) {\n\n");

		filew.write("\t\t\t\ttry {\n");
		filew.write("\t\t\t\t\tlogw.write(\"item='\" + method_name.substring(3) + \"', category='" + class_name + "', value='\" + _val_name + \"' was empty, but not nillable.\\n\");\n");
		filew.write("\t\t\t\t} catch (IOException e) {\n");
		filew.write("\t\t\t\t\te.printStackTrace();\n");
		filew.write("\t\t\t\t}\n\n");

		filew.write("\t\t\t\treturn false;\n");
		filew.write("\t\t\t}\n\n");

		filew.write("\t\t\treturn true;\n");
		filew.write("\t\t}\n\n");

	}

	private static void write_missing_date_log_code(FileWriter filew) throws IOException {

		filew.write("\t\tif (nil && (nil_method_name == null || nil_method_name.isEmpty())) {\n\n");

		filew.write("\t\t\tif (required) {\n\n");

		filew.write("\t\t\t\ttry {\n");
		filew.write("\t\t\t\t\tlogw.write(\"item='\" + method_name.substring(3) + \"', category='" + class_name + "', value='\" + date + \"' was empty, but not nillable.\\n\");\n");
		filew.write("\t\t\t\t} catch (IOException e) {\n");
		filew.write("\t\t\t\t\te.printStackTrace();\n");
		filew.write("\t\t\t\t}\n\n");

		filew.write("\t\t\t\treturn false;\n");
		filew.write("\t\t\t}\n\n");

		filew.write("\t\t\treturn true;\n");
		filew.write("\t\t}\n\n");

	}

	private static void write_invoke_string_method(FileWriter filew) throws IOException {

		filew.write("\t\tClass<?> _class = list.getClass();\n\n");

		filew.write("\t\ttry {\n");
		filew.write("\t\t\tMethod method = _class.getMethod(method_name, new Class[]{ String.class });\n");
		filew.write("\t\t\tMethod nil_method = null;\n\n");

		filew.write("\t\t\tif (nil_method_name != null && !nil_method_name.isEmpty())\n");
		filew.write("\t\t\t\tnil_method = _class.getMethod(nil_method_name);\n\n");

		filew.write("\t\t\ttry {\n");
		filew.write("\t\t\t\tif (nil)\n");
		filew.write("\t\t\t\t\tnil_method.invoke(list);\n");
		filew.write("\t\t\t\telse\n");
		filew.write("\t\t\t\t\tmethod.invoke(list, val_name);\n");
		filew.write("\t\t\t} catch (IllegalAccessException e) {\n");
		filew.write("\t\t\t\te.printStackTrace();\n");
		filew.write("\t\t\t} catch (IllegalArgumentException e) {\n");
		filew.write("\t\t\t\te.printStackTrace();\n");
		filew.write("\t\t\t} catch (InvocationTargetException e) {\n");
		filew.write("\t\t\t\te.printStackTrace();\n\t\t\t}\n\n");

		filew.write("\t\t} catch (NoSuchMethodException e) {\n");
		filew.write("\t\t\te.printStackTrace();\n");
		filew.write("\t\t\tSystem.exit(1);\n");
		filew.write("\t\t} catch (SecurityException e) {\n");
		filew.write("\t\t\te.printStackTrace();\n\t\t}\n\n");

		filew.write("\t\treturn true;\n\t}\n");

	}

	private static void write_invoke_bigint_method(FileWriter filew) throws IOException {

		filew.write("\t\tClass<?> _class = list.getClass();\n\n");

		filew.write("\t\ttry {\n");
		filew.write("\t\t\tMethod method = _class.getMethod(method_name, new Class[]{ BigInteger.class });\n");
		filew.write("\t\t\tMethod nil_method = null;\n\n");

		filew.write("\t\t\tif (nil_method_name != null && !nil_method_name.isEmpty())\n");
		filew.write("\t\t\t\tnil_method = _class.getMethod(nil_method_name);\n\n");

		filew.write("\t\t\ttry {\n");
		filew.write("\t\t\t\tif (nil)\n");
		filew.write("\t\t\t\t\tnil_method.invoke(list);\n");
		filew.write("\t\t\t\telse\n");
		filew.write("\t\t\t\t\tmethod.invoke(list, new BigInteger(val_name));\n");
		filew.write("\t\t\t} catch (IllegalAccessException e) {\n");
		filew.write("\t\t\t\te.printStackTrace();\n");
		filew.write("\t\t\t} catch (IllegalArgumentException e) {\n");
		filew.write("\t\t\t\te.printStackTrace();\n");
		filew.write("\t\t\t} catch (InvocationTargetException e) {\n");
		filew.write("\t\t\t\te.printStackTrace();\n\t\t\t}\n\n");

		filew.write("\t\t} catch (NoSuchMethodException e) {\n");
		filew.write("\t\t\te.printStackTrace();\n");
		filew.write("\t\t\tSystem.exit(1);\n");
		filew.write("\t\t} catch (SecurityException e) {\n");
		filew.write("\t\t\te.printStackTrace();\n\t\t}\n\n");

		filew.write("\t\treturn true;\n\t}\n");

	}

	private static void write_invoke_bigdec_method(FileWriter filew) throws IOException {

		filew.write("\t\tClass<?> _class = list.getClass();\n\n");

		filew.write("\t\ttry {\n");
		filew.write("\t\t\tMethod method = _class.getMethod(method_name, new Class[]{ BigDecimal.class });\n");
		filew.write("\t\t\tMethod nil_method = null;\n\n");

		filew.write("\t\t\tif (nil_method_name != null && !nil_method_name.isEmpty())\n");
		filew.write("\t\t\t\tnil_method = _class.getMethod(nil_method_name);\n\n");

		filew.write("\t\t\ttry {\n");
		filew.write("\t\t\t\tif (nil)\n");
		filew.write("\t\t\t\t\tnil_method.invoke(list);\n");
		filew.write("\t\t\t\telse\n");
		filew.write("\t\t\t\t\tmethod.invoke(list, new BigDecimal(val_name, MathContext.DECIMAL32));\n");
		filew.write("\t\t\t} catch (IllegalAccessException e) {\n");
		filew.write("\t\t\t\te.printStackTrace();\n");
		filew.write("\t\t\t} catch (IllegalArgumentException e) {\n");
		filew.write("\t\t\t\te.printStackTrace();\n");
		filew.write("\t\t\t} catch (InvocationTargetException e) {\n");
		filew.write("\t\t\t\te.printStackTrace();\n\t\t\t}\n\n");

		filew.write("\t\t} catch (NoSuchMethodException e) {\n");
		filew.write("\t\t\te.printStackTrace();\n");
		filew.write("\t\t\tSystem.exit(1);\n");
		filew.write("\t\t} catch (SecurityException e) {\n");
		filew.write("\t\t\te.printStackTrace();\n\t\t}\n\n");

		filew.write("\t\treturn true;\n\t}\n");

	}

	private static String empty_check(String val_name) {
		return val_name + " == null || " + val_name + ".isEmpty() || " + val_name + ".equals(\".\") || " + val_name + ".equals(\"?\")";
	}
}
