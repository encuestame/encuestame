dojo.provide("encuestame.org.core.commons.rated.RatedProfile");

dojo.require('encuestame.org.core.commons');
dojo.require("encuestame.org.core.commons.rated.RatedOperations");
dojo.require("encuestame.org.core.shared.utils.AccountPicture");

dojo.declare(
    "encuestame.org.core.commons.rated.RatedProfile",
    [encuestame.org.core.commons.rated.RatedOperations],{

    /*
     * template.
     */
     templatePath: dojo.moduleUrl("encuestame.org.core.commons.rated", "templates/profile-rate.html"),

    /*
     *
     */
    service : encuestame.service.list.rate.profile,

    /*
     *
     */
    _key : ["profile"],

    /*
     *
     */
     _createItem : function(item) {
         var widget = new encuestame.org.core.commons.rated.UsersProfile({
             data : item
         });
         return widget.domNode;
     },

     /*
      * comment params.
      */
     getParams : function() {
         return { status : true };
     }
});


dojo.declare(
    "encuestame.org.core.commons.rated.UsersProfile",
    [encuestame.org.main.EnmeMainLayoutWidget],{

     templatePath: dojo.moduleUrl("encuestame.org.core.commons.rated", "templates/profile-item.html"),

});