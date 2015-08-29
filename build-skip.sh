#!/bin/bash
mvn -DMAVEN_OPTS="-Xmx1024m -Xms512m" clean install -Pproduction -Dgrunt=default -Dselenium=false -Dmaven.test.skip=true