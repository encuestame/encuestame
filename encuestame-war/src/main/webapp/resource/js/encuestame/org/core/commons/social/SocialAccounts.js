dojo.provide("encuestame.org.core.commons.social.SocialAccounts");

dojo.declare(
    "encuestame.org.core.commons.social.SocialAccounts",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/socialAccounts.inc"),

        widgetsInTemplate: true,

        domain : "",

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
                    this._printListOfAccounts(data.success.items);
                });
                var error = function(error) {
                    console.debug("error", error);
                };
                encuestame.service.xhrGet(
                        encuestame.service.list.twitterAccount, {}, load, error);
            },

            _printListOfAccounts : function(listAccounts){

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
            },

            /*
             * list twitter account.s
             */
            _printListOfAccounts : function(listAccounts){
                console.debug("list accounts", listAccounts);
                dojo.empty(this._list);
                dojo.forEach
                (listAccounts,
                    //Process elements dropped.
                    dojo.hitch(this,
                        function(account) {
                           console.debug("account", account);
                           var widget = this._createTwitterAccount(account);
                           this._list.appendChild(widget.domNode);
                    }));
             },

             /*
              *
              */
             _createTwitterAccount : function(twitterAccount){
                 var widget = new encuestame.org.core.commons.social.SocialAccountRow({account : twitterAccount});
                 console.debug("_createTwitterAccount", widget);
                 return widget;
             }
 });

dojo.declare(
        "encuestame.org.core.commons.social.SocialAccountRow",
        [dijit._Widget, dijit._Templated],{

            templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/socialAccountRow.inc")

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
