dojo.provide("encuestame.org.core.commons.support.Wipe");

dojo.require("dojox.charting.Chart2D");
dojo.require("dojox.charting.plot2d.Pie");
dojo.require("dojox.charting.action2d.Highlight");
dojo.require("dojox.charting.action2d.MoveSlice");
dojo.require("dojox.charting.action2d.Tooltip");
dojo.require("dojox.charting.themes.MiamiNice");
dojo.require("dojox.charting.widget.Legend");

dojo.declare("encuestame.org.core.commons.support.Wipe", null, {

        node : null,
        duration : 200,
        height : 300,

        constructor: function(node, duration, heigth) {
                this.node = node;
                if (duration != null) {
                    this.duration = duration;
                }
                if (heigth != null) {
                    this.heigth = heigth;
                }
        },

        /*
        *
        */
       wipeInOne: function() {
           if (this.node) {
               dojox.fx.wipeTo({
                    node: this.node,
                   duration: this.duration,
                   height: this.height
               }).play();
           }
       },

       /*
        *
        */
       wipeOutOne : function() {
           if (this.node) {
               dojox.fx.wipeOut({
                   node: this.node,
                  duration: this.duration
               }).play();
           }
       }
});
