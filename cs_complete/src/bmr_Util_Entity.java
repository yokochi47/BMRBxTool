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

import java.util.logging.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class bmr_Util_Entity {

	public static void check(Connection conn_bmrb, int thrd_id, String entry_id, FileWriter strw, File char_file) {

		try {

			strw.write("data_chem_shift_completeness_list\n\n");

			strw.write("        ############################################\n");
			strw.write("        # Completeness of Assigned Chemical Shifts #\n");
			strw.write("        ############################################\n\n");

			strw.write(" ###############################################################################\n");
			strw.write(" # Excluded atoms in calculation of completeness are listed below.             #\n");
			strw.write(" # http://bmrbpub.protein.osaka-u.ac.jp/archive/cs_complete/excluded_atoms.str #\n");
			strw.write(" ###############################################################################\n");

			PreparedStatement pstate_datum = conn_bmrb.prepareStatement("select \"Type\",\"Count\" from \"Datum\" where \"Entry_ID\"=? and \"Count\"::integer > 0");
			pstate_datum.setString(1, entry_id);

			PreparedStatement pstate_cs_list = conn_bmrb.prepareStatement("select \"ID\" from \"Assigned_chem_shift_list\" where \"Entry_ID\"=? order by \"ID\"::integer");
			pstate_cs_list.setString(1, entry_id);

			PreparedStatement pstate_ent = conn_bmrb.prepareStatement("select \"ID\",\"Polymer_type\" from \"Entity\" where \"Entry_ID\"=? order by \"ID\"::integer");
			pstate_ent.setString(1, entry_id);

			PreparedStatement pstate_easm = conn_bmrb.prepareStatement("select \"ID\" from \"Entity_assembly\" where \"Entry_ID\"=? and \"Entity_ID\"=?");
			pstate_easm.setString(1, entry_id);

			PreparedStatement pstate_eci = conn_bmrb.prepareStatement("select \"Comp_ID\" from \"Entity_comp_index\" where \"Entry_ID\"=? and \"Entity_ID\"=? order by \"ID\"::integer");
			pstate_eci.setString(1, entry_id);

			PreparedStatement pstate_comp = conn_bmrb.prepareStatement("select \"Comp_ID\" from \"Entity_comp_index\" where \"Entry_ID\"=? and \"Entity_ID\"=? and \"ID\"=?");
			pstate_comp.setString(1, entry_id);

			PreparedStatement pstate_cs = conn_bmrb.prepareStatement("select \"Comp_ID\",\"Atom_ID\",\"Val\" from \"Atom_chem_shift\" where \"Entry_ID\"=? and \"Entity_ID\"=? and \"Comp_index_ID\"=? and \"Assigned_chem_shift_list_ID\"=?");
			pstate_cs.setString(1, entry_id);

			ResultSet rset_datum = pstate_datum.executeQuery();

			boolean has_1h = false;
			boolean has_13c = false;
			boolean has_15n = false;
			boolean has_31p = false;

			while (rset_datum.next()) {

				String type = rset_datum.getString("Type");

				if (type.equalsIgnoreCase("1H chemical shifts"))
					has_1h = true;

				if (type.equalsIgnoreCase("13C chemical shifts"))
					has_13c = true;

				if (type.equalsIgnoreCase("15N chemical shifts"))
					has_15n = true;

				if (type.equalsIgnoreCase("31P chemical shifts"))
					has_31p = true;

			}

			rset_datum.close();

			assignable_atom_counter atom_counter = new assignable_atom_counter(has_1h, has_13c, has_15n, has_31p);
			backbone_atom_counter bb_atom_counter = new backbone_atom_counter(has_1h, has_13c, has_15n, has_31p);
			sidechain_atom_counter sc_atom_counter = new sidechain_atom_counter(has_1h, has_13c, has_15n);
			aromatic_atom_counter arom_atom_counter = new aromatic_atom_counter(has_1h, has_13c, has_15n);
			methyl_atom_counter met_atom_counter = new methyl_atom_counter(has_1h, has_13c);

			String _polymer_type = "";
			String _cs_list_id = "";

			ResultSet rset_cs_list = pstate_cs_list.executeQuery();

			while (rset_cs_list.next()) {

				int total_seq = 0;
				int found_seq = 0;

				String cs_list_id = rset_cs_list.getString("ID");

				if (cs_list_id == null || cs_list_id.isEmpty()) {
					if (_cs_list_id.isEmpty())
						cs_list_id = "1";
					else
						cs_list_id = String.valueOf(Integer.valueOf(_cs_list_id) + 1);
				}

				else if (cs_list_id.equals(_cs_list_id))
					cs_list_id = String.valueOf(Integer.valueOf(_cs_list_id) + 1);

				_cs_list_id = cs_list_id;

				FileWriter charw = new FileWriter(char_file);

				ResultSet rset_ent = pstate_ent.executeQuery();

				while (rset_ent.next()) {

					String entity_id = rset_ent.getString("ID");
					String polymer_type = rset_ent.getString("Polymer_type");

					if (polymer_type == null || polymer_type.isEmpty())
						continue;

					if (_polymer_type.isEmpty())
						_polymer_type += polymer_type;
					else if (!_polymer_type.contains(polymer_type))
						_polymer_type += " " + polymer_type; 

					pstate_easm.setString(2, entity_id);

					ResultSet rset_easm = pstate_easm.executeQuery();

					String entity_assembly_id = ".";

					while (rset_easm.next()) {
						entity_assembly_id = rset_easm.getString("ID");
						break;
					}

					rset_easm.close();

					pstate_eci.setString(2, entity_id);
					pstate_comp.setString(2, entity_id);
					pstate_cs.setString(2, entity_id);

					pstate_cs.setString(4, cs_list_id);

					String entity_comp_index_comp_id_list = bmr_Util_EntityCompIndex.get_polymer_seq_one_letter_code(pstate_eci);

					total_seq += entity_comp_index_comp_id_list.length();

					//bmr_Util_AtomChemShift.check_comp_id(thrd_id, entry_id, entity_id, null, cs_list_id, has_1h, has_13c, has_15n, has_31p, pstate_comp, pstate_cs, entity_comp_index_comp_id_list.length());

					if (polymer_type.contains("polypeptide"))
						found_seq += bmr_Util_AtomChemShift.calc_ass_comp(thrd_id, entry_id, entity_assembly_id, entity_id, polymer_types.aa, cs_list_id, atom_counter, bb_atom_counter, sc_atom_counter, arom_atom_counter, met_atom_counter, pstate_comp, pstate_cs, entity_comp_index_comp_id_list.length(), charw);

					else if (polymer_type.equalsIgnoreCase("polydeoxyribonucleotide"))
						found_seq += bmr_Util_AtomChemShift.calc_ass_comp(thrd_id, entry_id, entity_assembly_id, entity_id, polymer_types.dna, cs_list_id, atom_counter, bb_atom_counter, sc_atom_counter, arom_atom_counter, met_atom_counter, pstate_comp, pstate_cs, entity_comp_index_comp_id_list.length(), charw);

					else if (polymer_type.equalsIgnoreCase("polyribonucleotide"))
						found_seq += bmr_Util_AtomChemShift.calc_ass_comp(thrd_id, entry_id, entity_assembly_id, entity_id, polymer_types.rna, cs_list_id, atom_counter, bb_atom_counter, sc_atom_counter, arom_atom_counter, met_atom_counter, pstate_comp, pstate_cs, entity_comp_index_comp_id_list.length(), charw);

					else if (polymer_type.equalsIgnoreCase("DNA/RNA hybrid"))
						found_seq += bmr_Util_AtomChemShift.calc_ass_comp(thrd_id, entry_id, entity_assembly_id, entity_id, null, cs_list_id, atom_counter, bb_atom_counter, sc_atom_counter, arom_atom_counter, met_atom_counter, pstate_comp, pstate_cs, entity_comp_index_comp_id_list.length(), charw);

					else {
						System.err.println("Entry.ID: " + entry_id + ", Entity.ID: " + entity_id + ", Polymer Type: " + polymer_type + ".");
						continue;
					}

				}

				rset_ent.close();

				charw.close();

				strw.write("\n\nsave_chem_shift_completeness_list_" + cs_list_id + "\n");

				strw.write("    _Chem_shift_completeness_list.Sf_category                    chem_shift_completeness_list\n");
				strw.write("    _Chem_shift_completeness_list.Queried_date                   " + bmr_Util_Main.today + "\n");
				strw.write("    _Chem_shift_completeness_list.Assigned_residue_coverage      " + (total_seq > 0 ? String.format("%4.3f", (float) found_seq / (float) total_seq) : ".") + "\n");
				strw.write("    _Chem_shift_completeness_list.Chem_shift_fraction            " + (atom_counter.total_all > 0 ? (atom_counter.found_all + "/" + atom_counter.total_all) : ".") + "\n");
				if (has_1h)
					strw.write("    _Chem_shift_completeness_list.Chem_shift_1H_fraction         " + (atom_counter.total_1h > 0 ? (atom_counter.found_1h + "/" + atom_counter.total_1h) : ".") + "\n");
				if (has_13c)
					strw.write("    _Chem_shift_completeness_list.Chem_shift_13C_fraction        " + (atom_counter.total_13c > 0 ? (atom_counter.found_13c + "/" + atom_counter.total_13c) : ".") + "\n");
				if (has_15n)
					strw.write("    _Chem_shift_completeness_list.Chem_shift_15N_fraction        " + (atom_counter.total_15n > 0 ? (atom_counter.found_15n + "/" + atom_counter.total_15n) : ".") + "\n");
				if (has_31p)
					strw.write("    _Chem_shift_completeness_list.Chem_shift_31P_fraction        " + (atom_counter.total_31p > 0 ? (atom_counter.found_31p + "/" + atom_counter.total_31p) : ".") + "\n");
				strw.write("    _Chem_shift_completeness_list.Bb_chem_shift_fraction         " + (bb_atom_counter.total_all > 0 ? (bb_atom_counter.found_all + "/" + bb_atom_counter.total_all) : ".") + "\n");
				if (has_1h)
					strw.write("    _Chem_shift_completeness_list.Bb_chem_shift_1H_fraction      " + (bb_atom_counter.total_1h > 0 ? (bb_atom_counter.found_1h + "/" + bb_atom_counter.total_1h) : ".") + "\n");
				if (has_13c)
					strw.write("    _Chem_shift_completeness_list.Bb_chem_shift_13C_fraction     " + (bb_atom_counter.total_13c > 0 ? (bb_atom_counter.found_13c + "/" + bb_atom_counter.total_13c) : ".") + "\n");
				if (has_15n)
					strw.write("    _Chem_shift_completeness_list.Bb_chem_shift_15N_fraction     " + (bb_atom_counter.total_15n > 0 ? (bb_atom_counter.found_15n + "/" + bb_atom_counter.total_15n) : ".") + "\n");
				if (has_31p)
					strw.write("    _Chem_shift_completeness_list.Bb_chem_shift_31P_fraction     " + (bb_atom_counter.total_31p > 0 ? (bb_atom_counter.found_31p + "/" + bb_atom_counter.total_31p) : ".") + "\n");
				strw.write("    _Chem_shift_completeness_list.Sc_chem_shift_fraction         " + (sc_atom_counter.total_all > 0 ? (sc_atom_counter.found_all + "/" + sc_atom_counter.total_all) : ".") + "\n");
				if (has_1h)
					strw.write("    _Chem_shift_completeness_list.Sc_chem_shift_1H_fraction      " + (sc_atom_counter.total_1h > 0 ? (sc_atom_counter.found_1h + "/" + sc_atom_counter.total_1h) : ".") + "\n");
				if (has_13c)
					strw.write("    _Chem_shift_completeness_list.Sc_chem_shift_13C_fraction     " + (sc_atom_counter.total_13c > 0 ? (sc_atom_counter.found_13c + "/" + sc_atom_counter.total_13c) : ".") + "\n");
				if (has_15n)
					strw.write("    _Chem_shift_completeness_list.Sc_chem_shift_15N_fraction     " + (sc_atom_counter.total_15n > 0 ? (sc_atom_counter.found_15n + "/" + sc_atom_counter.total_15n) : ".") + "\n");
				strw.write("    _Chem_shift_completeness_list.Arom_chem_shift_fraction       " + (arom_atom_counter.total_all > 0 ? (arom_atom_counter.found_all + "/" + arom_atom_counter.total_all) : ".") + "\n");
				if (has_1h)
					strw.write("    _Chem_shift_completeness_list.Arom_chem_shift_1H_fraction    " + (arom_atom_counter.total_1h > 0 ? (arom_atom_counter.found_1h + "/" + arom_atom_counter.total_1h) : ".") + "\n");
				if (has_13c)
					strw.write("    _Chem_shift_completeness_list.Arom_chem_shift_13C_fraction   " + (arom_atom_counter.total_13c > 0 ? (arom_atom_counter.found_13c + "/" + arom_atom_counter.total_13c) : ".") + "\n");
				if (has_15n)
					strw.write("    _Chem_shift_completeness_list.Arom_chem_shift_15N_fraction   " + (arom_atom_counter.total_15n > 0 ? (arom_atom_counter.found_15n + "/" + arom_atom_counter.total_15n) : ".") + "\n");
				strw.write("    _Chem_shift_completeness_list.Methyl_chem_shift_fraction     " + (met_atom_counter.total_all > 0 ? (met_atom_counter.found_all + "/" + met_atom_counter.total_all) : ".") + "\n");
				if (has_1h)
					strw.write("    _Chem_shift_completeness_list.Methyl_chem_shift_1H_fraction  " + (met_atom_counter.total_1h > 0 ? (met_atom_counter.found_1h + "/" + met_atom_counter.total_1h) : ".") + "\n");
				if (has_13c)
					strw.write("    _Chem_shift_completeness_list.Methyl_chem_shift_13C_fraction " + (met_atom_counter.total_13c > 0 ? (met_atom_counter.found_13c + "/" + met_atom_counter.total_13c) : ".") + "\n");
				strw.write("    _Chem_shift_completeness_list.Entity_polymer_type           " + (_polymer_type.contains(" ") ? "\"" : " ") + _polymer_type + (_polymer_type.contains(" ") ? "\"" : "") + "\n");
				strw.write("    _Chem_shift_completeness_list.Entry_ID                       " + entry_id + "\n");
				strw.write("    _Chem_shift_completeness_list.Assigned_chem_shift_list_ID    " + cs_list_id + "\n\n");

				strw.write("    loop_\n");
				strw.write("    _Chem_shift_completeness_char.Entity_assembly_ID\n");
				strw.write("    _Chem_shift_completeness_char.Entity_ID\n");
				strw.write("    _Chem_shift_completeness_char.Comp_index_ID\n");
				strw.write("    _Chem_shift_completeness_char.Comp_ID\n");
				strw.write("    _Chem_shift_completeness_char.Chem_shift_coverage\n");
				if (has_1h)
					strw.write("    _Chem_shift_completeness_char.Chem_shift_1H_coverage\n");
				if (has_13c)
					strw.write("    _Chem_shift_completeness_char.Chem_shift_13C_coverage\n");
				if (has_15n)
					strw.write("    _Chem_shift_completeness_char.Chem_shift_15N_coverage\n");
				if (has_31p)
					strw.write("    _Chem_shift_completeness_char.Chem_shift_31P_coverage\n");
				strw.write("    _Chem_shift_completeness_char.Bb_chem_shift_coverage\n");
				if (has_1h)
					strw.write("    _Chem_shift_completeness_char.Bb_chem_shift_1H_coverage\n");
				if (has_13c)
					strw.write("    _Chem_shift_completeness_char.Bb_chem_shift_13C_coverage\n");
				if (has_15n)
					strw.write("    _Chem_shift_completeness_char.Bb_chem_shift_15N_coverage\n");
				if (has_31p)
					strw.write("    _Chem_shift_completeness_char.Bb_chem_shift_31P_coverage\n");
				strw.write("    _Chem_shift_completeness_char.Sc_chem_shift_coverage\n");
				if (has_1h)
					strw.write("    _Chem_shift_completeness_char.Sc_chem_shift_1H_coverage\n");
				if (has_13c)
					strw.write("    _Chem_shift_completeness_char.Sc_chem_shift_13C_coverage\n");
				if (has_15n)
					strw.write("    _Chem_shift_completeness_char.Sc_chem_shift_15N_coverage\n");
				strw.write("    _Chem_shift_completeness_char.Arom_chem_shift_coverage\n");
				if (has_1h)
					strw.write("    _Chem_shift_completeness_char.Arom_chem_shift_1H_coverage\n");
				if (has_13c)
					strw.write("    _Chem_shift_completeness_char.Arom_chem_shift_13C_coverage\n");
				if (has_15n)
					strw.write("    _Chem_shift_completeness_char.Arom_chem_shift_15N_coverage\n");
				strw.write("    _Chem_shift_completeness_char.Methyl_chem_shift_coverage\n");
				if (has_1h)
					strw.write("    _Chem_shift_completeness_char.Methyl_chem_shift_1H_coverage\n");
				if (has_13c)
					strw.write("    _Chem_shift_completeness_char.Methyl_chem_shift_13C_coverage\n");
				strw.write("    _Chem_shift_completeness_char.Entry_ID\n");
				strw.write("    _Chem_shift_completeness_char.Assigned_chem_shift_list_ID\n\n");

				BufferedReader bufferr = new BufferedReader(new FileReader(char_file));

				String line = null;

				while ((line = bufferr.readLine()) != null)
					strw.write(line + "\n");

				bufferr.close();

				strw.write("    stop_\n\n");

				strw.write("save_\n");

				if (char_file.exists())
					char_file.delete();

			}

			rset_cs_list.close();

			pstate_datum.close();
			pstate_cs_list.close();
			pstate_ent.close();
			pstate_easm.close();
			pstate_eci.close();
			pstate_comp.close();
			pstate_cs.close();

		} catch (SQLException | IOException ex) {

			Logger lgr = Logger.getLogger(bmr_Util_Entity.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		}

	}

}
