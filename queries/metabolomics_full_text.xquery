declare namespace BMRBx='http://bmrbpub.protein.osaka-u.ac.jp/schema/mmcif_nmr-star.xsd';

for $elem in //*
where $elem/*[text() contains text {'tyrosine', 'phospho'} any]
return $elem
