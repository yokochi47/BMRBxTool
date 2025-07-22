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

import org.xml.sax.*;
import org.apache.xerces.parsers.*;

public class bmr_Util_Valid {

	DOMParser dom_parser = null;

	String _dir_name = null;

	public bmr_Util_Valid(String dir_name) {

		dom_parser = new DOMParser();

		_dir_name = dir_name;

		if (!bmr_Util_Main.well_formed) {

			try {

				dom_parser.setFeature("http://xml.org/sax/features/validation", true);
				dom_parser.setFeature("http://apache.org/xml/features/validation/schema", true);
				dom_parser.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true);
				dom_parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
				dom_parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", new File(dir_name + "mmcif_nmr-star.xsd"));

			} catch (SAXNotRecognizedException e) {
				e.printStackTrace();
				System.exit(1);
			} catch (SAXNotSupportedException e) {
				e.printStackTrace();
				System.exit(1);
			}

		}

	}

	synchronized public void exec(String xml_base_name, FileWriter errw) {

		ErrHandler err_handler = new ErrHandler();

		err_handler.init(errw);

		dom_parser.setErrorHandler(err_handler);

		try {

			dom_parser.parse(_dir_name + xml_base_name);

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			return;
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		if (err_handler.success)
			System.out.println(xml_base_name + " is valid.");

		dom_parser.reset();

	}

	private class ErrHandler implements ErrorHandler {

		public boolean success = true;
		public FileWriter errw = null;

		public void init(FileWriter errw) {
			this.errw = errw;
		}

		@Override
		public void error(SAXParseException e) throws SAXException {

			String message = e.getMessage();

			if (message.contains("Duplicate key value"))
				return;

			if (message.contains("datablock"))
				return;

			if (message.contains("Date") || message.contains("date"))
				return;

			success = false;

			System.err.println("Error: at " + e.getLineNumber());
			System.err.println(e.getMessage());

			try {
				errw.write("Error: at " + e.getLineNumber() + "\n");
				errw.write(e.getMessage() + "\n");
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}

		@Override
		public void fatalError(SAXParseException e) throws SAXException {

			success = false;

			System.err.println("Fatal Error: at " + e.getLineNumber());
			System.err.println(e.getMessage());

			try {
				errw.write("Fatal Error: at " + e.getLineNumber() + "\n");
				errw.write(e.getMessage() + "\n");
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}

		@Override
		public void warning(SAXParseException e) throws SAXException {

			success = false;

			System.out.println("Warning: at " + e.getLineNumber());
			System.out.println(e.getMessage());

			try {
				errw.write("Warning: at " + e.getLineNumber() + "\n");
				errw.write(e.getMessage() + "\n");
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}
	}
}
