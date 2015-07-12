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

'use strict';

var domain = global.__enme_widget.host;
var fn = {
    box_dimensions: {
        DEFAULT_HEIGHT: "230",
        DEFAULT_WIDTH: "520",
        NARROW_WIDTH: "320",
        MIN_WIDTH: "220",
        MIN_HEIGHT: "200",
        WIDE_MEDIA_PADDING: 81,
        NARROW_MEDIA_PADDING: 16,
        WIDE_MEDIA_PADDING_CL: 60,
        NARROW_MEDIA_PADDING_CL: 12
    },
    cssStyle: domain + "resources/dev/embebed/detail.css"
};

module.exports = fn;
