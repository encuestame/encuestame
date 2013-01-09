///**
// * Represents a social item external link.
// */
define([
     "dojo/_base/declare",
     "dijit/_WidgetBase",
     "dijit/_TemplatedMixin",
     "dijit/_WidgetsInTemplateMixin",
     "me/core/main_widgets/EnmeMainLayoutWidget",
     "me/core/enme",
     "dojo/text!me/web/widget/social/templates/linksPublishedItem.html" ],
    function(
    declare,
    _WidgetBase,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    main_widget,
    _ENME,
     template) {

  return declare([ _WidgetBase,
                   _TemplatedMixin,
                   main_widget,
                   _WidgetsInTemplateMixin], {

   // template string.
   templateString : template,

    /**
    * Social data.
    */
   social : null,

   /**
    * Date of publication
    */
   date : "",

   /**
    * Text of publication
    */
   text : "",

   /**
    * Default link.
    */
   link : "#",

   /**
    * Triggered before render the template.
    */
   postMixInProperties : function() {
     if ( this.date) {
       this.date = _ENME.fromNow(this.date, "YYYY-MM-DD");
     }
   },

   /*
    * post create.
    */
   postCreate : function() {
            this._image.src = _ENME.shortPicture(this.social);
   }


  });
});