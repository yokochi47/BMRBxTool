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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

import EDU.bmrb.starlibj.DataLoopNameListNode;
import EDU.bmrb.starlibj.LoopRowNode;
import EDU.bmrb.starlibj.LoopTableNode;
import EDU.bmrb.starlibj.SaveFrameNode;
import EDU.bmrb.starlibj.StarNode;
import EDU.bmrb.starlibj.StarValidity;
import EDU.bmrb.starlibj.VectorCheckType;

public class BMRB_plus_PDB_str2csv {

	static MyCategoryList list;

	public BMRB_plus_PDB_str2csv() {

		list = new MyCategoryList();

	}

	public void parse_saveframe(Connection conn_bmrb, String entry_id, StarNode node, String tag_name, String value) {

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
				write_csv(conn_bmrb, entry_id, data_items);

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

	private void parse_dataloop(Connection conn_bmrb, String entry_id, DataLoopNameListNode name_list, LoopTableNode table) {

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

			write_csv(conn_bmrb, entry_id, data_items);

		}

	}

	private void write_csv(Connection conn_bmrb, String entry_id, MyDataItem[] data_items) {

		if (data_items == null || data_items.length == 0)
			return;

		String category_name = data_items[0].label.split("\\.")[0];

		if (category_name.isEmpty())
			return;

		String id = null;

		for (MyDataItem data_item : data_items) {

			if (data_item.label.split("\\.")[1].equals("ID"))
				id = data_item.value;

			if (!data_item.label.split("\\.")[0].equals(category_name)) {

				System.err.println("Unmatched category name with " + category_name);
				System.exit(1);

			}

		}

		String table_name = category_name.substring(1);

		if (!is_valid(conn_bmrb, entry_id, data_items, table_name, id))
			return;

		MyCategory category = list.get(table_name);

		if (category == null)
			return;

		File csv_file = new File(BMRB_plus_PDB_ext.csv_dir_name, table_name + ".csv");

		try {

			FileWriter filew = new FileWriter(csv_file, csv_file.exists());

			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < category.items.length; i++) {

				int data_item_pos = category.data_item_pos[i];

				if (data_item_pos != -1)
					sb.append(StringEscapeUtils.escapeCsv(category.items[i].equals("Entry_ID") ? entry_id : data_items[data_item_pos].value));

				if (i < category.items.length - 1)
					sb.append(",");

			}

			filew.write(sb.toString() + "\n");

			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unused")
	private void insert_data_item(Connection conn_bmrb, String entry_id, MyDataItem[] data_items) {

		if (data_items == null || data_items.length == 0)
			return;

		String category_name = data_items[0].label.split("\\.")[0];

		if (category_name.isEmpty())
			return;

		String id = null;

		for (MyDataItem data_item : data_items) {

			if (data_item.label.split("\\.")[1].equals("ID"))
				id = data_item.value;

			if (!data_item.label.split("\\.")[0].equals(category_name)) {

				System.err.println("Unmatched category name with " + category_name);
				System.exit(1);

			}

		}

		String table_name = category_name.substring(1);

		if (data_item_exists(conn_bmrb, entry_id, data_items, table_name, id))
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

	private boolean is_valid(Connection conn_bmrb, String entry_id, MyDataItem[] data_items, String table_name, String id) {

		MyCategory category = list.get(table_name);

		if (category != null) {

			if (category.data_item_len != data_items.length || category.data_item_hash != category.hash(data_items))
				category.bipartite(data_items);

			return true;
		}

		boolean entry_id_exists = false;

		for (MyDataItem data_item : data_items) {

			String label = data_item.label.split("\\.")[1];

			if (label.equals("Entry_ID")) {

				entry_id_exists = true;

				break;
			}

		}

		if (!entry_id_exists) {

			System.err.println("Not found \"Entry_ID\" label in data items");

			return false;
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

				return false;
			}

			category = new MyCategory(table_name);

			List<String> columns = new ArrayList<String>();

			rset = meta.getColumns(null, null, table_name, null);

			entry_id_exists = false;

			while (rset.next()) {

				if (!table_name.equals(rset.getString("TABLE_NAME")))
					continue;

				String column_name = rset.getString("COLUMN_NAME");

				if (column_name.equals("Entry_ID"))
					entry_id_exists = true;

				columns.add(column_name);

			}

			rset.close();

			if (!entry_id_exists) {

				System.err.println("Not found \"Entry_ID\" column in " + table_name);

				return false;
			}

			category.items = columns.toArray(new String[0]);
			category.bipartite(data_items);

			list.add(category);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return true;
	}

	private boolean data_item_exists(Connection conn_bmrb, String entry_id, MyDataItem[] data_items, String table_name, String id) {

		if (!is_valid(conn_bmrb, entry_id, data_items, table_name, id))
			return false;

		boolean data_item_exists = false;

		try {

			String sql;

			if (id != null)
				sql = "select * from \"" + table_name + "\" where \"Entry_ID\"='" + entry_id + "' and \"ID\"='" + id + "'";
			else
				sql = "select * from \"" + table_name + "\" where \"Entry_ID\"='" + entry_id + "'";

			Statement state = conn_bmrb.createStatement();
			ResultSet rset = state.executeQuery(sql);

			while (rset.next()) {

				if (id != null && id.equals(rset.getString("ID"))) {
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