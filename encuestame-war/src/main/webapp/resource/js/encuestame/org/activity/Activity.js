/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
dojo.provide("encuestame.org.activity.Activity");

dojo.require("dojox.cometd");
dojo.require("dojox.cometd.timestamp");
dojo.require("dojox.cometd.ack");
dojo.require("dojox.cometd.reload");

dojo.declare("encuestame.org.activity.Activity", null, {


        ackEnabled : false,

        /*
         * comet refecen.
         */
        cometd : null,

        /*
         * subscription.
         */
        _subscription : null,

        /*
         * conected flag.
         */
        _connected : false,

        /*
         *
         */
        constructor: function(ackEnabled){
                this.ackEnabled = ackEnabled;
                this.cometd = dojox.cometd;
                this.cometd.ackEnabled = this.ackEnabled;
                this._init();
        },

        // The idempotent method
        _refresh : function(){
            this._appUnsubscribe();
            this._appSubscribe();
        },

        _appUnsubscribe: function (){
            if (this._subscription) {
              this.cometd.unsubscribe(this._subscription);
            }
            this._subscription = null;
        },

        _appSubscribe : function(service){
            this._subscription = this.cometd.subscribe(service, function() {
              //console.debug("subscribe");
            });
        },

        /*
         *
         */
        _init: function(){
           dojo.addOnLoad(dojo.hitch(this, function() {
             var cometURL = location.protocol
               + "//"
               + location.host
               + config.contextPath + "/activity";
               //configure cometd.
               this.cometd.configure({
                 url : cometURL, //url
                 logLevel : config.activity.levelDebug, //log level
                 maxConnections : config.activity.maxConnections,
                 maxNetworkDelay : config.activity.maxNetworkDelay,
                 jsonDebug : true  //json debug enabled
             });
             this.cometd.addListener('/meta/handshake', dojo.hitch(this,this._metaHandshake));
             this.cometd.addListener('/meta/connect', dojo.hitch(this,this._metaConnect));
             this.cometd.onListenerException = function(exception, subscriptionHandle, isListener, message){
                 console.info("onListenerException", exception);
                 console.info("onListenerException", subscriptionHandle);
                 console.info("onListenerException", isListener);
                 console.info("onListenerException", message);
                 // Uh-oh, something went wrong, disable this listener/subscriber
                 // Object "this" points to the CometD object
                 if (isListener) {
                     this.removeListener(subscriptionHandle);
                 } else {
                     this.unsubscribe(subscriptionHandle);
                 }
             }
             this.handshake();
           }))

          // disconnect when the page unloads
          dojo.addOnUnload(dojo.hitch(this, function() {
              this.cometd.reload();
              this.cometd.disconnect(true);
          }));
        },

        //
        _metaConnect : function(message) {
            if (this.cometd.isDisconnected()) {
                this._connected = false;
                this._connectionClosed();
                return;
            }
            var wasConnected =  this._connected;
            this._connected = message.successful === true;
            if (!wasConnected &&  this._connected) {
                this._connectionEstablished();
            } else if (wasConnected && ! this._connected) {
                this._connectionBroken();
            }
        },

        // function invoked when first contacting the server and
        // when the server has lost the state of this client
        _metaHandshake : function (handshake) {
            if (handshake.successful === true) {
                //console.debug("_metaHandshake");
            }
        },

        /*
         *
         */
        handshake : function(){
          this.cometd.handshake();
        },

         _connectionEstablished : function() {
            console.info('CometD Connection Established');
        },

        _connectionBroken : function() {
            console.info('CometD Connection Broken');
        },

         _connectionClosed : function() {
           console.info('CometD Connection Closed');
         }
});
