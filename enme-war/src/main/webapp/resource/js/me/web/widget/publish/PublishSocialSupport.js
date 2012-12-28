define([
         "dojo/_base/declare",
         "dojo/Deferred",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/support/ContextSupport",
         "me/web/widget/support/SocialFilterMenuItem",
         "me/web/widget/social/SocialAccountPicker",
         "me/web/widget/social/SocialAccountsSupport",
         "me/core/enme",
         "dojo/text!me/web/widget/publish/templates/socialPublishSupport.html" ],
        function(
                declare,
                Deferred,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                ContextSupport,
                SocialFilterMenuItem,
                SocialAccountPicker,
                SocialAccountsSupport,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, ContextSupport, SocialAccountsSupport, _WidgetsInTemplateMixin], {

         /*
          * template string.
          */
         templateString : template,

         widgetsInTemplate: true,

         messageToPublish : "",

         context : "",

         _socialWidget : null,

         itemId : null,

         /*
         *
         */
        _socialWidget : null,

         /**
          * i18n message for this widget.
          */
         i18nMessage : {
           social_picker_filter_selected : _ENME.getMessage("social_picker_filter_selected"),
           commons_filter : _ENME.getMessage("commons_filter")
         },

        /**
         *
         */
         postCreate : function() {
             this._loadSocialConfirmedAccounts();
             dojo.connect(this._button, "onclick", dojo.hitch(this, function(event) {
                if(this.getSocialAccounts().length > 0) {
                    this.publish();
                } else {
                    console.info("eeror social count");
                }
             }));
         },

         /**
          *
          * @method
          */
         publish : function() {
               console.log('publish polll', this.getSocialAccounts());
               var load = dojo.hitch(this, function(data) {
                   console.info("social publish", data);
               });
               var error = function(error) {
                   console.error("error", error);
               };
               this.getURLService().post('encuestame.poll.publish.social', {
                    id : this.itemId,
                    "twitterAccounts" : this.getSocialAccounts()
               }, load, error , dojo.hitch(this, function() {}));
           },

         /**
          * reload counter
          */
         _reloadCounter : function() {
             var counter = this._countSelected();
             this._counter.innerHTML =  counter + " " + this.i18nMessage.social_picker_filter_selected;
             if (typeof this.arrayAccounts === 'object') {
                this.storeSelected(this.arrayAccounts);
             }
         },

         /**
          * Create a pick social account.
          * @param data
          */
         createPickSocialAccount : function(data) {
             var widget = new SocialFilterMenuItem({data : data, account : data});
             this._social.appendChild(widget.domNode);
             this.arrayWidgetAccounts.push(widget);
         }
    });
});