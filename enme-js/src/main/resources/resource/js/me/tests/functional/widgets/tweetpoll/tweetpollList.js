/**
 * Copyright 2015 encuestame
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
define([
    'intern!object',
    'intern/chai!assert',
    'require',
    'intern/dojo/node!leadfoot/helpers/pollUntil'
], function (
    registerSuite,
    assert,
    require,
    pollUntil) {
    registerSuite({
        name: 'TweetPoll List',
        'functional test': function () {
            var url = require.toUrl('./tweetpoll-list.html');
            return this.remote.get(url)
                .setFindTimeout(90000)
                .setPageLoadTimeout(90000)
                .setExecuteAsyncTimeout(90000)
                .then(pollUntil('return window.ready', 90000))
                .findAllByClassName('web-tweetpoll-list-item')
                .then(function (items) {
                    //alert(1);
                    assert.equal(2, items.length);
                }).end();
        }
    });
});
