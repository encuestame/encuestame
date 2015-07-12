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
 *  @module Websocket
 *  @namespace support
 *  @class Websocket
 */
define(["dojo",
         "me/core/enme",
        'dojo/domReady!'], function(
            dojo,
            _ENME) {

        var _options = {
          // Should we check the connection status immediatly on page load.
          checkOnLoad: true,

          // Should we monitor AJAX requests to help decide if we have a connection.
          interceptRequests: true,

          // Should we automatically retest periodically when the connection is down (set to false to disable).
          reconnect: {
            // How many seconds should we wait before rechecking.
            initialDelay: 3
            // How long should we wait between retries.
            //delay: (1.5 * last delay, capped at 1 hour)
          },

          // Should we store and attempt to remake requests which fail while the connection is down.
          requests: true,

          // Should we show a snake game while the connection is down to keep the user entertained?
          // It's not included in the normal build, you should bring in js/snake.js in addition to
          // offline.min.js.
          game: false
        };  

        var OFFLINE = function(options) {
            this.executions = [];
            this.bindExecutions(options);
        };

        OFFLINE.prototype = {
           
            /**
             * Add additional executions
             * @property functions [Array]
             */
            bindExecutions : function(functions) {
                for (var key in functions) {
                   if (functions.hasOwnProperty(key)) {
                      Offline.on(key, functions[key], this);
                   }
                }
            }
        }

        return OFFLINE;
    }
);