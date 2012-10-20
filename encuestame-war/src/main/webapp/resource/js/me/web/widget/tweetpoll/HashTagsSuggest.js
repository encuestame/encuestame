define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/suggestion/Suggest",
         "me/core/enme",
         "dojo/text!me/web/widget/tweetpoll/templates/suggest.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                Suggest,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, Suggest, _WidgetsInTemplateMixin], {

          // template string.
            templateString : template,

          /*
           *
           */
          block : function(){},

          /*
           *
           */
          unblock : function(){}
    });
});