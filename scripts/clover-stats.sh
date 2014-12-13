#!/bin/bash
mvn clover2:setup test clover2:aggregate clover2:clover -Dmaven.clover.licenseLocation=clover.license