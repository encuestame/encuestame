define([
    'intern!object',
    'intern/chai!assert',
    'dojo/Deferred',
    'require',
    'intern/dojo/node!leadfoot/helpers/pollUntil'
], function (
    registerSuite,
    assert,
    Deferred,
    require,
    pollUntil) {
    function asyncProcess(){
        var deferred = new Deferred();
        setTimeout(function(){
            deferred.resolve("success");
        }, 5000);
        return deferred.promise;
    }
    registerSuite({

        name: 'Hote Item Vote Test',

        'home vote item logged': function () {
            var url = require.toUrl('./ItemVoteLogged.html');
            return this.remote.get(url)
                .setFindTimeout(88000)
                .setPageLoadTimeout(88000)
                .setExecuteAsyncTimeout(10000)
                .then(pollUntil('return window.ready', 88000))
                .findByClassName('enme-rating')
                .click()
                .click()
                .then(function (e) {
                    console.log("--->", e);
                });
        },
        'home vote item not logged': function () {
            var url = require.toUrl('./ItemVote.html');
            return this.remote.get(url)
                .setFindTimeout(88000)
                .setPageLoadTimeout(88000)
                .setExecuteAsyncTimeout(10000)
                .then(pollUntil('return window.ready', 88000))
                .findByClassName('enme-rating')
                .click()
                .click()
                .then(function (e) {
                    console.log("--->", e);
                });
        }
    });
});
