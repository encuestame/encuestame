/**
 * Copyright 2015 encuestame
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

var dojoConfig = {
    async: true,
    requestProvider: 'dojo/request/registry',
    has: {
        'dojo-undef-api': 1
    }
};

define({
        environments: [{browserName: 'chrome'}],
        maxConcurrency: 2,
        capabilities: {
            'browserstack.debug' : true,
            'project' : 'encuestame intern test',
            'idleTimeout': '300',
            'idle-timeout' : '300',
            'browserstack.selenium_version': "2.45.0"
        },
        reporters : ['console'],
        tunnel: 'BrowserStackTunnel',
				useLoader: {
        'host-node': 'dojo/dojo',
            'host-browser': '../../js/dojo/dojo.js'
        },
        loader: {
            packages: [
                { name: 'me', location: 'me' },
                { name: 'dojo', location: 'js/dojo' },
                { name: 'dojox', location: 'js/dojox' },
                { name: 'dijit', location: 'js/dijit' },
                { name: 'sinon', location: 'node_modules/sinon/pkg', main: 'sinon' }
            ],
            requestProvider: 'dojo/request/registry'
        },
        suites: [ 'me/tests/all' ],
        //functionalSuites: ['me/tests/functional/all'],
        excludeInstrumentation: /^(?:tests|third-party|node_modules|web\/widgets|js\/dojo|js\/dijit|js\/dojox)\//
});
