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

import java.sql.*;
import java.util.logging.*;

public class bmr_Util_Tax {

	public static String latestNCBITaxonomyID(Connection conn_tax, String ncbi_taxonomy_id) {

		if (isValidNCBITaxonomyID(conn_tax, ncbi_taxonomy_id))
			return ncbi_taxonomy_id;

		Statement state = null;
		ResultSet rset = null;

		if (conn_tax != null && !(ncbi_taxonomy_id == null || ncbi_taxonomy_id.isEmpty() || ncbi_taxonomy_id.equals(".") || ncbi_taxonomy_id.equals("?")) && ncbi_taxonomy_id.matches("^[0-9]+$") && !ncbi_taxonomy_id.equals("0")) {

			try {

				String query = new String("select new_tax_id from merged where old_tax_id=" + ncbi_taxonomy_id);

				state = conn_tax.createStatement();
				rset = state.executeQuery(query);

				while (rset.next()) {

					String tax_id = rset.getString("new_tax_id");

					if (tax_id != null) {

						if (isValidNCBITaxonomyID(conn_tax, tax_id)){
							ncbi_taxonomy_id = tax_id;
							break;
						}

					}

				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {

				try {

					if (rset != null)
						rset.close();

					if (state != null)
						state.close();

				} catch (SQLException ex) {

					Logger lgr = Logger.getLogger(bmr_Util_Tax.class.getName());
					lgr.log(Level.WARNING, ex.getMessage(), ex);

				}
			}

		}

		return ncbi_taxonomy_id;
	}

	public static boolean isValidNCBITaxonomyID(Connection conn_tax, String ncbi_taxonomy_id) {

		boolean valid = false;

		Statement state = null;
		ResultSet rset = null;

		if (conn_tax != null && !(ncbi_taxonomy_id == null || ncbi_taxonomy_id.isEmpty() || ncbi_taxonomy_id.equals(".") || ncbi_taxonomy_id.equals("?")) && ncbi_taxonomy_id.matches("^[0-9]+$") && !ncbi_taxonomy_id.equals("0")) {

			try {

				String query = new String("select tax_id from nodes where tax_id=" + ncbi_taxonomy_id);

				state = conn_tax.createStatement();
				rset = state.executeQuery(query);

				while (rset.next()) {

					String tax_id = rset.getString("tax_id");

					if (tax_id != null && ncbi_taxonomy_id.equals(tax_id)) {
						valid = true;
						break;
					}

				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {

				try {

					if (rset != null)
						rset.close();

					if (state != null)
						state.close();

				} catch (SQLException ex) {

					Logger lgr = Logger.getLogger(bmr_Util_Tax.class.getName());
					lgr.log(Level.WARNING, ex.getMessage(), ex);

				}
			}

		}

		return valid;
	}

	public static String getNCBITaxonomyID(Connection conn_tax, String ncbi_taxonomy_id, String ncbi_taxonomy_name) {

		if (isValidNCBITaxonomyID(conn_tax, ncbi_taxonomy_id))
			return ncbi_taxonomy_id;

		Statement state = null;
		ResultSet rset = null;

		if (conn_tax != null && !(ncbi_taxonomy_name == null || ncbi_taxonomy_name.isEmpty() || ncbi_taxonomy_name.equals(".") || ncbi_taxonomy_name.equals("?")) && !ncbi_taxonomy_name.matches("na")) {

			try {

				String query = new String("select tax_id from names where name_txt='" + ncbi_taxonomy_name + "'");

				state = conn_tax.createStatement();
				rset = state.executeQuery(query);

				while (rset.next()) {

					String tax_id = rset.getString("tax_id");

					if (tax_id != null) {

						if (isValidNCBITaxonomyID(conn_tax, tax_id)){
							ncbi_taxonomy_id = tax_id;
							break;
						}

					}

				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {

				try {

					if (rset != null)
						rset.close();

					if (state != null)
						state.close();

				} catch (SQLException ex) {

					Logger lgr = Logger.getLogger(bmr_Util_Tax.class.getName());
					lgr.log(Level.WARNING, ex.getMessage(), ex);

				}
			}

		}

		return ncbi_taxonomy_id;
	}

	public static String getSuperkingdom(String val_name, Connection conn_tax, String ncbi_taxonomy_id) {

		Statement state = null;
		ResultSet rset = null;

		boolean cont = true;
		String _ncbi_taxonomy_id = ncbi_taxonomy_id;

		if (conn_tax != null && !(ncbi_taxonomy_id == null || ncbi_taxonomy_id.isEmpty() || ncbi_taxonomy_id.equals(".") || ncbi_taxonomy_id.equals("?")) && ncbi_taxonomy_id.matches("^[0-9]+$") && !ncbi_taxonomy_id.equals("0")) {

			try {

				while (cont) {

					String query = new String("select nodes.tax_id,nodes.parent_tax_id,rank,name_txt from nodes,names where nodes.tax_id=" + ncbi_taxonomy_id + " and nodes.tax_id=names.tax_id and name_class='scientific name'");

					state = conn_tax.createStatement();
					rset = state.executeQuery(query);

					while (rset.next()) {

						if (rset.getString("rank").equals("superkingdom")) {
							val_name = rset.getString("name_txt");
							cont = false;
							break;
						}

						String parent_tax_id = rset.getString("parent_tax_id");

						if (parent_tax_id == null || ncbi_taxonomy_id.equals(parent_tax_id)) {
							cont = false;
							break;
						}

						ncbi_taxonomy_id = parent_tax_id;

					}

					if (ncbi_taxonomy_id.equals(_ncbi_taxonomy_id))
						break;

					_ncbi_taxonomy_id = ncbi_taxonomy_id;

				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {

				try {

					if (rset != null)
						rset.close();

					if (state != null)
						state.close();

				} catch (SQLException ex) {

					Logger lgr = Logger.getLogger(bmr_Util_Tax.class.getName());
					lgr.log(Level.WARNING, ex.getMessage(), ex);

				}
			}

		}

		if (ncbi_taxonomy_id != null && ncbi_taxonomy_id.equals("na"))
			val_name = "Unclassified";

		return val_name;
	}

	public static String getKingdom(String val_name, Connection conn_tax, String ncbi_taxonomy_id) {

		Statement state = null;
		ResultSet rset = null;

		boolean cont = true;
		String _ncbi_taxonomy_id = ncbi_taxonomy_id;

		if (conn_tax != null && !(ncbi_taxonomy_id == null || ncbi_taxonomy_id.isEmpty() || ncbi_taxonomy_id.equals(".") || ncbi_taxonomy_id.equals("?")) && ncbi_taxonomy_id.matches("^[0-9]+$") && !ncbi_taxonomy_id.equals("0")) {

			try {

				while (cont) {

					String query = new String("select nodes.tax_id,nodes.parent_tax_id,rank,name_txt from nodes,names where nodes.tax_id=" + ncbi_taxonomy_id + " and nodes.tax_id=names.tax_id and name_class='scientific name'");

					state = conn_tax.createStatement();
					rset = state.executeQuery(query);

					while (rset.next()) {

						if (rset.getString("rank").equals("kingdom")) {
							val_name = rset.getString("name_txt");
							cont = false;
							break;
						}

						if (rset.getString("rank").equals("superkingdom")) {
							val_name = "Not applicable";
							cont = false;
							break;
						}

						String parent_tax_id = rset.getString("parent_tax_id");

						if (parent_tax_id == null || ncbi_taxonomy_id.equals(parent_tax_id)) {
							cont = false;
							break;
						}

						ncbi_taxonomy_id = parent_tax_id;

					}

					if (ncbi_taxonomy_id.equals(_ncbi_taxonomy_id))
						break;

					_ncbi_taxonomy_id = ncbi_taxonomy_id;

				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {

				try {

					if (rset != null)
						rset.close();

					if (state != null)
						state.close();

				} catch (SQLException ex) {

					Logger lgr = Logger.getLogger(bmr_Util_Tax.class.getName());
					lgr.log(Level.WARNING, ex.getMessage(), ex);

				}
			}

		}

		if (ncbi_taxonomy_id != null && ncbi_taxonomy_id.equals("na"))
			val_name = "Not applicable";

		if (val_name != null && val_name.equals("Eubacteria"))
			val_name = "Not applicable";

		return val_name;
	}

	public static String getGenus(String val_name, Connection conn_tax, String ncbi_taxonomy_id) {

		Statement state = null;
		ResultSet rset = null;

		boolean cont = true;
		String _ncbi_taxonomy_id = ncbi_taxonomy_id;

		if (conn_tax != null && !(ncbi_taxonomy_id == null || ncbi_taxonomy_id.isEmpty() || ncbi_taxonomy_id.equals(".") || ncbi_taxonomy_id.equals("?")) && ncbi_taxonomy_id.matches("^[0-9]+$") && !ncbi_taxonomy_id.equals("0")) {

			try {

				while (cont) {

					String query = new String("select nodes.tax_id,nodes.parent_tax_id,rank,name_txt from nodes,names where nodes.tax_id=" + ncbi_taxonomy_id + " and nodes.tax_id=names.tax_id and name_class='scientific name'");

					state = conn_tax.createStatement();
					rset = state.executeQuery(query);

					while (rset.next()) {

						if (rset.getString("rank").equals("genus")) {
							val_name = rset.getString("name_txt");
							cont = false;
							break;
						}

						if (rset.getString("rank").equals("superkingdom")) {
							cont = false;
							break;
						}

						String parent_tax_id = rset.getString("parent_tax_id");

						if (parent_tax_id == null || ncbi_taxonomy_id.equals(parent_tax_id)) {
							cont = false;
							break;
						}

						ncbi_taxonomy_id = parent_tax_id;

					}

					if (ncbi_taxonomy_id.equals(_ncbi_taxonomy_id))
						break;

					_ncbi_taxonomy_id = ncbi_taxonomy_id;

				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {

				try {

					if (rset != null)
						rset.close();

					if (state != null)
						state.close();

				} catch (SQLException ex) {

					Logger lgr = Logger.getLogger(bmr_Util_Tax.class.getName());
					lgr.log(Level.WARNING, ex.getMessage(), ex);

				}
			}

		}

		if (ncbi_taxonomy_id != null && ncbi_taxonomy_id.equals("na"))
			val_name = "Not applicable";

		return val_name;
	}

	public static String getSpecies(String val_name, Connection conn_tax, String ncbi_taxonomy_id) {

		Statement state = null;
		ResultSet rset = null;

		boolean cont = true;
		String _ncbi_taxonomy_id = ncbi_taxonomy_id;

		if (conn_tax != null && !(ncbi_taxonomy_id == null || ncbi_taxonomy_id.isEmpty() || ncbi_taxonomy_id.equals(".") || ncbi_taxonomy_id.equals("?")) && ncbi_taxonomy_id.matches("^[0-9]+$") && !ncbi_taxonomy_id.equals("0")) {

			try {

				while (cont) {

					String query = new String("select nodes.tax_id,nodes.parent_tax_id,rank,name_txt from nodes,names where nodes.tax_id=" + ncbi_taxonomy_id + " and nodes.tax_id=names.tax_id and name_class='scientific name'");

					state = conn_tax.createStatement();
					rset = state.executeQuery(query);

					while (rset.next()) {

						if (rset.getString("rank").equals("species")) {
							val_name = rset.getString("name_txt");
							String genus = getGenus(null, conn_tax, ncbi_taxonomy_id);
							if (genus != null && val_name.startsWith(genus + " "))
								val_name = val_name.replaceFirst(genus + " ", "");
							cont = false;
							break;
						}

						if (rset.getString("rank").equals("superkingdom")) {
							cont = false;
							break;
						}

						String parent_tax_id = rset.getString("parent_tax_id");

						if (parent_tax_id == null || ncbi_taxonomy_id.equals(parent_tax_id)) {
							cont = false;
							break;
						}

						ncbi_taxonomy_id = parent_tax_id;

					}

					if (ncbi_taxonomy_id.equals(_ncbi_taxonomy_id))
						break;

					_ncbi_taxonomy_id = ncbi_taxonomy_id;

				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {

				try {

					if (rset != null)
						rset.close();

					if (state != null)
						state.close();

				} catch (SQLException ex) {

					Logger lgr = Logger.getLogger(bmr_Util_Tax.class.getName());
					lgr.log(Level.WARNING, ex.getMessage(), ex);

				}
			}

		}

		if (ncbi_taxonomy_id != null && ncbi_taxonomy_id.equals("na"))
			val_name = "unidentified";

		return val_name;
	}

	public static String getOrganismScientificName(String val_name, Connection conn_tax, String ncbi_taxonomy_id) {

		Statement state = null;
		ResultSet rset = null;

		if (conn_tax != null && !(ncbi_taxonomy_id == null || ncbi_taxonomy_id.isEmpty() || ncbi_taxonomy_id.equals(".") || ncbi_taxonomy_id.equals("?")) && ncbi_taxonomy_id.matches("^[0-9]+$") && !ncbi_taxonomy_id.equals("0")) {

			try {

				String query = new String("select nodes.tax_id,nodes.parent_tax_id,rank,name_txt from nodes,names where nodes.tax_id=" + ncbi_taxonomy_id + " and nodes.tax_id=names.tax_id and name_class='scientific name'");

				state = conn_tax.createStatement();
				rset = state.executeQuery(query);

				while (rset.next()) {
					val_name = rset.getString("name_txt");
					break;
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {

				try {

					if (rset != null)
						rset.close();

					if (state != null)
						state.close();

				} catch (SQLException ex) {

					Logger lgr = Logger.getLogger(bmr_Util_Tax.class.getName());
					lgr.log(Level.WARNING, ex.getMessage(), ex);

				}
			}

		}

		if (ncbi_taxonomy_id != null && ncbi_taxonomy_id.equals("na"))
			val_name = "unidentified";

		return val_name;
	}

	public static String getOrganismCommonName(String val_name, Connection conn_tax, String ncbi_taxonomy_id) {

		Statement state = null;
		ResultSet rset = null;

		boolean cont = true;
		String _ncbi_taxonomy_id = ncbi_taxonomy_id;

		if (conn_tax != null && !(ncbi_taxonomy_id == null || ncbi_taxonomy_id.isEmpty() || ncbi_taxonomy_id.equals(".") || ncbi_taxonomy_id.equals("?")) && ncbi_taxonomy_id.matches("^[0-9]+$") && !ncbi_taxonomy_id.equals("0")) {

			try {

				while (cont) {

					String query = new String("select nodes.tax_id,nodes.parent_tax_id,rank,name_txt from nodes,names where nodes.tax_id=" + ncbi_taxonomy_id + " and nodes.tax_id=names.tax_id and name_class like '%common name'");

					state = conn_tax.createStatement();
					rset = state.executeQuery(query);

					while (rset.next()) {

						if (rset.getString("rank").equals("species")) {
							val_name = rset.getString("name_txt");
							cont = false;
							break;
						}

						if (rset.getString("rank").equals("superkingdom")) {
							cont = false;
							break;
						}

						String parent_tax_id = rset.getString("parent_tax_id");

						if (parent_tax_id == null || ncbi_taxonomy_id.equals(parent_tax_id)) {
							cont = false;
							break;
						}

						ncbi_taxonomy_id = parent_tax_id;

					}

					if (ncbi_taxonomy_id.equals(_ncbi_taxonomy_id))
						break;

					_ncbi_taxonomy_id = ncbi_taxonomy_id;

				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {

				try {

					if (rset != null)
						rset.close();

					if (state != null)
						state.close();

				} catch (SQLException ex) {

					Logger lgr = Logger.getLogger(bmr_Util_Tax.class.getName());
					lgr.log(Level.WARNING, ex.getMessage(), ex);

				}
			}

		}

		if (ncbi_taxonomy_id != null && ncbi_taxonomy_id.equals("na"))
			val_name = "unidentified";

		return val_name;
	}
}
