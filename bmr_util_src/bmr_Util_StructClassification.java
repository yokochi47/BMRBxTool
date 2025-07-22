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

public class bmr_Util_StructClassification {

	private String[][] sunidtbl = null;

	bmr_Util_StructClassification() {

		try {

			FileReader filer = new FileReader(bmr_Util_Main.xsd_dir_name + "struct_classification.sunid");
			BufferedReader bufferr = new BufferedReader(filer);
			bufferr.mark(4000000);

			int i = 0;
			String line = bufferr.readLine();

			while ((line = bufferr.readLine()) != null) {

				if (!line.matches(".*,.*"))
					continue;

				i++;
			}

			bufferr.reset();

			sunidtbl = new String[i][2];
			i = 0;
			line = bufferr.readLine();

			while ((line = bufferr.readLine()) != null) {

				if (!line.matches(".*,.*"))
					continue;

				String[] elem = line.split(",");

				sunidtbl[i] = elem;

				i++;
			}

			bufferr.close();
			filer.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getSunIDByKey(String val_name) {

		if (val_name == null || val_name.isEmpty())
			return null;

		val_name = val_name.toLowerCase();

		for (int i = 0; i < sunidtbl.length; i++) {

			if (sunidtbl[i][0].equals(val_name))
				return sunidtbl[i][1];

		}

		return null;

	}
}
