/*
 * Copyright 2013 encuestame
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
 *  @version 1.146
 *  @module SignUp
 *  @namespace Widgets
 *  @class UsernameValidator
 */
define( [
	"dojo/_base/declare",
	"dijit/_WidgetBase",
	"dijit/_TemplatedMixin",
	"dijit/_WidgetsInTemplateMixin",
	"me/core/main_widgets/EnmeMainLayoutWidget",
	"me/web/widget/validator/AbstractValidatorWidget",
	"me/core/enme",
	"dojo/text!me/web/widget/validator/templates/usernameValidator.html" ],
	function(
	    declare,
	    _WidgetBase,
	    _TemplatedMixin,
	    _WidgetsInTemplateMixin,
	    main_widget,
	    abstractValidatorWidget,
	    _ENME,
	     template ) {
	return declare( [ _WidgetBase, _TemplatedMixin, main_widget, abstractValidatorWidget, _WidgetsInTemplateMixin ], {

		/**
		* Template string.
		* @property
		*/
		templateString: template,

		/**
		*
		* @property
		*/
		placeholder: "Write your username",

		/**
		*
		* @method
		*/
		postCreate: function() {
		    this.inherited( arguments );
		},

		/*
		 *
		 */
		_validate: function( event ) {
		    this.inputTextValue = this._input.value;
			if ( this.validateUsername( this.inputTextValue ) ) {
			    this._loadService(
			        this.getServiceUrl(), {
			        context: this.enviroment,
			        username:  this._input.value
			    }, this.error );
			} else {
				this.isValid = false;
				var _me = {
					msg: "Username is not valid. Only characters and digits"
				};
				this._showErrorMessage( _me );
				this._additionalErrorHandler( _me );
			}
		},

		/**
		 *
		 * @method
		 */
		getServiceUrl: function() {
		    return "encuestame.service.publicService.validate.username";
		},

		/**
		 * Add suggestions
		 * @param data
		 */
		_additionalErrorHandler: function( data ) {
		    if ( data.suggestions ) {
		        if ( data.suggestions.length > 0 ) {
		            dojo.style( this._block, "display", "block");
		            dojo.empty( this._suggest );
		            dojo.style( this._suggest, "opacity", "0");
		            var fadeArgs = {
		                    node: this._suggest,
		                    duration: 500
		            };
		            dojo.fadeIn( fadeArgs ).play();
		            dojo.forEach( data.suggestions,
		                    dojo.hitch( this, function( item ) {
		                        var li = dojo.doc.createElement("li");
		                        dojo.addClass( li, "label");
		                        li.innerHTML = item;
		                        dojo.connect( li, "onclick", dojo.hitch( this, function( event ) {
		                            this._replaceUsername( item );
		                        }) );
		                        this._suggest.appendChild( li );
		            }) );
		        } else {
		            dojo.style( this._block, "display", "none");
		        }
		    }
		},

		/**
		 *
		 * @method
		 */
		_additionalSuccessHandler: function( data ) {
		    var fadeArgs = {
	            node: this._suggest,
	            duration: 500
	        };
	        dojo.fadeOut( fadeArgs ).play();
	        dojo.empty( this._suggest );
            dojo.style( this._block, "display", "none");
            dojo.style( this._suggest, "opacity", "1");
		},

		/**
		 *
		 * @method
		 */
		getValue: function() {
		    return this._input.value;
		},

		/**
		 *
		 * @method
		 */
		clear: function() {
		   this.cleanMessage();
		   this._input.value = "";
		},

		/**
		 *
		 * @method
		 */
		_replaceUsername: function( item ) {
		    dojo.style( this._block, "display", "none");
		    this._input.value = item;
		    this._loadService(
		            "encuestame.service.publicService.validate.username", {
		                context: this.enviroment,
		                username:  item
		            }, this.error );
		},

		/**
		 *
		 * @method
		 */
		error: function( error ) {
		    console.debug("error", error );
		}

    });
});
