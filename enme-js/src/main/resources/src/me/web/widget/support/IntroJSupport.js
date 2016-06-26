/*
 *
 *  * Copyright 2014 encuestame
 *  *
 *  *  Licensed under the Apache License, Version 2.0 (the "License");
 *  *  you may not use this file except in compliance with the License.
 *  *  You may obtain a copy of the License at
 *  *
 *  *       http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *  Unless required by applicable law or agreed to in writing, software
 *  *  distributed under the License is distributed on an "AS IS" BASIS,
 *  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  See the License for the specific language governing permissions and
 *  *  limitations under the License.
 *
 */

define( [
        "dojo",
        "dojo/_base/declare",
        "dojo/keys",
        "dojo/Deferred",
        "dojo/dom",
        "dojo/on",
        "me/core/enme" ],
    function(
        dojo,
        declare,
        keys,
        Deferred,
        dom,
        on,
        _ENME ) {

    return declare( null, {

        /**
         *
         */
        helpSteps: [],

        /**
         * Private array to store connect event handlers.
         * @property _events
         */
        _events: [],

        /**
         * Constructor.
         */
        constructor: function() {},

        /**
         *
         */
         initHelpLinks: function( onFinish ) {

            //TODO: user Promises here
            //var dfd = new Deferred();
            var _init = dojo.hitch( this, function() {
                if ( window.introJs && _ENME.config("helpLinks") ) {
                    var intro = introJs();
                    intro.setOptions({
                        steps: this.helpSteps
                    });
                    intro.start();
                    intro.oncomplete( function() {
                        if ( onFinish ) {
                            onFinish();
                        }
                    });
                    intro.onexit( function() {
                        if ( onFinish ) {
                            onFinish();
                        }
                    });
                }
            });
            on( document, "keyup", function( e ) {
                e.preventDefault();
                if ( e.keyCode == 72 && e.ctrlKey ) {
                    _ENME.set( "helpLinks", true );
                    _init();
                }
            });
            _init();
         },

        /**
         * Update the current status of the page
         */
         updateHelpPageStatus: function( path, status ) {
            this.getURLService().put( "encuestame.service.help.status", {
                path: path,
                status: status
            }, function() {

                // Nothing if the ser
            }, function() {}, dojo.hitch( this, function() {

            }) );
         }
        });
    });
