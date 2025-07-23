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

import java.util.HashMap;
import java.util.Map;

public class bmr_Util_Release {

	static final Map<String, String> map = new HashMap<String, String>() {

		private static final long serialVersionUID = 111L;

		{

			put("author", "original");
			put("reformat", "reformat");
			put("version_update", "update");
			put("revision", "revision");
			put("update", "update");
			put("addition of minor form", "update");
			put("orginal", "original");
			put("correction", "revision");
			put("original release", "original");
			put("original", "original");
			put("origninal", "original");
			put("udpate", "update");
			put("updated", "update");
			put("revised", "revision");
			put("upate", "update");
			put("oirginal", "original");
			put("not available", "not available");
			put("BMRB", "update");
			put("update shift table", "update");

		}
	};

	public static String getType(String val_name) {
		return (String) map.get(val_name);
	}
}
