dojo.provide("encuestame.org.core.commons.dashboard.Dashboard");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");

dojo.declare(
    "encuestame.org.core.commons.dashboard.Dashboard",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.dashboard", "template/dashboard.html"),

        widgetsInTemplate: true,

        /*
         * Post create.
         */
        postCreate: function() {

        },

        _createNewDashBoard : function(){},
        _switchDashBard : function(){},
        _addGadgetToDashBard : function(){},
        _addGadgetToDashBard : function(){},
    }
);
