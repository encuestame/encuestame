dojo.provide("encuestame.org.core.commons.social.SocialAccounts");

dojo.declare(
    "encuestame.org.core.commons.social.SocialAccounts",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/socialAccounts.inc"),

        widgetsInTemplate: true,

        domain : "",

        socialList : ["Twitter", "Facebook", "LinkedIn"],

        postCreate : function(){
              console.debug("domain", this.domain);
        }
});

dojo.declare(
        "encuestame.org.core.commons.social.SocialButton",
        [dijit._Widget, dijit._Templated],{

            label : "define label",

            templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/socialButton.inc"),

            postCreate : function(){

            },

            _click : function(event){
                console.debug("click button");
                var widget = dijit.byId(this.id.toLowerCase()+"Detail");
                console.debug("widget ", widget);
                dojo.publish("/encuestame/social/change", [widget]);
                dojo.publish("/encuestame/social/"+this.id+"/loadAccounts");
            }
 });

dojo.declare(
        "encuestame.org.core.commons.social.SocialAccountDetail",
        [dijit._Widget, dijit._Templated],{

            widgetsInTemplate: true,

            postCreate : function(){
                dojo.subscribe("/encuestame/social/change", this, function(type){
                    console.debug(this.id, type.id);
                    if(this.id == type.id){
                        console.debug("Si");
                        dojo.removeClass(this.domNode, "defaultDisplayHide");
                    } else {
                        console.debug("No");
                        dojo.addClass(this.domNode, "defaultDisplayHide");

                    }
                 });
            },

            _callListSocialAccounts : function(){
                var load = dojo.hitch(this, function(data){
                    console.debug("social accounts", data);
                    //his._listSocialAccounts = data.success.items;
                    //dojo.empty(this._tweetPublishAccounts);
                    //dojo.publish("/encuestame/tweetpoll/twitter/accounts");
                });
                var error = function(error) {
                    console.debug("error", error);
                };
                encuestame.service.xhrGet(
                        encuestame.service.list.twitterAccount, {}, load, error);
            }
 });

dojo.declare(
        "encuestame.org.core.commons.social.SocialAccountTwitterDetail",
        [encuestame.org.core.commons.social.SocialAccountDetail],{

            templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/twitterAccounts.inc"),

            key : "twitter",

            postCreate : function(){
                dojo.subscribe("/encuestame/social/twitter/loadAccounts", this, "_callTwitterAccounts");
                this.inherited(arguments);
            },

            _callTwitterAccounts : function(){
                this._callListSocialAccounts();
            },

            _callAuthorizeUrl : function(){
                var i = false;
                var load = dojo.hitch(this, function(data){
                   console.debug("data", data);
                });
                var error = function(error) {
                    console.debug("error", error);
                };
                encuestame.service.xhrGet(encuestame.service.social.twitter.authorize, params, load, error);
            }
 });

dojo.declare(
        "encuestame.org.core.commons.social.SocialAccountFacebookDetail",
        [encuestame.org.core.commons.social.SocialAccountDetail],{
            templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/facebookAccounts.inc"),

            postCreate : function(){
                this.inherited(arguments);
            }
 });


dojo.declare(
        "encuestame.org.core.commons.social.SocialAccountLinkedInDetail",
        [encuestame.org.core.commons.social.SocialAccountDetail],{
            templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/linkedInAccounts.inc"),

            postCreate : function(){
                this.inherited(arguments);
            }
 });

dojo.declare(
        "encuestame.org.core.commons.social.SocialAccountGoogleBuzzDetail",
        [encuestame.org.core.commons.social.SocialAccountDetail],{
            templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/buzzAccounts.inc"),

            postCreate : function(){
                this.inherited(arguments);
            }
 });
