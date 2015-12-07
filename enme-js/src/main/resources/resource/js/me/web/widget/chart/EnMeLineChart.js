define( [
    "dojo/_base/declare",
    "dojo/_base/array",
    "dojo/_base/lang",
    "me/web/widget/chart/AbstractChart",
    "me/core/enme" ],
    function(
        declare,
        _array,
        lang,
        AbstractChart,
        _ENME ) {

        return declare( [ AbstractChart ], {

            /**
             * Render the chart.
             */
            render: function() {
                this.getChart().Line( this._buildSeries(), {});
            },

            /**
             * Constructor.
             * @param node
             * @param results
             * @param size
             */
            constructor: function( node, results, period ) {},

            /**
             * Build series.
             */
            _buildSeries: function() {
                var Labels = [ "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" ];
                var preparateDate = function( dates ) {
                    var years = [];
                    var months = [];
                    var grouped = [];
                    dates.forEach( function( entry ) {
                        grouped.push({
                            date: new Date( entry.year, entry.month, entry.day ),
                            value: entry.value
                        });
                        grouped.sort( function( a, b ) {
                            return b.date - a.date;
                        });
                    });
                    return grouped.sort();
                };

                var data_stats = preparateDate( this.data );

                //Console.log("data_stats", data_stats);

                // Agrupar valores por meses y años
                var month_year = {};
                data_stats.forEach( function( entry ) {
                    var year = entry.date.getFullYear();
                    var month = entry.date.getMonth();
                    if ( typeof month_year[ year ] === "undefined" ) {
                        month_year[ year ] = {};
                    }
                    if ( typeof month_year[ month ] === "undefined" ) {
                        month_year[ year ][ Labels[ month ]] = entry.value;
                    } else {
                        var v = month_year[ year ][ month ];
                        month_year[ year ][ Labels[ month ]] = v + entry.value;
                    }
                });

                //Console.log("month_year", month_year);
                var label_m_y = [];
                var label_values = [];
                for ( var prop in month_year ) {
                    if ( month_year.hasOwnProperty( prop ) ) {
                        var b = month_year[ prop ];

                        //Console.log(prop + " = ", month_year[prop]);
                        for ( var prop2 in b ) {
                            if ( b.hasOwnProperty( prop2 ) ) {

                                //Console.log(prop2 + " = ", b[prop2]);
                                label_m_y.push( prop2 + "-" + prop );
                                label_values.push( b[ prop2 ] );
                            }
                        }
                    }
                }
                console.log("label_m_y", label_m_y );
                console.log("label_values", label_values );

                var data = {
                    labels: label_m_y,
                    datasets: [
                        {
                            fillColor: "rgba(220,220,220,0.5)",
                            strokeColor: "rgba(220,220,220,1)",
                            pointColor: "rgba(220,220,220,1)",
                            pointStrokeColor: "#fff",
                            data: label_values
                        }
                    ]
                };

                return data;
            }

        });
    });
