define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/enme",
         "dojo/dom-attr",
         "dojo/text!me/web/widget/pictures/templates/icon.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                _ENME,
                domAttr,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

            /*
             * template string.
             */
            templateString : template,

            /*
             *
             */
            img_name : "#",

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

            src : "",

            /*
            *
            */
           postMixInProperties: function() {
               var src = _ENME.config("contextPath") + "/resources/images/icons/" + this.img_name;
               this.title = src;
               this.src = src;
           }
    });
});
