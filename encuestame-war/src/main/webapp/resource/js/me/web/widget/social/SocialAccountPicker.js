define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/social/SocialAccountsSupport",
         "me/web/widget/social/SocialPickerAccount",
         "me/core/enme",
         "dojo/text!me/web/widget/social/templates/socialAccountPicker.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                SocialAccountsSupport,
                SocialPickerAccount,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, SocialAccountsSupport, _WidgetsInTemplateMixin], {

          /*
           * template string.
           */
           templateString : template,

         widgetsInTemplate: true,

         _listSocialAccounts : null,

         _arrayWidgetSelected : [],

         _showOnlySelected : false,

         _selectAll : false,

         _required : 1,

         _isValidMessage : "",

               /**
        * i18n Message.
        */
       i18nMessage : {
           social_picker_only_selected : _ENME.getMessage("social_picker_only_selected", "Only selected"),
           social_picker_select_all : _ENME.getMessage("social_picker_select_all", "Select All"),
           social_picker_accounts_selected : _ENME.getMessage("social_picker_accounts_selected", "Accounts selected"),
           social_picker_unselect_all : _ENME.getMessage("social_picker_unselect_all", "Unselect all")
      },


               /*
        * post create.
        */
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
       _selectAll : function(event) {
           this._selectAll = !this._selectAll;
           if (!this._selectAll) {
                dojo.forEach(
                     this.arrayWidgetAccounts,
                     dojo.hitch(this, function(widget, index) {
                         widget.markAsSelected();
                }));
                this._all.innerHTML = this.i18nMessage.social_picker_unselect_all;
           } else {
               dojo.forEach(
                       this.arrayWidgetAccounts,
                       dojo.hitch(this, function(widget, index) {
                           widget.unSelected();
                   }));
               this._all.innerHTML = this.i18nMessage.social_picker_select_all;
           }
           dojo.publish("/encuestame/social/picker/counter/reload");
               },

       /*
        * reload counter
        */
       _reloadCounter : function() {
           var counter = this._countSelected();
           this._counter.innerHTML = counter;
       },

               /*
        * show only selected accounts.
        */
       _selectedAccount : function() {
           //console.debug("_selectedAccount", this._showOnlySelected);
                   this._showOnlySelected = !this._showOnlySelected;
                   if (this._showOnlySelected) {
                       dojo.forEach(
                           this.arrayWidgetAccounts,
                           dojo.hitch(this, function(widget, index) {
                               widget.showIsSelected();
                       }));
                       this._selected.innerHTML = this.i18nMessage.social_picker_select_all;
                   } else {
                       dojo.forEach(
                               this.arrayWidgetAccounts,
                               dojo.hitch(this, function(widget, index) {
                                   widget.show();
                           }));
                       this._selected.innerHTML = this.i18nMessage.social_picker_only_selected;
                   }
               },

       /*
        * clean the social accounts.
        */
       cleanSocialAccounts : function(){
            dojo.empty(this._listSocialAccounts);
       },

     /*
       * create pick social account.
       */
      createPickSocialAccount : function(data) {
          var widget = new SocialPickerAccount({account : data, parentPicker: this});
          this._listSocialAccounts.appendChild(widget.domNode);
          this.arrayWidgetAccounts.push(widget);
      }

   });
});
//dojo.provide("encuestame.org.core.commons.social.SocialAccountPicker");
//
//dojo.require("encuestame.org.core.commons.dialog.Dialog");
//dojo.require("encuestame.org.core.commons.dialog.Confirm");
//dojo.require("encuestame.org.core.shared.utils.SocialAccountsSupport");
//dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
//
//dojo.require("dojo.hash");
//
//dojo.declare(
//        "encuestame.org.core.commons.social.SocialAccountPicker",
//        [encuestame.org.main.EnmeMainLayoutWidget, encuestame.org.core.shared.utils.SocialAccountsSupport],{
//
//        templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/socialAccountPicker.html"),
//

//});
//
///*
// * Widget Social Account to Pick.
// */
//dojo.declare(
//        "encuestame.org.core.commons.social.SocialPickerAccount",
//        [dijit._Widget, dijit._Templated],{
//
//        templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/socialPickerAccount.html"),
//

// });
