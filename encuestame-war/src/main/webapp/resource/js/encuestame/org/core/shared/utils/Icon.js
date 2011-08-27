dojo.provide("encuestame.org.core.shared.utils.Icon");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.Tooltip");
dojo.require('encuestame.org.core.commons');

dojo.declare(
    "encuestame.org.core.shared.utils.Icon",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.shared.utils", "template/icon.html"),

        /** Allow other widgets in the template. **/
        widgetsInTemplate: true,

        src : "#",

        title : "icon",

        height : "16",

        width : "16",

        /*
        *
        */
       postMixInProperties: function(){
           var src = this.src;
           this.src = encuestame.contextDefault+"/resources/images/icons/"+src;
           this.title = src;
       },

        postCreate : function(){

        }
});
