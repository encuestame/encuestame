dojo.provide("encuestame.org.core.shared.utils.StandBy");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dojox.widget.Standby");
dojo.require('encuestame.org.core.commons');

dojo.declare("encuestame.org.core.shared.utils.StandBy", [ dijit._Widget,
        dijit._Templated ], {

    templatePath : dojo.moduleUrl("encuestame.org.core.shared.utils",
            "template/standBy.html"),

    /** Allow other widgets in the template. * */
    widgetsInTemplate : true,

    size : "medium",

    target : "",

    postCreate : function() {
       this.init();
    },

    init : function() {

    },

    start : function() {
        console.debug("STAND BY START", standById);
        standById.show();
    },

    stop : function() {
        console.debug("STAND BY START", standById);
        standById.hide();
    }

});
