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

public enum rna_two_letter_codes {

	RA, RC, RG, RU;

	private static EnumMap<rna_two_letter_codes, rna_seq_codes> seq_two_letter_map;

	static {

		seq_two_letter_map = new EnumMap<rna_two_letter_codes, rna_seq_codes>(rna_two_letter_codes.class);

		seq_two_letter_map.put(RA, rna_seq_codes.A);
		seq_two_letter_map.put(RC, rna_seq_codes.C);
		seq_two_letter_map.put(RG, rna_seq_codes.G);
		seq_two_letter_map.put(RU, rna_seq_codes.U);

	}

	public static rna_seq_codes convert(rna_two_letter_codes code) {

		try {

			if (code == null)
				return null;

			return seq_two_letter_map.get(code);

		} catch (IllegalArgumentException e) {
			return null;
		}

	}

}
