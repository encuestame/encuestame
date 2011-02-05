dojo.provide("encuestame.org.core.commons.error.ErrorHandler");

dojo.require("dojox.widget.Dialog");
dojo.require("encuestame.org.core.commons.error.AbstractErrorHandler");
dojo.require("dijit._Templated");
dojo.require("dijit._Widget");

dojo.declare(
    "encuestame.org.core.commons.error.ErrorHandler",
    [dijit._Widget, dijit._Templated],{

        widgetsInTemplate: true,

        templatePath: dojo.moduleUrl("encuestame.org.core.commons.error", "template/error.inc"),

        type : "Error",

        postCreate: function() {
            console.debug("postCreate");
            //this.inherited(arguments);
        }
    }
);