#!/bin/bash
set -e

ssh root@topred.fr "screen -S RedCraftTestServer -p 0 -X stuff \"^Msay plugin update started^M\""
echo "Compiling ..."
export SCREENDIR=$HOME/.screen
JAVA_HOME=/usr/java/openjdk/jdk-16.0.1+9
mvn -B package | tee -a /dev/tty | grep --line-buffered "\[INFO\]" | while read line; do
  screen -dm ssh -n root@topred.fr "screen -S RedCraftTestServer -p 0 -X stuff \"^Msay ${line} ^M\""
done
echo "Uploading ..."
#Path to plugin target file
scp target/RedCraftProtect-0.0.1-SNAPSHOT.jar root@topred.fr:RedCraftTestServer/plugins/ >>/dev/null
echo "Refreshing the plugin ..."
ssh root@topred.fr "screen -S RedCraftTestServer -p 0 -X stuff \"^Mplugman reload RedCraftProtect^M\""
ssh root@topred.fr "screen -S RedCraftTestServer -p 0 -X stuff \"^Msay plugin update finished^M\""
echo "Done !"
