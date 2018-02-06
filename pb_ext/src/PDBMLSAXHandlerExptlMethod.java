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

public class PDBMLSAXHandlerExptlMethod extends DefaultHandler {

	StringBuffer sbuff = null;
	boolean filled = false;

	public PDBMLSAXHandlerExptlMethod(StringBuffer _sbuff) {

		sbuff = _sbuff;

	}

	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) {

		if (qName.equals("PDBx:exptl")) {

			if (filled)
				sbuff.append(", ");

			sbuff.append(atts.getValue("method"));

			filled = true;

		}

	}

}