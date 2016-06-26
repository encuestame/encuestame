"use strict";

    var body = {
        get: function(url, _module, id, instance) {
            var script = document.createElement('script');
            var name = "__enme_widget.callbacks._" + _module +"_" + id;
            script.src = url + '?id=' + id + '&callback=' + name;
            document.getElementsByTagName('head')[0].appendChild(script);
        }
    };

module.exports = body;
