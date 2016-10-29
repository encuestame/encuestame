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
	"intern/chai!expect",
	"../../Helper",
	"me/core/_base/_messages"
], function(
	registerSuite,
	assert,
	expect,
	Helper,
	_messages ) {

	registerSuite({
		name: "Base Messages Test",

		setup: function() {
			Helper.init();
		},

		"success Message Test": function() {
			_messages.success( "test message", "test description" );
			var a = Helper.query( ".ui-ios-overlay" );
			expect( 1 ).to.equal( a.length );
			var b = Helper.query( "img" )[ 0 ];
			assert.match( b.src, /check/ );
			Helper.removeElement( a[ 0 ] );
		},

		"warning Message Test": function() {
			_messages.warning( "test message", "test description" );
			var a = Helper.query( ".ui-ios-overlay" );
			expect( 1 ).to.equal( a.length );
			var b = Helper.query( "img" )[ 0 ];
			assert.match( b.src, /cross/ );
			Helper.removeElement( a[ 0 ] );
		},

		"error Message Test": function() {
			_messages.error( "test message", "test description" );
			var a = Helper.query( ".ui-ios-overlay" );
			expect( 1 ).to.equal( a.length );
			var b = Helper.query( "img" )[ 0 ];
			assert.match( b.src, /cross/ );
			Helper.removeElement( a[ 0 ] );
		},

		"fatal Message Test": function() {
			_messages.fatal( "test message", "test description" );
			var a = Helper.query( ".ui-ios-overlay" );
			expect( 1 ).to.equal( a.length );
			var b = Helper.query( "img" )[ 0 ];
			assert.match( b.src, /cross/ );
			Helper.removeElement( a[ 0 ] );
		}

	});
});
