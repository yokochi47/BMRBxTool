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

import java.util.logging.*;
import java.sql.*;

public class bmr_Util_EntityCompIndex {

	public static String get_polymer_seq_one_letter_code(PreparedStatement pstate) {

		StringBuffer polymer_seq_one_letter_code = new StringBuffer();

		try {

			ResultSet rset = pstate.executeQuery();

			while (rset.next()) {

				String comp_id = rset.getString("Comp_ID");

				if (comp_id != null && !comp_id.isEmpty())
					polymer_seq_one_letter_code.append(seq_one_letter_code.getCode(comp_id));

			}

			rset.close();

		}  catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_Util_EntityCompIndex.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		}

		return polymer_seq_one_letter_code.toString();
	}

}
