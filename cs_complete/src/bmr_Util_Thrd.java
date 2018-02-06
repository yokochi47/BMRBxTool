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

import java.sql.*;
import java.util.logging.*;

public class bmr_Util_Thrd implements Runnable {

	private String _str_dir_name = null;
	private String _loc_dir_name = null;

	private Connection conn_bmrb = null;

	private static int _thrd_id;

	private static final String url_bmrb = bmr_Util_Main.url_bmrb;
	private static final String user_bmrb = bmr_Util_Main.user_bmrb.isEmpty() ? bmr_Util_Main.user : bmr_Util_Main.user_bmrb;
	private static final String pass_bmrb = bmr_Util_Main.pass_bmrb;

	public bmr_Util_Thrd(String str_dir_name, String loc_dir_name, int thrd_id) {

		_str_dir_name = str_dir_name;
		_loc_dir_name = loc_dir_name;
		_thrd_id = thrd_id;

	}

	@Override
	public void run() {

		try {

			conn_bmrb = DriverManager.getConnection(url_bmrb, user_bmrb, pass_bmrb);

			while (true) {

				String entry_id = bmr_Util_Serv.get_entry_id();

				if (entry_id == null)
					break;

				bmr_Util_Proc.exec(conn_bmrb, _str_dir_name, _loc_dir_name, _thrd_id, entry_id);

			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_Util_Thrd.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (conn_bmrb != null)
					conn_bmrb.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_Util_Thrd.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}
	}
}
