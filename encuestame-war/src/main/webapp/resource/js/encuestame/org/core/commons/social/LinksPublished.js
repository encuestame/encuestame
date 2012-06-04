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
    [encuestame.org.main.EnmeMainLayoutWidget],{
        
    	/**
    	 * Template.
    	 */
    	templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/linksPublished.html"),


        /**
         * Type.
         */
        type : "TWEETPOLL",

        /**
         * Item Id.
         */
        itemId : null,
        
        /**
         * 
         */
        hasthag : "",

        /*
         * post create.
         */
        postCreate : function() {
            if (this.type == null) {
                EMNE.log("type is null");
            } else {
                if(this.type == encuestame.surveys[0]){
                    this._loadLinks(this.itemId, encuestame.surveys[0]);
                } else if(this.type == encuestame.surveys[1]){
                    //TODO: future
                } else if(this.type == encuestame.surveys[2]){
                    //TODO: future
                } else if(this.type == encuestame.surveys[3]){
                    this._loadLinks(this.hasthag, encuestame.surveys[3]);
                }
            }
        },

        /**
         *
         */
        _loadLinks : function(id, type){
            var params = {
                    "id" : id,
                    "type" : type
           };
           //console.debug("params", params);
           var load = dojo.hitch(this, function(data){
               var links = data.success.links;
               if (links.length > 0) {
                   dojo.forEach(links,
                           dojo.hitch(this,function(item) {
                             this._createLink(item);
                           }));
               } else {
                   this._showNoLinksMessage();
               }
           });
           var error = function(error) {
               this.autosave = true;
               EMNE.log("error", error);
           };
           encuestame.service.xhrGet(
                   encuestame.service.social.links.loadByType, params, load, error);
        },

        /**
         *
         */
        _showNoLinksMessage : function() {
            var message = dojo.doc.createElement("h2");
            message.innerHTML = "No Links Refered.";
            this._items.appendChild(message);
        },

        /**
         * Create link.
         * @param data link data.
         */
        _createLink : function(data){
            var widget = new encuestame.org.core.commons.social.LinksPublishedItem(
            		{
            			social : data.provider_social, 
            			link : data.link_url,
            			text : data.publishd_text,
            			date : data.published_date
            		});
            this._items.appendChild(widget.domNode);
        }
});

/**
 * Represents a social item external link.
 */
dojo.declare(
        "encuestame.org.core.commons.social.LinksPublishedItem",
        [encuestame.org.main.EnmeMainLayoutWidget],{
        	
        	/**
        	 * Template.
        	 */
            templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/linksPublishedItem.html"),
            
            /**
             * Social data.
             */
            social : null,
            
            /**
             * Date of publication
             */
            date : "",
            
            /**
             * Text of publication
             */
            text : "",
            
            /**
             * Default link.
             */
            link : "#",
            
            /**
             * Triggered before render the template.
             */
            postMixInProperties : function() {
            	if ( this.date) {
            		this.date = ENME.fromNow(this.date, "YYYY-MM-DD");
            	}
            },

            /*
             * post create.
             */
            postCreate : function() {
                this._image.src = encuestame.social.shortPicture(this.social);
            }

});