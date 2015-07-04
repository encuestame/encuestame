define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/enme",
         "dojo/text!me/web/widget/menu/template/optionMenuItem.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                _ENME,
                 template) {

            "use strict";

            return declare([ _WidgetBase,
                            _TemplatedMixin,
                            main_widget,
                            _WidgetsInTemplateMixin], {

       // template string.
       templateString : template


    });
});