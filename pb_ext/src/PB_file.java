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
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class PB_file {

	public static void parse(Connection conn_bmrb, String entry_id, String pdb_id, File pdb, File pdbml, File pb, File str, WhiteList white, BlackList black) throws IOException {

		FileReader filer = new FileReader(pb);
		BufferedReader buffr = new BufferedReader(filer);
		StringBuilder builder = new StringBuilder();

		String line = null;

		while ((line = buffr.readLine()) != null)
			builder.append(line.replaceFirst("<HTML>", "<html>")
					.replaceAll("<b>", "")
					.replaceAll("<\\/b>", "")
					.replaceAll("<tr><td  class=\"tab3\"><\\/td><td  class=\"tab3\"><\\/td><\\/tr>", "")
					.replaceAll("<[bh]r>", "")
					.replaceFirst("<tr><td> <form", "<td> <form")
					.replaceFirst("type=submit", "type=\"submit\"")
					.replaceFirst("name=\"ID\" ><\\/form>", "name=\"ID\" \\/><\\/form>")
					.replaceFirst("<\\/span><\\/a>", "<\\/a>")).append("\n");

		buffr.close();

		byte[] bytes = builder.toString().getBytes();

		InputStream input = new ByteArrayInputStream(bytes);

		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();

			List<PBE_result> pbe_results = new ArrayList<PBE_result>();

			pbe_results.clear();

			PBSAXHandler handler = new PBSAXHandler(pbe_results);

			parser.parse(input, handler);

			PreparedStatement pstate = conn_bmrb.prepareStatement("select * from \"Entity_assembly\" where \"Entry_ID\" = ?");
			pstate.setString(1, entry_id);

			PreparedStatement pstate2 = conn_bmrb.prepareStatement("select \"Comp_ID\" from \"Entity_comp_index\" where \"Entry_ID\"= ? and \"Entity_ID\"= ? order by \"ID\"::integer");
			pstate2.setString(1, entry_id);

			for (int i = 0; i < pbe_results.size(); i++) {

				PBE_result elem = pbe_results.get(i);

				if (!elem.valid)
					continue;

				elem.align(pstate, pstate2, entry_id, pdb_id, pdbml);

				pbe_results.set(i, elem);

			}

			pstate.close();
			pstate2.close();

			if (pbe_results.size() > 0)
				STR_file.write(conn_bmrb, entry_id, pdb_id, pdb, pdbml, pb, str, white, pbe_results);
			else if (black != null)
				black.add(pdb_id);

			pbe_results.clear();

		} catch (ParserConfigurationException | SAXException e) {
			System.err.println(pb.getName());
			e.printStackTrace();
		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(PB_file.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		}

	}

}
