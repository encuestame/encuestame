define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/support/ToggleMenu",
         "me/core/enme",
         "dojo/text!me/web/widget/menu/templates/optionMenuItem.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, ToggleMenu, _WidgetsInTemplateMixin], {

       // template string.
       templateString : template,


    });
});