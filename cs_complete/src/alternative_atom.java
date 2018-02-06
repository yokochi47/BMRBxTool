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

public class alternative_atom {

	String comp_id, atom_id, alt_atom_id;

	alternative_atom(String comp_id, String atom_id, String alt_atom_id) {

		this.comp_id = comp_id;
		this.atom_id = atom_id;
		this.alt_atom_id = alt_atom_id;

	}

	public static String[] alt_atoms(polymer_types type, String comp_id, String atom_id) {

		List<alternative_atom> ref = null;

		switch (type) {
		case aa:
			ref = bmr_Util_Main.aa_alt_atom;
			break;
		case dna:
			ref = bmr_Util_Main.dna_alt_atom;
			break;
		case rna:
			ref = bmr_Util_Main.rna_alt_atom;
			break;
		}

		int total = 0;

		boolean hit = false;

		for (alternative_atom atom : ref) {

			if (!atom.comp_id.equals(comp_id) || !atom.atom_id.equals(atom_id))
				continue;

			hit = true;

			total++;

		}

		String[] list = new String[total > 0 ? total : 1];

		if (hit) {

			total = 0;

			for (alternative_atom atom : ref) {

				if (!atom.comp_id.equals(comp_id) || !atom.atom_id.equals(atom_id))
					continue;

				list[total++] = atom.alt_atom_id;

			}

		} else
			list[0] = atom_id;

		return list;
	}

	public static void init_list(polymer_types type, List<alternative_atom> list) {
		
		switch (type) {
		case aa:
			// methyl

			list.add(new alternative_atom("ALA", "HB", "MB"));
			list.add(new alternative_atom("ALA", "HB1", "MB"));
			list.add(new alternative_atom("ALA", "HB2", "MB"));
			list.add(new alternative_atom("ALA", "HB3", "MB"));

			list.add(new alternative_atom("ILE", "HD1", "MD"));
			list.add(new alternative_atom("ILE", "HD11", "MD"));
			list.add(new alternative_atom("ILE", "HD12", "MD"));
			list.add(new alternative_atom("ILE", "HD13", "MD"));

			list.add(new alternative_atom("ILE", "HG2", "MG"));
			list.add(new alternative_atom("ILE", "HG21", "MG"));
			list.add(new alternative_atom("ILE", "HG22", "MG"));
			list.add(new alternative_atom("ILE", "HG23", "MG"));

			list.add(new alternative_atom("LEU", "HD1", "MD1"));
			list.add(new alternative_atom("LEU", "HD11", "MD1"));
			list.add(new alternative_atom("LEU", "HD12", "MD1"));
			list.add(new alternative_atom("LEU", "HD13", "MD1"));

			list.add(new alternative_atom("LEU", "HD2", "MD2"));
			list.add(new alternative_atom("LEU", "HD21", "MD2"));
			list.add(new alternative_atom("LEU", "HD22", "MD2"));
			list.add(new alternative_atom("LEU", "HD23", "MD2"));

			list.add(new alternative_atom("LEU", "CD", "CD1"));
			list.add(new alternative_atom("LEU", "CD", "CD2"));
			list.add(new alternative_atom("LEU", "CD1", "CD2"));
			list.add(new alternative_atom("LEU", "CD2", "CD1"));

			list.add(new alternative_atom("MET", "HE", "ME"));
			list.add(new alternative_atom("MET", "HE1", "ME"));
			list.add(new alternative_atom("MET", "HE2", "ME"));
			list.add(new alternative_atom("MET", "HE3", "ME"));

			list.add(new alternative_atom("THR", "HG2", "MG"));
			list.add(new alternative_atom("THR", "HG21", "MG"));
			list.add(new alternative_atom("THR", "HG22", "MG"));
			list.add(new alternative_atom("THR", "HG23", "MG"));

			list.add(new alternative_atom("VAL", "HG1", "MG1"));
			list.add(new alternative_atom("VAL", "HG11", "MG1"));
			list.add(new alternative_atom("VAL", "HG12", "MG1"));
			list.add(new alternative_atom("VAL", "HG13", "MG1"));

			list.add(new alternative_atom("VAL", "HG2", "MG2"));
			list.add(new alternative_atom("VAL", "HG21", "MG2"));
			list.add(new alternative_atom("VAL", "HG22", "MG2"));
			list.add(new alternative_atom("VAL", "HG23", "MG2"));

			list.add(new alternative_atom("VAL", "CG", "CG1"));
			list.add(new alternative_atom("VAL", "CG", "CG2"));
			list.add(new alternative_atom("VAL", "CG1", "CG2"));
			list.add(new alternative_atom("VAL", "CG2", "CG1"));

			// amine

			list.add(new alternative_atom("LYS", "HZ", "QZ"));
			list.add(new alternative_atom("LYS", "HZ1", "QZ"));
			list.add(new alternative_atom("LYS", "HZ2", "QZ"));
			list.add(new alternative_atom("LYS", "HZ2", "QZ"));

			// methylene

			list.add(new alternative_atom("ARG", "HB", "HB2"));
			list.add(new alternative_atom("ARG", "HB", "HB3"));
			list.add(new alternative_atom("ARG", "HB2", "HB3"));
			list.add(new alternative_atom("ARG", "HB3", "HB2"));

			list.add(new alternative_atom("ARG", "HG", "HG2"));
			list.add(new alternative_atom("ARG", "HG", "HG3"));
			list.add(new alternative_atom("ARG", "HG2", "HG3"));
			list.add(new alternative_atom("ARG", "HG3", "HG2"));

			list.add(new alternative_atom("ARG", "HD", "HD2"));
			list.add(new alternative_atom("ARG", "HD", "HD3"));
			list.add(new alternative_atom("ARG", "HD2", "HD3"));
			list.add(new alternative_atom("ARG", "HD3", "HD2"));

			list.add(new alternative_atom("ARG", "HH", "HH11"));
			list.add(new alternative_atom("ARG", "HH", "HH12"));
			list.add(new alternative_atom("ARG", "HH", "HH21"));
			list.add(new alternative_atom("ARG", "HH", "HH22"));
			list.add(new alternative_atom("ARG", "HH1", "HH11"));
			list.add(new alternative_atom("ARG", "HH1", "HH12"));
			list.add(new alternative_atom("ARG", "HH2", "HH21"));
			list.add(new alternative_atom("ARG", "HH2", "HH22"));
			list.add(new alternative_atom("ARG", "HH11", "HH12"));
			list.add(new alternative_atom("ARG", "HH12", "HH11"));
			list.add(new alternative_atom("ARG", "HH21", "HH22"));
			list.add(new alternative_atom("ARG", "HH22", "HH21"));

			list.add(new alternative_atom("ARG", "NH", "NH1"));
			list.add(new alternative_atom("ARG", "NH", "NH2"));
			list.add(new alternative_atom("ARG", "NH1", "NH2"));
			list.add(new alternative_atom("ARG", "NH2", "NH1"));

			list.add(new alternative_atom("ASN", "HB", "HB2"));
			list.add(new alternative_atom("ASN", "HB", "HB3"));
			list.add(new alternative_atom("ASN", "HB2", "HB3"));
			list.add(new alternative_atom("ASN", "HB3", "HB2"));

			list.add(new alternative_atom("ASN", "HD2", "HD21"));
			list.add(new alternative_atom("ASN", "HD2", "HD22"));
			list.add(new alternative_atom("ASN", "HD21", "HD22"));
			list.add(new alternative_atom("ASN", "HD22", "HD21"));

			list.add(new alternative_atom("ASP", "HB", "HB2"));
			list.add(new alternative_atom("ASP", "HB", "HB3"));
			list.add(new alternative_atom("ASP", "HB2", "HB3"));
			list.add(new alternative_atom("ASP", "HB3", "HB2"));

			list.add(new alternative_atom("CYS", "HB", "HB2"));
			list.add(new alternative_atom("CYS", "HB", "HB3"));
			list.add(new alternative_atom("CYS", "HB2", "HB3"));
			list.add(new alternative_atom("CYS", "HB3", "HB2"));

			list.add(new alternative_atom("GLN", "HB", "HB2"));
			list.add(new alternative_atom("GLN", "HB", "HB3"));
			list.add(new alternative_atom("GLN", "HB2", "HB3"));
			list.add(new alternative_atom("GLN", "HB3", "HB2"));

			list.add(new alternative_atom("GLN", "HG", "HG2"));
			list.add(new alternative_atom("GLN", "HG", "HG3"));
			list.add(new alternative_atom("GLN", "HG2", "HG3"));
			list.add(new alternative_atom("GLN", "HG3", "HG2"));

			list.add(new alternative_atom("GLN", "HE2", "HE21"));
			list.add(new alternative_atom("GLN", "HE2", "HE22"));
			list.add(new alternative_atom("GLN", "HE21", "HE22"));
			list.add(new alternative_atom("GLN", "HE22", "HE21"));

			list.add(new alternative_atom("GLU", "HB", "HB2"));
			list.add(new alternative_atom("GLU", "HB", "HB3"));
			list.add(new alternative_atom("GLU", "HB2", "HB3"));
			list.add(new alternative_atom("GLU", "HB3", "HB2"));

			list.add(new alternative_atom("GLU", "HG", "HG2"));
			list.add(new alternative_atom("GLU", "HG", "HG3"));
			list.add(new alternative_atom("GLU", "HG2", "HG3"));
			list.add(new alternative_atom("GLU", "HG3", "HG2"));

			list.add(new alternative_atom("GLY", "HA", "HA2"));
			list.add(new alternative_atom("GLY", "HA", "HA3"));
			list.add(new alternative_atom("GLY", "HA2", "HA3"));
			list.add(new alternative_atom("GLY", "HA3", "HA2"));

			list.add(new alternative_atom("HIS", "HB", "HB2"));
			list.add(new alternative_atom("HIS", "HB", "HB3"));
			list.add(new alternative_atom("HIS", "HB2", "HB3"));
			list.add(new alternative_atom("HIS", "HB3", "HB2"));

			list.add(new alternative_atom("ILE", "HG1", "HG12"));
			list.add(new alternative_atom("ILE", "HG1", "HG13"));
			list.add(new alternative_atom("ILE", "HG12", "HG13"));
			list.add(new alternative_atom("ILE", "HG13", "HG12"));

			list.add(new alternative_atom("LEU", "HB", "HB2"));
			list.add(new alternative_atom("LEU", "HB", "HB3"));
			list.add(new alternative_atom("LEU", "HB2", "HB3"));
			list.add(new alternative_atom("LEU", "HB3", "HB2"));

			list.add(new alternative_atom("LYS", "HB", "HB2"));
			list.add(new alternative_atom("LYS", "HB", "HB3"));
			list.add(new alternative_atom("LYS", "HB2", "HB3"));
			list.add(new alternative_atom("LYS", "HB3", "HB2"));

			list.add(new alternative_atom("LYS", "HG", "HG2"));
			list.add(new alternative_atom("LYS", "HG", "HG3"));
			list.add(new alternative_atom("LYS", "HG2", "HG3"));
			list.add(new alternative_atom("LYS", "HG3", "HG2"));

			list.add(new alternative_atom("LYS", "HD", "HD2"));
			list.add(new alternative_atom("LYS", "HD", "HD3"));
			list.add(new alternative_atom("LYS", "HD2", "HD3"));
			list.add(new alternative_atom("LYS", "HD3", "HD2"));

			list.add(new alternative_atom("LYS", "HE", "HE2"));
			list.add(new alternative_atom("LYS", "HE", "HE3"));
			list.add(new alternative_atom("LYS", "HE2", "HE3"));
			list.add(new alternative_atom("LYS", "HE3", "HE2"));

			list.add(new alternative_atom("MET", "HB", "HB2"));
			list.add(new alternative_atom("MET", "HB", "HB3"));
			list.add(new alternative_atom("MET", "HB2", "HB3"));
			list.add(new alternative_atom("MET", "HB3", "HB2"));

			list.add(new alternative_atom("MET", "HG", "HG2"));
			list.add(new alternative_atom("MET", "HG", "HG3"));
			list.add(new alternative_atom("MET", "HG2", "HG3"));
			list.add(new alternative_atom("MET", "HG3", "HG2"));

			list.add(new alternative_atom("PHE", "HB", "HB2"));
			list.add(new alternative_atom("PHE", "HB", "HB3"));
			list.add(new alternative_atom("PHE", "HB2", "HB3"));
			list.add(new alternative_atom("PHE", "HB3", "HB2"));

			list.add(new alternative_atom("PHE", "HD", "HD1"));
			list.add(new alternative_atom("PHE", "HD", "HD2"));
			list.add(new alternative_atom("PHE", "HD1", "HD2"));
			list.add(new alternative_atom("PHE", "HD2", "HD1"));

			list.add(new alternative_atom("PHE", "HE", "HE1"));
			list.add(new alternative_atom("PHE", "HE", "HE2"));
			list.add(new alternative_atom("PHE", "HE1", "HE2"));
			list.add(new alternative_atom("PHE", "HE2", "HE1"));

			list.add(new alternative_atom("PHE", "CD", "CD1"));
			list.add(new alternative_atom("PHE", "CD", "CD2"));
			list.add(new alternative_atom("PHE", "CD1", "CD2"));
			list.add(new alternative_atom("PHE", "CD2", "CD1"));

			list.add(new alternative_atom("PHE", "CE", "CE1"));
			list.add(new alternative_atom("PHE", "CE", "CE2"));
			list.add(new alternative_atom("PHE", "CE1", "CE2"));
			list.add(new alternative_atom("PHE", "CE2", "CE1"));

			list.add(new alternative_atom("PRO", "HB", "HB2"));
			list.add(new alternative_atom("PRO", "HB", "HB3"));
			list.add(new alternative_atom("PRO", "HB2", "HB3"));
			list.add(new alternative_atom("PRO", "HB3", "HB2"));

			list.add(new alternative_atom("PRO", "HG", "HG2"));
			list.add(new alternative_atom("PRO", "HG", "HG3"));
			list.add(new alternative_atom("PRO", "HG2", "HG3"));
			list.add(new alternative_atom("PRO", "HG3", "HG2"));

			list.add(new alternative_atom("PRO", "HD", "HD2"));
			list.add(new alternative_atom("PRO", "HD", "HD3"));
			list.add(new alternative_atom("PRO", "HD2", "HD3"));
			list.add(new alternative_atom("PRO", "HD3", "HD2"));

			list.add(new alternative_atom("SER", "HB", "HB2"));
			list.add(new alternative_atom("SER", "HB", "HB3"));
			list.add(new alternative_atom("SER", "HB2", "HB3"));
			list.add(new alternative_atom("SER", "HB3", "HB2"));

			list.add(new alternative_atom("TRP", "HB", "HB2"));
			list.add(new alternative_atom("TRP", "HB", "HB3"));
			list.add(new alternative_atom("TRP", "HB2", "HB3"));
			list.add(new alternative_atom("TRP", "HB3", "HB2"));
			break;
		case dna:
			list.add(new alternative_atom("DA", "H6", "H61"));
			list.add(new alternative_atom("DA", "H6", "H62"));
			list.add(new alternative_atom("DA", "H61", "H62"));
			list.add(new alternative_atom("DA", "H62", "H61"));

			list.add(new alternative_atom("DC", "H4", "H41"));
			list.add(new alternative_atom("DC", "H4", "H42"));
			list.add(new alternative_atom("DC", "H41", "H42"));
			list.add(new alternative_atom("DC", "H42", "H41"));

			list.add(new alternative_atom("DG", "H2", "H21"));
			list.add(new alternative_atom("DG", "H2", "H22"));
			list.add(new alternative_atom("DG", "H21", "H22"));
			list.add(new alternative_atom("DG", "H22", "H21"));

			list.add(new alternative_atom("DT", "H7", "M7"));
			list.add(new alternative_atom("DT", "H71", "M7"));
			list.add(new alternative_atom("DT", "H72", "M7"));
			list.add(new alternative_atom("DT", "H73", "M7"));

			list.add(new alternative_atom("DA", "H2'", "H2''"));
			list.add(new alternative_atom("DA", "H2''", "H2'"));

			list.add(new alternative_atom("DA", "H5'", "H5''"));
			list.add(new alternative_atom("DA", "H5''", "H5'"));

			list.add(new alternative_atom("DC", "H2'", "H2''"));
			list.add(new alternative_atom("DC", "H2''", "H2'"));

			list.add(new alternative_atom("DC", "H5'", "H5''"));
			list.add(new alternative_atom("DC", "H5''", "H5'"));

			list.add(new alternative_atom("DG", "H2'", "H2''"));
			list.add(new alternative_atom("DG", "H2''", "H2'"));

			list.add(new alternative_atom("DG", "H5'", "H5''"));
			list.add(new alternative_atom("DG", "H5''", "H5'"));

			list.add(new alternative_atom("DT", "H2'", "H2''"));
			list.add(new alternative_atom("DT", "H2''", "H2'"));

			list.add(new alternative_atom("DT", "H5'", "H5''"));
			list.add(new alternative_atom("DT", "H5''", "H5'"));
			break;
		case rna:
			list.add(new alternative_atom("A", "H6", "H61"));
			list.add(new alternative_atom("A", "H6", "H62"));
			list.add(new alternative_atom("A", "H61", "H62"));
			list.add(new alternative_atom("A", "H62", "H61"));

			list.add(new alternative_atom("C", "H4", "H41"));
			list.add(new alternative_atom("C", "H4", "H42"));
			list.add(new alternative_atom("C", "H41", "H42"));
			list.add(new alternative_atom("C", "H42", "H41"));

			list.add(new alternative_atom("G", "H2", "H21"));
			list.add(new alternative_atom("G", "H2", "H22"));
			list.add(new alternative_atom("G", "H21", "H22"));
			list.add(new alternative_atom("G", "H22", "H21"));

			list.add(new alternative_atom("A", "H5'", "H5''"));
			list.add(new alternative_atom("A", "H5''", "H5'"));

			list.add(new alternative_atom("C", "H5'", "H5''"));
			list.add(new alternative_atom("C", "H5''", "H5'"));

			list.add(new alternative_atom("G", "H5'", "H5''"));
			list.add(new alternative_atom("G", "H5''", "H5'"));

			list.add(new alternative_atom("U", "H5'", "H5''"));
			list.add(new alternative_atom("U", "H5''", "H5'"));
			break;
		}
		
	}

}
