dojo.provide("encuestame.org.core.commons.dashboard.chart.EncuestamePieChart");

dojo.require("dojox.charting.Chart2D");
dojo.require("dojox.charting.plot2d.Pie");
dojo.require("dojox.charting.action2d.Highlight");
dojo.require("dojox.charting.action2d.MoveSlice");
dojo.require("dojox.charting.action2d.Tooltip");
dojo.require("dojox.charting.themes.MiamiNice");
dojo.require("dojox.charting.widget.Legend");

dojo.declare("encuestame.org.core.commons.dashboard.chart.EncuestamePieChart", null, {

        constructor: function(node, results, size){
                this.node = node;
                this.data = results;
                this.size = size;
                console.debug("data", this.data);
        },

        _buildSeries : function(){
             this._seriesData = [];
              dojo.forEach(
                      this.data,
                      dojo.hitch(this, function(data, index) {
                          if (data[1] > 0) {
                             var item = {
                                 y: data[1],
                                 text: data[0],
                                 stroke: "black",
                                 tooltip: data[1]

                             };
                             if (data[2]) {
                                 item.fill = data[2];
                             }
                             this._seriesData.push(item);
                         }
           }));
        },

        node : null,

        size : 80,

        data : [],

        _seriesData : [],

        render: function(){
            console.debug("render 1");
            this._buildSeries();
            console.debug("render 2");
            var dc = dojox.charting;
            var chartTwo = new dc.Chart2D(this.node);
            console.debug("render 3");
            chartTwo.setTheme(dc.themes.MiamiNice).addPlot("default", {
                type: "Pie",
                font: "normal normal 11pt Tahoma",
                fontColor: "black",
                labelOffset: -30,
                radius: this.size
            }).addSeries("A", this._seriesData);
            console.debug("render 4");
            var anim_a = new dc.action2d.MoveSlice(chartTwo, "default");
            var anim_b = new dc.action2d.Highlight(chartTwo, "default");
            var anim_c = new dc.action2d.Tooltip(chartTwo, "default");
            console.debug("render 5");
            chartTwo.render();
            console.debug("render 6");
            var legendTwo = new dojox.charting.widget.Legend({
                chart: chartTwo
            },
            "legendTwo");
            console.debug("render 7");
        }
});
