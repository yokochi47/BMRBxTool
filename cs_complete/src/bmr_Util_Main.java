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
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class bmr_Util_Main {

	public static final String user = System.getProperty("user.name");

	public static String url_bmrb = "jdbc:postgresql://localhost/bmrb";
	public static String user_bmrb = "";
	public static String pass_bmrb = "";

	public static List<assignable_atom> aa_atom = null;
	public static List<assignable_atom> dna_atom = null;
	public static List<assignable_atom> rna_atom = null;

	public static List<backbone_atom> aa_bb_atom = null;
	public static List<backbone_atom> dna_bb_atom = null;
	public static List<backbone_atom> rna_bb_atom = null;

	public static List<sidechain_atom> aa_sc_atom = null;
	public static List<sidechain_atom> dna_sc_atom = null;
	public static List<sidechain_atom> rna_sc_atom = null;

	public static List<aromatic_atom> aa_arom_atom = null;
	public static List<aromatic_atom> dna_arom_atom = null;
	public static List<aromatic_atom> rna_arom_atom = null;

	public static List<methyl_atom> aa_methyl_atom = null;
	public static List<methyl_atom> dna_methyl_atom = null;
	public static List<methyl_atom> rna_methyl_atom = null;

	public static assignable_atom[][][] popular_aa_atom_by_comp_id = null;
	public static assignable_atom[][][] popular_dna_atom_by_comp_id = null;
	public static assignable_atom[][][] popular_rna_atom_by_comp_id = null;

	public static backbone_atom[][][] popular_aa_bb_atom_by_comp_id = null;
	public static backbone_atom[][][] popular_dna_bb_atom_by_comp_id = null;
	public static backbone_atom[][][] popular_rna_bb_atom_by_comp_id = null;

	public static sidechain_atom[][][] popular_aa_sc_atom_by_comp_id = null;
	public static sidechain_atom[][][] popular_dna_sc_atom_by_comp_id = null;
	public static sidechain_atom[][][] popular_rna_sc_atom_by_comp_id = null;

	public static aromatic_atom[][][] popular_aa_arom_atom_by_comp_id = null;
	public static aromatic_atom[][][] popular_dna_arom_atom_by_comp_id = null;
	public static aromatic_atom[][][] popular_rna_arom_atom_by_comp_id = null;

	public static methyl_atom[][][] popular_aa_methyl_atom_by_comp_id = null;
	public static methyl_atom[][][] popular_dna_methyl_atom_by_comp_id = null;
	public static methyl_atom[][][] popular_rna_methyl_atom_by_comp_id = null;

	public static List<alternative_atom> aa_alt_atom = null;
	public static List<alternative_atom> dna_alt_atom = null;
	public static List<alternative_atom> rna_alt_atom = null;

	public static final String url_src = "https://bmrb.pdbj.org/ftp/pub/bmrb/statistics/chem_shifts/";
	public static final String aa_filt_csv = "aa_filt.csv";
	public static final String dna_filt_csv = "dna_filt.csv";
	public static final String rna_filt_csv = "rna_filt.csv";

	public static final float aa_frac_threshold = (float) 0.1;
	public static final float dna_frac_threshold = (float) 0.3;
	public static final float rna_frac_threshold = (float) 0.3;

	public static String today;

	private static String bmrbx_tool_home = ".";

	private static String str_dir_name = bmrbx_tool_home + "/cs_complete_str/";
	private static String loc_dir_name = bmrbx_tool_home + "/cs_complete_loc/";

	private static Runtime runtime = Runtime.getRuntime();
	private static final int cpu_num = runtime.availableProcessors();
	private static int max_thrds = cpu_num;

	public static void main(String[] args) throws IOException, ParseException {

		for (int i = 0; i < args.length; i++) {

			if (args[i].equals("--home")) {
				bmrbx_tool_home = args[++i];

				str_dir_name = bmrbx_tool_home + "/cs_complete_str/";
				loc_dir_name = bmrbx_tool_home + "/cs_complete_loc/";
			}

			else if (args[i].equals("--url-bmrb"))
				url_bmrb = args[++i];

			else if (args[i].equals("--user-bmrb"))
				user_bmrb = args[++i];

			else if (args[i].equals("--pass-bmrb"))
				pass_bmrb = args[++i];

			else if (args[i].equals("--max-thrds")) {
				max_thrds = Integer.valueOf(args[++i]);

				if (max_thrds <= 0 || max_thrds > cpu_num * 16) {
					System.err.println("Out of range (max_thrds).");
					System.exit(1);
				}

			}

			else {
				System.out.println("Usage: --home DIR --url-bmrb BMRB --user-bmrb USER --pass-bmrb WORD");
				System.out.println(" --home      DIR  : BMRBxTool home directory. (" + bmrbx_tool_home + ")");
				System.out.println(" --url-bmrb  BMRB : URL of BMRB database. (" + url_bmrb + ")");
				System.out.println(" --user-bmrb USER : Username of BMRB database.");
				System.out.println(" --pass-bmrb WORD : Password of BMRB database.");
				System.out.println(" --max-thrds PROC : Number of threads. (default is number of processores)");

				System.exit(1);
			}

		}

		File str_dir = new File(str_dir_name);

		if (!str_dir.isDirectory()) {

			if (!str_dir.mkdir()) {
				System.err.println("Can't create directory '" + str_dir_name + "'.");
				System.exit(1);
			}

		}

		File loc_dir = new File(loc_dir_name);

		if (loc_dir.exists()) {

			if (loc_dir.isFile())
				loc_dir.delete();

			else if (loc_dir.isDirectory()) {

				File[] files = loc_dir.listFiles();

				for (int i = 0; i < files.length; i++)
					files[i].delete();
			}

		}

		if (!loc_dir.isDirectory()) {

			if (!loc_dir.mkdir()) {
				System.err.println("Can't create directory '" + loc_dir_name + "'.");
				System.exit(1);
			}

		}

		File excluded_atom_str = new File(str_dir_name + "excluded_atoms.str");

		FileWriter exclw = new FileWriter(excluded_atom_str);

		exclw.write("data_chem_shift_completeness_excluded_atom_list\n\n");

		exclw.write("save_chem_shift_completeness_excluded_atom_list_aa\n");
		exclw.write("    _chem_shift_completeness_excluded_atom_list.Input_file         " + aa_filt_csv + "\n");
		exclw.write("    _chem_shift_completeness_excluded_atom_list.Electric_address   " + url_src + aa_filt_csv + "\n");

		// assignable atoms

		aa_atom = new ArrayList<assignable_atom>();

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

			URL url_aa = new URL(url_src + aa_filt_csv);
			HttpsURLConnection conn = (HttpsURLConnection) url_aa.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());

			String last_modified = conn.getHeaderField("Last-Modified");
			Date last_date = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH).parse(last_modified);
			DateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
			exclw.write("    _chem_shift_completeness_excluded_atom_list.Last_modified_date " + date_format.format(last_date) + "\n");
			exclw.write("    _chem_shift_completeness_excluded_atom_list.Fraction_threshold " + aa_frac_threshold + "\n\n");

			exclw.write("    _loop\n");
			exclw.write("    _chem_shift_completeness_excluded_atom.Comp_ID\n");
			exclw.write("    _chem_shift_completeness_excluded_atom.Atom_ID\n");
			exclw.write("    _chem_shift_completeness_excluded_atom.Fraction\n\n");

			BufferedReader in = new BufferedReader(new InputStreamReader(url_aa.openStream()));

			String line = in.readLine(); // header

			while ((line = in.readLine()) != null) {

				if (!line.matches(".*,.*,.*,.*,.*,.*,.*"))
					continue;

				String[] elem = line.split(",");

				aa_atom.add(new assignable_atom(elem[0], elem[1], Integer.valueOf(elem[2])));

			}

			in.close();

			for (aa_seq_codes aa_seq_code : aa_seq_codes.values()) {

				String aa_comp_id = aa_seq_code.name();

				int max_count = 0;

				for (assignable_atom atom : aa_atom) {

					if (!atom.comp_id.equals(aa_comp_id))
						continue;

					if (atom.count > max_count)
						max_count = atom.count;

				}

				for (assignable_atom atom : aa_atom) {

					if (!atom.comp_id.equals(aa_comp_id))
						continue;

					if (atom.count < max_count * aa_frac_threshold) {

						exclw.write(String.format("    %-4s %-4s %3.2f\n", aa_comp_id, atom.alt_atom_id, (float) atom.count / (float) max_count));
						atom.minor = true;

					}

				}

			}

			exclw.write("    stop_\n");
			exclw.write("save_\n\n");

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}

		popular_aa_atom_by_comp_id = new assignable_atom[max_thrds][aa_seq_codes.values().length][];

		for (int thrd_id = 0; thrd_id < max_thrds; thrd_id++) {

			for (aa_seq_codes aa_seq_code : aa_seq_codes.values())
				popular_aa_atom_by_comp_id[thrd_id][aa_seq_code.ordinal()] = assignable_atom.popular_atoms(polymer_types.aa, aa_seq_code.name());

		}

		exclw.write("save_chem_shift_completeness_excluded_atom_list_dna\n");
		exclw.write("    _chem_shift_completeness_excluded_atom_list.Input_file         " + dna_filt_csv + "\n");
		exclw.write("    _chem_shift_completeness_excluded_atom_list.Electric_address   " + url_src + dna_filt_csv + "\n");

		dna_atom = new ArrayList<assignable_atom>();

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

			URL url_dna = new URL(url_src + dna_filt_csv);
			HttpsURLConnection conn = (HttpsURLConnection) url_dna.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());

			String last_modified = conn.getHeaderField("Last-Modified");
			Date last_date = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH).parse(last_modified);
			DateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
			exclw.write("    _chem_shift_completeness_excluded_atom_list.Last_modified_date " + date_format.format(last_date) + "\n");
			exclw.write("    _chem_shift_completeness_excluded_atom_list.Fraction_threshold " + dna_frac_threshold + "\n\n");

			exclw.write("    _loop\n");
			exclw.write("    _chem_shift_completeness_excluded_atom.Comp_ID\n");
			exclw.write("    _chem_shift_completeness_excluded_atom.Atom_ID\n");
			exclw.write("    _chem_shift_completeness_excluded_atom.Fraction\n\n");

			BufferedReader in = new BufferedReader(new InputStreamReader(url_dna.openStream()));

			String line = in.readLine(); // header

			while ((line = in.readLine()) != null) {

				if (!line.matches(".*,.*,.*,.*,.*,.*,.*"))
					continue;

				String[] elem = line.split(",");

				dna_atom.add(new assignable_atom(elem[0], elem[1], Integer.valueOf(elem[2])));

			}

			in.close();

			for (dna_seq_codes dna_seq_code : dna_seq_codes.values()) {

				String dna_comp_id = dna_seq_code.name();

				int max_count = 0;

				for (assignable_atom atom : dna_atom) {

					if (!atom.comp_id.equals(dna_comp_id))
						continue;

					if (atom.count > max_count)
						max_count = atom.count;

				}

				for (assignable_atom atom : dna_atom) {

					if (!atom.comp_id.equals(dna_comp_id))
						continue;

					if (!atom.alt_atom_id.equals("P") && atom.count < max_count * dna_frac_threshold) {

						exclw.write(String.format("    %-4s %-4s %3.2f\n", dna_comp_id, atom.alt_atom_id, (float) atom.count / (float) max_count));
						atom.minor = true;

					}

				}

			}

			exclw.write("    stop_\n");
			exclw.write("save_\n\n");

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}

		popular_dna_atom_by_comp_id = new assignable_atom[max_thrds][dna_seq_codes.values().length][];

		for (int thrd_id = 0; thrd_id < max_thrds; thrd_id++) {

			for (dna_seq_codes dna_seq_code : dna_seq_codes.values())
				popular_dna_atom_by_comp_id[thrd_id][dna_seq_code.ordinal()] = assignable_atom.popular_atoms(polymer_types.dna, dna_seq_code.name());

		}

		exclw.write("save_chem_shift_completeness_excluded_atom_list_rna\n");
		exclw.write("    _chem_shift_completeness_excluded_atom_list.Input_file         " + rna_filt_csv + "\n");
		exclw.write("    _chem_shift_completeness_excluded_atom_list.Electric_address   " + url_src + rna_filt_csv + "\n");

		rna_atom = new ArrayList<assignable_atom>();

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

			URL url_rna = new URL(url_src + rna_filt_csv);
			HttpsURLConnection conn = (HttpsURLConnection) url_rna.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());

			String last_modified = conn.getHeaderField("Last-Modified");
			Date last_date = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH).parse(last_modified);
			DateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
			exclw.write("    _chem_shift_completeness_excluded_atom_list.Last_modified_date " + date_format.format(last_date) + "\n");
			exclw.write("    _chem_shift_completeness_excluded_atom_list.Fraction_threshold " + rna_frac_threshold + "\n\n");

			exclw.write("    _loop\n");
			exclw.write("    _chem_shift_completeness_excluded_atom.Comp_ID\n");
			exclw.write("    _chem_shift_completeness_excluded_atom.Atom_ID\n");
			exclw.write("    _chem_shift_completeness_excluded_atom.Fraction\n\n");

			BufferedReader in = new BufferedReader(new InputStreamReader(url_rna.openStream()));

			String line = in.readLine(); // header

			while ((line = in.readLine()) != null) {

				if (!line.matches(".*,.*,.*,.*,.*,.*,.*"))
					continue;

				String[] elem = line.split(",");

				rna_atom.add(new assignable_atom(elem[0], elem[1], Integer.valueOf(elem[2])));

			}

			in.close();

			for (rna_seq_codes rna_seq_code : rna_seq_codes.values()) {

				String rna_comp_id = rna_seq_code.name();

				int max_count = 0;

				for (assignable_atom atom : rna_atom) {

					if (!atom.comp_id.equals(rna_comp_id))
						continue;

					if (atom.count > max_count)
						max_count = atom.count;

				}

				for (assignable_atom atom : rna_atom) {

					if (!atom.comp_id.equals(rna_comp_id))
						continue;

					if (!atom.alt_atom_id.equals("P") && atom.count < max_count * rna_frac_threshold) {

						exclw.write(String.format("    %-4s %-4s %3.2f\n", rna_comp_id, atom.alt_atom_id, (float) atom.count / (float) max_count));
						atom.minor = true;

					}

				}

			}

			exclw.write("    stop_\n");
			exclw.write("save_\n\n");

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}

		popular_rna_atom_by_comp_id = new assignable_atom[max_thrds][rna_seq_codes.values().length][];

		for (int thrd_id = 0; thrd_id < max_thrds; thrd_id++) {

			for (rna_seq_codes rna_seq_code : rna_seq_codes.values())
				popular_rna_atom_by_comp_id[thrd_id][rna_seq_code.ordinal()] = assignable_atom.popular_atoms(polymer_types.rna, rna_seq_code.name());

		}

		exclw.close();

		// backbone atoms

		aa_bb_atom = new ArrayList<backbone_atom>();

		backbone_atom.init_list(polymer_types.aa, aa_bb_atom);

		popular_aa_bb_atom_by_comp_id = new backbone_atom[max_thrds][aa_seq_codes.values().length][];

		for (int thrd_id = 0; thrd_id < max_thrds; thrd_id++) {

			for (aa_seq_codes aa_seq_code : aa_seq_codes.values())
				popular_aa_bb_atom_by_comp_id[thrd_id][aa_seq_code.ordinal()] = backbone_atom.popular_bb_atoms(polymer_types.aa, aa_seq_code.name());

		}

		dna_bb_atom = new ArrayList<backbone_atom>();

		backbone_atom.init_list(polymer_types.dna, dna_bb_atom);

		popular_dna_bb_atom_by_comp_id = new backbone_atom[max_thrds][dna_seq_codes.values().length][];

		for (int thrd_id = 0; thrd_id < max_thrds; thrd_id++) {

			for (dna_seq_codes dna_seq_code : dna_seq_codes.values())
				popular_dna_bb_atom_by_comp_id[thrd_id][dna_seq_code.ordinal()] = backbone_atom.popular_bb_atoms(polymer_types.dna, dna_seq_code.name());

		}

		rna_bb_atom = new ArrayList<backbone_atom>();

		backbone_atom.init_list(polymer_types.rna, rna_bb_atom);

		popular_rna_bb_atom_by_comp_id = new backbone_atom[max_thrds][rna_seq_codes.values().length][];

		for (int thrd_id = 0; thrd_id < max_thrds; thrd_id++) {

			for (rna_seq_codes rna_seq_code : rna_seq_codes.values())
				popular_rna_bb_atom_by_comp_id[thrd_id][rna_seq_code.ordinal()] = backbone_atom.popular_bb_atoms(polymer_types.rna, rna_seq_code.name());

		}

		// sidechain atoms

		aa_sc_atom = new ArrayList<sidechain_atom>();

		sidechain_atom.init_list(polymer_types.aa, aa_sc_atom);

		popular_aa_sc_atom_by_comp_id = new sidechain_atom[max_thrds][aa_seq_codes.values().length][];

		for (int thrd_id = 0; thrd_id < max_thrds; thrd_id++) {

			for (aa_seq_codes aa_seq_code : aa_seq_codes.values())
				popular_aa_sc_atom_by_comp_id[thrd_id][aa_seq_code.ordinal()] = sidechain_atom.popular_sc_atoms(polymer_types.aa, aa_seq_code.name());

		}

		dna_sc_atom = new ArrayList<sidechain_atom>();

		sidechain_atom.init_list(polymer_types.dna, dna_sc_atom);

		popular_dna_sc_atom_by_comp_id = new sidechain_atom[max_thrds][dna_seq_codes.values().length][];

		for (int thrd_id = 0; thrd_id < max_thrds; thrd_id++) {

			for (dna_seq_codes dna_seq_code : dna_seq_codes.values())
				popular_dna_sc_atom_by_comp_id[thrd_id][dna_seq_code.ordinal()] = sidechain_atom.popular_sc_atoms(polymer_types.dna, dna_seq_code.name());

		}

		rna_sc_atom = new ArrayList<sidechain_atom>();

		sidechain_atom.init_list(polymer_types.rna, rna_sc_atom);

		popular_rna_sc_atom_by_comp_id = new sidechain_atom[max_thrds][rna_seq_codes.values().length][];

		for (int thrd_id = 0; thrd_id < max_thrds; thrd_id++) {

			for (rna_seq_codes rna_seq_code : rna_seq_codes.values())
				popular_rna_sc_atom_by_comp_id[thrd_id][rna_seq_code.ordinal()] = sidechain_atom.popular_sc_atoms(polymer_types.rna, rna_seq_code.name());

		}

		// aromatic atoms

		aa_arom_atom = new ArrayList<aromatic_atom>();

		aromatic_atom.init_list(polymer_types.aa, aa_arom_atom);

		popular_aa_arom_atom_by_comp_id = new aromatic_atom[max_thrds][aa_seq_codes.values().length][];

		for (int thrd_id = 0; thrd_id < max_thrds; thrd_id++) {

			for (aa_seq_codes aa_seq_code : aa_seq_codes.values())
				popular_aa_arom_atom_by_comp_id[thrd_id][aa_seq_code.ordinal()] = aromatic_atom.popular_arom_atoms(polymer_types.aa, aa_seq_code.name());

		}

		dna_arom_atom = new ArrayList<aromatic_atom>();

		aromatic_atom.init_list(polymer_types.dna, dna_arom_atom);

		popular_dna_arom_atom_by_comp_id = new aromatic_atom[max_thrds][dna_seq_codes.values().length][];

		for (int thrd_id = 0; thrd_id < max_thrds; thrd_id++) {

			for (dna_seq_codes dna_seq_code : dna_seq_codes.values())
				popular_dna_arom_atom_by_comp_id[thrd_id][dna_seq_code.ordinal()] = aromatic_atom.popular_arom_atoms(polymer_types.dna, dna_seq_code.name());

		}

		rna_arom_atom = new ArrayList<aromatic_atom>();

		aromatic_atom.init_list(polymer_types.rna, rna_arom_atom);

		popular_rna_arom_atom_by_comp_id = new aromatic_atom[max_thrds][rna_seq_codes.values().length][];

		for (int thrd_id = 0; thrd_id < max_thrds; thrd_id++) {

			for (rna_seq_codes rna_seq_code : rna_seq_codes.values())
				popular_rna_arom_atom_by_comp_id[thrd_id][rna_seq_code.ordinal()] = aromatic_atom.popular_arom_atoms(polymer_types.rna, rna_seq_code.name());

		}

		// methyl atoms

		aa_methyl_atom = new ArrayList<methyl_atom>();

		methyl_atom.init_list(polymer_types.aa, aa_methyl_atom);

		popular_aa_methyl_atom_by_comp_id = new methyl_atom[max_thrds][aa_seq_codes.values().length][];

		for (int thrd_id = 0; thrd_id < max_thrds; thrd_id++) {

			for (aa_seq_codes aa_seq_code : aa_seq_codes.values())
				popular_aa_methyl_atom_by_comp_id[thrd_id][aa_seq_code.ordinal()] = methyl_atom.popular_methyl_atoms(polymer_types.aa, aa_seq_code.name());

		}

		dna_methyl_atom = new ArrayList<methyl_atom>();

		methyl_atom.init_list(polymer_types.dna, dna_methyl_atom);

		popular_dna_methyl_atom_by_comp_id = new methyl_atom[max_thrds][dna_seq_codes.values().length][];

		for (int thrd_id = 0; thrd_id < max_thrds; thrd_id++) {

			for (dna_seq_codes dna_seq_code : dna_seq_codes.values())
				popular_dna_methyl_atom_by_comp_id[thrd_id][dna_seq_code.ordinal()] = methyl_atom.popular_methyl_atoms(polymer_types.dna, dna_seq_code.name());

		}

		rna_methyl_atom = new ArrayList<methyl_atom>();

		methyl_atom.init_list(polymer_types.rna, rna_methyl_atom);

		popular_rna_methyl_atom_by_comp_id = new methyl_atom[max_thrds][rna_seq_codes.values().length][];

		for (int thrd_id = 0; thrd_id < max_thrds; thrd_id++) {

			for (rna_seq_codes rna_seq_code : rna_seq_codes.values())
				popular_rna_methyl_atom_by_comp_id[thrd_id][rna_seq_code.ordinal()] = methyl_atom.popular_methyl_atoms(polymer_types.rna, rna_seq_code.name());

		}

		// alternative atoms

		aa_alt_atom = new ArrayList<alternative_atom>();

		alternative_atom.init_list(polymer_types.aa, aa_alt_atom);

		dna_alt_atom = new ArrayList<alternative_atom>();

		alternative_atom.init_list(polymer_types.dna, dna_alt_atom);

		rna_alt_atom = new ArrayList<alternative_atom>();

		alternative_atom.init_list(polymer_types.rna, rna_alt_atom);

		// today

		DateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
		today = date_format.format(new Date());

		bmr_Util_Serv.open(str_dir_name, loc_dir_name);

		bmr_Util_Thrd[] util_thrd = new bmr_Util_Thrd[max_thrds];
		Thread[] thrd = new Thread[max_thrds];

		for (int thrd_id = 0; thrd_id < max_thrds; thrd_id++) {

			String thrd_name = "bmr_Util_Thrd-" + max_thrds + "-" + thrd_id;

			util_thrd[thrd_id] = new bmr_Util_Thrd(str_dir_name, loc_dir_name, thrd_id);
			thrd[thrd_id] = new Thread(util_thrd[thrd_id], thrd_name);

			thrd[thrd_id].start();

		}

		for (int thrd_id = 0; thrd_id < max_thrds; thrd_id++) {

			try {

				if (thrd[thrd_id] != null)
					thrd[thrd_id].join();

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		bmr_Util_Serv.close();

		if (loc_dir.isFile())
			loc_dir.delete();

		else if (loc_dir.isDirectory()) {

			File[] files = loc_dir.listFiles();

			for (int i = 0; i < files.length; i++)
				files[i].delete();
		}

		loc_dir.delete();

	}

	public static assignable_atom[] get_popular_atoms(polymer_types type, int thrd_id, String comp_id) {

		switch (type) {
		case aa:
			return popular_aa_atom_by_comp_id[thrd_id][aa_seq_codes.valueOf(comp_id).ordinal()];
		case dna:
			return popular_dna_atom_by_comp_id[thrd_id][dna_seq_codes.valueOf(comp_id).ordinal()];
		case rna:
			return popular_rna_atom_by_comp_id[thrd_id][rna_seq_codes.valueOf(comp_id).ordinal()];
		default:
			return null;
		}
	}

	public static backbone_atom[] get_popular_bb_atoms(polymer_types type, int thrd_id, String comp_id) {

		switch (type) {
		case aa:
			return popular_aa_bb_atom_by_comp_id[thrd_id][aa_seq_codes.valueOf(comp_id).ordinal()];
		case dna:
			return popular_dna_bb_atom_by_comp_id[thrd_id][dna_seq_codes.valueOf(comp_id).ordinal()];
		case rna:
			return popular_rna_bb_atom_by_comp_id[thrd_id][rna_seq_codes.valueOf(comp_id).ordinal()];
		default:
			return null;
		}
	}

	public static sidechain_atom[] get_popular_sc_atoms(polymer_types type, int thrd_id, String comp_id) {

		switch (type) {
		case aa:
			return popular_aa_sc_atom_by_comp_id[thrd_id][aa_seq_codes.valueOf(comp_id).ordinal()];
		case dna:
			return popular_dna_sc_atom_by_comp_id[thrd_id][dna_seq_codes.valueOf(comp_id).ordinal()];
		case rna:
			return popular_rna_sc_atom_by_comp_id[thrd_id][rna_seq_codes.valueOf(comp_id).ordinal()];
		default:
			return null;
		}
	}

	public static aromatic_atom[] get_popular_arom_atoms(polymer_types type, int thrd_id, String comp_id) {

		switch (type) {
		case aa:
			return popular_aa_arom_atom_by_comp_id[thrd_id][aa_seq_codes.valueOf(comp_id).ordinal()];
		case dna:
			return popular_dna_arom_atom_by_comp_id[thrd_id][dna_seq_codes.valueOf(comp_id).ordinal()];
		case rna:
			return popular_rna_arom_atom_by_comp_id[thrd_id][rna_seq_codes.valueOf(comp_id).ordinal()];
		default:
			return null;
		}
	}

	public static methyl_atom[] get_popular_methyl_atoms(polymer_types type, int thrd_id, String comp_id) {

		switch (type) {
		case aa:
			return popular_aa_methyl_atom_by_comp_id[thrd_id][aa_seq_codes.valueOf(comp_id).ordinal()];
		case dna:
			return popular_dna_methyl_atom_by_comp_id[thrd_id][dna_seq_codes.valueOf(comp_id).ordinal()];
		case rna:
			return popular_rna_methyl_atom_by_comp_id[thrd_id][rna_seq_codes.valueOf(comp_id).ordinal()];
		default:
			return null;
		}
	}

}
