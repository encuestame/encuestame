# This script create release assembly of encuestame

pushd enme-utils
mvn clean install -Dmaven.test.skip=true
popd

pushd enme-persistence
mvn clean install -Dmaven.test.skip=true
popd

pushd enme-core
mvn clean install -Dmaven.test.skip=true
popd

pushd enme-social
mvn clean install -Dmaven.test.skip=true
popd

pushd enme-oauth
mvn clean install -Dmaven.test.skip=true
popd

pushd enme-business
mvn clean install -Dmaven.test.skip=true
popd

pushd enme-mvc
mvn clean install -Dmaven.test.skip=true
popd