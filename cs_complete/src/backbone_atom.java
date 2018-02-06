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

public class backbone_atom {

	String comp_id, alt_atom_id;
	boolean hasVal = false;

	backbone_atom(String comp_id, String alt_atom_id) {

		this.comp_id = comp_id;
		this.alt_atom_id = alt_atom_id;

	}

	public static backbone_atom[] popular_bb_atoms(polymer_types type, String comp_id) {

		List<backbone_atom> ref = null;

		switch (type) {
		case aa:
			ref = bmr_Util_Main.aa_bb_atom;
			break;
		case dna:
			ref = bmr_Util_Main.dna_bb_atom;
			break;
		case rna:
			ref = bmr_Util_Main.rna_bb_atom;
			break;
		}

		int total = 0;

		for (backbone_atom atom : ref) {

			if (!atom.comp_id.equals(comp_id))
				continue;

			if (assignable_atom.is_minor_atom(type, atom.comp_id, atom.alt_atom_id))
				continue;

			total++;

		}

		if (total == 0)
			return null;

		backbone_atom[] list = new backbone_atom[total];

		total = 0;

		for (backbone_atom atom : ref) {

			if (!atom.comp_id.equals(comp_id))
				continue;

			if (assignable_atom.is_minor_atom(type, atom.comp_id, atom.alt_atom_id))
				continue;

			list[total++] = atom;

		}

		return list;
	}

	public static void init_has_val(backbone_atom[] list) {

		for (backbone_atom atom : list)
			atom.hasVal = false;

	}

	public static void set_has_val(backbone_atom[] list, String alt_atom_id) {

		for (backbone_atom atom : list) {

			if (atom.alt_atom_id.equals(alt_atom_id))
				atom.hasVal = true;

		}

	}

	public static int total(backbone_atom[] list, atom_types type) {

		int total = 0;

		for (backbone_atom atom : list) {

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

	public static int found(backbone_atom[] list, atom_types type) {

		int found = 0;

		for (backbone_atom atom : list) {

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

	public static void init_list(polymer_types type, List<backbone_atom> list) {

		switch (type) {
		case aa:
			String[] aa_comp_sel_ids = { "ALA", "ARG", "ASN", "ASP", "CYS", "GLN", "GLU", "HIS", "ILE", "LEU", "LYS", "MET", "PHE", "SER", "THR", "TRP", "TYR", "VAL" };

			for (String aa_comp_id : aa_comp_sel_ids) {

				list.add(new backbone_atom(aa_comp_id, "C"));
				list.add(new backbone_atom(aa_comp_id, "CA"));
				list.add(new backbone_atom(aa_comp_id, "CB"));
				list.add(new backbone_atom(aa_comp_id, "H"));
				list.add(new backbone_atom(aa_comp_id, "HA"));
				list.add(new backbone_atom(aa_comp_id, "N"));

			}

			list.add(new backbone_atom("GLY", "C"));
			list.add(new backbone_atom("GLY", "CA"));
			list.add(new backbone_atom("GLY", "HA2"));
			list.add(new backbone_atom("GLY", "HA3"));
			list.add(new backbone_atom("GLY", "N"));

			list.add(new backbone_atom("PRO", "C"));
			list.add(new backbone_atom("PRO", "CA"));
			list.add(new backbone_atom("PRO", "CB"));
			list.add(new backbone_atom("PRO", "HA"));
			list.add(new backbone_atom("PRO", "N"));
			break;
		case dna:
			for (dna_seq_codes dna_seq_code : dna_seq_codes.values()) {

				String dna_comp_id = dna_seq_code.name();

				list.add(new backbone_atom(dna_comp_id, "C1'"));
				list.add(new backbone_atom(dna_comp_id, "C2'"));
				list.add(new backbone_atom(dna_comp_id, "C3'"));
				list.add(new backbone_atom(dna_comp_id, "C4'"));
				list.add(new backbone_atom(dna_comp_id, "C5'"));
				list.add(new backbone_atom(dna_comp_id, "H1'"));
				list.add(new backbone_atom(dna_comp_id, "H2'"));
				list.add(new backbone_atom(dna_comp_id, "H2''"));
				list.add(new backbone_atom(dna_comp_id, "H3'"));
				list.add(new backbone_atom(dna_comp_id, "H4'"));
				list.add(new backbone_atom(dna_comp_id, "H5'"));
				list.add(new backbone_atom(dna_comp_id, "H5''"));
				list.add(new backbone_atom(dna_comp_id, "P"));

			}
			break;
		case rna:
			for (rna_seq_codes rna_seq_code : rna_seq_codes.values()) {

				String rna_comp_id = rna_seq_code.name();

				list.add(new backbone_atom(rna_comp_id, "C1'"));
				list.add(new backbone_atom(rna_comp_id, "C2'"));
				list.add(new backbone_atom(rna_comp_id, "C3'"));
				list.add(new backbone_atom(rna_comp_id, "C4'"));
				list.add(new backbone_atom(rna_comp_id, "C5'"));
				list.add(new backbone_atom(rna_comp_id, "H1'"));
				list.add(new backbone_atom(rna_comp_id, "H2'"));
				list.add(new backbone_atom(rna_comp_id, "H3'"));
				list.add(new backbone_atom(rna_comp_id, "H4'"));
				list.add(new backbone_atom(rna_comp_id, "H5'"));
				list.add(new backbone_atom(rna_comp_id, "H5''"));
				list.add(new backbone_atom(rna_comp_id, "P"));

			}
			break;
		}

	}

}
