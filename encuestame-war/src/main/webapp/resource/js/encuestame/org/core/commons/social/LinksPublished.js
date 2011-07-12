dojo.provide("encuestame.org.core.commons.social.LinksPublished");

dojo.require("dijit.form.ValidationTextBox");
dojo.require("dijit.form.Textarea");
dojo.require("dijit.form.Select");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.Form");
dojo.require("encuestame.org.core.commons.dialog.Dialog");
dojo.require("encuestame.org.core.commons.dialog.Confirm");

dojo.require("dojo.hash");

dojo.declare(
    "encuestame.org.core.commons.social.LinksPublished",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/linksPublished.html"),

        widgetsInTemplate: true,

        /*
         * type.
         */
        type : "TWEETPOLL",

        itemId : null,

        /*
         * post create.
         */
        postCreate : function() {
            console.debug("data", this.type);
            console.debug("data", this.itemId);
            if (this.type == null) {
                console.error("type is null");
            } else {
                if(this.type == encuestame.surveys[0]){
                    this._loadLinks(this.itemId, encuestame.surveys[0]);
                } else if(this.type == encuestame.surveys[1]){
                    //TODO: future
                } else if(this.type == encuestame.surveys[2]){
                    //TODO: future
                }
            }
        },

        /*
         *
         */
        _loadLinks : function(id, type){
            var params = {
                    "id" : id,
                    "type" : type
           };
           console.debug("params", params);
           var load = dojo.hitch(this, function(data){
               console.debug(data);
           });
           var error = function(error) {
               this.autosave = true;
               console.debug("error", error);
           };
           encuestame.service.xhrGet(
                   encuestame.service.social.links.loadByType, params, load, error);
        }
});