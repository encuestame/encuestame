/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: open source social survey Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
dojo.provide("encuestame.org.core.gadget.Gadget");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dojo.parser");

dojo.declare(
        "encuestame.org.core.gadget.Gadget",
        [dijit._Widget, dijit._Templated],{

             timer: null,

             params : { timer : 180000}, //180000 , 900000

             postCreate : function() {
                 var subscriptionNotification = null;
                 dojo.addOnLoad(dojo.hitch(this, function(){
                 subscriptionNotification  = encuestame.activity.cometd.subscribe(this.getUrl(),
                     dojo.hitch(this, function(message) {
                         this._updateStream(message);
                   }));
                 }));
                 dojo.addOnUnload(function() {
                     if(subscriptionNotification != null){
                         encuestame.activity.cometd.unsubscribe(subscriptionNotification);
                     }
                 });
                 this.loadTimer();
                 this.callCometd();
             },

             /*
              * override.
              */
             _updateStream : function(message){

             },

            /*
             * Load Timer.
             */
            loadTimer : function(){
                var father = this;
                this.timer = new dojox.timing.Timer(90000);
                this.timer.onTick = function() {
                    father.callCometd();
                };
                this.timer.onStart = function() {
                };
                this.timer.start();
            },

            /*
             *
             */
            stopTimer : function() {
                 this.timer.stop();
            },

            /*
             *
             */
            callCometd : function() {
                encuestame.activity.cometd.startBatch();
                encuestame.activity.cometd.publish(this.getUrl(), {});
                encuestame.activity.cometd.endBatch();
            },

    });