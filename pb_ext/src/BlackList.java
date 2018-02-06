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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlackList {

	private File black_list = null;
	private List<String> pdbids = null;

	private boolean added = false;

	BlackList (String file_name) {

		pdbids = new ArrayList<String>();

		try {

			File black_list = new File(file_name);

			if (!black_list.exists())
				return;

			FileReader filer = new FileReader(black_list);
			BufferedReader bufferr = new BufferedReader(filer);

			String line;

			while ((line = bufferr.readLine()) != null)
				pdbids.add(line);

			bufferr.close();
			filer.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public boolean matched(String pdb_id) {
		return pdbids.contains(pdb_id);
	}

	public boolean add(String pdb_id) {

		if (pdbids.contains(pdb_id))
			return false;

		return (added = pdbids.add(pdb_id));
	}

	public void write() {

		if (black_list == null || !added)
			return;

		try {

			FileWriter filew = new FileWriter(black_list);

			for (String pdbid : pdbids)
				filew.write(pdbid + "\n");

			filew.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
