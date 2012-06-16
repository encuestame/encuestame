# This script create demo war site of encuestame
mvn clean
mvn install -Dmaven.test.skip=true -Pdemo -DMAVEN_OPTS="-Xmx1024m -Xms512m"o
