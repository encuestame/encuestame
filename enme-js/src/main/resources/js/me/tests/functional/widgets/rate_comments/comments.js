define( [
    "intern!object",
    "intern/chai!assert",
    "require",
    "intern/dojo/node!leadfoot/helpers/pollUntil"
], function(
    registerSuite,
    assert,
    require,
    pollUntil ) {
    registerSuite({

        name: "Rate Comments",
        "functional test": function() {
            var url = require.toUrl( "./comments-rate.html" );
            return this.remote.get( url )
                .setFindTimeout( 88000 )
                .setPageLoadTimeout( 88000 )
                .setExecuteAsyncTimeout( 88000 )
                .then( pollUntil( "return window.ready", 88000 ) )
                .findAllByClassName( "web-rated-comment-item" )
                .then( function( items ) {
                    console.log("itemss=============>", items.length );
                    assert.equal( 4, items.length );
                    items[ 0 ].findByCssSelector( ".web-rated-comment-picture" )
                    .then( function( element ) {
                        assert.isDefined( element );
                        console.log("element--------->", element.length );
                            throw new Error("ds");

                        //Assert.isNotNull(element);
                        //assert.equal(1, element.length);
                    });
                });

        }
    });
});
