"use strict";

 var form = require('./../form'),
     base = require('./../widget_base'),
    _module = "form_poll";

 var poll_form = {
    render : function(widget, onRender) {
        var iframeDom = base.getDocument(widget);
        var cssNode = base.getCss(form.cssStyle);
        iframeDom.height = form.box_dimensions.DEFAULT_HEIGHT;
        iframeDom.style.cssText = '';
        iframeDom.style.border = "none";
        iframeDom.style.maxWidth = "450px";
        iframeDom.style.width = window.innerWidth - 20 + "px";
        iframeDom.style.minWidth = form.box_dimensions.MIN_WIDTH + "px";
        global.__enme_widget.callbacks["_" + _module +"_" + widget.widget.properties.id] = function(data) {
            widget.body = data;
            onRender(widget, iframeDom, cssNode);
        };
        base.getBody(widget, _module);
    }
 };


module.exports = poll_form;
