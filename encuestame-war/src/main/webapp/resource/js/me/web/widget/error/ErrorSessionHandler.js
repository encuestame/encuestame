dojo.provide("encuestame.org.core.commons.error.ErrorSessionHandler");

dojo.require("dojox.widget.Dialog");
dojo.require("encuestame.org.core.commons.error.AbstractErrorHandler");

dojo.declare(
    "encuestame.org.core.commons.error.ErrorSessionHandler",
    [encuestame.org.core.commons.error.AbstractErrorHandler],{

        widgetsInTemplate: true,

        type : "Session Error",

        postMixInProperties: function(){

        },

        postCreate: function() {

        }
    }
);