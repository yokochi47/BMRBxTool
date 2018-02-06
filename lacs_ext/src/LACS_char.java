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

public class LACS_char {

	String Assembly_ID = "", Entity_assembly_ID = "", Entity_ID = "", Comp_index_ID, Comp_ID;
	String X_coord_val, Y_coord_val;
	String Designator;
	int Sf_ID;
	String Entry_ID;
	int LACS_plot_ID;

	public void setComp_index_ID(String string) {
		Comp_index_ID = string;
	}

	public void setComp_ID(String string) {
		Comp_ID = string;
	}

	public void setX_coord_val(String string) {
		X_coord_val = string;
	}

	public void setY_coord_val(String string) {
		Y_coord_val = string;
	}

	public void setDesignator(String string) {
		Designator = string;
	}

	public static void write_cvs_header(FileWriter lacs_char_csv) {

		try {
			lacs_char_csv.write("Assembly_ID,Entity_assembly_ID,Entity_ID,Comp_index_ID,Comp_ID,X_coord_val,Y_coord_val,Designator,Sf_ID,Entry_ID,LACS_plot_ID\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void write_csv(FileWriter lacs_char_csv) {

		try {
			lacs_char_csv.write(Assembly_ID + "," + Entity_assembly_ID + "," + Entity_ID + "," + Comp_index_ID + "," + Comp_ID + "," + X_coord_val + "," + Y_coord_val + "," + Designator + "," + Sf_ID + "," + Entry_ID + "," + LACS_plot_ID + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}