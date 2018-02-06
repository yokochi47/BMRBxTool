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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.io.FilenameUtils;
import org.xml.sax.SAXException;

public class PDBML {

	public static String extract_entity_id(File pdbml, String pdb_strand_id) throws IOException {

		StringBuffer sbuff = new StringBuffer();

		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setValidating(false);
			factory.setFeature("http://xml.org/sax/features/validation", false);

			SAXParser parser = factory.newSAXParser();

			PDBMLSAXHandlerEntityID handler = new PDBMLSAXHandlerEntityID(pdb_strand_id, sbuff);

			FileInputStream in = new FileInputStream(pdbml);

			if (FilenameUtils.getExtension(pdbml.getName()).equals("gz")) {
				GZIPInputStream gzin = new GZIPInputStream(in);
				parser.parse(gzin, handler);
			}

			else
				parser.parse(in, handler);

		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}

		return sbuff.toString();
	}

	public static String extract_seq_code(File pdbml, String pdb_strand_id) throws IOException {

		StringBuffer sbuff = new StringBuffer();

		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();

			PDBMLSAXHandlerSeqCode handler = new PDBMLSAXHandlerSeqCode(pdb_strand_id, sbuff);

			FileInputStream in = new FileInputStream(pdbml);

			if (FilenameUtils.getExtension(pdbml.getName()).equals("gz")) {
				GZIPInputStream gzin = new GZIPInputStream(in);
				parser.parse(gzin, handler);
			}

			else
				parser.parse(in, handler);

		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}

		return sbuff.toString();
	}

	public static void extract_mon_id(File pdbml, String entity_id, List<PB_char> pb_chars) throws IOException {

		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();

			PDBMLSAXHandlerMonID handler = new PDBMLSAXHandlerMonID(entity_id, pb_chars);

			FileInputStream in = new FileInputStream(pdbml);

			if (FilenameUtils.getExtension(pdbml.getName()).equals("gz")) {
				GZIPInputStream gzin = new GZIPInputStream(in);
				parser.parse(gzin, handler);
			}

			else
				parser.parse(in, handler);

		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}

	}

	public static String extract_exptl_method(File pdbml) throws IOException {

		StringBuffer sbuff = new StringBuffer();

		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();

			PDBMLSAXHandlerExptlMethod handler = new PDBMLSAXHandlerExptlMethod(sbuff);

			FileInputStream in = new FileInputStream(pdbml);

			if (FilenameUtils.getExtension(pdbml.getName()).equals("gz")) {
				GZIPInputStream gzin = new GZIPInputStream(in);
				parser.parse(gzin, handler);
			}

			else
				parser.parse(in, handler);

		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}

		return sbuff.toString();
	}

	public static String extract_nmr_refine_method(File pdbml) throws IOException {

		StringBuffer sbuff = new StringBuffer();

		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();

			PDBMLSAXHandlerNMRRefineMethod handler = new PDBMLSAXHandlerNMRRefineMethod(sbuff);

			FileInputStream in = new FileInputStream(pdbml);

			if (FilenameUtils.getExtension(pdbml.getName()).equals("gz")) {
				GZIPInputStream gzin = new GZIPInputStream(in);
				parser.parse(gzin, handler);
			}

			else
				parser.parse(in, handler);

		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}

		return sbuff.toString();
	}

	public static void extract_ls_values(File pdbml, PB_list rep_pb_list) throws IOException {

		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();

			PDBMLSAXHandlerLSValues handler = new PDBMLSAXHandlerLSValues(rep_pb_list);

			FileInputStream in = new FileInputStream(pdbml);

			if (FilenameUtils.getExtension(pdbml.getName()).equals("gz")) {
				GZIPInputStream gzin = new GZIPInputStream(in);
				parser.parse(gzin, handler);
			}

			else
				parser.parse(in, handler);

		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}

	}

}
