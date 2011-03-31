dojo.provide("encuestame.org.core.admon.location.Locations");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");

dojo.declare(
    "encuestame.org.core.admon.location.Locations",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.admon.location", "template/location.inc"),

        widgetsInTemplate: true,

        postMixInProperties: function(){

        },

        postCreate: function() {

        }
    }
);
