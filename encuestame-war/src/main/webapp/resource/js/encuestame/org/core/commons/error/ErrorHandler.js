dojo.provide("encuestame.org.core.commons.error.ErrorHandler");

dojo.require("dojox.widget.Dialog");
dojo.require("encuestame.org.core.commons.error.AbstractErrorHandler");

dojo.declare(
    "encuestame.org.core.commons.error.ErrorHandler",
    [encuestame.org.core.commons.error.AbstractErrorHandler],{

        widgetsInTemplate: true,

        type : "Error",

        postMixInProperties: function(){

        },

        postCreate: function() {

        }
    }
);