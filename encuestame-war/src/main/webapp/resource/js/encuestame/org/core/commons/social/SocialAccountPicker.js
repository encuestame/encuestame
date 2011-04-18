dojo.provide("encuestame.org.core.commons.social.SocialAccountPicker");

dojo.require("encuestame.org.core.commons.dialog.Dialog");
dojo.require("encuestame.org.core.commons.dialog.Confirm");

dojo.require("dojo.hash");

dojo.declare(
        "encuestame.org.core.commons.social.SocialAccountPicker",
        [dijit._Widget, dijit._Templated],{

        templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/socialAccountPicker.html"),

        widgetsInTemplate: true,

        _listSocialAccounts : null,

        arrayAccounts : [],

        postCreate : function(){
            dojo.subscribe("/encuestame/tweetpoll/twitter/accounts", this, "showAccounts");
            console.debug("ACCOUNTSSSSSS");
            this._loadSocialConfirmedAccounts();
            console.debug("ACCOUNTSSSSSS2222");
        },

        _loadSocialConfirmedAccounts : function(){
           var load = dojo.hitch(this, function(data){
                this._listSocialAccounts = data.success.items;
                dojo.empty(this._listSocialAccounts);
            });
            var error = function(error) {
                console.debug("error", error);
            };
            encuestame.service.xhrGet(
                    encuestame.service.list.socialAccounts, {}, load, error);
       },

       showAccounts : function(){
           dojo.forEach(
                   this._listSocialAccounts,
                   dojo.hitch(this, function(data, index) {
                     if (data.typeAccount == "TWITTER") {
                         this.createTwitterAccount(data);
                     } else if(data.typeAccount == "IDENTICA"){

                     }
               }));
       },

       createTwitterAccount : function(data){
           var widget = new encuestame.org.core.commons.social.TwitterAccount({account : data});
           this._tweetPublishAccounts.appendChild(widget.domNode);
           this.arrayAccounts.push(widget);
       },

       createIdentiCaAccount : function(){
          //future.
       }
});

/*
 * Twitter Account.
 */
dojo.declare(
        "encuestame.org.core.commons.social.SocialPickerAccount",
        [dijit._Widget, dijit._Templated],{

        templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/twitterAccount.html"),

        account : {},

        widgetsInTemplate: true,

        selected : false,

        postCreate : function(){
        },

        _select : function(b){
            this.selected = b;
         }
 });

/*
 * Identi.ca account.
 */
dojo.declare(
        "encuestame.org.core.commons.social.TwitterAccount",
        [encuestame.org.core.commons.social.SocialPickerAccount],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/socialAccountPickerTwitter.html"),
        postCreate : function(){
        }
 });

