#!/usr/bin/env bash
file="triggers.json"
fileType="json"
jarFile="project-control.jar"
jar -xvf ${jarFile} ${file}
nano -ltY ${fileType} ${file}
jar -uvf ${jarFile} ${file}
rm -f ${file}
