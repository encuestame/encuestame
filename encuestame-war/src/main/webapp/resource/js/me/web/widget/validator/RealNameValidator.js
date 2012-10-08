define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/validator/AbstractValidatorWidget",
         "me/core/enme",
         "dojo/text!me/web/widget/validator/templates/realNameValidator.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                abstractValidatorWidget,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, abstractValidatorWidget, _WidgetsInTemplateMixin], {

          // template string.
           templateString : template,

         focusDefault: true,

         placeholder : "Write your Real Name",

         postCreate : function(){
             this.inherited(arguments);
         },

        /**
         *
         */
        _validate : function(event){
            this.inputTextValue = this._input.value;
                this._loadService(
            this.getServiceUrl(), {
                context : this.enviroment,
                real_name : this._input.value
            }, this.error);
        },

        /**
         *
         */
        getServiceUrl : function(){
            return 'encuestame.service.publicService.validate.realName';
        },

        /**
         *
         */
         error : function(error) {
            console.debug("error", error);
         }


    });
});