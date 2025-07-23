/*
   BMRBxTool - XML converter for NMR-STAR data
    Copyright 2013-2025 Masashi Yokochi

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

package org.pdbj.bmrbpub.schema.mmcifNmrStar;

import java.io.*;
import java.net.*;

import java.security.cert.*;
import java.security.*;
import javax.net.ssl.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;

public class bmr_Util_Entry {

	private static String[][] bmrb_pdb_ets_match = null;
	private static String[][] bmrb_pdb_coor_match = null;
	private static String[][] bmrb_pdb_adit_match = null;
	private static String[][] bmrb_pdb_pubmed_match = null;

	bmr_Util_Entry() {

		HttpURLConnection.setFollowRedirects(false);

		String bmrb_pdb_coor_match_csv_file = "https://bmrb.pdbj.org/ftp/pub/bmrb/nmr_pdb_integrated_data/coordinates_restraints_chemshifts/BMRB_PDB_match.csv";
		String bmrb_pdb_adit_match_csv_file = "https://bmrb.pdbj.org/ftp/pub/bmrb/nmr_pdb_integrated_data/adit_nmr_matched_pdb_bmrb_entry_ids.csv";

		String _bmrb_pdb_coor_match_csv_file = "https://bmrb.io/ftp/pub/bmrb/nmr_pdb_integrated_data/coordinates_restraints_chemshifts/BMRB_PDB_match.csv";
		String _bmrb_pdb_adit_match_csv_file = "https://bmrb.io/ftp/pub/bmrb/nmr_pdb_integrated_data/adit_nmr_matched_pdb_bmrb_entry_ids.csv";

		try {

			FileReader filer = new FileReader(bmr_Util_Main.xsd_dir_name + "BMRB_PDB_match.csv");
			BufferedReader bufferr = new BufferedReader(filer);
			bufferr.mark(400000);

			int i = 0;
			String line = null;

			while ((line = bufferr.readLine()) != null) {

				if (!line.matches(".*,.*,.*"))
					continue;

				i++;
			}

			bufferr.reset();

			bmrb_pdb_ets_match = new String[i][3];
			i = 0;

			while ((line = bufferr.readLine()) != null) {

				if (!line.matches(".*,.*,.*"))
					continue;

				String[] elem = line.split(",");

				bmrb_pdb_ets_match[i++] = elem;

			}

			bufferr.close();
			filer.close();

			filer = new FileReader(bmr_Util_Main.xsd_dir_name + "BMRB_PDB_PUBMED_match.csv");
			bufferr = new BufferedReader(filer);
			bufferr.mark(400000);

			i = 0;
			line = null;

			while ((line = bufferr.readLine()) != null) {

				if (!line.matches(".*,.*,.*,.*"))
					continue;

				i++;
			}

			bufferr.reset();

			bmrb_pdb_pubmed_match = new String[i][4];
			i = 0;

			while ((line = bufferr.readLine()) != null) {

				if (!line.matches(".*,.*,.*,.*"))
					continue;

				String[] elem = line.split(",");

				bmrb_pdb_pubmed_match[i++] = elem;

			}

			bufferr.close();
			filer.close();

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

			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});

			try {

				URL url = new URL(bmrb_pdb_coor_match_csv_file);
				HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
				conn.setSSLSocketFactory(sc.getSocketFactory());

				conn.setRequestMethod("GET");

				conn.getResponseCode();

				bufferr = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			} catch (NoRouteToHostException e) {

				URL url = new URL(_bmrb_pdb_coor_match_csv_file);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();

				conn.setRequestMethod("GET");

				if (conn.getResponseCode() != HttpURLConnection.HTTP_OK)
					return;

				bufferr = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			}

			bufferr.mark(400000);

			line = bufferr.readLine();

			if (!line.startsWith("\"BMRB ID\",\"PDB ID\",\"score\""))
				return;

			i = 0;

			while ((line = bufferr.readLine()) != null) {

				if (!line.matches(".*,.*,.*,.*"))
					continue;

				String[] elem = line.split(",");

				if (!elem[2].equals("1"))
					continue;

				i++;
			}

			bufferr.close();

			try {

				URL url = new URL(bmrb_pdb_coor_match_csv_file);
				HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
				conn.setSSLSocketFactory(sc.getSocketFactory());

				conn.setRequestMethod("GET");

				conn.getResponseCode();

				bufferr = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			} catch (NoRouteToHostException e) {

				URL url = new URL(_bmrb_pdb_coor_match_csv_file);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();

				conn.setRequestMethod("GET");

				if (conn.getResponseCode() != HttpURLConnection.HTTP_OK)
					return;

				bufferr = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			}

			bufferr.mark(400000);

			bmrb_pdb_coor_match = new String[i][2];
			i = 0;

			line = bufferr.readLine();

			while ((line = bufferr.readLine()) != null) {

				if (!line.matches(".*,.*,.*,.*"))
					continue;

				String[] elem = line.split(",");

				if (!elem[2].equals("1"))
					continue;

				bmrb_pdb_coor_match[i][0] = elem[0];
				bmrb_pdb_coor_match[i][1] = elem[1].replaceAll("\"", "");

				i++;
			}

			bufferr.close();

			try {

				URL url = new URL(bmrb_pdb_adit_match_csv_file);
				HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
				conn.setSSLSocketFactory(sc.getSocketFactory());

				conn.setRequestMethod("GET");

				conn.getResponseCode();

				bufferr = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			} catch (NoRouteToHostException e) {

				URL url = new URL(_bmrb_pdb_adit_match_csv_file);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();

				conn.setRequestMethod("GET");

				if (conn.getResponseCode() != HttpURLConnection.HTTP_OK)
					return;

				bufferr = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			}

			bufferr.mark(400000);

			i = 0;

			while ((line = bufferr.readLine()) != null) {

				if (!line.matches(".*,.*"))
					continue;

				i++;
			}

			bufferr.close();

			try {

				URL url = new URL(bmrb_pdb_adit_match_csv_file);
				HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
				conn.setSSLSocketFactory(sc.getSocketFactory());

				conn.setRequestMethod("GET");

				conn.getResponseCode();

				bufferr = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			} catch (NoRouteToHostException e) {

				URL url = new URL(_bmrb_pdb_adit_match_csv_file);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();

				conn.setRequestMethod("GET");

				if (conn.getResponseCode() != HttpURLConnection.HTTP_OK)
					return;

				bufferr = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			}

			bufferr.mark(400000);

			bmrb_pdb_adit_match = new String[i][2];
			i = 0;

			while ((line = bufferr.readLine()) != null) {

				if (!line.matches(".*,.*"))
					continue;

				String[] elem = line.split(",");

				bmrb_pdb_adit_match[i++] = elem;

			}

			bufferr.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}

	}

	static final Map<String, String> map_version_type = new HashMap<String, String>() {

		private static final long serialVersionUID = 72L;

		{

			put("original", "original");
			put("obsolete", "obsolete");
			put("revised", "update");
			put("new", "original");
			put("update", "update");

		}
	};

	public static String getVersionType(String val_name) {
		return (String) map_version_type.get(val_name);
	}

	static final Map<String, String> map_experimental_method = new HashMap<String, String>() {

		private static final long serialVersionUID = 73L;

		{

			put("SOLUTION NMR", "NMR");
			put("NMR", "NMR");
			put("theoretical calculation", "Theoretical");
			put("nmr", "NMR");
			put("theoretical", "Theoretical");
			put("Theoretical", "Theoretical");

		}
	};

	public static String getExperimentalMethod(String val_name) {
		return (String) map_experimental_method.get(val_name);
	}

	static final Map<String, String> map_experimental_method_subtyoe = new HashMap<String, String>() {

		private static final long serialVersionUID = 74L;

		{

			put("NMR, 19 STRUCTURES", "solution");
			put("NMR, 10 STRUCTURES", "solution");
			put("Solution NMR", "solution");
			put("THEORETICAL", "theoretical");
			put("NMR, 14 STRUCTURES", "solution");
			put("NMR, 29 STRUCTURES", "solution");
			put("NMR, 11 STRUCTURES", "solution");
			put("NMR, 20 STRUCTURES", "solution");
			put("SOLID-STATE NMR", "solid-state");
			put("NMR, 15 STRUCTURES", "solution");
			put("theoretical", "theoretical");
			put("NMR, 30 STRUCTURES", "solution");
			put("NMR, 21 STRUCTURES", "solution");
			put("solution", "solution");
			put("X-RAY DIFFRACTION", "solution");
			put("Solution", "solution");
			put("SOLUTION", "solution");
			put("NMR, 16 STRUCTURES", "solution");
			put("NMR, 25 STRUCTURES", "solution");
			put("SOLUTION NMR", "solution");
			put("NMR, 9 STRUCTURES", "solution");
			put("NMR", "solution");
			put("VARIATION, DNA, SOLUTION NMR", "solution");
			put("NMR, 4 STRUCTURES", "solution");
			put("NMR, 1 STRUCTURE", "solution");
			put("NMR,20 STRUCTURES", "solution");
			put("solid-state", "solid-state");
			put("NMR, 12 STRUCTURES", "solution");
			put("NMR, 5 STRUCTURES", "solution");
			put("NMR, 24 STRUCTURES", "solution");

		}
	};

	public static String getExperimentalMethodSubtype(String val_name, String entry_id) {

		if (val_name != null && val_name.equalsIgnoreCase("NMR")) {

			if (entry_id.equals("16206"))
				return "solution";

			if (entry_id.equals("16254"))
				return "solid-state";

		}

		return (String) map_experimental_method_subtyoe.get(val_name);
	}

	static final Map<String, String> map_nmr_star_version = new HashMap<String, String>() {

		private static final long serialVersionUID = 75L;

		{

			put("3.1.1.99", "3.1.1.99");
			put("3.1.1.97", "3.1.1.97");
			put("3.1.1.96", "3.1.1.96");
			put("3.1.1.31", "3.1.1.31");
			put("3.1.1.93", "3.1.1.93");
			put("3.1.1.61", "3.1.1.61");
			put("3.1.1.92", "3.1.1.92");
			put("3.2.1.32", "3.2.1.32");
			put("3.2.6.0", "3.2.6.0");
			put("3.1.2.6", "3.1.2.6");
			put("3.2.1.9", "3.2.1.9");
			put("3.2.0.16", "3.2.0.16");
			put("3.2.0.15", "3.2.0.15");
			put("3.1.1.77", "3.1.1.77");
			put("3.2.1.5", "3.2.1.5");
			put("3.2.0.13", "3.2.0.13");
			put("3.2.0.11", "3.2.0.11");
			put("3.2.1.18", "3.2.1.18");
			put("3.2.0.10", "3.2.0.10");
			put("3.2.1.2", "3.2.1.2");
			put("3.1.0.29", "3.1.0.29");
			put("3.2.1.15", "3.2.1.15");
			put("NMR STAR v3.1", "3.1");
			put("3.2.1.12", "3.2.1.12");
			put("3.2.10.3", "3.2.10.3");
			put("3.1.1.7", "3.1.1.7");
			put("3.2.0.9", "3.2.0.9");
			put("3.1.1.21", "3.1.1.21");
			put("3.1", "3.1");
			put("3.1.1.81", "3.1.1.81");
			put("3.2.14.0", "3.2.14.0");

		}
	};

	public static String getNMRStarVersion(String val_name) {
		return (String) map_nmr_star_version.get(val_name);
	}

	static final Map<String, String> map_original_nmr_star_version = new HashMap<String, String>() {

		private static final long serialVersionUID = 76L;

		{

			put("3.1.1.19", "3.1.1.19");
			put("3.1.1.15", "3.1.1.15");
			put("3.1.1.7", "3.1.1.7");
			put("3.0.8.59", "3.0.8.59");
			put("3.0.8.58", "3.0.8.58");
			put("3.1.1.77", "3.1.1.77");
			put("3.1.1.44", "3.1.1.44");
			put("3.1.1.75", "3.1.1.75");
			put("3.0.8.53", "3.0.8.53");
			put("3.2.0.16", "3.2.0.16");
			put("3.2.0.15", "3.2.0.15");
			put("3.2.0.13", "3.2.0.13");
			put("3.0.8.125", "3.0.8.125");
			put("3.2.0.11", "3.2.0.11");
			put("3.0.2.8", "3.0.2.8");
			put("3.2.0.10", "3.2.0.10");
			put("3.0.8.120", "3.0.8.120");
			put("NMR STAR v3.1", "3.1");
			put("3.2.6.0", "3.2.6.0");
			put("3.2.10.3", "3.2.10.3");
			put("3.1.1.99", "3.1.1.99");
			put("3.0.8.78", "3.0.8.78");
			put("3.1.1.65", "3.1.1.65");
			put("3.1.1.31", "3.1.1.31");
			put("3.0.9.2", "3.0.9.2");
			put("3.1.1.61", "3.1.1.61");
			put("production 3.0.2.8", "3.0.2.8");
			put("3.1.1.92", "3.1.1.92");
			put("3.0.8.116", "3.0.8.116");
			put("3.0.8.112", "3.0.8.112");
			put("3.2.1.18", "3.2.1.18");
			put("3.0.8.111", "3.0.8.111");
			put("production.3.0.2.8", "3.0.2.8");
			put("3.1", "3.1");
			put("3.2.1.15", "3.2.1.15");
			put("3.2.1.12", "3.2.1.12");
			put("3.1.0.46", "3.1.0.46");
			put("2.1", "2.1");
			put("3.0.8.34", "3.0.8.34");
			put("3.1.1.21", "3.1.1.21");
			put("3.2.1.9", "3.2.1.9");
			put("3.0.8.96", "3.0.8.96");
			put("3.0.8.109", "3.0.8.109");
			put("production.3.0.9.2", "3.0.9.2");
			put("3.0.8.94", "3.0.8.94");
			put("3.1.1.81", "3.1.1.81");
			put("3.2.1.5", "3.2.1.5");
			put("3.0.9.14", "3.0.9.14");
			put("3.0.9.13", "3.0.9.13");
			put("3.2.1.2", "3.2.1.2");
			put("3.0.8.100", "3.0.8.100");
			put("3.2.0.9", "3.2.0.9");
			put("3.2.14.0", "3.2.14.0");
			put("3.2.1.32", "3.2.1.32");
			put("3.1.2.6", "3.1.2.6");

		}
	};

	public static String getOriginalNMRStarVersion(String val_name) {
		return (String) map_original_nmr_star_version.get(val_name);
	}

	static final Map<String, String> type = new HashMap<String, String>() {

		private static final long serialVersionUID = 77L;

		{

			put("small molecule structure", "small molecule structure");
			put("biological small molecule structure", "small molecule structure");
			put("macromolecule", "macromolecule");
			put("metabolite/natural product", "metabolite/natural product");
			put("small molecule", "small molecule");

		}
	};

	public static String getType(String val_name) {
		return (String) type.get(val_name);
	}

	public String getAssignedPDBID(String pdb_id, Connection conn_bmrb, String entry_id) {

		if (!(pdb_id == null || pdb_id.isEmpty() || pdb_id.equals(".") || pdb_id.equals("?")))
			return getEffectivePDBID(pdb_id);

		if (bmrb_pdb_ets_match != null) {
			for (int i = 0; i < bmrb_pdb_ets_match.length; i++) {
				if (entry_id.equals(bmrb_pdb_ets_match[i][0]))
					return getEffectivePDBID(bmrb_pdb_ets_match[i][1]);
			}
		}

		if (bmrb_pdb_adit_match != null) {
			for (int i = 0; i < bmrb_pdb_adit_match.length; i++) {
				if (entry_id.equals(bmrb_pdb_adit_match[i][0]))
					return getEffectivePDBID(bmrb_pdb_adit_match[i][1]);
			}
		}

		if (bmrb_pdb_coor_match != null) {
			for (int i = 0; i < bmrb_pdb_coor_match.length; i++) {
				if (entry_id.equals(bmrb_pdb_coor_match[i][0]))
					return getEffectivePDBID(bmrb_pdb_coor_match[i][1]);
			}
		}

		if (bmrb_pdb_pubmed_match != null) {
			for (int i = 0; i < bmrb_pdb_pubmed_match.length; i++) {
				if (entry_id.equals(bmrb_pdb_pubmed_match[i][0]))
					return getEffectivePDBID(bmrb_pdb_pubmed_match[i][2]);
			}
		}

		try {

			String query = new String("select \"Family_name\" from \"Citation_author\" where \"Entry_ID\"='" + entry_id + "'");

			Statement state = conn_bmrb.createStatement();
			ResultSet rset = state.executeQuery(query);

			String first_author_family_name = null;
			String last_author_family_name = null;

			while (rset.next()) {

				String family_name = rset.getString("Family_name");

				if (first_author_family_name == null)
					first_author_family_name = family_name;

				last_author_family_name = family_name;

			}

			if (rset != null)
				rset.close();

			if (state != null)
				state.close();

			String sparql = "PREFIX PDBo: <http://rdf.wwpdb.org/schema/pdbx-with-vrptx-v50#> SELECT ?pdb_id COUNT(?pdb_id) AS ?count FROM <http://rdf.wwpdb.org/pdb> WHERE { ?pdb_s PDBo:database_2.database_id \"BMRB\" ; PDBo:database_2.database_code \"" + entry_id + "\" . ?pdb_s PDBo:of_datablock ?pdb_db . BIND (STRAFTER(STR(?pdb_db), \"http://rdf.wwpdb.org/pdb/\") AS ?pdb_id) BIND (IRI(CONCAT(?pdb_db, \"/citation_authorCategory\")) AS ?pdb_citation_author_category) ?pdb_citation_author_category PDBo:has_citation_author ?pdb_citation_author . ?pdb_citation_author PDBo:citation_author.name ?citation_author_name . FILTER (STRSTARTS(?citation_author_name, \"" + first_author_family_name + "\") || STRSTARTS(?citation_author_name, \"" + last_author_family_name + "\")) } GROUP BY ?pdb_id";
			File query_file = new File("queries", "get_pdb_id_from_bmrb_id_" + entry_id + ".sparql");
			FileWriter queryw = new FileWriter(query_file);
			queryw.write(sparql);
			queryw.close();

			ProcessBuilder builder = new ProcessBuilder("curl", "-F", "query=@" + query_file.getAbsolutePath(), "-F", "format=text/csv", "https://bmrbpub.pdbj.org/search/rdf");
			Process proc = builder.start();

			if (proc.waitFor() != 0)
				throw new RuntimeException("error occured while processing " + query_file.getAbsolutePath() + ".");

			InputStream is = proc.getInputStream();
			BufferedReader bufferr = new BufferedReader(new InputStreamReader(is));
			String line = bufferr.readLine();

			while ((line = bufferr.readLine()) != null) {

				String[] array = line.replaceAll("\"", "").split(",");

				if (array.length < 2)
					continue;

				try {

					int count = Integer.valueOf(array[1]);

					if (count >= 2 || (count == 1 && first_author_family_name.equals(last_author_family_name))) {
						pdb_id = array[0];
						break;
					}

				} catch (Exception e) {
					continue;
				}

			}

			query_file.delete();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return getEffectivePDBID(pdb_id);
	}

	public String getAssignedPDBDepositionCode(String rcsb_id, Connection conn_bmrb, String entry_id) {

		if (!(rcsb_id == null || rcsb_id.isEmpty() || rcsb_id.equals(".") || rcsb_id.equals("?")))
			return rcsb_id;

		String pdb_id = getAssignedPDBID(null, conn_bmrb, entry_id);

		if (!(pdb_id == null || pdb_id.isEmpty() || pdb_id.equals(".") || pdb_id.equals("?")))
			return getAssignedPDBDepositionCodeByPDBID(rcsb_id, pdb_id);

		try {

			String sparql = "PREFIX PDBo: <http://rdf.wwpdb.org/schema/pdbx-with-vrptx-v50#> SELECT ?rcsb_id FROM <http://rdf.wwpdb.org/pdb> WHERE { ?pdb_s PDBo:database_2.database_id \"BMRB\" ; PDBo:database_2.database_code \"" + entry_id + "\" . ?pdb_s PDBo:of_datablock ?pdb_db . BIND (IRI(CONCAT(?pdb_db, \"/database_2Category\")) AS ?pdb_db2c) ?pdb_db2c PDBo:has_database_2 ?rcsb_s . FILTER (CONTAINS(STR(?rcsb_s), \"RCSB\")) ?rcsb_s PDBo:database_2.database_code ?rcsb_id . }";
			File query_file = new File("queries", "get_rcsb_id_from_bmrb_id_" + entry_id + ".sparql");
			FileWriter queryw = new FileWriter(query_file);
			queryw.write(sparql);
			queryw.close();

			ProcessBuilder builder = new ProcessBuilder("curl", "-F", "query=@" + query_file.getAbsolutePath(), "-F", "format=text/csv", "https://bmrbpub.pdbj.org/search/rdf");
			Process proc = builder.start();

			if (proc.waitFor() != 0)
				throw new RuntimeException("error occured while processing " + query_file.getAbsolutePath() + ".");

			InputStream is = proc.getInputStream();
			BufferedReader bufferr = new BufferedReader(new InputStreamReader(is));
			String line = bufferr.readLine();

			while ((line = bufferr.readLine()) != null) {
				rcsb_id = line.replaceAll("\"", "");
				break;
			}

			query_file.delete();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rcsb_id;
	}

	public String getAssignedPDBDepositionCodeByPDBID(String rcsb_id, String pdb_id) {

		if (!(rcsb_id == null || rcsb_id.isEmpty() || rcsb_id.equals(".") || rcsb_id.equals("?")))
			return rcsb_id;

		try {

			String sparql = "PREFIX PDBo: <http://rdf.wwpdb.org/schema/pdbx-with-vrptx-v50#> SELECT ?rcsb_id FROM <http://rdf.wwpdb.org/pdb> WHERE {  BIND (IRI(CONCAT(\"http://rdf.wwpdb.org/pdb/\", \"" + pdb_id + "\", \"/database_2Category\")) AS ?pdb_db2c) ?pdb_db2c PDBo:has_database_2 ?rcsb_s . FILTER (CONTAINS(STR(?rcsb_s), \"RCSB\")) ?rcsb_s PDBo:database_2.database_code ?rcsb_id . }";
			File query_file = new File("queries", "get_rcsb_id_from_pdb_id_" + pdb_id + ".sparql");
			FileWriter queryw = new FileWriter(query_file);
			queryw.write(sparql);
			queryw.close();

			ProcessBuilder builder = new ProcessBuilder("curl", "-F", "query=@" + query_file.getAbsolutePath(), "-F", "format=text/csv", "https://bmrbpub.pdbj.org/search/rdf");
			Process proc = builder.start();

			if (proc.waitFor() != 0)
				throw new RuntimeException("error occured while processing " + query_file.getAbsolutePath() + ".");

			InputStream is = proc.getInputStream();
			BufferedReader bufferr = new BufferedReader(new InputStreamReader(is));
			String line = bufferr.readLine();

			while ((line = bufferr.readLine()) != null) {
				rcsb_id = line.replaceAll("\"", "");
				break;
			}

			query_file.delete();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rcsb_id;
	}

	public String getPrimaryPubMedIDViaPDB(String pubmed_id, Connection conn_bmrb, String entry_id) {

		if (!(pubmed_id == null || pubmed_id.isEmpty() || pubmed_id.equals(".") || pubmed_id.equals("?")))
			return pubmed_id;

		String pdb_id = getAssignedPDBID(null, conn_bmrb, entry_id);

		if (pdb_id == null || pdb_id.isEmpty() || pdb_id.equals(".") || pdb_id.equals("?"))
			return pubmed_id;

		try {

			String sparql = "PREFIX PDBo: <http://rdf.wwpdb.org/schema/pdbx-with-vrptx-v50#> SELECT ?pubmed_id FROM <http://rdf.wwpdb.org/pdb> WHERE {  BIND (IRI(CONCAT(\"http://rdf.wwpdb.org/pdb/\", \"" + pdb_id + "\", \"/citation/primary\")) AS ?pdb_cp) ?pdb_cp PDBo:citation.pdbx_database_id_PubMed ?pubmed_id }";
			File query_file = new File("queries", "get_primary_pubmed_id_from_pdb_id_" + pdb_id + ".sparql");
			FileWriter queryw = new FileWriter(query_file);
			queryw.write(sparql);
			queryw.close();

			ProcessBuilder builder = new ProcessBuilder("curl", "-F", "query=@" + query_file.getAbsolutePath(), "-F", "format=text/csv", "https://bmrbpub.pdbj.org/search/rdf");
			Process proc = builder.start();

			if (proc.waitFor() != 0)
				throw new RuntimeException("error occured while processing " + query_file.getAbsolutePath() + ".");

			InputStream is = proc.getInputStream();
			BufferedReader bufferr = new BufferedReader(new InputStreamReader(is));
			String line = bufferr.readLine();

			while ((line = bufferr.readLine()) != null) {
				pubmed_id = line.replaceAll("\"", "");
				break;
			}

			query_file.delete();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return pubmed_id;
	}

	static final Map<String, String> map_obsolete_pdb_id = new HashMap<String, String>() {

		private static final long serialVersionUID = 78L;

		{

			put("2GOZ", "3ZD5");
			put("3BBO", "4V61");
			put("3NZY", "3QBQ");
			put("6XDL", "7UJR");
			put("3BBN", "4V61");
			put("6R8D", "7AFQ");
			put("7LJ8", "7M1U");
			put("7LJ7", "7M1T");
			put("3HNN", "4FEK");
			put("3UH9", "4IR0");
			put("7LJ6", "7MSU");
			put("1FPX", "6CIG");
			put("3NZO", "3PVZ");
			put("4OYG", "5CFY");
			put("3UGN", "4EN0");
			put("2GOI", "4YF2");
			put("1YUV", "1VRR");
			put("3HN9", "3UMN");
			put("1SIM", "2SIM");
			put("1SIL", "2SIL");
			put("1SIC", "2SIC");
			put("2GNR", "3IRB");
			put("4OXJ", "5F0D");
			put("1SIA", "1KYJ");
			put("4OXE", "5IDD");
			put("2MZO", "5ODD");
			put("3HMA", "3RDR");
			put("6XBX", "7UJP");
			put("6XBP", "7UJQ");
			put("1Z61", "1ZAE");
			put("1SHE", "2PK8");
			put("6KHW", "7V7Y");
			put("3NXM", "3PHA");
			put("2MYR", "1E70");
			put("1FNR", "1FNB");
			put("5JIV", "5M36");
			put("5JIT", "5M35");
			put("1LZM", "2LZM");
			put("4OW9", "5m1y");
			put("4P7P", "5I0K");
			put("2GM6", "4QMA");
			put("1SGB", "2SGB");
			put("1SGA", "2SGA");
			put("1Z4D", "2FPM");
			put("1Z4C", "2FPL");
			put("2MXR", "5HUZ");
			put("1Z4B", "2FPK");
			put("1FMP", "3RTI");
			put("6QSV", "6RAV");
			put("1LZ3", "135L");
			put("2GLD", "3ETZ");
			put("2MXI", "5LDL");
			put("1LYM", "5LYM");
			put("2GLB", "3IT2");
			put("2GLA", "3IT1");
			put("2GL6", "4Z9M");
			put("2GKQ", "3ETY");
			put("3NW0", "5WY5");
			put("2MX3", "2N7H");
			put("2GKC", "2L65");
			put("3NVB", "3SLR");
			put("4II6", "4WVD");
			put("1SEO", "2ZAL");
			put("4OTW", "6OP9");
			put("1YR4", "2F91");
			put("4P63", "7CMC");
			put("5JGG", "6IYH");
			put("3NUW", "3R1X");
			put("7F8Q", "7WSV");
			put("1YQI", "3CF3");
			put("1SEC", "2SEC");
			put("1FKU", "1JFW");
			put("3UBS", "4EOU");
			put("1M98", "5UI2");
			put("4IGZ", "5VEI");
			put("9TNA", "3TRA");
			put("2GJC", "3FPZ");
			put("1YPW", "3CF2");
			put("1SE1", "1VRS");
			put("7EVX", "7WYS");
			put("1YQ0", "3CF1");
			put("1SDH", "3SDH");
			put("3HHO", "4IT5");
			put("6DX6", "6MX5");
			put("2GJ1", "5GH0");
			put("4IGC", "4YG2");
			put("1YPD", "1QPB");
			put("5D9U", "6MFM");
			put("1LW8", "1PC1");
			put("2N6Q", "5KMZ");
			put("2MUP", "6ZZF");
			put("2MUO", "6ZZE");
			put("4BYX", "4V8Z");
			put("3O5H", "4V7R");
			put("4BYW", "4V8Z");
			put("4BYV", "4V8Z");
			put("4BYU", "4V8Z");
			put("4BYT", "4V8Z");
			put("4BYS", "4V8Y");
			put("1M80", "3M10");
			put("1SCT", "4HRR");
			put("4BYR", "4V8Y");
			put("4BYQ", "4V8Y");
			put("4BYP", "4V8Y");
			put("4BYO", "4V8Y");
			put("1FJF", "1J5E");
			put("1SCP", "2SCP");
			put("4BYN", "4V8Y");
			put("3HH9", "3ADN");
			put("4BYL", "4V8Y");
			put("1LVD", "1LVE");
			put("3O58", "4V7R");
			put("3NSV", "2xyn");
			put("4P41", "4TTH");
			put("4BYE", "4V8X");
			put("4BYD", "4V8X");
			put("4BYC", "4V8X");
			put("3NSR", "4E1V");
			put("4BYB", "4V8X");
			put("4ORJ", "5HLK");
			put("3UA2", "4DUG");
			put("5D95", "6K8H");
			put("3HGC", "4NYK");
			put("1LUS", "1N0N");
			put("1SBV", "2SBV");
			put("3AYY", "5Y34");
			put("2MTH", "3MTH");
			put("3NSA", "3OHX");
			put("4P2W", "4TRL");
			put("6E7A", "6NY1");
			put("1YO3", "5WOF");
			put("6E79", "6NY2");
			put("4BXB", "5CQU");
			put("3HFL", "1YQV");
			put("4BXA", "5CQW");
			put("5CVX", "5L8Z");
			put("1SBA", "2SBA");
			put("6QNY", "6QNX");
			put("5D7T", "5JSZ");
			put("2MT2", "4MT2");
			put("2ZLZ", "4A91");
			put("1FHP", "1QQP");
			put("1M5M", "3GXZ");
			put("1M5J", "3GXY");
			put("6DUA", "6UB9");
			put("3AY1", "3VKH");
			put("3O2Z", "4V7R");
			put("1SAJ", "1SAF");
			put("1SAI", "1SAE");
			put("1SAH", "1SAF");
			put("1SAG", "1SAE");
			put("1YMI", "3BW5");
			put("3NQQ", "3POC");
			put("1YMF", "5FFM");
			put("3O30", "4V7R");
			put("1FH6", "1OHG");
			put("6DU1", "6UAP");
			put("6E5O", "6NY3");
			put("3HED", "3IN5");
			put("6DTJ", "6MVD");
			put("1M50", "3ENI");
			put("1M4O", "1ZRR");
			put("2ZKR", "4V5Z");
			put("2ZKQ", "4V5Z");
			put("2A9Q", "6EBB");
			put("1M3Y", "5TIP");
			put("3B8C", "5KSD");
			put("5CTO", "6J63");
			put("1LS0", "1o0v");
			put("2N2I", "2N7S");
			put("3NPB", "4KQY");
			put("1YKU", "3PMC");
			put("3HD9", "3IPD");
			put("1YL4", "4V4P");
			put("1YL3", "4V4P");
			put("1YL2", "2CA7");
			put("1LRD", "1LMB");
			put("1M2Y", "1RWD");
			put("6DRP", "6N1I");
			put("2N22", "5URN");
			put("6DRN", "6N1H");
			put("2GDK", "2H39");
			put("4ONB", "4R5Q");
			put("1LQR", "1TYK");
			put("6X8V", "7UIQ");
			put("2GDB", "2IL4");
			put("2A7V", "3OU5");
			put("4BTD", "4V90");
			put("4BTC", "4CR1");
			put("1FDX", "1DUR");
			put("2GCR", "1A45");
			put("2MP7", "2MQG");
			put("3NNP", "4IXS");
			put("1FE7", "1KPM");
			put("2MP6", "2N34");
			put("3HBI", "2AV0");
			put("2N0P", "5SYQ");
			put("3NNI", "3RJW");
			put("1LPT", "1GH1");
			put("1LPR", "1GBK");
			put("2N0L", "5IV1");
			put("4UYC", "5AFP");
			put("3B5V", "3HOJ");
			put("2MOD", "6ALI");
			put("1FDC", "6FDR");
			put("4OM6", "4PAY");
			put("3NMY", "4IXZ");
			put("2MNT", "2N87");
			put("3B5C", "1CYO");
			put("3ATC", "5ATC");
			put("1FD1", "2FD1");
			put("2GBD", "2QW4");
			put("1YHX", "2YHX");
			put("2ZH0", "4H4L");
			put("4UWZ", "5AM6");
			put("4UX0", "5AM7");
			put("1LNR", "1NKW");
			put("1LNP", "2JMN");
			put("2ZG5", "2ZP7");
			put("1FBJ", "2FBJ");
			put("1YGV", "3HQV");
			put("1YH6", "2Z5D");
			put("2A4Y", "2OJ2");
			put("1YH1", "3KZK");
			put("1YH0", "3KZC");
			put("3NL8", "3UD6");
			put("3NL4", "4HBC");
			put("3B3H", "3BKN");
			put("4V80", "4WWW");
			put("3B3E", "3F7J");
			put("3ARC", "3WU2");
			put("1FB4", "2FB4");
			put("1FB3", "1SM4");
			put("2MM1", "3RGK");
			put("4BPP", "4BTS");
			put("4BPO", "4BTS");
			put("4BPN", "4BTS");
			put("4OJ7", "6CNZ");
			put("1FAB", "2FAB");
			put("4BPE", "4BTS");
			put("7ELO", "7W8A");
			put("5CNZ", "5H5Z");
			put("2ZEA", "3VDQ");
			put("3TVS", "4GU5");
			put("3B2B", "3UWD");
			put("3B2A", "4L7M");
			put("2ZDW", "3A4Y");
			put("4C0V", "4CR4");
			put("4BP6", "4BP7");
			put("4BP5", "4BP7");
			put("4BP4", "4BP7");
			put("3TVH", "4V87");
			put("2ZDR", "6PPW");
			put("3TVG", "4V87");
			put("3TVF", "4V87");
			put("3TVE", "4V87");
			put("4V5U", "4WZJ");
			put("2ZDF", "3IDZ");
			put("2ZDE", "3IE1");
			put("6WRG", "7KHE");
			put("2ZDD", "3IE0");
			put("3TUW", "5ZGS");
			put("2ZDB", "3B02");
			put("6WRD", "7KHI");
			put("6DM2", "6O9G");
			put("2A2H", "2JOU");
			put("1LKU", "1O1W");
			put("3TUN", "4MVX");
			put("3TUM", "4K28");
			put("4UTD", "6CPF");
			put("6WR8", "7KHB");
			put("6WR6", "7KHC");
			put("2ZCP", "3W7F");
			put("4BNO", "4UOE");
			put("5PFK", "6PFK");
			put("2A23", "2JWO");
			put("6X2G", "6XQB");
			put("2ZCD", "3VQV");
			put("7KW5", "7LDX");
			put("6JX8", "7XFY");
			put("3TU2", "4HCL");
			put("2MIL", "2MY8");
			put("1RW7", "4QYX");
			put("2MIK", "2MY7");
			put("7KVG", "7N6N");
			put("1RW6", "3NYL");
			put("3U5I", "4V88");
			put("3U5H", "4V88");
			put("3U5G", "4V88");
			put("1RW3", "4MH8");
			put("3U5F", "4V88");
			put("3U5E", "4V88");
			put("3U5D", "4V88");
			put("4OG9", "5MJ4");
			put("3U5C", "4V88");
			put("3U5B", "4V88");
			put("356D", "364D");
			put("2A0O", "2B40");
			put("3TSX", "4V86");
			put("3TT5", "4DBK");
			put("1LIZ", "1SR3");
			put("1LIY", "2VGG");
			put("1LIX", "2VGI");
			put("1LIW", "2VGF");
			put("2STV", "2BUK");
			put("5VQC", "6BRD");
			put("1LIU", "2VGB");
			put("1RUX", "1P30");
			put("1S6T", "1VKJ");
			put("1S6S", "2IEO");
			put("3TSF", "4OEW");
			put("1RV2", "2P5O");
			put("3TSE", "4OEX");
			put("1S71", "1SGV");
			put("2A09", "2DT0");
			put("6JVE", "7XFX");
			put("1YBP", "1VQW");
			put("3AN0", "3VN9");
			put("4OEQ", "4YYX");
			put("1S6G", "2IEN");
			put("4UR5", "5HWJ");
			put("4HXH", "4L8R");
			put("2FZR", "2HZC");
			put("2FZQ", "2NYR");
			put("6DIP", "6MAL");
			put("3U42", "4REO");
			put("1YBB", "1VR4");
			put("3NFJ", "3NFN");
			put("6DIJ", "6UH1");
			put("1RU8", "3RJZ");
			put("1S65", "2IDW");
			put("5CJL", "6B4D");
			put("1YAW", "1VQV");
			put("2SSI", "3SSI");
			put("3U3C", "4R32");
			put("4OE8", "5MJ3");
			put("4UPZ", "4UJC");
			put("4UPY", "4UJD");
			put("3H9T", "3OW7");
			put("4BKI", "4CKR");
			put("4UPX", "4UJD");
			put("5J7H", "6MX8");
			put("3GXS", "3LAX");
			put("4UPW", "4UJD");
			put("4UQ7", "4D52");
			put("1LHB", "2LHB");
			put("3ALK", "4H2N");
			put("4UQ5", "4UJD");
			put("4UQ4", "4UJC");
			put("3ALI", "4JY3");
			put("3ALH", "4JY2");
			put("4UQ1", "4UJC");
			put("4I8E", "5IUC");
			put("4UQ0", "4UJC");
			put("3GXJ", "3HMM");
			put("3H9I", "3OOC");
			put("7RA6", "7SMO");
			put("6JU3", "6KUV");
			put("6JU2", "6KUJ");
			put("1LH4", "1GDJ");
			put("4V1C", "6ISO");
			put("4UPC", "5A63");
			put("5CIJ", "5DSD");
			put("5CII", "5E2X");
			put("6JTE", "7DY5");
			put("1LGG", "1T0U");
			put("1RSL", "2RSL");
			put("4BJG", "4V8W");
			put("4BJF", "4V8W");
			put("3H8P", "3TM0");
			put("3U1Z", "4MVW");
			put("4BJE", "4V8V");
			put("4BJD", "4V8V");
			put("4I7G", "4KFB");
			put("7KS5", "7LFE");
			put("3AKE", "7CKJ");
			put("4UOL", "5VYJ");
			put("2G9M", "3MWN");
			put("3U20", "4MW1");
			put("2FXJ", "5SX7");
			put("2FXH", "5SX6");
			put("5J66", "5KUD");
			put("2FXG", "5SX3");
			put("5J65", "5KUC");
			put("1RS5", "2RS5");
			put("1RRT", "1VRL");
			put("3U1G", "4MW5");
			put("1RS3", "2RS3");
			put("2FXB", "1IQZ");
			put("3U1F", "4MW0");
			put("3U1E", "4MVY");
			put("1RS1", "2RS1");
			put("1LFJ", "1S6B");
			put("3H88", "3OTA");
			put("5PAP", "4PAD");
			put("1LFF", "1SZ8");
			put("6DFZ", "6PX2");
			put("1F9Y", "3H4A");
			put("2G8O", "4IW5");
			put("4HUB", "4V9F");
			put("2G8M", "4GEV");
			put("2FWK", "3PGG");
			put("3H7E", "3RK0");
			put("2MDN", "2MZF");
			put("5CH2", "5KJI");
			put("5CH1", "5KJH");
			put("1RR5", "1U68");
			put("2MDH", "4MDH");
			put("1RR1", "2RR1");
			put("1LEF", "2LEF");
			put("3AJ0", "4KEP");
			put("4BHE", "4UQV");
			put("3H6L", "4H12");
			put("2MCS", "6AMR");
			put("3U03", "6O5D");
			put("1LDX", "2LDX");
			put("5CG4", "5XEJ");
			put("4OAC", "5LPB");
			put("2FVI", "2Q80");
			put("4OAB", "5LPY");
			put("3H6B", "3LRC");
			put("3TNK", "5DE9");
			put("4BGT", "4CL3");
			put("4HSY", "3WOH");
			put("4BGR", "4C0V");
			put("1EWG", "3B8D");
			put("4OA9", "5LPV");
			put("3TNC", "4TNC");
			put("3TNA", "4TNA");
			put("4OA6", "5LPW");
			put("4I4M", "4V9F");
			put("1S1I", "4V4B");
			put("4OA2", "5LPZ");
			put("5VKP", "6CQ9");
			put("1S1H", "4V4B");
			put("2FUW", "3K6A");
			put("4ULT", "4U50");
			put("5VKN", "6CQ8");
			put("2FV6", "2JW6");
			put("4ULS", "4U50");
			put("7XIA", "1XIB");
			put("4ULR", "4U50");
			put("3H5M", "3LRB");
			put("4ULQ", "4U50");
			put("4ULP", "4U50");
			put("4ULO", "4U4Z");
			put("2MBU", "5TLQ");
			put("4I4D", "4NEO");
			put("4ULN", "4U4Z");
			put("4ULM", "4U4Z");
			put("5CF7", "5NJI");
			put("4I4A", "4MV2");
			put("4ULK", "4U4Z");
			put("5CEU", "5JJU");
			put("1F83", "3G94");
			put("3TN1", "4RA8");
			put("4ULJ", "4U4Z");
			put("2MBP", "1ANF");
			put("3NAJ", "4FQZ");
			put("4ULI", "4U4Y");
			put("3H5D", "3VFL");
			put("4ULH", "4U4Y");
			put("1ROY", "2ROY");
			put("2MBN", "4MBN");
			put("4ULG", "4U4Y");
			put("1ROX", "2ROX");
			put("3ZZ2", "4BC6");
			put("4ULF", "4U4Y");
			put("4HRZ", "5IW9");
			put("4ULE", "4U4Y");
			put("4ULD", "4U52");
			put("3TMI", "5E3H");
			put("4ULC", "4U52");
			put("4ULB", "4U52");
			put("2G6C", "3L06");
			put("4ULA", "4U52");
			put("4HRU", "5OEG");
			put("2G6A", "3L05");
			put("4UKZ", "4U4O");
			put("5VK5", "6CQ6");
			put("2MBA", "5MBA");
			put("3H4U", "3HPA");
			put("4UKY", "4U4N");
			put("1S0K", "2AH2");
			put("4UL9", "4U52");
			put("4UKX", "4U4N");
			put("4UL8", "4U4Q");
			put("4UKW", "4U4N");
			put("4UL7", "4U4Q");
			put("4UKV", "4U4N");
			put("2G68", "3L04");
			put("4UL6", "4U4Q");
			put("4UKU", "4U4N");
			put("7EC4", "7VFM");
			put("4UL5", "4U4Q");
			put("4UKT", "4U53");
			put("3AGJ", "3WXM");
			put("4UL4", "4U4Q");
			put("4UKS", "4U53");
			put("7EC2", "7VFK");
			put("2G65", "3L02");
			put("4UL3", "4U4O");
			put("4UKR", "4U53");
			put("3NA3", "4P7A");
			put("4UL2", "4U4O");
			put("4UKQ", "4U53");
			put("4UL1", "4U4O");
			put("4UKP", "4U53");
			put("2G5Q", "4P20");
			put("4UL0", "4U4O");
			put("4UKO", "4U55");
			put("4UKN", "4U55");
			put("4UKM", "4U55");
			put("4UKL", "4U55");
			put("4UKK", "4U55");
			put("4UKJ", "4U56");
			put("4UKI", "4U56");
			put("2G5J", "3IAZ");
			put("3TLN", "8TLN");
			put("4UKH", "4U56");
			put("4UKG", "4U56");
			put("4UKF", "4U56");
			put("3GSA", "3GSB");
			put("3H4A", "3IP0");
			put("4UKE", "4U3U");
			put("1EUL", "1SU4");
			put("4UKC", "4U3U");
			put("4HQW", "5OEH");
			put("4UKB", "4U3U");
			put("1RNS", "2RNS");
			put("4UKA", "4U3U");
			put("4UJZ", "4U3M");
			put("4UJY", "4U6F");
			put("4UK9", "4U3U");
			put("4UJX", "4U6F");
			put("4UK8", "4U3N");
			put("4UJW", "4U6F");
			put("6JOA", "6LFK");
			put("1LBD", "6HN6");
			put("4UK7", "4U3N");
			put("4UJV", "4U6F");
			put("4UK6", "4U3N");
			put("4UJU", "4U6F");
			put("4UK5", "4U3N");
			put("4UJT", "4U4R");
			put("4UK4", "4U3N");
			put("4UJS", "4U4R");
			put("2YYD", "2ZOD");
			put("4UK3", "4U3M");
			put("4UJR", "4U4R");
			put("4UK2", "4U3M");
			put("4UJQ", "4U4R");
			put("3TKV", "4E9L");
			put("4UK1", "4U3M");
			put("4UJP", "4U4R");
			put("4UK0", "4U3M");
			put("4UJO", "4U4U");
			put("4UJN", "4U4U");
			put("4UJM", "4U4U");
			put("4UJL", "4U4U");
			put("4UJK", "4U4U");
			put("4UJJ", "4U51");
			put("4UJI", "4U51");
			put("2MA0", "5ZB6");
			put("4UJH", "4U51");
			put("4UJG", "4U51");
			put("5J0V", "5TZV");
			put("4UJF", "4U51");
			put("1RMW", "2B82");
			put("4I1X", "4R2V");
			put("1RN5", "1Y6H");
			put("2YY4", "5B6O");
			put("1F5I", "1K4Z");
			put("2Z9R", "2ZBD");
			put("1RN3", "3RN3");
			put("1ETD", "1R36");
			put("1F5D", "1J4X");
			put("2Z9M", "2ZXJ");
			put("3GQW", "3PBK");
			put("1ETC", "1R36");
			put("5IOG", "5WO2");
			put("1RMI", "1WU3");
			put("3H2R", "3QQD");
			put("5IOE", "5WO1");
			put("2Z9F", "3AJ2");
			put("2Z9E", "3AJ1");
			put("3GQN", "3SUC");
			put("5IOA", "5WO3");
			put("2FRN", "3K6R");
			put("2FRM", "4ND5");
			put("2FRL", "2M22");
			put("5INV", "6BD3");
			put("5INU", "6BD9");
			put("3ZVP", "4V8O");
			put("1RM7", "2B8J");
			put("2YWU", "3ACD");
			put("3ZVO", "4V8O");
			put("2Z8T", "2ZK9");
			put("2YWT", "3ACC");
			put("2YWS", "3ACB");
			put("4I0V", "4MAX");
			put("6JMJ", "6KYG");
			put("1RM2", "2RM2");
			put("3H29", "3HYX");
			put("3H28", "3HYW");
			put("3H27", "3HYV");
			put("1RLL", "1VJJ");
			put("3ADN", "3O4F");
			put("4HOL", "4NAQ");
			put("2FQV", "2HBZ");
			put("2FQU", "2HBY");
			put("2FQS", "2HBR");
			put("3ADH", "4ADH");
			put("5INA", "5WNW");
			put("1F49", "4V41");
			put("2FQR", "2HBQ");
			put("7KL4", "7UIS");
			put("6JLX", "6K72");
			put("6JLW", "6K71");
			put("6JLT", "6K4I");
			put("2G2J", "2RB4");
			put("1ERL", "2ERL");
			put("1ERK", "5UMO");
			put("1F3I", "1MUH");
			put("4NZX", "4RZN");
			put("1RKO", "1ZKN");
			put("3GOW", "3HRX");
			put("5VG5", "6E07");
			put("2YVD", "3IE2");
			put("5VFL", "6E08");
			put("2G23", "3GYR");
			put("1EQS", "1J9M");
			put("4NZE", "6EO9");
			put("5CA4", "5GW7");
			put("1EQO", "1Q0N");
			put("2G1I", "2VK4");
			put("8RUB", "8RUC");
			put("3AC6", "3VIU");
			put("6Q8L", "6HVK");
			put("4NYS", "5EPQ");
			put("1F1Y", "1Q0O");
			put("1XVH", "4KJM");
			put("3GNH", "3MTW");
			put("4HLV", "4QNE");
			put("4HLP", "4OSY");
			put("4HLO", "4OSX");
			put("1XV1", "2Z5F");
			put("2FNZ", "4ND1");
			put("2FNY", "3E47");
			put("1RIG", "2RIG");
			put("4UES", "5FTA");
			put("3MYS", "3PH7");
			put("3AAH", "4AAH");
			put("1F19", "2F19");
			put("2FNR", "1FND");
			put("2Z5A", "2ZS6");
			put("1EOR", "1F9L");
			put("5IJY", "6CDK");
			put("1RHV", "2RHV");
			put("1XTX", "2DJJ");
			put("2Z4N", "4V5Y");
			put("2Z4M", "4V5Y");
			put("2Z4L", "4V5Y");
			put("4UDZ", "5AAW");
			put("2Z4K", "4V5Y");
			put("1F0A", "1GHH");
			put("4NX3", "4WEB");
			put("2SGB", "3SGB");
			put("7DZQ", "7V6Y");
			put("1RHE", "2RHE");
			put("7DZP", "7V6Z");
			put("3MY3", "3OPG");
			put("7KGL", "7MFV");
			put("1ENL", "2ENL");
			put("4NVZ", "4V9S");
			put("1ENG", "2ENG");
			put("4NVY", "4V9S");
			put("4NVX", "4V9R");
			put("4NVW", "4V9R");
			put("1END", "2END");
			put("4NVV", "4V9R");
			put("4NVU", "4V9R");
			put("4NW1", "4V9S");
			put("4NW0", "4V9S");
			put("4UD3", "5AAM");
			put("2FM3", "4ND4");
			put("1EN0", "1I3Q");
			put("2YQX", "2ZKY");
			put("7KFJ", "7LFP");
			put("2YQW", "2ZKX");
			put("1RFW", "1SZX");
			put("2Z2V", "3A63");
			put("2YQV", "2ZKW");
			put("6WA7", "6XFS");
			put("2Z2Q", "4FTB");
			put("1RFM", "2X06");
			put("1KYG", "1YEP");
			put("5VB4", "6DCO");
			put("1Y41", "2HR9");
			put("1KYE", "3LIW");
			put("3MW9", "6DHM");
			put("4O73", "4PS5");
			put("5VB1", "6DCN");
			put("1KYB", "1O0R");
			put("2FKV", "2GGK");
			put("2FKU", "2GGL");
			put("2FKT", "2GGJ");
			put("2FKS", "2GGI");
			put("2FKR", "2GGH");
			put("3MVQ", "6DHL");
			put("2FKQ", "2GGG");
			put("3MVO", "6DHN");
			put("3GJG", "3HAE");
			put("1XQU", "5UVM");
			put("2SDH", "4SDH");
			put("1REL", "2REL");
			put("1L9F", "2GB0");
			put("3TBX", "4NSK");
			put("4HHC", "4IZK");
			put("1EKT", "1Z0R");
			put("3ZNW", "4D2O");
			put("1L8U", "2B0Q");
			put("2FJJ", "2K3K");
			put("4AZX", "4CCT");
			put("2YOV", "5AEQ");
			put("2YOU", "5AER");
			put("1KWN", "6COA");
			put("3MUB", "3S46");
			put("4HH7", "4PM3");
			put("3GHX", "3N10");
			put("1EKC", "1G1X");
			put("1XQ2", "2AO6");
			put("3ZNE", "4V8N");
			put("3ZND", "4V8N");
			put("3MU9", "3RT4");
			put("4NT3", "6LCO");
			put("2Z0H", "3HJN");
			put("2Z0C", "3A4I");
			put("3ZN9", "4V8N");
			put("3ZN7", "4V8N");
			put("4NSF", "4XAN");
			put("1L7W", "1OQM");
			put("2SBV", "3SBV");
			put("5C9T", "5T8G");
			put("1L7U", "1M40");
			put("1L7S", "1VPO");
			put("2SC2", "3SC2");
			put("4HFY", "4RN6");
			put("5BXK", "6JBG");
			put("4HG1", "4IQN");
			put("1XP2", "2VO9");
			put("3GGT", "3U7S");
			put("1Y0F", "3HR2");
			put("3MSM", "3S2O");
			put("5C96", "5DF6");
			put("5BWS", "6JBF");
			put("1EIP", "2EIP");
			put("1KUS", "1LKC");
			put("3GGB", "3U89");
			put("1EIM", "1KW9");
			put("1KUO", "2IZM");
			put("1XO9", "2KMW");
			put("2YMR", "4V5X");
			put("1L6K", "2OU1");
			put("4HER", "4R2W");
			put("1XO4", "2EVN");
			put("3MRZ", "4V7P");
			put("1XNP", "2P4W");
			put("4HEN", "4R2X");
			put("2YMI", "4V5X");
			put("2YMH", "4V5X");
			put("3N48", "3PEL");
			put("2YMG", "4V5X");
			put("3N3V", "4ITR");
			put("2YMF", "4V5X");
			put("3GFN", "3V0Y");
			put("3N3S", "5SXX");
			put("3N3R", "5SXW");
			put("3N3Q", "5SXT");
			put("7KB4", "7LA6");
			put("3MS1", "4V7P");
			put("3N3P", "5SXS");
			put("3MS0", "4V7P");
			put("3N3O", "5SXR");
			put("3N3N", "5SXQ");
			put("5OOX", "6T7O");
			put("2FGD", "2PC6");
			put("3N3C", "6V0T");
			put("4AWR", "3ZHA");
			put("2FGA", "2YWP");
			put("1RB1", "3K7Z");
			put("4NQ7", "5W8W");
			put("1XMR", "2UZ3");
			put("3GEV", "4Q4Q");
			put("4B8I", "4V8U");
			put("4B8H", "4V8U");
			put("3MR8", "4V7P");
			put("4B8G", "4V8U");
			put("4B8F", "4V8U");
			put("3MQU", "3NL9");
			put("3MR4", "3SI8");
			put("5ICB", "1IG5");
			put("6CU4", "6V2N");
			put("4NPG", "4Q1Z");
			put("3A9A", "3ABX");
			put("1EH0", "1EUP");
			put("1KST", "1N4Y");
			put("3ZK3", "5A1J");
			put("3N2F", "4RES");
			put("6D5I", "6V2L");
			put("2FFE", "3CGW");
			put("2YL4", "4AYT");
			put("5IC2", "5MHB");
			put("3A97", "3ADP");
			put("2LRF", "2MDL");
			put("2LRB", "2MP4");
			put("1XM0", "2KZN");
			put("5BUB", "5LGY");
			put("3MQ8", "4H70");
			put("5UZO", "5VSV");
			put("6CSY", "6MXT");
			put("1KSA", "3ENI");
			put("5IBA", "5B6P");
			put("3N22", "4DUQ");
			put("3ZJ3", "4P5H");
			put("6D4J", "6OEV");
			put("2FEG", "2H01");
			put("3MPF", "5QBV");
			put("3MPE", "5QBY");
			put("6D4H", "6OEU");
			put("4AUS", "4UON");
			put("1KRK", "1N2Y");
			put("5UZ5", "6N7X");
			put("2FDZ", "1VSN");
			put("6W9J", "6XAE");
			put("3MOV", "3TYY");
			put("3N16", "4UTH");
			put("4B6B", "4V8T");
			put("4B6A", "4V8T");
			put("2M25", "2N17");
			put("1XKC", "1YEM");
			put("1L2R", "1NTH");
			put("4ATR", "4BL4");
			put("1EEE", "1FLG");
			put("3N09", "4V7Q");
			put("5OLI", "6QE4");
			put("3MNT", "3O7W");
			put("4ATC", "6AT1");
			put("2FD1", "3FD1");
			put("3ZGW", "4BL1");
			put("5C3V", "6CMW");
			put("3ZGU", "4BVT");
			put("3ZGT", "4BVS");
			put("3ZGS", "4BVR");
			put("3GBC", "3PL1");
			put("3ZGR", "4BVQ");
			put("4B4T", "4CR2");
			put("1KPO", "4V43");
			put("2YI4", "4V5W");
			put("2YI3", "4V5W");
			put("2YI2", "4V5W");
			put("2YHQ", "4V5V");
			put("4ASP", "3ZLK");
			put("2YHP", "4V5V");
			put("5BRI", "5IC5");
			put("1KPJ", "1LNR");
			put("3A63", "3ABI");
			put("5BRC", "5XBP");
			put("1L1B", "1VL3");
			put("3GAP", "1G6N");
			put("4U9T", "4WJW");
			put("5V8N", "6NAO");
			put("1L1A", "1MUR");
			put("5V8J", "6NFO");
			put("5V8H", "6NFG");
			put("5UWE", "6W4M");
			put("4B3Y", "4V8L");
			put("4AS6", "3ZLL");
			put("1ECK", "2ECK");
			put("2YGR", "3ZQJ");
			put("1KOM", "1T23");
			put("4TWX", "4WY3");
			put("6PIE", "6U3L");
			put("2FB1", "5BS6");
			put("3ZEY", "4V8M");
			put("3ZEX", "4V8M");
			put("3ZF7", "4V8M");
			put("5BQ4", "5KD9");
			put("5BPR", "5KCW");
			put("3ZEQ", "4V8M");
			put("2FAG", "2QZY");
			put("3ZEN", "4V8L");
			put("1KNN", "1MC9");
			put("5C1K", "5ENV");
			put("2FAB", "3FAB");
			put("4NJZ", "6YPX");
			put("4NJY", "6YPR");
			put("4NJX", "6YI0");
			put("5UUW", "7MTU");
			put("5V78", "5O66");
			put("1QZO", "2DSZ");
			put("5UUR", "6DAY");
			put("3MKX", "3NWH");
			put("2YFG", "4BHT");
			put("5OIJ", "6QJN");
			put("4NK0", "6YQD");
			put("5OII", "6QJL");
			put("5OIH", "6QJK");
			put("5OIG", "6QJJ");
			put("5OIB", "6QJI");
			put("5BP6", "5KCU");
			put("6CO0", "6OP5");
			put("2YF6", "4D0B");
			put("2YF5", "4D0C");
			put("5OHW", "6QJD");
			put("2RXN", "4RXN");
			put("1XG9", "1ZGH");
			put("5OI6", "6QJG");
			put("3ZDK", "5AHO");
			put("4B1P", "4V8S");
			put("2YF1", "5AD0");
			put("3A2U", "3VZU");
			put("4B1O", "4V8S");
			put("5OI4", "6QJF");
			put("3A2T", "3VZW");
			put("5C0H", "5N1Y");
			put("3A2R", "3VZT");
			put("4APK", "4V8R");
			put("1QYH", "1U2O");
			put("5BNU", "5KCT");
			put("3MJJ", "3TKK");
			put("3ZD3", "3ZP8");
			put("1QY5", "6D28");
			put("1R9R", "1SFO");
			put("2YDN", "4B7U");
			put("4AOL", "4V8R");
			put("2LKA", "2LO7");
			put("4NI4", "4QN2");
			put("1R9E", "4MTJ");
			put("3MIQ", "3OGO");
			put("3T76", "3TYS");
			put("5USI", "6B9J");
			put("3T75", "3TYR");
			put("3MIM", "5YRS");
			put("6IXU", "7E2W");
			put("5USC", "6U60");
			put("2LJO", "2MAL");
			put("5BMR", "5KL0");
			put("3T6M", "4OP4");
			put("4B06", "4BNC");
			put("3T6H", "5JVA");
			put("1R8R", "1S9D");
			put("5URR", "7MTX");
			put("6IXB", "7CYD");
			put("347D", "362D");
			put("4TSW", "5TSW");
			put("6IXA", "7CYC");
			put("4TSU", "1OH0");
			put("3T68", "4ONW");
			put("3MHQ", "4H5X");
			put("3MHN", "4H71");
			put("5V3E", "6NP6");
			put("2YC9", "4AYZ");
			put("2YC8", "4BI5");
			put("2YC7", "4BI7");
			put("2YC6", "4BI6");
			put("1R83", "2CVR");
			put("1R7K", "1Z9W");
			put("1QVH", "2A45");
			put("2RUB", "5RUB");
			put("4U3K", "5MY3");
			put("3T4I", "4HJV");
			put("1KIN", "1KIM");
			put("2YB3", "2YPU");
			put("1R72", "1XCB");
			put("1XC2", "1YQG");
			put("2LH4", "1GDJ");
			put("5UPC", "6ALH");
			put("1KI5", "2KI5");
			put("5UPA", "6ALF");
			put("1QU8", "1HYK");
			put("1XAZ", "2PJI");
			put("1R5R", "3BJH");
			put("4H8T", "4L5H");
			put("3T3B", "3TRU");
			put("5UP6", "6ALG");
			put("6VNN", "7KCD");
			put("1QTL", "1C5F");
			put("1XAQ", "2GA5");
			put("5V14", "5VH1");
			put("4U29", "4YIR");
			put("2RSA", "3RSA");
			put("3FXN", "5NLL");
			put("4GWH", "4QFW");
			put("2EZ3", "2NLO");
			put("3T2U", "5D73");
			put("5V0G", "6CTY");
			put("1DZS", "2IZ9");
			put("3FXC", "4FXC");
			put("4TPF", "4U27");
			put("3SQK", "4F4J");
			put("4TPE", "4U27");
			put("4TPD", "4U27");
			put("4TPC", "4U27");
			put("4TPB", "4U26");
			put("6PAP", "5PAD");
			put("4TPA", "4U26");
			put("3SQA", "3TB3");
			put("3G95", "3L3C");
			put("4TOX", "4U20");
			put("4TP9", "4U26");
			put("4TOW", "4U20");
			put("4TP8", "4U26");
			put("5OBL", "7OBE");
			put("4TOV", "4U20");
			put("4TP7", "4U25");
			put("4TOU", "4U20");
			put("4TP6", "4U25");
			put("3T1Z", "4HJY");
			put("4TP5", "4U25");
			put("4TP4", "4U25");
			put("3G8N", "3G8O");
			put("4TP3", "4U24");
			put("3FWM", "3VMA");
			put("4TP2", "4U24");
			put("4TP1", "4U24");
			put("4TOO", "4U1V");
			put("4TP0", "4U24");
			put("1DYV", "1UN2");
			put("4TON", "4U1V");
			put("4TOM", "4U1V");
			put("3T23", "3UKJ");
			put("4TOL", "4U1V");
			put("125D", "1AW6");
			put("3T21", "4HJZ");
			put("3FWD", "4H57");
			put("1R3P", "1VJM");
			put("4H70", "4HCO");
			put("5UN4", "6BLM");
			put("5OAG", "6F6P");
			put("3T18", "4EMY");
			put("4H6G", "4V9E");
			put("4H6F", "4V9E");
			put("6J3W", "7XFW");
			put("3T0N", "4EY3");
			put("6IRN", "6K1P");
			put("1R2V", "2DPE");
			put("6IRM", "6JYL");
			put("2EWD", "4ND2");
			put("4ZZG", "5NIF");
			put("2LDH", "4LDH");
			put("5HSN", "5X80");
			put("5HSL", "5X7Z");
			put("5I4G", "5ID5");
			put("7K1Z", "7LVA");
			put("1KED", "1Z7J");
			put("4H5H", "4NZ8");
			put("5HSB", "6Q99");
			put("4AHB", "4UQ7");
			put("1KDZ", "6M9F");
			put("3MBN", "5MBN");
			put("1KDY", "6M8Y");
			put("3SNR", "3UK0");
			put("7DDJ", "7WYT");
			put("1KDV", "6M8W");
			put("4AGY", "4BIQ");
			put("7DDG", "7EVX");
			put("4AGX", "4BIP");
			put("1QQ8", "1N45");
			put("1KE2", "6M9D");
			put("4AH5", "4D4U");
			put("1KE1", "6M9C");
			put("2LCD", "2MAM");
			put("4TLY", "5KN1");
			put("6IQB", "6M46");
			put("3FU4", "3K1V");
			put("6IQA", "6M45");
			put("3G63", "4F1V");
			put("3G62", "4F1U");
			put("7JOX", "7KHP");
			put("2EV7", "2I18");
			put("1R1E", "1ERI");
			put("4H4H", "4I51");
			put("1E7X", "2C4Q");
			put("3SMU", "3U7X");
			put("4GSE", "4IFH");
			put("4TLO", "4QHR");
			put("6IQ9", "6M43");
			put("1DW7", "1GH9");
			put("6IQ8", "6M42");
			put("6IQ7", "6M41");
			put("1E7T", "1GK6");
			put("4ZXJ", "5T3D");
			put("2RNP", "3RNP");
			put("2EUE", "3FAM");
			put("1R0T", "1Z7K");
			put("5I31", "5U73");
			put("2EUB", "2PL1");
			put("4AFO", "4BQT");
			put("6VIQ", "7JVF");
			put("2XZN", "4V5O");
			put("6VJ1", "7UJ7");
			put("2XZM", "4V5O");
			put("5I2J", "5KDT");
			put("2XZH", "4G55");
			put("3FSQ", "5DPK");
			put("3FSP", "6Q0C");
			put("2EU6", "2PKX");
			put("1QOE", "1J7C");
			put("3MA4", "3O3T");
			put("7JNU", "7UUT");
			put("1QOD", "1J7B");
			put("4TL2", "4ZDN");
			put("1QOC", "1J7A");
			put("3SM5", "5UGY");
			put("7JNQ", "7UTC");
			put("2ETO", "3D9V");
			put("1E6T", "2BU1");
			put("1KC9", "1KPJ");
			put("4ZWK", "5KCF");
			put("2ETG", "3BFX");
			put("1R07", "2R07");
			put("7JNG", "7N6M");
			put("1R06", "2R06");
			put("4ZWH", "5KCE");
			put("1KC0", "1N2S");
			put("1R04", "2R04");
			put("7JMZ", "7N6L");
			put("3G41", "3N26");
			put("1DU7", "2X9D");
			put("4ZW4", "5CST");
			put("1DTR", "2DTR");
			put("7JML", "7N6K");
			put("1KAU", "1FWJ");
			put("2Y9S", "2YFU");
			put("4H28", "4JY3");
			put("2Y9O", "4BWF");
			put("6VGP", "7JU9");
			put("1QMM", "1E8X");
			put("2Y9L", "2YOK");
			put("1QMK", "1QNV");
			put("2Y9I", "2YFZ");
			put("4ADH", "8ADH");
			put("2ES8", "2F2S");
			put("4H1K", "4JLQ");
			put("3FQP", "4NP8");
			put("7JM8", "7N6J");
			put("2XXE", "4A73");
			put("3SJY", "4M53");
			put("2ERT", "3ERT");
			put("2Y9D", "4V5U");
			put("2Y9C", "4V5U");
			put("2Y9B", "4V5U");
			put("2Y9A", "4V5U");
			put("6CB4", "6V7G");
			put("1E53", "1Z60");
			put("6IMS", "7W0X");
			put("2RL6", "3CY4");
			put("2F3H", "4KXP");
			put("1KA3", "1TKV");
			put("2RKP", "3H30");
			put("2ERD", "3ERD");
			put("2Y94", "4CFH");
			put("4ACQ", "6TAV");
			put("1DSH", "1Y0D");
			put("4ZUC", "5KCD");
			put("4GOR", "4KI2");
			put("4ZUB", "5KCC");
			put("2Y8M", "2YG0");
			put("4H0Q", "4RFP");
			put("3FPQ", "6CN9");
			put("4ZU8", "5DBE");
			put("4ZTW", "5CSO");
			put("1QLA", "2BS2");
			put("5UGE", "5UVF");
			put("1DRQ", "1LT0");
			put("3SIN", "4JKI");
			put("1E3N", "1OGX");
			put("2Y7V", "4V5T");
			put("5HMT", "5KKW");
			put("2Y7U", "4V5T");
			put("2Y86", "3ZOI");
			put("2XW5", "2Y4M");
			put("2Y7T", "4V5T");
			put("4ABS", "4V8Q");
			put("2XW4", "2Y4L");
			put("4ABR", "4V8Q");
			put("1DRI", "2DRI");
			put("2EQC", "2RRT");
			put("2XW3", "2Y4K");
			put("2XW2", "2Y4J");
			put("4GNS", "4YG8");
			put("1WX3", "2ZMX");
			put("3FOS", "4DBJ");
			put("2XUY", "4V5N");
			put("2XUX", "4V5N");
			put("3SHH", "4HCH");
			put("4GN6", "6M7E");
			put("5UF9", "6C42");
			put("1X7U", "5L02");
			put("2XUN", "2XZW");
			put("1E2C", "2E2C");
			put("3G07", "5UNA");
			put("2EP9", "3A97");
			put("1X7L", "2JQA");
			put("4MYJ", "5DYK");
			put("4ZS5", "5V3B");
			put("4MYI", "5DYL");
			put("2RHV", "4RHV");
			put("3LZH", "3R7Z");
			put("5HKU", "5IWO");
			put("5HKT", "5IWN");
			put("4ZRH", "5DOD");
			put("2RHN", "1AYN");
			put("4AA3", "4AYW");
			put("2Y5R", "3ZVT");
			put("3SGF", "4V85");
			put("1QJ2", "1N5W");
			put("2Y5O", "3ZVW");
			put("1WUS", "2DWK");
			put("5NWO", "6Y3E");
			put("1E1B", "1E5U");
			put("1X72", "2CMU");
			put("4MXS", "4QM1");
			put("2XTG", "4V5M");
			put("1X6K", "2A29");
			put("5O8G", "6G9Z");
			put("3SFS", "4V85");
			put("4MXH", "5EUA");
			put("4MXG", "5EOO");
			put("1WUC", "3CTK");
			put("2ENL", "3ENL");
			put("3LYJ", "3AL4");
			put("2XSY", "4V5M");
			put("5UDB", "5V8F");
			put("1DOO", "1E94");
			put("2XT9", "6I2Q");
			put("4MXB", "5EPH");
			put("6OV5", "6PZN");
			put("6OV4", "6PZM");
			put("1E0I", "1GKB");
			put("1X5U", "5GVQ");
			put("4MX4", "5EOE");
			put("3LXW", "3V70");
			put("4MWN", "5I5Q");
			put("4MWM", "5HQ1");
			put("5UCL", "6NJG");
			put("6IHZ", "7EMN");
			put("4MWK", "5HMV");
			put("3M9P", "3OA6");
			put("1E01", "1E0G");
			put("5HIV", "5HOL");
			put("2RFP", "3MQU");
			put("2Y3T", "4BEB");
			put("1WSY", "1BKS");
			put("3FKX", "3HWQ");
			put("1WT4", "2V1T");
			put("3LWY", "3N2C");
			put("5UC2", "6NTR");
			put("4MVP", "5SYK");
			put("3M8T", "5WCM");
			put("1JYZ", "4V44");
			put("1JYY", "4V44");
			put("1DN1", "3C98");
			put("4N7A", "6LF7");
			put("5HHU", "6BO7");
			put("2ELF", "3WNB");
			put("2REP", "5WDH");
			put("1JYP", "2PJF");
			put("1JZ1", "4V45");
			put("1JZ0", "4V45");
			put("1X3V", "2DMO");
			put("1JYG", "1ryk");
			put("2EKW", "3I5I");
			put("2EKV", "3I5H");
			put("2XQE", "4V5L");
			put("2XQD", "4V5L");
			put("1DLX", "1LY7");
			put("4N6I", "4XYN");
			put("5UAF", "6UBW");
			put("2KWR", "2KXI");
			put("3SD1", "4LVV");
			put("2KWM", "2LML");
			put("1JXR", "2JXR");
			put("6ORS", "7RY7");
			put("2Y1U", "2Y4R");
			put("2XPT", "4D7N");
			put("5AZM", "5WQW");
			put("2XPS", "4D7M");
			put("3FIZ", "4HZ7");
			put("3FIY", "4HZ6");
			put("4N5W", "4UAB");
			put("2XPF", "2Y1U");
			put("3FIO", "3GHD");
			put("3FJ0", "4HZ8");
			put("3FIN", "4V68");
			put("1WQI", "2E1O");
			put("3SBV", "4SBV");
			put("3FIK", "4V69");
			put("1DKV", "1KFU");
			put("2EJP", "2ZMW");
			put("2EJO", "2ZMU");
			put("3FIH", "4V69");
			put("2Y0Z", "4V5Q");
			put("2Y0Y", "4V5Q");
			put("2EJI", "2ZO7");
			put("2Y0X", "4V5P");
			put("2Y19", "4V5R");
			put("3FIC", "4V68");
			put("1QDX", "1ES1");
			put("2EJH", "2ZO6");
			put("2Y0W", "4V5P");
			put("2Y18", "4V5R");
			put("5HG6", "5IJD");
			put("1QE8", "1C7J");
			put("2Y0V", "4V5P");
			put("2Y17", "4V5S");
			put("2Y0U", "4V5P");
			put("2Y16", "4V5S");
			put("5HG4", "5IJC");
			put("2Y15", "4V5S");
			put("5HG3", "5IJB");
			put("2Y14", "4V5S");
			put("2Y13", "4V5Q");
			put("2Y12", "4V5Q");
			put("2Y11", "4V5R");
			put("2Y10", "4V5R");
			put("6IEF", "7EH9");
			put("6IEE", "7EHA");
			put("1K8E", "1O17");
			put("3FHS", "4TOP");
			put("3LTX", "4N1Y");
			put("7D7T", "7E35");
			put("4GGI", "4J6E");
			put("1WPH", "2PAQ");
			put("4GGE", "4QEF");
			put("1WPE", "2Z9R");
			put("1JW7", "2PD4");
			put("1K87", "4O8A");
			put("3M5F", "3S4L");
			put("2KUJ", "2LRE");
			put("6C8A", "6E7F");
			put("4N46", "4XL4");
			put("1JVF", "2PD3");
			put("6BW7", "6CFM");
			put("4N3L", "6EO8");
			put("2KU8", "2LRD");
			put("4ZL3", "5X6M");
			put("2EHN", "3AH4");
			put("4ZL2", "5X6H");
			put("2EHM", "3AH2");
			put("3FGF", "4FGF");
			put("2EHK", "2ZTI");
			put("2EHI", "3AH1");
			put("5NPX", "6QCC");
			put("1K71", "3L1Q");
			put("1DIJ", "2DIJ");
			put("4ZKG", "5X6G");
			put("2KTG", "2LC5");
			put("4A9P", "4ALH");
			put("1WNQ", "2D2D");
			put("6ICB", "1IGV");
			put("2EGX", "3U6U");
			put("4MQO", "4CXR");
			put("3FG0", "4MPY");
			put("4MQN", "4CXQ");
			put("2KSX", "2KZ1");
			put("1DHX", "1P2Z");
			put("4GEF", "4KQ6");
			put("3FFJ", "3N04");
			put("1JTR", "1MWA");
			put("4A96", "2YOE");
			put("2XM6", "4BWR");
			put("4GDW", "4PVS");
			put("4GDV", "4PVR");
			put("4GDU", "4PVQ");
			put("4GDT", "4PVP");
			put("1DHB", "2DHB");
			put("3M2S", "3Q09");
			put("4GDH", "4QYT");
			put("1DGY", "1LII");
			put("4GDG", "4O7I");
			put("3M2Q", "3Q08");
			put("4ZJ6", "5F8X");
			put("2L3V", "2N57");
			put("4ZJ5", "5F8T");
			put("3M2O", "3VCX");
			put("4GDD", "5VWU");
			put("4ZJ4", "5F8Z");
			put("2EG0", "3VNP");
			put("4GDC", "5VWT");
			put("1DGT", "1V9P");
			put("2EFM", "3LXS");
			put("2KS3", "2L38");
			put("6IB4", "6RVW");
			put("1DH2", "1LIO");
			put("6IB3", "6RVV");
			put("1DH1", "1LIJ");
			put("1DH0", "1LIK");
			put("1QA8", "1GFW");
			put("2L3K", "2LXD");
			put("4A7O", "4UQO");
			put("1JSK", "4PP8");
			put("4GD2", "4V9D");
			put("4GD1", "4V9D");
			put("1WLQ", "2ZXX");
			put("1DGA", "1NM1");
			put("5HBG", "6AHI");
			put("1JSB", "1ryj");
			put("1WLL", "3A20");
			put("2KR8", "2XEB");
			put("5B69", "6JO3");
			put("1DFR", "3DFR");
			put("7VX7", "7WEV");
			put("1K3P", "4G6B");
			put("4A74", "4D6P");
			put("5TZ9", "6O1S");
			put("4GBO", "5UIZ");
			put("5ATC", "7ATC");
			put("3FD1", "4FD1");
			put("2KPS", "2L3U");
			put("1DER", "1KP8");
			put("5B54", "5Z5C");
			put("3LOH", "4ZXB");
			put("1K31", "1KSM");
			put("7CQA", "7VJQ");
			put("2L1I", "5K5F");
			put("4GAU", "4V9C");
			put("2XIP", "2XWC");
			put("4GB4", "4HN2");
			put("4GAS", "4V9C");
			put("4GAR", "4V9C");
			put("4GAQ", "4V9C");
			put("6OKI", "6WUP");
			put("7CQ9", "7VJP");
			put("7CQ8", "7VJO");
			put("6C38", "6CM4");
			put("6OKA", "6U0X");
			put("1WJH", "2ECC");
			put("2L0V", "2L51");
			put("2XIA", "7XIA");
			put("2L0U", "2L50");
			put("3FBH", "2YEY");
			put("6C2P", "6DTA");
			put("3RZQ", "4GHM");
			put("6OK9", "6U0W");
			put("1DDQ", "1HQM");
			put("6C2L", "6DT8");
			put("6C2K", "6CNP");
			put("6C2J", "6DT7");
			put("5B3M", "6J47");
			put("6C2E", "6CNQ");
			put("1WJ8", "1WTY");
			put("2XHQ", "4AXQ");
			put("3LNA", "3O8K");
			put("1DDC", "1H21");
			put("4MLU", "4O6W");
			put("5B3C", "5XP1");
			put("2EBX", "3EBX");
			put("4ZEZ", "5JZI");
			put("3FAM", "3HYH");
			put("1JOZ", "1LLH");
			put("2KO5", "2M1S");
			put("2KO4", "2LPB");
			put("1K16", "1J5B");
			put("3FAB", "7FAB");
			put("1K0Q", "1KTI");
			put("4ZEH", "5OKB");
			put("2EBC", "3UMR");
			put("2EAS", "4YCL");
			put("4A3A", "4ATM");
			put("2QYX", "3NEK");
			put("2XFZ", "4V5K");
			put("3LLJ", "3QGZ");
			put("3RXN", "7RXN");
			put("3LLG", "3VOS");
			put("6C0I", "6NPZ");
			put("2QZ1", "3FKX");
			put("5NIX", "6R3K");
			put("1WGZ", "5WVU");
			put("1K00", "1J5A");
			put("5APL", "5N9W");
			put("2XG2", "4V5K");
			put("2XG1", "4V5K");
			put("5API", "7API");
			put("6C0C", "6CD7");
			put("2XG0", "4V5K");
			put("1DBC", "1O3T");
			put("2QYE", "2RDX");
			put("5APB", "5N9X");
			put("5U71", "5UAK");
			put("1JNC", "1NT7");
			put("3LL6", "4O38");
			put("1JNA", "1NWG");
			put("1DB9", "1O3S");
			put("1DB8", "1O3R");
			put("1DB7", "1O3Q");
			put("6C03", "6DXO");
			put("3RWS", "4JHG");
			put("5AOW", "6BII");
			put("1JN8", "1NMM");
			put("4ZCO", "5CHB");
			put("1WGA", "2WGA");
			put("1DB0", "1H1V");
			put("5B11", "6J48");
			put("1JMR", "1MKM");
			put("5B10", "6J49");
			put("1WG9", "1XKV");
			put("4A1Q", "4AAI");
			put("2QXC", "4LOE");
			put("2QXB", "4LO9");
			put("3LJV", "3P3Q");
			put("2QXA", "4KVP");
			put("4A1E", "4V8P");
			put("4A1D", "4V8P");
			put("3S89", "4R1W");
			put("3RVX", "5VPH");
			put("4A1C", "4V8P");
			put("3RVW", "5VPG");
			put("4A1B", "4V8P");
			put("4MIL", "4ZU1");
			put("3RVV", "5VPL");
			put("4A1A", "4V8P");
			put("5TTI", "6DLL");
			put("4MII", "4ZU8");
			put("3S80", "4LTG");
			put("2KL0", "2LEK");
			put("4A19", "4V8P");
			put("4A18", "4V8P");
			put("4A17", "4V8P");
			put("1Q9V", "3T4Y");
			put("2XDT", "2YD0");
			put("1Q9T", "3T77");
			put("1Q9R", "3T65");
			put("1Q9Q", "3SY0");
			put("1WF4", "4V4O");
			put("2R8C", "3MKV");
			put("1PXF", "3ERS");
			put("3LJ4", "4V4K");
			put("5TSI", "5UAR");
			put("1WED", "2WED");
			put("2KJS", "2KWM");
			put("1WEC", "2WEC");
			put("1WEB", "2WEB");
			put("1WEA", "2WEA");
			put("3S6N", "5XJL");
			put("6BLL", "6DNH");
			put("6V2Z", "7M0V");
			put("6V2Y", "7M0Y");
			put("2QVQ", "4LOF");
			put("6V2X", "7M0U");
			put("4ZAI", "7JNY");
			put("5NG8", "6FXC");
			put("6V2V", "7M0X");
			put("4MH9", "4ZU6");
			put("5GYM", "6JNO");
			put("6V32", "7M0T");
			put("6V31", "7M0Z");
			put("6V30", "7M0W");
			put("1WE3", "4V4O");
			put("3LI7", "4ZM8");
			put("228D", "1A6H");
			put("1WDH", "1ZS9");
			put("3RTU", "4GUY");
			put("1JJY", "1SU8");
			put("1WDB", "1HKO");
			put("2KJ2", "2KQH");
			put("2KJ0", "2KQG");
			put("1JJN", "2B8S");
			put("1JJM", "2B8R");
			put("5U37", "6CJ9");
			put("2XBH", "1C4B");
			put("4FYL", "4HEI");
			put("3LH7", "3PSN");
			put("3LH6", "3PSO");
			put("3S59", "4OI8");
			put("3S58", "4OI7");
			put("4FYA", "4V99");
			put("1WCA", "2BOV");
			put("3EZD", "3EZB");
			put("3EZC", "3EZB");
			put("4FY9", "4V99");
			put("5H8R", "5KCJ");
			put("4FY8", "4V99");
			put("4FY7", "4V99");
			put("4G9U", "5BY6");
			put("4FY6", "4V99");
			put("4FY5", "4V99");
			put("4FY4", "4V99");
			put("4FY3", "4V99");
			put("4FY2", "4V99");
			put("3RSA", "4RSA");
			put("4FY1", "4V99");
			put("4FXN", "2FOX");
			put("2XAI", "3ZKJ");
			put("3LFX", "4XTK");
			put("5NDL", "6IBW");
			put("3EYR", "3L6I");
			put("3EYN", "3GPC");
			put("6BJ0", "6UIQ");
			put("4MED", "6KZI");
			put("7PAP", "6PAD");
			put("2KGM", "2L4U");
			put("5AK1", "5G56");
			put("1PTP", "2PTP");
			put("1PTN", "2PTN");
			put("5AJF", "5FJE");
			put("1WB3", "4ACB");
			put("5AJE", "5FJD");
			put("1WB2", "4ACA");
			put("5H7I", "5XF8");
			put("1WB1", "4AC9");
			put("2R4A", "3HR9");
			put("116L", "216L");
			put("7CH2", "7DE5");
			put("1PTE", "3PTE");
			put("1PTC", "2PTC");
			put("3RQV", "4V4M");
			put("1PTB", "2PTB");
			put("3EXK", "3HP1");
			put("1JH2", "1P3H");
			put("2QS0", "4HHE");
			put("5NBR", "6QAI");
			put("2KFC", "2L1V");
			put("1Q4M", "2F2G");
			put("1JGF", "1LS5");
			put("3EX5", "3L0N");
			put("3F94", "3USZ");
			put("3F93", "3UT0");
			put("5H6F", "5ZK0");
			put("5H6E", "6A4Q");
			put("1PSG", "3PSG");
			put("5H6D", "6A4P");
			put("1Q4F", "1VRZ");
			put("3EX0", "3L0K");
			put("5H6C", "6A4O");
			put("5H6A", "6A4N");
			put("1CYT", "3CYT");
			put("1JFY", "1JR7");
			put("1CYP", "2CYP");
			put("5GTV", "5YKB");
			put("5TNA", "6TNA");
			put("1JFO", "1B1U");
			put("1PS4", "1Q2U");
			put("1CYH", "2CYH");
			put("2KEA", "6UZJ");
			put("1JFE", "1M1J");
			put("1PRJ", "2PRJ");
			put("3EW4", "4E25");
			put("1PRI", "2PRI");
			put("3EVL", "4E23");
			put("5TMI", "6ALT");
			put("3S0S", "4I30");
			put("1D9T", "1NE7");
			put("1CXT", "1EU1");
			put("3EVH", "4E1Y");
			put("1CXS", "1EU1");
			put("1CY3", "2CY3");
			put("3RP0", "3U87");
			put("3S0L", "3V6N");
			put("2R1O", "3C86");
			put("4G5W", "4V9B");
			put("4G5V", "4V9B");
			put("4G5U", "4V9B");
			put("1CXG", "2CXG");
			put("1JEL", "2JEL");
			put("4G5T", "4V9B");
			put("4G64", "5EYH");
			put("4G62", "5EYG");
			put("4FTP", "6LSC");
			put("1D9B", "1HN9");
			put("4G60", "5F24");
			put("4G5N", "4V9A");
			put("4G5M", "4V9A");
			put("4G5L", "4V9A");
			put("4G5K", "4V9A");
			put("4YZ8", "5UPD");
			put("2QOZ", "4V57");
			put("2KD5", "2KVK");
			put("2QOY", "4V57");
			put("2QOX", "4V56");
			put("2QOW", "4V56");
			put("1D8R", "1VJ3");
			put("2QOV", "4V56");
			put("3RNP", "4RNP");
			put("5AFT", "5ADX");
			put("2QOU", "4V56");
			put("1CX0", "4PR6");
			put("2QP1", "4V57");
			put("2QP0", "4V57");
			put("6I2L", "6R1F");
			put("2KCI", "2KJS");
			put("1PPS", "2PPS");
			put("5TKX", "5UU1");
			put("3EU6", "3F5T");
			put("5ZWW", "7DLQ");
			put("2E79", "3A2E");
			put("2DV8", "3FO7");
			put("3LB7", "3OMV");
			put("1Q1D", "1QZR");
			put("3LAR", "5AYX");
			put("2DV2", "5SX2");
			put("3ETK", "3ICJ");
			put("2DV1", "5SX1");
			put("4G4D", "4KBY");
			put("3F5I", "3AHV");
			put("4G4A", "5HLL");
			put("1JCW", "2JCW");
			put("3LAB", "3VCR");
			put("5TJV", "5U09");
			put("2QNH", "4V4J");
			put("3F55", "4IIS");
			put("4G3L", "4KC0");
			put("2DTV", "2ZG5");
			put("4YX8", "6NEN");
			put("2DTQ", "3AVE");
			put("3RLT", "4FWB");
			put("3ESG", "5V00");
			put("2KAO", "2KV1");
			put("1PNY", "4V4A");
			put("1PNX", "4V4A");
			put("1CUM", "1BKJ");
			put("2DTG", "4ZXB");
			put("1PNU", "4V49");
			put("4G2X", "5CSO");
			put("1PNS", "4V49");
			put("5TIT", "5U04");
			put("2E58", "3VYW");
			put("5TJ0", "6B5W");
			put("1VZL", "2CCN");
			put("6UHG", "6V41");
			put("2E4S", "2ZMF");
			put("6UHD", "6VO5");
			put("3RKT", "3X3H");
			put("4Z7S", "5HDB");
			put("4YW0", "5F9T");
			put("1PMZ", "2OPO");
			put("4YVL", "5J8L");
			put("4FPZ", "5ILX");
			put("2X9U", "4V5J");
			put("1D5K", "1GH8");
			put("2X9T", "4V5J");
			put("1VYY", "2CHH");
			put("2X9S", "4V5J");
			put("2X9R", "4V5J");
			put("1CTH", "2CTH");
			put("1PMQ", "4Z9L");
			put("1PN1", "1S0P");
			put("4G1S", "4P6A");
			put("1JAG", "2OCP");
			put("2E3Y", "2ZGW");
			put("3F2S", "3G15");
			put("2WXE", "2X38");
			put("2DS3", "3D3I");
			put("1CT7", "1E8R");
			put("2QKZ", "2KKD");
			put("1D55", "2D55");
			put("1CT3", "4CAA");
			put("1D4Q", "1C8P");
			put("1PLZ", "1X82");
			put("1PM8", "1RYS");
			put("2WX7", "2Y4E");
			put("2WX6", "2Y4D");
			put("1VXZ", "3J78");
			put("1VXY", "3J78");
			put("5GNQ", "7F4Y");
			put("1VY9", "4TVX");
			put("1VXX", "3J78");
			put("1VY8", "4TVX");
			put("1VXW", "3J77");
			put("2WWQ", "4V5H");
			put("1D4G", "1K0U");
			put("1VXV", "3J77");
			put("5ABI", "5GW6");
			put("5GNN", "5H0Q");
			put("1VXU", "3J77");
			put("1PM0", "1RYR");
			put("1VXT", "4LT8");
			put("1VXS", "4LT8");
			put("3RJB", "4IQI");
			put("1VY3", "4P70");
			put("2QKG", "3EB7");
			put("2WWL", "4V5H");
			put("1VY2", "4P70");
			put("1VXQ", "4LT8");
			put("1VY1", "4P70");
			put("1VXP", "4LT8");
			put("1VY0", "4P70");
			put("1VXN", "4LSK");
			put("1VXM", "4LSK");
			put("3EPQ", "3OHR");
			put("1VXL", "4LSK");
			put("1VXK", "4LSK");
			put("1VXJ", "4LNT");
			put("4FOH", "5BVZ");
			put("1VXI", "4LNT");
			put("5MZD", "6R43");
			put("5MZB", "6R3T");
			put("3F1H", "4V67");
			put("3F1G", "4V67");
			put("3F1F", "4V67");
			put("2QK6", "2VKN");
			put("3F1E", "4V67");
			put("1CRO", "5CRO");
			put("5AB1", "6YHW");
			put("4YTJ", "4ZAS");
			put("5MZ9", "6R41");
			put("1VWZ", "4W25");
			put("1VWY", "4W24");
			put("1VX9", "4LNT");
			put("1VWX", "4W20");
			put("5AAK", "5MGB");
			put("1VX8", "4LNT");
			put("1VWW", "4W1Z");
			put("5AAJ", "5OMO");
			put("1VX7", "3J79");
			put("1VWV", "3J6Y");
			put("1VX6", "3J79");
			put("1VWU", "3J6Y");
			put("1VX5", "4W23");
			put("1VX4", "4W22");
			put("1VWS", "3J6Y");
			put("3EOW", "3URO");
			put("1W8R", "2YCE");
			put("1VX3", "4W21");
			put("1VX2", "4W28");
			put("6NXH", "6O98");
			put("1VX1", "4W27");
			put("1VX0", "4W26");
			put("2DPV", "3DPV");
			put("5MYH", "6SN6");
			put("2DQ2", "2ZR3");
			put("3F0K", "3NB9");
			put("2DQ1", "2ZR2");
			put("3F0J", "3NB8");
			put("2DPO", "3ADO");
			put("3RI2", "4EJO");
			put("4FMY", "5BU5");
			put("1VVZ", "4LFZ");
			put("1VVY", "4LFZ");
			put("1VW9", "3J6X");
			put("1VVX", "4LFZ");
			put("1VW8", "3J6X");
			put("1VVW", "4LEL");
			put("1VW7", "3J6X");
			put("1VVV", "4LEL");
			put("1VW6", "4NWR");
			put("1VVU", "4LEL");
			put("1VW5", "4NWR");
			put("1VVT", "4LEL");
			put("3F09", "4JM7");
			put("1VW4", "3J6B");
			put("1VVS", "4L71");
			put("1VW3", "3J6B");
			put("1VVR", "4L71");
			put("1CQB", "1EKH");
			put("1VW2", "4O9Y");
			put("1VVQ", "4L71");
			put("1VW1", "4O9Y");
			put("1VVP", "4L71");
			put("1VW0", "4LFZ");
			put("1VVO", "4L47");
			put("6NWF", "6XL3");
			put("1VVN", "4L47");
			put("5TEP", "6X47");
			put("1VVM", "4L47");
			put("1VVL", "4L47");
			put("1VVK", "4V4N");
			put("1VVI", "3J3Y");
			put("2E14", "3W55");
			put("1VVH", "3J3Y");
			put("1VVG", "3J3Y");
			put("1CPV", "5CPV");
			put("1VVF", "3J3Y");
			put("4Z3R", "5DOX");
			put("4Z3Q", "5DOY");
			put("1VVB", "3J3Y");
			put("1VVA", "3J3Y");
			put("1CPP", "2CPP");
			put("3END", "3FWY");
			put("1CPK", "2CPK");
			put("1VUZ", "3J3Y");
			put("1VUY", "3J3Y");
			put("1VV9", "3J3Y");
			put("1VUX", "3J3Y");
			put("1VV8", "3J3Y");
			put("1VUW", "3J3Y");
			put("1VV7", "3J3Y");
			put("1VUV", "3J3Y");
			put("1VV6", "3J3Y");
			put("1VUU", "3J3Y");
			put("5MWR", "6EL2");
			put("1VV5", "3J3Y");
			put("1VUT", "3J3Q");
			put("1VV4", "3J3Y");
			put("1VUS", "3J3Q");
			put("1VV3", "3J3Y");
			put("1VUR", "3J3Q");
			put("2QHG", "3BJW");
			put("1VV2", "3J3Y");
			put("1VUQ", "3J3Q");
			put("1CPA", "3CPA");
			put("1VV1", "3J3Y");
			put("1VUP", "3J3Q");
			put("1VV0", "3J3Y");
			put("1VUO", "3J3Q");
			put("1VUN", "3J3Q");
			put("5N8K", "5NXB");
			put("1VUM", "3J3Q");
			put("1VUL", "3J3Q");
			put("1VUK", "3J3Q");
			put("2X5E", "2XU2");
			put("1VUJ", "3J3Q");
			put("1VUI", "3J3Q");
			put("1COX", "3COX");
			put("1VUH", "3J3Q");
			put("2X5B", "2XAW");
			put("3KZ2", "4IQE");
			put("1VUG", "3J3Q");
			put("2X5A", "4V5I");
			put("3KYP", "5X7V");
			put("1VUF", "3J3Q");
			put("4FLD", "4HFG");
			put("1VUE", "3J3Q");
			put("1VUD", "3J3Q");
			put("1VUC", "3J3Q");
			put("2QH8", "3LKV");
			put("4LXE", "4MAN");
			put("1VUA", "3J3Q");
			put("3EME", "3IWH");
			put("3RFO", "4IQF");
			put("1PHY", "2PHY");
			put("2X59", "2XAZ");
			put("2QGP", "4H9D");
			put("1VTZ", "4V4M");
			put("2X54", "4V5I");
			put("1VU9", "3J3Q");
			put("1VU8", "3J3Q");
			put("1VU7", "3J3Q");
			put("5N7S", "6GHU");
			put("1VU6", "3J3Q");
			put("5N7R", "6H7X");
			put("1VU5", "3J3Q");
			put("1VU4", "3J3Q");
			put("1VU3", "4V98");
			put("1VU2", "4V98");
			put("1VU1", "4V86");
			put("3EM5", "4HPG");
			put("4FKN", "4HOM");
			put("1VU0", "4V86");
			put("4LWR", "5Y9A");
			put("6UBG", "7LUL");
			put("2JYX", "2K1G");
			put("2X4C", "2Y5E");
			put("2X4B", "2Y4S");
			put("4FKF", "4HOL");
			put("3L9O", "4QU4");
			put("5ZOM", "5ZUH");
			put("1CO3", "1EZ0");
			put("3KXJ", "3PVG");
			put("5GJ8", "5Z8T");
			put("1VSZ", "4CWU");
			put("1VSY", "4V7O");
			put("2X3S", "2XAY");
			put("5GJ2", "5Y8B");
			put("21GS", "3CSJ");
			put("1VSX", "4V7G");
			put("2QFM", "3C6K");
			put("2WRR", "4V5G");
			put("5GJ1", "5Y8A");
			put("1VSW", "4V7G");
			put("2WRQ", "4V5G");
			put("5GJ0", "5Y89");
			put("2WRP", "2OZ9");
			put("2WRO", "4V5G");
			put("2WRN", "4V5G");
			put("7BYZ", "7CH2");
			put("1CND", "2CND");
			put("1VT4", "4V4L");
			put("2JYC", "2LGR");
			put("1PGM", "3PGM");
			put("2WRL", "4V5F");
			put("1VT2", "4V80");
			put("2WRK", "4V5F");
			put("1PGK", "3PGK");
			put("1VSP", "4V4J");
			put("2WRJ", "4V5F");
			put("1VT0", "4V4K");
			put("2WRI", "4V5F");
			put("2X3I", "2XAX");
			put("3L99", "4JAG");
			put("3L98", "4JAF");
			put("1PGH", "2PGH");
			put("3L97", "4JAE");
			put("3L96", "4JAD");
			put("3KWU", "6NYT");
			put("3KWT", "6NYC");
			put("1PGD", "2PGD");
			put("1CN9", "1T0K");
			put("1CN8", "1T0K");
			put("3L90", "3MI3");
			put("4YP4", "6H5F");
			put("3KWN", "5QC4");
			put("1VSA", "4V4I");
			put("3KWH", "3OC0");
			put("3RDL", "3TWY");
			put("2X37", "2XH5");
			put("5N5X", "5O7X");
			put("1VS9", "1VSA");
			put("2X33", "2XAV");
			put("1VS8", "4V4H");
			put("4M6Z", "4XVX");
			put("1VS7", "4V4H");
			put("1VS6", "4V4H");
			put("1VS5", "4V4H");
			put("1IYA", "1J3G");
			put("3L7S", "3T0C");
			put("2JWX", "2MF9");
			put("3L83", "3M3P");
			put("2QDZ", "4QKY");
			put("3L80", "4L9A");
			put("4YNS", "5HUQ");
			put("3RCQ", "5APA");
			put("3RD1", "4Y0V");
			put("2X1Y", "2XAP");
			put("1CLN", "3CLN");
			put("7C9D", "7FFP");
			put("2JWJ", "2JZF");
			put("2JWI", "2RNK");
			put("1PEP", "4PEP");
			put("1PF0", "1VP6");
			put("4LU8", "4NYP");
			put("4LU7", "4NYO");
			put("3RCA", "3SR6");
			put("1PEL", "2PEL");
			put("4LU2", "5KX4");
			put("3L6S", "4DYO");
			put("1PEC", "2PEC");
			put("2DJO", "2ECB");
			put("1J8X", "1NKH");
			put("1J8W", "1NF5");
			put("3RBP", "4HHC");
			put("2JVP", "2K5S");
			put("2X0Z", "2XAK");
			put("3RBO", "4JJZ");
			put("1J94", "1NHE");
			put("1J92", "1NQI");
			put("1PDS", "1TI7");
			put("3KUB", "3LLG");
			put("3KUA", "4ELZ");
			put("2X0M", "2X2G");
			put("3L5Y", "4PS4");
			put("3L69", "4DBK");
			put("3KU8", "4ELY");
			put("1VPJ", "2ISB");
			put("1CK9", "1CN7");
			put("3L5Q", "4V7O");
			put("1CK8", "1T0K");
			put("1VPG", "2PWN");
			put("1CK5", "1T0K");
			put("3EHD", "7M5H");
			put("1CJO", "2CJO");
			put("1IW5", "1V9J");
			put("1PCY", "1PLC");
			put("1CJN", "2CJN");
			put("3L5G", "3QME");
			put("6B8I", "6E49");
			put("1VOZ", "4V4G");
			put("2QC0", "3EQX");
			put("1VOY", "4V4G");
			put("1VOX", "4V4G");
			put("5MQU", "5OSN");
			put("1VOW", "4V4G");
			put("1VOV", "4V4G");
			put("2QBK", "4V55");
			put("4FG5", "5IKX");
			put("1VOU", "4V4G");
			put("2QBJ", "4V55");
			put("2QBI", "4V55");
			put("1VOS", "4V4G");
			put("2QBH", "4V55");
			put("1VOR", "4V4G");
			put("2QBG", "4V54");
			put("1VOQ", "4V4G");
			put("2QBF", "4V54");
			put("2QBE", "4V54");
			put("6NPG", "6OGN");
			put("1VP0", "4V4G");
			put("2QBD", "4V54");
			put("2QBC", "4V53");
			put("2QBB", "4V53");
			put("2QBA", "4V53");
			put("3EGP", "3IRC");
			put("3L55", "3VDH");
			put("1PCD", "2PCD");
			put("3L53", "3V77");
			put("3L52", "3V75");
			put("2K64", "2M23");
			put("2QB9", "4V53");
			put("2K63", "2M24");
			put("4M3A", "5AWM");
			put("2QAO", "4V52");
			put("2QAN", "4V52");
			put("1J6N", "1O58");
			put("2QAM", "4V52");
			put("2QAL", "4V52");
			put("2WN1", "2WWN");
			put("1PC1", "1Q4V");
			put("2WN0", "2WWO");
			put("2QAH", "4D8L");
			put("3EG7", "5CNP");
			put("4FF0", "4IX2");
			put("4LQN", "6I36");
			put("3KS1", "3MWO");
			put("2DH0", "2ZOZ");
			put("1CI2", "2CI2");
			put("1J5V", "2afb");
			put("6HBO", "6QXZ");
			put("1J5R", "1O2D");
			put("1PAW", "2PAW");
			put("1J5Q", "5TIQ");
			put("3L3E", "3PD7");
			put("1PB6", "3LOC");
			put("1PAP", "8PAP");
			put("4FDS", "4MZ6");
			put("4FDR", "4MZ5");
			put("3EEW", "3VDR");
			put("4LQ7", "4LYU");
			put("1CHB", "2CHB");
			put("2DFZ", "2ZYK");
			put("4LPR", "1GBL");
			put("2DFW", "3IF5");
			put("3L2T", "3OYA");
			put("3EEN", "3R97");
			put("3L2S", "3OY9");
			put("2DFR", "4DFR");
			put("1PAB", "2PAB");
			put("2DFQ", "2DN2");
			put("1CH6", "1HWX");
			put("1J4Z", "4V43");
			put("2DFO", "2DN1");
			put("4YIM", "6FPN");
			put("2JRN", "2K3A");
			put("3L2G", "4V7N");
			put("3L2F", "4V7N");
			put("2WL2", "2Y3P");
			put("4FD1", "5FD1");
			put("3EDS", "3SMD");
			put("1ISD", "2ISD");
			put("7BRR", "7D1M");
			put("1VLF", "4V4E");
			put("2DF1", "2EF1");
			put("1VLE", "4V4D");
			put("1VLD", "4V4C");
			put("1J3V", "2CVZ");
			put("3KPI", "3T2Z");
			put("1CFO", "1NEW");
			put("3KPG", "3T31");
			put("6TXY", "5QUB");
			put("1VKV", "1ZWJ");
			put("1VKS", "1LNP");
			put("3ED6", "4MPB");
			put("3XIA", "1XYA");
			put("6NLD", "6OEB");
			put("6NLC", "6OEA");
			put("4RZN", "5GUN");
			put("1CF6", "1HUO");
			put("7V7Y", "7XAS");
			put("4LNE", "5K0S");
			put("1J2S", "1J3G");
			put("2WIR", "3ZWQ");
			put("1J2I", "1O3Y");
			put("2K1C", "2L6E");
			put("1J2H", "1O3X");
			put("1VJP", "3CIN");
			put("1CDV", "2CDV");
			put("3KNO", "4V7M");
			put("2DD0", "2DNS");
			put("3KNN", "4V7M");
			put("4FAC", "5E5H");
			put("3KNM", "4V7M");
			put("3KNL", "4V7M");
			put("3KNK", "4V7L");
			put("3KNJ", "4V7L");
			put("3KNI", "4V7L");
			put("4RYH", "5DEL");
			put("3KNH", "4V7L");
			put("5A41", "5FXB");
			put("3QZK", "4DQ4");
			put("1IQ2", "1L3X");
			put("3QZJ", "4DQ3");
			put("4LLZ", "4OTY");
			put("5T8X", "5WHX");
			put("4YFC", "5Y32");
			put("1VIR", "2VIR");
			put("1VIL", "2VIL");
			put("7BOQ", "7CRD");
			put("1CCY", "2CCY");
			put("2WHA", "2X1N");
			put("2DBP", "3A3U");
			put("1CD4", "1CDH");
			put("4RXK", "4ZWV");
			put("7C0H", "7VJM");
			put("1CCO", "2CCO");
			put("6API", "8API");
			put("7C0B", "7FA3");
			put("2PZO", "3E2U");
			put("7C0A", "7FA2");
			put("2WH4", "4V5E");
			put("2WH3", "4V5E");
			put("2WH2", "4V5E");
			put("1J0L", "1WOT");
			put("2WH1", "4V5E");
			put("6B1A", "6CHH");
			put("2WGK", "5AEC");
			put("5ZD7", "6IQY");
			put("5ZD6", "6IQZ");
			put("5ZD5", "5ZYA");
			put("1CBZ", "1EYY");
			put("5MJF", "6EK5");
			put("6TUE", "7A62");
			put("2WGA", "3WGA");
			put("2JMQ", "2K1J");
			put("1CBP", "2CBP");
			put("2PZ4", "3PHS");
			put("1INS", "4INS");
			put("2PZ2", "3UMS");
			put("1INM", "1e1b");
			put("1VGS", "2CV4");
			put("5ZBV", "6A4J");
			put("6U5I", "7N09");
			put("2JM7", "2JV4");
			put("4LJJ", "7WCS");
			put("3R8T", "4V9D");
			put("1IMZ", "1GXG");
			put("3R8S", "4V9D");
			put("3QWS", "6ON0");
			put("1CAT", "3CAT");
			put("1IMY", "1GXH");
			put("6B01", "6BST");
			put("3R8O", "4GD2");
			put("2JLO", "4D1B");
			put("3R8N", "4GD1");
			put("3KKH", "2X6L");
			put("2JLK", "2XYC");
			put("1OZ4", "3CF1");
			put("5A0K", "6OH7");
			put("5A0J", "6OH6");
			put("5A0I", "6OH8");
			put("1CAF", "2CAH");
			put("1CAE", "2CAE");
			put("2WEM", "2WUL");
			put("1CAC", "1CA2");
			put("1OYM", "1Q9M");
			put("1CAB", "2CAB");
			put("3R7Z", "4DBX");
			put("1VFK", "3A6O");
			put("6AN7", "6OIH");
			put("2JL8", "2WH4");
			put("2JL7", "2WH3");
			put("2JL6", "2WH2");
			put("2JL5", "2WH1");
			put("2JL3", "2XXF");
			put("3R82", "3VCQ");
			put("3R81", "4DFB");
			put("3R80", "4DE4");
			put("2JL0", "2XXG");
			put("2WDX", "4K3T");
			put("1P9V", "1SDJ");
			put("2WDN", "4V5D");
			put("2WDM", "4V5D");
			put("2WDL", "4V5D");
			put("2WDK", "4V5D");
			put("2WDJ", "4V5C");
			put("3KIY", "4V7K");
			put("2WDI", "4V5C");
			put("3KIX", "4V7K");
			put("4RU8", "5K5Z");
			put("2WDH", "4V5C");
			put("3KIW", "4V7K");
			put("4RU7", "5K1Y");
			put("2WDG", "4V5C");
			put("3KIU", "4V7K");
			put("3R6Z", "3UZR");
			put("3KIT", "4V7J");
			put("3KIS", "4V7J");
			put("3KIR", "4V7J");
			put("3R78", "4EJ7");
			put("3KIQ", "4V7J");
			put("1IKZ", "1M3G");
			put("4LHG", "5DBH");
			put("3R70", "4DCA");
			put("2PW4", "3H0N");
			put("5FZ2", "6Z9M");
			put("1VDU", "1WY6");
			put("1IKH", "1KOI");
			put("1IKB", "1AI2");
			put("6GWW", "6Q5V");
			put("2PV8", "3MH1");
			put("3R5Q", "7VE3");
			put("2PV5", "3MGY");
			put("4RSF", "5K5A");
			put("1P87", "4V48");
			put("3QTJ", "3UQH");
			put("1P86", "4V48");
			put("1P85", "4V47");
			put("4RSB", "5K5Q");
			put("4RSA", "5RSA");
			put("3KHA", "4FXI");
			put("3DZX", "6D0S");
			put("3KH6", "3V2B");
			put("5FXB", "5NKQ");
			put("3KGO", "3SH8");
			put("8PAP", "9PAP");
			put("3KGN", "3SH9");
			put("3KGM", "3SH7");
			put("1IIX", "1T89");
			put("2PU6", "2RI6");
			put("1IJ7", "1M9U");
			put("1P6Z", "3SSW");
			put("1IIS", "1T83");
			put("2WAV", "2WXC");
			put("4RRE", "5DWS");
			put("2PTP", "3PTP");
			put("2PTO", "3MH2");
			put("6H7K", "6IBL");
			put("4EXU", "4YNO");
			put("1VBV", "5YCQ");
			put("1VC7", "4PRF");
			put("3R4E", "4K1W");
			put("2PTJ", "3MH3");
			put("2PTI", "3PTI");
			put("5ME2", "5NI1");
			put("2WAK", "2X29");
			put("2CZZ", "2E6U");
			put("2PTB", "3PTB");
			put("1P6G", "4V47");
			put("3QRZ", "4JDL");
			put("4F9I", "4NMB");
			put("4LEG", "5J94");
			put("1OTZ", "4V46");
			put("1P67", "1RGJ");
			put("2JGH", "2Y2U");
			put("2PSL", "2RH7");
			put("2JGG", "2Y2V");
			put("2PSI", "1QLP");
			put("5T0T", "5UFP");
			put("4RPW", "6BAF");
			put("2PSC", "3BCN");
			put("2CYT", "4CYT");
			put("3R39", "4F3S");
			put("2CYS", "3CYS");
			put("2CYR", "3CYR");
			put("3KF1", "4E43");
			put("4LDH", "6LDH");
			put("1VAB", "2VAB");
			put("1VAA", "2VAA");
			put("2PRP", "1B10");
			put("4F7Y", "4MLL");
			put("4EW8", "4Q20");
			put("4S0Y", "5GLS");
			put("4S19", "5WSJ");
			put("3KDV", "4EXW");
			put("4ROR", "5ULV");
			put("4ROQ", "5UGR");
			put("1BYT", "1NO3");
			put("4ROI", "5DZD");
			put("4ROH", "5CQ2");
			put("6GT1", "6S73");
			put("3E8A", "3MAA");
			put("2PQP", "3C10");
			put("1IG2", "2IG2");
			put("2PQO", "3C0Z");
			put("1BYK", "4XXH");
			put("5MB8", "5NMS");
			put("4F77", "4V98");
			put("1P41", "1O57");
			put("4EV3", "4GP5");
			put("2PQH", "3THK");
			put("6TLN", "7TLN");
			put("1IFE", "2IFE");
			put("4RO6", "5D7Z");
			put("4S06", "5WSI");
			put("3QOZ", "4LL3");
			put("3KCR", "4V7I");
			put("3QOV", "4R1X");
			put("1ORA", "2ORA");
			put("3QOT", "5I18");
			put("3QOS", "5I1D");
			put("3R14", "4IPT");
			put("1C9R", "1J5O");
			put("3E7E", "4R8Q");
			put("2Q26", "4FSJ");
			put("2Q25", "4FTE");
			put("1OQZ", "3S8R");
			put("3QON", "6AMF");
			put("2Q23", "4FTS");
			put("1P38", "5UOJ");
			put("2PQ1", "3I7V");
			put("1BXJ", "2V1V");
			put("4RNB", "4S0V");
			put("2JDE", "2VNT");
			put("2Q1I", "3D6V");
			put("3R0B", "4IZJ");
			put("2Q1G", "3D6U");
			put("4RMX", "6E0V");
			put("2JCZ", "4AIC");
			put("3KC4", "4V7I");
			put("2D83", "2DRW");
			put("3KBI", "4EOH");
			put("2JCI", "2XD5");
			put("3QNH", "4FSD");
			put("2JCF", "2VFZ");
			put("3KAX", "3T32");
			put("4ESL", "4GP8");
			put("5YX5", "6A4V");
			put("2CUV", "2V1S");
			put("3KAU", "1KRA");
			put("3KB4", "6E12");
			put("4F4G", "4MK4");
			put("3QMU", "6DHQ");
			put("3DTH", "3JZ6");
			put("4Y9Q", "5MF5");
			put("1BVS", "7OA5");
			put("2PNP", "1ULA");
			put("1OP5", "6N2X");
			put("1P0T", "4V46");
			put("1OP3", "6N35");
			put("3E4X", "3GOR");
			put("2VZJ", "4LTN");
			put("2VZH", "4LTM");
			put("2VZF", "4LTD");
			put("1ICB", "2ICB");
			put("3KA1", "3Q20");
			put("1BV6", "1DRM");
			put("2PMX", "3GFT");
			put("1BV5", "1DRQ");
			put("3WXW", "6KS1");
			put("3WXV", "6KS0");
			put("1BUR", "1IR1");
			put("1BV0", "1B27");
			put("6H0O", "6SK9");
			put("3DSA", "3E7N");
			put("1P08", "1GBM");
			put("1P07", "2P07");
			put("1BUK", "2DIK");
			put("2PMM", "2PUQ");
			put("1UZT", "1WCO");
			put("3E3W", "3FKT");
			put("2PMG", "3PMG");
			put("5YV4", "6IG1");
			put("4RK3", "6CHK");
			put("3QL6", "6LAQ");
			put("3WWZ", "6ABJ");
			put("3WWY", "6ABI");
			put("2JA0", "2UV2");
			put("1IB3", "1M8Z");
			put("4Y7H", "5JOD");
			put("6ABG", "6KUU");
			put("6ABF", "6KUR");
			put("6ABE", "6KUT");
			put("3QKF", "4K2S");
			put("6ABD", "6KUP");
			put("6ABB", "6KUK");
			put("2W9K", "2YK3");
			put("4EPO", "4ORH");
			put("2W9I", "5E4T");
			put("1OMF", "2OMF");
			put("6AB7", "6KV5");
			put("1C59", "1DV4");
			put("6ZSF", "7OG4");
			put("3E2G", "3EVX");
			put("1C4J", "1EXY");
			put("1OM3", "6N32");
			put("2PKJ", "3MH0");
			put("3DPV", "4DPV");
			put("6MYH", "7RJ0");
			put("2D2Y", "2JEK");
			put("2W8E", "2Y7Y");
			put("2W8A", "2WIT");
			put("1OLB", "2OLB");
			put("2PJX", "3PUK");
			put("4EOA", "4NP5");
			put("7O94", "7P1L");
			put("1V9B", "4LU8");
			put("5Z4I", "6KCZ");
			put("3E1D", "4V66");
			put("3E1C", "4V66");
			put("3E1B", "4V65");
			put("3E1A", "4V65");
			put("2PK1", "3EKA");
			put("1OKU", "2C5V");
			put("2PJN", "2R2X");
			put("2PJM", "3IXQ");
			put("1V99", "4LU7");
			put("2PJK", "3IWT");
			put("4F05", "4GP4");
			put("7O8E", "7P7D");
			put("3E0T", "3HSU");
			put("2CPV", "5CPV");
			put("4ENI", "4H1P");
			put("6ZQL", "7ARQ");
			put("3QHU", "4FR0");
			put("1BR7", "2E2C");
			put("1BQV", "2JV3");
			put("1V8A", "3HPD");
			put("6GKP", "6Q8E");
			put("1OJU", "2X0J");
			put("2W74", "4BE7");
			put("1OJS", "2X0I");
			put("1UVW", "3ZDW");
			put("1UVV", "2BTY");
			put("5LY4", "5MW1");
			put("4Y4C", "5J25");
			put("3DNW", "3HZI");
			put("1C2C", "2C2C");
			put("4KYU", "5X4B");
			put("1UW2", "2VRD");
			put("2PIB", "3KBB");
			put("3WTA", "5AX1");
			put("4XS8", "5UKA");
			put("4XRV", "5W57");
			put("2D12", "2ZQN");
			put("1OJB", "2XFU");
			put("6GJX", "6YBM");
			put("3WT9", "5AX0");
			put("4EMC", "5KTB");
			put("1UVD", "2BUF");
			put("3WT8", "5AWZ");
			put("1V7B", "2ZOY");
			put("3WSS", "3X0D");
			put("2COH", "2ZCW");
			put("2PI1", "3KB6");
			put("5Z2A", "7CMQ");
			put("1V78", "2E1W");
			put("4EM5", "6D24");
			put("2PHJ", "2WQK");
			put("5Z29", "7CMN");
			put("4ELN", "6KLY");
			put("3JYX", "4V7H");
			put("3JYW", "4V7H");
			put("3DMQ", "6BOG");
			put("3JYV", "4V7H");
			put("4Y2Z", "5B6J");
			put("1UUL", "4LLR");
			put("4XQY", "5IHD");
			put("1BOV", "2xsc");
			put("1C13", "1IAV");
			put("3DMA", "4PY9");
			put("4XQI", "6GVR");
			put("4XQH", "6GVP");
			put("1UTW", "1UYP");
			put("1C0H", "1C40");
			put("4KWZ", "1VVJ");
			put("1UTV", "4V4F");
			put("2W4N", "2YEP");
			put("1C0D", "1VRX");
			put("3X3E", "5XOY");
			put("2VSJ", "2VZ7");
			put("2CMZ", "5I2M");
			put("3DM4", "3KLW");
			put("2CMX", "2VQC");
			put("4KWQ", "5SYJ");
			put("4KX2", "1VVJ");
			put("4KX1", "1VVJ");
			put("2CMV", "5YZI");
			put("4KX0", "1VVJ");
			put("3DLO", "6HCD");
			put("2CMQ", "2J9T");
			put("1UTF", "4V4F");
			put("2PG9", "3GON");
			put("2IZ2", "2XHS");
			put("3DLD", "5E5D");
			put("2CMJ", "5YZH");
			put("1C00", "1C7I");
			put("2VRV", "2XFH");
			put("1HZR", "1J5M");
			put("5LUY", "6EO7");
			put("1HZQ", "1J5L");
			put("6N5R", "6WTR");
			put("5LUW", "6EO6");
			put("5M6W", "6RUR");
			put("2IYH", "2UW2");
			put("1BNH", "2BNH");
			put("5M74", "5MJV");
			put("5FIM", "7PVC");
			put("6N5L", "6WTL");
			put("5M6P", "6TA8");
			put("1V52", "2DJK");
			put("3X2D", "5UQY");
			put("3K95", "3M49");
			put("1USJ", "2BI7");
			put("2IXX", "2XE1");
			put("1BMY", "1IG6");
			put("2IXW", "2XE2");
			put("4EJC", "4V97");
			put("1HYY", "2DQU");
			put("4EJB", "4V97");
			put("6MSZ", "6O1T");
			put("1HYX", "2DQT");
			put("4EJA", "4V97");
			put("1BN2", "2BN2");
			put("1OG9", "2BUI");
			put("1OG8", "2BUH");
			put("2PEP", "5PEP");
			put("4EJ9", "4V97");
			put("1BMI", "2BMI");
			put("1BME", "1BVT");
			put("7AY4", "7OJ4");
			put("4L6M", "4V9Q");
			put("4L6L", "4V9Q");
			put("7NQY", "7OOG");
			put("4L6K", "4V9Q");
			put("7NQX", "7OOH");
			put("1BLW", "1CN4");
			put("3JVP", "3QDK");
			put("4L6J", "4V9Q");
			put("2CL1", "2JAV");
			put("7NQV", "7OOE");
			put("273D", "309D");
			put("7NQT", "6S02");
			put("4RBK", "4W2I");
			put("4XNP", "5JW8");
			put("3QD1", "4I8E");
			put("4RBJ", "4W2I");
			put("4RBI", "4W2I");
			put("2PDT", "6CNY");
			put("4RBH", "4W2I");
			put("4RBG", "4W2H");
			put("7NR0", "6S02");
			put("4RBF", "4W2H");
			put("6N3T", "6OR2");
			put("1BLM", "3BLM");
			put("4RBE", "4W2H");
			put("2VPU", "2WZN");
			put("1OF7", "2VLE");
			put("4RBD", "4W2H");
			put("4RBC", "4W2G");
			put("4RBB", "4W2G");
			put("4RBA", "4W2G");
			put("1OER", "2BYX");
			put("1OEQ", "2BYW");
			put("2J8E", "3FGZ");
			put("4KU9", "4RSR");
			put("3X0H", "5B5S");
			put("4EI3", "4OLB");
			put("4EI1", "4OLA");
			put("4EHO", "4GW9");
			put("4RB9", "4W2G");
			put("4RB8", "4W2F");
			put("3JV8", "4V7G");
			put("4RB7", "4W2F");
			put("4L5P", "4NG3");
			put("4RB6", "4W2F");
			put("2CJV", "2UUD");
			put("4RB5", "4W2F");
			put("7AWO", "7PN0");
			put("3DIJ", "3GEG");
			put("3DII", "3GED");
			put("1HWZ", "6DHD");
			put("2J84", "2J91");
			put("1I99", "1K9O");
			put("1HWX", "3MW9");
			put("1BL2", "1QRK");
			put("1BKQ", "1F3C");
			put("2W0Y", "2X98");
			put("3K6E", "7LZG");
			put("5M3R", "5O41");
			put("2VOL", "3ZO0");
			put("1UPO", "1VYN");
			put("1HWA", "1E8L");
			put("5M3G", "7BBA");
			put("1BJY", "4V2G");
			put("3QB6", "4JJK");
			put("4EGB", "6BI4");
			put("2PBT", "3I7U");
			put("1HVT", "2HVT");
			put("6MPU", "6NE0");
			put("1BJL", "3BJL");
			put("3WMO", "4V8K");
			put("2J6J", "5I2S");
			put("3WMN", "4V8K");
			put("1HW0", "1IH5");
			put("1UOX", "1R51");
			put("1HVM", "2HVM");
			put("3K5A", "3MAQ");
			put("3QAD", "3RZF");
			put("4L3W", "6A0W");
			put("5LQL", "6FDG");
			put("4L3M", "4QP0");
			put("5LQE", "5NEV");
			put("2PAZ", "8PAZ");
			put("3WLS", "6MI1");
			put("1BJ0", "4V2F");
			put("2PAP", "1PAD");
			put("2ITI", "2Q5A");
			put("5M1V", "5MK5");
			put("4EF7", "4GLL");
			put("7ATT", "7ZYU");
			put("3JS7", "4JRW");
			put("5FDE", "5I13");
			put("6MOC", "6MUK");
			put("2PA9", "3FHB");
			put("3JRL", "3O5X");
			put("3DFD", "3KLU");
			put("3DFB", "3U8V");
			put("1HU4", "1N5D");
			put("2CGG", "2VJ1");
			put("7ATC", "5AT1");
			put("3WKO", "4ZK8");
			put("2VLS", "2VWC");
			put("1UN7", "2VHL");
			put("1OAM", "1OKE");
			put("3DF4", "4V64");
			put("3DF3", "4V64");
			put("3DF2", "4V64");
			put("1UMM", "2C8S");
			put("3DF1", "4V64");
			put("3JQU", "4TN9");
			put("6A5V", "6INQ");
			put("3JR0", "3RWN");
			put("5YHK", "6J92");
			put("1BGR", "1B1X");
			put("1BGM", "4V40");
			put("1BGL", "4V40");
			put("1HSP", "2HSP");
			put("1BGH", "1VQB");
			put("5FC0", "5K1Y");
			put("4KP9", "6H8T");
			put("1UM3", "1VF5");
			put("1HSD", "2HSD");
			put("1HSC", "2HSC");
			put("2J2T", "2JER");
			put("4L0H", "4TXT");
			put("1HRX", "1LE1");
			put("1HRW", "1LE0");
			put("3WJ6", "5B7V");
			put("1BFL", "1CNQ");
			put("1HS0", "1LE3");
			put("4KNZ", "6NNR");
			put("4EBT", "5E0A");
			put("2VJG", "2WQL");
			put("2IPY", "3SNP");
			put("4EBH", "4ISX");
			put("2IQ8", "2QMW");
			put("4QZP", "5SYX");
			put("4QZO", "5SYY");
			put("4QZN", "5SYW");
			put("1UKD", "1UKE");
			put("4QZL", "5SYV");
			put("4QZK", "5SYU");
			put("1BER", "1J59");
			put("4QZJ", "5SYL");
			put("1HR5", "1JM0");
			put("4EAZ", "5Z2Q");
			put("2J1F", "2J7M");
			put("3DBW", "3FMS");
			put("3PZX", "4JIM");
			put("2CD4", "3CD4");
			put("4XG5", "5GHV");
			put("1UJA", "1VCJ");
			put("1BDP", "1XWL");
			put("3WH4", "5Y98");
			put("3DBB", "3F70");
			put("4QYC", "5DZL");
			put("2VHP", "4V5B");
			put("2VHO", "4V5B");
			put("2VHN", "4V5B");
			put("2VHM", "4V5B");
			put("2J0C", "2V0M");
			put("1UIQ", "1V7C");
			put("5LKL", "5MZ4");
			put("3WGA", "9WGA");
			put("4KLP", "4LVS");
			put("3PYV", "4V84");
			put("3PYU", "4V84");
			put("3PYT", "4V84");
			put("3PYS", "4V84");
			put("1I0Y", "1J5D");
			put("3PYR", "4V83");
			put("2J03", "4V51");
			put("3PYQ", "4V83");
			put("2J02", "4V51");
			put("1I0W", "1J5C");
			put("2J01", "4V51");
			put("3PYO", "4V83");
			put("2J00", "4V51");
			put("1HP6", "1M5K");
			put("3PYN", "4V83");
			put("2VH8", "2WQZ");
			put("1BCL", "2BCL");
			put("6MIR", "6OKJ");
			put("4KKZ", "6HWR");
			put("2OZI", "3LAG");
			put("5YD9", "6A4Y");
			put("5YD1", "6JPF");
			put("4KKK", "4XWN");
			put("2CAP", "2V3Q");
			put("4KKF", "4XWM");
			put("2IN1", "3EVX");
			put("3Q9M", "5UCQ");
			put("1I03", "1JGM");
			put("3Q9K", "6L9E");
			put("2CAF", "1MQF");
			put("2CAE", "1M85");
			put("1NZT", "1XXE");
			put("2CAA", "3CAA");
			put("4XDB", "5NA1");
			put("1NZH", "1Y21");
			put("3Q97", "5T1Z");
			put("3PX5", "3R0K");
			put("1HMZ", "2HMZ");
			put("4KJH", "4ZT8");
			put("1HMX", "2HMX");
			put("4KJF", "4MZC");
			put("4KJE", "4MZB");
			put("2OY6", "3I5F");
			put("1HN7", "1JBD");
			put("1NYZ", "1SY4");
			put("4KJC", "4V9P");
			put("4KJB", "4V9P");
			put("4KJA", "4V9P");
			put("1HMQ", "2HMQ");
			put("2P9O", "4OOQ");
			put("1HMN", "1HMM");
			put("1HMM", "1HMQ");
			put("4KIZ", "4V9O");
			put("5LI5", "5LX2");
			put("4KIY", "4V9O");
			put("4KIX", "4V9O");
			put("4KJ9", "4V9P");
			put("4KJ8", "4V9P");
			put("1HMI", "2HMI");
			put("4KJ7", "4V9P");
			put("2ILB", "3H50");
			put("4KJ6", "4V9P");
			put("1HMG", "2HMG");
			put("4KJ5", "4V9P");
			put("2VEJ", "2VNQ");
			put("1BAA", "2BAA");
			put("4KJ4", "4V9O");
			put("4KJ3", "4V9O");
			put("2VEH", "2VNP");
			put("4KJ2", "4V9O");
			put("4KJ1", "4V9O");
			put("2OXA", "3HJR");
			put("4KJ0", "4V9O");
			put("5YAR", "5Z3S");
			put("3WCX", "7CNE");
			put("4KIH", "4XWL");
			put("2VDZ", "4A2E");
			put("2OWR", "5JX3");
			put("2P92", "4PEO");
			put("1HLR", "1VLB");
			put("2VE5", "2WME");
			put("2VDS", "4A2H");
			put("2VE2", "2WBA");
			put("2P8K", "3H4V");
			put("2VE0", "4A2D");
			put("1UF6", "1WUB");
			put("6SRM", "7KY1");
			put("3PV9", "4WJ0");
			put("3Q6X", "5ZGE");
			put("2OW8", "4V4I");
			put("2IJP", "3EFZ");
			put("4XAN", "5HMJ");
			put("6MF7", "6NN3");
			put("2VCU", "2VG8");
			put("2VCR", "2WJU");
			put("2OVK", "3I5G");
			put("1UDP", "2UDP");
			put("1NWJ", "1Q53");
			put("1HKE", "1UUZ");
			put("3WBC", "4WBC");
			put("3PTP", "4PTP");
			put("1UCZ", "2UCZ");
			put("3PTI", "4PTI");
			put("1O7R", "1VZT");
			put("2OUF", "4U12");
			put("4R3Y", "5AYZ");
			put("5LEN", "6F5E");
			put("4R3X", "5AYY");
			put("1NVH", "1QXF");
			put("4KG1", "5H5O");
			put("4QS3", "4W29");
			put("4KFL", "4V9N");
			put("4QS2", "4W29");
			put("4KFK", "4V9N");
			put("4QS1", "4W29");
			put("4QS0", "4W29");
			put("4KFI", "4V9N");
			put("4KFH", "4V9N");
			put("1HIU", "2HIU");
			put("2VB4", "2VQ6");
			put("2IHI", "3MAV");
			put("1O6N", "2BP4");
			put("1NUM", "1AUD");
			put("4E9P", "4NER");
			put("1HID", "2HID");
			put("4R2V", "5HBG");
			put("2P5A", "3HVC");
			put("4KEO", "4LRH");
			put("4E9I", "6D23");
			put("7TNA", "8TNA");
			put("3CYK", "3DGD");
			put("2BZP", "2CKG");
			put("2BZO", "2CKH");
			put("4QQM", "6MEW");
			put("3PRR", "4V82");
			put("3PRQ", "4V82");
			put("2OT6", "2R33");
			put("1UBA", "1DV0");
			put("1O5Y", "1VJL");
			put("6MC5", "6N4G");
			put("2OSP", "1FJ1");
			put("2VA4", "2XY1");
			put("5F80", "5HQD");
			put("4DX4", "4E8S");
			put("1O5N", "1VJ2");
			put("5F7I", "5B7O");
			put("3CXT", "3O03");
			put("4R1X", "4RVN");
			put("4R1W", "4RVO");
			put("4E8L", "4MYO");
			put("1HHB", "2HHB");
			put("4E8J", "4WH5");
			put("4E8I", "4FO1");
			put("6SNG", "7Q1G");
			put("1UAH", "1UG6");
			put("4KDK", "4V9M");
			put("6YZI", "7P9C");
			put("4KDJ", "4V9M");
			put("4KDH", "4V9M");
			put("4KDG", "4V9M");
			put("3CXE", "4NKQ");
			put("4KDB", "4V9L");
			put("2P3R", "3EZW");
			put("4KDA", "4V9L");
			put("1NT7", "1O23");
			put("4E87", "4JD8");
			put("4KCZ", "4V9K");
			put("4KCY", "4V9K");
			put("4KD9", "4V9L");
			put("4KD8", "4V9L");
			put("6YZ6", "7NEV");
			put("1AZA", "2AZA");
			put("4KD2", "4V9K");
			put("4KD0", "4V9K");
			put("5XZS", "6K4N");
			put("1AZ9", "1WL9");
			put("1HFV", "2J5X");
			put("1AYQ", "2AYQ");
			put("1HFT", "2HFT");
			put("1AYH", "2AYH");
			put("4KBW", "4V9J");
			put("4KBV", "4V9J");
			put("4KBU", "4V9J");
			put("4KBT", "4V9J");
			put("1NRH", "1U8X");
			put("1NRD", "2NRD");
			put("1NRC", "1OIA");
			put("4QNO", "5ZT2");
			put("2OQ8", "3II2");
			put("1HF7", "1H2J");
			put("1HF5", "1H11");
			put("2P21", "1Q21");
			put("2P1K", "3E2B");
			put("1AXF", "1Y85");
			put("3D6S", "5VPK");
			put("2BW6", "2C3S");
			put("3CUM", "3OBB");
			put("1O2C", "1O51");
			put("4WZ1", "4XI1");
			put("5ERZ", "5XEW");
			put("1HDW", "2C4Z");
			put("1HE6", "2C50");
			put("1B91", "1N72");
			put("2ICN", "3ES6");
			put("2BVI", "4V4U");
			put("5F45", "5V2A");
			put("1HE0", "2C4Y");
			put("5ES0", "5XJW");
			put("2ICB", "3ICB");
			put("4KA6", "5SYI");
			put("3CTU", "3K6E");
			put("1B8B", "1H7A");
			put("4KA5", "5SYH");
			put("5XWQ", "7FBT");
			put("2P07", "1GBJ");
			put("3D5D", "4V63");
			put("2C6J", "5X6N");
			put("1O0Z", "1O53");
			put("3D5C", "4V63");
			put("3CTC", "3HVL");
			put("1O0Y", "3R12");
			put("3D5B", "4V63");
			put("3D5A", "4V63");
			put("4QLD", "4RAL");
			put("1O0U", "2B8N");
			put("1O14", "2ajr");
			put("5F30", "6I3Q");
			put("4X9A", "5B7B");
			put("6FPE", "6S84");
			put("1B7C", "1C75");
			put("1O0I", "1SC0");
			put("1O0G", "1QWQ");
			put("4QL4", "5XW3");
			put("2C5T", "2UUE");
			put("4WWT", "4wt8");
			put("2C5P", "2WEV");
			put("6FOV", "6G1W");
			put("6FOU", "6G1V");
			put("2C5M", "2VO1");
			put("6FOT", "6G1U");
			put("3PLO", "3UJ3");
			put("1HC6", "1HC1");
			put("1HC5", "1HC1");
			put("1B6O", "1Z1R");
			put("1O09", "1P9K");
			put("1HC4", "1HC1");
			put("1B6N", "1Z1H");
			put("1HC3", "1HC1");
			put("1HC2", "1HC1");
			put("4WWE", "4wt8");
			put("1HBL", "1LH1");
			put("1NO0", "1os0");
			put("2IAC", "3BED");
			put("1NNN", "1PG3");
			put("1NNM", "1PG4");
			put("1AUB", "1E0E");
			put("2BSV", "2V2X");
			put("1NNG", "1YLI");
			put("4QJT", "4W2E");
			put("5F1D", "5VST");
			put("2BSU", "2V2W");
			put("4QJS", "4W2E");
			put("6YTH", "6ZLN");
			put("2C4O", "2CLW");
			put("3PL4", "3USY");
			put("2BSN", "2W6K");
			put("3PKR", "3USW");
			put("1ATQ", "1B0M");
			put("6LZS", "7EBM");
			put("6LZR", "7EBI");
			put("4E25", "4F13");
			put("6YT8", "7B83");
			put("4DPS", "4H55");
			put("4E23", "4F10");
			put("1ATC", "3ATC");
			put("1B5C", "2B5C");
			put("3D35", "3QM0");
			put("5F0D", "5FET");
			put("3CQM", "3NVD");
			put("1AT7", "1BAX");
			put("4WUS", "4wt8");
			put("2UWZ", "2X52");
			put("2UWY", "2XD1");
			put("3VVQ", "3WBN");
			put("6FMM", "6FUY");
			put("1ASI", "2ASI");
			put("1TXW", "1VRN");
			put("1TXV", "2VDL");
			put("1TY7", "2VC2");
			put("3VVJ", "6KGA");
			put("1TY6", "2VDN");
			put("1TY5", "2VDM");
			put("3CPV", "5CPV");
			put("1TY3", "2VDK");
			put("3D1U", "3F7W");
			put("3D1T", "5K9G");
			put("2UWG", "4AML");
			put("3CPN", "3K1U");
			put("1NLE", "1O3Z");
			put("1AS9", "1B0K");
			put("6FLX", "6HS3");
			put("4WTO", "6KJB");
			put("2OJS", "3BZI");
			put("1ARN", "1DYZ");
			put("1B3M", "1L9F");
			put("6LY2", "7VLJ");
			put("6Z2Y", "7PEL");
			put("6Z2R", "7BJ1");
			put("1ARA", "1AM0");
			put("2OJD", "3C6G");
			put("2BQ9", "2BZD");
			put("4X4Y", "6DN0");
			put("2BPT", "5OWU");
			put("2UVC", "4V59");
			put("2UVB", "4V59");
			put("2UVA", "4V58");
			put("1U8D", "4FE5");
			put("2BPL", "4AMV");
			put("1AR3", "1B0J");
			put("4QGJ", "5CST");
			put("3D0D", "3FMR");
			put("2BPJ", "2J6H");
			put("2UV9", "4V58");
			put("1B2N", "1G72");
			put("5L9M", "6HQH");
			put("4JYR", "4PNH");
			put("3IZW", "4V6K");
			put("3IZV", "4V6L");
			put("3IZU", "4V6K");
			put("3IZT", "4V6L");
			put("3IZS", "4V6I");
			put("2V6D", "2V95");
			put("3IZR", "3J61");
			put("1B1W", "1CMI");
			put("1U7E", "3FHI");
			put("1APR", "2APR");
			put("6FJR", "6FKO");
			put("1APP", "2APP");
			put("3IZF", "4V6I");
			put("3IZE", "4V6I");
			put("1NJ7", "1T50");
			put("3IZC", "3IZS");
			put("3IZB", "4V6I");
			put("3IZA", "3J2T");
			put("1APG", "3RTJ");
			put("1APE", "2APE");
			put("3IZ9", "3J62");
			put("3IZ8", "4V4L");
			put("3IZ7", "3J5Z");
			put("4JXP", "4MJ4");
			put("3IZ6", "3J60");
			put("3IYU", "4V7Q");
			put("4JXO", "4MJ2");
			put("3IZ5", "3IZR");
			put("3IYT", "3IZA");
			put("5XPQ", "6JMI");
			put("3CMK", "3FMQ");
			put("3IYN", "6B1T");
			put("4QEJ", "5W4X");
			put("4JXD", "4TN5");
			put("6SBZ", "6SCT");
			put("2V4W", "2WYA");
			put("3IYE", "3LOS");
			put("6FIM", "6FEX");
			put("2V4T", "2W8L");
			put("2V4S", "2W8K");
			put("4QEA", "4ZG5");
			put("3W3H", "4W4S");
			put("3W3F", "4W4R");
			put("3CM4", "6PPX");
			put("3PEZ", "3RRM");
			put("3PEX", "3RRN");
			put("1NHD", "1VRW");
			put("4X26", "5CM6");
			put("2BMP", "3BMP");
			put("5L7C", "6GIS");
			put("2V49", "4V5A");
			put("2V48", "4V5A");
			put("2V47", "4V5A");
			put("2V46", "4V5A");
			put("1NGV", "1YOV");
			put("156B", "256B");
			put("2V44", "2XY2");
			put("2OFL", "3C07");
			put("1ANH", "2ANH");
			put("3J9A", "6B43");
			put("1TSS", "2TSS");
			put("4QCZ", "1VY7");
			put("4QCY", "1VY7");
			put("4QCX", "1VY6");
			put("4QCW", "1VY6");
			put("4QCV", "1VY6");
			put("5XNU", "6A7W");
			put("4QCU", "1VY6");
			put("4QCT", "1VY5");
			put("4QCS", "1VY5");
			put("373D", "413D");
			put("2BLT", "1XX2");
			put("4QCR", "1VY5");
			put("1U4I", "2PJG");
			put("4QCQ", "1VY5");
			put("4QCP", "1VY4");
			put("4QD1", "1VY7");
			put("1TSG", "1O7B");
			put("4QCO", "1VY4");
			put("4QD0", "1VY7");
			put("1AMV", "2AMV");
			put("4QCN", "1VY4");
			put("4WOS", "4YDU");
			put("4QCM", "1VY4");
			put("2HY4", "3E5H");
			put("1TSB", "2TSB");
			put("2BLK", "2J85");
			put("1TSA", "2TSA");
			put("2V2Y", "2V8P");
			put("3J8E", "5TB0");
			put("4X0I", "5HU6");
			put("2HXJ", "3MW6");
			put("2BLD", "4V4U");
			put("4DIW", "4V96");
			put("1TRX", "1XOB");
			put("4DIV", "4V96");
			put("1AMG", "2AMG");
			put("2HXE", "3GF0");
			put("4JUX", "4V9H");
			put("6YM7", "7P87");
			put("1TRT", "2TRT");
			put("4JUW", "4V9H");
			put("1GYI", "2GYI");
			put("3VPF", "6J9U");
			put("3CJU", "3EGV");
			put("1TS1", "2TS1");
			put("4K6S", "4ZZ6");
			put("4K6Q", "4ZUO");
			put("4K6P", "5CST");
			put("3J7U", "5GKN");
			put("1TRC", "1FW4");
			put("5EGZ", "5JAV");
			put("3J7K", "3J8C");
			put("1AM3", "1A8O");
			put("1ALR", "2ALR");
			put("3J7J", "3J8B");
			put("1ALP", "2ALP");
			put("3W14", "5KQV");
			put("2ODS", "3DW0");
			put("1ALO", "1HLR");
			put("6M46", "7C2D");
			put("6M45", "7C2C");
			put("4QBD", "5W1A");
			put("6M43", "7C2A");
			put("6M42", "7C29");
			put("6M41", "7C23");
			put("4DI7", "4L4X");
			put("2I8G", "3V7B");
			put("1NEO", "2NEO");
			put("3J6V", "3JD5");
			put("2BK7", "2BO9");
			put("1TQK", "1VQX");
			put("1NEF", "2NEF");
			put("3J74", "3J7P");
			put("3J73", "3J7R");
			put("3J72", "3J7Q");
			put("3J71", "3J7O");
			put("2HVU", "2JXN");
			put("2HVT", "3HVT");
			put("4DHC", "4V95");
			put("4DHB", "4V95");
			put("6FEU", "6GWW");
			put("4DHA", "4V95");
			put("2BJL", "4BJL");
			put("3CIE", "1VSV");
			put("2I7M", "2JV2");
			put("6LR5", "7CZA");
			put("4DH9", "4V95");
			put("3J6A", "3JBC");
			put("2V0Q", "2VAF");
			put("3CHZ", "1VSU");
			put("1H8J", "2C51");
			put("6FED", "6EYD");
			put("3J5Z", "4V7E");
			put("4DGO", "6QS5");
			put("3J69", "3JBD");
			put("3J5X", "4V7D");
			put("3J5W", "4V7D");
			put("3J66", "4V7F");
			put("3J5U", "4V7C");
			put("3J65", "4V7F");
			put("3J5T", "4V7C");
			put("3J64", "4V7F");
			put("2HUY", "3C7I");
			put("4K4M", "4QEX");
			put("1TPI", "3TPI");
			put("4K4L", "4NCZ");
			put("3J62", "4V7E");
			put("3J61", "4V7E");
			put("3J60", "4V7E");
			put("3J5O", "4V7B");
			put("3J5N", "4V7B");
			put("2OBW", "2QED");
			put("7N0S", "7R98");
			put("3J5K", "4V7A");
			put("1AK3", "2AK3");
			put("3PAP", "2PAD");
			put("3J5J", "4V7A");
			put("3J5I", "4V79");
			put("2HV0", "2O28");
			put("3J5H", "4V79");
			put("1GW5", "2VGL");
			put("3J5G", "4V78");
			put("3J5F", "4V78");
			put("3CHA", "5CHA");
			put("2I6L", "7AQB");
			put("3J5E", "4V77");
			put("3J5D", "4V77");
			put("3J5C", "4V76");
			put("1U0Y", "6D1X");
			put("3J5B", "4V76");
			put("3J5A", "4V75");
			put("3PAD", "9PAP");
			put("2I6C", "4M3S");
			put("3CGV", "3OZ2");
			put("1NCM", "2NCM");
			put("3J4Z", "4V71");
			put("4K3T", "5AWV");
			put("3J4Y", "4V70");
			put("3J59", "4V75");
			put("3J4X", "4V6Z");
			put("3J58", "4V74");
			put("3J4W", "4V6Z");
			put("3J57", "4V74");
			put("3J4V", "4V6Y");
			put("14PS", "1QJB");
			put("5EEE", "6T42");
			put("3J56", "4V73");
			put("1GVB", "2GVB");
			put("5EED", "6T44");
			put("3J55", "4V73");
			put("1GVA", "2GVA");
			put("3J54", "4V72");
			put("3J53", "4V72");
			put("3J52", "4V6Y");
			put("3J51", "4V70");
			put("4WL5", "5LHC");
			put("3J50", "4V71");
			put("1H6Z", "2X0S");
			put("1H77", "1HK8");
			put("7N01", "7RW2");
			put("4WKK", "5I5L");
			put("151C", "251C");
			put("5L1Y", "6BF2");
			put("1NBT", "2NBT");
			put("1TO7", "1U8E");
			put("4K2W", "4YET");
			put("2BGX", "2WKX");
			put("3CG2", "3L8X");
			put("1NBG", "1P4M");
			put("3J44", "4V4N");
			put("2I4Y", "2NVR");
			put("1ZZO", "3IOS");
			put("3J43", "4V4N");
			put("1GTX", "1OHV");
			put("3VKV", "6J9T");
			put("3VKU", "6J9S");
			put("1TNA", "5TNA");
			put("6LNV", "7C73");
			put("6LO7", "7C74");
			put("3IRG", "3KNB");
			put("3J3F", "4V6X");
			put("3J3E", "4V6W");
			put("5L0X", "6D7R");
			put("3J3D", "4V6X");
			put("3J3C", "4V6W");
			put("3J3B", "4V6X");
			put("3J3A", "4V6X");
			put("2I4F", "2NML");
			put("2HSF", "3HSF");
			put("4WJC", "5F0A");
			put("2HSC", "3HSC");
			put("3J39", "4V6W");
			put("3J38", "4V6W");
			put("3J37", "4V6V");
			put("3J36", "4V6V");
			put("4K1M", "4YRV");
			put("5KOG", "6ASZ");
			put("2BG4", "2UWY");
			put("2BG3", "2UWX");
			put("4DDE", "4F66");
			put("3J2L", "4V6U");
			put("3J2K", "3J5Y");
			put("3IQK", "4FIK");
			put("3CEF", "3D7W");
			put("3CEE", "3GSH");
			put("1GST", "6GST");
			put("1GSR", "2GSR");
			put("1ZYA", "2B4R");
			put("1GT2", "2X0R");
			put("1TM8", "1YMG");
			put("4DD6", "5L3I");
			put("6RYS", "6XVE");
			put("4DD4", "5L3H");
			put("1TLN", "3TLN");
			put("4K0Q", "4V9I");
			put("4K0P", "4V9I");
			put("4K0M", "4V9I");
			put("4K0L", "4V9I");
			put("3J21", "4V6U");
			put("2I2V", "4V50");
			put("3J20", "4V6U");
			put("2I2U", "4V50");
			put("2I2T", "4V50");
			put("1GRY", "1GZU");
			put("5KNA", "5VZY");
			put("4K0F", "5EQB");
			put("2I2P", "4V50");
			put("1GRS", "2GRS");
			put("1AFN", "2AFN");
			put("1H40", "1UZC");
			put("5KMV", "5VZX");
			put("1AFG", "2AFG");
			put("2I2E", "4DY6");
			put("1GRK", "1OCM");
			put("3J0Z", "4V6Q");
			put("3J0Y", "4V6P");
			put("4JNS", "4R7W");
			put("3J19", "4V6T");
			put("3J0X", "4V6P");
			put("1TL0", "2NZG");
			put("4JNR", "4R88");
			put("3J18", "4V6T");
			put("3J0W", "4V6O");
			put("3J0V", "4V6O");
			put("4JNP", "4R85");
			put("3J0U", "4V6N");
			put("4DBJ", "4JGO");
			put("3J0T", "4V6N");
			put("4DBI", "4JGR");
			put("3J14", "4V6R");
			put("1ZX0", "3ORH");
			put("3J13", "4V6S");
			put("4JNL", "5YC7");
			put("3J12", "4V6Q");
			put("3J11", "4V6S");
			put("3J10", "4V6R");
			put("4JNI", "5YC6");
			put("1GR9", "1OCL");
			put("2HQ2", "4CDP");
			put("1GR8", "1OCK");
			put("1GR6", "2C7E");
			put("2HPK", "4MN0");
			put("5KM7", "6B42");
			put("2I1C", "2OW8");
			put("1TJQ", "3G8F");
			put("4DAK", "4JGQ");
			put("4DAH", "4JGP");
			put("3J01", "4V6M");
			put("3J00", "4V6M");
			put("1ADT", "2WB0");
			put("4WFP", "4ZAH");
			put("2BCL", "3BCL");
			put("2HON", "2O6I");
			put("1GQ3", "5VMQ");
			put("1ADM", "2ADM");
			put("2BCF", "4P0M");
			put("1ADK", "2ADK");
			put("1TIX", "1GOM");
			put("5XDY", "5ZVJ");
			put("3IMZ", "3R9A");
			put("1GPG", "1AIO");
			put("3CAT", "7CAT");
			put("1ADA", "2ADA");
			put("2BC6", "3ETX");
			put("2I09", "3M7V");
			put("3VFX", "4R0F");
			put("1ACT", "2ACT");
			put("2HO3", "4IQ0");
			put("1ACK", "2ACK");
			put("2NZN", "3C8S");
			put("1TI9", "1XS9");
			put("2NZK", "3C8R");
			put("1TI6", "4V4E");
			put("1ACE", "2ACE");
			put("1ZU9", "2B31");
			put("1TI4", "4V4D");
			put("2NZG", "3EFX");
			put("1ZU7", "2B41");
			put("1TI2", "4V4C");
			put("1ZU6", "2B2Z");
			put("1H0F", "1UN3");
			put("1TI0", "2DV8");
			put("1H0E", "1UN4");
			put("2NZB", "3C8Q");
			put("4PWR", "5HOQ");
			put("1ABX", "2ABX");
			put("4Q8J", "4XR7");
			put("1H06", "1V1K");
			put("1ABP", "1ABE");
			put("2BAH", "2FKP");
			put("1ABM", "1N0J");
			put("2NYO", "2JQ7");
			put("2BAE", "2FKT");
			put("1ABK", "2ABK");
			put("1ABH", "2ABH");
			put("3P9E", "4V81");
			put("3P9D", "4V81");
			put("3IKZ", "3PXU");
			put("1TGQ", "2B95");
			put("1TGP", "2TGP");
			put("2BA8", "2FKR");
			put("2BA7", "2FKS");
			put("2BA6", "2FKQ");
			put("2BA5", "2FKV");
			put("2BA4", "2FKU");
			put("4WCS", "5ZEM");
			put("4WCR", "5ZEJ");
			put("4WCQ", "5ZEI");
			put("3P8Q", "3QPR");
			put("4WCP", "5ZEG");
			put("1TGA", "2TGA");
			put("1GN5", "2GN5");
			put("1GMS", "1XFG");
			put("2HLL", "3ET5");
			put("2HLK", "3ET4");
			put("3VDO", "4NQW");
			put("1AAK", "2AAK");
			put("1MYS", "2MYS");
			put("1GMF", "2GMF");
			put("1GMA", "1ALZ");
			put("1AA8", "1VE9");
			put("2HKT", "3C8G");
			put("1GLY", "3GLY");
			put("4WBN", "6I5C");
			put("1GLT", "2GLT");
			put("3VCQ", "4DFU");
			put("1GM3", "1H3P");
			put("3P7E", "4FS8");
			put("3OVC", "3TYK");
			put("1MXM", "2OAU");
			put("4Q5Z", "5WOB");
			put("1TEP", "2TEP");
			put("1TEO", "1VQ2");
			put("4PTP", "5PTP");
			put("4Q61", "4ZGL");
			put("4WAR", "4WF1");
			put("4WAQ", "4WF1");
			put("4JHF", "4WYV");
			put("4WAP", "4WF1");
			put("4WB1", "4XRR");
			put("1TEB", "1XR5");
			put("1GKW", "2IZN");
			put("4WAO", "4WF1");
			put("1TEA", "1VPD");
			put("1GKV", "2IZ8");
			put("2NW5", "3FM3");
			put("2NVS", "2YU9");
			put("2NVR", "3C0Y");
			put("1MWX", "1VQQ");
			put("1N98", "2Q33");
			put("1MWV", "5L05");
			put("1TDX", "2TDX");
			put("1TE9", "1XR7");
			put("1TE8", "1XR6");
			put("2O7J", "3I5O");
			put("416D", "1O56");
			put("1ZPY", "3K6C");
			put("1ZQ8", "3KZO");
			put("4PSZ", "4RGC");
			put("1N8L", "2PNG");
			put("1ZQ6", "3KZN");
			put("3II8", "3N3T");
			put("1ZQ4", "2FNQ");
			put("1TDM", "2TDM");
			put("1ZQ2", "3KZM");
			put("4Q55", "4QT4");
			put("2UCE", "1QCQ");
			put("1MWF", "1PVN");
			put("4Q54", "4RVY");
			put("1ZQ0", "2FXK");
			put("4PT3", "5GTL");
			put("4PT0", "5GTK");
			put("3OU5", "6DK3");
			put("3IHN", "3JZE");
			put("2HJ2", "2P02");
			put("3IHH", "4E5O");
			put("4CYX", "4V93");
			put("4CYT", "5CYT");
			put("415D", "1O55");
			put("1TCT", "2TCT");
			put("3OTA", "3QMN");
			put("8TNA", "1TRA");
			put("4PS9", "5GT6");
			put("1TCI", "2TCI");
			put("3IH1", "4IQD");
			put("1GIY", "4V42");
			put("2HHR", "3HHR");
			put("1GIX", "4V42");
			put("1N6W", "1PXH");
			put("1MV7", "1SM5");
			put("4PRC", "2JBL");
			put("1N6S", "1VRO");
			put("1MUR", "4DM0");
			put("3OSC", "4RV4");
			put("1TBS", "2TBS");
			put("3BZ2", "4V62");
			put("1TBM", "2HD1");
			put("3BZ1", "4V62");
			put("4CXE", "4UJE");
			put("2HGU", "4V4Z");
			put("4CXD", "4UJE");
			put("4D9D", "4DIA");
			put("4CXC", "4UJE");
			put("4CXB", "4UJE");
			put("2HGR", "4V4Z");
			put("4D9A", "4DI9");
			put("2HGQ", "4V4Y");
			put("2HGP", "4V4Y");
			put("3IFH", "4V6H");
			put("3P3M", "4M0L");
			put("3IFG", "4V6H");
			put("5DVU", "5T6A");
			put("5DVT", "5T6I");
			put("2HGJ", "4V4X");
			put("2HGI", "4V4X");
			put("1GHO", "4V41");
			put("1TAX", "1GOK");
			put("4CWU", "6CGV");
			put("4D95", "4DI8");
			put("3C9Y", "3EMY");
			put("4D8R", "4V94");
			put("4D93", "4LNV");
			put("3ORB", "4V80");
			put("4D8Q", "4V94");
			put("3ORA", "4V80");
			put("3BXT", "3CBT");
			put("4PPW", "5K0T");
			put("5DVG", "5J7Z");
			put("3BY3", "3D26");
			put("4CWL", "4CTF");
			put("4JE2", "4QAG");
			put("4CWK", "4CTF");
			put("4CWJ", "4CTF");
			put("4CWI", "4CTF");
			put("2HFY", "3UYN");
			put("3OR9", "4V80");
			put("4CWH", "4CTF");
			put("2HFX", "3UYQ");
			put("3OR8", "3PJP");
			put("4CWG", "4CTF");
			put("6ETX", "6HTS");
			put("1TAA", "2TAA");
			put("1GGS", "1FI5");
			put("2HFL", "3HFL");
			put("6LAQ", "7DN7");
			put("1N4T", "2ILX");
			put("5DV1", "5XGF");
			put("1MT2", "2MT2");
			put("6F5G", "7ANQ");
			put("5E6L", "5JYY");
			put("1ZLW", "6MNF");
			put("5KBO", "6CMY");
			put("3BX6", "3KQ0");
			put("1ZLV", "6MU3");
			put("1MSL", "2OAR");
			put("1ZLU", "6MUB");
			put("3C93", "3NKW");
			put("1ZLS", "6MSY");
			put("1GGA", "2X0N");
			put("3P29", "4DZ9");
			put("4PP2", "5VCO");
			put("4PP1", "5VCN");
			put("2B9P", "4V4T");
			put("3P25", "4DZ7");
			put("2B9O", "4V4T");
			put("2B9N", "4V4S");
			put("2NR8", "3NWN");
			put("2HER", "2Q58");
			put("2B9M", "4V4S");
			put("2O37", "4RWU");
			put("3P21", "2Q21");
			put("6ESR", "6QE3");
			put("4JCB", "4V9G");
			put("2NR3", "3BZ6");
			put("6ESO", "6O1G");
			put("1N3V", "1NXN");
			put("4CUY", "4V92");
			put("4CUX", "4V92");
			put("4CUW", "4V91");
			put("4CUV", "4V91");
			put("1MS2", "2MS2");
			put("4JC9", "4V9G");
			put("6ESE", "6G2P");
			put("3BW5", "3JUH");
			put("5DTG", "5HCU");
			put("3BVR", "3C25");
			put("2HDY", "3GZC");
			put("5WYP", "6IZO");
			put("6ERX", "6FXN");
			put("3ICG", "3NDY");
			put("3C7B", "3MMC");
			put("2O1R", "3LAE");
			put("1N2U", "1Q54");
			put("1GEP", "2GEP");
			put("1A9K", "1B0G");
			put("1GEO", "1AOP");
			put("2AWB", "4V4Q");
			put("5DSN", "5YUK");
			put("3P0D", "4FL4");
			put("1A9D", "1BZ9");
			put("5DSJ", "5YUJ");
			put("5DSI", "5YUI");
			put("2AVY", "4V4Q");
			put("2B7W", "3JR6");
			put("2AW7", "4V4Q");
			put("2HCY", "4W6Z");
			put("2AW4", "4V4Q");
			put("2AVR", "3ETW");
			put("1GDZ", "1GWP");
			put("1GDY", "1GWP");
			put("5E47", "5KK0");
			put("1GDS", "1GWP");
			put("1N1W", "1O1H");
			put("3UZN", "4V8F");
			put("1GDO", "1XFF");
			put("3UZM", "4V8F");
			put("3UZL", "4V8F");
			put("1GDM", "2GDM");
			put("3UZK", "4V8F");
			put("3UZI", "4V8E");
			put("3C69", "3W7U");
			put("3UZH", "4V8E");
			put("3C68", "3W7T");
			put("3UZG", "4V8E");
			put("3C67", "3W7S");
			put("3UZF", "4V8E");
			put("1GDG", "1KW8");
			put("2B79", "3IJT");
			put("5WX0", "5ZT1");
			put("5X8O", "6LK4");
			put("2HC6", "2HVA");
			put("3UZ9", "4V8D");
			put("3UZ8", "4V8D");
			put("1GCX", "1WNS");
			put("3UZ7", "4V8D");
			put("3UZ6", "4V8D");
			put("5DQX", "7EZZ");
			put("3UZ4", "4V8C");
			put("3UZ3", "4V8C");
			put("6F21", "6R5M");
			put("3C5B", "3J40");
			put("3UZ2", "4V8C");
			put("1GCR", "4GCR");
			put("3UZ1", "4V8C");
			put("1N16", "1VRP");
			put("5WWB", "6JHG");
			put("2HBI", "2AV3");
			put("2B6D", "3IB0");
			put("3UYM", "4K0U");
			put("5WWA", "6JHI");
			put("6Y6W", "6ZWP");
			put("4CRR", "5FUI");
			put("1MON", "3MON");
			put("3UYG", "4V8B");
			put("1GCH", "2GCH");
			put("3UYF", "4V8B");
			put("3UYE", "4V8B");
			put("3UYD", "4V8B");
			put("6Y70", "6ZWR");
			put("3UYA", "3UYB");
			put("5WVT", "6JFX");
			put("5WVS", "6JHH");
			put("5X84", "6IP9");
			put("2B66", "4V4R");
			put("5WVQ", "6JHF");
			put("5WW2", "6JFJ");
			put("2B64", "4V4R");
			put("5WW1", "6JEQ");
			put("3C4I", "4PT4");
			put("2HAT", "3HAT");
			put("2NN9", "7NN9");
			put("2NMW", "3OXC");
			put("3UXT", "4V8A");
			put("3C4D", "4FK3");
			put("1ZHE", "2ETG");
			put("3UXS", "4V8A");
			put("1ZHD", "1VRY");
			put("1A6O", "1LR4");
			put("3UXR", "4V8A");
			put("3UXQ", "4V8A");
			put("3V9Q", "4GQ0");
			put("1GBP", "3GBP");
			put("2B5C", "3B5C");
			put("4CQT", "5AJM");
			put("5DPK", "6U7T");
			put("4CR1", "4V90");
			put("2ASZ", "3C76");
			put("3BRR", "4XC4");
			put("2ASX", "2L7H");
			put("2ASW", "2L7H");
			put("2AT7", "3C78");
			put("2AT4", "3C77");
			put("1ZGM", "2FNO");
			put("4W83", "4YFJ");
			put("4D2A", "4CTG");
			put("3OL1", "3UF1");
			put("3UWR", "5EJ2");
			put("1GAP", "3GAP");
			put("3C2Z", "4Q4S");
			put("4W7B", "4WNB");
			put("3OKB", "3Q1R");
			put("3BQS", "3MAB");
			put("1ZFR", "2OUE");
			put("5DOD", "5V3P");
			put("3C2N", "4PUJ");
			put("3OK7", "3Q1Q");
			put("3UVZ", "4E4T");
			put("2B41", "2DT1");
			put("2B40", "2DT2");
			put("2B3N", "3K67");
			put("5JZZ", "6RK9");
			put("1MLT", "2MLT");
			put("4D16", "4CTG");
			put("4D15", "4CTG");
			put("3BPY", "3L2C");
			put("4D14", "4CTG");
			put("6EME", "6I2H");
			put("6EMC", "6I2B");
			put("2B2Z", "2DT3");
			put("5DNG", "5TRB");
			put("1ZER", "1KA5");
			put("4D0J", "5JU6");
			put("1ZEP", "2A8B");
			put("4D0I", "4V93");
			put("4PI4", "4XUI");
			put("1MLE", "1MUC");
			put("4D0H", "4V93");
			put("2B2S", "5SX0");
			put("2B2R", "5SW6");
			put("2B2Q", "5SW5");
			put("2B32", "2PZV");
			put("2B2P", "2DSW");
			put("5WSM", "5Z4W");
			put("2B2O", "5SW4");
			put("3V6X", "4V8J");
			put("3V6W", "4V8J");
			put("2AQM", "4L05");
			put("2B2M", "2EXN");
			put("3V6V", "4V8J");
			put("4COA", "4CZ1");
			put("5WSJ", "5YAR");
			put("2B2L", "2DSV");
			put("3V6U", "4V8J");
			put("5WSI", "5Z05");
			put("6ELH", "6FWF");
			put("1ZDZ", "2O05");
			put("4IZR", "5LFS");
			put("6L9E", "7DN6");
			put("3OHZ", "4V7Y");
			put("3OHY", "4V7Y");
			put("1ZE0", "2O9X");
			put("5X3Q", "6J6F");
			put("2APP", "3APP");
			put("3OI5", "4V7Z");
			put("3OI4", "4V7Z");
			put("3OI3", "4V7Z");
			put("3OI2", "4V7Z");
			put("4CNA", "5CNA");
			put("3OI1", "4V7Y");
			put("3OI0", "4V7Y");
			put("3OHM", "7SQ2");
			put("3V63", "4IRM");
			put("3OHK", "4V7X");
			put("3OHJ", "4V7X");
			put("2APE", "4APE");
			put("5X3A", "5XD0");
			put("3OHD", "4V7X");
			put("3OHC", "4V7X");
			put("1ZCX", "2R99");
			put("475D", "1ENN");
			put("1ZCS", "3L4P");
			put("2B0W", "2IU0");
			put("3OGY", "4V7W");
			put("3OGW", "7VIN");
			put("3OH7", "4V7W");
			put("3OH5", "4V7W");
			put("2AOK", "2B2P");
			put("6EK0", "6QZP");
			put("5JX6", "6IDW");
			put("1SV8", "2O9O");
			put("1SV7", "1T0G");
			put("3OGE", "4V7W");
			put("1MIN", "2MIN");
			put("5JWN", "6CX6");
			put("6L7G", "7E3W");
			put("4IY3", "6RS2");
			put("6L7F", "7E3V");
			put("3BMR", "3JQG");
			put("2AO8", "2DSU");
			put("3USA", "4I0P");
			put("5WQ5", "5XN8");
			put("3OFZ", "4V7V");
			put("3OFY", "4V7V");
			put("3OFX", "4V7V");
			put("3BMM", "3JQF");
			put("2AO4", "2GZL");
			put("1A0Y", "1Y4P");
			put("3BML", "3JQ9");
			put("2AO3", "2ZQO");
			put("1A0X", "1Y4G");
			put("3BMK", "3JQE");
			put("1A0W", "1Y4F");
			put("3BMJ", "3JQD");
			put("1A0V", "1Y46");
			put("4PEN", "5CWA");
			put("3BMI", "3JQC");
			put("2GZT", "3BOY");
			put("2AO0", "3H4G");
			put("3BMH", "3JQB");
			put("3OFR", "4V7U");
			put("6XOE", "7UJT");
			put("3BMG", "3JQA");
			put("3OFQ", "4V7U");
			put("3US7", "4Q0K");
			put("7SB9", "7T6Y");
			put("3BMF", "3JQ8");
			put("3OFP", "4V7U");
			put("3BME", "3JQ7");
			put("3OFO", "4V7U");
			put("3OG0", "4V7V");
			put("3BMD", "3JQ6");
			put("3OFL", "5W1X");
			put("2ANF", "2B2L");
			put("4PEC", "4U1U");
			put("4PEB", "4U1U");
			put("4PEA", "4U1U");
			put("1STW", "2STW");
			put("1MHR", "2MHR");
			put("4CL5", "5A2O");
			put("4W2D", "4P6F");
			put("3OFD", "4V7T");
			put("4CL4", "5A2N");
			put("4W2C", "4P6F");
			put("1STT", "2STT");
			put("3OFC", "4V7T");
			put("4W2B", "4P6F");
			put("3OFB", "4V7T");
			put("4W2A", "4P6F");
			put("3OFA", "4V7T");
			put("4CKO", "4CQ1");
			put("2AMZ", "2Q3N");
			put("4PE9", "4U1U");
			put("2AMW", "2LKI");
			put("4W1Z", "3J7O");
			put("1ZB2", "2R3D");
			put("1ZB0", "2PJO");
			put("4W28", "3J7R");
			put("4W27", "3J7R");
			put("1ZAM", "2R2X");
			put("4W26", "3J7R");
			put("1MHB", "2MHB");
			put("4W25", "3J7Q");
			put("4W24", "3J7Q");
			put("4W23", "3J7P");
			put("4W22", "3J7P");
			put("4W21", "3J7P");
			put("4W20", "3J7O");
			put("3I9E", "4V6G");
			put("4CJY", "4UN0");
			put("3I9D", "4V6G");
			put("3I9C", "4V6G");
			put("3I9B", "4V6G");
			put("3OEG", "3VDI");
			put("2GYC", "4V4W");
			put("1SSS", "1WB8");
			put("2GYB", "4V4W");
			put("3V2F", "4V8I");
			put("3HWZ", "3IB1");
			put("2GYA", "4V4V");
			put("3V2E", "4V8I");
			put("4IW5", "4KNZ");
			put("3HWY", "3IAZ");
			put("3V2D", "4V8I");
			put("3V2C", "4V8I");
			put("3HX7", "4V6B");
			put("3HWV", "3IB0");
			put("3HX5", "4V6B");
			put("2ALT", "3IB1");
			put("2GY9", "4V4V");
			put("1SSI", "2SSI");
			put("3HX2", "4V6B");
			put("3HWQ", "3IB2");
			put("1T4H", "3FPQ");
			put("3V29", "4V8H");
			put("3V28", "4V8H");
			put("2NEW", "1NEW");
			put("3OE2", "5XB0");
			put("3V27", "4V8H");
			put("2H9Q", "3GLB");
			put("3V26", "4V8H");
			put("3V25", "4V8G");
			put("3I8I", "4V6F");
			put("2H9O", "2O9K");
			put("3V24", "4V8G");
			put("5DHW", "6ISD");
			put("3I8H", "4V6F");
			put("2ALI", "3QY3");
			put("3V23", "4V8G");
			put("3I8G", "4V6F");
			put("3V22", "4V8G");
			put("3I8F", "4V6F");
			put("3UQ1", "4FO6");
			put("1SRT", "2SRT");
			put("1MFO", "2CC1");
			put("1T42", "1U6B");
			put("6L4F", "7D52");
			put("1MFH", "2AKQ");
			put("3V0Y", "4Q4R");
			put("6L3Z", "7C75");
			put("3BJG", "3SRP");
			put("7FEN", "7VSI");
			put("3UOS", "4V89");
			put("3UOQ", "4V89");
			put("3OCK", "4H0B");
			put("1MEV", "2MEV");
			put("6L41", "7DMR");
			put("3HVB", "4J40");
			put("1FXN", "3FXN");
			put("1FXM", "1K6A");
			put("5JT3", "5GH0");
			put("3HUZ", "4V6A");
			put("3HUY", "4V6A");
			put("3HUX", "4V6A");
			put("3HUW", "4V6A");
			put("1FXC", "3FXC");
			put("1FXB", "2FXB");
			put("1MEF", "3MEF");
			put("4PAP", "3PAD");
			put("1T2G", "2A4J");
			put("6EES", "6ORB");
			put("2H7P", "4TZT");
			put("6L2V", "7BYZ");
			put("2H7N", "4U0K");
			put("2H7M", "4TZK");
			put("2H7L", "4TRJ");
			put("2H7K", "2OCD");
			put("5K49", "5WBC");
			put("1SPZ", "2SPZ");
			put("2H7I", "4U0J");
			put("3OBD", "4H07");
			put("3OBC", "4QGP");
			put("3BI8", "3IRD");
			put("1MDH", "2MDH");
			put("4PA2", "5GT4");
			put("2H78", "3CUM");
			put("2H6W", "3GWS");
			put("3OAT", "4V7S");
			put("1FVZ", "1T27");
			put("3OAS", "4V7S");
			put("3OAR", "4V7S");
			put("6KPZ", "7DDJ");
			put("2GUR", "1C4E");
			put("251C", "351C");
			put("3OAQ", "4V7S");
			put("6KPY", "7DDG");
			put("3UMU", "4GYF");
			put("6KPX", "7DDI");
			put("6KPW", "7DDH");
			put("6KPV", "7DDL");
			put("3BHC", "3CK6");
			put("6KPU", "7DDF");
			put("6EDO", "6OR5");
			put("1MCU", "1F2S");
			put("2AIC", "2JOL");
			put("6KQ0", "7DDK");
			put("2NBK", "6CO4");
			put("3OAE", "5W1O");
			put("1MD1", "1N5B");
			put("5JQO", "6KYL");
			put("5DEE", "5F2V");
			put("1MCG", "2MCG");
			put("2TNC", "5TNC");
			put("2AI4", "3N55");
			put("2TNA", "7TNA");
			put("6ED8", "7MIQ");
			put("1SOD", "2SOD");
			put("3ULW", "4IYL");
			put("1SOB", "2SOB");
			put("5DDX", "5VII");
			put("1MBW", "2MBW");
			put("5DE4", "5VIH");
			put("1MC6", "1MR0");
			put("1SNW", "2SNW");
			put("1MBR", "2MBR");
			put("1FUM", "1l0v");
			put("5DDN", "5VIJ");
			put("1MBP", "2MBP");
			put("1SNS", "2SNS");
			put("3BFN", "6NJE");
			put("1YZO", "2GG1");
			put("1SNI", "2SNI");
			put("2H58", "5WDE");
			put("5K1E", "5WV3");
			put("2H4S", "2DUT");
			put("3I3K", "3LD6");
			put("3HRJ", "3LC0");
			put("3UKS", "4IR8");
			put("1MB5", "2MB5");
			put("1G5O", "1GJH");
			put("5JOT", "6BQE");
			put("4CDS", "4UEU");
			put("2ZXS", "3AJN");
			put("2TLN", "3TLN");
			put("1YZ8", "2LKX");
			put("1SN3", "2SN3");
			put("5DCJ", "5XGA");
			put("2H4A", "3CKM");
			put("3HQS", "3KKI");
			put("1MAD", "2MAD");
			put("7FA2", "7VJN");
			put("2GRS", "3GRS");
			put("3HQK", "3NCY");
			put("2AFK", "4WZB");
			put("1G56", "1VM1");
			put("5JNZ", "6IYI");
			put("4IOZ", "4O9B");
			put("3I1Z", "4V6E");
			put("6XG2", "6XKH");
			put("3I1X", "3MG3");
			put("4J13", "4QXD");
			put("3I1W", "3MG2");
			put("3I1V", "3MG1");
			put("3I1T", "4V6D");
			put("3I1S", "4V6D");
			put("3I1R", "4V6D");
			put("2H2X", "2M21");
			put("3I22", "4V6E");
			put("3I1Q", "4V6D");
			put("3I21", "4V6E");
			put("3I1P", "4V6C");
			put("3I20", "4V6E");
			put("3I1O", "4V6C");
			put("3I1N", "4V6C");
			put("3I1M", "4V6C");
			put("4IOG", "4J11");
			put("2H33", "2JM5");
			put("4IOE", "4J10");
			put("1FRU", "3FRU");
			put("2AED", "4DRZ");
			put("5DAO", "5EHF");
			put("2AEA", "2APJ");
			put("1YWZ", "2K07");
			put("5JN1", "6UZU");
			put("4J05", "7SP5");
			put("6XF0", "7UIR");
			put("1FRC", "2FRC");
			put("4INM", "4X23");
			put("3BCL", "4BCL");
			put("1SKE", "2SKE");
			put("1SKD", "2SKD");
			put("4ING", "4QRN");
			put("1SKC", "2SKC");
			put("2H1Q", "3L5O");
			put("2ADK", "3ADK");
			put("1G34", "1OYQ");
			put("5WFA", "6B2Y");
			put("2ZV5", "4V60");
			put("2ZV4", "4V60");
			put("2GPD", "4GPD");
			put("6XDU", "7UJS");
			put("2TIM", "5TIM");
			put("3BC7", "3P7G");
			put("3BC6", "3P7H");
			put("1YVV", "3KKJ");
			put("2ZUO", "4V60");
			put("3BBS", "3P7F");
			put("6QWJ", "7QCO");
			put("1YW3", "2IFA");

		}
	};

	public String getEffectivePDBID(String val_name) {

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?"))
			return val_name;

		String pdb_id = map_obsolete_pdb_id.get(val_name);

		if (!(pdb_id == null || pdb_id.isEmpty() || pdb_id.equals(".") || pdb_id.equals("?")))
			return pdb_id;

		return val_name;
	}

	public static java.sql.Date getOriginalReleaseDate(java.sql.Date date, Connection conn_bmrb, String entry_id) {

		Statement state = null;
		ResultSet rset = null;

		String query = new String("select \"Date\" as \"Original_release_date\" from \"Release\" where \"Entry_ID\"='" + entry_id + "' and \"Type\"='%original%'");

		try {

			state = conn_bmrb.createStatement();
			rset = state.executeQuery(query);

			while (rset.next()) {

				date = rset.getDate("Original_release_date");

				break;
			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_Util_Entry.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_Util_Entry.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		if (entry_id.equals("18260"))
			date = java.sql.Date.valueOf("2012-05-12");

		return date;
	}

	public static java.sql.Date getFirstReleaseDate(java.sql.Date date, Connection conn_bmrb, String entry_id) {

		Statement state = null;
		ResultSet rset = null;

		String query = new String("select min(\"Date\") as \"First_release_date\" from \"Release\" where \"Entry_ID\"='" + entry_id + "'");

		try {

			state = conn_bmrb.createStatement();
			rset = state.executeQuery(query);

			while (rset.next()) {

				date = rset.getDate("First_release_date");

				break;
			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_Util_OrderParam.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_Util_Entry.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		if (entry_id.equals("18260"))
			date = java.sql.Date.valueOf("2012-05-12");

		return date;
	}

	public static java.sql.Date getLastReleaseDate(java.sql.Date date, Connection conn_bmrb, String entry_id) {

		Statement state = null;
		ResultSet rset = null;

		String query = new String("select max(\"Date\") as \"Last_release_date\" from \"Release\" where \"Entry_ID\"='" + entry_id + "'");

		try {

			state = conn_bmrb.createStatement();
			rset = state.executeQuery(query);

			while (rset.next()) {

				date = rset.getDate("Last_release_date");

				break;
			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_Util_Entry.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_Util_Entry.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		if (entry_id.equals("18260"))
			date = java.sql.Date.valueOf("2013-02-12");

		return date;
	}
}
