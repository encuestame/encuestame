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

// In tests/support/requestMocker.js
// http://www.sitepen.com/blog/2014/07/14/mocking-data-with-intern/
define( [
	"dojo/request/registry",
	"dojo/when"
], function( registry, when ) {
	var mocking = false,
		handles = [];

	function start() {
		if ( mocking ) {
			return;
		}

		mocking = true;

		// Set up a handler for requests to '/info' that mocks a
		// response without requesting from the server at all
		handles.push(
			registry.register( "/info", function( url, options ) {

				// Wrap using `when` to return a promise;
				// you could also delay the response
				return when({
					hello: "world"
				});
			})
	);
	}

	function stop() {
		if ( !mocking ) {
			return;
		}

		mocking = false;

		var handle;

		while ( ( handle = handles.pop() ) ) {
			handle.remove();
		}
	}

	return {
		start: start,
		stop: stop
	};
});
