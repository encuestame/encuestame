define([
     "dojo/_base/declare",
     "dojo/on",
     "me/web/widget/dashboard/AbstractGadget",
     "me/core/enme",
     "me/web/widget/rated/LikeRate",
     "me/web/widget/utils/ToggleText",
     "me/web/widget/ui/More",
     "dojo/text!me/web/widget/gadget/template/comments.html",
     "dojo/text!me/web/widget/gadget/template/commentItem.html"],
    function(
    declare,
    on,
    AbstractGadget,
    _ENME,
    LikeRate,
    ToggleText,
    More,
    template,
    templateItem) {

  var CommentItem = declare([AbstractGadget], {

    /**
     * template string.
     * @property templateString
     */
    templateString : templateItem,

    /**
     *
     * @method postMixInProperties
     */
    postMixInProperties: function() {
        this.item.likeVote = _ENME.shortAmmount(this.item.likeVote);
        this.item.dislike_vote = _ENME.shortAmmount(this.item.dislike_vote);
        this.item.created_at = _ENME.fromNow(this.item.created_at, "YYYY-MM-DD");
    },

    /**
     *
     * @method
     */
    postCreate: function() {
        this._positive.appendChild(this._createLinkRate(true, false, this.item.likeVote));
        this._negative.appendChild(this._createLinkRate(false, true, this.item.dislike_vote));
        var parent = this;
        on(this.domNode, "mouseover", function(e) {
            dojo.addClass(parent._buttons, 'active');
        });
        on(this.domNode, "mouseout", function(e) {
            dojo.removeClass(parent._buttons, 'active');
        });
    },

   /**
    * Create a link rate widget.
    * @param value
    * @returns
    */
   _createLinkRate : function(positive, negative, value) {
     var widget = new LikeRate({
       positive : positive,
       value : value,
       negative : negative
     });
     return widget.domNode;
   },

    /**
     *
     * @property
     */
    item: {}
  });


  return declare([AbstractGadget], {

    /**
     * template string.
     * @property templateString
     */
    templateString : template,

    /**
     *
     * @property
     */
    limit: 5,

    /**
     * Control the current items are displayed
     * @property items
     */
    items : 0,

   /**
    * PostCreate life cycle.
    */
     initGadget : function() {
          var parent = this;
          this.more = new More({
               parentWidget : this,
               more_max : 5
          });

          this.more.loadItems = dojo.hitch(this, function () {
              parent.load();
          });
          // if more
          if (this._more) {
            this._more.appendChild(this.more.domNode);
          }
          this.load();
     },


     /**
      *
      * @method
      */
     load: function() {
         var parent = this,
         load = dojo.hitch(this, function(data) {
          if("success" in data) {
              data = data.success.comments;
              this.items += data.length;
              this.printStream(data);
          }
         }),
         error =  dojo.hitch(this, function(error) {
              parent.errorLoadGadget();
         });
         this.getURLService().get("encuestame.service.comments.my", {
             limit: this.limit,
             option: 'ALL',
             // TODO: different posible status
             // APPROVE
             // MODERATE
             // PUBLISHED
             // SPAM
             start: this.more.pagination.start
          }, load, error , dojo.hitch(this, function() {
         }));
     },

     /**
      *
      * @method
      */
     printStream: function(data) {
        dojo.forEach(data, dojo.hitch(this, function(item, index) {
          var activityItem = new CommentItem({
              item: item
          });
          this._comments.appendChild(activityItem.domNode);
        }));
     }
  });
});