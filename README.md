# BMRBxTool

The BMRBxTool converts NMR-STAR data into XML format ([**BMRB/XML**](http://bmrbpub.protein.osaka-u.ac.jp/archive/xml)) and JSON format ([**BMRB/JSON**](http://bmrbpub.protein.osaka-u.ac.jp/archive/json-noatom)). The schema of the BMRB/XML has been translated from the current NMR-STAR dictionary using [**mmCIF Dictionary Suite**](http://mmcif.wwpdb.org/docs/software-resources.html). BMRBxTool generates qualified XML files by programmed remediation and reports validation errors against the XML schema. By using scripts bundled with the tool, XML and JSON documents can be updated. BMRBxTool uses [a extended NMR-STAR dictionary](http://bmrbpub.protein.osaka-u.ac.jp/schema/mmcif_nmr-star.dic), derived from the original [NMR-STAR v3.2 Dictionary](http://svn.bmrb.wisc.edu/svn/nmr-star-dictionary/bmrb_only_files/adit_input/NMR-STAR.dic), to pass XML schema validation and accomodate extra data; the difference between the dictionaries is minimal.<br />
The [**PDBj-BMRB Data Server**](http://bmrbpub.protein.osaka-u.ac.jp) has opened for publishing these alternative representations of NMR-STAR data.

## Usage

Please refer to [INSTALL](https://github.com/yokochi47/BMRBxTool/blob/master/INSTALL).

## References

- Masashi Yokochi, Naohiro Kobayashi, Eldon L. Ulrich, Akira R. Kinjo, Takeshi Iwata, Yannis E. Ioannidis, Miron Linvy, John L. Markley, Haruki Nakamura, Chojiro Kojima, Toshimichi Fujiwara.<br />
 "Publication of nuclear magnetic resonance experimental data with semantic web technology and the application thereof to biomedical research of proteins", J. Biomed. Semantics, 7, 1-4 (2016)
- Akira R. Kinjo, Gert-Jan Bekker, Hiroshi Wako, Shigeru Endo, Yuko Tsuchiya, Hiromu Sato, Hafumi Nishi, Kengo Kinoshita, Hirofumi Suzuki, Takeshi Kawabata, Masashi Yokochi, Takeshi Iwata, Naohiro Kobayashi, Toshimichi Fujiwara, Genji Kurisu, Haruki Nakamura,<br />
 "New tools and functions in data-out activities at Protein Data Bank Japan (PDBj)", Protein Science, 27(1), 95-102 (2018)

