dojo.provide("encuestame.org.class.commons.dashboard.Dashboard");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");

dojo.declare(
    "encuestame.org.class.commons.dashboard.Dashboard",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.class.commons.dashboard", "template/dashboard.inc"),

        widgetsInTemplate: true,

        postMixInProperties: function(){

        },

        postCreate: function() {

        },
    }
);
