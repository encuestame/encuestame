README.txt

If you want to build and deploy from source follow these instructions:

   http://wiki.encuestame.org/display/DOC/How+to+build+Encuestame


Fast Deploy

encuestame have this modules:

 encuestame-utils: Bean utils
 encuestame-persistence Domains and Dao
 encuestame-core: core classes
 encuestame-bussiness app business logic
 encuestame-mvc: Controller module
 encuestame-war:  The encuestame webapp, create war and jetty server

To checkout the last source code version run this.

    git clone git://github.com/encuestame/encuestame.git

Building this version requires Apache Maven 2 to build. Version 2.0.10 or higher is suggested.

After get source code is necesary move to top level and  run following command:

    ant build - to get dojo source code, maybe you need change parameters on buildDojo.sh

    mvn install - to install app

After doing this, you can run jetty server running following command:

    cd encuestame-war/
    mvn jetty:run

If you want deploy on webcontainer you can find WAR file on (after run mvn install on top level):

    encuestame-war/target/encuestame-{version}.war
