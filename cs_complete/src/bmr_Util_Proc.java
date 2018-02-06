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

import java.io.*;
import java.sql.*;

public class bmr_Util_Proc {

	public static void exec(Connection conn_bmrb, String str_dir_name, String loc_dir_name, int thrd_id, String entry_id) {

		String str_base_name = "bmr" + entry_id + "_chem_shift_completeness.str";
		String char_base_name = "bmr" + entry_id + "_chem_shift_completeness_char.str";
		String loc_base_name = "bmr" + entry_id + "_lock";

		File str_file = new File(str_dir_name, str_base_name);
		File char_file = new File(str_dir_name, char_base_name);

		File loc_file = new File(loc_dir_name, loc_base_name);

		if (str_file.exists() || loc_file.exists())
			return;

		try {
			
			FileWriter strw = new FileWriter(str_file);

			FileWriter locw = new FileWriter(loc_file);
			locw.write(entry_id);
			locw.flush();
			locw.close();

			bmr_Util_Entity.check(conn_bmrb, thrd_id, entry_id, strw, char_file);
			
			if (char_file.exists())
				char_file.delete();
			
			strw.close();

			System.out.println(entry_id + " done.");

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
