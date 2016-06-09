define( [
     "dojo/_base/declare",
     "me/web/widget/chart/EncuestamePieChart",
     "me/core/enme" ],
    function(
    declare,
    EncuestamePieChart,
    _ENME ) {

  return declare( null, {

    nodeChart: null,

    widgetChart: null,

    _size: 100,

    /**
     * ChartLayerSupport
     * @param node node to append the cart.
     */
    constructor: function() {},

   /**
     * Build a new Chart.
     * @param options
     */
      buildChart: function( options ) {
          this.widgetChart = new EncuestamePieChart( options.id, options.results, this._size );
          return this.widgetChart;
      },

        /**
     * Render the chart
     */
    renderChart: function( render ) {
         if ( this.widgetChart ) {
             this.widgetChart.render();
         }
    }

  });
});
