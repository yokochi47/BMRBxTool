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

public enum dna_one_letter_codes {

	A, C, G, T;

	private static EnumMap<dna_one_letter_codes, dna_seq_codes> seq_one_letter_map;

	static {

		seq_one_letter_map = new EnumMap<dna_one_letter_codes, dna_seq_codes>(dna_one_letter_codes.class);

		seq_one_letter_map.put(A, dna_seq_codes.DA);
		seq_one_letter_map.put(C, dna_seq_codes.DC);
		seq_one_letter_map.put(G, dna_seq_codes.DG);
		seq_one_letter_map.put(T, dna_seq_codes.DT);

	}

	public static dna_seq_codes convert(dna_one_letter_codes code) {

		try {

			if (code == null)
				return null;

			return seq_one_letter_map.get(code);

		} catch (IllegalArgumentException e) {
			return null;
		}

	}

}
