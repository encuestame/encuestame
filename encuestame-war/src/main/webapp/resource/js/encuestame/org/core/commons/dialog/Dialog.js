dojo.provide("encuestame.org.core.commons.dialog.Dialog");

dojo.require("dijit.Dialog");

dojo.declare(
    "encuestame.org.core.commons.dialog.Dialog",
    [dijit.Dialog],{
        //disable drag support.
        draggable : false,
        //post create
        postCreate : function(){
            this.inherited(arguments);
            console.debug(this.templatePath);
        }
});

