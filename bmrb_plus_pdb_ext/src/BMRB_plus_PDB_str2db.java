/*
   Copyright 2015 PDBj-BMRB, Institute for Protein Research, Osaka University

   Licensed under the PDBj-BMRB License (the "License");
   you may not use this file except in compliance with the License.
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

   Questions or comments to yokochi@protein.osaka-u.ac.jp

   This program is generated machinery using BMRxTool.
   Please edit BMRxTool directly to commit the change in the program.
 */

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import EDU.bmrb.starlibj.DataLoopNameListNode;
import EDU.bmrb.starlibj.LoopRowNode;
import EDU.bmrb.starlibj.LoopTableNode;
import EDU.bmrb.starlibj.ParseException;
import EDU.bmrb.starlibj.SaveFrameNode;
import EDU.bmrb.starlibj.StarNode;
import EDU.bmrb.starlibj.StarParser;
import EDU.bmrb.starlibj.StarValidity;
import EDU.bmrb.starlibj.VectorCheckType;

public class BMRB_plus_PDB_str2db {

	public static void link_db(Connection conn_bmrb, File src_dir) {

		PreparedStatement pstate = null;
		ResultSet rset = null;

		String entry_id = null;
		//		String pdb_id = null;

		StarParser parser = null;

		try {

			File[] file_list = src_dir.listFiles();

			for (File file : file_list) {

				String file_name = file.getName();

				if (!file_name.matches("merged_[0-9]+_[0-9][0-9a-z]{3}.str"))
					continue;

				String[] split_name = file_name.split("_");

				entry_id = split_name[1];
				//				pdb_id = split_name[2].substring(0, 4).toUpperCase(); 

				pstate = conn_bmrb.prepareStatement("select \"ID\" from \"Entry\" where \"ID\"='" + entry_id + "'");
				rset = pstate.executeQuery();

				while (rset.next()) {

					entry_id = rset.getString("ID");

					if (entry_id == null)
						break;

				}

				rset.close();
				pstate.close();

				if (entry_id == null)
					continue;

				//				System.out.println("entry_id: " + entry_id);
				//				System.out.println("pdb_id:   " + pdb_id);

				FileInputStream in = new FileInputStream(file);

				if (parser == null)
					parser = new StarParser(in);
				else
					parser.ReInit(in);

				parser.StarFileNodeParse(parser);
				StarNode node = parser.popResult();

				final String sf_category = ".Sf_category";
				String category, tag_name;

				category = "conformer_family_coord_set";
				tag_name = "_" + category.substring(0, 1).toUpperCase() + category.substring(1) + sf_category;

				parse_saveframe(conn_bmrb, entry_id, node, tag_name, category);

				category = "conformer_statistics";
				tag_name = "_Conformer_stat_list"+ sf_category;

				parse_saveframe(conn_bmrb, entry_id, node, tag_name, category);

				category = "constraint_statistics";
				tag_name = "_Constraint_stat_list" + sf_category;

				parse_saveframe(conn_bmrb, entry_id, node, tag_name, category);

				category = "general_distance_constraints";
				tag_name = "_Gen_dist_constraint_list" + sf_category;

				parse_saveframe(conn_bmrb, entry_id, node, tag_name, category);

				category = "org_constr_file_comment";
				tag_name = "_" + category.substring(0, 1).toUpperCase() + category.substring(1) + sf_category;

				parse_saveframe(conn_bmrb, entry_id, node, tag_name, category);

				category = "RDC_constraints";
				tag_name = "_RDC_constraint_list" + sf_category;

				parse_saveframe(conn_bmrb, entry_id, node, tag_name, category);

				category = "torsion_angle_constraints";
				tag_name = "_Torsion_angle_constraint_list" + sf_category;

				parse_saveframe(conn_bmrb, entry_id, node, tag_name, category);

				in.close();

				System.out.print(".");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void parse_saveframe(Connection conn_bmrb, String entry_id, StarNode node, String tag_name, String value) {

		try {

			VectorCheckType vec = node.searchForTypeByTagValue(Class.forName(StarValidity.pkgName() + ".DataItemNode"), tag_name, value);

			if (vec.size() == 0)
				return;

			StarNode tag = (StarNode) (vec.firstElement());
			SaveFrameNode sf = (SaveFrameNode) tag.getParent();

			if (sf.size() == 0)
				return;

			int data_item_size = 0;

			for (int i = 0; i < sf.size(); i++) {

				StarNode sf_child = sf.elementAt(i);

				if (sf_child.getClass().getSimpleName().equals("DataItemNode"))
					data_item_size++;

			}

			MyDataItem[] data_items = new MyDataItem[data_item_size];

			data_item_size = 0;

			for (int i = 0; i < sf.size(); i++) {

				StarNode sf_child = sf.elementAt(i);

				Class<?> _class = sf_child.getClass();

				String class_name = _class.getSimpleName();

				switch (class_name) {
				case "DataItemNode":

					Method get_label_method = _class.getMethod("getLabel", (Class<?>[]) null );
					Method get_val_method = _class.getMethod("getValue", (Class<?>[]) null );

					data_items[data_item_size++] = new MyDataItem((String) get_label_method.invoke(sf_child), (String) get_val_method.invoke(sf_child));

					break;

				case "DataLoopNode":

					Method get_names_method = _class.getMethod("getNames", (Class<?>[]) null );
					Method get_vals_method = _class.getMethod("getVals", (Class<?>[]) null );

					DataLoopNameListNode name_list = (DataLoopNameListNode) get_names_method.invoke(sf_child);
					LoopTableNode table = (LoopTableNode) get_vals_method.invoke(sf_child);

					parse_dataloop(conn_bmrb, entry_id, name_list, table);

					break;

				default:
					System.err.println(class_name + " is out of target.");
					System.exit(1);
				}

			}

			if (data_item_size > 0)
				insert_data_item(conn_bmrb, entry_id, data_items);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

	}

	private static void parse_dataloop(Connection conn_bmrb, String entry_id, DataLoopNameListNode name_list, LoopTableNode table) {

		int name_list_size = name_list.firstElement().size();
		int table_size = table.size();

		if (name_list_size == 0 || table_size == 0)
			return;

		MyDataItem[] data_items = new MyDataItem[name_list_size];

		for (int i = 0; i < name_list_size; i++)
			data_items[i] = new MyDataItem(name_list.firstElement().elementAt(i).getLabel());

		for (int j = 0; j < table_size; j++) {

			LoopRowNode row = table.elementAt(j);

			for (int i = 0; i < name_list_size; i++)
				data_items[i].value = row.elementAt(i).getValue();

			insert_data_item(conn_bmrb, entry_id, data_items);

		}

	}

	private static void insert_data_item(Connection conn_bmrb, String entry_id, MyDataItem[] data_items) {

		if (data_items == null || data_items.length == 0)
			return;

		String category_name = data_items[0].label.split("\\.")[0];

		if (category_name.isEmpty())
			return;

		for (MyDataItem data_item : data_items) {

			if (!data_item.label.split("\\.")[0].equals(category_name)) {

				System.err.println("Unmatched category name with " + category_name);
				System.exit(1);

			}

		}

		String table_name = category_name.substring(1);

		if (data_item_exists(conn_bmrb, entry_id, data_items, table_name))
			return;

		String sql = "insert into \"" + table_name + "\" (";

		for (MyDataItem data_item : data_items)
			sql += "\"" + data_item.label.split("\\.")[1] + "\", ";

		sql = sql.substring(0, sql.length() - 2) + ") values (";

		for (MyDataItem data_item : data_items) {

			String column_name = data_item.label.split("\\.")[1];

			if (column_name.equals("Entry_ID"))
				sql += "'" + entry_id + "', ";
			else
				sql += "'" + data_item.value.replaceAll("'", "''") + "', ";

		}

		sql = sql.substring(0, sql.length() - 2) + ")";

		try {

			Statement state = conn_bmrb.createStatement();
			state.executeUpdate(sql);
			state.close();

		} catch (SQLException e) {

			System.err.println(sql);

			e.printStackTrace();

			try {

				conn_bmrb.rollback();

			} catch (SQLException e2) {
				e2.printStackTrace();
			}

			System.exit(1);

		}

	}

	private static boolean data_item_exists(Connection conn_bmrb, String entry_id, MyDataItem[] data_items, String table_name) {

		String id = null;

		boolean id_exists = false, entry_id_exists = false;
		boolean data_item_exists = false;

		for (MyDataItem data_item : data_items) {

			String label = data_item.label.split("\\.")[1];

			if (label.equals("ID")) {

				id = data_item.value;
				id_exists = true;

			}

			if (label.equals("Entry_ID"))
				entry_id_exists = true;

			if (id_exists && entry_id_exists)
				break;

		}

		if (!entry_id_exists) {

			System.err.println("Not found \"Entry_ID\" label in data items");
			System.exit(1);

		}

		try {

			ResultSet rset;

			DatabaseMetaData meta = conn_bmrb.getMetaData();

			rset = meta.getTables(null, null, table_name, null);

			boolean table_exists = false;

			while (rset.next()) {

				if (table_name.equals(rset.getString("TABLE_NAME"))) {

					table_exists = true;

					break;
				}

			}

			rset.close();

			if (!table_exists) {

				System.err.println("Not found table matches with " + table_name);
				System.exit(1);

			}

			rset = meta.getColumns(null, null, table_name, null);

			id_exists = false; entry_id_exists = false;

			while (rset.next()) {

				if (!table_name.equals(rset.getString("TABLE_NAME")))
					continue;

				String column_name = rset.getString("COLUMN_NAME");

				if ("ID".equals(column_name))
					id_exists = true;

				if ("Entry_ID".equals(column_name))
					entry_id_exists = true;

				if (id_exists && entry_id_exists)
					break;
			}

			rset.close();

			if (!entry_id_exists) {

				System.err.println("Not found \"Entry_ID\" column in " + table_name);
				System.exit(1);

			}

			String sql;

			if (id_exists && id != null)
				sql = "select * from \"" + table_name + "\" where \"Entry_ID\"='" + entry_id + "' and \"ID\"='" + id + "'";
			else
				sql = "select * from \"" + table_name + "\" where \"Entry_ID\"='" + entry_id + "'";

			Statement state = conn_bmrb.createStatement();
			rset = state.executeQuery(sql);

			while (rset.next()) {

				if (id_exists && id != null && id.equals(rset.getString("ID"))) {
					data_item_exists = true;
					break;
				}

				else if (entry_id.equals(rset.getString("Entry_ID"))) {
					data_item_exists = true;
					break;
				}

			}

			rset.close();
			state.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return data_item_exists;
	}

}