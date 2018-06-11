#!/bin/bash

if [ -d ../BMRBoTool ] || [ -d ../../BMRBoTool ] ; then
 BMRBO_TOOL_HOME=../BMRBoTool
elif [ -e VERSION ] ; then
 BMRBO_TOOL_HOME=../bmrbo-tool-`cat VERSION` # Please edit me.
elif [ -e ../VERSION ] ; then
 BMRBO_TOOL_HOME=../bmrbo-tool-`cat ../VERSION` # Please edit me.
fi

