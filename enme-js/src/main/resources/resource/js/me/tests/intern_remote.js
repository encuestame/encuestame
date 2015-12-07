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
define( [ "./intern_common" ], function( common ) {
	common.environments = [
		{ browserName: "internet explorer", version: "11", platform: "WIN8", fixSessionCapabilities: false },
		{ browserName: "firefox", version: "29", platform: [ "ANY" ], timeout:"300", idleTimeout:"300", fixSessionCapabilities: false },
		{ browserName: "firefox", version: "37", platform: [ "ANY" ], timeout:"300", idleTimeout:"300", fixSessionCapabilities: false },
		{ browserName: "chrome", version: "35", platform: [ "ANY" ], timeout:"300", idleTimeout:"300", fixSessionCapabilities: false },
		{ browserName: "chrome", timeout:"300", idleTimeout:"300", fixSessionCapabilities: false }
	];
	common.capabilities = {
		"browserstack.debug": true,
		"project": "encuestame intern test",
		"idleTimeout": "300",
		"idle-timeout": "300"
	};
	common.tunnel = "BrowserStackTunnel";
	common.useLoader = {
		"host-browser": "../../js/dojo/dojo.js"
	};
	return common;
});
