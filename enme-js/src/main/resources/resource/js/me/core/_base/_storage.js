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
	"me/core/features/base",
	"dojo/_base/json"
], function( features, json ) {
	return {

		/*
		 * Store a item into session storage
		 * @method storeItem
		 * @param key
		 * @param value
		 * @param local
		 */
		storeItem: function( key, value, local ) {
			local = local || false;
			if ( typeof features !== "undefined" && features.sessionstorage ) {
				sessionStorage.setItem( key, json.toJson( value ) );
			} else {

				//TODO: save on COOKIE
			}
		},

		/*
		 * Remove a item from session storage
		 * @method removeItem
		 * @param key the key item
		 * @param local define if the source is local
		 */
		removeItem: function( key, local ) {
			local = local || false;
			if ( typeof features !== "undefined" && features.sessionstorage ) {
				sessionStorage.removeItem( key );
			} else {

				//TODO: remove on COOKIE
			}
		},

		/**
		 * @method restoreItem
		 * @param key
		 * @param local
		 */
		restoreItem: function( key, local ) {
			local = local || false;
			if ( typeof features !== "undefined" && features.sessionstorage ) {
				return sessionStorage.getItem( key );
			} else {

				//TODO: get on COOKIE
			}
		}
	};
});
