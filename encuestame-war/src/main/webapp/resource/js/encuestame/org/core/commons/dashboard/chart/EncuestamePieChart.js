dojo.provide("encuestame.org.core.commons.dashboard.chart.EncuestamePieChart");

dojo.require("dojox.charting.Chart2D");
dojo.require("dojox.charting.plot2d.Pie");
dojo.require("dojox.charting.action2d.Highlight");
dojo.require("dojox.charting.action2d.MoveSlice");
dojo.require("dojox.charting.action2d.Tooltip");
dojo.require("dojox.charting.themes.MiamiNice");
dojo.require("dojox.charting.widget.Legend");

dojo.declare("encuestame.org.core.commons.dashboard.chart.EncuestamePieChart", null, {

        constructor: function(node, results){
                this.node = node;
                this.data = results;
                console.debug("data", this.data);
        },

        _buildSeries : function(){
              dojo.forEach(
                      this.data,
                      dojo.hitch(this, function(data, index) {
                         var item = {
                             y: data[1],
                             text: data[0],
                             stroke: "black",
                             tooltip: data[1]
                         };
                         this._seriesData.push(item);
           }));
        },

        node : null,

        data : [],

        _seriesData : [],

        render: function(){
            console.debug("render");
            this._buildSeries();
            var dc = dojox.charting;
            var chartTwo = new dc.Chart2D(this.node);
            chartTwo.setTheme(dc.themes.MiamiNice).addPlot("default", {
                type: "Pie",
                font: "normal normal 11pt Tahoma",
                fontColor: "black",
                labelOffset: -30,
                radius: 80
            }).addSeries("A", this._seriesData);
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