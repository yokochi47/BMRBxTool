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

import java.util.HashMap;
import java.util.Map;

public class bmr_Util_Study {

	static final Map<String, String> map = new HashMap<String, String>() {

		private static final long serialVersionUID = 123L;

		{

			put("Protein-protein interaction", "Protein-protein interaction");
			put("Protein-protein interaction study", "Protein-protein interaction");
			put("solid state NMR assignment", "NMR signal assignment");
			put("Mutant comparison", "Mutant comparison");
			put("denatured state of RBP", "Dynamics analysis");
			put("Stoichiometry problems", "Stoichiometry problems");
			put("pH titration", "pH titration");
			put("Structure, binding experiments on wildtype and mutant proteins and ligands", "Protein-ligand interaction");
			put("Dynamics analysis", "Dynamics analysis");
			put("structure study", "Structure analysis");
			put("Interactions with different ligands", "Interactions with different ligands");
			put("Structural and dynamics analysis", "Structural and dynamics analysis");
			put("NMR assignment", "NMR signal assignment");
			put("Dynamics", "Dynamics analysis");
			put("Protein-ligand interaction", "Protein-ligand interaction");
			put("Chemical shift assignment", "NMR signal assignment");
			put("Backbone assignment", "NMR signal assignment");
			put("Protein-Protein Interaction", "Protein-protein interaction");
			put("NMR Resonance Assignments and Localization of the Active Site", "Chemical shift mapping");
			put("Structural analysis", "Structure analysis");
			put("Structure analysis", "Structure analysis");
			put("NMR signal assignment", "NMR signal assignment");
			put("Chemical shift mapping", "Chemical shift mapping");
			put("monomer/dimer comparison", "Stoichiometry problems");
			put("structural and dynamic analysis", "Structural and dynamics analysis");
			put("chemical shift mapping", "Chemical shift mapping");

		}
	};

	public static String getType(String val_name) {
		return (String) map.get(val_name);
	}
}
