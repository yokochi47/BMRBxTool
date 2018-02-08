#!/bin/bash

echo
echo "Do you want to clean? (y [n]) "

read ans

case $ans in
 y*|Y*)
  ;;
 *)
  echo skipped.;;
esac

rm -rf bm[rs]_xml_* bm[rs]_json_*

