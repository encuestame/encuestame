
"use strict";

var domUtils = require('../util/dom-utils');
var _ = require('underscore');
var fn = {

    /**
     *
     * @method
     */
    findWidgets: function(widgets_list, exec) {
        var list_wi = [];
        _.each(widgets_list, function(a,b,c) {
            var list = domUtils.querySelectorAll(b);
            for (var i = 0; i < list.length; i++) {
                var item_widget = list[i];
                list_wi.push({
                    node: item_widget,
                    properties: domUtils.widgetInfo(item_widget),
                    _module: a
                });
            }
        });
        exec(list_wi);
    },

    /**
     *
     * @method
     */
    load: function(){

    }
};

module.exports = fn;
