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

import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PB_char {

	int Sf_ID = 0;
    String Entity_assembly_ID, Assembly_ID, Entity_ID, Comp_index_ID, Comp_ID;
    String PDB_model_num;
    char PDB_strand_ID;
    char PDB_ins_code = '.';
    String PDB_residue_no, PDB_residue_name;
    char PB_code = '.';
    int Align = 0;
    String Entry_ID;
    int PB_list_ID;
    
	public PB_char(PreparedStatement pstate, String entry_id, String pdb_id, int pb_list_id, PBE_result result, List<PB_char> pb_chars, int id) {

		Entity_assembly_ID = result.Entity_assembly_ID;
		Assembly_ID = result.Assembly_ID;
		Entity_ID = result.Entity_ID;
		Comp_index_ID = String.valueOf(id + 1);
		Comp_ID = get_comp_id(pstate, Comp_index_ID);

		PDB_model_num = (result.Model_Num_Max > 1 ? String.valueOf(result.Model_Num) : ".");
		PDB_strand_ID = result.PDB_strand_ID;

		PDB_residue_no = get_pdb_residue_no(result, id + 1, true);
		PDB_residue_name = ".";

		Align = (PDB_residue_no.equals(".") ? 0 : 1);

		if (Align == 0) {

			String _PDB_residue_no = get_pdb_residue_no(result, id + 1, false);

			if (id == 0 || (id > 0 && !_PDB_residue_no.equals(get_pdb_residue_no(result, id, false))))
				PDB_residue_no = _PDB_residue_no;

		}

		if (!PDB_residue_no.equals("."))
			PB_code = get_pb_code(result, Integer.valueOf(PDB_residue_no));

		if (PB_code == 'x' || PB_code == 'z')
			PB_code = '.';

		Entry_ID = entry_id;
		PB_list_ID = pb_list_id;

		pb_chars.add(this);

	}

	public PB_char(int _Sf_ID) {
		Sf_ID = _Sf_ID;
	}

	public void setEntity_assembly_ID(String string) {
		Entity_assembly_ID = string;
	}
	
	public void setAssembly_ID(String string) {
		Assembly_ID = string;
	}

	public void setEntity_ID(String string) {
		Entity_ID = string;
	}

	public void setComp_index_ID(String string) {
		Comp_index_ID = string;
	}

	public void setComp_ID(String string) {
		Comp_ID = string;
	}

	public void setPDB_model_num(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			PDB_model_num = string;
		else
			PDB_model_num = "";
	}

	public void setPDB_strand_ID(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			PDB_strand_ID = string.charAt(0);
		else
			PDB_strand_ID = '\0';
	}

	public void setPDB_ins_code(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			PDB_ins_code = string.charAt(0);
		else
			PDB_ins_code = '\0';
	}

	public void setPDB_residue_no(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			PDB_residue_no = string;
		else
			PDB_residue_no = "";
	}

	public void setPDB_residue_name(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			PDB_residue_name = string;
		else
			PDB_residue_name = "";
	}

	public void setPB_code(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			PB_code = string.charAt(0);
		else
			PB_code = '\0';
	}

	public void setAlign(String string) {
		Align = Integer.valueOf(string);
	}

	public void setEntry_ID(String string) {
		Entry_ID = string;
	}

	public void setPB_list_ID(String string) {
		PB_list_ID = Integer.valueOf(string);
	}

	private String get_comp_id(PreparedStatement pstate, String comp_index_id) {

		String Comp_ID = ".";

		try {

			pstate.setString(3, comp_index_id);

			ResultSet rset = pstate.executeQuery();

			while (rset.next()) {
				Comp_ID = rset.getString("Comp_ID");
				break;
			}

			rset.close();

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(PB_char.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		}

		return Comp_ID;
	}

	private String get_pdb_residue_no(PBE_result result, int id, boolean comp_id_check) {

		int bmrb_align_pos = 0;

		for (int bmrb_seq_id = 0; bmrb_align_pos < result.bmrb_aligned.length; bmrb_align_pos++) {

			if (result.bmrb_aligned[bmrb_align_pos] != '?') {

				if (++bmrb_seq_id == id)
					break;

			}

		}

		if (bmrb_align_pos >= result.bmrb_aligned.length || bmrb_align_pos >= result.pdb_aligned.length)
			return ".";

		int _id = 0;
		int pdb_align_pos = 0;

		for (; pdb_align_pos <= bmrb_align_pos; pdb_align_pos++) {

			if (result.pdb_aligned[pdb_align_pos] != '?')
				_id++;

		}

		if (comp_id_check && result.bmrb_aligned[bmrb_align_pos] != result.pdb_aligned[bmrb_align_pos])
			return ".";

		if (_id < 1 || (!comp_id_check && _id >= result.Pdbx_seq_one_letter_code.length()))
			return ".";

		return String.valueOf(_id);
	}

	private char get_pb_code(PBE_result result, int id) {

		int pdb_align_pos = 0;

		for (int pdb_seq_id = 0; pdb_align_pos < result._pdb_aligned.length; pdb_align_pos++) {

			if (result._pdb_aligned[pdb_align_pos] != '?') {

				if (++pdb_seq_id == id)
					break;

			}

		}

		if (pdb_align_pos >= result._pdb_aligned.length || pdb_align_pos >= result._pb_aligned.length)
			return '.';

		int _id = 0;
		int pb_align_pos = 0;

		for (; pb_align_pos <= pdb_align_pos; pb_align_pos++) {

			if (result._pb_aligned[pb_align_pos] != '?')
				_id++;

		}

		if (result._pdb_aligned[pdb_align_pos] != result._pb_aligned[pdb_align_pos])
			return '.';

		if (_id - 1 >= result.PB.length())
			return '.';

		return result.PB.charAt(_id - 1);
	}

	public static String toString(PB_char item, PB_char_max_len len) {

		StringBuffer buff = new StringBuffer();
		String form = null;

		buff.append("    ");

		form = "%" + len.Entity_assembly_ID + "s ";
		buff.append(String.format(form, item.Entity_assembly_ID));

		form = "%" + len.Assembly_ID + "s ";
		buff.append(String.format(form, item.Assembly_ID));

		form = "%" + len.Entity_ID + "s ";
		buff.append(String.format(form, item.Entity_ID));

		form = "%" + len.Comp_index_ID + "s ";
		buff.append(String.format(form, item.Comp_index_ID));

		form = "%-" + (len.Comp_ID + 1) + "s";
		buff.append(String.format(form, item.Comp_ID));

		form = "%" + len.PDB_model_num + "s ";
		buff.append(String.format(form, item.PDB_model_num));

		form = "%" + len.PDB_strand_ID + "s ";
		buff.append(String.format(form, item.PDB_strand_ID));

		form = "%" + len.PDB_ins_code + "s ";
		buff.append(String.format(form, item.PDB_ins_code));

		form = "%" + len.PDB_residue_no + "s ";
		buff.append(String.format(form, item.PDB_residue_no));

		form = "%-" + (len.PDB_residue_name + 1) + "s";
		buff.append(String.format(form, item.PDB_residue_name));

		form = "%" + len.PB_code + "s ";
		buff.append(String.format(form, item.PB_code));

		form = "%" + len.Align + "d ";
		buff.append(String.format(form, item.Align));

		form = "%" + len.Entry_ID + "s ";
		buff.append(String.format(form, item.Entry_ID));

		form = "%" + len.PB_list_ID + "s ";
		buff.append(String.format(form, item.PB_list_ID));

		return buff.toString();
	}

	public static void write_cvs_header(FileWriter pb_char_csv) {

		try {
			pb_char_csv.write("Sf_ID,Entity_assembly_ID,Assembly_ID,Entity_ID,Comp_index_ID,Comp_ID,PDB_model_num,PDB_strand_ID,PDB_ins_code,PDB_residue_no,PDB_residue_name,PB_code,Align,Entry_ID,PB_list_ID\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void write_csv(FileWriter pb_char_csv) {

		try {
			pb_char_csv.write(Sf_ID + "," + Entity_assembly_ID + "," + Assembly_ID + "," + Entity_ID + "," + Comp_index_ID + "," + Comp_ID + "," + PDB_model_num + "," + (PDB_strand_ID != '\0' ? PDB_strand_ID : "") + "," + (PDB_ins_code != '\0' ? PDB_ins_code : "") + "," + PDB_residue_no + "," + PDB_residue_name + "," + (PB_code != '\0' ? PB_code : "") + "," + Align + "," + Entry_ID + "," + PB_list_ID + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
