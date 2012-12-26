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
 *  @module Activity
 *  @namespace Notifications
 *  @class Activity
 */

//require(["dojo/parser", "ready!"], function(AuthoredDialog, parser) {
//    // Parse the page
//    parser.parse();
//
//});
define(["dojo",
        "dojo/dom",
        'dojo/_base/unload',
        'dojox/cometd',
         "me/core/enme",
        'dojo/domReady!'], function(
            dojo,
            dom,
            unloader,
            cometd,
            _ENME) {

        // Disconnect when the page unloads
        unloader.addOnUnload(function() {
            cometd.disconnect(true);
        });

        /**
         * Create a Activity Object
         * @param _options a list of options to customize the connection with cometd server
         * @param wekbsocketSupport enable the websocket support
         * @constructor Activity
         */
        var Activity = function (_options, wekbsocketSupport) {
            this.options = _options;
            this.options.websocket = wekbsocketSupport || false;
            this.cometd = cometd;
            this._connected = false;
        };


        /**
         * Connect
         * @method
         */
        Activity.prototype.connect = function () {
            var parent = this;
            this.cometd.websocketEnabled = this.options.websocket;
            this.cometd .configure({
                    url: this.options.url,
                    logLevel: this.options.logLevel,
                    maxConnections : this.options.maxConnections,
                    maxNetworkDelay : this.options.maxNetworkDelay
            });

            /**
             * Function that manages the connection status with the Bayeux server
             * @method _metaConnect
             */
            var _metaConnect = function(message) {
                // check if the cometd server is disconected
                if (parent.cometd.isDisconnected()) {
                    parent._connected = false;
                    parent._connectionClosed();
                    return;
                }

                var wasConnected = parent._connected;
                parent._connected = message.successful === true;
                if (!wasConnected && parent._connected) {
                     parent._connectionEstablished();
                } else if (wasConnected && !parent._connected) {
                     parent._connectionBroken();
                }
            };

            /**
             * Function invoked when first contacting the server and when the server has lost the state of this client
             * @method _metaHandshake
             */
            var _metaHandshake = function(handshake) {
                console.warn("_metaHandshake =====", handshake);
                if (handshake.successful === true) {
                    parent.clientId = handshake.clientId;
                    cometd.batch(function() {
                        cometd.subscribe('/service/notification/status', function(message) {
                           dojo.publish('/notifications/service/messages', message.data);
                        });
                        // Publish on a service channel since the message is for the server only
                        cometd.publish('/service/notification/status', { r: '0' });
                    });
                }
            };
            this.cometd .addListener('/meta/handshake', _metaHandshake);
            this.cometd .addListener('/meta/connect', _metaConnect);
            this.cometd .handshake();
        };

        /**
         * This method is triggered when the connection with cometd server re established
         * @method _connectionEstablished
         */
        Activity.prototype._connectionEstablished = function () {
                _ENME.log('CometD Connection Established');
                dojo.publish('/activity/connection/established');
        };

        /**
         * This method is triggered when the connection with cometd server is broken
         * @method _connectionBroken
         */
        Activity.prototype._connectionBroken = function () {
                _ENME.log('CometD Connection Broken');
                dojo.publish('/activity/connection/broken');
        };

        /**
         * This method is triggered when the connection with cometd server is closed
         * @method _connectionClosed
         */
        Activity.prototype._connectionClosed = function() {
                _ENME.log('<div>CometD Connection Closed');
                dojo.publish('/activity/connection/closed');
        };

        return Activity;
    }
);


// require(['dojo/dom', 'dojo/_base/unload', 'dojox/cometd', 'dojo/domReady!'], function(dom, unloader, cometd) {



// });


///*
// ************************************************************************************
// * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
// * encuestame Development Team.
// * Licensed under the Apache Software License version 2.0
// * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
// * Unless required by applicable law or agreed to  in writing,  software  distributed
// * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
// * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
// * specific language governing permissions and limitations under the License.
// ************************************************************************************
// */
//dojo.provide("encuestame.org.activity.Activity");
//
//dojo.require("dojox.cometd");
//dojo.require("dojox.cometd.timestamp");
//dojo.require("dojox.cometd.ack");
//dojo.require("dojox.cometd.reload");
//
//dojo.declare("encuestame.org.activity.Activity", null, {
//
//
//        ackEnabled : false,
//
//        /*
//         * comet refecen.
//         */
//        cometd : null,
//
//        /*
//         * subscription.
//         */
//        _subscription : null,
//
//        /*
//         * conected flag.
//         */
//        _connected : false,
//
//        /*
//         *
//         */
//        constructor: function(ackEnabled){
//                this.ackEnabled = ackEnabled;
//                this.cometd = dojox.cometd;
//                this.cometd.ackEnabled = this.ackEnabled;
//                this._init();
//        },
//
//        // The idempotent method
//        _refresh : function(){
//            this._appUnsubscribe();
//            this._appSubscribe();
//        },
//
//        _appUnsubscribe: function (){
//            if (this._subscription) {
//              this.cometd.unsubscribe(this._subscription);
//            }
//            this._subscription = null;
//        },
//
//        _appSubscribe : function(service){
//            this._subscription = this.cometd.subscribe(service, function() {
//              //console.debug("subscribe");
//            });
//        },
//
//        /*
//         *
//         */
//        _init: function(){
//           dojo.addOnLoad(dojo.hitch(this, function() {
//             var cometURL = location.protocol
//               + "//"
//               + location.host
//               + ENME.config('contextPath') + "/activity";
//               //configure cometd.
//               this.cometd.configure({
//                 url : cometURL, //url
//                 logLevel : ENME.config('activity_levelDebug'), //log level
//                 maxConnections : ENME.config('activity_maxConnections'),
//                 maxNetworkDelay : ENME.config('activity_maxNetworkDelay'),
//                 jsonDebug : true  //json debug enabled
//             });
//             this.cometd.addListener('/meta/handshake', dojo.hitch(this,this._metaHandshake));
//             this.cometd.addListener('/meta/connect', dojo.hitch(this,this._metaConnect));
//             this.cometd.onListenerException = function(exception, subscriptionHandle, isListener, message){
//                 console.info("onListenerException", exception);
//                 console.info("onListenerException", subscriptionHandle);
//                 console.info("onListenerException", isListener);
//                 console.info("onListenerException", message);
//                 // Uh-oh, something went wrong, disable this listener/subscriber
//                 // Object "this" points to the CometD object
//                 if (isListener) {
//                     this.removeListener(subscriptionHandle);
//                 } else {
//                     this.unsubscribe(subscriptionHandle);
//                 }
//             }
//             this.handshake();
//           }))
//
//          // disconnect when the page unloads
//          dojo.addOnUnload(dojo.hitch(this, function() {
//              this.cometd.reload();
//              this.cometd.disconnect(true);
//          }));
//        },
//
//        //
//        _metaConnect : function(message) {
//            if (this.cometd.isDisconnected()) {
//                this._connected = false;
//                this._connectionClosed();
//                return;
//            }
//            var wasConnected =  this._connected;
//            this._connected = message.successful === true;
//            if (!wasConnected &&  this._connected) {
//                this._connectionEstablished();
//            } else if (wasConnected && ! this._connected) {
//                this._connectionBroken();
//            }
//        },
//
//        // function invoked when first contacting the server and
//        // when the server has lost the state of this client
//        _metaHandshake : function (handshake) {
//            if (handshake.successful === true) {
//                //console.debug("_metaHandshake");
//            }
//        },
//
//        /*
//         *
//         */
//        handshake : function(){
//          this.cometd.handshake();
//        },
//
//         _connectionEstablished : function() {
//            console.info('CometD Connection Established');
//        },
//
//        _connectionBroken : function() {
//            console.info('CometD Connection Broken');
//        },
//
//         _connectionClosed : function() {
//           console.info('CometD Connection Closed');
//         }
//});
