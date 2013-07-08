#!/bin/bash

if $(type -P node &>/dev/null) ; then

	if $(type -P yuidoc &>/dev/null) ; then
	   yuidoc . -x 'dojo,dijit,dojox' -o 'doc/api/widgets'
	   #yuidoc --server 5000
	else
		echo "You need Install yuidoc, all about yuidoc in this url"
		echo "http://yui.github.com/yuidoc/"
	fi

else
	echo "You need instal NODE.JS, all about install node in the next url"
	echo "http://nodejs.org/"
fi
