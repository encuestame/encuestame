define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/tweetpoll/HashTagsSuggest",
         "me/core/enme",
         "dojo/text!me/web/widget/tweetpoll/templates/hashtag.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                HashTagsSuggest,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

          // template string.
            templateString : template,

          /**
          * the body of hashtag.
          */
           data : null,

           /**
            * the label of the hashtag.
            */
           label : null,

           /**
            * Parent widget reference.
            */
           parentWidget : null,

           /**
            *
            */
           postCreate : function() {
               //console.debug("new HashTag", this.label);
           },

           /**
            *
            * @param event
            */
           _options : function(event){
               var dialog = this.parentWidget.getDialog();
               dialog.item = this;
               dialog.show();
           }

    });
});
