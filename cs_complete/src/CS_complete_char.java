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

public class CS_complete_char {

	int Sf_ID = 0;
	String Entity_assembly_ID = "", Entity_ID, Comp_index_ID, Comp_ID;
	String Chem_shift_coverage = "";
	String Chem_shift_1H_coverage = "";
	String Chem_shift_13C_coverage = "";
	String Chem_shift_15N_coverage = "";
	String Chem_shift_31P_coverage = "";
	String Bb_chem_shift_coverage = "";
	String Bb_chem_shift_1H_coverage = "";
	String Bb_chem_shift_13C_coverage = "";
	String Bb_chem_shift_15N_coverage = "";
	String Bb_chem_shift_31P_coverage = "";
	String Sc_chem_shift_coverage = "";
	String Sc_chem_shift_1H_coverage = "";
	String Sc_chem_shift_13C_coverage = "";
	String Sc_chem_shift_15N_coverage = "";
	String Arom_chem_shift_coverage = "";
	String Arom_chem_shift_1H_coverage = "";
	String Arom_chem_shift_13C_coverage = "";
	String Arom_chem_shift_15N_coverage = "";
	String Methyl_chem_shift_coverage = "";
	String Methyl_chem_shift_1H_coverage = "";
	String Methyl_chem_shift_13C_coverage = "";
	String Entry_ID, Assigned_chem_shift_list_ID;

	public CS_complete_char(int _Sf_ID) {
		Sf_ID = _Sf_ID;
	}

	public void setEntity_assembly_ID(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Entity_assembly_ID = string;
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

	public void setChem_shift_coverage(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Chem_shift_coverage = string;
	}

	public void setChem_shift_1H_coverage(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Chem_shift_1H_coverage = string;
	}

	public void setChem_shift_13C_coverage(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Chem_shift_13C_coverage = string;
	}

	public void setChem_shift_15N_coverage(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Chem_shift_15N_coverage = string;
	}

	public void setChem_shift_31P_coverage(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Chem_shift_31P_coverage = string;
	}

	public void setBb_chem_shift_coverage(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Bb_chem_shift_coverage = string;
	}

	public void setBb_chem_shift_1H_coverage(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Bb_chem_shift_1H_coverage = string;
	}

	public void setBb_chem_shift_13C_coverage(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Bb_chem_shift_13C_coverage = string;
	}

	public void setBb_chem_shift_15N_coverage(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Bb_chem_shift_15N_coverage = string;
	}

	public void setBb_chem_shift_31P_coverage(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Bb_chem_shift_31P_coverage = string;
	}

	public void setSc_chem_shift_coverage(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Sc_chem_shift_coverage = string;
	}

	public void setSc_chem_shift_1H_coverage(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Sc_chem_shift_1H_coverage = string;
	}

	public void setSc_chem_shift_13C_coverage(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Sc_chem_shift_13C_coverage = string;
	}

	public void setSc_chem_shift_15N_coverage(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Sc_chem_shift_15N_coverage = string;
	}

	public void setArom_chem_shift_coverage(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Arom_chem_shift_coverage = string;
	}

	public void setArom_chem_shift_1H_coverage(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Arom_chem_shift_1H_coverage = string;
	}

	public void setArom_chem_shift_13C_coverage(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Arom_chem_shift_13C_coverage = string;
	}

	public void setArom_chem_shift_15N_coverage(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Arom_chem_shift_15N_coverage = string;
	}

	public void setMethyl_chem_shift_coverage(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Methyl_chem_shift_coverage = string;
	}

	public void setMethyl_chem_shift_1H_coverage(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Methyl_chem_shift_1H_coverage = string;
	}

	public void setMethyl_chem_shift_13C_coverage(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Methyl_chem_shift_13C_coverage = string;
	}

	public void setEntry_ID(String string) {
		Entry_ID = string;
	}

	public void setAssigned_chem_shift_list_ID(String string) {
		Assigned_chem_shift_list_ID = string;
	}

	public static void write_cvs_header(FileWriter cs_complete_char_csv) {

		try {
			cs_complete_char_csv.write("Sf_ID,Entity_assembly_ID,Entity_ID,Comp_index_ID,Comp_ID,"
					+ "Chem_shift_coverage,Chem_shift_1H_coverage,Chem_shift_13C_coverage,Chem_shift_15N_coverage,Chem_shift_31P_coverage,"
					+ "Bb_chem_shift_coverage,Bb_chem_shift_1H_coverage,Bb_chem_shift_13C_coverage,Bb_chem_shift_15N_coverage,Bb_chem_shift_31P_coverage,"
					+ "Sc_chem_shift_coverage,Sc_chem_shift_1H_coverage,Sc_chem_shift_13C_coverage,Sc_chem_shift_15N_coverage,"
					+ "Arom_chem_shift_coverage,Arom_chem_shift_1H_coverage,Arom_chem_shift_13C_coverage,Arom_chem_shift_15N_coverage,"
					+ "Methyl_chem_shift_coverage,Methyl_chem_shift_1H_coverage,Methyl_chem_shift_13C_coverage,"
					+ "Entry_ID,Assigned_chem_shift_list_ID\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void write_csv(FileWriter cs_complete_char_csv) {

		try {
			cs_complete_char_csv.write(Sf_ID + "," + Entity_assembly_ID + "," + Entity_ID + "," + Comp_index_ID + "," + Comp_ID + ","
					+ Chem_shift_coverage + "," + Chem_shift_1H_coverage + "," + Chem_shift_13C_coverage + "," + Chem_shift_15N_coverage + "," + Chem_shift_31P_coverage + ","
					+ Bb_chem_shift_coverage + "," + Bb_chem_shift_1H_coverage + "," + Bb_chem_shift_13C_coverage + "," + Bb_chem_shift_15N_coverage + "," + Bb_chem_shift_31P_coverage + ","
					+ Sc_chem_shift_coverage + "," + Sc_chem_shift_1H_coverage + "," + Sc_chem_shift_13C_coverage + "," + Sc_chem_shift_15N_coverage + ","
					+ Arom_chem_shift_coverage + "," + Arom_chem_shift_1H_coverage + "," + Arom_chem_shift_13C_coverage + "," + Arom_chem_shift_15N_coverage + ","
					+ Methyl_chem_shift_coverage + "," + Methyl_chem_shift_1H_coverage + "," + Methyl_chem_shift_13C_coverage + ","
					+ Entry_ID + "," + Assigned_chem_shift_list_ID + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
