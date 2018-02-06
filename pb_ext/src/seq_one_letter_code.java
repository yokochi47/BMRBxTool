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

import java.util.EnumMap;

public class seq_one_letter_code {
	
	private enum comp_ids {
		 ALA, ARG, ASN, ASP, CYS, GLN, GLU, GLY, HIS, ILE, LEU, LYS, MET, PHE, PRO, SER, THR, TRP, TYR, VAL,
		 DG, DC, DA, DT, G, C, A, U
	}

	private static EnumMap<comp_ids, String> seq_one_letter_map;

	static {

		seq_one_letter_map = new EnumMap<comp_ids, String>(comp_ids.class);

		seq_one_letter_map.put(comp_ids.ALA, "A");
		seq_one_letter_map.put(comp_ids.ARG, "R");
		seq_one_letter_map.put(comp_ids.ASN, "N");
		seq_one_letter_map.put(comp_ids.ASP, "D");
		seq_one_letter_map.put(comp_ids.CYS, "C");
		seq_one_letter_map.put(comp_ids.GLN, "Q");
		seq_one_letter_map.put(comp_ids.GLU, "E");
		seq_one_letter_map.put(comp_ids.GLY, "G");
		seq_one_letter_map.put(comp_ids.HIS, "H");
		seq_one_letter_map.put(comp_ids.ILE, "I");
		seq_one_letter_map.put(comp_ids.LEU, "L");
		seq_one_letter_map.put(comp_ids.LYS, "K");
		seq_one_letter_map.put(comp_ids.MET, "M");
		seq_one_letter_map.put(comp_ids.PHE, "F");
		seq_one_letter_map.put(comp_ids.PRO, "P");
		seq_one_letter_map.put(comp_ids.SER, "S");
		seq_one_letter_map.put(comp_ids.THR, "T");
		seq_one_letter_map.put(comp_ids.TRP, "W");
		seq_one_letter_map.put(comp_ids.TYR, "Y");
		seq_one_letter_map.put(comp_ids.VAL, "V");
		
		seq_one_letter_map.put(comp_ids.DG, "G");
		seq_one_letter_map.put(comp_ids.DC, "C");
		seq_one_letter_map.put(comp_ids.DA, "A");
		seq_one_letter_map.put(comp_ids.DT, "T");
		
		seq_one_letter_map.put(comp_ids.G, "G");
		seq_one_letter_map.put(comp_ids.C, "C");
		seq_one_letter_map.put(comp_ids.A, "A");
		seq_one_letter_map.put(comp_ids.U, "U");

	}

	public static String getCode(String comp_id) {

		try {
			
			if (comp_id == null || comp_id.isEmpty())
				return "";

			comp_ids three_letter_code = comp_ids.valueOf(comp_id);
			return seq_one_letter_map.get(three_letter_code);

		} catch (IllegalArgumentException e) {
			return "X";
		}

	}
	
	public static String getCodeCan(String comp_id) {

		try {
			
			if (comp_id == null || comp_id.isEmpty())
				return "";

			comp_ids three_letter_code = comp_ids.valueOf(comp_id);
			return seq_one_letter_map.get(three_letter_code);

		} catch (IllegalArgumentException e) {
			if (comp_id.matches("^[0-9]{1,2}$"))
				return "X";
			return "(" + comp_id.toUpperCase() + ")";
		}

	}
 
}