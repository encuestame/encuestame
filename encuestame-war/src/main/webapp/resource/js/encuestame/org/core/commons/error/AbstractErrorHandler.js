dojo.provide("encuestame.org.core.commons.error.AbstractErrorHandler");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");

dojo.declare(
    "encuestame.org.core.commons.error.AbstractErrorHandler",
    [dijit._Widget, dijit._Templated],{

        templatePath: dojo.moduleUrl("encuestame.org.core.commons.error", "template/error.inc"),

        widgetsInTemplate: true,

        type : "",

        postMixInProperties: function(){

        },

        postCreate: function() {

        }
    }
);