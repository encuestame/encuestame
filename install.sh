#!/bin/bash
mvn -DMAVEN_OPTS="-Xmx1024m -Xms512m" clean install -Dmaven.test.skip=true
