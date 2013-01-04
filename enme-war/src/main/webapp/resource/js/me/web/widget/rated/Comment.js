define([
     "dojo/_base/declare",
     "dijit/_WidgetBase",
     "dijit/_TemplatedMixin",
     "dijit/_WidgetsInTemplateMixin",
     "me/core/main_widgets/EnmeMainLayoutWidget",
     "me/web/widget/rated/LikeRate",
     "me/web/widget/utils/ToggleText",
     "me/web/widget/pictures/AccountPicture",
     "me/core/enme",
     "dojo/text!me/web/widget/rated/templates/comment-item.html" ],
    function(
    declare,
    _WidgetBase,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    main_widget,
    rate,
    toggleText,
    accountPicture,
    _ENME,
     template) {

  return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

       // template string.
       templateString : template,

       data : null,

       limit_comment : 100,

       /**
        * executed before render template.
        */
       postMixInProperties: function() {
           if (this.data != null ) {
        this.data.likeVote = _ENME.shortAmmount(this.data.likeVote);
        this.data.dislike_vote = _ENME.shortAmmount(this.data.dislike_vote);
        //format : 2012-05-27
        this.data.created_at = _ENME.fromNow(this.data.created_at, "YYYY-MM-DD");
           }
       },

       /**
        * Post create life cycle.
        */
       postCreate : function () {
         this._positive.appendChild(this._createLinkRate(true, false, this.data.likeVote));
         this._negative.appendChild(this._createLinkRate(false, true, this.data.dislike_vote));
       },

       /**
        * Create a link rate widget.
        * @param value
        * @returns
        */
       _createLinkRate : function(positive, negative, value) {
         var widget = new rate({
           positive : positive,
           value : value,
           negative : negative
         });
         return widget.domNode;
       }

  });
});