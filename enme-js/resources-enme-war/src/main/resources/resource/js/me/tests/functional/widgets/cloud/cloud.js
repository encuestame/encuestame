define([
    'intern!object',
    'intern/chai!assert',
    //'../../../unit/Helper',
    'require',
    'intern/dojo/node!leadfoot/helpers/pollUntil'
], function (
    registerSuite,
    assert,
    //Helper,
    require,
    pollUntil) {
    registerSuite({

        name: 'Cloud Functional Test',
        'functional test': function () {
            var url = require.toUrl('./cloud.html');
            return this.remote.get(url)
                .setFindTimeout(88000)
                .setPageLoadTimeout(88000)
                .setExecuteAsyncTimeout(88000)
                .then(pollUntil('return window.ready', 88000))
                .findAllByCssSelector('.enme-hashtag')
                .then(function (items) {
                    assert.equal(3, items.length);
                }).end();
        }
    });
});
