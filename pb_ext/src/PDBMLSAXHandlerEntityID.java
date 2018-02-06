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

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class PDBMLSAXHandlerEntityID extends DefaultHandler {

	String pdb_strand_id = null;
	StringBuffer sbuff = null, _sbuff_ = null;
	boolean entity_poly = false;
	boolean pdbx_seq_one_letter_code = false;
	boolean pdbx_strand_id = false;

	public PDBMLSAXHandlerEntityID(String _pdb_strand_id, StringBuffer _sbuff) {

		pdb_strand_id = _pdb_strand_id;
		sbuff = _sbuff;

	}

	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) {

		if (qName.equals("PDBx:entity_poly")) {

			entity_poly = true;

			_sbuff_ = new StringBuffer();
			_sbuff_.append(atts.getValue("entity_id"));

		}

		if (!entity_poly)
			return;

		else if (qName.equals("PDBx:pdbx_strand_id"))
			pdbx_strand_id = true;

	}

	public void endElement(String namespaceURI, String localName, String qName) {

		if (qName.equals("PDBx:entity_poly"))
			entity_poly = false;

		if (!entity_poly)
			return;

		else if (qName.equals("PDBx:pdbx_strand_id"))
			pdbx_strand_id = false;

	}

	public void characters(char[] chars, int offset, int length) {

		if (!entity_poly)
			return;

		else if (pdbx_strand_id) {

			String strand_id = new String(chars, offset, length);

			if (strand_id.contains(pdb_strand_id))
				sbuff.append(_sbuff_.toString());

		}

	}

}