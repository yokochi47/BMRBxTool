declare namespace BMRBx='https://bmrbpub.pdbj.org/schema/mmcif_nmr-star.xsd';

for $elem in //*
where $elem/*[text() contains text {'tyrosine', 'phospho'} any]
return $elem
