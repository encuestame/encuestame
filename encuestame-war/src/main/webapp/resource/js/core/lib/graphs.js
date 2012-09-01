require(["dojo/parser", "ready!"], function(parser) {
	ENME.namespace("ENME.graph");
	
	ENME.graph = ( function(dojo, undefined) {
		
			var line_default = {
					smoot : false,
					radius : 1,
					symbol : 'circle',
					nostroke : false,
					axis : '0 0 1 1',
					gutter : 20,
					colors : ["#66B920"],
					shade : false,
					x : 14,
					y : 14
					
			};
			
			/**
			 * Create a Raphael JS Object.
			 * @param id
			 */
			var _raphaelGraph = function (id) {
				return  Raphael(id);
			};
		
			/**
			 * Render a circle graph.
			 */
			var circle = function() {
				
			};
			
			/**
			 * Render a line graph.
			 */
			var line = function(options) {
				  var graph_node = ENME.$.byId(options.id);
				  if (graph_node) {
					  var lines = _raphaelGraph(options.id).linechart(
							  options.x || line_default.x, 
							  options.y || line_default.y, 
							  options.width || 400, 
							  options.height || 200, 
							  options.valuesx, 
							  options.valuesy, { 
		              	nostroke: options.nostroke || line_default.nostroke, 
		              	axis: options.axis || line_default.axis, 
		              	symbol: options.symbol || line_default.symbol,
		              	gutter: options.gutter || line_default.gutter,
		              	colors: options.colors || line_default.colors,
		              	shade : options.shade || line_default.shade,
		              	width : options.radius || line_default.radius,
		              	smooth: options.smooth || line_default.smooth });
		              	//.hover(
		              	//		function(){console.log("1", this);}, 
		              	//		function(){console.log("2", this);}
		              	//		);
		              	lines.symbols.attr({ r: 2 });
		                lines.symbols[0].attr({stroke: "#888"});
		                return lines;
				  }
			};
			
			/**
			 * Render a bar graph.
			 */
			var bar = function() {
				
			};
		
			/**
			 * Grahp Facade pattern.
			 */
			return function() {
				try{
					typeof(arguments[0]) === 'string' ? "" : arguments[0] = "";
					arguments[1] = arguments[1] || {};
					if (arguments[1].id) { 
			            if ( arguments[0] === 'circle' ) {
			                return circle(arguments[1]);
			            } else if ( arguments[0] === 'line' ) {
			            	return line(arguments[1]);
			            } else if ( arguments[0] === 'bar' ) {
			            	return bar(arguments[1]);
			            } else {
			            	ENME.log("graph :: type not exist");
			            }
					} else {
						ENME.log("graph :: id is not defined");
					}
				} catch (e) {
					ENME.log("graph :: something is wrong");
				}
			};
		
	})(dojo);
});