dojo.provide("encuestame.org.core.commons.social.SocialAccounts");

dojo.require("dijit.form.ValidationTextBox");
dojo.require("dijit.form.Textarea");
dojo.require("dijit.form.Select");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.Form");

dojo.require("dojo.hash");

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

            widgetsInTemplate: true,

            templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/socialButton.inc"),

            postCreate : function(){
                var hash = dojo.queryToObject(dojo.hash());
                if (hash.provider && hash.provider == this.id) {
                    this._loadAccountInterface(hash.provider);
                }

            },

            _loadAccountInterface : function(id){
                console.debug("_loadAccountInterface ", id.toLowerCase()+"Detail");
                var widget = dijit.byId(id.toLowerCase()+"Detail");
                console.debug("widget ", widget);
                dojo.publish("/encuestame/social/change", [widget]);
                dojo.publish("/encuestame/social/"+id+"/loadAccounts");
            },

            _click : function(event){
                var hash = dojo.queryToObject(dojo.hash());
                console.debug("click button");
                this._loadAccountInterface(this.id);
                params = {
                   provider : this.id
                };
                dojo.hash(dojo.objectToQuery(params));
            }
 });

dojo.declare(
        "encuestame.org.core.commons.social.SocialAccountDetail",
        [dijit._Widget, dijit._Templated],{

            widgetsInTemplate: true,

            _menu : false,

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
                        encuestame.service.list.allSocialAccount, {}, load, error);
            },


            _openAuthorizeWindow : function(url){
                window.open(
                        url,
                        "_blank",
                        "fullscreen=yes,"
                        + " location=yes,"
                        + " menubar=yes,"
                        + " resizable=yes,"
                        + " scrollbars=yes,"
                        + " titlebar=yes,"
                        + " toolbar=yes,width=850,height=470", false);
            },


            _printListOfAccounts : function(listAccounts){

             }
 });

dojo.declare(
        "encuestame.org.core.commons.social.SocialAccountTwitterDetail",
        [encuestame.org.core.commons.social.SocialAccountDetail],{

            templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/twitterAccounts.inc"),

            key : "twitter",

            widgetsInTemplate: true,

            postCreate : function(){
                dojo.subscribe("/encuestame/social/twitter/loadAccounts", this, "_callTwitterAccounts");
                this.inherited(arguments);
            },

            _callTwitterAccounts : function(){
                this._callListSocialAccounts();
            },

            _callAuthorizeUrl : function(){
                var load = dojo.hitch(this, function(data){
                   console.debug("data", data);
                   this._openAuthorizeWindow(data.success.url);
                });
                var error = function(error) {
                    console.debug("error", error);
                };
                encuestame.service.xhrGet(encuestame.service.social.twitter.authorize, {}, load, error);
            },

            /*
             *
             */
            _autorizeOpen : function(event){
                dojo.stopEvent(event);
                console.debug("_autorizeOpen");
                this._callAuthorizeUrl();
            },

            /*
             * validate pin.
             */
            _validatePin : function(event){
                dojo.stopEvent(event);
                var load = dojo.hitch(this, function(data){
                    console.debug("data", data);
                 });
                 var error = function(error) {
                     console.debug("error", error);
                 };
                 var pin =  dijit.byId("_pin").get('value');
                 if (pin != undefined || pin != null) {
                     encuestame.service.xhrGet(encuestame.service.social.twitter.confirm,
                                {pin : pin}, load, error);
                 } else {
                     console.warn("PIN NOT VALID", pin);
                 }
            },

            _autorize : function(event){
                dojo.stopEvent(event);
                if (this._menu) {
                    this._addTitle.innerHTML = "Authorize Account";
                    dojo.addClass(dijit.byId("twitterNewAccountForm").domNode, "defaultDisplayHide");
                    dojo.addClass(dijit.byId("_pin").domNode, "defaultDisplayHide");
                    dojo.addClass(dijit.byId("_validate").domNode, "defaultDisplayHide");
                    dojo.removeClass(dijit.byId("_authorize").domNode, "defaultDisplayHide");
                } else {
                    dojo.removeClass(dijit.byId("twitterNewAccountForm").domNode, "defaultDisplayHide");
                    dojo.removeClass(dijit.byId("_pin").domNode, "defaultDisplayHide");
                    dojo.removeClass(dijit.byId("_validate").domNode, "defaultDisplayHide");
                    dojo.addClass(dijit.byId("_authorize").domNode, "defaultDisplayHide");
                    this._addTitle.innerHTML = "Cancel Authorize Account";
                }
                this._menu = !this._menu;
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
                 var widget = new encuestame.org.core.commons.social.SocialAccountRow(
                         {
                             account : twitterAccount
                          }
                         );
                 console.debug("_createTwitterAccount", widget);
                 return widget;
             }
 });

dojo.declare(
        "encuestame.org.core.commons.social.SocialAccountRow",
        [dijit._Widget, dijit._Templated],{

            templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/socialAccountRow.inc"),

            account : null,

            widgetsInTemplate: true,

            _secrets : false,

            type : "twitter",

            postCreate : function(){
              console.debug("account", this.account);
              dojo.subscribe("/encuestame/social/account/row/show", this, function(widget){
                  console.debug(this.id, widget.id);
                  if (this.id != widget.id) {
                      console.debug("hiding ...", widget.id);
                      this._secrets = true;
                      this._showHideAction();
                  } else {

                  }
              });
            },

            _disableSocialAccount : function(){

            },

            _removeSocialAccount : function(){

            },

            _remove : function(event){
                dojo.stopEvent(event);

            },

            _changeStatusAccount : function(){
                dojo.stopEvent(event);
            },

            _showHideAction : function(){
                console.debug("_showHideAction ", this);
                if(this._secrets){
                    dojo.addClass(this._secretView, "defaultDisplayHide");
                } else {
                    dojo.removeClass(this._secretView, "defaultDisplayHide");
                }
                this._secrets = !this._secrets;
            },

            _showHideSecrets : function(event){
                dojo.stopEvent(event);
                this._showHideAction();
                dojo.publish("/encuestame/social/account/row/show", [this]);
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
