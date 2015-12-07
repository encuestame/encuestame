define( [
     "dojo/_base/declare",
     "me/web/widget/dialog/Dialog",
     "me/core/enme" ],
    function(
    declare,
    Dialog,
    _ENME ) {

  return declare( null, {

      /*
      *
      */
     node: null,

     /*
      *
      */
     dialogHandlerWidget: null,

     /*
      *
      */
     hide_header: true,

     /*
      *
      */
     _dragable: false,

     /*
      *
      */
     constructor: function() {
          this.dialogHandlerWidget = new Dialog({
              content: this.getMessage(),
              style: "width: 700px",
              draggable: this._dragable
          });
          if ( this.hide_header ) {
              dojo.addClass( this.dialogHandlerWidget.titleBar, "defaultDisplayHide");
          }
     },

     /*
      *
      */
     getMessage: function() {
         return "";
     },

     /*
      * Show error message.
      */
     showErrorMessage: function( errorMessage ) {
         this.dialogHandlerWidget = new Dialog({
             content: errorMessage,
             style: "width: 700px",
             draggable: this._dragable
         });
         this.dialogHandlerWidget.show();
     },

     /*
      *
      */
     showSuccessMessage: function( successMessage ) {
         this.dialogHandlerWidget.content = successMessage;
         this.dialogHandlerWidget.show();
     }

  });
});
