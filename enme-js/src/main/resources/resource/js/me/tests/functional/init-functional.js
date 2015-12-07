require( [
    "dojo/parser",
    "../../../unit/Helper.js",
    "dojo/domReady!"
], function( parser, Helper ) {
    Helper.init({
        contextPath: "../../../resources"
    });
    Helper.createElement( "mainWrapper" );
    Helper.createElement( "previewWrapperFixed" );
    parser.parse( document.body ).then( function() {
        ready = true;
    });
});
