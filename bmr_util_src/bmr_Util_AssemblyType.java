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

import java.sql.*;

import java.util.List;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

public class bmr_Util_AssemblyType {

	static final Map<String, String> map = new HashMap<String, String>() {

		private static final long serialVersionUID = 8L;

		{

			put("cyclic lipopeptide", "peptide");
			put("other", "non-polymer");
			put("protein-RNA-inhibitor complex", "protein-RNA-inhibitor complex");
			put("MTF-1", "protein monomer");
			put("DNA double strand", "DNA double strand");
			put("RNA double strand", "RNA double strand");
			put("protein-DNA complex", "protein-DNA complex");
			put("DNA-drug complex", "DNA-drug complex");
			put("protein-DNA-inhibitor complex", "protein-DNA-inhibitor complex");
			put("protein hetero-multimer", "protein hetero-multimer");
			put("non-polymer", "non-polymer");
			put("protein-RNA complex", "protein-RNA complex");
			put("DNA-RNA complex", "DNA-RNA complex");
			put("protein-DNA-ligand complex", "protein-DNA-ligand complex");
			put("protein monomer", "protein monomer");
			put("DNA single strand", "DNA single strand");
			put("peptide", "peptide");
			put("RNA single strand", "RNA single strand");
			put("protein-ligand system", "protein-ligand complex");
			put("cyclic peptide", "peptide");
			put("protein-RNA-ligand complex", "protein-RNA-ligand complex");
			put("protein homo-multimer", "protein homo-multimer");
			put("DNA-RNA hybrid", "DNA-RNA hybrid");
			put("bacterial cell wall peptidoglycan", "polysaccharide");
			put("protein-drug complex", "protein-drug complex");
			put("RNA RNA homodimer", "RNA double strand");
			put("RNA-drug complex", "RNA-drug complex");
			put("protein-inhibitor complex", "protein-inhibitor complex");
			put("IA3", "protein monomer");
			put("polysaccharide", "polysaccharide");
			put("protein-nucleic acid complex", "protein-nucleic acid complex");
			put("protein-protein complex", "protein-protein complex");
			put("protein-carbohydrate complex", "protein-carbohydrate complex");
			put("single stranded RNA", "RNA single strand");
			put("At2g23090 monomer", "protein monomer");
			put("protein-ligand complex", "protein-ligand complex");

		}
	};

	public static String getType(String val_name) {
		return (String) map.get(val_name);
	}

	public static String getType(String val_name, String entry_id, String assembly_id) {

		final String[][] typetbl = {

				{"4361", "1", "DNA-drug complex"},
				{"4362", "1", "DNA-drug complex"},
				{"4736", "1", "protein monomer"},
				{"4746", "1", "DNA-drug complex"},
				{"4753", "1", "DNA-drug complex"},
				{"5003", "1", "protein-ligand complex"},
				{"5770", "1", "protein-protein complex"},
				{"5870", "1", "non-polymer"},

		};

		if (assembly_id == null)
			return val_name;

		for (int i = 0; i < typetbl.length; i++) {

			if (typetbl[i][0].equals(entry_id) && typetbl[i][1].equals(assembly_id))
				return typetbl[i][2];

		}

		return val_name;
	}

	public static String guessType(Connection conn_bmrb, String entry_id, String assembly_id) {

		String type = "na";

		Statement state = null;
		ResultSet rset = null;

		List<bmr_Util_AssemblyType.entity_type> entity_list = new ArrayList<bmr_Util_AssemblyType.entity_type>();

		String query;

		if (!(assembly_id == null || assembly_id.isEmpty() || assembly_id.equals(".") || assembly_id.equals("?")))
			query = new String("select \"Type\",\"Polymer_type\",\"Number_of_monomers\",\"Name\",\"Polymer_seq_one_letter_code\" from \"Entity\",\"Entity_assembly\" where \"Entity_assembly\".\"Entry_ID\"='" + entry_id + "' and \"Entity_assembly\".\"Assembly_ID\"='" + assembly_id + "' and \"Entity\".\"Entry_ID\"=\"Entity_assembly\".\"Entry_ID\" and \"Entity\".\"ID\"=\"Entity_assembly\".\"Entity_ID\"");
		else
			query = new String("select \"Type\",\"Polymer_type\",\"Number_of_monomers\",\"Name\",\"Polymer_seq_one_letter_code\" from \"Entity\",\"Entity_assembly\" where \"Entity_assembly\".\"Entry_ID\"='" + entry_id + "' and \"Entity\".\"Entry_ID\"=\"Entity_assembly\".\"Entry_ID\" and \"Entity\".\"ID\"=\"Entity_assembly\".\"Entity_ID\"");

		try {

			state = conn_bmrb.createStatement();
			rset = state.executeQuery(query);

			while (rset.next())
				entity_list.add(new bmr_Util_AssemblyType().new entity_type(rset.getString("Type"), rset.getString("Polymer_type"), rset.getString("Number_of_monomers"), rset.getString("Name"), rset.getString("Polymer_seq_one_letter_code")));

			boolean polypeptide = false;
			boolean polydeoxyribonucleotide = false;
			boolean polyribonucleotide = false;
			boolean polysaccharide = false;
			boolean peptide = false;
			boolean drug = false;
			boolean inhibitor = false;
			boolean ligand = false;

			boolean hetero_polymer_complex = false;

			int number_of_polymers = 0;
			int sort_of_polymers = 0;

			for (int i = 0; i < entity_list.size(); i++) {

				bmr_Util_AssemblyType.entity_type cont = entity_list.get(i);

				if (cont == null)
					continue;

				if (cont.name != null && cont.type != null && !cont.type.equalsIgnoreCase("polymer")) {

					if (cont.name.matches(".*[Dd][Rr][Uu][Gg].*"))
						drug = true;

					if (cont.name.matches(".*[Ii][Nn][Hh][Ii][Bb][Ii][Tt].*"))
						inhibitor = true;

					if (cont.name.matches(".*[Ll][Gg][Aa][Nn][Dd].*"))
						ligand = true;

				}

				if (cont.type == null || !cont.type.equalsIgnoreCase("polymer"))
					continue;

				number_of_polymers++;

				if (cont.polymer_type != null) {

					if (cont.polymer_type.startsWith("polypeptide")) {

						if (cont.number_of_monomers > 23)
							polypeptide = true;
						else
							peptide = true;

					}

					else if (cont.polymer_type.equalsIgnoreCase("polydeoxyribonucleotide"))
						polydeoxyribonucleotide = true;

					else if (cont.polymer_type.equalsIgnoreCase("polyribonucleotide"))
						polyribonucleotide = true;

					else if (cont.polymer_type.equalsIgnoreCase("DNA_RNA_hybrid"))
						polydeoxyribonucleotide = polyribonucleotide = true;

					else if (cont.polymer_type.startsWith("polysaccharide"))
						polysaccharide = true;

				}
			}

			if (polypeptide && (polydeoxyribonucleotide || polyribonucleotide || polysaccharide || peptide))
				hetero_polymer_complex = true;

			if (polydeoxyribonucleotide && (polyribonucleotide || polysaccharide || peptide || polypeptide))
				hetero_polymer_complex = true;

			if (polyribonucleotide && (polysaccharide || peptide || polypeptide || polydeoxyribonucleotide))
				hetero_polymer_complex = true;

			if (polysaccharide && (peptide || polypeptide || polydeoxyribonucleotide || polyribonucleotide))
				hetero_polymer_complex = true;

			if (peptide && (polypeptide || polydeoxyribonucleotide || polyribonucleotide || polysaccharide))
				hetero_polymer_complex = true;

			for (int i = 0; i < entity_list.size(); i++) {

				bmr_Util_AssemblyType.entity_type cont = entity_list.get(i);

				if (cont == null)
					continue;

				if (cont.type == null || !cont.type.equalsIgnoreCase("polymer"))
					continue;

				int j;

				for (j = 0; j < i; j++) {

					bmr_Util_AssemblyType.entity_type cont2 = entity_list.get(j);

					if (cont2 == null)
						continue;

					if (cont2.type == null || !cont2.type.equalsIgnoreCase("polymer"))
						continue;

					if (cont2.name.equals(cont.name))
						break;

				}

				if (j == i)
					sort_of_polymers++;

			}

			if (!hetero_polymer_complex) {

				if (number_of_polymers == 1 || polysaccharide || peptide) {

					if (polypeptide) {

						if (drug)
							type = "protein-drug complex";

						else if (inhibitor)
							type = "protein-inhibitor complex";

						else if (ligand)
							type = "protein-ligand complex";

						else
							type = "protein monomer";

					}

					else if (polydeoxyribonucleotide && polyribonucleotide)
						type = "DNA-RNA hybrid";

					else if (polydeoxyribonucleotide) {

						if (drug)
							type = "DNA-drug complex";

						else
							type = "DNA single strand";

					}

					else if (polyribonucleotide) {

						if (drug)
							type = "RNA-drug complex";

						else
							type = "RNA single strand";

					}

					else if (polysaccharide)
						type = "polysaccharide";

					else if (peptide)
						type = "peptide";

				}

				else if (sort_of_polymers == 1) {

					if (polypeptide)
						type = "protein homo-multimer";

					else if (polydeoxyribonucleotide)
						type = "DNA double strand";

					else if (polyribonucleotide)
						type = "RNA double strand";

					else if (polysaccharide)
						type = "polysaccharide";

					else if (peptide)
						type = "peptide";

				}

				else {

					if (polypeptide && number_of_polymers >= sort_of_polymers * 2)
						type = "protein hetero-multimer";

					else if (polypeptide && number_of_polymers == sort_of_polymers)
						type = "protein-protein complex";

					else if (polydeoxyribonucleotide)
						type = "DNA double strand";

					else if (polyribonucleotide)
						type = "RNA double strand";

					else if (polysaccharide)
						type = "polysaccharide";

					else if (peptide)
						type = "peptide";

				}
			}

			else {

				if (polypeptide) {

					if (polydeoxyribonucleotide && polyribonucleotide)
						type = "protein-nucleic acid complex";

					else if (polydeoxyribonucleotide && inhibitor)
						type = "protein-DNA-inhibitor complex";

					else if (polydeoxyribonucleotide && peptide)
						type = "protein-DNA-ligand complex";

					else if (polydeoxyribonucleotide)
						type = "protein-DNA complex";

					else if (polyribonucleotide && inhibitor)
						type = "protein-RNA-inhibitor complex";

					else if (polyribonucleotide && peptide)
						type = "protein-RNA-ligand complex";

					else if (polyribonucleotide)
						type = "protein-RNA complex";

					else if (polysaccharide)
						type = "protein-carbohydrate complex";

					else if (peptide)
						type = "protein-ligand complex";

				}

				else if (polydeoxyribonucleotide && polyribonucleotide)
					type = "DNA-RNA complex";

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return type;
	}

	private class entity_type {

		String type;
		String polymer_type;
		String name;
		String polymer_seq_one_letter_code;

		int number_of_monomers;

		entity_type(String type_name, String polymer_type_name, String number_of_monomers_name, String name_name, String polymer_seq_one_letter_code_name) {

			type = type_name;
			polymer_type = polymer_type_name;
			name = name_name;
			polymer_seq_one_letter_code = polymer_seq_one_letter_code_name;

			if (!(number_of_monomers_name == null || number_of_monomers_name.isEmpty() || number_of_monomers_name.equals(".") || number_of_monomers_name.equals("?")))
				number_of_monomers = Integer.valueOf(number_of_monomers_name);
			else if (!(polymer_seq_one_letter_code == null || polymer_seq_one_letter_code.isEmpty() || polymer_seq_one_letter_code.equals(".") || polymer_seq_one_letter_code.equals("?")))
				number_of_monomers = polymer_seq_one_letter_code.length();

		}
	}
}
