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
                var _chart = this.getChart().Bar( this._buildSeries(), {});

            },

            /**
             * Var data = {
                    labels: ["January", "February", "March", "April", "May", "June", "July"],
                    datasets: [
                        {
                            label: "My First dataset",
                            fillColor: "rgba(220,220,220,0.5)",
                            strokeColor: "rgba(220,220,220,0.8)",
                            highlightFill: "rgba(220,220,220,0.75)",
                            highlightStroke: "rgba(220,220,220,1)",
                            data: [65, 59, 80, 81, 56, 55, 40]
                        },
                        {
                            label: "My Second dataset",
                            fillColor: "rgba(151,187,205,0.5)",
                            strokeColor: "rgba(151,187,205,0.8)",
                            highlightFill: "rgba(151,187,205,0.75)",
                            highlightStroke: "rgba(151,187,205,1)",
                            data: [28, 48, 40, 19, 86, 27, 90]
                        }
                    ]
                };

             */

            /**
             * Build series.
             * this.data = [Array[3],Array[3]]
             * [ label, value, color ]
             */
            _buildSeries: function() {
                var _seriesData = {
                    labels: [ "" ],
                    datasets: []
                };
                dojo.forEach(
                    this.data,
                    dojo.hitch( this, function( data, index ) {
                        var d = {
                            label: data[ 0 ],
                            fillColor: data[ 2 ],
                            strokeColor: data[ 2 ],
                            data: [ data[ 1 ]]
                        };
                        _seriesData.datasets.push( d );
                    }) );
                return _seriesData;
            }

        });
    });
