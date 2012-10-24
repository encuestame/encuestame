define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/enme",
         "dojo/text!me/web/widget/pictures/templates/icon.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

            /*
             * template string.
             */
            templateString : template,

            /*
             *
             */
            src : "#",

            /*
             *
             */
            title : "icon",

            /*
             *
             */
            height : "16",

            /*
             *
             */
            width : "16",

            /*
            *
            */
           postMixInProperties: function() {
               var src = this.src;
               this.src = _ENME.config("contextPath") + "/resources/images/icons/" + src;
               this.title = src;
           },

            postCreate : function(){

            }
    });
});
