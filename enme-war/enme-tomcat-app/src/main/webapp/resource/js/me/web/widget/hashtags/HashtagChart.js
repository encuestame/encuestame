define([
     "dojo/_base/declare",
     "dojo",
     'chart/graphael/g.line',
     "me/core/enme"],
    function(
    declare,
    dojo,
    RaphaelLine,
    _ENME) {

    _ENME.log("_RaphaelLine", RaphaelLine);

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

  return declare(null, {

      /**
       * Render a line graph.
       */
      line : function(options) {
          var graph_node = _ENME.$.byId(options.id);
          if (graph_node) {
            var lines = RaphaelLine(options.id).linechart(
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
      }
  });
});
