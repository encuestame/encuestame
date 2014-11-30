#!/bin/bash
mvn -DMAVEN_OPTS="-Xmx1024m -Xms512m" clean install -Pproduction -Dmaven.test.skip=true