define([
         "dojo/_base/declare",
         "dojo/Deferred",
         "dojo/dom-construct",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/web/widget/ui/MessageSearch",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/support/ContextSupport",
         "me/web/widget/support/SocialFilterMenuItem",
         "me/web/widget/publish/PublishSocialStatus",
         "me/web/widget/social/SocialAccountsSupport",
         "me/core/enme",
         "dojo/text!me/web/widget/publish/templates/socialPublishSupport.html" ],
        function(
                declare,
                Deferred,
                domConstruct,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                MessageSearch,
                main_widget,
                ContextSupport,
                SocialFilterMenuItem,
                PublishSocialStatus,
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

         /**
          * i18n message for this widget.
          */
         i18nMessage : {
           social_picker_filter_selected : _ENME.getMessage("social_picker_filter_selected"),
           commons_filter : _ENME.getMessage("commons_filter"),
           counter_zero : _ENME.getMessage("counter_zero"),
           loading_message : _ENME.getMessage("loading_message"),
           publish_social : _ENME.getMessage("publish_social")
         },

        /**
         *
         */
         postCreate : function() {
             this._loading = new MessageSearch();
             domConstruct.place(this._loading.domNode, this._custom_loading);
             dojo.subscribe("/encuestame/social/picker/counter/reload", this, "_reloadCounter");
             this._loadSocialConfirmedAccounts();
             dojo.connect(this._button, "onclick", dojo.hitch(this, function(event) {
                if(this.getSocialAccounts().length > 0) {
                    this.publish();
                } else {
                    _ENME.log("error social count");
                }
             }));
         },

         /**
          *
          * @method
          */
         publish : function() {
               var parent = this;
               var load = dojo.hitch(this, function(data) {
                    _ENME.log("social publish", data);
                    if( 'success' in data) {
                       var widget = new PublishSocialStatus({
                            socialAccounts : this.getSocialCompleteAccounts(),
                            socialPublish : data.success.socialPublish
                       });
                       dojo.removeClass(this._social_status, "hidden");
                       this._social_status.appendChild(widget.domNode);
                       parent._loading.hide();
                    }
               });
               var error = function(error) {
                   console.error("error", error);
               };
               dojo.addClass(this._social_accounts_wrapper, "hidden");
               this._loading.show(this.i18nMessage.loading_message, _ENME.MESSAGES_TYPE.WARNING);
               this.getURLService().post('encuestame.poll.publish.social', {
                    id : this.itemId,
                    "twitterAccounts" : this.getSocialAccounts()
               }, load, error , dojo.hitch(this, function() {

               }));
           },

         /**
          * reload counter
          */
         _reloadCounter : function() {
             var counter = this._countSelected();
             this._counter.innerHTML =  counter + " " + this.i18nMessage.social_picker_filter_selected;
             // if (typeof this.arrayAccounts === 'object') {
             //    this.storeSelected(this.arrayAccounts);
             // }
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