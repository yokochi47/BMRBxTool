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

import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class bmr_Util_AtomChemShift {

	public static int calc_ass_comp(int thrd_id, String entry_id, String entity_assembly_id, String entity_id, polymer_types type, String cs_list_id, assignable_atom_counter atom_counter, backbone_atom_counter bb_atom_counter, sidechain_atom_counter sc_atom_counter, aromatic_atom_counter arom_atom_counter, methyl_atom_counter met_atom_counter, PreparedStatement pstate_comp, PreparedStatement pstate_cs, int list_len, FileWriter charw) throws IOException {

		int found_seq = 0;

		boolean has_1h = atom_counter.has_1h;
		boolean has_13c = atom_counter.has_13c;
		boolean has_15n = atom_counter.has_15n;
		boolean has_31p = atom_counter.has_31p;

		String comp_index_id_format = "%" + String.valueOf(list_len).length() + "d";

		for (int id = 1; id <= list_len; id++) {

			try {

				String id_str = String.valueOf(id);

				pstate_comp.setString(3, id_str);
				pstate_cs.setString(3, id_str);

				ResultSet rset_comp = pstate_comp.executeQuery();

				String comp_id = null;

				while (rset_comp.next()) {

					comp_id = rset_comp.getString("Comp_ID");
					break;

				}

				if (comp_id == null || comp_id.isEmpty())
					continue;

				polymer_types _type = type;
				String _comp_id = comp_id;

				if (type != null) {

					try {

						switch (type) {
						case aa:
							_comp_id = aa_seq_codes.valueOf(comp_id).name();
							break;
						case dna:
							_comp_id = dna_seq_codes.valueOf(comp_id).name();
							break;
						case rna:
							_comp_id = rna_seq_codes.valueOf(comp_id).name();
							break;
						}

					} catch (IllegalArgumentException e) {

						switch (type) {
						case aa:
							continue;
						case dna:
							try {
								_comp_id = dna_one_letter_codes.convert(dna_one_letter_codes.valueOf(comp_id)).name();
							} catch (IllegalArgumentException e2) {
								continue;
							}
							break;
						case rna:
							try {
								_comp_id = rna_two_letter_codes.convert(rna_two_letter_codes.valueOf(comp_id)).name();
							} catch (IllegalArgumentException e2) {
								continue;
							}
							break;
						}

					}

				}

				else { // hybrid

					try {

						_comp_id = dna_seq_codes.valueOf(comp_id).name();
						_type = polymer_types.dna;

					} catch (IllegalArgumentException e) {

						try {

							_comp_id = rna_two_letter_codes.convert(rna_two_letter_codes.valueOf(comp_id)).name();
							_type = polymer_types.rna;

						} catch (IllegalArgumentException e2) {

							try {

								_comp_id = rna_seq_codes.valueOf(comp_id).name();
								_type = polymer_types.rna;

							} catch (IllegalArgumentException e3) {

								try {

									_comp_id = dna_one_letter_codes.convert(dna_one_letter_codes.valueOf(comp_id)).name();
									_type = polymer_types.dna;

								} catch (IllegalArgumentException e4) {
									continue;
								}

							}

						}

					}

				}

				assignable_atom[] atoms = bmr_Util_Main.get_popular_atoms(_type, thrd_id, _comp_id);
				assignable_atom.init_has_val(atoms);

				backbone_atom[] bb_atoms = bmr_Util_Main.get_popular_bb_atoms(_type, thrd_id, _comp_id);
				backbone_atom.init_has_val(bb_atoms);

				sidechain_atom[] sc_atoms = bmr_Util_Main.get_popular_sc_atoms(_type, thrd_id, _comp_id);
				sidechain_atom.init_has_val(sc_atoms);

				aromatic_atom[] arom_atoms = bmr_Util_Main.get_popular_arom_atoms(_type, thrd_id, _comp_id);
				aromatic_atom.init_has_val(arom_atoms);

				methyl_atom[] methyl_atoms = bmr_Util_Main.get_popular_methyl_atoms(_type, thrd_id, _comp_id);
				methyl_atom.init_has_val(methyl_atoms);

				ResultSet rset_cs = pstate_cs.executeQuery();

				while (rset_cs.next()) {

					String __comp_id = rset_cs.getString("Comp_ID");

					if (!comp_id.equals(__comp_id)) {
						bmr_Util_Serv.append_unmatch_seq(entry_id, entity_id, id_str, comp_id, __comp_id, cs_list_id);
						System.err.println("Unmatched comp_id between Entity_comp_index.ID and Atom_chem_shift.Comp_ID. Entry_ID: " + entry_id + ", Entity_ID: " + entity_id + ", Comp_index_ID: " + id + ", Assigned_chem_shift_list_ID: " + cs_list_id);
						continue;
					}

					String val = rset_cs.getString("Val");

					if (val == null || val.isEmpty() || val.equals(".") || val.equals("?"))
						continue;

					String atom_id = rset_cs.getString("Atom_ID");

					if (atom_id.endsWith("\""))
						atom_id.replaceFirst("\"$", "''");

					String[] alt_atoms = alternative_atom.alt_atoms(_type, _comp_id, atom_id);

					for (String alt_atom_id : alt_atoms) {

						assignable_atom.set_has_val(atoms, alt_atom_id);
						backbone_atom.set_has_val(bb_atoms, alt_atom_id);
						sidechain_atom.set_has_val(sc_atoms, alt_atom_id);
						aromatic_atom.set_has_val(arom_atoms, alt_atom_id);
						methyl_atom.set_has_val(methyl_atoms, alt_atom_id);

					}

				}

				assignable_atom_counter _atom_counter = assignable_atom_counter.set(atoms, has_1h, has_13c, has_15n, has_31p);
				atom_counter.add(_atom_counter);

				if (_atom_counter.found_all > 0)
					found_seq++;

				backbone_atom_counter _bb_atom_counter = backbone_atom_counter.set(bb_atoms, has_1h, has_13c, has_15n, has_31p);
				bb_atom_counter.add(_bb_atom_counter);

				sidechain_atom_counter _sc_atom_counter = sidechain_atom_counter.set(sc_atoms, has_1h, has_13c, has_15n);
				sc_atom_counter.add(_sc_atom_counter);

				aromatic_atom_counter _arom_atom_counter = aromatic_atom_counter.set(arom_atoms, has_1h, has_13c, has_15n);
				arom_atom_counter.add(_arom_atom_counter);

				methyl_atom_counter _met_atom_counter = methyl_atom_counter.set(methyl_atoms, has_1h, has_13c);
				met_atom_counter.add(_met_atom_counter);

				charw.write("    " + entity_assembly_id + " " + entity_id + " " + String.format(comp_index_id_format, id) + " " + comp_id + " ");
				charw.write((_atom_counter.total_all > 0 ? String.format("%4.3f", (float) _atom_counter.found_all / (float) _atom_counter.total_all) : " .   ") + " ");
				if (has_1h)
					charw.write((_atom_counter.total_1h > 0 ? String.format("%4.3f", (float) _atom_counter.found_1h / (float) _atom_counter.total_1h) : " .   ") + " ");
				if (has_13c)
					charw.write((_atom_counter.total_13c > 0 ? String.format("%4.3f", (float) _atom_counter.found_13c / (float) _atom_counter.total_13c) : " .   ") + " ");
				if (has_15n)
					charw.write((_atom_counter.total_15n > 0 ? String.format("%4.3f", (float) _atom_counter.found_15n / (float) _atom_counter.total_15n) : " .   ") + " ");
				if (has_31p)
					charw.write((_atom_counter.total_31p > 0 ? String.format("%4.3f", (float) _atom_counter.found_31p / (float) _atom_counter.total_31p) : " .   ") + " ");
				charw.write((_bb_atom_counter.total_all > 0 ? String.format("%4.3f", (float) _bb_atom_counter.found_all / (float) _bb_atom_counter.total_all) : " .   ") + " ");
				if (has_1h)
					charw.write((_bb_atom_counter.total_1h > 0 ? String.format("%4.3f", (float) _bb_atom_counter.found_1h / (float) _bb_atom_counter.total_1h) : " .   ") + " ");
				if (has_13c)
					charw.write((_bb_atom_counter.total_13c > 0 ? String.format("%4.3f", (float) _bb_atom_counter.found_13c / (float) _bb_atom_counter.total_13c) : " .   ") + " ");
				if (has_15n)
					charw.write((_bb_atom_counter.total_15n > 0 ? String.format("%4.3f", (float) _bb_atom_counter.found_15n / (float) _bb_atom_counter.total_15n) : " .   ") + " ");
				if (has_31p)
					charw.write((_bb_atom_counter.total_31p > 0 ? String.format("%4.3f", (float) _bb_atom_counter.found_31p / (float) _bb_atom_counter.total_31p) : " .   ") + " ");
				charw.write((_sc_atom_counter.total_all > 0 ? String.format("%4.3f", (float) _sc_atom_counter.found_all / (float) _sc_atom_counter.total_all) : " .   ") + " ");
				if (has_1h)
					charw.write((_sc_atom_counter.total_1h > 0 ? String.format("%4.3f", (float) _sc_atom_counter.found_1h / (float) _sc_atom_counter.total_1h) : " .   ") + " ");
				if (has_13c)
					charw.write((_sc_atom_counter.total_13c > 0 ? String.format("%4.3f", (float) _sc_atom_counter.found_13c / (float) _sc_atom_counter.total_13c) : " .   ") + " ");
				if (has_15n)
					charw.write((_sc_atom_counter.total_15n > 0 ? String.format("%4.3f", (float) _sc_atom_counter.found_15n / (float) _sc_atom_counter.total_15n) : " .   ") + " ");
				charw.write((_arom_atom_counter.total_all > 0 ? String.format("%4.3f", (float) _arom_atom_counter.found_all / (float) _arom_atom_counter.total_all) : " .   ") + " ");
				if (has_1h)
					charw.write((_arom_atom_counter.total_1h > 0 ? String.format("%4.3f", (float) _arom_atom_counter.found_1h / (float) _arom_atom_counter.total_1h) : " .   ") + " ");
				if (has_13c)
					charw.write((_arom_atom_counter.total_13c > 0 ? String.format("%4.3f", (float) _arom_atom_counter.found_13c / (float) _arom_atom_counter.total_13c) : " .   ") + " ");
				if (has_15n)
					charw.write((_arom_atom_counter.total_15n > 0 ? String.format("%4.3f", (float) _arom_atom_counter.found_15n / (float) _arom_atom_counter.total_15n) : " .   ") + " ");
				charw.write((_met_atom_counter.total_all > 0 ? String.format("%4.3f", (float) _met_atom_counter.found_all / (float) _met_atom_counter.total_all) : " .   ") + " ");
				if (has_1h)
					charw.write((_met_atom_counter.total_1h > 0 ? String.format("%4.3f", (float) _met_atom_counter.found_1h / (float) _met_atom_counter.total_1h) : " .   ") + " ");
				if (has_13c)
					charw.write((_met_atom_counter.total_13c > 0 ? String.format("%4.3f", (float) _met_atom_counter.found_13c / (float) _met_atom_counter.total_13c) : " .   ") + " ");
				charw.write(entry_id + " " + cs_list_id + "\n");

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return found_seq;
	}

	public static void check_comp_id(int thrd_id, String entry_id, String entity_id, polymer_types type, String cs_list_id, boolean has_1h, boolean has_13c, boolean has_15n, boolean has_31p, PreparedStatement pstate_comp, PreparedStatement pstate_cs, int list_len) {

		for (int id = 1; id <= list_len; id++) {

			try {

				String id_str = String.valueOf(id);

				pstate_comp.setString(3, id_str);
				pstate_cs.setString(3, id_str);

				ResultSet rset_comp = pstate_comp.executeQuery();

				String comp_id = null;

				while (rset_comp.next()) {

					comp_id = rset_comp.getString("Comp_ID");
					break;

				}

				if (comp_id == null || comp_id.isEmpty())
					continue;

				ResultSet rset_cs = pstate_cs.executeQuery();

				while (rset_cs.next()) {

					String __comp_id = rset_cs.getString("Comp_ID");

					if (!comp_id.equals(__comp_id)) {
						bmr_Util_Serv.append_unmatch_seq(entry_id, entity_id, id_str, comp_id, __comp_id, cs_list_id);
						System.err.println("Unmatched comp_id between Entity_comp_index.ID and Atom_chem_shift.Comp_ID. Entry_ID: " + entry_id + ", Entity_ID: " + entity_id + ", Comp_index_ID: " + id + ", Assigned_chem_shift_list_ID: " + cs_list_id);
					}

				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

}
