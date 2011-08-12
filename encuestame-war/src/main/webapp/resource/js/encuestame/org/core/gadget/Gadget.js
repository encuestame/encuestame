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