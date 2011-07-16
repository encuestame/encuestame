#!/bin/bash
DOJO_PATH=dojo
SVN_DOJO=http://svn.dojotoolkit.org/src/tags/release-1.6.1/
FILE_DOJO=http://download.dojotoolkit.org/release-1.6.1/dojo-release-1.6.1-src.zip
FILE_NAME=dojo-release-1.6.1-src.zip

function createFromDownload {
        if [ -d ${DOJO_PATH} ]; then
            removeCurrentResources
	    copyResources  
        else
            warningNoResources      
        fi
}

function removeCurrentResources {
            if [ -d encuestame-war/src/main/webapp/resource/js/dojo ]; then
                rm -Rf encuestame-war/src/main/webapp/resource/js/dojo
            fi

            if [ -d encuestame-war/src/main/webapp/resource/js/dojox ]; then
                rm -Rf encuestame-war/src/main/webapp/resource/js/dojox
            fi

            if [ -d encuestame-war/src/main/webapp/resource/js/dijit ]; then
                rm -Rf encuestame-war/src/main/webapp/resource/js/dijit
            fi
	    if [ -d encuestame-war/src/main/webapp/resource/js/org ]; then
                rm -Rf encuestame-war/src/main/webapp/resource/js/org
            fi
}


function replaceCometDResources {
    if [ -d ${DOJO_PATH} ]; then
	    cp -Rfv lib/dojo-replace/org ${DOJO_PATH}
	    cp -v lib/dojo-replace/cometd.js ${DOJO_PATH}/dojox/ 
	    rm -Rf ${DOJO_PATH}/dojox/cometd 
	    cp -Rfv lib/dojo-replace/cometd ${DOJO_PATH}/dojox/
    else
	warningNoResources
    fi
}

function copyResources {
        removeCurrentResources
	replaceCometDResources
	if [ -d ${DOJO_PATH} ]; then
		cp -Rfv ${DOJO_PATH}/dojo encuestame-war/src/main/webapp/resource/js/
		cp -Rfv ${DOJO_PATH}/dojox encuestame-war/src/main/webapp/resource/js/
		cp -Rfv ${DOJO_PATH}/dijit encuestame-war/src/main/webapp/resource/js/
		cp -Rfv ${DOJO_PATH}/org encuestame-war/src/main/webapp/resource/js/
	else
		warningNoResources
	fi                
}

function warningNoResources {
    echo "****************************************************************"
    echo "*  WARNING !!! -- DOJO FOLDER NOT EXIST                        *"
    echo "*  http://wiki.encuestame.org/en/Build_Project for more info.  *"
    echo "****************************************************************"
    exit
}

function warningShow {
    echo "****************************************************************"
    echo "*  WARNING !!! -- DOJO 1.6 IS REQUIRED                         *"
    echo "*  Download from http://dojotoolkit.org/download               *"
    echo "*  Copy the content on root folder named DOJO                  *"   
    echo "*  After that run again this script                            *"
    echo "*  http://wiki.encuestame.org/en/Build_Project for more info.  *"
    echo "****************************************************************"
}

function svnDownload {
    if [ -d ${DOJO_PATH} ]; then
	 rm -Rf ${DOJO_PATH}
    fi	
    echo "SVN - Remove old resources"
    echo "SVN - Create work path"
    mkdir ${DOJO_PATH}
    echo "SVN - Downloading source code .."
    svn export ${SVN_DOJO} ${DOJO_PATH} --force
    echo "SVN - Copy Resources"
    copyResources
}

function fileDownload {
    if [ -d ${DOJO_PATH} ]; then
	 rm -Rf ${DOJO_PATH}
    fi	
    echo "ZIP - Download"
    wget ${FILE_DOJO}
    echo "ZIP - Create work path"
    unzip ${FILE_NAME}
    mv dojo-release-1.6.1-src ${DOJO_PATH}
    echo "ZIP - Copy Resources"
    rm ${FILE_NAME}
    copyResources
}

fileDownload
