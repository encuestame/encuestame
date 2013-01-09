define([
     "dojo/_base/declare",
     "me/core/enme"],
    function(
    declare,
    _ENME) {

  return declare(null, {

  /*
   *
   */
   service : null,

   /*
    *
    */
   _items : [],

   /*
    *
    */
   _key : null,

   /*
    * print items.
    */
   _print : function(items) {
      if ( typeof(items) == 'object' ) {
          dojo.forEach(items, dojo.hitch(this,function(item) {
            this._item_store.appendChild(this._createItem(item));
          }));
      }
   },

   /*
    * method should override.
    */
   _createItem : function(item) {},

   /*
    *
    */
   getParams : function(){},

   /*
    *
    */
   _loadItems : function(){
         var load = dojo.hitch(this, function(data) {
             if (this._key != null) {
                 this._items = data.success[this._key];
                 dojo.empty(this._item_store);
                 this._print(this._items);
             }
         });
         var error = function(error) {
             this.errorMesage(error);
         };
         encuestame.service.xhrGet(this.getURLService().service(this.service), this.getParams(), load, error);
     }

  });
});