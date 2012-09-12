dojo.provide("encuestame.org.core.commons.questions.Question");

dojo.require("dijit.form.TextBox");
dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require('encuestame.org.core.commons');

dojo.declare(
    "encuestame.org.core.commons.questions.Question",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.questions", "templates/question.html"),

        widgetsInTemplate: true,

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
        	widget_question_type : ENME.getMessage("widget_question_type"),
        },         

        postCreate : function(){
            this.questionWidget = dijit.byId(this._question);
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