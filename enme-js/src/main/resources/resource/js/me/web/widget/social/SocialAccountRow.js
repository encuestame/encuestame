define( [
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "dijit/registry",
         "dijit/form/Form",
         "dijit/form/Button",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/enme",
         "me/web/widget/dialog/Confirm",
         "dojo/hash",
         "dojo/io-query",
         "dojo/text!me/web/widget/social/templates/socialAccountRow.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                registry,
                Form,
                Button,
                main_widget,
                _ENME,
                Confirm,
                has,
                ioQuery,
                template ) {
            return declare( [ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin ], {

      /*
       * Template string.
       */
      templateString: template,

      /*
       *
       */
      account: null,

      //
      /**
       * I18n Message.
       */
      i18nMessage: {
          settings_config_profile_email: _ENME.getMessage("settings_config_profile_email"),
          settings_config_profile_complete_name: _ENME.getMessage("settings_config_profile_complete_name"),
          settings_social_tp_published_whith_this_account: _ENME.getMessage("settings_social_tp_published_whith_this_account"),
          settings_social_pll_published_whith_this_account: _ENME.getMessage("settings_social_pll_published_whith_this_account"),
          settings_social_su_published_whith_this_account: _ENME.getMessage("settings_social_su_published_whith_this_account"),
          settings_social_profile_url:  _ENME.getMessage("settings_social_profile_url"),
          button_remove: _ENME.getMessage("button_remove"),
          settings_social_set_default: _ENME.getMessage("settings_social_set_default"),
          settings_social_seted_as_default: _ENME.getMessage("settings_social_seted_as_default"),
          commons_confirm: _ENME.getMessage("commons_confirm"),
          commons_yes: _ENME.getMessage("commons_yes"),
          commons_no: _ENME.getMessage("commons_no")
       },

       _secrets: false,

     /**
       * Social network flag.
       */
      type: "twitter",

      favourite_picture: "",

      /**
       * PostMixInProperties lyfe cycle.
       * @method postMixInProperties
       */
      postMixInProperties: function() {
            this.favourite_picture = _ENME.config("contextPath") + "/resources/images/icons/enme_full_star.png";
      },

      /**
       * Post create cycle life.
       * @method postCreate
       */
      postCreate: function() {
          if ( this._removeButton ) {

              // Right now, i's not possible remove a social account if has elements pubished
              if ( this.account.tweetpoll_stats > 0 || this.account.poll_stats > 0 || this.account.survey_stats > 0 ) {
                  dojo.destroy( this._removeButton.domNode );
              } else {
                  dojo.connect( this._removeButton, "onClick", dojo.hitch( this, "_remove") );
              }
          }
          dojo.subscribe("/encuestame/social/account/row/show", this, function( widget ) {
              if ( this.id != widget.id ) {
                  this._secrets = true;
                  this._showHideAction();
              }
          });

          //
          this.changeDefaultStatus();
      },

      /**
       *
       * @method changeDefaultStatus
       */
      changeDefaultStatus: function() {
          if ( this.account.default_selected ) {
             this._defaultButton.set("label", this.i18nMessage.settings_social_seted_as_default );
             dojo.removeClass( this._favorite, "hidden");
          } else {
             this._defaultButton.set("label", this.i18nMessage.settings_social_set_default );
             dojo.addClass( this._favorite, "hidden");
          }
      },

      /*
       * Change status account
       * @method _changeStatusAccount
       * @event Event
       */
      _changeStatusAccount: function( event ) {
          dojo.stopEvent( event );

          // Success handler
          var load = dojo.hitch( this, function( data ) {
              console.debug("data", data );

              //Dojo.publish("/encuestame/social/list/reload");
              this.account.default_selected = !this.account.default_selected;
              this.changeDefaultStatus();
          });

          // Error handler
          var error = function( error ) {
              console.debug("error", error );
                  };
                  var params = { socialAccountId: this.account.id };
                  this.getURLService().get("encuestame.service.social.action.defaultState",
                          params, load, error, dojo.hitch( this, function() {

          }) );
       },

       /*
        *
        */
        _disableSocialAccount: function() {},

      /*
       * Open dialog.
       * @param title
       * @param content
       */
      _openDialog: function( title, content ) {
          var myDialog = new Confirm({
              title: title,
              content: content,
              style: "width: 350px",
              label: {
                  question: this.i18nMessage.commons_confirm,
                  yes: this.i18nMessage.commons_yes,
                  no: this.i18nMessage.commons_no
              }
          });

          //Console.debug("dialog 1 ", myDialog);
          myDialog.functionYes = dojo.hitch( this, function() {
                this._removeAction();
                myDialog.hide();
            });
           myDialog.show();
        },

       /*
       * Remove Action.
       * @method _removeAction
       */
      _removeAction: function() {

          // Load handler
          var load = dojo.hitch( this, function( data ) {

              //Console.debug("data", data);
              dojo.publish("/encuestame/social/list/reload");
          });

          // Error handler
          var error = function( error ) {};
          this.getURLService().get("encuestame.service.social.action.remove",
                  { socialAccountId: this.account.id }, load, error, dojo.hitch( this, function() {

          }) );
      },

      /*
       * Remove Social Account.
       */
      _remove: function( event ) {
          dojo.stopEvent( event );
          this._openDialog("title", "");
      },

      /**
       *
       * @method _showHideAction
       */
      _showHideAction: function() {
          if ( this._secrets ) {
              dojo.addClass( this._secretView, "defaultDisplayHide");
          } else {
              dojo.removeClass( this._secretView, "defaultDisplayHide");
                  }
                  this._secrets = !this._secrets;
              },

      /**
       *
       * @method _showHideSecrets
       */
      _showHideSecrets: function( event ) {
          dojo.stopEvent( event );
          this._showHideAction();
          dojo.publish("/encuestame/social/account/row/show", [ this ] );
      }

   });
});
