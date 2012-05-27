dojo.provide("encuestame.org.core.commons.rated.Comments");

dojo.require('encuestame.org.core.commons');
dojo.require("encuestame.org.core.commons.rated.RatedOperations");
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
dojo.require("encuestame.org.core.shared.utils.ToggleText");
dojo.require("encuestame.org.core.commons.rated.LikeRate");

/**
 * A widget reference of comment.
 */
dojo.declare(
    "encuestame.org.core.commons.rated.Comments",
    [encuestame.org.core.commons.rated.RatedOperations], {

    templatePath: dojo.moduleUrl("encuestame.org.core.commons.rated", "templates/rate.html"),

    /*
     *
     */
    service : encuestame.service.list.rate.comments,

    /*
     *
     */
    _key : ["topComments"],

    /*
     *
     */
     _createItem : function(item) {
         var widget = new encuestame.org.core.commons.rated.Comment({
             data : item
         });
         return widget.domNode;
     },

     /*
      * comment params.
      */
     getParams : function() {
         return { commentOption : "", max : 10 };
     }

});

/**
 * Short comment widget.
 */
dojo.declare(
        "encuestame.org.core.commons.rated.Comment",
        [encuestame.org.main.EnmeMainLayoutWidget], {

        data : null,
        
        limit_comment : 100,

        /**
         * template.
         */
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.rated", "templates/comment-item.html"),

        /**
         * executed before render template.
         */
        postMixInProperties: function() {
            if (this.data != null ) {
				this.data.likeVote = ENME.shortAmmount(this.data.likeVote);
				this.data.dislike_vote = ENME.shortAmmount(this.data.dislike_vote);
				//format : 2012-05-27 
				this.data.created_at = ENME.fromNow(this.data.created_at, "YYYY-MM-DD");
            }
        },
        
        /**
         * Post create life cycle.
         */
        postCreate : function () {
        	this._positive.appendChild(this._createLinkRate(true, false, this.data.likeVote));
        	this._negative.appendChild(this._createLinkRate(false, true, this.data.dislike_vote));
        },
        
        /**
         * Create a link rate widget.
         * @param value
         * @returns
         */
        _createLinkRate : function(positive, negative, value) {
        	var widget = new encuestame.org.core.commons.rated.LikeRate({
        		positive : positive,
        		value : value,
        		negative : negative
        	});
        	return widget.domNode;
        },
});
