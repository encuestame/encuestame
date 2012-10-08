define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/stream/HashTagInfo",
         "me/core/enme",
         "dojo/text!me/web/widget/ui/templates/loading.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                hashTagInfo,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

          // template string.
            templateString : template,

            /**
             *
             */
            i18nMessage : {
              loading : _ENME.getMessage("loading_message", "Loading")
            },

            /**
             * Show the loader.
             */
            show : function (message) {
              dojo.removeClass(this._loading, "hidden");
              if (message) {
                this._message.innerHTML = message;
              } else {
                this._message.innerHTML = this.i18nMessage.loading;
              }
            },

            /**
             * Hide the loader.
             */
            hide : function () {
              dojo.addClass(this._loading, "hidden");
            }

    });
});