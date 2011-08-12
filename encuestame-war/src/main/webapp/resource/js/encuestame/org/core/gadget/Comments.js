dojo.provide("encuestame.org.core.gadget.Comments");

dojo.require("encuestame.org.core.gadget.Gadget");

dojo.declare(
    "encuestame.org.core.gadget.Comments",
    [encuestame.org.core.gadget.Gadget],{
        templatePath: dojo.moduleUrl("encuestame.org.core.gadget", "template/comments.html"),


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
            return '/service/comments/get';
        },

        /*
         * override.
         */
        _updateStream : function(message){
            console.info("stream ...", message);
        }

});