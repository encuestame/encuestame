define([
     "dojo/_base/declare",
     "dijit/_WidgetBase",
     "dijit/_TemplatedMixin",
     "dijit/_WidgetsInTemplateMixin",
     "me/core/main_widgets/EnmeMainLayoutWidget",
     "me/web/widget/rated/Comment",
     "me/web/widget/rated/RatedOperations",
     "me/web/widget/rated/UsersProfile",
     "me/web/widget/ui/More",
     "me/core/enme",
     "dojo/text!me/web/widget/rated/templates/profile-rate.html" ],
    function(
    declare,
    _WidgetBase,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    main_widget,
    comment,
    ratedOperations,
    userProfile,
    More,
    _ENME,
     template) {

  return declare([ _WidgetBase, _TemplatedMixin, ratedOperations,  main_widget, _WidgetsInTemplateMixin], {

     // template string.
     templateString : template,

     /**
      *
      * @property
      */
     _params : {
        status : true
     },

      /*
      *
      */
     service : 'encuestame.service.list.rate.profile',

     /*
      *
      */
     _key : ["profile"],


     clean_after_reload : false,

    /*
     *
     */
    postCreate : function() {
      this.more = new More({
           parentWidget : this
      });

      var parent = this;
      this.more.loadItems = dojo.hitch(this, function () {
          parent._loadItems();
      });
      // if more
      if(this._more) {
        this._more.appendChild(this.more.domNode);
      }
      // if service exist, load items.
      if (this.service !== null) {
          this._loadItems();
      }
     },

     /*
      *
      */
      _createItem : function(item) {
          var widget = new userProfile({
              data : item
          });
          return widget.domNode;
      },

      /**
       * Return a list of service parameters.
       * @method getParams
       */
      getParams : function() {
          return this.more.merge(this._params);
      }
  });
});