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

import org.apache.commons.lang3.StringEscapeUtils;

public class PB_list {

	int Sf_ID;
	String Sf_category, Sf_framecode;
	int ID;
	String Query_ID, Queried_date, Input_file_name, Output_file_name, Electronic_address;
	String AA_seq_one_letter_code, PB_seq_code;
	String PDB_ID;
	String PDBX_exptl_method = "";
	String PDBX_NMR_refine_method = "";
	String PDBX_refine_ls_R_factor_R_free = "", PDBX_refine_ls_R_factor_R_work = "";
	String PDBX_refine_ls_d_res_high = "", PDBX_refine_ls_d_res_low = "";
	String Entry_ID;

	public void setSf_category(String string) {
		Sf_category = string;
	}

	public void setID(String string) {
		ID = Integer.valueOf(string);
	}

	public void setQuery_ID(String string) {
		Query_ID = string;
	}

	public void setQueried_date(String string) {
		Queried_date = string;
	}

	public void setInput_file_name(String string) {
		Input_file_name = string;
	}

	public void setOutput_file_name(String string) {
		Output_file_name = string;
	}

	public void setElectronic_address(String string) {
		Electronic_address = string;
	}

	public void setAA_seq_one_letter_code(String string) {
		AA_seq_one_letter_code = string;
	}

	public void setPB_seq_code(String string) {
		PB_seq_code = string;
	}

	public void setPDB_ID(String string) {
		PDB_ID = string;
	}

	public void setPDBX_exptl_method(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			PDBX_exptl_method = string;
	}

	public void setPDBX_NMR_refine_method(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			PDBX_NMR_refine_method = string;

	}

	public void setPDBX_refine_ls_R_factor_R_free(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			PDBX_refine_ls_R_factor_R_free = string;
	}

	public void setPDBX_refine_ls_R_factor_R_work(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			PDBX_refine_ls_R_factor_R_work = string;
	}

	public void setPDBX_refine_ls_d_res_high(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			PDBX_refine_ls_d_res_high = string;
	}

	public void setPDBX_refine_ls_d_res_low(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			PDBX_refine_ls_d_res_low = string;
	}

	public void setEntry_ID(String string) {
		Entry_ID = string;
	}

	public static void write_cvs_header(FileWriter pb_list_csv) {

		try {
			pb_list_csv.write("Sf_ID,Sf_category,Sf_framecode,ID,Query_ID,Queried_date,Input_file_name,Output_file_name,Electronic_address,AA_seq_one_letter_code,PB_seq_code,PDB_ID,PDBX_exptl_method,PDBX_NMR_refine_method,PDBX_refine_ls_R_factor_R_free,PDBX_refine_ls_R_factor_R_work,PDBX_refine_ls_d_res_high,PDBX_refine_ls_d_res_low,Entry_ID\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void write_csv(FileWriter pb_list_csv) {

		try {
			pb_list_csv.write(Sf_ID + "," + Sf_category + "," + Sf_framecode + "," + ID + "," + Query_ID + "," + Queried_date + "," + Input_file_name + "," + Output_file_name + "," + Electronic_address + "," + AA_seq_one_letter_code + "," + PB_seq_code + "," + PDB_ID + "," + StringEscapeUtils.escapeCsv(PDBX_exptl_method) + "," + StringEscapeUtils.escapeCsv(PDBX_NMR_refine_method) + "," + PDBX_refine_ls_R_factor_R_free + "," + PDBX_refine_ls_R_factor_R_work + "," + PDBX_refine_ls_d_res_high + "," + PDBX_refine_ls_d_res_low + "," + Entry_ID + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}