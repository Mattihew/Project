#!/usr/bin/env bash
COL="\e[4;96m"
NC="\e[0m"
jarFile="project-control.jar"
choice=$1
while [ "${choice}" != "c" ] && [ "${choice}" != "t" ]
do
    echo "which file to edit?"
    echo -e "${COL}c${NC}: config.properties"
    echo -e "${COL}t${NC}: triggers.json"
    echo -en "choice: ${COL}"
    read -n 1 choice
    echo -e "${NC}"
done

if [ "${choice}" = "c" ]
then
    file="config.properties"
    fileType="sh"
elif [ "${choice}" = "t" ]
then
    file="triggers.json"
    fileType="json"
fi
jar -xvf ${jarFile} "${file}"
nano -ltY ${fileType} "${file}"
jar -uvf ${jarFile} "${file}"
rm -f "${file}"
