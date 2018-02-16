#!/bin/sh

if [ ! `which DictToSdb` ] || [ ! `which Dict2XMLSchema` ] || [ ! `which Dict2XMLSchema` ]; then

 echo "Please install MMCIF Dictionary Suite (http://sw-tools.pdb.org/)."
 exit 1

fi

if [ ! `which scomp` ] ; then

 echo "scomp: command not found..."
 echo "Please install XMLBeans (http://xmlbeans.apache.org/)."

 exit 1

fi

if [ ! `which javac` ] ; then

 echo "javac: command not found..."
 exit 1

fi

BMRB_URL=svn.bmrb.wisc.edu
NMRSTAR_DICT_DIR=http://$BMRB_URL/svn/nmr-star-dictionary/bmrb_only_files/adit_input
NMRSTAR_DICT_FILE=NMR-STAR.dic

DICT_PREFIX=mmcif_nmr-star
NAME_SPACE=BMRBx

rm -f $NMRSTAR_DICT_FILE

wget -c $NMRSTAR_DICT_DIR/$NMRSTAR_DICT_FILE --no-check-certificate
tr -d '\r' < $NMRSTAR_DICT_FILE > $NMRSTAR_DICT_FILE~
sed -e 's/^   _pdbx_item_enumeration_details/#  _pdbx_item_enumeration_details/' $NMRSTAR_DICT_FILE~ > $NMRSTAR_DICT_FILE
rm -f $NMRSTAR_DICT_FILE~

patch -N < $NMRSTAR_DICT_FILE.patch

rm -f  $NMRSTAR_DICT_FILE.rej

DictToSdb -ddlFile mmcif_nmr-star_ddl.dic -dictFile $NMRSTAR_DICT_FILE -dictSdbFile $DICT_PREFIX.sdb -ec
DictObjFileCreator -dictSdbFile $DICT_PREFIX.sdb -o $DICT_PREFIX.odb
Dict2XMLSchema -dictName $DICT_PREFIX.dic -df $DICT_PREFIX.odb -ns $NAME_SPACE -prefix $DICT_PREFIX

if [ -e $DICT_PREFIX.xsd ] ; then

 rm -f $DICT_PREFIX.xsd

fi

if [ -e $NMRSTAR_DICT_FILE ] ; then

 arg=(`grep dictionary.version $NMRSTAR_DICT_FILE`)
 DICT_VERSION=${arg[1]}

fi

echo $DICT_VERSION

sed '2,6d' $DICT_PREFIX-v$DICT_VERSION.xsd |\
grep -v "enumeration value=\"\"" |\
sed -e 's/<xsd:element name=\"seq_homology_expectation_val\" minOccurs=\"0\" maxOccurs=\"1\" nillable=\"true\" type=\"xsd:decimal\">/<xsd:element name=\"seq_homology_expectation_val\" minOccurs=\"0\" maxOccurs=\"1\" nillable=\"true\" type=\"xsd:double\">/' > $DICT_PREFIX-v$DICT_VERSION.xsd~
mv -f $DICT_PREFIX-v$DICT_VERSION.xsd~ $DICT_PREFIX-v$DICT_VERSION.xsd

source ../scripts/db-user.sh

BMRB_MIRROR=("www.bmrb.wisc.edu" "bmrb.pdbj.org" "bmrb.cerm.unifi.it")

printf "    BMRB mirror sites\t\t delay [ms]\n"
echo "-------------------------------------------"

BMRB_URL=${BMRB_MIRROR[0]}

delay=10000
i=1

for url in ${BMRB_MIRROR[@]}
do

 time=`ping -c 1 -w 10 $url | grep 'avg' | cut -d '=' -f 2 | cut -d '/' -f 2`

 if [ $? = 0 ] ; then

  printf "[%d] %s\t\t%6.1f\n" $i $url $time

  cmp=`echo "$time > $delay" | bc`

  if [ $cmp = 0 ] ; then
   BMRB_URL=$url
   delay=$time
  fi

 else
  echo $url: timed out.
 fi

 let i++

done

echo
echo "$BMRB_URL was selected."

java -classpath ../xsd-ann.jar:../extlibs/* XSD_ann --home .. --user-bmrb $DB_USER --url-mirror $BMRB_URL --dic-ver $DICT_VERSION

SAXON=../extlibs/saxon9he.jar

APPEND_XSD_XSL=append_xsd.xsl

java -jar $SAXON -s:$DICT_PREFIX-v$DICT_VERSION.xsd -xsl:$APPEND_XSD_XSL -o:$DICT_PREFIX-v$DICT_VERSION.xsd~

mv -f $DICT_PREFIX-v$DICT_VERSION.xsd~ $DICT_PREFIX-v$DICT_VERSION.xsd

ln -s $DICT_PREFIX-v$DICT_VERSION.xsd $DICT_PREFIX.xsd

scomp $DICT_PREFIX.xsd -out mmcifNmrStar.jar -compiler `which javac`

