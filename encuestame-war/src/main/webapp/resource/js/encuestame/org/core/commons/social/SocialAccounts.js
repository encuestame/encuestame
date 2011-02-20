dojo.provide("encuestame.org.core.commons.social.SocialAccounts");

dojo.declare(
    "encuestame.org.core.commons.social.SocialAccounts",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/socialAccounts.inc"),

        widgetsInTemplate: true,

        domain : "",

        socialList : ["Twitter", "Facebook", "LinkedIn"],

        tempData : ["vBsv4g7o65SriyNRVVzt5g", "9c1wPMRfbGCnDA6JaWzFNx5kqWuJuV0FsOR5mtVbc"],

        postCreate : function(){
              console.debug("domain", this.domain);
              this._callAuthorizeUrl();
        },

        _callAuthorizeUrl : function(){
            var i = false;
            var load = dojo.hitch(this, function(data){
               console.debug("data", data);
            });
            var params = {
                consumerKey : this.tempData[0],
                consumerSecret : this.tempData[1]
            };
            var error = function(error) {
                console.debug("error", error);
            };
            encuestame.service.xhrGet(encuestame.service.social.twitter.authorize, params, load, error);
        }
});
