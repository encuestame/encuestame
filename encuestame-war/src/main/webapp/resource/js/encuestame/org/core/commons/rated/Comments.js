dojo.provide("encuestame.org.core.commons.rated.Comments");

dojo.require('encuestame.org.core.commons');
dojo.require("encuestame.org.core.commons.rated.RatedOperations");
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
dojo.require("encuestame.org.core.shared.utils.ToggleText");
dojo.require("encuestame.org.core.commons.rated.LikeRate");

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

dojo.declare(
        "encuestame.org.core.commons.rated.Comment",
        [encuestame.org.main.EnmeMainLayoutWidget], {

        data : null,

        /*
         * template.
         */
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.rated", "templates/comment-item.html"),

        /*
         * executed before render template.
         */
        postMixInProperties: function() {
            if (this.data != null ) {

            }
        },

        /*
         *
         */
        postCreate : function() {
        }
});
