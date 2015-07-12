define([
    'intern!object',
    'intern/chai!assert',
    "me/core/enme"
], function (registerSuite, assert, _ENME) {
    registerSuite({
        name: 'enme',
        // before the suite starts
        setup: function () {
            //var express = require('express');
            //var app = express();
            //console.log("EXPRESS", app);
        },
        'default data': function () {
            assert.strictEqual(true, true, 'Id should default to true');
        }
    });
});