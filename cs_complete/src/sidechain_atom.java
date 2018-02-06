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

import java.util.List;

public class sidechain_atom {

	String comp_id, alt_atom_id;
	boolean hasVal = false;

	sidechain_atom(String comp_id, String alt_atom_id) {

		this.comp_id = comp_id;
		this.alt_atom_id = alt_atom_id;

	}

	public static sidechain_atom[] popular_sc_atoms(polymer_types type, String comp_id) {

		List<sidechain_atom> ref = null;

		switch (type) {
		case aa:
			ref = bmr_Util_Main.aa_sc_atom;
			break;
		case dna:
			ref = bmr_Util_Main.dna_sc_atom;
			break;
		case rna:
			ref = bmr_Util_Main.rna_sc_atom;
			break;
		}

		int total = 0;

		for (sidechain_atom atom : ref) {

			if (!atom.comp_id.equals(comp_id))
				continue;

			if (assignable_atom.is_minor_atom(type, atom.comp_id, atom.alt_atom_id))
				continue;

			total++;

		}

		if (total == 0)
			return null;

		sidechain_atom[] list = new sidechain_atom[total];

		total = 0;

		for (sidechain_atom atom : ref) {

			if (!atom.comp_id.equals(comp_id))
				continue;

			if (assignable_atom.is_minor_atom(type, atom.comp_id, atom.alt_atom_id))
				continue;

			list[total++] = atom;

		}

		return list;
	}

	public static void init_has_val(sidechain_atom[] list) {

		if (list == null)
			return;

		for (sidechain_atom atom : list)
			atom.hasVal = false;

	}

	public static void set_has_val(sidechain_atom[] list, String alt_atom_id) {

		if (list == null)
			return;

		for (sidechain_atom atom : list) {

			if (atom.alt_atom_id.equals(alt_atom_id))
				atom.hasVal = true;

		}

	}

	public static int total(sidechain_atom[] list, atom_types type) {

		if (list == null)
			return 0;

		int total = 0;

		for (sidechain_atom atom : list) {

			switch (type) {
			case H:
				if (atom.alt_atom_id.startsWith("H") || atom.alt_atom_id.startsWith("M") || atom.alt_atom_id.startsWith("Q"))
					total++;
				break;
			default: // C, N, P
				if (atom.alt_atom_id.startsWith(type.name()))
					total++;
				break;
			}

		}

		return total;
	}

	public static int found(sidechain_atom[] list, atom_types type) {

		if (list == null)
			return 0;

		int found = 0;

		for (sidechain_atom atom : list) {

			if (!atom.hasVal)
				continue;

			switch (type) {
			case H:
				if (atom.alt_atom_id.startsWith("H") || atom.alt_atom_id.startsWith("M") || atom.alt_atom_id.startsWith("Q"))
					found++;
				break;
			default: // C, N, P
				if (atom.alt_atom_id.startsWith(type.name()))
					found++;
				break;
			}

		}

		return found;
	}

	public static void init_list(polymer_types type, List<sidechain_atom> list) {

		List<assignable_atom> all = null;
		List<backbone_atom> bb = null;

		switch (type) {
		case aa:
			all = bmr_Util_Main.aa_atom;
			bb = bmr_Util_Main.aa_bb_atom;
			break;
		case dna:
			all = bmr_Util_Main.dna_atom;
			bb = bmr_Util_Main.dna_bb_atom;
			break;
		case rna:
			all = bmr_Util_Main.rna_atom;
			bb = bmr_Util_Main.rna_bb_atom;
			break;
		}

		for (assignable_atom atom : all) {

			if (atom.minor)
				continue;

			boolean hit = false;

			for (backbone_atom bb_atom : bb) {

				if (!bb_atom.comp_id.equals(atom.comp_id))
					continue;

				if (!bb_atom.alt_atom_id.equals("CB") && bb_atom.alt_atom_id.equals(atom.alt_atom_id)) {

					hit = true;
					break;

				}

			}

			if (hit)
				continue;

			list.add(new sidechain_atom(atom.comp_id, atom.alt_atom_id));

		}

	}

}
