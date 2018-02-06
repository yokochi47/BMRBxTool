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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class WhiteList {

	private String[][] id_pairs = null;

	WhiteList (String file_name) {

		try {

			FileReader filer = new FileReader(file_name);
			BufferedReader bufferr = new BufferedReader(filer);
			bufferr.mark(1000000);

			int i = 0;
			String line = null;

			while ((line = bufferr.readLine()) != null) {

				if (!line.matches(".*,.*"))
					continue;

				i++;

			}

			bufferr.reset();

			id_pairs = new String[i][2];
			i = 0;

			while ((line = bufferr.readLine()) != null) {

				if (!line.matches(".*,.*"))
					continue;

				String[] elem = line.split(",");

				id_pairs[i++] = elem;

			}

			bufferr.close();
			filer.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public boolean matched(String entry_id, String pdb_id) {

		for (int i = 0; i < id_pairs.length; i++) {

			if (id_pairs[i][0].equals(entry_id) && id_pairs[i][1].equals(pdb_id))
				return true;

		}

		return false;
	}

}
