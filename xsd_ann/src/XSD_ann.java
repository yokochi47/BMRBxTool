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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.*;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

public class XSD_ann {

	public final static String dictionary_url = "http://svn.bmrb.wisc.edu/svn/nmr-star-dictionary/bmrb_only_files/adit_input/NMR-STAR.dic";
	public final static String alt_dictionary_url = "http://bmrbpub.protein.osaka-u.ac.jp/schema/mmcif_nmr-star.dic";

	public final static String category_api = "http://www.bmrb.wisc.edu/dictionary/tag.php?tagcat=";
	public final static String alt_category_api = "http://%s/dictionary/tag.php?tagcat="; 
	public final static String tag_api = "http://www.bmrb.wisc.edu/dictionary/tagdetail.php?tag=_";
	public final static String alt_tag_api = "http://%s/dictionary/tagdetail.php?tag=_";

	public static String dic_version = "3.2.1.2";
	public static String url_mirror = "bmrb.pdbj.org";
	public static List<String> table_list;

	static XSD_zero_occurs zero_occurs = new XSD_zero_occurs();

	public static void main(String[] args) {

		Connection conn_bmrb = null;

		final String user = System.getProperty("user.name");

		String xsd_name = "mmcif_nmr-star-v" + dic_version + ".xsd";

		String bmrbx_tool_home = "/home/yokochi/Applications/bmrbx-tool-1.27.0"; // ".";

		String url_bmrb = "jdbc:postgresql://localhost/bmrb";
		String user_bmrb = "bmrb"; // user;
		String password_bmrb = "";

		for (int i = 0; i < args.length; i++) {

			if (args[i].equals("--home"))
				bmrbx_tool_home = args[++i];

			else if (args[i].equals("--url-bmrb"))
				url_bmrb = args[++i];

			else if (args[i].equals("--user-bmrb"))
				user_bmrb = args[++i];

			else if (args[i].equals("--pass-bmrb"))
				password_bmrb = args[++i];

			else if (args[i].equals("--url-mirror"))
				url_mirror = args[++i];

			else if (args[i].equals("--dic-ver")) {
				dic_version = args[++i];
				xsd_name = "mmcif_nmr-star-v" + dic_version + ".xsd";
			}

			else {
				System.out.println("Usage: --home DIR --url-bmrb BMRB --user-bmrb USER --pass-bmrb WORD");
				System.out.println(" --home      DIR  : BMRBxTool home directory. (" + bmrbx_tool_home + ")");
				System.out.println(" --url-bmrb  BMRB : URL of BMRB database. (" + url_bmrb + ")");
				System.out.println(" --user-bmrb USER : Username of BMRB database.");
				System.out.println(" --pass-bmrb WORD : Password of BMRB database.");
				System.out.println(" --url-mirror URL : URL of BMRB mirror site. (" + url_mirror + ")");
				System.out.println(" --dic-ver    VER : Version of NMR-STAR Dictionary. (" + dic_version + ")");

				System.exit(1);
			}

		}

		String xsd_dir_name = bmrbx_tool_home + "/schema/"; // schema directory which includes schema file
		String xsd_file_name = xsd_dir_name + xsd_name; // schema file

		File xsd_file = new File(xsd_file_name);

		if (!xsd_file.exists()) {

			xsd_dir_name = bmrbx_tool_home + "/dic2xsd/"; // for BMRBoTool
			xsd_file_name = xsd_dir_name + xsd_name; // schema file

			xsd_file = new File(xsd_file_name);

			if (!xsd_file.exists()) {

				System.err.println(xsd_file.getPath() + " not found.");
				System.exit(1);

			}

		}

		try {

			// parse XSD document

			DocumentBuilderFactory doc_builder_fac = DocumentBuilderFactory.newInstance();

			doc_builder_fac.setValidating(false);
			doc_builder_fac.setNamespaceAware(true);
			doc_builder_fac.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
			doc_builder_fac.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

			DocumentBuilder doc_builder = doc_builder_fac.newDocumentBuilder();

			Document xsd_doc = doc_builder.parse(xsd_file);

			// connect to bmrb database

			conn_bmrb = DriverManager.getConnection(url_bmrb, user_bmrb.isEmpty() ? user : user_bmrb, password_bmrb); // bmrb is ready.

			table_list = new ArrayList<String>();

			// get all table names from BMRB.

			DatabaseMetaData meta = conn_bmrb.getMetaData();
			ResultSet rset = meta.getTables(null, null, null, null);

			while (rset.next())
				table_list.add(rset.getString("TABLE_NAME"));

			rset.close();

			// XSD document annotation

			xsd_anne(xsd_doc, conn_bmrb);

			// write the content into xml file

			DOMImplementation domImpl = xsd_doc.getImplementation();
			DOMImplementationLS domImplLS = (DOMImplementationLS) domImpl.getFeature("LS", "3.0");

			LSOutput output = domImplLS.createLSOutput();
			output.setByteStream(new FileOutputStream(xsd_dir_name + xsd_name));

			LSSerializer serializer = domImplLS.createLSSerializer();
			serializer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
			serializer.write(xsd_doc, output);
			/*	
			OutputFormat format = new OutputFormat(xsd_doc);
			format.setIndenting(true);
			format.setIndent(2);

			FileWriter writer = new FileWriter(new File(xsd_dir_name + xsd_name));
			XMLSerializer serializer = new XMLSerializer(writer, format);

			serializer.serialize(xsd_doc);
			 */
			/*
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

			DOMSource source = new DOMSource(xsd_doc);
			StreamResult result = new StreamResult(new File(xsd_dir_name + xsd_name));

			// Output to console for testing
			//			StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);
			 */
			if (conn_bmrb != null)
				conn_bmrb.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

	private static void xsd_anne(Document doc, Connection conn) {

		Element root_node = doc.getDocumentElement();

		if (!root_node.getNodeName().endsWith(":schema")) {
			System.err.println("Not found xsd:schema root element in a document.");
			return;
		}

		if (table_list == null)
			return;

		boolean has_root_ann = false;

		for (Node child = root_node.getFirstChild(); child != null; child = child.getNextSibling()) {

			if (child.getNodeName().endsWith(":annotation")) {

				has_root_ann = true;

				break;
			}

		}

		if (!has_root_ann) {

			Element root_ann = doc.createElement("xsd:annotation");

			Element root_ann_app = doc.createElement("xsd:appinfo");
			root_ann_app.setTextContent("BMRB/XML Schema v" + dic_version);

			Element root_ann_doc = doc.createElement("xsd:documentation");
			root_ann_doc.setAttribute("source", alt_dictionary_url);
			root_ann_doc.setAttribute("xml:lang", "en");
			root_ann_doc.setTextContent("\n\nBMRB/XML Schema translated from extended version of NMR-STAR Dictionary v" + dic_version + ", which is backward compatible with the original NMR-STAR Dictionary:\n " + dictionary_url + "\n\n    ");

			root_ann.appendChild(root_ann_app);
			root_ann.appendChild(root_ann_doc);
			root_node.insertBefore(root_ann, root_node.getFirstChild());

		}

		for (Node child = root_node.getFirstChild(); child != null; child = child.getNextSibling()) {

			if (child.getNodeName().endsWith(":complexType")) {

				extract_admin_element_for_annotation(child, conn);
				extract_admin_element_for_zero_occurs(child, conn);

			}

		}

	}

	private static void extract_admin_element_for_annotation(Node node, Connection conn) {

		Element e = (Element) node;

		String category = e.getAttribute("name");

		if (category == null || category.isEmpty())
			return;

		if (category.equalsIgnoreCase("chem_shift_completeness_charType"))
			return;

		if (category.equalsIgnoreCase("chem_shift_completeness_listType"))
			return;

		if (category.equalsIgnoreCase("lacs_charType"))
			return;

		if (category.equalsIgnoreCase("lacs_plotType"))
			return;

		if (category.equalsIgnoreCase("pb_charType"))
			return;

		if (category.equalsIgnoreCase("pb_listType"))
			return;

		category = category.replaceFirst("Type$", "");

		String Category = null;

		boolean match = false;

		for (String table : table_list) {

			if (table.equalsIgnoreCase(category)) {

				Category = table.substring(0, 1).toUpperCase() + table.substring(1);
				match = true;

				break;
			}

		}

		if (!match)
			return;

		add_category_annotation(node, conn, Category);

	}

	private static void add_category_annotation(Node node, Connection conn, String Category) {

		List<String> column_list = new ArrayList<String>();

		// get column names from table

		try {

			DatabaseMetaData meta = conn.getMetaData();
			ResultSet rset = meta.getColumns(null, null, Category, null);

			while (rset.next())
				column_list.add(rset.getString("COLUMN_NAME"));

			rset.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (Node anno = node.getFirstChild(); anno != null; anno = anno.getNextSibling()) {

			if (anno.getNodeName().endsWith(":annotation")) {

				for (Node doc = anno.getFirstChild(); doc != null; doc = doc.getNextSibling()) {

					if (!doc.getNodeName().endsWith(":documentation"))
						continue;

					Element e = (Element) doc;

					String src = e.getAttribute("source");

					if (src != null && !src.isEmpty())
						continue;

					String dic_url = category_api + Category;
					String alt_url = String.format(alt_category_api, url_mirror) + Category;

					if (has_annotation(alt_url)) {

						e.setAttribute("source", dic_url);
						System.out.println(Category + ": source=" + dic_url);

					}

				}

			}

			else
				add_tag_annotation(anno, Category, column_list);

		}

	}

	private static void add_tag_annotation(Node node, String Category, List<String> column_list) {

		for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {

			if (child.getNodeType() == Node.ELEMENT_NODE && (child.getNodeName().endsWith(":element") || child.getNodeName().endsWith("xsd:attribute"))) {

				Element elem = (Element) child;

				String item = elem.getAttribute("name");

				if (item.equalsIgnoreCase(Category)) {

					add_tag_annotation(child, Category, column_list);

					continue;
				}

				String Item = null;

				boolean match = false;

				for (String column : column_list) {

					if (column.equalsIgnoreCase(item)) {

						Item = column.substring(0, 1).toUpperCase() + column.substring(1);
						match = true;

						break;
					}

				}

				if (!match || item.equalsIgnoreCase("pdbx_name") || item.equalsIgnoreCase("sunid")) {

					add_tag_annotation(child, Category, column_list);

					continue;
				}

				for (Node anno = child.getFirstChild(); anno != null; anno = anno.getNextSibling()) {

					if (anno.getNodeName().endsWith(":annotation")) {

						for (Node doc = anno.getFirstChild(); doc != null; doc = doc.getNextSibling()) {

							if (!doc.getNodeName().endsWith(":documentation"))
								continue;

							Element e = (Element) doc;

							String src = e.getAttribute("source");

							if (src != null && !src.isEmpty())
								continue;

							String dic_url = tag_api + Category + "." + Item;
							String alt_url = String.format(alt_tag_api, url_mirror) + Category + "." + Item;

							if (has_annotation(alt_url)) {

								e.setAttribute("source", dic_url);
								System.out.println(Category + "." + Item + ": source=" + dic_url);

							}

						}

					}

				}

			}

			else
				add_tag_annotation(child, Category, column_list);

		}

	}

	@SuppressWarnings("unused")
	private static boolean file_exist(String url) {

		HttpURLConnection.setFollowRedirects(false);

		try {

			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setRequestMethod("HEAD");

			return (conn.getResponseCode() == HttpURLConnection.HTTP_OK);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	private static boolean has_annotation(String url) {

		try {

			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

			conn.setRequestMethod("GET");

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK)
				return false;

			BufferedReader bufferr = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = null;

			while ((line = bufferr.readLine()) != null) {

				if (line.contains("not found")) {

					bufferr.close();

					return false;
				}

			}

			bufferr.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	private static void extract_admin_element_for_zero_occurs(Node node, Connection conn) {

		Element e = (Element) node;

		String category = e.getAttribute("name");

		if (category == null || category.isEmpty())
			return;

		category = category.replaceFirst("Type$", "");

		String Category = null;

		boolean match = false;

		for (String table : table_list) {

			if (table.equalsIgnoreCase(category)) {

				Category = table.substring(0, 1).toUpperCase() + table.substring(1);
				match = true;

				break;
			}

		}

		if (!match)
			return;

		extract_category_for_zero_occurs(node, conn, Category);

	}

	private static void extract_category_for_zero_occurs(Node node, Connection conn, String Category) {

		List<String> column_list = new ArrayList<String>();

		// get column names from table

		try {

			DatabaseMetaData meta = conn.getMetaData();
			ResultSet rset = meta.getColumns(null, null, Category, null);

			while (rset.next())
				column_list.add(rset.getString("COLUMN_NAME"));

			rset.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (Node anno = node.getFirstChild(); anno != null; anno = anno.getNextSibling()) {

			if (!anno.getNodeName().endsWith(":annotation")) 
				add_tag_zero_occurs(anno, Category, column_list);

		}

	}

	private static void add_tag_zero_occurs(Node node, String Category, List<String> column_list) {

		for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {

			if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().endsWith(":element")) {

				Element elem = (Element) child;

				String item = elem.getAttribute("name");

				if (item.equalsIgnoreCase(Category)) {

					add_tag_zero_occurs(child, Category, column_list);

					continue;
				}

				String Item = null;

				boolean match = false;

				for (String column : column_list) {

					if (column.equalsIgnoreCase(item)) {

						Item = column.substring(0, 1).toUpperCase() + column.substring(1);
						match = true;

						break;
					}

				}

				if (!match || !zero_occurs.check(Category.toLowerCase() + "." + item)) {

					add_tag_zero_occurs(child, Category, column_list);

					continue;
				}

				String minoccurs = elem.getAttribute("minOccurs");

				if (minoccurs != null && minoccurs.equals("0")) {

					add_tag_zero_occurs(child, Category, column_list);

					continue;
				}

				elem.setAttribute("minOccurs", "0");
				System.out.println(Category + "." + Item + ": minOcuurs=0");

			}

			else
				add_tag_zero_occurs(child, Category, column_list);

		}

	}

}
