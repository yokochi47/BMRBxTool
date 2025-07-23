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

public class bmr_Util_EntityNaturalSrc {

	private String[][] taxidtbl = null;

	bmr_Util_EntityNaturalSrc() {

		try {

			FileReader filer = new FileReader(bmr_Util_Main.xsd_dir_name + "entity_natural_src.ncbi_taxonomy_id");
			BufferedReader bufferr = new BufferedReader(filer);
			bufferr.mark(400000);

			int i = 0;
			String line = null;

			while ((line = bufferr.readLine()) != null) {

				if (!line.matches(".*,.*,.*"))
					continue;

				i++;
			}

			bufferr.reset();

			taxidtbl = new String[i][3];
			i = 0;

			while ((line = bufferr.readLine()) != null) {

				if (!line.matches(".*,.*,.*"))
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

	static final Map<String, String> map_type = new HashMap<String, String>() {

		private static final long serialVersionUID = 66L;

		{

			put("organsim", "organism");
			put("orgnanism", "organism");
			put("no natrual source", "no natural source");
			put("mussel", "organism");
			put("synthetic construct", "not applicable");
			put("venom", "organism");
			put("oragnism", "organism");
			put("synthesized", "not applicable");
			put("plant, red alga Griffithsia sp", "organism");
			put("multiple natural sources", "multiple natural sources");
			put("virus", "virus");
			put("bacteria", "organism");
			put("BACTERIA", "organism");
			put("organisms", "organism");
			put("solid phase peptide synthesis", "not applicable");
			put("fungus", "organism");
			put("designed peptide", "not applicable");
			put("Bacterium", "organism");
			put("toxin", "organism");
			put("Xylella fastidiosa", "organism");
			put("archeon", "organism");
			put("mouse", "organism");
			put("chemically synthesized", "not applicable");
			put("not applicable", "not applicable");
			put("Homo sapiens", "organism");
			put("Turtle egg shell", "organism");
			put("cellular organism", "organism");
			put("no natural source", "no natural source");
			put("plasmid", "plasmid");
			put("Staphylococcus aureus", "organism");
			put("synthetic peptide", "not applicable");
			put("synthetic", "not applicable");
			put("organism", "organism");
			put("Solid phase peptide synthesis", "not applicable");
			put("Virus", "virus");
			put("cosmid", "cosmid");
			put("Maize ( Zea mays) seeds", "organism");
			put("chemical sythesis", "not applicable");

		}
	};

	public String getType(String val_name) {
		return (String) map_type.get(val_name);
	}

	static final Map<String, String> map_organ = new HashMap<String, String>() {

		private static final long serialVersionUID = 67L;

		{

			put("endskeleton", "endskeleton");
			put("skeletal", "skeletal muscle");
			put("thymus", "thymus");
			put("skeletal muscle", "skeletal muscle");
			put("na", "na");
			put("Oral-mucosa Submandibular-sublingual", "oral mucosa submandibular-sublingual glands");
			put("seminal vesicles", "seminal vesicles");
			put("heart, skeletal", "cardiac skeletal muscle");
			put("musculus", "na");
			put("not specified", "na");
			put("testicle", "testicle");
			put("duodendum", "duodendum");
			put("head", "brain");
			put("brain", "brain");
			put("electric organ", "electric organ");
			put("heart", "heart");
			put("jejunum", "jejunum");
			put("blood", "blood");
			put("aciniform gland", "aciniform gland");
			put("skin", "skin");
			put("venomous gland", "venom gland");
			put("seeds", "seed");
			put("seed", "seed");
			put("kidney", "kidney");
			put("ubiquitous", "ubiquitous");
			put("ileum", "ileum");
			put("antennal hair", "antennae");
			put("breast, stomach", "breast");
			put("heamolymph", "heamolymph");
			put("oocyte", "oocyte");
			put("cardiac skeletal muscle", "cardiac skeletal muscle");
			put("small intestine", "small intestine");
			put("hepatopancreas", "hepatopancreas");
			put("vulva", "vulva");
			put("liver", "liver");
			put("eye", "eye");
			put("antennae", "antennae");
			put("intestine", "intestine");
			put("mucous epithelia and associated glands", "mucous epithelium");
			put("lung", "lung");
			put("venom gland", "venom gland");
			put("placenta", "placenta");
			put("fruit", "fruit");
			put("breast", "breast");
			put("telson", "telson");
			put("oral mucosa submandibular-sublingual glands", "oral mucosa submandibular-sublingual glands");
			put("venom duct", "venom gland");
			put("mucous epithelium", "mucous epithelium");
			put("egg white", "egg white");
			put("leaf", "leaf");
			put("pancreas", "pancreas");
			put("aorta", "aorta");
			put("Breast, Stomach", "breast");
			put("Antenne", "antennae");
			put("salivary glands", "salivary glands");
			put("mid-small intestine (jejunum)", "jejunum");

		}
	};

	public String getOrgan(String val_name) {
		return (String) map_organ.get(val_name);
	}

	static final Map<String, String> map_organelle = new HashMap<String, String>() {

		private static final long serialVersionUID = 68L;

		{

			put("mitosome", "mitosome");
			put("P-pilli", "na");
			put("acrosome", "acrosome");
			put("nucleolus", "nucleolus");
			put("cytoskeleton", "cytoskeleton");
			put("parenthesome", "parenthesome");
			put("RIBOSOME,60S SUBUNIT", "ribosome");
			put("glycosome", "glycosome");
			put("na", "na");
			put("proteasome", "proteasome");
			put("ribosome", "ribosome");
			put("vacuole", "vacuole");
			put("hydrogenosome", "hydrogenosome");
			put("nucleosome", "nucleosome");
			put("endomembrane system", "endomembrane system");
			put("cilium", "cilium");
			put("vesicle", "vesicle");
			put("Golgi apparatus", "golgi apparatus");
			put("chroloplast", "chloroplast");
			put("autophagosome", "autophagosome");
			put("sarcomere", "myofibril");
			put("eyespot apparatus", "eyespot apparatus");
			put("nucleomorph", "chloroplast");
			put("peroxisome", "peroxisome");
			put("lysosome", "lysosome");
			put("melanosome", "melanosome");
			put("rod", "cytosol");
			put("c-Myc and Max are nuclear proteins", "nucleus");
			put("glyoxysome", "glyoxysome");
			put("outer membrane", "cell membrane");
			put("mitochondria", "mitochondria");
			put("myofibril", "myofibril");
			put("chloroplast", "chloroplast");
			put("lung", "cytosol");
			put("nucleus", "nucleus");
			put("cytosol", "cytosol");
			put("Chloroplast", "chloroplast");
			put("cell membrane", "cell membrane");
			put("endoplasmic reticulum", "endoplasmic reticulum");
			put("golgi apparatus", "golgi apparatus");
			put("mitochondrion", "mitochondria");
			put("extracellular", "na");
			put("centriole", "centriole");
			put("microtubule", "microtubule");

		}
	};

	public String getOrganelle(String val_name) {
		return (String) map_organelle.get(val_name);
	}

	static final Map<String, String> map_secretion = new HashMap<String, String>() {

		private static final long serialVersionUID = 69L;

		{

			put("extracellular", "extracellular");
			put("CYTOPLASM", "na");
			put("Paneth cells", "na");
			put("venom", "venom");
			put("Saliva", "saliva");
			put("pancreas", "na");
			put("vaginal fluid", "vaginal fluid");
			put("saliva", "saliva");
			put("hen egg white", "hen egg white");
			put("sensillum lymph", "sensillum lymph");
			put("cytoplasm", "na");
			put("semen", "semen");
			put("na", "na");
			put("cement", "cement");
			put("venomous gland", "venom");
			put("dermal", "dermal");
			put("urine", "urine");
			put("seminal plasma", "semen");
			put("blood", "blood");
			put("venom component", "venom");
			put("salivary", "saliva");
			put("Paneth cell", "na");
			put("milk", "milk");
			put("snake venom", "venom");
			put("venom gland", "venom");
			put("Cement", "cement");

		}
	};

	public String getSecretion(String val_name) {
		return (String) map_secretion.get(val_name);
	}

	static final Map<String, String> map_common = new HashMap<String, String>() {

		private static final long serialVersionUID = 70L;

		{

			put("Man", "null");
			put("E. coli", "null");
			put("yes", "yes");
			put("no", "no");

		}
	};

	public String getCommon(String val_name) {

		val_name = map_common.get(val_name);

		if (val_name != null && val_name.equalsIgnoreCase("null"))
			return null;

		return val_name;
	}

	public String checkNCBITaxonomyID(String ncbi_taxonomy_id, String entry_id, String scientific_name) {

		for (int i = 0; i < taxidtbl.length; i++) {

			if (taxidtbl[i][0].equals(ncbi_taxonomy_id) && taxidtbl[i][1].equals(entry_id))
				return taxidtbl[i][2];

			if (taxidtbl[i][0].isEmpty() && (ncbi_taxonomy_id == null || ncbi_taxonomy_id.isEmpty() || ncbi_taxonomy_id.equals(".") || ncbi_taxonomy_id.equals("?")) && taxidtbl[i][1].equals(entry_id))
				return taxidtbl[i][2];

		}

		if ((ncbi_taxonomy_id == null || ncbi_taxonomy_id.isEmpty() || ncbi_taxonomy_id.equals(".") || ncbi_taxonomy_id.equals("?")) && (scientific_name == null || scientific_name.isEmpty() || scientific_name.equals(".") || scientific_name.equals("?") || scientific_name.equals("not applicable")))
			return "na";

		return ncbi_taxonomy_id;
	}
}
