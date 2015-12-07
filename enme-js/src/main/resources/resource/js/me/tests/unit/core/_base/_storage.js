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

define( [
	"intern!object",
	"intern/chai!assert",
	"../../Helper",
	"me/core/_base/_storage"
], function(
	registerSuite,
	assert,
	Helper,
	_storage ) {

	registerSuite({
		name: "Base Storage Test",

		setup: function() {
			Helper.init();
		},

		"storeItem, removeItem && restoreItem Test": function() {
			_storage.storeItem("test", { a: 1 });
			var a = _storage.restoreItem("test");
			assert.isNumber( Helper.json( a ).a );
			_storage.removeItem( "test" );
			var a1 = _storage.restoreItem("test");
			assert.isNull( a1 );
		}
	});
});
