define([
     "dojo/_base/declare",
     "dijit/_WidgetBase",
     "dijit/_TemplatedMixin",
     "dijit/_WidgetsInTemplateMixin",
     "me/core/main_widgets/EnmeMainLayoutWidget",
     "me/web/widget/social/LinksPublishedItem",
     "me/core/enme",
     "dojo/text!me/web/widget/social/templates/linksPublishedItem.html" ],
    function(
    declare,
    _WidgetBase,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    main_widget,
    linksPublishedItem,
    _ENME,
     template) {

  return declare([ _WidgetBase, _TemplatedMixin, main_widget, , _WidgetsInTemplateMixin], {

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
     //       this._image.src = encuestame.social.shortPicture(this.social);
   }


  });
});

//dojo.provide("encuestame.org.core.commons.social.LinksPublished");
//
//dojo.require("dijit.form.ValidationTextBox");
//dojo.require("dijit.form.Textarea");
//dojo.require("dijit.form.Select");
//dojo.require("dijit.form.Button");
//dojo.require("dijit.form.Form");
//dojo.require("encuestame.org.core.commons.dialog.Dialog");
//dojo.require("encuestame.org.core.commons.dialog.Confirm");
//dojo.require("encuestame.org.core.shared.utils.CacheLinkedList");
//
//dojo.require("dojo.hash");
//
//dojo.declare(
//    "encuestame.org.core.commons.social.LinksPublished",
//    [encuestame.org.main.EnmeMainLayoutWidget,
//     encuestame.org.core.shared.utils.CacheLinkedList],{
//
//    	/**
//    	 * Template.
//    	 */
//    	templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/linksPublished.html"),
//
//        /**

//});
//
///**
// * Represents a social item external link.
// */
//dojo.declare(
//        "encuestame.org.core.commons.social.LinksPublishedItem",
//        [encuestame.org.main.EnmeMainLayoutWidget],{
//
//        	/**
//        	 * Template.
//        	 */
//            templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/linksPublishedItem.html"),
//

//
//});