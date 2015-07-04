//TODO: deprecated, remove

define([
         "dojo/_base/declare",
         "dijit/Dialog",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "dojo/Deferred",
         "dojo/_base/fx",
         "me/core/enme"],
        function(
                declare,
                dialog,
                main_widget,
                Deferred,
                fx,
                _ENME) {
            return declare([dialog], {

          draggable : false,

          style : "width: 200px; height: 200px;",

          destroy_after_hide : true,

          //post create
          postCreate : function(){
              this.inherited(arguments);
          },

          onHide : function() {
              //something before hide.
          },

          /*
           *
           */
          _afterHide : function() {
              if( this.destroy_after_hide) {
                  console.info("after hide");
                  //this.destroy();
              }
          }
    });
});