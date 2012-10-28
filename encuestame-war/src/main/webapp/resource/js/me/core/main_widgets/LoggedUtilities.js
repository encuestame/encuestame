define([  "dojo",
          "dojo/_base/declare",
          "me/core/main_widgets/EnmeMainLayoutWidget",
          "dijit/registry",
          "me/core/enme"], function(
            dojo,
            declare,
            EnmeMainLayoutWidget,
            registry,
            _ENME) {

  return declare([EnmeMainLayoutWidget], {

      /*
       *
       */
      loading : function() {
          dojo.publish('encuestame/loading/display/on');
      },

      /*
       *
       */
      unloading : function() {
          dojo.publish('encuestame/loading/display/off');
      }
  });
});