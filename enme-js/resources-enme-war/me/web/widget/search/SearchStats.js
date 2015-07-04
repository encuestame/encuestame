define([
     "dojo/_base/declare",
     "dijit/_WidgetBase",
     "dijit/_TemplatedMixin",
     "dijit/_WidgetsInTemplateMixin",
     "me/core/main_widgets/EnmeMainLayoutWidget",
     "me/web/widget/rated/Comment",
     "me/web/widget/rated/RatedOperations",
     "me/core/enme",
     "dojo/text!me/web/widget/search/templates/searchStats.html" ],
    function(
    declare,
    _WidgetBase,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    main_widget,
    comment,
    ratedOperations,
    _ENME,
     template) {

  return declare([ _WidgetBase, _TemplatedMixin, ratedOperations,  main_widget, _WidgetsInTemplateMixin], {

      // template string.
      templateString : template,

      /*
       *
       */
      service : 'encuestame.service.list.rate.comments',

      /*
       *
       */
      _key : ["topComments"],

      /*
       * Limited comments.
       */
      comments : 5,

      /*
       *
       */
      postCreate : function() {
        if (this.service !== null) {
            this._loadItems();
        }
      },

      /*
       *
       */
       _createItem : function(item) {
           var widget = new comment({
               data : item
           });
           return widget.domNode;
       },

       /*
        * comment params.
        */
       getParams : function() {
           return { commentOption : "", max : this.comments, start : 0 };
       }

  });
});