//Dojo.require("encuestame.org.core.commons.questions.patterns.AbstractPattern");
//dojo.require("encuestame.org.core.commons.questions.patterns.AbstractSoundResponse");
//dojo.require("encuestame.org.core.commons.questions.patterns.AbstractVideoResponse");
//dojo.require("encuestame.org.core.commons.questions.patterns.AbstractImageResponse");
//dojo.require("encuestame.org.core.commons.questions.patterns.AbstractAddNewAnswer");
//dojo.require("encuestame.org.core.commons.questions.patterns.AbstractMultipleSelection");

define( [
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/questions/patterns/AbstractPattern",
         "me/web/widget/questions/patterns/AbstractSoundResponse",
         "me/web/widget/questions/patterns/AbstractVideoResponse",
         "me/web/widget/questions/patterns/AbstractImageResponse",
         "me/web/widget/questions/patterns/AbstractAddNewAnswer",
         "me/web/widget/questions/patterns/AbstractMultipleSelection",
         "me/core/enme",
         "dojo/text!me/web/widget/questions/patterns/templates/singleOptionResponse.html" ],
        function(
            declare,
            _WidgetBase,
            _TemplatedMixin,
            _WidgetsInTemplateMixin,
            main_widget,
            AbstractPattern,
            AbstractSoundResponse,
            AbstractVideoResponse,
            AbstractImageResponse,
            AbstractAddNewAnswer,
            AbstractMultipleSelection,
            _ENME,
             template ) {
        return declare( [ _WidgetBase,
         _TemplatedMixin,
         main_widget,
         AbstractPattern,
         AbstractSoundResponse,
         AbstractVideoResponse,
         AbstractImageResponse,
         AbstractAddNewAnswer,
         AbstractMultipleSelection,
         _WidgetsInTemplateMixin ], {

          // Template string.
          templateString: template,

         /**
          *  Name of the option node.
          */
         name: "",

         /**
          *
          */
         vote: "",

         type_input: "radio",

        /**
         * Check of th
         */
         postMixInProperties: function() {
            this.type_input = this.multiple ? "checkbox" : "radio";
            if ( this.multiple ) {
               this.name = "multiplesVotes";
            }
         },

         postCreate: function() {

             //Initialize the parents widgets.
             this.inherited( arguments );
         }

    });
});
