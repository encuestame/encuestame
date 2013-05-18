(function(d3, document, window) {

    'use strict';


    //variables
    var options = {
        margin : {
            top: 20,
            right: 20,
            bottom: 30,
            left: 50
        },
        label : {
           y: "y",
           x: "x"
        }
    };

    // calculate dimensions
    var calculate_dimensions = function() {
      options.width = options.width - options.margin.left - options.margin.right;
      options.height = options.height - options.margin.top - options.margin.bottom;
    },
    onInit = function(_callback) {
        calculate_dimensions();
        _callback();
    };

    var parseDate = d3.time.format("%d-%b-%y").parse;

    var fn = {

        // display a line graph
        line: function(selector, data, _options) {
            options = _.extend(options, _options);
            onInit(_options.callback || function(){});
            var x = d3.time.scale().range([0, options.width]);

            var y = d3.scale.linear().range([options.height, 0]);

            var xAxis = d3.svg.axis().scale(x).orient("bottom");

            var yAxis = d3.svg.axis()
                .scale(y)
                .orient("left");

            var line = d3.svg.line().x(function(d) {
               return x(d.date);
            }).y(function(d) {
               return y(d.close);
            });

            // create the svg node
            var svg = d3.select(selector).append("svg")
                .attr("width", options.width + options.margin.left + options.margin.right)
                .attr("height", options.height + options.margin.top + options.margin.bottom)
                .append("g")
                .attr("transform", "translate(" + options.margin.left + "," + options.margin.top + ")");

              // set the x data
              x.domain(d3.extent(data, function(d) {
                return d.date;
              }));

              // set the y data
              y.domain(d3.extent(data, function(d) {
                return d.close;
              }));

              // create the x axis
              svg.append("g")
                  .attr("class", "x axis")
                  .attr("transform", "translate(0," + options.height + ")")
                  .call(xAxis);

              // create the y axis
              svg.append("g")
                  .attr("class", "y axis")
                  .call(yAxis)
                  .append("text")
                  .attr("transform", "rotate(-90)")
                  .attr("y", 6)
                  .attr("dy", ".71em")
                  .style("text-anchor", "end")
                  .text(options.label.y);

              // create the line
              svg.append("path")
                  .datum(data)
                  .attr("class", "line")
                  .attr("d", line);
        },

        // pie graph
        pie: function() {

        },

        // spider graph
        spider: function(){

        },

        // tagcloud
        tagcloud: function() {

        }
    }

    window.jchart = fn;

})(d3, document, window);