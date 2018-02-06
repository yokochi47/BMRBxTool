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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PB_pb2str {

	public static final String user = System.getProperty("user.name");

	public static String url_bmrb = "jdbc:postgresql://localhost/bmrb";
	public static String user_bmrb = "";
	public static String pass_bmrb = "";

	private static String entry_id = "";
	private static String pdb_id = "";
	private static String pdb_file = "";
	private static String pdbml_file = "";
	private static String pb_file = "";
	private static String str_file = "";
	private static String white_list = "";
	private static String black_list = "";

	public static void main(String[] args) {

		Connection conn_bmrb = null;

		for (int i = 0; i < args.length; i++) {

			if (args[i].equals("--bmrb-id"))
				entry_id = args[++i];

			else if (args[i].equals("--pdb-id"))
				pdb_id = args[++i].toUpperCase();

			else if (args[i].equals("--pdb-file"))
				pdb_file = args[++i];

			else if (args[i].equals("--pdbml-file"))
				pdbml_file = args[++i];

			else if (args[i].equals("--pb-file"))
				pb_file = args[++i];

			else if (args[i].equals("--str-file"))
				str_file = args[++i];

			else if (args[i].equals("--white-list"))
				white_list = args[++i];

			else if (args[i].equals("--black-list"))
				black_list = args[++i];

			else if (args[i].equals("--url-bmrb"))
				url_bmrb = args[++i];

			else if (args[i].equals("--user-bmrb"))
				user_bmrb = args[++i];

			else if (args[i].equals("--pass-bmrb"))
				pass_bmrb = args[++i];

			else {
				System.out.println("Usage: --entry_id BMRB_ID --pdb-id PDB_ID --pdb-file PDB --pdbml-file PDBML --pb-file PB --str-file STR");
				System.out.println(" --white-list LIST : BMRB entries ignoring mismatch sequence between BMRB and PDB");
				System.out.println(" --black-list LIST : Failed PDB entries");
				System.out.println(" --url-bmrb   BMRB : URL of BMRB database. (" + url_bmrb + ")");
				System.out.println(" --user-bmrb  USER : Username of BMRB database.");
				System.out.println(" --pass-bmrb  WORD : Password of BMRB database.");

				System.exit(1);
			}

		}

		if (entry_id.isEmpty()) {
			System.err.println("No BMRB ID was selected");
			System.exit(1);
		}

		if (pdb_id.isEmpty()) {
			System.err.println("No PDB ID was selected");
			System.exit(1);
		}

		if (pdb_file.isEmpty()) {
			System.err.println("No PDB file was selected.");
			System.exit(1);
		}

		if (pdbml_file.isEmpty()) {
			System.err.println("No PDBML file was selected.");
			System.exit(1);
		}

		if (pb_file.isEmpty()) {
			System.err.println("No PB file was selected.");
			System.exit(1);
		}

		if (str_file.isEmpty()) {
			System.err.println("No STR file was selected.");
			System.exit(1);
		}

		File pdb = new File(pdb_file);

		if (!pdb.exists()) {
			System.err.println("Couldn't find '" + pdb_file + "'.");
			System.exit(1);
		}

		File pdbml = new File(pdbml_file);

		if (!pdbml.exists()) {
			System.err.println("Couldn't find '" + pdbml_file + "'.");
			System.exit(1);
		}

		File pb = new File(pb_file);

		if (!pb.exists()) {
			System.err.println("Couldn't read '" + pb_file + "'.");
			System.exit(1);
		}

		File str = new File(str_file);

		WhiteList white = null;

		if (!white_list.isEmpty())
			white = new WhiteList(white_list);

		BlackList black = null;

		if (!black_list.isEmpty())
			black = new BlackList(black_list);

		try {

			conn_bmrb = DriverManager.getConnection(url_bmrb, user_bmrb.isEmpty() ? user : user_bmrb, pass_bmrb); // bmrb is ready.

			PB_file.parse(conn_bmrb, entry_id, pdb_id, pdb, pdbml, pb, str, white, black);

			conn_bmrb.close();

		} catch (SQLException | IOException ex) {

			Logger lgr = Logger.getLogger(PB_pb2str.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		}

		if (black != null)
			black.write();

	}

}
