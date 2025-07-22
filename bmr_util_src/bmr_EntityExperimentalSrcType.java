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
import java.util.logging.*;
import java.sql.*;

import org.apache.xmlbeans.*;
import org.pdbj.bmrbpub.schema.mmcifNmrStar.EntityExperimentalSrcType.*;

public class bmr_EntityExperimentalSrcType {

	private static final String table_name = "Entity_experimental_src";

	public static int write_xml(Connection conn_bmrb, Connection conn_tax, Connection conn_le, bmr_Util_EntityExperimentalSrc ee, String entry_id, XmlOptions xml_opt, BufferedWriter buffw, FileWriter logw, FileWriter errw) {

		EntityExperimentalSrcType body = EntityExperimentalSrcType.Factory.newInstance();
		EntityExperimentalSrc[] list = new EntityExperimentalSrc[1];

		Statement state = null;
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

				String query2 = new String("select * from \"" + table_name + "\" where \"Entry_ID\"='" + entry_id + "' order by (0 || \"Entity_experimental_src_list_ID\")::decimal,(0 || \"ID\")::decimal");

				rset.close();

				rset = state.executeQuery(query2);

				int cols = rset.getMetaData().getColumnCount();
				int lines = 0;

				buffw.write("  <BMRBx:entity_experimental_srcCategory>\n");

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
							logw.write("category='EntityExperimentalSrc', duplicated content of the category was excluded.\n");
						} catch (IOException e) {
							e.printStackTrace();
						}

						continue;
					}

					list[0] = EntityExperimentalSrc.Factory.newInstance();

					String host_org_scientific_name = rset.getString("Host_org_scientific_name");
					String production_method = rset.getString("Production_method");

					String _ncbi_taxonomy_id = rset.getString("Host_org_NCBI_taxonomy_ID");
					String ncbi_taxonomy_id = bmr_Util_Tax.latestNCBITaxonomyID(conn_tax, ee.checkNCBITaxonomyID(_ncbi_taxonomy_id, entry_id, rset.getString("Entity_ID"), host_org_scientific_name, production_method));

					if ((ncbi_taxonomy_id == null || ncbi_taxonomy_id.isEmpty() || ncbi_taxonomy_id.equals(".") || ncbi_taxonomy_id.equals("?") || ncbi_taxonomy_id.equals("na")) && !(host_org_scientific_name == null || host_org_scientific_name.isEmpty() || host_org_scientific_name.equals(".") || host_org_scientific_name.equals("?") || host_org_scientific_name.equals("na")) && !(production_method == null || production_method.isEmpty() || production_method.equals(".") || production_method.equals("?") || production_method.contains("synthesis") || production_method.contains("obtained")))
						ncbi_taxonomy_id = bmr_Util_Tax.getNCBITaxonomyID(conn_tax, ncbi_taxonomy_id, host_org_scientific_name);

					if (!(ncbi_taxonomy_id == null || ncbi_taxonomy_id.isEmpty() || ncbi_taxonomy_id.equals(".") || ncbi_taxonomy_id.equals("?")) && (_ncbi_taxonomy_id == null || _ncbi_taxonomy_id.isEmpty() || _ncbi_taxonomy_id.equals(".") || _ncbi_taxonomy_id.equals("?") || !ncbi_taxonomy_id.equals(_ncbi_taxonomy_id)))
						logw.write("item='HostOrgNcbiTaxonomyId', category='EntityExperimentalSrc', value='" + _ncbi_taxonomy_id + "' -> '" + ncbi_taxonomy_id + "'\n");

					if (!set_integer(list[0], "setCitationId", "", false, rset.getString("Citation_ID"), logw))
						continue;
					if (!set_string(list[0], "setCitationLabel", "setNilCitationLabel", false, rset.getString("Citation_label"), logw))
						continue;
					if (!set_string(list[0], "setDetails", "setNilDetails", false, rset.getString("Details"), logw))
						continue;
					if (!set_integer(list[0], "setEntityChimeraSegmentId", "", false, rset.getString("Entity_chimera_segment_ID"), logw))
						continue;
					if (!set_integer(list[0], "setEntityId", "", false, rset.getString("Entity_ID"), logw))
						continue;
					if (!set_string(list[0], "setEntityLabel", "", true, rset.getString("Entity_label"), logw))
						continue;
					if (!set_string(list[0], "setHostOrgAtccNumber", "setNilHostOrgAtccNumber", false, rset.getString("Host_org_ATCC_number"), logw))
						continue;
					if (!set_string(list[0], "setHostOrgCellLine", "setNilHostOrgCellLine", false, rset.getString("Host_org_cell_line"), logw))
						continue;
					if (!set_string(list[0], "setHostOrgCellType", "setNilHostOrgCellType", false, rset.getString("Host_org_cell_type"), logw))
						continue;
					if (!set_string(list[0], "setHostOrgCellularLocation", "setNilHostOrgCellularLocation", false, rset.getString("Host_org_cellular_location"), logw))
						continue;
					if (!set_string(list[0], "setHostOrgCultureCollection", "setNilHostOrgCultureCollection", false, rset.getString("Host_org_culture_collection"), logw))
						continue;
					if (!set_string(list[0], "setHostOrgDetails", "setNilHostOrgDetails", false, rset.getString("Host_org_details"), logw))
						continue;
					if (!set_string(list[0], "setHostOrgDevStage", "setNilHostOrgDevStage", false, rset.getString("Host_org_dev_stage"), logw))
						continue;
					if (!set_string(list[0], "setHostOrgGene", "setNilHostOrgGene", false, rset.getString("Host_org_gene"), logw))
						continue;
					if (!set_string_host_org_genus(list[0], "setHostOrgGenus", "setNilHostOrgGenus", false, rset.getString("Host_org_genus"), conn_tax, ncbi_taxonomy_id, logw))
						continue;
					if (!set_string_host_org_name_common(list[0], "setHostOrgNameCommon", "setNilHostOrgNameCommon", false, rset.getString("Host_org_name_common"), conn_tax, ncbi_taxonomy_id, logw))
						continue;
					if (!set_string_ncbi_taxonomy_id(list[0], "setHostOrgNcbiTaxonomyId", "setNilHostOrgNcbiTaxonomyId", false, ncbi_taxonomy_id, conn_tax, logw, errw))
						continue;
					if (!set_string(list[0], "setHostOrgOrgan", "setNilHostOrgOrgan", false, rset.getString("Host_org_organ"), logw))
						continue;
					if (!set_string(list[0], "setHostOrgOrganelle", "setNilHostOrgOrganelle", false, rset.getString("Host_org_organelle"), logw))
						continue;
					if (!set_string_host_org_scientific_name(list[0], "setHostOrgScientificName", "setNilHostOrgScientificName", false, rset.getString("Host_org_scientific_name"), conn_tax, ncbi_taxonomy_id, logw))
						continue;
					if (!set_string_host_org_species(list[0], "setHostOrgSpecies", "setNilHostOrgSpecies", false, rset.getString("Host_org_species"), conn_tax, ncbi_taxonomy_id, logw))
						continue;
					if (!set_string(list[0], "setHostOrgStrain", "setNilHostOrgStrain", false, rset.getString("Host_org_strain"), logw))
						continue;
					if (!set_string(list[0], "setHostOrgSubvariant", "setNilHostOrgSubvariant", false, rset.getString("Host_org_subvariant"), logw))
						continue;
					if (!set_string(list[0], "setHostOrgTissue", "setNilHostOrgTissue", false, rset.getString("Host_org_tissue"), logw))
						continue;
					if (!set_string(list[0], "setHostOrgTissueFraction", "setNilHostOrgTissueFraction", false, rset.getString("Host_org_tissue_fraction"), logw))
						continue;
					if (!set_string(list[0], "setHostOrgVariant", "setNilHostOrgVariant", false, rset.getString("Host_org_variant"), logw))
						continue;
					if (!set_string(list[0], "setPdbviewHostOrgVectorName", "setNilPdbviewHostOrgVectorName", false, rset.getString("PDBview_host_org_vector_name"), logw))
						continue;
					if (!set_string(list[0], "setPdbviewPlasmidName", "setNilPdbviewPlasmidName", false, rset.getString("PDBview_plasmid_name"), logw))
						continue;
					if (!set_enum_production_method(list[0], "setProductionMethod", "", true, rset.getString("Production_method"), ee, logw, errw))
						continue;
					if (!set_string(list[0], "setVectorDetails", "setNilVectorDetails", false, rset.getString("Vector_details"), logw))
						continue;
					if (!set_string(list[0], "setVectorName", "setNilVectorName", false, rset.getString("Vector_name"), logw))
						continue;
					if (!set_string(list[0], "setVectorType", "setNilVectorType", false, rset.getString("Vector_type"), logw))
						continue;
					if (!set_string(list[0], "setVendorName", "setNilVendorName", false, rset.getString("Vendor_name"), logw))
						continue;
					if (!set_integer(list[0], "setEntityExperimentalSrcListId", "", true, rset.getString("Entity_experimental_src_list_ID"), logw))
						continue;
					if (!set_string(list[0], "setEntryId", "", true, rset.getString("Entry_ID"), logw))
						continue;
					if (!set_integer(list[0], "setId", "", true, rid[lines - 1], logw))
						continue;

					body.setEntityExperimentalSrcArray(list);

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

				buffw.write("  </BMRBx:entity_experimental_srcCategory>\n");

			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_EntityExperimentalSrcType.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} catch (IOException ex) {

			Logger lgr = Logger.getLogger(bmr_EntityExperimentalSrcType.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_EntityExperimentalSrcType.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		return list_len;
	}

	private static boolean set_string(EntityExperimentalSrc list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

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
				logw.write("item='" + method_name.substring(3) + "', category='EntityExperimentalSrc', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='EntityExperimentalSrc', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_string_ncbi_taxonomy_id(EntityExperimentalSrc list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_tax, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml && !bmr_Util_Tax.isValidNCBITaxonomyID(conn_tax, val_name)) {

			if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && val_name.matches("^[0-9]+$") && !val_name.equals("0")) {

				System.err.println("class_name:EntityExperimentalSrc method_name:host_org_ncbi_taxonomy_id val_name:" + val_name);
				try {
					errw.write("class_name:EntityExperimentalSrc method_name:host_org_ncbi_taxonomy_id val_name:" + val_name + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			val_name = null;

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='EntityExperimentalSrc', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_string_host_org_genus(EntityExperimentalSrc list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_tax, String ncbi_taxonomy_id, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml)
			val_name = bmr_Util_Tax.getGenus(val_name, conn_tax, ncbi_taxonomy_id);

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='EntityExperimentalSrc', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='EntityExperimentalSrc', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_string_host_org_species(EntityExperimentalSrc list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_tax, String ncbi_taxonomy_id, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml)
			val_name = bmr_Util_Tax.getSpecies(val_name, conn_tax, ncbi_taxonomy_id);

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='EntityExperimentalSrc', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='EntityExperimentalSrc', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_string_host_org_name_common(EntityExperimentalSrc list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_tax, String ncbi_taxonomy_id, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml)
			val_name = bmr_Util_Tax.getOrganismCommonName(val_name, conn_tax, ncbi_taxonomy_id);

		if (bmr_Util_Main.remediate_xml && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")))
			val_name = "not available";

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='EntityExperimentalSrc', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='EntityExperimentalSrc', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_string_host_org_scientific_name(EntityExperimentalSrc list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_tax, String ncbi_taxonomy_id, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml)
			val_name = bmr_Util_Tax.getOrganismScientificName(val_name, conn_tax, ncbi_taxonomy_id);

		if (bmr_Util_Main.remediate_xml && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")))
			val_name = "unidentified";

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='EntityExperimentalSrc', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='EntityExperimentalSrc', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_integer(EntityExperimentalSrc list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml && (nil_method_name == null || nil_method_name.isEmpty()) && !method_name.equalsIgnoreCase("setSampleID") && !method_name.equalsIgnoreCase("setExperimentID") && !method_name.equalsIgnoreCase("setStudyID") && !method_name.equalsIgnoreCase("setAssemblyID") && (method_name.contains("Id") || method_name.contains("Number") || method_name.contains("Count")) && !method_name.contains("ListId") && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$")))
			val_name = "0";

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='EntityExperimentalSrc', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='EntityExperimentalSrc', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_enum_production_method(EntityExperimentalSrc list, String method_name, String nil_method_name, boolean required, String val_name, bmr_Util_EntityExperimentalSrc ee, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (val_name != null)
			val_name = ee.getProductionMethod(val_name);

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?"))
			val_name = "na";

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='EntityExperimentalSrc', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='EntityExperimentalSrc', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ EntityExperimentalSrc.ProductionMethod.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					EntityExperimentalSrc.ProductionMethod.Enum _enum = EntityExperimentalSrc.ProductionMethod.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:EntityExperimentalSrc method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= EntityExperimentalSrc.ProductionMethod.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + EntityExperimentalSrc.ProductionMethod.Enum.forInt(i));
						try {
							errw.write("class_name:EntityExperimentalSrc method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= EntityExperimentalSrc.ProductionMethod.Enum.table.lastInt(); i++)
								errw.write(" enum:" + EntityExperimentalSrc.ProductionMethod.Enum.forInt(i) + "\n");
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
