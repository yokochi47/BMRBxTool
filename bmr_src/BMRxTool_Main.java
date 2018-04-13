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
import java.net.*;
import java.sql.*;
import java.util.logging.*;
import javax.xml.parsers.*;

import org.w3c.dom.Document;

public class BMRxTool_Main {

	public final static String version = "1.27.0";
	public final static String bmrb_version = "3.2.1.2";
	public final static String dev_mail_addr = "yokochi@protein.osaka-u.ac.jp";

	public static String your_org = "your.org"; // edit this before you run

	public static String to_mail_addr = "sysadmin@" + your_org; // edit this before you run
	public static String from_mail_addr = "webmaster@." + your_org; // edit this before you run
	public static String smtp_host_name = "smtp_host_name." + your_org; // edit this before you run
	public static String smtp_ip_addr = null;

	public static void main(String[] args) {

		Connection conn_bmrb = null;
		Connection conn_tax = null;
		Connection conn_le = null;

		final String user = System.getProperty("user.name");
		final String user_home = System.getProperty("user.home");

		final String prefix = "BMRBx";
		final String xsd_name = "mmcif_nmr-star.xsd";
		final String namespace_uri = "http://bmrbpub.protein.osaka-u.ac.jp/schema/" + xsd_name;

		String bmrbx_tool_home = user_home + "/Applications/bmrbx-tool-" + version; // edit this before you run

		String default_user = "bmrb";

		String url_bmrb = "jdbc:postgresql://localhost/bmrb";
		String user_bmrb = default_user;
		String password_bmrb = "";

		String url_tax = "jdbc:postgresql://localhost/taxonomy";
		String user_tax = default_user;
		String password_tax = "";

		String url_le = "jdbc:postgresql://localhost/ligand_expo";
		String user_le = default_user;
		String password_le = "";

		for (int i = 0; i < args.length; i++) {

			if (args[i].equals("--home"))
				bmrbx_tool_home = args[++i];

			else if (args[i].equals("--url-bmrb"))
				url_bmrb = args[++i];

			else if (args[i].equals("--user-bmrb"))
				user_bmrb = args[++i];

			else if (args[i].equals("--pass-bmrb"))
				password_bmrb = args[++i];

			else if (args[i].equals("--url-tax"))
				url_tax = args[++i];

			else if (args[i].equals("--user-tax"))
				user_tax = args[++i];

			else if (args[i].equals("--pass-tax"))
				password_tax = args[++i];

			else if (args[i].equals("--url-le"))
				url_le = args[++i];

			else if (args[i].equals("--user-le"))
				user_le = args[++i];

			else if (args[i].equals("--pass-le"))
				password_le = args[++i];

			else if (args[i].equals("--mail-to"))
				to_mail_addr = args[++i];

			else if (args[i].equals("--mail-from"))
				from_mail_addr = args[++i];

			else if (args[i].equals("--smtp-host"))
				smtp_host_name = args[++i];

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

				System.exit(1);
			}

		}

		final String xsd_dir_name = bmrbx_tool_home + "/schema/"; // schema directory which includes schema file
		final String xsd_file_name = xsd_dir_name + xsd_name; // schema file

		final String file_prefix = "bmr";
		final String src_dir_name = bmrbx_tool_home + "/" + file_prefix + "_util_src/"; // source file directory

		File xsd_file = new File(xsd_file_name);

		if (!xsd_file.exists()) {

			System.err.println(xsd_file.getPath() + " not found.");
			System.exit(1);

		}

		// resolve host name.

		try {

			InetAddress inet_addr = InetAddress.getByName(smtp_host_name);
			smtp_ip_addr = inet_addr.toString().replaceFirst(smtp_host_name + "/", "");

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		// make source directory if it doesn't exist.

		File src_dir = new File(src_dir_name);

		if (!src_dir.isDirectory()) {

			if (!src_dir.mkdir()) {
				System.err.println("Couldn't create directory '" + src_dir_name + "'.");
				System.exit(1);
			}

		}

		try {

			conn_bmrb = DriverManager.getConnection(url_bmrb, user_bmrb.isEmpty() ? user : user_bmrb, password_bmrb); // bmrb is ready.
			conn_tax = DriverManager.getConnection(url_tax, user_tax.isEmpty() ? user : user_tax, password_tax); // taxonomy is ready.
			conn_le = DriverManager.getConnection(url_le, user_le.isEmpty() ? user : user_le, password_le); // liagnd expo is ready.

			try {

				// parse schema file using DOM.

				DocumentBuilderFactory doc_builder_fac = DocumentBuilderFactory.newInstance();
				DocumentBuilder doc_builder = doc_builder_fac.newDocumentBuilder();
				Document xsd_doc = doc_builder.parse(xsd_file);

				// link schema and database.

				// BMRxTool_DOM.reveal(xsd_doc);

				BMRxTool_DOM.link_db(xsd_doc, conn_bmrb, password_bmrb, conn_tax, password_tax, conn_le, password_le, prefix, xsd_dir_name, src_dir_name, namespace_uri, xsd_name, file_prefix);

				System.out.println("Generated source files are located in " + src_dir_name + ".");

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(BMRxTool_Main.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		} finally {

			try {

				if (conn_bmrb != null)
					conn_bmrb.close();

				if (conn_tax != null)
					conn_tax.close();

				if (conn_le != null)
					conn_le.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(BMRxTool_Main.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}
	}
}
