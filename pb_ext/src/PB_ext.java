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

public class PB_ext {

	public static final String user = System.getProperty("user.name");

	public static String url_bmrb = "jdbc:postgresql://localhost/bmrb";
	public static String user_bmrb = "";
	public static String pass_bmrb = "";

	private static String bmrbx_tool_home = ".";

	public static String csv_dir_name = bmrbx_tool_home + "/pb_ext";

	public static void main(String[] args) {

		Connection conn_bmrb = null;

		for (int i = 0; i < args.length; i++) {

			if (args[i].equals("--home")) {
				bmrbx_tool_home = args[++i];

				csv_dir_name = bmrbx_tool_home + "/pb_ext";
			}

			else if (args[i].equals("--url-bmrb"))
				url_bmrb = args[++i];

			else if (args[i].equals("--user-bmrb"))
				user_bmrb = args[++i];

			else if (args[i].equals("--pass-bmrb"))
				pass_bmrb = args[++i];

			else {
				System.out.println("Usage: --home DIR --url-bmrb BMRB --user-bmrb USER --pass-bmrb WORD");
				System.out.println(" --home       DIR  : BMRBxTool home directory. (" + bmrbx_tool_home + ")");
				System.out.println(" --url-bmrb   BMRB : URL of BMRB database. (" + url_bmrb + ")");
				System.out.println(" --user-bmrb  USER : Username of BMRB database.");
				System.out.println(" --pass-bmrb  WORD : Password of BMRB database.");

				System.exit(1);
			}

		}

		File csv_dir = new File(csv_dir_name);

		if (csv_dir.exists()) {

			if (csv_dir.isFile())
				csv_dir.delete();

		}

		if (!csv_dir.isDirectory()) {

			if (!csv_dir.mkdir()) {
				System.err.println("Can't create directory '" + csv_dir_name + "'.");
				System.exit(1);
			}

		}

		try {

			conn_bmrb = DriverManager.getConnection(url_bmrb, user_bmrb.isEmpty() ? user : user_bmrb, pass_bmrb); // bmrb is ready.

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(PB_ext.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		}

		try {

			FileWriter pb_list_csv = new FileWriter(csv_dir_name + "/PB_list.csv");
			PB_list.write_cvs_header(pb_list_csv);

			FileWriter pb_char_csv = new FileWriter(csv_dir_name + "/PB_char.csv");
			PB_char.write_cvs_header(pb_char_csv);

			PB_str2csv.link_db(conn_bmrb, pb_list_csv, pb_char_csv);

			pb_list_csv.close();
			pb_char_csv.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
