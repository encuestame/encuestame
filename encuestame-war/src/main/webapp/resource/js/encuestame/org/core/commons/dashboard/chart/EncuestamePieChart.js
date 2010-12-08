dojo.provide("encuestame.org.core.commons.dashboard.chart.EncuestamePieChart");

dojo.require("dojox.charting.Chart2D");
dojo.require("dojox.charting.plot2d.Pie");
dojo.require("dojox.charting.action2d.Highlight");
dojo.require("dojox.charting.action2d.MoveSlice");
dojo.require("dojox.charting.action2d.Tooltip");
dojo.require("dojox.charting.themes.MiamiNice");
dojo.require("dojox.charting.widget.Legend");

dojo.declare("encuestame.org.core.commons.dashboard.chart.EncuestamePieChart", null, {

        constructor: function(node){
                this.node = node;
        },

        node : null,

        render: function(){
            console.debug("render");
            var dc = dojox.charting;
            var chartTwo = new dc.Chart2D(this.node);
            chartTwo.setTheme(dc.themes.MiamiNice).addPlot("default", {
                type: "Pie",
                font: "normal normal 11pt Tahoma",
                fontColor: "black",
                labelOffset: -30,
                radius: 80
            }).addSeries("Series A", [{
                y: 4,
                text: "Red",
                stroke: "black",
                tooltip: "Red is 50%"
            },
            {
                y: 2,
                text: "Green",
                stroke: "black",
                tooltip: "Green is 25%"
            },
            {
                y: 1,
                text: "Blue",
                stroke: "black",
                tooltip: "I am feeling Blue!"
            },
            {
                y: 1,
                text: "Other",
                stroke: "black",
                tooltip: "Mighty <strong>strong</strong><br>With two lines!"
            }]);
            var anim_a = new dc.action2d.MoveSlice(chartTwo, "default");
            var anim_b = new dc.action2d.Highlight(chartTwo, "default");
            var anim_c = new dc.action2d.Tooltip(chartTwo, "default");
            chartTwo.render();
            var legendTwo = new dojox.charting.widget.Legend({
                chart: chartTwo
            },
            "legendTwo");
        }
});