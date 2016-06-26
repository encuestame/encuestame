define( [
     "dojo/_base/declare",
     "me/web/widget/chart/AbstractChart",
     "me/core/enme" ],
    function(
    declare,
    AbstractChart,
    _ENME ) {

  return declare( [ AbstractChart ], {

        /**
        * Constructor.
        * @param node
        * @param results
        * @param size
        */
       constructor: function( node, results, size ) {},

       /**
        * Render the chart.
        */
       render: function() {
           var data = [
               {
                   value: 30,
                   color:"#F38630"
               },
               {
                   value: 50,
                   color: "#E0E4CC"
               },
               {
                   value: 100,
                   color: "#69D2E7"
               }
           ];
           var _chart = this.getChart().Pie( this._buildSeries(), {});

       },

       /**
        * Build series.
        */
       _buildSeries: function() {
            var _seriesData = [];
             dojo.forEach(
             this.data,
             dojo.hitch( this, function( data, index ) {
                 if ( data[ 1 ] > 0 ) {
                    var item = {
                        value: data[ 1 ]

                    };
                    if ( data[ 2 ] ) {
                        item.color = data[ 2 ];
                    }
                    _seriesData.push( item );
                }
          }) );
          return _seriesData;
       }

  });
});
