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

public class aromatic_atom {

	String comp_id, alt_atom_id;
	boolean hasVal = false;

	aromatic_atom(String comp_id, String alt_atom_id) {

		this.comp_id = comp_id;
		this.alt_atom_id = alt_atom_id;

	}

	public static aromatic_atom[] popular_arom_atoms(polymer_types type, String comp_id) {

		List<aromatic_atom> ref = null;

		switch (type) {
		case aa:
			ref = bmr_Util_Main.aa_arom_atom;
			break;
		case dna:
			ref = bmr_Util_Main.dna_arom_atom;
			break;
		case rna:
			ref = bmr_Util_Main.rna_arom_atom;
			break;
		}

		int total = 0;

		for (aromatic_atom atom : ref) {

			if (!atom.comp_id.equals(comp_id))
				continue;

			if (assignable_atom.is_minor_atom(type, atom.comp_id, atom.alt_atom_id))
				continue;

			total++;

		}

		if (total == 0)
			return null;

		aromatic_atom[] list = new aromatic_atom[total];

		total = 0;

		for (aromatic_atom atom : ref) {

			if (!atom.comp_id.equals(comp_id))
				continue;

			if (assignable_atom.is_minor_atom(type, atom.comp_id, atom.alt_atom_id))
				continue;

			list[total++] = atom;

		}

		return list;
	}

	public static void init_has_val(aromatic_atom[] list) {

		if (list == null)
			return;

		for (aromatic_atom atom : list)
			atom.hasVal = false;

	}

	public static void set_has_val(aromatic_atom[] list, String alt_atom_id) {

		if (list == null)
			return;

		for (aromatic_atom atom : list) {

			if (atom.alt_atom_id.equals(alt_atom_id))
				atom.hasVal = true;

		}

	}

	public static int total(aromatic_atom[] list, atom_types type) {

		if (list == null)
			return 0;

		int total = 0;

		for (aromatic_atom atom : list) {

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

	public static int found(aromatic_atom[] list, atom_types type) {

		if (list == null)
			return 0;

		int found = 0;

		for (aromatic_atom atom : list) {

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

	public static void init_list(polymer_types type, List<aromatic_atom> list) {

		switch (type) {
		case aa:
			list.add(new aromatic_atom("HIS", "CG"));
			list.add(new aromatic_atom("HIS", "CD2"));
			list.add(new aromatic_atom("HIS", "CE1"));
			list.add(new aromatic_atom("HIS", "HD1"));
			list.add(new aromatic_atom("HIS", "HD2"));
			list.add(new aromatic_atom("HIS", "HE1"));
			list.add(new aromatic_atom("HIS", "HE2"));
			list.add(new aromatic_atom("HIS", "ND1"));
			list.add(new aromatic_atom("HIS", "NE2"));

			list.add(new aromatic_atom("PHE", "CG"));
			list.add(new aromatic_atom("PHE", "CD1"));
			list.add(new aromatic_atom("PHE", "CD2"));
			list.add(new aromatic_atom("PHE", "CE1"));
			list.add(new aromatic_atom("PHE", "CE2"));
			list.add(new aromatic_atom("PHE", "CZ"));
			list.add(new aromatic_atom("PHE", "HD1"));
			list.add(new aromatic_atom("PHE", "HD2"));
			list.add(new aromatic_atom("PHE", "HE1"));
			list.add(new aromatic_atom("PHE", "HE2"));
			list.add(new aromatic_atom("PHE", "HZ"));

			list.add(new aromatic_atom("TRP", "CG"));
			list.add(new aromatic_atom("TRP", "CD1"));
			list.add(new aromatic_atom("TRP", "CD2"));
			list.add(new aromatic_atom("TRP", "CE2"));
			list.add(new aromatic_atom("TRP", "CE3"));
			list.add(new aromatic_atom("TRP", "CZ2"));
			list.add(new aromatic_atom("TRP", "CZ3"));
			list.add(new aromatic_atom("TRP", "CH2"));
			list.add(new aromatic_atom("TRP", "HD1"));
			list.add(new aromatic_atom("TRP", "HE1"));
			list.add(new aromatic_atom("TRP", "HE3"));
			list.add(new aromatic_atom("TRP", "HZ2"));
			list.add(new aromatic_atom("TRP", "HZ3"));
			list.add(new aromatic_atom("TRP", "HH2"));
			list.add(new aromatic_atom("TRP", "NE1"));

			list.add(new aromatic_atom("TYR", "CG"));
			list.add(new aromatic_atom("TYR", "CD1"));
			list.add(new aromatic_atom("TYR", "CD2"));
			list.add(new aromatic_atom("TYR", "CE1"));
			list.add(new aromatic_atom("TYR", "CE2"));
			list.add(new aromatic_atom("TYR", "CZ"));
			list.add(new aromatic_atom("TYR", "HD1"));
			list.add(new aromatic_atom("TYR", "HD2"));
			list.add(new aromatic_atom("TYR", "HE1"));
			list.add(new aromatic_atom("TYR", "HE2"));
			break;
		case dna:
			list.add(new aromatic_atom("DA", "C2"));
			list.add(new aromatic_atom("DA", "C4"));
			list.add(new aromatic_atom("DA", "C5"));
			list.add(new aromatic_atom("DA", "C6"));
			list.add(new aromatic_atom("DA", "C8"));
			list.add(new aromatic_atom("DA", "H2"));
			list.add(new aromatic_atom("DA", "H8"));
			list.add(new aromatic_atom("DA", "N1"));
			list.add(new aromatic_atom("DA", "N3"));
			list.add(new aromatic_atom("DA", "N7"));
			list.add(new aromatic_atom("DA", "N9"));

			list.add(new aromatic_atom("DC", "C2"));
			list.add(new aromatic_atom("DC", "C4"));
			list.add(new aromatic_atom("DC", "C5"));
			list.add(new aromatic_atom("DC", "C6"));
			list.add(new aromatic_atom("DC", "H5"));
			list.add(new aromatic_atom("DC", "H6"));
			list.add(new aromatic_atom("DC", "N1"));
			list.add(new aromatic_atom("DC", "N3"));

			list.add(new aromatic_atom("DG", "C2"));
			list.add(new aromatic_atom("DG", "C4"));
			list.add(new aromatic_atom("DG", "C5"));
			list.add(new aromatic_atom("DG", "C6"));
			list.add(new aromatic_atom("DG", "C8"));
			list.add(new aromatic_atom("DG", "H1"));
			list.add(new aromatic_atom("DG", "H8"));
			list.add(new aromatic_atom("DG", "N1"));
			list.add(new aromatic_atom("DG", "N7"));
			list.add(new aromatic_atom("DG", "N9"));

			list.add(new aromatic_atom("DT", "C2"));
			list.add(new aromatic_atom("DT", "C4"));
			list.add(new aromatic_atom("DT", "C5"));
			list.add(new aromatic_atom("DT", "C6"));
			list.add(new aromatic_atom("DT", "H3"));
			list.add(new aromatic_atom("DT", "H6"));
			list.add(new aromatic_atom("DT", "N1"));
			list.add(new aromatic_atom("DT", "N3"));
			break;
		case rna:
			list.add(new aromatic_atom("A", "C2"));
			list.add(new aromatic_atom("A", "C4"));
			list.add(new aromatic_atom("A", "C5"));
			list.add(new aromatic_atom("A", "C6"));
			list.add(new aromatic_atom("A", "C8"));
			list.add(new aromatic_atom("A", "H2"));
			list.add(new aromatic_atom("A", "H8"));
			list.add(new aromatic_atom("A", "N1"));
			list.add(new aromatic_atom("A", "N3"));
			list.add(new aromatic_atom("A", "N7"));
			list.add(new aromatic_atom("A", "N9"));

			list.add(new aromatic_atom("C", "C2"));
			list.add(new aromatic_atom("C", "C4"));
			list.add(new aromatic_atom("C", "C5"));
			list.add(new aromatic_atom("C", "C6"));
			list.add(new aromatic_atom("C", "H5"));
			list.add(new aromatic_atom("C", "H6"));
			list.add(new aromatic_atom("C", "N1"));
			list.add(new aromatic_atom("C", "N3"));

			list.add(new aromatic_atom("G", "C2"));
			list.add(new aromatic_atom("G", "C4"));
			list.add(new aromatic_atom("G", "C5"));
			list.add(new aromatic_atom("G", "C6"));
			list.add(new aromatic_atom("G", "C8"));
			list.add(new aromatic_atom("G", "H1"));
			list.add(new aromatic_atom("G", "H8"));
			list.add(new aromatic_atom("G", "N1"));
			list.add(new aromatic_atom("G", "N7"));
			list.add(new aromatic_atom("G", "N9"));

			list.add(new aromatic_atom("U", "C2"));
			list.add(new aromatic_atom("U", "C4"));
			list.add(new aromatic_atom("U", "C5"));
			list.add(new aromatic_atom("U", "C6"));
			list.add(new aromatic_atom("U", "H3"));
			list.add(new aromatic_atom("U", "H5"));
			list.add(new aromatic_atom("U", "H6"));
			list.add(new aromatic_atom("U", "N1"));
			list.add(new aromatic_atom("U", "N3"));
			break;
		}

	}

}
