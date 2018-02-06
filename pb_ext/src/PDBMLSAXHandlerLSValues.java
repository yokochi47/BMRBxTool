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

public class PDBMLSAXHandlerLSValues extends DefaultHandler {

	PB_list rep_pb_list = null;

	boolean refine = false;
	boolean ls_r_factor_r_free = false;
	boolean ls_r_factor_r_work = false;
	boolean ls_d_res_high = false;
	boolean ls_d_res_low = false;

	public PDBMLSAXHandlerLSValues(PB_list _rep_pb_list) {

		rep_pb_list = _rep_pb_list;

	}

	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) {

		if (qName.equals("PDBx:refine"))
			refine = true;

		if (!refine)
			return;

		else if (qName.equals("PDBx:ls_R_factor_R_free"))
			ls_r_factor_r_free = true;

		else if (qName.equals("PDBx:ls_R_factor_R_work"))
			ls_r_factor_r_work = true;

		else if (qName.equals("PDBx:ls_d_res_high"))
			ls_d_res_high = true;

		else if (qName.equals("PDBx:ls_d_res_low"))
			ls_d_res_low = true;

	}

	public void endElement(String namespaceURI, String localName, String qName) {

		if (qName.equals("PDBx:refine"))
			refine = false;

		if (!refine)
			return;

		else if (qName.equals("PDBx:ls_R_factor_R_free"))
			ls_r_factor_r_free = false;

		else if (qName.equals("PDBx:ls_R_factor_R_work"))
			ls_r_factor_r_work = false;

		else if (qName.equals("PDBx:ls_d_res_high"))
			ls_d_res_high = false;

		else if (qName.equals("PDBx:ls_d_res_low"))
			ls_d_res_low = false;

	}

	public void characters(char[] chars, int offset, int length) {

		if (!refine)
			return;

		else if (ls_r_factor_r_free)
			rep_pb_list.PDBX_refine_ls_R_factor_R_free = new String(chars, offset, length);

		else if (ls_r_factor_r_work)
			rep_pb_list.PDBX_refine_ls_R_factor_R_work = new String(chars, offset, length);

		else if (ls_d_res_high)
			rep_pb_list.PDBX_refine_ls_d_res_high = new String(chars, offset, length);

		else if (ls_d_res_low)
			rep_pb_list.PDBX_refine_ls_d_res_low = new String(chars, offset, length);

	}

}
