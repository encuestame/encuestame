"use strict";

var  base = require('./../widget_base');
var _module = "profile_w";
var detail = require('./../detail');
var fn = {
    render: function(widget, onRender) {
        var iframeDom = base.getDocument(widget);
        var cssNode = base.getCss(detail.cssStyle);
        iframeDom.style.cssText = '';
        iframeDom.style.width = window.innerWidth - 20 + "px";
        iframeDom.height = detail.box_dimensions.DEFAULT_HEIGHT;
        iframeDom.style.border = "none";
        iframeDom.style.maxWidth = "450px";
        iframeDom.style.minWidth = detail.box_dimensions.MIN_WIDTH + "px";
        global.__enme_widget.callbacks["_" + _module +"_" + widget.widget.properties.id] = function(data) {
            widget.body = data;
            onRender(widget, iframeDom, cssNode);
        };
        base.getBody(widget, _module);
    }
};

module.exports = fn;
