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
	"me/core/_base/_config",
	"me/core/support/iosOverlay",
	"me/core/support/Spinner"
],function(_config,
           iosOverlay,
           Spinner) {

	/**
	 *
	 */
	var _publish = function(message, description, type) {
		description = description === null ? '' : description;
		if (typeof(message === 'string')) {
			var image_path = _config.get('contextPath') + "/resources/images/";
			if (type === "error" || type === "fatal" || type === "warning") {
				image_path += "cross.png";
			} else  {
				image_path += "check.png";
			}
			iosOverlay({
				text: message,
				duration: 2e3,
				icon: image_path
			});
		}
	},
	messageTypes = {
		MESSAGE: "message",
		WARNING: "warning",
		ERROR: "error",
		FATAL: "fatal"
	};

	return {
		success : function (message, description) {
			_publish(message, description, messageTypes.MESSAGE);
		},

		warning : function (message, description) {
			_publish(message, description, messageTypes.WARNING);
		},

		error : function (message, description) {
			_publish(message, description, messageTypes.ERROR);
		},

		fatal : function (message, description) {
			_publish(message, description, messageTypes.FATAL);
		}
	};
});