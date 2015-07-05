    "use strict";

    var env = require('./env');
    var prefix = "data-";
    var fn = {

    /**
     *
     * @method
     */
    querySelectorAll: function(selectors) {
        // http://caniuse.com/queryselector
        if (env.ie7() || env.ie6()) {
            //todo
        } else {
          return document.querySelectorAll(selectors);
        }
    },

    // Cross-browser implementation of element.addEventListener()
    listen : function(evnt, elem, func) {
        if (elem.addEventListener) { // W3C DOM
            elem.addEventListener(evnt, func, false);
        } else if (elem.attachEvent) { // IE DOM
            var r = elem.attachEvent("on"+evnt, func);
            return r;
        }
    },

    /**
     *
     * @param options
     */
    createCanvas : function(doc, options) {
        var canvas = doc.createElement("canvas");
        canvas.width = doc.body.clientWidth - 20;
        canvas.height = 200;
        return canvas;
    },

    /**
     *
     * @method
     */
    querySelector: function(selectors, doc) {
        // http://caniuse.com/queryselector
        if (env.ie7() || env.ie6()) {
            //todo
        } else {
          return doc ? doc.querySelector(selectors) : document.querySelector(selectors);
        }
    },

    /**
     *
     * @method
     */
    widgetInfo: function(widget) {
        var o = {
            id: widget.getAttribute(prefix + 'id'),
            url: widget.getAttribute(prefix + 'url')
        };
        return o;
    }
    };

    module.exports =  fn;
