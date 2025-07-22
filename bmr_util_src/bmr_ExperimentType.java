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
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.logging.*;
import java.sql.*;

import org.apache.xmlbeans.*;
import org.pdbj.bmrbpub.schema.mmcifNmrStar.ExperimentType.*;

public class bmr_ExperimentType {

	private static final String table_name = "Experiment";

	public static int write_xml(Connection conn_bmrb, Connection conn_tax, Connection conn_le, String entry_id, XmlOptions xml_opt, BufferedWriter buffw, FileWriter logw, FileWriter errw) {

		ExperimentType body = ExperimentType.Factory.newInstance();
		Experiment[] list = new Experiment[1];

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

				String query2 = new String("select * from \"" + table_name + "\" where \"Entry_ID\"='" + entry_id + "' order by (0 || \"Experiment_list_ID\")::decimal,(0 || \"ID\")::decimal");

				rset.close();

				rset = state.executeQuery(query2);

				int cols = rset.getMetaData().getColumnCount();
				int lines = 0;

				buffw.write("  <BMRBx:experimentCategory>\n");

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
							logw.write("category='Experiment', duplicated content of the category was excluded.\n");
						} catch (IOException e) {
							e.printStackTrace();
						}

						continue;
					}

					list[0] = Experiment.Factory.newInstance();

					if (!set_integer(list[0], "setChromatographicColumnId", "", false, rset.getString("Chromatographic_column_ID"), logw))
						continue;
					if (!set_string(list[0], "setChromatographicColumnLabel", "setNilChromatographicColumnLabel", false, rset.getString("Chromatographic_column_label"), logw))
						continue;
					if (!set_integer(list[0], "setChromatographicSystemId", "", false, rset.getString("Chromatographic_system_ID"), logw))
						continue;
					if (!set_string(list[0], "setChromatographicSystemLabel", "setNilChromatographicSystemLabel", false, rset.getString("Chromatographic_system_label"), logw))
						continue;
					if (!set_integer(list[0], "setEmrExptId", "setNilEmrExptId", false, rset.getString("EMR_expt_ID"), logw))
						continue;
					if (!set_string(list[0], "setEmrExptLabel", "setNilEmrExptLabel", false, rset.getString("EMR_expt_label"), logw))
						continue;
					if (!set_integer(list[0], "setEmrInstrumentId", "", false, rset.getString("EMR_instrument_ID"), logw))
						continue;
					if (!set_string(list[0], "setEmrInstrumentLabel", "setNilEmrInstrumentLabel", false, rset.getString("EMR_instrument_label"), logw))
						continue;
					if (!set_integer(list[0], "setFluorescenceInstrumentId", "", false, rset.getString("Fluorescence_instrument_ID"), logw))
						continue;
					if (!set_string(list[0], "setFluorescenceInstrumentLabel", "setNilFluorescenceInstrumentLabel", false, rset.getString("Fluorescence_instrument_label"), logw))
						continue;
					if (!set_integer(list[0], "setFretExptId", "setNilFretExptId", false, rset.getString("FRET_expt_ID"), logw))
						continue;
					if (!set_string(list[0], "setFretExptLabel", "setNilFretExptLabel", false, rset.getString("FRET_expt_label"), logw))
						continue;
					if (!set_integer(list[0], "setMassSpectrometerId", "", false, rset.getString("Mass_spectrometer_ID"), logw))
						continue;
					if (!set_string(list[0], "setMassSpectrometerLabel", "setNilMassSpectrometerLabel", false, rset.getString("Mass_spectrometer_label"), logw))
						continue;
					if (!set_integer(list[0], "setMsExptId", "setNilMsExptId", false, rset.getString("MS_expt_ID"), logw))
						continue;
					if (!set_string(list[0], "setMsExptLabel", "setNilMsExptLabel", false, rset.getString("MS_expt_label"), logw))
						continue;
					if (!set_string(list[0], "setName", "", true, rset.getString("Name"), logw))
						continue;
					if (!set_integer(list[0], "setNmrSpecExptId", "setNilNmrSpecExptId", false, rset.getString("NMR_spec_expt_ID"), logw))
						continue;
					if (!set_string(list[0], "setNmrSpecExptLabel", "setNilNmrSpecExptLabel", false, rset.getString("NMR_spec_expt_label"), logw))
						continue;
					if (!set_integer(list[0], "setNmrSpectralProcessingId", "setNilNmrSpectralProcessingId", false, rset.getString("NMR_spectral_processing_ID"), logw))
						continue;
					if (!set_string(list[0], "setNmrSpectralProcessingLabel", "setNilNmrSpectralProcessingLabel", false, rset.getString("NMR_spectral_processing_label"), logw))
						continue;
					if (!set_integer(list[0], "setNmrSpectrometerId", "", false, rset.getString("NMR_spectrometer_ID"), logw))
						continue;
					if (!set_string(list[0], "setNmrSpectrometerLabel", "setNilNmrSpectrometerLabel", false, rset.getString("NMR_spectrometer_label"), logw))
						continue;
					if (!set_integer(list[0], "setNmrSpectrometerProbeId", "", false, rset.getString("NMR_spectrometer_probe_ID"), logw))
						continue;
					if (!set_string(list[0], "setNmrSpectrometerProbeLabel", "setNilNmrSpectrometerProbeLabel", false, rset.getString("NMR_spectrometer_probe_label"), logw))
						continue;
					if (!set_string(list[0], "setNmrTubeType", "setNilNmrTubeType", false, rset.getString("NMR_tube_type"), logw))
						continue;
					if (!set_enum_raw_data_flag(list[0], "setRawDataFlag", "setNilRawDataFlag", false, rset.getString("Raw_data_flag"), logw, errw))
						continue;
					if (!set_decimal(list[0], "setSampleAngle", "setNilSampleAngle", false, rset.getString("Sample_angle"), logw))
						continue;
					if (!set_integer_sample_condition_list_id(list[0], "setSampleConditionListId", "", false, rset.getString("Sample_condition_list_ID"), conn_bmrb, entry_id, logw))
						continue;
					if (!set_string(list[0], "setSampleConditionListLabel", "setNilSampleConditionListLabel", false, rset.getString("Sample_condition_list_label"), logw))
						continue;
					if (!set_integer_sample_id(list[0], "setSampleId", "", false, rset.getString("Sample_ID"), conn_bmrb, entry_id, logw))
						continue;
					if (!set_string(list[0], "setSampleLabel", "setNilSampleLabel", false, rset.getString("Sample_label"), logw))
						continue;
					if (!set_decimal(list[0], "setSampleSpinningRate", "setNilSampleSpinningRate", false, rset.getString("Sample_spinning_rate"), logw))
						continue;
					if (!set_enum_sample_state(list[0], "setSampleState", "setNilSampleState", false, rset.getString("Sample_state"), logw, errw))
						continue;
					if (!set_decimal(list[0], "setSampleVolume", "setNilSampleVolume", false, rset.getString("Sample_volume"), logw))
						continue;
					if (!set_string(list[0], "setSampleVolumeUnits", "setNilSampleVolumeUnits", false, rset.getString("Sample_volume_units"), logw))
						continue;
					if (!set_integer(list[0], "setSaxsExptId", "setNilSaxsExptId", false, rset.getString("SAXS_expt_ID"), logw))
						continue;
					if (!set_string(list[0], "setSaxsExptLabel", "setNilSaxsExptLabel", false, rset.getString("SAXS_expt_label"), logw))
						continue;
					if (!set_integer(list[0], "setXrayInstrumentId", "setNilXrayInstrumentId", false, rset.getString("Xray_instrument_ID"), logw))
						continue;
					if (!set_string(list[0], "setXrayInstrumentLabel", "setNilXrayInstrumentLabel", false, rset.getString("Xray_instrument_label"), logw))
						continue;
					if (!set_string(list[0], "setEntryId", "", true, rset.getString("Entry_ID"), logw))
						continue;
					if (!set_integer(list[0], "setExperimentListId", "", true, rset.getString("Experiment_list_ID"), logw))
						continue;
					if (!set_integer(list[0], "setId", "", true, rid[lines - 1], logw))
						continue;

					body.setExperimentArray(list);

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

				buffw.write("  </BMRBx:experimentCategory>\n");

			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_ExperimentType.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} catch (IOException ex) {

			Logger lgr = Logger.getLogger(bmr_ExperimentType.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_ExperimentType.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		return list_len;
	}

	private static boolean set_string(Experiment list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

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
				logw.write("item='" + method_name.substring(3) + "', category='Experiment', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='Experiment', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_integer(Experiment list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml && (nil_method_name == null || nil_method_name.isEmpty()) && !method_name.equalsIgnoreCase("setSampleID") && !method_name.equalsIgnoreCase("setExperimentID") && !method_name.equalsIgnoreCase("setStudyID") && !method_name.equalsIgnoreCase("setAssemblyID") && (method_name.contains("Id") || method_name.contains("Number") || method_name.contains("Count")) && !method_name.contains("ListId") && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$")))
			val_name = "0";

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='Experiment', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='Experiment', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_integer_sample_id(Experiment list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, String entry_id, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")))
			val_name = bmr_Util_BMRB.getSampleID(val_name, conn_bmrb, entry_id);

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='Experiment', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='Experiment', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_integer_sample_condition_list_id(Experiment list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, String entry_id, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")))
			val_name = bmr_Util_BMRB.getSampleConditionListID(val_name, conn_bmrb, entry_id);

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='Experiment', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='Experiment', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_decimal(Experiment list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || (!val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)?$") && !val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)([eE][-+]?[0-9]+)?$"))) && (nil_method_name == null || nil_method_name.isEmpty())) {

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='Experiment', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || (!val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)?$") && !val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)([eE][-+]?[0-9]+)?$")))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='Experiment', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ BigDecimal.class });
			Method nil_method = null;

			if (nil_method_name != null && !nil_method_name.isEmpty())
				nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					nil_method.invoke(list);
				else
					method.invoke(list, new BigDecimal(val_name, MathContext.DECIMAL32));
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

	private static boolean set_enum_raw_data_flag(Experiment list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='Experiment', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='Experiment', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ Experiment.RawDataFlag.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					Experiment.RawDataFlag.Enum _enum = Experiment.RawDataFlag.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:Experiment method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= Experiment.RawDataFlag.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + Experiment.RawDataFlag.Enum.forInt(i));
						try {
							errw.write("class_name:Experiment method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= Experiment.RawDataFlag.Enum.table.lastInt(); i++)
								errw.write(" enum:" + Experiment.RawDataFlag.Enum.forInt(i) + "\n");
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

	private static boolean set_enum_sample_state(Experiment list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (val_name != null && val_name.startsWith("aniso"))
			val_name = "anisotropic";

		else if (val_name != null && (val_name.equals("solid") || val_name.contains("crystal")))
			val_name = "solid";

		else
			val_name = "isotropic";

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='Experiment', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='Experiment', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ Experiment.SampleState.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					Experiment.SampleState.Enum _enum = Experiment.SampleState.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:Experiment method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= Experiment.SampleState.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + Experiment.SampleState.Enum.forInt(i));
						try {
							errw.write("class_name:Experiment method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= Experiment.SampleState.Enum.table.lastInt(); i++)
								errw.write(" enum:" + Experiment.SampleState.Enum.forInt(i) + "\n");
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
