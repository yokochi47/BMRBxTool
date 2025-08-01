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
import java.util.*;
import java.util.logging.*;
import java.sql.*;

import org.apache.xmlbeans.*;
import org.pdbj.bmrbpub.schema.mmcifNmrStar.SampleType.*;

public class bmr_SampleType {

	private static final String table_name = "Sample";

	public static int write_xml(Connection conn_bmrb, Connection conn_tax, Connection conn_le, String entry_id, XmlOptions xml_opt, BufferedWriter buffw, FileWriter logw, FileWriter errw) {

		SampleType body = SampleType.Factory.newInstance();
		Sample[] list = new Sample[1];

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

				String query2 = new String("select * from \"" + table_name + "\" where \"Entry_ID\"='" + entry_id + "' order by (0 || \"ID\")::decimal");

				rset.close();

				rset = state.executeQuery(query2);

				int cols = rset.getMetaData().getColumnCount();
				int lines = 0;

				buffw.write("  <BMRBx:sampleCategory>\n");

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
							logw.write("category='Sample', duplicated content of the category was excluded.\n");
						} catch (IOException e) {
							e.printStackTrace();
						}

						continue;
					}

					list[0] = Sample.Factory.newInstance();

					if (!set_integer(list[0], "setAggregateSampleNumber", "setNilAggregateSampleNumber", false, rset.getString("Aggregate_sample_number"), logw))
						continue;
					if (!set_string(list[0], "setCrystalGrowApparatus", "setNilCrystalGrowApparatus", false, rset.getString("Crystal_grow_apparatus"), logw))
						continue;
					if (!set_string(list[0], "setCrystalGrowAtmosphere", "setNilCrystalGrowAtmosphere", false, rset.getString("Crystal_grow_atmosphere"), logw))
						continue;
					if (!set_string(list[0], "setCrystalGrowDetails", "setNilCrystalGrowDetails", false, rset.getString("Crystal_grow_details"), logw))
						continue;
					if (!set_string(list[0], "setCrystalGrowMethod", "setNilCrystalGrowMethod", false, rset.getString("Crystal_grow_method"), logw))
						continue;
					if (!set_integer(list[0], "setCrystalGrowMethodCitId", "", false, rset.getString("Crystal_grow_method_cit_ID"), logw))
						continue;
					if (!set_decimal(list[0], "setCrystalGrowPh", "setNilCrystalGrowPh", false, rset.getString("Crystal_grow_pH"), logw))
						continue;
					if (!set_string(list[0], "setCrystalGrowPhRange", "setNilCrystalGrowPhRange", false, rset.getString("Crystal_grow_pH_range"), logw))
						continue;
					if (!set_decimal(list[0], "setCrystalGrowPressure", "setNilCrystalGrowPressure", false, rset.getString("Crystal_grow_pressure"), logw))
						continue;
					if (!set_decimal(list[0], "setCrystalGrowPressureEsd", "setNilCrystalGrowPressureEsd", false, rset.getString("Crystal_grow_pressure_esd"), logw))
						continue;
					if (!set_string(list[0], "setCrystalGrowSeeding", "setNilCrystalGrowSeeding", false, rset.getString("Crystal_grow_seeding"), logw))
						continue;
					if (!set_integer(list[0], "setCrystalGrowSeedingCitId", "", false, rset.getString("Crystal_grow_seeding_cit_ID"), logw))
						continue;
					if (!set_decimal(list[0], "setCrystalGrowTemp", "setNilCrystalGrowTemp", false, rset.getString("Crystal_grow_temp"), logw))
						continue;
					if (!set_string(list[0], "setCrystalGrowTempDetails", "setNilCrystalGrowTempDetails", false, rset.getString("Crystal_grow_temp_details"), logw))
						continue;
					if (!set_decimal(list[0], "setCrystalGrowTempEsd", "setNilCrystalGrowTempEsd", false, rset.getString("Crystal_grow_temp_esd"), logw))
						continue;
					if (!set_decimal(list[0], "setCrystalGrowTime", "setNilCrystalGrowTime", false, rset.getString("Crystal_grow_time"), logw))
						continue;
					if (!set_string(list[0], "setDetails", "setNilDetails", false, rset.getString("Details"), logw))
						continue;
					if (!set_string(list[0], "setLyophilizationCryoProtectant", "setNilLyophilizationCryoProtectant", false, rset.getString("Lyophilization_cryo_protectant"), logw))
						continue;
					if (!set_string(list[0], "setName", "setNilName", false, rset.getString("Name"), logw))
						continue;
					if (!set_string(list[0], "setOrientedSamplePrepProtocol", "setNilOrientedSamplePrepProtocol", false, rset.getString("Oriented_sample_prep_protocol"), logw))
						continue;
					if (!set_string(list[0], "setPolycrystallizationProtocol", "setNilPolycrystallizationProtocol", false, rset.getString("Polycrystallization_protocol"), logw))
						continue;
					if (!set_date(list[0], "setPreparationDate", "setNilPreparationDate", false, rset.getString("Preparation_date") != null && !rset.getString("Preparation_date").equals(".") && !rset.getString("Preparation_date").equals("?") ? rset.getDate("Preparation_date") : null, logw))
						continue;
					if (!set_date(list[0], "setPreparationExpirationDate", "setNilPreparationExpirationDate", false, rset.getString("Preparation_expiration_date") != null && !rset.getString("Preparation_expiration_date").equals(".") && !rset.getString("Preparation_expiration_date").equals("?") ? rset.getDate("Preparation_expiration_date") : null, logw))
						continue;
					if (!set_string(list[0], "setSfCategory", "", true, rset.getString("Sf_category"), logw))
						continue;
					if (!set_string(list[0], "setSfFramecode", "", true, rset.getString("Sf_framecode"), logw))
						continue;
					if (!set_string(list[0], "setSingleCrystalProtocol", "setNilSingleCrystalProtocol", false, rset.getString("Single_crystal_protocol"), logw))
						continue;
					if (!set_string(list[0], "setSolventSystem", "setNilSolventSystem", false, rset.getString("Solvent_system"), logw))
						continue;
					if (!set_string(list[0], "setStorageProtocol", "setNilStorageProtocol", false, rset.getString("Storage_protocol"), logw))
						continue;
					if (!set_string(list[0], "setSubType", "setNilSubType", false, rset.getString("Sub_type"), logw))
						continue;
					if (!set_enum_type(list[0], "setType", "", true, rset.getString("Type"), conn_bmrb, entry_id, logw, errw))
						continue;
					if (!set_string(list[0], "setEntryId", "", true, rset.getString("Entry_ID"), logw))
						continue;
					if (!set_integer(list[0], "setId", "", true, rid[lines - 1], logw))
						continue;

					body.setSampleArray(list);

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

				buffw.write("  </BMRBx:sampleCategory>\n");

			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_SampleType.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} catch (IOException ex) {

			Logger lgr = Logger.getLogger(bmr_SampleType.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_SampleType.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		return list_len;
	}

	private static boolean set_string(Sample list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

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
				logw.write("item='" + method_name.substring(3) + "', category='Sample', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='Sample', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_integer(Sample list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml && (nil_method_name == null || nil_method_name.isEmpty()) && !method_name.equalsIgnoreCase("setSampleID") && !method_name.equalsIgnoreCase("setExperimentID") && !method_name.equalsIgnoreCase("setStudyID") && !method_name.equalsIgnoreCase("setAssemblyID") && (method_name.contains("Id") || method_name.contains("Number") || method_name.contains("Count")) && !method_name.contains("ListId") && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$")))
			val_name = "0";

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='Sample', value='" + _val_name + "' -> '" + val_name + "'\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || !val_name.matches("^[-+]?[0-9]+$"))
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='Sample', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_decimal(Sample list, String method_name, String nil_method_name, boolean required, String val_name, FileWriter logw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml && (val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?") || (!val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)?$") && !val_name.matches("^[-+]?([0-9]+(\\.[0-9]*)?|\\.[0-9]+)([eE][-+]?[0-9]+)?$"))) && (nil_method_name == null || nil_method_name.isEmpty())) {

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='Sample', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='Sample', value='" + _val_name + "' was empty, but not nillable.\n");
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

	private static boolean set_date(Sample list, String method_name, String nil_method_name, boolean required, java.sql.Date date, FileWriter logw) {

		boolean nil = false;

		if (date == null)
			nil = true;

		if (nil && (nil_method_name == null || nil_method_name.isEmpty())) {

			if (required) {

				try {
					logw.write("item='" + method_name.substring(3) + "', category='Sample', value='" + date + "' was empty, but not nillable.\n");
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

	private static boolean set_enum_type(Sample list, String method_name, String nil_method_name, boolean required, String val_name, Connection conn_bmrb, String entry_id, FileWriter logw, FileWriter errw) {

		boolean nil = false;

		String _val_name = val_name;

		if (bmr_Util_Main.remediate_xml) {

		if (val_name != null)
			val_name = bmr_Util_Sample.getType(val_name);

		if (val_name != null && val_name.equals("gel"))
			val_name = bmr_Util_Sample.getGelType(val_name, conn_bmrb, entry_id);

		if (!(val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && (_val_name == null || _val_name.isEmpty() || _val_name.equals(".") || _val_name.equals("?") || !val_name.equals(_val_name))) {

			try {
				logw.write("item='" + method_name.substring(3) + "', category='Sample', value='" + _val_name + "' -> '" + val_name + "'\n");
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
					logw.write("item='" + method_name.substring(3) + "', category='Sample', value='" + _val_name + "' was empty, but not nillable.\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}

			return true;
		}

		Class<?> _class = list.getClass();

		try {
			Method method = _class.getMethod(method_name, new Class[]{ Sample.Type.Enum.class });
			;//Method nil_method = null;

			;//if (nil_method_name != null && !nil_method_name.isEmpty())
				;//nil_method = _class.getMethod(nil_method_name);

			try {
				if (nil)
					;//nil_method.invoke(list);
				else {
					Sample.Type.Enum _enum = Sample.Type.Enum.forString(val_name);
					if (_enum != null)
						method.invoke(list, _enum);
					else {
						System.err.println("class_name:Sample method_name:" + method_name + " val_name:" + val_name);
						for (int i = 1; i <= Sample.Type.Enum.table.lastInt(); i++)
							System.err.println(" enum:" + Sample.Type.Enum.forInt(i));
						try {
							errw.write("class_name:Sample method_name:" + method_name + " val_name:" + val_name + "\n");
							for (int i = 1; i <= Sample.Type.Enum.table.lastInt(); i++)
								errw.write(" enum:" + Sample.Type.Enum.forInt(i) + "\n");
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
