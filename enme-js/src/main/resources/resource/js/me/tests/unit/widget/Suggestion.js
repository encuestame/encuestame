define( [
    "intern!object",
    "intern/chai!assert",
    "me/web/widget/suggestion/Suggest",
    "me/web/widget/suggestion/SuggestItem"
], function(
    registerSuite,
    assert,
    Suggest,
    SuggestItem ) {
    registerSuite({
        name: "Suggestion Widgets",

        "default data": function() {
            var suggest = new Suggest({

            });
        },

        "SuggestItem Widget": function() {
            var suggestItem = new SuggestItem({
				data: {
					id: 1,
					label: "label"
				}
            });
            assert.isObject( suggestItem, "SuggestItem should be an object" );
        }
    });
});
