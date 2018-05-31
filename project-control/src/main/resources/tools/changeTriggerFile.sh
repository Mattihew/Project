#!/usr/bin/env bash
COL="\e[4;96m"
NC="\e[0m"
jarFile="project-control.jar"
if [ $# = 0 ]
then
    echo "missing file name"
    exit 1
fi
jar -uvf ${jarFile} "${1}"
