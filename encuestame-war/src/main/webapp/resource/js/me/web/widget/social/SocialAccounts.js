dojo.provide("encuestame.org.core.commons.social.SocialAccounts");

dojo.require("dijit.form.ValidationTextBox");
dojo.require("dijit.form.Textarea");
dojo.require("dijit.form.Select");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.Form");
dojo.require("encuestame.org.core.commons.dialog.Dialog");
dojo.require("encuestame.org.core.commons.dialog.Confirm");
dojo.require("encuestame.org.core.shared.utils.AccountPicture");
dojo.require("encuestame.org.core.shared.utils.SettingsMenuSwitch");
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");

dojo.require("dojo.hash");

dojo.declare(
    "encuestame.org.core.commons.social.SocialAccounts",
    [encuestame.org.core.shared.utils.SettingsMenuSwitch],{
    	   
    	/**
    	 * 
    	 */
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/socialAccounts.html"),
        
        /**
         * 
         */
        domain : "",

        /**
         * 
         */
        postCreate : function() {
            //details
            this._createDetail("twitterDetail", "Twitter");
            this._createDetail("facebookDetail", "Facebook");
            this._createDetail("linkedinDetail", "LinkedIn");
            this._createDetail("googleplusDetail", "GooglePlus");
            this._createDetail("identicaDetail", "Identica");
            //this._createDetail("yahooDetail", "Yahoo"); DISABLED
            //buttons
            this._cretateButton("twitter", "Twitter");
            this._cretateButton("facebook", "Facebook");
            this._cretateButton("linkedin", "LinkedIn");
            this._cretateButton("googleplus", "Google +");
            this._cretateButton("identica", "Identi.ca");
            //this._cretateButton("yahoo", "Yahoo"); DISABLED
        },

        /*
         *
         */
        _createDetail : function(id, provider) {
            //console.debug("_createDetail", provider);
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
        _cretateButton : function(id, provider) {
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
        [encuestame.org.main.EnmeMainLayoutWidget],{

            label : "define label",

            templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/socialButton.html"),

            /**
             * 
             */
            postCreate : function() {
                dojo.subscribe("/encuestame/social/clean/buttons", this, dojo.hitch(this, function(type) {
                    dojo.removeClass(this.domNode, "selected");
                }));
                var hash = dojo.queryToObject(dojo.hash());
                if (hash.provider && hash.provider == this.id) {
                    this._loadAccountInterface(hash.provider);
                }
            },

            /**
             * 
             * @param id
             */
            _loadAccountInterface : function(id) {
                //console.debug("_loadAccountInterface ", id.toLowerCase()+"Detail");
                var widget = dijit.byId(id.toLowerCase()+"Detail");
                //console.debug("widget ", widget);
                dojo.publish("/encuestame/social/change", [widget]);
                dojo.publish("/encuestame/social/" + id + "/loadAccounts");
            },

            /**
             * 
             * @param event
             */
            _click : function(event){
                dojo.publish("/encuestame/social/clean/buttons");
                var hash = dojo.queryToObject(dojo.hash());
                //console.debug("click button");
                this._loadAccountInterface(this.id);
                params = {
                   provider : this.id
                };
                dojo.hash(dojo.objectToQuery(params));
                dojo.addClass(this.domNode, "selected");
            }
 });



/*
 * Social Account Detail Widget.
 */
dojo.declare(
        "encuestame.org.core.commons.social.SocialAccountDetail",
        [encuestame.org.main.EnmeMainLayoutWidget],{

            templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/detailAccounts.html"),

            _menu : false,

            socialProvider : null,

            postCreate : function(){
                dojo.subscribe("/encuestame/social/change", this, function(type) {
                    if (this.id == type.id) {
                        //console.debug("SELECTED "+this.socialProvider);
                        dojo.removeClass(this.domNode, "defaultDisplayHide");
                        this._callListSocialAccounts();
                    } else {
                        dojo.addClass(this.domNode, "defaultDisplayHide");
                    }
                 });
                dojo.subscribe("/encuestame/social/list/reload", this, "_callListSocialAccounts");
                var myForm = dojo.byId(this._form);
                myForm.setAttribute("action",encuestame.contextDefault + "/connect/" + this.socialProvider.toLowerCase());
                var hash = dojo.queryToObject(dojo.hash());
                if (hash.refresh && hash.refresh) {
                    if(hash.successful && hash.provider == this.socialProvider){
                        //console.debug("hash", hash);
                        this._callListSocialAccounts();
                    }
                }
            },

            /**
             * 
             * @param event
             */
            _autorize : function(event) {
                dojo.stopEvent(event);
                this._openAuthorizeWindow();
            },

            /**
             * 
             */
            _callListSocialAccounts : function() {
                var load = dojo.hitch(this, function(data) {
                    //console.debug("social accounts", data);
                    this._listSocialAccounts = data.success.items;
                    dojo.empty(this._list);
                    this._printListOfAccounts(data.success.items);
                });
                var error = function(error) {
                    //console.debug("error", error);
                };
               // console.debug("PROVIDER", this.socialProvider.toLowerCase());
                encuestame.service.xhrGet(
                        encuestame.service.list.allSocialAccount, {provider:this.socialProvider.toLowerCase()}, load, error);
            },

            /*
             * Display list of accounts on dom node.
             */
            _printListOfAccounts : function(listAccounts){
                //console.debug("list accounts", listAccounts);
                dojo.empty(this._list);
                dojo.forEach
                (listAccounts,
                    //Process elements dropped.
                    dojo.hitch(this,
                        function(account) {
                           //console.debug("account", account);
                           var widget = this._createTwitterAccount(account);
                           this._list.appendChild(widget.domNode);
                    }));
             },

             /*
              * Create twitter account row on dom node.
              */
             _createTwitterAccount : function(account){
                 var widget = new encuestame.org.core.commons.social.SocialAccountRow(
                         {
                             account : account
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
        [encuestame.org.main.EnmeMainLayoutWidget],{

            templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/socialAccountRow.html"),

            account : null,

            /**
             * i18n Message.
             */
            i18nMessage : {
            	settings_config_profile_email : ENME.getMessage("settings_config_profile_email"),
            	settings_config_profile_complete_name : ENME.getMessage("settings_config_profile_complete_name"),
            	settings_social_tp_published_whith_this_account : ENME.getMessage("settings_social_tp_published_whith_this_account"),
            	settings_social_pll_published_whith_this_account : ENME.getMessage("settings_social_pll_published_whith_this_account"),
            	settings_social_su_published_whith_this_account : ENME.getMessage("settings_social_su_published_whith_this_account"),
            	settings_social_profile_url :  ENME.getMessage("settings_social_profile_url"),
            	button_remove : ENME.getMessage("button_remove"),
            	settings_social_set_default : ENME.getMessage("settings_social_set_default"),
            },

            _secrets : false,

            /**
             * social network flag.
             */
            type : "twitter",

            /**
             * Post create cycle lufe.
             */
            postCreate : function() {
              //console.debug("account", this.account);
              if (this._removeButton) {
                  if (this.account.tweetpoll_stats > 0 || this.account.poll_stats > 0 || this.account.survey_stats > 0) {
                      //console.debug("remove this button ", this._removeButton);
                      dojo.destroy(this._removeButton.domNode);
                  } else {
                      //console.debug("add this button ", this._removeButton);
                      dojo.connect(this._removeButton, "onClick", dojo.hitch(this, "_remove"));
                  }
              }
              
              dojo.subscribe("/encuestame/social/account/row/show", this, function(widget) {
                  if (this.id != widget.id) {
                      this._secrets = true;
                      this._showHideAction();
                  } else {

                  }
              });
            },

            /**
             * 
             */
            _disableSocialAccount : function() {

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
                //console.debug("dialog 1 ", myDialog);
                myDialog.functionYes = dojo.hitch(this, function(){
                    this._removeAction();
                    myDialog.hide();
                });
                //console.debug("dialog 2 ", myDialog);
                myDialog.show();
            },

            /*
             * Remove Action.
             */
            _removeAction : function(){
                //console.debug("_removeAction");
                var load = dojo.hitch(this, function(data){
                    //console.debug("data", data);
                    dojo.publish("/encuestame/social/list/reload");
                });
                var error = function(error) {
                    //console.debug("error", error);
                };
                encuestame.service.xhrGet(
                       encuestame.service.social.action.remove,
                      {socialAccountId : this.account.id}, load, error);
            },

            /*
             * Remove Social Account.
             */
            _remove : function(event){
                dojo.stopEvent(event);
                this._openDialog("title", "");
            },

            /*
             * change status account.
             */
            _changeStatusAccount : function(event){
                dojo.stopEvent(event);
                var load = dojo.hitch(this, function(data){
                    //console.debug("data", data);
                    dojo.publish("/encuestame/social/list/reload");
                });
                var error = function(error) {
                    console.debug("error", error);
                };
                var params = {socialAccountId : this.account.id};
                encuestame.service.xhrGet(
                       encuestame.service.social.action.defaultState,
                      params, load, error);
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