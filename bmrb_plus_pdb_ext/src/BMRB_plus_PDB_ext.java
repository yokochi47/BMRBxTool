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

public class BMRB_plus_PDB_ext {

	public static final String user = System.getProperty("user.name");

	public static String url_bmrb = "jdbc:postgresql://localhost/bmrb";
	public static String user_bmrb = "bmrb";
	public static String pass_bmrb = "";

	private static String bmrbx_tool_home = ".";

	public static final String str_dir_template = "/bmrb_plus_pdb_ext/ftp/pub/bmrb/nmr_pdb_integrated_data/coordinates_restraints_chemshifts/bmrb_plus_pdb";
	public static final String csv_dir_template = "/bmrb_plus_pdb_ext/csv.";

	public static String src_dir_name = bmrbx_tool_home + str_dir_template;

	private static Runtime runtime = Runtime.getRuntime();
	private static final int cpu_num = runtime.availableProcessors();
	private static int thrd_id = 0;
	private static int max_thrds = 1;

	public static String csv_dir_name = bmrbx_tool_home + csv_dir_template + thrd_id;

	public static void main(String[] args) {

		for (int i = 0; i < args.length; i++) {

			if (args[i].equals("--home")) {
				bmrbx_tool_home = args[++i];

				src_dir_name = bmrbx_tool_home + str_dir_template;
				csv_dir_name = bmrbx_tool_home + csv_dir_template + thrd_id;
			}

			else if (args[i].equals("--csv-dir"))
				csv_dir_name = args[++i];

			else if (args[i].equals("--url-bmrb"))
				url_bmrb = args[++i];

			else if (args[i].equals("--user-bmrb"))
				user_bmrb = args[++i];

			else if (args[i].equals("--pass-bmrb"))
				pass_bmrb = args[++i];

			else if (args[i].equals("--thrd-id")) {
				thrd_id = Integer.valueOf(args[++i]);

				if (thrd_id < 0 || thrd_id >= cpu_num) {
					System.err.println("Out of range (thrd_id).");
					System.exit(1);
				}

				csv_dir_name = bmrbx_tool_home + csv_dir_template + thrd_id;
			}

			else if (args[i].equals("--max-thrds")) {
				max_thrds = Integer.valueOf(args[++i]);

				if (max_thrds <= 0 || max_thrds > cpu_num) {
					System.err.println("Out of range (max_thrds).");
					System.exit(1);
				}

			}

			else {
				System.out.println("Usage: --home DIR --url-bmrb BMRB --user-bmrb USER --pass-bmrb WORD");
				System.out.println(" --home      DIR  : BMRBxTool home directory. (" + bmrbx_tool_home + ")");
				System.out.println(" --csv-dir   DIR  : CSV directory. (" + csv_dir_name + ")");
				System.out.println(" --url-bmrb  BMRB : URL of BMRB database. (" + url_bmrb + ")");
				System.out.println(" --user-bmrb USER : Username of BMRB database.");
				System.out.println(" --pass-bmrb WORD : Password of BMRB database.");
				System.out.println(" --thrd-id   THRD : Thread ID. (default is 0)");
				System.out.println(" --max-thrds PROC : Number of threads. (default is 1)");

				System.exit(1);
			}

		}

		if (thrd_id >= max_thrds) {
			System.err.println("Out of range (thrd_id).");
			System.exit(1);
		}

		File src_dir = new File(src_dir_name);

		if (!src_dir.isDirectory()) {

			System.err.println("Can't find directory '" + src_dir_name + "'.");
			System.exit(1);

		}

		File csv_dir = new File(csv_dir_name);

		if (csv_dir.isDirectory()) {

			File[] files = csv_dir.listFiles();

			for (int i = 0; i < files.length; i++)
				files[i].delete();

		}

		else {

			if (!csv_dir.mkdir()) {
				System.err.println("Couldn't create directory '" + csv_dir_name + "'.");
				System.exit(1);
			}

		}

		csv_dir_name = csv_dir_name.replaceFirst("/$", "") + "/";

		BMRB_plus_PDB_thrd[] util_thrd = new BMRB_plus_PDB_thrd[max_thrds];
		Thread[] thrd = new Thread[max_thrds];

		for (int _thrd_id = 0; _thrd_id < max_thrds; _thrd_id++) {

			if (_thrd_id != thrd_id)
				continue;

			String thrd_name = "BMRB_plus_PDB_Thrd-" + max_thrds + "-" + thrd_id;

			util_thrd[thrd_id] = new BMRB_plus_PDB_thrd(src_dir);
			thrd[thrd_id] = new Thread(util_thrd[thrd_id], thrd_name);

			thrd[thrd_id].start();

		}

		for (int _thrd_id = 0; _thrd_id < max_thrds; _thrd_id++) {

			if (_thrd_id != thrd_id)
				continue;

			try {

				if (thrd[thrd_id] != null)
					thrd[thrd_id].join();

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

}