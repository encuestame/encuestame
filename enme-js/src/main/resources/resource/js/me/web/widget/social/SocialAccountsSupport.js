define([
     "dojo/_base/declare",
     "me/core/main_widgets/EnmeMainLayoutWidget",
     "me/core/enme"],
    function(
    declare,
    _MAIN,
    _ENME) {

  return declare([_MAIN], {

   /*
    *
    */
   arrayWidgetAccounts : [],

   /*
    *
    */
   arrayAccounts : [],

   /*
    *
    */
   constructor : function() {
       this.arrayWidgetAccounts = [];
   },

   /*
    *
    */
   cleanSocialAccounts : function(){},

   /*
    * load all social accounts verified.
    */
   _loadSocialConfirmedAccounts : function() {
       var load = dojo.hitch(this, function(data) {
           this.arrayAccounts = data.success.items;
           this.cleanSocialAccounts();
           this.showListAccounts();
       });
       var error = function(error) {
           console.debug("error", error);
       };
       return this.getURLService().get('encuestame.service.list.allSocialAccount',
           {},
           load,
           error,
           dojo.hitch(this, function() {
               //nothing
           }));
   },


   /*
    * return array of id for each social account selected.
    */
   getSocialAccounts : function() {
       var accountsId = [];
       dojo.forEach(
               this.arrayWidgetAccounts,
               dojo.hitch(this, function(widget, index) {
                   if (widget.selected) {
                       accountsId.push(widget.account.id);
                   }
           }));
       //console.debug("getSocialAccounts", accountsId);
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
                   if (widget.selected) {
                       accounts.push(widget.account);
                   }
           }));
       //console.debug("getSocialAccounts", accounts);
       return accounts;
   },

   /*
    * is valid check.
    */
   isValid : function() {
       if (this._countSelected() >= this._required) {
           return true;
       } else {
           this._isValidMessage = _ENME.getMessage("e_022");
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
               if (widget.selected) {
                   counter++;
               }
           }));
       //console.debug("_reloadCounter", counter);
       return counter;
   },

   /*
    * to override.
    */
   createPickSocialAccount : function(data){},

   /*
    * show up list accounts.
    */
   showListAccounts : function(){
       dojo.forEach(
               this.arrayAccounts,
               dojo.hitch(this, function(data, index) {
                     this.createPickSocialAccount(data);
           }));
   }

  });
});
