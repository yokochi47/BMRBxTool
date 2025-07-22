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
import org.pdbj.bmrbpub.schema.mmcifNmrStar.EntryType.*;

public class bmr_EntryType {

	private static final String table_name = "Entry";

	public static int write_xml(Connection conn_bmrb, Connection conn_tax, Connection conn_le, bmr_Util_Entry ent, String entry_id, XmlOptions xml_opt, BufferedWriter buffw, FileWriter logw, FileWriter errw) {

		EntryType body = EntryType.Factory.newInstance();
		Entry[] list = new Entry[1];

		Statement state = null;
		ResultSet rset = null;

		String[] rcsv = null;

		int list_len = 0;

		try {

			String query = new String("select count(*) from \"" + table_name + "\" where \"ID\"='" + entry_id + "' group by \"ID\"");

			state = conn_bmrb.createStatement();
			rset = state.executeQuery(query);

			if (rset.next()) {

				list_len = Integer.parseInt(rset.getString(1));

				rcsv = new String[list_len];

				String query2 = new String("select * from \"" + table_name + "\" where \"ID\"='" + entry_id + "'");

				rset.close();

				rset = state.executeQuery(query2);

				int cols = rset.getMetaData().getColumnCount();
				int lines = 0;

				buffw.write("  <BMRBx:entryCategory>\n");

				StringBuilder sb = new StringBuilder();

				while (rset.next()) {

					for (int j = 1; j <= cols; j++) {
						String val = rset.getString(j);
						sb.append((val != null ? val : "") + ",");
					}

					rcsv[lines] = sb.toString();

					sb.setLength(0);

					int l;

					for (l = 0; l < lines; l++) {
						if (rcsv[lines].equals(rcsv[l]))
							break;
					}

					if (l < lines++) {

						try {
							logw.write("category='Entry', duplicated content of the category was excluded.\n");
						} catch (IOException e) {
							e.printStackTrace();
						}

						continue;
					}

					list[0] = Entry.Factory.newInstance();

					java.sql.Date accession_date = rset.getString("Accession_date") != null && !rset.getString("Accession_date").equals(".") && !rset.getString("Accession_date").equals("?") ? rset.getDate("Accession_date") : null;
					java.sql.Date submission_date = rset.getString("Submission_date") != null && !rset.getString("Submission_date").equals(".") && !rset.getString("Submission_date").equals("?") ? rset.getDate("Submission_date") : null;

					if (!set_date_entry(list[0], "setAccessionDate", "", true, accession_date != null ? accession_date : submission_date, conn_bmrb, entry_id, logw))
						continue;
					if (!set_string_assigned_pdb_deposition_code(list[0], "setAssignedPdbDepositionCode", "setNilAssignedPdbDepositionCode", false, rset.getString("Assigned_PDB_deposition_code"), ent, conn_bmrb, entry_id, logw))
						continue;
					if (!set_string_assigned_pdb_id(list[0], "setAssignedPdbId", "setNilAssignedPdbId", false, rset.getString("Assigned_PDB_ID"), ent, conn_bmrb, entry_id, logw))
						continue;
					if (!set_string(list[0], "setBmrbInternalDirectoryName", "setNilBmrbInternalDirectoryName", false, rset.getString("BMRB_internal_directory_name"), logw))
						continue;
					if (!set_string(list[0], "setDetails", "setNilDetails", false, rset.getString("Details"), logw))
						continue;
					if (!set_string(list[0], "setDoi", "setNilDoi", false, rset.getString("DOI"), logw))
						continue;
					if (!set_enum_experimental_method(list[0], "setExperimentalMethod", "", true, rset.getString("Experimental_method"), ent, logw, errw))
						continue;
					if (!set_enum_experimental_method_subtype(list[0], "setExperimentalMethodSubtype", "setNilExperimentalMethodSubtype", false, rset.getString("Experimental_method_subtype"), ent, entry_id, logw, errw))
						continue;
					if (!set_enum_format_name(list[0], "setFormatName", "setNilFormatName", false, rset.getString("Format_name"), logw, errw))
						continue;
					if (!set_date(list[0], "setGeneratedDate", "setNilGeneratedDate", false, rset.getString("Generated_date") != null && !rset.getString("Generated_date").equals(".") && !rset.getString("Generated_date").equals("?") ? rset.getDate("Generated_date") : null, logw))
						continue;
					if (!set_integer(list[0], "setGeneratedSoftwareId", "setNilGeneratedSoftwareId", false, rset.getString("Generated_software_ID"), logw))
						continue;
					if (!set_string(list[0], "setGeneratedSoftwareLabel", "setNilGeneratedSoftwareLabel", false, rset.getString("Generated_software_label"), logw))
						continue;
					if (!set_string(list[0], "setGeneratedSoftwareName", "setNilGeneratedSoftwareName", false, rset.getString("Generated_software_name"), logw))
						continue;
					if (!set_string(list[0], "setGeneratedSoftwareVersion", "setNilGeneratedSoftwareVersion", false, rset.getString("Generated_software_version"), logw))
						continue;
					if (!set_last_release_date(list[0], "setLastReleaseDate", "setNilLastReleaseDate", false, rset.getString("Last_release_date") != null && !rset.getString("Last_release_date").equals(".") && !rset.getString("Last_release_date").equals("?") ? rset.getDate("Last_release_date") : null, conn_bmrb, entry_id, accession_date != null ? accession_date : submission_date, logw))
						continue;
					if (!set_string(list[0], "setNmrStarVersion", "", true, rset.getString("NMR_STAR_version"), logw))
						continue;
					if (!set_string(list[0], "setOriginalNmrStarVersion", "setNilOriginalNmrStarVersion", false, rset.getString("Original_NMR_STAR_version"), logw))
						continue;
					if (!set_original_release_date(list[0], "setOriginalReleaseDate", "setNilOriginalReleaseDate", false, rset.getString("Original_release_date") != null && !rset.getString("Original_release_date").equals(".") && !rset.getString("Original_release_date").equals("?") ? rset.getDate("Original_release_date") : null, conn_bmrb, entry_id, accession_date != null ? accession_date : submission_date, logw))
						continue;
					if (!set_string(list[0], "setOrigination", "", true, rset.getString("Origination"), logw))
						continue;
					if (!set_string(list[0], "setRelatedCoordinateFileName", "setNilRelatedCoordinateFileName", false, rset.getString("Related_coordinate_file_name"), logw))
						continue;
					if (!set_string(list[0], "setSfCategory", "", true, rset.getString("Sf_category"), logw))
						continue;
					if (!set_string(list[0], "setSfFramecode", "", true, rset.getString("Sf_framecode"), logw))
						continue;
					if (!set_string(list[0], "setSourceDataFormat", "setNilSourceDataFormat", false, rset.getString("Source_data_format"), logw))
						continue;
					if (!set_string(list[0], "setSourceDataFormatVersion", "setNilSourceDataFormatVersion", false, rset.getString("Source_data_format_version"), logw))
						continue;
					if (!set_date_entry(list[0], "setSubmissionDate", "", true, submission_date != null ? submission_date : accession_date, conn_bmrb, entry_id, logw))
						continue;
					if (!set_string(list[0], "setTitle", "", true, rset.getString("Title"), logw))
						continue;
					if (!set_enum_type(list[0], "setType", "", true, rset.getString("Type"), ent, logw, errw))
						continue;
					if (!set_string(list[0], "setUuid", "setNilUuid", false, rset.getString("UUID"), logw))
						continue;
					if (!set_enum_version_type(list[0], "setVersionType", "", true, rset.getString("Version_type"), ent, logw, errw))
						continue;
					if (!set_string(list[0], "setId", "", true, rset.getString("ID"), logw))
						continue;

					body.setEntryArray(list);

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

				buffw.write("  </BMRBx:entryCategory>\n");

			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_EntryType.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} catch (IOException ex) {

			Logger lgr = Logger.getLogger(bmr_EntryType.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_EntryType.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		return list_len;
	}

	private static boolean set_string(Entry list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

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
				logw.write("item='" + method_name.substring(3) + "', category='Entry', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='Entry', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_string_assigned_pdb_id(Entry list, String method_name, String nil_method_name, boolean required, String val_name, bmr_Util_Entry ent, Connection conn_bmrb, String entry_id, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

			val_name = ent.getAssignedPDBID(val_name, conn_bmrb, entry_id);

			if ((val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (nil_method_name == null || nil_method_name.isEmpty()))
				val_name = "";

		}

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='Entry', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='Entry', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_string_assigned_pdb_deposition_code(Entry list, String method_name, String nil_method_name, boolean required, String val_name, bmr_Util_Entry ent, Connection conn_bmrb, String entry_id, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

			val_name = ent.getAssignedPDBDepositionCode(val_name, conn_bmrb, entry_id);

			if ((val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (nil_method_name == null || nil_method_name.isEmpty()))
				val_name = "";

		}

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='Entry', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='Entry', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_integer(Entry list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml && (nil_method_name == null || nil_method_name.isEmpty()) && !method_name.equalsIgnoreCase("setSampleID") && !method_name.equalsIgnoreCase("setExperimentID") && !method_name.equalsIgnoreCase("setStudyID") && !method_name.equalsIgnoreCase("setAssemblyID") && (method_name.contains("Id") || method_name.contains("Number") || method_name.contains("Count")) && !method_name.contains("ListId") && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$")))
			val_name = "0";

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='Entry', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='Entry', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_original_release_date(Entry list, String method_name, String nil_method_name, boolean required, java.sql.Date date, Connection conn_bmrb, String entry_id, java.sql.Date accession_date, FileWriter logw) {

		boolean nil = false;

		if (bmr_Util_Main.remediate_xml && date == null) {

			date = bmr_Util_Entry.getOriginalReleaseDate(date, conn_bmrb, entry_id);

			if (date == null)
				date = bmr_Util_Entry.getFirstReleaseDate(date, conn_bmrb, entry_id);

			if (date == null)
				date = accession_date;

		}

		if (date == null)
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='Entry', value='" + date + "' was empty, but not nillable.\n");
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
			Method nil_method = null;

			if (nil_method_name != null && !nil_method_name.isEmpty())
				nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					nil_method.invoke(list);
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

	private static boolean set_last_release_date(Entry list, String method_name, String nil_method_name, boolean required, java.sql.Date date, Connection conn_bmrb, String entry_id, java.sql.Date accession_date, FileWriter logw) {

		boolean nil = false;

		if (bmr_Util_Main.remediate_xml && date == null) {

			date = bmr_Util_Entry.getLastReleaseDate(date, conn_bmrb, entry_id);

			if (date == null)
				date = accession_date;

		}

		if (date == null)
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='Entry', value='" + date + "' was empty, but not nillable.\n");
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
			Method nil_method = null;

			if (nil_method_name != null && !nil_method_name.isEmpty())
				nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					nil_method.invoke(list);
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

	private static boolean set_date_entry(Entry list, String method_name, String nil_method_name, boolean required, java.sql.Date date, Connection conn_bmrb, String entry_id, FileWriter logw) {

		boolean nil = false;

		if (bmr_Util_Main.remediate_xml && date == null)
			date = bmr_Util_Entry.getFirstReleaseDate(date, conn_bmrb, entry_id);

		if (date == null)
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='Entry', value='" + date + "' was empty, but not nillable.\n");
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
			Method nil_method = null;

			if (nil_method_name != null && !nil_method_name.isEmpty())
				nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					nil_method.invoke(list);
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

	private static boolean set_date(Entry list, String method_name, String nil_method_name, boolean required, java.sql.Date date, FileWriter logw) {

		boolean nil = false;

		if (date == null)
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='Entry', value='" + date + "' was empty, but not nillable.\n");
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
			Method nil_method = null;

			if (nil_method_name != null && !nil_method_name.isEmpty())
				nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					nil_method.invoke(list);
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

	
	@SuppressWarnings("static-access")
	private static boolean set_enum_experimental_method(Entry list, String method_name, String nil_method_name, boolean required, String val_name, bmr_Util_Entry ent, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (val_name != null)
			val_name = ent.getExperimentalMethod(val_name);

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='Entry', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='Entry', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ Entry.ExperimentalMethod.Enum.class });
			Method nil_method = null;

			if (nil_method_name != null && !nil_method_name.isEmpty())
				nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					nil_method.invoke(list);
				else {
					Entry.ExperimentalMethod.Enum _enum = Entry.ExperimentalMethod.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:Entry method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= Entry.ExperimentalMethod.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + Entry.ExperimentalMethod.Enum.forInt(i));
						try {
							errw.write("class_name:Entry method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= Entry.ExperimentalMethod.Enum.table.lastInt(); i++)
								errw.write(" enum:" + Entry.ExperimentalMethod.Enum.forInt(i) + "\n");
						} catch (IOException e) {
							e.printStackTrace();
						}
						if (nil_method != null)
							nil_method.invoke(list);
						else
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

	
	@SuppressWarnings("static-access")
	private static boolean set_enum_experimental_method_subtype(Entry list, String method_name, String nil_method_name, boolean required, String val_name, bmr_Util_Entry ent, String entry_id, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (val_name != null)
			val_name = ent.getExperimentalMethodSubtype(val_name, entry_id);

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='Entry', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='Entry', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ Entry.ExperimentalMethodSubtype.Enum.class });
			Method nil_method = null;

			if (nil_method_name != null && !nil_method_name.isEmpty())
				nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					nil_method.invoke(list);
				else {
					Entry.ExperimentalMethodSubtype.Enum _enum = Entry.ExperimentalMethodSubtype.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:Entry method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= Entry.ExperimentalMethodSubtype.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + Entry.ExperimentalMethodSubtype.Enum.forInt(i));
						try {
							errw.write("class_name:Entry method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= Entry.ExperimentalMethodSubtype.Enum.table.lastInt(); i++)
								errw.write(" enum:" + Entry.ExperimentalMethodSubtype.Enum.forInt(i) + "\n");
						} catch (IOException e) {
							e.printStackTrace();
						}
						if (nil_method != null)
							nil_method.invoke(list);
						else
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

	private static boolean set_enum_format_name(Entry list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='Entry', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='Entry', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ Entry.FormatName.Enum.class });
			Method nil_method = null;

			if (nil_method_name != null && !nil_method_name.isEmpty())
				nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					nil_method.invoke(list);
				else {
					Entry.FormatName.Enum _enum = Entry.FormatName.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:Entry method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= Entry.FormatName.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + Entry.FormatName.Enum.forInt(i));
						try {
							errw.write("class_name:Entry method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= Entry.FormatName.Enum.table.lastInt(); i++)
								errw.write(" enum:" + Entry.FormatName.Enum.forInt(i) + "\n");
						} catch (IOException e) {
							e.printStackTrace();
						}
						if (nil_method != null)
							nil_method.invoke(list);
						else
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

	
	@SuppressWarnings("static-access")
	private static boolean set_enum_type(Entry list, String method_name, String nil_method_name, boolean required, String val_name, bmr_Util_Entry ent, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (val_name != null)
			val_name = ent.getType(val_name);

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?"))
			val_name = "macromolecule";

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='Entry', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='Entry', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ Entry.Type.Enum.class });
			Method nil_method = null;

			if (nil_method_name != null && !nil_method_name.isEmpty())
				nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					nil_method.invoke(list);
				else {
					Entry.Type.Enum _enum = Entry.Type.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:Entry method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= Entry.Type.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + Entry.Type.Enum.forInt(i));
						try {
							errw.write("class_name:Entry method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= Entry.Type.Enum.table.lastInt(); i++)
								errw.write(" enum:" + Entry.Type.Enum.forInt(i) + "\n");
						} catch (IOException e) {
							e.printStackTrace();
						}
						if (nil_method != null)
							nil_method.invoke(list);
						else
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

	
	@SuppressWarnings("static-access")
	private static boolean set_enum_version_type(Entry list, String method_name, String nil_method_name, boolean required, String val_name, bmr_Util_Entry ent, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (val_name != null)
			val_name = ent.getVersionType(val_name);

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='Entry', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='Entry', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ Entry.VersionType.Enum.class });
			Method nil_method = null;

			if (nil_method_name != null && !nil_method_name.isEmpty())
				nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					nil_method.invoke(list);
				else {
					Entry.VersionType.Enum _enum = Entry.VersionType.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:Entry method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= Entry.VersionType.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + Entry.VersionType.Enum.forInt(i));
						try {
							errw.write("class_name:Entry method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= Entry.VersionType.Enum.table.lastInt(); i++)
								errw.write(" enum:" + Entry.VersionType.Enum.forInt(i) + "\n");
						} catch (IOException e) {
							e.printStackTrace();
						}
						if (nil_method != null)
							nil_method.invoke(list);
						else
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
