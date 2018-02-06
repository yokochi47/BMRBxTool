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

import java.io.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class PBE_query {

	public final static String pbe_url = "http://www.bo-protscience.fr/cgi-bin/PBE/java.cgi";

	private static String pdb_id = "";
	private static String pdb_file = "";
	private static String pb_file = "";
	private static String black_list = "";

	public static void main(String[] args) {

		for (int i = 0; i < args.length; i++) {

			if (args[i].equals("--pdb-id"))
				pdb_id = args[++i].toUpperCase();

			else if (args[i].equals("--pdb-file"))
				pdb_file = args[++i];

			else if (args[i].equals("--pb-file"))
				pb_file = args[++i];

			else if (args[i].equals("--black-list"))
				black_list = args[++i];

			else {
				System.out.println("Usage: --pdb-id PDB_ID --pdb-file PDB --pb-file PB");
				System.out.println(" --black-list LIST : Failed PDB entries");
				System.exit(1);
			}

		}

		if (pdb_file.isEmpty()) {
			System.err.println("No PDB file was selected.");
			System.exit(1);
		}

		if (pb_file.isEmpty()) {
			System.err.println("No PB file was selected.");
			System.exit(1);
		}

		File pdb = new File(pdb_file);

		if (!pdb.exists()) {
			System.err.println("Couldn't read '" + pdb_file + "'.");
			System.exit(1);
		}

		if (!black_list.isEmpty()) {

			BlackList black = new BlackList(black_list);

			if (black.matched(pdb_id))
				System.exit(2);

		}

		try {

			FileWriter pbw = new FileWriter(pb_file);

			CloseableHttpClient client = HttpClients.createDefault();

			HttpPost post = new HttpPost(pbe_url);

			FileBody file_body = new FileBody(pdb);

			HttpEntity post_entity = MultipartEntityBuilder.create()
					.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
					.addPart("pdbfile", file_body)
					.build();

			post.setEntity(post_entity);

			CloseableHttpResponse res = client.execute(post);

			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				HttpEntity res_entity = res.getEntity();
				InputStream in = res_entity.getContent();

				BufferedReader buffr = new BufferedReader(new InputStreamReader(in));

				String line = null;

				while ((line = buffr.readLine()) != null)
					pbw.write(line + "\n");

				buffr.close();
				in.close();

			}

			res.close();
			client.close();
			pbw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}