/**
 * Copyright 2014 encuestame
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

"use strict";

    var iframe = require('../util/iframe'),
    jsonp = require('../util/jsonp'),
    css = "";
    var fn =  {

        /**
         *
         * @method
         */
        getDocument: function(widget) {
            var _iframe = iframe.createIframeBody(widget);
            return _iframe;
        },

        /**
         *
         * @method
         */
        getCss: function(url) {
            var css = document.createElement("link");
            css.rel = "stylesheet";
            css.type = "text/css";
            css.href = url;
            return css;
        },

        /**
         *
         * @method
         */
        getBody: function(widget, _module) {
            jsonp.get(widget.url, _module, widget.widget.properties.id, null);
        }
    };

module.exports = fn;


