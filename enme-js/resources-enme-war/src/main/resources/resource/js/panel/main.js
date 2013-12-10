define([
  'dojo/has', 
  'require'], function(has, require) {

  var app = {};

  if (has('host-browser')) {

    require([ 
      "dojo",
      "dojo/query",
      './PanelMenu',
      "panel/SlideTopPanel",
      "panel/SlideBottomPanel",
      'dojo/domReady!' ], function (dojo, query, PanelMenu, SlideTopPanel, SlideBottomPanel) {
      
      dojo.unwrap = dojo.unwrap || function( /* node ID or node */ n) {
        var node = dojo.byId(n);
        dojo.query(' > *', node).forEach(function(childNode) {
          dojo.place(childNode, node, 'before');
        });

        dojo.destroy(node);
      };

      var options = {
        "openPosition" : '250px',
        "slide_top_menu" : ".main",
        "slide_bottom_menu" : "#bottom"
      };

      app.panel = new PanelMenu(options);
      app.top_panel = new SlideTopPanel(options, app.panel);
      app.bottom_panel = new SlideBottomPanel(options, app.panel);
      
    });

  }
});