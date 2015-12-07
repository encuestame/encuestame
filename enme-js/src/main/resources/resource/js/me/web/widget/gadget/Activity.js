define( [
     "dojo/_base/declare",
     "me/web/widget/dashboard/AbstractGadget",
     "me/core/enme",
     "dojo/text!me/web/widget/gadget/template/activity.html",
     "dojo/text!me/web/widget/gadget/template/activityItem.html" ],
    function(
    declare,
    AbstractGadget,
    _ENME,
    template,
    templateItem ) {

  var ActivityItem = declare( [ AbstractGadget ], {

    /**
     * Template string.
     * @property templateString
     */
    templateString: templateItem,

    /**
     * PostMixInProperties lyfe cycle.
     * @method postMixInProperties
     */
    postMixInProperties: function() {
        if ( this.item.type === "TWEETPOLL_PUBLISHED") { //TODO: it's possible we would add more type  in this condition
            this.item.url = _ENME.config("contextPath") + this.item.url;
        }
    },

    /**
     *
     * @property
     */
    item: {}
  });

  return declare( [ AbstractGadget ], {

    /**
     * Template string.
     * @property templateString
     */
    templateString: template,

    /**
     *
     * @property
     */
    limit: 5,

	no_results: _ENME.getMessage("commons_no_results"),

	  //This.no_results = _ENME.getMessage("commons_no_results");

   /**
    * PostCreate life cycle.
    */
     initGadget: function() {
        this.loadActivity();
     },

     /**
      *
      * @method
      */
     loadActivity: function() {
         var parent = this,
         load = dojo.hitch( this, function( data ) {
          if ("success" in data ) {
	          dojo.empty( this._list );
              data = data.success.notifications;
              this.printStream( data );
          }
         }),
         error =  dojo.hitch( this, function( error ) {
              parent.errorLoadGadget();
         });
         this.getURLService().get("encuestame.service.list.getNotifications", {
            limit: this.limit
          }, load, error, dojo.hitch( this, function() {
         }) );
     },

     /**
      *
      * @method
      */
     printStream: function( data ) {
        dojo.forEach( data, dojo.hitch( this, function( item, index ) {
          var activityItem = new ActivityItem({
              item: item
          });
          this._list.appendChild( activityItem.domNode );
        }) );
     }
  });
});
