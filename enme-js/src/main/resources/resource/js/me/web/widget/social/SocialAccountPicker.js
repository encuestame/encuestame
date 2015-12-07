define( [
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
     template ) {
    return declare( [ _WidgetBase, _TemplatedMixin, main_widget, SocialAccountsSupport, _WidgetsInTemplateMixin ], {

          /*
           * Template string.
           */
          templateString: template,

         _listSocialAccounts: null,

         _arrayWidgetSelected: [],

         _showOnlySelected: false,

         _selectAll: false,

         _required: 1,

         _isValidMessage: "",

               /**
        * I18n Message.
        */
       i18nMessage: {
           social_picker_only_selected: _ENME.getMessage("social_picker_only_selected", "Only selected"),
           social_picker_select_all: _ENME.getMessage("social_picker_select_all", "Select All"),
           social_picker_accounts_selected: _ENME.getMessage("social_picker_accounts_selected", "Accounts selected"),
           social_picker_unselect_all: _ENME.getMessage("social_picker_unselect_all", "Unselect all")
      },

               /*
        * Post create.
        */
       postCreate: function() {
           dojo.subscribe("/encuestame/social/picker/accounts/reload", this, "showListAccounts");
           this._loadSocialConfirmedAccounts();
           dojo.connect( this._all, "onclick", this, this._selectAll );
           dojo.connect( this._selected, "onclick", this, this._selectedAccount );
           dojo.subscribe("/encuestame/social/picker/counter/reload", this, "_reloadCounter");
               },

       /**
        * Select all items.
        */

       //Renamed _selectAll to _selectAllItems
       _selectAllItems: function( event ) {
           this._selectAll = !this._selectAll;
           if ( !this._selectAll ) {
                dojo.forEach(
                     this.arrayWidgetAccounts,
                     dojo.hitch( this, function( widget, index ) {
                         widget.markAsSelected();
                }) );
                this._all.innerHTML = this.i18nMessage.social_picker_unselect_all;
           } else {
               dojo.forEach(
                       this.arrayWidgetAccounts,
                       dojo.hitch( this, function( widget, index ) {
                           widget.unSelected();
                   }) );
               this._all.innerHTML = this.i18nMessage.social_picker_select_all;
           }
           dojo.publish("/encuestame/social/picker/counter/reload");
       },

       /*
        * Reload counter
        */
       _reloadCounter: function() {
           var counter = this._countSelected();
           this._counter.innerHTML = counter;
       },

               /*
        * Show only selected accounts.
        */
       _selectedAccount: function() {
           this._showOnlySelected = !this._showOnlySelected;
           if ( this._showOnlySelected ) {
               dojo.forEach(
                   this.arrayWidgetAccounts,
                   dojo.hitch( this, function( widget, index ) {
                       widget.showIsSelected();
               }) );
               this._selected.innerHTML = this.i18nMessage.social_picker_select_all;
           } else {
               dojo.forEach(
                       this.arrayWidgetAccounts,
                       dojo.hitch( this, function( widget, index ) {
                           widget.show();
                   }) );
               this._selected.innerHTML = this.i18nMessage.social_picker_only_selected;
           }
       },

       /*
        * Clean the social accounts.
        */
       cleanSocialAccounts: function() {
            dojo.empty( this._listSocialAccounts );
       },

     /*
       * Create pick social account.
       */
      createPickSocialAccount: function( data ) {
          var widget = new SocialPickerAccount({ account: data, parentPicker: this });
          this._listSocialAccounts.appendChild( widget.domNode );
          this.arrayWidgetAccounts.push( widget );
      }

   });
});

