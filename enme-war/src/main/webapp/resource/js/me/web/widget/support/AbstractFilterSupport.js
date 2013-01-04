define([
     "dojo/_base/declare",
     "dojo/_base/json",
     "me/core/enme"],
    function(
    declare,
    json,
    _ENME) {

  return declare(null, {

         /*
        * the key to restore / save the status of the widget in the browser
        */
       _key_save : 'filter-default',

       /*
        * save the status of all components.
        */
       _status : {},

       /*
        * Save the status of widget in the browser
        */
       _saveStatus : function(val) {
           _ENME.storeItem(this._key_save, val);
           dojo.publish('/encuestame/filters/invoke');
       },

       /*
        * Clean the storage
        */
       clean : function () {
           _ENME.log("clean filter should be implemented");
       },

       /*
        * Customized re-build
        */
       _buildStatusObject : function(data) {},

       /*
        * Restore the status form storage data.
        */
       _restoreStatus : function() {
           var _saved = _ENME.restoreItem(this._key_save);
           _ENME.log("RESTORE STATUS", _saved);
           if(_saved) {
               this._status = json.fromJson(_saved);
               _ENME.log("RESTORE STATUS this._status", this._status);
               this._buildStatusObject(this._status);
           }
       }

  });
});