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
   publish_url : null,

   /*
    *
    */
   constructor: function(){
       this.publish_url = "";
   },

   /*
    * get action.
    */
   publish : function(params) {
        if (this.publish_url != null && this.publish_url != "") {
            dojo.publish(this.publish_url, [params]);
        }
   }

  });
});