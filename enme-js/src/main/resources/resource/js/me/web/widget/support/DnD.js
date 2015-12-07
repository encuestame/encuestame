define( [
     "dojo",
     "dojo/_base/declare",
     "me/core/features/base",
     "me/core/enme" ],
    function(
      dojo,
      declare,
      featuresHas,
      _ENME ) {

  return declare( null, {

    /**
     * Check if the client has drag and drop support
     * @property isDnD
     */
    isDnD: false,

    /**
     *
     * @property DnDManager
     */
    DnDManager: null,

    /**
     * Private array to store connect event handlers.
     * @property _events
     */
    _events: [],

   /**
    * Constructor.
    */
    constructor: function() {
        this.isDnD = featuresHas.draganddrop;
    },

    /**
     * Enable dnd support on a list of nodes.
     * @param nodes
     * @param handlers
     * @method
     */
    enableDnDSupport: function( nodes, handlers ) {
      var parent = this;

      //Remove previous events
      dojo.forEach( this._events, dojo.hitch( this, function( item ) {
            dojo.disconnect( item );
      }) );
      this._events = [];

      // Iterate all nodes and add the DnD events.
      [].forEach.call( nodes, function( col ) {
          if ( handlers.dragstart ) {
              var dragstart = dojo.connect( col, "dragstart", handlers.dragstart );
              parent._events.push( dragstart );
          }
          if ( handlers.dragenter ) {
              var dragenter = dojo.connect( col, "dragenter", handlers.dragenter );
              parent._events.push( dragenter );
          }
          if ( handlers.dragover ) {
              var dragover = dojo.connect( col, "dragover", handlers.dragover );
              parent._events.push( dragover );
          }
          if ( handlers.drop ) {
              var drop = dojo.connect( col, "drop", handlers.drop );
              parent._events.push( drop );
          }
          if ( handlers.dragend ) {
              var dragend = dojo.connect( col, "dragend", handlers.dragend );
              parent._events.push( dragend );
          }
          if ( handlers.drag ) {
              var drag = dojo.connect( col, "drag", handlers.drag );
              parent._events.push( drag );
          }
      });
    }
  });
});
