define( [
     "dojo/_base/declare",
     "me/core/enme" ],
    function(
    declare,
    _ENME ) {

  return declare( null, {

   /**
    * PostCreate life cycle.
    */
     postCreate: function() {}
  });
});

// Dojo.provide("encuestame.org.core.commons.dashboard.chart.DashboardPie");

// Dojo.require("dijit._Templated");
// dojo.require("dijit._Widget");

// Dojo.require("dojox.charting.Chart2D");
// dojo.require("dojox.charting.plot2d.Pie");
// dojo.require("dojox.charting.action2d.Highlight");
// dojo.require("dojox.charting.action2d.MoveSlice");
// dojo.require("dojox.charting.action2d.Tooltip");
// dojo.require("dojox.charting.themes.MiamiNice");
// dojo.require("dojox.charting.widget.Legend");

// Dojo.declare(
//     "encuestame.org.core.commons.dashboard.chart.DashboardPie",
//     [dijit._Widget, dijit._Templated],{
//         templatePath: dojo.moduleUrl("encuestame.org.core.commons.dashboard.chart", "template/dashboardPie.html"),

//         WidgetsInTemplate: true,

//         Data : null,

//         PostCreate: function() {
//             dojo.subscribe("/encuestame/chart/pie/render", this, "render");
//         },

//         Render: function(){

//         }
//     }
// );
