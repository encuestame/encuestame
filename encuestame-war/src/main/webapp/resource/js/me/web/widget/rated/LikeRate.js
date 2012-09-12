dojo.provide("encuestame.org.core.commons.rated.LikeRate");

dojo.require("encuestame.org.main.EnmeMainLayoutWidget");

dojo.declare(
        "encuestame.org.core.commons.rated.LikeRate",
        [encuestame.org.main.EnmeMainLayoutWidget], {

          /*
         * template.
         */
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.rated", "templates/likeRate.html"),
        
        value : 0,

        negative : false,

        positive : false,
        
        postMixInProperties: function() {

        },


        postCreate : function() {
        	this._value.innerHTML = this.value;
            if (this.positive && this.negative) {
                //nothing to do.
            } else if (this.positive) {
                dojo.addClass(this._likeRate, "positive");
            } else if (this.negative) {
                dojo.addClass(this._likeRate, "negative");
            }
        }
});