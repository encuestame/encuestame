define( [
     "dojo/_base/declare",
     "me/core/enme" ],
    function(
    declare,
    _ENME ) {

  return declare( null, {

  /*
   *
   */
   service: null,

   /*
    *
    */
   _items: [],

   /*
    *
    */
   _key: null,

   /**
    * A items print counter.
    * @property items
    */
   items: 0,

   /**
    * Clean the main node before load the service.
    * @property
    */
   clean_after_reload: true,

   /*
    * Print items.
    */
   _print: function( items ) {
      if ( typeof( items ) == "object" ) {
          this.items += items.length;
          dojo.forEach( items, dojo.hitch( this, function( item ) {
            this._item_store.appendChild( this._createItem( item ) );
          }) );
      }
   },

   /*
    * Method should override.
    */
   _createItem: function( item ) {},

   /*
    *
    */
   getParams: function() {},

   /*
    *
    */
   _loadItems: function() {
         var load = dojo.hitch( this, function( data ) {
             if ( this._key !== null ) {
                 this._items = data.success[ this._key ];
                 if ( this.clean_after_reload ) {
                   dojo.empty( this._item_store );
                  }
                 this._print( this._items );
             }
         });
         var error = function( error ) {
             this.errorMesage( error );
         };
         this.getURLService().get( this.service, this.getParams(), load, error, dojo.hitch( this, function() {

         }) );
     }

  });
});
