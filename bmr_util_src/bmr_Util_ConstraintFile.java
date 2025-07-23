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

public class bmr_Util_ConstraintFile {

	static final Map<String, String> map_constraint_type = new HashMap<String, String>() {

		private static final long serialVersionUID = 44L;

		{

			put("dipolar coupling", "dipolar coupling");
			put("comment", "na");
			put("line broadening", "line broadening");
			put("hydrogen exchange", "hydrogen exchange");
			put("distance", "distance");
			put("nucleic acid dihedral angle", "nucleic acid dihedral angle");
			put("peak", "na");
			put("chemical shift", "chemical shift");
			put("na", "na");
			put("planarity", "protein peptide planarity");
			put("sequence", "na");
			put("protein peptide planarity", "protein peptide planarity");
			put("stereochemistry", "na");
			put("nucleic acid base planarity", "nucleic acid base planarity");
			put("not specified", "na");
			put("protocol", "na");
			put("pseudocontact shift", "pseudocontact shift");
			put("protein other kinds of constraints", "protein other kinds of constraints");
			put("nomenclature mapping", "na");
			put("intervector projection angle", "intervector projection angle");
			put("other angle", "other angle");
			put("chemical shift anisotropy", "chemical shift anisotropy");
			put("n/a", "na");
			put("dihedral angle", "protein dihedral angle");
			put("coordinate", "na");
			put("dihedral combo", "na");
			put("unknown", "na");
			put("protein dihedral angle", "protein dihedral angle");
			put("nucleic acid other kinds of constraints", "nucleic acid other kinds of constraints");
			put("coupling constant", "coupling constant");
			put("stereospecific assignment", "na");
			put("rdc", "na");

		}
	};

	public static String getConstraintType(String val_name) {
		return (String) map_constraint_type.get(val_name);
	}

	static final Map<String, String> map_constraint_subtype = new HashMap<String, String>() {

		private static final long serialVersionUID = 45L;

		{

			put("disulfide bond", "disulfide bond");
			put("general distance", "general distance");
			put("initial", "Not applicable");
			put("prochirality", "prochirality");
			put("Not applicable", "Not applicable");
			put("ring", "ring");
			put("ensemble", "Not applicable");
			put("not applicable", "Not applicable");
			put("carbon-carbon distances", "general distance");
			put("peptide", "peptide");
			put("structure calculation", "Not applicable");
			put("NOE", "NOE");
			put("chirality", "chirality");
			put("PRE", "PRE");
			put("symmetry", "symmetry");
			put("alignment tensor", "alignment tensor");
			put("ROE", "ROE");
			put("NOE not seen", "NOE not seen");
			put("ambi", "Not applicable");
			put("hydrogen bond", "hydrogen bond");
			put("NOE buildup", "NOE buildup");

		}
	};

	public static String getConstraintSubtype(String val_name) {
		return (String) map_constraint_subtype.get(val_name);
	}

	static final Map<String, String> map_constraint_subsubtype = new HashMap<String, String>() {

		private static final long serialVersionUID = 46L;

		{

			put("ambi", "ambi");
			put("na", "na");
			put("not applicable", "na");
			put("Not applicable", "na");
			put("simple", "simple");

		}
	};

	public static String getConstraintSubsubtype(String val_name) {
		return (String) map_constraint_subsubtype.get(val_name);
	}
}
