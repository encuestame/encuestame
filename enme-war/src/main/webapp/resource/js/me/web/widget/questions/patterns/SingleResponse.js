define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "dijit/form/TextBox",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "dijit/registry",
         "me/web/widget/questions/patterns/AbstractPattern",
         "me/web/widget/pictures/Icon",
         "me/core/enme",
         "dojo/text!me/web/widget/questions/patterns/templates/single.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                TextBox,
                main_widget,
                registry,
                AbstractPattern,
                Icon,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, AbstractPattern, _WidgetsInTemplateMixin], {

          // template string.
            templateString : template,

            /**
            *
            */
           dndEnabled : false,

           /**
            * i18n message for this widget.
            */
           i18nMessage : {
               pattern_question_single : _ENME.getMessage("pattern_question_single")
           },

           /**
            * Max length for the answer.
            */
           max_length : 100,

           /**
            * Trim the the text.
            */
           trim : true,

           /**
            *
            */
           postCreate : function() {
               if (this.dndEnabled) {
                   dojo.addClass(this._handle, "dojoDndHandle");
               }
           },

           /*
            * response.
            */
           getResponse : function() {
               if (this._single) {
                   var response =  registry.byId(this._single).get('value');
                   return response;
               } else {
                   return null;
               }
           }
    });
});

//dojo.provide("encuestame.org.core.commons.questions.patterns.SingleResponse");
//
//dojo.require("dijit.form.TextBox");
//dojo.require("dijit._Templated");
//dojo.require("dijit._Widget");
//dojo.require('encuestame.org.core.commons');
//dojo.require('encuestame.org.core.shared.utils.Icon');
//dojo.require('encuestame.org.core.commons.questions.patterns.AbstractPattern');
//
//dojo.declare(
//    "encuestame.org.core.commons.questions.patterns.SingleResponse",
//    [encuestame.org.core.commons.questions.patterns.AbstractPattern],{
//        templatePath: dojo.moduleUrl("encuestame.org.core.commons.questions.patterns", "templates/single.html"),
//

//});