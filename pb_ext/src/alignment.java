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

class alignment {

//	protected final double match_reward = 1.0;
//	protected final double mismatch_penalty = -1.0;
	protected final double gap_open_cost = 10.0;
	protected final double gap_ext_cost = 0.1;
	protected final double new_gap_cost = gap_open_cost + gap_ext_cost;
	protected final double large_number = 1000000.0;

	protected final double BLOSUM[][] =
		{ // the blosum 62 scoring matrix
			{4, 0, 0, -2, -1, -2, 0, -2, -1, 0, -1, -1, -1, -2, 0, -1, -1, -1, 1, 0, 0, 0, -3, 0, -2}, // A
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 9, -3, -4, -2, -3, -3, -1, 0, -3, -1, -1, -3, 0, -3, -3, -3, -1, -1, 0, -1, -2, 0, -2}, // C
			{-2, 0, -3, 6, 2, -3, -1, -1, -3, 0, -1, -4, -3, 1, 0, -1, 0, -2, 0, -1, 0, -3, -4, 0, -3}, // D
			{-1, 0, -4, 2, 5, -3, -2, 0, -3, 0, 1, -3, -2, 0, 0, -1, 2, 0, 0, -1, 0, -2, -3, 0, -2}, // E
			{-2, 0, -2, -3, -3, 6, -3, -1, 0, 0, -3, 0, 0, -3, 0, -4, -3, -3, -2, -2, 0, -1, 1, 0, 3}, // F
			{0, 0, -3, -1, -2, -3, 6, -2, -4, 0, -2, -4, -3, 0, 0, -2, -2, -2, 0, -2, 0, -3, -2, 0, -3}, // G
			{-2, 0, -3, -1, 0, -1, -2, 8, -3, 0, -1, -3, -2, 1, 0, -2, 0, 0, -1, -2, 0, -3, -2, 0, 2}, // H
			{-1, 0, -1, -3, -3, 0, -4, -3, 4, 0, -3, 2, 1, -3, 0, -3, -3, -3, -2, -1, 0, 3, -3, 0, -1}, // I
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{-1, 0, -3, -1, 1, -3, -2, -1, -3, 0, 5, -2, -1, 0, 0, -1, 1, 2, 0, -1, 0, -2, -3, 0, -2}, // K
			{-1, 0, -1, -4, -3, 0, -4, -3, 2, 0, -2, 4, 2, -3, 0, -3, -2, -2, -2, -1, 0, 1, -2, 0, -1}, // L
			{-1, 0, -1, -3, -2, 0, -3, -2, 1, 0, -1, 2, 5, -2, 0, -2, 0, -1, -1, -1, 0, 1, -1, 0, -1}, // M
			{-2, 0, -3, 1, 0, -3, 0, 1, -3, 0, 0, -3, -2, 6, 0, -2, 0, 0, 1, 0, 0, -3, -4, 0, -2}, // N
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{-1, 0, -3, -1, -1, -4, -2, -2, -3, 0, -1, -3, -2, -2, 0, 7, -1, -2, -1, -1, 0, -2, -4, 0, -3}, // P
			{-1, 0, -3, 0, 2, -3, -2, 0, -3, 0, 1, -2, 0, 0, 0, -1, 5, 1, 0, -1, 0, -2, -2, 0, -1}, // Q
			{-1, 0, -3, -2, 0, -3, -2, 0, -3, 0, 2, -2, -1, 0, 0, -2, 1, 5, -1, -1, 0, -3, -3, 0, -2}, // R
			{1, 0, -1, 0, 0, -2, 0, -1, -2, 0, 0, -2, -1, 1, 0, -1, 0, -1, 4, 1, 0, -2, -3, 0, -2}, // S
			{0, 0, -1, -1, -1, -2, -2, -2, -1, 0, -1, -1, -1, 0, 0, -1, -1, -1, 1, 5, 0, 0, -2, 0, -2}, // T
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, -1, -3, -2, -1, -3, -3, 3, 0, -2, 1, 1, -3, 0, -2, -2, -3, -2, 0, 0, 4, -3, 0, -1}, // V
			{-3, 0, -2, -4, -3, 1, -2, -2, -3, 0, -3, -2, -1, -4, 0, -4, -2, -3, -3, -2, 0, -3, 11, 0, 2}, // W
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, // X for uncommon residues
			{-2, 0, -2, -3, -2, 3, -3, 2, -1, 0, -2, -1, -1, -2, 0, -3, -1, -2, -2, -2, 0, -1, 2, 0, 7} // Y
		};

	private int n = 0, m = 0;

	private double[][] r = null, t = null, s = null;

	public String S1 = null, S2 = null;
	public double score = -large_number;
	public int gap = 0;

	alignment(char[] s1, char[] s2) {

		n = s1.length + 1;
		m = s2.length + 1;

		r = new double [n][m];
		t = new double [n][m];
		s = new double [n][m];

		S1 = String.valueOf(s1);
		S2 = String.valueOf(s2);

		int i, j;

		r[0][0] = t[0][0] = s[0][0] = 0;

		for (i = 0; i < n; i++) {
			r[i][0] = -large_number;
			s[i][0] = t[i][0] = -gap_open_cost - i * gap_ext_cost;
		}

		for (j = 1; j < m; j++) {
			t[0][j] = -large_number;
			s[0][j] = r[0][j] = -gap_open_cost - j * gap_ext_cost;
		}

		for (i = 1; i < n; i++) {
			for (j = 1; j < m; j++) {
				r[i][j] = Math.max(r[i][j - 1] - gap_ext_cost, s[i][j - 1] - new_gap_cost);
				t[i][j] = Math.max(t[i - 1][j] - gap_ext_cost, s[i - 1][j] - new_gap_cost);
				// blosum 62 matching
		        s[i][j] = max3(s[i - 1][j - 1] + (s1[i - 1] - 'A' >= 0 && s2[j - 1] - 'A' >= 0 ? BLOSUM[s1[i - 1] - 'A'][s2[j - 1] - 'A'] : 0), r[i][j], t[i][j]);
		        // strict matching
//				s[i][j] = max3(s[i - 1][j - 1] + (s1[i - 1] == s2[j - 1] ? match_reward : mismatch_penalty), r[i][j], t[i][j]);
			}
		}

		gap = 0;

		i = n - 1;
		j = m - 1;

		while (i > 0 || j > 0) {

			if (s[i][j] == r[i][j]) {
				S1 = S1.substring(0, i) + "?" + S1.substring(i, S1.length());
				gap++;
				j--;
			} else if (s[i][j] == t[i][j]) {
				S2 = S2.substring(0, j) + "?" + S2.substring(j, S2.length());
				gap++;
				i--;
			} else {
				i--; j--;
			}
		}

		score = s[n - 1][m - 1];

		r = null;
		t = null;
		s = null;

	}

	private double max3(double x, double y, double z) {
		return x > y ? Math.max(x,z) : Math.max(y,z);
	}

}
