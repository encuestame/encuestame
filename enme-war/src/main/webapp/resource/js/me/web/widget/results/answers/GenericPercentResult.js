define([
     "dojo/_base/declare",
     "dijit/_WidgetBase",
     "dijit/_TemplatedMixin",
     "dijit/_WidgetsInTemplateMixin",
     "me/core/main_widgets/EnmeMainLayoutWidget",
     "me/web/widget/results/answers/ResultSupport",
     "me/core/enme",
     "dojo/text!me/web/widget/results/answers/templates/generic_result.html" ],
    function(
    declare,
    _WidgetBase,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    main_widget,
    resultSupport,
    _ENME,
     template) {

  return declare([ _WidgetBase, _TemplatedMixin, main_widget, resultSupport, _WidgetsInTemplateMixin], {

   /**
    * template string.
    */
   templateString : template,

    /**
    * Represent the unique item id of the result.
    */
   itemId : null,

   /**
    * Represent the label of the result / answer.
    */
   labelResponse : "",

   /**
    * Represent the color of the result.
    */
   color : "",

   /**
    * The current total of votes.
    */
   votes : "",

   /**
    * The question id.
    */
   questionId : null,

   /**
    * The percent result.
    */
   percent : "0%",


  });
});