# This script create release assembly of encuestame
mvn clean
mvn install -Pproduction
pushd enme-assembly
mvn clean
mvn -Drelease=true install
popd
