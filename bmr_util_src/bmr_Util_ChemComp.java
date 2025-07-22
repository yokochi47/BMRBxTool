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
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.*;
import javax.xml.parsers.*;

//import org.apache.xml.serialize.*;
import org.w3c.dom.*;
import org.w3c.dom.ls.*;
import org.xml.sax.*;

public class bmr_Util_ChemComp {

	static final Map<String, String> map_type = new HashMap<String, String>() {

		private static final long serialVersionUID = 13L;

		{

			put("fragment", "OTHER");
			put("OTHER", "OTHER");
			put("PEPTIDE LINKING", "PEPTIDE LINKING");
			put("L-GAMMA-PEPTIDE, C-DELTA LINKING", "L-GAMMA-PEPTIDE, C-DELTA LINKING");
			put("L-BETA-PEPTIDE, C-GAMMA LINKING", "L-BETA-PEPTIDE, C-GAMMA LINKING");
			put("L-DNA LINKING", "L-DNA LINKING");
			put("DNA linking", "DNA LINKING");
			put("saccharide", "SACCHARIDE");
			put("D-SACCHARIDE", "D-SACCHARIDE");
			put("L-PEPTIDE LINKING", "L-PEPTIDE LINKING");
			put("D-peptide linking", "D-PEPTIDE LINKING");
			put("complete", "OTHER");
			put("RNA linking", "RNA LINKING");
			put("PEPTIDE-LIKE", "PEPTIDE-LIKE");
			put("L-RNA LINKING", "L-RNA LINKING");
			put("non-polymer", "NON-POLYMER");
			put("D-SACCHARIDE 1,4 AND 1,6 LINKING", "D-SACCHARIDE 1,4 AND 1,6 LINKING");
			put("L-PEPTIDE NH3 AMINO TERMINUS", "L-PEPTIDE NH3 AMINO TERMINUS");
			put("D-sacchride", "D-SACCHARIDE");
			put("D-BETA-PEPTIDE, C-GAMMA LINKING", "D-BETA-PEPTIDE, C-GAMMA LINKING");
			put("D-saccharide", "D-SACCHARIDE");
			put("D-SACCHARIDE 1,4 AND 1,4 LINKING", "D-SACCHARIDE 1,4 AND 1,4 LINKING");
			put("L-SACCHARIDE 1,4 AND 1,6 LINKING", "L-SACCHARIDE 1,4 AND 1,6 LINKING");
			put("L-SACCHARIDE", "L-SACCHARIDE");
			put("peptide-like", "PEPTIDE-LIKE");
			put("L-peptide linking", "L-PEPTIDE LINKING");
			put("NON-POLYMER", "NON-POLYMER");
			put("Saccharide", "SACCHARIDE");
			put("D-Peptide Linking", "D-PEPTIDE LINKING");
			put("L-PEPTIDE COOH CARBOXY TERMINUS", "L-PEPTIDE COOH CARBOXY TERMINUS");
			put("L-SACCHARIDE 1,4 AND 1,4 LINKING", "L-SACCHARIDE 1,4 AND 1,4 LINKING");
			put("DNA OH 5 PRIME TERMINUS", "DNA OH 5 PRIME TERMINUS");
			put("DNA LINKING", "DNA LINKING");
			put("L-peptide COOH carboxy terminus", "L-PEPTIDE COOH CARBOXY TERMINUS");
			put("DNA OH 3 PRIME TERMINUS", "DNA OH 3 PRIME TERMINUS");
			put("RNA OH 5 PRIME TERMINUS", "RNA OH 5 PRIME TERMINUS");
			put("l-peptide linking", "L-PEPTIDE LINKING");
			put("RNA LINKING", "RNA LINKING");
			put("RNA OH 3 PRIME TERMINUS", "RNA OH 3 PRIME TERMINUS");
			put("D-PEPTIDE NH3 AMINO TERMINUS", "D-PEPTIDE NH3 AMINO TERMINUS");
			put("D-GAMMA-PEPTIDE, C-DELTA LINKING", "D-GAMMA-PEPTIDE, C-DELTA LINKING");
			put("SACCHARIDE", "SACCHARIDE");
			put("D-PEPTIDE COOH CARBOXY TERMINUS", "D-PEPTIDE COOH CARBOXY TERMINUS");
			put("L-Peptide Linking", "L-PEPTIDE LINKING");
			put("D-PEPTIDE LINKING", "D-PEPTIDE LINKING");

		}
	};

	public static String getType(String val_name) {
		return (String) map_type.get(val_name);
	}

	static final Map<String, String> map_processing_site = new HashMap<String, String>() {

		private static final long serialVersionUID = 14L;

		{

			put("PDBE", "PDBE");
			put("EBI", "EBI");
			put("PDBe", "PDBE");
			put("RCSB", "RCSB");
			put("PDBJ", "PDBJ");
			put("PDBj", "PDBJ");
			put("MSD", "MSD");

		}
	};

	public static String getProcessingSite(String val_name) {
		return (String) map_processing_site.get(val_name);
	}

	static final Map<String, String> map_paramagnetic = new HashMap<String, String>() {

		private static final long serialVersionUID = 15L;

		{

			put("No", "no");
			put("yes", "yes");
			put("no", "no");
			put("NO", "no");

		}
	};

	public static String getParamagnetic(String val_name) {
		return (String) map_paramagnetic.get(val_name);
	}

	/* synchronized */ public static String checkChemCompIDwithChemComp(String pdb_code, String pdb_id, int trials) {

		if (trials < 0 || trials >= 3 || pdb_code == null || pdb_code.isEmpty() || pdb_code.equals(".") || pdb_code.equals("?"))
			return null;

		File ccd_file = new File(bmr_Util_Main.ccd_dir_name + pdb_code + ".rdf");

		if (ccd_file.exists())
			return checkChemCompIDwithChemComp(pdb_code, pdb_id, trials, ccd_file);

		try {

			String _pdb_code = URLEncoder.encode(pdb_code, "UTF-8");
			String rdf_pdb_ccd_api = "http://rdf.wwpdb.org/cc/" + _pdb_code;

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

			URL url = new URL(rdf_pdb_ccd_api);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());

			DocumentBuilderFactory doc_builder_fac = DocumentBuilderFactory.newInstance();

			doc_builder_fac.setValidating(false);
			doc_builder_fac.setNamespaceAware(false);
			doc_builder_fac.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
			doc_builder_fac.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

			DocumentBuilder doc_builder = doc_builder_fac.newDocumentBuilder();

			InputStream is = conn.getInputStream();

			Document doc = doc_builder.parse(is);

			if (is != null)
				is.close();

			Node root = doc.getDocumentElement();

			if (!root.getNodeName().equals("rdf:RDF")) {

				if (pdb_id == null || pdb_id.isEmpty() || pdb_id.equals(".") || pdb_id.equals("?"))
					return checkChemCompIDwithBMRBLigandExpo(pdb_code, false, 0);

				return checkChemCompIDwithPDB(pdb_code, pdb_id, 0);
			}

			for (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {

				if (child.getNodeName().equals("rdf:Description")) {

					for (Node db_name = child.getFirstChild(); db_name != null; db_name = db_name.getNextSibling()) {

						if (db_name.getNodeName().equals("PDBo:datablockName")) {

							writeChemCompDictionary(ccd_file, doc);

							return db_name.getFirstChild().getNodeValue();
						}

					}

				}

			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			return checkChemCompIDwithChemComp(pdb_code, pdb_id, ++trials);

			//e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			//e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}

		return pdb_code;
	}

	private static void writeChemCompDictionary(File ccd_file, Document doc) {

		File ccd_file_ = new File(ccd_file.getPath() + "~");

		if (ccd_file.exists())
			return;

		try {

			DOMImplementation domImpl = doc.getImplementation();
			DOMImplementationLS domImplLS = (DOMImplementationLS) domImpl.getFeature("LS", "3.0");

			LSOutput output = domImplLS.createLSOutput();
			output.setByteStream(new FileOutputStream(ccd_file_));

			LSSerializer serializer = domImplLS.createLSSerializer();
			serializer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
			serializer.write(doc, output);

/*
			OutputFormat format = new OutputFormat(doc);

			format.setIndenting(true);
			format.setIndent(2);

			FileWriter writer = new FileWriter(ccd_file_);
			XMLSerializer serializer = new XMLSerializer(writer, format);

			serializer.serialize(doc);
*/

			ccd_file_.renameTo(ccd_file);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static String checkChemCompIDwithChemComp(String pdb_code, String pdb_id, int trials, File ccd_file) {

		try {

			DocumentBuilderFactory doc_builder_fac = DocumentBuilderFactory.newInstance();

			doc_builder_fac.setValidating(false);
			doc_builder_fac.setNamespaceAware(false);
			doc_builder_fac.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
			doc_builder_fac.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

			DocumentBuilder doc_builder = doc_builder_fac.newDocumentBuilder();

			Document doc = doc_builder.parse(ccd_file);

			Node root = doc.getDocumentElement();

			if (!root.getNodeName().equals("rdf:RDF")) {

				ccd_file.delete();

				if (pdb_id == null || pdb_id.isEmpty() || pdb_id.equals(".") || pdb_id.equals("?"))
					return checkChemCompIDwithBMRBLigandExpo(pdb_code, false, 0);

				return checkChemCompIDwithPDB(pdb_code, pdb_id, 0);
			}

			for (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {

				if (child.getNodeName().equals("rdf:Description")) {

					for (Node db_name = child.getFirstChild(); db_name != null; db_name = db_name.getNextSibling()) {

						if (db_name.getNodeName().equals("PDBo:datablockName"))
							return db_name.getFirstChild().getNodeValue();

					}

				}

			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ccd_file.delete();

		return pdb_code;
	}

	/* synchronized */ public static String checkChemCompIDwithPDB(String pdb_code, String pdb_id, int trials) {

		if (trials < 0 || trials >= 3 || pdb_code == null || pdb_code.isEmpty() || pdb_code.equals(".") || pdb_code.equals("?") || pdb_id == null || pdb_id.isEmpty() || pdb_id.equals(".") || pdb_id.equals("?"))
			return null;

		File cc_file = new File(bmr_Util_Main.cc_dir_name + pdb_id + "_" + pdb_code + ".rdf");

		if (cc_file.exists())
			return checkChemCompIDwithPDB(pdb_code, pdb_id, trials, cc_file);

		try {

			String _pdb_code = URLEncoder.encode(pdb_code, "UTF-8");
			String rdf_pdb_api = "http://rdf.wwpdb.org/pdb/" + pdb_id + "/chem_comp/" + _pdb_code;

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

			URL url = new URL(rdf_pdb_api);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());

			DocumentBuilderFactory doc_builder_fac = DocumentBuilderFactory.newInstance();

			doc_builder_fac.setValidating(false);
			doc_builder_fac.setNamespaceAware(false);
			doc_builder_fac.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
			doc_builder_fac.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

			DocumentBuilder doc_builder = doc_builder_fac.newDocumentBuilder();

			InputStream is = conn.getInputStream();

			Document doc = doc_builder.parse(is);

			if (is != null)
				is.close();

			Node root = doc.getDocumentElement();

			if (!root.getNodeName().equals("rdf:RDF"))
				return checkChemCompIDwithBMRBLigandExpo(pdb_code, false, 0);

			boolean fill_pdb_id = false;
			boolean fill_pdb_code = false;

			for (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {

				if (child.getNodeName().equals("PDBo:PDBID")) {

					for (Node label = child.getFirstChild(); label != null; label = label.getNextSibling()) {

						if (label.getNodeName().equals("rdfs.label")) {

							pdb_id = label.getFirstChild().getNodeValue();
							fill_pdb_id = true;

						}

					}

				}

			}

			for (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {

				if (child.getNodeName().equals("rdf:Description")) {

					for (Node label = child.getFirstChild(); label != null; label = label.getNextSibling()) {

						if (label.getNodeName().equals("PDBo:chem_comp.id")) {

							pdb_code = label.getFirstChild().getNodeValue();
							fill_pdb_code = true;

						}

					}

				}

			}

			if (fill_pdb_id && fill_pdb_code)
				writeChemCompDictionary(cc_file, doc);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			return checkChemCompIDwithPDB(pdb_code, pdb_id, ++trials);

			//e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			//e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}

		return "pdb/" + pdb_id + "/chem_comp/" + pdb_code;
	}

	private static String checkChemCompIDwithPDB(String pdb_code, String pdb_id, int trials, File cc_file) {

		try {

			DocumentBuilderFactory doc_builder_fac = DocumentBuilderFactory.newInstance();

			doc_builder_fac.setValidating(false);
			doc_builder_fac.setNamespaceAware(false);
			doc_builder_fac.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
			doc_builder_fac.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

			DocumentBuilder doc_builder = doc_builder_fac.newDocumentBuilder();

			Document doc = doc_builder.parse(cc_file);

			Node root = doc.getDocumentElement();

			if (!root.getNodeName().equals("rdf:RDF")) {

				cc_file.delete();

				return checkChemCompIDwithBMRBLigandExpo(pdb_code, false, 0);
			}

			boolean fill_pdb_id = false;
			boolean fill_pdb_code = false;

			for (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {

				if (child.getNodeName().equals("PDBo:PDBID")) {

					for (Node label = child.getFirstChild(); label != null; label = label.getNextSibling()) {

						if (label.getNodeName().equals("rdfs.label")) {

							pdb_id = label.getFirstChild().getNodeValue();
							fill_pdb_id = true;

						}

					}

				}

			}

			for (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {

				if (child.getNodeName().equals("rdf:Description")) {

					for (Node label = child.getFirstChild(); label != null; label = label.getNextSibling()) {

						if (label.getNodeName().equals("PDBo:chem_comp.id")) {

							pdb_code = label.getFirstChild().getNodeValue();
							fill_pdb_code = true;

						}

					}

				}

			}

			if (!fill_pdb_id || !fill_pdb_code)
				cc_file.delete();

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "pdb/" + pdb_id + "/chem_comp/" + pdb_code;
	}

	/* synchronized */ public static String checkChemCompIDwithBMRBLigandExpo(String bmrb_code, boolean bmrb, int trials) {

		if (trials < 0 || trials >= 3 || bmrb_code == null || bmrb_code.isEmpty() || bmrb_code.equals(".") || bmrb_code.equals("?"))
			return null;

		if (bmrb_code.matches("^bms[et][0-9][0-9][0-9][0-9][0-9][0-9]$"))
			return bmrb ? bmrb_code : "bmrb_metabolomics/" + bmrb_code;

		try {

/*			TrustManager[] tm = new TrustManager[] { new X509TrustManager() {

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

*/			String _bmrb_code = URLEncoder.encode(bmrb_code, "UTF-8");
			String bmrb_ligand_expo_api = "http://octopus.bmrb.wisc.edu/ligand-expo?what=find&exact_id=on&compid=" + _bmrb_code;

			URL url = new URL(bmrb_ligand_expo_api);
			URLConnection conn = url.openConnection();
//			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
//			conn.setSSLSocketFactory(sc.getSocketFactory());

			InputStream is = conn.getInputStream();

			BufferedReader bufferr = new BufferedReader(new InputStreamReader(is));

			if (is != null)
				is.close();

			String line = null;

			while ((line = bufferr.readLine()) != null) {

				if (line.contains("No records")) {

					bufferr.close();

					return "no_records/" + bmrb_code;
				}

			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			return checkChemCompIDwithBMRBLigandExpo(bmrb_code, bmrb, ++trials);

			//e.printStackTrace();
/*		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
*/		}

		return bmrb ? bmrb_code : "bmrb_ligand_expo/" + bmrb_code;
	}
}
