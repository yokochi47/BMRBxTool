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

public class CS_complete_list {

	int Sf_ID;
	String Sf_category, Sf_framecode;
	String Queried_date, Output_file_name, Electronic_address;
	String Assigned_residue_coverage = "";
	String Chem_shift_fraction = "";
	String Chem_shift_1H_fraction = "";
	String Chem_shift_13C_fraction = "";
	String Chem_shift_15N_fraction = "";
	String Chem_shift_31P_fraction = "";
	String Bb_chem_shift_fraction = "";
	String Bb_chem_shift_1H_fraction = "";
	String Bb_chem_shift_13C_fraction = "";
	String Bb_chem_shift_15N_fraction = "";
	String Bb_chem_shift_31P_fraction = "";
	String Sc_chem_shift_fraction = "";
	String Sc_chem_shift_1H_fraction = "";
	String Sc_chem_shift_13C_fraction = "";
	String Sc_chem_shift_15N_fraction = "";
	String Arom_chem_shift_fraction = "";
	String Arom_chem_shift_1H_fraction = "";
	String Arom_chem_shift_13C_fraction = "";
	String Arom_chem_shift_15N_fraction = "";
	String Methyl_chem_shift_fraction = "";
	String Methyl_chem_shift_1H_fraction = "";
	String Methyl_chem_shift_13C_fraction = "";
	String Entity_polymer_type = "";
	String Entry_ID, Assigned_chem_shift_list_ID;

	CS_complete_list(String output_file_name, String electronic_address) {
		Output_file_name = output_file_name;
		Electronic_address = electronic_address;
	}

	public void setSf_category(String string) {
		Sf_category = string;
	}

	public void setQueried_date(String string) {
		Queried_date = string;
	}

	public void setAssigned_residue_coverage(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Assigned_residue_coverage = string;
	}

	public void setChem_shift_fraction(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Chem_shift_fraction = string;
	}

	public void setChem_shift_1H_fraction(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Chem_shift_1H_fraction = string;
	}

	public void setChem_shift_13C_fraction(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Chem_shift_13C_fraction = string;
	}

	public void setChem_shift_15N_fraction(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Chem_shift_15N_fraction = string;
	}

	public void setChem_shift_31P_fraction(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Chem_shift_31P_fraction = string;
	}

	public void setBb_chem_shift_fraction(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Bb_chem_shift_fraction = string;
	}

	public void setBb_chem_shift_1H_fraction(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Bb_chem_shift_1H_fraction = string;
	}

	public void setBb_chem_shift_13C_fraction(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Bb_chem_shift_13C_fraction = string;
	}

	public void setBb_chem_shift_15N_fraction(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Bb_chem_shift_15N_fraction = string;
	}

	public void setBb_chem_shift_31P_fraction(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Bb_chem_shift_31P_fraction = string;
	}

	public void setSc_chem_shift_fraction(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Sc_chem_shift_fraction = string;
	}

	public void setSc_chem_shift_1H_fraction(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Sc_chem_shift_1H_fraction = string;
	}

	public void setSc_chem_shift_13C_fraction(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Sc_chem_shift_13C_fraction = string;
	}

	public void setSc_chem_shift_15N_fraction(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Sc_chem_shift_15N_fraction = string;
	}

	public void setArom_chem_shift_fraction(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Arom_chem_shift_fraction = string;
	}

	public void setArom_chem_shift_1H_fraction(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Arom_chem_shift_1H_fraction = string;
	}

	public void setArom_chem_shift_13C_fraction(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Arom_chem_shift_13C_fraction = string;
	}

	public void setArom_chem_shift_15N_fraction(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Arom_chem_shift_15N_fraction = string;
	}

	public void setMethyl_chem_shift_fraction(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Methyl_chem_shift_fraction = string;
	}

	public void setMethyl_chem_shift_1H_fraction(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Methyl_chem_shift_1H_fraction = string;
	}

	public void setMethyl_chem_shift_13C_fraction(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Methyl_chem_shift_13C_fraction = string;
	}

	public void setEntity_polymer_type(String string) {
		if (string != null && !string.isEmpty() && !string.equals(".") && !string.equals("?"))
			Entity_polymer_type = string;
	}

	public void setEntry_ID(String string) {
		Entry_ID = string;
	}

	public void setAssigned_chem_shift_list_ID(String string) {
		Assigned_chem_shift_list_ID = string;
	}

	public static void write_cvs_header(FileWriter cs_complete_list_csv) {

		try {
			cs_complete_list_csv.write("Sf_ID,Sf_category,Sf_framecode,Queried_date,Output_file_name,Electronic_address,Assigned_residue_coverage,"
					+ "Chem_shift_fraction,Chem_shift_1H_fraction,Chem_shift_13C_fraction,Chem_shift_15N_fraction,Chem_shift_31P_fraction,"
					+ "Bb_chem_shift_fraction,Bb_chem_shift_1H_fraction,Bb_chem_shift_13C_fraction,Bb_chem_shift_15N_fraction,Bb_chem_shift_31P_fraction,"
					+ "Sc_chem_shift_fraction,Sc_chem_shift_1H_fraction,Sc_chem_shift_13C_fraction,Sc_chem_shift_15N_fraction,"
					+ "Arom_chem_shift_fraction,Arom_chem_shift_1H_fraction,Arom_chem_shift_13C_fraction,Arom_chem_shift_15N_fraction,"
					+ "Methyl_chem_shift_fraction,Methyl_chem_shift_1H_fraction,Methyl_chem_shift_13C_fraction,"
					+ "Entity_polymer_type,Entry_ID,Assigned_chem_shift_list_ID\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void write_csv(FileWriter cs_complete_list_csv) {

		try {
			cs_complete_list_csv.write(Sf_ID + "," + Sf_category + "," + Sf_framecode + "," + Queried_date + "," + Output_file_name + "," + Electronic_address + "," + Assigned_residue_coverage + ","
					+ Chem_shift_fraction + "," + Chem_shift_1H_fraction + "," + Chem_shift_13C_fraction + "," + Chem_shift_15N_fraction + "," + Chem_shift_31P_fraction + ","
					+ Bb_chem_shift_fraction + "," + Bb_chem_shift_1H_fraction + "," + Bb_chem_shift_13C_fraction + "," + Bb_chem_shift_15N_fraction + "," + Bb_chem_shift_31P_fraction + ","
					+ Sc_chem_shift_fraction + "," + Sc_chem_shift_1H_fraction + "," + Sc_chem_shift_13C_fraction + "," + Sc_chem_shift_15N_fraction + ","
					+ Arom_chem_shift_fraction + "," + Arom_chem_shift_1H_fraction + "," + Arom_chem_shift_13C_fraction + "," + Arom_chem_shift_15N_fraction + ","
					+ Methyl_chem_shift_fraction + "," + Methyl_chem_shift_1H_fraction + "," + Methyl_chem_shift_13C_fraction + ","
					+ Entity_polymer_type + "," + Entry_ID + "," + Assigned_chem_shift_list_ID + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
