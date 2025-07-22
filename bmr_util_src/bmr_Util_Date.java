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

import java.text.*;
import java.util.Calendar;
import java.util.TimeZone;

public class bmr_Util_Date {

	public static Calendar sqldate2calendar (java.sql.Date sql_date) {

		Calendar cal = null;
		java.util.Date util_date;

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		try {

			util_date = (java.util.Date) formatter.parse(sql_date.toString());
			cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			cal.setTime(util_date);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return cal;
	}
}
