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
import java.util.logging.*;

public class bmr_Util_Serv {

	private static boolean connected = false;

	private static String _str_dir_name = null;
	private static String _loc_dir_name = null;

	private static Connection conn_bmrb = null;
	private static PreparedStatement pstate = null;
	private static ResultSet rset = null;

	private static final String url_bmrb = bmr_Util_Main.url_bmrb;
	private static final String user_bmrb = bmr_Util_Main.user_bmrb.isEmpty() ? bmr_Util_Main.user : bmr_Util_Main.user_bmrb;
	private static final String pass_bmrb = bmr_Util_Main.pass_bmrb;

	private static final String user_home = System.getProperty("user.home");
	private static final String log_file_name = "/Documents/unmatch_seq";
	private static File log_file = null;

	synchronized public static void open(String str_dir_name, String loc_dir_name) {

		if (connected)
			return;

		_str_dir_name = str_dir_name;
		_loc_dir_name = loc_dir_name;

		try {

			conn_bmrb = DriverManager.getConnection(url_bmrb, user_bmrb, pass_bmrb);
			pstate = conn_bmrb.prepareStatement("select \"ID\" from \"Entry\" order by random()"); // order by \"ID\"::integer");
			rset = pstate.executeQuery();

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_Util_Serv.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		}

		log_file = new File(user_home + log_file_name);

		if (log_file.exists())
			log_file.delete();

		connected = true;

	}

	synchronized public static String get_entry_id() throws SQLException {

		if (!connected)
			return null;

		String entry_id = null;

		try {

			while (rset.next()) {

				entry_id = rset.getString("ID");

				if (entry_id == null)
					return null;

				File str_dir = new File(_str_dir_name);

				if (!str_dir.exists())
					return null;

				String str_file_name = _str_dir_name + "bmr" + entry_id + "_chem_shift_completeness.str";

				File str_file = new File(str_file_name);

				if (str_file.exists())
					continue;

				File loc_dir = new File(_loc_dir_name);

				if (!loc_dir.exists())
					return null;

				String loc_file_name = _loc_dir_name + "bmr" + entry_id + "_lock";

				File loc_file = new File(loc_file_name);

				if (loc_file.exists())
					continue;

				System.out.println(entry_id + " is new.");

				break;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return entry_id;

	}

	synchronized public static void close() {

		if (!connected)
			return;

		try {

			if (rset != null)
				rset.close();

			if (pstate != null)
				pstate.close();

			if (conn_bmrb != null)
				conn_bmrb.close();

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_Util_Serv.class.getName());
			lgr.log(Level.WARNING, ex.getMessage(), ex);

		}

		connected = false;

	}

	synchronized public static void append_unmatch_seq(String entry_id, String entity_id, String comp_index_id, String comp_id, String comp_id2, String cs_list_id) {

		if (entity_id == null || entity_id.isEmpty())
			return;

		try {

			if (!log_file.exists())
				log_file.createNewFile();

			FileWriter filew = new FileWriter(log_file, true);
			BufferedWriter bufferw = new BufferedWriter(filew);

			bufferw.write("Entry_ID: " + entry_id + ", Entity_ID: " + entity_id + ", Comp_index_ID: " + comp_index_id + ", Assigned_chm_shift_list_ID: " + cs_list_id + "\n");
			bufferw.write("Comp_ID (entity_comp_index): " + comp_id + "\n");
			bufferw.write("Comp_ID (atom_chem_shift)  : " + comp_id2 + "\n");

			bufferw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
