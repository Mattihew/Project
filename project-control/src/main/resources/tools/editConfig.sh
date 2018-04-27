#!/usr/bin/env bash
file="config.properties"
fileType="sh"
jarFile="project-control.jar"
jar -xvf ${jarFile} ${file}
nano -ltY ${fileType} ${file}
jar -uvf ${jarFile} ${file}
rm -f ${file}
