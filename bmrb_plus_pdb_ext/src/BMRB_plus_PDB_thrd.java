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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

import EDU.bmrb.starlibj.StarNode;
import EDU.bmrb.starlibj.StarParser;

public class BMRB_plus_PDB_thrd implements Runnable {

	private static File _src_dir = null;

	private static Connection conn_bmrb = null;

	private static final String url_bmrb = BMRB_plus_PDB_ext.url_bmrb;
	private static final String user_bmrb = BMRB_plus_PDB_ext.user_bmrb.isEmpty() ? BMRB_plus_PDB_ext.user : BMRB_plus_PDB_ext.user_bmrb;
	private static final String pass_bmrb = BMRB_plus_PDB_ext.pass_bmrb;

	public BMRB_plus_PDB_thrd(File src_dir) {

		_src_dir = src_dir;

		try {

			conn_bmrb = DriverManager.getConnection(url_bmrb, user_bmrb, pass_bmrb);

		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

	@Override
	public void run() {

		String[] thrd_name = Thread.currentThread().getName().split("-");

		int max_thrds = Integer.valueOf(thrd_name[1]);
		int thrd_id = Integer.valueOf(thrd_name[2]);

		PreparedStatement pstate = null;
		ResultSet rset = null;

		String entry_id = null;
		//		String pdb_id = null;

		StarParser parser = null;

		BMRB_plus_PDB_str2csv converter = new BMRB_plus_PDB_str2csv();

		try {

			File[] file_list = _src_dir.listFiles();

			int i = 0;

			for (File file : file_list) {

				String file_name = file.getName();

				if (!file_name.matches("merged_[0-9]+_[0-9][0-9a-z]{3}.str"))
					continue;

				if ((i++) % max_thrds != thrd_id)
					continue;

				String[] split_name = file_name.split("_");

				entry_id = split_name[1];
				//			pdb_id = split_name[2].substring(0, 4).toUpperCase(); 

				pstate = conn_bmrb.prepareStatement("select \"ID\" from \"Entry\" where \"ID\"='" + entry_id + "'");
				rset = pstate.executeQuery();

				while (rset.next()) {

					entry_id = rset.getString("ID");

					if (entry_id == null)
						break;

				}

				rset.close();
				pstate.close();

				if (entry_id == null)
					continue;

				//				System.out.println("entry_id: " + entry_id);
				//				System.out.println("pdb_id:   " + pdb_id);

				try {

					FileInputStream in = new FileInputStream(file);

					if (parser == null)
						parser = new StarParser(in);
					else
						parser.ReInit(in);

					parser.StarFileNodeParse(parser);
					StarNode node = parser.popResult();

					final String sf_category = ".Sf_category";
					String category, tag_name;

					category = "conformer_family_coord_set";
					tag_name = "_" + category.substring(0, 1).toUpperCase() + category.substring(1) + sf_category;

					converter.parse_saveframe(conn_bmrb, entry_id, node, tag_name, category);

					category = "conformer_statistics";
					tag_name = "_Conformer_stat_list"+ sf_category;

					converter.parse_saveframe(conn_bmrb, entry_id, node, tag_name, category);

					category = "constraint_statistics";
					tag_name = "_Constraint_stat_list" + sf_category;

					converter.parse_saveframe(conn_bmrb, entry_id, node, tag_name, category);

					category = "general_distance_constraints";
					tag_name = "_Gen_dist_constraint_list" + sf_category;

					converter.parse_saveframe(conn_bmrb, entry_id, node, tag_name, category);

					category = "org_constr_file_comment";
					tag_name = "_" + category.substring(0, 1).toUpperCase() + category.substring(1) + sf_category;

					converter.parse_saveframe(conn_bmrb, entry_id, node, tag_name, category);

					category = "RDC_constraints";
					tag_name = "_RDC_constraint_list" + sf_category;

					converter.parse_saveframe(conn_bmrb, entry_id, node, tag_name, category);

					category = "torsion_angle_constraints";
					tag_name = "_Torsion_angle_constraint_list" + sf_category;

					converter.parse_saveframe(conn_bmrb, entry_id, node, tag_name, category);

					in.close();

					System.out.print(".");

				} catch (Exception e) {
					System.err.print(file_name);
					e.printStackTrace();
				}

			}

			csv2pgsql(conn_bmrb);

			conn_bmrb.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void csv2pgsql(Connection conn_bmrb) {

		try {

			CopyManager copy_man = new CopyManager((BaseConnection) conn_bmrb);

			File csv_dir = new File(BMRB_plus_PDB_ext.csv_dir_name);

			if (csv_dir.isDirectory()) {

				File[] files = csv_dir.listFiles();

				for (File file : files) {

					String file_name = file.getName();

					if (file_name.endsWith(".csv")) {

						String table_name = file_name.split("\\.")[0];

						long rows = copy_from_csv(copy_man, file_name.split("\\.")[0], file);

						if (rows < 0) {

							System.out.println("Table: " + table_name + ", CSV File: " + file_name);
							System.err.println("Failed in copy.");

						}

					}

				}

				for (int i = 0; i < files.length; i++)
					files[i].delete();

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private long copy_from_csv(CopyManager copy_man, String table_name, File csv_file) {

		try {

			String sql = "COPY \"" + table_name + "\" FROM STDIN CSV";

			return copy_man.copyIn(sql, new FileInputStream(csv_file));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return -1;
	}

}