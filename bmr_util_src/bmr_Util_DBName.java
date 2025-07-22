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

public class bmr_Util_DBName {

	public static String guessDbName(String val_name, String accession_code) {

		if (accession_code.matches("^[A-NR-Z][0-9][A-Z][A-Z0-9][A-Z0-9][0-9]$") || accession_code.matches("^[OPQ][0-9][A-Z0-9][A-Z0-9][A-Z0-9][0-9]$"))
			val_name = "SP";

		if (accession_code.matches("^[HNTRW][0-9][0-9][0-9][0-9][0-9]$") || accession_code.matches("^A[AIW]|^B[EFGIMQU]|^C[ABDFKNOVX]|^D[NRTVWY]|^E[BCEGHLSVWXY]|^F[CDEFGKL]|^G[DEHORTW]|^H[OS]|^J[GKZ][0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "GB";

		if (accession_code.matches("^U[0-9][0-9][0-9][0-9][0-9]$") || accession_code.matches("^A[FY]|^DQ|^E[FU]|^FJ|^G[QU]|^H[MQ]|^J[FNQX]|^KC[0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "GB";

		if (accession_code.matches("^AE|^C[PY][0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "GB";

		if (accession_code.matches("^B[0-9][0-9][0-9][0-9][0-9]$") || accession_code.matches("^A[QZ]|^B[HZ]|^C[CEGLWZ]|^D[UX]|^E[DIJKRT]|^F[HI]|^GS|^H[NR]|^J[JMSY][0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "GB";

		if (accession_code.matches("^AC|^DP[0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "GB";

		if (accession_code.matches("^I[0-9][0-9][0-9][0-9][0-9]$") || accession_code.matches("^AR|^DZ|^EA|^G[CPVXYZ]|^H[JKL][0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "GB";

		if (accession_code.matches("^G[0-9][0-9][0-9][0-9][0-9]$") || accession_code.matches("^BV|^GF[0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "GB";

		if (accession_code.matches("^BK[0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "GB";

		if (accession_code.matches("^BL|^G[JK][0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "GB";

		if (accession_code.matches("^EZ|^HP|^J[ILOPRTUVW]|^KA[0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "GB";

		if (accession_code.matches("^S[0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "GB";

		if (accession_code.matches("^AD[0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "GB";

		if (accession_code.matches("^AH[0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "GB";

		if (accession_code.matches("^AS[0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "GB";

		if (accession_code.matches("^BC[0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "GB";

		if (accession_code.matches("^BT[0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "GB";

		if (accession_code.matches("^[JKLM][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "GB";

		if (accession_code.matches("^A[A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9]+"))
			val_name = "GB";

		if (accession_code.matches("^D[A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9]+"))
			val_name = "GB";

		if (accession_code.matches("^G[A-Z][A-Z][A-Z][0-9][0-9]$"))
			val_name = "GB";

		if (accession_code.matches("^A[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "GB";

		if (accession_code.matches("^D[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "GB";

		if (accession_code.matches("^E[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "GB";

		if (accession_code.matches("^H[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "GB";

		if (accession_code.matches("^J[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "GB";

		if (accession_code.matches("^BA|^DF|^DG[0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^C[0-9][0-9][0-9][0-9][0-9]$") || accession_code.matches("^A[TUV]|^B[BJPWY]|^C[IJ]|^D[ABCK]|^F[SY]|^H[XY][0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^D[0-9][0-9][0-9][0-9][0-9]$") || accession_code.matches("^AB[0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^AP[0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^BS[0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^AG|^D[EH]|^FT|^GA[0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^AK[0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^E[0-9][0-9][0-9][0-9][0-9]$") || accession_code.matches("^BD|^D[DIJLM]|^F[UVWZ]|^GB|^H[VWZ][0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^BR[0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^H[TU][0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^FX[0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^B[A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9]+"))
			val_name = "DBJ";

		if (accession_code.matches("^E[A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9]+"))
			val_name = "DBJ";

		if (accession_code.matches("^A[A-Z][A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^B[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^F[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^G[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^I[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^AN[0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "EMBL";

		if (accession_code.matches("^F[0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "EMBL";

		if (accession_code.matches("^[VXYZ][0-9][0-9][0-9][0-9][0-9]$") || accession_code.matches("^A[JM]|^F[MNO]|^H[EFG][0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "EMBL";

		if (accession_code.matches("^AL|^BX|^C[RTU]|^F[PQR][0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "EMBL";

		if (accession_code.matches("^A[0-9][0-9][0-9][0-9][0-9]$") || accession_code.matches("^AX|^C[QS]|^FB|^G[MN]|^H[ABCDHI]|^J[ABCDE][0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "EMBL";

		if (accession_code.matches("^BN[0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "EMBL";

		if (accession_code.matches("^C[A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9]+"))
			val_name = "EMBL";

		if (accession_code.matches("^C[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "EMBL";

		if (accession_code.matches("^C[HM]|^DS|^E[MNPQ]|^FA|^G[GL]|^JH|^KB[0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "NCBI";

		if (accession_code.matches("^AC_[0-9][0-9][0-9][0-9][0-9][0-9]$") || accession_code.matches("^N[CG]_[0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "REF";

		if (accession_code.matches("^N[STW]_[0-9][0-9][0-9][0-9][0-9][0-9]$") || accession_code.matches("^NW_[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$") || accession_code.matches("^NZ_[A-Z][A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "REF";

		if (accession_code.matches("^[ANY]P_[0-9][0-9][0-9][0-9][0-9][0-9]$") || accession_code.matches("^[NY]P_[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "REF";

		if (accession_code.matches("^XP_[0-9][0-9][0-9][0-9][0-9][0-9]$") || accession_code.matches("^ZP_[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$") || accession_code.matches("^XP_[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "REF";

		if (accession_code.matches("^NM_[0-9][0-9][0-9][0-9][0-9][0-9]$") || accession_code.matches("^NM_[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "REF";

		if (accession_code.matches("^XM_[0-9][0-9][0-9][0-9][0-9][0-9]$") || accession_code.matches("^XM_[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "REF";

		if (accession_code.matches("^NR_[0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "REF";

		if (accession_code.matches("^XR_[0-9][0-9][0-9][0-9][0-9][0-9]$"))
			val_name = "REF";

		if ((val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && accession_code.matches("^[0-9][0-9A-Za-z][0-9A-Za-z][0-9A-Za-z]$") && (accession_code.matches("^[0-9][A-Za-z][0-9A-Za-z][0-9A-Za-z]$") || accession_code.matches("^[0-9][0-9A-Za-z][A-Za-z][0-9A-Za-z]$") || accession_code.matches("^[0-9][0-9A-Za-z][0-9A-Za-z][A-Za-z]$")))
			val_name = "PDB";

		if (accession_code.matches("^[HNTRW][0-9][0-9][0-9][0-9][0-9].[1-9]$") || accession_code.matches("^A[AIW]|^B[EFGIMQU]|^C[ABDFKNOVX]|^D[NRTVWY]|^E[BCEGHLSVWXY]|^F[CDEFGKL]|^G[DEHORTW]|^H[OS]|^J[GKZ][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "GB";

		if (accession_code.matches("^U[0-9][0-9][0-9][0-9][0-9].[1-9]$") || accession_code.matches("^A[FY]|^DQ|^E[FU]|^FJ|^G[QU]|^H[MQ]|^J[FNQX]|^KC[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "GB";

		if (accession_code.matches("^AE|^C[PY][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "GB";

		if (accession_code.matches("^B[0-9][0-9][0-9][0-9][0-9].[1-9]$") || accession_code.matches("^A[QZ]|^B[HZ]|^C[CEGLWZ]|^D[UX]|^E[DIJKRT]|^F[HI]|^GS|^H[NR]|^J[JMSY][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "GB";

		if (accession_code.matches("^AC|^DP[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "GB";

		if (accession_code.matches("^I[0-9][0-9][0-9][0-9][0-9].[1-9]$") || accession_code.matches("^AR|^DZ|^EA|^G[CPVXYZ]|^H[JKL][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "GB";

		if (accession_code.matches("^G[0-9][0-9][0-9][0-9][0-9].[1-9]$") || accession_code.matches("^BV|^GF[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "GB";

		if (accession_code.matches("^BK[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "GB";

		if (accession_code.matches("^BL|^G[JK][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "GB";

		if (accession_code.matches("^EZ|^HP|^J[ILOPRTUVW]|^KA[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "GB";

		if (accession_code.matches("^S[0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "GB";

		if (accession_code.matches("^AD[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "GB";

		if (accession_code.matches("^AH[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "GB";

		if (accession_code.matches("^AS[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "GB";

		if (accession_code.matches("^BC[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "GB";

		if (accession_code.matches("^BT[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "GB";

		if (accession_code.matches("^[JKLM][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "GB";

		if (accession_code.matches("^A[A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9]+.[1-9]"))
			val_name = "GB";

		if (accession_code.matches("^D[A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9]+.[1-9]"))
			val_name = "GB";

		if (accession_code.matches("^G[A-Z][A-Z][A-Z][0-9][0-9].[1-9]$"))
			val_name = "GB";

		if (accession_code.matches("^A[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "GB";

		if (accession_code.matches("^D[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "GB";

		if (accession_code.matches("^E[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "GB";

		if (accession_code.matches("^H[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "GB";

		if (accession_code.matches("^J[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "GB";

		if (accession_code.matches("^BA|^DF|^DG[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^C[0-9][0-9][0-9][0-9][0-9].[1-9]$") || accession_code.matches("^A[TUV]|^B[BJPWY]|^C[IJ]|^D[ABCK]|^F[SY]|^H[XY][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^D[0-9][0-9][0-9][0-9][0-9].[1-9]$") || accession_code.matches("^AB[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^AP[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^BS[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^AG|^D[EH]|^FT|^GA[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^AK[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^E[0-9][0-9][0-9][0-9][0-9].[1-9]$") || accession_code.matches("^BD|^D[DIJLM]|^F[UVWZ]|^GB|^H[VWZ][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^BR[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^H[TU][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^FX[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^B[A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9]+.[1-9]"))
			val_name = "DBJ";

		if (accession_code.matches("^E[A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9]+.[1-9]"))
			val_name = "DBJ";

		if (accession_code.matches("^A[A-Z][A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^B[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^F[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^G[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^I[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "DBJ";

		if (accession_code.matches("^AN[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "EMBL";

		if (accession_code.matches("^F[0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "EMBL";

		if (accession_code.matches("^[VXYZ][0-9][0-9][0-9][0-9][0-9].[1-9]$") || accession_code.matches("^A[JM]|^F[MNO]|^H[EFG][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "EMBL";

		if (accession_code.matches("^AL|^BX|^C[RTU]|^F[PQR][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "EMBL";

		if (accession_code.matches("^A[0-9][0-9][0-9][0-9][0-9].[1-9]$") || accession_code.matches("^AX|^C[QS]|^FB|^G[MN]|^H[ABCDHI]|^J[ABCDE][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "EMBL";

		if (accession_code.matches("^BN[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "EMBL";

		if (accession_code.matches("^C[A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9]+.[1-9]"))
			val_name = "EMBL";

		if (accession_code.matches("^C[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "EMBL";

		if (accession_code.matches("^C[HM]|^DS|^E[MNPQ]|^FA|^G[GL]|^JH|^KB[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "NCBI";

		if (accession_code.matches("^AC_[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$") || accession_code.matches("^N[CG]_[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "REF";

		if (accession_code.matches("^N[STW]_[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$") || accession_code.matches("^NW_[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$") || accession_code.matches("^NZ_[A-Z][A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "REF";

		if (accession_code.matches("^[ANY]P_[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$") || accession_code.matches("^[NY]P_[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "REF";

		if (accession_code.matches("^XP_[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$") || accession_code.matches("^ZP_[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$") || accession_code.matches("^XP_[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "REF";

		if (accession_code.matches("^NM_[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$") || accession_code.matches("^NM_[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "REF";

		if (accession_code.matches("^XM_[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$") || accession_code.matches("^XM_[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "REF";

		if (accession_code.matches("^NR_[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "REF";

		if (accession_code.matches("^XR_[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = "REF";

		if ((val_name == null || val_name.isEmpty() || val_name.equals(".") || val_name.equals("?")) && accession_code.matches("^[0-9][0-9A-Za-z][0-9A-Za-z][0-9A-Za-z]_[A-Za-z]$") && (accession_code.matches("^[0-9][A-Za-z][0-9A-Za-z][0-9A-Za-z]_[A-Za-z]$") || accession_code.matches("^[0-9][0-9A-Za-z][A-Za-z][0-9A-Za-z]_[A-Za-z]$") || accession_code.matches("^[0-9][0-9A-Za-z][0-9A-Za-z][A-Za-z]_[A-Za-z]$")))
			val_name = "PDB";

		if (val_name != null && val_name.equalsIgnoreCase("PDB") && accession_code.matches("^[1-2][0-9][0-9][0-9][0-9]$"))
			val_name = "BMRB";

		if (val_name != null && val_name.equals("NCBI") && !accession_code.matches("^C[HM]|^DS|^E[MNPQ]|^FA|^G[GL]|^JH|^KB[0-9][0-9][0-9][0-9][0-9][0-9]$") && !accession_code.matches("^C[HM]|^DS|^E[MNPQ]|^FA|^G[GL]|^JH|^KB[0-9][0-9][0-9][0-9][0-9][0-9].[1-9]$"))
			val_name = null;		return val_name;
	}
}
