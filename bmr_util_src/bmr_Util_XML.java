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
import java.sql.*;

import org.apache.xmlbeans.XmlOptions;

public class bmr_Util_XML {

	public static void write(Object lock_obj, Connection conn_bmrb, Connection conn_clone, Connection conn_tax, Connection conn_le, bmr_Util_EntityExperimentalSrc ee, bmr_Util_EntityNaturalSrc en, bmr_Util_StructClassification sc, bmr_Util_Entry ent, String xml_dir_name, String rel_dir_name, String log_dir_name, String err_dir_name, String loc_dir_name, String file_prefix, String entry_id, bmr_Util_Valid validator, XmlOptions xml_opt) {

		String xml_base_name = file_prefix + entry_id + bmr_Util_Main.noatom_suffix + ".xml";
		String rel_base_name = file_prefix + entry_id + bmr_Util_Main.noatom_suffix + "_last";
		String log_base_name = file_prefix + entry_id + bmr_Util_Main.noatom_suffix + "_log";
		String err_base_name = file_prefix + entry_id + bmr_Util_Main.noatom_suffix + "_err";
		String loc_base_name = file_prefix + entry_id + bmr_Util_Main.noatom_suffix + "_lock";

		File err_dir = new File(err_dir_name);

		if (!err_dir.isDirectory())
			return;

		File xml_file = new File(xml_dir_name, xml_base_name);
		File log_file = new File(log_dir_name, log_base_name);
		File err_file = new File(err_dir_name, err_base_name);
		File loc_file = new File(loc_dir_name, loc_base_name);

		if (loc_file.exists() || err_file.exists())
			return;

		FileWriter logw = null;
		FileWriter errw = null;

		boolean written = false;

		try {

			boolean no_exist = true;

			synchronized (lock_obj) {

				if (loc_file.exists() || err_file.exists())
					no_exist = false;

				else {

					FileWriter locw = new FileWriter(loc_file);
					locw.write(entry_id);
					locw.close();

				}

			}

			if (!no_exist)
				return;

			errw = new FileWriter(err_file);

			if (bmr_Util_Main.write_xml) {

				logw = new FileWriter(log_file);

				FileWriter filew = new FileWriter(xml_file);
				BufferedWriter buffw = new BufferedWriter(filew);

				buffw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");

				buffw.write("<BMRBx:datablock datablockName=\"" + entry_id + "\"\n");
				buffw.write("  xmlns:BMRBx=\"http://bmrbpub.pdbj.org/schema/mmcif_nmr-star.xsd\"\n");
				buffw.write("  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
				buffw.write("  xsi:schemaLocation=\"http://bmrbpub.pdbj.org/schema/mmcif_nmr-star.xsd mmcif_nmr-star.xsd\">\n");

				bmr_AmbiguousAtomChemShiftType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AngleType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AngularOrderParamType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AngularOrderParameterListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AssemblyType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AssemblyAnnotationListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AssemblyBioFunctionType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AssemblyCitationType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AssemblyCommonNameType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AssemblyDbLinkType.write_xml(conn_bmrb, conn_tax, conn_le, ent, entry_id, xml_opt, buffw, logw, errw);
				bmr_AssemblyInteractionType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AssemblyKeywordType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AssemblySegmentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AssemblySegmentDescriptionType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AssemblySubsystemType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AssemblySystematicNameType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AssemblyTypeType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AssignedChemShiftListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AssignedPeakChemShiftType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AssignedSpectralTransitionType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AtomType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AtomChemShiftType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AtomNomenclatureType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AtomSiteType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AtomSitesFootnoteType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AtomTypeType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AuthorAnnotationType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AutoRelaxationType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AutoRelaxationExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AutoRelaxationListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AutoRelaxationSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_AuxiliaryFilesType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_BindingExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_BindingParamType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_BindingParamListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_BindingPartnersType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_BindingResultType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_BindingSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_BindingValueListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_BondType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_BondAnnotationType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_BondAnnotationListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_BondObservedConformerType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_CaCbConstraintType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_CaCbConstraintExptType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_CaCbConstraintListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_CaCbConstraintSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_CharacteristicType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemCompType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemCompAngleType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemCompAssemblyType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemCompAtomType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemCompBioFunctionType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemCompBondType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemCompCitationType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemCompCommonNameType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemCompDbLinkType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemCompDescriptorType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemCompIdentifierType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemCompKeywordType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemCompSmilesType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemCompSystematicNameType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemCompTorType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemShiftAnisotropyType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemShiftCompletenessCharType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemShiftCompletenessListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemShiftExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemShiftIsotopeEffectListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemShiftPerturbationType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemShiftPerturbationExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemShiftPerturbationListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemShiftPerturbationSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemShiftRefType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemShiftReferenceType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemShiftSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemShiftsCalcSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemShiftsCalcTypeType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemStructDescriptorType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemicalRateType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemicalRateExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemicalRateListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChemicalRateSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChromatographicColumnType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ChromatographicSystemType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_CitationType.write_xml(conn_bmrb, conn_clone, conn_tax, conn_le, ent, entry_id, xml_opt, buffw, logw, errw);
				bmr_CitationAuthorType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_CitationEditorType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_CitationKeywordType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ComputerType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ComputerCitationType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ConfFamilyCoordSetConstrListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ConfStatsSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ConformerFamilyCoordSetType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ConformerFamilyCoordSetExptType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ConformerFamilyRefinementType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ConformerFamilySoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ConformerStatListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ConformerStatListEnsType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ConformerStatListRepType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ConstraintFileType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ConstraintStatListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ConstraintStatListEnsType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ConstraintStatListRepType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ConstraintStatsConstrListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_CouplingConstantType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_CouplingConstantExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_CouplingConstantListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_CouplingConstantSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_CrossCorrelationDCsaType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_CrossCorrelationDCsaExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_CrossCorrelationDCsaListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_CrossCorrelationDCsaSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_CrossCorrelationDdType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_CrossCorrelationDdExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_CrossCorrelationDdListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_CrossCorrelationDdSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_CsAnisotropyType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_CsAnisotropyExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_CsAnisotropySoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DHFractFactorExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DHFractFactorSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DHFractionationFactorType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DHFractionationFactorListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DataSetType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DatumType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DecouplingPulseSequenceType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DeducedHBondType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DeducedHBondExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DeducedHBondListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DeducedHBondSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DeducedSecdStructExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DeducedSecdStructExptlType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DeducedSecdStructFeatureType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DeducedSecdStructListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DeducedSecdStructSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DipolarCouplingType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DipolarCouplingExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DipolarCouplingListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DipolarCouplingSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DipoleDipoleRelaxType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DipoleDipoleRelaxExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DipoleDipoleRelaxListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DipoleDipoleRelaxSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DistConstrSoftwareSettingType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DistConstraintType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DistConstraintCommentOrgType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DistConstraintConvErrType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DistConstraintParseErrType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DistConstraintParseFileType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DistConstraintTreeType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DistConstraintValueType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DistanceConstraintExptType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DistanceConstraintListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_DistanceConstraintSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_EmrExptType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_EmrInstrumentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_EnergeticPenaltyFunctionType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_EntityType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_EntityAssemblyType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_EntityAtomListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_EntityBiologicalFunctionType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_EntityBondType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_EntityChemCompDeletedAtomType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_EntityChimeraSegmentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_EntityCitationType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_EntityCommonNameType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_EntityCompIndexType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_EntityCompIndexAltType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_EntityDbLinkType.write_xml(conn_bmrb, conn_tax, conn_le, ent, entry_id, xml_opt, buffw, logw, errw);
				bmr_EntityDeletedAtomType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_EntityExperimentalSrcType.write_xml(conn_bmrb, conn_tax, conn_le, ee, entry_id, xml_opt, buffw, logw, errw);
				bmr_EntityExperimentalSrcListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_EntityKeywordType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_EntityNaturalSrcType.write_xml(conn_bmrb, conn_tax, conn_le, en, entry_id, xml_opt, buffw, logw, errw);
				bmr_EntityNaturalSrcListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_EntityPolySeqType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_EntityPurityType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_EntityPurityCitationType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_EntityPurityListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_EntitySystematicNameType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_EntryType.write_xml(conn_bmrb, conn_tax, conn_le, ent, entry_id, xml_opt, buffw, logw, errw);
				bmr_EntryAuthorType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_EntryExperimentalMethodsType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_EntrySrcType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ExperimentFileType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ExperimentListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_FloatingChiralityType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_FloatingChiralityAssignType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_FloatingChiralitySoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_FluorescenceInstrumentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ForceConstantType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ForceConstantListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ForceConstantSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_FretExptType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_GenDistConstraintType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_GenDistConstraintCommentOrgType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_GenDistConstraintConvErrType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_GenDistConstraintExptType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_GenDistConstraintListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_GenDistConstraintParseErrType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_GenDistConstraintParseFileType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_GenDistConstraintSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_GenDistConstraintSoftwareParamType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HChemShiftConstraintType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HChemShiftConstraintExptType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HChemShiftConstraintListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HChemShiftConstraintSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HExchProtectionFactExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HExchProtectionFactSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HExchProtectionFactorType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HExchProtectionFactorListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HExchRateType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HExchRateExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HExchRateListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HExchRateSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HeteronuclNoeType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HeteronuclNoeExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HeteronuclNoeListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HeteronuclNoeSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HeteronuclT1ExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HeteronuclT1ListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HeteronuclT1SoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HeteronuclT1RhoExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HeteronuclT1RhoListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HeteronuclT1RhoSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HeteronuclT2ExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HeteronuclT2ListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HeteronuclT2SoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HistoryType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HomonuclNoeType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HomonuclNoeExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HomonuclNoeListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_HomonuclNoeSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_InteratomicDistType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_InteratomicDistanceListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_IsotopeEffectType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_IsotopeEffectExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_IsotopeEffectSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_IsotopeLabelPatternType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_JThreeBondConstraintType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_JThreeBondConstraintExptType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_JThreeBondConstraintListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_JThreeBondConstraintSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_KarplusEquationType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_LacsCharType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_LacsPlotType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_LocalStructureQualityType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MassSpecType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MassSpecCitationType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MassSpecComponentParamType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MassSpecConfigType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MassSpecRefCompdType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MassSpecRefCompdSetType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MassSpecSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MassSpectrometerListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MassSpectrometerViewType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MatchedEntriesType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MethodType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MethodCitationType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MethodFileType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MethodParamType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ModelTypeType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MsChromIonAnnotationType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MsChromatogramExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MsChromatogramIonType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MsChromatogramListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MsChromatogramParamType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MsChromatogramSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MsExptType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MsExptParamType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MsExptSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MzPrecursorIonType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MzPrecursorIonAnnotationType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MzProductIonType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MzProductIonAnnotationType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MzRatioDataListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MzRatioExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MzRatioSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_MzRatioSpectrumParamType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_NaturalSourceDbType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_NmrExperimentCitationType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_NmrExperimentFileType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_NmrExptSystematicNameType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_NmrProbeType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_NmrSpecExptType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_NmrSpectralProcSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_NmrSpectralProcessingType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_NmrSpectrometerType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_NmrSpectrometerCitationType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_NmrSpectrometerListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_NmrSpectrometerProbeType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_NmrSpectrometerProbeCitationType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_NmrSpectrometerViewType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ObservedConformerType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_OrderParamType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_OrderParameterExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_OrderParameterListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_OrderParameterSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_OrgConstrFileCommentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_OtherConstraintExptType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_OtherConstraintListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_OtherConstraintSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_OtherDataType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_OtherDataExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_OtherDataSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_OtherDataTypeListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_OtherStructFeatureType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_OtherStructFeatureListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_PbCharType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_PbListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_PdbxChemCompFeatureType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_PdbxNonpolySchemeType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_PdbxPolySeqSchemeType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_PeakType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_PeakCharType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_PeakConstraintLinkType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_PeakConstraintLinkListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_PeakGeneralCharType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_PeakRowFormatType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_PhParamType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_PhParamListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_PhTitrResultType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_PhTitrationExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_PhTitrationListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_PhTitrationSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_RdcType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_RdcConstraintType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_RdcConstraintCommentOrgType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_RdcConstraintConvErrType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_RdcConstraintExptType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_RdcConstraintListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_RdcConstraintParseErrType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_RdcConstraintParseFileType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_RdcConstraintSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_RdcExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_RdcListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_RdcSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_RecouplingPulseSequenceType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_RelatedEntriesType.write_xml(conn_bmrb, conn_tax, conn_le, ent, entry_id, xml_opt, buffw, logw, errw);
				bmr_ReleaseType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_RepConfType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_RepConfRefinementType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_RepConfSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_RepCoordinateDetailsType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_RepresentativeConformerType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ResonanceType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ResonanceAssignmentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ResonanceCovalentLinkType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_ResonanceLinkerListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SampleType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SampleCitationType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SampleComponentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SampleComponentAtomIsotopeType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SampleConditionCitationType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SampleConditionListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SampleConditionVariableType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SaxsConstraintType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SaxsConstraintExptType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SaxsConstraintListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SaxsConstraintSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SaxsExptType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SecondaryStructType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SecondaryStructListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SecondaryStructSelType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SgProjectType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SoftwareAppliedHistoryType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SoftwareAppliedListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SoftwareAppliedMethodsType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SoftwareCitationType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SoftwareSpecificInfoType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SoftwareSpecificInfoListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SpectralAcqParamType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SpectralDensityType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SpectralDensityExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SpectralDensityListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SpectralDensitySoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SpectralDimType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SpectralDimTransferType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SpectralPeakListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SpectralPeakSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SpectralProcessingParamType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SpectralTransitionType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SpectralTransitionCharType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SpectralTransitionGeneralCharType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SpinSystemType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SpinSystemLinkType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_StructAnnoCharType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_StructAnnoSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_StructAsymType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_StructClassificationType.write_xml(conn_bmrb, conn_tax, conn_le, sc, entry_id, xml_opt, buffw, logw, errw);
				bmr_StructImageType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_StructKeywordsType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_StructureAnnotationType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_StructureInteractionType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_StructureInteractionListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_StudyType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_StudyEntryListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_StudyKeywordType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_StudyListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SubsystemBiologicalFunctionType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SubsystemCitationType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SubsystemCommonNameType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SubsystemComponentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SubsystemDbLinkType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SubsystemKeywordType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SubsystemTypeType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_SystematicChemShiftOffsetType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_T1Type.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_T1RhoType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_T2Type.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TaConstraintCommentOrgType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TaConstraintConvErrType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TaConstraintParseErrType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TaConstraintParseFileType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TaskType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TensorType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TensorListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TerminalResidueType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TertiaryStructType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TertiaryStructElementListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TertiaryStructElementSelType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TheoreticalAutoRelaxationType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TheoreticalAutoRelaxationExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TheoreticalAutoRelaxationListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TheoreticalAutoRelaxationSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TheoreticalChemShiftType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TheoreticalChemShiftListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TheoreticalCouplingConstantType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TheoreticalCouplingConstantExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TheoreticalCouplingConstantListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TheoreticalCouplingConstantSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TheoreticalCrossCorrelationDdType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TheoreticalCrossCorrelationDdExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TheoreticalCrossCorrelationDdListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TheoreticalCrossCorrelationDdSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TheoreticalHeteronuclNoeType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TheoreticalHeteronuclNoeExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TheoreticalHeteronuclNoeListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TheoreticalHeteronuclNoeSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TheoreticalHeteronuclT1ExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TheoreticalHeteronuclT1ListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TheoreticalHeteronuclT1SoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TheoreticalHeteronuclT2ExperimentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TheoreticalHeteronuclT2ListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TheoreticalHeteronuclT2SoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TheoreticalT1Type.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TheoreticalT2Type.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TorsionAngleType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TorsionAngleConstraintType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TorsionAngleConstraintListType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TorsionAngleConstraintSoftwareType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_TorsionAngleConstraintsExptType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_VendorType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);
				bmr_XrayInstrumentType.write_xml(conn_bmrb, conn_tax, conn_le, entry_id, xml_opt, buffw, logw, errw);

				buffw.write("</BMRBx:datablock>\n");

				buffw.close();
				filew.close();

				logw.close();

				if (log_file.length() == 0)
					log_file.delete();

				System.out.println(xml_base_name + " done.");

				written = true;

			}

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		if (!written && !bmr_Util_Main.validate_xml)
			return;

		if (!bmr_Util_Main.validate_xml || !xml_file.exists()) {

			try {

				if (errw != null) {

					errw.close();

					if (err_file.length() == 0)
						err_file.delete();

				}

			} catch (IOException e) {
				e.printStackTrace();
			}

			return;
		}

		if (xml_file.exists())
			validator.exec(xml_base_name, errw);

		try {

			if (errw != null)
				errw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		if (err_file.length() != 0)
			return;

		err_file.delete();

		Statement state = null;
		ResultSet rset = null;

		try {

			java.sql.Date release_date = null;
			java.sql.Date entity_date = null;

			String date = null;

			String query = new String("select \"Date\" from \"Release\" where \"Entry_ID\"='" + entry_id + "' order by \"Date\" desc");

			state = conn_bmrb.createStatement();
			rset = state.executeQuery(query);

			release_date = null;

			while (rset.next()) {

				String _date = rset.getString("Date");

				if (_date == null || _date.isEmpty() || _date.equals(".") || _date.equals("?"))
					break;

				release_date = rset.getDate("Date");

				if (release_date != null)
					break;

			}

			query = new String("select \"DB_query_revised_last_date\" from \"Entity\" where \"Entry_ID\"='" + entry_id + "' order by \"DB_query_revised_last_date\" desc");

			rset = state.executeQuery(query);

			entity_date = null;

			while (rset.next()) {

				String _date = rset.getString("DB_query_revised_last_date");

				if (_date == null || _date.isEmpty() || _date.equals(".") || _date.equals("?"))
					break;

				entity_date = rset.getDate("DB_query_revised_last_date");

				if (entity_date != null)
					break;

			}

			if (release_date != null && entity_date != null) {

				if (entity_date.after(release_date))
					date = entity_date.toString();
				else
					date = release_date.toString();

			}

			else if (entity_date != null)
				date = entity_date.toString();

			else if (release_date != null)
				date = release_date.toString();

			File rel_file = new File(rel_dir_name, rel_base_name);

			try {

				FileWriter filew = new FileWriter(rel_file);
				filew.write(date + "\n");
				filew.close();

			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			} finally {

				if (rset != null)
					rset.close();

				if (state != null)
					state.close();

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
