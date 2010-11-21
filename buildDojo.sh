#!/bin/bash
DOJO_PATH=/home/jpicado/workspaceTrunk/dojo
SVN_DOJO=http://svn.dojotoolkit.org/src/tags/release-1.5.0/

echo 'Building Dojo'

function createSymbolic {
        rm encuestame-war/src/main/webapp/resource/js/dojo
        rm encuestame-war/src/main/webapp/resource/js/dojox
        rm encuestame-war/src/main/webapp/resource/js/dijit
        cp -Rf ${DOJO_PATH}/dojo encuestame-war/src/main/webapp/resource/js/
        cp -Rf ${DOJO_PATH}/dojox encuestame-war/src/main/webapp/resource/js/
        cp -Rf ${DOJO_PATH}/dijit encuestame-war/src/main/webapp/resource/js/
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
