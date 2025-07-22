package org.pdbj.bmrbpub.schema.mmcifNmrStar;

import java.io.*;
import java.sql.*;
import java.util.logging.*;

public class bmr_Util_Serv {

	private static boolean connected = false;

	private static String _rel_dir_name = null;
	private static String _loc_dir_name = null;
	private static String _file_prefix = null;

	private static Connection conn_bmrb = null;
	private static Statement state = null;
	private static ResultSet rset = null;

	private static PreparedStatement pstate2 = null;
	private static PreparedStatement pstate3 = null;

	private static final String url_bmrb = bmr_Util_Main.url_bmrb;
	private static final String user_bmrb = bmr_Util_Main.user_bmrb.isEmpty() ? bmr_Util_Main.user : bmr_Util_Main.user_bmrb;
	private static final String pass_bmrb = bmr_Util_Main.pass_bmrb;

	synchronized public static void open(String rel_dir_name, String loc_dir_name, String file_prefix) {

		if (connected)
			return;

		_rel_dir_name = rel_dir_name;
		_loc_dir_name = loc_dir_name;
		_file_prefix = file_prefix;

		try {

			conn_bmrb = DriverManager.getConnection(url_bmrb, user_bmrb, pass_bmrb);
			state = conn_bmrb.createStatement();
			rset = state.executeQuery("select \"ID\" from \"Entry\" order by random()");

			pstate2 = conn_bmrb.prepareStatement("select \"Date\" from \"Release\" where \"Entry_ID\"=? order by \"Date\" desc");
			pstate3 = conn_bmrb.prepareStatement("select \"DB_query_revised_last_date\" from \"Entity\" where \"Entry_ID\"=? order by \"DB_query_revised_last_date\" desc");

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_Util_Serv.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		}

		connected = true;

	}

	synchronized public static String get_entry_id() throws SQLException {

		if (!connected)
			return null;

		String entry_id = null;

		ResultSet rset2 = null;

		try {

			while (rset.next()) {

				entry_id = rset.getString("ID");

				if (entry_id == null)
					return null;

				File loc_dir = new File(_loc_dir_name);

				if (!loc_dir.exists())
					return null;

				String rel_file_name = _rel_dir_name + _file_prefix + entry_id + bmr_Util_Main.noatom_suffix + "_last";
				String loc_file_name = _loc_dir_name + _file_prefix + entry_id + bmr_Util_Main.noatom_suffix + "_lock";

				File loc_file = new File(loc_file_name);

				if (loc_file.exists())
					continue;

				File rel_file = new File(rel_file_name);

				if (!rel_file.exists()) {

					if (bmr_Util_Main.write_xml)
						System.out.println(entry_id + " is new.");

					break;
				}

				try {

					FileReader filer = new FileReader(rel_file_name);
					BufferedReader bufferr = new BufferedReader(filer);

					String line = bufferr.readLine();

					bufferr.close();
					filer.close();

					if (line == null) {

						if (bmr_Util_Main.write_xml)
							System.out.println(entry_id + " is new.");

						break;
					}

					java.sql.Date release_date = null;
					java.sql.Date entity_date = null;

					String date = null;

					pstate2.setString(1, entry_id);

					rset2 = pstate2.executeQuery();

					release_date = null;

					while (rset2.next()) {

						String _date = rset2.getString("Date");

						if (_date == null || _date.isEmpty() || _date.equals(".") || _date.equals("?"))
							break;

						release_date = rset2.getDate("Date");

						if (release_date != null)
							break;

					}

					rset2.close();

					pstate3.setString(1, entry_id);

					rset2 = pstate3.executeQuery();

					entity_date = null;

					while (rset2.next()) {

						String _date = rset2.getString("DB_query_revised_last_date");

						if (_date == null || _date.isEmpty() || _date.equals(".") || _date.equals("?"))
							break;

						entity_date = rset2.getDate("DB_query_revised_last_date");

						if (entity_date != null)
							break;

					}

					if (release_date != null && entity_date != null) {

						if (entity_date.after(release_date))
							date = entity_date.toString();
						else
							date = release_date.toString();

					}

					else if (entity_date != null)
						date = entity_date.toString();

					else if (release_date != null)
						date = release_date.toString();

					if (date == null || !line.equals(date)) {

						if (!entry_id.equals("4617") && !entry_id.equals("4677") && !entry_id.equals("4980") && !entry_id.equals("19958") && !entry_id.equals("25839")) {

							if (bmr_Util_Main.write_xml)
								System.out.println(entry_id + " is revised (" + line + " -> " + date + ").");

							break;

						}

					}

					entry_id = null;

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			if (rset2 != null)
				rset2.close();

		}

		return entry_id;

	}

	synchronized public static void close() {

		if (!connected)
			return;

		try {

			if (rset != null)
				rset.close();

			if (state != null)
				state.close();

			if (pstate2 != null)
				pstate2.close();

			if (pstate3 != null)
				pstate3.close();

			if (conn_bmrb != null)
				conn_bmrb.close();

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_Util_Serv.class.getName());
			lgr.log(Level.WARNING, ex.getMessage(), ex);

		}

		connected = false;

	}
}
