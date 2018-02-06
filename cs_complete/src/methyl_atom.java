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

public class methyl_atom {

	String comp_id, alt_atom_id;
	boolean hasVal = false;

	methyl_atom(String comp_id, String alt_atom_id) {

		this.comp_id = comp_id;
		this.alt_atom_id = alt_atom_id;

	}

	public static methyl_atom[] popular_methyl_atoms(polymer_types type, String comp_id) {

		List<methyl_atom> ref = null;

		switch (type) {
		case aa:
			ref = bmr_Util_Main.aa_methyl_atom;
			break;
		case dna:
			ref = bmr_Util_Main.dna_methyl_atom;
			break;
		case rna:
			ref = bmr_Util_Main.rna_methyl_atom;
			break;
		}

		int total = 0;

		for (methyl_atom atom : ref) {

			if (!atom.comp_id.equals(comp_id))
				continue;

			if (assignable_atom.is_minor_atom(type, atom.comp_id, atom.alt_atom_id))
				continue;

			total++;

		}

		if (total == 0)
			return null;

		methyl_atom[] list = new methyl_atom[total];

		total = 0;

		for (methyl_atom atom : ref) {

			if (!atom.comp_id.equals(comp_id))
				continue;

			if (assignable_atom.is_minor_atom(type, atom.comp_id, atom.alt_atom_id))
				continue;

			list[total++] = atom;

		}

		return list;
	}

	public static void init_has_val(methyl_atom[] list) {

		if (list == null)
			return;

		for (methyl_atom atom : list)
			atom.hasVal = false;

	}

	public static void set_has_val(methyl_atom[] list, String alt_atom_id) {

		if (list == null)
			return;

		for (methyl_atom atom : list) {

			if (atom.alt_atom_id.equals(alt_atom_id))
				atom.hasVal = true;

		}

	}

	public static int total(methyl_atom[] list, atom_types type) {

		if (list == null)
			return 0;

		int total = 0;

		for (methyl_atom atom : list) {

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

	public static int found(methyl_atom[] list, atom_types type) {

		if (list == null)
			return 0;

		int found = 0;

		for (methyl_atom atom : list) {

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

	public static void init_list(polymer_types type, List<methyl_atom> list) {

		switch (type) {
		case aa:
			list.add(new methyl_atom("ALA", "CB"));
			list.add(new methyl_atom("ALA", "MB"));

			list.add(new methyl_atom("ILE", "CD1"));
			list.add(new methyl_atom("ILE", "CG2"));
			list.add(new methyl_atom("ILE", "MD"));
			list.add(new methyl_atom("ILE", "MG"));

			list.add(new methyl_atom("LEU", "CD1"));
			list.add(new methyl_atom("LEU", "CD2"));
			list.add(new methyl_atom("LEU", "MD1"));
			list.add(new methyl_atom("LEU", "MD2"));

			list.add(new methyl_atom("THR", "CG2"));
			list.add(new methyl_atom("THR", "MG"));

			list.add(new methyl_atom("VAL", "CG1"));
			list.add(new methyl_atom("VAL", "CG2"));
			list.add(new methyl_atom("VAL", "MG1"));
			list.add(new methyl_atom("VAL", "MG2"));
			break;
		case dna:
			list.add(new methyl_atom("DT", "C7"));
			list.add(new methyl_atom("DT", "M7"));
			break;
		case rna:
			break;
		}

	}

}
