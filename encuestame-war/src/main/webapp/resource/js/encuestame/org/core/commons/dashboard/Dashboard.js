dojo.provide("encuestame.org.core.commons.dashboard.Dashboard");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");

dojo.declare(
    "encuestame.org.core.commons.dashboard.Dashboard",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.dashboard", "template/Dashboard.html"),

        widgetsInTemplate: true,

        postMixInProperties: function(){

        },

        postCreate: function() {

        }
    }
);
