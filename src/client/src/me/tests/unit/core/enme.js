define( [
    "intern!object",
    "intern/chai!assert",
    "me/core/enme"
], function( registerSuite, assert, _ENME ) {
    registerSuite({
        name: "enme",

        // Before the suite starts
        setup: function() {

            //Var express = require('express');
            //var app = express();
            //console.log("EXPRESS", app);
        },
        "default data": function() {
            assert.strictEqual( true, true, "Id should default to true" );
        }
    });
});
