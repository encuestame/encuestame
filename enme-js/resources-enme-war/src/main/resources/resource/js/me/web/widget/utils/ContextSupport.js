//
// @deprecated use me/core/support/ContextSupport
//
//

define([
     "dojo/_base/declare",
     "me/core/enme"],
    function(
    declare,
    _ENME) {

  dojo.deprecated('use me/core/support/ContextSupport');

  return declare(null, {

     /*
      * context.
      */
     _context : ["tweetpoll", "poll", "survey"],

     /*
      *
      */
     context : null,

     /*
      *
      */
     constructor: function() {
         this.context = "";
     },

     /*
      * get action.
      */
     getContext : function(context) {
         var position = dojo.indexOf(this._context, context);
         //console.info("getAction", position);
         if (position == -1) {
             console.error("invalid context");
         } else {
             return position;
         }
     }
  });
});