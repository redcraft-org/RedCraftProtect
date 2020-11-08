#!/bin/bash
echo "Compiling ..."
mvn -B package >>/dev/null
echo "Uploading ..."
#Path to plugin target file
scp target/RedCraftProtect-0.0.1-SNAPSHOT.jar root@topred.fr:RedProtectTestServer/plugins/ >>/dev/null
echo "Refreshing the plugin ..."
ssh root@topred.fr "screen -S RedProtect -p 0 -X stuff \"^Mplugman reload RedCraftProtect^M\""
echo "Done !"
