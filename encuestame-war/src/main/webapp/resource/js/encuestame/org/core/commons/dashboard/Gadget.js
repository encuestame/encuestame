dojo.provide("encuestame.org.core.commons.dashboard.Gadget");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");

dojo.declare(
    "encuestame.org.core.commons.dashboard.Gadget",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.dashboard", "template/gadget.html"),

        widgetsInTemplate: true,

        data : null,

        /*
         * Post create.
         */
        postCreate: function() {

        },

        _editConfiguration : function(){},
        _updateConfiguration : function(){},
        _removeGadget : function(){},
        _removeGadget : function(){},
        _minimizeGadget : function(){},
        _maximizeGadget : function(){},
        _savePosition : function(){}
    }
);
