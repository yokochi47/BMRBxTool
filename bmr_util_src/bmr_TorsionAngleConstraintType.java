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
import org.pdbj.bmrbpub.schema.mmcifNmrStar.TorsionAngleConstraintType.*;

public class bmr_TorsionAngleConstraintType {

	private static final String table_name = "Torsion_angle_constraint";

	public static int write_xml(Connection conn_bmrb, Connection conn_tax, Connection conn_le, String entry_id, XmlOptions xml_opt, BufferedWriter buffw, FileWriter logw, FileWriter errw) {

		if (bmr_Util_Main.noatom)
			return 0;

		TorsionAngleConstraintType body = TorsionAngleConstraintType.Factory.newInstance();
		TorsionAngleConstraint[] list = new TorsionAngleConstraint[1];

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

				String query2 = new String("select * from \"" + table_name + "\" where \"Entry_ID\"='" + entry_id + "' order by (0 || \"Torsion_angle_constraint_list_ID\")::decimal,(0 || \"ID\")::decimal");

				rset.close();

				rset = state.executeQuery(query2);

				int cols = rset.getMetaData().getColumnCount();
				int lines = 0;

				buffw.write("  <BMRBx:torsion_angle_constraintCategory>\n");

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
							logw.write("category='TorsionAngleConstraint', duplicated content of the category was excluded.\n");
						} catch (IOException e) {
							e.printStackTrace();
						}

						continue;
					}

					list[0] = TorsionAngleConstraint.Factory.newInstance();

					if (!set_decimal(list[0], "setAngleLowerBoundVal", "", true, rset.getString("Angle_lower_bound_val"), logw))
						continue;
					if (!set_decimal(list[0], "setAngleLowerLinearLimit", "setNilAngleLowerLinearLimit", false, rset.getString("Angle_lower_linear_limit"), logw))
						continue;
					if (!set_decimal(list[0], "setAngleTargetVal", "setNilAngleTargetVal", false, rset.getString("Angle_target_val"), logw))
						continue;
					if (!set_decimal(list[0], "setAngleTargetValErr", "setNilAngleTargetValErr", false, rset.getString("Angle_target_val_err"), logw))
						continue;
					if (!set_decimal(list[0], "setAngleUpperBoundVal", "", true, rset.getString("Angle_upper_bound_val"), logw))
						continue;
					if (!set_decimal(list[0], "setAngleUpperLinearLimit", "setNilAngleUpperLinearLimit", false, rset.getString("Angle_upper_linear_limit"), logw))
						continue;
					if (!set_integer(list[0], "setAssemblyAtomId1", "", false, rset.getString("Assembly_atom_ID_1"), logw))
						continue;
					if (!set_integer(list[0], "setAssemblyAtomId2", "", false, rset.getString("Assembly_atom_ID_2"), logw))
						continue;
					if (!set_integer(list[0], "setAssemblyAtomId3", "", false, rset.getString("Assembly_atom_ID_3"), logw))
						continue;
					if (!set_integer(list[0], "setAssemblyAtomId4", "", false, rset.getString("Assembly_atom_ID_4"), logw))
						continue;
					if (!set_string(list[0], "setAtomId1", "", true, rset.getString("Atom_ID_1"), logw))
						continue;
					if (!set_string(list[0], "setAtomId2", "", true, rset.getString("Atom_ID_2"), logw))
						continue;
					if (!set_string(list[0], "setAtomId3", "", true, rset.getString("Atom_ID_3"), logw))
						continue;
					if (!set_string(list[0], "setAtomId4", "", true, rset.getString("Atom_ID_4"), logw))
						continue;
					if (!set_string(list[0], "setAtomType1", "", true, rset.getString("Atom_type_1"), logw))
						continue;
					if (!set_string(list[0], "setAtomType2", "", true, rset.getString("Atom_type_2"), logw))
						continue;
					if (!set_string(list[0], "setAtomType3", "", true, rset.getString("Atom_type_3"), logw))
						continue;
					if (!set_string(list[0], "setAtomType4", "", true, rset.getString("Atom_type_4"), logw))
						continue;
					if (!set_string(list[0], "setAuthAltId1", "setNilAuthAltId1", false, rset.getString("Auth_alt_ID_1"), logw))
						continue;
					if (!set_string(list[0], "setAuthAltId2", "setNilAuthAltId2", false, rset.getString("Auth_alt_ID_2"), logw))
						continue;
					if (!set_string(list[0], "setAuthAltId3", "setNilAuthAltId3", false, rset.getString("Auth_alt_ID_3"), logw))
						continue;
					if (!set_string(list[0], "setAuthAltId4", "setNilAuthAltId4", false, rset.getString("Auth_alt_ID_4"), logw))
						continue;
					if (!set_string(list[0], "setAuthAsymId1", "setNilAuthAsymId1", false, rset.getString("Auth_asym_ID_1"), logw))
						continue;
					if (!set_string(list[0], "setAuthAsymId2", "setNilAuthAsymId2", false, rset.getString("Auth_asym_ID_2"), logw))
						continue;
					if (!set_string(list[0], "setAuthAsymId3", "setNilAuthAsymId3", false, rset.getString("Auth_asym_ID_3"), logw))
						continue;
					if (!set_string(list[0], "setAuthAsymId4", "setNilAuthAsymId4", false, rset.getString("Auth_asym_ID_4"), logw))
						continue;
					if (!set_string(list[0], "setAuthAtomId1", "setNilAuthAtomId1", false, rset.getString("Auth_atom_ID_1"), logw))
						continue;
					if (!set_string(list[0], "setAuthAtomId2", "setNilAuthAtomId2", false, rset.getString("Auth_atom_ID_2"), logw))
						continue;
					if (!set_string(list[0], "setAuthAtomId3", "setNilAuthAtomId3", false, rset.getString("Auth_atom_ID_3"), logw))
						continue;
					if (!set_string(list[0], "setAuthAtomId4", "setNilAuthAtomId4", false, rset.getString("Auth_atom_ID_4"), logw))
						continue;
					if (!set_string(list[0], "setAuthAtomName1", "setNilAuthAtomName1", false, rset.getString("Auth_atom_name_1"), logw))
						continue;
					if (!set_string(list[0], "setAuthAtomName2", "setNilAuthAtomName2", false, rset.getString("Auth_atom_name_2"), logw))
						continue;
					if (!set_string(list[0], "setAuthAtomName3", "setNilAuthAtomName3", false, rset.getString("Auth_atom_name_3"), logw))
						continue;
					if (!set_string(list[0], "setAuthAtomName4", "setNilAuthAtomName4", false, rset.getString("Auth_atom_name_4"), logw))
						continue;
					if (!set_string(list[0], "setAuthChainId1", "setNilAuthChainId1", false, rset.getString("Auth_chain_ID_1"), logw))
						continue;
					if (!set_string(list[0], "setAuthChainId2", "setNilAuthChainId2", false, rset.getString("Auth_chain_ID_2"), logw))
						continue;
					if (!set_string(list[0], "setAuthChainId3", "setNilAuthChainId3", false, rset.getString("Auth_chain_ID_3"), logw))
						continue;
					if (!set_string(list[0], "setAuthChainId4", "setNilAuthChainId4", false, rset.getString("Auth_chain_ID_4"), logw))
						continue;
					if (!set_string(list[0], "setAuthCompId1", "setNilAuthCompId1", false, rset.getString("Auth_comp_ID_1"), logw))
						continue;
					if (!set_string(list[0], "setAuthCompId2", "setNilAuthCompId2", false, rset.getString("Auth_comp_ID_2"), logw))
						continue;
					if (!set_string(list[0], "setAuthCompId3", "setNilAuthCompId3", false, rset.getString("Auth_comp_ID_3"), logw))
						continue;
					if (!set_string(list[0], "setAuthCompId4", "setNilAuthCompId4", false, rset.getString("Auth_comp_ID_4"), logw))
						continue;
					if (!set_integer(list[0], "setAuthEntityAssemblyId1", "setNilAuthEntityAssemblyId1", false, rset.getString("Auth_entity_assembly_ID_1"), logw))
						continue;
					if (!set_integer(list[0], "setAuthEntityAssemblyId2", "setNilAuthEntityAssemblyId2", false, rset.getString("Auth_entity_assembly_ID_2"), logw))
						continue;
					if (!set_integer(list[0], "setAuthEntityAssemblyId3", "setNilAuthEntityAssemblyId3", false, rset.getString("Auth_entity_assembly_ID_3"), logw))
						continue;
					if (!set_integer(list[0], "setAuthEntityAssemblyId4", "setNilAuthEntityAssemblyId4", false, rset.getString("Auth_entity_assembly_ID_4"), logw))
						continue;
					if (!set_string(list[0], "setAuthSeqId1", "setNilAuthSeqId1", false, rset.getString("Auth_seq_ID_1"), logw))
						continue;
					if (!set_string(list[0], "setAuthSeqId2", "setNilAuthSeqId2", false, rset.getString("Auth_seq_ID_2"), logw))
						continue;
					if (!set_string(list[0], "setAuthSeqId3", "setNilAuthSeqId3", false, rset.getString("Auth_seq_ID_3"), logw))
						continue;
					if (!set_string(list[0], "setAuthSeqId4", "setNilAuthSeqId4", false, rset.getString("Auth_seq_ID_4"), logw))
						continue;
					if (!set_integer(list[0], "setCombinationId", "setNilCombinationId", false, rset.getString("Combination_ID"), logw))
						continue;
					if (!set_string(list[0], "setCompId1", "", true, rset.getString("Comp_ID_1"), logw))
						continue;
					if (!set_string(list[0], "setCompId2", "", true, rset.getString("Comp_ID_2"), logw))
						continue;
					if (!set_string(list[0], "setCompId3", "", true, rset.getString("Comp_ID_3"), logw))
						continue;
					if (!set_string(list[0], "setCompId4", "", true, rset.getString("Comp_ID_4"), logw))
						continue;
					if (!set_integer(list[0], "setCompIndexId1", "", true, rset.getString("Comp_index_ID_1"), logw))
						continue;
					if (!set_integer(list[0], "setCompIndexId2", "", true, rset.getString("Comp_index_ID_2"), logw))
						continue;
					if (!set_integer(list[0], "setCompIndexId3", "", true, rset.getString("Comp_index_ID_3"), logw))
						continue;
					if (!set_integer(list[0], "setCompIndexId4", "", true, rset.getString("Comp_index_ID_4"), logw))
						continue;
					if (!set_integer(list[0], "setEntityAssemblyId1", "", true, rset.getString("Entity_assembly_ID_1"), logw))
						continue;
					if (!set_integer(list[0], "setEntityAssemblyId2", "", true, rset.getString("Entity_assembly_ID_2"), logw))
						continue;
					if (!set_integer(list[0], "setEntityAssemblyId3", "", true, rset.getString("Entity_assembly_ID_3"), logw))
						continue;
					if (!set_integer(list[0], "setEntityAssemblyId4", "", true, rset.getString("Entity_assembly_ID_4"), logw))
						continue;
					if (!set_integer(list[0], "setEntityId1", "", true, rset.getString("Entity_ID_1"), logw))
						continue;
					if (!set_integer(list[0], "setEntityId2", "", true, rset.getString("Entity_ID_2"), logw))
						continue;
					if (!set_integer(list[0], "setEntityId3", "", true, rset.getString("Entity_ID_3"), logw))
						continue;
					if (!set_integer(list[0], "setEntityId4", "", true, rset.getString("Entity_ID_4"), logw))
						continue;
					if (!set_decimal(list[0], "setFigureOfMerit", "setNilFigureOfMerit", false, rset.getString("Figure_of_merit"), logw))
						continue;
					if (!set_integer(list[0], "setIndexId", "", true, rset.getString("ID"), logw))
						continue;
					if (!set_string(list[0], "setPdbAtomName1", "setNilPdbAtomName1", false, rset.getString("PDB_atom_name_1"), logw))
						continue;
					if (!set_string(list[0], "setPdbAtomName2", "setNilPdbAtomName2", false, rset.getString("PDB_atom_name_2"), logw))
						continue;
					if (!set_string(list[0], "setPdbAtomName3", "setNilPdbAtomName3", false, rset.getString("PDB_atom_name_3"), logw))
						continue;
					if (!set_string(list[0], "setPdbAtomName4", "setNilPdbAtomName4", false, rset.getString("PDB_atom_name_4"), logw))
						continue;
					if (!set_string(list[0], "setPdbInsCode1", "setNilPdbInsCode1", false, rset.getString("PDB_ins_code_1"), logw))
						continue;
					if (!set_string(list[0], "setPdbInsCode2", "setNilPdbInsCode2", false, rset.getString("PDB_ins_code_2"), logw))
						continue;
					if (!set_string(list[0], "setPdbInsCode3", "setNilPdbInsCode3", false, rset.getString("PDB_ins_code_3"), logw))
						continue;
					if (!set_string(list[0], "setPdbInsCode4", "setNilPdbInsCode4", false, rset.getString("PDB_ins_code_4"), logw))
						continue;
					if (!set_integer(list[0], "setPdbModelNum1", "setNilPdbModelNum1", false, rset.getString("PDB_model_num_1"), logw))
						continue;
					if (!set_integer(list[0], "setPdbModelNum2", "setNilPdbModelNum2", false, rset.getString("PDB_model_num_2"), logw))
						continue;
					if (!set_integer(list[0], "setPdbModelNum3", "setNilPdbModelNum3", false, rset.getString("PDB_model_num_3"), logw))
						continue;
					if (!set_integer(list[0], "setPdbModelNum4", "setNilPdbModelNum4", false, rset.getString("PDB_model_num_4"), logw))
						continue;
					if (!set_string(list[0], "setPdbRecordId1", "setNilPdbRecordId1", false, rset.getString("PDB_record_ID_1"), logw))
						continue;
					if (!set_string(list[0], "setPdbRecordId2", "setNilPdbRecordId2", false, rset.getString("PDB_record_ID_2"), logw))
						continue;
					if (!set_string(list[0], "setPdbRecordId3", "setNilPdbRecordId3", false, rset.getString("PDB_record_ID_3"), logw))
						continue;
					if (!set_string(list[0], "setPdbRecordId4", "setNilPdbRecordId4", false, rset.getString("PDB_record_ID_4"), logw))
						continue;
					if (!set_string(list[0], "setPdbResidueName1", "setNilPdbResidueName1", false, rset.getString("PDB_residue_name_1"), logw))
						continue;
					if (!set_string(list[0], "setPdbResidueName2", "setNilPdbResidueName2", false, rset.getString("PDB_residue_name_2"), logw))
						continue;
					if (!set_string(list[0], "setPdbResidueName3", "setNilPdbResidueName3", false, rset.getString("PDB_residue_name_3"), logw))
						continue;
					if (!set_string(list[0], "setPdbResidueName4", "setNilPdbResidueName4", false, rset.getString("PDB_residue_name_4"), logw))
						continue;
					if (!set_string(list[0], "setPdbResidueNo1", "setNilPdbResidueNo1", false, rset.getString("PDB_residue_no_1"), logw))
						continue;
					if (!set_string(list[0], "setPdbResidueNo2", "setNilPdbResidueNo2", false, rset.getString("PDB_residue_no_2"), logw))
						continue;
					if (!set_string(list[0], "setPdbResidueNo3", "setNilPdbResidueNo3", false, rset.getString("PDB_residue_no_3"), logw))
						continue;
					if (!set_string(list[0], "setPdbResidueNo4", "setNilPdbResidueNo4", false, rset.getString("PDB_residue_no_4"), logw))
						continue;
					if (!set_string(list[0], "setPdbStrandId1", "setNilPdbStrandId1", false, rset.getString("PDB_strand_ID_1"), logw))
						continue;
					if (!set_string(list[0], "setPdbStrandId2", "setNilPdbStrandId2", false, rset.getString("PDB_strand_ID_2"), logw))
						continue;
					if (!set_string(list[0], "setPdbStrandId3", "setNilPdbStrandId3", false, rset.getString("PDB_strand_ID_3"), logw))
						continue;
					if (!set_string(list[0], "setPdbStrandId4", "setNilPdbStrandId4", false, rset.getString("PDB_strand_ID_4"), logw))
						continue;
					if (!set_integer(list[0], "setResonanceId1", "", false, rset.getString("Resonance_ID_1"), logw))
						continue;
					if (!set_integer(list[0], "setResonanceId2", "", false, rset.getString("Resonance_ID_2"), logw))
						continue;
					if (!set_integer(list[0], "setResonanceId3", "", false, rset.getString("Resonance_ID_3"), logw))
						continue;
					if (!set_integer(list[0], "setResonanceId4", "", false, rset.getString("Resonance_ID_4"), logw))
						continue;
					if (!set_integer(list[0], "setSeqId1", "", true, rset.getString("Seq_ID_1"), logw))
						continue;
					if (!set_integer(list[0], "setSeqId2", "", true, rset.getString("Seq_ID_2"), logw))
						continue;
					if (!set_integer(list[0], "setSeqId3", "", true, rset.getString("Seq_ID_3"), logw))
						continue;
					if (!set_integer(list[0], "setSeqId4", "", true, rset.getString("Seq_ID_4"), logw))
						continue;
					if (!set_integer(list[0], "setSetId", "setNilSetId", false, rset.getString("Set_ID"), logw))
						continue;
					if (!set_integer(list[0], "setSourceExperimentId", "", false, rset.getString("Source_experiment_ID"), logw))
						continue;
					if (!set_string(list[0], "setTorsionAngleName", "setNilTorsionAngleName", false, rset.getString("Torsion_angle_name"), logw))
						continue;
					if (!set_decimal(list[0], "setWeight", "setNilWeight", false, rset.getString("Weight"), logw))
						continue;
					if (!set_string(list[0], "setEntryId", "", true, rset.getString("Entry_ID"), logw))
						continue;
					if (!set_integer(list[0], "setId", "", true, rid[lines - 1], logw))
						continue;
					if (!set_integer(list[0], "setTorsionAngleConstraintListId", "", true, rset.getString("Torsion_angle_constraint_list_ID"), logw))
						continue;

					body.setTorsionAngleConstraintArray(list);

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

				buffw.write("  </BMRBx:torsion_angle_constraintCategory>\n");

			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_TorsionAngleConstraintType.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} catch (IOException ex) {

			Logger lgr = Logger.getLogger(bmr_TorsionAngleConstraintType.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_TorsionAngleConstraintType.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		return list_len;
	}

	private static boolean set_string(TorsionAngleConstraint list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

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
				logw.write("item='" + method_name.substring(3) + "', category='TorsionAngleConstraint', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='TorsionAngleConstraint', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_integer(TorsionAngleConstraint list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml && (nil_method_name == null || nil_method_name.isEmpty()) && !method_name.equalsIgnoreCase("setSampleID") && !method_name.equalsIgnoreCase("setExperimentID") && !method_name.equalsIgnoreCase("setStudyID") && !method_name.equalsIgnoreCase("setAssemblyID") && (method_name.contains("Id") || method_name.contains("Number") || method_name.contains("Count")) && !method_name.contains("ListId") && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$")))
			val_name = "0";

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='TorsionAngleConstraint', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='TorsionAngleConstraint', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_decimal(TorsionAngleConstraint list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || (!val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)?$") && !val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)([eE][-+]?[0-9]+)?$"))) && (nil_method_name == null || nil_method_name.isEmpty())) {

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='TorsionAngleConstraint', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='TorsionAngleConstraint', value='" + _val_name + "' was empty, but not nillable.\n");
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
