define([
         "dojo",
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/enme",
         "dojo/text!me/core/ui/templates/loading.html" ],
        function(
                dojo,
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {

            /*
             * template string.
             */
             templateString : template,

            /*
             *
             */
            channel_on : "encuestame/loading/display/on",

            /*
             *
             */
            channel_off : "encuestame/loading/display/off",

            /*
             * Post create life cycle.
             */
            postCreate : function() {
                 dojo.subscribe(this.channel_on, this, "show");
                 dojo.subscribe(this.channel_off, this, "hide");
            },

            /*
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