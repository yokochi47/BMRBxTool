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

import java.util.List;

public class PB_char_max_len {

	int Sf_ID = 0;
    int Entity_assembly_ID = 0, Assembly_ID = 0, Entity_ID = 0, Comp_index_ID = 0, Comp_ID = 0;
    int PDB_model_num = 0;
    final int PDB_strand_ID = 1;
    final int PDB_ins_code = 1;
    int PDB_residue_no = 0, PDB_residue_name = 0;
    final int PB_code = 1;
    final int Align = 1;
    int Entry_ID = 0, PB_list_ID = 0;
    
	public PB_char_max_len(List<PB_char> pb_chars) {

		for (PB_char elem : pb_chars) {

			if (String.valueOf(elem.Sf_ID).length() > Sf_ID)
				Sf_ID = String.valueOf(elem.Sf_ID).length();

			if (elem.Entity_assembly_ID.length() > Entity_assembly_ID)
				Entity_assembly_ID = elem.Entity_assembly_ID.length();

			if (elem.Assembly_ID.length() > Assembly_ID)
				Assembly_ID = elem.Assembly_ID.length();

			if (elem.Entity_ID.length() > Entity_ID)
				Entity_ID = elem.Entity_ID.length();

			if (elem.Comp_index_ID.length() > Comp_index_ID)
				Comp_index_ID = elem.Comp_index_ID.length();

			if (elem.Comp_ID.length() > Comp_ID)
				Comp_ID = elem.Comp_ID.length();

			if (elem.PDB_model_num.length() > PDB_model_num)
				PDB_model_num = elem.PDB_model_num.length();

			if (elem.PDB_residue_no.length() > PDB_residue_no)
				PDB_residue_no = elem.PDB_residue_no.length();

			if (elem.PDB_residue_name.length() > PDB_residue_name)
				PDB_residue_name = elem.PDB_residue_name.length();

			if (elem.Entry_ID.length() > Entry_ID)
				Entry_ID = elem.Entry_ID.length();

			if (String.valueOf(elem.PB_list_ID).length() > PB_list_ID)
				PB_list_ID = String.valueOf(elem.PB_list_ID).length();

		}

	}
    
}