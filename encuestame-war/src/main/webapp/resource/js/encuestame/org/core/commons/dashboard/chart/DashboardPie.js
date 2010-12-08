dojo.provide("encuestame.org.core.commons.dashboard.chart.DashboardPie");


dojo.require("dijit._Templated");
dojo.require("dijit._Widget");

dojo.require("dojox.charting.Chart2D");
dojo.require("dojox.charting.plot2d.Pie");
dojo.require("dojox.charting.action2d.Highlight");
dojo.require("dojox.charting.action2d.MoveSlice");
dojo.require("dojox.charting.action2d.Tooltip");
dojo.require("dojox.charting.themes.MiamiNice");
dojo.require("dojox.charting.widget.Legend");


dojo.declare(
    "encuestame.org.core.commons.dashboard.chart.DashboardPie",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.dashboard.chart", "template/dashboardPie.inc"),

        widgetsInTemplate: true,

        data : null,

        postCreate: function() {
            dojo.subscribe("/encuestame/chart/pie/render", this, "render");
        },

        render: function(){

        }
    }
);
