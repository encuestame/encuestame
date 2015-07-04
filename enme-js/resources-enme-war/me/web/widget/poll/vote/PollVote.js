define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/enme",
         "dojo/text!me/web/widget/poll/vote/templates/pollvote.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

            // template string.
            templateString : template,

            /**
             * postCreate
             * @method postCreate
             */
             postCreate : function() {
                // Retrieve the dojoType from template and append inside to this widget
                dojo.query("> [data-dojo-type]", this.srcNodeRef).forEach(
                    dojo.hitch(this, function(node) {
                        this._responses.appendChild(node);
                }));
             }
    });
});