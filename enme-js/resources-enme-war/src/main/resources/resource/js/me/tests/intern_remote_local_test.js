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
define(['./intern_common'], function(common) {
	common.useLoader = {
		'host-node'   : 'dojo/dojo',
		'host-browser': '../../js/dojo/dojo.js'
	};
	common.environments= [
		{ browserName: 'chrome'}
	];
	common.capabilities = {
		'browserstack.debug'	: true,
		'project'				: 'encuestame intern test',
		'idleTimeout'			: '300',
		'idle-timeout'			: '300'
	};
	common.tunnel = 'BrowserStackTunnel';
	return common;
});
