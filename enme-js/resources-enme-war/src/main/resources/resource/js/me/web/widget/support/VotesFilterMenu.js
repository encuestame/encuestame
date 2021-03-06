define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/support/AbstractFilterSupport",
         "me/core/enme",
         "dojo/text!me/web/widget/support/templates/votes-filters.html" ],
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

          // template string.
            templateString : template

    });
});