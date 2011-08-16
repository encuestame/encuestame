dojo.provide("encuestame.org.core.gadget.Activity");

dojo.require("encuestame.org.core.gadget.Gadget");

dojo.declare(
    "encuestame.org.core.gadget.Activity",
    [encuestame.org.core.gadget.Gadget],{
        templatePath: dojo.moduleUrl("encuestame.org.core.gadget", "template/activity.html"),

        /*
         *
         */
        postCreate : function(){
            this.inherited(arguments);
        },

        /*
         *
         */
        getUrl : function(){
            return '/service/stream/get';
        },

        /*
         * override.
         */
        _updateStream : function(message){
            var obj = dojo.fromJson(message.data.stream);
            console.log(obj);
            console.info("stream ...", message);
        }

});