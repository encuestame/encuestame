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

        arrayWidgetAccounts : [],

        postCreate : function(){
            dojo.subscribe("/encuestame/tweetpoll/twitter/accounts", this, "showListAccounts");
            this._loadSocialConfirmedAccounts();
        },

        /*
         * load all social accounts verified.
         */
        _loadSocialConfirmedAccounts : function(){
           var load = dojo.hitch(this, function(data) {
                this.arrayAccounts = data.success.items;
                dojo.empty(this._listSocialAccounts);
                console.debug("social", this.arrayAccounts);
                this.showListAccounts();
            });
            var error = function(error) {
                console.debug("error", error);
            };
            encuestame.service.xhrGet(
                    encuestame.service.list.socialAccounts, {}, load, error);
       },

       /*
        * show up list accounts.
        */
       showListAccounts : function(){
           dojo.forEach(
                   this.arrayAccounts,
                   dojo.hitch(this, function(data, index) {
                         this.createPickSocualAccount(data);
               }));
       },

       /*
        *
        */
       createPickSocualAccount : function(data){
           var widget = new encuestame.org.core.commons.social.SocialPickerAccount({account : data});
           this._listSocialAccounts.appendChild(widget.domNode);
           this.arrayWidgetAccounts.push(widget);
       }
});

/*
 * Twitter Account.
 */
dojo.declare(
        "encuestame.org.core.commons.social.SocialPickerAccount",
        [dijit._Widget, dijit._Templated],{

        templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/socialPickerAccount.html"),

        account : {},

        widgetsInTemplate: true,

        selected : false,

        postCreate : function(){
            console.debug("social", this.account);
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

