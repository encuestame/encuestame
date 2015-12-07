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

define( [
	"intern!object",
	"intern/chai!assert",
	"me/core/_base/_config"
], function( registerSuite, assert, _config ) {

	var store;

	registerSuite({
		name: "Config Test",

		setup: function() {
			_config.merge({
				param1: 0
			});
		},

		merge: function() {
			_config.merge({
				param1: 1
			});
			assert.strictEqual( _config.get( "param1" ), 1, "Param 1 should be override" );
		},

		get: function() {
			var path = _config.get( "contextPath" );
			assert.strictEqual( path, "../../json", "context path by default /" );
		},

		set: function() {
			_config.set( "test", 1 );
			var path = _config.get( "test" );
			assert.strictEqual( path, 1 );
		}
	});
});
