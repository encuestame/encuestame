define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "dijit/form/CheckBox",
         "dijit/registry",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/support/PublishSupport",
         "me/core/support/ContextSupport",
         "me/core/enme",
         "dojo/text!me/web/widget/options/templates/checkSingleOption.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                CheckBox,
                registry,
                main_widget,
                PublishSupport,
                ContextSupport,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, ContextSupport, PublishSupport, _WidgetsInTemplateMixin], {

          // template string.
            templateString : template,

            /*
            * option value.
            */
           option_value : "value",

           /*
            * label.
            */
           label : "value",

           /*
            *
            */
           option : {checked : false, value : ""},

           /*
            *
            */
           checkWidget: null,

           /*
            *
            */
           postCreate : function() {
               this.checkWidget = registry.byId("check_" + this.id);
               this.option.value = this.checkWidget.get('value');
               this.checkWidget.onChange = dojo.hitch(this, function(event){
                   this.option.checked = event;
               });
           },

           /*
           *
           */
          getValue : function() {
             return this.option;
          }
    });
});