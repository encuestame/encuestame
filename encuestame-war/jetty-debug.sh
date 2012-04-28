#!/bin/bash
mvn -DMAVEN_OPTS="-Xmx1024m -Xms512m -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=4000,server=y,suspend=y" -Djetty.port=8080 jetty:run
