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

public class LACS_plot {

	String Sf_category, Sf_framecode, Entry_ID;
	int Sf_ID, ID;
	String Queried_date, Input_file_name, Output_file_name, Electronic_address, Source_release_designation, Source_release_date, Details, X_coord_name, Y_coord_name;
	String Line_1_terminator_val_x_1, Line_1_terminator_val_y_1, Line_1_terminator_val_x_2, Line_1_terminator_val_y_2;
	String Line_2_terminator_val_x_1, Line_2_terminator_val_y_1, Line_2_terminator_val_x_2, Line_2_terminator_val_y_2;
	String Y_axis_chem_shift_offset;

	public void setSf_category(String string) {
//		Sf_category = string; LACS_output -> LACS_plot
	}

	public void setInput_file_name(String string) {
		Input_file_name = string;
	}

	public void setX_coord_name(String string) {
		X_coord_name = string;
	}

	public void setY_coord_name(String string) {
		Y_coord_name = string;
	}

	public void setLine_1_terminator_val_x_1(String string) {
		Line_1_terminator_val_x_1 = string;
	}

	public void setLine_1_terminator_val_y_1(String string) {
		Line_1_terminator_val_y_1 = string;
	}

	public void setLine_1_terminator_val_x_2(String string) {
		Line_1_terminator_val_x_2 = string;
	}

	public void setLine_1_terminator_val_y_2(String string) {
		Line_1_terminator_val_y_2 = string;
	}

	public void setLine_2_terminator_val_x_1(String string) {
		Line_2_terminator_val_x_1 = string;
	}

	public void setLine_2_terminator_val_y_1(String string) {
		Line_2_terminator_val_y_1 = string;
	}

	public void setLine_2_terminator_val_x_2(String string) {
		Line_2_terminator_val_x_2 = string;
	}

	public void setLine_2_terminator_val_y_2(String string) {
		Line_2_terminator_val_y_2 = string;
	}

	public void setY_axis_chem_shift_offset(String string) {
		Y_axis_chem_shift_offset = string;
	}

	public static void write_cvs_header(FileWriter lacs_plot_csv) {

		try {
			lacs_plot_csv.write("Sf_category,Sf_framecode,Entry_ID,Sf_ID,ID,Queried_date,Input_file_name,Output_file_name,Electronic_address,Source_release_designation,Source_release_date,Details,X_coord_name,Y_coord_name,Line_1_terminator_val_x_1,Line_1_terminator_val_y_1,Line_1_terminator_val_x_2,Line_1_terminator_val_y_2,Line_2_terminator_val_x_1,Line_2_terminator_val_y_1,Line_2_terminator_val_x_2,Line_2_terminator_val_y_2,Y_axis_chem_shift_offset\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void write_csv(FileWriter lacs_plot_csv) {

		try {
			lacs_plot_csv.write(Sf_category + "," + Sf_framecode + "," + Entry_ID + "," + Sf_ID + "," + ID + "," + Queried_date + "," + Input_file_name + "," + Output_file_name + "," + Electronic_address + "," + Source_release_designation + "," + Source_release_date + "," + Details + "," + X_coord_name + "," + Y_coord_name + "," + Line_1_terminator_val_x_1 + "," + Line_1_terminator_val_y_1 + "," + Line_1_terminator_val_x_2 + "," + Line_1_terminator_val_y_2 + "," + Line_2_terminator_val_x_1 + "," + Line_2_terminator_val_y_1 + "," + Line_2_terminator_val_x_2 + "," + Line_2_terminator_val_y_2 + "," + Y_axis_chem_shift_offset + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}