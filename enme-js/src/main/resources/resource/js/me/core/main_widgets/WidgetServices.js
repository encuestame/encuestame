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
 *  @author juanpicado19D0Tgm@il.com
 *  @version 1.146
 *  @module Services
 *  @namespace Widget
 *  @class WidgetServices
 */
define( [ "dojo/_base/declare",
     "me/web/widget/dialog/ModalBox",
     "dijit/registry",
     "me/core/enme" ], function(
    declare,
    ModalBox,
    registry,
    _ENME ) {

  return declare( null, {

      /**
       * Context path from global variable
       */
      __contextPath: window.config ? ( config.contextPath || "/" ) : "/",

      /**
       * Return the url service
       * @method getURLService
       */
      getURLService: function() {
        return _ENME.xhr;
      },

      /**
       * Create a modal box.
       * @method _createModalBox
       * @private
       */
      _createModalBox: function( type, handler ) {

        //FUTURE: replace by Dojo Dialog
        var modal = dojo.byId("modal-box");
        if ( modal !== null ) {
           var modalBox = new ModalBox( dojo.byId("modal-box"), type, dojo.hitch( handler ) );
           return modalBox;
        } else {
           return null;
        }
      },

      /**
       * Unload customized support.
       * @method unLoadSupport
       */
      unLoadSupport: function( message ) {
          window.onbeforeunload = function() {
                  return message;
          };
      },

      /**
       * Cancel unload support.
       * @method cancelUnLoadSupport
       */
      cancelUnLoadSupport: function() {
          window.onbeforeunload = function() {};
      },

      /**
       * Display the loading process.
       * @method loading_show
       */
      loading_show: function( message ) {
        var loading = registry.byId("loading");
        if ( loading !== null ) {
          loading.show( message );
        }
      },

      /**
       * Hide the loading process.
       * @method loading_hide
       */
      loading_hide: function() {
        var loading = registry.byId("loading");
        if ( loading !== null ) {
          loading.hide();
        }
      },

      /**
       * Publish a message on the context
       * @method publishMessage
       * @param message the message
       * @param type error, warning, info, success
       */
      publishMessage: function( message, type, desc ) {
        if ( type === "success" ) {
          this.successMesage( message, desc );
        } else if ( type === "error" ) {
          this.errorMessage( message, desc );
        } else if ( type === "warn" ) {
          this.warningMesage( message, desc );
        } else if ( type === "fatal" ) {
          this.fatalMesage( message, desc );
        }
      },

      /**
       * Display a success message.
       * @method successMesage
       */
      successMesage: function( message, description ) {
        _ENME.success( message, description );
      },

      /**
       * Display a warning message.
       * @method successMesage
       */
      warningMesage: function( message, description ) {
        _ENME.warning( message, description );
      },

      /**
       * Display a warning message.
       * @method errorMessage
       */
      errorMessage: function( message, description ) {
        _ENME.error( message, description );
      },

     /**
      * Display a fatal message.
       * @method fatalMesage
      */
     fatalMesage: function( message ) {
       _ENME.fatal( message, description );
     },

      /**
       * Display a default loader.
       * @method loadingDefaultMessage
       */
      loadingDefaultMessage: function() {
       var loading = {
             init: function() {
                 console.debug("init");
             },
             end: function() {
                     console.debug("end");
             }
       };
       return loading;
      },

      /**
       * Retrieve the default services response.
       * Succesfull === {"error":{},"success":{"r":0}}
       * or
       * Failed === {"error":{},"success":{"r":-1}}
       * @method getDefaultResponse
       * @param data
       */
      getDefaultResponse: function( data ) {
        if ("success" in data ) {
          var r = parseInt( data.success.r );
          if ( r === 0 ) {
            return true;
          } else {
            return false;
          }
        } else {
          return false;
        }
      },

      /**
       * Display a error message
       * @method errorMessage
       */
      errorMesage: function( error ) {
	        var error_message = ( error == null ? _ENME.getMessage("e_023") : error );
	        this.errorMessage( error_message, "" );
      },

      /*
       *
       */
			infoMesage: function( info ) {
				var modal = this._createModalBox("alert", null );
				if ( modal != null ) {
					 modal.show( info );
				}
	      console.warn( "error message deprecated" );
      },

      /**
       * Stop the event
       * @param e Event
       * @method stopEvent
       */
       stopEvent: function( e ) {
					if ( e ) {
					  e.preventDefault();
					}
       }

  });
});
