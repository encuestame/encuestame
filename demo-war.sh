# This script create demo war site of encuestame
mvn clean
mvn install -Pdemo  -Dmaven.test.skip=true
