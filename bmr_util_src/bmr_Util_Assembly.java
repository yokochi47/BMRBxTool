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

public class bmr_Util_Assembly {

	static final Map<String, String> map_thiol_state = new HashMap<String, String>() {

		private static final long serialVersionUID = 1L;

		{

			put("not reported", "not reported");
			put("free and bound", "free and disulfide bound");
			put("disulfide bound and other bound", "disulfide and other bound");
			put("disulfide bound and free", "free and disulfide bound");
			put("all disuldide bound", "all disulfide bound");
			put("free and other bound", "free and other bound");
			put("Fully oxidized", "all disulfide bound");
			put("other bound or free", "free and other bound");
			put("not present", "not present");
			put("fully reduced", "all free");
			put("disulfide bound", "free and disulfide bound");
			put("free, other bound", "free and other bound");
			put("all disulfide bond", "all disulfide bound");
			put("disulfide and other bound", "disulfide and other bound");
			put("free disulfide and other bound", "free disulfide and other bound");
			put("other bound and free", "free and other bound");
			put("free and disulfide bound", "free and disulfide bound");
			put("all bound", "all disulfide bound");
			put("Fully reduced", "all free");
			put("fully oxidized", "all disulfide bound");
			put("unkown", "unknown");
			put("all disufide bound", "all disulfide bound");
			put("all bound other", "all other bound");
			put("all other bound", "all other bound");
			put("other bound, and free", "free and other bound");
			put("not available", "not available");
			put("unknown", "unknown");
			put("all free", "all free");
			put("all disulfide bound", "all disulfide bound");
			put("reduced and oxidized present", "free and disulfide bound");
			put("All free", "all free");
			put("all reduced", "all free");

		}
	};

	public static String getThiolState(String val_name) {
		return (String) map_thiol_state.get(val_name);
	}

	static final Map<String, String> map_non_standard_bonds = new HashMap<String, String>() {

		private static final long serialVersionUID = 2L;

		{

			put("0", "no");
			put("yes", "yes");
			put("no", "no");

		}
	};

	public static String getNonStandardBonds(String val_name) {
		return (String) map_non_standard_bonds.get(val_name);
	}

	public static String checkECNumber(String ec_number, String entry_id) {

		final String[][] ecnumtbl = {

				{"E.C.2.7.1.112", "10245", "2.7.1.112"},
				{"E.C.2.7.1.-", "10253", "2.7.1.-"},
				{"E.C.3.1.3.48", "10260", "3.1.3.48"},
				{"E.C.6.1.1.10", "10261", "6.1.1.10"},
				{"E.C.3.1.3.48", "10263", "3.1.3.48"},
				{"E.C.3.1.3.48", "10265", "3.1.3.48"},
				{"E.C.3.1.3.48", "10266", "3.1.3.48"},
				{"E.C.3.6.1.-", "10269", "3.6.1.-"},
				{"E.C.2.7.1.112", "10300", "2.7.1.112"},
				{"E.C.2.7.10.1", "11094", "2.7.10.1"},
				{"E.C.5.3.4.1", "11100", "5.3.4.1"},
				{"E.C.5.3.4.1", "11101", "5.3.4.1"},
				{"E.C.5.3.4.1", "11102", "5.3.4.1"},
				{"E.C.5.3.4.1", "11104", "5.3.4.1"},
				{"E.C.5.3.4.1", "11109", "5.3.4.1"},
				{"E.C.5.3.4.1", "11114", "5.3.4.1"},
				{"E.C.5.3.4.1", "11116", "5.3.4.1"},
				{"E.C.2.7.1.112", "11117", "2.7.1.112"},
				{"E.C.3.1.4.1, 3.6.1.9", "11150", "3.1.4.1, 3.6.1.9"},
				{"E.C.2.7.11.13", "11151", "2.7.11.13"},
				{"E.C.2.7.10.2", "11157", "2.7.10.2"},
				{"E.C.2.1.1.43", "11160", "2.1.1.43"},
				{"E.C.6.3.2.-", "11163", "6.3.2.-"},
				{"E.C.3.1.2.15", "11173", "3.1.2.15"},
				{"E.C.2.7.1.112", "11208", "2.7.1.112"},
				{"E.C.2.3.1.-", "11209", "2.3.1.-"},
				{"E.C.2.7.1.112", "11214", "2.7.1.112"},
				{"E.C.2.7.10.1", "11227", "2.7.10.1"},
				{"E.C.2.7.11.18", "11236", "2.7.11.18"},
				{"E.C.2.1.1.43", "11237", "2.1.1.43"},
				{"E.C.2.3.1.168", "11240", "2.3.1.168"},
				{"E.C.6.3.2.-", "11247", "6.3.2.-"},
				{"E.C.2.7.11.1", "11248", "2.7.11.1"},
				{"E.C.3.1.2.15", "11256", "3.1.2.15"},
				{"E.C.2.7.1.37", "11265", "2.7.1.37"},
				{"E.C.3.1.3.48", "11292", "3.1.3.48"},
				{"E.C.6.3.2.-", "11296", "6.3.2.-"},
				{"E.C.6.3.2.-", "11303", "6.3.2.-"},
				{"E.C.5.4.2.3", "11310", "5.4.2.3"},
				{"E.C.6.3.2.-", "11317", "6.3.2.-"},
				{"E.C.6.3.2.-", "11327", "6.3.2.-"},
				{"E.C.2.7.11.13", "11401", "2.7.11.13"},
				{"E.C.3.6.1.-", "11402", "3.6.1.-"},
				{"na", "15267", "na"},
				{"EC 2.7.1.112", "15488", "2.7.1.112"},
				{"n.a.", "15719", "na"},
				{"EC 3.4.21.-", "15806", "3.4.21.-"},
				{"PA3407", "15962", "na"},
				{"PA3407", "15963", "na"},
				{"EC 5.3.4.1", "15974", "5.3.4.1"},
				{"EC 5.3.4.1", "15998", "5.3.4.1"},
				{"YP_336021.1", "16144", "1.11.1.15"},
				{"NIL", "16257", "na"},
				{"EC 3.2.1.39", "16481", "3.2.1.39"},
				{"EC: 5.2.1.8", "16690", "5.2.1.8"},
				{"EC 3.4.24.83", "16735", "3.4.24.83"},
				{"EC 2.7.7.7", "16869", "2.7.7.7"},
				{"EC 2.7.7.7", "17201", "2.7.7.7"},
				{"N.A.", "17206", "na"},
				{"EC 6.3.4.1", "17935", "6.3.4.1"},
				{"E.C.2.4.1.119", "18477", "2.4.1.119"},
				{"E.C. 3.1.1.3", "18574", "3.1.1.3"},
				{"E.C. 3.1.1.3", "18575", "3.1.1.3"},
				{"EC 3.4.25.1", "19194", "3.4.25.1"},
				{"EC 3.1.3.29", "19207", "3.1.3.29"},
				{"EC 3.1.3.29", "19209", "3.1.3.29"},
				{"na", "20004", "na"},
				{"na", "20005", "na"},
				{"EC 6.1.1.6", "4134", "6.1.1.6"},
				{"n/a", "4431", "na"},
				{"n/a", "4494", "na"},
				{"3.2.1.17.", "4876", "3.2.1.17"},
				{"SWISSPROT P00324", "4881", "1.19.6.1"},
				{"3.2.1.17.", "4883", "3.2.1.17"},
				{"3.2.1.17.", "4887", "3.2.1.17"},
				{"1.8.1.2.", "4985", "1.8.1.2"},
				{"3.1.3.48 and 3.1.3.16", "5552", "3.1.3.48, 3.1.3.16"},
				{"E.C.3.6.3.3", "5604", "3.6.3.3"},
				{"2.7.1", "5778", "2.7.1.-"},
				{"E.C.2.7.1.-", "6103", "2.7.1.-"},
				{"E.C.2.7.1.-", "6104", "2.7.1.-"},
				{"Pointed domain", "6287", "na"},
				{"EC: 2.7.1.-", "6297", "2.7.1.-"},
				{"E.C.2.3.1.48", "6325", "2.3.1.48"},
				{"E.C.2.3.1.48", "6326", "2.3.1.48"},
				{"E.C.2.3.1.48", "6327", "2.3.1.48"},
				{"E.C.2.3.1.48", "6328", "2.3.1.48"},
				{"E.C.2.3.1.48", "6329", "2.3.1.48"},
				{"E.C.3.6.3.4", "6914", "3.6.3.4"},
				{"EC 6.3.2.1", "6940", "6.3.2.1"},
				{"0", "7020", "na"},
				{"E.3.4.24.65", "7089", "3.4.24.65"},
				{"EC 3.4.24.83", "16735", "3.4.24.83"},
				{"Enzyme class:6.3.2 by Uniprot", "25351", "6.3.2.-"},
				{"EC. 2. 1. 1. 6", "26848", "2.1.1.6"},
				{"EC. 2. 1. 1. 6", "26851", "2.1.1.6"},
				{"not available", "26986", "na"},
				{"EC 2.7.2.3", "27022", "2.7.2.3"},
				{"EC 3.1.-.-", "27160", "3.1.-.-"},
				{"EC 5.4.2.6", "27174", "5.4.2.6"},
				{"EC 5.4.2.6", "27175", "5.4.2.6"},
				{"EC 3.6.3.14", "27232", "3.6.3.14"},
				{"EC 3.1.-.-", "27403", "3.1.-.-"},
				{"EC 3.1.-.-", "27404", "3.1.-.-"},
				{"EC 3.6.4.12", "27780", "3.6.4.12"},
				{"EC 3.6.4.12", "27785", "3.6.4.12"},
				{"n.a.", "27235", "na"},
				{"1268.31", "25738", "na"},
				{"N/A", "27268", "na"},
				{"EC:2.3.2.26", "27477", "na"},
				{"ec:3.1.3.48", "27950", "na"},
				{"n.a", "25432", "na"},
				{"none", "25197", "na"},
				{"EC:3.4.19.12", "27627", "na"},

		};

		for (int i = 0; i < ecnumtbl.length; i++) {

			if (ecnumtbl[i][0].equals(ec_number) && ecnumtbl[i][1].equals(entry_id))
				return ecnumtbl[i][2];

			if (ecnumtbl[i][0].isEmpty() && (ec_number == null || ec_number.isEmpty() || ec_number.equals(".") || ec_number.equals("?")) && ecnumtbl[i][1].equals(entry_id))
				return ecnumtbl[i][2];

		}

		if (ec_number.toLowerCase().startsWith("n") || !ec_number.contains("."))
			return "na";

		if (!ec_number.contains(" ") && ec_number.toLowerCase().startsWith("ec:"))
			return ec_number.substring(3);

		if (ec_number.contains(","))
			return ec_number.replaceAll(" ", "").replaceAll(",", ", ");

		return ec_number;
	}
}
