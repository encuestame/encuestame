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
            dojo.subscribe("/encuestame/social/picker/accounts/reload", this, "showListAccounts");
            this._loadSocialConfirmedAccounts();
            dojo.connect(this._all, "onclick", this, this.editUSer);
            dojo.connect(this._selected, "onclick", this, this._selected);
        },

        _selectAll : function(){

        },

        _selected : function(){

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
                         this.createPickSocialAccount(data);
               }));
       },

       /*
        *
        */
       createPickSocialAccount : function(data){
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
            //images/social/myspace/enme_icon_myspace.png
            var provider = this.account.type_account.toLowerCase();
            var url = encuestame.contextDefault + "/resources/images/social/"+provider+"/enme_icon_"+provider+".png";
            console.debug(url);
            this._accountProviderIcon.src = url;
        },

        _select : function(b){
            this.selected = b;
         }
 });
