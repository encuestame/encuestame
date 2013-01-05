define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "dijit/registry",
         "dijit/form/Form",
         "dijit/form/Button",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/enme",
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
                has,
                ioQuery,
                template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

      /*
       * template string.
       */
      templateString : template,

      /*
       *
       */
      account : null,

      //
      /**
       * i18n Message.
       */
      i18nMessage : {
          settings_config_profile_email : _ENME.getMessage("settings_config_profile_email"),
          settings_config_profile_complete_name : _ENME.getMessage("settings_config_profile_complete_name"),
          settings_social_tp_published_whith_this_account : _ENME.getMessage("settings_social_tp_published_whith_this_account"),
          settings_social_pll_published_whith_this_account : _ENME.getMessage("settings_social_pll_published_whith_this_account"),
          settings_social_su_published_whith_this_account : _ENME.getMessage("settings_social_su_published_whith_this_account"),
          settings_social_profile_url :  _ENME.getMessage("settings_social_profile_url"),
          button_remove : _ENME.getMessage("button_remove"),
          settings_social_set_default : _ENME.getMessage("settings_social_set_default")
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
                    }
                });
              },

       /*
        *
        */
        _disableSocialAccount : function() {},


      /*
       * open dialog.
       * @param title
       * @param content
       */
      _openDialog : function(title, content) {
          var myDialog = new Confirm({
              title: title,
              content: content,
              style: "width: 350px"
          });
          //console.debug("dialog 1 ", myDialog);
          myDialog.functionYes = dojo.hitch(this, function(){
                this._removeAction();
                myDialog.hide();
            });
           myDialog.show();
        },

       /*
       * Remove Action.
       */
      _removeAction : function() {
          //console.debug("_removeAction");
          var load = dojo.hitch(this, function(data) {
              //console.debug("data", data);
              dojo.publish("/encuestame/social/list/reload");
          });
          var error = function(error) {
              //console.debug("error", error);
                  };
                  encuestame.service.xhrGet(
                        this.getURLService().service('encuestame.service.social.action.remove'),
                        {socialAccountId : this.account.id}, load, error);
              },

      /*
       * Remove Social Account.
       */
      _remove : function(event) {
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
                        this.getURLService().service('encuestame.service.social.action.defaultState'),
                        params, load, error);
              },

              _showHideAction : function(){
                  //console.debug("_showHideAction ", this);
          if (this._secrets) {
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
});