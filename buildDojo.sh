#!/bin/bash
DOJO_PATH=dojo
SVN_DOJO=http://svn.dojotoolkit.org/src/tags/release-1.5.0/

echo 'Building Dojo'

function createSymbolic {
        if [ -d ${DOJO_PATH} ]; then
            if [ -d encuestame-war/src/main/webapp/resource/js/dojo ]; then
                rm -Rf encuestame-war/src/main/webapp/resource/js/dojo
            fi

            if [ -d encuestame-war/src/main/webapp/resource/js/dojox ]; then
                rm -Rf encuestame-war/src/main/webapp/resource/js/dojox
            fi

            if [ -d encuestame-war/src/main/webapp/resource/js/dijit ]; then
                rm -Rf encuestame-war/src/main/webapp/resource/js/dijit
            fi
            cp -Rf ${DOJO_PATH}/dojo encuestame-war/src/main/webapp/resource/js/
            cp -Rf ${DOJO_PATH}/dojox encuestame-war/src/main/webapp/resource/js/
            cp -Rf ${DOJO_PATH}/dijit encuestame-war/src/main/webapp/resource/js/
        fi
}


if [ -d ${DOJO_PATH} ]; then
    echo "Create Links"
    createSymbolic
else
    echo "Create Dir"
    mkdir ${DOJO_PATH}
    #cd ${DOJO_PATH}
    echo "SVN Checkout"
    svn co ${SVN_DOJO} ${DOJO_PATH}
    echo "Create Links"
    createSymbolic
fi
echo 'Done'
