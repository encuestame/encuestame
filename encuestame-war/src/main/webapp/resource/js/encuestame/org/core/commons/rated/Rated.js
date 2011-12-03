dojo.provide("encuestame.org.core.commons.rated.Rated");

dojo.require('encuestame.org.core.commons');
dojo.require('encuestame.org.core.shared.utils.AccountPicture');
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");

dojo.declare(
    "encuestame.org.core.commons.rated.Rated",
    [encuestame.org.main.EnmeMainLayoutWidget],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.rated", "templates/rate.html"),

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
         * executed after render template.
         */
        postCreate : function() {
            if (this.service != null) {
                this._loadItems();
            }
        },

        /*
         * executed before render template.
         */
        postMixInProperties: function() {

        },

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
              encuestame.service.xhrGet(this.service, this.getParams(), load, error);
          }
});
