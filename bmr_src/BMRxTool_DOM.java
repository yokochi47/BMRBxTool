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
import java.sql.*;
import java.util.*;
import java.util.logging.*;

import org.w3c.dom.*;

public class BMRxTool_DOM {

	public static final String package_name = "org.pdbj.bmrbpub.schema.mmcifNmrStar";
	public static final String namespace_xs = "http://www.w3.org/2001/XMLSchema";
	public static final String namespace_xsi = "http://www.w3.org/2001/XMLSchema-instance";

	public static final String util_main = "Util_Main";
	private static final String util_serv = "Util_Serv";
	private static final String util_thrd = "Util_Thrd";
	private static final String util_xml = "Util_XML";
	private static final String util_valid = "Util_Valid";

	public static final String util_date = "Util_Date";
	public static final String util_tax = "Util_Tax";
	public static final String util_le = "Util_LE";
	public static final String util_bmrb = "Util_BMRB";
	public static final String util_dbname = "Util_DBName";

	public static final String util_alignmentblosom = "Util_AlignmentBlosom";
	public static final String util_alignmentstrict = "Util_AlignmentStrict";
	public static final String util_assembly = "Util_Assembly";
	public static final String util_assemblydblink = "Util_AssemblyDbLink";
	public static final String util_assemblyinteraction = "Util_AssemblyInteraction";
	public static final String util_assemblysystematicname = "Util_AssemblySystematicName";
	public static final String util_assemblytype = "Util_AssemblyType";
	public static final String util_atomchemshift = "Util_AtomChemShift";
	public static final String util_autorelaxationlist = "Util_AutoRelaxationList";
	public static final String util_bond = "Util_Bond";
	public static final String util_chemcomp = "Util_ChemComp";
	public static final String util_chemcompatom = "Util_ChemCompAtom";
	public static final String util_chemcompbond= "Util_ChemCompBond";
	public static final String util_chemcompcommonname = "Util_ChemCompCommonName";
	public static final String util_chemcompdescriptor = "Util_ChemCompDescriptor";
	public static final String util_chemcompidentifier = "Util_ChemCompIdentifier";
	public static final String util_chemcompsystematicname = "Util_ChemCompSystematicName";
	public static final String util_chemshiftref = "Util_ChemShiftRef";
	public static final String util_citation = "Util_Citation";
	public static final String util_citationauthor = "Util_CitationAuthor";
	public static final String util_conformerfamilyrefinement = "Util_ConformerFamilyRefinement";
	public static final String util_conformerstatlist = "Util_ConformerStatList";
	public static final String util_constraintfile = "Util_ConstraintFile";
	public static final String util_couplingconstantexperiment = "Util_CouplingConstantExperiment";
	public static final String util_couplingconstantlist = "Util_CouplingConstatnList";
	public static final String util_crossref = "Util_CrossRef";
	public static final String util_dataset = "Util_DataSet";
	public static final String util_datum = "Util_Datum";
	public static final String util_entity = "Util_Entity";
	public static final String util_entityassembly = "Util_EntityAssembly";
	public static final String util_entitybond = "Util_EntityBond";
	public static final String util_entitycompindex = "Util_EntityCompIndex";
	public static final String util_entitydblink = "Util_EntityDbLink";
	public static final String util_entityexperimentalsrc = "Util_EntityExperimentalSrc";
	public static final String util_entitynaturalsrc = "Util_EntityNaturalSrc";
	public static final String util_entitysystematicname = "Util_EntitySystematicName";
	public static final String util_entry = "Util_Entry";
	public static final String util_entryauthor = "Util_EntryAuthor";
	public static final String util_entryexperimentalmethods = "Util_EntryExperimentalMethods";
	public static final String util_heteronuclt1list = "Util_HeteronuclT1List";
	public static final String util_heteronuclt2list = "Util_HeteronuclT2List";
	public static final String util_heteronuclt1rholist = "Util_HeteronuclT1RhoList";
	public static final String util_heteronuclnoelist = "Util_HeteronuclNOEList";
	public static final String util_homonuclnoelist = "Util_HomonuclNOEList";
	public static final String util_hexchratelist = "Util_HExchRateList";
	public static final String util_gendistconstraintlist = "Util_GenDistConstraintList";
	public static final String util_naturalsourcedb = "Util_NaturalSourceDb";
	public static final String util_nmrexperimentfile = "Util_NMRExperimentFile";
	public static final String util_nmrprobe = "Util_NMRProbe";
	public static final String util_nmrspectrometer = "Util_NMRSpectrometer";
	public static final String util_nmrspectrometerprobe = "Util_NMRSpectrometerProbe";
	public static final String util_nmrspectrometerview = "Util_NMRSpectrometerView";
	public static final String util_nmrspecexpt = "Util_NMRSpecExpt";
	public static final String util_orderparam = "Util_OrderParam";
	public static final String util_orderparameterlist = "Util_OrderParameterList";
	public static final String util_pdbxpolyseqscheme = "Util_PDBXPolySeqScheme";
	public static final String util_phtitrationlist = "Util_PHTitrationList";
	public static final String util_rdcexperiment = "Util_RDCExperiment";
	public static final String util_relatedentries = "Util_RelatedEntries";
	public static final String util_release = "Util_Release";
	public static final String util_repconfrefinement = "Util_RepConfRefinement";
	public static final String util_sample = "Util_Sample";
	public static final String util_samplecomponent = "Util_SampleComponent";
	public static final String util_sampleconditionvariable = "Util_SampleConditionVariable";
	public static final String util_seqonelettercode = "Util_SeqOneLetterCode";
	public static final String util_sgproject = "Util_SGProject";
	public static final String util_structannochar = "Util_StructAnnoChar";
	public static final String util_structclassification = "Util_StructClassification";
	public static final String util_study = "Util_Study";
	public static final String util_systematicchemshiftoffset = "Util_SystematicChemShiftOffset";

	public static final String pubmed_esummary_api = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esummary.fcgi?db=pubmed&id=";
	public static final String pubmed_esearch_api = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?database=pubmed&term=";
	public static final String crossref_doi_api = "https://search.crossref.org/dois?q=";
	public static final String bmrb_ligand_expo_api = "http://octopus.bmrb.wisc.edu/ligand-expo?what=find&exact_id=on&compid=";
	public static final String pdb_graph_uri = "https://rdf.wwpdb.org/pdb";
	public static final String pdb_cc_graph_uri = "https://rdf.wwpdb.org/cc";
	public static final String rdf_pdb_api = pdb_graph_uri + "/";
	public static final String rdf_pdb_cc_api = pdb_cc_graph_uri + "/";
	public static final String pdbo_uri = "https://rdf.wwpdb.org/schema/pdbx-v50.owl#";

	public static final int service_trials = 3;
	public static final int service_wait = 10000; // wait for 10 sec

	public static final String license = "/*\n   BMRBxTool - XML converter for NMR-STAR data\n    Copyright 2013-2018 Masashi Yokochi\n\n    https://github.com/yokochi47/BMRBxTool\n\nLicensed under the Apache License, Version 2.0 (the \"License\");\nyou may not use this file except in compliance with the License.\n You may obtain a copy of the License at\n    http://www.apache.org/licenses/LICENSE-2.0\nUnless required by applicable law or agreed to in writing, software\ndistributed under the License is distributed on an \"AS IS\" BASIS,\nWITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\nSee the License for the specific language governing permissions and\nlimitations under the License.\n */\n\n";

	private static int serial_version_uid = 0;

	// link_db: link java class files generated by XMLBeans and BMRB database.

	public static int link_db(Document doc, Connection conn_bmrb, String pass_bmrb, Connection conn_tax, String pass_tax, Connection conn_le, String pass_le, String prefix, String xsd_dir_name, String src_dir_name, String namespace_uri, String xsd_name, String file_prefix) {

		ResultSet rset = null;
		DatabaseMetaData meta;

		List<String> table_list = new ArrayList<String>();
		List<String> attr_list = new ArrayList<String>();

		// get all table names from BMRB.

		try {

			meta = conn_bmrb.getMetaData();
			rset = meta.getTables(null, null, null, null);

			while (rset.next())
				table_list.add(rset.getString("TABLE_NAME"));

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(BMRxTool_DOM.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		} finally {

			try {

				if (rset != null)
					rset.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(BMRxTool_DOM.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		Node root = doc.getDocumentElement(); // root node of schema.

		for (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {

			NamedNodeMap node_map = child.getAttributes();

			if (node_map != null) {

				Node node = node_map.item(0);

				if (node == null)
					continue;

				String attr_name = node.getTextContent();

				if (attr_name.startsWith("datablock")) // categories starting with 'datablock' are irrelevant.
					continue;

				boolean match = false;

				for (String table_name : table_list) {

					if (table_name.matches("^dict\\..*")) // table names starting with "dict." are irrelevant.
						continue;

					if (table_name.equals("pacsy.software") || table_name.equals("pacsy.task") || table_name.equals("pacsy.vendor"))
						continue;

					String _table_name = table_name + "Type"; // java class names always end with "Type".

					if (_table_name.startsWith("pacsy."))
						_table_name = _table_name.replaceFirst("^pacsy\\.", "");

					if (attr_name.equalsIgnoreCase(_table_name)) { // category and table are matched!

						match = true;

						char[] _Table_name = table_name.toCharArray();
						_Table_name[0] = Character.toUpperCase(_Table_name[0]);
						String Table_name = String.valueOf(_Table_name);

						// write java classes to link schema and database.
						BMRxTool_Java.write(child, conn_bmrb, attr_name, Table_name, src_dir_name, prefix, file_prefix);

						attr_list.add(attr_name);

						break;
					}
				}
				/* 3.1.1.53 specific case
				if (!match && attr_name.equals("x_ray_instrumentType")) {

					for (String table_name : table_list) {

						String _table_name = table_name + "Type"; // java class names always end with "Type".

						if (_table_name.equals("Xray_instrumentType")) { // category and table are matched!

							match = true;

							// write java classes to link schema and database.
							BMRxTool_Java.write(child, conn_bmrb, attr_name, table_name, src_dir_name, prefix, file_prefix);

							attr_list.add(attr_name);

							break;
						}
					}

				}
				 */
				if (!match) {

					// report unresolved categories.
					System.err.println("xsd_attr: \"" + attr_name + "\" is unresolved.");

				}
			}
		}

		try {

			File index_sql = new File(src_dir_name + "../index_" + file_prefix + ".schema");

			FileWriter filew = new FileWriter(index_sql);
			BufferedWriter  buffw = new BufferedWriter(filew);

			buffw.write("\n");

			for (String table_name : table_list) {

				if (table_name.matches("^dict\\..*")) // table names starting with "dict." are irrelevant.
					continue;

				if (table_name.equals("pacsy.software") || table_name.equals("pacsy.task") || table_name.equals("pacsy.vendor"))
					continue;

				if (!table_name.matches("pacsy\\..*$") && table_name.matches("^[a-z_].*"))
					continue;

				String _table_name = table_name + "Type";

				boolean match = false;

				for (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {

					NamedNodeMap node_map = child.getAttributes();

					if (node_map != null) {

						Node node = node_map.item(0);

						if (node != null) {

							String attr_name = node.getTextContent();

							if (attr_name.equalsIgnoreCase(_table_name)) {

								String index_name = table_name.toLowerCase() + "_index";

								buffw.write("drop index if exists " + index_name + ";\n");
								buffw.write("create index " + index_name + " on \"" + table_name + "\" ( \"" + (table_name.equals("Entry") ? "ID" : "Entry_ID") + "\" );\n\n");

								match = true;

								break;
							}
						}
					}
				}
				/*
				if (!match && _table_name.equals("Xray_instrumentType")) {

					for (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {

						NamedNodeMap node_map = child.getAttributes();

						if (node_map != null) {
							String attr_name = node_map.item(0).getTextContent();

							if (attr_name.equalsIgnoreCase("x_ray_instrumentType")) {
								match = true;
								break;
							}
						}
					}

				}
				 */
				if (!match) {

					// report unresolved tables.
					System.err.println("db_table: \"" + table_name + "\" is unresolved.");

				}
			}

			buffw.close();
			filew.close();

		} catch (DOMException | IOException e) {
			e.printStackTrace();
		}

		// write utility files.

		write_main(src_dir_name, prefix, file_prefix, xsd_name, conn_bmrb, pass_bmrb, conn_tax, pass_tax, conn_le, pass_le); // main routine
		write_serv(src_dir_name, prefix, file_prefix); // entry_id server
		write_thrd(src_dir_name, prefix, file_prefix, namespace_uri); // thread (runnable)
		write_xml(src_dir_name, prefix, file_prefix, attr_list, namespace_uri, xsd_name); // write XML document
		write_valid(src_dir_name, prefix, file_prefix, xsd_name); // validation

		write_util_date(src_dir_name, file_prefix); // date conversion
		write_util_tax(src_dir_name, file_prefix); // NCBI taxonomy utility
		write_util_le(src_dir_name, file_prefix); // RCSB Ligand Expo utility
		write_util_bmrb(src_dir_name, file_prefix); // retrieve data from BMRB database
		write_util_dbname(src_dir_name, file_prefix); // retrieve database name from accession code

		write_util_alignmentblosom(src_dir_name, xsd_dir_name, file_prefix);
		write_util_alignmentstrict(src_dir_name, xsd_dir_name, file_prefix);
		write_util_assembly(src_dir_name, xsd_dir_name, file_prefix);
		write_util_assemblydblink(src_dir_name, xsd_dir_name, file_prefix);
		write_util_assemblyinteraction(src_dir_name, xsd_dir_name, file_prefix);
		write_util_assemblysystematicname(src_dir_name, xsd_dir_name, file_prefix);
		write_util_assemblytype(src_dir_name, xsd_dir_name, file_prefix);
		write_util_atomchemshift(src_dir_name, xsd_dir_name, file_prefix);
		write_util_autorelaxationlist(src_dir_name, xsd_dir_name, file_prefix);
		write_util_bond(src_dir_name, xsd_dir_name, file_prefix);
		write_util_chemcomp(src_dir_name, xsd_dir_name, file_prefix);
		write_util_chemcompatom(src_dir_name, xsd_dir_name, file_prefix);
		write_util_chemcompbond(src_dir_name, xsd_dir_name, file_prefix);
		write_util_chemcompcommonname(src_dir_name, xsd_dir_name, file_prefix);
		write_util_chemcompdescriptor(src_dir_name, xsd_dir_name, file_prefix);
		write_util_chemcompidentifier(src_dir_name, xsd_dir_name, file_prefix);
		write_util_chemcompsystematicname(src_dir_name, xsd_dir_name, file_prefix);
		write_util_chemshiftref(src_dir_name, xsd_dir_name, file_prefix);
		write_util_citation(src_dir_name, xsd_dir_name, file_prefix);
		write_util_citationauthor(src_dir_name, xsd_dir_name, file_prefix);
		write_util_conformerfamilyrefinement(src_dir_name, xsd_dir_name, file_prefix);
		write_util_conformerstatlist(src_dir_name, xsd_dir_name, file_prefix);
		write_util_constraintfile(src_dir_name, xsd_dir_name, file_prefix);
		write_util_couplingconstantexperiment(src_dir_name, xsd_dir_name, file_prefix);
		write_util_couplingconstantlist(src_dir_name, xsd_dir_name, file_prefix);
		write_util_crossref(src_dir_name, xsd_dir_name, file_prefix);
		write_util_dataset(src_dir_name, xsd_dir_name, file_prefix);
		write_util_datum(src_dir_name, xsd_dir_name, file_prefix);
		write_util_entity(src_dir_name, xsd_dir_name, file_prefix);
		write_util_entityassembly(src_dir_name, xsd_dir_name, file_prefix);
		write_util_entitybond(src_dir_name, xsd_dir_name, file_prefix);
		write_util_entitycompindex(src_dir_name, xsd_dir_name, file_prefix);
		write_util_entitydblink(src_dir_name, xsd_dir_name, file_prefix);
		write_util_entityexperimentalsrc(src_dir_name, xsd_dir_name, file_prefix);
		write_util_entitynaturalsrc(src_dir_name, xsd_dir_name, file_prefix);
		write_util_entitysystematicname(src_dir_name, xsd_dir_name, file_prefix);
		write_util_entry(src_dir_name, xsd_dir_name, file_prefix);
		write_util_entryauthor(src_dir_name, xsd_dir_name, file_prefix);
		write_util_entryexperimentalmethods(src_dir_name, xsd_dir_name, file_prefix);
		write_util_heteronuclt1list(src_dir_name, xsd_dir_name, file_prefix);
		write_util_heteronuclt2list(src_dir_name, xsd_dir_name, file_prefix);
		write_util_heteronuclt1rholist(src_dir_name, xsd_dir_name, file_prefix);
		write_util_heteronuclnoelist(src_dir_name, xsd_dir_name, file_prefix);
		write_util_homonuclnoelist(src_dir_name, xsd_dir_name, file_prefix);
		write_util_hexchratelist(src_dir_name, xsd_dir_name, file_prefix);
		write_util_gendistconstraintlist(src_dir_name, xsd_dir_name, file_prefix);
		write_util_naturalsourcedb(src_dir_name, xsd_dir_name, file_prefix);
		write_util_nmrexperimentfile(src_dir_name, xsd_dir_name, file_prefix);
		write_util_nmrprobe(src_dir_name, xsd_dir_name, file_prefix);
		write_util_nmrspectrometer(src_dir_name, xsd_dir_name, file_prefix);
		write_util_nmrspectrometerprobe(src_dir_name, xsd_dir_name, file_prefix);
		write_util_nmrspectrometerview(src_dir_name, xsd_dir_name, file_prefix);
		write_util_nmrspecexpt(src_dir_name, xsd_dir_name, file_prefix);
		write_util_orderparam(src_dir_name, xsd_dir_name, file_prefix);
		write_util_orderparameterlist(src_dir_name, xsd_dir_name, file_prefix);
		write_util_pdbxpolyseqscheme(src_dir_name, xsd_dir_name, file_prefix);
		write_util_phtitrationlist(src_dir_name, xsd_dir_name, file_prefix);
		write_util_rdcexperiment(src_dir_name, xsd_dir_name, file_prefix);
		write_util_relatedentries(src_dir_name, xsd_dir_name, file_prefix);
		write_util_release(src_dir_name, xsd_dir_name, file_prefix);
		write_util_repconfrefinement(src_dir_name, xsd_dir_name, file_prefix);
		write_util_sample(src_dir_name, xsd_dir_name, file_prefix);
		write_util_samplecomponent(src_dir_name, xsd_dir_name, file_prefix);
		write_util_sampleconditionvariable(src_dir_name, xsd_dir_name, file_prefix);
		write_util_seqonelettercode(src_dir_name, xsd_dir_name, file_prefix);
		write_util_sgproject(src_dir_name, xsd_dir_name, file_prefix);
		write_util_structannochar(src_dir_name, xsd_dir_name, file_prefix);
		write_util_structclassfication(src_dir_name, xsd_dir_name, file_prefix);
		write_util_study(src_dir_name, xsd_dir_name, file_prefix);
		write_util_systematicchemshiftoffset(src_dir_name, xsd_dir_name, file_prefix);

		return 0;
	}

	private static void write_main(String src_dir_name, String prefix, String file_prefix, String xsd_name, Connection conn_bmrb, String pass_bmrb, Connection conn_tax, String pass_tax, Connection conn_le, String pass_le) {

		final String program_name = src_dir_name + file_prefix + "_" + util_main + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			DatabaseMetaData meta_bmrb = conn_bmrb.getMetaData();
			DatabaseMetaData meta_tax = conn_tax.getMetaData();
			DatabaseMetaData meta_le = conn_le.getMetaData();

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.io.*;\n");
			buffw.write("import java.nio.channels.FileChannel;\n\n");

			buffw.write("import java.util.Properties;\n");
			buffw.write("import java.net.*;\n");
			buffw.write("import javax.mail.*;\n");
			buffw.write("import javax.mail.internet.*;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_main + " {\n\n");

			buffw.write("\tpublic static final String user = System.getProperty(\"user.name\");\n\n");

			buffw.write("\tpublic static String url_bmrb = \"" + meta_bmrb.getURL() + "\";\n");
			buffw.write("\tpublic static String user_bmrb = \"\";\n");
			buffw.write("\tpublic static String pass_bmrb = \"" + pass_bmrb + "\";\n\n");

			buffw.write("\tpublic static String url_tax = \"" + meta_tax.getURL() + "\";\n");
			buffw.write("\tpublic static String user_tax = \"\";\n");
			buffw.write("\tpublic static String pass_tax = \"" + pass_tax + "\";\n\n");

			buffw.write("\tpublic static String url_le = \"" + meta_le.getURL() + "\";\n");
			buffw.write("\tpublic static String user_le = \"\";\n");
			buffw.write("\tpublic static String pass_le = \"" + pass_le + "\";\n\n");

			buffw.write("\tpublic static String noatom_suffix = \"\";\n\n");

			buffw.write("\tpublic static boolean write_xml = true;\n");
			buffw.write("\tpublic static boolean remediate_xml = true;\n");
			buffw.write("\tpublic static boolean validate_xml = true;\n\n");

			buffw.write("\tpublic static boolean well_formed = false;\n\n");

			buffw.write("\tpublic static boolean noatom = false;\n\n");

			buffw.write("\tpublic static boolean init = true;\n\n");

			buffw.write("\tprivate static final String version = \"" + BMRxTool_Main.version + "\";\n");
			buffw.write("\tprivate static final String dev_mail_addr = \"" + BMRxTool_Main.dev_mail_addr + "\";\n\n");

			buffw.write("\tprivate static final String xsd_name = \"" + xsd_name + "\";\n");
			buffw.write("\tprivate static final String file_prefix = \"" + file_prefix + "\";\n\n");

			buffw.write("\tprivate static String bmrbx_tool_home = \".\";\n\n");

			buffw.write("\tpublic static String xsd_dir_name = bmrbx_tool_home + \"/schema/\";\n");
			buffw.write("\tpublic static String esum_dir_name = bmrbx_tool_home + \"/pubmed.esum/\";\n");
			buffw.write("\tpublic static String ccd_dir_name = bmrbx_tool_home + \"/pdb.ccd/\";\n");
			buffw.write("\tpublic static String cc_dir_name = bmrbx_tool_home + \"/pdb.cc/\";\n");
			buffw.write("\tprivate static String xml_dir_name = bmrbx_tool_home + \"/\" + file_prefix + \"_xml_doc/\";\n");
			buffw.write("\tprivate static String rel_dir_name = bmrbx_tool_home + \"/\" + file_prefix + \"_xml_rel/\";\n");
			buffw.write("\tprivate static String log_dir_name = bmrbx_tool_home + \"/\" + file_prefix + \"_xml_log/\";\n");
			buffw.write("\tprivate static String err_dir_name = bmrbx_tool_home + \"/\" + file_prefix + \"_xml_err/\";\n");
			buffw.write("\tprivate static String loc_dir_name = bmrbx_tool_home + \"/\" + file_prefix + \"_xml_loc/\";\n\n");

			buffw.write("\tprivate static String to_mail_addr = \"" + BMRxTool_Main.to_mail_addr + "\";\n");
			buffw.write("\tprivate static String from_mail_addr = \"" + BMRxTool_Main.from_mail_addr + "\";\n");
			buffw.write("\tprivate static String smtp_host_name = \"" + BMRxTool_Main.smtp_host_name + "\";\n\n");
			buffw.write("\tprivate static String smtp_ip_addr = \"" + BMRxTool_Main.smtp_ip_addr + "\";\n\n");

			buffw.write("\tprivate static Runtime runtime = Runtime.getRuntime();\n");
			buffw.write("\tprivate static final int cpu_num = runtime.availableProcessors();\n");
			buffw.write("\tprivate static int max_thrds = cpu_num;\n\n");

			buffw.write("\tpublic static void main(String[] args) {\n\n");

			buffw.write("\t\tfor (int i = 0; i < args.length; i++) {\n\n");

			buffw.write("\t\t\tif (args[i].equals(\"--home\")) {\n");
			buffw.write("\t\t\t\tbmrbx_tool_home = args[++i];\n\n");

			buffw.write("\t\t\t\txsd_dir_name = bmrbx_tool_home + \"/schema/\";\n");
			buffw.write("\t\t\t\tesum_dir_name = bmrbx_tool_home + \"/pubmed.esum/\";\n");
			buffw.write("\t\t\t\tccd_dir_name = bmrbx_tool_home + \"/pdb.ccd/\";\n");
			buffw.write("\t\t\t\tcc_dir_name = bmrbx_tool_home + \"/pdb.cc/\";\n");
			buffw.write("\t\t\t\txml_dir_name = bmrbx_tool_home + \"/\" + file_prefix + \"_xml_doc/\";\n");
			buffw.write("\t\t\t\trel_dir_name = bmrbx_tool_home + \"/\" + file_prefix + \"_xml_rel/\";\n");
			buffw.write("\t\t\t\tlog_dir_name = bmrbx_tool_home + \"/\" + file_prefix + \"_xml_log/\";\n");
			buffw.write("\t\t\t\terr_dir_name = bmrbx_tool_home + \"/\" + file_prefix + \"_xml_err/\";\n");
			buffw.write("\t\t\t\tloc_dir_name = bmrbx_tool_home + \"/\" + file_prefix + \"_xml_loc/\";\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\telse if (args[i].equals(\"--url-bmrb\"))\n");
			buffw.write("\t\t\t\turl_bmrb = args[++i];\n\n");

			buffw.write("\t\t\telse if (args[i].equals(\"--user-bmrb\"))\n");
			buffw.write("\t\t\t\tuser_bmrb = args[++i];\n\n");

			buffw.write("\t\t\telse if (args[i].equals(\"--pass-bmrb\"))\n");
			buffw.write("\t\t\t\tpass_bmrb = args[++i];\n\n");

			buffw.write("\t\t\telse if (args[i].equals(\"--url-tax\"))\n");
			buffw.write("\t\t\t\turl_tax = args[++i];\n\n");

			buffw.write("\t\t\telse if (args[i].equals(\"--user-tax\"))\n");
			buffw.write("\t\t\t\tuser_tax = args[++i];\n\n");

			buffw.write("\t\t\telse if (args[i].equals(\"--pass-tax\"))\n");
			buffw.write("\t\t\t\tpass_tax = args[++i];\n\n");

			buffw.write("\t\t\telse if (args[i].equals(\"--url-le\"))\n");
			buffw.write("\t\t\t\turl_le = args[++i];\n\n");

			buffw.write("\t\t\telse if (args[i].equals(\"--user-le\"))\n");
			buffw.write("\t\t\t\tuser_le = args[++i];\n\n");

			buffw.write("\t\t\telse if (args[i].equals(\"--pass-le\"))\n");
			buffw.write("\t\t\t\tpass_le = args[++i];\n\n");

			buffw.write("\t\t\telse if (args[i].equals(\"--mail-to\"))\n");
			buffw.write("\t\t\t\tto_mail_addr = args[++i];\n\n");

			buffw.write("\t\t\telse if (args[i].equals(\"--mail-from\"))\n");
			buffw.write("\t\t\t\tfrom_mail_addr = args[++i];\n\n");

			buffw.write("\t\t\telse if (args[i].equals(\"--smtp-host\")) {\n");
			buffw.write("\t\t\t\tsmtp_host_name = args[++i];\n\n");

			buffw.write("\t\t\t\ttry {\n\n");

			buffw.write("\t\t\t\t\tInetAddress inet_addr = InetAddress.getByName(smtp_host_name);\n");
			buffw.write("\t\t\t\t\tsmtp_ip_addr = inet_addr.toString().replaceFirst(smtp_host_name + \"/\", \"\");\n\n");

			buffw.write("\t\t\t\t} catch (UnknownHostException e) {\n");
			buffw.write("\t\t\t\t\te.printStackTrace();\n");
			buffw.write("\t\t\t\t\tSystem.exit(1);\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\telse if (args[i].equals(\"--max-thrds\")) {\n");
			buffw.write("\t\t\t\tmax_thrds = Integer.valueOf(args[++i]);\n\n");

			buffw.write("\t\t\t\tif (max_thrds <= 0 || max_thrds > cpu_num) {\n");
			buffw.write("\t\t\t\t\tSystem.err.println(\"Out of range (max_thrds).\");\n");
			buffw.write("\t\t\t\t\tSystem.exit(1);\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\telse if (args[i].equals(\"--no-remediate\"))\n");
			buffw.write("\t\t\t\tremediate_xml = false;\n\n");

			buffw.write("\t\t\telse if (args[i].equals(\"--no-validate\")) {\n");
			buffw.write("\t\t\t\twrite_xml = true;\n");
			buffw.write("\t\t\t\tvalidate_xml = false;\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\telse if (args[i].equals(\"--validate\")) {\n");
			buffw.write("\t\t\t\twrite_xml = true;\n");
			buffw.write("\t\t\t\tvalidate_xml = true;\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\telse if (args[i].equals(\"--validate-only\")) {\n");
			buffw.write("\t\t\t\twrite_xml = false;\n");
			buffw.write("\t\t\t\tvalidate_xml = true;\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\telse if (args[i].equals(\"--well-formed\"))\n");
			buffw.write("\t\t\t\twell_formed = true;\n\n");

			buffw.write("\t\t\telse if (args[i].equals(\"--noatom\")) {\n");
			buffw.write("\t\t\t\tnoatom = true;\n");
			buffw.write("\t\t\t\tnoatom_suffix = \"-noatom\";\n\n");

			buffw.write("\t\t\t\txml_dir_name = bmrbx_tool_home + \"/\" + file_prefix + \"_xml_noatom_doc/\";\n");
			buffw.write("\t\t\t\trel_dir_name = bmrbx_tool_home + \"/\" + file_prefix + \"_xml_noatom_rel/\";\n");
			buffw.write("\t\t\t\tlog_dir_name = bmrbx_tool_home + \"/\" + file_prefix + \"_xml_noatom_log/\";\n");
			buffw.write("\t\t\t\terr_dir_name = bmrbx_tool_home + \"/\" + file_prefix + \"_xml_noatom_err/\";\n");
			buffw.write("\t\t\t\tloc_dir_name = bmrbx_tool_home + \"/\" + file_prefix + \"_xml_noatom_loc/\";\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\telse if (args[i].equals(\"--atom\")) {\n");
			buffw.write("\t\t\t\tnoatom = false;\n");
			buffw.write("\t\t\t\tnoatom_suffix = \"\";\n\n");

			buffw.write("\t\t\t\txml_dir_name = bmrbx_tool_home + \"/\" + file_prefix + \"_xml_doc/\";\n");
			buffw.write("\t\t\t\trel_dir_name = bmrbx_tool_home + \"/\" + file_prefix + \"_xml_rel/\";\n");
			buffw.write("\t\t\t\tlog_dir_name = bmrbx_tool_home + \"/\" + file_prefix + \"_xml_log/\";\n");
			buffw.write("\t\t\t\terr_dir_name = bmrbx_tool_home + \"/\" + file_prefix + \"_xml_err/\";\n");
			buffw.write("\t\t\t\tloc_dir_name = bmrbx_tool_home + \"/\" + file_prefix + \"_xml_loc/\";\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\telse if (args[i].equals(\"--noinit\"))\n");
			buffw.write("\t\t\t\tinit = false;\n\n");

			buffw.write("\t\t\telse {\n");
			buffw.write("\t\t\t\tSystem.out.println(\"Usage: --home DIR --url-bmrb BMRB --user-bmrb USER --pass-bmrb WORD --url-tax TAX --user-tax USER --pass-tax WORD --url-le LE --user-le USER --pass-le WORD --mail-to ADDR --mail-from ADDR --smtp-host NAME\");\n");
			buffw.write("\t\t\t\tSystem.out.println(\" --home      DIR  : BMRBxTool home directory. (\" + bmrbx_tool_home + \")\");\n");
			buffw.write("\t\t\t\tSystem.out.println(\" --url-bmrb  BMRB : URL of BMRB database. (\" + url_bmrb + \")\");\n");
			buffw.write("\t\t\t\tSystem.out.println(\" --user-bmrb USER : Username of BMRB database.\");\n");
			buffw.write("\t\t\t\tSystem.out.println(\" --pass-bmrb WORD : Password of BMRB database.\");\n");
			buffw.write("\t\t\t\tSystem.out.println(\" --url-tax   TAX  : URL of Taxonomy database. (\" + url_tax + \")\");\n");
			buffw.write("\t\t\t\tSystem.out.println(\" --user-tax  USER : Username of Taxonomy database.\");\n");
			buffw.write("\t\t\t\tSystem.out.println(\" --pass-tax  WORD : Password of Taxonomy database.\");\n");
			buffw.write("\t\t\t\tSystem.out.println(\" --url-le    LE   : URL of Ligand Expo database. (\" + url_le + \")\");\n");
			buffw.write("\t\t\t\tSystem.out.println(\" --user-le   USER : Username of Ligand Expo database.\");\n");
			buffw.write("\t\t\t\tSystem.out.println(\" --pass-le   WORD : Password of Ligand Expo database.\");\n");
			buffw.write("\t\t\t\tSystem.out.println(\" --mail-to   ADDR : Mail address of administrator. (\" + to_mail_addr + \")\");\n");
			buffw.write("\t\t\t\tSystem.out.println(\" --mail-from ADDR : Mail address of sender. (\" + from_mail_addr + \")\");\n");
			buffw.write("\t\t\t\tSystem.out.println(\" --smtp-host NAME : SMTP host name. (\" + smtp_host_name + \")\");\n");
			buffw.write("\t\t\t\tSystem.out.println(\" --max-thrds PROC : Number of threads. (default is number of processores)\");\n");
			buffw.write("\t\t\t\tSystem.out.println(\" --no-remediate   : Turn off programmed remediation.\");\n");
			buffw.write("\t\t\t\tSystem.out.println(\" --no-validate    : Write XML files without XML Schema validation.\");\n");
			buffw.write("\t\t\t\tSystem.out.println(\" --validate       : Write XML files and Validate them against XML Schema. (default)\");\n");
			buffw.write("\t\t\t\tSystem.out.println(\" --validate-only  : Validate XML files against XML Schema.\");\n");
			buffw.write("\t\t\t\tSystem.out.println(\" --well-formed    : Check only if XML files are well-formed. (Option)\");\n");
			buffw.write("\t\t\t\tSystem.out.println(\" --noatom         : Omit atomic coordinates, restraints and peak lists.\");\n");
			buffw.write("\t\t\t\tSystem.out.println(\" --noinit         : Don't refresh log and lock directories on startup.\");\n\n");

			buffw.write("\t\t\t\tSystem.exit(1);\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\tFile xml_dir = new File(xml_dir_name);\n\n");

			buffw.write("\t\tif (xml_dir.exists()) {\n\n");

			buffw.write("\t\t\tif (xml_dir.isFile())\n");
			buffw.write("\t\t\t\txml_dir.delete();\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif (!xml_dir.isDirectory()) {\n\n");

			buffw.write("\t\t\tif (!xml_dir.mkdir()) {\n");
			buffw.write("\t\t\t\tSystem.err.println(\"Couldn't create directory '\" + xml_dir_name + \"'.\");\n");
			buffw.write("\t\t\t\tSystem.exit(1);\n");
			buffw.write("\t\t\t}\n\n\t\t}\n\n");

			buffw.write("\t\tFile rel_dir = new File(rel_dir_name);\n\n");

			buffw.write("\t\tif (rel_dir.exists()) {\n\n");

			buffw.write("\t\t\tif (rel_dir.isFile())\n");
			buffw.write("\t\t\t\trel_dir.delete();\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif (!rel_dir.isDirectory()) {\n\n");

			buffw.write("\t\t\tif (!rel_dir.mkdir()) {\n");
			buffw.write("\t\t\t\tSystem.err.println(\"Couldn't create directory '\" + rel_dir_name + \"'.\");\n");
			buffw.write("\t\t\t\tSystem.exit(1);\n");
			buffw.write("\t\t\t}\n\n\t\t}\n\n");

			buffw.write("\t\tFile log_dir = new File(log_dir_name);\n\n");

			buffw.write("\t\tif (log_dir.exists()) {\n\n");

			buffw.write("\t\t\tif (log_dir.isFile())\n");
			buffw.write("\t\t\t\tlog_dir.delete();\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif (!log_dir.isDirectory()) {\n\n");

			buffw.write("\t\t\tif (!log_dir.mkdir()) {\n");
			buffw.write("\t\t\t\tSystem.err.println(\"Couldn't create directory '\" + log_dir_name + \"'.\");\n");
			buffw.write("\t\t\t\tSystem.exit(1);\n");
			buffw.write("\t\t\t}\n\n\t\t}\n\n");

			buffw.write("\t\tFile err_dir = new File(err_dir_name);\n\n");

			buffw.write("\t\tif (err_dir.exists()) {\n\n");

			buffw.write("\t\t\tif (err_dir.isFile())\n");
			buffw.write("\t\t\t\terr_dir.delete();\n\n");

			buffw.write("\t\t\telse if (err_dir.isDirectory() && init) {\n\n");

			buffw.write("\t\t\t\tFile[] files = err_dir.listFiles();\n\n");

			buffw.write("\t\t\t\tfor (int i = 0; i < files.length; i++)\n");
			buffw.write("\t\t\t\t\tfiles[i].delete();\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif (!err_dir.isDirectory()) {\n\n");

			buffw.write("\t\t\tif (!err_dir.mkdir()) {\n");
			buffw.write("\t\t\t\tSystem.err.println(\"Couldn't create directory '\" + err_dir_name + \"'.\");\n");
			buffw.write("\t\t\t\tSystem.exit(1);\n");
			buffw.write("\t\t\t}\n\n\t\t}\n\n");

			buffw.write("\t\tif (init) {\n\n");

			buffw.write("\t\t\ttry {\n\n");
			buffw.write("\t\t\t\tcopyTransfer(xsd_dir_name + xsd_name, xml_dir_name + xsd_name);\n\n");
			buffw.write("\t\t\t} catch (IOException e) {\n");
			buffw.write("\t\t\t\te.printStackTrace();\n");
			buffw.write("\t\t\t\tSystem.exit(1);\n");
			buffw.write("\t\t\t}\n\n\t\t}\n\n");

			buffw.write("\t\tFile loc_dir = new File(loc_dir_name);\n\n");

			buffw.write("\t\tif (loc_dir.exists()) {\n\n");

			buffw.write("\t\t\tif (loc_dir.isFile())\n");
			buffw.write("\t\t\t\tloc_dir.delete();\n\n");

			buffw.write("\t\t\telse if (loc_dir.isDirectory() && init) {\n\n");

			buffw.write("\t\t\t\tFile[] files = loc_dir.listFiles();\n\n");

			buffw.write("\t\t\t\tfor (int i = 0; i < files.length; i++)\n");
			buffw.write("\t\t\t\t\tfiles[i].delete();\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif (!loc_dir.isDirectory()) {\n\n");

			buffw.write("\t\t\tif (!loc_dir.mkdir()) {\n");
			buffw.write("\t\t\t\tSystem.err.println(\"Couldn't create directory '\" + loc_dir_name + \"'.\");\n");
			buffw.write("\t\t\t\tSystem.exit(1);\n");
			buffw.write("\t\t\t}\n\n\t\t}\n\n");

			buffw.write("\t\tFile esum_dir = new File(esum_dir_name);\n\n");

			buffw.write("\t\tif (!esum_dir.isDirectory()) {\n\n");

			buffw.write("\t\t\tif (!esum_dir.mkdir()) {\n");
			buffw.write("\t\t\t\tSystem.err.println(\"Couldn't create directory '\" + esum_dir_name + \"'.\");\n");
			buffw.write("\t\t\t\tSystem.exit(1);\n");
			buffw.write("\t\t\t}\n\n\t\t}\n\n");

			buffw.write("\t\telse if (init) {\n\n");

			buffw.write("\t\t\tFile[] files = esum_dir.listFiles();\n\n");

			buffw.write("\t\t\tfor (int i = 0; i < files.length; i++) {\n");
			buffw.write("\t\t\t\tif (files[i].getName().endsWith(\"~\"))\n");
			buffw.write("\t\t\t\t\tfiles[i].delete();\n");
			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\tFile ccd_dir = new File(ccd_dir_name);\n\n");

			buffw.write("\t\tif (!ccd_dir.isDirectory()) {\n\n");

			buffw.write("\t\t\tif (!ccd_dir.mkdir()) {\n");
			buffw.write("\t\t\t\tSystem.err.println(\"Couldn't create directory '\" + ccd_dir_name + \"'.\");\n");
			buffw.write("\t\t\t\tSystem.exit(1);\n");
			buffw.write("\t\t\t}\n\n\t\t}\n\n");

			buffw.write("\t\telse if (init) {\n\n");

			buffw.write("\t\t\tFile[] files = ccd_dir.listFiles();\n\n");

			buffw.write("\t\t\tfor (int i = 0; i < files.length; i++) {\n");
			buffw.write("\t\t\t\tif (files[i].getName().endsWith(\"~\"))\n");
			buffw.write("\t\t\t\t\tfiles[i].delete();\n");
			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\tFile cc_dir = new File(cc_dir_name);\n\n");

			buffw.write("\t\tif (!cc_dir.isDirectory()) {\n\n");

			buffw.write("\t\t\tif (!cc_dir.mkdir()) {\n");
			buffw.write("\t\t\t\tSystem.err.println(\"Couldn't create directory '\" + cc_dir_name + \"'.\");\n");
			buffw.write("\t\t\t\tSystem.exit(1);\n");
			buffw.write("\t\t\t}\n\n\t\t}\n\n");

			buffw.write("\t\telse if (init) {\n\n");

			buffw.write("\t\t\tFile[] files = cc_dir.listFiles();\n\n");

			buffw.write("\t\t\tfor (int i = 0; i < files.length; i++) {\n");
			buffw.write("\t\t\t\tif (files[i].getName().endsWith(\"~\"))\n");
			buffw.write("\t\t\t\t\tfiles[i].delete();\n");
			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\tObject lock_obj = new Object();\n\n");

			buffw.write("\t\t" + file_prefix + "_" + util_serv + ".open(rel_dir_name, loc_dir_name, file_prefix);\n\n");

			buffw.write("\t\t" + file_prefix + "_" + util_thrd + "[] util_thrd = new " + file_prefix + "_" + util_thrd + "[max_thrds];\n");
			buffw.write("\t\tThread[] thrd = new Thread[max_thrds];\n\n");

			buffw.write("\t\tfor (int thrd_id = 0; thrd_id < max_thrds; thrd_id++) {\n\n");

			buffw.write("\t\t\tString thrd_name = \"" + file_prefix + "_" + util_thrd + "-\" + max_thrds + \"-\" + thrd_id;\n\n");

			buffw.write("\t\t\tutil_thrd[thrd_id] = new " + file_prefix + "_" + util_thrd + "(lock_obj, xml_dir_name, rel_dir_name, log_dir_name, err_dir_name, loc_dir_name, file_prefix);\n");
			buffw.write("\t\t\tthrd[thrd_id] = new Thread(util_thrd[thrd_id], thrd_name);\n\n");

			buffw.write("\t\t\tthrd[thrd_id].start();\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\tfor (int thrd_id = 0; thrd_id < max_thrds; thrd_id++) {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tif (thrd[thrd_id] != null)\n");
			buffw.write("\t\t\t\t\tthrd[thrd_id].join();\n\n");

			buffw.write("\t\t\t} catch (InterruptedException e) {\n");
			buffw.write("\t\t\t\te.printStackTrace();\n");
			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\t" + file_prefix + "_" + util_serv + ".close();\n\n");

			buffw.write("\t\tlock_obj = null;\n\n");

			buffw.write("\t\tif (init)\n");
			buffw.write("\t\t\tsendStatus();\n\n");

			buffw.write("\t\tif (validate_xml && init) {\n\n");

			buffw.write("\t\t\tif (loc_dir.isFile())\n");
			buffw.write("\t\t\t\tloc_dir.delete();\n\n");

			buffw.write("\t\t\telse if (loc_dir.isDirectory()) {\n\n");

			buffw.write("\t\t\t\tFile[] files = loc_dir.listFiles();\n\n");

			buffw.write("\t\t\t\tfor (int i = 0; i < files.length; i++)\n");
			buffw.write("\t\t\t\t\tfiles[i].delete();\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tloc_dir.delete();\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t}\n\n");

			buffw.write("\tpublic static void copyTransfer(String src_path, String dst_path) throws IOException {\n\n");

			buffw.write("\t\tFileInputStream src_in = new FileInputStream(src_path);\n");
			buffw.write("\t\tFileOutputStream dst_out = new FileOutputStream(dst_path);\n\n");

			buffw.write("\t\tFileChannel src_ch = src_in.getChannel();\n");
			buffw.write("\t\tFileChannel dst_ch = dst_out.getChannel();\n\n");

			buffw.write("\t\ttry {\n\n");
			buffw.write("\t\t\tsrc_ch.transferTo(0, src_ch.size(), dst_ch);\n\n");
			buffw.write("\t\t} finally {\n\n");

			buffw.write("\t\t\tif (src_ch != null)\n");
			buffw.write("\t\t\t\tsrc_ch.close();\n\n");

			buffw.write("\t\t\tif (dst_ch != null)\n");
			buffw.write("\t\t\t\tdst_ch.close();\n\n");

			buffw.write("\t\t\tif (src_in != null)\n");
			buffw.write("\t\t\t\tsrc_in.close();\n\n");

			buffw.write("\t\t\tif (dst_out != null)\n");
			buffw.write("\t\t\t\tdst_out.close();\n\n");

			buffw.write("\t\t}\n\t}\n\n");

			buffw.write("\tpublic static void sendStatus() {\n\n");

			buffw.write("\t\tProperties props = new Properties();\n\n");

			buffw.write("\t\tprops.put(\"mail.smtp.host\", smtp_ip_addr);\n\n");

			buffw.write("\t\tSession session = Session.getDefaultInstance(props, null);\n");
			buffw.write("\t\tMessage msg = new MimeMessage(session);\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tInternetAddress from_addr = new InternetAddress(from_mail_addr);\n");
			buffw.write("\t\t\tmsg.setFrom(from_addr);\n\n");

			buffw.write("\t\t\tInternetAddress[] to_addr = { new InternetAddress(to_mail_addr), new InternetAddress(dev_mail_addr) };\n");
			buffw.write("\t\t\tmsg.setRecipients(Message.RecipientType.TO, to_addr);\n\n");

			buffw.write("\t\t\tString host_name = null;\n");
			buffw.write("\t\t\tString host_addr = null;\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\thost_name = InetAddress.getLocalHost().getHostName();\n");
			buffw.write("\t\t\t\thost_addr = InetAddress.getLocalHost().getHostAddress();\n\n");

			buffw.write("\t\t\t} catch (UnknownHostException e) {\n");
			buffw.write("\t\t\t\t//e.printStackTrace();\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tmsg.setSubject(\"Error message from bmrbx-tool (\" + file_prefix + noatom_suffix + \") on \" + host_name);\n\n");

			buffw.write("\t\t\tStringBuilder content = new StringBuilder();\n\n");

			buffw.write("\t\t\tcontent.append(String.format(\"bmrbx-tool (%s%s) version: %s\\n\", file_prefix, noatom_suffix, version));\n");
			buffw.write("\t\t\tcontent.append(String.format(\"host_name: %s\\n\", host_name));\n");
			buffw.write("\t\t\tcontent.append(String.format(\"host_addr: %s\\n\\n\", host_addr));\n\n");

			buffw.write("\t\t\tFile err_dir = new File(err_dir_name);\n\n");

			buffw.write("\t\t\tif (err_dir.exists() && err_dir.isDirectory()) {\n\n");

			buffw.write("\t\t\t\tFile[] files = err_dir.listFiles();\n\n");

			buffw.write("\t\t\t\tif (files.length == 0) {\n");
			buffw.write("\t\t\t\t\tif (validate_xml)\n");
			buffw.write("\t\t\t\t\t\tSystem.out.println(\"XML files (prefix:\" + file_prefix + noatom_suffix + \") are update.\");\n");
			buffw.write("\t\t\t\t\treturn;\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t\tboolean success = true;\n\n");

			buffw.write("\t\t\t\tfor (int i = 0; i < files.length; i++) {\n\n");

			buffw.write("\t\t\t\t\tif (files[i].length() == 0)\n");
			buffw.write("\t\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\t\tsuccess = false;\n\n");

			buffw.write("\t\t\t\t\tcontent.append(String.format(\"File: %s\\n\", files[i].getName().replace(\"_err\", \"\").concat(\".xml\")));\n\n");

			buffw.write("\t\t\t\t\ttry {\n\n");

			buffw.write("\t\t\t\t\t\tFileReader filer = new FileReader(err_dir_name + files[i].getName());\n");
			buffw.write("\t\t\t\t\t\tBufferedReader bufferr = new BufferedReader(filer);\n\n");

			buffw.write("\t\t\t\t\t\tString line = null;\n\n");

			buffw.write("\t\t\t\t\t\twhile ((line = bufferr.readLine()) != null)\n");
			buffw.write("\t\t\t\t\t\t\tcontent.append(line + \"\\n\");\n\n");

			buffw.write("\t\t\t\t\t\tbufferr.close();\n");
			buffw.write("\t\t\t\t\t\tfiler.close();\n\n");

			buffw.write("\t\t\t\t\t\tcontent.append(\"\\n\\n\");\n\n");

			buffw.write("\t\t\t\t\t} catch (IOException e) {\n");
			buffw.write("\t\t\t\t\t\te.printStackTrace();\n");
			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\tif (content.length() > 10000)\n");
			buffw.write("\t\t\t\t\t\tbreak;\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t\tif (success) {\n");
			buffw.write("\t\t\t\t\tif (validate_xml)\n");
			buffw.write("\t\t\t\t\t\tSystem.out.println(\"XML files (prefix:\" + file_prefix + noatom_suffix + \") are update.\");\n");
			buffw.write("\t\t\t\t\treturn;\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tif (content.length() > 10000) {\n\n");

			buffw.write("\t\t\t\tcontent.setLength(0);\n\n");

			buffw.write("\t\t\t\tcontent.append(String.format(\"%sx-tool version: %s\\n\", file_prefix, version));\n");
			buffw.write("\t\t\t\tcontent.append(String.format(\"host_name: %s\\n\", host_name));\n");
			buffw.write("\t\t\t\tcontent.append(String.format(\"host_addr: %s\\n\\n\", host_addr));\n\n");

			buffw.write("\t\t\t\tFile[] files = err_dir.listFiles();\n\n");

			buffw.write("\t\t\t\tfor (int i = 0; i < files.length; i++) {\n\n");

			buffw.write("\t\t\t\t\tif (files[i].length() == 0)\n");
			buffw.write("\t\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\t\tcontent.append(String.format(\"File: %s\\n\", files[i].getName().replace(\"_err\", \"\").concat(\".xml\")));\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t\tcontent.append(String.format(\"\\n\\nFound validation errors. Please check log files for more details.\\n\", host_addr));\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tmsg.setText(content.toString());\n\n");

			buffw.write("\t\t\tTransport.send(msg);\n\n");

			buffw.write("\t\t} catch (MessagingException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t}\n\n");

			//			buffw.write("\t\tSystem.exit(1);\n");

			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void write_serv(String src_dir_name, String prefix, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_serv + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.io.*;\n");
			buffw.write("import java.sql.*;\n");
			buffw.write("import java.util.logging.*;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_serv + " {\n\n");

			buffw.write("\tprivate static boolean connected = false;\n\n");

			buffw.write("\tprivate static String _rel_dir_name = null;\n");
			buffw.write("\tprivate static String _loc_dir_name = null;\n");
			buffw.write("\tprivate static String _file_prefix = null;\n\n");

			buffw.write("\tprivate static Connection conn_bmrb = null;\n");
			buffw.write("\tprivate static Statement state = null;\n");
			buffw.write("\tprivate static ResultSet rset = null;\n\n");

			buffw.write("\tprivate static PreparedStatement pstate2 = null;\n");
			buffw.write("\tprivate static PreparedStatement pstate3 = null;\n\n");

			buffw.write("\tprivate static final String url_bmrb = " + file_prefix + "_" + util_main + ".url_bmrb;\n");
			buffw.write("\tprivate static final String user_bmrb = " + file_prefix + "_" + util_main + ".user_bmrb.isEmpty() ? " + file_prefix + "_" + util_main + ".user : " + file_prefix + "_" + util_main + ".user_bmrb;\n");
			buffw.write("\tprivate static final String pass_bmrb = " + file_prefix + "_" + util_main + ".pass_bmrb;\n\n");

			buffw.write("\tsynchronized public static void open(String rel_dir_name, String loc_dir_name, String file_prefix) {\n\n");

			buffw.write("\t\tif (connected)\n");
			buffw.write("\t\t\treturn;\n\n");

			buffw.write("\t\t_rel_dir_name = rel_dir_name;\n");
			buffw.write("\t\t_loc_dir_name = loc_dir_name;\n");
			buffw.write("\t\t_file_prefix = file_prefix;\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tconn_bmrb = DriverManager.getConnection(url_bmrb, user_bmrb, pass_bmrb);\n");
			buffw.write("\t\t\tstate = conn_bmrb.createStatement();\n");
			buffw.write("\t\t\trset = state.executeQuery(\"select \\\"ID\\\" from \\\"Entry\\\" order by random()\");\n\n");

			buffw.write("\t\t\tpstate2 = conn_bmrb.prepareStatement(\"select \\\"Date\\\" from \\\"Release\\\" where \\\"Entry_ID\\\"=? order by \\\"Date\\\" desc\");\n");
			buffw.write("\t\t\tpstate3 = conn_bmrb.prepareStatement(\"select \\\"DB_query_revised_last_date\\\" from \\\"Entity\\\" where \\\"Entry_ID\\\"=? order by \\\"DB_query_revised_last_date\\\" desc\");\n\n");

			buffw.write("\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_serv + ".class.getName());\n");
			buffw.write("\t\t\tlgr.log(Level.SEVERE, ex.getMessage(), ex);\n");
			buffw.write("\t\t\tSystem.exit(1);\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\tconnected = true;\n\n");

			buffw.write("\t}\n\n");

			buffw.write("\tsynchronized public static String get_entry_id() throws SQLException {\n\n");

			buffw.write("\t\tif (!connected)\n");
			buffw.write("\t\t\treturn null;\n\n");

			buffw.write("\t\tString entry_id = null;\n\n");

			buffw.write("\t\tResultSet rset2 = null;\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\tentry_id = rset.getString(\"ID\");\n\n");

			buffw.write("\t\t\t\tif (entry_id == null)\n");
			buffw.write("\t\t\t\t\treturn null;\n\n");

			buffw.write("\t\t\t\tFile loc_dir = new File(_loc_dir_name);\n\n");

			buffw.write("\t\t\t\tif (!loc_dir.exists())\n");
			buffw.write("\t\t\t\t\treturn null;\n\n");

			buffw.write("\t\t\t\tString rel_file_name = _rel_dir_name + _file_prefix + entry_id + " + file_prefix + "_" + util_main + ".noatom_suffix + \"_last\";\n");
			buffw.write("\t\t\t\tString loc_file_name = _loc_dir_name + _file_prefix + entry_id + " + file_prefix + "_" + util_main + ".noatom_suffix + \"_lock\";\n\n");

			buffw.write("\t\t\t\tFile loc_file = new File(loc_file_name);\n\n");

			buffw.write("\t\t\t\tif (loc_file.exists())\n");
			buffw.write("\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\tFile rel_file = new File(rel_file_name);\n\n");

			buffw.write("\t\t\t\tif (!rel_file.exists()) {\n\n");

			buffw.write("\t\t\t\t\tif (" + file_prefix + "_" + util_main + ".write_xml)\n");
			buffw.write("\t\t\t\t\t\tSystem.out.println(entry_id + \" is new.\");\n\n");

			buffw.write("\t\t\t\t\tbreak;\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t\ttry {\n\n");

			buffw.write("\t\t\t\t\tFileReader filer = new FileReader(rel_file_name);\n");
			buffw.write("\t\t\t\t\tBufferedReader bufferr = new BufferedReader(filer);\n\n");

			buffw.write("\t\t\t\t\tString line = bufferr.readLine();\n\n");

			buffw.write("\t\t\t\t\tbufferr.close();\n");
			buffw.write("\t\t\t\t\tfiler.close();\n\n");

			buffw.write("\t\t\t\t\tif (line == null) {\n\n");

			buffw.write("\t\t\t\t\t\tif (" + file_prefix + "_" + util_main + ".write_xml)\n");
			buffw.write("\t\t\t\t\t\t\tSystem.out.println(entry_id + \" is new.\");\n\n");

			buffw.write("\t\t\t\t\t\tbreak;\n");
			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\tjava.sql.Date release_date = null;\n");
			buffw.write("\t\t\t\t\tjava.sql.Date entity_date = null;\n\n");

			buffw.write("\t\t\t\t\tString date = null;\n\n");

			buffw.write("\t\t\t\t\tpstate2.setString(1, entry_id);\n\n");

			buffw.write("\t\t\t\t\trset2 = pstate2.executeQuery();\n\n");

			buffw.write("\t\t\t\t\trelease_date = null;\n\n");

			buffw.write("\t\t\t\t\twhile (rset2.next()) {\n\n");

			buffw.write("\t\t\t\t\t\tString _date = rset2.getString(\"Date\");\n\n");

			buffw.write("\t\t\t\t\t\tif (" + empty_check("_date") + ")\n");
			buffw.write("\t\t\t\t\t\t\tbreak;\n\n");

			buffw.write("\t\t\t\t\t\trelease_date = rset2.getDate(\"Date\");\n\n");

			buffw.write("\t\t\t\t\t\tif (release_date != null)\n");
			buffw.write("\t\t\t\t\t\t\tbreak;\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\trset2.close();\n\n");

			buffw.write("\t\t\t\t\tpstate3.setString(1, entry_id);\n\n");

			buffw.write("\t\t\t\t\trset2 = pstate3.executeQuery();\n\n");

			buffw.write("\t\t\t\t\tentity_date = null;\n\n");

			buffw.write("\t\t\t\t\twhile (rset2.next()) {\n\n");

			buffw.write("\t\t\t\t\t\tString _date = rset2.getString(\"DB_query_revised_last_date\");\n\n");

			buffw.write("\t\t\t\t\t\tif (" + empty_check("_date") + ")\n");
			buffw.write("\t\t\t\t\t\t\tbreak;\n\n");

			buffw.write("\t\t\t\t\t\tentity_date = rset2.getDate(\"DB_query_revised_last_date\");\n\n");

			buffw.write("\t\t\t\t\t\tif (entity_date != null)\n");
			buffw.write("\t\t\t\t\t\t\tbreak;\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\tif (release_date != null && entity_date != null) {\n\n");

			buffw.write("\t\t\t\t\t\tif (entity_date.after(release_date))\n");
			buffw.write("\t\t\t\t\t\t\tdate = entity_date.toString();\n");
			buffw.write("\t\t\t\t\t\telse\n");
			buffw.write("\t\t\t\t\t\t\tdate = release_date.toString();\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\telse if (entity_date != null)\n");
			buffw.write("\t\t\t\t\t\tdate = entity_date.toString();\n\n");

			buffw.write("\t\t\t\t\telse if (release_date != null)\n");
			buffw.write("\t\t\t\t\t\tdate = release_date.toString();\n\n");

			buffw.write("\t\t\t\t\tif (date == null || !line.equals(date)) {\n\n");

			buffw.write("\t\t\t\t\t\tif (!entry_id.equals(\"4617\") && !entry_id.equals(\"4677\") && !entry_id.equals(\"4980\") && !entry_id.equals(\"19958\") && !entry_id.equals(\"25839\")) {\n\n");

			buffw.write("\t\t\t\t\t\t\tif (" + file_prefix + "_" + util_main + ".write_xml)\n");
			buffw.write("\t\t\t\t\t\t\t\tSystem.out.println(entry_id + \" is revised (\" + line + \" -> \" + date + \").\");\n\n");

			buffw.write("\t\t\t\t\t\t\tbreak;\n\n");

			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\tentry_id = null;\n\n");

			buffw.write("\t\t\t\t} catch (IOException e) {\n");
			buffw.write("\t\t\t\t\te.printStackTrace();\n");
			buffw.write("\t\t\t\t}\n\t\t\t}\n\n");

			buffw.write("\t\t} catch (SQLException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} finally {\n\n");

			buffw.write("\t\t\tif (rset2 != null)\n");
			buffw.write("\t\t\t\trset2.close();\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn entry_id;\n\n");

			buffw.write("\t}\n\n");

			buffw.write("\tsynchronized public static void close() {\n\n");

			buffw.write("\t\tif (!connected)\n");
			buffw.write("\t\t\treturn;\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\tif (pstate2 != null)\n");
			buffw.write("\t\t\t\tpstate2.close();\n\n");

			buffw.write("\t\t\tif (pstate3 != null)\n");
			buffw.write("\t\t\t\tpstate3.close();\n\n");

			buffw.write("\t\t\tif (conn_bmrb != null)\n");
			buffw.write("\t\t\t\tconn_bmrb.close();\n\n");

			buffw.write("\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_serv + ".class.getName());\n");
			buffw.write("\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\tconnected = false;\n\n");

			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_thrd(String src_dir_name, String prefix, String file_prefix, String namespace_uri) {

		final String program_name = src_dir_name + file_prefix + "_" + util_thrd + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.net.URI;\n");
			buffw.write("import java.net.URISyntaxException;\n");
			buffw.write("import java.sql.*;\n");
			buffw.write("import java.util.logging.*;\n\n");

			buffw.write("import javax.xml.namespace.QName;\n\n");

			buffw.write("import org.apache.xmlbeans.XmlOptions;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_thrd + " implements Runnable {\n\n");

			buffw.write("\tprivate Object _lock_obj = null;\n\n");

			buffw.write("\tprivate String _xml_dir_name = null;\n");
			buffw.write("\tprivate String _rel_dir_name = null;\n");
			buffw.write("\tprivate String _log_dir_name = null;\n");
			buffw.write("\tprivate String _err_dir_name = null;\n");
			buffw.write("\tprivate String _loc_dir_name = null;\n\n");

			buffw.write("\tprivate Connection conn_bmrb = null;\n");
			buffw.write("\tprivate Connection conn_clone = null;\n");
			buffw.write("\tprivate Connection conn_tax = null;\n");
			buffw.write("\tprivate Connection conn_le = null;\n\n");

			buffw.write("\tprivate " + file_prefix + "_" + util_valid + " validator = null;\n\n");

			buffw.write("\tprivate XmlOptions xml_opt = null;\n\n");

			buffw.write("\tprivate static String _file_prefix;\n\n");

			buffw.write("\tprivate static final String url_bmrb = " + file_prefix + "_" + util_main + ".url_bmrb;\n");
			buffw.write("\tprivate static final String user_bmrb = " + file_prefix + "_" + util_main + ".user_bmrb.isEmpty() ? " + file_prefix + "_" + util_main + ".user : " + file_prefix + "_" + util_main + ".user_bmrb;\n");
			buffw.write("\tprivate static final String pass_bmrb = " + file_prefix + "_" + util_main + ".pass_bmrb;\n\n");

			buffw.write("\tprivate static final String url_tax = " + file_prefix + "_" + util_main + ".url_tax;\n");
			buffw.write("\tprivate static final String user_tax = " + file_prefix + "_" + util_main + ".user_tax.isEmpty() ? " + file_prefix + "_" + util_main + ".user : " + file_prefix + "_" + util_main + ".user_tax;\n");
			buffw.write("\tprivate static final String pass_tax = " + file_prefix + "_" + util_main + ".pass_tax;\n\n");

			buffw.write("\tprivate static final String url_le = " + file_prefix + "_" + util_main + ".url_le;\n");
			buffw.write("\tprivate static final String user_le = " + file_prefix + "_" + util_main + ".user_le.isEmpty() ? " + file_prefix + "_" + util_main + ".user : " + file_prefix + "_" + util_main + ".user_le;\n");
			buffw.write("\tprivate static final String pass_le = " + file_prefix + "_" + util_main + ".pass_le;\n\n");

			buffw.write("\tprivate static final String namespace_uri = \"" + namespace_uri + "\";\n");
			buffw.write("\tprivate static final String prefix = \"" + prefix + "\";\n\n");

			buffw.write("\tpublic " + file_prefix + "_" + util_thrd + "(Object lock_obj, String xml_dir_name, String rel_dir_name, String log_dir_name, String err_dir_name, String loc_dir_name, String file_prefix) {\n\n");

			buffw.write("\t\t_lock_obj = lock_obj;\n\n");

			buffw.write("\t\t_xml_dir_name = xml_dir_name;\n");
			buffw.write("\t\t_rel_dir_name = rel_dir_name;\n");
			buffw.write("\t\t_log_dir_name = log_dir_name;\n");
			buffw.write("\t\t_err_dir_name = err_dir_name;\n");
			buffw.write("\t\t_loc_dir_name = loc_dir_name;\n");
			buffw.write("\t\t_file_prefix = file_prefix;\n\n");

			buffw.write("\t\tvalidator = new " + file_prefix + "_" + util_valid + "(xml_dir_name);\n\n");

			buffw.write("\t\txml_opt = new XmlOptions();\n\n");

			buffw.write("\t\txml_opt.setSaveUseOpenFrag();\n");
			buffw.write("\t\txml_opt.setSavePrettyPrint();\n");
			buffw.write("\t\txml_opt.setSavePrettyPrintOffset(2);\n\n");

			buffw.write("\t\ttry {\n");
			buffw.write("\t\t\txml_opt.setBaseURI(new URI(namespace_uri));\n");
			buffw.write("\t\t} catch (URISyntaxException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\txml_opt.setSaveAggressiveNamespaces();\n");
			buffw.write("\t\txml_opt.setSaveSyntheticDocumentElement(new QName(namespace_uri, \"\", prefix));\n\n");

			buffw.write("\t}\n\n");

			buffw.write("\t@Override\n");
			buffw.write("\tpublic void run() {\n\n");

			buffw.write("\t\ttry {\n");
			buffw.write("\t\t\tconn_clone = DriverManager.getConnection(url_bmrb + \"_clone\", user_bmrb, pass_bmrb);\n");
			buffw.write("\t\t} catch (SQLException e) {}\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tconn_bmrb = DriverManager.getConnection(url_bmrb, user_bmrb, pass_bmrb);\n");
			buffw.write("\t\t\tconn_tax = DriverManager.getConnection(url_tax, user_tax, pass_tax);\n");
			buffw.write("\t\t\tconn_le = DriverManager.getConnection(url_le, user_le, pass_le);\n\n");

			buffw.write("\t\t\t" + file_prefix + "_" + util_entityexperimentalsrc + " ee = new " + file_prefix + "_" + util_entityexperimentalsrc + "();\n");
			buffw.write("\t\t\t" + file_prefix + "_" + util_entitynaturalsrc + " en = new " + file_prefix + "_" + util_entitynaturalsrc + "();\n");
			buffw.write("\t\t\t" + file_prefix + "_" + util_structclassification + " sc = new " + file_prefix + "_" + util_structclassification + "();\n\n");

			buffw.write("\t\t\t" + file_prefix + "_" + util_entry + " ent = new " + file_prefix + "_" + util_entry + "();\n\n");

			buffw.write("\t\t\twhile (true) {\n\n");

			buffw.write("\t\t\t\tString entry_id = " + file_prefix + "_" + util_serv + ".get_entry_id();\n\n");

			buffw.write("\t\t\t\tif (entry_id == null)\n");
			buffw.write("\t\t\t\t\tbreak;\n\n");

			buffw.write("\t\t\t\t" + file_prefix + "_" + util_xml + ".write(_lock_obj, conn_bmrb, conn_clone, conn_tax, conn_le, ee, en, sc, ent, _xml_dir_name, _rel_dir_name, _log_dir_name, _err_dir_name, _loc_dir_name, _file_prefix, entry_id, validator, xml_opt);\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_thrd + ".class.getName());\n");
			buffw.write("\t\t\tlgr.log(Level.SEVERE, ex.getMessage(), ex);\n");
			buffw.write("\t\t\tSystem.exit(1);\n\n");

			buffw.write("\t\t} finally {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tif (conn_bmrb != null)\n");
			buffw.write("\t\t\t\t\tconn_bmrb.close();\n\n");

			buffw.write("\t\t\t\tif (conn_clone != null)\n");
			buffw.write("\t\t\t\t\tconn_clone.close();\n\n");

			buffw.write("\t\t\t\tif (conn_tax != null)\n");
			buffw.write("\t\t\t\t\tconn_tax.close();\n\n");

			buffw.write("\t\t\t\tif (conn_le != null)\n");
			buffw.write("\t\t\t\t\tconn_le.close();\n\n");

			buffw.write("\t\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_thrd + ".class.getName());\n");
			buffw.write("\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t}\n\t\t}\n\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_xml(String src_dir_name, String prefix, String file_prefix, List<String> attr_list, String namespace_uri, String xsd_name) {

		String type_class_name;
		String class_name;

		final String program_name = src_dir_name + file_prefix + "_" + util_xml + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.io.*;\n");
			buffw.write("import java.sql.*;\n\n");

			buffw.write("import org.apache.xmlbeans.XmlOptions;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_xml + " {\n\n");

			buffw.write("\tpublic static void write(Object lock_obj, Connection conn_bmrb, Connection conn_clone, Connection conn_tax, Connection conn_le, " + file_prefix + "_" + util_entityexperimentalsrc + " ee, " + file_prefix + "_" + util_entitynaturalsrc + " en, " + file_prefix + "_" + util_structclassification + " sc, " + file_prefix + "_" + util_entry + " ent, String xml_dir_name, String rel_dir_name, String log_dir_name, String err_dir_name, String loc_dir_name, String file_prefix, String entry_id, " + file_prefix + "_" + util_valid + " validator, XmlOptions xml_opt) {\n\n");

			buffw.write("\t\tString xml_base_name = file_prefix + entry_id + " + file_prefix + "_" + util_main + ".noatom_suffix + \".xml\";\n");
			buffw.write("\t\tString rel_base_name = file_prefix + entry_id + " + file_prefix + "_" + util_main + ".noatom_suffix + \"_last\";\n");
			buffw.write("\t\tString log_base_name = file_prefix + entry_id + " + file_prefix + "_" + util_main + ".noatom_suffix + \"_log\";\n");
			buffw.write("\t\tString err_base_name = file_prefix + entry_id + " + file_prefix + "_" + util_main + ".noatom_suffix + \"_err\";\n");
			buffw.write("\t\tString loc_base_name = file_prefix + entry_id + " + file_prefix + "_" + util_main + ".noatom_suffix + \"_lock\";\n\n");

			buffw.write("\t\tFile err_dir = new File(err_dir_name);\n\n");

			buffw.write("\t\tif (!err_dir.isDirectory())\n");
			buffw.write("\t\t\treturn;\n\n");

			buffw.write("\t\tFile xml_file = new File(xml_dir_name, xml_base_name);\n");
			buffw.write("\t\tFile log_file = new File(log_dir_name, log_base_name);\n");
			buffw.write("\t\tFile err_file = new File(err_dir_name, err_base_name);\n");
			buffw.write("\t\tFile loc_file = new File(loc_dir_name, loc_base_name);\n\n");

			buffw.write("\t\tif (loc_file.exists() || err_file.exists())\n");
			buffw.write("\t\t\treturn;\n\n");

			buffw.write("\t\tFileWriter logw = null;\n");
			buffw.write("\t\tFileWriter errw = null;\n\n");

			buffw.write("\t\tboolean written = false;\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tboolean no_exist = true;\n\n");

			buffw.write("\t\t\tsynchronized (lock_obj) {\n\n");;

			buffw.write("\t\t\t\tif (loc_file.exists() || err_file.exists())\n");
			buffw.write("\t\t\t\t\tno_exist = false;\n\n");

			buffw.write("\t\t\t\telse {\n\n");

			buffw.write("\t\t\t\t\tFileWriter locw = new FileWriter(loc_file);\n");
			buffw.write("\t\t\t\t\tlocw.write(entry_id);\n");
			buffw.write("\t\t\t\t\tlocw.close();\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tif (!no_exist)\n");
			buffw.write("\t\t\t\treturn;\n\n");

			buffw.write("\t\t\terrw = new FileWriter(err_file);\n\n");

			buffw.write("\t\t\tif (" + file_prefix + "_" + util_main + ".write_xml) {\n\n");

			buffw.write("\t\t\t\tlogw = new FileWriter(log_file);\n\n");

			buffw.write("\t\t\t\tFileWriter filew = new FileWriter(xml_file);\n");
			buffw.write("\t\t\t\tBufferedWriter buffw = new BufferedWriter(filew);\n\n");

			buffw.write("\t\t\t\tbuffw.write(\"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?>\\n\");\n\n");

			buffw.write("\t\t\t\tbuffw.write(\"<" + prefix + ":datablock datablockName=\\\"\" + entry_id + \"\\\"\\n\");\n");
			buffw.write("\t\t\t\tbuffw.write(\"  xmlns:" + prefix + "=\\\"" + namespace_uri + "\\\"\\n\");\n");
			buffw.write("\t\t\t\tbuffw.write(\"  xmlns:xsi=\\\"" + namespace_xsi + "\\\"\\n\");\n");
			buffw.write("\t\t\t\tbuffw.write(\"  xsi:schemaLocation=\\\"" + namespace_uri + " " + xsd_name + "\\\">\\n\");\n\n");

			for (String attr_name : attr_list) {

				char[] _class_name = attr_name.toLowerCase().toCharArray();
				char[] _Class_name = attr_name.toCharArray();

				boolean under_score = true;
				boolean numeric_code = false;

				for (int i = 0; i < attr_name.length(); i++) {
					if (under_score || numeric_code || (_Class_name[i] >= 'A' && _Class_name[i] <= 'Z'))
						_class_name[i] = Character.toUpperCase(_class_name[i]);
					under_score = (_class_name[i] == '_');
					numeric_code = (_class_name[i] >= '0' && _class_name[i] <= '9');
				}

				class_name = String.valueOf(_class_name).replace("_", "").replaceFirst("Type$", "");
				if (class_name.matches(".*[12]Type$"))
					class_name = String.valueOf(_class_name).replace("_", "").replaceFirst("Type$", "").replaceFirst("Type$", "");

				type_class_name = class_name + "Type";

				if (class_name.equalsIgnoreCase("EntityExperimentalSrc"))
					buffw.write("\t\t\t\t" + file_prefix + "_" + type_class_name + ".write_xml(conn_bmrb, conn_tax, conn_le, ee, entry_id, xml_opt, buffw, logw, errw);\n");
				else if (class_name.equalsIgnoreCase("EntityNaturalSrc"))
					buffw.write("\t\t\t\t" + file_prefix + "_" + type_class_name + ".write_xml(conn_bmrb, conn_tax, conn_le, en, entry_id, xml_opt, buffw, logw, errw);\n");
				else if (class_name.equalsIgnoreCase("StructClassification"))
					buffw.write("\t\t\t\t" + file_prefix + "_" + type_class_name + ".write_xml(conn_bmrb, conn_tax, conn_le, sc, entry_id, xml_opt, buffw, logw, errw);\n");
				else if (class_name.equals("Citation"))
					buffw.write("\t\t\t\t" + file_prefix + "_" + type_class_name + ".write_xml(conn_bmrb, conn_clone, conn_tax, conn_le, ent, entry_id, xml_opt, buffw, logw, errw);\n");
				else if (class_name.equalsIgnoreCase("Entry") || class_name.equalsIgnoreCase("AssemblyDbLink") || class_name.equalsIgnoreCase("EntityDbLink") || class_name.equalsIgnoreCase("RelatedEntries"))
					buffw.write("\t\t\t\t" + file_prefix + "_" + type_class_name + ".write_xml(conn_bmrb, conn_tax, conn_le, ent, entry_id, xml_opt, buffw, logw, errw);\n");
				else
					buffw.write("\t\t\t\t" + file_prefix + "_" + type_class_name + ".write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);\n");
			}

			buffw.write("\n\t\t\t\tbuffw.write(\"</" + prefix + ":datablock>\\n\");\n\n");

			buffw.write("\t\t\t\tbuffw.close();\n");
			buffw.write("\t\t\t\tfilew.close();\n\n");

			buffw.write("\t\t\t\tlogw.close();\n\n");

			buffw.write("\t\t\t\tif (log_file.length() == 0)\n");
			buffw.write("\t\t\t\t\tlog_file.delete();\n\n");

			buffw.write("\t\t\t\tSystem.out.println(xml_base_name + \" done.\");\n\n");

			buffw.write("\t\t\t\twritten = true;\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t} catch (FileNotFoundException e) {\n");
			buffw.write("\t\t} catch (IOException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t\tSystem.exit(1);\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif (!written && !" + file_prefix + "_" + util_main + ".validate_xml)\n");
			buffw.write("\t\t\treturn;\n\n");

			buffw.write("\t\tif (!" + file_prefix + "_" + util_main + ".validate_xml || !xml_file.exists()) {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tif (errw != null) {\n\n");

			buffw.write("\t\t\t\t\terrw.close();\n\n");

			buffw.write("\t\t\t\t\tif (err_file.length() == 0)\n");
			buffw.write("\t\t\t\t\t\terr_file.delete();\n\n");

			buffw.write("\t\t\t\t}\n\n");

			//			buffw.write("\t\t\t\tif (loc_file.exists())\n");
			//			buffw.write("\t\t\t\t\tloc_file.delete();\n\n");

			buffw.write("\t\t\t} catch (IOException e) {\n");
			buffw.write("\t\t\t\te.printStackTrace();\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\treturn;\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif (xml_file.exists())\n");
			buffw.write("\t\t\tvalidator.exec(xml_base_name, errw);\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tif (errw != null)\n");
			buffw.write("\t\t\t\terrw.close();\n\n");

			buffw.write("\t\t} catch (IOException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif (err_file.length() != 0)\n");
			buffw.write("\t\t\treturn;\n\n");

			buffw.write("\t\terr_file.delete();\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tjava.sql.Date release_date = null;\n");
			buffw.write("\t\t\tjava.sql.Date entity_date = null;\n\n");

			buffw.write("\t\t\tString date = null;\n\n");

			buffw.write("\t\t\tString query = new String(\"select \\\"Date\\\" from \\\"Release\\\" where \\\"Entry_ID\\\"='\" + entry_id + \"' order by \\\"Date\\\" desc\");\n\n");

			buffw.write("\t\t\tstate = conn_bmrb.createStatement();\n");
			buffw.write("\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\trelease_date = null;\n\n");

			buffw.write("\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\tString _date = rset.getString(\"Date\");\n\n");

			buffw.write("\t\t\t\tif (" + empty_check("_date") + ")\n");
			buffw.write("\t\t\t\t\tbreak;\n\n");

			buffw.write("\t\t\t\trelease_date = rset.getDate(\"Date\");\n\n");

			buffw.write("\t\t\t\tif (release_date != null)\n");
			buffw.write("\t\t\t\t\tbreak;\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tquery = new String(\"select \\\"DB_query_revised_last_date\\\" from \\\"Entity\\\" where \\\"Entry_ID\\\"='\" + entry_id + \"' order by \\\"DB_query_revised_last_date\\\" desc\");\n\n");

			buffw.write("\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\tentity_date = null;\n\n");

			buffw.write("\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\tString _date = rset.getString(\"DB_query_revised_last_date\");\n\n");

			buffw.write("\t\t\t\tif (" + empty_check("_date") + ")\n");
			buffw.write("\t\t\t\t\tbreak;\n\n");

			buffw.write("\t\t\t\tentity_date = rset.getDate(\"DB_query_revised_last_date\");\n\n");

			buffw.write("\t\t\t\tif (entity_date != null)\n");
			buffw.write("\t\t\t\t\tbreak;\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tif (release_date != null && entity_date != null) {\n\n");

			buffw.write("\t\t\t\tif (entity_date.after(release_date))\n");
			buffw.write("\t\t\t\t\tdate = entity_date.toString();\n");
			buffw.write("\t\t\t\telse\n");
			buffw.write("\t\t\t\t\tdate = release_date.toString();\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\telse if (entity_date != null)\n");
			buffw.write("\t\t\t\tdate = entity_date.toString();\n\n");

			buffw.write("\t\t\telse if (release_date != null)\n");
			buffw.write("\t\t\t\tdate = release_date.toString();\n\n");

			buffw.write("\t\t\tFile rel_file = new File(rel_dir_name, rel_base_name);\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tFileWriter filew = new FileWriter(rel_file);\n");
			buffw.write("\t\t\t\tfilew.write(date + \"\\n\");\n");
			buffw.write("\t\t\t\tfilew.close();\n\n");

			buffw.write("\t\t\t} catch (IOException e) {\n");
			buffw.write("\t\t\t\te.printStackTrace();\n");
			buffw.write("\t\t\t\tSystem.exit(1);\n");
			buffw.write("\t\t\t} finally {\n\n");

			buffw.write("\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t} catch (SQLException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t}\n\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_valid(String src_dir_name, String prefix, String file_prefix, String xsd_name) {

		final String program_name = src_dir_name + file_prefix + "_" + util_valid + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.io.*;\n\n");

			buffw.write("import org.xml.sax.*;\n");
			buffw.write("import org.apache.xerces.parsers.*;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_valid + " {\n\n");

			buffw.write("\tDOMParser dom_parser = null;\n\n");

			buffw.write("\tString _dir_name = null;\n\n");

			buffw.write("\tpublic " + file_prefix + "_" + util_valid + "(String dir_name) {\n\n");

			buffw.write("\t\tdom_parser = new DOMParser();\n\n");

			buffw.write("\t\t_dir_name = dir_name;\n\n");

			buffw.write("\t\tif (!" + file_prefix + "_" + BMRxTool_DOM.util_main + ".well_formed) {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tdom_parser.setFeature(\"http://xml.org/sax/features/validation\", true);\n");
			buffw.write("\t\t\t\tdom_parser.setFeature(\"http://apache.org/xml/features/validation/schema\", true);\n");
			buffw.write("\t\t\t\tdom_parser.setFeature(\"http://apache.org/xml/features/validation/schema-full-checking\", true);\n");
			buffw.write("\t\t\t\tdom_parser.setProperty(\"http://java.sun.com/xml/jaxp/properties/schemaLanguage\", \"" + namespace_xs + "\");\n");
			buffw.write("\t\t\t\tdom_parser.setProperty(\"http://java.sun.com/xml/jaxp/properties/schemaSource\", new File(dir_name + \"" + xsd_name + "\"));\n\n");

			buffw.write("\t\t\t} catch (SAXNotRecognizedException e) {\n");
			buffw.write("\t\t\t\te.printStackTrace();\n");
			buffw.write("\t\t\t\tSystem.exit(1);\n");
			buffw.write("\t\t\t} catch (SAXNotSupportedException e) {\n");
			buffw.write("\t\t\t\te.printStackTrace();\n");
			buffw.write("\t\t\t\tSystem.exit(1);\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t}\n\n");

			buffw.write("\tsynchronized public void exec(String xml_base_name, FileWriter errw) {\n\n");

			buffw.write("\t\tErrHandler err_handler = new ErrHandler();\n\n");

			buffw.write("\t\terr_handler.init(errw);\n\n");

			buffw.write("\t\tdom_parser.setErrorHandler(err_handler);\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tdom_parser.parse(_dir_name + xml_base_name);\n\n");

			buffw.write("\t\t} catch (SAXException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (FileNotFoundException e) {\n");
			buffw.write("\t\t\treturn;\n");
			buffw.write("\t\t} catch (IOException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t\tSystem.exit(1);\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif (err_handler.success)\n");
			buffw.write("\t\t\tSystem.out.println(xml_base_name + \" is valid.\");\n\n");

			buffw.write("\t\tdom_parser.reset();\n\n");

			buffw.write("\t}\n\n");

			buffw.write("\tprivate class ErrHandler implements ErrorHandler {\n\n");

			buffw.write("\t\tpublic boolean success = true;\n");
			buffw.write("\t\tpublic FileWriter errw = null;\n\n");

			buffw.write("\t\tpublic void init(FileWriter errw) {\n");
			buffw.write("\t\t\tthis.errw = errw;\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\t@Override\n");
			buffw.write("\t\tpublic void error(SAXParseException e) throws SAXException {\n\n");

			buffw.write("\t\t\tString message = e.getMessage();\n\n");

			buffw.write("\t\t\tif (message.contains(\"Duplicate key value\"))\n");
			buffw.write("\t\t\t\treturn;\n\n");

			buffw.write("\t\t\tif (message.contains(\"datablock\"))\n");
			buffw.write("\t\t\t\treturn;\n\n");

			buffw.write("\t\t\tif (message.contains(\"Date\") || message.contains(\"date\"))\n");
			buffw.write("\t\t\t\treturn;\n\n");

			buffw.write("\t\t\tsuccess = false;\n\n");

			buffw.write("\t\t\tSystem.err.println(\"Error: at \" + e.getLineNumber());\n");
			buffw.write("\t\t\tSystem.err.println(e.getMessage());\n\n");

			buffw.write("\t\t\ttry {\n");
			buffw.write("\t\t\t\terrw.write(\"Error: at \" + e.getLineNumber() + \"\\n\");\n");
			buffw.write("\t\t\t\terrw.write(e.getMessage() + \"\\n\");\n");
			buffw.write("\t\t\t} catch (IOException ex) {\n");
			buffw.write("\t\t\t\tex.printStackTrace();\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\t@Override\n");
			buffw.write("\t\tpublic void fatalError(SAXParseException e) throws SAXException {\n\n");

			buffw.write("\t\t\tsuccess = false;\n\n");

			buffw.write("\t\t\tSystem.err.println(\"Fatal Error: at \" + e.getLineNumber());\n");
			buffw.write("\t\t\tSystem.err.println(e.getMessage());\n\n");

			buffw.write("\t\t\ttry {\n");
			buffw.write("\t\t\t\terrw.write(\"Fatal Error: at \" + e.getLineNumber() + \"\\n\");\n");
			buffw.write("\t\t\t\terrw.write(e.getMessage() + \"\\n\");\n");
			buffw.write("\t\t\t} catch (IOException ex) {\n");
			buffw.write("\t\t\t\tex.printStackTrace();\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\t@Override\n");
			buffw.write("\t\tpublic void warning(SAXParseException e) throws SAXException {\n\n");

			buffw.write("\t\t\tsuccess = false;\n\n");

			buffw.write("\t\t\tSystem.out.println(\"Warning: at \" + e.getLineNumber());\n");
			buffw.write("\t\t\tSystem.out.println(e.getMessage());\n\n");

			buffw.write("\t\t\ttry {\n");
			buffw.write("\t\t\t\terrw.write(\"Warning: at \" + e.getLineNumber() + \"\\n\");\n");
			buffw.write("\t\t\t\terrw.write(e.getMessage() + \"\\n\");\n");
			buffw.write("\t\t\t} catch (IOException ex) {\n");
			buffw.write("\t\t\t\tex.printStackTrace();\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_date(String src_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_date + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.text.*;\n");
			buffw.write("import java.util.Calendar;\n");
			buffw.write("import java.util.TimeZone;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_date + " {\n\n");

			buffw.write("\tpublic static Calendar sqldate2calendar (java.sql.Date sql_date) {\n\n");

			buffw.write("\t\tCalendar cal = null;\n");
			buffw.write("\t\tjava.util.Date util_date;\n\n");

			buffw.write("\t\tDateFormat formatter = new SimpleDateFormat(\"yyyy-MM-dd\");\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tutil_date = (java.util.Date) formatter.parse(sql_date.toString());\n");
			buffw.write("\t\t\tcal = Calendar.getInstance(TimeZone.getTimeZone(\"UTC\"));\n");
			buffw.write("\t\t\tcal.setTime(util_date);\n\n");

			buffw.write("\t\t} catch (ParseException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn cal;\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_tax(String src_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_tax + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.sql.*;\n");
			buffw.write("import java.util.logging.*;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_tax + " {\n\n");

			buffw.write("\tpublic static String latestNCBITaxonomyID(Connection conn_tax, String ncbi_taxonomy_id) {\n\n");

			buffw.write("\t\tif (isValidNCBITaxonomyID(conn_tax, ncbi_taxonomy_id))\n");
			buffw.write("\t\t\treturn ncbi_taxonomy_id;\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tif (conn_tax != null && !(" + empty_check("ncbi_taxonomy_id") + ") && ncbi_taxonomy_id.matches(\"^[0-9]+$\") && !ncbi_taxonomy_id.equals(\"0\")) {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tString query = new String(\"select new_tax_id from merged where old_tax_id=\" + ncbi_taxonomy_id);\n\n");

			buffw.write("\t\t\t\tstate = conn_tax.createStatement();\n");
			buffw.write("\t\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\t\tString tax_id = rset.getString(\"new_tax_id\");\n\n");

			buffw.write("\t\t\t\t\tif (tax_id != null) {\n\n");

			buffw.write("\t\t\t\t\t\tif (isValidNCBITaxonomyID(conn_tax, tax_id)){\n");
			buffw.write("\t\t\t\t\t\t\tncbi_taxonomy_id = tax_id;\n");
			buffw.write("\t\t\t\t\t\t\tbreak;\n");
			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t} catch (SQLException e) {\n");
			buffw.write("\t\t\t\te.printStackTrace();\n");
			buffw.write("\t\t\t} finally {\n\n");

			buffw.write("\t\t\t\ttry {\n\n");

			buffw.write("\t\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_tax + ".class.getName());\n");
			buffw.write("\t\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t\t}\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn ncbi_taxonomy_id;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static boolean isValidNCBITaxonomyID(Connection conn_tax, String ncbi_taxonomy_id) {\n\n");

			buffw.write("\t\tboolean valid = false;\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tif (conn_tax != null && !(" + empty_check("ncbi_taxonomy_id") + ") && ncbi_taxonomy_id.matches(\"^[0-9]+$\") && !ncbi_taxonomy_id.equals(\"0\")) {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tString query = new String(\"select tax_id from nodes where tax_id=\" + ncbi_taxonomy_id);\n\n");

			buffw.write("\t\t\t\tstate = conn_tax.createStatement();\n");
			buffw.write("\t\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\t\tString tax_id = rset.getString(\"tax_id\");\n\n");

			buffw.write("\t\t\t\t\tif (tax_id != null && ncbi_taxonomy_id.equals(tax_id)) {\n");
			buffw.write("\t\t\t\t\t\tvalid = true;\n");
			buffw.write("\t\t\t\t\t\tbreak;\n");
			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t} catch (SQLException e) {\n");
			buffw.write("\t\t\t\te.printStackTrace();\n");
			buffw.write("\t\t\t} finally {\n\n");

			buffw.write("\t\t\t\ttry {\n\n");

			buffw.write("\t\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_tax + ".class.getName());\n");
			buffw.write("\t\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t\t}\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn valid;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getNCBITaxonomyID(Connection conn_tax, String ncbi_taxonomy_id, String ncbi_taxonomy_name) {\n\n");

			buffw.write("\t\tif (isValidNCBITaxonomyID(conn_tax, ncbi_taxonomy_id))\n");
			buffw.write("\t\t\treturn ncbi_taxonomy_id;\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tif (conn_tax != null && !(" + empty_check("ncbi_taxonomy_name") + ") && !ncbi_taxonomy_name.matches(\"na\")) {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tString query = new String(\"select tax_id from names where name_txt='\" + ncbi_taxonomy_name + \"'\");\n\n");

			buffw.write("\t\t\t\tstate = conn_tax.createStatement();\n");
			buffw.write("\t\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\t\tString tax_id = rset.getString(\"tax_id\");\n\n");

			buffw.write("\t\t\t\t\tif (tax_id != null) {\n\n");

			buffw.write("\t\t\t\t\t\tif (isValidNCBITaxonomyID(conn_tax, tax_id)){\n");
			buffw.write("\t\t\t\t\t\t\tncbi_taxonomy_id = tax_id;\n");
			buffw.write("\t\t\t\t\t\t\tbreak;\n");
			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t} catch (SQLException e) {\n");
			buffw.write("\t\t\t\te.printStackTrace();\n");
			buffw.write("\t\t\t} finally {\n\n");

			buffw.write("\t\t\t\ttry {\n\n");

			buffw.write("\t\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_tax + ".class.getName());\n");
			buffw.write("\t\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t\t}\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn ncbi_taxonomy_id;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getSuperkingdom(String val_name, Connection conn_tax, String ncbi_taxonomy_id) {\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tboolean cont = true;\n");
			buffw.write("\t\tString _ncbi_taxonomy_id = ncbi_taxonomy_id;\n\n");

			buffw.write("\t\tif (conn_tax != null && !(" + empty_check("ncbi_taxonomy_id") + ") && ncbi_taxonomy_id.matches(\"^[0-9]+$\") && !ncbi_taxonomy_id.equals(\"0\")) {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\twhile (cont) {\n\n");

			buffw.write("\t\t\t\t\tString query = new String(\"select nodes.tax_id,nodes.parent_tax_id,rank,name_txt from nodes,names where nodes.tax_id=\" + ncbi_taxonomy_id + \" and nodes.tax_id=names.tax_id and name_class='scientific name'\");\n\n");

			buffw.write("\t\t\t\t\tstate = conn_tax.createStatement();\n");
			buffw.write("\t\t\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\t\t\tif (rset.getString(\"rank\").equals(\"superkingdom\")) {\n");
			buffw.write("\t\t\t\t\t\t\tval_name = rset.getString(\"name_txt\");\n");
			buffw.write("\t\t\t\t\t\t\tcont = false;\n");
			buffw.write("\t\t\t\t\t\t\tbreak;\n");
			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t\tString parent_tax_id = rset.getString(\"parent_tax_id\");\n\n");

			buffw.write("\t\t\t\t\t\tif (parent_tax_id == null || ncbi_taxonomy_id.equals(parent_tax_id)) {\n");
			buffw.write("\t\t\t\t\t\t\tcont = false;\n");
			buffw.write("\t\t\t\t\t\t\tbreak;\n");
			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t\tncbi_taxonomy_id = parent_tax_id;\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\tif (ncbi_taxonomy_id.equals(_ncbi_taxonomy_id))\n");
			buffw.write("\t\t\t\t\t\tbreak;\n\n");

			buffw.write("\t\t\t\t\t_ncbi_taxonomy_id = ncbi_taxonomy_id;\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t} catch (SQLException e) {\n");
			buffw.write("\t\t\t\te.printStackTrace();\n");
			buffw.write("\t\t\t} finally {\n\n");

			buffw.write("\t\t\t\ttry {\n\n");

			buffw.write("\t\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t\t} catch (SQLException ex) {\n\n");
			buffw.write("\t\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_tax + ".class.getName());\n");
			buffw.write("\t\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t\t}\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif (ncbi_taxonomy_id != null && ncbi_taxonomy_id.equals(\"na\"))\n");
			buffw.write("\t\t\tval_name = \"Unclassified\";\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getKingdom(String val_name, Connection conn_tax, String ncbi_taxonomy_id) {\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tboolean cont = true;\n");
			buffw.write("\t\tString _ncbi_taxonomy_id = ncbi_taxonomy_id;\n\n");

			buffw.write("\t\tif (conn_tax != null && !(" + empty_check("ncbi_taxonomy_id") + ") && ncbi_taxonomy_id.matches(\"^[0-9]+$\") && !ncbi_taxonomy_id.equals(\"0\")) {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\twhile (cont) {\n\n");

			buffw.write("\t\t\t\t\tString query = new String(\"select nodes.tax_id,nodes.parent_tax_id,rank,name_txt from nodes,names where nodes.tax_id=\" + ncbi_taxonomy_id + \" and nodes.tax_id=names.tax_id and name_class='scientific name'\");\n\n");

			buffw.write("\t\t\t\t\tstate = conn_tax.createStatement();\n");
			buffw.write("\t\t\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\t\t\tif (rset.getString(\"rank\").equals(\"kingdom\")) {\n");
			buffw.write("\t\t\t\t\t\t\tval_name = rset.getString(\"name_txt\");\n");
			buffw.write("\t\t\t\t\t\t\tcont = false;\n");
			buffw.write("\t\t\t\t\t\t\tbreak;\n");
			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t\tif (rset.getString(\"rank\").equals(\"superkingdom\")) {\n");
			buffw.write("\t\t\t\t\t\t\tval_name = \"Not applicable\";\n");
			buffw.write("\t\t\t\t\t\t\tcont = false;\n");
			buffw.write("\t\t\t\t\t\t\tbreak;\n");
			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t\tString parent_tax_id = rset.getString(\"parent_tax_id\");\n\n");

			buffw.write("\t\t\t\t\t\tif (parent_tax_id == null || ncbi_taxonomy_id.equals(parent_tax_id)) {\n");
			buffw.write("\t\t\t\t\t\t\tcont = false;\n");
			buffw.write("\t\t\t\t\t\t\tbreak;\n");
			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t\tncbi_taxonomy_id = parent_tax_id;\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\tif (ncbi_taxonomy_id.equals(_ncbi_taxonomy_id))\n");
			buffw.write("\t\t\t\t\t\tbreak;\n\n");

			buffw.write("\t\t\t\t\t_ncbi_taxonomy_id = ncbi_taxonomy_id;\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t} catch (SQLException e) {\n");
			buffw.write("\t\t\t\te.printStackTrace();\n");
			buffw.write("\t\t\t} finally {\n\n");

			buffw.write("\t\t\t\ttry {\n\n");

			buffw.write("\t\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t\t} catch (SQLException ex) {\n\n");
			buffw.write("\t\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_tax + ".class.getName());\n");
			buffw.write("\t\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t\t}\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif (ncbi_taxonomy_id != null && ncbi_taxonomy_id.equals(\"na\"))\n");
			buffw.write("\t\t\tval_name = \"Not applicable\";\n\n");

			buffw.write("\t\tif (val_name != null && val_name.equals(\"Eubacteria\"))\n");
			buffw.write("\t\t\tval_name = \"Not applicable\";\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getGenus(String val_name, Connection conn_tax, String ncbi_taxonomy_id) {\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tboolean cont = true;\n");
			buffw.write("\t\tString _ncbi_taxonomy_id = ncbi_taxonomy_id;\n\n");

			buffw.write("\t\tif (conn_tax != null && !(" + empty_check("ncbi_taxonomy_id") + ") && ncbi_taxonomy_id.matches(\"^[0-9]+$\") && !ncbi_taxonomy_id.equals(\"0\")) {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\twhile (cont) {\n\n");

			buffw.write("\t\t\t\t\tString query = new String(\"select nodes.tax_id,nodes.parent_tax_id,rank,name_txt from nodes,names where nodes.tax_id=\" + ncbi_taxonomy_id + \" and nodes.tax_id=names.tax_id and name_class='scientific name'\");\n\n");

			buffw.write("\t\t\t\t\tstate = conn_tax.createStatement();\n");
			buffw.write("\t\t\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\t\t\tif (rset.getString(\"rank\").equals(\"genus\")) {\n");
			buffw.write("\t\t\t\t\t\t\tval_name = rset.getString(\"name_txt\");\n");
			buffw.write("\t\t\t\t\t\t\tcont = false;\n");
			buffw.write("\t\t\t\t\t\t\tbreak;\n");
			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t\tif (rset.getString(\"rank\").equals(\"superkingdom\")) {\n");
			buffw.write("\t\t\t\t\t\t\tcont = false;\n");
			buffw.write("\t\t\t\t\t\t\tbreak;\n");
			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t\tString parent_tax_id = rset.getString(\"parent_tax_id\");\n\n");

			buffw.write("\t\t\t\t\t\tif (parent_tax_id == null || ncbi_taxonomy_id.equals(parent_tax_id)) {\n");
			buffw.write("\t\t\t\t\t\t\tcont = false;\n");
			buffw.write("\t\t\t\t\t\t\tbreak;\n");
			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t\tncbi_taxonomy_id = parent_tax_id;\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\tif (ncbi_taxonomy_id.equals(_ncbi_taxonomy_id))\n");
			buffw.write("\t\t\t\t\t\tbreak;\n\n");

			buffw.write("\t\t\t\t\t_ncbi_taxonomy_id = ncbi_taxonomy_id;\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t} catch (SQLException e) {\n");
			buffw.write("\t\t\t\te.printStackTrace();\n");
			buffw.write("\t\t\t} finally {\n\n");

			buffw.write("\t\t\t\ttry {\n\n");

			buffw.write("\t\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t\t} catch (SQLException ex) {\n\n");
			buffw.write("\t\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_tax + ".class.getName());\n");
			buffw.write("\t\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t\t}\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif (ncbi_taxonomy_id != null && ncbi_taxonomy_id.equals(\"na\"))\n");
			buffw.write("\t\t\tval_name = \"Not applicable\";\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getSpecies(String val_name, Connection conn_tax, String ncbi_taxonomy_id) {\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tboolean cont = true;\n");
			buffw.write("\t\tString _ncbi_taxonomy_id = ncbi_taxonomy_id;\n\n");

			buffw.write("\t\tif (conn_tax != null && !(" + empty_check("ncbi_taxonomy_id") + ") && ncbi_taxonomy_id.matches(\"^[0-9]+$\") && !ncbi_taxonomy_id.equals(\"0\")) {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\twhile (cont) {\n\n");

			buffw.write("\t\t\t\t\tString query = new String(\"select nodes.tax_id,nodes.parent_tax_id,rank,name_txt from nodes,names where nodes.tax_id=\" + ncbi_taxonomy_id + \" and nodes.tax_id=names.tax_id and name_class='scientific name'\");\n\n");

			buffw.write("\t\t\t\t\tstate = conn_tax.createStatement();\n");
			buffw.write("\t\t\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\t\t\tif (rset.getString(\"rank\").equals(\"species\")) {\n");
			buffw.write("\t\t\t\t\t\t\tval_name = rset.getString(\"name_txt\");\n");
			buffw.write("\t\t\t\t\t\t\tString genus = getGenus(null, conn_tax, ncbi_taxonomy_id);\n");
			buffw.write("\t\t\t\t\t\t\tif (genus != null && val_name.startsWith(genus + \" \"))\n");
			buffw.write("\t\t\t\t\t\t\t\tval_name = val_name.replaceFirst(genus + \" \", \"\");\n");
			buffw.write("\t\t\t\t\t\t\tcont = false;\n");
			buffw.write("\t\t\t\t\t\t\tbreak;\n");
			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t\tif (rset.getString(\"rank\").equals(\"superkingdom\")) {\n");
			buffw.write("\t\t\t\t\t\t\tcont = false;\n");
			buffw.write("\t\t\t\t\t\t\tbreak;\n");
			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t\tString parent_tax_id = rset.getString(\"parent_tax_id\");\n\n");

			buffw.write("\t\t\t\t\t\tif (parent_tax_id == null || ncbi_taxonomy_id.equals(parent_tax_id)) {\n");
			buffw.write("\t\t\t\t\t\t\tcont = false;\n");
			buffw.write("\t\t\t\t\t\t\tbreak;\n");
			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t\tncbi_taxonomy_id = parent_tax_id;\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\tif (ncbi_taxonomy_id.equals(_ncbi_taxonomy_id))\n");
			buffw.write("\t\t\t\t\t\tbreak;\n\n");

			buffw.write("\t\t\t\t\t_ncbi_taxonomy_id = ncbi_taxonomy_id;\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t} catch (SQLException e) {\n");
			buffw.write("\t\t\t\te.printStackTrace();\n");
			buffw.write("\t\t\t} finally {\n\n");

			buffw.write("\t\t\t\ttry {\n\n");

			buffw.write("\t\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t\t} catch (SQLException ex) {\n\n");
			buffw.write("\t\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_tax + ".class.getName());\n");
			buffw.write("\t\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t\t}\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif (ncbi_taxonomy_id != null && ncbi_taxonomy_id.equals(\"na\"))\n");
			buffw.write("\t\t\tval_name = \"unidentified\";\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getOrganismScientificName(String val_name, Connection conn_tax, String ncbi_taxonomy_id) {\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tif (conn_tax != null && !(" + empty_check("ncbi_taxonomy_id") + ") && ncbi_taxonomy_id.matches(\"^[0-9]+$\") && !ncbi_taxonomy_id.equals(\"0\")) {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tString query = new String(\"select nodes.tax_id,nodes.parent_tax_id,rank,name_txt from nodes,names where nodes.tax_id=\" + ncbi_taxonomy_id + \" and nodes.tax_id=names.tax_id and name_class='scientific name'\");\n\n");

			buffw.write("\t\t\t\tstate = conn_tax.createStatement();\n");
			buffw.write("\t\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\t\twhile (rset.next()) {\n");
			buffw.write("\t\t\t\t\tval_name = rset.getString(\"name_txt\");\n");
			buffw.write("\t\t\t\t\tbreak;\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t} catch (SQLException e) {\n");
			buffw.write("\t\t\t\te.printStackTrace();\n");
			buffw.write("\t\t\t} finally {\n\n");

			buffw.write("\t\t\t\ttry {\n\n");

			buffw.write("\t\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t\t} catch (SQLException ex) {\n\n");
			buffw.write("\t\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_tax + ".class.getName());\n");
			buffw.write("\t\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t\t}\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif (ncbi_taxonomy_id != null && ncbi_taxonomy_id.equals(\"na\"))\n");
			buffw.write("\t\t\tval_name = \"unidentified\";\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getOrganismCommonName(String val_name, Connection conn_tax, String ncbi_taxonomy_id) {\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tboolean cont = true;\n");
			buffw.write("\t\tString _ncbi_taxonomy_id = ncbi_taxonomy_id;\n\n");

			buffw.write("\t\tif (conn_tax != null && !(" + empty_check("ncbi_taxonomy_id") + ") && ncbi_taxonomy_id.matches(\"^[0-9]+$\") && !ncbi_taxonomy_id.equals(\"0\")) {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\twhile (cont) {\n\n");

			buffw.write("\t\t\t\t\tString query = new String(\"select nodes.tax_id,nodes.parent_tax_id,rank,name_txt from nodes,names where nodes.tax_id=\" + ncbi_taxonomy_id + \" and nodes.tax_id=names.tax_id and name_class='common name'\");\n\n");

			buffw.write("\t\t\t\t\tstate = conn_tax.createStatement();\n");
			buffw.write("\t\t\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\t\t\tif (rset.getString(\"rank\").equals(\"species\")) {\n");
			buffw.write("\t\t\t\t\t\t\tval_name = rset.getString(\"name_txt\");\n");
			buffw.write("\t\t\t\t\t\t\tcont = false;\n");
			buffw.write("\t\t\t\t\t\t\tbreak;\n");
			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t\tif (rset.getString(\"rank\").equals(\"superkingdom\")) {\n");
			buffw.write("\t\t\t\t\t\t\tcont = false;\n");
			buffw.write("\t\t\t\t\t\t\tbreak;\n");
			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t\tString parent_tax_id = rset.getString(\"parent_tax_id\");\n\n");

			buffw.write("\t\t\t\t\t\tif (parent_tax_id == null || ncbi_taxonomy_id.equals(parent_tax_id)) {\n");
			buffw.write("\t\t\t\t\t\t\tcont = false;\n");
			buffw.write("\t\t\t\t\t\t\tbreak;\n");
			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t\tncbi_taxonomy_id = parent_tax_id;\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\tif (ncbi_taxonomy_id.equals(_ncbi_taxonomy_id))\n");
			buffw.write("\t\t\t\t\t\tbreak;\n\n");

			buffw.write("\t\t\t\t\t_ncbi_taxonomy_id = ncbi_taxonomy_id;\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t} catch (SQLException e) {\n");
			buffw.write("\t\t\t\te.printStackTrace();\n");
			buffw.write("\t\t\t} finally {\n\n");

			buffw.write("\t\t\t\ttry {\n\n");

			buffw.write("\t\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t\t} catch (SQLException ex) {\n\n");
			buffw.write("\t\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_tax + ".class.getName());\n");
			buffw.write("\t\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t\t}\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif (ncbi_taxonomy_id != null && ncbi_taxonomy_id.equals(\"na\"))\n");
			buffw.write("\t\t\tval_name = \"unidentified\";\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_le(String src_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_le + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.math.BigDecimal;\n");
			buffw.write("import java.math.RoundingMode;\n\n");

			buffw.write("import java.sql.*;\n");
			buffw.write("import java.util.logging.*;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_le + " {\n\n");

			buffw.write("\tpublic static String getFormulaWeight(String val_name, Connection conn_bmrb, Connection conn_le, String entry_id, String entity_id) {\n\n");

			//			buffw.write("\t\tif (!(" + empty_check("val_name") + "))\n");
			//			buffw.write("\t\t\treturn val_name;\n\n");

			buffw.write("\t\tString _val_name = null;\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tStatement state2 = null;\n");
			buffw.write("\t\tResultSet rset2 = null;\n\n");

			buffw.write("\t\tif (conn_bmrb != null && conn_le != null && !(" + empty_check("entry_id") + ") && !(" + empty_check("entity_id") + ")) {\n\n");

			buffw.write("\t\t\tString assembly_id = " + file_prefix + "_" + util_bmrb + ".getAssemblyID(null, conn_bmrb, entry_id, entity_id);\n\n");

			//			buffw.write("\t\t\tif (" + empty_check("assembly_id") + ")\n");
			//			buffw.write("\t\t\t\treturn val_name;\n\n");

			buffw.write("\t\t\tString type = " + file_prefix + "_" + util_assemblytype + ".guessType(conn_bmrb, entry_id, assembly_id);\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tBigDecimal formula_weight = BigDecimal.ZERO;\n\n");

			buffw.write("\t\t\t\tString query = new String(\"select \\\"Comp_ID\\\",count(\\\"Comp_ID\\\") from \\\"Entity_comp_index\\\" where \\\"Entry_ID\\\"='\" + entry_id + \"' and \\\"Entity_ID\\\"='\" + entity_id + \"' and \\\"Comp_ID\\\" is not null group by \\\"Comp_ID\\\" having count(\\\"Comp_ID\\\") > 0\");\n\n");

			buffw.write("\t\t\t\tstate = conn_bmrb.createStatement();\n");
			buffw.write("\t\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\t\tint total = 0;\n\n");

			buffw.write("\t\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\t\tString comp_id = rset.getString(\"Comp_ID\");\n");
			buffw.write("\t\t\t\t\tint count = rset.getInt(\"count\");\n\n");

			buffw.write("\t\t\t\t\tif (" + empty_check("comp_id") + " || count == 0)\n");

			buffw.write("\t\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\t\tcomp_id = comp_id.toUpperCase();\n\n");

			buffw.write("\t\t\t\t\tif (comp_id.equals(\"X\") && (type.startsWith(\"protein\") || type.startsWith(\"peptide\"))) {\n\n");

			buffw.write("\t\t\t\t\t\tformula_weight = null;\n");
			buffw.write("\t\t\t\t\t\tbreak;\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\tString query2 = new String(\"select formula_weight * \" + count + \" as total_weight from chem_comp where id='\" + comp_id + \"'\");\n\n");

			buffw.write("\t\t\t\t\tstate2 = conn_le.createStatement();\n");
			buffw.write("\t\t\t\t\trset2 = state2.executeQuery(query2);\n\n");

			buffw.write("\t\t\t\t\ttotal += count;\n\n");

			buffw.write("\t\t\t\t\twhile (rset2.next()) {\n\n");

			buffw.write("\t\t\t\t\t\tBigDecimal total_weight = rset2.getBigDecimal(\"total_weight\");\n");
			buffw.write("\t\t\t\t\t\tformula_weight = formula_weight.add(total_weight);\n\n");

			buffw.write("\t\t\t\t\t\tswitch (comp_id) {\n");
			buffw.write("\t\t\t\t\t\tcase \"ARG\":\n");
			buffw.write("\t\t\t\t\t\tcase \"HIS\":\n");
			buffw.write("\t\t\t\t\t\tcase \"LYS\":\n");
			buffw.write("\t\t\t\t\t\t\tformula_weight = formula_weight.subtract(new BigDecimal(1.00794 * count));\n");
			buffw.write("\t\t\t\t\t\t\tbreak;\n");
			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t\tbreak;\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t\tif (formula_weight != null) {\n\n");

			buffw.write("\t\t\t\t\tif (total > 1) {\n\n");

			buffw.write("\t\t\t\t\t\tBigDecimal water_weight = new BigDecimal(18.01528 * (total - 1));\n");
			buffw.write("\t\t\t\t\t\tformula_weight = formula_weight.subtract(water_weight);\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\tformula_weight.setScale(3, RoundingMode.HALF_EVEN);\n");
			buffw.write("\t\t\t\t\t_val_name = formula_weight.toString();\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t} catch (SQLException e) {\n");
			buffw.write("\t\t\t\te.printStackTrace();\n");
			buffw.write("\t\t\t} finally {\n\n");

			buffw.write("\t\t\t\ttry {\n\n");

			buffw.write("\t\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t\t\tif (rset2 != null)\n");
			buffw.write("\t\t\t\t\t\trset2.close();\n\n");

			buffw.write("\t\t\t\t\tif (state2 != null)\n");
			buffw.write("\t\t\t\t\t\tstate2.close();\n\n");

			buffw.write("\t\t\t\t} catch (SQLException ex) {\n\n");
			buffw.write("\t\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_le + ".class.getName());\n");
			buffw.write("\t\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t\t}\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif (_val_name != null && Double.valueOf(_val_name) > 0.0)\n");
			buffw.write("\t\t\treturn _val_name;\n\n");

			buffw.write("\t\treturn (val_name == null || !val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)?$\") || Double.valueOf(val_name) == 0.0 ? null : val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getFormulaWeight(String val_name, Connection conn_le, String pdb_code) {\n\n");

			//			buffw.write("\t\tif (!(" + empty_check("val_name") + "))\n");
			//			buffw.write("\t\t\treturn val_name;\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tif (conn_le != null && !(" + empty_check("pdb_code") + ")) {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tString query = new String(\"select formula_weight from chem_comp where id='\" + pdb_code + \"'\");\n\n");

			buffw.write("\t\t\t\tstate = conn_le.createStatement();\n");
			buffw.write("\t\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\t\tBigDecimal formula_weight = rset.getBigDecimal(\"formula_weight\");\n");
			buffw.write("\t\t\t\t\tformula_weight.setScale(3, RoundingMode.HALF_EVEN);\n");
			buffw.write("\t\t\t\t\tString weight = formula_weight.toString();\n\n");

			buffw.write("\t\t\t\t\treturn (Double.valueOf(weight) == 0.0 ? null : weight);\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t} catch (SQLException e) {\n");
			buffw.write("\t\t\t\te.printStackTrace();\n");
			buffw.write("\t\t\t} finally {\n\n");

			buffw.write("\t\t\t\ttry {\n\n");

			buffw.write("\t\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t\t} catch (SQLException ex) {\n\n");
			buffw.write("\t\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_le + ".class.getName());\n");
			buffw.write("\t\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t\t}\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_bmrb(String src_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_bmrb + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.sql.*;\n");
			buffw.write("import java.util.logging.*;\n\n");

			buffw.write("interface experiment_name_cmp {\n\n");

			buffw.write("\tboolean hexchprotectionfact(String experiment_name);\n");
			buffw.write("\tboolean hexchrate(String experiment_name);\n");
			buffw.write("\tboolean heteronuclt1(String experiment_name);\n");
			buffw.write("\tboolean heteronuclt2(String experiment_name);\n");
			buffw.write("\tboolean heteronuclt1rho(String experiment_name);\n");
			buffw.write("\tboolean heteronuclnoe(String experiment_name);\n");
			buffw.write("\tboolean homonuclnoe(String experiment_name);\n\n");

			buffw.write("}\n\n");

			buffw.write("class experiment_name_cmp_impl implements experiment_name_cmp {\n\n");

			buffw.write("\tpublic boolean hexchprotectionfact(String experiment_name) {\n");
			buffw.write("\t\treturn experiment_name.matches(\".*[Ee][Xe].*\") || experiment_name.matches(\".*H.*D.*\") || experiment_name.contains(\"D2O\");\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic boolean hexchrate(String experiment_name) {\n");
			buffw.write("\t\treturn experiment_name.matches(\".*[Ee][Xe].*\");\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic boolean heteronuclt1(String experiment_name) {\n");
			buffw.write("\t\treturn experiment_name.matches(\".*[RT]1.*\") && (experiment_name.contains(\"15\") || experiment_name.contains(\"13\"));\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic boolean heteronuclt2(String experiment_name) {\n");
			buffw.write("\t\treturn experiment_name.matches(\".*[RT]2.*\") && (experiment_name.contains(\"15\") || experiment_name.contains(\"13\"));\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic boolean heteronuclt1rho(String experiment_name) {\n");
			buffw.write("\t\treturn experiment_name.matches(\".*[RT]1[Rr].*\") && (experiment_name.contains(\"15\") || experiment_name.contains(\"13\"));\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic boolean heteronuclnoe(String experiment_name) {\n");
			buffw.write("\t\treturn experiment_name.matches(\".*[Nn][Oo][Ee][^Ss].*\") && (experiment_name.contains(\"15\") || experiment_name.contains(\"13\"));\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic boolean homonuclnoe(String experiment_name) {\n");
			buffw.write("\t\treturn experiment_name.matches(\".*[Nn][Oo][Ee].*\") && !experiment_name.contains(\"15\") && !experiment_name.contains(\"13\");\n");
			buffw.write("\t}\n\n");

			buffw.write("}\n\n");

			buffw.write("public class " + file_prefix + "_" + util_bmrb + " {\n\n");

			buffw.write("\tpublic enum experiment_type {\n\n");

			buffw.write("\t\tHExchProtectionFact, HExchRate, HeteronuclT1, HeteronuclT2, HeteronuclT1Rho, HeteronuclNOE, HomonuclNOE\n\n");

			buffw.write("\t}\n\n");

			buffw.write("\tprivate static boolean cmp_sel (experiment_type type, String experiment_name) {\n\n");

			buffw.write("\t\texperiment_name_cmp_impl cmp = new experiment_name_cmp_impl();\n\n");

			buffw.write("\t\tswitch (type) {\n");
			buffw.write("\t\tcase HExchProtectionFact:\n");
			buffw.write("\t\t\treturn cmp.hexchprotectionfact(experiment_name);\n");
			buffw.write("\t\tcase HExchRate:\n");
			buffw.write("\t\t\treturn cmp.hexchrate(experiment_name);\n");
			buffw.write("\t\tcase HeteronuclT1:\n");
			buffw.write("\t\t\treturn cmp.heteronuclt1(experiment_name);\n");
			buffw.write("\t\tcase HeteronuclT2:\n");
			buffw.write("\t\t\treturn cmp.heteronuclt2(experiment_name);\n");
			buffw.write("\t\tcase HeteronuclT1Rho:\n");
			buffw.write("\t\t\treturn cmp.heteronuclt1rho(experiment_name);\n");
			buffw.write("\t\tcase HeteronuclNOE:\n");
			buffw.write("\t\t\treturn cmp.heteronuclnoe(experiment_name);\n");
			buffw.write("\t\tcase HomonuclNOE:\n");
			buffw.write("\t\t\treturn cmp.homonuclnoe(experiment_name);\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn false;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getExperimentID(String val_name, Connection conn_bmrb, String entry_id, String sample_id) {\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tString query = new String(\"select \\\"ID\\\",\\\"Name\\\",\\\"Sample_ID\\\" from \\\"Experiment\\\" where \\\"Entry_ID\\\"='\" + entry_id + \"'\");\n\n");

			buffw.write("\t\tint experiments = 0;\n");
			buffw.write("\t\tString id = null;\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tstate = conn_bmrb.createStatement();\n");
			buffw.write("\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\tString experiment_name = rset.getString(\"Name\");\n\n");

			buffw.write("\t\t\t\tif (" + empty_check("experiment_name") + ") {\n\n");

			buffw.write("\t\t\t\t\tif (!(" + empty_check("sample_id") + ")) {\n\n");

			buffw.write("\t\t\t\t\t\tString _sample_id = rset.getString(\"Sample_ID\");\n\n");

			buffw.write("\t\t\t\t\t\tif (_sample_id != null && _sample_id.equals(sample_id)) {\n");
			buffw.write("\t\t\t\t\t\t\tid = rset.getString(\"ID\");\n");
			buffw.write("\t\t\t\t\t\t\texperiments++;\n");
			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\tcontinue;\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tif (experiments == 1)\n");
			buffw.write("\t\t\t\tval_name = id;\n\n");

			buffw.write("\t\t\telse {\n\n");

			buffw.write("\t\t\t\texperiments = 0;\n\n");

			buffw.write("\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\t\tid = rset.getString(\"ID\");\n");
			buffw.write("\t\t\t\t\texperiments++;\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t\tif (experiments == 1)\n");
			buffw.write("\t\t\t\t\tval_name = id;\n\n");

			buffw.write("\t\t\t\tif (" + empty_check("val_name") + ")\n");
			buffw.write("\t\t\t\t\tval_name = \"0\";\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_bmrb + ".class.getName());\n");
			buffw.write("\t\t\tlgr.log(Level.SEVERE, ex.getMessage(), ex);\n");
			buffw.write("\t\t\tSystem.exit(1);\n\n");

			buffw.write("\t\t} finally {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_bmrb + ".class.getName());\n");
			buffw.write("\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getExperimentID(String val_name, Connection conn_bmrb, String entry_id, String sample_id, experiment_type type) {\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tString query = new String(\"select \\\"ID\\\",\\\"Name\\\",\\\"Sample_ID\\\" from \\\"Experiment\\\" where \\\"Entry_ID\\\"='\" + entry_id + \"'\");\n\n");

			buffw.write("\t\tint experiments = 0;\n");
			buffw.write("\t\tString id = null;\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tstate = conn_bmrb.createStatement();\n");
			buffw.write("\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\tString experiment_name = rset.getString(\"Name\");\n\n");

			buffw.write("\t\t\t\tif (" + empty_check("experiment_name") + ") {\n\n");

			buffw.write("\t\t\t\t\tif (!(" + empty_check("sample_id") + ")) {\n\n");

			buffw.write("\t\t\t\t\t\tString _sample_id = rset.getString(\"Sample_ID\");\n\n");

			buffw.write("\t\t\t\t\t\tif (_sample_id != null && _sample_id.equals(sample_id)) {\n");
			buffw.write("\t\t\t\t\t\t\tid = rset.getString(\"ID\");\n");
			buffw.write("\t\t\t\t\t\t\texperiments++;\n");
			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\tcontinue;\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t\tif (cmp_sel(type, experiment_name)) {\n");
			buffw.write("\t\t\t\t\tid = rset.getString(\"ID\");\n");
			buffw.write("\t\t\t\t\texperiments++;\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tif (experiments == 1)\n");
			buffw.write("\t\t\t\tval_name = id;\n\n");

			buffw.write("\t\t\telse {\n\n");

			buffw.write("\t\t\t\texperiments = 0;\n\n");

			buffw.write("\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\t\tid = rset.getString(\"ID\");\n");
			buffw.write("\t\t\t\t\texperiments++;\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t\tif (experiments == 1)\n");
			buffw.write("\t\t\t\t\tval_name = id;\n\n");

			buffw.write("\t\t\t\tif (" + empty_check("val_name") + ")\n");
			buffw.write("\t\t\t\t\tval_name = \"0\";\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_bmrb + ".class.getName());\n");
			buffw.write("\t\t\tlgr.log(Level.SEVERE, ex.getMessage(), ex);\n");
			buffw.write("\t\t\tSystem.exit(1);\n\n");

			buffw.write("\t\t} finally {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_bmrb + ".class.getName());\n");
			buffw.write("\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getSampleID(String val_name, Connection conn_bmrb, String entry_id, String experiment_id) {\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tString query = new String(\"select \\\"ID\\\",\\\"Sample_ID\\\" from \\\"Experiment\\\" where \\\"Entry_ID\\\"='\" + entry_id + \"'\");\n\n");

			buffw.write("\t\tint samples = 0;\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tstate = conn_bmrb.createStatement();\n");
			buffw.write("\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\tsamples++;\n\n");

			buffw.write("\t\t\t\tString sample_id = rset.getString(\"Sample_ID\");\n\n");

			buffw.write("\t\t\t\tif (" + empty_check("sample_id") + ")\n");
			buffw.write("\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\tString id = rset.getString(\"ID\");\n\n");

			buffw.write("\t\t\t\tif (" + empty_check("id") + ")\n");
			buffw.write("\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\tif (experiment_id != null && experiment_id.equals(id)) {\n");
			buffw.write("\t\t\t\t\tval_name = sample_id;\n");
			buffw.write("\t\t\t\t\tbreak;\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tif (samples == 1 && (" + empty_check("val_name") + "))\n");
			buffw.write("\t\t\t\tval_name = \"1\";\n\n");

			buffw.write("\t\t\telse\n");
			buffw.write("\t\t\t\tval_name = \"0\";\n\n");

			buffw.write("\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_bmrb + ".class.getName());\n");
			buffw.write("\t\t\tlgr.log(Level.SEVERE, ex.getMessage(), ex);\n");
			buffw.write("\t\t\tSystem.exit(1);\n\n");

			buffw.write("\t\t} finally {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_bmrb + ".class.getName());\n");
			buffw.write("\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getSampleID(String val_name, Connection conn_bmrb, String entry_id) {\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tString query = new String(\"select \\\"ID\\\" from \\\"Sample\\\" where \\\"Entry_ID\\\"='\" + entry_id + \"'\");\n\n");

			buffw.write("\t\tint ids = 0;\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tstate = conn_bmrb.createStatement();\n");
			buffw.write("\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\twhile (rset.next())\n");
			buffw.write("\t\t\t\tids++;\n\n");

			buffw.write("\t\t\tif (ids == 1 && (" + empty_check("val_name") + "))\n");
			buffw.write("\t\t\t\tval_name = \"1\";\n\n");

			buffw.write("\t\t\telse\n");
			buffw.write("\t\t\t\tval_name = \"0\";\n\n");

			buffw.write("\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_bmrb + ".class.getName());\n");
			buffw.write("\t\t\tlgr.log(Level.SEVERE, ex.getMessage(), ex);\n");
			buffw.write("\t\t\tSystem.exit(1);\n\n");

			buffw.write("\t\t} finally {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_bmrb + ".class.getName());\n");
			buffw.write("\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getStudyID(String val_name, Connection conn_bmrb, String entry_id, String study_list_id) {\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tString query = new String(\"select \\\"ID\\\" from \\\"Study\\\" where \\\"Entry_ID\\\"='\" + entry_id + \"' and \\\"Study_list_ID\\\"='\" + study_list_id + \"'\");\n\n");

			buffw.write("\t\tint studies = 0;\n");
			buffw.write("\t\tString id = null;\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tstate = conn_bmrb.createStatement();\n");
			buffw.write("\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\tid = rset.getString(\"ID\");\n");
			buffw.write("\t\t\t\tstudies++;\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tif (studies == 1 && (!(" + empty_check("id") + ")))\n");
			buffw.write("\t\t\t\tval_name = id;\n");
			buffw.write("\t\t\telse if (studies <= 1)\n");
			buffw.write("\t\t\t\tval_name = \"1\";\n");
			buffw.write("\t\t\telse // if (" + empty_check("val_name") + ")\n");
			buffw.write("\t\t\t\tval_name = \"0\";\n\n");

			buffw.write("\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_bmrb + ".class.getName());\n");
			buffw.write("\t\t\tlgr.log(Level.SEVERE, ex.getMessage(), ex);\n");
			buffw.write("\t\t\tSystem.exit(1);\n\n");

			buffw.write("\t\t} finally {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_bmrb + ".class.getName());\n");
			buffw.write("\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getAssemblyID(String val_name, Connection conn_bmrb, String entry_id, String entity_id) {\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tString query = new String(\"select \\\"Assembly_ID\\\" from \\\"Entity_assembly\\\" where \\\"Entry_ID\\\"='\" + entry_id + \"' and \\\"Entity_ID\\\"='\" + entity_id + \"'\");\n\n");

			buffw.write("\t\tint assemblies = 0;\n");
			buffw.write("\t\tString assembly_id = null;\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tstate = conn_bmrb.createStatement();\n");
			buffw.write("\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\tassembly_id = rset.getString(\"Assembly_ID\");\n");
			buffw.write("\t\t\t\tassemblies++;\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tif (assemblies == 1 && (!(" + empty_check("assembly_id") + ")))\n");
			buffw.write("\t\t\t\tval_name = assembly_id;\n");
			buffw.write("\t\t\telse if (assemblies <= 1)\n");
			buffw.write("\t\t\t\tval_name = \"1\";\n");
			buffw.write("\t\t\telse // if (" + empty_check("val_name") + ")\n");
			buffw.write("\t\t\t\tval_name = \"0\";\n\n");

			buffw.write("\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_bmrb + ".class.getName());\n");
			buffw.write("\t\t\tlgr.log(Level.SEVERE, ex.getMessage(), ex);\n");
			buffw.write("\t\t\tSystem.exit(1);\n\n");

			buffw.write("\t\t} finally {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_bmrb + ".class.getName());\n");
			buffw.write("\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getSampleConditionListID(String val_name, Connection conn_bmrb, String entry_id) {\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tString query = new String(\"select \\\"ID\\\" from \\\"Sample_condition_list\\\" where \\\"Entry_ID\\\"='\" + entry_id + \"'\");\n\n");

			buffw.write("\t\tint ids = 0;\n");
			buffw.write("\t\tString id = null;\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tstate = conn_bmrb.createStatement();\n");
			buffw.write("\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\tid = rset.getString(\"ID\");\n");
			buffw.write("\t\t\t\tids++;\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tif (ids == 1 && (!(" + empty_check("id") + ")))\n");
			buffw.write("\t\t\t\tval_name = id;\n");
			buffw.write("\t\t\telse if (ids <= 1)\n");
			buffw.write("\t\t\t\tval_name = \"1\";\n");
			buffw.write("\t\t\telse // if (" + empty_check("val_name") + ")\n");
			buffw.write("\t\t\t\tval_name = \"0\";\n\n");

			buffw.write("\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_bmrb + ".class.getName());\n");
			buffw.write("\t\t\tlgr.log(Level.SEVERE, ex.getMessage(), ex);\n");
			buffw.write("\t\t\tSystem.exit(1);\n\n");

			buffw.write("\t\t} finally {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_bmrb + ".class.getName());\n");
			buffw.write("\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getAssignedChemShiftListID(String val_name, Connection conn_bmrb, String entry_id) {\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tString query = new String(\"select \\\"ID\\\" from \\\"Assigned_chem_shift_list\\\" where \\\"Entry_ID\\\"='\" + entry_id + \"'\");\n\n");

			buffw.write("\t\tint ids = 0;\n");
			buffw.write("\t\tString id = null;\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tstate = conn_bmrb.createStatement();\n");
			buffw.write("\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\tid = rset.getString(\"ID\");\n");
			buffw.write("\t\t\t\tids++;\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tif (ids == 1 && (!(" + empty_check("id") + ")))\n");
			buffw.write("\t\t\t\tval_name = id;\n");
			buffw.write("\t\t\telse if (ids <= 1)\n");
			buffw.write("\t\t\t\tval_name = \"1\";\n");
			buffw.write("\t\t\telse // if (" + empty_check("val_name") + ")\n");
			buffw.write("\t\t\t\tval_name = \"0\";\n\n");

			buffw.write("\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_bmrb + ".class.getName());\n");
			buffw.write("\t\t\tlgr.log(Level.SEVERE, ex.getMessage(), ex);\n");
			buffw.write("\t\t\tSystem.exit(1);\n\n");

			buffw.write("\t\t} finally {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_bmrb + ".class.getName());\n");
			buffw.write("\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getSpectralPeakListID(String val_name, Connection conn_bmrb, String entry_id) {\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tString query = new String(\"select \\\"ID\\\" from \\\"Spectral_peak_list\\\" where \\\"Entry_ID\\\"='\" + entry_id + \"'\");\n\n");

			buffw.write("\t\tint ids = 0;\n");
			buffw.write("\t\tString id = null;\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tstate = conn_bmrb.createStatement();\n");
			buffw.write("\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\tid = rset.getString(\"ID\");\n");
			buffw.write("\t\t\t\tids++;\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tif (ids == 1 && (!(" + empty_check("id") + ")))\n");
			buffw.write("\t\t\t\tval_name = id;\n");
			buffw.write("\t\t\telse if (ids <= 1)\n");
			buffw.write("\t\t\t\tval_name = \"1\";\n");
			buffw.write("\t\t\telse // if (" + empty_check("val_name") + ")\n");
			buffw.write("\t\t\t\tval_name = \"0\";\n\n");

			buffw.write("\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_bmrb + ".class.getName());\n");
			buffw.write("\t\t\tlgr.log(Level.SEVERE, ex.getMessage(), ex);\n");
			buffw.write("\t\t\tSystem.exit(1);\n\n");

			buffw.write("\t\t} finally {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_bmrb + ".class.getName());\n");
			buffw.write("\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_dbname(String src_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_dbname + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("public class " + file_prefix + "_" + util_dbname + " {\n\n");

			buffw.write("\tpublic static String guessDbName(String val_name, String accession_code) {\n\n");

			// Swiss-Prot accession number format https://web.expasy.org/docs/userman.html
			buffw.write("\t\tif (accession_code.matches(\"^[A-NR-Z][0-9][A-Z][A-Z0-9][A-Z0-9][0-9]$\") || accession_code.matches(\"^[OPQ][0-9][A-Z0-9][A-Z0-9][A-Z0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"SP\";\n\n");

			// GenBank accession number format (nucleotide EST) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^[HNTRW][0-9][0-9][0-9][0-9][0-9]$\") || accession_code.matches(\"^A[AIW]|^B[EFGIMQU]|^C[ABDFKNOVX]|^D[NRTVWY]|^E[BCEGHLSVWXY]|^F[CDEFGKL]|^G[DEHORTW]|^H[OS]|^J[GKZ][0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide Direct submissions) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^U[0-9][0-9][0-9][0-9][0-9]$\") || accession_code.matches(\"^A[FY]|^DQ|^E[FU]|^FJ|^G[QU]|^H[MQ]|^J[FNQX]|^KC[0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide Genome project data) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^AE|^C[PY][0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide GSS) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^B[0-9][0-9][0-9][0-9][0-9]$\") || accession_code.matches(\"^A[QZ]|^B[HZ]|^C[CEGLWZ]|^D[UX]|^E[DIJKRT]|^F[HI]|^GS|^H[NR]|^J[JMSY][0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide HTGS) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^AC|^DP[0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide Patents) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^I[0-9][0-9][0-9][0-9][0-9]$\") || accession_code.matches(\"^AR|^DZ|^EA|^G[CPVXYZ]|^H[JKL][0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide STS) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^G[0-9][0-9][0-9][0-9][0-9]$\") || accession_code.matches(\"^BV|^GF[0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide TPA) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^BK[0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide TPA CON division) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^BL|^G[JK][0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide TSA) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^EZ|^HP|^J[ILOPRTUVW]|^KA[0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide From journal scanning) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^S[0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide From GSDB) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^AD[0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide Segmented set header) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^AH[0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide Other - not currently being used) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^AS[0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide MGC project) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^BC[0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide FLI-cDNA projects) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^BT[0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide from GSDB direct submissions) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^[JKLM][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide WGS) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^A[A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9]+\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide WGS TPA) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^D[A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9]+\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide TSA) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^G[A-Z][A-Z][A-Z][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (protein from Protein ID) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^A[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (protein from TPA Protein ID) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^D[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (protein from WGS Protein ID) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^E[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (protein from TPA WGS Protein ID) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^H[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (protein from TSA Protein ID) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^J[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// DDBJ accession number format (nucleotide CON division) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^BA|^DF|^DG[0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (nucleotide EST) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^C[0-9][0-9][0-9][0-9][0-9]$\") || accession_code.matches(\"^A[TUV]|^B[BJPWY]|^C[IJ]|^D[ABCK]|^F[SY]|^H[XY][0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (nucleotide Direct submissions) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^D[0-9][0-9][0-9][0-9][0-9]$\") || accession_code.matches(\"^AB[0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (nucleotide Genome project data) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^AP[0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (nucleotide Chimpanzee genome data) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^BS[0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (nucleotide GSS) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^AG|^D[EH]|^FT|^GA[0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (nucleotide cDNA projects) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^AK[0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (nucleotide Patents) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^E[0-9][0-9][0-9][0-9][0-9]$\") || accession_code.matches(\"^BD|^D[DIJLM]|^F[UVWZ]|^GB|^H[VWZ][0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (nucleotide TPA) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^BR[0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (nucleotide TPA CON division) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^H[TU][0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (nucleotide TSA) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^FX[0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (nucleotide WGS) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^B[A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9]+\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (nucleotide WGS TPA) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^E[A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9]+\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (nucleotide MGA) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^A[A-Z][A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (protein from Protein ID) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^B[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (protein from TPA Protein ID) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^F[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (protein from WGS Protein ID) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^G[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (protein from TPA WGS Protein ID) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^I[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// EMBL accession number format (nucleotide CON division) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^AN[0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"EMBL\";\n\n");

			// EMBL accession number format (nucleotide EST) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^F[0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"EMBL\";\n\n");

			// EMBL accession number format (nucleotide Direct submissions) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^[VXYZ][0-9][0-9][0-9][0-9][0-9]$\") || accession_code.matches(\"^A[JM]|^F[MNO]|^H[EFG][0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"EMBL\";\n\n");

			// EMBL accession number format (nucleotide Genome project data) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^AL|^BX|^C[RTU]|^F[PQR][0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"EMBL\";\n\n");

			// EMBL accession number format (nucleotide Patents (nucleotide only)) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^A[0-9][0-9][0-9][0-9][0-9]$\") || accession_code.matches(\"^AX|^C[QS]|^FB|^G[MN]|^H[ABCDHI]|^J[ABCDE][0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"EMBL\";\n\n");

			// EMBL accession number format (nucleotide TPA) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^BN[0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"EMBL\";\n\n");

			// EMBL accession number format (nucleotide WGS) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^C[A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9]+\"))\n");
			buffw.write("\t\t\tval_name = \"EMBL\";\n\n");

			// EMBL accession number format (protein from Protein ID) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^C[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"EMBL\";\n\n");

			// NCBI accession number format (nucleotide CON division) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^C[HM]|^DS|^E[MNPQ]|^FA|^G[GL]|^JH|^KB[0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"NCBI\";\n\n");

			// RefSeq accession number format (Genomic Mixed) https://ncbi.nlm.nih.gov/books/NBK21091/
			buffw.write("\t\tif (accession_code.matches(\"^AC_[0-9][0-9][0-9][0-9][0-9][0-9]$\") || accession_code.matches(\"^N[CG]_[0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"REF\";\n\n");

			// RefSeq accession number format (Genomic Automated) https://ncbi.nlm.nih.gov/books/NBK21091/
			buffw.write("\t\tif (accession_code.matches(\"^N[STW]_[0-9][0-9][0-9][0-9][0-9][0-9]$\") || accession_code.matches(\"^NW_[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$\") || accession_code.matches(\"^NZ_[A-Z][A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"REF\";\n\n");

			// RefSeq accession number format (Protein Mixed) https://ncbi.nlm.nih.gov/books/NBK21091/
			buffw.write("\t\tif (accession_code.matches(\"^[ANY]P_[0-9][0-9][0-9][0-9][0-9][0-9]$\") || accession_code.matches(\"^[NY]P_[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"REF\";\n\n");

			// RefSeq accession number format (Protein Automated) https://ncbi.nlm.nih.gov/books/NBK21091/
			buffw.write("\t\tif (accession_code.matches(\"^XP_[0-9][0-9][0-9][0-9][0-9][0-9]$\") || accession_code.matches(\"^ZP_[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$\") || accession_code.matches(\"^XP_[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"REF\";\n\n");

			// RefSeq accession number format (mRNA Mixed) https://ncbi.nlm.nih.gov/books/NBK21091/
			buffw.write("\t\tif (accession_code.matches(\"^NM_[0-9][0-9][0-9][0-9][0-9][0-9]$\") || accession_code.matches(\"^NM_[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"REF\";\n\n");

			// RefSeq accession number format (mRNA Automated) https://ncbi.nlm.nih.gov/books/NBK21091/
			buffw.write("\t\tif (accession_code.matches(\"^XM_[0-9][0-9][0-9][0-9][0-9][0-9]$\") || accession_code.matches(\"^XM_[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"REF\";\n\n");

			// RefSeq accession number format (RNA Mixed) https://ncbi.nlm.nih.gov/books/NBK21091/
			buffw.write("\t\tif (accession_code.matches(\"^NR_[0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"REF\";\n\n");

			// RefSeq accession number format (RNA Automated) https://ncbi.nlm.nih.gov/books/NBK21091/
			buffw.write("\t\tif (accession_code.matches(\"^XR_[0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"REF\";\n\n");

			// PDB accession number format http://pdbwiki.org/wiki/PDB_code
			buffw.write("\t\tif ((" + empty_check("val_name") + ") && accession_code.matches(\"^[0-9][0-9A-Za-z][0-9A-Za-z][0-9A-Za-z]$\") && (accession_code.matches(\"^[0-9][A-Za-z][0-9A-Za-z][0-9A-Za-z]$\") || accession_code.matches(\"^[0-9][0-9A-Za-z][A-Za-z][0-9A-Za-z]$\") || accession_code.matches(\"^[0-9][0-9A-Za-z][0-9A-Za-z][A-Za-z]$\")))\n");
			buffw.write("\t\t\tval_name = \"PDB\";\n\n");

			// gene name tolerance

			// GenBank accession number format (nucleotide EST) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^[HNTRW][0-9][0-9][0-9][0-9][0-9].[1-9]$\") || accession_code.matches(\"^A[AIW]|^B[EFGIMQU]|^C[ABDFKNOVX]|^D[NRTVWY]|^E[BCEGHLSVWXY]|^F[CDEFGKL]|^G[DEHORTW]|^H[OS]|^J[GKZ][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide Direct submissions) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^U[0-9][0-9][0-9][0-9][0-9].[1-9]$\") || accession_code.matches(\"^A[FY]|^DQ|^E[FU]|^FJ|^G[QU]|^H[MQ]|^J[FNQX]|^KC[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide Genome project data) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^AE|^C[PY][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide GSS) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^B[0-9][0-9][0-9][0-9][0-9].[1-9]$\") || accession_code.matches(\"^A[QZ]|^B[HZ]|^C[CEGLWZ]|^D[UX]|^E[DIJKRT]|^F[HI]|^GS|^H[NR]|^J[JMSY][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide HTGS) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^AC|^DP[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide Patents) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^I[0-9][0-9][0-9][0-9][0-9].[1-9]$\") || accession_code.matches(\"^AR|^DZ|^EA|^G[CPVXYZ]|^H[JKL][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide STS) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^G[0-9][0-9][0-9][0-9][0-9].[1-9]$\") || accession_code.matches(\"^BV|^GF[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide TPA) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^BK[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide TPA CON division) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^BL|^G[JK][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide TSA) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^EZ|^HP|^J[ILOPRTUVW]|^KA[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide From journal scanning) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^S[0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide From GSDB) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^AD[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide Segmented set header) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^AH[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide Other - not currently being used) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^AS[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide MGC project) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^BC[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide FLI-cDNA projects) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^BT[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide from GSDB direct submissions) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^[JKLM][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide WGS) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^A[A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9]+.[1-9]\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide WGS TPA) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^D[A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9]+.[1-9]\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (nucleotide TSA) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^G[A-Z][A-Z][A-Z][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (protein from Protein ID) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^A[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (protein from TPA Protein ID) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^D[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (protein from WGS Protein ID) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^E[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (protein from TPA WGS Protein ID) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^H[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// GenBank accession number format (protein from TSA Protein ID) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^J[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"GB\";\n\n");

			// DDBJ accession number format (nucleotide CON division) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^BA|^DF|^DG[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (nucleotide EST) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^C[0-9][0-9][0-9][0-9][0-9].[1-9]$\") || accession_code.matches(\"^A[TUV]|^B[BJPWY]|^C[IJ]|^D[ABCK]|^F[SY]|^H[XY][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (nucleotide Direct submissions) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^D[0-9][0-9][0-9][0-9][0-9].[1-9]$\") || accession_code.matches(\"^AB[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (nucleotide Genome project data) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^AP[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (nucleotide Chimpanzee genome data) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^BS[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (nucleotide GSS) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^AG|^D[EH]|^FT|^GA[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (nucleotide cDNA projects) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^AK[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (nucleotide Patents) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^E[0-9][0-9][0-9][0-9][0-9].[1-9]$\") || accession_code.matches(\"^BD|^D[DIJLM]|^F[UVWZ]|^GB|^H[VWZ][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (nucleotide TPA) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^BR[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (nucleotide TPA CON division) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^H[TU][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (nucleotide TSA) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^FX[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (nucleotide WGS) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^B[A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9]+.[1-9]\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (nucleotide WGS TPA) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^E[A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9]+.[1-9]\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (nucleotide MGA) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^A[A-Z][A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (protein from Protein ID) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^B[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (protein from TPA Protein ID) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^F[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (protein from WGS Protein ID) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^G[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// DDBJ accession number format (protein from TPA WGS Protein ID) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^I[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"DBJ\";\n\n");

			// EMBL accession number format (nucleotide CON division) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^AN[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"EMBL\";\n\n");

			// EMBL accession number format (nucleotide EST) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^F[0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"EMBL\";\n\n");

			// EMBL accession number format (nucleotide Direct submissions) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^[VXYZ][0-9][0-9][0-9][0-9][0-9].[1-9]$\") || accession_code.matches(\"^A[JM]|^F[MNO]|^H[EFG][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"EMBL\";\n\n");

			// EMBL accession number format (nucleotide Genome project data) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^AL|^BX|^C[RTU]|^F[PQR][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"EMBL\";\n\n");

			// EMBL accession number format (nucleotide Patents (nucleotide only)) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^A[0-9][0-9][0-9][0-9][0-9].[1-9]$\") || accession_code.matches(\"^AX|^C[QS]|^FB|^G[MN]|^H[ABCDHI]|^J[ABCDE][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"EMBL\";\n\n");

			// EMBL accession number format (nucleotide TPA) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^BN[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"EMBL\";\n\n");

			// EMBL accession number format (nucleotide WGS) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^C[A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9]+.[1-9]\"))\n");
			buffw.write("\t\t\tval_name = \"EMBL\";\n\n");

			// EMBL accession number format (protein from Protein ID) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^C[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"EMBL\";\n\n");

			// NCBI accession number format (nucleotide CON division) https://www.ncbi.nlm.nih.gov/Sequin/acc.html
			buffw.write("\t\tif (accession_code.matches(\"^C[HM]|^DS|^E[MNPQ]|^FA|^G[GL]|^JH|^KB[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"NCBI\";\n\n");

			// RefSeq accession number format (Genomic Mixed) https://ncbi.nlm.nih.gov/books/NBK21091/
			buffw.write("\t\tif (accession_code.matches(\"^AC_[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\") || accession_code.matches(\"^N[CG]_[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"REF\";\n\n");

			// RefSeq accession number format (Genomic Automated) https://ncbi.nlm.nih.gov/books/NBK21091/
			buffw.write("\t\tif (accession_code.matches(\"^N[STW]_[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\") || accession_code.matches(\"^NW_[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\") || accession_code.matches(\"^NZ_[A-Z][A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"REF\";\n\n");

			// RefSeq accession number format (Protein Mixed) https://ncbi.nlm.nih.gov/books/NBK21091/
			buffw.write("\t\tif (accession_code.matches(\"^[ANY]P_[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\") || accession_code.matches(\"^[NY]P_[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"REF\";\n\n");

			// RefSeq accession number format (Protein Automated) https://ncbi.nlm.nih.gov/books/NBK21091/
			buffw.write("\t\tif (accession_code.matches(\"^XP_[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\") || accession_code.matches(\"^ZP_[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\") || accession_code.matches(\"^XP_[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"REF\";\n\n");

			// RefSeq accession number format (mRNA Mixed) https://ncbi.nlm.nih.gov/books/NBK21091/
			buffw.write("\t\tif (accession_code.matches(\"^NM_[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\") || accession_code.matches(\"^NM_[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"REF\";\n\n");

			// RefSeq accession number format (mRNA Automated) https://ncbi.nlm.nih.gov/books/NBK21091/
			buffw.write("\t\tif (accession_code.matches(\"^XM_[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\") || accession_code.matches(\"^XM_[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"REF\";\n\n");

			// RefSeq accession number format (RNA Mixed) https://ncbi.nlm.nih.gov/books/NBK21091/
			buffw.write("\t\tif (accession_code.matches(\"^NR_[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"REF\";\n\n");

			// RefSeq accession number format (RNA Automated) https://ncbi.nlm.nih.gov/books/NBK21091/
			buffw.write("\t\tif (accession_code.matches(\"^XR_[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"REF\";\n\n");

			// PDB accession number format http://pdbwiki.org/wiki/PDB_code
			buffw.write("\t\tif ((" + empty_check("val_name") + ") && accession_code.matches(\"^[0-9][0-9A-Za-z][0-9A-Za-z][0-9A-Za-z]_[A-Za-z]$\") && (accession_code.matches(\"^[0-9][A-Za-z][0-9A-Za-z][0-9A-Za-z]_[A-Za-z]$\") || accession_code.matches(\"^[0-9][0-9A-Za-z][A-Za-z][0-9A-Za-z]_[A-Za-z]$\") || accession_code.matches(\"^[0-9][0-9A-Za-z][0-9A-Za-z][A-Za-z]_[A-Za-z]$\")))\n");
			buffw.write("\t\t\tval_name = \"PDB\";\n\n");

			// BMRB accession number format
			buffw.write("\t\tif (val_name != null && val_name.equalsIgnoreCase(\"PDB\") && accession_code.matches(\"^[1-2][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\tval_name = \"BMRB\";\n\n");

			buffw.write("\t\tif (val_name != null && val_name.equals(\"NCBI\") && !accession_code.matches(\"^C[HM]|^DS|^E[MNPQ]|^FA|^G[GL]|^JH|^KB[0-9][0-9][0-9][0-9][0-9][0-9]$\") && !accession_code.matches(\"^C[HM]|^DS|^E[MNPQ]|^FA|^G[GL]|^JH|^KB[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$\"))\n");
			buffw.write("\t\t\tval_name = null;");

			buffw.write("\t\treturn val_name;\n");

			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_alignmentblosom(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_alignmentblosom + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("public class " + file_prefix + "_" + util_alignmentblosom + " {\n\n");

			buffw.write("\tprotected double gap_open_cost = 10.0;\n");
			buffw.write("\tprotected double gap_ext_cost = 0.1;\n");
			buffw.write("\tprotected final double new_gap_cost = gap_open_cost + gap_ext_cost;\n");
			buffw.write("\tprotected final double large_number = 1000000.0;\n\n");

			buffw.write("\tprotected final double BLOSUM[][] =\n");
			buffw.write("\t\t{ // the blosum 62 scoring matrix\n");
			buffw.write("\t\t\t{4, 0, 0, -2, -1, -2, 0, -2, -1, 0, -1, -1, -1, -2, 0, -1, -1, -1, 1, 0, 0, 0, -3, 0, -2}, // A\n");
			buffw.write("\t\t\t{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},\n");
			buffw.write("\t\t\t{0, 0, 9, -3, -4, -2, -3, -3, -1, 0, -3, -1, -1, -3, 0, -3, -3, -3, -1, -1, 0, -1, -2, 0, -2}, // C\n");
			buffw.write("\t\t\t{-2, 0, -3, 6, 2, -3, -1, -1, -3, 0, -1, -4, -3, 1, 0, -1, 0, -2, 0, -1, 0, -3, -4, 0, -3}, // D\n");
			buffw.write("\t\t\t{-1, 0, -4, 2, 5, -3, -2, 0, -3, 0, 1, -3, -2, 0, 0, -1, 2, 0, 0, -1, 0, -2, -3, 0, -2}, // E\n");
			buffw.write("\t\t\t{-2, 0, -2, -3, -3, 6, -3, -1, 0, 0, -3, 0, 0, -3, 0, -4, -3, -3, -2, -2, 0, -1, 1, 0, 3}, // F\n");
			buffw.write("\t\t\t{0, 0, -3, -1, -2, -3, 6, -2, -4, 0, -2, -4, -3, 0, 0, -2, -2, -2, 0, -2, 0, -3, -2, 0, -3}, // G\n");
			buffw.write("\t\t\t{-2, 0, -3, -1, 0, -1, -2, 8, -3, 0, -1, -3, -2, 1, 0, -2, 0, 0, -1, -2, 0, -3, -2, 0, 2}, // H\n");
			buffw.write("\t\t\t{-1, 0, -1, -3, -3, 0, -4, -3, 4, 0, -3, 2, 1, -3, 0, -3, -3, -3, -2, -1, 0, 3, -3, 0, -1}, // I\n");
			buffw.write("\t\t\t{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},\n");
			buffw.write("\t\t\t{-1, 0, -3, -1, 1, -3, -2, -1, -3, 0, 5, -2, -1, 0, 0, -1, 1, 2, 0, -1, 0, -2, -3, 0, -2}, // K\n");
			buffw.write("\t\t\t{-1, 0, -1, -4, -3, 0, -4, -3, 2, 0, -2, 4, 2, -3, 0, -3, -2, -2, -2, -1, 0, 1, -2, 0, -1}, // L\n");
			buffw.write("\t\t\t{-1, 0, -1, -3, -2, 0, -3, -2, 1, 0, -1, 2, 5, -2, 0, -2, 0, -1, -1, -1, 0, 1, -1, 0, -1}, // M\n");
			buffw.write("\t\t\t{-2, 0, -3, 1, 0, -3, 0, 1, -3, 0, 0, -3, -2, 6, 0, -2, 0, 0, 1, 0, 0, -3, -4, 0, -2}, // N\n");
			buffw.write("\t\t\t{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},\n");
			buffw.write("\t\t\t{-1, 0, -3, -1, -1, -4, -2, -2, -3, 0, -1, -3, -2, -2, 0, 7, -1, -2, -1, -1, 0, -2, -4, 0, -3}, // P\n");
			buffw.write("\t\t\t{-1, 0, -3, 0, 2, -3, -2, 0, -3, 0, 1, -2, 0, 0, 0, -1, 5, 1, 0, -1, 0, -2, -2, 0, -1}, // Q\n");
			buffw.write("\t\t\t{-1, 0, -3, -2, 0, -3, -2, 0, -3, 0, 2, -2, -1, 0, 0, -2, 1, 5, -1, -1, 0, -3, -3, 0, -2}, // R\n");
			buffw.write("\t\t\t{1, 0, -1, 0, 0, -2, 0, -1, -2, 0, 0, -2, -1, 1, 0, -1, 0, -1, 4, 1, 0, -2, -3, 0, -2}, // S\n");
			buffw.write("\t\t\t{0, 0, -1, -1, -1, -2, -2, -2, -1, 0, -1, -1, -1, 0, 0, -1, -1, -1, 1, 5, 0, 0, -2, 0, -2}, // T\n");
			buffw.write("\t\t\t{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0}, // U (dummy for Uracil)\n");
			buffw.write("\t\t\t{0, 0, -1, -3, -2, -1, -3, -3, 3, 0, -2, 1, 1, -3, 0, -2, -2, -3, -2, 0, 0, 4, -3, 0, -1}, // V\n");
			buffw.write("\t\t\t{-3, 0, -2, -4, -3, 1, -2, -2, -3, 0, -3, -2, -1, -4, 0, -4, -2, -3, -3, -2, 0, -3, 11, 0, 2}, // W\n");
			buffw.write("\t\t\t{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, // X (dummy for uncommon residues)\n");
			buffw.write("\t\t\t{-2, 0, -2, -3, -2, 3, -3, 2, -1, 0, -2, -1, -1, -2, 0, -3, -1, -2, -2, -2, 0, -1, 2, 0, 7} // Y\n");
			buffw.write("\t\t};\n\n");

			buffw.write("\tprivate int n = 0, m = 0;\n\n");

			buffw.write("\tprivate double[][] r = null, t = null, s = null;\n\n");

			buffw.write("\tpublic String S1 = null, S2 = null;\n");
			buffw.write("\tpublic double score = -large_number;\n");
			buffw.write("\tpublic int gap = 0;\n\n");

			buffw.write("\t" + file_prefix + "_" + util_alignmentblosom + "(char[] s1, char[] s2) {\n\n");

			buffw.write("\t\tn = s1.length + 1;\n");
			buffw.write("\t\tm = s2.length + 1;\n\n");

			buffw.write("\t\tr = new double [n][m];\n");
			buffw.write("\t\tt = new double [n][m];\n");
			buffw.write("\t\ts = new double [n][m];\n\n");

			buffw.write("\t\tS1 = String.valueOf(s1);\n");
			buffw.write("\t\tS2 = String.valueOf(s2);\n\n");

			buffw.write("\t\tint i, j;\n\n");

			buffw.write("\t\tr[0][0] = t[0][0] = s[0][0] = 0;\n\n");

			buffw.write("\t\tfor (i = 0; i < n; i++) {\n");
			buffw.write("\t\t\tr[i][0] = -large_number;\n");
			buffw.write("\t\t\ts[i][0] = t[i][0] = -gap_open_cost - i * gap_ext_cost;\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\tfor (j = 1; j < m; j++) {\n");
			buffw.write("\t\t\tt[0][j] = -large_number;\n");
			buffw.write("\t\t\ts[0][j] = r[0][j] = -gap_open_cost - j * gap_ext_cost;\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\tfor (i = 1; i < n; i++) {\n");
			buffw.write("\t\t\tfor (j = 1; j < m; j++) {\n");
			buffw.write("\t\t\t\tr[i][j] = Math.max(r[i][j - 1] - gap_ext_cost, s[i][j - 1] - new_gap_cost);\n");
			buffw.write("\t\t\t\tt[i][j] = Math.max(t[i - 1][j] - gap_ext_cost, s[i - 1][j] - new_gap_cost);\n");
			buffw.write("\t\t\t\ts[i][j] = max3(s[i - 1][j - 1] + (s1[i - 1] - 'A' >= 0 && s2[j - 1] - 'A' >= 0 ? BLOSUM[s1[i - 1] - 'A'][s2[j - 1] - 'A'] : 0), r[i][j], t[i][j]);\n");
			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\tgap = 0;\n\n");

			buffw.write("\t\ti = n - 1;\n");
			buffw.write("\t\tj = m - 1;\n\n");

			buffw.write("\t\twhile (i > 0 || j > 0) {\n\n");

			buffw.write("\t\t\tif (s[i][j] == r[i][j]) {\n");
			buffw.write("\t\t\t\tS1 = S1.substring(0, i) + \"?\" + S1.substring(i, S1.length());\n");
			buffw.write("\t\t\t\tgap++;\n");
			buffw.write("\t\t\t\tj--;\n");
			buffw.write("\t\t\t} else if (s[i][j] == t[i][j]) {\n");
			buffw.write("\t\t\t\tS2 = S2.substring(0, j) + \"?\" + S2.substring(j, S2.length());\n");
			buffw.write("\t\t\t\tgap++;\n");
			buffw.write("\t\t\t\ti--;\n");
			buffw.write("\t\t\t} else {\n");
			buffw.write("\t\t\t\ti--; j--;\n");
			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\tscore = s[n - 1][m - 1];\n");
			buffw.write("\t}\n\n");

			buffw.write("\tprivate double max3(double x, double y, double z) {\n");
			buffw.write("\t\treturn x > y ? Math.max(x,z) : Math.max(y,z);\n");
			buffw.write("\t}\n}");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_alignmentstrict(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_alignmentstrict + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("public class " + file_prefix + "_" + util_alignmentstrict + " {\n\n");

			buffw.write("\tprotected int match_reward = 1;\n");
			buffw.write("\tprotected int mismatch_penalty = -1;\n");
			buffw.write("\tprotected int gap_open_cost = 0;\n");
			buffw.write("\tprotected int gap_ext_cost = 0;\n");
			buffw.write("\tprotected int new_gap_cost = gap_open_cost + gap_ext_cost;\n");
			buffw.write("\tprotected int large_number = 1000000;\n\n");

			buffw.write("\tprivate int n = 0, m = 0;\n\n");

			buffw.write("\tprivate int[][] r = null, t = null, s = null;\n\n");

			buffw.write("\tpublic String S1 = null, S2 = null;\n");
			buffw.write("\tpublic int score = -large_number;\n\n");

			buffw.write("\t" + file_prefix + "_" + util_alignmentstrict + "(char[] s1, char[] s2) {\n\n");

			buffw.write("\t\tn = s1.length + 1;\n");
			buffw.write("\t\tm = s2.length + 1;\n\n");

			buffw.write("\t\tr = new int [n][m];\n");
			buffw.write("\t\tt = new int [n][m];\n");
			buffw.write("\t\ts = new int [n][m];\n\n");

			buffw.write("\t\tS1 = String.valueOf(s1);\n");
			buffw.write("\t\tS2 = String.valueOf(s2);\n\n");

			buffw.write("\t\tint i, j;\n\n");

			buffw.write("\t\tr[0][0] = t[0][0] = s[0][0] = 0;\n\n");

			buffw.write("\t\tfor (i = 0; i < n; i++) {\n");
			buffw.write("\t\t\tr[i][0] = -large_number;\n");
			buffw.write("\t\t\ts[i][0] = t[i][0] = -gap_open_cost - i * gap_ext_cost;\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\tfor (j = 1; j < m; j++) {\n");
			buffw.write("\t\t\tt[0][j] = -large_number;\n");
			buffw.write("\t\t\ts[0][j] = r[0][j] = -gap_open_cost - j * gap_ext_cost;\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\tfor (i = 1; i < n; i++) {\n");
			buffw.write("\t\t\tfor (j = 1; j < m; j++) {\n");
			buffw.write("\t\t\t\tr[i][j] = Math.max(r[i][j - 1] - gap_ext_cost, s[i][j - 1] - new_gap_cost);\n");
			buffw.write("\t\t\t\tt[i][j] = Math.max(t[i - 1][j] - gap_ext_cost, s[i - 1][j] - new_gap_cost);\n");
			buffw.write("\t\t\t\ts[i][j] = max3(s[i - 1][j - 1] + (s1[i - 1] == s2[j - 1] ? match_reward : mismatch_penalty), r[i][j], t[i][j]);\n");
			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\ti = n - 1;\n");
			buffw.write("\t\tj = m - 1;\n\n");

			buffw.write("\t\twhile (i > 0 || j > 0) {\n\n");

			buffw.write("\t\t\tif (s[i][j] == r[i][j]) {\n");
			buffw.write("\t\t\t\tS1 = S1.substring(0, i) + \"?\" + S1.substring(i, S1.length());\n");
			buffw.write("\t\t\t\tj--;\n");
			buffw.write("\t\t\t} else if (s[i][j] == t[i][j]) {\n");
			buffw.write("\t\t\t\tS2 = S2.substring(0, j) + \"?\" + S2.substring(j, S2.length());\n");
			buffw.write("\t\t\t\ti--;\n");
			buffw.write("\t\t\t} else {\n");
			buffw.write("\t\t\t\ti--; j--;\n");
			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\tscore = s[n - 1][m - 1];\n");
			buffw.write("\t}\n\n");

			buffw.write("\tprivate int max3(int x, int y, int z) {\n");
			buffw.write("\t\treturn x > y ? Math.max(x,z) : Math.max(y,z);\n");
			buffw.write("\t}\n}");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_assembly(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_assembly + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_assembly + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map_thiol_state = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "assembly.thiol_state.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getThiolState(String val_name) {\n");
			buffw.write("\t\treturn (String) map_thiol_state.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_non_standard_bonds = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "assembly.non_standard_bonds.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getNonStandardBonds(String val_name) {\n");
			buffw.write("\t\treturn (String) map_non_standard_bonds.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String checkECNumber(String ec_number, String entry_id) {\n\n");

			buffw.write("\t\tfinal String[][] ecnumtbl = {\n\n");

			FileReader filer = new FileReader(xsd_dir_name + "assembly.enzyme_commission_number");
			BufferedReader bufferr = new BufferedReader(filer);

			String line = null;

			while ((line = bufferr.readLine()) != null) {

				if (!line.matches(".*;.*;.*"))
					continue;

				String[] elem = line.split(";");

				buffw.write("\t\t\t\t{\"" + elem[0] + "\", \"" + elem[1] + "\", \"" + elem[2] + "\"},\n");

			}

			bufferr.close();
			filer.close();

			buffw.write("\n\t\t};\n\n");

			buffw.write("\t\tfor (int i = 0; i < ecnumtbl.length; i++) {\n\n");

			buffw.write("\t\t\tif (ecnumtbl[i][0].equals(ec_number) && ecnumtbl[i][1].equals(entry_id))\n");
			buffw.write("\t\t\t\treturn ecnumtbl[i][2];\n\n");

			buffw.write("\t\t\tif (ecnumtbl[i][0].isEmpty() && (" + empty_check("ec_number") + ") && ecnumtbl[i][1].equals(entry_id))\n");
			buffw.write("\t\t\t\treturn ecnumtbl[i][2];\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn ec_number;\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_assemblydblink(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_assemblydblink + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_assemblydblink + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map_database_code = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "assembly_db_link.database_code.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getDatabaseCode(String val_name, String accession_code) {\n\n");

			buffw.write("\t\tif (accession_code != null)\n");
			buffw.write("\t\t\tval_name = " + file_prefix + "_" + util_dbname + ".guessDbName(val_name, accession_code);\n\n");

			buffw.write("\t\tif (val_name != null && val_name.equals(\"NCBI\") && accession_code != null && accession_code.matches(\"^[0-9]+$\"))\n");
			buffw.write("\t\t\treturn null;\n\n");

			buffw.write("\t\treturn (String) map_database_code.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_entry_experimental_method = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "assembly_db_link.entry_experimental_method.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getEntryExperimentalMethod(String val_name) {\n");
			buffw.write("\t\treturn (String) map_entry_experimental_method.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_author_supplied = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "assembly_db_link.author_supplied.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getAuthorSupplied(String val_name) {\n");
			buffw.write("\t\treturn (String) map_author_supplied.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String checkAccessionCode(String accession_code, String entry_id) {\n\n");

			buffw.write("\t\tfinal String[][] accodetbl = {\n\n");

			FileReader filer = new FileReader(xsd_dir_name + "assembly_db_link.accession_code");
			BufferedReader bufferr = new BufferedReader(filer);

			String line = null;

			while ((line = bufferr.readLine()) != null) {

				if (!line.matches(".*,.*,.*"))
					continue;

				String[] elem = line.split(",");

				buffw.write("\t\t\t\t{\"" + elem[0] + "\", \"" + elem[1] + "\", \"" + elem[2] + "\"},\n");

			}

			bufferr.close();
			filer.close();

			buffw.write("\n\t\t};\n\n");

			buffw.write("\t\tfor (int i = 0; i < accodetbl.length; i++) {\n\n");

			buffw.write("\t\t\tif (accodetbl[i][0].equals(accession_code) && accodetbl[i][1].equals(entry_id))\n");
			buffw.write("\t\t\t\treturn accodetbl[i][2];\n\n");

			buffw.write("\t\t\tif (accodetbl[i][0].isEmpty() && (" + empty_check("accession_code") + ") && accodetbl[i][1].equals(entry_id))\n");
			buffw.write("\t\t\t\treturn accodetbl[i][2];\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif (accession_code == null)\n");
			buffw.write("\t\t\treturn null;\n\n");

			buffw.write("\t\treturn accession_code.toUpperCase();\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_assemblyinteraction(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_assemblyinteraction + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_assemblyinteraction + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "assembly_interaction.mol_interaction_type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getMolInteractionType(String val_name) {\n");
			buffw.write("\t\treturn (String) map.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_assemblysystematicname(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_assemblysystematicname + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_assemblysystematicname + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "assembly_systematic_name.naming_system.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getNamingSystem(String val_name) {\n");
			buffw.write("\t\treturn (String) map.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_assemblytype(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_assemblytype + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.sql.*;\n\n");

			buffw.write("import java.util.List;\n");
			buffw.write("import java.util.ArrayList;\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_assemblytype + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "assembly_type.type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getType(String val_name) {\n");
			buffw.write("\t\treturn (String) map.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getType(String val_name, String entry_id, String assembly_id) {\n\n");

			buffw.write("\t\tfinal String[][] typetbl = {\n\n");

			FileReader filer = new FileReader(xsd_dir_name + "assembly_type.type");
			BufferedReader bufferr = new BufferedReader(filer);

			String line = null;

			while ((line = bufferr.readLine()) != null) {

				if (!line.matches(".*,.*,.*"))
					continue;

				String[] elem = line.split(",");

				buffw.write("\t\t\t\t{\"" + elem[0] + "\", \"" + elem[1] + "\", \"" + elem[2] + "\"},\n");

			}

			bufferr.close();
			filer.close();

			buffw.write("\n\t\t};\n\n");

			buffw.write("\t\tif (assembly_id == null)\n");
			buffw.write("\t\t\treturn val_name;\n\n");

			buffw.write("\t\tfor (int i = 0; i < typetbl.length; i++) {\n\n");

			buffw.write("\t\t\tif (typetbl[i][0].equals(entry_id) && typetbl[i][1].equals(assembly_id))\n");
			buffw.write("\t\t\t\treturn typetbl[i][2];\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String guessType(Connection conn_bmrb, String entry_id, String assembly_id) {\n\n");

			buffw.write("\t\tString type = \"na\";\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tList<" + file_prefix + "_" + util_assemblytype + ".entity_type> entity_list = new ArrayList<" + file_prefix + "_" + util_assemblytype + ".entity_type>();\n\n");

			buffw.write("\t\tString query;\n\n");

			buffw.write("\t\tif (!(" + empty_check("assembly_id") + "))\n");
			buffw.write("\t\t\tquery = new String(\"select \\\"Type\\\",\\\"Polymer_type\\\",\\\"Number_of_monomers\\\",\\\"Name\\\",\\\"Polymer_seq_one_letter_code\\\" from \\\"Entity\\\",\\\"Entity_assembly\\\" where \\\"Entity_assembly\\\".\\\"Entry_ID\\\"='\" + entry_id + \"' and \\\"Entity_assembly\\\".\\\"Assembly_ID\\\"='\" + assembly_id + \"' and \\\"Entity\\\".\\\"Entry_ID\\\"=\\\"Entity_assembly\\\".\\\"Entry_ID\\\" and \\\"Entity\\\".\\\"ID\\\"=\\\"Entity_assembly\\\".\\\"Entity_ID\\\"\");\n");
			buffw.write("\t\telse\n");
			buffw.write("\t\t\tquery = new String(\"select \\\"Type\\\",\\\"Polymer_type\\\",\\\"Number_of_monomers\\\",\\\"Name\\\",\\\"Polymer_seq_one_letter_code\\\" from \\\"Entity\\\",\\\"Entity_assembly\\\" where \\\"Entity_assembly\\\".\\\"Entry_ID\\\"='\" + entry_id + \"' and \\\"Entity\\\".\\\"Entry_ID\\\"=\\\"Entity_assembly\\\".\\\"Entry_ID\\\" and \\\"Entity\\\".\\\"ID\\\"=\\\"Entity_assembly\\\".\\\"Entity_ID\\\"\");\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tstate = conn_bmrb.createStatement();\n");
			buffw.write("\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\twhile (rset.next())\n");
			buffw.write("\t\t\t\tentity_list.add(new " + file_prefix + "_" + util_assemblytype + "().new entity_type(rset.getString(\"Type\"), rset.getString(\"Polymer_type\"), rset.getString(\"Number_of_monomers\"), rset.getString(\"Name\"), rset.getString(\"Polymer_seq_one_letter_code\")));\n\n");

			buffw.write("\t\t\tboolean polypeptide = false;\n");
			buffw.write("\t\t\tboolean polydeoxyribonucleotide = false;\n");
			buffw.write("\t\t\tboolean polyribonucleotide = false;\n");
			buffw.write("\t\t\tboolean polysaccharide = false;\n");
			buffw.write("\t\t\tboolean peptide = false;\n");
			buffw.write("\t\t\tboolean drug = false;\n");
			buffw.write("\t\t\tboolean inhibitor = false;\n");
			buffw.write("\t\t\tboolean ligand = false;\n\n");

			buffw.write("\t\t\tboolean hetero_polymer_complex = false;\n\n");

			buffw.write("\t\t\tint number_of_polymers = 0;\n");
			buffw.write("\t\t\tint sort_of_polymers = 0;\n\n");

			buffw.write("\t\t\tfor (int i = 0; i < entity_list.size(); i++) {\n\n");

			buffw.write("\t\t\t\t" + file_prefix + "_" + util_assemblytype + ".entity_type cont = entity_list.get(i);\n\n");

			buffw.write("\t\t\t\tif (cont == null)\n");
			buffw.write("\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\tif (cont.name != null && cont.type != null && !cont.type.equalsIgnoreCase(\"polymer\")) {\n\n");

			buffw.write("\t\t\t\t\tif (cont.name.matches(\".*[Dd][Rr][Uu][Gg].*\"))\n");
			buffw.write("\t\t\t\t\t\tdrug = true;\n\n");

			buffw.write("\t\t\t\t\tif (cont.name.matches(\".*[Ii][Nn][Hh][Ii][Bb][Ii][Tt].*\"))\n");
			buffw.write("\t\t\t\t\t\tinhibitor = true;\n\n");

			buffw.write("\t\t\t\t\tif (cont.name.matches(\".*[Ll][Gg][Aa][Nn][Dd].*\"))\n");
			buffw.write("\t\t\t\t\t\tligand = true;\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t\tif (cont.type == null || !cont.type.equalsIgnoreCase(\"polymer\"))\n");
			buffw.write("\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\tnumber_of_polymers++;\n\n");

			buffw.write("\t\t\t\tif (cont.polymer_type != null) {\n\n");

			buffw.write("\t\t\t\t\tif (cont.polymer_type.startsWith(\"polypeptide\")) {\n\n");

			buffw.write("\t\t\t\t\t\tif (cont.number_of_monomers > 23)\n");
			buffw.write("\t\t\t\t\t\t\tpolypeptide = true;\n");
			buffw.write("\t\t\t\t\t\telse\n");
			buffw.write("\t\t\t\t\t\t\tpeptide = true;\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\telse if (cont.polymer_type.equalsIgnoreCase(\"polydeoxyribonucleotide\"))\n");
			buffw.write("\t\t\t\t\t\tpolydeoxyribonucleotide = true;\n\n");

			buffw.write("\t\t\t\t\telse if (cont.polymer_type.equalsIgnoreCase(\"polyribonucleotide\"))\n");
			buffw.write("\t\t\t\t\t\tpolyribonucleotide = true;\n\n");

			buffw.write("\t\t\t\t\telse if (cont.polymer_type.equalsIgnoreCase(\"DNA_RNA_hybrid\"))\n");
			buffw.write("\t\t\t\t\t\tpolydeoxyribonucleotide = polyribonucleotide = true;\n\n");

			buffw.write("\t\t\t\t\telse if (cont.polymer_type.startsWith(\"polysaccharide\"))\n");
			buffw.write("\t\t\t\t\t\tpolysaccharide = true;\n\n");

			buffw.write("\t\t\t\t}\n\t\t\t}\n\n");

			buffw.write("\t\t\tif (polypeptide && (polydeoxyribonucleotide || polyribonucleotide || polysaccharide || peptide))\n");
			buffw.write("\t\t\t\thetero_polymer_complex = true;\n\n");

			buffw.write("\t\t\tif (polydeoxyribonucleotide && (polyribonucleotide || polysaccharide || peptide || polypeptide))\n");
			buffw.write("\t\t\t\thetero_polymer_complex = true;\n\n");

			buffw.write("\t\t\tif (polyribonucleotide && (polysaccharide || peptide || polypeptide || polydeoxyribonucleotide))\n");
			buffw.write("\t\t\t\thetero_polymer_complex = true;\n\n");

			buffw.write("\t\t\tif (polysaccharide && (peptide || polypeptide || polydeoxyribonucleotide || polyribonucleotide))\n");
			buffw.write("\t\t\t\thetero_polymer_complex = true;\n\n");

			buffw.write("\t\t\tif (peptide && (polypeptide || polydeoxyribonucleotide || polyribonucleotide || polysaccharide))\n");
			buffw.write("\t\t\t\thetero_polymer_complex = true;\n\n");

			buffw.write("\t\t\tfor (int i = 0; i < entity_list.size(); i++) {\n\n");

			buffw.write("\t\t\t\t" + file_prefix + "_" + util_assemblytype + ".entity_type cont = entity_list.get(i);\n\n");

			buffw.write("\t\t\t\tif (cont == null)\n");
			buffw.write("\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\tif (cont.type == null || !cont.type.equalsIgnoreCase(\"polymer\"))\n");
			buffw.write("\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\tint j;\n\n");

			buffw.write("\t\t\t\tfor (j = 0; j < i; j++) {\n\n");

			buffw.write("\t\t\t\t\t" + file_prefix + "_" + util_assemblytype + ".entity_type cont2 = entity_list.get(j);\n\n");

			buffw.write("\t\t\t\t\tif (cont2 == null)\n");
			buffw.write("\t\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\t\tif (cont2.type == null || !cont2.type.equalsIgnoreCase(\"polymer\"))\n");
			buffw.write("\t\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\t\tif (cont2.name.equals(cont.name))\n");
			buffw.write("\t\t\t\t\t\tbreak;\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t\tif (j == i)\n");
			buffw.write("\t\t\t\t\tsort_of_polymers++;\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tif (!hetero_polymer_complex) {\n\n");

			buffw.write("\t\t\t\tif (number_of_polymers == 1 || polysaccharide || peptide) {\n\n");

			buffw.write("\t\t\t\t\tif (polypeptide) {\n\n");

			buffw.write("\t\t\t\t\t\tif (drug)\n");
			buffw.write("\t\t\t\t\t\t\ttype = \"protein-drug complex\";\n\n");

			buffw.write("\t\t\t\t\t\telse if (inhibitor)\n");
			buffw.write("\t\t\t\t\t\t\ttype = \"protein-inhibitor complex\";\n\n");

			buffw.write("\t\t\t\t\t\telse if (ligand)\n");
			buffw.write("\t\t\t\t\t\t\ttype = \"protein-ligand complex\";\n\n");

			buffw.write("\t\t\t\t\t\telse\n");
			buffw.write("\t\t\t\t\t\t\ttype = \"protein monomer\";\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\telse if (polydeoxyribonucleotide && polyribonucleotide)\n");
			buffw.write("\t\t\t\t\t\ttype = \"DNA-RNA hybrid\";\n\n");

			buffw.write("\t\t\t\t\telse if (polydeoxyribonucleotide) {\n\n");

			buffw.write("\t\t\t\t\t\tif (drug)\n");
			buffw.write("\t\t\t\t\t\t\ttype = \"DNA-drug complex\";\n\n");

			buffw.write("\t\t\t\t\t\telse\n");
			buffw.write("\t\t\t\t\t\t\ttype = \"DNA single strand\";\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\telse if (polyribonucleotide) {\n\n");

			buffw.write("\t\t\t\t\t\tif (drug)\n");
			buffw.write("\t\t\t\t\t\t\ttype = \"RNA-drug complex\";\n\n");

			buffw.write("\t\t\t\t\t\telse\n");
			buffw.write("\t\t\t\t\t\t\ttype = \"RNA single strand\";\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\telse if (polysaccharide)\n");
			buffw.write("\t\t\t\t\t\ttype = \"polysaccharide\";\n\n");

			buffw.write("\t\t\t\t\telse if (peptide)\n");
			buffw.write("\t\t\t\t\t\ttype = \"peptide\";\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t\telse if (sort_of_polymers == 1) {\n\n");

			buffw.write("\t\t\t\t\tif (polypeptide)\n");
			buffw.write("\t\t\t\t\t\ttype = \"protein homo-multimer\";\n\n");

			buffw.write("\t\t\t\t\telse if (polydeoxyribonucleotide)\n");
			buffw.write("\t\t\t\t\t\ttype = \"DNA double strand\";\n\n");

			buffw.write("\t\t\t\t\telse if (polyribonucleotide)\n");
			buffw.write("\t\t\t\t\t\ttype = \"RNA double strand\";\n\n");

			buffw.write("\t\t\t\t\telse if (polysaccharide)\n");
			buffw.write("\t\t\t\t\t\ttype = \"polysaccharide\";\n\n");

			buffw.write("\t\t\t\t\telse if (peptide)\n");
			buffw.write("\t\t\t\t\t\ttype = \"peptide\";\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t\telse {\n\n");

			buffw.write("\t\t\t\t\tif (polypeptide && number_of_polymers >= sort_of_polymers * 2)\n");
			buffw.write("\t\t\t\t\t\ttype = \"protein hetero-multimer\";\n\n");

			buffw.write("\t\t\t\t\telse if (polypeptide && number_of_polymers == sort_of_polymers)\n");
			buffw.write("\t\t\t\t\t\ttype = \"protein-protein complex\";\n\n");

			buffw.write("\t\t\t\t\telse if (polydeoxyribonucleotide)\n");
			buffw.write("\t\t\t\t\t\ttype = \"DNA double strand\";\n\n");

			buffw.write("\t\t\t\t\telse if (polyribonucleotide)\n");
			buffw.write("\t\t\t\t\t\ttype = \"RNA double strand\";\n\n");

			buffw.write("\t\t\t\t\telse if (polysaccharide)\n");
			buffw.write("\t\t\t\t\t\ttype = \"polysaccharide\";\n\n");

			buffw.write("\t\t\t\t\telse if (peptide)\n");
			buffw.write("\t\t\t\t\t\ttype = \"peptide\";\n\n");

			buffw.write("\t\t\t\t}\n\t\t\t}\n\n");

			buffw.write("\t\t\telse {\n\n");

			buffw.write("\t\t\t\tif (polypeptide) {\n\n");

			buffw.write("\t\t\t\t\tif (polydeoxyribonucleotide && polyribonucleotide)\n");
			buffw.write("\t\t\t\t\t\ttype = \"protein-nucleic acid complex\";\n\n");

			buffw.write("\t\t\t\t\telse if (polydeoxyribonucleotide && inhibitor)\n");
			buffw.write("\t\t\t\t\t\ttype = \"protein-DNA-inhibitor complex\";\n\n");

			buffw.write("\t\t\t\t\telse if (polydeoxyribonucleotide && peptide)\n");
			buffw.write("\t\t\t\t\t\ttype = \"protein-DNA-ligand complex\";\n\n");

			buffw.write("\t\t\t\t\telse if (polydeoxyribonucleotide)\n");
			buffw.write("\t\t\t\t\t\ttype = \"protein-DNA complex\";\n\n");

			buffw.write("\t\t\t\t\telse if (polyribonucleotide && inhibitor)\n");
			buffw.write("\t\t\t\t\t\ttype = \"protein-RNA-inhibitor complex\";\n\n");

			buffw.write("\t\t\t\t\telse if (polyribonucleotide && peptide)\n");
			buffw.write("\t\t\t\t\t\ttype = \"protein-RNA-ligand complex\";\n\n");

			buffw.write("\t\t\t\t\telse if (polyribonucleotide)\n");
			buffw.write("\t\t\t\t\t\ttype = \"protein-RNA complex\";\n\n");

			buffw.write("\t\t\t\t\telse if (polysaccharide)\n");
			buffw.write("\t\t\t\t\t\ttype = \"protein-carbohydrate complex\";\n\n");

			buffw.write("\t\t\t\t\telse if (peptide)\n");
			buffw.write("\t\t\t\t\t\ttype = \"protein-ligand complex\";\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t\telse if (polydeoxyribonucleotide && polyribonucleotide)\n");
			buffw.write("\t\t\t\t\ttype = \"DNA-RNA complex\";\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t} catch (SQLException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn type;\n");

			buffw.write("\t}\n\n");

			buffw.write("\tprivate class entity_type {\n\n");

			buffw.write("\t\tString type;\n");
			buffw.write("\t\tString polymer_type;\n");
			buffw.write("\t\tString name;\n");
			buffw.write("\t\tString polymer_seq_one_letter_code;\n\n");

			buffw.write("\t\tint number_of_monomers;\n\n");

			buffw.write("\t\tentity_type(String type_name, String polymer_type_name, String number_of_monomers_name, String name_name, String polymer_seq_one_letter_code_name) {\n\n");

			buffw.write("\t\t\ttype = type_name;\n");
			buffw.write("\t\t\tpolymer_type = polymer_type_name;\n");
			buffw.write("\t\t\tname = name_name;\n");
			buffw.write("\t\t\tpolymer_seq_one_letter_code = polymer_seq_one_letter_code_name;\n\n");

			buffw.write("\t\t\tif (!(" + empty_check("number_of_monomers_name") + "))\n");
			buffw.write("\t\t\t\tnumber_of_monomers = Integer.valueOf(number_of_monomers_name);\n");
			buffw.write("\t\t\telse if (!(" + empty_check("polymer_seq_one_letter_code") + "))\n");
			buffw.write("\t\t\t\tnumber_of_monomers = polymer_seq_one_letter_code.length();\n\n");

			buffw.write("\t\t}\n\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_atomchemshift(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_atomchemshift + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_atomchemshift + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "atom_chem_shift.ambiguity_code.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getAmbiguityCode(String val_name, String entry_id) {\n\n");

			buffw.write("\t\tif (entry_id.equals(\"18205\"))\n");
			buffw.write("\t\t\treturn null;\n\n");

			buffw.write("\t\tval_name = map.get(val_name);\n\n");

			buffw.write("\t\tif (val_name != null && val_name.equalsIgnoreCase(\"null\"))\n");
			buffw.write("\t\t\treturn null;\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_autorelaxationlist(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_autorelaxationlist + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_autorelaxationlist + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map_temp_control_method = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "auto_relaxation_list.temp_control_method.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getTempControlMethod(String val_name) {\n");
			buffw.write("\t\treturn (String) map_temp_control_method.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_bond(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_bond + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_bond + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map_type = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "bond.type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getType(String val_name) {\n");
			buffw.write("\t\treturn (String) map_type.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_value_order = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "bond.value_order.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getValueOrder(String val_name, String entry_id) {\n\n");

			buffw.write("\t\tif ((" + empty_check("val_name") + ") && entry_id.equals(\"19312\"))\n");
			buffw.write("\t\t\treturn \"sing\";\n\n");

			buffw.write("\t\treturn (String) map_value_order.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getHECAtomID2(String val_name, String comp_id_1, String atom_id_1, String comp_id_2) {\n\n");

			buffw.write("\t\tif (comp_id_1 != null && atom_id_1 != null && comp_id_2 != null && comp_id_1.equalsIgnoreCase(\"CYS\") && atom_id_1.equalsIgnoreCase(\"SG\") && comp_id_2.equalsIgnoreCase(\"HEC\") && (" + empty_check("val_name") + "))\n");
			buffw.write("\t\t\treturn \"FE\";\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_chemcomp(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_chemcomp + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.io.*;\n");
			buffw.write("import java.net.*;\n\n");

			buffw.write("import java.security.cert.*;\n");
			buffw.write("import java.security.*;\n");
			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("import javax.net.ssl.*;\n");
			buffw.write("import javax.xml.parsers.*;\n\n");

			buffw.write("//import org.apache.xml.serialize.*;\n");
			buffw.write("import org.w3c.dom.*;\n");
			buffw.write("import org.w3c.dom.ls.*;\n");
			buffw.write("import org.xml.sax.*;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_chemcomp + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map_type = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "chem_comp.type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getType(String val_name) {\n");
			buffw.write("\t\treturn (String) map_type.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_processing_site = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "chem_comp.processing_site.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getProcessingSite(String val_name) {\n");
			buffw.write("\t\treturn (String) map_processing_site.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_paramagnetic = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "chem_comp.paramagnetic.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getParamagnetic(String val_name) {\n");
			buffw.write("\t\treturn (String) map_paramagnetic.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\t/* synchronized */ public static String checkChemCompIDwithChemComp(String pdb_code, String pdb_id, int trials) {\n\n");

			buffw.write("\t\tif (trials < 0 || trials >= " + service_trials + " || " + empty_check("pdb_code") + ")\n");
			buffw.write("\t\t\treturn null;\n\n");

			buffw.write("\t\tFile ccd_file = new File(" + file_prefix + "_" + util_main + ".ccd_dir_name + pdb_code + \".rdf\");\n\n");

			buffw.write("\t\tif (ccd_file.exists())\n");
			buffw.write("\t\t\treturn checkChemCompIDwithChemComp(pdb_code, pdb_id, trials, ccd_file);\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tString _pdb_code = URLEncoder.encode(pdb_code, \"UTF-8\");\n");
			buffw.write("\t\t\tString rdf_pdb_ccd_api = \"" + rdf_pdb_cc_api + "\" + _pdb_code;\n\n");

			buffw.write("\t\t\tTrustManager[] tm = new TrustManager[] { new X509TrustManager() {\n\n");

			buffw.write("\t\t\t\t\tpublic X509Certificate[] getAcceptedIssuers() {\n");
			buffw.write("\t\t\t\t\t\treturn null;\n");
			buffw.write("\t\t\t\t\t}\n");
			buffw.write("\t\t\t\t\t@Override\n");
			buffw.write("\t\t\t\t\tpublic void checkClientTrusted(X509Certificate[] certs, String authType) {\n");
			buffw.write("\t\t\t\t\t}\n");
			buffw.write("\t\t\t\t\t@Override\n");
			buffw.write("\t\t\t\t\tpublic void checkServerTrusted(X509Certificate[] certs, String authType) {\n");
			buffw.write("\t\t\t\t\t}\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t};\n\n");

			buffw.write("\t\t\tSSLContext sc = SSLContext.getInstance(\"SSL\");\n");
			buffw.write("\t\t\tsc.init(null, tm, new java.security.SecureRandom());\n\n");

			buffw.write("\t\t\tHttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {\n");
			buffw.write("\t\t\t\t@Override\n");
			buffw.write("\t\t\t\tpublic boolean verify(String hostname, SSLSession session) {\n");
			buffw.write("\t\t\t\t\treturn true;\n");
			buffw.write("\t\t\t\t}\n");
			buffw.write("\t\t\t});\n\n");

			buffw.write("\t\t\tURL url = new URL(rdf_pdb_ccd_api);\n");
			buffw.write("\t\t\tHttpsURLConnection conn = (HttpsURLConnection) url.openConnection();\n");
			buffw.write("\t\t\tconn.setSSLSocketFactory(sc.getSocketFactory());\n\n");

			buffw.write("\t\t\tDocumentBuilderFactory doc_builder_fac = DocumentBuilderFactory.newInstance();\n\n");

			buffw.write("\t\t\tdoc_builder_fac.setValidating(false);\n");
			buffw.write("\t\t\tdoc_builder_fac.setNamespaceAware(false);\n");
			buffw.write("\t\t\tdoc_builder_fac.setFeature(\"http://apache.org/xml/features/nonvalidating/load-dtd-grammar\", false);\n");
			buffw.write("\t\t\tdoc_builder_fac.setFeature(\"http://apache.org/xml/features/nonvalidating/load-external-dtd\", false);\n\n");

			buffw.write("\t\t\tDocumentBuilder doc_builder = doc_builder_fac.newDocumentBuilder();\n\n");

			buffw.write("\t\t\tInputStream is = conn.getInputStream();\n\n");

			buffw.write("\t\t\tDocument doc = doc_builder.parse(is);\n\n");

			buffw.write("\t\t\tif (is != null)\n\t\t\t\tis.close();\n\n");

			buffw.write("\t\t\tNode root = doc.getDocumentElement();\n\n");

			buffw.write("\t\t\tif (!root.getNodeName().equals(\"rdf:RDF\")) {\n\n");

			buffw.write("\t\t\t\tif (" + empty_check("pdb_id") + ")\n");
			buffw.write("\t\t\t\t\treturn checkChemCompIDwithBMRBLigandExpo(pdb_code, false, 0);\n\n");

			buffw.write("\t\t\t\treturn checkChemCompIDwithPDB(pdb_code, pdb_id, 0);\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tfor (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {\n\n");

			buffw.write("\t\t\t\tif (child.getNodeName().equals(\"rdf:Description\")) {\n\n");

			buffw.write("\t\t\t\t\tfor (Node db_name = child.getFirstChild(); db_name != null; db_name = db_name.getNextSibling()) {\n\n");

			buffw.write("\t\t\t\t\t\tif (db_name.getNodeName().equals(\"PDBo:datablockName\")) {\n\n");

			buffw.write("\t\t\t\t\t\t\twriteChemCompDictionary(ccd_file, doc);\n\n");

			buffw.write("\t\t\t\t\t\t\treturn db_name.getFirstChild().getNodeValue();\n");
			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t} catch (MalformedURLException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (IOException e) {\n");
			buffw.write("\n\t\t\ttry {\n\t\t\t\tThread.sleep(" + service_wait + ");\n\t\t\t} catch (InterruptedException e1) {\n\t\t\t\te1.printStackTrace();\n\t\t\t}\n\n");
			buffw.write("\t\t\treturn checkChemCompIDwithChemComp(pdb_code, pdb_id, ++trials);\n\n");
			buffw.write("\t\t\t//e.printStackTrace();\n");
			buffw.write("\t\t} catch (ParserConfigurationException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (SAXException e) {\n");
			buffw.write("\t\t\t//e.printStackTrace();\n");
			buffw.write("\t\t} catch (NoSuchAlgorithmException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (KeyManagementException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn pdb_code;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tprivate static void writeChemCompDictionary(File ccd_file, Document doc) {\n\n");

			buffw.write("\t\tFile ccd_file_ = new File(ccd_file.getPath() + \"~\");\n\n");

			buffw.write("\t\tif (ccd_file.exists())\n");
			buffw.write("\t\t\treturn;\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tDOMImplementation domImpl = doc.getImplementation();\n");
			buffw.write("\t\t\tDOMImplementationLS domImplLS = (DOMImplementationLS) domImpl.getFeature(\"LS\", \"3.0\");\n\n");

			buffw.write("\t\t\tLSOutput output = domImplLS.createLSOutput();\n");
			buffw.write("\t\t\toutput.setByteStream(new FileOutputStream(ccd_file_));\n\n");

			buffw.write("\t\t\tLSSerializer serializer = domImplLS.createLSSerializer();\n");
			buffw.write("\t\t\tserializer.getDomConfig().setParameter(\"format-pretty-print\", Boolean.TRUE);\n");
			buffw.write("\t\t\tserializer.write(doc, output);\n\n");

			buffw.write("/*\n\t\t\tOutputFormat format = new OutputFormat(doc);\n\n");

			buffw.write("\t\t\tformat.setIndenting(true);\n");
			buffw.write("\t\t\tformat.setIndent(2);\n\n");

			buffw.write("\t\t\tFileWriter writer = new FileWriter(ccd_file_);\n");
			buffw.write("\t\t\tXMLSerializer serializer = new XMLSerializer(writer, format);\n\n");

			buffw.write("\t\t\tserializer.serialize(doc);\n*/\n\n");

			buffw.write("\t\t\tccd_file_.renameTo(ccd_file);\n\n");

			buffw.write("\t\t} catch (IOException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t}\n\n");

			buffw.write("\tprivate static String checkChemCompIDwithChemComp(String pdb_code, String pdb_id, int trials, File ccd_file) {\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tDocumentBuilderFactory doc_builder_fac = DocumentBuilderFactory.newInstance();\n\n");

			buffw.write("\t\t\tdoc_builder_fac.setValidating(false);\n");
			buffw.write("\t\t\tdoc_builder_fac.setNamespaceAware(false);\n");
			buffw.write("\t\t\tdoc_builder_fac.setFeature(\"http://apache.org/xml/features/nonvalidating/load-dtd-grammar\", false);\n");
			buffw.write("\t\t\tdoc_builder_fac.setFeature(\"http://apache.org/xml/features/nonvalidating/load-external-dtd\", false);\n\n");

			buffw.write("\t\t\tDocumentBuilder doc_builder = doc_builder_fac.newDocumentBuilder();\n\n");

			buffw.write("\t\t\tDocument doc = doc_builder.parse(ccd_file);\n\n");

			buffw.write("\t\t\tNode root = doc.getDocumentElement();\n\n");

			buffw.write("\t\t\tif (!root.getNodeName().equals(\"rdf:RDF\")) {\n\n");

			buffw.write("\t\t\t\tccd_file.delete();\n\n");

			buffw.write("\t\t\t\tif (" + empty_check("pdb_id") + ")\n");
			buffw.write("\t\t\t\t\treturn checkChemCompIDwithBMRBLigandExpo(pdb_code, false, 0);\n\n");

			buffw.write("\t\t\t\treturn checkChemCompIDwithPDB(pdb_code, pdb_id, 0);\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tfor (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {\n\n");

			buffw.write("\t\t\t\tif (child.getNodeName().equals(\"rdf:Description\")) {\n\n");

			buffw.write("\t\t\t\t\tfor (Node db_name = child.getFirstChild(); db_name != null; db_name = db_name.getNextSibling()) {\n\n");

			buffw.write("\t\t\t\t\t\tif (db_name.getNodeName().equals(\"PDBo:datablockName\"))\n");
			buffw.write("\t\t\t\t\t\t\treturn db_name.getFirstChild().getNodeValue();\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t} catch (ParserConfigurationException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (SAXException e) {\n");
			buffw.write("\t\t\t//e.printStackTrace();\n");
			buffw.write("\t\t} catch (IOException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\tccd_file.delete();\n\n");

			buffw.write("\t\treturn pdb_code;\n");
			buffw.write("\t}\n\n");

			buffw.write("\t/* synchronized */ public static String checkChemCompIDwithPDB(String pdb_code, String pdb_id, int trials) {\n\n");

			buffw.write("\t\tif (trials < 0 || trials >= " + service_trials + " || " + empty_check("pdb_code") + " || " + empty_check("pdb_id") + ")\n");
			buffw.write("\t\t\treturn null;\n\n");

			buffw.write("\t\tFile cc_file = new File(" + file_prefix + "_" + util_main + ".cc_dir_name + pdb_id + \"_\" + pdb_code + \".rdf\");\n\n");

			buffw.write("\t\tif (cc_file.exists())\n");
			buffw.write("\t\t\treturn checkChemCompIDwithPDB(pdb_code, pdb_id, trials, cc_file);\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tString _pdb_code = URLEncoder.encode(pdb_code, \"UTF-8\");\n");
			buffw.write("\t\t\tString rdf_pdb_api = \"" + rdf_pdb_api + "\" + pdb_id + \"/chem_comp/\" + _pdb_code;\n\n");

			buffw.write("\t\t\tTrustManager[] tm = new TrustManager[] { new X509TrustManager() {\n\n");

			buffw.write("\t\t\t\t\tpublic X509Certificate[] getAcceptedIssuers() {\n");
			buffw.write("\t\t\t\t\t\treturn null;\n");
			buffw.write("\t\t\t\t\t}\n");
			buffw.write("\t\t\t\t\t@Override\n");
			buffw.write("\t\t\t\t\tpublic void checkClientTrusted(X509Certificate[] certs, String authType) {\n");
			buffw.write("\t\t\t\t\t}\n");
			buffw.write("\t\t\t\t\t@Override\n");
			buffw.write("\t\t\t\t\tpublic void checkServerTrusted(X509Certificate[] certs, String authType) {\n");
			buffw.write("\t\t\t\t\t}\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t};\n\n");

			buffw.write("\t\t\tSSLContext sc = SSLContext.getInstance(\"SSL\");\n");
			buffw.write("\t\t\tsc.init(null, tm, new java.security.SecureRandom());\n\n");

			buffw.write("\t\t\tHttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {\n");
			buffw.write("\t\t\t\t@Override\n");
			buffw.write("\t\t\t\tpublic boolean verify(String hostname, SSLSession session) {\n");
			buffw.write("\t\t\t\t\treturn true;\n");
			buffw.write("\t\t\t\t}\n");
			buffw.write("\t\t\t});\n\n");

			buffw.write("\t\t\tURL url = new URL(rdf_pdb_api);\n");
			buffw.write("\t\t\tHttpsURLConnection conn = (HttpsURLConnection) url.openConnection();\n");
			buffw.write("\t\t\tconn.setSSLSocketFactory(sc.getSocketFactory());\n\n");

			buffw.write("\t\t\tDocumentBuilderFactory doc_builder_fac = DocumentBuilderFactory.newInstance();\n\n");

			buffw.write("\t\t\tdoc_builder_fac.setValidating(false);\n");
			buffw.write("\t\t\tdoc_builder_fac.setNamespaceAware(false);\n");
			buffw.write("\t\t\tdoc_builder_fac.setFeature(\"http://apache.org/xml/features/nonvalidating/load-dtd-grammar\", false);\n");
			buffw.write("\t\t\tdoc_builder_fac.setFeature(\"http://apache.org/xml/features/nonvalidating/load-external-dtd\", false);\n\n");

			buffw.write("\t\t\tDocumentBuilder doc_builder = doc_builder_fac.newDocumentBuilder();\n\n");

			buffw.write("\t\t\tInputStream is = conn.getInputStream();\n\n");

			buffw.write("\t\t\tDocument doc = doc_builder.parse(is);\n\n");

			buffw.write("\t\t\tif (is != null)\n\t\t\t\tis.close();\n\n");

			buffw.write("\t\t\tNode root = doc.getDocumentElement();\n\n");

			buffw.write("\t\t\tif (!root.getNodeName().equals(\"rdf:RDF\"))\n");
			buffw.write("\t\t\t\treturn checkChemCompIDwithBMRBLigandExpo(pdb_code, false, 0);\n\n");

			buffw.write("\t\t\tboolean fill_pdb_id = false;\n");
			buffw.write("\t\t\tboolean fill_pdb_code = false;\n\n");

			buffw.write("\t\t\tfor (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {\n\n");

			buffw.write("\t\t\t\tif (child.getNodeName().equals(\"PDBo:PDBID\")) {\n\n");

			buffw.write("\t\t\t\t\tfor (Node label = child.getFirstChild(); label != null; label = label.getNextSibling()) {\n\n");

			buffw.write("\t\t\t\t\t\tif (label.getNodeName().equals(\"rdfs.label\")) {\n\n");

			buffw.write("\t\t\t\t\t\t\tpdb_id = label.getFirstChild().getNodeValue();\n");
			buffw.write("\t\t\t\t\t\t\tfill_pdb_id = true;\n\n");

			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tfor (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {\n\n");

			buffw.write("\t\t\t\tif (child.getNodeName().equals(\"rdf:Description\")) {\n\n");

			buffw.write("\t\t\t\t\tfor (Node label = child.getFirstChild(); label != null; label = label.getNextSibling()) {\n\n");

			buffw.write("\t\t\t\t\t\tif (label.getNodeName().equals(\"PDBo:chem_comp.id\")) {\n\n");

			buffw.write("\t\t\t\t\t\t\tpdb_code = label.getFirstChild().getNodeValue();\n");
			buffw.write("\t\t\t\t\t\t\tfill_pdb_code = true;\n\n");

			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tif (fill_pdb_id && fill_pdb_code)\n");
			buffw.write("\t\t\t\twriteChemCompDictionary(cc_file, doc);\n\n");

			buffw.write("\t\t} catch (MalformedURLException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (IOException e) {\n");
			buffw.write("\n\t\t\ttry {\n\t\t\t\tThread.sleep(" + service_wait + ");\n\t\t\t} catch (InterruptedException e1) {\n\t\t\t\te1.printStackTrace();\n\t\t\t}\n\n");
			buffw.write("\t\t\treturn checkChemCompIDwithPDB(pdb_code, pdb_id, ++trials);\n\n");
			buffw.write("\t\t\t//e.printStackTrace();\n");
			buffw.write("\t\t} catch (ParserConfigurationException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (SAXException e) {\n");
			buffw.write("\t\t\t//e.printStackTrace();\n");
			buffw.write("\t\t} catch (NoSuchAlgorithmException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (KeyManagementException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn \"pdb/\" + pdb_id + \"/chem_comp/\" + pdb_code;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tprivate static String checkChemCompIDwithPDB(String pdb_code, String pdb_id, int trials, File cc_file) {\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tDocumentBuilderFactory doc_builder_fac = DocumentBuilderFactory.newInstance();\n\n");

			buffw.write("\t\t\tdoc_builder_fac.setValidating(false);\n");
			buffw.write("\t\t\tdoc_builder_fac.setNamespaceAware(false);\n");
			buffw.write("\t\t\tdoc_builder_fac.setFeature(\"http://apache.org/xml/features/nonvalidating/load-dtd-grammar\", false);\n");
			buffw.write("\t\t\tdoc_builder_fac.setFeature(\"http://apache.org/xml/features/nonvalidating/load-external-dtd\", false);\n\n");

			buffw.write("\t\t\tDocumentBuilder doc_builder = doc_builder_fac.newDocumentBuilder();\n\n");

			buffw.write("\t\t\tDocument doc = doc_builder.parse(cc_file);\n\n");

			buffw.write("\t\t\tNode root = doc.getDocumentElement();\n\n");

			buffw.write("\t\t\tif (!root.getNodeName().equals(\"rdf:RDF\")) {\n\n");

			buffw.write("\t\t\t\tcc_file.delete();\n\n");

			buffw.write("\t\t\t\treturn checkChemCompIDwithBMRBLigandExpo(pdb_code, false, 0);\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tboolean fill_pdb_id = false;\n");
			buffw.write("\t\t\tboolean fill_pdb_code = false;\n\n");

			buffw.write("\t\t\tfor (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {\n\n");

			buffw.write("\t\t\t\tif (child.getNodeName().equals(\"PDBo:PDBID\")) {\n\n");

			buffw.write("\t\t\t\t\tfor (Node label = child.getFirstChild(); label != null; label = label.getNextSibling()) {\n\n");

			buffw.write("\t\t\t\t\t\tif (label.getNodeName().equals(\"rdfs.label\")) {\n\n");

			buffw.write("\t\t\t\t\t\t\tpdb_id = label.getFirstChild().getNodeValue();\n");
			buffw.write("\t\t\t\t\t\t\tfill_pdb_id = true;\n\n");

			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tfor (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {\n\n");

			buffw.write("\t\t\t\tif (child.getNodeName().equals(\"rdf:Description\")) {\n\n");

			buffw.write("\t\t\t\t\tfor (Node label = child.getFirstChild(); label != null; label = label.getNextSibling()) {\n\n");

			buffw.write("\t\t\t\t\t\tif (label.getNodeName().equals(\"PDBo:chem_comp.id\")) {\n\n");

			buffw.write("\t\t\t\t\t\t\tpdb_code = label.getFirstChild().getNodeValue();\n");
			buffw.write("\t\t\t\t\t\t\tfill_pdb_code = true;\n\n");

			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tif (!fill_pdb_id || !fill_pdb_code)\n");
			buffw.write("\t\t\t\tcc_file.delete();\n\n");

			buffw.write("\t\t} catch (ParserConfigurationException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (SAXException e) {\n");
			buffw.write("\t\t\t//e.printStackTrace();\n");
			buffw.write("\t\t} catch (IOException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn \"pdb/\" + pdb_id + \"/chem_comp/\" + pdb_code;\n");
			buffw.write("\t}\n\n");

			buffw.write("\t/* synchronized */ public static String checkChemCompIDwithBMRBLigandExpo(String bmrb_code, boolean bmrb, int trials) {\n\n");

			buffw.write("\t\tif (trials < 0 || trials >= " + service_trials + " || " + empty_check("bmrb_code") + ")\n");
			buffw.write("\t\t\treturn null;\n\n");

			buffw.write("\t\tif (bmrb_code.matches(\"^bms[et][0-9][0-9][0-9][0-9][0-9][0-9]$\"))\n");
			buffw.write("\t\t\treturn bmrb ? bmrb_code : \"bmrb_metabolomics/\" + bmrb_code;\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("/*\t\t\tTrustManager[] tm = new TrustManager[] { new X509TrustManager() {\n\n");

			buffw.write("\t\t\t\t\tpublic X509Certificate[] getAcceptedIssuers() {\n");
			buffw.write("\t\t\t\t\t\treturn null;\n");
			buffw.write("\t\t\t\t\t}\n");
			buffw.write("\t\t\t\t\t@Override\n");
			buffw.write("\t\t\t\t\tpublic void checkClientTrusted(X509Certificate[] certs, String authType) {\n");
			buffw.write("\t\t\t\t\t}\n");
			buffw.write("\t\t\t\t\t@Override\n");
			buffw.write("\t\t\t\t\tpublic void checkServerTrusted(X509Certificate[] certs, String authType) {\n");
			buffw.write("\t\t\t\t\t}\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t};\n\n");

			buffw.write("\t\t\tSSLContext sc = SSLContext.getInstance(\"SSL\");\n");
			buffw.write("\t\t\tsc.init(null, tm, new java.security.SecureRandom());\n\n");

			buffw.write("\t\t\tHttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {\n");
			buffw.write("\t\t\t\t@Override\n");
			buffw.write("\t\t\t\tpublic boolean verify(String hostname, SSLSession session) {\n");
			buffw.write("\t\t\t\t\treturn true;\n");
			buffw.write("\t\t\t\t}\n");
			buffw.write("\t\t\t});\n\n*/");

			buffw.write("\t\t\tString _bmrb_code = URLEncoder.encode(bmrb_code, \"UTF-8\");\n");
			buffw.write("\t\t\tString bmrb_ligand_expo_api = \"" + bmrb_ligand_expo_api + "\" + _bmrb_code;\n\n");

			buffw.write("\t\t\tURL url = new URL(bmrb_ligand_expo_api);\n");
			buffw.write("\t\t\tURLConnection conn = url.openConnection();\n");
			buffw.write("//\t\t\tHttpsURLConnection conn = (HttpsURLConnection) url.openConnection();\n");
			buffw.write("//\t\t\tconn.setSSLSocketFactory(sc.getSocketFactory());\n\n");

			buffw.write("\t\t\tInputStream is = conn.getInputStream();\n\n");

			buffw.write("\t\t\tBufferedReader bufferr = new BufferedReader(new InputStreamReader(is));\n\n");

			buffw.write("\t\t\tif (is != null)\n\t\t\t\tis.close();\n\n");

			buffw.write("\t\t\tString line = null;\n\n");

			buffw.write("\t\t\twhile ((line = bufferr.readLine()) != null) {\n\n");

			buffw.write("\t\t\t\tif (line.contains(\"No records\")) {\n\n");

			buffw.write("\t\t\t\t\tbufferr.close();\n\n");

			buffw.write("\t\t\t\t\treturn \"no_records/\" + bmrb_code;\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t} catch (MalformedURLException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (IOException e) {\n");
			buffw.write("\n\t\t\ttry {\n\t\t\t\tThread.sleep(" + service_wait + ");\n\t\t\t} catch (InterruptedException e1) {\n\t\t\t\te1.printStackTrace();\n\t\t\t}\n\n");
			buffw.write("\t\t\treturn checkChemCompIDwithBMRBLigandExpo(bmrb_code, bmrb, ++trials);\n\n");
			buffw.write("\t\t\t//e.printStackTrace();\n");
			buffw.write("/*\t\t} catch (NoSuchAlgorithmException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (KeyManagementException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n*/");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn bmrb ? bmrb_code : \"bmrb_ligand_expo/\" + bmrb_code;\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_chemcompatom(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_chemcompatom + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_chemcompatom + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map_aromatic_flag = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "chem_comp_atom.aromatic_flag.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getAromaticFlag(String val_name) {\n");
			buffw.write("\t\treturn (String) map_aromatic_flag.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_leaving_atom_flag = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "chem_comp_atom.leaving_atom_flag.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getLeavingAtomFlag(String val_name) {\n");
			buffw.write("\t\treturn (String) map_leaving_atom_flag.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_chemcompbond(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_chemcompbond + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_chemcompbond + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map_type = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "chem_comp_bond.type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getType(String val_name) {\n");
			buffw.write("\t\treturn (String) map_type.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_value_order = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "chem_comp_bond.value_order.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getValueOrder(String val_name) {\n");
			buffw.write("\t\treturn (String) map_value_order.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_stereo_config = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "chem_comp_bond.stereo_config.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getStereoConfig(String val_name) {\n");
			buffw.write("\t\treturn (String) map_stereo_config.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_chemcompcommonname(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_chemcompcommonname + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_chemcompcommonname + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "chem_comp_common_name.type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getType(String val_name) {\n");
			buffw.write("\t\treturn (String) map.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_chemcompdescriptor(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_chemcompdescriptor + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_chemcompdescriptor + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map_type = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "chem_comp_descriptor.type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getType(String val_name) {\n");
			buffw.write("\t\treturn (String) map_type.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_program = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "chem_comp_descriptor.program.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getProgram(String val_name) {\n");
			buffw.write("\t\treturn (String) map_program.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_chemcompidentifier(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_chemcompidentifier + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_chemcompidentifier + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "chem_comp_identifier.program.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getProgram(String val_name) {\n");
			buffw.write("\t\treturn (String) map.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_chemcompsystematicname(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_chemcompsystematicname + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_chemcompsystematicname + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "chem_comp_systematic_name.naming_system.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getNamingSystem(String val_name) {\n");
			buffw.write("\t\treturn (String) map.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_chemshiftref(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_chemshiftref + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_chemshiftref + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map_mol_common_name = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "chem_shift_ref.mol_common_name.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getMolCommonName(String val_name) {\n");
			buffw.write("\t\treturn (String) map_mol_common_name.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_ref_method = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "chem_shift_ref.ref_method.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getRefMethod(String val_name) {\n");
			buffw.write("\t\treturn (String) map_ref_method.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_ref_type = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "chem_shift_ref.ref_type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getRefType(String val_name) {\n");
			buffw.write("\t\treturn (String) map_ref_type.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_chem_shift_units = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "chem_shift_ref.chem_shift_units.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getChemShiftUnits(String val_name) {\n");
			buffw.write("\t\treturn (String) map_chem_shift_units.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_external_ref_loc = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "chem_shift_ref.external_ref_loc.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getExternalRefLoc(String val_name) {\n");
			buffw.write("\t\treturn (String) map_external_ref_loc.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_external_ref_axis = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "chem_shift_ref.external_ref_axis.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getExternalRefAxis(String val_name) {\n");
			buffw.write("\t\treturn (String) map_external_ref_axis.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_external_ref_sample_geometry = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "chem_shift_ref.external_ref_sample_geometry.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getExternalRefSampleGeometry(String val_name) {\n");
			buffw.write("\t\treturn (String) map_external_ref_sample_geometry.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tString atom_isotope_number;\n");
			buffw.write("\tString atom_type;\n");
			buffw.write("\tString indirect_shift_ratio;\n\n");

			buffw.write("\t" + file_prefix + "_" + util_chemshiftref + "(String atom_type_name, String atom_isotope_number_name, String indirect_shift_ratio_name) {\n\n");

			buffw.write("\t\tif (atom_type_name == null && !(" + empty_check("atom_isotope_number_name") + ")) {\n\n");

			buffw.write("\t\t\tif (atom_isotope_number_name.equals(\"1\") || atom_isotope_number_name.equals(\"2\"))\n");
			buffw.write("\t\t\t\tatom_type_name = \"H\";\n\n");

			buffw.write("\t\t\tif (atom_isotope_number_name.equals(\"13\"))\n");
			buffw.write("\t\t\t\tatom_type_name = \"C\";\n\n");

			buffw.write("\t\t\tif (atom_isotope_number_name.equals(\"15\"))\n");
			buffw.write("\t\t\t\tatom_type_name = \"N\";\n\n");

			buffw.write("\t\t\tif (atom_isotope_number_name.equals(\"31\"))\n");
			buffw.write("\t\t\t\tatom_type_name = \"P\";\n\n");

			buffw.write("\t\t\tif (atom_isotope_number_name.equals(\"19\"))\n");
			buffw.write("\t\t\t\tatom_type_name = \"F\";\n\n");

			buffw.write("\t\t\tif (atom_isotope_number_name.equals(\"6\"))\n");
			buffw.write("\t\t\t\tatom_type_name = \"Li\";\n\n");

			buffw.write("\t\t\tif (atom_isotope_number_name.equals(\"11\") || atom_isotope_number_name.equals(\"10\"))\n");
			buffw.write("\t\t\t\tatom_type_name = \"B\";\n\n");

			buffw.write("\t\t\tif (atom_isotope_number_name.equals(\"17\"))\n");
			buffw.write("\t\t\t\tatom_type_name = \"O\";\n\n");

			buffw.write("\t\t\tif (atom_isotope_number_name.equals(\"23\"))\n");
			buffw.write("\t\t\t\tatom_type_name = \"Na\";\n\n");

			buffw.write("\t\t\tif (atom_isotope_number_name.equals(\"29\"))\n");
			buffw.write("\t\t\t\tatom_type_name = \"Si\";\n\n");

			buffw.write("\t\t\tif (atom_isotope_number_name.equals(\"35\"))\n");
			buffw.write("\t\t\t\tatom_type_name = \"Cl\";\n\n");

			buffw.write("\t\t\tif (atom_isotope_number_name.equals(\"113\") || atom_isotope_number_name.equals(\"111\"))\n");
			buffw.write("\t\t\t\tatom_type_name = \"Cd\";\n\n");

			buffw.write("\t\t\tif (atom_isotope_number_name.equals(\"129\"))\n");
			buffw.write("\t\t\t\tatom_type_name = \"Xe\";\n\n");

			buffw.write("\t\t\tif (atom_isotope_number_name.equals(\"195\"))\n");
			buffw.write("\t\t\t\tatom_type_name = \"Pt\";\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif (atom_type_name != null) {\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"1H\") || atom_type_name.equalsIgnoreCase(\"2H\"))\n");
			buffw.write("\t\t\t\tatom_type_name = \"H\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"13C\"))\n");
			buffw.write("\t\t\t\tatom_type_name = \"C\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"15N\") || atom_type_name.equalsIgnoreCase(\"14N\"))\n");
			buffw.write("\t\t\t\tatom_type_name = \"N\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"31P\"))\n");
			buffw.write("\t\t\t\tatom_type_name = \"P\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"19F\"))\n");
			buffw.write("\t\t\t\tatom_type_name = \"F\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"6Li\"))\n");
			buffw.write("\t\t\t\tatom_type_name = \"Li\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"11B\") || atom_type_name.equalsIgnoreCase(\"10B\"))\n");
			buffw.write("\t\t\t\tatom_type_name = \"B\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"17O\"))\n");
			buffw.write("\t\t\t\tatom_type_name = \"O\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"23Na\"))\n");
			buffw.write("\t\t\t\tatom_type_name = \"Na\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"29Si\"))\n");
			buffw.write("\t\t\t\tatom_type_name = \"Si\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"35Cl\"))\n");
			buffw.write("\t\t\t\tatom_type_name = \"Cl\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"113Cd\") || atom_type_name.equalsIgnoreCase(\"111Cd\"))\n");
			buffw.write("\t\t\t\tatom_type_name = \"Cd\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"129Xe\"))\n");
			buffw.write("\t\t\t\tatom_type_name = \"Xe\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"195Pt\"))\n");
			buffw.write("\t\t\t\tatom_type_name = \"Pt\";\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\tatom_type = atom_type_name;\n\n");

			buffw.write("\t\tif (atom_type_name != null && (" + empty_check("atom_isotope_number_name") + ")) {\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"H\"))\n");
			buffw.write("\t\t\t\tatom_isotope_number_name = \"1\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"C\"))\n");
			buffw.write("\t\t\t\tatom_isotope_number_name = \"13\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"N\"))\n");
			buffw.write("\t\t\t\tatom_isotope_number_name = \"15\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"P\"))\n");
			buffw.write("\t\t\t\tatom_isotope_number_name = \"31\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"F\"))\n");
			buffw.write("\t\t\t\tatom_isotope_number_name = \"19\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"Li\"))\n");
			buffw.write("\t\t\t\tatom_isotope_number_name = \"6\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"B\"))\n");
			buffw.write("\t\t\t\tatom_isotope_number_name = \"11\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"O\"))\n");
			buffw.write("\t\t\t\tatom_isotope_number_name = \"17\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"Na\"))\n");
			buffw.write("\t\t\t\tatom_isotope_number_name = \"23\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"Si\"))\n");
			buffw.write("\t\t\t\tatom_isotope_number_name = \"29\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"Cl\"))\n");
			buffw.write("\t\t\t\tatom_isotope_number_name = \"35\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"Cd\"))\n");
			buffw.write("\t\t\t\tatom_isotope_number_name = \"113\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"Xe\"))\n");
			buffw.write("\t\t\t\tatom_isotope_number_name = \"129\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"Pt\"))\n");
			buffw.write("\t\t\t\tatom_isotope_number_name = \"195\";\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif (atom_isotope_number_name != null && atom_type_name != null) {\n\n");

			buffw.write("\t\t\tInteger _atom_isotope_number = Integer.valueOf(atom_isotope_number_name);\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"H\") && _atom_isotope_number != 1 && _atom_isotope_number != 2)\n");
			buffw.write("\t\t\t\tatom_isotope_number_name = \"1\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"C\") && _atom_isotope_number != 13)\n");
			buffw.write("\t\t\t\tatom_isotope_number_name = \"13\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"N\") && _atom_isotope_number != 15 && _atom_isotope_number != 14)\n");
			buffw.write("\t\t\t\tatom_isotope_number_name = \"15\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"P\") && _atom_isotope_number != 31)\n");
			buffw.write("\t\t\t\tatom_isotope_number_name = \"31\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"F\") && _atom_isotope_number != 19)\n");
			buffw.write("\t\t\t\tatom_isotope_number_name = \"19\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"B\") && _atom_isotope_number != 11 && _atom_isotope_number != 10)\n");
			buffw.write("\t\t\t\tatom_isotope_number_name = \"11\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"O\") && _atom_isotope_number != 17)\n");
			buffw.write("\t\t\t\tatom_isotope_number_name = \"17\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"Na\") && _atom_isotope_number != 23)\n");
			buffw.write("\t\t\t\tatom_isotope_number_name = \"23\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"Si\") && _atom_isotope_number != 29)\n");
			buffw.write("\t\t\t\tatom_isotope_number_name = \"29\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"Cl\") && _atom_isotope_number != 35)\n");
			buffw.write("\t\t\t\tatom_isotope_number_name = \"35\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"Cd\") && _atom_isotope_number != 113 && _atom_isotope_number != 111)\n");
			buffw.write("\t\t\t\tatom_isotope_number_name = \"113\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"Xe\") && _atom_isotope_number != 129)\n");
			buffw.write("\t\t\t\tatom_isotope_number_name = \"129\";\n\n");

			buffw.write("\t\t\tif (atom_type_name.equalsIgnoreCase(\"Pt\") && _atom_isotope_number != 195)\n");
			buffw.write("\t\t\t\tatom_isotope_number_name = \"195\";\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\tatom_isotope_number = atom_isotope_number_name;\n\n");

			buffw.write("\t\tif (indirect_shift_ratio_name != null) {\n\n");

			buffw.write("\t\t\tif (indirect_shift_ratio_name.startsWith(\"-\"))\n");
			buffw.write("\t\t\t\tindirect_shift_ratio_name.replaceFirst(\"^-\", \"\");\n\n");

			buffw.write("\t\t\tif (indirect_shift_ratio_name.startsWith(\".\"))\n");
			buffw.write("\t\t\t\tindirect_shift_ratio_name.replaceFirst(\"^.\", \"0.\");\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\tindirect_shift_ratio = indirect_shift_ratio_name;\n\n");

			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_atom_type = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "chem_shift_ref.atom_type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getAtomType(String val_name) {\n\n");

			buffw.write("\t\tval_name = map_atom_type.get(val_name);\n\n");

			buffw.write("\t\tif (val_name != null && val_name.equalsIgnoreCase(\"null\"))\n");
			buffw.write("\t\t\treturn null;\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic void setAtomType(String val_name) {\n");
			buffw.write("\t\tatom_type = val_name;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic void setAtomIsotopeNumber(String val_name) {\n");
			buffw.write("\t\tatom_isotope_number = val_name;\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_citation(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_citation + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.io.*;\n");
			buffw.write("import java.net.*;\n\n");

			buffw.write("import java.security.cert.*;\n");
			buffw.write("import java.security.*;\n");
			buffw.write("import java.sql.*;\n");
			buffw.write("import java.util.logging.*;\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("import javax.net.ssl.*;\n");
			buffw.write("import javax.xml.parsers.*;\n\n");

			buffw.write("//import org.apache.xml.serialize.*;\n");
			buffw.write("import org.w3c.dom.*;\n");
			buffw.write("import org.w3c.dom.ls.*;\n");
			buffw.write("import org.xml.sax.*;\n\n");

			buffw.write("import com.fasterxml.jackson.databind.ObjectMapper;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_citation + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map_class = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "citation.class.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getClass1(String val_name) {\n");
			buffw.write("\t\treturn (String) map_class.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_type = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "citation.type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getType(String val_name) {\n");
			buffw.write("\t\treturn (String) map_type.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_status = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "citation.status.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getStatus(String val_name) {\n");
			buffw.write("\t\treturn (String) map_status.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_journal_abbrev = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "citation.journal_abbrev.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getJournalAbbrev(String val_name) {\n");
			buffw.write("\t\treturn (String) map_journal_abbrev.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\t/* synchronized */ public static String getPubMedIDByTitle(String title, boolean author_check, Connection conn_bmrb, String entry_id, int trials) {\n\n");

			buffw.write("\t\tif (trials < 0 || trials >= " + service_trials + " || " + empty_check("title") + ")\n");
			buffw.write("\t\t\treturn null;\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\ttitle = title.replaceAll(\"\\n\", \"\");\n");
			buffw.write("\t\t\tString pubmed_api = \"" + pubmed_esearch_api + "\" + URLEncoder.encode(title, \"UTF-8\");\n\n");

			buffw.write("\t\t\tTrustManager[] tm = new TrustManager[] { new X509TrustManager() {\n\n");

			buffw.write("\t\t\t\t\tpublic X509Certificate[] getAcceptedIssuers() {\n");
			buffw.write("\t\t\t\t\t\treturn null;\n");
			buffw.write("\t\t\t\t\t}\n");
			buffw.write("\t\t\t\t\t@Override\n");
			buffw.write("\t\t\t\t\tpublic void checkClientTrusted(X509Certificate[] certs, String authType) {\n");
			buffw.write("\t\t\t\t\t}\n");
			buffw.write("\t\t\t\t\t@Override\n");
			buffw.write("\t\t\t\t\tpublic void checkServerTrusted(X509Certificate[] certs, String authType) {\n");
			buffw.write("\t\t\t\t\t}\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t};\n\n");

			buffw.write("\t\t\tSSLContext sc = SSLContext.getInstance(\"SSL\");\n");
			buffw.write("\t\t\tsc.init(null, tm, new java.security.SecureRandom());\n\n");

			buffw.write("\t\t\tHttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {\n");
			buffw.write("\t\t\t\t@Override\n");
			buffw.write("\t\t\t\tpublic boolean verify(String hostname, SSLSession session) {\n");
			buffw.write("\t\t\t\t\treturn true;\n");
			buffw.write("\t\t\t\t}\n");
			buffw.write("\t\t\t});\n\n");

			buffw.write("\t\t\tURL url = new URL(pubmed_api);\n");
			buffw.write("\t\t\tHttpsURLConnection conn = (HttpsURLConnection) url.openConnection();\n");
			buffw.write("\t\t\tconn.setSSLSocketFactory(sc.getSocketFactory());\n\n");

			buffw.write("\t\t\tDocumentBuilderFactory doc_builder_fac = DocumentBuilderFactory.newInstance();\n\n");

			buffw.write("\t\t\tdoc_builder_fac.setValidating(false);\n");
			buffw.write("\t\t\tdoc_builder_fac.setNamespaceAware(false);\n");
			buffw.write("\t\t\tdoc_builder_fac.setFeature(\"http://apache.org/xml/features/nonvalidating/load-dtd-grammar\", false);\n");
			buffw.write("\t\t\tdoc_builder_fac.setFeature(\"http://apache.org/xml/features/nonvalidating/load-external-dtd\", false);\n\n");

			buffw.write("\t\t\tDocumentBuilder doc_builder = doc_builder_fac.newDocumentBuilder();\n\n");

			buffw.write("\t\t\tDocument doc = doc_builder.parse(conn.getInputStream());\n\n");

			buffw.write("\t\t\tNode root = doc.getDocumentElement();\n\n");

			buffw.write("\t\t\tif (!root.getNodeName().equals(\"eSearchResult\"))\n");
			buffw.write("\t\t\t\treturn null;\n\n");

			buffw.write("\t\t\tint count = 0;\n\n");

			buffw.write("\t\t\tfor (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {\n\n");

			buffw.write("\t\t\t\tif (child.getNodeName().equals(\"Count\")) {\n");
			buffw.write("\t\t\t\t\tcount = Integer.valueOf(child.getFirstChild().getNodeValue());\n");
			buffw.write("\t\t\t\t\tbreak;\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tif (count == 0) {\n\n");

			buffw.write("\t\t\t\tif(!title.contains(\":\"))\n");
			buffw.write("\t\t\t\t\treturn null;\n\n");

			buffw.write("\t\t\t\tString[] _title = title.split(\":\");\n");
			buffw.write("\t\t\t\tString header = _title[0].toLowerCase();\n\n");

			buffw.write("\t\t\t\tif (_title.length > 1 && (header.contains(\"article\") || header.contains(\"communication\") || header.contains(\"data\") || header.contains(\"letter\") || header.contains(\"note\") || header.contains(\"paper\") || header.contains(\"perspective\") || header.contains(\"review\") || header.contains(\"supplemental\") || header.contains(\"video\"))) {\n\n");

			buffw.write("\t\t\t\t\ttitle = \"\";\n\n");

			buffw.write("\t\t\t\t\tfor (int l = 1; l < _title.length; l++) {\n\n");

			buffw.write("\t\t\t\t\t\ttitle += _title[l];\n\n");

			buffw.write("\t\t\t\t\t\tif (l + 1 < _title.length)\n");
			buffw.write("\t\t\t\t\t\t\ttitle += \":\";\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\treturn getPubMedIDByTitle(title, author_check, conn_bmrb, entry_id, 0);\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t\treturn null;\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\telse if (count > 1)\n");
			buffw.write("\t\t\t\tauthor_check = true;\n\n");

			buffw.write("\t\t\tfor (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {\n\n");

			buffw.write("\t\t\t\tif (child.getNodeName().equals(\"IdList\")) {\n\n");

			buffw.write("\t\t\t\t\tfor (Node id_list = child.getFirstChild(); id_list != null; id_list = id_list.getNextSibling()) {\n\n");

			buffw.write("\t\t\t\t\t\tif (id_list.getNodeName().equals(\"Id\")) {\n\n");

			buffw.write("\t\t\t\t\t\t\tString pubmed_id = id_list.getFirstChild().getNodeValue();\n\n");

			buffw.write("\t\t\t\t\t\t\tNode doc_sum = getDocSumNodeByPubMedID(pubmed_id, 0);\n\n");

			buffw.write("\t\t\t\t\t\t\tif (doc_sum == null)\n");
			buffw.write("\t\t\t\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\t\t\t\tif (!author_check)\n");
			buffw.write("\t\t\t\t\t\t\t\treturn pubmed_id;\n\n");

			buffw.write("\t\t\t\t\t\t\tString _title = getTitle(null, doc_sum);\n\n");

			buffw.write("\t\t\t\t\t\t\tif (count > 1) {\n\n");

			buffw.write("\t\t\t\t\t\t\t\tif (_title == null)\n");
			buffw.write("\t\t\t\t\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\t\t\t\t\tif (!_title.toLowerCase().contains(title.toLowerCase()))\n");
			buffw.write("\t\t\t\t\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t\t\tString first_author = getFirstAuthor(null, doc_sum);\n");
			buffw.write("\t\t\t\t\t\t\tString last_author = getLastAuthor(null, doc_sum);\n\n");

			buffw.write("\t\t\t\t\t\t\tif (first_author == null || last_author == null)\n");
			buffw.write("\t\t\t\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\t\t\t\tif (checkAuthorFamilyName(first_author.split(\" \")[0], last_author.split(\" \")[0], conn_bmrb, entry_id))\n");
			buffw.write("\t\t\t\t\t\t\t\treturn pubmed_id;\n\n");
			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\treturn null;\n\n");

			buffw.write("\t\t} catch (MalformedURLException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (IOException e) {\n");
			buffw.write("\n\t\t\ttry {\n\t\t\t\tThread.sleep(" + service_wait + ");\n\t\t\t} catch (InterruptedException e1) {\n\t\t\t\te1.printStackTrace();\n\t\t\t}\n\n");
			buffw.write("\t\t\treturn getPubMedIDByTitle(title, author_check, conn_bmrb, entry_id, ++trials);\n\n");
			buffw.write("\t\t\t//e.printStackTrace();\n");
			buffw.write("\t\t} catch (ParserConfigurationException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (SAXException e) {\n");
			buffw.write("\t\t\t//e.printStackTrace();\n");
			buffw.write("\t\t} catch (NoSuchAlgorithmException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (KeyManagementException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn null;\n");
			buffw.write("\t}\n\n");

			buffw.write("\t/* synchronized */ public static String getDOIByTitle(String title, boolean author_check, Connection conn_bmrb, String entry_id, String year, int trials) {\n\n");

			buffw.write("\t\tif (trials < 0 || trials >= " + service_trials + " || " + empty_check("title") + ")\n");
			buffw.write("\t\t\treturn null;\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\t" + file_prefix + "_" + util_crossref + "[] crossrefs = null;\n\n");

			buffw.write("\t\t\ttitle = title.replaceAll(\"\\n\", \"\");\n");
			buffw.write("\t\t\tString crossref_doi_api = \"" + crossref_doi_api + "\" + URLEncoder.encode(title, \"UTF-8\") + \"&year=\" + year + \"&rows=1\";\n");

			buffw.write("\t\t\tTrustManager[] tm = new TrustManager[] { new X509TrustManager() {\n\n");

			buffw.write("\t\t\t\t\tpublic X509Certificate[] getAcceptedIssuers() {\n");
			buffw.write("\t\t\t\t\t\treturn null;\n");
			buffw.write("\t\t\t\t\t}\n");
			buffw.write("\t\t\t\t\t@Override\n");
			buffw.write("\t\t\t\t\tpublic void checkClientTrusted(X509Certificate[] certs, String authType) {\n");
			buffw.write("\t\t\t\t\t}\n");
			buffw.write("\t\t\t\t\t@Override\n");
			buffw.write("\t\t\t\t\tpublic void checkServerTrusted(X509Certificate[] certs, String authType) {\n");
			buffw.write("\t\t\t\t\t}\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t};\n\n");

			buffw.write("\t\t\tSSLContext sc = SSLContext.getInstance(\"SSL\");\n");
			buffw.write("\t\t\tsc.init(null, tm, new java.security.SecureRandom());\n\n");

			buffw.write("\t\t\tHttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {\n");
			buffw.write("\t\t\t\t@Override\n");
			buffw.write("\t\t\t\tpublic boolean verify(String hostname, SSLSession session) {\n");
			buffw.write("\t\t\t\t\treturn true;\n");
			buffw.write("\t\t\t\t}\n");
			buffw.write("\t\t\t});\n\n");

			buffw.write("\t\t\tURL url = new URL(crossref_doi_api);\n");
			buffw.write("\t\t\tHttpsURLConnection conn = (HttpsURLConnection) url.openConnection();\n");
			buffw.write("\t\t\tconn.setSSLSocketFactory(sc.getSocketFactory());\n\n");

			buffw.write("\t\t\tObjectMapper mapper = new ObjectMapper();\n\n");

			buffw.write("\t\t\tcrossrefs = mapper.readValue(conn.getInputStream(), " + file_prefix + "_" + util_crossref + "[].class);\n\n");

			buffw.write("\t\t\tif (crossrefs == null || crossrefs.length != 1)\n");
			buffw.write("\t\t\t\treturn null;\n\n");

			buffw.write("\t\t\t" + file_prefix + "_" + util_crossref + " crossref = crossrefs[0];\n\n");

			buffw.write("\t\t\tif (crossref.getScore() < 2.0 || crossref.getTitle() == null)\n");
			buffw.write("\t\t\t\treturn null;\n\n");

			buffw.write("\t\t\t" + file_prefix + "_" + util_alignmentstrict + " align = new " + file_prefix + "_" + util_alignmentstrict + "(title.trim().replaceAll(\"\\\\s\", \"\").toLowerCase().replaceAll(\"[^\\\\x01-\\\\x7f]\", \"\").toCharArray(), crossref.getTitle().trim().replaceAll(\"\\\\s\", \"\").toLowerCase().replaceAll(\"[^\\\\x01-\\\\x7f]\", \"\").toCharArray());\n\n");

			buffw.write("\t\t\tif ((float) align.score / (float) (crossref.getTitle().length() - Math.abs(title.length() - crossref.getTitle().length())) <= 0.8)\n");
			buffw.write("\t\t\t\treturn null;\n\n");

			buffw.write("\t\t\tString[] citations = crossref.getFullCitation().split(\",\");\n");
			buffw.write("\t\t\tString first_author_family_name = null;\n");
			buffw.write("\t\t\tString second_author_family_name = null;\n");
			buffw.write("\t\t\tString last_author_family_name = null;\n\n");

			buffw.write("\t\t\tfor (String citation : citations) {\n\n");

			buffw.write("\t\t\t\tcitation = citation.trim();\n\n");

			buffw.write("\t\t\t\tif (citation.matches(\"^[0-9]+$\"))\n");
			buffw.write("\t\t\t\t\tbreak;\n\n");

			buffw.write("\t\t\t\tString[] author = citation.split(\" \");\n\n");

			buffw.write("\t\t\t\tif (first_author_family_name == null)\n");
			buffw.write("\t\t\t\t\tfirst_author_family_name = author[author.length - 1];\n\n");

			buffw.write("\t\t\t\telse if (second_author_family_name == null)\n");
			buffw.write("\t\t\t\t\tsecond_author_family_name = author[author.length - 1];\n\n");

			buffw.write("\t\t\t\tlast_author_family_name = author[author.length - 1];\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tif (first_author_family_name == null || last_author_family_name == null)\n");
			buffw.write("\t\t\t\treturn null;\n\n");

			buffw.write("\t\t\tif (checkAuthorFamilyName(first_author_family_name, last_author_family_name, conn_bmrb, entry_id) || (second_author_family_name != null && checkAuthorFamilyName(second_author_family_name, last_author_family_name, conn_bmrb, entry_id)))\n");
			buffw.write("\t\t\t\treturn crossref.getDoi().replaceFirst(\"https?//dx.doi.org/\", \"\");\n\n");

			buffw.write("\t\t} catch (UnsupportedEncodingException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (MalformedURLException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (NoSuchAlgorithmException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (KeyManagementException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (IOException e) {\n");
			buffw.write("\n\t\t\ttry {\n\t\t\t\tThread.sleep(" + service_wait + ");\n\t\t\t} catch (InterruptedException e1) {\n\t\t\t\te1.printStackTrace();\n\t\t\t}\n\n");
			buffw.write("\t\t\treturn getDOIByTitle(title, author_check, conn_bmrb, entry_id, year, ++trials);\n\n");
			buffw.write("\t\t\t//e.printStackTrace();\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn null;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static boolean checkAuthorFamilyName(String first_author_family_name, String last_author_family_name, Connection conn_bmrb, String entry_id) {\n\n");

			buffw.write("\t\tif (" + empty_check("first_author_family_name") + ")\n");
			buffw.write("\t\t\treturn false;\n\n");

			buffw.write("\t\tif (" + empty_check("last_author_family_name") + ")\n");
			buffw.write("\t\t\treturn false;\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tString query = new String(\"select \\\"Family_name\\\" from \\\"Citation_author\\\" where \\\"Entry_ID\\\"='\" + entry_id + \"' and \\\"Family_name\\\" is not null\");\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tstate = conn_bmrb.createStatement();\n");
			buffw.write("\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\tfirst_author_family_name = first_author_family_name.toLowerCase();\n");
			buffw.write("\t\t\tlast_author_family_name = last_author_family_name.toLowerCase();\n\n");

			buffw.write("\t\t\tint min_match_first_author = first_author_family_name.length() - first_author_family_name.replaceAll(\"[\\\\x01-\\\\x7f]\", \"\").length() - 1;\n");
			buffw.write("\t\t\tint min_match_last_author = last_author_family_name.length() - last_author_family_name.replaceAll(\"[\\\\x01-\\\\x7f]\", \"\").length() - 1;\n\n");

			buffw.write("\t\t\tboolean found_first_author = false;\n");
			buffw.write("\t\t\tboolean found_last_author = false;\n\n");

			buffw.write("\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\tString family_name = rset.getString(\"Family_name\");\n\n");

			buffw.write("\t\t\t\tif (!found_first_author) {\n\n");

			buffw.write("\t\t\t\t\t" + file_prefix + "_" + util_alignmentstrict + " align = new " + file_prefix + "_" + util_alignmentstrict + "(family_name.toLowerCase().toCharArray(), first_author_family_name.toCharArray());\n\n");

			buffw.write("\t\t\t\t\tif (align.score >= min_match_first_author)\n");
			buffw.write("\t\t\t\t\t\tfound_first_author = true;\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t\telse if (!found_last_author) {\n\n");

			buffw.write("\t\t\t\t\t" + file_prefix + "_" + util_alignmentstrict + " align = new " + file_prefix + "_" + util_alignmentstrict + "(family_name.toLowerCase().toCharArray(), last_author_family_name.toCharArray());\n\n");

			buffw.write("\t\t\t\t\tif (align.score >= min_match_last_author)\n");
			buffw.write("\t\t\t\t\t\tfound_last_author = true;\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tif (found_first_author && found_last_author)\n");
			buffw.write("\t\t\t\treturn true;\n\n");

			buffw.write("\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_citation + ".class.getName());\n");
			buffw.write("\t\t\tlgr.log(Level.SEVERE, ex.getMessage(), ex);\n");
			buffw.write("\t\t\tSystem.exit(1);\n\n");

			buffw.write("\t\t} finally {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_citation + ".class.getName());\n");
			buffw.write("\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn false;\n");
			buffw.write("\t}\n\n");

			buffw.write("\t/* synchronized */ public static Node getDocSumNodeByPubMedID(String pubmed_id, int trials) {\n\n");

			buffw.write("\t\tif (trials < 0 || trials >= " + service_trials + " || " + empty_check("pubmed_id") + " || !pubmed_id.matches(\"^[0-9]+$\"))\n");
			buffw.write("\t\t\treturn null;\n\n");

			buffw.write("\t\tFile esum_file = new File(" + file_prefix + "_" + util_main + ".esum_dir_name + pubmed_id + \".xml\");\n\n");

			buffw.write("\t\tif (esum_file.exists())\n");
			buffw.write("\t\t\treturn getDocSumNodeByPubMedID(pubmed_id, trials, esum_file);\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tTrustManager[] tm = new TrustManager[] { new X509TrustManager() {\n\n");

			buffw.write("\t\t\t\t\tpublic X509Certificate[] getAcceptedIssuers() {\n");
			buffw.write("\t\t\t\t\t\treturn null;\n");
			buffw.write("\t\t\t\t\t}\n");
			buffw.write("\t\t\t\t\t@Override\n");
			buffw.write("\t\t\t\t\tpublic void checkClientTrusted(X509Certificate[] certs, String authType) {\n");
			buffw.write("\t\t\t\t\t}\n");
			buffw.write("\t\t\t\t\t@Override\n");
			buffw.write("\t\t\t\t\tpublic void checkServerTrusted(X509Certificate[] certs, String authType) {\n");
			buffw.write("\t\t\t\t\t}\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t};\n\n");

			buffw.write("\t\t\tSSLContext sc = SSLContext.getInstance(\"SSL\");\n");
			buffw.write("\t\t\tsc.init(null, tm, new java.security.SecureRandom());\n\n");

			buffw.write("\t\t\tHttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {\n");
			buffw.write("\t\t\t\t@Override\n");
			buffw.write("\t\t\t\tpublic boolean verify(String hostname, SSLSession session) {\n");
			buffw.write("\t\t\t\t\treturn true;\n");
			buffw.write("\t\t\t\t}\n");
			buffw.write("\t\t\t});\n\n");

			buffw.write("\t\t\tString pubmed_api = \"" + pubmed_esummary_api + "\" + pubmed_id;\n\n");

			buffw.write("\t\t\tURL url = new URL(pubmed_api);\n");
			buffw.write("\t\t\tHttpsURLConnection conn = (HttpsURLConnection) url.openConnection();\n");
			buffw.write("\t\t\tconn.setSSLSocketFactory(sc.getSocketFactory());\n\n");

			buffw.write("\t\t\tDocumentBuilderFactory doc_builder_fac = DocumentBuilderFactory.newInstance();\n\n");

			buffw.write("\t\t\tdoc_builder_fac.setValidating(false);\n");
			buffw.write("\t\t\tdoc_builder_fac.setNamespaceAware(false);\n");
			buffw.write("\t\t\tdoc_builder_fac.setFeature(\"http://apache.org/xml/features/nonvalidating/load-dtd-grammar\", false);\n");
			buffw.write("\t\t\tdoc_builder_fac.setFeature(\"http://apache.org/xml/features/nonvalidating/load-external-dtd\", false);\n\n");

			buffw.write("\t\t\tDocumentBuilder doc_builder = doc_builder_fac.newDocumentBuilder();\n\n");

			buffw.write("\t\t\tDocument doc = doc_builder.parse(conn.getInputStream());\n\n");

			buffw.write("\t\t\tNode root = doc.getDocumentElement();\n\n");

			buffw.write("\t\t\tif (!root.getNodeName().equals(\"eSummaryResult\"))\n");
			buffw.write("\t\t\t\treturn null;\n\n");

			buffw.write("\t\t\tfor (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {\n\n");

			buffw.write("\t\t\t\tif (child.getNodeName().equals(\"DocSum\")) {\n\n");

			buffw.write("\t\t\t\t\twritePubMedESummary(esum_file, doc);\n\n");

			buffw.write("\t\t\t\t\treturn child;\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\treturn null;\n\n");

			buffw.write("\t\t} catch (MalformedURLException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (IOException e) {\n");
			buffw.write("\n\t\t\ttry {\n\t\t\t\tThread.sleep(" + service_wait + ");\n\t\t\t} catch (InterruptedException e1) {\n\t\t\t\te1.printStackTrace();\n\t\t\t}\n\n");
			buffw.write("\t\t\treturn getDocSumNodeByPubMedID(pubmed_id, ++trials);\n\n");
			buffw.write("\t\t\t//e.printStackTrace();\n");
			buffw.write("\t\t} catch (ParserConfigurationException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (SAXException e) {\n");
			buffw.write("\t\t\t//e.printStackTrace();\n");
			buffw.write("\t\t} catch (NoSuchAlgorithmException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (KeyManagementException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn null;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tprivate static void writePubMedESummary(File esum_file, Document doc) {\n\n");

			buffw.write("\t\tFile esum_file_ = new File(esum_file.getPath() + \"~\");\n\n");

			buffw.write("\t\tif (esum_file.exists())\n");
			buffw.write("\t\t\treturn;\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tDOMImplementation domImpl = doc.getImplementation();\n");
			buffw.write("\t\t\tDOMImplementationLS domImplLS = (DOMImplementationLS) domImpl.getFeature(\"LS\", \"3.0\");\n\n");

			buffw.write("\t\t\tLSOutput output = domImplLS.createLSOutput();\n");
			buffw.write("\t\t\toutput.setByteStream(new FileOutputStream(esum_file_));\n\n");

			buffw.write("\t\t\tLSSerializer serializer = domImplLS.createLSSerializer();\n");
			buffw.write("\t\t\tserializer.getDomConfig().setParameter(\"format-pretty-print\", Boolean.TRUE);\n");
			buffw.write("\t\t\tserializer.write(doc, output);\n\n");

			buffw.write("/*\n\t\t\tOutputFormat format = new OutputFormat(doc);\n\n");

			buffw.write("\t\t\tformat.setIndenting(true);\n");
			buffw.write("\t\t\tformat.setIndent(2);\n\n");

			buffw.write("\t\t\tFileWriter writer = new FileWriter(esum_file_);\n");
			buffw.write("\t\t\tXMLSerializer serializer = new XMLSerializer(writer, format);\n\n");

			buffw.write("\t\t\tserializer.serialize(doc);\n*/\n\n");

			buffw.write("\t\t\tesum_file_.renameTo(esum_file);\n\n");

			buffw.write("\t\t} catch (IOException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t}\n\n");

			buffw.write("\tprivate static Node getDocSumNodeByPubMedID(String pubmed_id, int trials, File esum_file) {\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tDocumentBuilderFactory doc_builder_fac = DocumentBuilderFactory.newInstance();\n\n");

			buffw.write("\t\t\tdoc_builder_fac.setValidating(false);\n");
			buffw.write("\t\t\tdoc_builder_fac.setNamespaceAware(false);\n");
			buffw.write("\t\t\tdoc_builder_fac.setFeature(\"http://apache.org/xml/features/nonvalidating/load-dtd-grammar\", false);\n");
			buffw.write("\t\t\tdoc_builder_fac.setFeature(\"http://apache.org/xml/features/nonvalidating/load-external-dtd\", false);\n\n");

			buffw.write("\t\t\tDocumentBuilder doc_builder = doc_builder_fac.newDocumentBuilder();\n\n");

			buffw.write("\t\t\tDocument doc = doc_builder.parse(esum_file);\n\n");

			buffw.write("\t\t\tNode root = doc.getDocumentElement();\n\n");

			buffw.write("\t\t\tif (root.getNodeName().equals(\"eSummaryResult\")) {\n\n");

			buffw.write("\t\t\t\tfor (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {\n\n");

			buffw.write("\t\t\t\t\tif (child.getNodeName().equals(\"DocSum\"))\n");
			buffw.write("\t\t\t\t\t\treturn child;\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t} catch (ParserConfigurationException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (SAXException e) {\n");
			buffw.write("\t\t\t//e.printStackTrace();\n");
			buffw.write("\t\t} catch (IOException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\tesum_file.delete();\n\n");

			buffw.write("\t\treturn getDocSumNodeByPubMedID(pubmed_id, trials);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getFirstAuthor(String val_name, Node doc_sum) {\n\n");

			buffw.write("\t\tif (doc_sum == null)\n");
			buffw.write("\t\t\treturn val_name;\n\n");

			buffw.write("\t\tfor (Node child = doc_sum.getFirstChild(); child != null; child = child.getNextSibling()) {\n\n");

			buffw.write("\t\t\tif (child.getNodeName().equals(\"Item\") && child.hasAttributes()) {\n\n");

			buffw.write("\t\t\t\tNamedNodeMap node_map = child.getAttributes();\n\n");

			buffw.write("\t\t\t\tif (node_map != null && child.hasChildNodes() && node_map.item(0).getTextContent().equals(\"AuthorList\")) {\n\n");

			buffw.write("\t\t\t\t\tfor (Node author = child.getFirstChild(); author != null; author = author.getNextSibling()) {\n\n");

			buffw.write("\t\t\t\t\t\tif (author.getNodeName().equals(\"Item\") && author.hasAttributes()) {\n\n");

			buffw.write("\t\t\t\t\t\t\tnode_map = author.getAttributes();\n\n");

			buffw.write("\t\t\t\t\t\t\tif (node_map != null && author.hasChildNodes() && node_map.item(0).getTextContent().equals(\"Author\"))\n");
			buffw.write("\t\t\t\t\t\t\t\treturn author.getFirstChild().getNodeValue();\n\n");

			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getLastAuthor(String val_name, Node doc_sum) {\n\n");

			buffw.write("\t\tif (doc_sum == null)\n");
			buffw.write("\t\t\treturn val_name;\n\n");

			buffw.write("\t\tfor (Node child = doc_sum.getFirstChild(); child != null; child = child.getNextSibling()) {\n\n");

			buffw.write("\t\t\tif (child.getNodeName().equals(\"Item\") && child.hasAttributes()) {\n\n");

			buffw.write("\t\t\t\tNamedNodeMap node_map = child.getAttributes();\n\n");

			buffw.write("\t\t\t\tif (node_map != null && child.hasChildNodes() && node_map.item(0).getTextContent().equals(\"LastAuthor\"))\n");
			buffw.write("\t\t\t\t\treturn child.getFirstChild().getNodeValue();\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getDOI(String val_name, Node doc_sum) {\n\n");

			buffw.write("\t\tif (doc_sum == null)\n");
			buffw.write("\t\t\treturn val_name.matches(\"^[0-9]$\") ? null : val_name;\n\n");

			buffw.write("\t\tfor (Node child = doc_sum.getFirstChild(); child != null; child = child.getNextSibling()) {\n\n");

			buffw.write("\t\t\tif (child.getNodeName().equals(\"Item\") && child.hasAttributes()) {\n\n");

			buffw.write("\t\t\t\tNamedNodeMap node_map = child.getAttributes();\n\n");

			buffw.write("\t\t\t\tif (node_map != null && child.hasChildNodes() && node_map.item(0).getTextContent().equals(\"DOI\"))\n");
			buffw.write("\t\t\t\t\treturn child.getFirstChild().getNodeValue();\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn null;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getTitle(String val_name, Node doc_sum) {\n\n");

			buffw.write("\t\tif (doc_sum == null)\n");
			buffw.write("\t\t\treturn val_name;\n\n");

			buffw.write("\t\tfor (Node child = doc_sum.getFirstChild(); child != null; child = child.getNextSibling()) {\n\n");

			buffw.write("\t\t\tif (child.getNodeName().equals(\"Item\") && child.hasAttributes()) {\n\n");

			buffw.write("\t\t\t\tNamedNodeMap node_map = child.getAttributes();\n\n");

			buffw.write("\t\t\t\tif (node_map != null && child.hasChildNodes() && node_map.item(0).getTextContent().equals(\"Title\"))\n");
			buffw.write("\t\t\t\t\treturn child.getFirstChild().getNodeValue();\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getJournalNameFull(String val_name, Node doc_sum) {\n\n");

			buffw.write("\t\tif (doc_sum == null)\n");
			buffw.write("\t\t\treturn val_name;\n\n");

			buffw.write("\t\tfor (Node child = doc_sum.getFirstChild(); child != null; child = child.getNextSibling()) {\n\n");

			buffw.write("\t\t\tif (child.getNodeName().equals(\"Item\") && child.hasAttributes()) {\n\n");

			buffw.write("\t\t\t\tNamedNodeMap node_map = child.getAttributes();\n\n");

			buffw.write("\t\t\t\tif (node_map != null && child.hasChildNodes() && node_map.item(0).getTextContent().equals(\"FullJournalName\"))\n");
			buffw.write("\t\t\t\t\treturn child.getFirstChild().getNodeValue();\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getJournalAbbrev(String val_name, Node doc_sum) {\n\n");

			buffw.write("\t\tif (doc_sum == null)\n");
			buffw.write("\t\t\treturn getJournalAbbrev(val_name);\n\n");

			buffw.write("\t\tfor (Node child = doc_sum.getFirstChild(); child != null; child = child.getNextSibling()) {\n\n");

			buffw.write("\t\t\tif (child.getNodeName().equals(\"Item\") && child.hasAttributes()) {\n\n");

			buffw.write("\t\t\t\tNamedNodeMap node_map = child.getAttributes();\n\n");

			buffw.write("\t\t\t\tif (node_map != null && child.hasChildNodes() && node_map.item(0).getTextContent().equals(\"Source\")) {\n\n");

			buffw.write("\t\t\t\t\tString[] array = getJournalNameFull(null, doc_sum).toLowerCase().split(\" \\\\(\");\n");
			buffw.write("\t\t\t\t\tString fullname = array[0].replaceAll(\"^the \", \"\").replaceAll(\" the \", \" \").replaceAll(\" of \", \" \").replaceAll(\" and \", \" \");\n");
			buffw.write("\t\t\t\t\tString Abbrev = child.getFirstChild().getNodeValue().replaceAll(\"\\\\.\", \" \").replaceAll(\"  \", \" \");\n");
			buffw.write("\t\t\t\t\tString abbrev = Abbrev.toLowerCase();\n\n");

			buffw.write("\t\t\t\t\tchar[] A = Abbrev.toCharArray();\n");
			buffw.write("\t\t\t\t\tchar[] a = abbrev.toCharArray();\n\n");

			buffw.write("\t\t\t\t\t" + file_prefix + "_" + util_alignmentstrict + " alignment = new " + file_prefix + "_" + util_alignmentstrict + "(fullname.toCharArray(), a);\n\n");

			buffw.write("\t\t\t\t\tif (alignment.score <= 0)\n");
			buffw.write("\t\t\t\t\t\treturn getJournalAbbrev(val_name);\n\n");

			buffw.write("\t\t\t\t\tchar[] af = alignment.S1.toCharArray();\n");
			buffw.write("\t\t\t\t\tchar[] aa = alignment.S2.toCharArray();\n\n");

			buffw.write("\t\t\t\t\tint ai = 0;\n\n");

			buffw.write("\t\t\t\t\tfor (int i = 0; i < A.length; i++) {\n\n");

			buffw.write("\t\t\t\t\t\tif (a[i] != ' ') {\n\n");

			buffw.write("\t\t\t\t\t\t\twhile (ai < aa.length && a[i] != aa[ai])\n");
			buffw.write("\t\t\t\t\t\t\t\tai++;\n\n");

			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t\telse if (++i < A.length) {\n\n");

			buffw.write("\t\t\t\t\t\t\twhile (ai < aa.length && a[i] != aa[ai])\n");
			buffw.write("\t\t\t\t\t\t\t\tai++;\n\n");

			buffw.write("\t\t\t\t\t\t\tif (ai < aa.length) {\n\n");

			buffw.write("\t\t\t\t\t\t\t\tint j = 2;\n\n");

			buffw.write("\t\t\t\t\t\t\t\twhile (i - j >= 0 && ai - j >= 0 && a[i - j] != ' ' && aa[ai - j] != ' ') {\n\n");

			buffw.write("\t\t\t\t\t\t\t\t\tif (a[i -j] != aa[ai - j]) {\n");
			buffw.write("\t\t\t\t\t\t\t\t\t\tA[i - 1] = '.';\n");
			buffw.write("\t\t\t\t\t\t\t\t\t\tbreak;\n");
			buffw.write("\t\t\t\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t\t\t\t\tj++;\n");
			buffw.write("\t\t\t\t\t\t\t\t}\n");
			buffw.write("\t\t\t\t\t\t\t}\n");
			buffw.write("\t\t\t\t\t\t}\n");
			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\tAbbrev = String.valueOf(A).replaceAll(\"\\\\.\", \"\\\\. \");\n\n");

			buffw.write("\t\t\t\t\tif (af[af.length - 1] != aa[aa.length - 1])\n");
			buffw.write("\t\t\t\t\t\tAbbrev = Abbrev.concat(\".\");\n\n");

			buffw.write("\t\t\t\t\treturn Abbrev;\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn getJournalAbbrev(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getJournalVolume(String val_name, Node doc_sum) {\n\n");

			buffw.write("\t\tif (doc_sum == null)\n");
			buffw.write("\t\t\treturn val_name;\n\n");

			buffw.write("\t\tfor (Node child = doc_sum.getFirstChild(); child != null; child = child.getNextSibling()) {\n\n");

			buffw.write("\t\t\tif (child.getNodeName().equals(\"Item\") && child.hasAttributes()) {\n\n");

			buffw.write("\t\t\t\tNamedNodeMap node_map = child.getAttributes();\n\n");

			buffw.write("\t\t\t\tif (node_map != null && child.hasChildNodes() && node_map.item(0).getTextContent().equals(\"Volume\"))\n");
			buffw.write("\t\t\t\t\treturn child.getFirstChild().getNodeValue();\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getJournalIssue(String val_name, Node doc_sum) {\n\n");

			buffw.write("\t\tif (doc_sum == null)\n");
			buffw.write("\t\t\treturn val_name;\n\n");

			buffw.write("\t\tfor (Node child = doc_sum.getFirstChild(); child != null; child = child.getNextSibling()) {\n\n");

			buffw.write("\t\t\tif (child.getNodeName().equals(\"Item\") && child.hasAttributes()) {\n\n");

			buffw.write("\t\t\t\tNamedNodeMap node_map = child.getAttributes();\n\n");

			buffw.write("\t\t\t\tif (node_map != null && child.hasChildNodes() && node_map.item(0).getTextContent().equals(\"Issue\"))\n");
			buffw.write("\t\t\t\t\treturn child.getFirstChild().getNodeValue();\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getJournalISSN(String val_name, Node doc_sum) {\n\n");

			buffw.write("\t\tif (doc_sum == null)\n");
			buffw.write("\t\t\treturn val_name.matches(\"^[0-9]$\") ? null : val_name;\n\n");

			buffw.write("\t\tfor (Node child = doc_sum.getFirstChild(); child != null; child = child.getNextSibling()) {\n\n");

			buffw.write("\t\t\tif (child.getNodeName().equals(\"Item\") && child.hasAttributes()) {\n\n");

			buffw.write("\t\t\t\tNamedNodeMap node_map = child.getAttributes();\n\n");

			buffw.write("\t\t\t\tif (node_map != null && child.hasChildNodes() && node_map.item(0).getTextContent().equals(\"ISSN\"))\n");
			buffw.write("\t\t\t\t\treturn child.getFirstChild().getNodeValue();\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn null;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getPageFirst(String val_name, Node doc_sum) {\n\n");

			buffw.write("\t\tif (doc_sum == null)\n");
			buffw.write("\t\t\treturn val_name;\n\n");

			buffw.write("\t\tfor (Node child = doc_sum.getFirstChild(); child != null; child = child.getNextSibling()) {\n\n");

			buffw.write("\t\t\tif (child.getNodeName().equals(\"Item\") && child.hasAttributes()) {\n\n");

			buffw.write("\t\t\t\tNamedNodeMap node_map = child.getAttributes();\n\n");

			buffw.write("\t\t\t\tif (node_map != null && child.hasChildNodes() && node_map.item(0).getTextContent().equals(\"Pages\")) {\n\n");

			buffw.write("\t\t\t\t\tString pages = child.getFirstChild().getNodeValue();\n\n");

			buffw.write("\t\t\t\t\treturn pages.split(\"-\")[0];\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getPageLast(String val_name, Node doc_sum) {\n\n");

			buffw.write("\t\tif (doc_sum == null)\n");
			buffw.write("\t\t\treturn val_name;\n\n");

			buffw.write("\t\tfor (Node child = doc_sum.getFirstChild(); child != null; child = child.getNextSibling()) {\n\n");

			buffw.write("\t\t\tif (child.getNodeName().equals(\"Item\") && child.hasAttributes()) {\n\n");

			buffw.write("\t\t\t\tNamedNodeMap node_map = child.getAttributes();\n\n");

			buffw.write("\t\t\t\tif (node_map != null && child.hasChildNodes() && node_map.item(0).getTextContent().equals(\"Pages\")) {\n\n");

			buffw.write("\t\t\t\t\tString pages = child.getFirstChild().getNodeValue();\n");
			buffw.write("\t\t\t\t\tString[] array = pages.split(\"-\");\n\n");

			buffw.write("\t\t\t\t\tchar[] page_first = array[0].toCharArray();\n\n");

			buffw.write("\t\t\t\t\tif (array.length > 1 && !(" + empty_check("array[1]") + ")) {\n\n");

			buffw.write("\t\t\t\t\t\tchar[] page_last = array[1].toCharArray();\n\n");

			buffw.write("\t\t\t\t\t\tint len_first = array[0].length();\n");
			buffw.write("\t\t\t\t\t\tint len_last = array[1].length();\n\n");

			buffw.write("\t\t\t\t\t\tif (len_first < len_last)\n");
			buffw.write("\t\t\t\t\t\t\treturn array[1];\n\n");

			buffw.write("\t\t\t\t\t\tfor (int l = 1; l <= len_last; l++)\n");
			buffw.write("\t\t\t\t\t\t\tpage_first[len_first - l] = page_last[len_last - l];\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\treturn String.valueOf(page_first);\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getYear(String val_name, Node doc_sum) {\n\n");

			buffw.write("\t\tif (doc_sum == null)\n");
			buffw.write("\t\t\treturn val_name;\n\n");

			buffw.write("\t\tfor (Node child = doc_sum.getFirstChild(); child != null; child = child.getNextSibling()) {\n\n");

			buffw.write("\t\t\tif (child.getNodeName().equals(\"Item\") && child.hasAttributes()) {\n\n");

			buffw.write("\t\t\t\tNamedNodeMap node_map = child.getAttributes();\n\n");

			buffw.write("\t\t\t\tif (node_map != null && child.hasChildNodes() && node_map.item(0).getTextContent().equals(\"PubDate\")) {\n\n");

			buffw.write("\t\t\t\t\tString pages = child.getFirstChild().getNodeValue();\n\n");

			buffw.write("\t\t\t\t\treturn pages.split(\" \")[0];\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_citationauthor(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_citationauthor + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_citationauthor + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "citation_author.family_title.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getFamilyTitle(String val_name) {\n\n");

			buffw.write("\t\tval_name = map.get(val_name);\n\n");

			buffw.write("\t\tif (val_name != null && val_name.equalsIgnoreCase(\"null\"))\n");
			buffw.write("\t\t\treturn null;\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_conformerfamilyrefinement(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_conformerfamilyrefinement + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_conformerfamilyrefinement + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "conformer_family_refinement.refine_method.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getRefineMethod(String val_name) {\n");
			buffw.write("\t\treturn (String) map.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_conformerstatlist(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_conformerstatlist + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_conformerstatlist + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map_conformer_selection_criteria = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "conformer_stat_list.conformer_selection_criteria.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getConformerSelectionCriteria(String val_name) {\n");
			buffw.write("\t\treturn (String) map_conformer_selection_criteria.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_rep_conformer_selection_criteria = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "conformer_stat_list.rep_conformer_selection_criteria.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getRepConformerSelectionCriteria(String val_name) {\n");
			buffw.write("\t\treturn (String) map_rep_conformer_selection_criteria.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_constraintfile(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_constraintfile + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_constraintfile + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map_constraint_type = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "constraint_file.constraint_type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getConstraintType(String val_name) {\n");
			buffw.write("\t\treturn (String) map_constraint_type.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_constraint_subtype = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "constraint_file.constraint_subtype.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getConstraintSubtype(String val_name) {\n");
			buffw.write("\t\treturn (String) map_constraint_subtype.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_constraint_subsubtype = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "constraint_file.constraint_subsubtype.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getConstraintSubsubtype(String val_name) {\n");
			buffw.write("\t\treturn (String) map_constraint_subsubtype.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_couplingconstantexperiment(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_couplingconstantexperiment + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.sql.*;\n");
			buffw.write("import java.util.logging.*;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_couplingconstantexperiment + " {\n\n");

			buffw.write("\tpublic static String getExperimentID(String val_name, Connection conn_bmrb, String entry_id, String sample_id, String coupling_constant_list_id) {\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tString query = new String(\"select \\\"ID\\\",\\\"Name\\\",\\\"Sample_ID\\\" from \\\"Experiment\\\" where \\\"Entry_ID\\\"='\" + entry_id + \"'\");\n\n");

			buffw.write("\t\tint experiments = 0;\n");
			buffw.write("\t\tString id = null;\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tstate = conn_bmrb.createStatement();\n");
			buffw.write("\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\tString experiment_name = rset.getString(\"Name\");\n\n");

			buffw.write("\t\t\t\tif (" + empty_check("experiment_name") + ") {\n\n");

			buffw.write("\t\t\t\t\tif (!(" + empty_check("sample_id") + ")) {\n\n");

			buffw.write("\t\t\t\t\t\tString _sample_id = rset.getString(\"Sample_ID\");\n\n");

			buffw.write("\t\t\t\t\t\tif (_sample_id != null && _sample_id.equals(sample_id)) {\n");
			buffw.write("\t\t\t\t\t\t\tid = rset.getString(\"ID\");\n");
			buffw.write("\t\t\t\t\t\t\texperiments++;\n");
			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\tcontinue;\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t\tif (experiment_name.matches(\".*J.*\") || experiment_name.matches(\".*[Cc][Oo][Ss][Yy].*\") || experiment_name.matches(\".*[Hh][Nn][Hh][Aa].*\") || experiment_name.matches(\".*[Ii][Pa][Aa][Pp].*\") || experiment_name.matches(\".*[Tt][Ro][Oo][Ss][Yy].*\") || experiment_name.matches(\".*[Cc][Oo][Uu][Pp][Ll][Ee].*\")) {\n");
			buffw.write("\t\t\t\t\tid = rset.getString(\"ID\");\n");
			buffw.write("\t\t\t\t\texperiments++;\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tif (experiments == 1)\n");
			buffw.write("\t\t\t\tval_name = id;\n\n");

			buffw.write("\t\t\telse {\n\n");

			buffw.write("\t\t\t\texperiments = 0;\n\n");

			buffw.write("\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\t\tid = rset.getString(\"ID\");\n");
			buffw.write("\t\t\t\t\texperiments++;\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t\tif (experiments == 1)\n");
			buffw.write("\t\t\t\t\tval_name = id;\n\n");

			buffw.write("\t\t\t\telse {\n\n");

			buffw.write("\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\t\tString query2 = new String(\"select \\\"Code\\\" from \\\"Coupling_constant\\\" where \\\"Entry_ID\\\"='\" + entry_id + \"' and \\\"Coupling_constant_list_ID\\\"='\" + coupling_constant_list_id + \"' and \\\"ID\\\"='1'\");\n\n");

			buffw.write("\t\t\t\t\tResultSet rset2 = state.executeQuery(query2);\n\n");

			buffw.write("\t\t\t\t\trset2.next();\n\n");

			buffw.write("\t\t\t\t\tString code = rset2.getString(\"Code\");\n\n");

			buffw.write("\t\t\t\t\trset2.close();\n\n");

			buffw.write("\t\t\t\t\tif (!(" + empty_check("code") + ")) {\n\n");

			buffw.write("\t\t\t\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\t\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\t\t\t\tString experiment_name = rset.getString(\"Name\");\n\n");

			buffw.write("\t\t\t\t\t\t\tif (" + empty_check("experiment_name") + ")\n");
			buffw.write("\t\t\t\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\t\t\t\tif ((experiment_name.matches(\".*[Cc][Oo][Ss][Yy].*\") && code.matches(\".*[Cc][Oo][Ss][Yy].*\")) || (experiment_name.matches(\".*[Hh][Nn][Hh][Aa].*\") && code.matches(\".*[Hh][Nn][Hh][Aa].*\")) || (experiment_name.matches(\".*[Ii][Pa][Aa][Pp].*\") && code.matches(\".*[Ii][Pa][Aa][Pp].*\")) || (experiment_name.matches(\".*[Tt][Ro][Oo][Ss][Yy].*\") && code.matches(\".*[Tt][Ro][Oo][Ss][Yy].*\"))) {\n\n");

			buffw.write("\t\t\t\t\t\t\t\tval_name = rset.getString(\"ID\");\n");
			buffw.write("\t\t\t\t\t\t\t\tbreak;\n\n");

			buffw.write("\t\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\tif (" + empty_check("val_name") + ")\n");
			buffw.write("\t\t\t\t\t\tval_name = \"0\";\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_couplingconstantexperiment + ".class.getName());\n");
			buffw.write("\t\t\tlgr.log(Level.SEVERE, ex.getMessage(), ex);\n");
			buffw.write("\t\t\tSystem.exit(1);\n\n");

			buffw.write("\t\t} finally {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_couplingconstantexperiment + ".class.getName());\n");
			buffw.write("\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_couplingconstantlist(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_couplingconstantlist + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_couplingconstantlist + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "coupling_constant_list.spectrometer_frequency_1h.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getSpectrometerFrequency1H(String val_name) {\n");
			buffw.write("\t\treturn (String) map.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_crossref(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_crossref + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("public class " + file_prefix + "_" + util_crossref + " {\n\n");

			buffw.write("\tprivate String doi;\n");
			buffw.write("\tprivate float score;\n");
			buffw.write("\tprivate int normalizedScore;\n");
			buffw.write("\tprivate String title;\n");
			buffw.write("\tprivate String fullCitation;\n");
			buffw.write("\tprivate String coins;\n");
			buffw.write("\tprivate String year;\n\n");

			buffw.write("\tpublic String getDoi() {\n");
			buffw.write("\t\treturn doi;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic void setDoi(String doi) {\n");
			buffw.write("\t\tthis.doi = doi;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic float getScore() {\n");
			buffw.write("\t\treturn score;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic void setScore(float score) {\n");
			buffw.write("\t\tthis.score = score;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic int getNormalizedScore() {\n");
			buffw.write("\t\treturn normalizedScore;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic void setNormalizedScore(int normalizedScore) {\n");
			buffw.write("\t\tthis.normalizedScore = normalizedScore;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic String getTitle() {\n");
			buffw.write("\t\treturn title;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic void setTitle(String title) {\n");
			buffw.write("\t\tthis.title = title;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic String getFullCitation() {\n");
			buffw.write("\t\treturn fullCitation;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic void setFullCitation(String fullCitation) {\n");
			buffw.write("\t\tthis.fullCitation = fullCitation;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic String getCoins() {\n");
			buffw.write("\t\treturn coins;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic void setCoins(String coins) {\n");
			buffw.write("\t\tthis.coins = coins;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic String getYear() {\n");
			buffw.write("\t\treturn year;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic void setYear(String year) {\n");
			buffw.write("\t\tthis.year = year;\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_dataset(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_dataset + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_dataset + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "data_set.type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getType(String val_name) {\n");
			buffw.write("\t\treturn (String) map.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_datum(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_datum + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_datum + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "datum.type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getType(String val_name) {\n");
			buffw.write("\t\treturn (String) map.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_entity(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_entity + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_entity + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map_type = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entity.type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getType(String val_name) {\n");
			buffw.write("\t\treturn (String) map_type.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_polymer_type = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entity.polymer_type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getPolymerType(String val_name) {\n");
			buffw.write("\t\treturn (String) map_polymer_type.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_thiol_state = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entity.thiol_state.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getThiolState(String val_name) {\n");
			buffw.write("\t\treturn (String) map_thiol_state.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_ambiguous_conformational_states = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entity.ambiguous_conformational_states.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getAmbiguousConformationalStates(String val_name) {\n");
			buffw.write("\t\treturn (String) map_ambiguous_conformational_states.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_ambiguous_chem_comp_sites = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entity.ambiguous_chem_comp_sites.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getAmbiguousChemCompSites(String val_name) {\n");
			buffw.write("\t\treturn (String) map_ambiguous_chem_comp_sites.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_nstd_chirality = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entity.nstd_chirality.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getNstdChirality(String val_name) {\n");
			buffw.write("\t\treturn (String) map_nstd_chirality.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_nstd_linkage = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entity.nstd_linkage.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getNstdLinkage(String val_name) {\n");
			buffw.write("\t\treturn (String) map_nstd_linkage.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_paramagnetic = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entity.paramagnetic.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getParamagnetic(String val_name) {\n");
			buffw.write("\t\treturn (String) map_paramagnetic.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String checkECNumber(String ec_number, String entry_id) {\n\n");

			buffw.write("\t\tfinal String[][] ecnumtbl = {\n\n");

			FileReader filer = new FileReader(xsd_dir_name + "entity.ec_number");
			BufferedReader bufferr = new BufferedReader(filer);

			String line = null;

			while ((line = bufferr.readLine()) != null) {

				if (!line.matches(".*;.*;.*"))
					continue;

				String[] elem = line.split(";");

				buffw.write("\t\t\t\t{\"" + elem[0] + "\", \"" + elem[1] + "\", \"" + elem[2] + "\"},\n");

			}

			bufferr.close();
			filer.close();

			buffw.write("\n\t\t};\n\n");

			buffw.write("\t\tfor (int i = 0; i < ecnumtbl.length; i++) {\n\n");

			buffw.write("\t\t\tif (ecnumtbl[i][0].equals(ec_number) && ecnumtbl[i][1].equals(entry_id))\n");
			buffw.write("\t\t\t\treturn ecnumtbl[i][2];\n\n");

			buffw.write("\t\t\tif (ecnumtbl[i][0].isEmpty() && (" + empty_check("ec_number") + ") && ecnumtbl[i][1].equals(entry_id))\n");
			buffw.write("\t\t\t\treturn ecnumtbl[i][2];\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn ec_number;\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_entityassembly(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_entityassembly + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_entityassembly + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map_physical_state = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entity_assembly.physical_state.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getPhysicalState(String val_name) {\n");
			buffw.write("\t\treturn (String) map_physical_state.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_conformational_isomer = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entity_assembly.conformational_isomer.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getConformationalIsomer(String val_name) {\n");
			buffw.write("\t\treturn (String) map_conformational_isomer.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_entitybond(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_entitybond + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_entitybond + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entity_bond.value_order.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getValueOrder(String val_name) {\n");
			buffw.write("\t\treturn (String) map.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_entitycompindex(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_entitycompindex + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.sql.*;\n");
			buffw.write("import java.util.logging.*;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_entitycompindex + " {\n\n");

			buffw.write("\tpublic static String getNumberOfMonomers(Connection conn_bmrb, String entry_id, String entity_id) {\n\n");

			buffw.write("\t\tString poly_seq_one_letter_code = getPolySeqOneLetterCode(conn_bmrb, entry_id, entity_id);\n\n");

			buffw.write("\t\tif (" + empty_check("poly_seq_one_letter_code") + ")\n");
			buffw.write("\t\t\treturn \"\";\n\n");

			buffw.write("\t\treturn String.valueOf(poly_seq_one_letter_code.length());\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getPolySeqOneLetterCode(Connection conn_bmrb, String entry_id, String entity_id) {\n\n");

			buffw.write("\t\tStringBuilder poly_seq_one_letter_code = new StringBuilder();\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tString query = new String(\"select \\\"Comp_ID\\\" from \\\"Entity_comp_index\\\" where \\\"Entry_ID\\\"='\" + entry_id + \"' and \\\"Entity_ID\\\"='\" + entity_id + \"' order by \\\"ID\\\"::integer\");\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tstate = conn_bmrb.createStatement();\n");
			buffw.write("\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\tString comp_id = rset.getString(\"Comp_ID\");\n\n");

			buffw.write("\t\t\t\tif (!(" + empty_check("comp_id") + "))\n");
			buffw.write("\t\t\t\t\tpoly_seq_one_letter_code.append(" + file_prefix + "_" + util_seqonelettercode + ".getCode(comp_id));\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_entitycompindex + ".class.getName());\n");
			buffw.write("\t\t\tlgr.log(Level.SEVERE, ex.getMessage(), ex);\n");
			buffw.write("\t\t\tSystem.exit(1);\n\n");

			buffw.write("\t\t} finally {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_entitycompindex + ".class.getName());\n");
			buffw.write("\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\tString code = poly_seq_one_letter_code.toString();\n\n");

			buffw.write("\t\treturn (code.matches(\"^X+$\") ? \".\" : code);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getPolySeqOneLetterCodeCan(Connection conn_bmrb, String entry_id, String entity_id) {\n\n");

			buffw.write("\t\tStringBuilder poly_seq_one_letter_code_can = new StringBuilder();\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tString query = new String(\"select \\\"Comp_ID\\\" from \\\"Entity_comp_index\\\" where \\\"Entry_ID\\\"='\" + entry_id + \"' and \\\"Entity_ID\\\"='\" + entity_id + \"' order by \\\"ID\\\"::integer\");\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tstate = conn_bmrb.createStatement();\n");
			buffw.write("\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\tString comp_id = rset.getString(\"Comp_ID\");\n\n");

			buffw.write("\t\t\t\tif (!(" + empty_check("comp_id") + "))\n");
			buffw.write("\t\t\t\t\tpoly_seq_one_letter_code_can.append(" + file_prefix + "_" + util_seqonelettercode + ".getCodeCan(comp_id));\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_entitycompindex + ".class.getName());\n");
			buffw.write("\t\t\tlgr.log(Level.SEVERE, ex.getMessage(), ex);\n");
			buffw.write("\t\t\tSystem.exit(1);\n\n");

			buffw.write("\t\t} finally {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_entitycompindex + ".class.getName());\n");
			buffw.write("\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\tString code = poly_seq_one_letter_code_can.toString();\n\n");

			buffw.write("\t\treturn (code.matches(\"^X+$\") ? \".\" : code);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_entitydblink(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_entitydblink + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_entitydblink + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entity_db_link.database_code.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getDatabaseCode(String val_name, String accession_code) {\n\n");

			buffw.write("\t\tif (accession_code != null)\n");
			buffw.write("\t\t\tval_name = " + file_prefix + "_" + util_dbname + ".guessDbName(val_name, accession_code);\n\n");

			buffw.write("\t\tif (val_name != null && val_name.equals(\"NCBI\") && accession_code != null && accession_code.matches(\"^[0-9]+$\"))\n");
			buffw.write("\t\t\treturn null;\n\n");

			buffw.write("\t\treturn (String) map.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_entry_experimental_method = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entity_db_link.entry_experimental_method.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getEntryExperimentalMethod(String val_name) {\n");
			buffw.write("\t\treturn (String) map_entry_experimental_method.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_author_supplied = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entity_db_link.author_supplied.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getAuthorSupplied(String val_name) {\n");
			buffw.write("\t\treturn (String) map_author_supplied.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String checkAccessionCode(String accession_code, String entry_id) {\n\n");

			buffw.write("\t\tfinal String[][] accodetbl = {\n\n");

			FileReader filer = new FileReader(xsd_dir_name + "entity_db_link.accession_code");
			BufferedReader bufferr = new BufferedReader(filer);

			String line = null;

			while ((line = bufferr.readLine()) != null) {

				if (!line.matches(".*,.*,.*"))
					continue;

				String[] elem = line.split(",");

				buffw.write("\t\t\t\t{\"" + elem[0] + "\", \"" + elem[1] + "\", \"" + elem[2] + "\"},\n");

			}

			bufferr.close();
			filer.close();

			buffw.write("\n\t\t};\n\n");

			buffw.write("\t\tfor (int i = 0; i < accodetbl.length; i++) {\n\n");

			buffw.write("\t\t\tif (accodetbl[i][0].equals(accession_code) && accodetbl[i][1].equals(entry_id))\n");
			buffw.write("\t\t\t\treturn accodetbl[i][2];\n\n");

			buffw.write("\t\t\tif (accodetbl[i][0].isEmpty() && (" + empty_check("accession_code") + ") && accodetbl[i][1].equals(entry_id))\n");
			buffw.write("\t\t\t\treturn accodetbl[i][2];\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif (accession_code == null)\n");
			buffw.write("\t\t\treturn null;\n\n");

			buffw.write("\t\treturn accession_code.toUpperCase();\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_entityexperimentalsrc(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_entityexperimentalsrc + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.io.*;\n");
			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_entityexperimentalsrc + " {\n\n");

			buffw.write("\tprivate String[][] taxidtbl = null;\n\n");

			buffw.write("\t" + file_prefix + "_" + util_entityexperimentalsrc + "() {\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tFileReader filer = new FileReader(" + file_prefix + "_" + util_main + ".xsd_dir_name + \"entity_experimental_src.host_org_ncbi_taxonomy_id\");\n");
			buffw.write("\t\t\tBufferedReader bufferr = new BufferedReader(filer);\n");
			buffw.write("\t\t\tbufferr.mark(400000);\n\n");

			buffw.write("\t\t\tint i = 0;\n");
			buffw.write("\t\t\tString line = null;\n\n");

			buffw.write("\t\t\twhile ((line = bufferr.readLine()) != null) {\n\n");

			buffw.write("\t\t\t\tif (!line.matches(\".*,.*,.*,.*\"))\n");
			buffw.write("\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\ti++;\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tbufferr.reset();\n\n");

			buffw.write("\t\t\ttaxidtbl = new String[i][4];\n");
			buffw.write("\t\t\ti = 0;\n\n");

			buffw.write("\t\t\twhile ((line = bufferr.readLine()) != null) {\n\n");

			buffw.write("\t\t\t\tif (!line.matches(\".*,.*,.*,.*\"))\n");
			buffw.write("\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\tString[] elem = line.split(\",\");\n\n");

			buffw.write("\t\t\t\ttaxidtbl[i] = elem;\n\n");

			buffw.write("\t\t\t\ti++;\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tbufferr.close();\n");
			buffw.write("\t\t\tfiler.close();\n\n");

			buffw.write("\t\t} catch (FileNotFoundException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (IOException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t}\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entity_experimental_src.production_method.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic String getProductionMethod(String val_name) {\n");
			buffw.write("\t\treturn (String) map.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic String checkNCBITaxonomyID(String ncbi_taxonomy_id, String entry_id, String entity_id, String scientific_name, String production_method) {\n\n");

			buffw.write("\t\tif (!(" + empty_check("production_method") + ") && (production_method.contains(\"synthesis\") || production_method.contains(\"obtained\")))\n");
			buffw.write("\t\t\treturn \"na\";\n\n");

			buffw.write("\t\tif (!(" + empty_check("scientific_name") + ")) {\n\n");

			buffw.write("\t\t\tif (scientific_name.equals(\"Pichia pastoris\"))\n");
			buffw.write("\t\t\t\treturn \"4922\";\n\n");

			buffw.write("\t\t\telse if (scientific_name.equals(\"E. coli - cell free\"))\n");
			buffw.write("\t\t\t\treturn \"562\";\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\tfor (int i = 0; i < taxidtbl.length; i++) {\n\n");

			buffw.write("\t\t\tif (taxidtbl[i][0].equals(ncbi_taxonomy_id) && taxidtbl[i][1].equals(entry_id) && taxidtbl[i][2].equals(entity_id))\n");
			buffw.write("\t\t\t\treturn taxidtbl[i][3];\n\n");

			buffw.write("\t\t\tif (taxidtbl[i][0].isEmpty() && (" + empty_check("ncbi_taxonomy_id") + ") && taxidtbl[i][1].equals(entry_id) && taxidtbl[i][2].equals(entity_id))\n");
			buffw.write("\t\t\t\treturn taxidtbl[i][3];\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif ((" + empty_check("ncbi_taxonomy_id") + ") && (" + empty_check("scientific_name") + " || scientific_name.equalsIgnoreCase(\"not applicable\") || scientific_name.equalsIgnoreCase(\"chemical synthesis\")))\n");
			buffw.write("\t\t\treturn \"na\";\n\n");

			buffw.write("\t\treturn ncbi_taxonomy_id;\n");

			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_entitynaturalsrc(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_entitynaturalsrc + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.io.*;\n");
			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_entitynaturalsrc + " {\n\n");

			buffw.write("\tprivate String[][] taxidtbl = null;\n\n");

			buffw.write("\t" + file_prefix + "_" + util_entitynaturalsrc + "() {\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tFileReader filer = new FileReader(" + file_prefix + "_" + util_main + ".xsd_dir_name + \"entity_natural_src.ncbi_taxonomy_id\");\n");
			buffw.write("\t\t\tBufferedReader bufferr = new BufferedReader(filer);\n");
			buffw.write("\t\t\tbufferr.mark(400000);\n\n");

			buffw.write("\t\t\tint i = 0;\n");
			buffw.write("\t\t\tString line = null;\n\n");

			buffw.write("\t\t\twhile ((line = bufferr.readLine()) != null) {\n\n");

			buffw.write("\t\t\t\tif (!line.matches(\".*,.*,.*\"))\n");
			buffw.write("\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\ti++;\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tbufferr.reset();\n\n");

			buffw.write("\t\t\ttaxidtbl = new String[i][3];\n");
			buffw.write("\t\t\ti = 0;\n\n");

			buffw.write("\t\t\twhile ((line = bufferr.readLine()) != null) {\n\n");

			buffw.write("\t\t\t\tif (!line.matches(\".*,.*,.*\"))\n");
			buffw.write("\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\tString[] elem = line.split(\",\");\n\n");

			buffw.write("\t\t\t\ttaxidtbl[i] = elem;\n\n");

			buffw.write("\t\t\t\ti++;\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tbufferr.close();\n");
			buffw.write("\t\t\tfiler.close();\n\n");

			buffw.write("\t\t} catch (FileNotFoundException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (IOException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t}\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_type = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entity_natural_src.type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic String getType(String val_name) {\n");
			buffw.write("\t\treturn (String) map_type.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_organ = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entity_natural_src.organ.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic String getOrgan(String val_name) {\n");
			buffw.write("\t\treturn (String) map_organ.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_organelle = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entity_natural_src.organelle.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic String getOrganelle(String val_name) {\n");
			buffw.write("\t\treturn (String) map_organelle.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_secretion = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entity_natural_src.secretion.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic String getSecretion(String val_name) {\n");
			buffw.write("\t\treturn (String) map_secretion.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_common = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entity_natural_src.common.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic String getCommon(String val_name) {\n\n");

			buffw.write("\t\tval_name = map_common.get(val_name);\n\n");

			buffw.write("\t\tif (val_name != null && val_name.equalsIgnoreCase(\"null\"))\n");
			buffw.write("\t\t\treturn null;\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic String checkNCBITaxonomyID(String ncbi_taxonomy_id, String entry_id, String scientific_name) {\n\n");

			buffw.write("\t\tfor (int i = 0; i < taxidtbl.length; i++) {\n\n");

			buffw.write("\t\t\tif (taxidtbl[i][0].equals(ncbi_taxonomy_id) && taxidtbl[i][1].equals(entry_id))\n");
			buffw.write("\t\t\t\treturn taxidtbl[i][2];\n\n");

			buffw.write("\t\t\tif (taxidtbl[i][0].isEmpty() && (" + empty_check("ncbi_taxonomy_id") + ") && taxidtbl[i][1].equals(entry_id))\n");
			buffw.write("\t\t\t\treturn taxidtbl[i][2];\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif ((" + empty_check("ncbi_taxonomy_id") + ") && (" + empty_check("scientific_name") + " || scientific_name.equals(\"not applicable\")))\n");
			buffw.write("\t\t\treturn \"na\";\n\n");

			buffw.write("\t\treturn ncbi_taxonomy_id;\n");

			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_entitysystematicname(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_entitysystematicname + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_entitysystematicname + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entity_systematic_name.naming_system.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getNamingSystem(String val_name) {\n");
			buffw.write("\t\treturn (String) map.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_entry(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_entry + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.io.*;\n");
			buffw.write("import java.net.*;\n\n");

			buffw.write("import java.security.cert.*;\n");
			buffw.write("import java.security.*;\n");

			buffw.write("import javax.net.ssl.*;\n");
			buffw.write("import java.sql.*;\n");
			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n");
			buffw.write("import java.util.logging.*;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_entry + " {\n\n");

			buffw.write("\tprivate static String[][] bmrb_pdb_ets_match = null;\n");
			buffw.write("\tprivate static String[][] bmrb_pdb_coor_match = null;\n");
			buffw.write("\tprivate static String[][] bmrb_pdb_adit_match = null;\n");
			buffw.write("\tprivate static String[][] bmrb_pdb_pubmed_match = null;\n\n");

			buffw.write("\t" + file_prefix + "_" + util_entry + "() {\n\n");

			buffw.write("\t\tHttpURLConnection.setFollowRedirects(false);\n\n");

			buffw.write("\t\tString bmrb_pdb_coor_match_csv_file = \"https://bmrb.pdbj.org/ftp/pub/bmrb/nmr_pdb_integrated_data/coordinates_restraints_chemshifts/BMRB_PDB_match.csv\";\n");
			buffw.write("\t\tString bmrb_pdb_adit_match_csv_file = \"https://bmrb.pdbj.org/ftp/pub/bmrb/nmr_pdb_integrated_data/adit_nmr_matched_pdb_bmrb_entry_ids.csv\";\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tFileReader filer = new FileReader(" + file_prefix + "_" + util_main + ".xsd_dir_name + \"BMRB_PDB_match.csv\");\n");
			buffw.write("\t\t\tBufferedReader bufferr = new BufferedReader(filer);\n");
			buffw.write("\t\t\tbufferr.mark(400000);\n\n");

			buffw.write("\t\t\tint i = 0;\n");
			buffw.write("\t\t\tString line = null;\n\n");

			buffw.write("\t\t\twhile ((line = bufferr.readLine()) != null) {\n\n");

			buffw.write("\t\t\t\tif (!line.matches(\".*,.*,.*\"))\n");
			buffw.write("\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\ti++;\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tbufferr.reset();\n\n");

			buffw.write("\t\t\tbmrb_pdb_ets_match = new String[i][3];\n");
			buffw.write("\t\t\ti = 0;\n\n");

			buffw.write("\t\t\twhile ((line = bufferr.readLine()) != null) {\n\n");

			buffw.write("\t\t\t\tif (!line.matches(\".*,.*,.*\"))\n");
			buffw.write("\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\tString[] elem = line.split(\",\");\n\n");

			buffw.write("\t\t\t\tbmrb_pdb_ets_match[i++] = elem;\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tbufferr.close();\n");
			buffw.write("\t\t\tfiler.close();\n\n");

			buffw.write("\t\t\tfiler = new FileReader(" + file_prefix + "_" + util_main + ".xsd_dir_name + \"BMRB_PDB_PUBMED_match.csv\");\n");
			buffw.write("\t\t\tbufferr = new BufferedReader(filer);\n");
			buffw.write("\t\t\tbufferr.mark(400000);\n\n");

			buffw.write("\t\t\ti = 0;\n");
			buffw.write("\t\t\tline = null;\n\n");

			buffw.write("\t\t\twhile ((line = bufferr.readLine()) != null) {\n\n");

			buffw.write("\t\t\t\tif (!line.matches(\".*,.*,.*,.*\"))\n");
			buffw.write("\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\ti++;\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tbufferr.reset();\n\n");

			buffw.write("\t\t\tbmrb_pdb_pubmed_match = new String[i][4];\n");
			buffw.write("\t\t\ti = 0;\n\n");

			buffw.write("\t\t\twhile ((line = bufferr.readLine()) != null) {\n\n");

			buffw.write("\t\t\t\tif (!line.matches(\".*,.*,.*,.*\"))\n");
			buffw.write("\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\tString[] elem = line.split(\",\");\n\n");

			buffw.write("\t\t\t\tbmrb_pdb_pubmed_match[i++] = elem;\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tbufferr.close();\n");
			buffw.write("\t\t\tfiler.close();\n\n");

			buffw.write("\t\t\tTrustManager[] tm = new TrustManager[] { new X509TrustManager() {\n\n");

			buffw.write("\t\t\t\t\tpublic X509Certificate[] getAcceptedIssuers() {\n");
			buffw.write("\t\t\t\t\t\treturn null;\n");
			buffw.write("\t\t\t\t\t}\n");
			buffw.write("\t\t\t\t\t@Override\n");
			buffw.write("\t\t\t\t\tpublic void checkClientTrusted(X509Certificate[] certs, String authType) {\n");
			buffw.write("\t\t\t\t\t}\n");
			buffw.write("\t\t\t\t\t@Override\n");
			buffw.write("\t\t\t\t\tpublic void checkServerTrusted(X509Certificate[] certs, String authType) {\n");
			buffw.write("\t\t\t\t\t}\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t};\n\n");

			buffw.write("\t\t\tSSLContext sc = SSLContext.getInstance(\"SSL\");\n");
			buffw.write("\t\t\tsc.init(null, tm, new java.security.SecureRandom());\n\n");

			buffw.write("\t\t\tHttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {\n");
			buffw.write("\t\t\t\t@Override\n");
			buffw.write("\t\t\t\tpublic boolean verify(String hostname, SSLSession session) {\n");
			buffw.write("\t\t\t\t\treturn true;\n");
			buffw.write("\t\t\t\t}\n");
			buffw.write("\t\t\t});\n\n");

			buffw.write("\t\t\tURL url = new URL(bmrb_pdb_coor_match_csv_file);\n");
			buffw.write("\t\t\tHttpsURLConnection conn = (HttpsURLConnection) url.openConnection();\n");
			buffw.write("\t\t\tconn.setSSLSocketFactory(sc.getSocketFactory());\n\n");

			buffw.write("\t\t\tconn.setRequestMethod(\"GET\");\n\n");

			buffw.write("\t\t\tif (conn.getResponseCode() != HttpURLConnection.HTTP_OK)\n");
			buffw.write("\t\t\t\treturn;\n\n");

			buffw.write("\t\t\tbufferr = new BufferedReader(new InputStreamReader(conn.getInputStream()));\n");
			buffw.write("\t\t\tbufferr.mark(400000);\n\n");

			buffw.write("\t\t\tline = bufferr.readLine();\n\n");

			buffw.write("\t\t\tif (!line.startsWith(\"\\\"BMRB ID\\\",\\\"PDB ID\\\",\\\"score\\\"\"))\n");
			buffw.write("\t\t\t\treturn;\n\n");

			buffw.write("\t\t\ti = 0;\n\n");

			buffw.write("\t\t\twhile ((line = bufferr.readLine()) != null) {\n\n");

			buffw.write("\t\t\t\tif (!line.matches(\".*,.*,.*,.*\"))\n");
			buffw.write("\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\tString[] elem = line.split(\",\");\n\n");

			buffw.write("\t\t\t\tif (!elem[2].equals(\"1\"))\n");
			buffw.write("\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\ti++;\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tbufferr.close();\n\n");

			buffw.write("\t\t\turl = new URL(bmrb_pdb_coor_match_csv_file);\n");
			buffw.write("\t\t\tconn = (HttpsURLConnection) url.openConnection();\n");
			buffw.write("\t\t\tconn.setSSLSocketFactory(sc.getSocketFactory());\n\n");

			buffw.write("\t\t\tconn.setRequestMethod(\"GET\");\n\n");

			buffw.write("\t\t\tif (conn.getResponseCode() != HttpURLConnection.HTTP_OK)\n");
			buffw.write("\t\t\t\treturn;\n\n");

			buffw.write("\t\t\tbufferr = new BufferedReader(new InputStreamReader(conn.getInputStream()));\n");
			buffw.write("\t\t\tbufferr.mark(400000);\n\n");

			buffw.write("\t\t\tbmrb_pdb_coor_match = new String[i][2];\n");
			buffw.write("\t\t\ti = 0;\n\n");

			buffw.write("\t\t\tline = bufferr.readLine();\n\n");

			buffw.write("\t\t\twhile ((line = bufferr.readLine()) != null) {\n\n");

			buffw.write("\t\t\t\tif (!line.matches(\".*,.*,.*,.*\"))\n");
			buffw.write("\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\tString[] elem = line.split(\",\");\n\n");

			buffw.write("\t\t\t\tif (!elem[2].equals(\"1\"))\n");
			buffw.write("\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\tbmrb_pdb_coor_match[i][0] = elem[0];\n");
			buffw.write("\t\t\t\tbmrb_pdb_coor_match[i][1] = elem[1].replaceAll(\"\\\"\", \"\");\n\n");

			buffw.write("\t\t\t\ti++;\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tbufferr.close();\n\n");

			buffw.write("\t\t\turl = new URL(bmrb_pdb_adit_match_csv_file);\n");
			buffw.write("\t\t\tconn = (HttpsURLConnection) url.openConnection();\n");
			buffw.write("\t\t\tconn.setSSLSocketFactory(sc.getSocketFactory());\n\n");

			buffw.write("\t\t\tconn.setRequestMethod(\"GET\");\n\n");

			buffw.write("\t\t\tif (conn.getResponseCode() != HttpURLConnection.HTTP_OK)\n");
			buffw.write("\t\t\t\treturn;\n\n");

			buffw.write("\t\t\tbufferr = new BufferedReader(new InputStreamReader(conn.getInputStream()));\n");
			buffw.write("\t\t\tbufferr.mark(400000);\n\n");

			buffw.write("\t\t\ti = 0;\n\n");

			buffw.write("\t\t\twhile ((line = bufferr.readLine()) != null) {\n\n");

			buffw.write("\t\t\t\tif (!line.matches(\".*,.*\"))\n");
			buffw.write("\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\ti++;\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tbufferr.close();\n\n");

			buffw.write("\t\t\turl = new URL(bmrb_pdb_adit_match_csv_file);\n");
			buffw.write("\t\t\tconn = (HttpsURLConnection) url.openConnection();\n");
			buffw.write("\t\t\tconn.setSSLSocketFactory(sc.getSocketFactory());\n\n");

			buffw.write("\t\t\tconn.setRequestMethod(\"GET\");\n\n");

			buffw.write("\t\t\tif (conn.getResponseCode() != HttpURLConnection.HTTP_OK)\n");
			buffw.write("\t\t\t\treturn;\n\n");

			buffw.write("\t\t\tbufferr = new BufferedReader(new InputStreamReader(conn.getInputStream()));\n");
			buffw.write("\t\t\tbufferr.mark(400000);\n\n");

			buffw.write("\t\t\tbmrb_pdb_adit_match = new String[i][2];\n");
			buffw.write("\t\t\ti = 0;\n\n");

			buffw.write("\t\t\twhile ((line = bufferr.readLine()) != null) {\n\n");

			buffw.write("\t\t\t\tif (!line.matches(\".*,.*\"))\n");
			buffw.write("\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\tString[] elem = line.split(\",\");\n\n");

			buffw.write("\t\t\t\tbmrb_pdb_adit_match[i++] = elem;\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tbufferr.close();\n\n");

			buffw.write("\t\t} catch (FileNotFoundException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (MalformedURLException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (IOException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (NoSuchAlgorithmException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (KeyManagementException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_version_type = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entry.version_type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getVersionType(String val_name) {\n");
			buffw.write("\t\treturn (String) map_version_type.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_experimental_method = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entry.experimental_method.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getExperimentalMethod(String val_name) {\n");
			buffw.write("\t\treturn (String) map_experimental_method.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_experimental_method_subtyoe = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entry.experimental_method_subtype.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getExperimentalMethodSubtype(String val_name, String entry_id) {\n\n");

			buffw.write("\t\tif (val_name != null && val_name.equalsIgnoreCase(\"NMR\")) {\n\n");

			buffw.write("\t\t\tif (entry_id.equals(\"16206\"))\n");
			buffw.write("\t\t\t\treturn \"SOLUTION\";\n\n");

			buffw.write("\t\t\tif (entry_id.equals(\"16254\"))\n");
			buffw.write("\t\t\t\treturn \"SOLID-STATE\";\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn (String) map_experimental_method_subtyoe.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_nmr_star_version = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entry.nmr_star_version.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getNMRStarVersion(String val_name) {\n");
			buffw.write("\t\treturn (String) map_nmr_star_version.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_original_nmr_star_version = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entry.original_nmr_star_version.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getOriginalNMRStarVersion(String val_name) {\n");
			buffw.write("\t\treturn (String) map_original_nmr_star_version.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic String getAssignedPDBID(String pdb_id, Connection conn_bmrb, String entry_id) {\n\n");

			buffw.write("\t\tif (!(" + empty_check("pdb_id") + "))\n");
			buffw.write("\t\t\treturn getEffectivePDBID(pdb_id);\n\n");

			buffw.write("\t\tif (bmrb_pdb_ets_match != null) {\n");
			buffw.write("\t\t\tfor (int i = 0; i < bmrb_pdb_ets_match.length; i++) {\n");
			buffw.write("\t\t\t\tif (entry_id.equals(bmrb_pdb_ets_match[i][0]))\n");
			buffw.write("\t\t\t\t\treturn getEffectivePDBID(bmrb_pdb_ets_match[i][1]);\n");
			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif (bmrb_pdb_adit_match != null) {\n");
			buffw.write("\t\t\tfor (int i = 0; i < bmrb_pdb_adit_match.length; i++) {\n");
			buffw.write("\t\t\t\tif (entry_id.equals(bmrb_pdb_adit_match[i][0]))\n");
			buffw.write("\t\t\t\t\treturn getEffectivePDBID(bmrb_pdb_adit_match[i][1]);\n");
			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif (bmrb_pdb_coor_match != null) {\n");
			buffw.write("\t\t\tfor (int i = 0; i < bmrb_pdb_coor_match.length; i++) {\n");
			buffw.write("\t\t\t\tif (entry_id.equals(bmrb_pdb_coor_match[i][0]))\n");
			buffw.write("\t\t\t\t\treturn getEffectivePDBID(bmrb_pdb_coor_match[i][1]);\n");
			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif (bmrb_pdb_pubmed_match != null) {\n");
			buffw.write("\t\t\tfor (int i = 0; i < bmrb_pdb_pubmed_match.length; i++) {\n");
			buffw.write("\t\t\t\tif (entry_id.equals(bmrb_pdb_pubmed_match[i][0]))\n");
			buffw.write("\t\t\t\t\treturn getEffectivePDBID(bmrb_pdb_pubmed_match[i][2]);\n");
			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tString query = new String(\"select \\\"Family_name\\\" from \\\"Citation_author\\\" where \\\"Entry_ID\\\"='\" + entry_id + \"'\");\n\n");

			buffw.write("\t\t\tStatement state = conn_bmrb.createStatement();\n");
			buffw.write("\t\t\tResultSet rset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\tString first_author_family_name = null;\n");
			buffw.write("\t\t\tString last_author_family_name = null;\n\n");

			buffw.write("\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\tString family_name = rset.getString(\"Family_name\");\n\n");

			buffw.write("\t\t\t\tif (first_author_family_name == null)\n");
			buffw.write("\t\t\t\t\tfirst_author_family_name = family_name;\n\n");

			buffw.write("\t\t\t\tlast_author_family_name = family_name;\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\tString sparql = \"PREFIX PDBo: <" + pdbo_uri + "> SELECT ?pdb_id COUNT(?pdb_id) AS ?count FROM <" + pdb_graph_uri + "> WHERE { ?pdb_s PDBo:database_2.database_id \\\"BMRB\\\" ; PDBo:database_2.database_code \\\"\" + entry_id + \"\\\" . ?pdb_s PDBo:of_datablock ?pdb_db . BIND (STRAFTER(STR(?pdb_db), \\\"" + rdf_pdb_api + "\\\") AS ?pdb_id) BIND (IRI(CONCAT(?pdb_db, \\\"/citation_authorCategory\\\")) AS ?pdb_citation_author_category) ?pdb_citation_author_category PDBo:has_citation_author ?pdb_citation_author . ?pdb_citation_author PDBo:citation_author.name ?citation_author_name . FILTER (STRSTARTS(?citation_author_name, \\\"\" + first_author_family_name + \"\\\") || STRSTARTS(?citation_author_name, \\\"\" + last_author_family_name + \"\\\")) } GROUP BY ?pdb_id\";\n");

			buffw.write("\t\t\tFile query_file = new File(\"queries\", \"get_pdb_id_from_bmrb_id_\" + entry_id + \".sparql\");\n");
			buffw.write("\t\t\tFileWriter queryw = new FileWriter(query_file);\n");
			buffw.write("\t\t\tqueryw.write(sparql);\n");
			buffw.write("\t\t\tqueryw.close();\n\n");

			buffw.write("\t\t\tProcessBuilder builder = new ProcessBuilder(\"curl\", \"-F\", \"query=@\" + query_file.getAbsolutePath(), \"-F\", \"format=text/csv\", \"https://bmrbpub.pdbj.org/search/rdf\");\n");
			buffw.write("\t\t\tProcess proc = builder.start();\n\n");

			buffw.write("\t\t\tif (proc.waitFor() != 0)\n");
			buffw.write("\t\t\t\tthrow new RuntimeException(\"error occured while processing \" + query_file.getAbsolutePath() + \".\");\n\n");

			buffw.write("\t\t\tInputStream is = proc.getInputStream();\n");
			buffw.write("\t\t\tBufferedReader bufferr = new BufferedReader(new InputStreamReader(is));\n");

			buffw.write("\t\t\tString line = bufferr.readLine();\n\n");

			buffw.write("\t\t\twhile ((line = bufferr.readLine()) != null) {\n\n");

			buffw.write("\t\t\t\tString[] array = line.replaceAll(\"\\\"\", \"\").split(\",\");\n\n");

			buffw.write("\t\t\t\tif (array.length < 2)\n");
			buffw.write("\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\tint count = Integer.valueOf(array[1]);\n\n");

			buffw.write("\t\t\t\tif (count >= 2 || (count == 1 && first_author_family_name.equals(last_author_family_name))) {\n");
			buffw.write("\t\t\t\t\tpdb_id = array[0];\n");
			buffw.write("\t\t\t\t\tbreak;\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tquery_file.delete();\n\n");

			buffw.write("\t\t} catch (Exception e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn getEffectivePDBID(pdb_id);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic String getAssignedPDBDepositionCode(String rcsb_id, Connection conn_bmrb, String entry_id) {\n\n");

			buffw.write("\t\tif (!(" + empty_check("rcsb_id") + "))\n");
			buffw.write("\t\t\treturn rcsb_id;\n\n");

			buffw.write("\t\tString pdb_id = getAssignedPDBID(null, conn_bmrb, entry_id);\n\n");

			buffw.write("\t\tif (!(" + empty_check("pdb_id") + "))\n");
			buffw.write("\t\t\treturn getAssignedPDBDepositionCodeByPDBID(rcsb_id, pdb_id);\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tString sparql = \"PREFIX PDBo: <" + pdbo_uri + "> SELECT ?rcsb_id FROM <" + pdb_graph_uri + "> WHERE { ?pdb_s PDBo:database_2.database_id \\\"BMRB\\\" ; PDBo:database_2.database_code \\\"\" + entry_id + \"\\\" . ?pdb_s PDBo:of_datablock ?pdb_db . BIND (IRI(CONCAT(?pdb_db, \\\"/database_2Category\\\")) AS ?pdb_db2c) ?pdb_db2c PDBo:has_database_2 ?rcsb_s . FILTER (CONTAINS(STR(?rcsb_s), \\\"RCSB\\\")) ?rcsb_s PDBo:database_2.database_code ?rcsb_id . }\";\n");

			buffw.write("\t\t\tFile query_file = new File(\"queries\", \"get_rcsb_id_from_bmrb_id_\" + entry_id + \".sparql\");\n");
			buffw.write("\t\t\tFileWriter queryw = new FileWriter(query_file);\n");
			buffw.write("\t\t\tqueryw.write(sparql);\n");
			buffw.write("\t\t\tqueryw.close();\n\n");

			buffw.write("\t\t\tProcessBuilder builder = new ProcessBuilder(\"curl\", \"-F\", \"query=@\" + query_file.getAbsolutePath(), \"-F\", \"format=text/csv\", \"https://bmrbpub.pdbj.org/search/rdf\");\n");
			buffw.write("\t\t\tProcess proc = builder.start();\n\n");

			buffw.write("\t\t\tif (proc.waitFor() != 0)\n");
			buffw.write("\t\t\t\tthrow new RuntimeException(\"error occured while processing \" + query_file.getAbsolutePath() + \".\");\n\n");

			buffw.write("\t\t\tInputStream is = proc.getInputStream();\n");
			buffw.write("\t\t\tBufferedReader bufferr = new BufferedReader(new InputStreamReader(is));\n");

			buffw.write("\t\t\tString line = bufferr.readLine();\n\n");

			buffw.write("\t\t\twhile ((line = bufferr.readLine()) != null) {\n");
			buffw.write("\t\t\t\trcsb_id = line.replaceAll(\"\\\"\", \"\");\n");
			buffw.write("\t\t\t\tbreak;\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tquery_file.delete();\n\n");

			buffw.write("\t\t} catch (Exception e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn rcsb_id;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic String getAssignedPDBDepositionCodeByPDBID(String rcsb_id, String pdb_id) {\n\n");

			buffw.write("\t\tif (!(" + empty_check("rcsb_id") + "))\n");
			buffw.write("\t\t\treturn rcsb_id;\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tString sparql = \"PREFIX PDBo: <" + pdbo_uri + "> SELECT ?rcsb_id FROM <" + pdb_graph_uri + "> WHERE {  BIND (IRI(CONCAT(\\\"" + rdf_pdb_api + "\\\", \\\"\" + pdb_id + \"\\\", \\\"/database_2Category\\\")) AS ?pdb_db2c) ?pdb_db2c PDBo:has_database_2 ?rcsb_s . FILTER (CONTAINS(STR(?rcsb_s), \\\"RCSB\\\")) ?rcsb_s PDBo:database_2.database_code ?rcsb_id . }\";\n");

			buffw.write("\t\t\tFile query_file = new File(\"queries\", \"get_rcsb_id_from_pdb_id_\" + pdb_id + \".sparql\");\n");
			buffw.write("\t\t\tFileWriter queryw = new FileWriter(query_file);\n");
			buffw.write("\t\t\tqueryw.write(sparql);\n");
			buffw.write("\t\t\tqueryw.close();\n\n");

			buffw.write("\t\t\tProcessBuilder builder = new ProcessBuilder(\"curl\", \"-F\", \"query=@\" + query_file.getAbsolutePath(), \"-F\", \"format=text/csv\", \"https://bmrbpub.pdbj.org/search/rdf\");\n");
			buffw.write("\t\t\tProcess proc = builder.start();\n\n");

			buffw.write("\t\t\tif (proc.waitFor() != 0)\n");
			buffw.write("\t\t\t\tthrow new RuntimeException(\"error occured while processing \" + query_file.getAbsolutePath() + \".\");\n\n");

			buffw.write("\t\t\tInputStream is = proc.getInputStream();\n");
			buffw.write("\t\t\tBufferedReader bufferr = new BufferedReader(new InputStreamReader(is));\n");

			buffw.write("\t\t\tString line = bufferr.readLine();\n\n");

			buffw.write("\t\t\twhile ((line = bufferr.readLine()) != null) {\n");
			buffw.write("\t\t\t\trcsb_id = line.replaceAll(\"\\\"\", \"\");\n");
			buffw.write("\t\t\t\tbreak;\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tquery_file.delete();\n\n");

			buffw.write("\t\t} catch (Exception e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn rcsb_id;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic String getPrimaryPubMedIDViaPDB(String pubmed_id, Connection conn_bmrb, String entry_id) {\n\n");

			buffw.write("\t\tif (!(" + empty_check("pubmed_id") + "))\n");
			buffw.write("\t\t\treturn pubmed_id;\n\n");

			buffw.write("\t\tString pdb_id = getAssignedPDBID(null, conn_bmrb, entry_id);\n\n");

			buffw.write("\t\tif (" + empty_check("pdb_id") + ")\n");
			buffw.write("\t\t\treturn pubmed_id;\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tString sparql = \"PREFIX PDBo: <" + pdbo_uri + "> SELECT ?pubmed_id FROM <" + pdb_graph_uri + "> WHERE {  BIND (IRI(CONCAT(\\\"" + rdf_pdb_api + "\\\", \\\"\" + pdb_id + \"\\\", \\\"/citation/primary\\\")) AS ?pdb_cp) ?pdb_cp PDBo:citation.pdbx_database_id_PubMed ?pubmed_id }\";\n");

			buffw.write("\t\t\tFile query_file = new File(\"queries\", \"get_primary_pubmed_id_from_pdb_id_\" + pdb_id + \".sparql\");\n");
			buffw.write("\t\t\tFileWriter queryw = new FileWriter(query_file);\n");
			buffw.write("\t\t\tqueryw.write(sparql);\n");
			buffw.write("\t\t\tqueryw.close();\n\n");

			buffw.write("\t\t\tProcessBuilder builder = new ProcessBuilder(\"curl\", \"-F\", \"query=@\" + query_file.getAbsolutePath(), \"-F\", \"format=text/csv\", \"https://bmrbpub.pdbj.org/search/rdf\");\n");
			buffw.write("\t\t\tProcess proc = builder.start();\n\n");

			buffw.write("\t\t\tif (proc.waitFor() != 0)\n");
			buffw.write("\t\t\t\tthrow new RuntimeException(\"error occured while processing \" + query_file.getAbsolutePath() + \".\");\n\n");

			buffw.write("\t\t\tInputStream is = proc.getInputStream();\n");
			buffw.write("\t\t\tBufferedReader bufferr = new BufferedReader(new InputStreamReader(is));\n");

			buffw.write("\t\t\tString line = bufferr.readLine();\n\n");

			buffw.write("\t\t\twhile ((line = bufferr.readLine()) != null) {\n");
			buffw.write("\t\t\t\tpubmed_id = line.replaceAll(\"\\\"\", \"\");\n");
			buffw.write("\t\t\t\tbreak;\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tquery_file.delete();\n\n");

			buffw.write("\t\t} catch (Exception e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn pubmed_id;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_obsolete_pdb_id = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "obsolete_pdb_id.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic String getEffectivePDBID(String val_name) {\n\n");

			buffw.write("\t\tif (" + empty_check("val_name") + ")\n");
			buffw.write("\t\t\treturn val_name;\n\n");

			buffw.write("\t\tString pdb_id = map_obsolete_pdb_id.get(val_name);\n\n");

			buffw.write("\t\tif (!(" + empty_check("pdb_id") + "))\n");
			buffw.write("\t\t\treturn pdb_id;\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static java.sql.Date getOriginalReleaseDate(java.sql.Date date, Connection conn_bmrb, String entry_id) {\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tString query = new String(\"select \\\"Date\\\" as \\\"Original_release_date\\\" from \\\"Release\\\" where \\\"Entry_ID\\\"='\" + entry_id + \"' and \\\"Type\\\"='%original%'\");\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tstate = conn_bmrb.createStatement();\n");
			buffw.write("\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\tdate = rset.getDate(\"Original_release_date\");\n\n");

			buffw.write("\t\t\t\tbreak;\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_entry + ".class.getName());\n");
			buffw.write("\t\t\tlgr.log(Level.SEVERE, ex.getMessage(), ex);\n");
			buffw.write("\t\t\tSystem.exit(1);\n\n");

			buffw.write("\t\t} finally {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_entry + ".class.getName());\n");
			buffw.write("\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif (entry_id.equals(\"18260\"))\n");
			buffw.write("\t\t\tdate = java.sql.Date.valueOf(\"2012-05-12\");\n\n");

			buffw.write("\t\treturn date;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static java.sql.Date getFirstReleaseDate(java.sql.Date date, Connection conn_bmrb, String entry_id) {\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tString query = new String(\"select min(\\\"Date\\\") as \\\"First_release_date\\\" from \\\"Release\\\" where \\\"Entry_ID\\\"='\" + entry_id + \"'\");\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tstate = conn_bmrb.createStatement();\n");
			buffw.write("\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\tdate = rset.getDate(\"First_release_date\");\n\n");

			buffw.write("\t\t\t\tbreak;\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_orderparam + ".class.getName());\n");
			buffw.write("\t\t\tlgr.log(Level.SEVERE, ex.getMessage(), ex);\n");
			buffw.write("\t\t\tSystem.exit(1);\n\n");

			buffw.write("\t\t} finally {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_entry + ".class.getName());\n");
			buffw.write("\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif (entry_id.equals(\"18260\"))\n");
			buffw.write("\t\t\tdate = java.sql.Date.valueOf(\"2012-05-12\");\n\n");

			buffw.write("\t\treturn date;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static java.sql.Date getLastReleaseDate(java.sql.Date date, Connection conn_bmrb, String entry_id) {\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tString query = new String(\"select max(\\\"Date\\\") as \\\"Last_release_date\\\" from \\\"Release\\\" where \\\"Entry_ID\\\"='\" + entry_id + \"'\");\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tstate = conn_bmrb.createStatement();\n");
			buffw.write("\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\tdate = rset.getDate(\"Last_release_date\");\n\n");

			buffw.write("\t\t\t\tbreak;\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_entry + ".class.getName());\n");
			buffw.write("\t\t\tlgr.log(Level.SEVERE, ex.getMessage(), ex);\n");
			buffw.write("\t\t\tSystem.exit(1);\n\n");

			buffw.write("\t\t} finally {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_entry + ".class.getName());\n");
			buffw.write("\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif (entry_id.equals(\"18260\"))\n");
			buffw.write("\t\t\tdate = java.sql.Date.valueOf(\"2013-02-12\");\n\n");

			buffw.write("\t\treturn date;\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_entryauthor(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_entryauthor + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_entryauthor + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entry_author.family_title.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getFamilyTitle(String val_name) {\n\n");

			buffw.write("\t\tval_name = map.get(val_name);\n\n");

			buffw.write("\t\tif (val_name != null && val_name.equalsIgnoreCase(\"null\"))\n");
			buffw.write("\t\t\treturn null;\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_entryexperimentalmethods(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_entryexperimentalmethods + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_entryexperimentalmethods + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map_method = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entry_experimental_methods.method.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getMethod(String val_name) {\n\n");

			buffw.write("\t\tval_name = map_method.get(val_name);\n\n");

			buffw.write("\t\tif (val_name != null && val_name.equalsIgnoreCase(\"null\"))\n");
			buffw.write("\t\t\treturn null;\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_subtype = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "entry_experimental_methods.subtype.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getSubtype(String val_name) {\n\n");

			buffw.write("\t\tval_name = map_subtype.get(val_name);\n\n");

			buffw.write("\t\tif (val_name != null && val_name.equalsIgnoreCase(\"null\"))\n");
			buffw.write("\t\t\treturn null;\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_heteronuclt1list(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_heteronuclt1list + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_heteronuclt1list + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map_t1_value_units = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "heteronucl_t1_list.t1_value_units.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getT1ValUnits(String val_name) {\n");
			buffw.write("\t\treturn (String) map_t1_value_units.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_t1_coherence_type = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "heteronucl_t1_list.t1_coherence_type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getT1CoherenceType(String val_name, String entry_id) {\n");
			buffw.write("\t\treturn (String) map_t1_coherence_type.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_heteronuclt2list(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_heteronuclt2list + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_heteronuclt2list + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map_t2_value_units = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "heteronucl_t2_list.t2_value_units.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getT2ValUnits(String val_name) {\n");
			buffw.write("\t\treturn (String) map_t2_value_units.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_t2_coherence_type = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "heteronucl_t2_list.t2_coherence_type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getT2CoherenceType(String val_name, String entry_id) {\n");
			buffw.write("\t\treturn (String) map_t2_coherence_type.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_rex_units = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "heteronucl_t2_list.rex_units.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getRexUnits(String val_name) {\n");
			buffw.write("\t\treturn (String) map_rex_units.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_temp_calibration_method = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "heteronucl_t2_list.temp_calibration_method.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getTempCalibrationMethod(String val_name) {\n");
			buffw.write("\t\treturn (String) map_temp_calibration_method.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_temp_control_method = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "heteronucl_t2_list.temp_control_method.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getTempControlMethod(String val_name) {\n");
			buffw.write("\t\treturn (String) map_temp_control_method.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_heteronuclt1rholist(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_heteronuclt1rholist + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_heteronuclt1rholist + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map_rex_units = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "heteronucl_t1rho_list.rex_units.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getRexUnits(String val_name) {\n");
			buffw.write("\t\treturn (String) map_rex_units.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_t1rho_coherence_type = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "heteronucl_t1rho_list.t1rho_coherence_type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getT1RhoCoherenceType(String val_name) {\n");
			buffw.write("\t\treturn (String) map_t1rho_coherence_type.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_heteronuclnoelist(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_heteronuclnoelist + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_heteronuclnoelist + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "heteronucl_noe_list.heteronuclear_noe_val_type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getHeteronuclearNOEValType(String val_name) {\n");
			buffw.write("\t\treturn (String) map.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_homonuclnoelist(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_homonuclnoelist + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_homonuclnoelist + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "homonucl_noe_list.homonuclear_noe_val_type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getHomonuclearNOEValType(String val_name) {\n");
			buffw.write("\t\treturn (String) map.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_hexchratelist(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_hexchratelist + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_hexchratelist + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "h_exch_rate_list.val_units.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getValUnits(String val_name) {\n");
			buffw.write("\t\treturn (String) map.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_gendistconstraintlist(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_gendistconstraintlist + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_gendistconstraintlist + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "gen_dist_constraint_list.constraint_type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getConstraintType(String val_name) {\n");
			buffw.write("\t\treturn (String) map.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_naturalsourcedb(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_naturalsourcedb + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("public class " + file_prefix + "_" + util_naturalsourcedb + " {\n\n");

			buffw.write("\tpublic static String getDatabaseCode(String val_name, String accession_code) {\n\n");

			buffw.write("\t\tif (accession_code != null)\n");
			buffw.write("\t\t\tval_name = " + file_prefix + "_" + util_dbname + ".guessDbName(val_name, accession_code);\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_nmrexperimentfile(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_nmrexperimentfile + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_nmrexperimentfile + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "nmr_experiment_file.type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getType(String val_name) {\n");
			buffw.write("\t\treturn (String) map.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_nmrprobe(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_nmrprobe + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_nmrprobe + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "nmr_probe.type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getType(String val_name) {\n");
			buffw.write("\t\treturn (String) map.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_nmrspectrometer(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_nmrspectrometer + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_nmrspectrometer + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map_manufacturer = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "nmr_spectrometer.manufacturer.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getManufacturer(String val_name) {\n");
			buffw.write("\t\treturn (String) map_manufacturer.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_field_strength = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "nmr_spectrometer.field_strength.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getFieldStrength(String val_name) {\n");
			buffw.write("\t\treturn (String) map_field_strength.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_nmrspectrometerprobe(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_nmrspectrometerprobe + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_nmrspectrometerprobe + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "nmr_spectrometer_probe.manufacturer.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getManufacturer(String val_name) {\n");
			buffw.write("\t\treturn (String) map.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_nmrspectrometerview(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_nmrspectrometerview + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_nmrspectrometerview + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map_manufacturer = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "nmr_spectrometer_view.manufacturer.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getManufacturer(String val_name) {\n");
			buffw.write("\t\treturn (String) map_manufacturer.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_field_strength = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "nmr_spectrometer_view.field_strength.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getFieldStrength(String val_name) {\n");
			buffw.write("\t\treturn (String) map_field_strength.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_nmrspecexpt(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_nmrspecexpt + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_nmrspecexpt + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map_type = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "nmr_spec_expt.type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getType(String val_name) {\n");
			buffw.write("\t\treturn (String) map_type.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_nmr_tube_type = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "nmr_spec_expt.nmr_tube_type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getNMRTubeType(String val_name) {\n");
			buffw.write("\t\treturn (String) map_nmr_tube_type.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_orderparam(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_orderparam + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.sql.*;\n");
			buffw.write("import java.util.logging.*;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_orderparam + " {\n\n");

			buffw.write("\tpublic static String getModelFit(String val_name) {\n\n");

			buffw.write("\t\tif (val_name != null) {\n\n");

			buffw.write("\t\t\tval_name = val_name.replaceAll(\",\", \", \").replaceAll(\",\\\\s+\", \", \").trim().replaceFirst(\",$\", \"\");\n\n");

			buffw.write("\t\t\tif (val_name.contains(\".\") && val_name.matches(\"^[-+]?([0-9]+(\\\\.[0-9]*)?|\\\\.[0-9]+)?$\"))\n");
			buffw.write("\t\t\t\tval_name = null;\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\treturn val_name;\n");

			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getTauEVal(String val_name, Connection conn_bmrb, String entry_id) {\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tString query = new String(\"select \\\"Tau_e_val_units\\\" from \\\"Order_parameter_list\\\" where \\\"Entry_ID\\\"='\" + entry_id + \"'\");\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tstate = conn_bmrb.createStatement();\n");
			buffw.write("\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\tString tau_e_val_units = rset.getString(\"Tau_e_val_units\");\n\n");

			buffw.write("\t\t\t\tif (tau_e_val_units != null && tau_e_val_units.equalsIgnoreCase(\"E-10 s\")) {\n");
			buffw.write("\t\t\t\t\tval_name = String.valueOf(Float.valueOf(val_name) / 10.0);\n");
			buffw.write("\t\t\t\t\tbreak;\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_orderparam + ".class.getName());\n");
			buffw.write("\t\t\tlgr.log(Level.SEVERE, ex.getMessage(), ex);\n");
			buffw.write("\t\t\tSystem.exit(1);\n\n");

			buffw.write("\t\t} finally {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_orderparam + ".class.getName());\n");
			buffw.write("\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_orderparameterlist(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_orderparameterlist + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_orderparameterlist + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map_tau_e_val_units = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "order_parameter_list.tau_e_val_units.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getTauEValUnits(String val_name) {\n");
			buffw.write("\t\treturn (String) map_tau_e_val_units.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_rex_val_units = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "order_parameter_list.rex_val_units.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getRexValUnits(String val_name) {\n");
			buffw.write("\t\treturn (String) map_rex_val_units.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_pdbxpolyseqscheme(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_pdbxpolyseqscheme + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.sql.*;\n");
			buffw.write("import java.util.logging.*;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_pdbxpolyseqscheme + " {\n\n");

			buffw.write("\tpublic static String getEntityAssemblyID(String val_name, Connection conn_bmrb, String entry_id, String entity_id) {\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tString query = new String(\"select \\\"ID\\\" from \\\"Entity_assembly\\\" where \\\"Entry_ID\\\"='\" + entry_id + \"' and \\\"Entity_ID\\\"='\" + entity_id + \"'\");\n\n");

			buffw.write("\t\tint ids = 0;\n");
			buffw.write("\t\tString id = null;\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tstate = conn_bmrb.createStatement();\n");
			buffw.write("\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\tid = rset.getString(\"ID\");\n\n");

			buffw.write("\t\t\t\tif (!(" + empty_check("id") + ")) {\n");
			buffw.write("\t\t\t\t\tids++;\n");
			buffw.write("\t\t\t\t\tcontinue;\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tif (ids == 1)\n");
			buffw.write("\t\t\t\tval_name = id;\n\n");

			buffw.write("\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_pdbxpolyseqscheme + ".class.getName());\n");
			buffw.write("\t\t\tlgr.log(Level.SEVERE, ex.getMessage(), ex);\n");
			buffw.write("\t\t\tSystem.exit(1);\n\n");

			buffw.write("\t\t} finally {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_pdbxpolyseqscheme + ".class.getName());\n");
			buffw.write("\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getCompIndexID(String val_name, Connection conn_bmrb, String entry_id, String entity_id, String mon_id, String pdb_seq_num) {\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tString query = new String(\"select \\\"ID\\\",\\\"Comp_ID\\\" from \\\"Entity_comp_index\\\" where \\\"Entry_ID\\\"='\" + entry_id + \"' and \\\"Entity_ID\\\"='\" + entity_id + \"'\");\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tstate = conn_bmrb.createStatement();\n");
			buffw.write("\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\tString id = rset.getString(\"ID\");\n");
			buffw.write("\t\t\t\tString comp_id = rset.getString(\"Comp_ID\");\n\n");

			buffw.write("\t\t\t\tif (!(" + empty_check("id") + ") && id.equals(pdb_seq_num) && !(" + empty_check("comp_id") + ") && comp_id.equals(mon_id)) {\n");
			buffw.write("\t\t\t\t\tval_name = id;\n");
			buffw.write("\t\t\t\t\tbreak;\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_pdbxpolyseqscheme + ".class.getName());\n");
			buffw.write("\t\t\tlgr.log(Level.SEVERE, ex.getMessage(), ex);\n");
			buffw.write("\t\t\tSystem.exit(1);\n\n");

			buffw.write("\t\t} finally {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_pdbxpolyseqscheme + ".class.getName());\n");
			buffw.write("\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_phtitrationlist(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_phtitrationlist + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_phtitrationlist + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map_expt_observed_param = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "ph_titration_list.expt_observed_param.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getExptObservedParam(String val_name) {\n");
			buffw.write("\t\treturn (String) map_expt_observed_param.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_rdcexperiment(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_rdcexperiment + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.sql.*;\n");
			buffw.write("import java.util.logging.*;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_rdcexperiment + " {\n\n");

			buffw.write("\tpublic static String getExperimentID(String val_name, Connection conn_bmrb, String entry_id, String sample_id, String rdc_list_id) {\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tString query = new String(\"select \\\"ID\\\",\\\"Name\\\",\\\"Sample_ID\\\" from \\\"Experiment\\\" where \\\"Entry_ID\\\"='\" + entry_id + \"'\");\n\n");

			buffw.write("\t\tint experiments = 0;\n");
			buffw.write("\t\tString id = null;\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tstate = conn_bmrb.createStatement();\n");
			buffw.write("\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\tString experiment_name = rset.getString(\"Name\");\n\n");

			buffw.write("\t\t\t\tif (" + empty_check("experiment_name") + ") {\n\n");

			buffw.write("\t\t\t\t\tif (!(" + empty_check("sample_id") + ")) {\n\n");

			buffw.write("\t\t\t\t\t\tString _sample_id = rset.getString(\"Sample_ID\");\n\n");

			buffw.write("\t\t\t\t\t\tif (_sample_id != null && _sample_id.equals(sample_id)) {\n");
			buffw.write("\t\t\t\t\t\t\tid = rset.getString(\"ID\");\n");
			buffw.write("\t\t\t\t\t\t\texperiments++;\n");
			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\tcontinue;\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t\tif (experiment_name.matches(\".*J.*\") || experiment_name.matches(\".*[Cc][Oo][Ss][Yy].*\") || experiment_name.matches(\".*[Hh][Nn][Hh][Aa].*\") || experiment_name.matches(\".*[Ii][Pa][Aa][Pp].*\") || experiment_name.matches(\".*[Tt][Ro][Oo][Ss][Yy].*\") || experiment_name.matches(\".*[Cc][Oo][Uu][Pp][Ll][Ee].*\")) {\n");
			buffw.write("\t\t\t\t\tid = rset.getString(\"ID\");\n");
			buffw.write("\t\t\t\t\texperiments++;\n");
			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tif (experiments == 1)\n");
			buffw.write("\t\t\t\tval_name = id;\n\n");

			buffw.write("\t\t\telse {\n\n");

			buffw.write("\t\t\t\texperiments = 0;\n\n");

			buffw.write("\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\t\tid = rset.getString(\"ID\");\n");
			buffw.write("\t\t\t\t\texperiments++;\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t\tif (experiments == 1)\n");
			buffw.write("\t\t\t\t\tval_name = id;\n\n");

			buffw.write("\t\t\t\telse {\n\n");

			buffw.write("\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\t\tString query2 = new String(\"select \\\"RDC_code\\\" from \\\"RDC\\\" where \\\"Entry_ID\\\"='\" + entry_id + \"' and \\\"RDC_list_ID\\\"='\" + rdc_list_id + \"' and \\\"ID\\\"='1'\");\n\n");

			buffw.write("\t\t\t\t\tResultSet rset2 = state.executeQuery(query2);\n\n");

			buffw.write("\t\t\t\t\trset2.next();\n\n");

			buffw.write("\t\t\t\t\tString code = rset2.getString(\"RDC_code\");\n\n");

			buffw.write("\t\t\t\t\trset2.close();\n\n");

			buffw.write("\t\t\t\t\tif (!(" + empty_check("code") + ")) {\n\n");

			buffw.write("\t\t\t\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\t\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\t\t\t\tString experiment_name = rset.getString(\"Name\");\n\n");

			buffw.write("\t\t\t\t\t\t\tif (" + empty_check("experiment_name") + ")\n");
			buffw.write("\t\t\t\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\t\t\t\tif ((experiment_name.matches(\".*[Cc][Oo][Ss][Yy].*\") && code.matches(\".*[Cc][Oo][Ss][Yy].*\")) || (experiment_name.matches(\".*[Hh][Nn][Hh][Aa].*\") && code.matches(\".*[Hh][Nn][Hh][Aa].*\")) || (experiment_name.matches(\".*[Ii][Pa][Aa][Pp].*\") && code.matches(\".*[Ii][Pa][Aa][Pp].*\")) || (experiment_name.matches(\".*[Tt][Ro][Oo][Ss][Yy].*\") && code.matches(\".*[Tt][Ro][Oo][Ss][Yy].*\"))) {\n\n");

			buffw.write("\t\t\t\t\t\t\t\tval_name = rset.getString(\"ID\");\n");
			buffw.write("\t\t\t\t\t\t\t\tbreak;\n\n");

			buffw.write("\t\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t\tif (" + empty_check("val_name") + ")\n");
			buffw.write("\t\t\t\t\t\tval_name = \"0\";\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_rdcexperiment + ".class.getName());\n");
			buffw.write("\t\t\tlgr.log(Level.SEVERE, ex.getMessage(), ex);\n");
			buffw.write("\t\t\tSystem.exit(1);\n\n");

			buffw.write("\t\t} finally {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_rdcexperiment + ".class.getName());\n");
			buffw.write("\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_relatedentries(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_relatedentries + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.sql.*;\n");
			buffw.write("import java.util.logging.*;\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_relatedentries + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "related_entries.database_code.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getDatabaseName(String val_name, Connection conn_bmrb, String accession_code) {\n\n");

			buffw.write("\t\tif (accession_code != null)\n");
			buffw.write("\t\t\tval_name = " + file_prefix + "_" + util_dbname + ".guessDbName(val_name, accession_code);\n\n");

			buffw.write("\t\tif ((" + empty_check("val_name") + ") && accession_code != null && accession_code.matches(\"^[0-9]+$\") && !accession_code.equals(\"0\")) {\n\n");

			buffw.write("\t\t\tStatement state = null;\n");
			buffw.write("\t\t\tResultSet rset = null;\n\n");

			buffw.write("\t\t\tString query = new String(\"select \\\"ID\\\" from \\\"Entry\\\" where \\\"ID\\\"='\" + accession_code + \"'\");\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tstate = conn_bmrb.createStatement();\n");
			buffw.write("\t\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\t\tString id = rset.getString(\"ID\");\n\n");

			buffw.write("\t\t\t\t\tif (id != null && id.equals(accession_code)) {\n\n");

			buffw.write("\t\t\t\t\t\tval_name = \"BMRB\";\n");
			buffw.write("\t\t\t\t\t\tbreak;\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t\tif (" + empty_check("val_name") + ") {\n");

			buffw.write("\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\t\tString query2 = new String(\"select \\\"ID\\\" from \\\"Entry\\\" where \\\"ID\\\"::integer>\" + (Integer.valueOf(accession_code) - 10) + \" and \\\"ID\\\"::integer<\" + (Integer.valueOf(accession_code) + 10));\n\n");

			buffw.write("\t\t\t\t\trset = state.executeQuery(query2);\n\n");

			buffw.write("\t\t\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\t\t\tval_name = \"BMRB(withdrawn)\";\n");
			buffw.write("\t\t\t\t\t\tbreak;\n\n");

			buffw.write("\t\t\t\t\t}\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_relatedentries + ".class.getName());\n");
			buffw.write("\t\t\t\tlgr.log(Level.SEVERE, ex.getMessage(), ex);\n");
			buffw.write("\t\t\t\tSystem.exit(1);\n\n");

			buffw.write("\t\t\t} finally {\n\n");

			buffw.write("\t\t\t\ttry {\n\n");

			buffw.write("\t\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_relatedentries + ".class.getName());\n");
			buffw.write("\t\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t\t}\n\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn (String) map.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String checkDatabaseAccessionCode(String database_accession_code, String entry_id) {\n\n");

			buffw.write("\t\tfinal String[][] accodetbl = {\n\n");

			FileReader filer = new FileReader(xsd_dir_name + "related_entries.database_accession_code");
			BufferedReader bufferr = new BufferedReader(filer);

			String line = null;

			while ((line = bufferr.readLine()) != null) {

				if (!line.matches(".*,.*,.*"))
					continue;

				String[] elem = line.split(",");

				buffw.write("\t\t\t\t{\"" + elem[0] + "\", \"" + elem[1] + "\", \"" + elem[2] + "\"},\n");

			}

			bufferr.close();
			filer.close();

			buffw.write("\n\t\t};\n\n");

			buffw.write("\t\tfor (int i = 0; i < accodetbl.length; i++) {\n\n");

			buffw.write("\t\t\tif (accodetbl[i][0].equals(database_accession_code) && accodetbl[i][1].equals(entry_id))\n");
			buffw.write("\t\t\t\treturn accodetbl[i][2];\n\n");

			buffw.write("\t\t\tif (accodetbl[i][0].isEmpty() && (" + empty_check("database_accession_code") + ") && accodetbl[i][1].equals(entry_id))\n");
			buffw.write("\t\t\t\treturn accodetbl[i][2];\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\tif (database_accession_code == null)\n");
			buffw.write("\t\t\treturn null;\n\n");

			buffw.write("\t\treturn database_accession_code.toUpperCase();\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_release(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_release + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_release + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "release.type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getType(String val_name) {\n");
			buffw.write("\t\treturn (String) map.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_repconfrefinement(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_repconfrefinement + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_repconfrefinement + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "rep_conf_refinement.refine_method.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getRefineMethod(String val_name) {\n");
			buffw.write("\t\treturn (String) map.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_sample(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_sample + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.sql.*;\n");
			buffw.write("import java.util.logging.*;\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_sample + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "sample.type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getType(String val_name) {\n");
			buffw.write("\t\treturn (String) map.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getGelType(String val_name, Connection conn_bmrb, String entry_id) {\n\n");

			buffw.write("\t\tif (val_name != null && !val_name.equalsIgnoreCase(\"gel\"))\n");
			buffw.write("\t\t\treturn val_name;\n\n");

			buffw.write("\t\tStatement state = null;\n");
			buffw.write("\t\tResultSet rset = null;\n\n");

			buffw.write("\t\tString query = new String(\"select \\\"Experimental_method_subtype\\\" from \\\"Entry\\\" where \\\"ID\\\"='\" + entry_id + \"'\");\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tstate = conn_bmrb.createStatement();\n");
			buffw.write("\t\t\trset = state.executeQuery(query);\n\n");

			buffw.write("\t\t\twhile (rset.next()) {\n\n");

			buffw.write("\t\t\t\tString subtype = " + file_prefix + "_" + util_entry + ".getExperimentalMethodSubtype(rset.getString(\"Experimental_method_subtype\"), entry_id);\n\n");

			buffw.write("\t\t\t\tif (subtype != null && subtype.equalsIgnoreCase(\"SOLID-STATE\"))\n");
			buffw.write("\t\t\t\t\tval_name = \"gel solid\";\n");
			buffw.write("\t\t\t\telse\n");
			buffw.write("\t\t\t\t\tval_name = \"gel solution\";\n");

			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_sample + ".class.getName());\n");
			buffw.write("\t\t\tlgr.log(Level.SEVERE, ex.getMessage(), ex);\n");
			buffw.write("\t\t\tSystem.exit(1);\n\n");

			buffw.write("\t\t} finally {\n\n");

			buffw.write("\t\t\ttry {\n\n");

			buffw.write("\t\t\t\tif (rset != null)\n");
			buffw.write("\t\t\t\t\trset.close();\n\n");

			buffw.write("\t\t\t\tif (state != null)\n");
			buffw.write("\t\t\t\t\tstate.close();\n\n");

			buffw.write("\t\t\t} catch (SQLException ex) {\n\n");

			buffw.write("\t\t\t\tLogger lgr = Logger.getLogger(" + file_prefix + "_" + util_sample + ".class.getName());\n");
			buffw.write("\t\t\t\tlgr.log(Level.WARNING, ex.getMessage(), ex);\n\n");

			buffw.write("\t\t\t}\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn val_name;\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_samplecomponent(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_samplecomponent + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_samplecomponent + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "sample_component.concentration_val_units.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getConcentrationValUnits(String val_name) {\n");
			buffw.write("\t\treturn (String) map.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_sampleconditionvariable(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_sampleconditionvariable + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_sampleconditionvariable + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map_type = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "sample_condition_variable.type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getType(String val_name) {\n");
			buffw.write("\t\treturn (String) map_type.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_val_units = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "sample_condition_variable.val_units.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getValUnits(String val_name) {\n");
			buffw.write("\t\treturn (String) map_val_units.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_seqonelettercode(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_seqonelettercode + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.EnumMap;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_seqonelettercode + " {\n\n");

			buffw.write("\tprivate enum comp_ids {\n");
			buffw.write("\t\tALA, ARG, ASN, ASP, CYS, GLN, GLU, GLY, HIS, ILE, LEU, LYS, MET, PHE, PRO, SER, THR, TRP, TYR, VAL,\n");
			buffw.write("\t\tDG, DC, DA, DT, G, C, A, U\n");
			buffw.write("\t}\n\n");

			buffw.write("\tprivate static EnumMap<comp_ids, String> seq_one_letter_map;\n\n");

			buffw.write("\tstatic {\n\n");

			buffw.write("\t\tseq_one_letter_map = new EnumMap<comp_ids, String>(comp_ids.class);\n\n");

			buffw.write("\t\tseq_one_letter_map.put(comp_ids.ALA, \"A\");\n");
			buffw.write("\t\tseq_one_letter_map.put(comp_ids.ARG, \"R\");\n");
			buffw.write("\t\tseq_one_letter_map.put(comp_ids.ASN, \"N\");\n");
			buffw.write("\t\tseq_one_letter_map.put(comp_ids.ASP, \"D\");\n");
			buffw.write("\t\tseq_one_letter_map.put(comp_ids.CYS, \"C\");\n");
			buffw.write("\t\tseq_one_letter_map.put(comp_ids.GLN, \"Q\");\n");
			buffw.write("\t\tseq_one_letter_map.put(comp_ids.GLU, \"E\");\n");
			buffw.write("\t\tseq_one_letter_map.put(comp_ids.GLY, \"G\");\n");
			buffw.write("\t\tseq_one_letter_map.put(comp_ids.HIS, \"H\");\n");
			buffw.write("\t\tseq_one_letter_map.put(comp_ids.ILE, \"I\");\n");
			buffw.write("\t\tseq_one_letter_map.put(comp_ids.LEU, \"L\");\n");
			buffw.write("\t\tseq_one_letter_map.put(comp_ids.LYS, \"K\");\n");
			buffw.write("\t\tseq_one_letter_map.put(comp_ids.MET, \"M\");\n");
			buffw.write("\t\tseq_one_letter_map.put(comp_ids.PHE, \"F\");\n");
			buffw.write("\t\tseq_one_letter_map.put(comp_ids.PRO, \"P\");\n");
			buffw.write("\t\tseq_one_letter_map.put(comp_ids.SER, \"S\");\n");
			buffw.write("\t\tseq_one_letter_map.put(comp_ids.THR, \"T\");\n");
			buffw.write("\t\tseq_one_letter_map.put(comp_ids.TRP, \"W\");\n");
			buffw.write("\t\tseq_one_letter_map.put(comp_ids.TYR, \"Y\");\n");
			buffw.write("\t\tseq_one_letter_map.put(comp_ids.VAL, \"V\");\n\n");

			buffw.write("\t\tseq_one_letter_map.put(comp_ids.DG, \"G\");\n");
			buffw.write("\t\tseq_one_letter_map.put(comp_ids.DC, \"C\");\n");
			buffw.write("\t\tseq_one_letter_map.put(comp_ids.DA, \"A\");\n");
			buffw.write("\t\tseq_one_letter_map.put(comp_ids.DT, \"T\");\n\n");

			buffw.write("\t\tseq_one_letter_map.put(comp_ids.G, \"G\");\n");
			buffw.write("\t\tseq_one_letter_map.put(comp_ids.C, \"C\");\n");
			buffw.write("\t\tseq_one_letter_map.put(comp_ids.A, \"A\");\n");
			buffw.write("\t\tseq_one_letter_map.put(comp_ids.U, \"U\");\n\n");

			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getCode(String comp_id) {\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tif (" + empty_check("comp_id") + ")\n");
			buffw.write("\t\t\t\treturn \"\";\n\n");

			buffw.write("\t\t\tcomp_ids three_letter_code = comp_ids.valueOf(comp_id);\n");
			buffw.write("\t\t\treturn seq_one_letter_map.get(three_letter_code);\n\n");

			buffw.write("\t\t} catch (IllegalArgumentException e) {\n");
			buffw.write("\t\t\treturn \"X\";\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t}\n\n");

			buffw.write("\tpublic static String getCodeCan(String comp_id) {\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tif (" + empty_check("comp_id") + ")\n");
			buffw.write("\t\t\t\treturn \"\";\n\n");

			buffw.write("\t\t\tcomp_ids three_letter_code = comp_ids.valueOf(comp_id);\n");
			buffw.write("\t\t\treturn seq_one_letter_map.get(three_letter_code);\n\n");

			buffw.write("\t\t} catch (IllegalArgumentException e) {\n");
			buffw.write("\t\t\tif (comp_id.matches(\"^[0-9]{1,2}$\"))\n");
			buffw.write("\t\t\t\treturn \"X\";\n");
			buffw.write("\t\t\treturn \"(\" + comp_id.toUpperCase() + \")\";\n");
			buffw.write("\t\t}\n\n");

			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_sgproject(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_sgproject + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_sgproject + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map_full_name_of_center = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "sg_project.full_name_of_center.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getFullNameOfCenter(String val_name) {\n");
			buffw.write("\t\treturn (String) map_full_name_of_center.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_initial_of_center = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "sg_project.initial_of_center.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getInitialOfCenter(String val_name) {\n");
			buffw.write("\t\treturn (String) map_initial_of_center.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_project_name = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "sg_project.project_name.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getProjectName(String val_name) {\n");
			buffw.write("\t\treturn (String) map_project_name.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_structannochar(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_structannochar + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_structannochar + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "struct_anno_char.edge_designation.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getEdgeDesignation(String val_name) {\n");
			buffw.write("\t\treturn (String) map.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_structclassfication(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_structclassification + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.io.*;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_structclassification + " {\n\n");

			buffw.write("\tprivate String[][] sunidtbl = null;\n\n");

			buffw.write("\t" + file_prefix + "_" + util_structclassification + "() {\n\n");

			buffw.write("\t\ttry {\n\n");

			buffw.write("\t\t\tFileReader filer = new FileReader(" + file_prefix + "_" + util_main + ".xsd_dir_name + \"struct_classification.sunid\");\n");
			buffw.write("\t\t\tBufferedReader bufferr = new BufferedReader(filer);\n");
			buffw.write("\t\t\tbufferr.mark(4000000);\n\n");

			buffw.write("\t\t\tint i = 0;\n");
			buffw.write("\t\t\tString line = bufferr.readLine();\n\n");

			buffw.write("\t\t\twhile ((line = bufferr.readLine()) != null) {\n\n");

			buffw.write("\t\t\t\tif (!line.matches(\".*,.*\"))\n");
			buffw.write("\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\ti++;\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tbufferr.reset();\n\n");

			buffw.write("\t\t\tsunidtbl = new String[i][2];\n");
			buffw.write("\t\t\ti = 0;\n");
			buffw.write("\t\t\tline = bufferr.readLine();\n\n");

			buffw.write("\t\t\twhile ((line = bufferr.readLine()) != null) {\n\n");

			buffw.write("\t\t\t\tif (!line.matches(\".*,.*\"))\n");
			buffw.write("\t\t\t\t\tcontinue;\n\n");

			buffw.write("\t\t\t\tString[] elem = line.split(\",\");\n\n");

			buffw.write("\t\t\t\tsunidtbl[i] = elem;\n\n");

			buffw.write("\t\t\t\ti++;\n");
			buffw.write("\t\t\t}\n\n");

			buffw.write("\t\t\tbufferr.close();\n");
			buffw.write("\t\t\tfiler.close();\n\n");

			buffw.write("\t\t} catch (FileNotFoundException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t} catch (IOException e) {\n");
			buffw.write("\t\t\te.printStackTrace();\n");
			buffw.write("\t\t}\n");
			buffw.write("\t}\n\n");

			buffw.write("\tpublic String getSunIDByKey(String val_name) {\n\n");

			buffw.write("\t\tif (val_name == null || val_name.isEmpty())\n");
			buffw.write("\t\t\treturn null;\n\n");

			buffw.write("\t\tval_name = val_name.toLowerCase();\n\n");

			buffw.write("\t\tfor (int i = 0; i < sunidtbl.length; i++) {\n\n");

			buffw.write("\t\t\tif (sunidtbl[i][0].equals(val_name))\n");
			buffw.write("\t\t\t\treturn sunidtbl[i][1];\n\n");

			buffw.write("\t\t}\n\n");

			buffw.write("\t\treturn null;\n\n");

			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_study(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_study + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_study + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "study.type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getType(String val_name) {\n");
			buffw.write("\t\treturn (String) map.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_systematicchemshiftoffset(String src_dir_name, String xsd_dir_name, String file_prefix) {

		final String program_name = src_dir_name + file_prefix + "_" + util_systematicchemshiftoffset + ".java";

		File java_file = new File(program_name);

		try {

			FileWriter filew = new FileWriter(java_file);
			BufferedWriter buffw = new BufferedWriter(filew);

			buffw.write(license);

			buffw.write("package " + package_name + ";\n\n");

			buffw.write("import java.util.HashMap;\n");
			buffw.write("import java.util.Map;\n\n");

			buffw.write("public class " + file_prefix + "_" + util_systematicchemshiftoffset + " {\n\n");

			buffw.write("\tstatic final Map<String, String> map_type = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "systematic_chem_shift_offset.type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getType(String val_name) {\n");
			buffw.write("\t\treturn (String) map_type.get(val_name);\n");
			buffw.write("\t}\n\n");

			buffw.write("\tstatic final Map<String, String> map_atom_type = new HashMap<String, String>() {\n\n");

			buffw.write("\t\tprivate static final long serialVersionUID = " + (++serial_version_uid) + "L;\n\n");

			buffw.write("\t\t{\n\n");

			write_util_from_properties(buffw, xsd_dir_name + "systematic_chem_shift_offset.atom_type.properties");

			buffw.write("\n\t\t}\n\t};\n\n");

			buffw.write("\tpublic static String getAtomType(String val_name) {\n");
			buffw.write("\t\treturn (String) map_atom_type.get(val_name);\n");
			buffw.write("\t}\n}\n");

			buffw.close();
			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write_util_from_properties(BufferedWriter buffw, String properties_file_name) throws IOException {

		Properties prop = new Properties();

		FileInputStream prop_in = new FileInputStream(properties_file_name);

		prop.load(prop_in);

		Enumeration<?> prop_enum = prop.propertyNames();

		while (prop_enum.hasMoreElements()) {

			String key = (String) prop_enum.nextElement();
			String val = prop.getProperty(key);

			buffw.write("\t\t\tput(\"" + key + "\", \"" + (val.isEmpty() ? key : val) + "\");\n");
		}

	}

	private static String empty_check(String val_name) {
		return val_name + " == null || " + val_name + ".isEmpty() || " + val_name + ".equals(\".\") || " + val_name + ".equals(\"?\")";
	}

	// reveal: reveal tree structure of XML document

	protected static int depth = 0;

	public static void reveal(Document doc) {

		Node root = doc.getDocumentElement();

		depth = 0;

		recursiveWalk(root);
	}

	public static void getNodeInfo(Node node) {

		space_fill();
		System.out.println(depth + " type=" + node.getNodeType());

		space_fill();
		System.out.println(depth + " name=" + node.getNodeName());

		String node_val = node.getNodeValue();
		if (node_val != null) {
			space_fill();
			System.out.println(depth + " value=" + node.getNodeValue());
		}

		NamedNodeMap node_map = node.getAttributes();

		if (node_map != null) {

			for (int i = 0; i < node_map.getLength(); i++) {
				space_fill();
				System.out.println(depth + " attr(" + i + ")=" + node_map.item(i));
			}
		}

		System.out.println();
	}

	private static void recursiveWalk(Node node) {

		if (node.getNodeType() == Node.TEXT_NODE && node.getNodeValue().trim().length() == 0)
			return;

		getNodeInfo(node);

		depth++;

		for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling())
			recursiveWalk(child);

		depth--;
	}

	private static void space_fill() {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < depth; i++)
			sb.append(" ");

		System.out.print(sb.toString());

	}
}
