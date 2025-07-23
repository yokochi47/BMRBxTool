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

public class bmr_Util_SampleComponent {

	static final Map<String, String> map = new HashMap<String, String>() {

		private static final long serialVersionUID = 114L;

		{

			put("w/w", "w/w");
			put("%v/v", "% v/v");
			put("eq", "eq");
			put("w/v", "w/v");
			put("equivalents", "eq");
			put("v/v", "v/v");
			put("ug/mL", "ug/mL");
			put("micM", "mM");
			put("MM", "mM");
			put("A260", "A260");
			put("mg/ul", "mg/uL");
			put("% (v/v)", "% v/v");
			put("% (w/v)", "% w/v");
			put("saturated", "saturated");
			put("molar equivalents", "eq");
			put("mg/uL", "mg/uL");
			put("OD260", "A260");
			put("%  (w/v)", "% w/v");
			put("x", "na");
			put("r", "%");
			put("n/a", "na");
			put("pM", "pM");
			put("mg/ml", "mg/mL");
			put("um", "uM");
			put("ul", "uL");
			put("%u03BCm", "mM");
			put("ug", "ug");
			put("trace", "na");
			put("%(v/v)", "% v/v");
			put("A260 units", "A260");
			put("X", "na");
			put("%(w/v)", "% w/v");
			put("M/L", "M");
			put("g/l", "g/L");
			put("(%w/v)", "% w/v");
			put("mg/mL", "mg/mL");
			put("mg/L", "mg/L");
			put("M", "M");
			put("uM", "uM");
			put("uL", "uL");
			put("na", "na");
			put("% by volume", "% v/v");
			put("w/w %", "% w/w");
			put("micromolar", "uM");
			put("w/v %", "% w/v");
			put("tablet/100mL", "tablet/100mL");
			put("mg / mL", "mg/mL");
			put("1.2", "na");
			put("g/L", "g/L");
			put("mm", "mM");
			put("1.0", "na");
			put("ml", "mL");
			put("nM", "nM");
			put("mg", "mg");
			put("% (vol)", "% v/v");
			put("ug/uL", "ug/uL");
			put("*", "na");
			put("ratio", "ratio");
			put("microM", "mM");
			put("mkM", "uM");
			put("%", "%");
			put("% (w:v)", "% w/v");
			put("% w/w", "% w/w");
			put("% w/v", "% w/v");
			put("v/v %", "% v/v");
			put("mN", "mM");
			put("mM", "mM");
			put("% v/v", "% v/v");
			put("mL", "mL");
			put("ug/ml", "ug/mL");
			put("per cent", "%");
			put("mH", "mM");
			put("Mm", "mM");
			put("%w/v", "% w/v");

		}
	};

	public static String getConcentrationValUnits(String val_name) {
		return (String) map.get(val_name);
	}
}
