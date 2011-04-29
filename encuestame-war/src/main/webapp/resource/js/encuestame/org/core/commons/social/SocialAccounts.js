dojo.provide("encuestame.org.core.commons.social.SocialAccounts");

dojo.require("dijit.form.ValidationTextBox");
dojo.require("dijit.form.Textarea");
dojo.require("dijit.form.Select");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.Form");
dojo.require("encuestame.org.core.commons.dialog.Dialog");
dojo.require("encuestame.org.core.commons.dialog.Confirm");

dojo.require("dojo.hash");

dojo.declare(
    "encuestame.org.core.commons.social.SocialAccounts",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/socialAccounts.html"),

        widgetsInTemplate: true,

        domain : "",

        postCreate : function(){
            //details
            this._createDetail("twitterDetail", "Twitter");
            this._createDetail("facebookDetail", "Facebook");
            this._createDetail("linkedinDetail", "LinkedIn");
            this._createDetail("googleDetail", "Google");
            this._createDetail("identicaDetail", "Identica");
            //this._createDetail("yahooDetail", "Yahoo"); DISABLED
            //this._createDetail("myspaceDetail", "MySpace"); DISABLED
            //buttons
            this._cretateButton("twitter", "Twitter");
            this._cretateButton("facebook", "Facebook");
            this._cretateButton("linkedin", "LinkedIn");
            this._cretateButton("google", "Google Buzz");
            this._cretateButton("identica", "Identi.ca");
            //this._cretateButton("yahoo", "Yahoo"); DISABLED
            //this._cretateButton("myspace", "MySpace"); DISABLED
        },

        /*
         *
         */
        _createDetail : function(id, provider){
            var widget = new encuestame.org.core.commons.social.SocialAccountDetail(
                    {
                        id : id,
                        socialProvider : provider
                    }
                    );
            dojo.addClass(widget.domNode, "defaultDisplayHide");
            this._detail.appendChild(widget.domNode);
        },

        /*
         *
         */
        _cretateButton : function(id, provider){
            var widget = new encuestame.org.core.commons.social.SocialButton(
                    {
                        id : id,
                        label : provider
                    }
                    );
            this._buttons.appendChild(widget.domNode);
        }
});

/*
 * Social Button Widget.
 */
dojo.declare(
        "encuestame.org.core.commons.social.SocialButton",
        [dijit._Widget, dijit._Templated],{

            label : "define label",

            widgetsInTemplate: true,

            templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/socialButton.html"),

            postCreate : function(){
                var hash = dojo.queryToObject(dojo.hash());
                if (hash.provider && hash.provider == this.id) {
                    console.info("LOADING....", this.id);
                    this._loadAccountInterface(hash.provider);
                }
            },

            _loadAccountInterface : function(id){
                //console.debug("_loadAccountInterface ", id.toLowerCase()+"Detail");
                var widget = dijit.byId(id.toLowerCase()+"Detail");
                //console.debug("widget ", widget);
                dojo.publish("/encuestame/social/change", [widget]);
                dojo.publish("/encuestame/social/"+id+"/loadAccounts");
            },

            _click : function(event){
                var hash = dojo.queryToObject(dojo.hash());
                //console.debug("click button");
                this._loadAccountInterface(this.id);
                params = {
                   provider : this.id
                };
                dojo.hash(dojo.objectToQuery(params));
            }
 });

/*
 * Social Account Detail Widget.
 */
dojo.declare(
        "encuestame.org.core.commons.social.SocialAccountDetail",
        [dijit._Widget, dijit._Templated],{

            widgetsInTemplate: true,

            templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/detailAccounts.html"),

            _menu : false,

            socialProvider : null,

            postCreate : function(){
                dojo.subscribe("/encuestame/social/change", this, function(type){
                    if (this.id == type.id) {
                        console.debug("SELECTED "+this.socialProvider);
                        dojo.removeClass(this.domNode, "defaultDisplayHide");
                        this._callListSocialAccounts();
                    } else {
                        dojo.addClass(this.domNode, "defaultDisplayHide");
                    }
                 });
                dojo.subscribe("/encuestame/social/list/reload", this, "_callListSocialAccounts");
                var myForm = dojo.byId(this._form);
                myForm.setAttribute("action",encuestame.contextDefault+"/connect/"+this.socialProvider.toLowerCase());
                var hash = dojo.queryToObject(dojo.hash());
                if (hash.refresh && hash.refresh) {
                    if(hash.successful && hash.provider == this.socialProvider){
                        console.debug("hash", hash);
                        this._callListSocialAccounts();
                    }
                }
            },

            _autorize : function(event){
                dojo.stopEvent(event);
                this._openAuthorizeWindow();
            },

            _callListSocialAccounts : function(){
                var load = dojo.hitch(this, function(data){
                    console.debug("social accounts", data);
                    this._listSocialAccounts = data.success.items;
                    dojo.empty(this._list);
                    this._printListOfAccounts(data.success.items);
                });
                var error = function(error) {
                    console.debug("error", error);
                };
                encuestame.service.xhrGet(
                        encuestame.service.list.allSocialAccount, {provider:this.socialProvider.toLowerCase()}, load, error);
            },

            /*
             * Display list of accounts on dom node.
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
              * Create twitter account row on dom node.
              */
             _createTwitterAccount : function(twitterAccount){
                 var widget = new encuestame.org.core.commons.social.SocialAccountRow(
                         {
                             account : twitterAccount
                          }
                         );
                 return widget;
             }
 });
/*
 * Social Account Row Widget.
 */
dojo.declare(
        "encuestame.org.core.commons.social.SocialAccountRow",
        [dijit._Widget, dijit._Templated],{

            templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/socialAccountRow.html"),

            account : null,

            widgetsInTemplate: true,

            _secrets : false,

            type : "twitter",

            postCreate : function(){
              console.debug("account", this.account);
              dojo.subscribe("/encuestame/social/account/row/show", this, function(widget){
                  if (this.id != widget.id) {
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

            /*
             * open dialog.
             */
            _openDialog : function(title, content){
                var myDialog = new encuestame.org.core.commons.dialog.Confirm({
                    title: title,
                    content: content,
                    style: "width: 350px"
                });
                myDialog.functionYes = dojo.hitch(this, function(){
                    this._removeAction();
                    myDialog.hide();
                });
                myDialog.show();
            },

            /*
             * Remove Action.
             */
            _removeAction : function(){
                console.debug("_removeAction");
                var load = dojo.hitch(this, function(data){
                    console.debug("data", data);
                    dojo.publish("/encuestame/social/list/reload");
                });
                var error = function(error) {
                    console.debug("error", error);
                };
                encuestame.service.xhrGet(
                       encuestame.service.social.twitter.remove,
                      {socialAccountId : this.account.accountId}, load, error);
            },

            /*
             * Remove Social Account.
             */
            _remove : function(event){
                dojo.stopEvent(event);
                this._openDialog();
                var load = dojo.hitch(this, function(data){
                    console.debug("data", data);
                });
                var error = function(error) {
                    console.debug("error", error);
                };
                console.debug("this.account", this.account);
                //encuestame.service.xhrGet(
                 //       encuestame.service.social.twitter.remove,
                  //      {socialAccountId : this.account.accountId}, load, error);
            },

            _changeStatusAccount : function(){
                dojo.stopEvent(event);
            },

            _showHideAction : function(){
                //console.debug("_showHideAction ", this);
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