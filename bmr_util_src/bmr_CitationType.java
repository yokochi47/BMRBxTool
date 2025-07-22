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
import java.lang.reflect.*;
import java.util.*;
import java.util.logging.*;
import java.sql.*;

import org.apache.xmlbeans.*;
import org.w3c.dom.Node;
import org.pdbj.bmrbpub.schema.mmcifNmrStar.CitationType.*;

public class bmr_CitationType {

	private static final String table_name = "Citation";

	public static int write_xml(Connection conn_bmrb, Connection conn_clone, Connection conn_tax, Connection conn_le, bmr_Util_Entry ent, String entry_id, XmlOptions xml_opt, BufferedWriter buffw, FileWriter logw, FileWriter errw) {

		CitationType body = CitationType.Factory.newInstance();
		Citation[] list = new Citation[1];

		Statement state = null;
		Statement state_clone = null;

		try {
			state_clone = (conn_clone != null ? conn_clone.createStatement() : null);
		} catch (SQLException e) {}

		ResultSet rset = null;

		String[] rcsv = null;

		String[] rid = null;

		int list_len = 0;

		try {

			String query = new String("select count(*) from \"" + table_name + "\" where \"Entry_ID\"='" + entry_id + "' group by \"Entry_ID\"");

			state = conn_bmrb.createStatement();
			rset = state.executeQuery(query);

			if (rset.next()) {

				list_len = Integer.parseInt(rset.getString(1));

				rcsv = new String[list_len];

				rid = new String[list_len];

				String query2 = new String("select * from \"" + table_name + "\" where \"Entry_ID\"='" + entry_id + "' order by (0 || \"ID\")::decimal");

				rset.close();

				rset = state.executeQuery(query2);

				int cols = rset.getMetaData().getColumnCount();
				int lines = 0;

				buffw.write("  <BMRBx:citationCategory>\n");

				StringBuilder sb = new StringBuilder();

				while (rset.next()) {

					for (int j = 1; j <= cols; j++) {
						String val = rset.getString(j);
						sb.append((val != null ? val : "") + ",");
					}

					rcsv[lines] = sb.toString();

					sb.setLength(0);

					int l;

					String id = rset.getString("ID");
					rid[lines] = id;

					if (id == null || id.isEmpty() || id.equals(".") || id.equals("?")) {
						if (lines == 0)
							rid[lines] = "1";
						else
							rid[lines] = String.valueOf(Integer.valueOf(rid[lines - 1]) + 1);
					}

					for (l = 0; l < lines; l++) {
						if (rid[lines].equals(rid[l]))
							break;
					}

					if (l < lines)
						rid[lines] = String.valueOf(Integer.valueOf(rid[lines - 1]) + 1);

					for (l = 0; l < lines; l++) {
						if (rcsv[lines].equals(rcsv[l]))
							break;
					}

					if (l < lines++) {

						try {
							logw.write("category='Citation', duplicated content of the category was excluded.\n");
						} catch (IOException e) {
							e.printStackTrace();
						}

						continue;
					}

					list[0] = Citation.Factory.newInstance();

					String _pubmed_id = rset.getString("PubMed_ID");
					String pubmed_id = _pubmed_id;
					String title = rset.getString("Title");
					String full_citation = rset.getString("Full_citation");
					String _doi = rset.getString("DOI");
					String doi = _doi;

					boolean clone_used = false;

					if (conn_clone != null) {

						String query_clone = "select pubmed_id,doi from citation where entry_id='" + entry_id + "' and id=" + rset.getString("Id");
						ResultSet rset_clone = state_clone.executeQuery(query_clone);

						while (rset_clone.next()) {
							pubmed_id = rset_clone.getString(1);
							doi = rset_clone.getString(2);
							clone_used = pubmed_id != null && !pubmed_id.isEmpty() && doi != null && !doi.isEmpty();
							if (!clone_used) {
								pubmed_id = _pubmed_id;
								doi = _doi;
							}
							break;
						}

						rset_clone.close();

					}

					if (pubmed_id == null || pubmed_id.isEmpty() || pubmed_id.equals(".") || pubmed_id.equals("?") || pubmed_id.matches("^[0-9]$") || pubmed_id.matches("^[A-Za-z].*"))
						pubmed_id = ent.getPrimaryPubMedIDViaPDB(pubmed_id, conn_bmrb, entry_id);

					if ((pubmed_id == null || pubmed_id.isEmpty() || pubmed_id.equals(".") || pubmed_id.equals("?") || pubmed_id.matches("^[0-9]$") || pubmed_id.matches("^[A-Za-z].*")) && !(title == null || title.isEmpty() || title.equals(".") || title.equals("?")))
						pubmed_id = bmr_Util_Citation.getPubMedIDByTitle(title, true, conn_bmrb, entry_id, 0);

					if (pubmed_id != null && (pubmed_id == null || pubmed_id.isEmpty() || pubmed_id.equals(".") || pubmed_id.equals("?") || pubmed_id.matches("^[0-9]$") || pubmed_id.matches("^[A-Za-z].*")))
						pubmed_id = null;

					Node doc_sum = bmr_Util_Citation.getDocSumNodeByPubMedID(pubmed_id, 0);

					if (doc_sum == null && !(title == null || title.isEmpty() || title.equals(".") || title.equals("?"))) {
						pubmed_id = bmr_Util_Citation.getPubMedIDByTitle(title, false, conn_bmrb, entry_id, 0);
						doc_sum = bmr_Util_Citation.getDocSumNodeByPubMedID(pubmed_id, 0);
						if (doc_sum == null)
							pubmed_id = null;
					}

					if (doc_sum == null && !(full_citation == null || full_citation.isEmpty() || full_citation.equals(".") || full_citation.equals("?"))) {
						String[] terms = full_citation.split("\n")
;						for (String term : terms) {
							pubmed_id = bmr_Util_Citation.getPubMedIDByTitle(term, false, conn_bmrb, entry_id, 0);
							doc_sum = bmr_Util_Citation.getDocSumNodeByPubMedID(pubmed_id, 0);
							if (doc_sum == null)
								pubmed_id = null;
							else
								break;
						}
					}

					if (!(pubmed_id == null || pubmed_id.isEmpty() || pubmed_id.equals(".") || pubmed_id.equals("?")) && (_pubmed_id == null || _pubmed_id.isEmpty() || _pubmed_id.equals(".") || _pubmed_id.equals("?") || !pubmed_id.equals(_pubmed_id)))
						logw.write("item='PubMedId', category='Citation', value='" + _pubmed_id + "' -> '" + pubmed_id + "'\n");

					if (doc_sum != null && (!clone_used || (doi == null || doi.isEmpty() || doi.equals(".") || doi.equals("?")))) {
						doi = bmr_Util_Citation.getDOI(doi, doc_sum);
						if (doi == null || doi.isEmpty() || doi.equals(".") || doi.equals("?"))
							doi = bmr_Util_Citation.getDOIByTitle(bmr_Util_Citation.getTitle(null, doc_sum), true, conn_bmrb, entry_id, bmr_Util_Citation.getYear(null, doc_sum), 0);
					}

					if (!(doi == null || doi.isEmpty() || doi.equals(".") || doi.equals("?")) && (_doi == null || _doi.isEmpty() || _doi.equals(".") || _doi.equals("?") || !doi.equals(_doi)))
						logw.write("item='Doi', category='Citation', value='" + _doi + "' -> '" + doi + "'\n");

					if (!set_string(list[0], "setBookChapterTitle", "setNilBookChapterTitle", false, rset.getString("Book_chapter_title"), logw))
						continue;
					if (!set_string(list[0], "setBookIsbn", "setNilBookIsbn", false, rset.getString("Book_ISBN"), logw))
						continue;
					if (!set_string(list[0], "setBookPublisher", "setNilBookPublisher", false, rset.getString("Book_publisher"), logw))
						continue;
					if (!set_string(list[0], "setBookPublisherCity", "setNilBookPublisherCity", false, rset.getString("Book_publisher_city"), logw))
						continue;
					if (!set_string(list[0], "setBookSeries", "setNilBookSeries", false, rset.getString("Book_series"), logw))
						continue;
					if (!set_string(list[0], "setBookTitle", "setNilBookTitle", false, rset.getString("Book_title"), logw))
						continue;
					if (!set_string(list[0], "setBookVolume", "setNilBookVolume", false, rset.getString("Book_volume"), logw))
						continue;
					if (!set_string(list[0], "setCasAbstractCode", "setNilCasAbstractCode", false, rset.getString("CAS_abstract_code"), logw))
						continue;
					if (!set_enum_class(list[0], "setClass1", "", true, rset.getString("Class"), logw, errw))
						continue;
					if (!set_string(list[0], "setConferenceAbstractNumber", "setNilConferenceAbstractNumber", false, rset.getString("Conference_abstract_number"), logw))
						continue;
					if (!set_string(list[0], "setConferenceCountry", "setNilConferenceCountry", false, rset.getString("Conference_country"), logw))
						continue;
					if (!set_date(list[0], "setConferenceEndDate", "setNilConferenceEndDate", false, rset.getString("Conference_end_date") != null && !rset.getString("Conference_end_date").equals(".") && !rset.getString("Conference_end_date").equals("?") ? rset.getDate("Conference_end_date") : null, logw))
						continue;
					if (!set_string(list[0], "setConferenceSite", "setNilConferenceSite", false, rset.getString("Conference_site"), logw))
						continue;
					if (!set_date(list[0], "setConferenceStartDate", "setNilConferenceStartDate", false, rset.getString("Conference_start_date") != null && !rset.getString("Conference_start_date").equals(".") && !rset.getString("Conference_start_date").equals("?") ? rset.getDate("Conference_start_date") : null, logw))
						continue;
					if (!set_string(list[0], "setConferenceStateProvince", "setNilConferenceStateProvince", false, rset.getString("Conference_state_province"), logw))
						continue;
					if (!set_string(list[0], "setConferenceTitle", "setNilConferenceTitle", false, rset.getString("Conference_title"), logw))
						continue;
					if (!set_string(list[0], "setDetails", "setNilDetails", false, rset.getString("Details"), logw))
						continue;
					if (!set_string(list[0], "setDoi", "setNilDoi", false, doi, logw))
						continue;
					if (!set_string(list[0], "setFullCitation", "setNilFullCitation", false, rset.getString("Full_citation"), logw))
						continue;
					if (!set_string_by_doc_sum(list[0], "setJournalAbbrev", "setNilJournalAbbrev", false, rset.getString("Journal_abbrev"), doc_sum, logw))
						continue;
					if (!set_string(list[0], "setJournalAstm", "setNilJournalAstm", false, rset.getString("Journal_ASTM"), logw))
						continue;
					if (!set_string(list[0], "setJournalCsd", "setNilJournalCsd", false, rset.getString("Journal_CSD"), logw))
						continue;
					if (!set_string_by_doc_sum(list[0], "setJournalIssn", "setNilJournalIssn", false, rset.getString("Journal_ISSN"), doc_sum, logw))
						continue;
					if (!set_string_by_doc_sum(list[0], "setJournalIssue", "setNilJournalIssue", false, rset.getString("Journal_issue"), doc_sum, logw))
						continue;
					if (!set_string_by_doc_sum(list[0], "setJournalNameFull", "setNilJournalNameFull", false, rset.getString("Journal_name_full"), doc_sum, logw))
						continue;
					if (!set_string_by_doc_sum(list[0], "setJournalVolume", "setNilJournalVolume", false, rset.getString("Journal_volume"), doc_sum, logw))
						continue;
					if (!set_string(list[0], "setMedlineUiCode", "setNilMedlineUiCode", false, rset.getString("MEDLINE_UI_code"), logw))
						continue;
					if (!set_string(list[0], "setName", "setNilName", false, rset.getString("Name"), logw))
						continue;
					if (!set_string_by_doc_sum(list[0], "setPageFirst", "setNilPageFirst", false, rset.getString("Page_first"), doc_sum, logw))
						continue;
					if (!set_string_by_doc_sum(list[0], "setPageLast", "setNilPageLast", false, rset.getString("Page_last"), doc_sum, logw))
						continue;
					if (!set_string(list[0], "setPubmedId", "setNilPubmedId", false, pubmed_id, logw))
						continue;
					if (!set_string(list[0], "setSfCategory", "", true, rset.getString("Sf_category"), logw))
						continue;
					if (!set_string(list[0], "setSfFramecode", "", true, rset.getString("Sf_framecode"), logw))
						continue;
					if (!set_enum_status(list[0], "setStatus", "setNilStatus", false, rset.getString("Status"), logw, errw))
						continue;
					if (!set_string(list[0], "setThesisInstitution", "setNilThesisInstitution", false, rset.getString("Thesis_institution"), logw))
						continue;
					if (!set_string(list[0], "setThesisInstitutionCity", "setNilThesisInstitutionCity", false, rset.getString("Thesis_institution_city"), logw))
						continue;
					if (!set_string(list[0], "setThesisInstitutionCountry", "setNilThesisInstitutionCountry", false, rset.getString("Thesis_institution_country"), logw))
						continue;
					if (!set_string_by_doc_sum(list[0], "setTitle", "setNilTitle", false, rset.getString("Title"), doc_sum, logw))
						continue;
					if (!set_enum_type(list[0], "setType", "setNilType", false, rset.getString("Type"), logw, errw))
						continue;
					if (!set_string(list[0], "setWwwUrl", "setNilWwwUrl", false, rset.getString("WWW_URL"), logw))
						continue;
					if (!set_string_by_doc_sum(list[0], "setYear", "setNilYear", false, rset.getString("Year"), doc_sum, logw))
						continue;
					if (!set_string(list[0], "setEntryId", "", true, rset.getString("Entry_ID"), logw))
						continue;
					if (!set_integer(list[0], "setId", "", true, rid[lines - 1], logw))
						continue;

					body.setCitationArray(list);

					BufferedReader buffr = new BufferedReader(new StringReader(body.xmlText(xml_opt)));

					String prev = buffr.readLine();

					String cont = null;

					while ((prev = buffr.readLine()) != null) {

						if (cont != null)
							buffw.write(cont + "\n");

						cont = prev;

					}

					buffr.close();

				}

				buffw.write("  </BMRBx:citationCategory>\n");

			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_CitationType.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} catch (IOException ex) {

			Logger lgr = Logger.getLogger(bmr_CitationType.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

				if (state_clone != null)
					state_clone.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_CitationType.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		return list_len;
	}

	private static boolean set_string(Citation list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (bmr_Util_Main.remediate_xml && (nil_method_name == null || nil_method_name.isEmpty()) && !method_name.equalsIgnoreCase("setSampleState") && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")))
			val_name = "na";

		if ((method_name.contains("BiologicalFunction") || method_name.contains("Citation") || method_name.contains("Details") || method_name.contains("Name") || method_name.contains("Relationship") || method_name.contains("Task") || method_name.contains("Title")) && !(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && val_name.contains("\"")) {

			if ((val_name.startsWith("\"") && val_name.endsWith("\"")) || (((val_name.startsWith("\"") && val_name.lastIndexOf("\"") + 2 >= val_name.length()) || method_name.contains("Task")) && val_name.replaceAll("[^\"]", "").length() == 2) || (val_name.replaceAll("[^\"]", "").length() % 2) == 1)
				val_name = val_name.replaceAll("\"", "");

		}

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='Citation', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='Citation', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ String.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else
					method.invoke(list, val_name);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		return true;
	}

	private static boolean set_string_by_doc_sum(Citation list, String method_name, String nil_method_name, boolean required, String val_name, Node doc_sum, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml && doc_sum != null) {
			//if (method_name.equalsIgnoreCase("setDoi"))
				//val_name = bmr_Util_Citation.getDOI(val_name, doc_sum);
			if (method_name.equalsIgnoreCase("setTitle"))
				val_name = bmr_Util_Citation.getTitle(val_name, doc_sum);
			if (method_name.equalsIgnoreCase("setJournalNameFull"))
				val_name = bmr_Util_Citation.getJournalNameFull(val_name, doc_sum);
			if (method_name.equalsIgnoreCase("setJournalAbbrev"))
				val_name = bmr_Util_Citation.getJournalAbbrev(val_name, doc_sum);
			if (method_name.equalsIgnoreCase("setJournalVolume"))
				val_name = bmr_Util_Citation.getJournalVolume(val_name, doc_sum);
			if (method_name.equalsIgnoreCase("setJournalIssue"))
				val_name = bmr_Util_Citation.getJournalIssue(val_name, doc_sum);
			if (method_name.equalsIgnoreCase("setJournalIssn"))
				val_name = bmr_Util_Citation.getJournalISSN(val_name, doc_sum);
			if (method_name.equalsIgnoreCase("setPageFirst"))
				val_name = bmr_Util_Citation.getPageFirst(val_name, doc_sum);
			if (method_name.equalsIgnoreCase("setPageLast"))
				val_name = bmr_Util_Citation.getPageLast(val_name, doc_sum);
			if (method_name.equalsIgnoreCase("setYear"))
				val_name = bmr_Util_Citation.getYear(val_name, doc_sum);
		}

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='Citation', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='Citation', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ String.class });
			Method nil_method = null;

			if (nil_method_name != null && !nil_method_name.isEmpty())
				nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					nil_method.invoke(list);
				else
					method.invoke(list, val_name);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		return true;
	}

	private static boolean set_integer(Citation list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml && (nil_method_name == null || nil_method_name.isEmpty()) && !method_name.equalsIgnoreCase("setSampleID") && !method_name.equalsIgnoreCase("setExperimentID") && !method_name.equalsIgnoreCase("setStudyID") && !method_name.equalsIgnoreCase("setAssemblyID") && (method_name.contains("Id") || method_name.contains("Number") || method_name.contains("Count")) && !method_name.contains("ListId") && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$")))
			val_name = "0";

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='Citation', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='Citation', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ int.class });
			Method nil_method = null;

			if (nil_method_name != null && !nil_method_name.isEmpty())
				nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					nil_method.invoke(list);
				else
					method.invoke(list, Integer.parseInt(val_name));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		return true;
	}

	private static boolean set_date(Citation list, String method_name, String nil_method_name, boolean required, java.sql.Date date, FileWriter logw) {

		boolean nil = false;

		if (date == null)
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='Citation', value='" + date + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ Calendar.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					Calendar cal = bmr_Util_Date.sqldate2calendar(date);
					if (cal == null)
						return false;
					method.invoke(list, cal);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		return true;
	}

	private static boolean set_enum_class(Citation list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (val_name != null)
			val_name = bmr_Util_Citation.getClass1(val_name);

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='Citation', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || val_name.equals("null"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='Citation', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ Citation.Class.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					Citation.Class.Enum _enum = Citation.Class.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:Citation method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= Citation.Class.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + Citation.Class.Enum.forInt(i));
						try {
							errw.write("class_name:Citation method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= Citation.Class.Enum.table.lastInt(); i++)
								errw.write(" enum:" + Citation.Class.Enum.forInt(i) + "\n");
						} catch (IOException e) {
							e.printStackTrace();
						}
						//if (nil_method != null)
							//nil_method.invoke(list);
						//else
							return false;
					}
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		return true;
	}

	private static boolean set_enum_status(Citation list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (val_name != null)
			val_name = bmr_Util_Citation.getStatus(val_name);

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='Citation', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || val_name.equals("null"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='Citation', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ Citation.Status.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					Citation.Status.Enum _enum = Citation.Status.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:Citation method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= Citation.Status.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + Citation.Status.Enum.forInt(i));
						try {
							errw.write("class_name:Citation method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= Citation.Status.Enum.table.lastInt(); i++)
								errw.write(" enum:" + Citation.Status.Enum.forInt(i) + "\n");
						} catch (IOException e) {
							e.printStackTrace();
						}
						//if (nil_method != null)
							//nil_method.invoke(list);
						//else
							return false;
					}
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		return true;
	}

	private static boolean set_enum_type(Citation list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (val_name != null)
			val_name = bmr_Util_Citation.getType(val_name);

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='Citation', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || val_name.equals("null"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='Citation', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ Citation.Type.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					Citation.Type.Enum _enum = Citation.Type.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:Citation method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= Citation.Type.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + Citation.Type.Enum.forInt(i));
						try {
							errw.write("class_name:Citation method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= Citation.Type.Enum.table.lastInt(); i++)
								errw.write(" enum:" + Citation.Type.Enum.forInt(i) + "\n");
						} catch (IOException e) {
							e.printStackTrace();
						}
						//if (nil_method != null)
							//nil_method.invoke(list);
						//else
							return false;
					}
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		return true;
	}
}
