Encuestame Widgets
================

![Encuestame](http://encuestame.org/logos/horizontal/enuestame_horizontal_small_alpha.png)

Encuestame UI is based in Dojo Widgets, we use [Intern](http://theintern.io/) to manage the Unit and Functional testing, this is a prototype project in beta phase.

[![Build Status](https://codeship.com/projects/c26c6560-f99f-0132-2eb5-228d89dce612/status?branch=master)](https://codeship.com/projects/86837)

### Sponsors

# ![BrowserStack](http://encuestame.org/images/sponsors/browserstack/browserstack.jpg)

### Setup

1. The repository should accessible via a local HTTP server (optional).
	* Via Apache HTTP Server.
	* I would recommend use [serve](https://www.npmjs.org/package/serve)
	* Create your own serve with Node.js with [Express](http://expressjs.com/)
	* Use the *new grunt support*, see last section

2. Install node modules and grunt-cli

    ```
    npm install -g grunt-cli
    npm install
    ```

### Running tests

##### Local node tests

    ./node_modules/.bin/intern-runner config=tests/intern_local_test

Previously we need to start Selenium Server Standalone in your terminal

````
npm install selenium-standalone@latest -g
selenium-standalone install
selenium-standalone start
````
If you wish use **Chrome** or related in your configuration, it will need [ChromeDriver](http://chromedriver.storage.googleapis
.com/index.html) configured in your environment.

##### Local browser tests

I'd recommend install 'serve' is a quick way to create a webserver in either folder you are.

````
npm install -g serve
cd test-root-folder
serve
````

Navigate to
````
http://localhost:3000/node_modules/intern/client.html?config=/js/me/tests/intern_browser
````
making sure to adjust the url to match your local web server.


##### Remote node / browser tests

[Browserstack](https://www.browserstack.com/) is enabled in the [**intern_remote**](https://github.com/encuestame/enme-intern-test/blob/grunt-enme-intern/tests/intern_remote.js) config file by default.

    ./node_modules/intern/bin/intern-runner.js config=tests/intern_remote

### Grunt Support

##### Local Selenium

This task require a previous selenium running in your computer

	grunt test-local

##### Remote Selenium Test

Run the test in the saucelabs environment

	grunt test

-----------------
Subproject of [@encuestame](https://github.com/encuestame) maintened by [Juan Picado](https://github.com/juanpicado) and [Diana Morales](https://github.com/dianmorales)

