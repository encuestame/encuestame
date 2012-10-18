define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/support/AbstractFilterSupport",
         "me/core/enme",
         "dojo/text!me/web/widget/support/templates/social-filters.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                AbstractFilterSupport,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, AbstractFilterSupport, _WidgetsInTemplateMixin], {

          /**
           * The template reference.
           */
          templateString : template,

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
             dojo.subscribe("/encuestame/social/picker/counter/reload", this, "_reloadCounter");
             this._loadSocialConfirmedAccounts();
             this._button.onClick = dojo.hitch(this, function() {

             });
         },

         /**
          *
          */
         cleanSocialAccounts : function() {
             dojo.empty(this._items);
         },


         /**
          * reload counter
          */
         _reloadCounter : function() {
             var counter = this._countSelected();
             this._counter.innerHTML =  counter + " " + this.i18nMessage.social_picker_filter_selected;
         },

         /**
          * Create a pick social account.
          * @param data
          */
         createPickSocialAccount : function(data) {
             var widget = new SocialFilterMenuItem({data : data});
             this._items.appendChild(widget.domNode);
             this.arrayWidgetAccounts.push(widget);
         }

    });
});