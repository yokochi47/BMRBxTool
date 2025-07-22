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

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.logging.*;

import javax.xml.namespace.QName;

import org.apache.xmlbeans.XmlOptions;

public class bmr_Util_Thrd implements Runnable {

	private Object _lock_obj = null;

	private String _xml_dir_name = null;
	private String _rel_dir_name = null;
	private String _log_dir_name = null;
	private String _err_dir_name = null;
	private String _loc_dir_name = null;

	private Connection conn_bmrb = null;
	private Connection conn_clone = null;
	private Connection conn_tax = null;
	private Connection conn_le = null;

	private bmr_Util_Valid validator = null;

	private XmlOptions xml_opt = null;

	private static String _file_prefix;

	private static final String url_bmrb = bmr_Util_Main.url_bmrb;
	private static final String user_bmrb = bmr_Util_Main.user_bmrb.isEmpty() ? bmr_Util_Main.user : bmr_Util_Main.user_bmrb;
	private static final String pass_bmrb = bmr_Util_Main.pass_bmrb;

	private static final String url_tax = bmr_Util_Main.url_tax;
	private static final String user_tax = bmr_Util_Main.user_tax.isEmpty() ? bmr_Util_Main.user : bmr_Util_Main.user_tax;
	private static final String pass_tax = bmr_Util_Main.pass_tax;

	private static final String url_le = bmr_Util_Main.url_le;
	private static final String user_le = bmr_Util_Main.user_le.isEmpty() ? bmr_Util_Main.user : bmr_Util_Main.user_le;
	private static final String pass_le = bmr_Util_Main.pass_le;

	private static final String namespace_uri = "http://bmrbpub.pdbj.org/schema/mmcif_nmr-star.xsd";
	private static final String prefix = "BMRBx";

	public bmr_Util_Thrd(Object lock_obj, String xml_dir_name, String rel_dir_name, String log_dir_name, String err_dir_name, String loc_dir_name, String file_prefix) {

		_lock_obj = lock_obj;

		_xml_dir_name = xml_dir_name;
		_rel_dir_name = rel_dir_name;
		_log_dir_name = log_dir_name;
		_err_dir_name = err_dir_name;
		_loc_dir_name = loc_dir_name;
		_file_prefix = file_prefix;

		validator = new bmr_Util_Valid(xml_dir_name);

		xml_opt = new XmlOptions();

		xml_opt.setSaveUseOpenFrag();
		xml_opt.setSavePrettyPrint();
		xml_opt.setSavePrettyPrintOffset(2);

		try {
			xml_opt.setBaseURI(new URI(namespace_uri));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		xml_opt.setSaveAggressiveNamespaces();
		xml_opt.setSaveSyntheticDocumentElement(new QName(namespace_uri, "", prefix));

	}

	@Override
	public void run() {

		try {
			conn_clone = DriverManager.getConnection(url_bmrb + "_clone", user_bmrb, pass_bmrb);
		} catch (SQLException e) {}

		try {

			conn_bmrb = DriverManager.getConnection(url_bmrb, user_bmrb, pass_bmrb);
			conn_tax = DriverManager.getConnection(url_tax, user_tax, pass_tax);
			conn_le = DriverManager.getConnection(url_le, user_le, pass_le);

			bmr_Util_EntityExperimentalSrc ee = new bmr_Util_EntityExperimentalSrc();
			bmr_Util_EntityNaturalSrc en = new bmr_Util_EntityNaturalSrc();
			bmr_Util_StructClassification sc = new bmr_Util_StructClassification();

			bmr_Util_Entry ent = new bmr_Util_Entry();

			while (true) {

				String entry_id = bmr_Util_Serv.get_entry_id();

				if (entry_id == null)
					break;

				bmr_Util_XML.write(_lock_obj, conn_bmrb, conn_clone, conn_tax, conn_le, ee, en, sc, ent, _xml_dir_name, _rel_dir_name, _log_dir_name, _err_dir_name, _loc_dir_name, _file_prefix, entry_id, validator, xml_opt);

			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_Util_Thrd.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (conn_bmrb != null)
					conn_bmrb.close();

				if (conn_clone != null)
					conn_clone.close();

				if (conn_tax != null)
					conn_tax.close();

				if (conn_le != null)
					conn_le.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_Util_Thrd.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}
	}
}
