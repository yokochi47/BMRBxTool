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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class LACS_str2csv {

	public static void link_db(String url_mirror, Connection conn_bmrb, FileWriter lacs_plot_csv, FileWriter lacs_char_csv) {

		PreparedStatement pstate = null;
		ResultSet rset = null;

		String entry_id = null;

		try {

			pstate = conn_bmrb.prepareStatement("select \"ID\" from \"Entry\"");
			rset = pstate.executeQuery();

			while (rset.next()) {

				entry_id = rset.getString("ID");

				if (entry_id == null)
					break;

				String output_file_name = "bmr" + entry_id + "_LACS.str";
				String output_url  = "http://" + url_mirror + "/ftp/pub/bmrb/validation_reports/LACS/" + output_file_name;

				if (file_exist(output_url))
					str2csv(conn_bmrb, lacs_plot_csv, lacs_char_csv, entry_id, output_file_name, output_url);

			}

			rset.close();
			pstate.close();

			System.out.print("\n");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static boolean file_exist(String output_url) {

		HttpURLConnection.setFollowRedirects(false);

		try {

			HttpURLConnection conn = (HttpURLConnection) new URL(output_url).openConnection();
			conn.setRequestMethod("HEAD");

			return (conn.getResponseCode() == HttpURLConnection.HTTP_OK);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	private static void str2csv(Connection conn_bmrb, FileWriter lacs_plot_csv, FileWriter lacs_char_csv, String entry_id, String output_file_name, String output_url) {

		HttpURLConnection.setFollowRedirects(false);

		try {

			HttpURLConnection conn = (HttpURLConnection) new URL(output_url).openConnection();
			conn.setRequestMethod("GET");

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK)
				return;

			SimpleDateFormat simple_date = new SimpleDateFormat("yyyy-MM-dd");

			int lacs_plot_id = 1;

			BufferedReader bufferr = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = bufferr.readLine();

			if (!line.startsWith("data_LACS"))
				return;

			LACS_plot plot = new LACS_plot();

			plot.Sf_category = "LACS_plot";
			plot.Entry_ID = entry_id;
			plot.Sf_ID = Integer.valueOf(entry_id + "0100") + lacs_plot_id;
			plot.ID = lacs_plot_id;
			plot.Queried_date = simple_date.format(Calendar.getInstance().getTime());
			plot.Electronic_address = output_url;
			plot.Output_file_name = output_file_name;
			plot.Source_release_designation = "";
			plot.Source_release_date = simple_date.format(new Date(conn.getLastModified()));
			plot.Details = "";

			PreparedStatement pstate = conn_bmrb.prepareStatement("select * from \"Entity_assembly\" where \"Entry_ID\" = ?");
			pstate.setString(1, entry_id);

			PreparedStatement pstate2 = conn_bmrb.prepareStatement("select \"Comp_ID\" from \"Entity_comp_index\" where \"Entry_ID\"= ? and \"Entity_ID\"= ? order by \"ID\"::integer");
			pstate2.setString(1, entry_id);

			List<LACS_char> char_list = new ArrayList<LACS_char>();

			List<String> loop_properties = new ArrayList<String>();

			String data_type = "";

			while ((line = bufferr.readLine()) != null) {

				String [] array = line.replaceAll("\\s+", " ").replaceFirst("^ ", "").split(" ");

				if (line.isEmpty()) {

					if (data_type.equals("save_prop"))
						data_type = "save_data";

					else if (data_type.equals("loop_prop"))
						data_type = "loop_data";

				}

				else if (array[0].startsWith("#"))
					continue;

				else if (array[0].equals("save_")) {
					data_type = "";

					write_csv(pstate, pstate2, lacs_plot_csv, lacs_char_csv, entry_id, plot, char_list);

					lacs_plot_id++;

					plot.Sf_ID = Integer.valueOf(entry_id + "0100") + lacs_plot_id;
					plot.ID = lacs_plot_id;

				}

				else if (array[0].startsWith("save_")) {
					data_type = "save_prop";

					char_list.clear();

					plot.Sf_framecode = array[0].substring(5);
				}

				else if (array[0].equals("loop_")) {
					data_type = "loop_prop";

					loop_properties.clear();
				}

				else if (array[0].equals("stop_")) {
					data_type = "save_data";
				}

				else if (!data_type.isEmpty()) {

					if (data_type.equals("save_prop")) {

						if (array[0].matches("^_LACS.plot..*")) {

							String item_name = array[0].substring(11);
							Class<?> _class = plot.getClass();

							try {
								Method method = _class.getMethod("set" + item_name, new Class[]{ String.class });
								method.invoke(plot, array[1]);
							} catch (NoSuchMethodException e) {
								e.printStackTrace();
							} catch (SecurityException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}

						}
					}

					else if (data_type.equals("loop_prop")) {

						if (array[0].matches("^_LACS..*")) {

							String [] data = array[0].split("\\.");

							if (data[0].equals("_LACS"))
								loop_properties.add(data[1]);

						}

					}

					else if (data_type.equals("loop_data")) {

						if (array.length != loop_properties.size()) {
							data_type = "save_data";
							continue;
						}

						try {

							LACS_char char_elem = new LACS_char();

							char_elem.Sf_ID = plot.Sf_ID;
							char_elem.Entry_ID = entry_id;
							char_elem.LACS_plot_ID = lacs_plot_id;

							Class<?> _class = char_elem.getClass();

							for (int l = 0; l < array.length; l++) {
								Method method = _class.getMethod("set" + loop_properties.get(l), new Class[]{ String.class });
								method.invoke(char_elem, array[l]);
							}

							char_list.add(char_elem);

						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						}
					}
				}
			}

			pstate.close();
			pstate2.close();

			char_list.clear();

			loop_properties.clear();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void write_csv(PreparedStatement pstate, PreparedStatement pstate2, FileWriter lacs_plot_csv, FileWriter lacs_char_csv, String entry_id, LACS_plot plot, List<LACS_char> char_list) {

		int lacs_seq_len = 0;

		for (LACS_char char_elem : char_list) {

			int comp_index_id = Integer.valueOf(char_elem.Comp_index_ID);

			if (comp_index_id > lacs_seq_len)
				lacs_seq_len = comp_index_id + 1;
		}

		if (lacs_seq_len == 0)
			return;

		boolean fill_entity_id = true;

		for (LACS_char char_elem : char_list) {

			if (char_elem.Entity_ID.isEmpty()) {
				fill_entity_id = false;
				break;
			}
		}

		if (!fill_entity_id) {

			char x = 'X';
			char[] lacs_seq = new char[lacs_seq_len];

			Arrays.fill(lacs_seq, x);

			for (LACS_char char_elem : char_list) {

				int comp_index_id = Integer.valueOf(char_elem.Comp_index_ID);

				if (comp_index_id >= 0 && comp_index_id < lacs_seq_len)
					lacs_seq[comp_index_id] = seq_one_letter_code.getCode(char_elem.Comp_ID).charAt(0);
			}

			try {

				ResultSet rset = pstate.executeQuery();

				double score_max = 0.0;

				while (rset.next()) {

					String entity_id = rset.getString("Entity_ID");

					if (entity_id == null || entity_id.isEmpty())
						continue;

					pstate2.setString(2, entity_id);

					String polymer_seq_one_letter_code = EntityCompIndex.get_polymer_seq_one_letter_code(pstate2);

					if (polymer_seq_one_letter_code == null || polymer_seq_one_letter_code.isEmpty())
						continue;

					char[] bmrb_seq = polymer_seq_one_letter_code.toCharArray();

					//					System.out.println(lacs_seq);
					//					System.out.println(bmrb_seq);

					alignment align = new alignment(lacs_seq, bmrb_seq);

					//					System.out.println(align.score);

					if (align.score > score_max) {

						score_max = align.score;

						String assembly_id = rset.getString("Assembly_ID");
						String entity_assembly_id = rset.getString("ID");

						for (int i = 0; i < char_list.size(); i++) {

							LACS_char char_elem = new LACS_char();

							char_elem = char_list.get(i);

							char_elem.Assembly_ID = assembly_id;
							char_elem.Entity_assembly_ID = entity_assembly_id;
							char_elem.Entity_ID = entity_id;

							char_list.set(i, char_elem);

						}

					}

				}

				rset.close();

				if (score_max <= 0.0)
					return;

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		System.out.print(".");

		plot.write_csv(lacs_plot_csv);

		for (LACS_char char_elem : char_list)
			char_elem.write_csv(lacs_char_csv);

	}

}