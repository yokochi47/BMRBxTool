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

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PBE_result {

	boolean valid = false;

	int Model_Num = 0, Model_Num_Max = 0;
	char PDB_strand_ID = 'A';
	String ID, AA, PB;

	String Entity_assembly_ID;
	String Assembly_ID;
	String Entity_ID, PDB_Entity_ID;
	String Polymer_seq_one_letter_code;
	String Pdbx_seq_one_letter_code;
	char[] bmrb_aligned = null, pdb_aligned = null;
	char[] _pdb_aligned = null, _pb_aligned = null;

	public void align(PreparedStatement pstate, PreparedStatement pstate2, String entry_id, String pdb_id, File pdbml) throws IOException {

		PDB_Entity_ID = PDBML.extract_entity_id(pdbml, String.valueOf(PDB_strand_ID));
		Pdbx_seq_one_letter_code = PDBML.extract_seq_code(pdbml, String.valueOf(PDB_strand_ID));
		String _polymer_seq_one_letter_code = "";

		char[] pdb_seq = Pdbx_seq_one_letter_code.toUpperCase().toCharArray();

		try {

			ResultSet rset = pstate.executeQuery();

			double score_max = 0.0;
			double _score_max = -1000000.0;

			while (rset.next()) {

				String entity_id = rset.getString("Entity_ID");

				if (entity_id == null || entity_id.isEmpty())
					continue;

				pstate2.setString(2, entity_id);

				String polymer_seq_one_letter_code = EntityCompIndex.get_polymer_seq_one_letter_code(pstate2);

				if (polymer_seq_one_letter_code == null || polymer_seq_one_letter_code.isEmpty())
					continue;

				char[] bmrb_seq = polymer_seq_one_letter_code.toCharArray();

//				System.out.println(pdb_seq);
//				System.out.println(bmrb_seq);

				alignment align = new alignment(pdb_seq, bmrb_seq);

//				System.out.println(align.score);

				if (align.score > score_max) {

					score_max = align.score;

					Entity_assembly_ID = rset.getString("ID");
					Assembly_ID = rset.getString("Assembly_ID");
					Entity_ID = entity_id;
					Polymer_seq_one_letter_code = polymer_seq_one_letter_code;
					pdb_aligned = align.S1.toCharArray();
					bmrb_aligned = align.S2.toCharArray();

					char[] pb_seq = AA.toCharArray();

					align = new alignment(pdb_seq, pb_seq);

					_pdb_aligned = align.S1.toCharArray();
					_pb_aligned = align.S2.toCharArray();

				}

				if (align.score > _score_max) {

					_score_max = align.score;
					_polymer_seq_one_letter_code = polymer_seq_one_letter_code;

				}

			}

			rset.close();

			if (score_max <= 0.0) {

				valid = false;

				if (Model_Num == 1) {

					System.err.println(entry_id + " unmatched with " + pdb_id + ".");

					char[] _bmrb_seq = _polymer_seq_one_letter_code.toCharArray();

					alignment align = new alignment(pdb_seq, _bmrb_seq);

					System.err.println(align.S1);
					System.err.println(align.S2);

				}

			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(PBE_result.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		}

	}

}
