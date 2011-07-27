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

        _arrayWidgetSelected : [],

        _showOnlySelected : false,

        enableEasyAddAccount : false,

        checkRequiredSocialAccounts : false,

        _selectAll : false,

        _required : 1,

        _isValidMessage : "",

        postCreate : function(){
            dojo.subscribe("/encuestame/social/picker/accounts/reload", this, "showListAccounts");
            this._loadSocialConfirmedAccounts();
            dojo.connect(this._all, "onclick", this, this._selectAll);
            dojo.connect(this._selected, "onclick", this, this._selectedAccount);
            dojo.subscribe("/encuestame/social/picker/counter/reload", this, "_reloadCounter");
        },

        /*
         * select all items.
         */
        _selectAll : function(){
            this._selectAll = !this._selectAll;
            if (!this._selectAll) {
                 dojo.forEach(
                      this.arrayWidgetAccounts,
                      dojo.hitch(this, function(widget, index) {
                          widget.markAsSelected();
                 }));
                 this._all.innerHTML = "UnSelect All";
            } else {
                dojo.forEach(
                        this.arrayWidgetAccounts,
                        dojo.hitch(this, function(widget, index) {
                            widget.unSelected();
                    }));
                this._all.innerHTML = "Select All";
            }
            dojo.publish("/encuestame/social/picker/counter/reload");
        },

        /*
         * return array of id for each social account selected.
         */
        getSocialAccounts : function(){
            var accountsId = [];
            dojo.forEach(
                    this.arrayWidgetAccounts,
                    dojo.hitch(this, function(widget, index) {
                        if(widget.selected){
                            accountsId.push(widget.account.id);
                        }
                }));
            console.debug("getSocialAccounts", accountsId);
            return accountsId;
        },

        /*
         * return complete data for selected accounts.
         */
        getSocialCompleteAccounts : function(){
            var accounts = [];
            dojo.forEach(
                    this.arrayWidgetAccounts,
                    dojo.hitch(this, function(widget, index) {
                        if(widget.selected){
                            accounts.push(widget.account);
                        }
                }));
            console.debug("getSocialAccounts", accounts);
            return accounts;
        },

        /*
         * is valid check.
         */
        isValid : function() {
            if (this._countSelected() >= this._required) {
                return true;
            } else {
                this._isValidMessage = encuestame.constants.errorCodes["022"];
                return false;
            }
        },

        /*
         * return a validator message.
         */
        isValidMessage : function() {
            return this._isValidMessage;
        },

        /*
         *
         */
        _countSelected : function(){
            var counter = 0;
            dojo.forEach(
                    this.arrayWidgetAccounts,
                    dojo.hitch(this, function(widget, index) {
                    if(widget.selected){
                        counter++;
                    }
                }));
            console.debug("_reloadCounter", counter);
            return counter;
        },

        /*
         * reload counter
         */
        _reloadCounter : function(){
            var counter = this._reloadCounter();
            this._counter.innerHTML = counter;
        },

        /*
         * show only selected accounts.
         */
        _selectedAccount : function() {
            console.debug("_selectedAccount", this._showOnlySelected);
            this._showOnlySelected = !this._showOnlySelected;
            if (this._showOnlySelected) {
                dojo.forEach(
                    this.arrayWidgetAccounts,
                    dojo.hitch(this, function(widget, index) {
                        widget.showIsSelected();
                }));
                this._selected.innerHTML = "Show All";
            } else {
                dojo.forEach(
                        this.arrayWidgetAccounts,
                        dojo.hitch(this, function(widget, index) {
                            widget.show();
                    }));
                this._selected.innerHTML = "Show only selected";
            }
        },

        /*
         * load all social accounts verified.
         */
        _loadSocialConfirmedAccounts : function() {
           var load = dojo.hitch(this, function(data) {
                this.arrayAccounts = data.success.items;
                dojo.empty(this._listSocialAccounts);
                console.debug("social", this.arrayAccounts);
                this.showListAccounts();
                if (checkRequiredSocialAccounts) {

                }
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
        * create pick social account.
        */
       createPickSocialAccount : function(data){
           var widget = new encuestame.org.core.commons.social.SocialPickerAccount({account : data, parentPicker: this});
           this._listSocialAccounts.appendChild(widget.domNode);
           this.arrayWidgetAccounts.push(widget);
       }
});

/*
 * Widget Social Account to Pick.
 */
dojo.declare(
        "encuestame.org.core.commons.social.SocialPickerAccount",
        [dijit._Widget, dijit._Templated],{

        templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/socialPickerAccount.html"),

        account : {},

        widgetsInTemplate: true,

        parentPicker : null,

        selected : false,

        postCreate : function(){
            this._accountProviderIcon.src = encuestame.social.shortPicture(this.account.type_account);
            dojo.connect(this.domNode, "onclick", this, dojo.hitch(this, function(){
                //console.debug("pick account ", this.account);
                //console.debug("pick account ", this.selected);
                this.selected = !this.selected;
                if (this.selected) {
                    this.markAsSelected();
                } else {
                    this.unSelected();
                }
                dojo.publish("/encuestame/social/picker/counter/reload");
            }));
            //mark as selected default items.
            if(this.account.default_selected){
                this.selected = true;
                this.markAsSelected();
            }
        },

        markAsSelected : function(){
            dojo.addClass(this.domNode, "selected");
            this.selected = true;
        },

        unSelected : function(){
            dojo.removeClass(this.domNode, "selected");
            this.selected = false;
        },

        show : function(){
                dojo.removeClass(this.domNode, "defaultDisplayHide");
        },

        showIsSelected : function(){
            if(!this.selected) {
                dojo.addClass(this.domNode, "defaultDisplayHide");
            }
        },

        _select : function(b){
            this.selected = b;
         }
 });
