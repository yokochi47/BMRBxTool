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
NMRSTAR_DIC_DIR=http://$BMRB_URL/svn/nmr-star-dictionary/bmrb_only_files/adit_input
NMRSTAR_DIC_DIR=https://raw.githubusercontent.com/uwbmrb/nmr-star-dictionary/7061314ffb32ba56b92392d7453cea1495d48db0
NMRSTAR_DIC_FILE=NMR-STAR.dic

DIC_PREFIX=mmcif_nmr-star
NAME_SPACE=BMRBx

rm -f $NMRSTAR_DIC_FILE

wget -c $NMRSTAR_DIC_DIR/$NMRSTAR_DIC_FILE --no-check-certificate
tr -d '\r' < $NMRSTAR_DIC_FILE > $NMRSTAR_DIC_FILE~
sed -e 's/^   _pdbx_item_enumeration_details/#  _pdbx_item_enumeration_details/' $NMRSTAR_DIC_FILE~ > $NMRSTAR_DIC_FILE
rm -f $NMRSTAR_DIC_FILE~

patch -N < $NMRSTAR_DIC_FILE.patch

rm -f $NMRSTAR_DIC_FILE.rej $NMRSTAR_DIC_FILE-*.log

DictToSdb -ddlFile mmcif_nmr-star_ddl.dic -dictFile $NMRSTAR_DIC_FILE -dictSdbFile $DIC_PREFIX.sdb -ec | grep '^Info:' > dict2sdb.info

DictObjFileCreator -dictSdbFile $DIC_PREFIX.sdb -o $DIC_PREFIX.odb
Dict2XMLSchema -dictName $DIC_PREFIX.dic -df $DIC_PREFIX.odb -ns $NAME_SPACE -prefix $DIC_PREFIX

if [ -e $DIC_PREFIX.xsd ] ; then

 rm -f $DIC_PREFIX.xsd

fi

if [ -e $NMRSTAR_DIC_FILE ] ; then

 arg=(`tr -d '\r' < $NMRSTAR_DIC_FILE | grep dictionary.version`)
 DIC_VERSION=${arg[1]}

fi

sed '2,6d' $DIC_PREFIX-v$DIC_VERSION.xsd |\
grep -v "enumeration value=\"\"" |\
sed -e 's/<xsd:element name=\"seq_homology_expectation_val\" minOccurs=\"0\" maxOccurs=\"1\" nillable=\"true\" type=\"xsd:decimal\">/<xsd:element name=\"seq_homology_expectation_val\" minOccurs=\"0\" maxOccurs=\"1\" nillable=\"true\" type=\"xsd:double\">/' > $DIC_PREFIX-v$DIC_VERSION.xsd~
mv -f $DIC_PREFIX-v$DIC_VERSION.xsd~ $DIC_PREFIX-v$DIC_VERSION.xsd

source ../scripts/db-user.sh

BMRB_MIRROR=("www.bmrb.wisc.edu" "bmrb.pdbj.org") # "bmrb.cerm.unifi.it")

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

  cmp=`echo "$time > $delay" | bc 2> /dev/null`

  if [ "$cmp" = 0 ] ; then

   server_alive=`curl -I $url -m 5 2> /dev/null`

   if [ $? == 0 ] ; then

    BMRB_URL=$url
    delay=$time

   fi

  fi

 else
  echo $url: timed out.
 fi

 let i++

done

echo
echo "$BMRB_URL was selected."

java -classpath ../xsd-ann.jar:../extlibs/* XSD_ann --home .. --user-bmrb $DB_USER --url-mirror $BMRB_URL --dic-ver $DIC_VERSION

SAXON=../extlibs/saxon9he.jar

APPEND_XSD_XSL=append_xsd.xsl

java -jar $SAXON -s:$DIC_PREFIX-v$DIC_VERSION.xsd -xsl:$APPEND_XSD_XSL -o:$DIC_PREFIX-v$DIC_VERSION.xsd~

if [ `which xmllint 2> /dev/null` ] ; then
 xmllint --format $DIC_PREFIX-v$DIC_VERSION.xsd~ > $DIC_PREFIX-v$DIC_VERSION.xsd
fi

mv -f $DIC_PREFIX-v$DIC_VERSION.xsd~ $DIC_PREFIX-v$DIC_VERSION.xsd

sed -i -e "3,2h; s/http:\/\/pdbml.pdb.org/https:\/\/bmrbpub.pdbj.org/g" $DIC_PREFIX-v$DIC_VERSION.xsd
sed -i -e "s/xsd:integer/xsd:int/g" $DIC_PREFIX-v$DIC_VERSION.xsd

ln -s $DIC_PREFIX-v$DIC_VERSION.xsd $DIC_PREFIX.xsd

scomp $DIC_PREFIX.xsd -out mmcifNmrStar.jar -compiler `which javac`

