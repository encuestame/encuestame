    "use strict";
    var fn = {

        /**
         * Create the iframe body
         * @method
         */
        createIframeBody: function(widget) {
            var iframe,
            name = "enme-widget-";
                try {
                    iframe = document.createElement('<iframe name="' + name + widget.properties.id + '"></iframe>');
                } catch (f) {
                    iframe = document.createElement("iframe");
                }
            iframe.scrolling = "no";
            iframe.allowtransparency = "true";
            iframe.setAttribute("frameBorder", 0);
            iframe.setAttribute("allowTransparency", !0);
            iframe.style.width = window.innerWidth - 20 + "px";
            return iframe;
        }
    };

module.exports = fn;
