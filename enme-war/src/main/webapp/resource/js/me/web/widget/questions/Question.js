define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/registry",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/enme",
         "dojo/text!me/web/widget/questions/templates/question.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                registry,
                _WidgetsInTemplateMixin,
                main_widget,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

            /*
             * template string.
             */
            templateString : template,

            //
            maxSize : "140",

            maxLength : "140",

            defaultValue : "",

            selectOnClick : true,

            classStyle : "questionTextBox inputClass",

            questionWidget : null,

            enableEvents : true,

            /*
             * i18n message for this widget.
             */
            i18nMessage : {
              widget_question_type : _ENME.getMessage("widget_question_type")
            },

            postCreate : function(){
                this.questionWidget = registry.byId(this._question);
                //this.questionWidget = this.questionWidget.get('value', this.defaultValue);
                if (this.enableEvents) {
                    dojo.connect(this.questionWidget, "onKeyUp", dojo.hitch(this, this.onKeyUp));
                    this.questionWidget.onChange = dojo.hitch(this, this.onChange);
                }
            },

            onKeyUp : function(event) {
                //override.
            },

            onChange : function(event) {
                //override.
            },

            /*
             *
             */
            getQuestion : function(){
                return this.questionWidget.get('value');
            },

            /*
             *
             */
            block : function(){
                this.questionWidget.block = true;
            },

            unblock : function(){
                this.questionWidget.block = true;
            },

            countCharacteres : function(){

            }

    });
});