define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/stream/HashTagInfo",
         "me/core/enme",
         "dojo/text!me/web/widget/suggestion/templates/suggestItem.html" ],
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

         data : null,

         parentWidget  : null,

         selected : false,

         postCreate : function(){},

        /**
         * select item
         */
        _selectItem: function(event) {
            dojo.stopEvent(event);
            this.selected = !this.selected;
            this.parentWidget.selectedItem = this.data;
            this.parentWidget.hide();
            this.processItem(this.data);
        }
    });
});