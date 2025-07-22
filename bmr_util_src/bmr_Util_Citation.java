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
import java.net.*;

import java.security.cert.*;
import java.security.*;
import java.sql.*;
import java.util.logging.*;

import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.*;
import javax.xml.parsers.*;

//import org.apache.xml.serialize.*;
import org.w3c.dom.*;
import org.w3c.dom.ls.*;
import org.xml.sax.*;

import com.fasterxml.jackson.databind.ObjectMapper;

public class bmr_Util_Citation {

	static final Map<String, String> map_class = new HashMap<String, String>() {

		private static final long serialVersionUID = 36L;

		{

			put("rference citation", "reference citation");
			put("entry citation", "entry citation");
			put("reference citation", "reference citation");
			put("entry_citation", "entry citation");

		}
	};

	public static String getClass1(String val_name) {
		return (String) map_class.get(val_name);
	}

	static final Map<String, String> map_type = new HashMap<String, String>() {

		private static final long serialVersionUID = 37L;

		{

			put("book chapter", "book");
			put("journal", "journal");
			put("BMRB_only", "BMRB only");
			put("book", "book");
			put("publication other", "other publication");
			put("thesis", "thesis");
			put("internet", "internet");
			put("BMRB only", "BMRB only");
			put("abstract", "abstract");
			put("Journal", "journal");
			put("personal communication", "personal communication");
			put("other publication", "other publication");

		}
	};

	public static String getType(String val_name) {
		return (String) map_type.get(val_name);
	}

	static final Map<String, String> map_status = new HashMap<String, String>() {

		private static final long serialVersionUID = 38L;

		{

			put("other publication status", "na");
			put("unknown", "na");
			put("publshed", "published");
			put("in press", "in press");
			put("in preparation", "in preparation");
			put("na", "na");
			put("accepted", "in press");
			put("pbulished", "published");
			put("retracted", "retracted");
			put("published", "published");
			put("submitted", "submitted");
			put("Published", "published");
			put("to be published", "published");

		}
	};

	public static String getStatus(String val_name) {
		return (String) map_status.get(val_name);
	}

	static final Map<String, String> map_journal_abbrev = new HashMap<String, String>() {

		private static final long serialVersionUID = 39L;

		{

			put("Chem. Biol. Drug Des.", "Chem. Biol. Drug Des.");
			put("Dev. Comp. Immunol.", "Dev. Comp. Immunol.");
			put("Vaccine", "Vaccine");
			put("Toxicon.", "Toxicon.");
			put("Med. Sci. Sports Exerc.", "Med. Sci. Sports Exerc.");
			put("Molecules", "Molecules");
			put("Blood", "Blood");
			put("Chem. Senses", "Chem. Senses");
			put("Plant Physiol.", "Plant Physiol.");
			put("J. Exp. Med.", "J. Exp. Med.");
			put("J. Biochem.", "J. Biochem.");
			put("Fertil. Steril.", "Fertil. Steril.");
			put("Acc Chem. Res.", "Acc Chem. Res.");
			put("Biochem. Cell Biol.", "Biochem. Cell Biol.");
			put("Sheng Wu Hua Xue Yu Sheng Wu Wu Li Xue Bao. (Shanghai).", "Sheng Wu Hua Xue Yu Sheng Wu Wu Li Xue Bao. (Shanghai).");
			put("J. Inorg. Biochem.", "J. Inorg. Biochem.");
			put("Cell", "Cell");
			put("Int. J. Biol. Macromol.", "Int. J. Biol. Macromol.");
			put("Cell. Microbiol.", "Cell. Microbiol.");
			put("BMC Evol. Biol.", "BMC Evol. Biol.");
			put("PLoS Negl. Trop. Dis.", "PLoS Negl. Trop. Dis.");
			put("Epigenetics. Chromatin", "Epigenetics. Chromatin");
			put("Toxins (Basel).", "Toxins (Basel).");
			put("J. Phys. Chem. B", "J. Phys. Chem. B");
			put("J. Virol.", "J. Virol.");
			put("Chem. Biodivers.", "Chem. Biodivers.");
			put("Inorg. Chem.", "Inorg. Chem.");
			put("Int. J. Biochem. Cell Biol.", "Int. J. Biochem. Cell Biol.");
			put("Biochem. Int.", "Biochem. Int.");
			put("PLoS One", "PLoS One");
			put("Glycoconj. J.", "Glycoconj. J.");
			put("Curr. Opin. Struct. Biol.", "Curr. Opin. Struct. Biol.");
			put("Acta Crystallogr. D. Biol. Crystallogr.", "Acta Crystallogr. D. Biol. Crystallogr.");
			put("Methods. Mol. Biol.", "Methods. Mol. Biol.");
			put("Annu. Rev. Neurosci.", "Annu. Rev. Neurosci.");
			put("Carcinogenesis", "Carcinogenesis");
			put("Growth Factors", "Growth Factors");
			put("Chembiochem.", "Chembiochem.");
			put("J. Control. Release.", "J. Control. Release.");
			put("J. Pharm. Biomed. Anal.", "J. Pharm. Biomed. Anal.");
			put("Am. J. Ophthalmol.", "Am. J. Ophthalmol.");
			put("J. Nat. Prod.", "J. Nat. Prod.");
			put("Bioconjug. Chem.", "Bioconjug. Chem.");
			put("J. Protein Chem.", "J. Protein Chem.");
			put("BMC Biol.", "BMC Biol.");
			put("Lancet", "Lancet");
			put("J. Biomol. NMR", "J. Biomol. NMR");
			put("Spectrochim. Acta. A. Mol. Biomol. Spectrosc.", "Spectrochim. Acta. A. Mol. Biomol. Spectrosc.");
			put("Cell Biochem. Biophys.", "Cell Biochem. Biophys.");
			put("Pharmacol. Ther.", "Pharmacol. Ther.");
			put("Methods", "Methods");
			put("J. Biol. Chem.", "J. Biol. Chem.");
			put("Org. Biomol. Chem.", "Org. Biomol. Chem.");
			put("Chem. Biol.", "Chem. Biol.");
			put("Genome Res.", "Genome Res.");
			put("Biotechnology. (N Y).", "Biotechnology. (N Y).");
			put("Protein Eng.", "Protein Eng.");
			put("Sci. China. B.", "Sci. China. B.");
			put("Exp. Eye Res.", "Exp. Eye Res.");
			put("J. Neurosci.", "J. Neurosci.");
			put("Curr. Biol.", "Curr. Biol.");
			put("J. Chromatogr. B. Biomed. Sci. Appl.", "J. Chromatogr. B. Biomed. Sci. Appl.");
			put("Extremophiles.", "Extremophiles.");
			put("Mol. Cell. Neurosci.", "Mol. Cell. Neurosci.");
			put("Biopolymers", "Biopolymers");
			put("J. Comb. Chem.", "J. Comb. Chem.");
			put("Amino Acids", "Amino Acids");
			put("DNA Repair (Amst).", "DNA Repair (Amst).");
			put("FEBS J.", "FEBS J.");
			put("Open Spectrosc. J.", "Open Spectrosc. J.");
			put("Biosci. Rep.", "Biosci. Rep.");
			put("Regul. Pept.", "Regul. Pept.");
			put("J. Biomed. Sci.", "J. Biomed. Sci.");
			put("J. R. Soc. Interface.", "J. R. Soc. Interface.");
			put("J. Antibiot. (Tokyo).", "J. Antibiot. (Tokyo).");
			put("Methods. Enzymol.", "Methods. Enzymol.");
			put("FEBS Open Bio", "FEBS Open Bio");
			put("BMB Rep.", "BMB Rep.");
			put("Open Biol.", "Open Biol.");
			put("Oncogene", "Oncogene");
			put("Antisense Res. Dev.", "Antisense Res. Dev.");
			put("PLoS Biol.", "PLoS Biol.");
			put("Mol. Biol (Mosk).", "Mol. Biol (Mosk).");
			put("BMC Biophys.", "BMC Biophys.");
			put("Adv. Protein Chem.", "Adv. Protein Chem.");
			put("Peptides", "Peptides");
			put("unknown at present", "Not known");
			put("Immunity", "Immunity");
			put("Acta Biochim. Biophys. Sin. (Shanghai).", "Acta Biochim. Biophys. Sin. (Shanghai).");
			put("Biochimie", "Biochimie");
			put("Protein Pept. Lett.", "Protein Pept. Lett.");
			put("Int. J. Oncol.", "Int. J. Oncol.");
			put("Mol. Metab.", "Mol. Metab.");
			put("BMC Microbiol.", "BMC Microbiol.");
			put("J. Struct. Funct. Genomics", "J. Struct. Funct. Genomics");
			put("Nat. Genet.", "Nat. Genet.");
			put("Chem. Res. Toxicol.", "Chem. Res. Toxicol.");
			put("Biophys. J.", "Biophys. J.");
			put("Mol. Cell. Biol.", "Mol. Cell. Biol.");
			put("DNA Res.", "DNA Res.");
			put("Metallomics.", "Metallomics.");
			put("Viruses", "Viruses");
			put("J. Am. Chem. Soc.", "J. Am. Chem. Soc.");
			put("Antiviral Res.", "Antiviral Res.");
			put("Silence", "Silence");
			put("ChemMedChem", "ChemMedChem");
			put("Sci. Signal.", "Sci. Signal.");
			put("Growth. Horm. IGF Res.", "Growth. Horm. IGF Res.");
			put("PLoS Pathog.", "PLoS Pathog.");
			put("Biochemistry", "Biochemistry");
			put("J. Cell. Physiol.", "J. Cell. Physiol.");
			put("Genes. Chromosomes. Cancer", "Genes. Chromosomes. Cancer");
			put("Biotechniques", "Biotechniques");
			put("Plant J.", "Plant J.");
			put("J. Med. Chem.", "J. Med. Chem.");
			put("Philos. Trans. R. Soc. Lond. B. Biol. Sci.", "Philos. Trans. R. Soc. Lond. B. Biol. Sci.");
			put("TO BE PUBLISHED", "Not known");
			put("BMC Struct. Biol.", "BMC Struct. Biol.");
			put("J. Comput. Chem.", "J. Comput. Chem.");
			put("Proteomics", "Proteomics");
			put("J. Mol. Graph.", "J. Mol. Graph.");
			put("EMBO J.", "EMBO J.");
			put("Antimicrob. Agents Chemother.", "Antimicrob. Agents Chemother.");
			put("BMC Bioinformatics", "BMC Bioinformatics");
			put("J. Biomol. Struct. Dyn.", "J. Biomol. Struct. Dyn.");
			put("Hepatology", "Hepatology");
			put("To be Published", "Not known");
			put("Int. J. Pept. Protein Res.", "Int. J. Pept. Protein Res.");
			put("Acta Crystallogr. Sect. F. Struct. Biol. Cryst. Commun.", "Acta Crystallogr. Sect. F. Struct. Biol. Cryst. Commun.");
			put("J. Biotechnol.", "J. Biotechnol.");
			put("FASEB J.", "FASEB J.");
			put("Arch. Biochem. Biophys.", "Arch. Biochem. Biophys.");
			put("Biochem. Soc. Trans.", "Biochem. Soc. Trans.");
			put("Sci. Rep.", "Sci. Rep.");
			put("Mol. Plant. Microbe Interact.", "Mol. Plant. Microbe Interact.");
			put("PeerJ", "PeerJ");
			put("Immunobiology", "Immunobiology");
			put("J. Biochem. Mol. Biol.", "J. Biochem. Mol. Biol.");
			put("Mol. Pharmacol.", "Mol. Pharmacol.");
			put("Mech. Ageing Dev.", "Mech. Ageing Dev.");
			put("Mol. Cells", "Mol. Cells");
			put("IUBMB Life", "IUBMB Life");
			put("Biomacromolecules", "Biomacromolecules");
			put("Cell Res.", "Cell Res.");
			put("Protein Expr. Purif.", "Protein Expr. Purif.");
			put("Cancer Cell", "Cancer Cell");
			put("J. Mol. Biol.", "J. Mol. Biol.");
			put("Protein Eng. Des. Sel.", "Protein Eng. Des. Sel.");
			put("J. Mol. Recognit.", "J. Mol. Recognit.");
			put("RSC Adv.", "RSC Adv.");
			put("Mol. Pharm.", "Mol. Pharm.");
			put("to be published", "Not known");
			put("Trends. Biochem. Sci.", "Trends. Biochem. Sci.");
			put("Biochem. Biophys. Res. Commun.", "Biochem. Biophys. Res. Commun.");
			put("Bioinformatics", "Bioinformatics");
			put("Biol. Pharm. Bull.", "Biol. Pharm. Bull.");
			put("FEBS Lett.", "FEBS Lett.");
			put("Mol. Biol. Evol.", "Mol. Biol. Evol.");
			put("Mol. Gen. Genet.", "Mol. Gen. Genet.");
			put("Matrix Biol.", "Matrix Biol.");
			put("Anal. Biochem.", "Anal. Biochem.");
			put("Genes. Cells.", "Genes. Cells.");
			put("Chemistry", "Chemistry");
			put("Proteins", "Proteins");
			put("Chem. Commun. (Camb).", "Chem. Commun. (Camb).");
			put("J. Biol. Inorg. Chem.", "J. Biol. Inorg. Chem.");
			put("Structure", "Structure");
			put("Neuron", "Neuron");
			put("Protein. Cell", "Protein. Cell");
			put("Insect Biochem. Mol. Biol.", "Insect Biochem. Mol. Biol.");
			put("Mol. Vis.", "Mol. Vis.");
			put("Mol. Endocrinol.", "Mol. Endocrinol.");
			put("Biochem. Pharmacol.", "Biochem. Pharmacol.");
			put("Nat. Cell Biol.", "Nat. Cell Biol.");
			put("Nucleic Acids Res.", "Nucleic Acids Res.");
			put("Science", "Science");
			put("Mol. Microbiol.", "Mol. Microbiol.");
			put("Genes. Dev.", "Genes. Dev.");
			put("Protein Sci.", "Protein Sci.");
			put("Int. J. Mol. Sci.", "Int. J. Mol. Sci.");
			put("EMBO Rep.", "EMBO Rep.");
			put("Bioorg. Khim.", "Bioorg. Khim.");
			put("Appl. Microbiol. Biotechnol.", "Appl. Microbiol. Biotechnol.");
			put("Neurosurgery", "Neurosurgery");
			put("Mol. Biochem. Parasitol.", "Mol. Biochem. Parasitol.");
			put("Development", "Development");
			put("Elife (Cambridge).", "Elife (Cambridge).");
			put("Eur. J. Biochem.", "Eur. J. Biochem.");
			put("Cold Spring Harb. Symp. Quant. Biol.", "Cold Spring Harb. Symp. Quant. Biol.");
			put("Archaea", "Archaea");
			put("Crit. Rev. Biochem. Mol. Biol.", "Crit. Rev. Biochem. Mol. Biol.");
			put("Not known", "Not known");
			put("Biol. Chem.", "Biol. Chem.");
			put("J. Leukoc. Biol.", "J. Leukoc. Biol.");
			put("J. Magn. Reson.", "J. Magn. Reson.");
			put("Cell Regul.", "Cell Regul.");
			put("To Be Published", "Not known");
			put("J. Mol. Cell. Cardiol.", "J. Mol. Cell. Cardiol.");
			put("Cell Rep.", "Cell Rep.");
			put("RNA Biol.", "RNA Biol.");
			put("RNA", "RNA");
			put("Nat. Struct. Biol.", "Nat. Struct. Biol.");
			put("Plant Cell", "Plant Cell");
			put("Metabolomics.", "Metabolomics.");
			put("Dev. Cell", "Dev. Cell");
			put("Br. J. Pharmacol.", "Br. J. Pharmacol.");
			put("Nat. Chem. Biol.", "Nat. Chem. Biol.");
			put("Front. Mol. Neurosci.", "Front. Mol. Neurosci.");
			put("Nat. Chem.", "Nat. Chem.");
			put("Antioxid. Redox Signal.", "Antioxid. Redox Signal.");
			put("Nat. Methods", "Nat. Methods");
			put("Blood Coagul. Fibrinolysis.", "Blood Coagul. Fibrinolysis.");
			put("Br. J. Hosp. Med.", "Br. J. Hosp. Med.");
			put("J. Immunol.", "J. Immunol.");
			put("Gene", "Gene");
			put("Mol. Plant", "Mol. Plant");
			put("Proc. Natl. Acad. Sci. U. S. A.", "Proc. Natl. Acad. Sci. U. S. A.");
			put("Nature", "Nature");
			put("Appl. Environ. Microbiol.", "Appl. Environ. Microbiol.");
			put("Biochim. Biophys. Acta", "Biochim. Biophys. Acta");
			put("J. Struct. Biol.", "J. Struct. Biol.");
			put("Cell Cycle", "Cell Cycle");
			put("J. Mol. Cell Biol.", "J. Mol. Cell Biol.");
			put("Cancer. Res.", "Cancer. Res.");
			put("Langmuir.", "Langmuir.");
			put("Nucleosides. Nucleotides. Nucleic Acids", "Nucleosides. Nucleotides. Nucleic Acids");
			put("Virology", "Virology");
			put("Mol. Cell. Biochem.", "Mol. Cell. Biochem.");
			put("Mol. Cell. Proteomics.", "Mol. Cell. Proteomics.");
			put("Mol. Biol. Cell", "Mol. Biol. Cell");
			put("J. Gen. Microbiol.", "J. Gen. Microbiol.");
			put("Microbiology", "Microbiology");
			put("J. Bacteriol.", "J. Bacteriol.");
			put("Bioorg. Med. Chem.", "Bioorg. Med. Chem.");
			put("Neurochem. Int.", "Neurochem. Int.");
			put("ACS Chem. Biol.", "ACS Chem. Biol.");
			put("Exp. Cell Res.", "Exp. Cell Res.");
			put("To be published", "Not known");
			put("Mol. Immunol.", "Mol. Immunol.");
			put("PLoS Comput. Biol.", "PLoS Comput. Biol.");
			put("J. Phys. Chem. Lett.", "J. Phys. Chem. Lett.");
			put("J. Cell Biol.", "J. Cell Biol.");
			put("J. Pept. Res.", "J. Pept. Res.");
			put("Biomol. NMR Assign.", "Biomol. NMR Assign.");
			put("Nat. Struct. Mol. Biol.", "Nat. Struct. Mol. Biol.");
			put("Angew. Chem Int Ed Engl.", "Angew. Chem Int Ed Engl.");
			put("Biophys. Chem.", "Biophys. Chem.");
			put("Acta Naturae", "Acta Naturae");
			put("J. Chem. Phys.", "J. Chem. Phys.");
			put("J. Exp. Biol.", "J. Exp. Biol.");
			put("Retrovirology", "Retrovirology");
			put("Curr. Opin. Cell Biol.", "Curr. Opin. Cell Biol.");
			put("Rev. Soc. Odontol. La Plata", "Rev. Soc. Odontol. La Plata");
			put("Cell. Mol. Life Sci.", "Cell. Mol. Life Sci.");
			put("J. Pept. Sci.", "J. Pept. Sci.");
			put("Nat. Commun.", "Nat. Commun.");
			put("J. Lipid Res.", "J. Lipid Res.");
			put("Elife", "Elife");
			put("Mol. Cell", "Mol. Cell");
			put("Glycobiology", "Glycobiology");
			put("J. Bioenerg. Biomembr.", "J. Bioenerg. Biomembr.");
			put("Biochem. J.", "Biochem. J.");
			put("Magn. Reson. Chem.", "Magn. Reson. Chem.");
			put("Bioorg. Chem.", "Bioorg. Chem.");
			put("Eur. Biophys. J.", "Eur. Biophys. J.");
			put("Tuberculosis (Edinb).", "Tuberculosis (Edinb).");
			put("Expert Opin. Biol. Ther.", "Expert Opin. Biol. Ther.");
			put("J. Mol. Microbiol. Biotechnol.", "J. Mol. Microbiol. Biotechnol.");
			put("Infect. Immun.", "Infect. Immun.");

		}
	};

	public static String getJournalAbbrev(String val_name) {
		return (String) map_journal_abbrev.get(val_name);
	}

	/* synchronized */ public static String getPubMedIDByTitle(String title, boolean author_check, Connection conn_bmrb, String entry_id, int trials) {

		if (trials < 0 || trials >= 3 || title == null || title.isEmpty() || title.equals(".") || title.equals("?"))
			return null;

		try {

			title = title.replaceAll("\n", "");
			String pubmed_api = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?database=pubmed&term=" + URLEncoder.encode(title, "UTF-8");

			TrustManager[] tm = new TrustManager[] { new X509TrustManager() {

					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}
					@Override
					public void checkClientTrusted(X509Certificate[] certs, String authType) {
					}
					@Override
					public void checkServerTrusted(X509Certificate[] certs, String authType) {
					}
				}

			};

			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, tm, new java.security.SecureRandom());

			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});

			URL url = new URL(pubmed_api);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());

			DocumentBuilderFactory doc_builder_fac = DocumentBuilderFactory.newInstance();

			doc_builder_fac.setValidating(false);
			doc_builder_fac.setNamespaceAware(false);
			doc_builder_fac.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
			doc_builder_fac.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

			DocumentBuilder doc_builder = doc_builder_fac.newDocumentBuilder();

			Document doc = doc_builder.parse(conn.getInputStream());

			Node root = doc.getDocumentElement();

			if (!root.getNodeName().equals("eSearchResult"))
				return null;

			int count = 0;

			for (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {

				if (child.getNodeName().equals("Count")) {
					count = Integer.valueOf(child.getFirstChild().getNodeValue());
					break;
				}

			}

			if (count == 0) {

				if(!title.contains(":"))
					return null;

				String[] _title = title.split(":");
				String header = _title[0].toLowerCase();

				if (_title.length > 1 && (header.contains("article") || header.contains("communication") || header.contains("data") || header.contains("letter") || header.contains("note") || header.contains("paper") || header.contains("perspective") || header.contains("review") || header.contains("supplemental") || header.contains("video"))) {

					title = "";

					for (int l = 1; l < _title.length; l++) {

						title += _title[l];

						if (l + 1 < _title.length)
							title += ":";

					}

					return getPubMedIDByTitle(title, author_check, conn_bmrb, entry_id, 0);
				}

				return null;
			}

			else if (count > 1)
				author_check = true;

			for (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {

				if (child.getNodeName().equals("IdList")) {

					for (Node id_list = child.getFirstChild(); id_list != null; id_list = id_list.getNextSibling()) {

						if (id_list.getNodeName().equals("Id")) {

							String pubmed_id = id_list.getFirstChild().getNodeValue();

							Node doc_sum = getDocSumNodeByPubMedID(pubmed_id, 0);

							if (doc_sum == null)
								continue;

							if (!author_check)
								return pubmed_id;

							String _title = getTitle(null, doc_sum);

							if (count > 1) {

								if (_title == null)
									continue;

								if (!_title.toLowerCase().contains(title.toLowerCase()))
									continue;

							}

							String first_author = getFirstAuthor(null, doc_sum);
							String last_author = getLastAuthor(null, doc_sum);

							if (first_author == null || last_author == null)
								continue;

							if (checkAuthorFamilyName(first_author.split(" ")[0], last_author.split(" ")[0], conn_bmrb, entry_id))
								return pubmed_id;

						}

					}

				}

			}

			return null;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			return getPubMedIDByTitle(title, author_check, conn_bmrb, entry_id, ++trials);

			//e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			//e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}

		return null;
	}

	/* synchronized */ public static String getDOIByTitle(String title, boolean author_check, Connection conn_bmrb, String entry_id, String year, int trials) {

		if (trials < 0 || trials >= 3 || title == null || title.isEmpty() || title.equals(".") || title.equals("?"))
			return null;

		try {

			bmr_Util_CrossRef[] crossrefs = null;

			title = title.replaceAll("\n", "");
			String crossref_doi_api = "https://search.crossref.org/dois?q=" + URLEncoder.encode(title, "UTF-8") + "&year=" + year + "&rows=1";
			TrustManager[] tm = new TrustManager[] { new X509TrustManager() {

					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}
					@Override
					public void checkClientTrusted(X509Certificate[] certs, String authType) {
					}
					@Override
					public void checkServerTrusted(X509Certificate[] certs, String authType) {
					}
				}

			};

			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, tm, new java.security.SecureRandom());

			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});

			URL url = new URL(crossref_doi_api);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());

			ObjectMapper mapper = new ObjectMapper();

			crossrefs = mapper.readValue(conn.getInputStream(), bmr_Util_CrossRef[].class);

			if (crossrefs == null || crossrefs.length != 1)
				return null;

			bmr_Util_CrossRef crossref = crossrefs[0];

			if (crossref.getScore() < 2.0 || crossref.getTitle() == null)
				return null;

			bmr_Util_AlignmentStrict align = new bmr_Util_AlignmentStrict(title.trim().replaceAll("\\s", "").toLowerCase().replaceAll("[^\\x01-\\x7f]", "").toCharArray(), crossref.getTitle().trim().replaceAll("\\s", "").toLowerCase().replaceAll("[^\\x01-\\x7f]", "").toCharArray());

			if ((float) align.score / (float) (crossref.getTitle().length() - Math.abs(title.length() - crossref.getTitle().length())) <= 0.8)
				return null;

			String[] citations = crossref.getFullCitation().split(",");
			String first_author_family_name = null;
			String second_author_family_name = null;
			String last_author_family_name = null;

			for (String citation : citations) {

				citation = citation.trim();

				if (citation.matches("^[0-9]+$"))
					break;

				String[] author = citation.split(" ");

				if (first_author_family_name == null)
					first_author_family_name = author[author.length - 1];

				else if (second_author_family_name == null)
					second_author_family_name = author[author.length - 1];

				last_author_family_name = author[author.length - 1];

			}

			if (first_author_family_name == null || last_author_family_name == null)
				return null;

			if (checkAuthorFamilyName(first_author_family_name, last_author_family_name, conn_bmrb, entry_id) || (second_author_family_name != null && checkAuthorFamilyName(second_author_family_name, last_author_family_name, conn_bmrb, entry_id)))
				return crossref.getDoi().replaceFirst("https?//dx.doi.org/", "");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (IOException e) {

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			return getDOIByTitle(title, author_check, conn_bmrb, entry_id, year, ++trials);

			//e.printStackTrace();
		}

		return null;
	}

	public static boolean checkAuthorFamilyName(String first_author_family_name, String last_author_family_name, Connection conn_bmrb, String entry_id) {

		if (first_author_family_name == null || first_author_family_name.isEmpty() || first_author_family_name.equals(".") || first_author_family_name.equals("?"))
			return false;

		if (last_author_family_name == null || last_author_family_name.isEmpty() || last_author_family_name.equals(".") || last_author_family_name.equals("?"))
			return false;

		Statement state = null;
		ResultSet rset = null;

		String query = new String("select \"Family_name\" from \"Citation_author\" where \"Entry_ID\"='" + entry_id + "' and \"Family_name\" is not null");

		try {

			state = conn_bmrb.createStatement();
			rset = state.executeQuery(query);

			first_author_family_name = first_author_family_name.toLowerCase();
			last_author_family_name = last_author_family_name.toLowerCase();

			int min_match_first_author = first_author_family_name.length() - first_author_family_name.replaceAll("[\\x01-\\x7f]", "").length() - 1;
			int min_match_last_author = last_author_family_name.length() - last_author_family_name.replaceAll("[\\x01-\\x7f]", "").length() - 1;

			boolean found_first_author = false;
			boolean found_last_author = false;

			while (rset.next()) {

				String family_name = rset.getString("Family_name");

				if (!found_first_author) {

					bmr_Util_AlignmentStrict align = new bmr_Util_AlignmentStrict(family_name.toLowerCase().toCharArray(), first_author_family_name.toCharArray());

					if (align.score >= min_match_first_author)
						found_first_author = true;

				}

				else if (!found_last_author) {

					bmr_Util_AlignmentStrict align = new bmr_Util_AlignmentStrict(family_name.toLowerCase().toCharArray(), last_author_family_name.toCharArray());

					if (align.score >= min_match_last_author)
						found_last_author = true;

				}

			}

			if (found_first_author && found_last_author)
				return true;

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(bmr_Util_Citation.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			System.exit(1);

		} finally {

			try {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(bmr_Util_Citation.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);

			}
		}

		return false;
	}

	/* synchronized */ public static Node getDocSumNodeByPubMedID(String pubmed_id, int trials) {

		if (trials < 0 || trials >= 3 || pubmed_id == null || pubmed_id.isEmpty() || pubmed_id.equals(".") || pubmed_id.equals("?") || !pubmed_id.matches("^[0-9]+$"))
			return null;

		File esum_file = new File(bmr_Util_Main.esum_dir_name + pubmed_id + ".xml");

		if (esum_file.exists())
			return getDocSumNodeByPubMedID(pubmed_id, trials, esum_file);

		try {

			TrustManager[] tm = new TrustManager[] { new X509TrustManager() {

					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}
					@Override
					public void checkClientTrusted(X509Certificate[] certs, String authType) {
					}
					@Override
					public void checkServerTrusted(X509Certificate[] certs, String authType) {
					}
				}

			};

			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, tm, new java.security.SecureRandom());

			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});

			String pubmed_api = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esummary.fcgi?db=pubmed&id=" + pubmed_id;

			URL url = new URL(pubmed_api);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());

			DocumentBuilderFactory doc_builder_fac = DocumentBuilderFactory.newInstance();

			doc_builder_fac.setValidating(false);
			doc_builder_fac.setNamespaceAware(false);
			doc_builder_fac.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
			doc_builder_fac.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

			DocumentBuilder doc_builder = doc_builder_fac.newDocumentBuilder();

			Document doc = doc_builder.parse(conn.getInputStream());

			Node root = doc.getDocumentElement();

			if (!root.getNodeName().equals("eSummaryResult"))
				return null;

			for (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {

				if (child.getNodeName().equals("DocSum")) {

					writePubMedESummary(esum_file, doc);

					return child;
				}

			}

			return null;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			return getDocSumNodeByPubMedID(pubmed_id, ++trials);

			//e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			//e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static void writePubMedESummary(File esum_file, Document doc) {

		File esum_file_ = new File(esum_file.getPath() + "~");

		if (esum_file.exists())
			return;

		try {

			DOMImplementation domImpl = doc.getImplementation();
			DOMImplementationLS domImplLS = (DOMImplementationLS) domImpl.getFeature("LS", "3.0");

			LSOutput output = domImplLS.createLSOutput();
			output.setByteStream(new FileOutputStream(esum_file_));

			LSSerializer serializer = domImplLS.createLSSerializer();
			serializer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
			serializer.write(doc, output);

/*
			OutputFormat format = new OutputFormat(doc);

			format.setIndenting(true);
			format.setIndent(2);

			FileWriter writer = new FileWriter(esum_file_);
			XMLSerializer serializer = new XMLSerializer(writer, format);

			serializer.serialize(doc);
*/

			esum_file_.renameTo(esum_file);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static Node getDocSumNodeByPubMedID(String pubmed_id, int trials, File esum_file) {

		try {

			DocumentBuilderFactory doc_builder_fac = DocumentBuilderFactory.newInstance();

			doc_builder_fac.setValidating(false);
			doc_builder_fac.setNamespaceAware(false);
			doc_builder_fac.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
			doc_builder_fac.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

			DocumentBuilder doc_builder = doc_builder_fac.newDocumentBuilder();

			Document doc = doc_builder.parse(esum_file);

			Node root = doc.getDocumentElement();

			if (root.getNodeName().equals("eSummaryResult")) {

				for (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {

					if (child.getNodeName().equals("DocSum"))
						return child;

				}

			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		esum_file.delete();

		return getDocSumNodeByPubMedID(pubmed_id, trials);
	}

	public static String getFirstAuthor(String val_name, Node doc_sum) {

		if (doc_sum == null)
			return val_name;

		for (Node child = doc_sum.getFirstChild(); child != null; child = child.getNextSibling()) {

			if (child.getNodeName().equals("Item") && child.hasAttributes()) {

				NamedNodeMap node_map = child.getAttributes();

				if (node_map != null && child.hasChildNodes() && node_map.item(0).getTextContent().equals("AuthorList")) {

					for (Node author = child.getFirstChild(); author != null; author = author.getNextSibling()) {

						if (author.getNodeName().equals("Item") && author.hasAttributes()) {

							node_map = author.getAttributes();

							if (node_map != null && author.hasChildNodes() && node_map.item(0).getTextContent().equals("Author"))
								return author.getFirstChild().getNodeValue();

						}

					}

				}

			}

		}

		return val_name;
	}

	public static String getLastAuthor(String val_name, Node doc_sum) {

		if (doc_sum == null)
			return val_name;

		for (Node child = doc_sum.getFirstChild(); child != null; child = child.getNextSibling()) {

			if (child.getNodeName().equals("Item") && child.hasAttributes()) {

				NamedNodeMap node_map = child.getAttributes();

				if (node_map != null && child.hasChildNodes() && node_map.item(0).getTextContent().equals("LastAuthor"))
					return child.getFirstChild().getNodeValue();

			}

		}

		return val_name;
	}

	public static String getDOI(String val_name, Node doc_sum) {

		if (doc_sum == null)
			return val_name.matches("^[0-9]$") ? null : val_name;

		for (Node child = doc_sum.getFirstChild(); child != null; child = child.getNextSibling()) {

			if (child.getNodeName().equals("Item") && child.hasAttributes()) {

				NamedNodeMap node_map = child.getAttributes();

				if (node_map != null && child.hasChildNodes() && node_map.item(0).getTextContent().equals("DOI"))
					return child.getFirstChild().getNodeValue();

			}

		}

		return null;
	}

	public static String getTitle(String val_name, Node doc_sum) {

		if (doc_sum == null)
			return val_name;

		for (Node child = doc_sum.getFirstChild(); child != null; child = child.getNextSibling()) {

			if (child.getNodeName().equals("Item") && child.hasAttributes()) {

				NamedNodeMap node_map = child.getAttributes();

				if (node_map != null && child.hasChildNodes() && node_map.item(0).getTextContent().equals("Title"))
					return child.getFirstChild().getNodeValue();

			}

		}

		return val_name;
	}

	public static String getJournalNameFull(String val_name, Node doc_sum) {

		if (doc_sum == null)
			return val_name;

		for (Node child = doc_sum.getFirstChild(); child != null; child = child.getNextSibling()) {

			if (child.getNodeName().equals("Item") && child.hasAttributes()) {

				NamedNodeMap node_map = child.getAttributes();

				if (node_map != null && child.hasChildNodes() && node_map.item(0).getTextContent().equals("FullJournalName"))
					return child.getFirstChild().getNodeValue();

			}

		}

		return val_name;
	}

	public static String getJournalAbbrev(String val_name, Node doc_sum) {

		if (doc_sum == null)
			return getJournalAbbrev(val_name);

		for (Node child = doc_sum.getFirstChild(); child != null; child = child.getNextSibling()) {

			if (child.getNodeName().equals("Item") && child.hasAttributes()) {

				NamedNodeMap node_map = child.getAttributes();

				if (node_map != null && child.hasChildNodes() && node_map.item(0).getTextContent().equals("Source")) {

					String[] array = getJournalNameFull(null, doc_sum).toLowerCase().split(" \\(");
					String fullname = array[0].replaceAll("^the ", "").replaceAll(" the ", " ").replaceAll(" of ", " ").replaceAll(" and ", " ");
					String Abbrev = child.getFirstChild().getNodeValue().replaceAll("\\.", " ").replaceAll("  ", " ");
					String abbrev = Abbrev.toLowerCase();

					char[] A = Abbrev.toCharArray();
					char[] a = abbrev.toCharArray();

					bmr_Util_AlignmentStrict alignment = new bmr_Util_AlignmentStrict(fullname.toCharArray(), a);

					if (alignment.score <= 0)
						return getJournalAbbrev(val_name);

					char[] af = alignment.S1.toCharArray();
					char[] aa = alignment.S2.toCharArray();

					int ai = 0;

					for (int i = 0; i < A.length; i++) {

						if (a[i] != ' ') {

							while (ai < aa.length && a[i] != aa[ai])
								ai++;

						}

						else if (++i < A.length) {

							while (ai < aa.length && a[i] != aa[ai])
								ai++;

							if (ai < aa.length) {

								int j = 2;

								while (i - j >= 0 && ai - j >= 0 && a[i - j] != ' ' && aa[ai - j] != ' ') {

									if (a[i - j] != aa[ai - j]) {
										A[i - 1] = '.';
										break;
									}

									j++;
								}
							}
						}
					}

					Abbrev = String.valueOf(A).replaceAll("\\.", "\\. ");

					if (af[af.length - 1] != aa[aa.length - 1])
						Abbrev = Abbrev.concat(".");

					return Abbrev;
				}

			}

		}

		return getJournalAbbrev(val_name);
	}

	public static String getJournalVolume(String val_name, Node doc_sum) {

		if (doc_sum == null)
			return val_name;

		for (Node child = doc_sum.getFirstChild(); child != null; child = child.getNextSibling()) {

			if (child.getNodeName().equals("Item") && child.hasAttributes()) {

				NamedNodeMap node_map = child.getAttributes();

				if (node_map != null && child.hasChildNodes() && node_map.item(0).getTextContent().equals("Volume"))
					return child.getFirstChild().getNodeValue();

			}

		}

		return val_name;
	}

	public static String getJournalIssue(String val_name, Node doc_sum) {

		if (doc_sum == null)
			return val_name;

		for (Node child = doc_sum.getFirstChild(); child != null; child = child.getNextSibling()) {

			if (child.getNodeName().equals("Item") && child.hasAttributes()) {

				NamedNodeMap node_map = child.getAttributes();

				if (node_map != null && child.hasChildNodes() && node_map.item(0).getTextContent().equals("Issue"))
					return child.getFirstChild().getNodeValue();

			}

		}

		return val_name;
	}

	public static String getJournalISSN(String val_name, Node doc_sum) {

		if (doc_sum == null)
			return val_name.matches("^[0-9]$") ? null : val_name;

		for (Node child = doc_sum.getFirstChild(); child != null; child = child.getNextSibling()) {

			if (child.getNodeName().equals("Item") && child.hasAttributes()) {

				NamedNodeMap node_map = child.getAttributes();

				if (node_map != null && child.hasChildNodes() && node_map.item(0).getTextContent().equals("ISSN"))
					return child.getFirstChild().getNodeValue();

			}

		}

		return null;
	}

	public static String getPageFirst(String val_name, Node doc_sum) {

		if (doc_sum == null)
			return val_name;

		for (Node child = doc_sum.getFirstChild(); child != null; child = child.getNextSibling()) {

			if (child.getNodeName().equals("Item") && child.hasAttributes()) {

				NamedNodeMap node_map = child.getAttributes();

				if (node_map != null && child.hasChildNodes() && node_map.item(0).getTextContent().equals("Pages")) {

					String pages = child.getFirstChild().getNodeValue();

					return pages.split("-")[0];
				}

			}

		}

		return val_name;
	}

	public static String getPageLast(String val_name, Node doc_sum) {

		if (doc_sum == null)
			return val_name;

		for (Node child = doc_sum.getFirstChild(); child != null; child = child.getNextSibling()) {

			if (child.getNodeName().equals("Item") && child.hasAttributes()) {

				NamedNodeMap node_map = child.getAttributes();

				if (node_map != null && child.hasChildNodes() && node_map.item(0).getTextContent().equals("Pages")) {

					String pages = child.getFirstChild().getNodeValue();
					String[] array = pages.split("-");

					char[] page_first = array[0].toCharArray();

					if (array.length > 1 && !(array[1] == null || array[1].isEmpty() || array[1].equals(".") || array[1].equals("?"))) {

						char[] page_last = array[1].toCharArray();

						int len_first = array[0].length();
						int len_last = array[1].length();

						if (len_first < len_last)
							return array[1].split("\\.")[0];

						for (int l = 1; l <= len_last; l++)
							page_first[len_first - l] = page_last[len_last - l];

					}

					return String.valueOf(page_first);
				}

			}

		}

		return val_name;
	}

	public static String getYear(String val_name, Node doc_sum) {

		if (doc_sum == null)
			return val_name;

		for (Node child = doc_sum.getFirstChild(); child != null; child = child.getNextSibling()) {

			if (child.getNodeName().equals("Item") && child.hasAttributes()) {

				NamedNodeMap node_map = child.getAttributes();

				if (node_map != null && child.hasChildNodes() && node_map.item(0).getTextContent().equals("PubDate")) {

					String pages = child.getFirstChild().getNodeValue();

					return pages.split(" ")[0];
				}

			}

		}

		return val_name;
	}
}
