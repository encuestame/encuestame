# How to Build Encuestame


### Source Code

To get the last source code version run this (git is required). Encuestame use git submodules, we need initialize after pull all the code.

````
git clone git://github.com/encuestame/encuestame.git
git fetch
git checkout v1.5.6
````

### Build with Maven (master branch)

Building this version requires Apache Maven 3. We suggest version 3.0.4 or higher. There exist 2 ways to build Encuestame, please read more about it. After get source code is necessary move to top level and run following command:

```bash
./build.sh
```

After build, it's ready to run with jetty server, follow this guide.

If you want deploy on web container you can find WAR file on (after run mvn install on top level):

```bash
enme-war/enme-tomcat-app/target/encuestame.war
```