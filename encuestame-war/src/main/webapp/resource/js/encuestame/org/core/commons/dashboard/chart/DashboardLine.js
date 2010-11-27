dojo.provide("encuestame.org.class.commons.dashboard.chart.DashboardLine");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");

dojo.declare(
    "encuestame.org.class.commons.dashboard.chart.DashboardLine",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.class.commons.dashboard.chart", "template/dashboardLine.inc"),

        widgetsInTemplate: true,

        postMixInProperties: function(){

        },

        postCreate: function() {

        },
    }
);
