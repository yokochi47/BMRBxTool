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

import java.util.HashMap;
import java.util.Map;

public class bmr_Util_NMRSpectrometer {

	static final Map<String, String> map_manufacturer = new HashMap<String, String>() {

		private static final long serialVersionUID = 100L;

		{

			put("Cambridge Instruments spectrometers", "Cambridge");
			put("Ruben/MIT", "Home-built");
			put("AVANCE", "Bruker");
			put("Brucker", "Bruker");
			put("DMX", "Bruker");
			put("na", "na");
			put("GE", "GE");
			put("Agilent", "Agilent");
			put("Home built", "Home-built");
			put("INOVA", "Varian");
			put("JEQL", "JEOL");
			put("FBML", "FBML");
			put("Nicolet", "Nicolet");
			put("varian", "Varian");
			put("Varian, Inc", "Varian");
			put("Varian Unity Plus", "Varian");
			put("CMR", "Cambridge");
			put("Custom-built", "Home-built");
			put("Burker", "Bruker");
			put("Varan", "Varian");
			put("Brruker", "Bruker");
			put("Home build", "Home-built");
			put("Jeol", "JEOL");
			put("Bruker", "Bruker");
			put("Bruker Spectrospin", "Bruker");
			put("Homemade", "Home-built");
			put("Homebuilt", "Home-built");
			put("BRUKER", "Bruker");
			put(".Bruker", "Bruker");
			put("DRX", "Bruker");
			put("home made", "Home-built");
			put("home-built", "Home-built");
			put("Francis Bitter Magnet Laboratory", "FBML");
			put("FMBL", "FBML");
			put("custom-built", "Home-built");
			put("Unity", "Varian");
			put("Cambridge", "Cambridge");
			put("Bruker Biospin", "Bruker");
			put("Varin", "Varian");
			put("Varian", "Varian");
			put("General Electric", "GE");
			put("Oxford", "Oxford");
			put("spectrometer_2", "na");
			put("home built", "Home-built");
			put("spectrometer_1", "na");
			put("Bruker/Oxford", "Bruker");
			put("VARIAN", "Varian");
			put("Biospin Bruker", "Bruker");
			put("Omega", "GE");
			put("JEOL", "JEOL");
			put("Home-built", "Home-built");
			put("Varianr", "Varian");
			put("bruker", "Bruker");
			put("Oxford Instruments", "Oxford");
			put("Home Built", "Home-built");
			put("ns", "na");
			put("n/a", "na");
			put("home build", "Home-built");
			put("Vairan", "Varian");
			put("N/A", "na");
			put("Avance", "Bruker");
			put("UNITY", "Varian");
			put("homebuilt", "Home-built");
			put("Varain", "Varian");
			put("Home-build", "Home-built");

		}
	};

	public static String getManufacturer(String val_name) {
		return (String) map_manufacturer.get(val_name);
	}

	static final Map<String, String> map_field_strength = new HashMap<String, String>() {

		private static final long serialVersionUID = 101L;

		{

			put("600.13", "600");
			put("500.03", "500");
			put("720 MHz", "720");
			put("599.98", "600");
			put("500 MHz", "500");
			put("600 MHZ", "600");
			put("750MHz", "750");
			put("900", "900");
			put("800.23", "800");
			put("700.13", "700");
			put("600.03", "600");
			put("470", "470");
			put("800", "800");
			put("700", "700");
			put("591.1", "590");
			put("270", "270");
			put("601", "600");
			put("800MHz", "800");
			put("600", "600");
			put("499.865", "500");
			put("500", "500");
			put("500.1323", "500");
			put("400", "400");
			put("800.13", "800");
			put("900.21", "900");
			put("300", "300");
			put("950", "950");
			put("597.76", "600");
			put("599.162", "600");
			put("360", "360");
			put("850", "850");
			put("200", "200");
			put("470.3", "470");
			put(".800", "800");
			put("n/a", "na");
			put("750", "750");
			put("900MHz", "900");
			put("650", "650");
			put("800.2", "800");
			put("550", "550");
			put("90", "90");
			put("450", "450");
			put("749", "750");
			put("ns", "na");
			put("350", "350");
			put("250", "250");
			put("799.7", "800");
			put("799.81", "800");
			put("800 MHz", "800");
			put("na", "na");
			put("400.13", "400");
			put("600M", "600");
			put("500.20", "500");
			put("700 MHz", "700");
			put("0", "na");
			put("700.133", "700");
			put("140", "140");
			put("121.5", "120");
			put("500MHz", "500");
			put("499.730", "500");
			put("591", "590");
			put("590", "590");
			put("600.2", "600");
			put("920", "920");
			put("141.2", "140");
			put("500.13", "500");
			put("600, 800", "na");
			put("720", "720");
			put("600MHz", "600");
			put("599.8", "600");
			put("501.9", "500");
			put("600 MHz", "600");

		}
	};

	public static String getFieldStrength(String val_name) {
		return (String) map_field_strength.get(val_name);
	}
}
