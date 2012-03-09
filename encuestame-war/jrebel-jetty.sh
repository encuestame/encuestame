#!/bin/bash
export REBEL_HOME=/Applications/ZeroTurnaround/JRebel
#export MAVEN_OPTS="-noverify -javaagent:$REBEL_HOME/jrebel.jar $MAVEN_OPTS"
export MAVEN_OPTS="-noverify -Xbootclasspath/p:$REBEL_HOME/jrebel-bootstrap.jar:$REBEL_HOME/jrebel.jar $MAVEN_OPTS"
mvn $@