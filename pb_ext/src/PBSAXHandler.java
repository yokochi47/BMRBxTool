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

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class PBSAXHandler extends DefaultHandler {

	List<PBE_result> pbe_results = null;
	int table_count = 0;
	int tr_count = 0;

	StringBuffer ID = null, AA = null, PB = null;

	public PBSAXHandler(List<PBE_result> _pbe_results) {

		pbe_results = _pbe_results;

	}

	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) {

		if (qName.equals("table"))
			table_count++;

		else if (table_count != 2)
			return;

		else if (qName.equals("tr"))
			tr_count++;

		else if (qName.equals("td")) {

			switch (tr_count % 3) {
			case 1: // ID
				ID = new StringBuffer();
				break;
			case 2: // AA
				AA = new StringBuffer();
				break;
			case 0: // PB
				PB = new StringBuffer();
				break;
			}

		}

	}

	public void endElement(String namespaceURI, String localName, String qName) {

		if (table_count != 2)
			return;

		else if (qName.equals("tr")) {

			if (tr_count % 3 == 0 && ID != null && AA != null && PB != null) {

				PBE_result pbe_result = new PBE_result();

				pbe_result.ID = ID.toString().replaceAll("\\s+", "").replaceAll(System.lineSeparator(), "");
				pbe_result.AA = AA.toString().replaceAll("\\s+", "").replaceAll(System.lineSeparator(), "").toUpperCase();
				pbe_result.PB = PB.toString().replaceAll("\\s+", "").replaceAll(System.lineSeparator(), "").toLowerCase();

				if (pbe_result.ID.contains("#"))
					pbe_result.PDB_strand_ID = pbe_result.ID.split("#")[1].toUpperCase().charAt(0);

				pbe_result.Model_Num = 1;

				for (PBE_result elem : pbe_results) {

					if (elem.PDB_strand_ID == pbe_result.PDB_strand_ID)
						pbe_result.Model_Num++;

				}

				if (pbe_result.Model_Num > pbe_result.Model_Num_Max) {

					for (int i = 0; i < pbe_results.size(); i++) {

						PBE_result elem = pbe_results.get(i);

						if (pbe_result.Model_Num > elem.Model_Num_Max) {
							elem.Model_Num_Max = pbe_result.Model_Num;
							pbe_results.set(i, elem);
						}

					}

					pbe_result.Model_Num_Max = pbe_result.Model_Num;

				}

				pbe_result.valid = (!pbe_result.AA.isEmpty() && !pbe_result.PB.isEmpty()
									&& pbe_result.AA.length() == pbe_result.PB.length()
									&& pbe_result.PB.matches(".*[a-p].*"));

				pbe_results.add(pbe_result);

				ID = AA = PB = null;

			}

		}

	}

	public void characters(char[] chars, int offset, int length) {

		if (table_count != 2)
			return;

		switch (tr_count % 3) {
		case 1: // ID
			if (ID != null)
				ID.append(new String(chars, offset, length));
			break;
		case 2: // AA
			if (AA != null)
				AA.append(new String(chars, offset, length));
			break;
		case 0: // PB
			if (PB != null)
				PB.append(new String(chars, offset, length));
			break;
		}

	}

}
