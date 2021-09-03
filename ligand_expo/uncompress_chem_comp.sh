#!/bin/bash

SRC_DIR=pdbjplus/data/cc/xml
DST_DIR=components-pub-xml

mkdir -p $DST_DIR

rm -f $DST_DIR/*
cp -f $SRC_DIR/* $DST_DIR

find $DST_DIR -maxdepth 1 -name '*.gz' -exec gunzip {} + > /dev/null

