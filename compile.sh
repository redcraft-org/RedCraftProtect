#!/bin/bash
ssh root@topred.fr "screen -S RedCraftTestServer -p 0 -X stuff \"^Msay plugin update started^M\""
echo "Compiling ..."
mvn -B package >> /dev/null
echo "Uploading ..."
#Path to plugin target file
scp target/RedCraftProtect-0.0.1-SNAPSHOT.jar root@topred.fr:RedCraftTestServer/plugins/ >> /dev/null
echo "Refreshing the plugin ..."
ssh root@topred.fr "screen -S RedCraftTestServer -p 0 -X stuff \"^Mplugman reload RedCraftProtect^M\""
ssh root@topred.fr "screen -S RedCraftTestServer -p 0 -X stuff \"^Msay plugin update finished^M\""
echo "Done !"