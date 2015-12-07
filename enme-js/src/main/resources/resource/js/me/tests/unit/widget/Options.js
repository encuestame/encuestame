define( [
    "intern!object",
    "intern/chai!assert",
    "me/web/widget/options/AbstractOptionSelect",
    "me/web/widget/options/CheckSingleOption",
    "me/web/widget/options/CommentsOptions",
    "me/web/widget/options/ConstrainNumberPicker",
    "me/web/widget/options/DateToClose",
    "me/web/widget/options/EmbebedOptions",
    "me/web/widget/options/LimitVotes",
    "me/web/widget/options/PublishOptions",
    "me/web/widget/options/RepeatedVotes",
    "me/web/widget/options/ResultsOptions",
    "me/web/widget/options/YesNoWidget"
], function(
    registerSuite,
    assert,
    AbstractOptionSelect,
    CheckSingleOption,
    CommentsOptions,
    ConstrainNumberPicker,
    DateToClose,
    EmbebedOptions,
    LimitVotes,
    PublishOptions,
    RepeatedVotes,
    ResultsOptions,
    YesNoWidget ) {
    registerSuite({
        name: "Home Widgets",

        "default data": function() {
            var yes = new YesNoWidget({
				id: 1,
	            data: true
            });
        },

        "AbstractOptionSelect Widget": function() {
            var abstractOption = new AbstractOptionSelect({

            });
            assert.isObject( abstractOption, "AbstractOptionSelect should be an object" );
        },

        "CheckSingleOption Widget": function() {
            var checkSingleOption = new CheckSingleOption({

            });
            assert.isObject( checkSingleOption, "CheckSingleOption should be an object" );
        },

        "CommentsOptions Widget": function() {
            var commentsOptions = new CommentsOptions({

            });
            assert.isObject( commentsOptions, "CommentsOptions should be an object" );
        },

        "ConstrainNumberPicker Widget": function() {
            var constraintNumber = new ConstrainNumberPicker({

            });
            assert.isObject( constraintNumber, "ConstrainNumberPicker should be an object" );
        },

        "DateToClose Widget": function() {
            var dateToClose = new DateToClose({

            });
            assert.isObject( dateToClose, "DateToClose should be an object" );
        },

        "EmbebedOptions Widget": function() {
            var embebedOptions = new EmbebedOptions({

            });
            assert.isObject( embebedOptions, "EmbebedOptions should be an object" );
        },

        "LimitVotes Widget": function() {
            var limitVotes = new LimitVotes({

            });
            assert.isObject( limitVotes, "LimitVotes should be an object" );
        },

        "PublishOptions Widget": function() {
            var publishOptions = new PublishOptions({

            });
            assert.isObject( publishOptions, "PublishOptions should be an object" );
        },

        "RepeatedVotes Widget": function() {
            var repeatedVotes = new RepeatedVotes({

            });
            assert.isObject( repeatedVotes, "RepeatedVotes should be an object" );
        },

        "ResultsOptions Widget": function() {
            var resultsOption = new ResultsOptions({

            });
            assert.isObject( resultsOption, "ResultsOption should be an object" );
        }
    });
});
