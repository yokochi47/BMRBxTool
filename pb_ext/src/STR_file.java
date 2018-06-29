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
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class STR_file {

	public static void write(Connection conn_bmrb, String entry_id, String pdb_id, File pdb, File pdbml, File pb, File str, WhiteList white, List<PBE_result> pbe_results) throws IOException {

		boolean exist = false;

		for (PBE_result elem : pbe_results) {

			if (elem.valid) {
				exist = true;
				break;
			}

		}

		if (!exist)
			return;

		boolean aligned = true;
		float coverage = (float) 1.0;

		System.out.print(".");

		FileWriter strw = new FileWriter(str);

		strw.write("data_PB\n\n");

		strw.write("        #############################\n");
		strw.write("        # Protein Blocks Annotation #\n");
		strw.write("        #############################\n\n");

		strw.write(" #######################################################################################\n");
		strw.write(" # PB encoding by Protein Blocks Expert 2.0 server (http://www.bo-protscience.fr/pbe/) #\n");
		strw.write(" # Reference: Biophys Rev. 2010 Aug;2(3):137-147. Epub 2010 Aug 5.                     #\n");
		strw.write(" #          : Nucleic Acids Res. 2006 Jul 1;34(Web Server issue):W119-23.              #\n");
		strw.write(" #######################################################################################\n");

		try {

			PreparedStatement pstate = conn_bmrb.prepareStatement("select \"Comp_ID\" from \"Entity_comp_index\" where \"Entry_ID\" = ? and \"Entity_ID\" = ? and \"ID\" = ?");
			pstate.setString(1, entry_id);

			int list_id = 1;

			String exptl_method = PDBML.extract_exptl_method(pdbml);
			String nmr_refine_method = ".";
			String refine_ls_R_factor_R_free = ".";
			String refine_ls_R_factor_R_work = ".";
			String refine_ls_d_res_high = ".";
			String refine_ls_d_res_low = ".";

			if (exptl_method.contains("NMR"))
				nmr_refine_method = PDBML.extract_nmr_refine_method(pdbml);

			else if (exptl_method.contains("DIFFRACTION")) {

				PB_list rep_pb_list = new PB_list();

				PDBML.extract_ls_values(pdbml, rep_pb_list);

				refine_ls_R_factor_R_free = rep_pb_list.PDBX_refine_ls_R_factor_R_free;
				refine_ls_R_factor_R_work = rep_pb_list.PDBX_refine_ls_R_factor_R_work;

				refine_ls_d_res_high = rep_pb_list.PDBX_refine_ls_d_res_high;
				refine_ls_d_res_low = rep_pb_list.PDBX_refine_ls_d_res_low;

			}

			for (int elem_id = 0; elem_id < pbe_results.size(); elem_id++) {

				PBE_result elem = pbe_results.get(elem_id);

				if (!elem.valid)
					continue;

				PB_list pb_list = new PB_list();

				SimpleDateFormat simple_date = new SimpleDateFormat("yyyy-MM-dd");
				Date last_modified = new Date(pb.lastModified());

				pb_list.Sf_category = "PB_list";
				pb_list.Sf_framecode = "PB_annotation_" + list_id;

				pb_list.ID = list_id;
				pb_list.Query_ID = elem.ID;
				pb_list.Queried_date = simple_date.format(last_modified);
				pb_list.Input_file_name = pdb.getName();
				pb_list.Output_file_name = str.getName();
				pb_list.Electronic_address = "https://bmrbpub.pdbj.org/archive/pb/" + pb_list.Output_file_name;
				pb_list.AA_seq_one_letter_code = elem.AA;
				pb_list.PB_seq_code = elem.PB;
				pb_list.PDB_ID = pdb_id;
				pb_list.PDBX_exptl_method = exptl_method;
				pb_list.PDBX_NMR_refine_method = nmr_refine_method;
				pb_list.PDBX_refine_ls_R_factor_R_free = refine_ls_R_factor_R_free;
				pb_list.PDBX_refine_ls_R_factor_R_work = refine_ls_R_factor_R_work;
				pb_list.PDBX_refine_ls_d_res_high = refine_ls_d_res_high;
				pb_list.PDBX_refine_ls_d_res_low = refine_ls_d_res_low;
				pb_list.Entry_ID = entry_id;

				strw.write("\n\nsave_" + pb_list.Sf_framecode + "\n");

				strw.write("    _PB_list.Sf_category                    " + pb_list.Sf_category + "\n");
				strw.write("    _PB_list.ID                             " + pb_list.ID + "\n");
				strw.write("    _PB_list.Query_ID                       " + pb_list.Query_ID + "\n");
				strw.write("    _PB_list.Queried_date                   " + pb_list.Queried_date + "\n");
				strw.write("    _PB_list.Input_file_name                " + pb_list.Input_file_name + "\n");
				strw.write("    _PB_list.Output_file_name               " + pb_list.Output_file_name + "\n");
				strw.write("    _PB_list.Electronic_address             " + pb_list.Electronic_address + "\n");
				strw.write("    _PB_list.AA_seq_one_letter_code         " + pb_list.AA_seq_one_letter_code + "\n");
				strw.write("    _PB_list.PB_seq_code                    " + pb_list.PB_seq_code + "\n");
				strw.write("    _PB_list.PDB_ID                         " + pb_list.PDB_ID + "\n");

				strw.write("    _PB_list.PDBX_exptl_method              \"" + pb_list.PDBX_exptl_method + "\"\n");

				if (exptl_method.contains("NMR") && !pb_list.PDBX_NMR_refine_method.isEmpty() && !pb_list.PDBX_NMR_refine_method.equals("."))
				strw.write("    _PB_list.PDBX_NMR_refine_method         \"" + pb_list.PDBX_NMR_refine_method + "\"\n");
				else if (exptl_method.contains("DIFFRACTION")) {
					if (!pb_list.PDBX_refine_ls_R_factor_R_free.isEmpty() && !pb_list.PDBX_refine_ls_R_factor_R_free.equals("."))
				strw.write("    _PB_list.PDBX_refine_ls_R_factor_R_free " + pb_list.PDBX_refine_ls_R_factor_R_free + "\n");
					if (!pb_list.PDBX_refine_ls_R_factor_R_work.isEmpty() && !pb_list.PDBX_refine_ls_R_factor_R_work.equals("."))
				strw.write("    _PB_list.PDBX_refine_ls_R_factor_R_work " + pb_list.PDBX_refine_ls_R_factor_R_work + "\n");
					if (!pb_list.PDBX_refine_ls_d_res_high.isEmpty() && !pb_list.PDBX_refine_ls_d_res_high.equals("."))
				strw.write("    _PB_list.PDBX_refine_ls_d_res_high      " + pb_list.PDBX_refine_ls_d_res_high + "\n");
					if (!pb_list.PDBX_refine_ls_d_res_low.isEmpty() && !pb_list.PDBX_refine_ls_d_res_low.equals("."))
				strw.write("    _PB_list.PDBX_refine_ls_d_res_low       " + pb_list.PDBX_refine_ls_d_res_low + "\n");
				}

				strw.write("    _PB_list.Entry_ID                       " + pb_list.Entry_ID + "\n\n");

				strw.write("    loop_\n");
				strw.write("    _PB_char.Entity_assembly_ID\n");
				strw.write("    _PB_char.Assembly_ID\n");
				strw.write("    _PB_char.Entity_ID\n");
				strw.write("    _PB_char.Comp_index_ID\n");
				strw.write("    _PB_char.Comp_ID\n");
				strw.write("    _PB_char.PDB_model_num\n");
				strw.write("    _PB_char.PDB_strand_ID\n");
				strw.write("    _PB_char.PDB_ins_code\n");
				strw.write("    _PB_char.PDB_residue_no\n");
				strw.write("    _PB_char.PDB_residue_name\n");
				strw.write("    _PB_char.PB_code\n");
				strw.write("    _PB_char.Align\n");
				strw.write("    _PB_char.Entry_ID\n");
				strw.write("    _PB_char.PB_list_ID\n\n");

				List<PB_char> pb_chars = new ArrayList<PB_char>();

				pb_chars.clear();

				pstate.setString(2, elem.Entity_ID);

				for (int i = 0; i < elem.Polymer_seq_one_letter_code.length(); i++)
					new PB_char(pstate, entry_id, pdb_id, pb_list.ID, elem, pb_chars, i);

				boolean _aligned = true;

				for (PB_char item : pb_chars) {

					if (item.Align == 0)
						aligned = _aligned = false;

				}

				if (!_aligned) {
					extend_no_align_code(pb_chars);

					float _coverage = pb_code_coverage(pb_list.PB_seq_code, pb_chars);

					if (_coverage == 0.0) {

						elem.valid = false;
						pbe_results.set(elem_id, elem);

					}

					if (_coverage < coverage)
						coverage = _coverage;

				}

				PDBML.extract_mon_id(pdbml, elem.PDB_Entity_ID, pb_chars);

				PB_char_max_len len = new PB_char_max_len(pb_chars);

				for (PB_char item : pb_chars)
					strw.write(PB_char.toString(item, len) + "\n");

				strw.write("    stop_\n\n");

				strw.write("save_\n");

				list_id++;
			}

			pstate.close();

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(STR_file.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		}

		strw.close();

		if (!aligned && coverage < 0.8) {

			if (white == null || (white != null && !white.matched(entry_id, pdb_id))) {

				String no_align_dir_name = str.getParent() + "/no_align";

				File no_align_dir = new File(no_align_dir_name);

				if (!no_align_dir.isDirectory()) {

					if (!no_align_dir.mkdir()) {
						System.err.println("Can't create directory '" + no_align_dir_name + "'.");
						System.exit(1);
					}

				}

				File no_align_str = new File(no_align_dir, str.getName());

				str.renameTo(no_align_str);

				if (coverage == 0.0) {

					exist = false;

					for (PBE_result elem : pbe_results) {

						if (elem.valid) {
							exist = true;
							break;
						}

					}

					no_align_str.delete();

					if (!exist)
						return;

					write(conn_bmrb, entry_id, pdb_id, pdb, pdbml, pb, str, white, pbe_results);

				}

			}

		}

	}

	private static void extend_no_align_code(List<PB_char> pb_chars) {

		for (int i = 0; i < pb_chars.size(); i++) {

			PB_char item = pb_chars.get(i);

			if (item.Align != 0)
				continue;

			for (int j = -2; j <= 2; j++) {

				int i2 = i + j;

				if (j == 0 || i2 < 0 || i2 >= pb_chars.size())
					continue;

				PB_char item2 = pb_chars.get(i2);

				if (item2.Align == 1)
					item2.Align = -1;

				pb_chars.set(i2, item2);

			}

		}

		for (int i = 0; i < pb_chars.size(); i++) {

			PB_char item = pb_chars.get(i);

			if (item.Align != -1)
				continue;

			item.Align = 0;

			pb_chars.set(i, item);

		}

	}

	private static float pb_code_coverage(String PB_seq_code, List<PB_char> pb_chars) {

		int pb_codes = 0;
		int pb_cov_codes = 0;

		char[] pb_seq_code = PB_seq_code.toCharArray();

		for (char pb_code : pb_seq_code) {

			if (pb_code == 'z')
				continue;

			pb_codes++;

		}

		for (int i = 0; i < pb_chars.size(); i++) {

			PB_char item = pb_chars.get(i);

			if (item.PB_code == '.')
				continue;

			if (item.Align == 0)
				continue;

			pb_cov_codes++;

		}

		return (pb_codes > 0 ? (float) pb_cov_codes / (float) pb_codes : (float) 1.0);
	}

}
