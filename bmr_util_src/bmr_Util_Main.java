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
import java.nio.channels.FileChannel;

import java.util.Properties;
import java.net.*;
import javax.mail.*;
import javax.mail.internet.*;

public class bmr_Util_Main {

	public static final String user = System.getProperty("user.name");

	public static String url_bmrb = "jdbc:postgresql://localhost/bmrb";
	public static String user_bmrb = "";
	public static String pass_bmrb = "";

	public static String url_tax = "jdbc:postgresql://localhost/taxonomy";
	public static String user_tax = "";
	public static String pass_tax = "";

	public static String url_le = "jdbc:postgresql://localhost/ligand_expo";
	public static String user_le = "";
	public static String pass_le = "";

	public static String noatom_suffix = "";

	public static boolean write_xml = true;
	public static boolean remediate_xml = true;
	public static boolean validate_xml = true;

	public static boolean well_formed = false;

	public static boolean noatom = false;

	public static boolean init = true;

	private static final String version = "1.35.0";
	private static final String dev_mail_addr = "yokochi@protein.osaka-u.ac.jp";

	private static final String xsd_name = "mmcif_nmr-star.xsd";
	private static final String file_prefix = "bmr";

	private static String bmrbx_tool_home = ".";

	public static String xsd_dir_name = bmrbx_tool_home + "/schema/";
	public static String esum_dir_name = bmrbx_tool_home + "/pubmed.esum/";
	public static String ccd_dir_name = bmrbx_tool_home + "/pdb.ccd/";
	public static String cc_dir_name = bmrbx_tool_home + "/pdb.cc/";
	private static String xml_dir_name = bmrbx_tool_home + "/" + file_prefix + "_xml_doc/";
	private static String rel_dir_name = bmrbx_tool_home + "/" + file_prefix + "_xml_rel/";
	private static String log_dir_name = bmrbx_tool_home + "/" + file_prefix + "_xml_log/";
	private static String err_dir_name = bmrbx_tool_home + "/" + file_prefix + "_xml_err/";
	private static String loc_dir_name = bmrbx_tool_home + "/" + file_prefix + "_xml_loc/";

	private static String to_mail_addr = "bmrbsys@protein.osaka-u.ac.jp";
	private static String from_mail_addr = "webmaster@bmrbpub.protein.osaka-u.ac.jp";
	private static String smtp_host_name = "postman.protein.osaka-u.ac.jp";

	private static String smtp_ip_addr = "192.168.1.52";

	private static Runtime runtime = Runtime.getRuntime();
	private static final int cpu_num = runtime.availableProcessors();
	private static int max_thrds = cpu_num;

	public static void main(String[] args) {

		for (int i = 0; i < args.length; i++) {

			if (args[i].equals("--home")) {
				bmrbx_tool_home = args[++i];

				xsd_dir_name = bmrbx_tool_home + "/schema/";
				esum_dir_name = bmrbx_tool_home + "/pubmed.esum/";
				ccd_dir_name = bmrbx_tool_home + "/pdb.ccd/";
				cc_dir_name = bmrbx_tool_home + "/pdb.cc/";
				xml_dir_name = bmrbx_tool_home + "/" + file_prefix + "_xml_doc/";
				rel_dir_name = bmrbx_tool_home + "/" + file_prefix + "_xml_rel/";
				log_dir_name = bmrbx_tool_home + "/" + file_prefix + "_xml_log/";
				err_dir_name = bmrbx_tool_home + "/" + file_prefix + "_xml_err/";
				loc_dir_name = bmrbx_tool_home + "/" + file_prefix + "_xml_loc/";
			}

			else if (args[i].equals("--url-bmrb"))
				url_bmrb = args[++i];

			else if (args[i].equals("--user-bmrb"))
				user_bmrb = args[++i];

			else if (args[i].equals("--pass-bmrb"))
				pass_bmrb = args[++i];

			else if (args[i].equals("--url-tax"))
				url_tax = args[++i];

			else if (args[i].equals("--user-tax"))
				user_tax = args[++i];

			else if (args[i].equals("--pass-tax"))
				pass_tax = args[++i];

			else if (args[i].equals("--url-le"))
				url_le = args[++i];

			else if (args[i].equals("--user-le"))
				user_le = args[++i];

			else if (args[i].equals("--pass-le"))
				pass_le = args[++i];

			else if (args[i].equals("--mail-to"))
				to_mail_addr = args[++i];

			else if (args[i].equals("--mail-from"))
				from_mail_addr = args[++i];

			else if (args[i].equals("--smtp-host")) {
				smtp_host_name = args[++i];

				try {

					InetAddress inet_addr = InetAddress.getByName(smtp_host_name);
					smtp_ip_addr = inet_addr.toString().replaceFirst(smtp_host_name + "/", "");

				} catch (UnknownHostException e) {
					e.printStackTrace();
					System.exit(1);
				}

			}

			else if (args[i].equals("--max-thrds")) {
				max_thrds = Integer.valueOf(args[++i]);

				if (max_thrds <= 0 || max_thrds > cpu_num) {
					System.err.println("Out of range (max_thrds).");
					System.exit(1);
				}

			}

			else if (args[i].equals("--no-remediate"))
				remediate_xml = false;

			else if (args[i].equals("--no-validate")) {
				write_xml = true;
				validate_xml = false;
			}

			else if (args[i].equals("--validate")) {
				write_xml = true;
				validate_xml = true;
			}

			else if (args[i].equals("--validate-only")) {
				write_xml = false;
				validate_xml = true;
			}

			else if (args[i].equals("--well-formed"))
				well_formed = true;

			else if (args[i].equals("--noatom")) {
				noatom = true;
				noatom_suffix = "-noatom";

				xml_dir_name = bmrbx_tool_home + "/" + file_prefix + "_xml_noatom_doc/";
				rel_dir_name = bmrbx_tool_home + "/" + file_prefix + "_xml_noatom_rel/";
				log_dir_name = bmrbx_tool_home + "/" + file_prefix + "_xml_noatom_log/";
				err_dir_name = bmrbx_tool_home + "/" + file_prefix + "_xml_noatom_err/";
				loc_dir_name = bmrbx_tool_home + "/" + file_prefix + "_xml_noatom_loc/";
			}

			else if (args[i].equals("--atom")) {
				noatom = false;
				noatom_suffix = "";

				xml_dir_name = bmrbx_tool_home + "/" + file_prefix + "_xml_doc/";
				rel_dir_name = bmrbx_tool_home + "/" + file_prefix + "_xml_rel/";
				log_dir_name = bmrbx_tool_home + "/" + file_prefix + "_xml_log/";
				err_dir_name = bmrbx_tool_home + "/" + file_prefix + "_xml_err/";
				loc_dir_name = bmrbx_tool_home + "/" + file_prefix + "_xml_loc/";
			}

			else if (args[i].equals("--noinit"))
				init = false;

			else {
				System.out.println("Usage: --home DIR --url-bmrb BMRB --user-bmrb USER --pass-bmrb WORD --url-tax TAX --user-tax USER --pass-tax WORD --url-le LE --user-le USER --pass-le WORD --mail-to ADDR --mail-from ADDR --smtp-host NAME");
				System.out.println(" --home      DIR  : BMRBxTool home directory. (" + bmrbx_tool_home + ")");
				System.out.println(" --url-bmrb  BMRB : URL of BMRB database. (" + url_bmrb + ")");
				System.out.println(" --user-bmrb USER : Username of BMRB database.");
				System.out.println(" --pass-bmrb WORD : Password of BMRB database.");
				System.out.println(" --url-tax   TAX  : URL of Taxonomy database. (" + url_tax + ")");
				System.out.println(" --user-tax  USER : Username of Taxonomy database.");
				System.out.println(" --pass-tax  WORD : Password of Taxonomy database.");
				System.out.println(" --url-le    LE   : URL of Ligand Expo database. (" + url_le + ")");
				System.out.println(" --user-le   USER : Username of Ligand Expo database.");
				System.out.println(" --pass-le   WORD : Password of Ligand Expo database.");
				System.out.println(" --mail-to   ADDR : Mail address of administrator. (" + to_mail_addr + ")");
				System.out.println(" --mail-from ADDR : Mail address of sender. (" + from_mail_addr + ")");
				System.out.println(" --smtp-host NAME : SMTP host name. (" + smtp_host_name + ")");
				System.out.println(" --max-thrds PROC : Number of threads. (default is number of processores)");
				System.out.println(" --no-remediate   : Turn off programmed remediation.");
				System.out.println(" --no-validate    : Write XML files without XML Schema validation.");
				System.out.println(" --validate       : Write XML files and Validate them against XML Schema. (default)");
				System.out.println(" --validate-only  : Validate XML files against XML Schema.");
				System.out.println(" --well-formed    : Check only if XML files are well-formed. (Option)");
				System.out.println(" --noatom         : Omit atomic coordinates, restraints and peak lists.");
				System.out.println(" --noinit         : Don't refresh log and lock directories on startup.");

				System.exit(1);
			}

		}

		File xml_dir = new File(xml_dir_name);

		if (xml_dir.exists()) {

			if (xml_dir.isFile())
				xml_dir.delete();

		}

		if (!xml_dir.isDirectory()) {

			if (!xml_dir.mkdir()) {
				System.err.println("Couldn't create directory '" + xml_dir_name + "'.");
				System.exit(1);
			}

		}

		File rel_dir = new File(rel_dir_name);

		if (rel_dir.exists()) {

			if (rel_dir.isFile())
				rel_dir.delete();

		}

		if (!rel_dir.isDirectory()) {

			if (!rel_dir.mkdir()) {
				System.err.println("Couldn't create directory '" + rel_dir_name + "'.");
				System.exit(1);
			}

		}

		File log_dir = new File(log_dir_name);

		if (log_dir.exists()) {

			if (log_dir.isFile())
				log_dir.delete();

		}

		if (!log_dir.isDirectory()) {

			if (!log_dir.mkdir()) {
				System.err.println("Couldn't create directory '" + log_dir_name + "'.");
				System.exit(1);
			}

		}

		File err_dir = new File(err_dir_name);

		if (err_dir.exists()) {

			if (err_dir.isFile())
				err_dir.delete();

			else if (err_dir.isDirectory() && init) {

				File[] files = err_dir.listFiles();

				for (int i = 0; i < files.length; i++)
					files[i].delete();
			}

		}

		if (!err_dir.isDirectory()) {

			if (!err_dir.mkdir()) {
				System.err.println("Couldn't create directory '" + err_dir_name + "'.");
				System.exit(1);
			}

		}

		if (init) {

			try {

				copyTransfer(xsd_dir_name + xsd_name, xml_dir_name + xsd_name);

			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

		}

		File loc_dir = new File(loc_dir_name);

		if (loc_dir.exists()) {

			if (loc_dir.isFile())
				loc_dir.delete();

			else if (loc_dir.isDirectory() && init) {

				File[] files = loc_dir.listFiles();

				for (int i = 0; i < files.length; i++)
					files[i].delete();
			}

		}

		if (!loc_dir.isDirectory()) {

			if (!loc_dir.mkdir()) {
				System.err.println("Couldn't create directory '" + loc_dir_name + "'.");
				System.exit(1);
			}

		}

		File esum_dir = new File(esum_dir_name);

		if (!esum_dir.isDirectory()) {

			if (!esum_dir.mkdir()) {
				System.err.println("Couldn't create directory '" + esum_dir_name + "'.");
				System.exit(1);
			}

		}

		else if (init) {

			File[] files = esum_dir.listFiles();

			for (int i = 0; i < files.length; i++) {
				if (files[i].getName().endsWith("~"))
					files[i].delete();
			}
		}

		File ccd_dir = new File(ccd_dir_name);

		if (!ccd_dir.isDirectory()) {

			if (!ccd_dir.mkdir()) {
				System.err.println("Couldn't create directory '" + ccd_dir_name + "'.");
				System.exit(1);
			}

		}

		else if (init) {

			File[] files = ccd_dir.listFiles();

			for (int i = 0; i < files.length; i++) {
				if (files[i].getName().endsWith("~"))
					files[i].delete();
			}
		}

		File cc_dir = new File(cc_dir_name);

		if (!cc_dir.isDirectory()) {

			if (!cc_dir.mkdir()) {
				System.err.println("Couldn't create directory '" + cc_dir_name + "'.");
				System.exit(1);
			}

		}

		else if (init) {

			File[] files = cc_dir.listFiles();

			for (int i = 0; i < files.length; i++) {
				if (files[i].getName().endsWith("~"))
					files[i].delete();
			}
		}

		Object lock_obj = new Object();

		bmr_Util_Serv.open(rel_dir_name, loc_dir_name, file_prefix);

		bmr_Util_Thrd[] util_thrd = new bmr_Util_Thrd[max_thrds];
		Thread[] thrd = new Thread[max_thrds];

		for (int thrd_id = 0; thrd_id < max_thrds; thrd_id++) {

			String thrd_name = "bmr_Util_Thrd-" + max_thrds + "-" + thrd_id;

			util_thrd[thrd_id] = new bmr_Util_Thrd(lock_obj, xml_dir_name, rel_dir_name, log_dir_name, err_dir_name, loc_dir_name, file_prefix);
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

		lock_obj = null;

		if (init)
			sendStatus();

		if (validate_xml && init) {

			if (loc_dir.isFile())
				loc_dir.delete();

			else if (loc_dir.isDirectory()) {

				File[] files = loc_dir.listFiles();

				for (int i = 0; i < files.length; i++)
					files[i].delete();
			}

			loc_dir.delete();

		}

	}

	public static void copyTransfer(String src_path, String dst_path) throws IOException {

		FileInputStream src_in = new FileInputStream(src_path);
		FileOutputStream dst_out = new FileOutputStream(dst_path);

		FileChannel src_ch = src_in.getChannel();
		FileChannel dst_ch = dst_out.getChannel();

		try {

			src_ch.transferTo(0, src_ch.size(), dst_ch);

		} finally {

			if (src_ch != null)
				src_ch.close();

			if (dst_ch != null)
				dst_ch.close();

			if (src_in != null)
				src_in.close();

			if (dst_out != null)
				dst_out.close();

		}
	}

	public static void sendStatus() {

		Properties props = new Properties();

		props.put("mail.smtp.host", smtp_ip_addr);

		Session session = Session.getDefaultInstance(props, null);
		Message msg = new MimeMessage(session);

		try {

			InternetAddress from_addr = new InternetAddress(from_mail_addr);
			msg.setFrom(from_addr);

			InternetAddress[] to_addr = { new InternetAddress(to_mail_addr), new InternetAddress(dev_mail_addr) };
			msg.setRecipients(Message.RecipientType.TO, to_addr);

			String host_name = null;
			String host_addr = null;

			try {

				host_name = InetAddress.getLocalHost().getHostName();
				host_addr = InetAddress.getLocalHost().getHostAddress();

			} catch (UnknownHostException e) {
				//e.printStackTrace();
			}

			msg.setSubject("Error message from bmrbx-tool (" + file_prefix + noatom_suffix + ") on " + host_name);

			StringBuilder content = new StringBuilder();

			content.append(String.format("bmrbx-tool (%s%s) version: %s\n", file_prefix, noatom_suffix, version));
			content.append(String.format("host_name: %s\n", host_name));
			content.append(String.format("host_addr: %s\n\n", host_addr));

			File err_dir = new File(err_dir_name);

			if (err_dir.exists() && err_dir.isDirectory()) {

				File[] files = err_dir.listFiles();

				if (files.length == 0) {
					if (validate_xml)
						System.out.println("XML files (prefix:" + file_prefix + noatom_suffix + ") are update.");
					return;
				}

				boolean success = true;

				for (int i = 0; i < files.length; i++) {

					if (files[i].length() == 0)
						continue;

					success = false;

					content.append(String.format("File: %s\n", files[i].getName().replace("_err", "").concat(".xml")));

					try {

						FileReader filer = new FileReader(err_dir_name + files[i].getName());
						BufferedReader bufferr = new BufferedReader(filer);

						String line = null;

						while ((line = bufferr.readLine()) != null)
							content.append(line + "\n");

						bufferr.close();
						filer.close();

						content.append("\n\n");

					} catch (IOException e) {
						e.printStackTrace();
					}

					if (content.length() > 10000)
						break;

				}

				if (success) {
					if (validate_xml)
						System.out.println("XML files (prefix:" + file_prefix + noatom_suffix + ") are update.");
					return;
				}

			}

			if (content.length() > 10000) {

				content.setLength(0);

				content.append(String.format("%sx-tool version: %s\n", file_prefix, version));
				content.append(String.format("host_name: %s\n", host_name));
				content.append(String.format("host_addr: %s\n\n", host_addr));

				File[] files = err_dir.listFiles();

				for (int i = 0; i < files.length; i++) {

					if (files[i].length() == 0)
						continue;

					content.append(String.format("File: %s\n", files[i].getName().replace("_err", "").concat(".xml")));

				}

				content.append(String.format("\n\nFound validation errors. Please check log files for more details.\n", host_addr));

			}

			msg.setText(content.toString());

			Transport.send(msg);

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
}
