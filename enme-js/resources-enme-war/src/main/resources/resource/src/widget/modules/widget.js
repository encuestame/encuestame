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

if (typeof global.__enme_widget != "undefined" && global.__enme_widget.host) {
    (function(document) {

        var _widget = {};
        _widget.callbacks = {};
        _widget.host  = global.__enme_widget.host;
        _widget.initialized = false;
        global.__enme_widget = _widget;
            var base = require('./widgets/base'),
            util = require('./util/dom-utils'),
            render = require('./widgets/render'),
            _ = require('underscore');

            var widgets_selectors = {
                'a.enme-poll-form': base.createPollForm,
                'a.enme-poll-vote': base.createPollVote,
                'a.enme-tp-form'  : base.createTpForm,
                'a.enme-tp-vote'  : base.createTpVote,
                'a.enme-profile'  : base.createProfile,
                'a.enme-hashtag'  : base.createHashtag
            };

            var initialize_widgets = function() {
                if (!_widget.initialized) {
                    render.findWidgets(widgets_selectors, function (data) {
                        _.each(data, function (a, b) {
                            a._module(a);
                        });
                    });
                    _widget.initialized = true;
                }
            };

            util.listen("load", window, function() {
                initialize_widgets();
            });

            _widget.init = initialize_widgets;


    })(document);
}
