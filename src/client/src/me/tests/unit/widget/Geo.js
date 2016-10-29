define( [
    "intern!object",
    "intern/chai!assert",
    "me/web/widget/geo/BasicGeoWidget"
], function(
    registerSuite,
    assert,
    BasicGeoWidget ) {
    registerSuite({
        name: "Geo Widgets",

        "default data": function() {
            var basicGeoWidget = new BasicGeoWidget({

            });
        }
    });
});
