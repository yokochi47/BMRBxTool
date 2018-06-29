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
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.sql.*;
import java.util.*;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class CS_complete_str2csv {

	public static void link_db(Connection conn_bmrb, FileWriter cs_complete_list_csv, FileWriter cs_complete_char_csv) {

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

				String output_file_name = "bmr" + entry_id + "_chem_shift_completeness.str";
				String output_url  = "https://bmrbpub.pdbj.org/archive/cs_complete/" + output_file_name;

				if (file_exist(output_url))
					str2csv(conn_bmrb, cs_complete_list_csv, cs_complete_char_csv, entry_id, output_file_name, output_url);

			}

			rset.close();
			pstate.close();

			System.out.print("\n");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static boolean file_exist(String cs_complete_annotation_file) {

		try {

			TrustManager[] tm = new TrustManager[] { new X509TrustManager() {

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
				@Override
				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}
				@Override
				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			}

			};

			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, tm, new java.security.SecureRandom());

			HttpsURLConnection.setFollowRedirects(false);

			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});

			URL url = new URL(cs_complete_annotation_file);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());

			conn.setRequestMethod("HEAD");

			return (conn.getResponseCode() == HttpURLConnection.HTTP_OK);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}

		return false;
	}

	private static void str2csv(Connection conn_bmrb, FileWriter cs_complete_list_csv, FileWriter cs_complete_char_csv, String entry_id, String output_file_name, String output_url) {

		try {

			TrustManager[] tm = new TrustManager[] { new X509TrustManager() {

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
				@Override
				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}
				@Override
				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			}

			};

			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, tm, new java.security.SecureRandom());

			HttpsURLConnection.setFollowRedirects(false);

			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});

			URL url = new URL(output_url);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());

			conn.setRequestMethod("GET");

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK)
				return;

			BufferedReader bufferr = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = bufferr.readLine();

			if (!line.startsWith("data_chem_shift_completeness_list"))
				return;

			CS_complete_list list = new CS_complete_list(output_file_name, output_url);

			List<CS_complete_char> char_list = new ArrayList<CS_complete_char>();

			List<String> loop_properties = new ArrayList<String>();

			String data_type = "";

			while ((line = bufferr.readLine()) != null) {

				String [] array = line.replaceAll("\\s+", " ").replaceAll("^ ", "").split(" ");

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

					write_csv(cs_complete_list_csv, cs_complete_char_csv, entry_id, list, char_list);
				}

				else if (array[0].startsWith("save_")) {
					data_type = "save_prop";

					char_list.clear();

					list.Sf_framecode = array[0].substring(5);
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

						if (array[0].matches("^_Chem_shift_completeness_list..*")) {

							String item_name = array[0].split("\\.")[1];
							Class<?> _class = list.getClass();

							try {

								Method method = _class.getMethod("set" + item_name, new Class[]{ String.class });

								StringBuffer sbuff = new StringBuffer();

								for (int l = 1; l < array.length; l++) {
									sbuff.append(array[l].replaceFirst("^\"", "").replaceFirst("\"$", ""));
									if (array.length > 1 && l < array.length - 1)
										sbuff.append(" ");
								}

								method.invoke(list, sbuff.toString());

								if (item_name.equals("Assigned_chem_shift_list_ID"))
									list.Sf_ID = Integer.valueOf(entry_id + "0300") + Integer.valueOf(sbuff.toString());

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

						if (array[0].matches("^_Chem_shift_completeness_char..*")) {

							String [] data = array[0].split("\\.");

							if (data[0].equals("_Chem_shift_completeness_char"))
								loop_properties.add(data[1]);

						}

					}

					else if (data_type.equals("loop_data")) {

						if (array.length != loop_properties.size()) {
							data_type = "save_data";
							continue;
						}

						try {

							CS_complete_char char_elem = new CS_complete_char(list.Sf_ID);

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

			char_list.clear();

			loop_properties.clear();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (KeyManagementException e1) {
			e1.printStackTrace();
		}


	}

	private static void write_csv(FileWriter cs_complete_list_csv, FileWriter cs_complete_char_csv, String entry_id, CS_complete_list list, List<CS_complete_char> char_list) {

		System.out.print(".");

		list.write_csv(cs_complete_list_csv);

		for (CS_complete_char char_elem : char_list)
			char_elem.write_csv(cs_complete_char_csv);

	}

}