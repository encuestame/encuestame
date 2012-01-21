# This script create demo war site of encuestame
mvn clean
mvn install -Pdemo -DMAVEN_OPTS="-Xmx1024m -Xms512m"o
