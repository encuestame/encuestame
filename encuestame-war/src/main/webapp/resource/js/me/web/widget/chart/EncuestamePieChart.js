
//https://dojotoolkit.org/reference-guide/1.8/dojox/charting.html#pie-chart

define([
     "dojo/_base/declare",
     "dojox/charting/Chart",
     "dojox/charting/plot2d/Pie",
     "dojox/charting/action2d/Highlight",
     "dojox/charting/action2d/MoveSlice" ,
     "dojox/charting/action2d/Tooltip",
     "dojox/charting/themes/MiamiNice",
     "dojox/charting/widget/Legend",
     "me/core/enme"],
    function(
    declare,
    chart,
    pie,
    highlight,
    moveSlice,
    tooltip,
    miami,
    legend,
    _ENME) {

  return declare(null, {

    /**
    * Constructor.
    * @param node
    * @param results
    * @param size
    */
   constructor: function(node, results, size) {
           this.node = node;
           this.data = results;
           this.size = size;
           //console.debug("data", this.data);
   },

   /**
    * Node to append the chart.
    */
   node : null,

   /**
    * Default size of the chart.
    */
   size : 280,

   /**
    * Chart data.
    */
   data : [],

   /**
    * Series.
    */
   _seriesData : [],

   /**
    * Render the chart.
    */
   render: function() {
       this._buildSeries();
       var chartTwo = new chart(this.node);
       chartTwo.setTheme(miami).addPlot("default", {
           type: pie,
           font: "normal normal 12px Myriad,Helvetica,Tahoma,Arial,clean,sans-serif",
           fontColor: "black",
           labelOffset: -30,
           radius: this.size
       }).addSeries("A", this._seriesData);
       var anim_a = new moveSlice(chartTwo, "default");
       var anim_b = new highlight(chartTwo, "default");
       var anim_c = new tooltip(chartTwo, "default");
       chartTwo.render();
       var legendTwo = new legend({
           chart: chartTwo
       },
       "legendTwo");
   },

   /**
    * Build series.
    */
   _buildSeries : function() {
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
   }

  });
});
///*
// ************************************************************************************
// * Copyright (C) 2001-2011 encuestame: open source social survey Copyright (C) 2009
// * encuestame Development Team.
// * Licensed under the Apache Software License version 2.0
// * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
// * Unless required by applicable law or agreed to  in writing,  software  distributed
// * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
// * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
// * specific language governing permissions and limitations under the License.
// ************************************************************************************
// */
//dojo.provide("encuestame.org.core.commons.chart.AbstractChartVoteSupport");
//
//dojo.require('dojox.timing');
//dojo.require("encuestame.org.core.commons.dashboard.chart.EncuestamePieChart");
//
//dojo.declare("encuestame.org.core.commons.chart.AbstractChartVoteSupport", null, {
//

//});


///*
// ************************************************************************************
// * Copyright (C) 2001-2011 encuestame: open source social survey Copyright (C) 2009
// * encuestame Development Team.
// * Licensed under the Apache Software License version 2.0
// * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
// * Unless required by applicable law or agreed to  in writing,  software  distributed
// * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
// * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
// * specific language governing permissions and limitations under the License.
// ************************************************************************************
// */
//dojo.provide("encuestame.org.core.commons.dashboard.chart.EncuestamePieChart");
//
//dojo.require("dojox.charting.Chart2D");
//dojo.require("dojox.charting.plot2d.Pie");
//dojo.require("dojox.charting.action2d.Highlight");
//dojo.require("dojox.charting.action2d.MoveSlice");
//dojo.require("dojox.charting.action2d.Tooltip");
//dojo.require("dojox.charting.themes.MiamiNice");
//dojo.require("dojox.charting.widget.Legend");
//
//dojo.declare("encuestame.org.core.commons.dashboard.chart.EncuestamePieChart", null, {
//
//        /**
//         * Constructor.
//         * @param node
//         * @param results
//         * @param size
//         */
//        constructor: function(node, results, size) {
//                this.node = node;
//                this.data = results;
//                this.size = size;
//                //console.debug("data", this.data);
//        },
//
//        /**
//         * Node to append the chart.
//         */
//        node : null,
//
//        /**
//         * Default size of the chart.
//         */
//        size : 80,
//
//        /**
//         * Chart data.
//         */
//        data : [],
//
//        /**
//         * Series.
//         */
//        _seriesData : [],
//
//        /**
//         * Render the chart.
//         */
//        render: function() {
//            this._buildSeries();
//            var dc = dojox.charting;
//            var chartTwo = new dc.Chart2D(this.node);
//            chartTwo.setTheme(dc.themes.MiamiNice).addPlot("default", {
//                type: "Pie",
//                font: "normal normal 12px Myriad,Helvetica,Tahoma,Arial,clean,sans-serif",
//                fontColor: "black",
//                labelOffset: -30,
//                radius: this.size
//            }).addSeries("A", this._seriesData);
//            var anim_a = new dc.action2d.MoveSlice(chartTwo, "default");
//            var anim_b = new dc.action2d.Highlight(chartTwo, "default");
//            var anim_c = new dc.action2d.Tooltip(chartTwo, "default");
//            chartTwo.render();
//            var legendTwo = new dojox.charting.widget.Legend({
//                chart: chartTwo
//            },
//            "legendTwo");
//        },
//
//        /**
//         * Build series.
//         */
//        _buildSeries : function() {
//             this._seriesData = [];
//              dojo.forEach(
//                      this.data,
//                      dojo.hitch(this, function(data, index) {
//                          if (data[1] > 0) {
//                             var item = {
//                                 y: data[1],
//                                 text: data[0],
//                                 stroke: "black",
//                                 tooltip: data[1]
//
//                             };
//                             if (data[2]) {
//                                 item.fill = data[2];
//                             }
//                             this._seriesData.push(item);
//                         }
//           }));
//        }
//});
