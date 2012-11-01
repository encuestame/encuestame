# This script create release assembly of encuestame
mvn clean
mvn install -Dmaven.test.skip=true
pushd enme-assembly
mvn clean
mvn -Drelease=true install
popd
