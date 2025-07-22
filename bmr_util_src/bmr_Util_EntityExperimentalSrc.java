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
import java.util.HashMap;
import java.util.Map;

public class bmr_Util_EntityExperimentalSrc {

	private String[][] taxidtbl = null;

	bmr_Util_EntityExperimentalSrc() {

		try {

			FileReader filer = new FileReader(bmr_Util_Main.xsd_dir_name + "entity_experimental_src.host_org_ncbi_taxonomy_id");
			BufferedReader bufferr = new BufferedReader(filer);
			bufferr.mark(400000);

			int i = 0;
			String line = null;

			while ((line = bufferr.readLine()) != null) {

				if (!line.matches(".*,.*,.*,.*"))
					continue;

				i++;
			}

			bufferr.reset();

			taxidtbl = new String[i][4];
			i = 0;

			while ((line = bufferr.readLine()) != null) {

				if (!line.matches(".*,.*,.*,.*"))
					continue;

				String[] elem = line.split(",");

				taxidtbl[i] = elem;

				i++;
			}

			bufferr.close();
			filer.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static final Map<String, String> map = new HashMap<String, String>() {

		private static final long serialVersionUID = 65L;

		{

			put("purified from the native source", "purified from the natural source");
			put("recombiant technology", "recombinant technology");
			put("na", "na");
			put("purified from natural source", "purified from the natural source");
			put("cell-free synthesis", "cell free synthesis");
			put("E. coli - cell free", "cell free synthesis");
			put("Purified from host after deliberate infection", "recombinant technology");
			put("cell free synthesis", "cell free synthesis");
			put("recombinant_technology", "recombinant technology");
			put("chemical", "chemical synthesis");
			put("Chemically synthetic peptides", "chemical synthesis");
			put("obtained from a vendor", "obtained from a vendor");
			put("obtained from vendor", "obtained from a vendor");
			put("Standard F-moc solid phase peptide synthesised", "chemical synthesis");
			put("chemical syntheses", "chemical synthesis");
			put("P. chrysogenum Q176 (ATCC 10002) was cultivated in minimal medium", "recombinant technology");
			put("purchased from commercial source", "obtained from a vendor");
			put("enzymatic semi-synthesis", "enzymatic semisynthesis");
			put("Purified from natural source and recombinant technology", "recombinant technology");
			put("chemical, enzymatic, and cell-free synthesis", "cell free synthesis");
			put("recombinant tecHology", "recombinant technology");
			put("over expression in E. Coli", "recombinant technology");
			put("recombinat technology", "recombinant technology");
			put("not reported", "na");
			put("vendor", "obtained from a vendor");
			put("cell-free", "cell free synthesis");
			put("Limited proteolysis", "enzymatic semisynthesis");
			put("enzymatic semisynthesis", "enzymatic semisynthesis");
			put("peptide synthesis", "chemical synthesis");
			put("Chemically synthesized", "chemical synthesis");
			put("chemically_synthesized", "chemical synthesis");
			put("chemically synthesis", "chemical synthesis");
			put("E. coli", "recombinant technology");
			put("chemical construct", "chemical synthesis");
			put("purified from the natural source", "purified from the natural source");
			put("chemically synthesized", "chemical synthesis");
			put("reverse transcriptase", "reverse transcriptase");
			put("prufified from the natural source", "purified from the natural source");
			put("recombinant technology", "recombinant technology");
			put("Cell-free synthesis", "cell free synthesis");
			put("in vitro transcription", "cell free synthesis");
			put("Cell free synthesis", "cell free synthesis");
			put("562", "na");
			put("solid phase synthesis", "chemical synthesis");
			put("from a vendor", "obtained from a vendor");
			put("recombinate technology", "recombinant technology");
			put("purified from the natural_source", "purified from the natural source");
			put("natural source", "purified from the natural source");
			put("not available", "na");
			put("purchased from vendor", "obtained from a vendor");
			put("organic synthesis and recombinant technology", "recombinant technology");
			put("obtained from a collaborator", "obtained from a collaborator");
			put("synthetic", "chemical synthesis");
			put("Subtilisin limited proteolysis", "enzymatic semisynthesis");
			put("Solid phase peptide synthesis", "chemical synthesis");
			put("enzymatic synthesis", "enzymatic semisynthesis");
			put("Chemical synthesis", "chemical synthesis");
			put("chemical synthesis", "chemical synthesis");
			put("Purified from the natural source", "purified from the natural source");

		}
	};

	public String getProductionMethod(String val_name) {
		return (String) map.get(val_name);
	}

	public String checkNCBITaxonomyID(String ncbi_taxonomy_id, String entry_id, String entity_id, String scientific_name, String production_method) {

		if (!(production_method == null || production_method.isEmpty() || production_method.equals(".") || production_method.equals("?")) && (production_method.contains("synthesis") || production_method.contains("obtained")))
			return "na";

		if (!(scientific_name == null || scientific_name.isEmpty() || scientific_name.equals(".") || scientific_name.equals("?"))) {

			if (scientific_name.equals("Pichia pastoris"))
				return "4922";

			else if (scientific_name.equals("E. coli - cell free"))
				return "562";

		}

		for (int i = 0; i < taxidtbl.length; i++) {

			if (taxidtbl[i][0].equals(ncbi_taxonomy_id) && taxidtbl[i][1].equals(entry_id) && taxidtbl[i][2].equals(entity_id))
				return taxidtbl[i][3];

			if (taxidtbl[i][0].isEmpty() && (ncbi_taxonomy_id == null || ncbi_taxonomy_id.isEmpty() || ncbi_taxonomy_id.equals(".") || ncbi_taxonomy_id.equals("?")) && taxidtbl[i][1].equals(entry_id) && taxidtbl[i][2].equals(entity_id))
				return taxidtbl[i][3];

		}

		if ((ncbi_taxonomy_id == null || ncbi_taxonomy_id.isEmpty() || ncbi_taxonomy_id.equals(".") || ncbi_taxonomy_id.equals("?")) && (scientific_name == null || scientific_name.isEmpty() || scientific_name.equals(".") || scientific_name.equals("?") || scientific_name.equalsIgnoreCase("not applicable") || scientific_name.equalsIgnoreCase("chemical synthesis") || scientific_name.equalsIgnoreCase("in vitro") || scientific_name.equalsIgnoreCase("in-vitro") || scientific_name.equalsIgnoreCase("in vitro transcription")))
			return "na";

		return ncbi_taxonomy_id;
	}
}
