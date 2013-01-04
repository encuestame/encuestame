#!/bin/bash
mvn -DMAVEN_OPTS="-Xmx1024m -Xms512m" -Djetty.port=8080 jetty:run