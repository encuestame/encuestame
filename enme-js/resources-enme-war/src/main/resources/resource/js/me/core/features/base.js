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
	'dojo/has',
	"dojo/dom-construct"
	],
	function(has, domConstruct) {

	if (has("host-browser")) {
		return {
			geolocation: function () {
				return 'geolocation' in navigator;
			},
			websocket: function () {
				return ('WebSocket' in window && window.WebSocket.CLOSING === 2)
			},
			draganddrop: function() {
				var div = domConstruct.create("div");
				return ('draggable' in div) || ('ondragstart' in div && 'ondrop' in div);
			},
			input : {
				placeholder : function(input) {
					//TODO: future
				}
			},
			sessionstorage: function() {
				try {
					sessionStorage.setItem("a", '{}');
					sessionStorage.removeItem("a");
					return true;
				} catch(e) {
					return false;
				}
			}
		}
	} else {
		// nothing to do is not a browser environment
		return {
			geolocation: false,
			websocket: false,
			draganddrop: false,
			sessionstorage: false
		};
	}
});