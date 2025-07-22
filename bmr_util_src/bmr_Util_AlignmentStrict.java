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

public class bmr_Util_AlignmentStrict {

	protected int match_reward = 1;
	protected int mismatch_penalty = -1;
	protected int gap_open_cost = 0;
	protected int gap_ext_cost = 0;
	protected int new_gap_cost = gap_open_cost + gap_ext_cost;
	protected int large_number = 1000000;

	private int n = 0, m = 0;

	private int[][] r = null, t = null, s = null;

	public String S1 = null, S2 = null;
	public int score = -large_number;

	bmr_Util_AlignmentStrict(char[] s1, char[] s2) {

		n = s1.length + 1;
		m = s2.length + 1;

		r = new int [n][m];
		t = new int [n][m];
		s = new int [n][m];

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
				s[i][j] = max3(s[i - 1][j - 1] + (s1[i - 1] == s2[j - 1] ? match_reward : mismatch_penalty), r[i][j], t[i][j]);
			}
		}

		i = n - 1;
		j = m - 1;

		while (i > 0 || j > 0) {

			if (s[i][j] == r[i][j]) {
				S1 = S1.substring(0, i) + "?" + S1.substring(i, S1.length());
				j--;
			} else if (s[i][j] == t[i][j]) {
				S2 = S2.substring(0, j) + "?" + S2.substring(j, S2.length());
				i--;
			} else {
				i--; j--;
			}
		}

		score = s[n - 1][m - 1];
	}

	private int max3(int x, int y, int z) {
		return x > y ? Math.max(x,z) : Math.max(y,z);
	}
}