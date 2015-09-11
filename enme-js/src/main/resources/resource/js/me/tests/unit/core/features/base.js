/**
 * Copyright 2014 encuestame
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

/***
 *  @author juanpicado19D0Tgm@ilDOTcom
 *  @version 1.147
 *  @module ModuleName
 *  @namespace Widget
 *  @class FileName
 */

define([
	'intern!object',
	'intern/chai!assert',
	"dojo/sniff",
	'me/core/features/base'
], function (registerSuite, assert, sniff, features) {
	var store;

	registerSuite({
		name: 'HTML5 Features Detector',

		geolocation: function () {
			if (sniff("chrome") ||
				sniff("safari") ||
				sniff("android") ||
				sniff("ios") ||
				sniff("opera") ||
				sniff("mozilla")) {
				assert.strictEqual(features.geolocation(), true, 'Geolocation should be true in this browser');
				assert.strictEqual(features.websocket(), true, 'Websocket should be true in this browser');
				assert.strictEqual(features.input.placeholder(), undefined, 'Placeholder should be true in this browser');
				assert.strictEqual(features.sessionstorage(), true, 'SessionStorage should be true in this browser');
			}
			if (sniff("chrome") ||
				sniff("safari") ||
				sniff("mozilla")) {
				assert.strictEqual(features.draganddrop(), true, 'Drag and Drop should be true in this browser');
			}
		}
	});
});