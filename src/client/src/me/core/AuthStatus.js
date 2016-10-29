////////////////////////////////////////////////////////////////////////////////
// /*
//  * Copyright 2015 encuestame
//  *
//  *  Licensed under the Apache License, Version 2.0 (the "License");
//  *  you may not use this file except in compliance with the License.
//  *  You may obtain a copy of the License at
//  *
//  *       http://www.apache.org/licenses/LICENSE-2.0
//  *
//  *  Unless required by applicable law or agreed to in writing, software
//  *  distributed under the License is distributed on an "AS IS" BASIS,
//  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  *  See the License for the specific language governing permissions and
//  *  limitations under the License.
//  *
////////////////////////////////////////////////////////////////////////////////

/***
 *  @author juanpicado19D0Tgm@ilDOTcom
 *  @version 1.5.2
 *  @module Core
 *  @namespace Widget
 *  @class AuthStatus
 *  @requires
 */
define( [
	"dojo/_base/declare", "dojo/topic", "dojo/_base/lang", "me/web/widget/signup/LoginDialog", "me/core/enme"
], function( declare, topic, _lang, LoginDialog, _ENME ) {

	var AuthStatus = declare( null, {

		// 60 seconds by default
		time: 60000,

		expired_session: false,

		/**
		 * Constructor
		 * @method constructor
		 */
		constructor: function() {
			var openLoginDialog = function() {
				var login = new LoginDialog({
					expired_session: true,
					action_url: _ENME.config( "contextPath" ) + "/user/signin/authenticate",
					callbackSuccessLogin: _lang.hitch( this, function() {
						this.expired_session = false;
					})
				});
			};
			setInterval( function() {
				var load = dojo.hitch( this, function( data ) {
					if ( !data.loggedIn && !this.expired_session ) {
						openLoginDialog();
						this.expired_session = true;
					}
				});
				var error =  _lang.hitch( this, function( error ) {});
				_ENME.xhr.post("encuestame.admon.status", {}, load, error, _lang.hitch( this, function() {}), false );
			}, this.time );
		}
	});

	return AuthStatus;
});
