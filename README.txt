README.txt

If you want to build and deploy from source follow these instructions:

    http://wiki.encuestame.org/en/Build_Project


Fast Deploy

encuestame have 3 modules

 encuestame-utils: Bean utils
 encuestame-core: Core encuestame project
 encuestame-war:  The encuestame webapp, create war and jetty server
 encuestame-web:  Beans for webapp, (JSF, RichFaces and Webflow Beans)

To checkout the last source code version run this.

	svn co http://www.encuestame.org/svn/trunk/encuestame encuestame

If you want only code without versions
 
	svn export http://www.encuestame.org/svn/trunk/encuestame encuestame

Building this version requires Apache Maven 2 to build. Version 2.0.10 or higher is suggested.

After get source code is necesary move to top level and  run following command:

	mvn install

After doing this, you can run jetty server running following command:
 
	cd encuestame-war/
	mvn jetty:run

If you want deploy on webcontainer you can find WAR file on (after run mvn install on top level):
	
	encuestame-war/target/encuestame.war

 
