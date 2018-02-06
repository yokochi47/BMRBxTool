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

public class assignable_atom {

	String comp_id, alt_atom_id;
	Integer count;
	boolean minor = false;
	boolean hasVal = false;

	assignable_atom(String comp_id, String alt_atom_id, Integer count) {

		this.comp_id = comp_id;
		this.alt_atom_id = alt_atom_id;
		this.count = count;

	}

	public static assignable_atom[] popular_atoms(polymer_types type, String comp_id) {

		List<assignable_atom> ref = null;

		switch (type) {
		case aa:
			ref = bmr_Util_Main.aa_atom;
			break;
		case dna:
			ref = bmr_Util_Main.dna_atom;
			break;
		case rna:
			ref = bmr_Util_Main.rna_atom;
			break;
		}

		int total = 0;

		for (assignable_atom atom : ref) {

			if (!atom.comp_id.equals(comp_id) || atom.minor)
				continue;

			total++;

		}

		if (total == 0)
			return null;

		assignable_atom[] list = new assignable_atom[total];

		total = 0;

		for (assignable_atom atom : ref) {

			if (!atom.comp_id.equals(comp_id) || atom.minor)
				continue;

			list[total++] = atom;

		}

		return list;
	}

	public static boolean is_minor_atom(polymer_types type, String comp_id, String alt_atom_id) {

		List<assignable_atom> ref = null;

		switch (type) {
		case aa:
			ref = bmr_Util_Main.aa_atom;
			break;
		case dna:
			ref = bmr_Util_Main.dna_atom;
			break;
		case rna:
			ref = bmr_Util_Main.rna_atom;
			break;
		}

		for (assignable_atom atom : ref) {

			if (!atom.comp_id.equals(comp_id) || !atom.alt_atom_id.equals(alt_atom_id))
				continue;

			return atom.minor;

		}

		return true;
	}

	public static void init_has_val(assignable_atom[] list) {

		for (assignable_atom atom : list)
			atom.hasVal = false;

	}

	public static void set_has_val(assignable_atom[] list, String alt_atom_id) {

		for (assignable_atom atom : list) {

			if (atom.alt_atom_id.equals(alt_atom_id))
				atom.hasVal = true;

		}

	}

	public static int total(assignable_atom[] list, atom_types type) {

		int total = 0;

		for (assignable_atom atom : list) {

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

	public static int found(assignable_atom[] list, atom_types type) {

		int found = 0;

		for (assignable_atom atom : list) {

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

}
