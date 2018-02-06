declare namespace BMRBx="http://bmrbpub.protein.osaka-u.ac.jp/schema/mmcif_nmr-star.xsd";

for $atom_chem_shift in //BMRBx:atom_chem_shiftCategory/BMRBx:atom_chem_shift
where $atom_chem_shift/BMRBx:atom_id/text() = 'CZ'
return $atom_chem_shift
