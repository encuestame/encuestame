dojo.provide("encuestame.org.core.commons.social.LinksPublished");

dojo.require("dijit.form.ValidationTextBox");
dojo.require("dijit.form.Textarea");
dojo.require("dijit.form.Select");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.Form");
dojo.require("encuestame.org.core.commons.dialog.Dialog");
dojo.require("encuestame.org.core.commons.dialog.Confirm");
dojo.require("encuestame.org.core.shared.utils.CacheLinkedList");

dojo.require("dojo.hash");

dojo.declare(
    "encuestame.org.core.commons.social.LinksPublished",
    [encuestame.org.main.EnmeMainLayoutWidget, 
     encuestame.org.core.shared.utils.CacheLinkedList],{
        
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
        itemId : "",
        
        /**
         * 
         */        
        more : true,
        
        /**
         * 
         */
        property : "links",
        
        /**
         * 
         */
        hasthag : "",
        
        /**
         * Poll Navigate default parameters.
         */
        _params : { type :  null, id : null, max : 10, start : 0},


        /*
         * post create.
         */
        postCreate : function() {
            if (this.type == null || this.itemId == null) {
                EMNE.log("type is null");
            } else {
                //enable more support.
                if (this.more) {
                    this.enableMoreSupport(this._params.start, this._params.max, this._more);
                }
                this._params.id = this.itemId;
                if (this.type === ENME.CONST.TYPE_SURVEYS[0]) { // tweeptoll
                	this._params.type = ENME.CONST.TYPE_SURVEYS[0];                	
                } else if(this.type === ENME.CONST.TYPE_SURVEYS[1]) { // poll
                	this._params.type = ENME.CONST.TYPE_SURVEYS[1];
                } else if(this.type === ENME.CONST.TYPE_SURVEYS[2]) { // survey
                	this._params.type = ENME.CONST.TYPE_SURVEYS[2];
                } else if(this.type === ENME.CONST.TYPE_SURVEYS[3]) { // hashtag
                	this._params.type = ENME.CONST.TYPE_SURVEYS[3];
                } else {
                	this._params.type = "";
                }
            }
            dojo.hitch(this, this.loadItems());
        },
        
        /**
         * Function to clean _items node.
         */
        _empty : function() {
            dojo.empty(this._items);
        },
                        
        handlerError : function() {
        	EMNE.log("error", error); 
        },
        
        /**
         * customize service params.
         */
        getParams : function() {
            return this._params;
        },    
        
        /**
         * The url json service.
         * @returns
         */
        getUrl : function() {
            return encuestame.service.social.links.loadByType;
        }, 
        
        /**
         * Create a new PollNavigateItem.
         */
        processItem : function(/** poll data**/  data, /** position **/ index) {
        	 this._createLink(data);
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