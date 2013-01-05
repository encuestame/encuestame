
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