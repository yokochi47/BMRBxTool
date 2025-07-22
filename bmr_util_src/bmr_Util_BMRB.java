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

interface experiment_name_cmp {

	boolean hexchprotectionfact(String experiment_name);
	boolean hexchrate(String experiment_name);
	boolean heteronuclt1(String experiment_name);
	boolean heteronuclt2(String experiment_name);
	boolean heteronuclt1rho(String experiment_name);
	boolean heteronuclnoe(String experiment_name);
	boolean homonuclnoe(String experiment_name);

}

class experiment_name_cmp_impl implements experiment_name_cmp {

	public boolean hexchprotectionfact(String experiment_name) {
		return experiment_name.matches(".*[Ee][Xe].*") || experiment_name.matches(".*H.*D.*") || experiment_name.contains("D2O");
	}

	public boolean hexchrate(String experiment_name) {
		return experiment_name.matches(".*[Ee][Xe].*");
	}

	public boolean heteronuclt1(String experiment_name) {
		return experiment_name.matches(".*[RT]1.*") && (experiment_name.contains("15") || experiment_name.contains("13"));
	}

	public boolean heteronuclt2(String experiment_name) {
		return experiment_name.matches(".*[RT]2.*") && (experiment_name.contains("15") || experiment_name.contains("13"));
	}

	public boolean heteronuclt1rho(String experiment_name) {
		return experiment_name.matches(".*[RT]1[Rr].*") && (experiment_name.contains("15") || experiment_name.contains("13"));
	}

	public boolean heteronuclnoe(String experiment_name) {
		return experiment_name.matches(".*[Nn][Oo][Ee][^Ss].*") && (experiment_name.contains("15") || experiment_name.contains("13"));
	}

	public boolean homonuclnoe(String experiment_name) {
		return experiment_name.matches(".*[Nn][Oo][Ee].*") && !experiment_name.contains("15") && !experiment_name.contains("13");
	}

}

public class bmr_Util_BMRB {

	public enum experiment_type {

		HExchProtectionFact, HExchRate, HeteronuclT1, HeteronuclT2, HeteronuclT1Rho, HeteronuclNOE, HomonuclNOE

	}

	private static boolean cmp_sel (experiment_type type, String experiment_name) {

		experiment_name_cmp_impl cmp = new experiment_name_cmp_impl();

		switch (type) {
		case HExchProtectionFact:
			return cmp.hexchprotectionfact(experiment_name);
		case HExchRate:
			return cmp.hexchrate(experiment_name);
		case HeteronuclT1:
			return cmp.heteronuclt1(experiment_name);
		case HeteronuclT2:
			return cmp.heteronuclt2(experiment_name);
		case HeteronuclT1Rho:
			return cmp.heteronuclt1rho(experiment_name);
		case HeteronuclNOE:
			return cmp.heteronuclnoe(experiment_name);
		case HomonuclNOE:
			return cmp.homonuclnoe(experiment_name);
		}

		return false;
	}

	public static String getExperimentID(String val_name, Connection conn_bmrb, String entry_id, String sample_id) {

		Statement state = null;
		ResultSet rset = null;

		String query = new String("select \"ID\",\"Name\",\"Sample_ID\" from \"Experiment\" where \"Entry_ID\"='" + entry_id + "'");

		int experiments = 0;
		String id = null;

		try {

			state = conn_bmrb.createStatement();
			rset = state.executeQuery(query);

			while (rset.next()) {

				String experiment_name = rset.getString("Name");

				if (experiment_name == null || experiment_name.isEmpty() || experiment_name.equals(".") || experiment_name.equals("?")) {

					if (!(sample_id == null || sample_id.isEmpty() || sample_id.equals(".") || sample_id.equals("?"))) {

						String _sample_id = rset.getString("Sample_ID");

						if (_sample_id != null && _sample_id.equals(sample_id)) {
							id = rset.getString("ID");
							experiments++;
						}

					}

					continue;
				}

			}

			if (experiments == 1)
				val_name = id;

			else {

				experiments = 0;

				rset.close();

				rset = state.executeQuery(query);

				while (rset.next()) {

					id = rset.getString("ID");
					experiments++;

				}

				if (experiments == 1)
					val_name = id;

				if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?"))
					val_name = "0";

			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_Util_BMRB.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_Util_BMRB.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		return val_name;
	}

	public static String getExperimentID(String val_name, Connection conn_bmrb, String entry_id, String sample_id, experiment_type type) {

		Statement state = null;
		ResultSet rset = null;

		String query = new String("select \"ID\",\"Name\",\"Sample_ID\" from \"Experiment\" where \"Entry_ID\"='" + entry_id + "'");

		int experiments = 0;
		String id = null;

		try {

			state = conn_bmrb.createStatement();
			rset = state.executeQuery(query);

			while (rset.next()) {

				String experiment_name = rset.getString("Name");

				if (experiment_name == null || experiment_name.isEmpty() || experiment_name.equals(".") || experiment_name.equals("?")) {

					if (!(sample_id == null || sample_id.isEmpty() || sample_id.equals(".") || sample_id.equals("?"))) {

						String _sample_id = rset.getString("Sample_ID");

						if (_sample_id != null && _sample_id.equals(sample_id)) {
							id = rset.getString("ID");
							experiments++;
						}

					}

					continue;
				}

				if (cmp_sel(type, experiment_name)) {
					id = rset.getString("ID");
					experiments++;
				}

			}

			if (experiments == 1)
				val_name = id;

			else {

				experiments = 0;

				rset.close();

				rset = state.executeQuery(query);

				while (rset.next()) {

					id = rset.getString("ID");
					experiments++;

				}

				if (experiments == 1)
					val_name = id;

				if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?"))
					val_name = "0";

			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_Util_BMRB.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_Util_BMRB.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		return val_name;
	}

	public static String getSampleID(String val_name, Connection conn_bmrb, String entry_id, String experiment_id) {

		Statement state = null;
		ResultSet rset = null;

		String query = new String("select \"ID\",\"Sample_ID\" from \"Experiment\" where \"Entry_ID\"='" + entry_id + "'");

		int samples = 0;

		try {

			state = conn_bmrb.createStatement();
			rset = state.executeQuery(query);

			while (rset.next()) {

				samples++;

				String sample_id = rset.getString("Sample_ID");

				if (sample_id == null || sample_id.isEmpty() || sample_id.equals(".") || sample_id.equals("?"))
					continue;

				String id = rset.getString("ID");

				if (id == null || id.isEmpty() || id.equals(".") || id.equals("?"))
					continue;

				if (experiment_id != null && experiment_id.equals(id)) {
					val_name = sample_id;
					break;
				}

			}

			if (samples == 1 && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")))
				val_name = "1";

			else
				val_name = "0";

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_Util_BMRB.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_Util_BMRB.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		return val_name;
	}

	public static String getSampleID(String val_name, Connection conn_bmrb, String entry_id) {

		Statement state = null;
		ResultSet rset = null;

		String query = new String("select \"ID\" from \"Sample\" where \"Entry_ID\"='" + entry_id + "'");

		int ids = 0;

		try {

			state = conn_bmrb.createStatement();
			rset = state.executeQuery(query);

			while (rset.next())
				ids++;

			if (ids == 1 && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")))
				val_name = "1";

			else
				val_name = "0";

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_Util_BMRB.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_Util_BMRB.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		return val_name;
	}

	public static String getStudyID(String val_name, Connection conn_bmrb, String entry_id, String study_list_id) {

		Statement state = null;
		ResultSet rset = null;

		String query = new String("select \"ID\" from \"Study\" where \"Entry_ID\"='" + entry_id + "' and \"Study_list_ID\"='" + study_list_id + "'");

		int studies = 0;
		String id = null;

		try {

			state = conn_bmrb.createStatement();
			rset = state.executeQuery(query);

			while (rset.next()) {

				id = rset.getString("ID");
				studies++;

			}

			if (studies == 1 && (!(id == null || id.isEmpty() || id.equals(".") || id.equals("?"))))
				val_name = id;
			else if (studies <= 1)
				val_name = "1";
			else // if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?"))
				val_name = "0";

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_Util_BMRB.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_Util_BMRB.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		return val_name;
	}

	public static String getAssemblyID(String val_name, Connection conn_bmrb, String entry_id, String entity_id) {

		Statement state = null;
		ResultSet rset = null;

		String query = new String("select \"Assembly_ID\" from \"Entity_assembly\" where \"Entry_ID\"='" + entry_id + "' and \"Entity_ID\"='" + entity_id + "'");

		int assemblies = 0;
		String assembly_id = null;

		try {

			state = conn_bmrb.createStatement();
			rset = state.executeQuery(query);

			while (rset.next()) {

				assembly_id = rset.getString("Assembly_ID");
				assemblies++;

			}

			if (assemblies == 1 && (!(assembly_id == null || assembly_id.isEmpty() || assembly_id.equals(".") || assembly_id.equals("?"))))
				val_name = assembly_id;
			else if (assemblies <= 1)
				val_name = "1";
			else // if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?"))
				val_name = "0";

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_Util_BMRB.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_Util_BMRB.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		return val_name;
	}

	public static String getSampleConditionListID(String val_name, Connection conn_bmrb, String entry_id) {

		Statement state = null;
		ResultSet rset = null;

		String query = new String("select \"ID\" from \"Sample_condition_list\" where \"Entry_ID\"='" + entry_id + "'");

		int ids = 0;
		String id = null;

		try {

			state = conn_bmrb.createStatement();
			rset = state.executeQuery(query);

			while (rset.next()) {

				id = rset.getString("ID");
				ids++;

			}

			if (ids == 1 && (!(id == null || id.isEmpty() || id.equals(".") || id.equals("?"))))
				val_name = id;
			else if (ids <= 1)
				val_name = "1";
			else // if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?"))
				val_name = "0";

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_Util_BMRB.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_Util_BMRB.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		return val_name;
	}

	public static String getAssignedChemShiftListID(String val_name, Connection conn_bmrb, String entry_id) {

		Statement state = null;
		ResultSet rset = null;

		String query = new String("select \"ID\" from \"Assigned_chem_shift_list\" where \"Entry_ID\"='" + entry_id + "'");

		int ids = 0;
		String id = null;

		try {

			state = conn_bmrb.createStatement();
			rset = state.executeQuery(query);

			while (rset.next()) {

				id = rset.getString("ID");
				ids++;

			}

			if (ids == 1 && (!(id == null || id.isEmpty() || id.equals(".") || id.equals("?"))))
				val_name = id;
			else if (ids <= 1)
				val_name = "1";
			else // if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?"))
				val_name = "0";

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_Util_BMRB.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_Util_BMRB.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		return val_name;
	}

	public static String getSpectralPeakListID(String val_name, Connection conn_bmrb, String entry_id) {

		Statement state = null;
		ResultSet rset = null;

		String query = new String("select \"ID\" from \"Spectral_peak_list\" where \"Entry_ID\"='" + entry_id + "'");

		int ids = 0;
		String id = null;

		try {

			state = conn_bmrb.createStatement();
			rset = state.executeQuery(query);

			while (rset.next()) {

				id = rset.getString("ID");
				ids++;

			}

			if (ids == 1 && (!(id == null || id.isEmpty() || id.equals(".") || id.equals("?"))))
				val_name = id;
			else if (ids <= 1)
				val_name = "1";
			else // if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?"))
				val_name = "0";

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_Util_BMRB.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_Util_BMRB.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		return val_name;
	}
}
