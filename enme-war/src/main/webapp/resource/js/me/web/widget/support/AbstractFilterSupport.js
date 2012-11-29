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
        *
        */
       _buildStatusObject : function() {},

       /* 
        * 
        */
       _restoreStatus : function() {
           var _saved = _ENME.restoreItem(this._key_save);
           console.log("RESTORE STATUS", _saved);
           if(_saved) {
               this._status = json.fromJson(_saved);
               console.log("RESTORE STATUS this._status", this._status);
               this._buildStatusObject(this._status);
           }
       },       

  });
});