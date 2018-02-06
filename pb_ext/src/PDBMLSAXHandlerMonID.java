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

public class PDBMLSAXHandlerMonID extends DefaultHandler {

	String entity_id = null, _entity_id_;
	List<PB_char> pb_chars = null;
	boolean pdbx_poly_seq_scheme = false;
	boolean pdb_ins_code = false;
	String mon_id, seq_id;

	public PDBMLSAXHandlerMonID(String _entity_id, List<PB_char> _pb_chars) {

		entity_id = _entity_id;
		pb_chars = _pb_chars;

	}

	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) {

		if (qName.equals("PDBx:pdbx_poly_seq_scheme")) {

			if (entity_id.equals(atts.getValue("entity_id"))) {

				pdbx_poly_seq_scheme = true;

				mon_id = atts.getValue("mon_id");
				seq_id = atts.getValue("seq_id");

				for (int i = 0; i < pb_chars.size(); i++) {

					PB_char elem = pb_chars.get(i);

					if (elem.PDB_residue_no.equals(seq_id)) {

						elem.PDB_residue_name = mon_id;
						pb_chars.set(i, elem);

					}

				}

			} else
				pdbx_poly_seq_scheme = false;

		}

		if (!pdbx_poly_seq_scheme)
			return;

		else if (qName.equals("PDBx:pdb_ins_code"))
			pdb_ins_code = true;

	}

	public void endElement(String namespaceURI, String localName, String qName) {

		if (!pdbx_poly_seq_scheme)
			return;

		else if (qName.equals("PDBx:pdbx_poly_seq_scheme"))
			pdbx_poly_seq_scheme = false;

		else if (qName.equals("PDBx:pdb_ins_code"))
			pdb_ins_code = false;

	}

	public void characters(char[] chars, int offset, int length) {

		if (!pdb_ins_code)
			return;

		String ins_code = new String(chars, offset, length);

		if (ins_code != null && !ins_code.isEmpty()) {

			for (int i = 0; i < pb_chars.size(); i++) {

				PB_char elem = pb_chars.get(i);

				if (elem.PDB_residue_no.equals(seq_id)) {

					elem.PDB_ins_code = ins_code.charAt(0);
					pb_chars.set(i, elem);

				}

			}

		}

	}

}