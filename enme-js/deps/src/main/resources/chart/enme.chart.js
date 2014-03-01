//temp file

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

		/**
		 * Display a hastag graph
		 * @property
		 */
		hashtag: function(selector, data, _options) {

			var margin = {
					top: 20,
					right: 50,
					bottom: 20,
					left: 20
				},
				width = _options.width  - margin.left - margin.right,
				height = _options.height - margin.top - margin.bottom;

			var parse = d3.time.format("%b %Y").parse;

			// Scales and axes. Note the inverted domain for the y-scale: bigger is up!
			var x = d3.time.scale().range([0, width]),
				y = d3.scale.linear().range([height, 0]),
				xAxis = d3.svg.axis().scale(x).tickSize(-height).tickSubdivide(true),
				yAxis = d3.svg.axis().scale(y).ticks(4).orient("right");

			// An area generator, for the light fill.
			var area = d3.svg.area()
				.interpolate("monotone")
				.x(function(d) {
					return x(d.miliseconds_date);
				})
				.y0(height)
				.y1(function(d) {
					return y(d.value);
				});

			// A line generator, for the dark stroke.
			var line = d3.svg.line()
				.interpolate("monotone")
				.x(function(d) {
					return x(d.miliseconds_date);
				})
				.y(function(d) {
					return y(d.value);
				});

			// sort items by date
			data = data.sort(function(a,b) {
				a = new Date(a.miliseconds_date);
				b = new Date(b.miliseconds_date);
				return a < b ? - 1 : a > b ? 1 : 0;
			});

			// Filter to one symbol; the S&P 500.
			var values = data.filter(function(d) {
				return d.value !== null;
			});

			// Compute the minimum and maximum date, and the maximum value.
			x.domain([values[0].miliseconds_date, values[values.length - 1].miliseconds_date]);
			y.domain([0, d3.max(values, function(d) { return d.value; })]).nice();

			// Add an SVG element with the desired dimensions and margin.
			var svg = d3.select(selector).append("svg")
				.attr("width", width + margin.left + margin.right)
				.attr("height", height + margin.top + margin.bottom)
				.append("g")
				.attr("transform", "translate(" + margin.left + "," + margin.top + ")")
				.on("click", click);

			// Add the clip path.
			svg.append("clipPath")
				.attr("id", "clip")
				.append("rect")
				.attr("width", width)
				.attr("height", height);

			// Add the area path.
			svg.append("path")
				.attr("class", "area")
				.attr("clip-path", "url(#clip)")
				.attr("d", area(values));

			// Add the x-axis.
			svg.append("g")
				.attr("class", "x axis")
				.attr("transform", "translate(0," + height + ")")
				.call(xAxis);

			// Add the y-axis.
			svg.append("g")
				.attr("class", "y axis")
				.attr("transform", "translate(" + width + ",0)")
				.call(yAxis);

			// Add the line path.
			svg.append("path")
				.attr("class", "line")
				.attr("clip-path", "url(#clip)")
				.attr("d", line(values));

			// Add a small label for the symbol name.
			svg.append("text")
				.attr("x", width - 6)
				.attr("y", height - 6)
				.style("text-anchor", "end")
				.text(values[0].symbol);

			// On click, upmiliseconds_date the x-axis.
			function click() {
				// var n = values.length - 1,
				//     i = Math.floor(Math.random() * n / 2),
				//     j = i + Math.floor(Math.random() * n / 2) + 1;
				// x.domain([values[i].miliseconds_date, values[j].miliseconds_date]);
				// var t = svg.transition().duration(750);
				// t.select(".x.axis").call(xAxis);
				// t.select(".area").attr("d", area(values));
				// t.select(".line").attr("d", line(values));
			}

			// Parse miliseconds_dates and numbers. We assume values are sorted by miliseconds_date.
			function type(d) {
				var date = new Date(d.miliseconds_date);
				d.miliseconds_date = date.toString();
				d.value = + d.value;
				return d;
			}
		},

		/**
		 * Display a line graph.
		 * @property selector
		 * @property data
		 * @property _options
		 */
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

		/**
		 * Cloud graph
		 * @property
		 */
		cloud: function() {

		},

		// pie graph
		pie: function(selector, data, _options) {
			options = _.extend(options, _options);
			var w = 300,                        //width
				h = 300,                            //height
				r = 100,                            //radius
				color = d3.scale.category20c();     //builtin range of colors

			// data = [{"label":"one", "value":20},
			//         {"label":"two", "value":50},
			//         {"label":"three", "value":30}];

			var vis = d3.select(selector)
				.append("svg:svg")              //create the SVG element inside the <body>
				.data([data])                   //associate our data with the document
				.attr("width", w)           //set the width and height of our visualization (these will be attributes of the <svg> tag
				.attr("height", h)
				.append("svg:g")                //make a group to hold our pie chart
				.attr("transform", "translate(" + r + "," + r + ")")    //move the center of the pie chart from 0, 0 to radius, radius

			var arc = d3.svg.arc()              //this will create <path> elements for us using arc data
				.outerRadius(r);

			var pie = d3.layout.pie()           //this will create arc data for us given a list of values
				.value(function(d) { return d.value; });    //we must tell it out to access the value of each element in our data array

			var arcs = vis.selectAll("g.slice")     //this selects all <g> elements with class slice (there aren't any yet)
				.data(pie)                          //associate the generated pie data (an array of arcs, each having startAngle, endAngle and value properties)
				.enter()                            //this will create <g> elements for every "extra" data element that should be associated with a selection. The result is creating a <g> for every object in the data array
				.append("svg:g")                //create a group to hold each slice (we will have a <path> and a <text> element associated with each slice)
				.attr("class", "slice");    //allow us to style things in the slices (like text)
			arcs.append("svg:path")
				.attr("fill", function(d, i) { return color(i); } ) //set the color for each slice to be chosen from the color function defined above
				.attr("d", arc);                                    //this creates the actual SVG path using the associated data (pie) with the arc drawing function
			arcs.append("svg:text")                                     //add a label to each slice
				.attr("transform", function(d) {                    //set the label's origin to the center of the arc
					//we have to make sure to set these before calling arc.centroid
					d.innerRadius = 0;
					d.outerRadius = r;
					return "translate(" + arc.centroid(d) + ")";        //this gives us a pair of coordinates like [50, 50]
				})
				.attr("text-anchor", "middle")                          //center the text on it's origin
				.text(function(d, i) { return data[i].label; });        //get the label from our original data array
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